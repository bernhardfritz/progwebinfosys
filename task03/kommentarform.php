<div id='commentcontent'>
  <?php echo "<form action='CommentPost.php?id=" . $_GET['id'] . "' method='post'>"; ?>
    <ul>
      <li><textarea name="text" rows="10" cols="50"></textarea></li>
      <li><input type='submit' /></li>
    </ul>
  </form>
</div>
