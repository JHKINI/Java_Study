package com.myspring.pro30.board.service;

import java.util.List;
import java.util.Map;

import com.myspring.pro30.board.vo.ArticleVO;

public interface BoardService {
    public List<ArticleVO> listArticles() throws Exception;
    public int addNewArticle(Map articleMap) throws Exception;     // 새 글 번호를 반환
    public ArticleVO viewArticle(int articleNO) throws Exception;  // 30.6
    public void removeArticle(int articleNO) throws Exception;    // 30.6
    public void modArticle(Map articleMap) throws Exception;       // 30.7
}
