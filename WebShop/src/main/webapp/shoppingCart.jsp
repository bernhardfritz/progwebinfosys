<%@ page import="java.util.*, model.*, control.*" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="header.jsp" />
</head>

<body>
    <jsp:include page="navbar.jsp" />
    <div class="container">
    <h1 class="text-center">Shopping cart</h1>
    <table class="table table-hover">
    	<thead>
    		<th>title</th>
    		<th>amount</th>
    		<th>discard</th>
    	</thead>
    	<tbody>
    <%
    	ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
    	if(shoppingCart != null) {
    		for(ShoppingCartItem sci : shoppingCart.getContent()) {
	    		out.println(String.format("<tr><td>%s</td><td><input type='number' value='%d' onchange='editItemFromShoppingCart("+sci.getItem().getId()+",this.value)'></input></td><td><button class='btn btn-sm btn-danger' onclick='deleteItemFromShoppingCart("+sci.getItem().getId()+")'><span class='glyphicon glyphicon-trash' aria-hidden='true'></span></button></td></tr>", sci.getItem().getTitle(), sci.getAmount()));
	    	}
    	}
    %>
    	</tbody>
    </table>
    <a href="checkout.jsp" class="btn btn-primary pull-right">Checkout</a>
    </div>
    <jsp:include page="footer.jsp" />
</body>