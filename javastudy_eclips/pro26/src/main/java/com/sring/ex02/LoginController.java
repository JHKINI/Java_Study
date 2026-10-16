package com.spring.ex02;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller("loginController")
public class LoginController {

    @RequestMapping(
        value = {"/test/loginForm.do", "/test/loginForm2.do"},
        method = {RequestMethod.GET}
    )
    public ModelAndView loginForm(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ModelAndView mav = new ModelAndView();

        mav.setViewName("loginForm");

        return mav;
    }

    @RequestMapping(
        value = "/test/login5.do",
        method = {RequestMethod.GET, RequestMethod.POST}
    )
    public ModelAndView login5(
            @ModelAttribute("info") LoginVO loginVO,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        request.setCharacterEncoding("utf-8");

        ModelAndView mav = new ModelAndView();

        System.out.println("userID: " + loginVO.getUserID());
        System.out.println("userName: " + loginVO.getUserName());

        mav.setViewName("result");

        return mav;
    }
}