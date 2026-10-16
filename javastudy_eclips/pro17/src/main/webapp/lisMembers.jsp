<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fmt"
    uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 출력창</title>

<style>

.cls1{
    font-size:40px;
    text-align:center;
}

.cls2{
    font-size:20px;
    text-align:center;
}

table{
    margin:auto;
}

</style>

</head>
<body>

<p class="cls1">회원정보</p>

<table border="1" align="center">

<tr bgcolor="lightgreen">
    <td>아이디</td>
    <td>비밀번호</td>
    <td>이름</td>
    <td>이메일</td>
    <td>가입일</td>
</tr>

<c:choose>

    <c:when test="${membersList==null}">
        <tr>
            <td colspan="5">
                <b>등록된 회원이 없습니다.</b>
            </td>
        </tr>
    </c:when>

    <c:when test="${membersList != null}">

        <c:forEach var="mem"
                   items="${membersList}">

            <tr align="center">
                <td>${mem.id}</td>
                <td>${mem.pwd}</td>
                <td>${mem.name}</td>
                <td>${mem.email}</td>
                <td>${mem.joinDate}</td>
            </tr>

        </c:forEach>

    </c:when>

</c:choose>

</table>

<a href="#">
    <p class="cls2">회원 가입하기</p>
</a>

</body>
</html>