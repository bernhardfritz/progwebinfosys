function login() {
  var login = document.getElementById("login");
  var overlay = document.getElementById("overlay");
  if(login.className === 'hidden') {
    overlay.className = 'overlay';
    login.className = 'visible';
  } else {
    overlay.className = '';
    login.className = 'hidden';
  }
}
