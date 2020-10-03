package com.xwwx.sewage.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.xwwx.sewage.bean.TCommUser;
import com.xwwx.sewage.common.CommTime;
import com.xwwx.sewage.common.Constant;
import com.xwwx.sewage.common.PageList;
import com.xwwx.sewage.common.Pages;
import com.xwwx.sewage.service.LogService;

/**
 * 日志操作
 * @author 可乐罐
 *
 */
@Controller
@RequestMapping("/log")
public class LogController {

	@Autowired
	private LogService logService;
	@RequestMapping("/loglist")
	public String logList(HttpServletRequest request,ModelMap map) {
		String pageno = WebUtils.findParameterValue(request, "pageno");
		String logtype = WebUtils.findParameterValue(request, "logtype");
		String stime = WebUtils.findParameterValue(request, "stime");
		String etime = WebUtils.findParameterValue(request, "etime");
		if (StringUtils.isEmpty(stime)) {
			stime = CommTime.getCurrDate();
		}
		if (StringUtils.isEmpty(etime)) {
			etime = CommTime.getCurrDate();
		}

		Pages pages = new Pages();
		TCommUser user = (TCommUser) request.getSession().getAttribute(Constant.USER_SESSION_KEY);
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
		if (!StringUtils.isEmpty(logtype)) {
			paramMap.put("logtype", logtype);
		}else {
			logtype = "-1";
		}
		PageList pageList = logService.getLogList("processtime",Constant.LIST_DESC, pages, paramMap);
		String pagination = pages.getUrlstr();
		map.put("pageList", pageList);
		map.put("pagination", pagination);
		map.put("stime", stime);
		map.put("etime", etime);
		map.put("logtype", logtype);
		return "log/loglist";
	}
	/**
	 * 删除日志
	 */
	@RequestMapping(value = "/clearlog", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String clearLog(HttpServletRequest request) {
		String rstr = "{\"code\":\"201\",\"message\":\"失败!\"}";
		String stime = WebUtils.findParameterValue(request, "stime");
		String etime = WebUtils.findParameterValue(request, "etime");
		try {
			Map paramMap = new HashMap();
			paramMap.put("stime", stime);
			paramMap.put("etime", etime);
			logService.clearLog(paramMap);
			rstr = "{\"code\":\"200\",\"message\":\"成功!\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rstr;
	}
}
