<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="bl.Handler" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="reset.css" />
<link rel="stylesheet" type="text/css" href="style.css" />
<title>Login to E-commerce/Web </title>
</head>
<body>
	<div>${message}</div>
	<div class="login_title"> Login here </div>
	<form action="${pageContext.request.contextPath}/LoginServlet" method="post" class="login_form">
		<div class="login_user"> User </div>
		<input type="text" name="user" style="margin-bottom: 15px;" class="login_input"></input>
		<div class="login_password"> Password </div>
		<input type="password" name="password" style="margin-bottom: 15px;" class="login_input"></input> <br>
		<button type="submit" class="login_button"> Login </button>
	</form>
	<form action="${pageContext.request.contextPath}/SignupServlet" method="get" class="login_form">
		<button type="submit" class="login_button"> Sign up </button>
	</form>
	
	
</body>
</html>