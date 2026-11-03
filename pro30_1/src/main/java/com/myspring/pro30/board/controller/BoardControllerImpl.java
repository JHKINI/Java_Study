package com.myspring.pro30.board.controller;

import com.myspring.pro30.board.service.BoardService;
import com.myspring.pro30.board.vo.ArticleVO;
import com.myspring.pro30.board.vo.ImageVO;
import com.myspring.pro30.common.file.FileDownloadController;
import com.myspring.pro30.member.vo.MemberVO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller("boardController")
public class BoardControllerImpl implements BoardController {
    // 이미지 저장 폴더 (FileDownloadController 와 같은 값을 쓴다)
    private static final String ARTICLE_IMAGE_REPO = FileDownloadController.ARTICLE_IMAGE_REPO;

    @Autowired
    private BoardService boardService;

    /* ------------------------------------------------------------------
     * 글 목록
     * ------------------------------------------------------------------ */
    @Override
    @RequestMapping(value = "/board/listArticles.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView listArticles(HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        String viewName = (String) request.getAttribute("viewName");
        List articlesList = boardService.listArticles();
        ModelAndView mav = new ModelAndView(viewName);
        mav.addObject("articlesList", articlesList);
        return mav;
    }

    /* ------------------------------------------------------------------
     * 새 글 쓰기
     * ------------------------------------------------------------------ */
    @Override
    @RequestMapping(value = "/board/addNewArticle.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addNewArticle(MultipartHttpServletRequest multipartRequest,
                                        HttpServletResponse response) throws Exception {
        multipartRequest.setCharacterEncoding("utf-8");
        String ctx = multipartRequest.getContextPath();

        MemberVO memberVO = getLoginMember(multipartRequest);
        if (memberVO == null) {
            return script("로그인 후 글쓰기가 가능합니다.", ctx + "/member/loginForm.do");
        }

        Map articleMap = readParameters(multipartRequest);
        articleMap.put("parentNO", 0);              // 새 글은 부모 글이 없다(0)
        articleMap.put("id", memberVO.getId());     // 작성자는 항상 세션의 로그인 회원

        return saveArticle(multipartRequest, articleMap,
                "새글을 추가했습니다.", ctx + "/board/listArticles.do",
                ctx + "/board/articleForm.do");
    }

