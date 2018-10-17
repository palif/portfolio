<%@ page language="java" contentType="text/html; charset=UTF-8" session="true" pageEncoding="UTF-8" import="java.util.*,ui.*"%>
<% 
	ArrayList<Hashtable> searchResult = (ArrayList<Hashtable>) session.getAttribute("searchResult"); 
	if ( session.getAttribute("username") == null) response.sendRedirect("login.jsp");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Stock</title>
</head>
<body>

<header> 
	<h1>Stock</h1>
</header>


<div>
	<h3> Search by Id </h3>
	<form action="${pageContext.request.contextPath}/StockPersonnelServlet" method="post">
		<input type="text" name="action" placeholder="search product by id here" >
		<button type="submit"> search </button>
	</form>
	<% 
	if( searchResult.size() > 0) { 
		for(Hashtable p : searchResult) {
	%>
	<form action="${pageContext.request.contextPath}/StockPersonnelServlet" method="post">
		<label class="item_title"> <% out.println(p.get("title")); %> </label>
		<label class="item_price"> <% out.println(p.get("price")); %> :- </label>
		<label class="item_quantity"> <% out.println(p.get("quantity")); %> left </label>
		<button type="submit" name="action" value="DELETE <% out.println(p.get("id"));%>"> DELETE </button>
	</form>
	<% 
		} 
	}
	%>
</div>

<div>
	<h3> Add product </h3>
	<form action="${pageContext.request.contextPath}/StockPersonnelServlet" method="post">
		<label>product Id: </label>
		<input type="text" name="productId"> 
		<label>title: </label>
		<input type="text" name="title"> 
		<label>price: </label>
		<input type="text" name="price"> 
		<label>quantity: </label>
		<input type="text" name="quantity">
		<button type="submit" name="action" value="ADD"> Add product</button> 
	</form>
</div>

<footer>
	<nav>
		<div> 
	 		<form action="${pageContext.request.contextPath}/LogoutServlet" method="post">
	 			<input type="submit" value="logout"> 
	 		</form> 
		 </div>
		<div style="color:white;" > <% out.println(session.getAttribute("username")); %> </div>
	</nav>
</footer>

</body>
</html>