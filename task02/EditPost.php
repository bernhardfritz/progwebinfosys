<!DOCTYPE html>
<html>
  <head>
    <link rel='stylesheet' href='css/style.css'>
    <?php
      include 'DbManager.php';
      $dbman = new DbManager();
      $posting = $dbman->getPosting($_GET['id']);
      $title = $posting->getTitle();
      $author = $posting->getAuthor();
      $text = $posting->getText();
      $keywords = $posting->getKeywords();
    ?>
  </head>
  <body>
    <div id='wrapper'>
      <div id='header'>
        <h1>
          MyBlog
        </h1>
      </div>
      <div id='content'>
        <form action='SavePost.php' method='post'>
          <ul>
            <li>
              <label for='title'>Title:</label>
              <?php echo "<input class='field' type='text' name='title' value='$title' />"; ?>
            </li>
            <br/>
            <li>
              <label for='text'>Text:</label>
              <?php echo "<textarea name='text' rows='10' cols='50'>$text</textarea>"; ?>
            </li>
            <br/>
            <li>
              <label for='keywords'>Keywords:</label>
              <?php echo "<input class='field' type='text' name='keywords' value='$keywords'/>"; ?>
            </li>
            <li>
              <input class='submit' type='submit' />
            </li>
          </ul>
        </form>
      </div>
      <div id='footer'>
        <span class='left'>
          <div>
            by FRITZ and ZELGER
          </div>
        </span>
        <span class='right'>
          <div>
            Version 1.0
          </div>
        </span>
      </div>
    </div>
  </body>
</html>
