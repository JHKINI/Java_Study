package com.spring.ex02;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class UserController extends MultiActionController {
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userID = "";
		String passwd = "";
		String viewName = getViewName(request);
		ModelAndView mav = new ModelAndView();

		request.setCharacterEncoding("utf-8");
		userID = request.getParameter("userID");
		passwd = request.getParameter("passwd");
		mav.addObject("userID", userID);
		mav.addObject("passwd", passwd);
		// mav.setViewName("result");
		mav.setViewName(viewName);
		return mav;
	}

	public ModelAndView memberInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ModelAndView mav = new ModelAndView();

		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String name = request.getParameter("name");
		String email = request.getParameter("email");

		mav.addObject("id", id);
		mav.addObject("pwd", pwd);
		mav.addObject("name", name);
		mav.addObject("email", email);
		mav.setViewName("memberInfo");
		return mav;

	}

	private String getViewName(HttpServletRequest request) throws Exception {
		String contextPath = request.getContextPath();
		System.out.println("1. contextPath : " + contextPath);
		String uri = (String)request.getAttribute("javax.servlet.include.request_uri");
		System.out.println("2. uri : " + uri);
		
		if(uri == null || uri.trim().equals("")) {
			uri = request.getRequestURI();
		}
		
		int begin = 0;
		if(!((contextPath == null) || ("".equals(contextPath)))) {
			begin = contextPath.length();
			System.out.println("3. begin : " + begin);
		}

		int end;
		if(uri.indexOf(";") != -1) {
			end = uri.indexOf(";");
		} else if(uri.indexOf("?") != -1) {
			end = uri.indexOf("?");
		} else {
			end = uri.length();
		}
		System.out.println("4. end : " + end);
		
		String fileName = uri.substring(begin, end);
		System.out.println("5. fileName : " + fileName);
		
		// 1) 점(.)이 있다면 제거하는 블록 (여기에 return이 있으면 안 됩니다!)
		if(fileName.indexOf(".") != -1) {
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
			System.out.println("6. ._fileName : " + fileName);
		}
		
		// 2) 슬래시(/)가 있다면 처리하는 블록 (첫 번째 if문 바깥에 독립적으로 존재)
		if(fileName.lastIndexOf("/") != -1) {
			fileName = fileName.substring(fileName.lastIndexOf("/"), fileName.length());
			System.out.println("7. /_fileName : " + fileName);
		}
		System.out.println("8. fileName : " + fileName);
		
		return fileName; // 3) 모든 처리가 끝난 후 안전하게 리턴 (메서드 맨 끝)
	}}