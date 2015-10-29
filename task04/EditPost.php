<!DOCTYPE html>
<html>
  <head>
    <link rel='stylesheet' href='css/style.css'>
    <title>MyBlog - Edit Posting</title>
    <?php
      include 'DbManager.php';
      $dbman = new DbManager();
      $posting = $dbman->getPosting($_GET['id']);
      $id = $posting->getId();
      $title = $posting->getTitle();
      $text = $posting->getText();
      $keywords = $posting->getKeywords();
    ?>
  </head>
  <body>
    <div id='wrapper'>
      <div id='header'>
        <h1>
          <a class='nohover' href='index.php'>MyBlog</a>
        </h1>
      </div>
      <div id='content'>
        <form class='form' action='SavePost.php' method='post'>
          <ul>
            <?php echo "<input type='hidden' name='id' value='$id'/>"; ?>
            <li>
              <label for='title'>Title:<sup>*</sup></label>
              <?php echo "<input class='field' type='text' name='title' value='$title' required='true' />"; ?>
            </li>
            <br/>
            <li>
              <?php include 'toolbox.php'; ?>
            </li>
            <li>
              <label for='text'>Text:<sup>*</sup></label>
              <?php echo "<textarea id='textarea' name='text' rows='10' cols='50' required='true'>$text</textarea>"; ?>
            </li>
            <br/>
            <li>
              <label for='keywords'>Keywords:</label>
              <?php echo "<input class='field' type='text' name='keywords' value='$keywords'/>"; ?>
            </li>
            <li>
              <input class='submit' type='submit' value='Save'/>
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
