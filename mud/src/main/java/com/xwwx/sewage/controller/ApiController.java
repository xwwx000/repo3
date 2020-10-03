package com.xwwx.sewage.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.util.StringUtil;
import com.xwwx.sewage.bean.TCommLog;
import com.xwwx.sewage.bean.TCsOrder;
import com.xwwx.sewage.bean.TCsPdaUser;
import com.xwwx.sewage.cache.Cache;
import com.xwwx.sewage.common.Constant;
import com.xwwx.sewage.common.Md5;
import com.xwwx.sewage.common.StringUtils;
import com.xwwx.sewage.redis.RedisCacheManager;
import com.xwwx.sewage.service.LogService;
import com.xwwx.sewage.service.OrderService;
import com.xwwx.sewage.service.PdaService;
import com.xwwx.sewage.token.JwtUtil;

/**
 * 接口服务
 * 
 * @author 可乐罐
 *
 */
@Controller
@RequestMapping("/api")
public class ApiController {
	private static Logger logger = Logger.getLogger(ApiController.class);
	@Autowired
	@Qualifier("pdaService")
	private PdaService pdaService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private LogService logService;
	@Autowired
	private RedisCacheManager redisCacheManager;

	/**
	 * pda登录
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/pdalogin", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	private String login(HttpServletRequest request, HttpServletResponse response) {
		String postcontent = "";// 传递的内容
		StringBuffer logdesc = new StringBuffer();
		TCommLog log = new TCommLog();
		boolean loginResult = false;
		TCsPdaUser pdaUser = null;
		String userid = "";
		String password = "";
		String rstr = "";// 返回结果
		String accesskeyid = "";
		try {
			postcontent = request.getParameter("param");
			logdesc.append(postcontent);
			log.setUseripaddress(request.getRemoteAddr());
			log.setLogtype(2);// pad操作
			JSONObject json = (JSONObject) JSON.parse(postcontent);
			userid = json.getString("userid");
			log.setUserid(userid);
			password = json.getString("password");
			pdaUser = pdaService.getUserByUserIdAndPassword(userid, Md5.encrypt(password));
			log.setUsername(pdaUser.getPdausername());
			logger.info(pdaUser.toString());
			accesskeyid = (String) json.get("accesskeyid");
			logdesc.append("accesskeyid=").append(accesskeyid);

			if (pdaUser != null) {
				loginResult = true;
			}
			if (loginResult) {
				String token = JwtUtil.sign(pdaUser.getPdauserid(), pdaUser.getPdausername());
				response.setHeader("Access-Control-Expose-Headers", "no-store");
				response.setHeader("Cache-Control", "Authorization");
				response.setHeader("user_token", token);
				redisCacheManager.set(token, JSON.toJSONString(pdaUser), Constant.REDIS_EXPIRE_TIME);
				logger.info("token:" + token + "\n\r radis中token:" + redisCacheManager.get(token));
				rstr = "{\"code\":\"200\",\"message\":\"成功\",\"token\":\"" + token + "\",\"pdadept\":\""
						+ pdaUser.getPdadept() + "\"}";
				logdesc.append(" 成功-user_token=").append(token);
			} else {
				rstr = "{\"code\":\"201\",\"message\":\"用户名密码错误\"}";
				logdesc.append(" 失败-用户名密码错误");
			}
			// 判断accesskeyid是否登记过
			List<String> mList = Cache.macAddrList;
			if (!mList.contains(accesskeyid) || StringUtils.isEmpty(accesskeyid)) {
				rstr = "{\"code\":\"201\",\"message\":\"失败-非法accesskeyid\"}";
				logdesc.append(" 失败-非法accesskeyid");
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			logdesc.append(" 失败-登录出错!");
			rstr = "{\"code\":\"201\",\"message\":\"登录失败!\"}";
			e.printStackTrace();
		}
		log.setLogdesc(logdesc.toString());
		// 插入日志
		insertLog(log);
		return rstr;
	}

	/**
	 * 发货扫描rfid
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/pdasendgoodsrfid", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	private String padSendGoodsRfid(HttpServletRequest request, HttpServletResponse response) {
		String rstr = "";// 返回结果
		StringBuffer logdesc = new StringBuffer();
		TCommLog log = new TCommLog();
		String postcontent = request.getParameter("param");
		logdesc.append(postcontent);
		log.setUseripaddress(request.getRemoteAddr());
		log.setLogtype(2);// pad操作
		logger.info(postcontent);
		String token = "";
		String rfidcode = "";
		JSONObject paramJson = (JSONObject) JSON.parse(postcontent);
		token = (String) paramJson.get("token");
		rfidcode = (String) paramJson.get("rfidcode");
		if (StringUtils.isEmpty(rfidcode)) {
			rstr = "{\"code\":\"201\",\"message\":\"rfid码为空!\"}";
		} else {
			String tokenStr = (String) redisCacheManager.get(token);
			TCsPdaUser pda = JSON.parseObject(tokenStr, TCsPdaUser.class);
			log.setUserid(pda.getPdauserid());
			log.setUsername(pda.getPdausername());
			logger.info(pda);
			if (pda.getPdatype() == 0 || pda.getPdatype() == 1) {
				//判断rfidcode是否合法
				/*
				String tidcode = pdaService.getTidByCode(rfidcode);
				if(StringUtils.isEmpty(tidcode)) {
					rstr = "{\"code\":\"201\",\"message\":\"封签失败,无效标签!\"}";
				}else{
					// 封签
					String result = orderService.pdaSGoodsRfid(pda.getPdadept(), rfidcode);
					if (result.equals("-1")) {
						rstr = "{\"code\":\"201\",\"message\":\"封签失败!\"}";
					} else if (result.equals("2")) {
						rstr = "{\"code\":\"201\",\"message\":\"封签失败,多条等待封签的数据!\"}";
					} else if (result.equals("0")) {
						rstr = "{\"code\":\"201\",\"message\":\"没有要封签的数据!\"}";
					} else if (result.equals("3")) {
						rstr = "{\"code\":\"201\",\"message\":\"该标签已经被使用!\"}";
					}else {
						String tmp = result + " 发货封签成功!";
						rstr = "{\"code\":\"200\",\"message\":\"" + tmp + "\"}";
						try {
							//成功后删除tid表对应数据
							pdaService.deleteTidByCode(rfidcode);
						}catch(Exception e) {
							
						}
					}
				}
				*/
				// 封签
				String result = orderService.pdaSGoodsRfid(pda.getPdadept(), rfidcode);
				if (result.equals("-1")) {
					rstr = "{\"code\":\"201\",\"message\":\"封签失败!\"}";
				} else if (result.equals("2")) {
					rstr = "{\"code\":\"201\",\"message\":\"封签失败,多条等待封签的数据!\"}";
				} else if (result.equals("0")) {
					rstr = "{\"code\":\"201\",\"message\":\"没有要封签的数据!\"}";
				} else if (result.equals("3")) {
					rstr = "{\"code\":\"201\",\"message\":\"该标签已经被使用!\"}";
				}else {
					String tmp = result + " 发货封签成功!";
					rstr = "{\"code\":\"200\",\"message\":\"" + tmp + "\"}";
				}
				
			} else {
				rstr = "{\"code\":\"201\",\"message\":\"用户没有封签权限!\"}";
			}
		}
		// 插入日志
		logdesc.append(rstr);
		log.setLogdesc(logdesc.toString());
		insertLog(log);
		return rstr;
	}

	/**
	 * 收货扫描rfid
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/pdareceivegoodsrfid", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	private String padReceiveGoodsRfid(HttpServletRequest request, HttpServletResponse response) {
		String rstr = "";// 返回结果
		StringBuffer logdesc = new StringBuffer();
		TCommLog log = new TCommLog();
		String postcontent = request.getParameter("param");
		logdesc.append(postcontent);
		log.setUseripaddress(request.getRemoteAddr());
		log.setLogtype(2);// pad操作
		logger.info(postcontent);
		String token = "";
		String rfidcode = "";
		String lo = "";
		String la = "";
		JSONObject paramJson = (JSONObject) JSON.parse(postcontent);
		token = (String) paramJson.get("token");
		rfidcode = (String) paramJson.get("rfidcode");
		lo = paramJson.getString("lo");
		la = paramJson.getString("la");
		if (StringUtils.isEmpty(rfidcode)) {
			rstr = "{\"code\":\"201\",\"message\":\"rfid码为空!\"}";
		} else {
			String tokenStr = (String) redisCacheManager.get(token);
			TCsPdaUser pda = JSON.parseObject(tokenStr, TCsPdaUser.class);
			log.setUserid(pda.getPdauserid());
			log.setUsername(pda.getPdausername());
			logger.info(pda);
			if (pda.getPdatype() == 0 || pda.getPdatype() == 2) {
				// 判断rfid码是否合法

				// 收货
				String result = orderService.pdaRGoods(rfidcode, lo, la);
				String strtmp = "";
				if (result.equals("100")) {
					rstr = "{\"code\":\"201\",\"message\":\"该封签rfid码对应多条业务数据!\"}";
				} else if (result.contains("-2")) {
					strtmp = result.substring(2);
					rstr = "{\"code\":\"201\",\"message\":\""+strtmp+"封签已被扫描收货!\"}";
				} else if (result.equals("0")) {
					rstr = "{\"code\":\"201\",\"message\":\"未查找到该封签数据!\"}";
				} else if (result.equals("-1")) {
					rstr = "{\"code\":\"201\",\"message\":\"收货失败!\"}";
				} else {
					rstr = "{\"code\":\"200\",\"message\":\"" + result + "\"}";
				}
			} else {
				rstr = "{\"code\":\"201\",\"message\":\"用户没有收货权限!\"}";
			}
		}
		// 插入日志
		logdesc.append(rstr);
		log.setLogdesc(logdesc.toString());
		insertLog(log);

		return rstr;
	}

	/**
	 * 称软件向平台推送数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/setcarweighdata", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	private String setCarWeighData(HttpServletRequest request, HttpServletResponse response) {
		StringBuffer logdesc = new StringBuffer();
		TCommLog log = new TCommLog();
		String postcontent = "";// 传递的内容
		String rstr = "";// 返回结果
		String accesskeyid = "";
		boolean repeat = false;
		String lastcarnum = "";
		String lastdept = "";
		String lasttime = "";
		String lastmark = "";
		try {
			postcontent = request.getParameter("param");
			logdesc.append(postcontent);
			logger.info(postcontent);
			log.setUseripaddress(request.getRemoteAddr());
			log.setLogtype(1);// 称软件操作
			JSONObject json = (JSONObject) JSON.parse(postcontent);

			accesskeyid = (String) json.get("accesskeyid");
			logdesc.append("accesskeyid=").append(accesskeyid);

			TCsOrder order = new TCsOrder();
			order.setSnum((String) json.get("snum"));
			String carnum = (String) json.get("carnum");
			if(StringUtils.isEmpty(carnum)) {
				carnum = "空车牌";
			}else {
				carnum = carnum.toUpperCase();
			}
			String sgoodsdept = StringUtils.nullOrBlankResult((String) json.get("sgoodsdept"));
			String goodstype = StringUtils.nullOrBlankResult((String) json.get("goodstype"));
			order.setCarnum(carnum);
			order.setGoodstype((String) json.get("goodstype"));
			order.setGoodsweight(new BigDecimal(json.get("goodsweight").toString()));
			order.setSgoodsdept((String) json.get("sgoodsdept"));
			order.setConsignor((String) json.get("consignor"));
			order.setSgoodstime((String) json.get("sgoodstime"));
			order.setRgoodsdept((String) json.get("rgoodsdept"));
			order.setConsignee((String) json.get(""));
			// 补发数据，防止此时有正常车辆通过误认为是最后记录。
			if ((Integer) json.get("datatype") == 1) {
				//order.setRfid("123456");
				// order.setRgoodsstatus(1);//收货
				// order.setRgoodsostatus(1);//遗漏收货
				order.setStatus(1);// 遗漏状态
			}
			order.setRfid("");
			
			//判断是否需要封签
			order.setOtheri1(0);
			/*
			if(sgoodsdept.equals("沈水湾")&&goodstype.equals("污泥")) {
				order.setOtheri1(1);
			}
			if(sgoodsdept.equals("仙女河")&&goodstype.equals("污泥")) {
				order.setOtheri1(1);
			}
			if(sgoodsdept.equals("振兴污泥")&&goodstype.equals("尾产物")) {
				order.setOtheri1(1);
			}
			if(sgoodsdept.equals("振兴污泥2号")&&goodstype.equals("尾产物")) {
				order.setOtheri1(1);
			}
			if(sgoodsdept.equals("振兴污泥场外")&&goodstype.equals("尾产物")) {
				order.setOtheri1(1);
			}
			if(sgoodsdept.equals("振兴污泥场外2")&&goodstype.equals("尾产物")) {
				order.setOtheri1(1);
			}	
			*/		
			// logdesc.append(" 内容:").append(order);
			// 判断accesskeyid是否登记过
			List<String> mList = Cache.macAddrList;
			if (mList.contains(accesskeyid) && !StringUtils.isEmpty(accesskeyid)) {
				// for(int i=0;i<10000;i++) {
				// order.setSnum("流水号:"+i);
				// order.setRfidstatus(1);
				
				for(TCsOrder tcsOrder : Cache.lastapi_list) {
					lastcarnum = tcsOrder.getCarnum();
					lastdept = tcsOrder.getSgoodsdept();
					lasttime = tcsOrder.getSgoodstime();
					lastmark = tcsOrder.getRemark();
					if(lastdept.equals(sgoodsdept)) {
						//判断是否重复数据
						
						if(lastcarnum.equals(order.getCarnum()) && lasttime.equals(order.getSgoodstime())) {
							repeat = true;
						}
						
						//替换最后一条记录
						tcsOrder.setCarnum(order.getCarnum());
						tcsOrder.setSgoodstime(order.getSgoodstime());
						
						//判断是否封签数据
						if(lastmark.indexOf(goodstype)>=0) {
							System.out.println("goodstype:"+goodstype + ";lastmark="+lastmark);
							order.setOtheri1(1);
						}	
					}
				}
				if(repeat) {
					//重复数据
					
				}else {
					orderService.insertOrder(order);
				}
				// }
				// 更新发货单位，收货单位
				rstr = "{\"code\":\"200\",\"message\":\"成功\"}";
				logdesc.append("成功!");
			} else {
				rstr = "{\"code\":\"201\",\"message\":\"失败-非法accesskeyid\"}";
				logdesc.append("失败-非法accesskeyid");
			}

		} catch (Exception e) {
			logger.info(e.getMessage());
			logdesc.append("失败-插入数据出错!");
			rstr = "{\"code\":\"201\",\"message\":\"失败-插入数据出错!\"}";
		}
		log.setLogdesc(logdesc.toString());
		// 插入日志
		insertLog(log);
		return rstr;
	}

	public String getPostContent(HttpServletRequest request) {
		InputStream in = null;
		BufferedInputStream buf = null;
		StringBuffer info = new StringBuffer();
		try {
			in = request.getInputStream();
			buf = new BufferedInputStream(in);
			byte[] buffer = new byte[1024];
			int iRead;
			while ((iRead = buf.read(buffer)) != -1) {
				info.append(new String(buffer, 0, iRead, "UTF-8"));
				logger.info(info.toString());
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (buf != null) {
				try {
					buf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return info.toString();
	}

	public void insertLog(TCommLog log) {
		if (!Constant.WRITE_LOG.equals("true")) {
			return;
		}
		// 插入日志
		try {
			logService.insertLog(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		BigDecimal bd = new BigDecimal("234".toString());
		System.out.println(bd);
	}
}
