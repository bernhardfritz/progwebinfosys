<?php
  session_start();
  ob_start();
  include 'DbManager.php';
  $dbman = new DbManager();

  $user = $dbman->login($_POST['username'], $_POST['password']);
  if (isset($user)) {
    $_SESSION['user'] = $user->getId();
  }
  else {
    $_SESSION['user'] = '4';
  }

  $dbman->disconnect();
  header("Location: index.php");
  die();
?>
