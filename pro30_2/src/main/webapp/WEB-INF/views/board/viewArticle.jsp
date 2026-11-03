<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    request.setCharacterEncoding("UTF-8");
%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<style>
    .article-table {
        width: 470px;
        border: 1px solid #555;
        border-collapse: separate;
        margin: 0 auto;
        table-layout: fixed;
    }

    .article-table td {
        border: 1px solid white;
        padding: 3px;
        font-size: 12px;
    }

    .article-label {
        width: 110px;
        background-color: #FF9933;
        text-align: center;
        vertical-align: middle;
    }

    .article-value {
        background-color: white;
        width: 350px;
    }

    .article-input {
        width: 125px;
        box-sizing: border-box;
        background-color: #EAE9E3;
        border: 1px solid #CCC;
        font-size: 11px;
    }

    .article-title {
        width: 190px;
    }

    .article-content {
        width: 320px;
        height: 225px;
        box-sizing: border-box;
        resize: both;
        background-color: #EAE9E3;
        border: 1px solid #CCC;
        font-size: 11px;
    }

    .article-image-area {
        height: 205px;
        text-align: center;
        vertical-align: middle;
    }

    #preview {
        max-width: 180px;
        max-height: 150px;
        object-fit: contain;
    }

    .article-date {
        width: 125px;
        background-color: #EAE9E3;
        border: 1px solid #CCC;
        font-size: 11px;
        text-align: center;
    }

    .article-buttons {
        text-align: center;
        padding: 5px;
    }
</style>

<script>
    // 글목록으로 돌아가기
    function backToList() {
        location.href = "${contextPath}/board/listArticles.do";
    }

    // 수정할 수 있도록 입력창 활성화
    function fn_enable() {
        document.getElementById("i_title").disabled = false;
        document.getElementById("i_content").disabled = false;
        document.getElementById("i_imageFileName").disabled = false;

        document.getElementById("tr_btn_modify").style.display = "table-row";
        document.getElementById("tr_btn").style.display = "none";
    }

    // 수정 내용 전송
    function fn_modify_article(form) {
        form.submit();
    }

    // 이미지 미리보기
    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function(e) {
                var img = document.getElementById("preview");
                img.src = e.target.result;
                img.style.display = "inline-block";
            };

            reader.readAsDataURL(input.files[0]);
        }
    }

    // 글 삭제
    function fn_remove_article(url, articleNO) {
        if (confirm("정말 삭제하시겠습니까?")) {
            location.href = url + "?articleNO=" + articleNO;
        }
    }

    // 답글 작성 화면으로 이동
    function fn_reply_form(url, articleNO) {
        location.href = url + "?parentNO=" + articleNO;
    }
</script>

<form name="frmArticle"
      method="post"
      action="${contextPath}/board/modArticle.do"
      enctype="multipart/form-data">

    <table class="article-table">

        <tr>
            <td class="article-label">글번호</td>
            <td class="article-value">
                <input type="text"
                       class="article-input"
                       value="${article.articleNO}"
                       disabled />
                <input type="hidden"
                       name="articleNO"
                       value="${article.articleNO}" />
            </td>
        </tr>

        <tr>
            <td class="article-label">작성자 아이디</td>
            <td class="article-value">
                <input type="text"
                       class="article-input"
                       value="${fn:escapeXml(article.id)}"
                       disabled />
            </td>
        </tr>

        <tr>
            <td class="article-label">제목</td>
            <td class="article-value">
                <input type="text"
                       class="article-input article-title"
                       name="title"
                       id="i_title"
                       value="${fn:escapeXml(article.title)}"
                       disabled />
            </td>
        </tr>

        <tr>
            <td class="article-label">내용</td>
            <td class="article-value">
                <textarea class="article-content"
                          name="content"
                          id="i_content"
                          disabled>${fn:escapeXml(article.content)}</textarea>
            </td>
        </tr>

        <tr>
            <td class="article-label article-image-area">이미지</td>
            <td class="article-value article-image-area">

                <c:if test="${not empty article.imageFileName
                              and article.imageFileName != 'null'}">

                    <input type="hidden"
                           name="originalFileName"
                           value="${fn:escapeXml(article.imageFileName)}" />

                    <img id="preview"
                         src="${contextPath}/download.do?articleNO=${article.articleNO}&amp;imageFileName=${fn:escapeXml(article.imageFileName)}"
                         alt="첨부 이미지" />

                    <br />
                </c:if>

                <c:if test="${empty article.imageFileName
                              or article.imageFileName == 'null'}">
                    <input type="hidden"
                           name="originalFileName"
                           value="" />

                    <img id="preview"
                         style="display:none"
                         alt="이미지 미리보기" />
                    <br />
                </c:if>

                <input type="file"
                       name="imageFileName"
                       id="i_imageFileName"
                       disabled
                       onchange="readURL(this);" />
            </td>
        </tr>

        <tr>
            <td class="article-label">등록일자</td>
            <td class="article-value">
                <input type="text"
                       class="article-date"
                       value="<fmt:formatDate value='${article.writeDate}'
                                             pattern='yyyy. MM. dd.'/>"
                       disabled />
            </td>
        </tr>

        <!-- 수정하기를 눌렀을 때 표시 -->
        <tr id="tr_btn_modify" style="display:none">
            <td colspan="2" class="article-buttons">
                <input type="button"
                       value="수정반영하기"
                       onclick="fn_modify_article(this.form)" />

                <input type="button"
                       value="취소"
                       onclick="backToList()" />
            </td>
        </tr>

        <!-- 기본 버튼 -->
        <tr id="tr_btn">
            <td colspan="2" class="article-buttons">

                <c:if test="${not empty member and member.id == article.id}">
                    <input type="button"
                           value="수정하기"
                           onclick="fn_enable()" />

                    <input type="button"
                           value="삭제하기"
                           onclick="fn_remove_article(
                                   '${contextPath}/board/removeArticle.do',
                               ${article.articleNO})" />
                </c:if>

                <input type="button"
                       value="리스트로 돌아가기"
                       onclick="backToList()" />

                <input type="button"
                       value="답글쓰기"
                       onclick="fn_reply_form(
                               '${contextPath}/board/replyForm.do',
                       ${article.articleNO})" />
            </td>
        </tr>

    </table>
</form>
