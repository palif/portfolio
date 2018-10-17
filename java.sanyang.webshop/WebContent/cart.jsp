<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"
    import="java.util.*, ui.*"
%>
    
<% Cart cart = (Cart) session.getAttribute("cart"); %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="reset.css" />
<link rel="stylesheet" type="text/css" href="style.css" />
<title> Cart </title>
</head>
<body>

	<header>
		<div id="page_title"> Cart </div>
		<div id="menu"> 
			<div id="cart"> <a href="${pageContext.request.contextPath}/WebshopServlet?action=cart"> Carter: <% out.println(cart.getAllProducts().size()); %> </a> </div>
		</div>
	</header>
	
	<% if (session.getAttribute("error") != null) { %> <h2> <% out.println(session.getAttribute("error")); %> </h2> <% } %>
	
	<div id="container_item">
		<% for(Hashtable c : cart.getAllProducts()) { %>
			<form class="item_container" action="${pageContext.request.contextPath}/WebshopServlet" method="post">
				<label class="item_title" > <% out.println(c.get("title")); %> </label>
				<label class="item_price"> <% out.println(c.get("price")); %> </label>
				<button class="item_button" type="submit" name="action" value="REMOVE <% out.println(c.get("id"));%>"> Remove </button>
			</form>
		<% } %>
		
		<form action="${pageContext.request.contextPath}/WebshopServlet" method="post" id="container_checkout">
		<% if (cart.getAllProducts().size() > 0) { %><button type="submit" id="checkout"  name="action" value="CHECKOUT"> checkout </button><% } %>
		</form>
		
	</div>
	
	<footer>
		<nav>
			<div> 
		 		<form action="${pageContext.request.contextPath}/LogoutServlet" method="post">
		 			<input type="submit" value="logout"> 
		 		</form> 
			 </div>
			<div> <a href="#"> <% out.println(session.getAttribute("username")); %> </a></div>
		</nav>
	</footer>
	
</body>
</html>