package com.xwwx.sewage.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;

import net.sf.json.JSONArray;

import com.alibaba.fastjson.JSON;
import com.xwwx.sewage.bean.TCommUser;
import com.xwwx.sewage.common.CommTime;
import com.xwwx.sewage.common.Constant;
import com.xwwx.sewage.common.Md5;
import com.xwwx.sewage.redis.RedisCacheManager;
import com.xwwx.sewage.service.UserService;
import com.xwwx.sewage.token.JwtUtil;
import com.xwwx.sewage.token.ResponseData;

/**
 * 
 * @author xwwx
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
	private static Logger logger = Logger.getLogger(UserController.class);

	// @Resource(name="userService")
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	@Autowired
	private RedisCacheManager redisCacheManager;

	@RequestMapping("/loginToken")
	@ResponseBody
	private ResponseData loginToken(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userid = WebUtils.findParameterValue(request, "userid");
		String password = WebUtils.findParameterValue(request, "password");
		boolean loginResult = false;
		TCommUser userBean = null;
		try {
			userBean = userService.login(userid, Md5.encrypt(password));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (userBean != null) {
			logger.info(userBean.toString());
			loginResult = true;
		}
		if (loginResult) {
			String token = JwtUtil.sign(userid, userBean.getUsername());
			response.setHeader("Access-Control-Expose-Headers", "no-store");
			response.setHeader("Cache-Control", "Authorization");
			response.setHeader("user_token", token);
			userBean.setUsertoken(token);
			redisCacheManager.set(token, JSON.toJSONString(userBean), Constant.REDIS_EXPIRE_TIME);
			logger.info("token:" + token + "\n\r radis中token:" + redisCacheManager.get(token));
			return ResponseData.ok().putDataValue("user_token", token);
		} else {
			return ResponseData.error().putDataValue("errormsg", new String[] { "用户名或者密码错误" });
		}
	}

	@RequestMapping("/login")
	@ResponseBody
	private ResponseData login(HttpServletRequest request, HttpServletResponse response) {
		String userid = WebUtils.findParameterValue(request, "userid");
		String password = WebUtils.findParameterValue(request, "password");
		boolean loginResult = false;
		TCommUser userBean = null;
		try {
			userBean = userService.login(userid, Md5.encrypt(password));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (userBean != null) {
			logger.info(userBean.toString());
			loginResult = true;
		}
		if (loginResult) {
			userBean.setLastloginip(request.getRemoteAddr());
			userBean.setLastlogintime(CommTime.getCurrTime2());
			try {
				userService.updateUser(userBean);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 登录成功 写入session
			request.getSession().setAttribute(Constant.USER_SESSION_KEY, userBean);
			return ResponseData.ok();
		} else {
			return ResponseData.error().putDataValue("errormsg", new String[] { "用户名或者密码错误" });
		}
	}

	@RequestMapping("/main")
	private String main() {

		return "frame/main";
	}

	@RequestMapping("/top")
	private String top() {

		return "frame/top";
	}
	@RequestMapping("/leftframe")
	private String leftFrame(HttpServletRequest request) {

		request.setAttribute("moduleid", "11");
		return "frame/left";
	}

	@RequestMapping("/bottom")
	private String bottom() {

		return "frame/bottom";
	}

	@RequestMapping("/click1")
	private String click1() {

		return "frame/click1";
	}

	@RequestMapping("/click2")
	private String click2() {

		return "frame/click2";
	}

	@RequestMapping("/welcome")
	private String welcome() {

		return "frame/welcome";
	}
	
	/**
	 * 左侧菜单列表显示(按权限)
	 * @param request
	 * @return
	 */
	@RequestMapping(value ="/left",produces = "application/json; charset=utf-8")
	@ResponseBody
	private String left(HttpServletRequest request) {

		String moduleid = WebUtils.findParameterValue(request, "moduleid");
		TCommUser user = (TCommUser)request.getSession().getAttribute(Constant.USER_SESSION_KEY);
		//取左侧权限菜单
		//logger.info("moduleid:" +moduleid + "userid:"+user.getUserid());
		List<Map<String,String>> list = userService.getMenuByUserIdAndModuleId(moduleid,user.getUserid());
		JSONArray jsonarray = JSONArray.fromObject(list);
		//JSONArray jsonarray = JSONArray.parseArray(JSON.toJSONString(list));
		return jsonarray.toString();
	}
	/**
	 * 左侧菜单列表显示(按权限)
	 * @param request
	 * @return
	 */
	@RequestMapping(value ="/heartbeat",produces = "application/json; charset=utf-8")
	@ResponseBody
	private String heartbeat() {
		userService.getUserCount();
		return "";
	}
}
