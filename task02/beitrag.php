<?php
  $author = $posting->getAuthor();
  $text = $posting->getText();
  $keywords = $posting->getKeywords();
  $createdOn = $posting->getCreated();
  $updatedOn = $posting->getUpdated();
?>
<div id='beitrag'>
  <div id='blogmenu'>
    <ul>
      <li><a href='#'>edit</a></li>
      <li><a href='#'>delete</a></li>
    </ul>
  </div>
  <div id='blogcontent'>
    <h1>
      <a href='#'>#</a>title
    </h1>
    <div>
      <span class='left'>
        by <a href='#'>author</a>
      </span>
      <span class='right'>
        <div>created on <?php echo $createdOn; ?></div>
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
        <div>updated on <?php echo $createdOn; ?></div>
      </span>
    </div>
  </div>
</div>
<br/>
