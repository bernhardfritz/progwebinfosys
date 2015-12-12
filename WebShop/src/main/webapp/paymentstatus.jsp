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
	    		String status = request.getParameter("status");
	    		if(status != null && status.equals("success"))
		    		out.println("<h1 id='statusText' class='text-center'><span class='glyphicon glyphicon-ok-sign text-success animated bounceIn' aria-hidden='true'></span> Payment successful!</h1>");
	    		else if(status != null && status.equals("failure"))
		    		out.println("<h1 id='statusText' class='text-center'><span class='glyphicon glyphicon-remove-sign text-danger animated bounceIn' aria-hidden='true'></span> Payment failed...</h1>");
	    		else
	    			out.println("<h1 id='statusText' class='text-center'><span class='glyphicon glyphicon-question-sign text-info animated bounceIn' aria-hidden='true'></span> Got lost?</h1>");
		    %>
	    </div>
	    <p class='text-center'><a href='index.jsp' class='btn btn-lg btn-primary'>Go back to main page</a></p>
    </div>
    <jsp:include page="footer.jsp" />
</body>