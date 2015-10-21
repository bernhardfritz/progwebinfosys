<div id='commentcontent'>
  <div>
    <span class='left'>
    <?php echo "by <a href='#'>" . $comment->getUser()->getUsername() . "</a>"; ?>
    </span>
    <span class='right'>
      <?php echo "<div>created on <b>" . date("d.m.Y H:i", strtotime($comment->getCreated())) . "</b></div>"; ?>
    </span>
  </div>
  <div id='commenttext'>
    <?php echo $comment->getText(); ?>
  </div>
</div>
<br/>
