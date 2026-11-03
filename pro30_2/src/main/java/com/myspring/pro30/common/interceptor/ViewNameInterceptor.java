package com.myspring.pro30.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ViewNameInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        try {
            String viewName = getViewName(request);
            request.setAttribute("viewName", viewName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) throws Exception {
    }

    private String getViewName(HttpServletRequest request) throws Exception {
        String contextPath = request.getContextPath();
        String uri = (String) request.getAttribute("javax.servlet.include.request_uri");

        if (uri == null || uri.trim().isEmpty()) {
            uri = request.getRequestURI();
        }

        int begin = 0;
        if (contextPath != null && !contextPath.isEmpty()) {
            begin = contextPath.length();
        }

        int end;
        if (uri.indexOf(';') != -1) {
            end = uri.indexOf(';');
        } else if (uri.indexOf('?') != -1) {
            end = uri.indexOf('?');
        } else {
            end = uri.length();
        }

        // 컨텍스트 경로와 확장자를 제거하되, /board/articleForm처럼
        // 디렉터리 경로는 유지해야 Tiles 정의 이름과 일치합니다.
        String fileName = uri.substring(begin, end);
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex != -1) {
            fileName = fileName.substring(0, dotIndex);
        }

        // 루트 요청은 메인 화면으로 연결
        if (fileName.isEmpty() || fileName.equals("/")) {
            return "main";
        }

        return fileName;
    }
}
