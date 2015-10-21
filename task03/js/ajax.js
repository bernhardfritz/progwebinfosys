function postComment(idPosting, idUser) {
  var text = document.getElementById('textarea').value;
  var xhttp = new XMLHttpRequest();
  xhttp.open("POST", "CommentPost.php", true);
  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  xhttp.send("id="+idPosting+"&user="+idUser+"&text="+text);
  loadComments(idPosting);
}

function loadComments(id) {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    document.getElementById("comment").className = "hidden";
    setTimeout(function() {
      if (xhttp.readyState == 4 && xhttp.status == 200) {
        document.getElementById("comments").innerHTML = xhttp.responseText;
        scrollToBottom();
      }
      setTimeout(function() {
        document.getElementById("comment").className = "visible";
      }, 250);
    }, 250);
  }
  xhttp.open("GET", "kommentare.php?id="+id, true);
  xhttp.send();
}

function scrollToBottom() {
  window.scrollTo(0,document.body.scrollHeight);
}
