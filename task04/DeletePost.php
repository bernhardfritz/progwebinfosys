<?php
  ob_start();
  include 'DbManager.php';
  $dbman = new DbManager();
  $dbman->deletePosting($_POST['id']);
  $dbman->disconnect();
  header("Location: posts");
  die();
?>
