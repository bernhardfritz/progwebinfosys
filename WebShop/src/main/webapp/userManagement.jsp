<!DOCTYPE html>
<html lang="en">

<head>
	<jsp:include page="header.jsp" />
  <script src="./node_modules/systemjs/dist/system.src.js"></script>
  <script src="./node_modules/angular2/bundles/angular2.dev.js"></script>
	<script src="./node_modules/angular2/bundles/http.dev.js"></script>
  <script>
		System.config({
	    packages: {'ts': {defaultExtension: 'js'}}
	  });
	  System.import('ts/app');
  </script>
</head>

<body>
  <jsp:include page="navbar.jsp" />
  <h1 class="text-center">User management</h1>
  <app></app>
  <jsp:include page="footer.jsp" />
</body>
