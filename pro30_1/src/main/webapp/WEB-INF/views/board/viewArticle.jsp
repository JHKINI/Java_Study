<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="article" value="${articleMap.article}" />
<c:set var="imageFileList" value="${articleMap.imageFileList}" />
<%
  request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글보기</title>
<style>
  /* 수정하기를 누르기 전에는 숨겨 두는 줄들 */
  .edit-only { display: none; }
  .label { width: 150px; text-align: center; background-color: #FF9933; }
  img.article-img { max-width: 300px; max-height: 300px; }
</style>
<script type="text/javascript">
  function backToList(obj) {
    obj.action = "${contextPath}/board/listArticles.do";
    obj.submit();
  }

  // "수정하기" 클릭: 입력칸을 풀고, 숨겨둔 줄(.edit-only)을 보여준다
  function fn_enable() {
    document.getElementById("i_title").disabled = false;
    document.getElementById("i_content").disabled = false;
    var rows = document.getElementsByClassName("edit-only");
    for (var i = 0; i < rows.length; i++) {
      rows[i].style.display = "table-row";
    }
    document.getElementById("tr_btn").style.display = "none";
  }

  function fn_modify_article(obj) {
    obj.action = "${contextPath}/board/modArticle.do";
    obj.submit();
  }

  // 삭제 / 답글쓰기는 POST 로 보내야 해서 폼을 만들어 전송
  function postTo(url, name, value) {
    var form = document.createElement("form");
    form.setAttribute("method", "post");
    form.setAttribute("action", url);
    var input = document.createElement("input");
    input.setAttribute("type", "hidden");
    input.setAttribute("name", name);
    input.setAttribute("value", value);
    form.appendChild(input);
    document.body.appendChild(form);
    form.submit();
  }

  function fn_remove_article(url, articleNO) {
    if (confirm("정말 삭제할까요? (답글도 함께 삭제됩니다)")) {
      postTo(url, "articleNO", articleNO);
    }
  }

  function fn_reply_form(url, parentNO) {
    postTo(url, "parentNO", parentNO);
  }

  // 새 이미지 추가칸 늘리기
  var cnt = 1;
  function fn_addFile() {
    var div = document.getElementById("d_file");
    div.appendChild(document.createElement("br"));
    var input = document.createElement("input");
    input.type = "file";
    input.name = "addFile" + cnt;
    div.appendChild(input);
    cnt++;
  }
</script>
</head>
<body>
<form name="frmArticle" method="post" action="${contextPath}/board/modArticle.do" enctype="multipart/form-data">
  <table border="0" align="center">
    <tr>
      <td class="label">글번호</td>
      <td>
        <input type="text" value="${article.articleNO}" disabled />
        <input type="hidden" name="articleNO" value="${article.articleNO}" />
      </td>
    </tr>
    <tr>
      <td class="label">작성자 아이디</td>
      <td><input type="text" value="<c:out value='${article.id}'/>" disabled /></td>
    </tr>
    <tr>
      <td class="label">제목</td>
      <td><input type="text" size="60" value="<c:out value='${article.title}'/>" name="title" id="i_title" disabled /></td>
    </tr>
    <tr>
      <td class="label">내용</td>
      <td><textarea rows="20" cols="60" name="content" id="i_content" disabled><c:out value="${article.content}"/></textarea></td>
    </tr>

    <%-- 첨부된 이미지: 이미지마다 한 줄, 수정 모드에서는 교체용 파일칸이 나타난다 --%>
    <c:forEach var="item" items="${imageFileList}" varStatus="status">
      <tr>
        <td class="label">이미지${status.count}</td>
        <td>
          <input type="hidden" name="originalFileName_${item.imageFileNO}" value="<c:out value='${item.imageFileName}'/>" />
          <c:url var="imgUrl" value="/download.do">
            <c:param name="articleNO" value="${article.articleNO}" />
            <c:param name="imageFileName" value="${item.imageFileName}" />
          </c:url>
          <img class="article-img" alt="<c:out value='${item.imageFileName}'/>" src="${imgUrl}" /><br>
          <c:out value="${item.imageFileName}" />
          <span class="edit-only">
            <br>바꿀 이미지: <input type="file" name="replace_${item.imageFileNO}" />
          </span>
        </td>
      </tr>
    </c:forEach>
    <c:if test="${empty imageFileList}">
      <tr>
        <td class="label">이미지</td>
        <td>첨부된 이미지가 없습니다.</td>
      </tr>
    </c:if>

    <tr class="edit-only">
      <td class="label">이미지 추가</td>
      <td>
        <input type="file" name="addFile0" />
        <input type="button" value="파일 추가" onClick="fn_addFile()" />
        <div id="d_file"></div>
      </td>
    </tr>

    <tr>
      <td class="label">등록일자</td>
      <td><input type="text" value="<fmt:formatDate value="${article.writeDate}" />" disabled /></td>
    </tr>

    <tr class="edit-only" align="center">
      <td colspan="2">
        <input type="button" value="수정반영하기" onClick="fn_modify_article(this.form)" />
        <input type="button" value="취소" onClick="backToList(this.form)" />
      </td>
    </tr>

    <tr id="tr_btn">
      <td colspan="2" align="center">
        <c:if test="${member.id == article.id}">
          <input type="button" value="수정하기" onClick="fn_enable()" />
          <input type="button" value="삭제하기"
                 onClick="fn_remove_article('${contextPath}/board/removeArticle.do', ${article.articleNO})" />
        </c:if>
        <input type="button" value="리스트로 돌아가기" onClick="backToList(this.form)" />
        <input type="button" value="답글쓰기"
               onClick="fn_reply_form('${contextPath}/board/replyForm.do', ${article.articleNO})" />
      </td>
    </tr>
  </table>
</form>
</body>
</html>
