<!DOCTYPE html>
<html>
  <head>
    <link rel='stylesheet' href='css/style.css'>
  </head>
  <body>
    <div id='wrapper'>
      <div id='header'>
        <h1>
          <span class='left'>
            <div>MyBlog</div>
          </span>
          <span class='right'>
            <div><a href='CreatePost.php' title='create post'>+</a></div>
          </span>
        </h1>
      </div>
      <div id='content'>
        <?php
          include 'DbManager.php';
          $dbman = new DbManager();
          if(isset($_GET['id'])) {
            $posting = $dbman->getPosting($_GET['id']);
            include 'beitrag.php';
          } else {
            $postings = $dbman->getAllPostings();
            foreach($postings as $posting) {
              include 'beitrag.php';
            }
          }
        ?>
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
