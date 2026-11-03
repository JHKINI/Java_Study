<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% request.setCharacterEncoding("UTF-8"); %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<style>
    .cls1 { text-decoration: none; }
    .cls2 { text-align: center; font-size: 30px; }
</style>

<script>
    // 글쓰기 클릭 시: 로그인 상태면 글쓰기창으로, 아니면 로그인창으로 이동
    //  - 로그인창으로 갈 때 action 값으로 "로그인 후 이동할 주소"를 같이 전달
    function fn_articleForm(isLogOn, articleForm, loginForm) {
        if (isLogOn != '' && isLogOn != 'false') {
            location.href = articleForm;
        } else {
            alert("로그인 후 글쓰기가 가능합니다.");
            location.href = loginForm + '?action=/board/articleForm.do';
        }
    }
</script>

<table border="1" width="100%" align="center">
    <tr align="center" bgcolor="#99FF99">
        <td width="7%">글번호</td>
        <td width="15%">작성자</td>
        <td>제목</td>
        <td width="15%">작성일</td>
    </tr>

    <c:choose>
        <c:when test="${empty articlesList}">
            <tr>
                <td colspan="4" align="center">등록된 글이 없습니다.</td>
            </tr>
        </c:when>
        <c:otherwise>
            <c:forEach var="article" items="${articlesList}" varStatus="articleNum">
                <tr align="center">
                    <td>${articleNum.count}</td>
                    <td>${article.id}</td>
                    <td align="left">
                        <span style="padding-right:30px"></span>
                        <c:choose>
                            <%-- level 이 1보다 크면 답글: 들여쓰기 + [답변] 표시 --%>
                            <c:when test="${article.level > 1}">
                                <c:forEach begin="1" end="${article.level}" step="1">
                                    <span style="padding-left:20px"></span>
                                </c:forEach>
                                <span style="font-size:12px;">[답변]</span>
                                <a class="cls1"
                                   href="${contextPath}/board/viewArticle.do?articleNO=${article.articleNO}">${fn:escapeXml(article.title)}</a>
                            </c:when>
                            <%-- level 이 1이면 부모 글 --%>
                            <c:otherwise>
                                <a class="cls1"
                                   href="${contextPath}/board/viewArticle.do?articleNO=${article.articleNO}">${fn:escapeXml(article.title)}</a>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <fmt:formatDate value="${article.writeDate}" pattern="yyyy-MM-dd"/>
                    </td>
                </tr>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</table>

<br>

<a class="cls1"
   href="javascript:fn_articleForm('${isLogOn}','${contextPath}/board/articleForm.do','${contextPath}/member/loginForm.do')">
    <p class="cls2">글쓰기</p>
</a>
