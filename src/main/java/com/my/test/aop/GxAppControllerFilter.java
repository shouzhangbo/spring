package com.my.test.aop;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;


public class GxAppControllerFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		this.beforMethod(request, response);
		filterChain.doFilter(request, response);
		this.afterMethod(request, response);
		
	}
	
	
	//执行方法前
	private void beforMethod(HttpServletRequest request,
			HttpServletResponse response){

		//通过ThreadLocal方式将request和response保存，可以获取session
//		SysContent.setRequest(request);
//		SysContent.setResponse(response);
		//log4j中添加ip
		//同时在服务器启动的时候也加载一次ip在cn.com.funguide.untils.common.IPInit中，启动后的ip在下面加载
		MDC.clear();
//		MDC.put("iputid", GetHostMessage.getInputId());
//		MDC.put("ip", GetHostMessage.getIp());
	}
	
	//执行方法后
	private void afterMethod(HttpServletRequest request,
			HttpServletResponse response){

	}

}
