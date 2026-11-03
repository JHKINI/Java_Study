package com.myspring.pro30.board.service;

import com.myspring.pro30.board.dao.BoardDAO;
import com.myspring.pro30.board.vo.ArticleVO;
import com.myspring.pro30.board.vo.ImageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("boardService")
@Transactional(propagation = Propagation.REQUIRED)
public class BoardServiceImpl implements BoardService {
    @Autowired
    BoardDAO boardDAO;

    @Override
    public List<ArticleVO> listArticles() throws Exception {
        List<ArticleVO> articlesList = boardDAO.selectAllArticlesList();
        return articlesList;
    }

    @Override
    public int addNewArticle(Map articleMap) throws Exception {
        int articleNO = boardDAO.insertNewArticle(articleMap);
        articleMap.put("articleNO", articleNO);
        boardDAO.insertNewImage(articleMap);
        return articleNO;
    }

    @Override
    public Map viewArticle(int articleNO) throws Exception {
        Map articleMap = new HashMap();
        ArticleVO articleVO = boardDAO.selectArticle(articleNO);
        List<ImageVO> imageFileList = boardDAO.selectImageFileList(articleNO);
        articleMap.put("article", articleVO);
        articleMap.put("imageFileList", imageFileList);
        return articleMap;
    }

    @Override
    public void modArticle(Map articleMap) throws Exception {
        // 1) 제목/내용 수정
        boardDAO.updateArticle(articleMap);

        // 2) 기존 이미지 교체
        List<ImageVO> replaceList = (List<ImageVO>) articleMap.get("replaceList");
        if (replaceList != null) {
            for (ImageVO imageVO : replaceList) {
                boardDAO.updateImage(imageVO);
            }
        }

        // 3) 새 이미지 추가 (목록이 비어 있으면 DAO 가 알아서 건너뜀)
        boardDAO.insertNewImage(articleMap);
    }

    @Override
    public void removeArticle(int articleNO) throws Exception {
        boardDAO.deleteArticle(articleNO);
    }
}
