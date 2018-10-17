<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> Signup in PHeShop </title>
</head>
<body>
	<header>
		<h1>Sign Up </h1>
	</header>
	
	<h3> Sign up </h3>
	<form action="${pageContext.request.contextPath}/SignupServlet" method="post">
		<label> User-name: </label>
		<input type="text" name="username"/> <br>
		<label> Password: </label>
		<input type="password" name="password"/> <br>
		<button type="submit" name="action"> SIGN UP </button>
	</form>
</body>
</html>