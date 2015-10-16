<?php
  ob_start();
  include 'DbManager.php';
  $author = 'User1';
  $title = $_POST['title'];
  $text = $_POST['text'];
  $keywords = $_POST['keywords'];
  $dbman = new DbManager();
  $dbman->createPosting($author, $title, $text, $keywords);
  $dbman->disconnect();
  header("Location: index.php");
  die();
?>
