function loginPrompt() {
  var username = prompt("Username","");
  var password = prompt("Password","");

  var form = document.createElement("form");
  form.setAttribute("method", "post");
  form.setAttribute("action", "login.php");

  function addField(key, value) {
    var field = document.createElement("input");
    field.setAttribute("type", "hidden");
    field.setAttribute("name", key);
    field.setAttribute("value", value);
    form.appendChild(field);
  }

  addField("username", username);
  addField("password", password);

  document.body.appendChild(form);
  form.submit();
}
