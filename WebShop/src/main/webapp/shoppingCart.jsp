<%@ page import="java.util.Map, java.util.Map.Entry, model.*, control.*" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="header.jsp" />
</head>

<body>
    <jsp:include page="navbar.jsp" />
    <table class="table table-hover">
    	<thead>
    		<th>title</th>
    		<th>amount</th>
    		<th>delete</th>
    	</thead>
    	<tbody>
    <%
    	ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
    	if(shoppingCart != null) {
    		for(Map.Entry<Item, Integer> entry : shoppingCart.getContent().entrySet()) {
	    		Item item = entry.getKey();
	    		int amount = entry.getValue();
	    		out.println(String.format("<tr><td>%s</td><td><input type='text' value='%d'></input></td><td><button class='btn btn-sm btn-danger'><span class='glyphicon glyphicon-trash' aria-hidden='true'></span></button></td></tr>", item.getTitle(), amount));
	    	}
    	}
    %>
    	</tbody>
    </table>
    <jsp:include page="footer.jsp" />
</body>