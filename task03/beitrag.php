<?php
  $id = $posting->getId();
  $title = $posting->getTitle();
  $author = $posting->getAuthor();
  $text = $posting->getText();
  $keywords = $posting->getKeywords();
  $createdOn = $posting->getCreated();
  $updatedOn = $posting->getUpdated();
?>
<div id='beitrag'>
  <div id='blogmenu'>
    <ul>
      <li><?php echo "<a href='EditPost.php?id=$id'>edit</a>"; ?></li>
      <li><?php echo "<a href='DeletePost.php?id=$id'>delete</a>"; ?></li>
      <li><?php echo "<a href='index.php?id=$id'>comment</a>"; ?></li>
    </ul>
  </div>
  <div id='blogcontent'>
    <h1>
      <?php echo "<a href='#$title' name='$title'>#</a>$title"; ?>
    </h1>
    <div>
      <span class='left'>
        <?php echo "by <a href='#'>$author</a>"; ?>
      </span>
      <span class='right'>
        <div>created on <b><?php echo date("d.m.Y H:i", strtotime($createdOn)); ?></b></div>
      </span>
    </div>
    <p id='blogtext'>
      <?php
        if(!isset($postings) || strlen($text) <= 120) {
          echo $text;
        } else {
          echo substr($text, 0, 120) . "... <a href='index.php?id=$id'>(read more)</a>";
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
      $dbman->disconnect();
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
