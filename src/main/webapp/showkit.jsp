<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*"%>
<%@page import="com.iiht.evaluation.coronokit.model.KitDetail"%>
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
<br/>
	<a href="user?action=showproducts">Available Products</a>
<br/>

	<%
		// fetch the shared data
		Map<Integer,KitDetail> kitDetails =  (Map<Integer,KitDetail>) session.getAttribute("shoppingcart");
	
		if(kitDetails==null || kitDetails.size()==0) {%>
			<h2>Your shopping cart is empty</h2>
		<%} else {%>
		
		<h1>Products Added to Kit</h1>
		<br/>
		<table border="1">
			<tr><th>Product Name</th><th>Quantity</th><th>Amount</th></tr>
			<%
				int i=0;
				for(Map.Entry<Integer, KitDetail> kit: kitDetails.entrySet()) {%>
					<tr>
						<td><%=((KitDetail)kit.getValue()).getProductName() %></td>
						<%System.out.println("********  Map kitdetails ProductName: "+((KitDetail)kit.getValue()).getProductName()); %>
						<td><%=((KitDetail)kit.getValue()).getQuantity() %></td>
						<td><%=((KitDetail)kit.getValue()).getAmount() %></td>
						<td><a href="user?action=deleteitem&id=<%=((KitDetail)kit.getValue()).getProductId()%>">Remove</a></td>
				<%}
			%>
		</table>
			
		<%} %>
</body>
</html>