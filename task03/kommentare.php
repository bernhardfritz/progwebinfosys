<?php
  include_once 'DbManager.php';
  $dbman = new DbManager();
  
  if (session_status() == PHP_SESSION_NONE) {
    session_start();
    $loggedInUser = $dbman->getUser($_SESSION['user']);
  }

  if(isset($_GET['id'])) {
    $postingId = $_GET['id'];
  }
  echo "<div id='topborder'></div>";
  $comments = $dbman->getComments($postingId);
  foreach($comments as $comment) {
    include 'kommentar.php';
  }

  if ($loggedInUser->getWriteComment()) {
    include 'kommentarform.php';
  }
?>
