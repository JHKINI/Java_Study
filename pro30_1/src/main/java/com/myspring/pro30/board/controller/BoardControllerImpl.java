package com.myspring.pro30.board.controller;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.myspring.pro30.board.service.BoardService;
import com.myspring.pro30.board.vo.ArticleVO;
import com.myspring.pro30.member.vo.MemberVO;

@Controller("boardController")
public class BoardControllerImpl implements BoardController {

    // 이미지가 저장되는 최상위 폴더
    private static final String ARTICLE_IMAGE_REPO =
            "C:\\board\\article_image";

    @Autowired
    private BoardService boardService;

    @Autowired
    private ArticleVO articleVO;

    // ------------------------------------------------------------
    // 글목록창
    // ------------------------------------------------------------
    @Override
    @RequestMapping(value = "/board/listArticles.do",
            method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView listArticles(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        String viewName =
                (String) request.getAttribute("viewName");

        List<ArticleVO> articlesList =
                boardService.listArticles();

        ModelAndView mav = new ModelAndView(viewName);
        mav.addObject("articlesList", articlesList);

        return mav;
    }

    // ------------------------------------------------------------
    // 글쓰기창
    // ------------------------------------------------------------
    @Override
    @RequestMapping(value = "/board/articleForm.do",
            method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView articleForm(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        String viewName =
                (String) request.getAttribute("viewName");

        ModelAndView mav = new ModelAndView(viewName);

        return mav;
    }

    // ------------------------------------------------------------
    // 새 글 추가 (30.5)
    // ------------------------------------------------------------
    @Override
    @RequestMapping(value = "/board/addNewArticle.do",
            method = RequestMethod.POST)
    public ResponseEntity<String> addNewArticle(
            MultipartHttpServletRequest multipartRequest,
            HttpServletResponse response) throws Exception {

        multipartRequest.setCharacterEncoding("utf-8");
        String contextPath = multipartRequest.getContextPath();

        // 로그인한 회원 정보
        HttpSession session = multipartRequest.getSession();
        MemberVO memberVO =
                (MemberVO) session.getAttribute("member");

        if (memberVO == null) {
            return makeScriptResponse(
                    "로그인 후 이용해 주세요.",
                    contextPath + "/member/loginForm.do");
        }

        // 글 정보를 Map에 저장
        Map<String, Object> articleMap =
                new HashMap<String, Object>();

        Enumeration<?> enu =
                multipartRequest.getParameterNames();

        while (enu.hasMoreElements()) {
            String name = (String) enu.nextElement();
            String value = multipartRequest.getParameter(name);
            articleMap.put(name, value);
        }

        String imageFileName = null;

        try {
            // 이미지를 임시 폴더에 업로드
            imageFileName = upload(multipartRequest);

            articleMap.put("parentNO", 0);
            articleMap.put("id", memberVO.getId());
            articleMap.put("imageFileName", imageFileName);

            // DB에 글 저장
            int articleNO =
                    boardService.addNewArticle(articleMap);

            // 임시 폴더에서 글 번호 폴더로 이미지 이동
            if (imageFileName != null
                    && imageFileName.length() != 0) {

                moveTempFileToArticleDir(
                        imageFileName,
                        String.valueOf(articleNO));
            }

            return makeScriptResponse(
                    "새글을 추가했습니다.",
                    contextPath + "/board/listArticles.do");

        } catch (Exception e) {
            deleteTempFile(imageFileName);
            e.printStackTrace();

            return makeScriptResponse(
                    "오류가 발생했습니다. 다시 시도해 주세요.",
                    contextPath + "/board/articleForm.do");
        }
    }

    // ------------------------------------------------------------
    // 글상세창 (30.6)
    // ------------------------------------------------------------
    @Override
    @RequestMapping(value = "/board/viewArticle.do",
            method = RequestMethod.GET)
    public ModelAndView viewArticle(
            @RequestParam("articleNO") int articleNO,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        String viewName =
                (String) request.getAttribute("viewName");

        // 글 번호로 글 정보 조회
        ArticleVO articleVO =
                boardService.viewArticle(articleNO);

        // 상세 화면에 조회 결과 전달
        ModelAndView mav = new ModelAndView();
        mav.setViewName(viewName);
        mav.addObject("article", articleVO);

        return mav;
    }

    // ------------------------------------------------------------
    // 글 삭제 (30.6)
    // ------------------------------------------------------------
    @Override
    @RequestMapping(value = "/board/removeArticle.do",
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> removeArticle(
            @RequestParam("articleNO") int articleNO,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        String contextPath = request.getContextPath();

        try {
            // 데이터베이스에서 게시글 삭제
            boardService.removeArticle(articleNO);

            // 해당 게시글의 이미지 폴더 삭제
            File destDir = new File(
                    ARTICLE_IMAGE_REPO
                            + File.separator
                            + articleNO);

            if (destDir.exists()) {
                FileUtils.deleteDirectory(destDir);
            }

            // 삭제 완료 후 게시글 목록으로 이동
            return makeScriptResponse(
                    "글을 삭제했습니다.",
                    contextPath + "/board/listArticles.do");

        } catch (Exception e) {
            e.printStackTrace();

            // 오류가 발생하면 해당 글 상세 화면으로 이동
            return makeScriptResponse(
                    "글 삭제 중 오류가 발생했습니다.",
                    contextPath
                            + "/board/viewArticle.do?articleNO="
                            + articleNO);
        }
    }

    // ------------------------------------------------------------
    // 글 수정 (30.7)
    // ------------------------------------------------------------
    @Override
    @RequestMapping(value = "/board/modArticle.do",
            method = RequestMethod.POST)
    public ResponseEntity<String> modArticle(
            MultipartHttpServletRequest multipartRequest,
            HttpServletResponse response) throws Exception {

        multipartRequest.setCharacterEncoding("utf-8");
        String contextPath = multipartRequest.getContextPath();

        // 로그인한 회원 정보
        HttpSession session = multipartRequest.getSession();
        MemberVO memberVO =
                (MemberVO) session.getAttribute("member");

        if (memberVO == null) {
            return makeScriptResponse(
                    "로그인 후 이용해 주세요.",
                    contextPath + "/member/loginForm.do");
        }

        // 수정할 글 정보 저장
        Map<String, Object> articleMap =
                new HashMap<String, Object>();

        Enumeration<?> enu =
                multipartRequest.getParameterNames();

        while (enu.hasMoreElements()) {
            String name = (String) enu.nextElement();
            String value = multipartRequest.getParameter(name);
            articleMap.put(name, value);
        }

        String articleNO =
                (String) articleMap.get("articleNO");

        String imageFileName = null;

        try {
            // 새 이미지가 있으면 임시 폴더에 업로드
            imageFileName = upload(multipartRequest);

            articleMap.put("id", memberVO.getId());
            articleMap.put("imageFileName", imageFileName);

            // DB 글 정보 수정
            boardService.modArticle(articleMap);

            // 새 이미지가 첨부된 경우에만 이미지 파일 처리
            if (imageFileName != null
                    && imageFileName.length() != 0) {

                moveTempFileToArticleDir(
                        imageFileName, articleNO);

                String originalFileName =
                        (String) articleMap.get("originalFileName");

                if (originalFileName != null
                        && originalFileName.length() != 0
                        && !originalFileName.equals(imageFileName)) {

                    File oldFile = new File(
                            ARTICLE_IMAGE_REPO
                                    + File.separator
                                    + articleNO
                                    + File.separator
                                    + originalFileName);

                    oldFile.delete();
                }
            }

            return makeScriptResponse(
                    "글을 수정했습니다.",
                    contextPath
                            + "/board/viewArticle.do?articleNO="
                            + articleNO);

        } catch (Exception e) {
            deleteTempFile(imageFileName);
            e.printStackTrace();

            return makeScriptResponse(
                    "오류가 발생했습니다. 다시 수정해 주세요.",
                    contextPath
                            + "/board/viewArticle.do?articleNO="
                            + articleNO);
        }
    }

    // ------------------------------------------------------------
    // 이미지 업로드
    // ------------------------------------------------------------
    private String upload(
            MultipartHttpServletRequest multipartRequest)
            throws Exception {

        String imageFileName = null;

        Iterator<String> fileNames =
                multipartRequest.getFileNames();

        while (fileNames.hasNext()) {
            String fileName = fileNames.next();

            MultipartFile mFile =
                    multipartRequest.getFile(fileName);

            imageFileName = mFile.getOriginalFilename();

            if (mFile.getSize() != 0) {
                File tempFile = new File(
                        ARTICLE_IMAGE_REPO
                                + File.separator
                                + "temp"
                                + File.separator
                                + imageFileName);

                tempFile.getParentFile().mkdirs();
                mFile.transferTo(tempFile);
            }
        }

        return imageFileName;
    }

    // ------------------------------------------------------------
    // 임시 폴더에서 글 번호 폴더로 이미지 이동
    // ------------------------------------------------------------
    private void moveTempFileToArticleDir(
            String imageFileName,
            String articleNO) throws Exception {

        File srcFile = new File(
                ARTICLE_IMAGE_REPO
                        + File.separator
                        + "temp"
                        + File.separator
                        + imageFileName);

        File destDir = new File(
                ARTICLE_IMAGE_REPO
                        + File.separator
                        + articleNO);

        File destFile = new File(destDir, imageFileName);

        if (destFile.exists()) {
            destFile.delete();
        }

        FileUtils.moveFileToDirectory(
                srcFile, destDir, true);
    }

    // ------------------------------------------------------------
    // 오류 발생 시 임시 이미지 삭제
    // ------------------------------------------------------------
    private void deleteTempFile(String imageFileName) {

        if (imageFileName != null
                && imageFileName.length() != 0) {

            File tempFile = new File(
                    ARTICLE_IMAGE_REPO
                            + File.separator
                            + "temp"
                            + File.separator
                            + imageFileName);

            tempFile.delete();
        }
    }

    // ------------------------------------------------------------
    // 메시지를 표시한 뒤 지정한 주소로 이동
    // ------------------------------------------------------------
    private ResponseEntity<String> makeScriptResponse(
            String message,
            String url) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(
                "Content-Type", "text/html; charset=utf-8");

        String script = "<script>";
        script += "alert('" + message + "');";
        script += "location.href='" + url + "';";
        script += "</script>";

        return new ResponseEntity<String>(
                script, responseHeaders, HttpStatus.OK);
    }
}
