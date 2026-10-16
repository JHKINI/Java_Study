package com.myspring.pro30.member.controller;

import com.myspring.pro30.member.service.MemberService;
import com.myspring.pro30.member.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller("memberController")
public class MemberControllerImpl implements MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberVO memberVO;

    @RequestMapping(value = {"/","/main.do"}, method = RequestMethod.GET)
    public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String viewName = (String) request.getAttribute("viewName");
        ModelAndView mav = new ModelAndView();
        mav.setViewName(viewName);
        return mav;
    }

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
    @RequestMapping(value = "/member/addMember.do", method = RequestMethod.POST)
    public ModelAndView addMember(@ModelAttribute("member") MemberVO member,
                                  HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        int result = memberService.addMember(member);
        ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
        return mav;
    }

    @Override
    @RequestMapping(value = "/member/removeMember.do", method = RequestMethod.GET)
    public ModelAndView removeMember(@RequestParam("id") String id,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        memberService.removeMember(id);
        ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
        return mav;
    }

    // ------------------------------------------------------------
    // 로그인 (30.5)
    // ------------------------------------------------------------
    @Override
    @RequestMapping(value = "/member/login.do", method = RequestMethod.POST)
    public ModelAndView login(@ModelAttribute("member") MemberVO member,
                              RedirectAttributes rAttr,        // 리다이렉트할 때 매개변수를 전달
                              HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView();

        // 아이디/비밀번호로 회원 조회 (없으면 null)
        MemberVO loginMember = memberService.login(member);

        if (loginMember != null) {
            // 로그인 성공: 세션에 회원 정보와 로그인 상태를 저장
            HttpSession session = request.getSession();
            session.setAttribute("member", loginMember);
            session.setAttribute("isLogOn", true);

            // 로그인 전에 세션에 저장해 둔 action(다음에 이동할 요청)을 가져옴
            String action = (String) session.getAttribute("action");
            session.removeAttribute("action");

            if (action != null) {
                mav.setViewName("redirect:" + action);          // 예: /board/articleForm.do
            } else {
                mav.setViewName("redirect:/member/listMembers.do");
            }
        } else {
            // 로그인 실패: 다시 로그인창으로
            rAttr.addAttribute("result", "loginFailed");
            mav.setViewName("redirect:/member/loginForm.do");
        }
        return mav;
    }

    // ------------------------------------------------------------
    // 로그아웃 (30.5)
    // ------------------------------------------------------------
    @Override
    @RequestMapping(value = "/member/logout.do", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        session.removeAttribute("member");
        session.removeAttribute("isLogOn");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/member/listMembers.do");
        return mav;
    }

    // ------------------------------------------------------------
    // 로그인창 / 회원가입창
    //   글쓰기에서 로그인창으로 올 때 ?action=/board/articleForm.do 가 같이 넘어옵니다.
    //   그 값을 세션에 저장해 두었다가 로그인 성공 후 바로 글쓰기창으로 이동합니다.
    // ------------------------------------------------------------
    @RequestMapping(value = {"/member/loginForm.do", "/member/memberForm.do"}, method = RequestMethod.GET)
    public ModelAndView form(@RequestParam(value = "result", required = false) String result,
                             @RequestParam(value = "action", required = false) String action,
                             HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        String viewName = getViewName(request);
        HttpSession session = request.getSession();
        if (action != null) {
            // 로그인에 실패해서 다시 이 창으로 올 때(action 없음) 기존 값이 지워지지 않도록 null 이 아닐 때만 저장
            session.setAttribute("action", action);
        }
        ModelAndView mav = new ModelAndView();
        mav.addObject("result", result);
        mav.setViewName(viewName);
        return mav;
    }

    private String getViewName(HttpServletRequest request) throws Exception {
        String contextPath = request.getContextPath();
        String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
        if (uri == null || uri.trim().equals("")) {
            uri = request.getRequestURI();
        }

        int begin = 0;
        if (!((contextPath == null) || ("".equals(contextPath)))) {
            begin = contextPath.length();
        }

        int end;
        if (uri.indexOf(";") != -1) {
            end = uri.indexOf(";");
        } else if (uri.indexOf("?") != -1) {
            end = uri.indexOf("?");
        } else {
            end = uri.length();
        }

        String viewName = uri.substring(begin, end);
        if (viewName.indexOf(".") != -1) {
            viewName = viewName.substring(0, viewName.lastIndexOf("."));
        }
        if (viewName.lastIndexOf("/") != -1) {
            viewName = viewName.substring(viewName.lastIndexOf("/", 1), viewName.length());
        }
        return viewName;
    }
}
