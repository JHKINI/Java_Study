<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="sec01.ex01.MemberBean" %>
<%

request.setCharacterEncoding("utf-8");
String id = request.getParameter("id");
String pwd = request.getParameter("pwd");
String name = request.getParameter("name");
String email = request.getParameter("email");
MemberBean member = new MemberBean(id, pwd, name, email);
//request.setAttribute("member", member);
%>
    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>forward</title>
</head>
<body>
 <jsp:forward page="member5.jsp" />
</body>
</html>