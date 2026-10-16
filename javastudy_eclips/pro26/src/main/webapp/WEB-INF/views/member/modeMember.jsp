<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false"%>

<%@ taglib prefix="fmt"
    uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath"
    value="${pageContext.request.contextPath}" />

<%
    request.setCharacterEncoding("UTF-8");
%>

<!DOCTYPE html>

<html>
<head>
    <meta charset="UTF-8">
    <title>회원 정보 수정</title>
</head>

<body>

    <form method="post"
          action="${contextPath}/mem4.do?action=updateMember">

        <h1 style="text-align:center;">회원 정보 수정</h1>

        <table align="center">

            <tr>
                <td width="200px">
                    <p align="right">아이디</p>
                </td>
                <td width="400px">
                    <input type="text" name="id">
                </td>
            </tr>

            <tr>
                <td width="200px">
                    <p align="right">비밀번호</p>
                </td>
                <td width="400px">
                    <input type="password" name="pwd">
                </td>
            </tr>

            <tr>
                <td width="200px">
                    <p align="right">이름</p>
                </td>
                <td width="400px">
                    <input type="text" name="name">
                </td>
            </tr>

            <tr>
                <td width="200px">
                    <p align="right">이메일</p>
                </td>
                <td width="400px">
                    <input type="text" name="email">
                </td>
            </tr>

            <tr>
                <td width="200px">
                    <p>&nbsp;</p>
                </td>
                <td width="400px">
                    <input type="submit" value="수정하기">
                    <input type="reset" value="다시입력">
                </td>
            </tr>

        </table>

    </form>

</body>
</html>