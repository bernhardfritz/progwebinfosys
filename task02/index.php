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
          $id = 0;
          if(isset($_GET['id'])) {
            $id = $_GET['id'];
            include 'beitrag.php';
          } else {
            for($i = 0; $i < 10; $i++) {
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
