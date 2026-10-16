package com.myspring.pro30.board.dao;

import java.util.List;
import java.util.Map;

import com.myspring.pro30.board.vo.ArticleVO;
import org.springframework.dao.DataAccessException;

public interface BoardDAO {
    public List<ArticleVO> selectAllArticlesList() throws DataAccessException;
    public int insertNewArticle(Map articleMap) throws DataAccessException;   // 새 글 번호를 반환
    public ArticleVO selectArticle(int articleNO) throws DataAccessException; // 30.6
    public void updateArticle(Map articleMap) throws DataAccessException;     // 30.7
    public void removeArticle(int articleNO) throws DataAccessException;     // 30.7
}
