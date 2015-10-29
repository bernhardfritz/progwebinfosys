<?php
  session_start();
  ob_start();
  include 'DbManager.php';

  $idPosting = $_POST['id'];
  $idUser = $_SESSION['user'];
  $text = $_POST['text'];

  $dbman = new DbManager();
  $dbman->createComment($idPosting, $idUser, $text);
  $dbman->disconnect();
  die();
?>
