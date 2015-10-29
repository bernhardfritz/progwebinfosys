<div id='commentcontent'>
  <div>
    <span class='left'>
    <?php echo "by <a href='#'>" . $comment->getUser()->getUsername() . "</a>"; ?>
    </span>
    <span class='right'>
      <div>
        <?php echo "created on <b>" . date("d.m.Y H:i", strtotime($comment->getCreated())) . "</b>"; ?>
        <span class='closebutton'>
          <?php
            if ($loggedInUser->getDeleteComment()) {
              echo "<a href='#' onclick='editComment(" . $postingId . "," . $comment->getId() . ");'><img src='img/iconsineed-new-24-128.png' class='loginbutton' width='12px' height='12px' /></a>&nbsp;";
              echo "<a href='#' onclick='deleteComment(" . $postingId . "," . $comment->getId() . ");'><img src='img/trash_recyclebin_empty_closed.png' class='loginbutton' width='12px' height='12px' /></a>";
            }
          ?>
        </span>
      </div>
    </span>
  </div>
  <div id='commenttext'>
    <?php echo $comment->getText(); ?>
  </div>
</div>
<br/>
