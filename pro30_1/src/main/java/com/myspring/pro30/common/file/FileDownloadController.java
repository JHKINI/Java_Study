package com.myspring.pro30.common.file;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.net.URLEncoder;

@Controller
public class FileDownloadController {
    // 이미지 저장 폴더. 다른 경로를 쓰려면 톰캣 실행 옵션에 -Dboard.repo=D:\board 처럼 지정
    public static final String ARTICLE_IMAGE_REPO =
            System.getProperty("board.repo", "c:\\board\\article_imagefile");

    @RequestMapping("/download.do")
    protected void download(@RequestParam("imageFileName") String imageFileName,
                            @RequestParam("articleNO") String articleNO,
                            HttpServletResponse response) throws Exception {
        // 경로 조작(../) 방지: 글번호는 숫자만, 파일명은 이름 부분만 사용
        if (!articleNO.matches("\\d+")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String safeName = FilenameUtils.getName(imageFileName);
        File file = new File(new File(ARTICLE_IMAGE_REPO, articleNO), safeName);
        if (!file.isFile()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String contentType = URLConnection.guessContentTypeFromName(safeName);
        response.setContentType(contentType != null ? contentType : "application/octet-stream");
        response.setHeader("Cache-Control", "no-cache");
        // 한글 파일명이 깨지지 않도록 인코딩
        String encoded = URLEncoder.encode(safeName, "UTF-8").replace("+", "%20");
        response.addHeader("Content-Disposition", "inline; filename*=UTF-8''" + encoded);

        OutputStream out = response.getOutputStream();
        FileInputStream in = new FileInputStream(file);
        try {
            byte[] buffer = new byte[1024 * 8];
            while (true) {
                int count = in.read(buffer);
                if (count == -1) break;
                out.write(buffer, 0, count);
            }
        } finally {
            in.close();
            out.close();
        }
    }
}
