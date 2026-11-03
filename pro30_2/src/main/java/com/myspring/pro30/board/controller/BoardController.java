package com.myspring.pro30.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

public interface BoardController {

    // 글목록창
    public ModelAndView listArticles(HttpServletRequest request,
                                     HttpServletResponse response) throws Exception;

    // 글쓰기창
    public ModelAndView articleForm(HttpServletRequest request,
                                    HttpServletResponse response) throws Exception;

    // 새 글 추가 (이미지 업로드 포함)
    public ResponseEntity<String> addNewArticle(MultipartHttpServletRequest multipartRequest,
                                                HttpServletResponse response) throws Exception;

    // 글상세창 (30.6)
    public ModelAndView viewArticle(int articleNO,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception;


    // 글 삭제
    public ResponseEntity<String> removeArticle(int articleNO,
                                                HttpServletRequest request,
                                                HttpServletResponse response) throws Exception;

    // 글 수정 (30.7)
    public ResponseEntity<String> modArticle(MultipartHttpServletRequest multipartRequest,
                                             HttpServletResponse response) throws Exception;
}
