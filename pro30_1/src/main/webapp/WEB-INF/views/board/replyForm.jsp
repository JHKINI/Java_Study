<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%
  request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>답글쓰기 페이지</title>
<script type="text/javascript">
  function backToList(obj) {
    obj.action = "${contextPath}/board/listArticles.do";
    obj.submit();
  }

  function readURL(input) {
    if (input.files && input.files[0]) {
      var reader = new FileReader();
      reader.onload = function (e) {
        document.getElementById('preview').src = e.target.result;
      };
      reader.readAsDataURL(input.files[0]);
    }
  }
</script>
</head>
<body>
<h1 style="text-align:center">답글쓰기</h1>
<form name="frmReply" method="post" action="${contextPath}/board/addReply.do" enctype="multipart/form-data">
  <table align="center">
    <tr>
      <td align="right">작성자:&nbsp;</td>
      <td><input type="text" size="20" maxlength="100" value="${member.name}" readonly /></td>
    </tr>
    <tr>
      <td align="right">제목:&nbsp;</td>
      <td><input type="text" size="67" maxlength="500" name="title" required /></td>
    </tr>
    <tr>
      <td align="right" valign="top"><br>내용:&nbsp;</td>
      <td><textarea name="content" rows="10" cols="65" maxlength="4000"></textarea></td>
    </tr>
    <tr>
      <td align="right">이미지파일 첨부:&nbsp;</td>
      <td>
        <input type="file" name="imageFileName" onchange="readURL(this);" />
        <img id="preview" src="${contextPath}/resources/image/duke.png" width="100" height="100" alt="미리보기" />
      </td>
    </tr>
    <tr>
      <td align="right"></td>
      <td>
        <input type="submit" value="답글쓰기" />
        <input type="button" value="취소" onClick="backToList(this.form)" />
      </td>
    </tr>
  </table>
</form>
</body>
</html>
