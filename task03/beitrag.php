<?php
  $id = $posting->getId();
  $title = $posting->getTitle();
  $user = $posting->getUser();
  $text = $posting->getText();
  $keywords = $posting->getKeywords();
  $createdOn = $posting->getCreated();
  $updatedOn = $posting->getUpdated();
?>
<div id='beitrag'>
  <div id='blogmenu'>
    <ul>
      <li>
        <?php
          if ($loggedInUser->getWritePost()) {
            echo "<a href='EditPost.php?id=$id'>edit</a>";
          }
        ?>
      </li>
      <li>
        <?php
          if ($loggedInUser->getDeletePost()) {
            echo "<a href='DeletePost.php?id=$id'>delete</a>";
          }
        ?>
      </li>
      <li>
        <?php
          if ($loggedInUser->getReadComment() || $loggedInUser->getWriteComment() || $loggedInUser->getDeleteComment()) {
            echo "<a href='index.php?id=$id'>comment</a>";
          }
        ?>
      </li>
    </ul>
  </div>
  <div id='blogcontent'>
    <h1>
      <?php echo "<a href='#$title' name='$title'>#</a>$title"; ?>
    </h1>
    <div>
      <span class='left'>
        <?php echo "by <a href='#'>" . $user->getUsername() . "</a>"; ?>
      </span>
      <span class='right'>
        <div>created on <b><?php echo date("d.m.Y H:i", strtotime($createdOn)); ?></b></div>
      </span>
    </div>
    <p id='blogtext'>
      <?php
        // BBCode replacement
        $text = preg_replace("/\[b\](.*)\[\/b\]/Usi", "<b>\\1</b>", $text);   // bold
        $text = preg_replace("/\[i\](.*)\[\/i\]/Usi", "<i>\\1</i>", $text);   // italic
        $text = preg_replace("/\[u\](.*)\[\/u\]/Usi", "<u>\\1</u>", $text);   // underlined
        $text = preg_replace("/\\n/Usi", "<br />", $text);   // underlined
        $text = preg_replace("/\[color=(.*)\](.*)\[\/color\]/Usi", "<font color=\"\\1\">\\2</font>", $text);  // text color
        $text = preg_replace("/\[size=(.*)\](.*)\[\/size\]/Usi", "<font size=\"\\1\">\\2</font>", $text);  // text size
        $text = preg_replace("/\[font=(.*)\](.*)\[\/font\]/Usi", "<span style=\"font-family:\\1\">\\2</span>", $text);  // text face
        $text = preg_replace("/\[url=(.*)\](.*)\[\/url\]/Usi", "<u><a href=\"\\1\" class=\"nohover\">\\2</a></u>", $text);  // link
        $text = preg_replace("/\[h1\](.*)\[\/h1\]/Usi", "<h1>\\1</h1>", $text);   // h1
        $text = preg_replace("/\[h2\](.*)\[\/h2\]/Usi", "<h2>\\1</h2>", $text);   // h2
        $text = preg_replace("/\[h3\](.*)\[\/h3\]/Usi", "<h3>\\1</h3>", $text);   // h3

        if(!isset($postings) || strlen($text) <= 300) {
          echo $text;
        } else {
          echo substr($text, 0, 300) . "... <a href='index.php?id=$id'>(read more)</a>";
        }
      ?>
    </p>
    <div>
      <span class='left'>
        <div id='keyword'>
          <?php
            foreach(explode(' ',$keywords) as $keyword) {
                echo "<a href='#'>$keyword</a> ";
            }
          ?>
        </div>
      </span>
      <span class='right'>
        <div>updated on <b><?php echo date("d.m.Y H:i", strtotime($updatedOn)); ?></b></div>
      </span>
    </div>
    <?php
      if(isset($_GET['id'])) {
        $postingId = $_GET['id'];
        echo "<div id='comments'>";
        include 'kommentare.php';
        echo "</div>";
      }
    ?>
  </div>
</div>
<br/>
