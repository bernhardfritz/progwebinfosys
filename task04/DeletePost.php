<?php
  ob_start();
  include 'DbManager.php';
  $dbman = new DbManager();
  $dbman->deletePosting($_GET['id']);
  $dbman->disconnect();
  header("Location: index.php");
  die();
?>
