<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%request.setCharacterEncoding("UTF-8");%>

<c:set var="id" value="hong" scope="page" />
<c:set var="pwd" value="1234" scope="page" />
<c:set var="name" value="${'홍길동'}" scope="page"/>
<c:set var="age" value="${22 }" scope="page" />
<c:set var="height" value="${177}" scope="page" />
<c:remove var="age" />
<c:remove var="height" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 출력창</title>
</head>
<body>
<table border="1" align="center">
    <tr align="center" bgcolor="lightblue">
        <td>아이디</td>
        <td>비밀번호</td>
        <td>이름</td>
        <td>이메일</td>
    </tr>

    <tr align="center">
        <td>${membersMap.id}</td>
        <td>${membersMap.pwd}</td>
        <td>${membersMap.name}</td>
        <td>${membersMap.email}</td>
    </tr>

    <tr align="center">
        <td>${membersList[0].id}</td>
        <td>${membersList[0].pwd}</td>
        <td>${membersList[0].name}</td>
        <td>${membersList[0].email}</td>
    </tr>

    <tr align="center">
        <td>${membersList[1].id}</td>
        <td>${membersList[1].pwd}</td>
        <td>${membersList[1].name}</td>
        <td>${membersList[1].email}</td>
    </tr>
<tr align="center">
<td>${id}</td>
<td>${pwd}</td>
<td>${name}</td>
<td>${age}</td>
<td>${height}</td>
</tr>
</table>
</body>
</html>