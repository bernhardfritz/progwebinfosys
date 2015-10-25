<?php
  $username = $_POST['username'];
  $password = $_POST['password'];
  include 'DbManager.php';
  $dbman = new DbManager();
  $user = $dbman->login($username, $password);
  session_start();
  $_SESSION['user'] = '' . $user.getId();
  echo $username . "\n";
  echo $password . "\n";
  header("Location: index.php");
  die();
?>
