package com.myspring.pro30.board.dao;

import com.myspring.pro30.board.vo.ArticleVO;
import com.myspring.pro30.board.vo.ImageVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("boardDAO")
public class BoardDAOImpl implements BoardDAO {
    @Autowired
    private SqlSession sqlSession;

    @Override
    public List selectAllArticlesList() throws Exception {
        List<ArticleVO> articlesList = sqlSession.selectList("mapper.board.selectAllArticlesList");
        return articlesList;
    }

    @Override
    public int insertNewArticle(Map articleMap) throws DataAccessException {
        int articleNO = selectNewArticleNO();
        articleMap.put("articleNO", articleNO);
        sqlSession.insert("mapper.board.insertNewArticle", articleMap);
        return articleNO;
    }

    @Override
    public ArticleVO selectArticle(int articleNO) throws DataAccessException {
        return sqlSession.selectOne("mapper.board.selectArticle", articleNO);
    }

    @Override
    public void updateArticle(Map articleMap) throws DataAccessException {
        sqlSession.update("mapper.board.updateArticle", articleMap);
    }

    @Override
    public void deleteArticle(int articleNO) throws DataAccessException {
        sqlSession.delete("mapper.board.deleteArticle", articleNO);
    }

    private int selectNewArticleNO() throws DataAccessException {
        return sqlSession.selectOne("mapper.board.selectNewArticleNO");
    }

    @Override
    public void insertNewImage(Map articleMap) throws DataAccessException {
        List<ImageVO> imageFileList = (List<ImageVO>) articleMap.get("imageFileList");
        // 첨부 파일이 없으면 할 일이 없다 (빈 목록으로 INSERT ALL 을 날리면 SQL 오류)
        if (imageFileList == null || imageFileList.isEmpty()) {
            return;
        }
        int articleNO = Integer.parseInt(String.valueOf(articleMap.get("articleNO")));
        int imageFileNO = selectNewImageFileNO();
        for (ImageVO imageVO : imageFileList) {
            imageVO.setImageFileNO(++imageFileNO);
            imageVO.setArticleNO(articleNO);
        }
        sqlSession.insert("mapper.board.insertNewImage", imageFileList);
    }

    @Override
    public void updateImage(ImageVO imageVO) throws DataAccessException {
        sqlSession.update("mapper.board.updateImage", imageVO);
    }

    @Override
    public List selectImageFileList(int articleNO) throws DataAccessException {
        List<ImageVO> imageFileList = sqlSession.selectList("mapper.board.selectImageFileList", articleNO);
        return imageFileList;
    }

    private int selectNewImageFileNO() throws DataAccessException {
        return sqlSession.selectOne("mapper.board.selectNewImageFileNO");
    }
}
