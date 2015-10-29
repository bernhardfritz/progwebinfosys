<?php
  include 'DbManager.php';
  $dbman = new DbManager();
  $dbman->editComment($_POST['id'],$_POST['text']);
  $dbman->disconnect();
?>
