package com.myspring.pro30.board.dao;

import com.myspring.pro30.board.vo.ArticleVO;
import com.myspring.pro30.board.vo.ImageVO;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

public interface BoardDAO {
    public List selectAllArticlesList() throws Exception;
    public int insertNewArticle(Map articleMap) throws DataAccessException;
    public ArticleVO selectArticle(int articleNO) throws DataAccessException;
    public void updateArticle(Map articleMap) throws DataAccessException;
    public void deleteArticle(int articleNO) throws DataAccessException;
    public void insertNewImage(Map articleMap) throws DataAccessException;
    public void updateImage(ImageVO imageVO) throws DataAccessException;
    public List selectImageFileList(int articleNO) throws DataAccessException;
}
