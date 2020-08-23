<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Online Shopping - HOME</title>
</head>
<body>
<form action="user?action=insertuser" method="post">
	<div align="center">
		<h1>NEW USER - Enter Contact Details</h1>
		<table>
			<tr>
				<td>Name:</td>
				<td><input type="text" name="pname"></td>
			</tr>
			<tr>
				<td>Phone:</td>
				<td><input type="number" name="pcontact"></td>
			</tr>
			<tr>
				<td>Email:</td>
				<td><input type="email" name="pemail"></td>
			</tr>
			
			<tr>
				<td><input type="submit" value="submit"></td>
			</tr>
		</table>
	</div>
</form>

</body>
</html>