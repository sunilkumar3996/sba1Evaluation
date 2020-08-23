<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List"%>
<%@page import="com.iiht.evaluation.coronokit.model.ProductMaster"%>

<html>
<head>
<title>Online Shopping - HOME</title>
</head>
<body>

<%
	if(session.getAttribute("username")==null) {
		response.sendRedirect("index.jsp");
	}
%>
<h1>Admin Online Shopping</h1>
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

<a href="admin?action=logout"><button>Logout</button></a>
<br/>
<br/>
<br/>
<br/>
	<a href="admin?action=newproduct"><button>Add New Product</button></a>
<br/>
	
<div align="center">
	<h1>List Of Products</h1>
	<%
		// fetch the shared data
		List<ProductMaster> products =  (List<ProductMaster>) request.getAttribute("products");
	%>
	<table border="1" width="100%">
		<thead>
			<th>Name</th>
			<th>Cost</th>
			<th>Description</th>
			
		</thead>
		<tbody>
			<% for(ProductMaster product : products) { %>
			<tr>
				<td><%=product.getProductName()%></td>
				<td><%=product.getCost()%></td>
				<td><%=product.getProductDescription()%></td>
				<td><a href="admin?action=editproduct&id=<%=product.getId()%>"><button>Edit</button></a></td>
				<td><a href="admin?action=deleteproduct&id=<%=product.getId()%>"><button>Delete</button></a></td>
			</tr>
			<% } %>
		</tbody>
	</table>
</div>
<hr/> 
</body>
</html>
