<?php
  session_start();
  ob_start();
  include 'DbManager.php';

  $id = $_POST['id'];
  $idUser = $_SESSION['user'];
  $title = $_POST['title'];
  $text = $_POST['text'];
  $keywords = $_POST['keywords'];

  $dbman = new DbManager();
  if (isset($id)) {
    $dbman->savePosting($id, $title, $text, $keywords);
  }
  else {
    $dbman->createPosting($idUser, $title, $text, $keywords);
  }
  $dbman->disconnect();
  header("Location: index.php");
  die();
?>
