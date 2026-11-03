package com.myspring.pro30.member.controller;

import com.myspring.pro30.member.vo.MemberVO;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface MemberController {
    public ModelAndView listMembers(HttpServletRequest request,
                                    HttpServletResponse response) throws Exception;
    public ModelAndView addMember(@ModelAttribute("member") MemberVO member,
                                  HttpServletRequest request,
                                  HttpServletResponse response) throws Exception;
    public ModelAndView removeMember(@RequestParam("id") String id,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws Exception;

    public ModelAndView login(@ModelAttribute("member") MemberVO member,
                              RedirectAttributes rAttr,
                              HttpServletRequest request,
                              HttpServletResponse response) throws Exception;

    public ModelAndView logout(HttpServletRequest request,
                               HttpServletResponse response) throws Exception;
}
