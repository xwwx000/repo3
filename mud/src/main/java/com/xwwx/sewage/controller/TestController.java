package com.xwwx.sewage.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {

	@RequestMapping("/webinf")
	public void toWebInf(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
		rd.forward(request, response);
	}
	
	@RequestMapping("/webinf1")
	public String toWebInf1() {
		
		return "index";
	}
	
	@RequestMapping("/webinf2")
	public void toWebInf2(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/index.html");
		rd.forward(request, response);
	}
	
	@RequestMapping("/cors")
	public void cors(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/static/cors.html");
		rd.forward(request, response);
	}	
}
