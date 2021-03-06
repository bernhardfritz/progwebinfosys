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
    		<th>quantity</th>
    		<th>price per item</th>
    		<th>total</th>
    	</thead>
    	<tbody>
    <%
    	User user = (User)session.getAttribute("user");
    	ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
    	float total = 0.0f;
    	if(shoppingCart != null) {
    		for(ShoppingCartItem sci : shoppingCart.getContent()) {
	    		out.println(String.format("<tr><td>%s</td><td>%d</td><td>%.2f&euro;</td><td>%.2f&euro;</td></tr>", sci.getItem().getTitle(), sci.getQuantity(), sci.getItem().getPrice(), sci.getQuantity() * sci.getItem().getPrice().floatValue()));
	    		total += sci.getQuantity() * sci.getItem().getPrice().floatValue();
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
    
    <a href="#" id="payWithPaypal" class="btn btn-primary pull-right" onclick="checkAddress(<%= user.getId() %>)">Pay with PayPal</a>
    <script>
    	function checkAddress(userId) {
    		$.get("/WebShop/api/user/" + userId, function(user) {
    			if(user.country === 'unknown' || user.state === 'unknown' || user.city === 'unknown' || user.streetname === 'unknown' || user.housenumber === 'unknown') {
    				alert("Update your profile information!");
    				window.location.href = "/WebShop/profile.jsp";	
    			} else {
    				window.location.href = "/WebShop/api/paypal/startTransaction";
    			}
    		});
    	}
    </script>
    </div>
    <jsp:include page="footer.jsp" />
</body>