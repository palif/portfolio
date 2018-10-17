<%@ page language="java" contentType="text/html; charset=UTF-8" session="true" pageEncoding="UTF-8" import="java.util.*"%>
<% ArrayList<Hashtable> transactions = (ArrayList<Hashtable>) session.getAttribute("allTransaction"); %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin</title>
</head>
<body>
	<header>
		<div><h1>ADMIN SITE</h1></div>
	</header>
	<div>
		<div>
			<h3><b>Transaction history:</b></h3>
		</div>
		<% for(Hashtable s : transactions) { %>
			<div> <% out.println(s); %> </div>
		<% } %>
	</div>
	
	<footer>
		<nav>
			<div> 
		 		<form action="${pageContext.request.contextPath}/LogoutServlet" method="post">
		 			<input type="submit" value="logout"> 
		 		</form> 
			 </div>
			<div style="color:white;"> <% out.println(session.getAttribute("username")); %> </div>
		</nav>
	</footer>
	
</body>
</html>