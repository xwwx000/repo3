package com.xwwx.sewage.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.xwwx.sewage.bean.PickList;
import com.xwwx.sewage.bean.TCommUser;
import com.xwwx.sewage.bean.TCsGoodsDept;
import com.xwwx.sewage.bean.TCsGoodsType;
import com.xwwx.sewage.bean.TCsOrder;
import com.xwwx.sewage.cache.Cache;
import com.xwwx.sewage.common.CommTime;
import com.xwwx.sewage.common.Constant;
import com.xwwx.sewage.common.PageList;
import com.xwwx.sewage.common.Pages;
import com.xwwx.sewage.service.OrderService;
import com.xwwx.sewage.token.Token;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private Cache cache;
	private static Logger logger = Logger.getLogger(OrderController.class);

	/**
	 * 记录查询
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/orderList")
	public String getOrderList(HttpServletRequest request, ModelMap map) {
		String pageno = WebUtils.findParameterValue(request, "pageno");
		String sgoodsdept = WebUtils.findParameterValue(request, "sgoodsdept");
		String rgoodsdept = WebUtils.findParameterValue(request, "rgoodsdept");
		String goodstype = WebUtils.findParameterValue(request, "goodstype");
		String rgoodsstatus = WebUtils.findParameterValue(request, "rgoodsstatus");
		String carnum = WebUtils.findParameterValue(request, "carnum");
		String stime = WebUtils.findParameterValue(request, "stime");
		String etime = WebUtils.findParameterValue(request, "etime");
		if (StringUtils.isEmpty(stime)) {
			stime = CommTime.getCurrDate();
		}
		if (StringUtils.isEmpty(etime)) {
			etime = CommTime.getCurrDate();
		}
		TCommUser user = (TCommUser) request.getSession().getAttribute(Constant.USER_SESSION_KEY);
		Pages pages = new Pages();
		pages.setPerPageNum(user.getPagenum());
		if (null == pageno || pageno.equals("0")) {
			pageno = "1";
		}
		pages.setPage(Integer.parseInt(pageno));
		Map paramMap = new HashMap();
		if (!StringUtils.isEmpty(sgoodsdept)) {
			paramMap.put("sgoodsdept", sgoodsdept);
		}
		if (!StringUtils.isEmpty(rgoodsdept)) {
			paramMap.put("rgoodsdept", rgoodsdept);
		}
		if (!StringUtils.isEmpty(goodstype)) {
			paramMap.put("goodstype", goodstype);
		}
		if (!StringUtils.isEmpty(rgoodsstatus)) {
			paramMap.put("rgoodsstatus", rgoodsstatus);
		} else {
			rgoodsstatus = "-1";
		}
		if (!StringUtils.isEmpty(carnum)) {
			paramMap.put("carnum", carnum);
		}
		if (!StringUtils.isEmpty(stime) && !StringUtils.isEmpty(etime)) {
			paramMap.put("stime", stime);
			paramMap.put("etime", etime);
		}
		paramMap.put("status", 0);
		
		List<TCsGoodsDept> sgoodsdeptlist = new ArrayList<TCsGoodsDept>();
		if(user.getGroupid() == 1 || user.getGroupid() == 3) {
			//管理员 或 领导查询
			//发货单位
			sgoodsdeptlist = orderService.getAllSGoodsDeptList();
		}else{
			//普通用户
			paramMap.put("sgoodsdept", user.getDept().getDeptname());
			for(TCsGoodsDept dept : orderService.getAllSGoodsDeptList()) {
				if(dept.getDeptname().equals(user.getDept().getDeptname())) {
					sgoodsdeptlist.add(dept);
					break;
				}
			}
			sgoodsdept = user.getDept().getDeptname();
		}
		map.put("sgoodsdeptlist", sgoodsdeptlist);
		PageList pageList = orderService.getOrderList("sgoodstime", Constant.LIST_ASC, pages, paramMap);
		String pagination = pages.getUrlstr();
		map.put("pageList", pageList);
		map.put("pagination", pagination);

		// 货物类型
		List<TCsGoodsType> goodstypelist = orderService.getAllSGoodsTypeList();
		map.put("goodstypelist", goodstypelist);
		map.put("goodstype", goodstype);
		map.put("sgoodsdept", sgoodsdept);
		map.put("rgoodsdept", rgoodsdept);
		map.put("rgoodsstatus", rgoodsstatus);
		map.put("stime", stime);
		map.put("etime", etime);
		// 收货状态
		List<PickList> shzt_list = Cache.shzt_list;
		map.put("shztlist", shzt_list);
		map.put("carnum", carnum);
		// 总重量
		Double weight = orderService.getOrderListWeight(paramMap);
		if (weight == null) {
			map.put("allweight", "0");
		} else {
			map.put("allweight", String.valueOf(orderService.getOrderListWeight(paramMap)));
		}
		return "order/orderlist";
	}
	/**
	 * 记录查询
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/orderUpdateList")
	public String getOrderUpdateList(HttpServletRequest request, ModelMap map) {
		String pageno = WebUtils.findParameterValue(request, "pageno");
		String sgoodsdept = WebUtils.findParameterValue(request, "sgoodsdept");
		String goodstype = WebUtils.findParameterValue(request, "goodstype");
		String rgoodsstatus = WebUtils.findParameterValue(request, "rgoodsstatus");
		String carnum = WebUtils.findParameterValue(request, "carnum");
		String stime = WebUtils.findParameterValue(request, "stime");
		String etime = WebUtils.findParameterValue(request, "etime");
		if (StringUtils.isEmpty(stime)) {
			stime = CommTime.getCurrDate();
		}
		if (StringUtils.isEmpty(etime)) {
			etime = CommTime.getCurrDate();
		}
		TCommUser user = (TCommUser) request.getSession().getAttribute(Constant.USER_SESSION_KEY);
		Pages pages = new Pages();
		pages.setPerPageNum(user.getPagenum());
		if (null == pageno || pageno.equals("0")) {
			pageno = "1";
		}
		pages.setPage(Integer.parseInt(pageno));
		Map paramMap = new HashMap();
		if (!StringUtils.isEmpty(sgoodsdept)) {
			paramMap.put("sgoodsdept", sgoodsdept);
		}
		if (!StringUtils.isEmpty(goodstype)) {
			paramMap.put("goodstype", goodstype);
		}
		if (!StringUtils.isEmpty(rgoodsstatus)) {
			paramMap.put("rgoodsstatus", rgoodsstatus);
		} else {
			rgoodsstatus = "-1";
		}
		if (!StringUtils.isEmpty(carnum)) {
			paramMap.put("carnum", carnum);
		}
		if (!StringUtils.isEmpty(stime) && !StringUtils.isEmpty(etime)) {
			paramMap.put("stime", stime);
			paramMap.put("etime", etime);
		}
		//paramMap.put("status", 0);
		
		List<TCsGoodsDept> sgoodsdeptlist = new ArrayList<TCsGoodsDept>();
		if(user.getGroupid() == 1 || user.getGroupid() == 3) {
			//管理员 或 领导查询
			//发货单位
			sgoodsdeptlist = orderService.getAllSGoodsDeptList();
		}else{
			//普通用户
			paramMap.put("sgoodsdept", user.getDept().getDeptname());
			for(TCsGoodsDept dept : orderService.getAllSGoodsDeptList()) {
				if(dept.getDeptname().equals(user.getDept().getDeptname())) {
					sgoodsdeptlist.add(dept);
					break;
				}
			}
			sgoodsdept = user.getDept().getDeptname();
		}
		map.put("sgoodsdeptlist", sgoodsdeptlist);
		PageList pageList = orderService.getOrderList("sgoodstime", Constant.LIST_ASC, pages, paramMap);
		String pagination = pages.getUrlstr();
		map.put("pageList", pageList);
		map.put("pagination", pagination);

		// 货物类型
		List<TCsGoodsType> goodstypelist = orderService.getAllSGoodsTypeList();
		map.put("goodstypelist", goodstypelist);
		map.put("goodstype", goodstype);
		map.put("sgoodsdept", sgoodsdept);
		map.put("rgoodsstatus", rgoodsstatus);
		map.put("stime", stime);
		map.put("etime", etime);
		// 收货状态
		List<PickList> shzt_list = Cache.shzt_list;
		map.put("shztlist", shzt_list);
		map.put("carnum", carnum);
		// 总重量
		Double weight = orderService.getOrderListWeight(paramMap);
		if (weight == null) {
			map.put("allweight", "0");
		} else {
			map.put("allweight", String.valueOf(orderService.getOrderListWeight(paramMap)));
		}
		return "order/orderupdatelist";
	}
	/**
	 * 修改订单数据页
	 */
	@RequestMapping(value = "/orderUpdateDetailPage")
	public String orderUpdateDetailPage(HttpServletRequest request, ModelMap map) {
		String id = WebUtils.findParameterValue(request, "id");
		// 货物类型
		List<TCsGoodsType> goodstypelist = orderService.getAllSGoodsTypeList();
		map.put("goodstypelist", goodstypelist);

		// 发货单位
		//List<TCsGoodsDept> sgoodsdeptlist = orderService.getAllSGoodsDeptList();
		//map.put("sgoodsdeptlist", sgoodsdeptlist);
		String sgoodsdept = "";
		List<TCsGoodsDept> sgoodsdeptlist = new ArrayList<TCsGoodsDept>();
		TCommUser user = (TCommUser) request.getSession().getAttribute(Constant.USER_SESSION_KEY);
		if(user.getGroupid() == 1 || user.getGroupid() == 3) {
			//管理员 或 领导查询
			//发货单位
			sgoodsdeptlist = orderService.getAllSGoodsDeptList();
		}else{
			//普通用户
			for(TCsGoodsDept dept : orderService.getAllSGoodsDeptList()) {
				if(dept.getDeptname().equals(user.getDept().getDeptname())) {
					sgoodsdeptlist.add(dept);
					break;
				}
			}
			sgoodsdept = user.getDept().getDeptname();
		}
		map.put("sgoodsdeptlist", sgoodsdeptlist);

		// 收货单位
		List<TCsGoodsDept> rgoodsdeptlist = orderService.getAllRGoodsDeptList();
		map.put("rgoodsdeptlist", rgoodsdeptlist);
		map.put("sgoodsdept", sgoodsdept);
		TCsOrder order = orderService.getOrderById(Integer.valueOf(id));
		
		map.put("order", order);
		return "order/orderupdatedetail";
	}
	/**
	 * 修改人工数据
	 */
	@RequestMapping(value = "/orderUpdateDetail", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String orderUpdateDetail(HttpServletRequest request, ModelMap map) {
		String rstr = " {\"code\":\"201\",\"message\":\"失败!\"}";
		String id = WebUtils.findParameterValue(request, "id");
		String carnum = WebUtils.findParameterValue(request, "carnum");
		String goodstype = WebUtils.findParameterValue(request, "goodstype");
		String sgoodsdept = WebUtils.findParameterValue(request, "sgoodsdept");
		String rgoodsdept = WebUtils.findParameterValue(request, "rgoodsdept");
		String goodsweight = WebUtils.findParameterValue(request, "goodsweight");
		TCsOrder order = orderService.getOrderById(Integer.parseInt(id));
		order.setCarnum(carnum);
		order.setGoodstype(goodstype);
		order.setSgoodsdept(sgoodsdept);
		order.setRgoodsdept(rgoodsdept);
		order.setGoodsweight(new BigDecimal(goodsweight));
		
		//判断是否封签数据
		List<TCsGoodsDept> goodsdeptlist = orderService.getAllSGoodsDeptList();
		for(TCsGoodsDept dept:goodsdeptlist) {
			if(dept.getDeptname().equals(sgoodsdept) && dept.getDeptmark().indexOf(goodstype)>=0) {
				order.setOtheri1(1);
			}
		}
		try {
			orderService.updateOrder(order);
			rstr = "{\"code\":\"200\",\"message\":\"成功!\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rstr;
	}
	@RequestMapping("/orderOmitList")
	public String getOrderOmitList(HttpServletRequest request, ModelMap map) {
		String pageno = WebUtils.findParameterValue(request, "pageno");
		String sgoodsdept = WebUtils.findParameterValue(request, "sgoodsdept");
		String rgoodsdept = WebUtils.findParameterValue(request, "rgoodsdept");
		String goodstype = WebUtils.findParameterValue(request, "goodstype");
		String carnum = WebUtils.findParameterValue(request, "carnum");

		String stime = WebUtils.findParameterValue(request, "stime");
		String etime = WebUtils.findParameterValue(request, "etime");
		if (StringUtils.isEmpty(stime)) {
			stime = CommTime.getCurrDate();
		}
		if (StringUtils.isEmpty(etime)) {
			etime = CommTime.getCurrDate();
		}
		TCommUser user = (TCommUser) request.getSession().getAttribute(Constant.USER_SESSION_KEY);
		Pages pages = new Pages();
		pages.setPerPageNum(user.getPagenum());
		if (null == pageno || pageno.equals("0")) {
			pageno = "1";
		}
		pages.setPage(Integer.parseInt(pageno));
		Map paramMap = new HashMap();
		if (!StringUtils.isEmpty(sgoodsdept)) {
			paramMap.put("sgoodsdept", sgoodsdept);
		}
		if (!StringUtils.isEmpty(rgoodsdept)) {
			paramMap.put("rgoodsdept", rgoodsdept);
		}
		if (!StringUtils.isEmpty(goodstype)) {
			paramMap.put("goodstype", goodstype);
		}
		if (!StringUtils.isEmpty(carnum)) {
			paramMap.put("carnum", carnum);
		}
		if (!StringUtils.isEmpty(stime) && !StringUtils.isEmpty(etime)) {
			paramMap.put("stime", stime);
			paramMap.put("etime", etime);
		}
		paramMap.put("status", 1);
		
		List<TCsGoodsDept> sgoodsdeptlist = new ArrayList<TCsGoodsDept>();
		if(user.getGroupid() == 1 || user.getGroupid() == 3) {
			//管理员
			//发货单位
			sgoodsdeptlist = orderService.getAllSGoodsDeptList();
		}else{
			//普通用户
			paramMap.put("sgoodsdept", user.getDept().getDeptname());
			for(TCsGoodsDept dept : orderService.getAllSGoodsDeptList()) {
				if(dept.getDeptname().equals(user.getDept().getDeptname())) {
					sgoodsdeptlist.add(dept);
					break;
				}
			}
			sgoodsdept = user.getDept().getDeptname();
		}
		map.put("sgoodsdeptlist", sgoodsdeptlist);
		PageList pageList = orderService.getOrderOmitList("sgoodstime", Constant.LIST_ASC, pages, paramMap);
		String pagination = pages.getUrlstr();
		map.put("pageList", pageList);
		map.put("pagination", pagination);

		// 货物类型
		List<TCsGoodsType> goodstypelist = orderService.getAllSGoodsTypeList();
		map.put("goodstypelist", goodstypelist);
		map.put("goodstype", goodstype);
		map.put("sgoodsdept", sgoodsdept);
		map.put("rgoodsdept", rgoodsdept);
		map.put("carnum", carnum);
		map.put("stime", stime);
		map.put("etime", etime);

		// 总重量
		Double weight = orderService.getOrderListWeight(paramMap);
		if (weight == null) {
			map.put("allweight", "0");
		} else {
			map.put("allweight", String.valueOf(orderService.getOrderListWeight(paramMap)));
		}
		return "order/orderomitlist";
	}

	/**
	 * 遗漏设置
	 * 
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/orderOmitSetList")
	public String orderOmitSetList(HttpServletRequest request, ModelMap map) {
		String pageno = WebUtils.findParameterValue(request, "pageno");
		String sgoodsdept = WebUtils.findParameterValue(request, "sgoodsdept");
		String etime = WebUtils.findParameterValue(request, "etime");
		String goodstype = WebUtils.findParameterValue(request, "goodstype");
		String rgoodsstatus = WebUtils.findParameterValue(request, "rgoodsstatus");
		String carnum = WebUtils.findParameterValue(request, "carnum");
		String stime = WebUtils.findParameterValue(request, "stime");
		String rgoodsdept = WebUtils.findParameterValue(request, "rgoodsdept");
		if (StringUtils.isEmpty(stime)) {
			stime = CommTime.getCurrDate();
		}
		if (StringUtils.isEmpty(etime)) {
			etime = CommTime.getCurrDate();
		}
		TCommUser user = (TCommUser) request.getSession().getAttribute(Constant.USER_SESSION_KEY);
		Pages pages = new Pages();
		pages.setPerPageNum(user.getPagenum());
		if (null == pageno || pageno.equals("0")) {
			pageno = "1";
		}
		pages.setPage(Integer.parseInt(pageno));
		Map paramMap = new HashMap();
		if (!StringUtils.isEmpty(sgoodsdept)) {
			paramMap.put("sgoodsdept", sgoodsdept);
		}
		if (!StringUtils.isEmpty(rgoodsdept)) {
			paramMap.put("rgoodsdept", rgoodsdept);
		}
		paramMap.put("rgoodsstatus", rgoodsstatus);
		if (!StringUtils.isEmpty(carnum)) {
			paramMap.put("carnum", carnum);
		}
		if (!StringUtils.isEmpty(stime) && !StringUtils.isEmpty(etime)) {
			paramMap.put("stime", stime);
			paramMap.put("etime", etime);
		}
		paramMap.put("rgoodsstatus", 0);// 未收货
		paramMap.put("status", 0);// 正常数据
		paramMap.put("otheri1", 1);//可以封签数据
		
		List<TCsGoodsDept> sgoodsdeptlist = new ArrayList<TCsGoodsDept>();
		if(user.getGroupid() == 1 || user.getGroupid() == 3) {
			//管理员 或 领导查询
			//发货单位
			sgoodsdeptlist = orderService.getAllSGoodsDeptList();
		}else{
			//普通用户
			paramMap.put("sgoodsdept", user.getDept().getDeptname());
			for(TCsGoodsDept dept : orderService.getAllSGoodsDeptList()) {
				if(dept.getDeptname().equals(user.getDept().getDeptname())) {
					sgoodsdeptlist.add(dept);
					break;
				}
			}
			sgoodsdept = user.getDept().getDeptname();
		}
		map.put("sgoodsdeptlist", sgoodsdeptlist);
		
		PageList pageList = orderService.getOrderList("sgoodstime", Constant.LIST_ASC, pages, paramMap);
		String pagination = pages.getUrlstr();
		map.put("pageList", pageList);
		map.put("pagination", pagination);

		map.put("sgoodsdept", sgoodsdept);
		map.put("rgoodsdept", rgoodsdept);
		map.put("stime", stime);
		map.put("etime", etime);
		map.put("carnum", carnum);
		return "order/orderomitsetlist";
	}

	@RequestMapping("/remark")
	private String remark(HttpServletRequest request, ModelMap map) {
		String id = WebUtils.findParameterValue(request, "id");
		TCsOrder order = orderService.getOrderById(Integer.valueOf(id));
		map.put("order", order);
		return "order/remark";
	}

	@RequestMapping(value = "/saveremark", produces = "application/json; charset=utf-8")
	@ResponseBody
	private String saveRemark(HttpServletRequest request) {
		String id = WebUtils.findParameterValue(request, "id");
		String remark = WebUtils.findParameterValue(request, "remark");
		String rstr = "{\"code\":\"201\",\"message\":\"失败!\"}";
		try {
			orderService.updateRemark(Integer.parseInt(id), remark);
			rstr = "{\"code\":\"200\",\"message\":\"成功!\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rstr;
	}

	/**
	 * 设置遗漏标志
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/omitSet", produces = "application/json; charset=utf-8")
	@ResponseBody
	private String omitSet(HttpServletRequest request) {
		String id = WebUtils.findParameterValue(request, "id");
		String rstr = "{\"code\":\"201\",\"message\":\"失败!\"}";
		try {
			orderService.updateOmitStatus(Integer.parseInt(id));
			rstr = "{\"code\":\"200\",\"message\":\"成功!\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rstr;
	}
	/**
	 * 删除记录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteById", produces = "application/json; charset=utf-8")
	@ResponseBody
	private String deleteById(HttpServletRequest request) {
		String id = WebUtils.findParameterValue(request, "id");
		String rstr = "{\"code\":\"201\",\"message\":\"失败!\"}";
		try {
			TCsOrder order = new TCsOrder();
			order.setId(Integer.parseInt(id));
			orderService.deleteOrder(order);
			rstr = "{\"code\":\"200\",\"message\":\"成功!\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rstr;
	}
	/**
	 * 收货点位置
	 */
	@RequestMapping("/map")
	private String map(HttpServletRequest request, ModelMap map) {
		String lo = WebUtils.findParameterValue(request, "lo");
		String la = WebUtils.findParameterValue(request, "la");
		String rgoodsdept = WebUtils.findParameterValue(request, "rgoodsdept");

		map.put("lo", lo);
		map.put("la", la);
		map.put("rgoodsdept", rgoodsdept);
		return "order/ordermap";
	}

	/**
	 * 手工录入数据
	 */
	@RequestMapping("/orderManualList")
	private String orderManualList(HttpServletRequest request, ModelMap map) {
		String pageno = WebUtils.findParameterValue(request, "pageno");
		String sgoodsdept = WebUtils.findParameterValue(request, "sgoodsdept");
		String goodstype = WebUtils.findParameterValue(request, "goodstype");
		String rgoodsstatus = WebUtils.findParameterValue(request, "rgoodsstatus");
		String carnum = WebUtils.findParameterValue(request, "carnum");
		String stime = WebUtils.findParameterValue(request, "stime");
		String etime = WebUtils.findParameterValue(request, "etime");
		if (StringUtils.isEmpty(stime)) {
			stime = CommTime.getCurrDate();
		}
		if (StringUtils.isEmpty(etime)) {
			etime = CommTime.getCurrDate();
		}
		TCommUser user = (TCommUser) request.getSession().getAttribute(Constant.USER_SESSION_KEY);
		Pages pages = new Pages();
		pages.setPerPageNum(user.getPagenum());
		if (null == pageno || pageno.equals("0")) {
			pageno = "1";
		}
		pages.setPage(Integer.parseInt(pageno));
		Map paramMap = new HashMap();
		if (!StringUtils.isEmpty(sgoodsdept)) {
			paramMap.put("sgoodsdept", sgoodsdept);
		}
		if (!StringUtils.isEmpty(goodstype)) {
			paramMap.put("goodstype", goodstype);
		}
		if (!StringUtils.isEmpty(rgoodsstatus)) {
			paramMap.put("rgoodsstatus", rgoodsstatus);
		} else {
			rgoodsstatus = "-1";
		}
		if (!StringUtils.isEmpty(carnum)) {
			paramMap.put("carnum", carnum);
		}
		if (!StringUtils.isEmpty(stime) && !StringUtils.isEmpty(etime)) {
			paramMap.put("stime", stime);
			paramMap.put("etime", etime);
		}
		paramMap.put("status", 1);
		paramMap.put("manualflag", 1);
		List<TCsGoodsDept> sgoodsdeptlist = new ArrayList<TCsGoodsDept>();
		if(user.getGroupid() == 1 || user.getGroupid() == 3) {
			//管理员 或 领导查询
			//发货单位
			sgoodsdeptlist = orderService.getAllSGoodsDeptList();
		}else{
			//普通用户
			paramMap.put("sgoodsdept", user.getDept().getDeptname());
			for(TCsGoodsDept dept : orderService.getAllSGoodsDeptList()) {
				if(dept.getDeptname().equals(user.getDept().getDeptname())) {
					sgoodsdeptlist.add(dept);
					break;
				}
			}
			sgoodsdept = user.getDept().getDeptname();
		}
		map.put("sgoodsdeptlist", sgoodsdeptlist);
		PageList pageList = orderService.getOrderManualList("sgoodstime", Constant.LIST_ASC, pages, paramMap);
		String pagination = pages.getUrlstr();
		map.put("pageList", pageList);
		map.put("pagination", pagination);

		// 货物类型
		List<TCsGoodsType> goodstypelist = orderService.getAllSGoodsTypeList();
		map.put("goodstypelist", goodstypelist);
		map.put("goodstype", goodstype);

		map.put("sgoodsdept", sgoodsdept);

		map.put("stime", stime);
		map.put("etime", etime);

		map.put("carnum", carnum);

		return "order/ordermanuallist";
	}

	/**
	 * 手工录入数据 add
	 */
	@RequestMapping("/ordermanualaddpage")
	@Token(save=true)
	private String orderManualAddPage(HttpServletRequest request, ModelMap map) {

		// 货物类型
		List<TCsGoodsType> goodstypelist = orderService.getAllSGoodsTypeList();
		map.put("goodstypelist", goodstypelist);
		TCommUser user = (TCommUser) request.getSession().getAttribute(Constant.USER_SESSION_KEY);
		// 发货单位
		List<TCsGoodsDept> sgoodsdeptlist = new ArrayList<TCsGoodsDept>();
		if(user.getGroupid() == 1 || user.getGroupid() == 3) {
			//管理员 或 领导查询
			//发货单位
			sgoodsdeptlist = orderService.getAllSGoodsDeptList();
		}else{
			//普通用户
			for(TCsGoodsDept dept : orderService.getAllSGoodsDeptList()) {
				if(dept.getDeptname().equals(user.getDept().getDeptname())) {
					sgoodsdeptlist.add(dept);
					break;
				}
			}
		}
		map.put("sgoodsdeptlist", sgoodsdeptlist);

		// 收货单位
		List<TCsGoodsDept> rgoodsdeptlist = orderService.getAllRGoodsDeptList();
		map.put("rgoodsdeptlist", rgoodsdeptlist);
		return "order/ordermanualadd";
	}

	/**
	 * 手工录入
	 */
	@RequestMapping(value = "/ordermanualadd", produces = "application/json; charset=utf-8")
	@ResponseBody
	@Token(remove=true)
	private String orderManualAdd(HttpServletRequest request, ModelMap map) {
		String rstr = " {\"code\":\"201\",\"message\":\"失败!\"}";
		String carnum = WebUtils.findParameterValue(request, "carnum");
		String goodstype = WebUtils.findParameterValue(request, "goodstype");
		String sgoodsdept = WebUtils.findParameterValue(request, "sgoodsdept");
		String consignor = WebUtils.findParameterValue(request, "consignor");
		String stime = WebUtils.findParameterValue(request, "stime");
		String rgoodsdept = WebUtils.findParameterValue(request, "rgoodsdept");
		String consignee = WebUtils.findParameterValue(request, "consignee");
		String etime = WebUtils.findParameterValue(request, "etime");
		String goodsweight = WebUtils.findParameterValue(request, "goodsweight");
		String remark = WebUtils.findParameterValue(request, "remark");
		
		String lastdept = "";
		String lastmark = "";
		
		TCsOrder order = new TCsOrder();
		order.setSnum(CommTime.getNewSSSTime());
		order.setCarnum(carnum);
		order.setGoodstype(goodstype);
		order.setSgoodsdept(sgoodsdept);
		order.setConsignor(consignor);
		if(StringUtils.isEmpty(stime)) {
			return rstr;
		}
		order.setSgoodstime(stime);
		order.setRgoodsdept(rgoodsdept);
		order.setConsignee(consignee);
		if(!StringUtils.isEmpty(etime)) {
			order.setRgoodstime(etime);
		}else {
			order.setRgoodstime(null);
		}
		order.setGoodsweight(new BigDecimal(goodsweight));
		order.setRemark(remark);
		order.setRfid("");
		order.setRfidstatus(1);
		order.setStatus(1);
		
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
		for(TCsOrder tcsOrder : Cache.lastapi_list) {
			lastmark = tcsOrder.getRemark();
			lastdept = tcsOrder.getSgoodsdept();
			if(lastdept.equals(sgoodsdept)) {
				//判断是否封签数据
				if(lastmark.indexOf(goodstype)>=0) {
					order.setOtheri1(1);
				}	
			}
		}
		//order.setRgoodsstatus(1);
		//order.setRgoodsostatus(1);
		order.setManualflag(1);
		try {
			orderService.insertManualOrder(order);
			rstr = "{\"code\":\"200\",\"message\":\"成功!\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rstr;
	}

	/**
	 * 删除人工数据
	 */
	@RequestMapping(value = "/deleteManualOrder", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String deleteManualOrder(HttpServletRequest request) {
		String rstr = "{\"code\":\"201\",\"message\":\"失败!\"}";
		String id = WebUtils.findParameterValue(request, "id");
		try {
			orderService.deleteOrderManual(Integer.parseInt(id));
			rstr = "{\"code\":\"200\",\"message\":\"成功!\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rstr;
	}

	/**
	 * 修改人工数据页
	 */
	@RequestMapping(value = "/editManualOrderPage")
	public String updateManualOrderPage(HttpServletRequest request, ModelMap map) {
		String id = WebUtils.findParameterValue(request, "id");
		// 货物类型
		List<TCsGoodsType> goodstypelist = orderService.getAllSGoodsTypeList();
		map.put("goodstypelist", goodstypelist);

		// 发货单位
		List<TCsGoodsDept> sgoodsdeptlist = orderService.getAllSGoodsDeptList();
		map.put("sgoodsdeptlist", sgoodsdeptlist);

		// 收货单位
		List<TCsGoodsDept> rgoodsdeptlist = orderService.getAllRGoodsDeptList();
		map.put("rgoodsdeptlist", rgoodsdeptlist);
		
		TCsOrder order = orderService.getOrderById(Integer.valueOf(id));
		
		map.put("order", order);
		return "order/ordermanualedit";
	}
	/**
	 * 修改人工数据
	 */
	@RequestMapping(value = "/editManualOrder", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String editManualOrder(HttpServletRequest request, ModelMap map) {
		String rstr = " {\"code\":\"201\",\"message\":\"失败!\"}";
		String id = WebUtils.findParameterValue(request, "id");
		String carnum = WebUtils.findParameterValue(request, "carnum");
		String goodstype = WebUtils.findParameterValue(request, "goodstype");
		String sgoodsdept = WebUtils.findParameterValue(request, "sgoodsdept");
		String consignor = WebUtils.findParameterValue(request, "consignor");
		String stime = WebUtils.findParameterValue(request, "stime");
		String rgoodsdept = WebUtils.findParameterValue(request, "rgoodsdept");
		String consignee = WebUtils.findParameterValue(request, "consignee");
		String etime = WebUtils.findParameterValue(request, "etime");
		String goodsweight = WebUtils.findParameterValue(request, "goodsweight");
		String remark = WebUtils.findParameterValue(request, "remark");
		TCsOrder order = orderService.getOrderById(Integer.parseInt(id));
		order.setCarnum(carnum);
		order.setGoodstype(goodstype);
		order.setSgoodsdept(sgoodsdept);
		order.setConsignor(consignor);
		if(!StringUtils.isEmpty(stime)) {
			order.setSgoodstime(stime);
		}else {
			order.setSgoodstime(null);
		}
		order.setRgoodsdept(rgoodsdept);
		order.setConsignee(consignee);
		if(!StringUtils.isEmpty(etime)) {
			order.setRgoodstime(etime);
		}else {
			order.setRgoodstime(null);
		}
		order.setGoodsweight(new BigDecimal(goodsweight));
		order.setRemark(remark);
		try {
			orderService.updateOrderManual(order);
			rstr = "{\"code\":\"200\",\"message\":\"成功!\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rstr;
	}
	/**
	 * 	发货单位维护
	 */
	@RequestMapping(value = "/sgoodsdeptlist")
	public String sGoodsDeptList(HttpServletRequest request, ModelMap map) {
		String pageno = WebUtils.findParameterValue(request, "pageno");
		TCommUser user = (TCommUser) request.getSession().getAttribute(Constant.USER_SESSION_KEY);
		Pages pages = new Pages();
		pages.setPerPageNum(user.getPagenum());
		if (null == pageno || pageno.equals("0")) {
			pageno = "1";
		}
		pages.setPage(Integer.parseInt(pageno));
		Map paramMap = new HashMap();
		PageList pageList = orderService.getSGoodsDeptList("id", Constant.LIST_DESC, pages, paramMap);
		String pagination = pages.getUrlstr();
		map.put("pageList", pageList);
		map.put("pagination", pagination);
		return "order/sgoodsdeptlist";
	}
	/**
	 * 	进入发货单位增加页面
	 */
	@RequestMapping(value="/addsgoodsdeptpage")
	public String addSgoodsDeptPage(HttpServletRequest request, ModelMap map) {
		return "order/addsgoodsdept";
	}
	/**
	 * 	增加发货单位
	 */
	@RequestMapping(value="/addsgoodsdept",produces = "application/json; charset=utf-8")
	@ResponseBody
	private String addSgoodsDept(HttpServletRequest request) {
		String deptname = WebUtils.findParameterValue(request, "deptname");
		String deptmark = WebUtils.findParameterValue(request, "deptmark");
		String status = WebUtils.findParameterValue(request, "status");
		TCsGoodsDept dept = new TCsGoodsDept();
		dept.setDeptname(deptname);
		dept.setStatus(Integer.valueOf(status));
		String rstr = "{\"code\":\"201\",\"message\":\"失败!\"}";
		try {
			orderService.insertSGoodsDept(dept);
			cache.setLaseApiData();
			rstr = "{\"code\":\"200\",\"message\":\"成功!\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rstr;
	}
	/**
	 * 	进入发货单位修改页面
	 */
	@RequestMapping(value="/editsgoodsdeptpage")
	public String editSgoodsDeptPage(HttpServletRequest request, ModelMap map) {
		String id = WebUtils.findParameterValue(request, "id");
		String deptname = WebUtils.findParameterValue(request, "deptname");
		String deptmark = WebUtils.findParameterValue(request, "deptmark");
		String status = WebUtils.findParameterValue(request, "status");
		map.put("id", id);
		map.put("deptname", deptname);
		map.put("deptmark", deptmark);
		map.put("status", status);
		return "order/editsgoodsdept";
	}
	/**
	 * 	修改发货单位
	 */
	@RequestMapping(value="/editsgoodsdept",produces = "application/json; charset=utf-8")
	@ResponseBody
	private String editSgoodsDept(HttpServletRequest request) {
		String id = WebUtils.findParameterValue(request, "id");
		String deptname = WebUtils.findParameterValue(request, "deptname");
		String deptmark = WebUtils.findParameterValue(request, "deptmark");
		String status = WebUtils.findParameterValue(request, "status");
		TCsGoodsDept dept = new TCsGoodsDept();
		dept.setId(Integer.parseInt(id));
		dept.setDeptname(deptname);
		dept.setDeptmark(deptmark);
		dept.setStatus(Integer.valueOf(status));
		String rstr = "{\"code\":\"201\",\"message\":\"失败!\"}";
		try {
			orderService.updateSGoodsDept(dept);
			cache.setLaseApiData();
			rstr = "{\"code\":\"200\",\"message\":\"成功!\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rstr;
	}
	/**
	 * 	删除发货单位
	 */
	@RequestMapping(value="/deletesgoodsdept",produces = "application/json; charset=utf-8")
	@ResponseBody
	private String deleteSgoodsDept(HttpServletRequest request) {
		String id = WebUtils.findParameterValue(request, "id");
		TCsGoodsDept dept = new TCsGoodsDept();
		dept.setId(Integer.parseInt(id));
		String rstr = "{\"code\":\"201\",\"message\":\"失败!\"}";
		try {
			orderService.deleteSGoodsDept(dept);
			cache.setLaseApiData();
			rstr = "{\"code\":\"200\",\"message\":\"成功!\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rstr;
	}
	/**
	 * 	收货单位维护
	 */
	@RequestMapping(value = "/rgoodsdeptlist")
	public String rGoodsDeptList(HttpServletRequest request, ModelMap map) {
		String pageno = WebUtils.findParameterValue(request, "pageno");
		TCommUser user = (TCommUser) request.getSession().getAttribute(Constant.USER_SESSION_KEY);
		Pages pages = new Pages();
		pages.setPerPageNum(user.getPagenum());
		if (null == pageno || pageno.equals("0")) {
			pageno = "1";
		}
		pages.setPage(Integer.parseInt(pageno));
		Map paramMap = new HashMap();
		PageList pageList = orderService.getRGoodsDeptList("id", Constant.LIST_DESC, pages, paramMap);
		String pagination = pages.getUrlstr();
		map.put("pageList", pageList);
		map.put("pagination", pagination);
		return "order/rgoodsdeptlist";
	}
	/**
	 * 	进入收货单位增加页面
	 */
	@RequestMapping(value="/addrgoodsdeptpage")
	public String addRgoodsDeptPage(HttpServletRequest request, ModelMap map) {
		return "order/addrgoodsdept";
	}
	/**
	 * 	增加收货单位
	 */
	@RequestMapping(value="/addrgoodsdept",produces = "application/json; charset=utf-8")
	@ResponseBody
	private String addRgoodsDept(HttpServletRequest request) {
		String deptname = WebUtils.findParameterValue(request, "deptname");
		String status = WebUtils.findParameterValue(request, "status");
		TCsGoodsDept dept = new TCsGoodsDept();
		dept.setDeptname(deptname);
		dept.setStatus(Integer.valueOf(status));
		String rstr = "{\"code\":\"201\",\"message\":\"失败!\"}";
		try {
			orderService.insertRGoodsDept(dept);
			rstr = "{\"code\":\"200\",\"message\":\"成功!\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rstr;
	}
	/**
	 * 	进入收货单位修改页面
	 */
	@RequestMapping(value="/editrgoodsdeptpage")
	public String editRgoodsDeptPage(HttpServletRequest request, ModelMap map) {
		String id = WebUtils.findParameterValue(request, "id");
		String deptname = WebUtils.findParameterValue(request, "deptname");
		String status = WebUtils.findParameterValue(request, "status");
		map.put("id", id);
		map.put("deptname", deptname);
		map.put("status", status);
		return "order/editrgoodsdept";
	}
	/**
	 * 	修改收货单位
	 */
	@RequestMapping(value="/editrgoodsdept",produces = "application/json; charset=utf-8")
	@ResponseBody
	private String editRgoodsDept(HttpServletRequest request) {
		String id = WebUtils.findParameterValue(request, "id");
		String deptname = WebUtils.findParameterValue(request, "deptname");
		String status = WebUtils.findParameterValue(request, "status");
		TCsGoodsDept dept = new TCsGoodsDept();
		dept.setId(Integer.parseInt(id));
		dept.setDeptname(deptname);
		dept.setStatus(Integer.valueOf(status));
		String rstr = "{\"code\":\"201\",\"message\":\"失败!\"}";
		try {
			orderService.updateRGoodsDept(dept);
			rstr = "{\"code\":\"200\",\"message\":\"成功!\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rstr;
	}
	/**
	 * 	删除收货单位
	 */
	@RequestMapping(value="/deletergoodsdept",produces = "application/json; charset=utf-8")
	@ResponseBody
	private String deleteRgoodsDept(HttpServletRequest request) {
		String id = WebUtils.findParameterValue(request, "id");
		TCsGoodsDept dept = new TCsGoodsDept();
		dept.setId(Integer.parseInt(id));
		String rstr = "{\"code\":\"201\",\"message\":\"失败!\"}";
		try {
			orderService.deleteRGoodsDept(dept);
			rstr = "{\"code\":\"200\",\"message\":\"成功!\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rstr;
	}
	/**
	 *  模拟订单
	 */
	@RequestMapping(value="/simulate",produces = "application/json; charset=utf-8")
	@ResponseBody
	private String simulate(HttpServletRequest request) {
		TCsOrder order = new TCsOrder();
		order.setSnum(CommTime.getNewSSSTime());
		order.setCarnum("辽A123456");
		order.setGoodstype("污泥");
		order.setGoodsweight(new BigDecimal(12312.5));
		order.setSgoodsdept("发货测试");
		order.setConsignor("发送测试");
		order.setSgoodstime(CommTime.getCurrTime2());
		order.setRgoodsdept("收货测试");
		order.setConsignee("收货测试");
		order.setOtheri1(1);
		String rstr = "{\"code\":\"201\",\"message\":\"失败!\"}";
		try {
			orderService.insertOrder(order);
			rstr = "{\"code\":\"200\",\"message\":\"成功!\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rstr;
	}
	/**
	 * 记录查询
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/orderrfidcount")
	public String getOrderRfidCount(HttpServletRequest request, ModelMap map) {
		String pageno = WebUtils.findParameterValue(request, "pageno");
		String stime = WebUtils.findParameterValue(request, "stime");
		String etime = WebUtils.findParameterValue(request, "etime");
		if (StringUtils.isEmpty(stime)) {
			stime = CommTime.getCurrDate();
		}
		if (StringUtils.isEmpty(etime)) {
			etime = CommTime.getCurrDate();
		}
		TCommUser user = (TCommUser) request.getSession().getAttribute(Constant.USER_SESSION_KEY);
		Pages pages = new Pages();
		pages.setPerPageNum(user.getPagenum());
		if (null == pageno || pageno.equals("0")) {
			pageno = "1";
		}
		pages.setPage(Integer.parseInt(pageno));
		Map paramMap = new HashMap();
		if (!StringUtils.isEmpty(stime) && !StringUtils.isEmpty(etime)) {
			paramMap.put("stime", stime);
			paramMap.put("etime", etime);
		}
		List<Map<String,Integer>> orderrfidrlist = orderService.getOrderRfidCountR(paramMap);
		List<Map<String,Integer>> orderrfidslist = orderService.getOrderRfidCountS(paramMap);
		map.put("orderrfidrlist", orderrfidrlist);
		map.put("orderrfidslist", orderrfidslist);
		map.put("stime", stime);
		map.put("etime", etime);
		return "order/orderrfidcount";
	}
}
