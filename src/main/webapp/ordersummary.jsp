<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List"%>
<%@page import="com.iiht.evaluation.coronokit.model.OrderSummary"%>
<%@page import="com.iiht.evaluation.coronokit.model.CoronaKit"%>
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
	}%>

	<% OrderSummary summary = (OrderSummary)session.getAttribute("ordersummary");
	CoronaKit cKit = summary.getCoronaKit();
	List<KitDetail> showkit = summary.getKitDetails();
%>

<H1> Order Summary</H1>

<table>
	<tbody>
		<tr><td>
			<h3> Shipping Details</h3>
			<br>
			User: <%=cKit.getPersonName() %>
			<br>
			Contact No: <%=cKit.getContactNumber() %>
			<br>
			Email: <%=cKit.getEmail() %>
			<br>
			Address: <%=cKit.getDeliveryAddress() %>
		</td></tr>
		<tr><td><h3>Order Details</h3>
		<br>
		<strong>Order ID: </strong><%=cKit.getId() %>
		<br>
		<%for(KitDetail kit: showkit) { %>
		<div>
			<span>Product: <%=kit.getProductName() %></span>
			<br>
			<span>Quantity: <%=kit.getQuantity() %></span>
			<br>
			<span>Price: <%=kit.getQuantity()*kit.getAmount() %></span>
		</div>
		<%}%>
		<tr>
			<td><strong>Order Total: </strong><%=cKit.getTotalAmount()%></td>
		</tr>
	</tbody>
</table>

</body>
</html>