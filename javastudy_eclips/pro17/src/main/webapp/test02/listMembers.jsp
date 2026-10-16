<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 목록</title>

<style>
table {
    border-collapse: collapse;
    width: 800px;
}

th, td {
    border: 1px solid green;
    text-align: center;
    padding: 5px;
}

th {
    background-color: #98FB98;
}
</style>

</head>
<body>

<h1 align="center">회원정보</h1>

<table align="center">
    <tr>
        <th>아이디</th>
        <th>비밀번호</th>
        <th>이름</th>
        <th>이메일</th>
        <th>가입일</th>
    </tr>

    <c:choose>

        <c:when test="${empty membersList}">
            <tr>
                <td colspan="5">
                    등록된 회원이 없습니다.
                </td>
            </tr>
        </c:when>

        <c:when test="${not empty membersList}">
            <c:forEach var="member" items="${membersList}">
                <tr>
                    <td>${member.id}</td>
                    <td>${member.pwd}</td>
                    <td>${member.name}</td>
                    <td>${member.email}</td>
                    <td>${member.joinDate}</td>
                </tr>
            </c:forEach>
        </c:when>

    </c:choose>

</table>

<br><br>

<div align="center">
    <a href="${contextPath}/member/memberForm.do">
        회원 가입하기
    </a>
</div>

</body>
</html>