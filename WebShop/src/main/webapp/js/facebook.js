window.fbAsyncInit = function() {
	FB.init({
	  appId      : '796292657163912',
	  cookie     : true,  // enable cookies to allow the server to access 
	                      // the session
	  xfbml      : true,  // parse social plugins on this page
	  version    : 'v2.2' // use version 2.2
	});
};

// Load the SDK asynchronously
(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_US/sdk.js";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

function login() {
	FB.api('/me', function(response) {
		console.log(response);
		$.post('/WebShop/api/user/login', {username: 'facebookuser'+response.id}, function(result) {
			location.reload(true);
		});
	});
}