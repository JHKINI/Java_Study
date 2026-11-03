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
<title>글쓰기창</title>
<script type="text/javascript">
  // 선택한 이미지를 미리보기로 보여준다
  function readURL(input) {
    if (input.files && input.files[0]) {
      var reader = new FileReader();
      reader.onload = function (e) {
        document.getElementById('preview').src = e.target.result;
      };
      reader.readAsDataURL(input.files[0]);
    }
  }

  function backToList(obj) {
    obj.action = "${contextPath}/board/listArticles.do";
    obj.submit();
  }

  // "파일 추가" 버튼: 파일 선택칸을 하나씩 늘린다 (name 이 file1, file2 ... 로 겹치지 않게)
  var cnt = 1;
  function fn_addFile() {
    var div = document.getElementById("d_file");
    div.appendChild(document.createElement("br"));
    var input = document.createElement("input");
    input.type = "file";
    input.name = "file" + cnt;
    div.appendChild(input);
    cnt++;
  }
</script>
</head>
<body>
<h1 style="text-align:center">글쓰기</h1>
<form name="articleForm" method="post" action="${contextPath}/board/addNewArticle.do" enctype="multipart/form-data">
  <table border="0" align="center">
    <tr>
      <td align="right">작성자</td>
      <td colspan="2" align="left"><input type="text" size="20" maxlength="100" value="${member.name}" readonly /></td>
    </tr>
    <tr>
      <td align="right">글제목: </td>
      <td colspan="2"><input type="text" size="67" maxlength="500" name="title" required /></td>
    </tr>
    <tr>
      <td align="right" valign="top"><br>글내용: </td>
      <td colspan="2"><textarea name="content" rows="10" cols="65" maxlength="4000"></textarea></td>
    </tr>
    <tr>
      <td align="right">이미지파일 첨부: </td>
      <td><input type="file" name="imageFileName" onchange="readURL(this);" /></td>
      <td><img id="preview" src="${contextPath}/resources/image/duke.png" width="200" height="200" alt="미리보기" /></td>
    </tr>
    <tr>
      <td align="right">이미지파일 더 첨부</td>
      <td align="left"><input type="button" value="파일 추가" onClick="fn_addFile()" /></td>
      <td></td>
    </tr>
    <tr>
      <td colspan="3"><div id="d_file"></div></td>
    </tr>
    <tr>
      <td align="right"></td>
      <td colspan="2">
        <input type="submit" value="글쓰기" />
        <input type="button" value="목록보기" onClick="backToList(this.form)" />
      </td>
    </tr>
  </table>
</form>
</body>
</html>
