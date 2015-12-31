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
		    	out.println("<div class='table-responsive'>");
		    	out.println("<table class='table table-striped table-condensed'>");
		    	out.println("<tbody>");
		    	out.println("<tr>");
		    	out.println("<td><h4>Country:</h4></td>");
		    	out.println("<td><h4 id='country'>" + user.getCountry() + "</h4></td>");
		    	out.println("</tr>");
		    	out.println("<tr>");
		    	out.println("<td><h4>State:</h4></td>");
		    	out.println("<td><h4 id='state'>" + user.getState() + "</h4></td>");
		    	out.println("</tr>");
		    	if (user.getCounty() != null) {
			    	out.println("<tr>");
			    	out.println("<td><h4>County:</h4></td>");
			    	out.println("<td><h4 id='county'>" + user.getCounty() + "</h4></td>");
			    	out.println("</tr>");
		    	}
		    	out.println("<tr>");
		    	out.println("<td><h4>City:</h4></td>");
		    	out.println("<td><h4 id='postcode'>" + user.getPostcode() + "</h4> <h4 id='city'>" + user.getCity() + "</h4></td>");
		    	out.println("</tr>");
		    	out.println("<tr>");
		    	out.println("<td><h4>Street:</h4></td>");
		    	out.println("<td><h4 id='streetname'>" + user.getStreetname() + "</h4> <h4 id='housenumber'>" + user.getHousenumber() + "</h4></td>");
		    	out.println("</tr>");
		    	out.println("</tbody>");
		    	out.println("</table>");
		    	out.println("</div>");
		    %>
		    <div id="map"></div>
	    </div>
	    <p class='text-center'><a href='index.jsp' class='btn btn-lg btn-primary'>Go back to main page</a></p>
    </div>
    <jsp:include page="footer.jsp" />
    <script src="js/profile.js"></script>
</body>