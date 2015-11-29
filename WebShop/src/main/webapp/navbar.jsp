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
      		out.println("<form action='/WebShop/api/user/login' method='post' id='loginForm' class='navbar-form navbar-right'>");
            out.println("<div class='form-group'>");
            out.println("<input id='username' type='text' name='username' placeholder='Username' class='form-control'>");
            out.println("</div>");
            out.println("<div class='form-group'>");
            out.println("<input id='password' type='password' name='password' placeholder='Password' class='form-control'>");
            out.println("</div>");
            out.println("<button id='signIn' type='submit' class='btn btn-success'>Sign in</button>");
            out.println("<a id='signUp' class='btn btn-primary' href='#' data-toggle='modal' data-target='#signUpModal'>Sign up</a>");
          	out.println("</form>");
      	} else {
      		out.println("<form action='/WebShop/api/user/logout' method='post' id='logoutForm' class='navbar-form navbar-right'>");
      		out.println("<text id='helloText' class='text-muted'>Hello, " + user.getUsername() + "!</text>");
      		out.println("&nbsp;");
      		out.println("<button id='logout' type='submit' class='btn btn-danger'>Logout</button>");
      		out.println("</form>");
      	}
      %>
    </div>
  </div>
</nav>
<jsp:include page="signUpModal.jsp"></jsp:include>