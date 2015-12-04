<%@ page import="java.util.*, model.*, control.*" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="header.jsp" />
</head>

<body>
    <jsp:include page="navbar.jsp" />
    <div class="container">
    <h1 class="text-center">Checkout</h1>
    <table class="table">
    	<thead>
    		<th>title</th>
    		<th>amount</th>
    		<th>price per item</th>
    		<th>total</th>
    	</thead>
    	<tbody>
    <%
    	ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
    	float total = 0.0f;
    	if(shoppingCart != null) {
    		for(ShoppingCartItem sci : shoppingCart.getContent()) {
	    		out.println(String.format("<tr><td>%s</td><td>%d</td><td>%.2f&euro;</td><td>%.2f&euro;</td></tr>", sci.getItem().getTitle(), sci.getAmount(), sci.getItem().getPrice(), sci.getAmount() * sci.getItem().getPrice().floatValue()));
	    		total += sci.getAmount() * sci.getItem().getPrice().floatValue();
	    	}
    	}
    %>
    		<tr>
    		<td></td>
    		<td></td>
    		<td></td>
    		<td><strong><% out.println(String.format("%.2f&euro;", total)); %></strong></td>
    		</tr>
    	</tbody>
    </table>
    <a href="#" class="btn btn-primary pull-right">Pay with PayPal</a>
    </div>
    <jsp:include page="footer.jsp" />
</body>