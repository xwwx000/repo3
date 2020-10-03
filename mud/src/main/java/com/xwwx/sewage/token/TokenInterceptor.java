package com.xwwx.sewage.token;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xwwx.sewage.common.Constant;
import com.xwwx.sewage.common.StringUtils;
import com.xwwx.sewage.redis.RedisCacheManager;

public class TokenInterceptor implements HandlerInterceptor {
	private static Logger logger = Logger.getLogger(TokenInterceptor.class);
	@Autowired 
	private RedisCacheManager redisCacheManager;
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		response.setCharacterEncoding("utf-8");// 设置服务器端编码
		response.setHeader("Cache-Control","no-store");
		response.setHeader("Pragrma","no-cache");
		response.setDateHeader("Expires",0);
		String requestURI = request.getRequestURI();

		//接口拦截
		if (requestURI.contains("/api/")) {
			//String postcontent = getPostContent(request);
			String postcontent = request.getParameter("param");
			String rstr = "";
			String token = "";
			try {
				JSONObject json = (JSONObject) JSON.parse(postcontent);
				token = (String) json.get("token");
			}catch(Exception e) {
				rstr = " {\"code\":\"201\",\"message\":\"解析请求json失败!\"}";
				sendError(response,rstr);
				return false;
			}
			if (null == token) {
				rstr = " {\"code\":\"300\",\"message\":\"token不能为空\"}";
				sendError(response,rstr);
				return false;
			}else {
				//验证token合法性
				String tokenStr = (String)redisCacheManager.get(token);
				if(!StringUtils.isEmpty(tokenStr)) {
					//更新redis中超时时间
					redisCacheManager.expire(token, Constant.REDIS_EXPIRE_TIME);
					return true;
				}else {
					rstr = " {\"code\":\"300\",\"message\":\"token无效\"}";
					sendError(response,rstr);
					return false;
				}
			}
		}else {
			HttpSession session = request.getSession();
			if ((session != null)) {
				if (session.getAttribute(Constant.USER_SESSION_KEY) != null) {
					response.setHeader("Cache-Control","no-store");
					response.setHeader("Pragrma","no-cache");
					response.setDateHeader("Expires",0);
				}else {
					toLogin(request, response);		
					return false;
				}
			}
		}

		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	public void toLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//request.setAttribute("unlogin","true");
		//request.getRequestDispatcher("/login.jsp").forward(request, response);		
		response.sendRedirect(request.getContextPath()+"/unlogin.jsp?unlogin=true");
	}
	public void sendError(HttpServletResponse response,String mstr) {
		PrintWriter pwriter = null;
		try {
			pwriter = response.getWriter();
			pwriter.write(mstr);
		} catch (IOException e) {
			e.getMessage();
		}finally {
			if (pwriter != null)
				pwriter.close();
		}
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
		}catch (Exception e) {
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
}
