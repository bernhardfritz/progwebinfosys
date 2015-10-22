<?php
  include 'DbManager.php';
  $dbman = new DbManager();
  $dbman->deleteComment($_POST['id']);
  $dbman->disconnect();
  die();
?>
