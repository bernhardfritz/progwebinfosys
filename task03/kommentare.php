<?php
  include_once 'DbManager.php';
  $dbman = new DbManager();
  if(isset($_GET['id'])) {
    $postingId = $_GET['id'];
  }
  echo "<div id='topborder'></div>";
  $comments = $dbman->getComments($postingId);
  foreach($comments as $comment) {
    include 'kommentar.php';
  }
  include 'kommentarform.php';
?>
