<%@ page import="java.util.*, model.*, control.*" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="header.jsp" />
</head>

<body>
    <jsp:include page="navbar.jsp" />
    <div class="container">
	    <div class="jumbotron">
	    	<%
	    		User user = (User)session.getAttribute("user");
		    	out.println("<h1 class='text-center'><span class='glyphicon glyphicon-user animated bounceIn' aria-hidden='true'></span></h1>");
		    	out.println("<h1 class='text-center'>" + user.getUsername() + "</h1>");
		    	out.println("<table>");
		    	out.println("<tr>");
		    	out.println("<td><h4>Adress:</h4></td>");
		    	out.println("<td><h4>foo</h4></td>");
		    	out.println("</tr>");
		    	out.println("<tr>");
		    	out.println("<td><h4>Other information:</h4></td>");
		    	out.println("<td><h4>bar</h4></td>");
		    	out.println("</tr>");
		    	out.println("</table>");
		    %>
	    </div>
	    <p class='text-center'><a href='index.jsp' class='btn btn-lg btn-primary'>Go back to main page</a></p>
    </div>
    <jsp:include page="footer.jsp" />
</body>