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
      	if(!user.isUser()) {
      		out.println("<form action='/WebShop/api/user/login' method='post' id='loginForm' class='navbar-form navbar-right'>");
      		out.println("<fb:login-button scope='public_profile,email' onlogin='login();'></fb:login-button>");
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
      		out.println("<text id='helloText' class='text-muted'>Hello, <a href='profile.jsp'>" + user.getUsername() + "</a>!</text>");
      		out.println("&nbsp;");
      		if (user.isUserPromote() || user.isUserDemote() || user.isUserDelete()) {
	      		out.println("<a id='userManagementButton' class='btn btn-primary' href='userManagement.jsp'><span class='glyphicon glyphicon-user' aria-hidden='true'></span></a>");
	      		out.println("&nbsp;");
      		} else if(!user.isAdmin()) {
      			out.println("<a id='userManagementButton' class='btn btn-primary' href='profile.jsp'><span class='glyphicon glyphicon-user' aria-hidden='true'></span></a>");
	      		out.println("&nbsp;");
      		}
      		out.println("<a id='shoppingCartButton' class='btn btn-success' href='shoppingCart.jsp'><span class='glyphicon glyphicon-shopping-cart' aria-hidden='true'></span></a>");
      		out.println("&nbsp;");
      		out.println("<button id='logout' type='submit' class='btn btn-danger'>Logout</button>");
      		out.println("</form>");
      	}
      %>
    </div>
  </div>
</nav>
<div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/de_DE/sdk.js#xfbml=1&version=v2.5&appId=796292657163912";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>
<jsp:include page="signUpModal.jsp"></jsp:include>