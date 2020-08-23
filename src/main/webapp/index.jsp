<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.*" errorPage=""%>

<html>
<head>
<title>Online Shopping</title>
</head>
<body>

<div align="center">
	<h1>Online Shopping - HOME</h1>
</div>

<hr/> 
<a href="user?action=newuser">New User</a>
<br/>
<br/>
<br/>
<br/>
<br/>
<div>
	<%
		String msg = request.getParameter("msg");
		if(msg!=null) {
	%>
	<label><font color="red"><%=msg%></font></label>
	<%
		}
	%>
</div>
<form action="admin?action=login" method="post">
	<div align="center">
		<h3>ADMIN LOGIN</h3>
		<table>
			<tr>
				<td>Username:</td>
				<td><input type="text" name="username"></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type="password" name="password"></td>
			</tr>
			<tr>
				<td><input type="submit" value="Login"></td>
			</tr>
		</table>
	</div>
</form>
</body>
</html>
