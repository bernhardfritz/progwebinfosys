<div id='commentcontent'>
  <div>
    <span class='left'>
    <?php echo "by <a href='#'>" . $comment->getUser()->getUsername() . "</a>"; ?>
    </span>
    <span class='right'>
      <div>
        <?php echo "created on <b>" . date("d.m.Y H:i", strtotime($comment->getCreated())) . "</b>"; ?>
        <span class='closebutton'>
          <?php echo "<a href='#' onclick='deleteComment(" . $postingId . "," . $comment->getId() . ");'>x</a>"; ?>
        </span>
      </div>
    </span>
  </div>
  <div id='commenttext'>
    <?php echo $comment->getText(); ?>
  </div>
</div>
<br/>
