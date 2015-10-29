function postComment(idPosting, idUser) {
  var text = document.getElementById('textarea').value;
  var xhttp = new XMLHttpRequest();
  xhttp.open("POST", "CommentPost.php", true);
  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  xhttp.send("id="+idPosting+"&user="+idUser+"&text="+text);
  loadComments(idPosting,true);
}

function deleteComment(idPosting, idComment) {
  var xhttp = new XMLHttpRequest();
  xhttp.open("POST", "DeleteComment.php", true);
  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  xhttp.send("id="+idComment);
  loadComments(idPosting);
}

function editComment(idPosting, idComment) {
  var oldText = document.getElementById('comment'+idComment).innerHTML;
  var newText = prompt('Comment:', oldText);
  var xhttp = new XMLHttpRequest();
  xhttp.open("POST", "EditComment.php", true);
  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  xhttp.send("id="+idComment+"&text="+newText);
  loadComments(idPosting);
}

function loadComments(id,scroll) {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    document.getElementById("comment").className = "hidden";
    setTimeout(function() {
      if (xhttp.readyState == 4 && xhttp.status == 200) {
        document.getElementById("comments").innerHTML = xhttp.responseText;
        if(scroll) scrollToBottom();
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
