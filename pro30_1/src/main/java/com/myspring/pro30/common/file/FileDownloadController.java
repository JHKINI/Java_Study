
package com.myspring.pro30.common.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLConnection;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FileDownloadController {

    // 글에 첨부된 이미지가 저장된 최상위 폴더
    private static final String ARTICLE_IMAGE_REPO =
            "C:\\board\\article_image";

    // 글 상세창의 이미지 요청 처리
    @RequestMapping("/download.do")
    public void download(
            @RequestParam("imageFileName") String imageFileName,
            @RequestParam("articleNO") String articleNO,
            HttpServletResponse response) throws Exception {

        // 글 번호는 숫자만 허용
        if (!articleNO.matches("\\d+")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // 파일 이름에서 경로 제거
        String safeFileName = new File(imageFileName).getName();

        // 글 번호 폴더 + 파일 이름으로 경로 설정
        File file = new File(
                ARTICLE_IMAGE_REPO + File.separator
                        + articleNO + File.separator
                        + safeFileName
        );

        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 파일 종류에 맞는 Content-Type 설정
        String contentType =
                URLConnection.guessContentTypeFromName(safeFileName);

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        response.setContentType(contentType);
        response.setHeader("Cache-Control", "no-cache");

        OutputStream out = response.getOutputStream();
        FileInputStream in = new FileInputStream(file);

        byte[] buffer = new byte[1024 * 8];

        try {
            while (true) {
                int count = in.read(buffer);

                if (count == -1) {
                    break;
                }

                out.write(buffer, 0, count);
            }
        } finally {
            in.close();
            out.close();
        }
    }
}
