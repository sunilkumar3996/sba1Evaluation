<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List"%>
<%@page import="com.iiht.evaluation.coronokit.model.ProductMaster"%>
<%@page import="java.util.*"%>
<%@page import="com.iiht.evaluation.coronokit.model.KitDetail"%>
<html>
<head>
<title>Online Shopping - HOME</title>
</head>
<body>

<%
	if(session.getAttribute("userkit")==null) {
		response.sendRedirect("newuser.jsp");
	}
%>

<div align="center">

<a href="user?action=showkit"><button>View Cart</button></a>

	<h1>List Of Products</h1>
	<%
		// fetch the shared data
		List<ProductMaster> products =  (List<ProductMaster>) request.getAttribute("availproducts");
	%>
	<table border="1" width="100%">
			<thead>
				<th>ID</th>
				<th>Name</th>
				<th>Cost</th>
				<th>Description</th>
				<th>Quantity</th>
				<th></th>
			</thead>
	</table>
	<% for(ProductMaster product : products) { %>
	<form action="user?action=addnewitem" method="post">
		<table border="1" width="100%">
			<tbody>
			<tr>			
				<td><%=product.getProductName()%>
					<input type="hidden" name="pname" value="<%=product.getProductName()%>">
					<input type="hidden" name="id" value="<%=product.getId()%>">
				</td>
				<td><%=product.getCost()%>
					<input type="hidden" name="cost" value="<%=product.getCost()%>">
				</td>
				<td><%=product.getProductDescription()%></td>
				<td><input type="number" name="quantity" value="1"></td>
				<td><input type="submit" value="Add To Cart"></td>
			</tr>
			</tbody>
		</table>
	</form>
	<% } %>
</div>
<hr/> 
<%Map<Integer,KitDetail> kitDetails =  (Map<Integer,KitDetail>) session.getAttribute("shoppingcart");

	if(!(kitDetails==null || kitDetails.size()==0)) {%>
		<a href="user?action=placeorder">Proceed to Order</a>
		<%
	}%>
</body>
</html>