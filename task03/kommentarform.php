<?php session_start(); ?>
<script type="text/javascript" src="js/ajax.js" ></script>
<div id='commentcontent'>
  <ul>
    <li class='nodecoration'><textarea id="textarea" name="text" rows="10" cols="50"></textarea></li>
    <?php echo "<li class='nodecoration'><button onClick='postComment(" . $postingId . "," . $_SESSION['user'] . ");'>Comment</button></li>"; ?>
  </ul>
</div>
