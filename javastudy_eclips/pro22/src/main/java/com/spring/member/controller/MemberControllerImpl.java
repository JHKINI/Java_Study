package com.spring.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.spring.member.service.MemberService;

public class MemberControllerImpl extends MultiActionController implements MemberController {

	private MemberService memberService;

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("===== listMembers 호출됨 =====");

		String viewName = getViewName(request);

		List membersList = memberService.listMembers();

		ModelAndView mav = new ModelAndView(viewName);

		mav.addObject("membersList", membersList);

		return mav;
	}

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

		String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
		System.out.println("2. uri : " + uri);

		if (uri == null || uri.trim().equals("")) {
			uri = request.getRequestURI();
		}

		int begin = 0;

		if (!((contextPath == null) || ("".equals(contextPath)))) {
			begin = contextPath.length();
			System.out.println("3. begin : " + begin);
		}

		int end;

		if (uri.indexOf(";") != -1) {
			end = uri.indexOf(";");
		} else if (uri.indexOf("?") != -1) {
			end = uri.indexOf("?");
		} else {
			end = uri.length();
		}

		System.out.println("4. end : " + end);

		String fileName = uri.substring(begin, end);

		System.out.println("5. fileName : " + fileName);

		if (fileName.indexOf(".") != -1) {
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
			System.out.println("6. ._fileName : " + fileName);
		}

		if (fileName.lastIndexOf("/") != -1) {
			fileName = fileName.substring(fileName.lastIndexOf("/"), fileName.length());
			System.out.println("7. /_fileName : " + fileName);
		}

		System.out.println("8. fileName : " + fileName);

		return fileName;
	}
}