    /* ------------------------------------------------------------------
     * 답글 쓰기 (폼 → 저장)
     * ------------------------------------------------------------------ */
    @RequestMapping(value = "/board/replyForm.do", method = RequestMethod.POST)
    public ModelAndView replyForm(@RequestParam("parentNO") int parentNO,
                                  HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
        if (getLoginMember(request) == null) {
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        request.getSession().setAttribute("parentNO", parentNO);
        String viewName = (String) request.getAttribute("viewName");
        return new ModelAndView(viewName);
    }

    @Override
    @RequestMapping(value = "/board/addReply.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addReply(MultipartHttpServletRequest multipartRequest,
                                   HttpServletResponse response) throws Exception {
        multipartRequest.setCharacterEncoding("utf-8");
        String ctx = multipartRequest.getContextPath();
        String listUrl = ctx + "/board/listArticles.do";

        HttpSession session = multipartRequest.getSession();
        MemberVO memberVO = getLoginMember(multipartRequest);
        Integer parentNO = (Integer) session.getAttribute("parentNO");
        if (memberVO == null || parentNO == null) {
            return script("잘못된 접근입니다. 글 상세 화면에서 답글쓰기를 눌러주세요.", listUrl);
        }

        Map articleMap = readParameters(multipartRequest);
        articleMap.put("parentNO", parentNO);
        articleMap.put("id", memberVO.getId());

        ResponseEntity resEnt = saveArticle(multipartRequest, articleMap,
                "답글을 추가했습니다.", listUrl, listUrl);
        session.removeAttribute("parentNO");
        return resEnt;
    }

    /* ------------------------------------------------------------------
     * 글쓰기 화면 (/board/articleForm.do 등)
     * ------------------------------------------------------------------ */
    @RequestMapping(value = "/board/*Form.do", method = RequestMethod.GET)
    public ModelAndView form(HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        String viewName = (String) request.getAttribute("viewName");
        // 글쓰기 화면은 로그인한 사람만
        if (getLoginMember(request) == null) {
            return new ModelAndView("redirect:/member/loginForm.do?action=/board/articleForm.do");
        }
        ModelAndView mav = new ModelAndView();
        mav.setViewName(viewName);
        return mav;
    }

    /* ------------------------------------------------------------------
     * 글 상세
     * ------------------------------------------------------------------ */
    @Override
    @RequestMapping(value = "/board/viewArticle.do", method = RequestMethod.GET)
    public ModelAndView viewArticle(@RequestParam("articleNO") int articleNO,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        String viewName = (String) request.getAttribute("viewName");
        Map articleMap = boardService.viewArticle(articleNO);
        ModelAndView mav = new ModelAndView();
        mav.setViewName(viewName);
        mav.addObject("articleMap", articleMap);
        return mav;
    }

    /* ------------------------------------------------------------------
     * 글 수정 (30.7)
     *   - replace_{imageFileNO} : 기존 이미지를 새 파일로 교체
     *   - 그 외 이름의 파일      : 새 이미지 추가
     * ------------------------------------------------------------------ */
    @Override
    @RequestMapping(value = "/board/modArticle.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity modArticle(MultipartHttpServletRequest multipartRequest,
                                     HttpServletResponse response) throws Exception {
        multipartRequest.setCharacterEncoding("utf-8");
        String ctx = multipartRequest.getContextPath();

        Map<String, Object> articleMap = readParameters(multipartRequest);
        int articleNO = Integer.parseInt((String) articleMap.get("articleNO"));
        articleMap.put("articleNO", articleNO);
        String viewUrl = ctx + "/board/viewArticle.do?articleNO=" + articleNO;

        List<ImageVO> replaceList = new ArrayList<ImageVO>();   // 교체할 이미지
        List<ImageVO> newList = new ArrayList<ImageVO>();       // 새로 추가할 이미지
        try {
            if (!isWriter(getLoginMember(multipartRequest), articleNO)) {
                return script("작성자만 수정할 수 있습니다.", viewUrl);
            }

            Map<String, String> uploaded = upload(multipartRequest);
            for (Map.Entry<String, String> entry : uploaded.entrySet()) {
                ImageVO imageVO = new ImageVO();
                imageVO.setArticleNO(articleNO);
                imageVO.setImageFileName(entry.getValue());
                if (entry.getKey().startsWith("replace_")) {
                    imageVO.setImageFileNO(Integer.parseInt(entry.getKey().substring("replace_".length())));
                    replaceList.add(imageVO);
                } else {
                    newList.add(imageVO);
                }
            }
            articleMap.put("replaceList", replaceList);
            articleMap.put("imageFileList", newList);

            boardService.modArticle(articleMap);

            // DB 수정이 끝난 뒤 파일 정리: 옛 파일 삭제 → 새 파일 이동
            File articleDir = new File(ARTICLE_IMAGE_REPO, String.valueOf(articleNO));
            for (ImageVO imageVO : replaceList) {
                String oldName = (String) articleMap.get("originalFileName_" + imageVO.getImageFileNO());
                if (oldName != null && oldName.length() != 0) {
                    new File(articleDir, FilenameUtils.getName(oldName)).delete();
                }
            }
            moveTempFiles(replaceList, articleNO);
            moveTempFiles(newList, articleNO);

            return script("게시글을 수정하였습니다.", viewUrl);
        } catch (Exception e) {
            deleteTempFiles(replaceList);
            deleteTempFiles(newList);
            e.printStackTrace();
            return script("오류가 발생했습니다. 다시 시도해 주세요.", viewUrl);
        }
    }

    /* ------------------------------------------------------------------
     * 글 삭제
     * ------------------------------------------------------------------ */
    @Override
    @RequestMapping(value = "/board/removeArticle.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity removeArticle(@RequestParam("articleNO") int articleNO,
                                        HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
        String ctx = request.getContextPath();
        String listUrl = ctx + "/board/listArticles.do";
        try {
            if (!isWriter(getLoginMember(request), articleNO)) {
                return script("작성자만 삭제할 수 있습니다.", listUrl);
            }
            boardService.removeArticle(articleNO);
            FileUtils.deleteDirectory(new File(ARTICLE_IMAGE_REPO, String.valueOf(articleNO)));
            return script("게시글을 삭제하였습니다.", listUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return script("오류가 발생했습니다. 다시 시도해 주세요.", listUrl);
        }
    }

    /* ==================================================================
     * 아래는 컨트롤러 안에서만 쓰는 도우미 메서드
     * ================================================================== */

    // 새 글 / 답글 공통 저장 처리
    private ResponseEntity saveArticle(MultipartHttpServletRequest multipartRequest, Map articleMap,
                                       String okMessage, String okUrl, String failUrl) {
        List<ImageVO> imageFileList = new ArrayList<ImageVO>();
        try {
            // 1) 첨부 파일을 temp 폴더에 저장하고 파일명 목록을 만든다
            for (String fileName : upload(multipartRequest).values()) {
                ImageVO imageVO = new ImageVO();
                imageVO.setImageFileName(fileName);
                imageFileList.add(imageVO);
            }
            articleMap.put("imageFileList", imageFileList);

            // 2) DB 저장 (글 + 이미지)
            int articleNO = boardService.addNewArticle(articleMap);

            // 3) temp → 글번호 폴더로 이동
            moveTempFiles(imageFileList, articleNO);
            return script(okMessage, okUrl);
        } catch (Exception e) {
            deleteTempFiles(imageFileList);
            e.printStackTrace();
            return script("오류가 발생했습니다. 다시 시도해 주세요.", failUrl);
        }
    }

    // 업로드된 파일을 temp 폴더에 저장. (input 이름 → 저장한 파일명) 순서 유지. 비어 있는 파일은 건너뜀.
    private Map<String, String> upload(MultipartHttpServletRequest multipartRequest) throws Exception {
        Map<String, String> result = new LinkedHashMap<String, String>();
        File tempDir = new File(ARTICLE_IMAGE_REPO, "temp");

        Iterator<String> fileNames = multipartRequest.getFileNames();
        while (fileNames.hasNext()) {
            String fieldName = fileNames.next();
            MultipartFile mFile = multipartRequest.getFile(fieldName);
            if (mFile == null || mFile.getSize() == 0) {
                continue;
            }
            // IE 는 전체 경로를 보내므로 파일 이름 부분만 사용 (경로 조작 방지도 겸함)
            String originalFileName = FilenameUtils.getName(mFile.getOriginalFilename());
            if (originalFileName.length() == 0) {
                continue;
            }
            tempDir.mkdirs();
            mFile.transferTo(new File(tempDir, originalFileName));
            result.put(fieldName, originalFileName);
        }
        return result;
    }

    private void moveTempFiles(List<ImageVO> imageFileList, int articleNO) throws IOException {
        File tempDir = new File(ARTICLE_IMAGE_REPO, "temp");
        File destDir = new File(ARTICLE_IMAGE_REPO, String.valueOf(articleNO));
        for (ImageVO imageVO : imageFileList) {
            File srcFile = new File(tempDir, imageVO.getImageFileName());
            File destFile = new File(destDir, imageVO.getImageFileName());
            if (destFile.exists()) {
                destFile.delete();      // 같은 이름이 있으면 덮어쓰기
            }
            FileUtils.moveFileToDirectory(srcFile, destDir, true);
        }
    }

    private void deleteTempFiles(List<ImageVO> imageFileList) {
        File tempDir = new File(ARTICLE_IMAGE_REPO, "temp");
        for (ImageVO imageVO : imageFileList) {
            if (imageVO.getImageFileName() != null) {
                new File(tempDir, imageVO.getImageFileName()).delete();
            }
        }
    }

    // 폼 파라미터를 Map 으로 (파일 제외)
    private Map readParameters(MultipartHttpServletRequest multipartRequest) {
        Map articleMap = new HashMap();
        Enumeration enu = multipartRequest.getParameterNames();
        while (enu.hasMoreElements()) {
            String name = (String) enu.nextElement();
            articleMap.put(name, multipartRequest.getParameter(name));
        }
        return articleMap;
    }

    private MemberVO getLoginMember(HttpServletRequest request) {
        return (MemberVO) request.getSession().getAttribute("member");
    }

    // 로그인한 회원이 이 글의 작성자인가?
    private boolean isWriter(MemberVO member, int articleNO) throws Exception {
        if (member == null) {
            return false;
        }
        ArticleVO article = (ArticleVO) boardService.viewArticle(articleNO).get("article");
        return article != null && member.getId().equals(article.getId());
    }

    // alert 를 띄우고 다른 주소로 이동시키는 응답
    private ResponseEntity script(String message, String url) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "text/html; charset=utf-8");
        String body = "<script>"
                + " alert('" + message + "');"
                + " location.href='" + url + "';"
                + " </script>";
        return new ResponseEntity(body, responseHeaders, HttpStatus.OK);
    }
}
