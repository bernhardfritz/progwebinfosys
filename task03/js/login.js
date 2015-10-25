function login() {
  var login = document.getElementById("login");
  if(login.className === 'hidden') {
    login.className = 'visible';
  } else {
    login.className = 'hidden';
  }
}
