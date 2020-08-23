<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Online Shopping - HOME</title>
</head>
<body>
 <%
	if(session.getAttribute("userkit")==null) {
		response.sendRedirect("newuser.jsp");
	}
%> 
<h3>Enter Shipping Details</h3>
<form action="user?action=saveorder" method="post">
	<table>
		<tr><td>Address:</td><td><input type="text" name="address1"></td></tr>
		<tr><td></td><td><input type="text" name="address2"></td></tr>
		<tr><td>City:</td><td><input type="text" name="city"></td></tr>
		<tr><td>State:</td><td><input type="text" name="state"></td></tr>
		<tr><td>Postal Code:</td><td><input type="number" name="postalCode"></td></tr>
		<tr><td>Country:</td><td><input type="text" name="country"></td></tr>
	</table>	 
	<input type="submit" value="complete order">
</form>


</body>
</html>