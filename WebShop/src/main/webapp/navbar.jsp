<%@ page import="model.*, control.*" %>
<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="index.jsp">WebShop</a>
    </div>
    <div id="navbar" class="navbar-collapse collapse">
      <%
      	User user = (User)session.getAttribute("user");
      	if(user == null) user = DBManager.getInstance().logout();
      	if(user.getId() == 3) {
      		out.println("<form action='/WebShop/api/user/login' method='post' class='navbar-form navbar-right'>");
            out.println("<div class='form-group'>");
            out.println("<input type='text' name='username' placeholder='Username' class='form-control'>");
            out.println("</div>");
            out.println("<div class='form-group'>");
            out.println("<input type='password' name='password' placeholder='Password' class='form-control'>");
            out.println("</div>");
            out.println("<button type='submit' class='btn btn-success'>Sign in</button>");
          	out.println("</form>");	
      	} else {
      		out.println("<form action='/WebShop/api/user/logout' method='post' class='navbar-form navbar-right'>");
      		out.println("<text class='text-muted'>Hello, " + user.getUsername() + "!</text>");
      		out.println("&nbsp;");
      		out.println("<button type='submit' class='btn btn-danger'>Logout</a>");
      		out.println("</form>");
      	}
      %>
    </div>
  </div>
</nav>