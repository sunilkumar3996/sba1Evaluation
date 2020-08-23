<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.iiht.evaluation.coronokit.model.ProductMaster"%>
<!DOCTYPE html>
<html>
<head>
<title>Admin - Online Shopping</title>
</head>
<body>
<!-- <h1>Admin Online Shopping</h1>
<ul>
	<li> <a href="index.jsp"><span>Home</span></a> </li>
	<li> <a href="login.jsp"><span>Login</span></a> </li>
</ul> -->

<%
	if(session.getAttribute("username")==null) {
		response.sendRedirect("index.jsp");
	}
%>

	
<br/>
	<a href="admin?action=list">List Products</a>
<br/>
	
<div align="center">
	<h1>Update Product</h1>
	<%
		// fetch the shared data
		ProductMaster product =  (ProductMaster) request.getAttribute("product");
	%>
<form action="admin?action=updateproduct" method="post">
	<table border="1" width="100%">
		<thead>
			<th>Name</th>
			<th>Cost</th>
			<th>Description</th>
			<th></th>
			<th></th>
		</thead>
		<tbody>
			<tr>
				<td><input type="hidden" name="id" value="<%=product.getId()%>">
				<input type="text" name="name" value="<%=product.getProductName()%>"></td>
				<td><input type="text" name="cost" value="<%=product.getCost()%>"></td>
				<td><input type="text" name="description" value="<%=product.getProductDescription()%>"></td>
			</tr>			
		</tbody>
	</table>
	<input type="submit" value="Update">
</form>
</div>
<hr/> 
</body>
</html>