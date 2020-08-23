<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin Online Shopping</title>
</head>
<body>

<%
	if(session.getAttribute("username")==null) {
		response.sendRedirect("login.jsp");
	}
%>

<a href="admin?action=list">List Products</a>

	<h1>Add New Product</h1>
	<form action="admin?action=insertproduct" method="post">
		<table>
			<tr>
				<td> Name:</td>
				<td><input type="text" name="pname"></td>
			</tr>
			<tr>
				<td> Price:</td>
				<td><input type="text" name="pcost"></td>
			</tr>
			<tr>
				<td> Description:</td>
				<td><input type="text" name="pdesc"></td>
			</tr>
			<!-- <tr>
				<td> Quantity:</td>
				<td><input type="text" name="Pquantity"></td>
			</tr> -->
		</table>
		<input type="submit" value="Add">
	</form>
</body>
</html>