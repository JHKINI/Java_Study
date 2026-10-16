package com.spring.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.spring.member.service.MemberService;
import com.spring.member.vo.MemberVO;

@Controller("memberController")
public class MemberControllerImpl extends MultiActionController implements MemberController {

//    private MemberService memberService;
//
//    public void setMemberService(MemberService memberService) {
//        this.memberService = memberService;
//    }

	@Autowired
	private MemberService memberService;

	@Autowired
	MemberVO memberVO;

	@Override
	@RequestMapping(value = "/member/listMembers.do", method = RequestMethod.GET)
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
		List membersList = memberService.listMembers();
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("membersList", membersList);
		return mav;
	}

	@Override
	@RequestMapping(value = "/member/addMember.do", method=RequestMethod.POST)
	public ModelAndView addMember(@ModelAttribute("member") MemberVO member,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		MemberVO memberVO = new MemberVO();
		bind(request, memberVO);
		int result = memberService.addMember(member);
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		return mav;
	}

	@Override
	@RequestMapping(value = "/member/removeMember.do", method=RequestMethod.GET)
	public ModelAndView removeMember(@RequestParam("id") String id,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		memberService.removeMember(id);
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		return mav;
	}
	@RequestMapping(value = "/member/*Form.do", method=RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String viewName = getViewName(request);
		ModelAndView mav = new ModelAndView();
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