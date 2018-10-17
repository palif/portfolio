<%@ page session="true" import="java.util.*, ui.*"
%>
<%	
	Cart cart = (Cart) session.getAttribute("cart");
	System.out.println("Cart size: " + cart.getAllProducts().size());
%>

<%!
public int getQuantityFromCart(Cart cart, String productId) {
	if(cart.getAllProducts().isEmpty()) return 0;
	int quantity = 0;
	for(int i = 0; i < cart.getAllProducts().size(); i++){
		if(productId.equals((String) cart.getAllProducts().get(i).get("id"))){
			quantity++;
		}
	}
	
	return quantity;
}
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="reset.css" />
<link rel="stylesheet" type="text/css" href="style.css" />
<title> KTH e-commerce </title>
</head>
<body>
	<header>
		<div id="page_title"> KTH e-commerce </div>
		<div id="menu"> 
			<div id="cart"> <a href="${pageContext.request.contextPath}/WebshopServlet?action=cart"> Carter: <% out.println(cart.getAllProducts().size()); %> </a> </div>
		</div>
	</header>
	
	<div id="container_item">
		<% for(Hashtable product : (ArrayList<Hashtable>) session.getAttribute("allProduct") ) { 
				System.out.println("-----1----");
		%>
		<form action="${pageContext.request.contextPath}/WebshopServlet" method="post" class="item_container">
			<label class="item_title"> <% out.println(product.get("title")); %> </label>
			<label class="item_price"> <% out.println(product.get("price")); %> :- </label>
			<label class="item_quantity"> <% out.println(product.get("quantity")); %> left </label>
			<% if((int) product.get("quantity") <= 0) { %>
			<button class="item_button" type="button"> Out of stock </button>
			<% } else if(getQuantityFromCart(cart, (String) product.get("id")) == (int) product.get("quantity")) { %>
			<button class="item_button" type="button"> Cannot add </button>
			<% } else if((int)product.get("quantity") != 0) { %>
			<button class="item_button" type="submit" name="action" value="<% out.println(product.get("id"));%>"> Add to carter </button>
			<% } %>
		</form>
		<% } %>
	</div>
	
	<footer>
		<nav>
			<div> 
		 		<form action="${pageContext.request.contextPath}/LogoutServlet" method="post">
		 			<input type="submit" value="logout"> 
		 		</form> 
			 </div>
			<div style="color:white;"> <%= session.getAttribute("username") %> </div>
		</nav>
	</footer>
</body>
</html>