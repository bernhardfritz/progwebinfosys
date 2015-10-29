<script type="text/javascript" src="js/ajax.js"></script>
<?php
  include_once 'DbManager.php';
  $dbman = new DbManager();

  if (session_status() == PHP_SESSION_NONE) {
    session_start();
    $loggedInUser = $dbman->getUser($_SESSION['user']);
  }

  if(isset($_GET['id'])) {
    $postingId = $_GET['id'];
  }
  echo "<div id='topborder'></div>";
  echo "<div id='comment'>";
  $comments = $dbman->getComments($postingId);
  foreach($comments as $comment) {
    include 'kommentar.php';
  }
  echo "</div>";

  if ($loggedInUser->getWriteComment()) {
    include 'kommentarform.php';
  }
?>
