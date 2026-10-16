<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% request.setCharacterEncoding("UTF-8"); %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<script>
    // 선택한 이미지를 글쓰기창에서 미리 보여줍니다.
    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                var img = document.getElementById('preview');
                img.src = e.target.result;
                img.style.display = 'block';
            };
            reader.readAsDataURL(input.files[0]);
        }
    }

    function backToList() {
        location.href = "${contextPath}/board/listArticles.do";
    }
</script>

<h1 style="text-align:center">글쓰기</h1>

<%-- 이미지 파일을 같이 전송하므로 enctype 은 반드시 multipart/form-data --%>
<form name="articleForm" method="post"
      action="${contextPath}/board/addNewArticle.do"
      enctype="multipart/form-data">
    <table border="0" align="center">
        <tr>
            <td align="right">작성자</td>
            <td colspan="2" align="left">
                <%-- 로그인한 회원의 이름 표시 (세션의 member) --%>
                <input type="text" size="20" maxlength="100"
                       value="${fn:escapeXml(member.name)}" readonly />
            </td>
        </tr>
        <tr>
            <td align="right">글제목:</td>
            <td colspan="2"><input type="text" size="67" maxlength="500" name="title" /></td>
        </tr>
        <tr>
            <td align="right" valign="top"><br>글내용:</td>
            <td colspan="2"><textarea name="content" rows="10" cols="65" maxlength="4000"></textarea></td>
        </tr>
        <tr>
            <td align="right">이미지파일 첨부:</td>
            <td>
                <input type="file" name="imageFileName" onchange="readURL(this);" />
            </td>
            <td><img id="preview" width="200" height="200" style="display:none" /></td>
        </tr>
        <tr>
            <td align="right">&nbsp;</td>
            <td colspan="2">
                <input type="submit" value="글쓰기" />
                <input type="button" value="목록보기" onClick="backToList()" />
            </td>
        </tr>
    </table>
</form>
