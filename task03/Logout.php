<?php
  session_start();
  ob_start();
  $_SESSION['user'] = '4';
  header("Location: index.php");
  die();
?>
