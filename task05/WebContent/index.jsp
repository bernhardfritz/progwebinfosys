<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, java.io.*, abc.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TestProject</title>
</head>
<body>
	<%
// 		Test t = new Test();
// 		out.println(t.test());
		DBManager dbManager = DBManager.getInstance();
// 		dbManager.registerUser("abc", "def");
		out.println(dbManager.findUserById(1L).getUsername());
// 		dbManager.disconnect();
	%>
</body>
</html>