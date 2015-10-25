<?php
  session_start();
  if (!isset($_SESSION['user'])) {
    $_SESSION['user'] = '4';
  }
  include 'DbManager.php';
  $dbman = new DbManager();
  $loggedInUser = $dbman->getUser($_SESSION['user']);
?>
<!DOCTYPE html>
<html>
  <head>
    <link rel='stylesheet' href='css/style.css'>
    <script type="text/javascript" src="js/login.js"></script>
    <title>MyBlog</title>
  </head>
  <body>
    <div id='wrapper'>
      <div id='header'>
        <h1>
          <span class='left'>
            <div><a class='nohover' href='index.php'>MyBlog</a></div>
          </span>
          <span class='right'>
            <div>
              <?php
                echo "Hello, " . $loggedInUser->getUsername() . "&nbsp;";
                if($loggedInUser->getId() != 4) {
                  echo "<a href='Logout.php' title='logout'>logout</a>";
                  if($loggedInUser->getWritePost()) {
                    echo "<a href='CreatePost.php' title='create post'>+</a>";
                  }
                } else {
                  echo "<a href='#' onClick='login()' title='login'>login</a>";
                }
              ?>
            </div>
          </span>
        </h1>
      </div>
      <div id='content'>
        <?php
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
      <div id='login' class='hidden'>
        <form action='Login.php' method='post'>
          <ul>
            <li>
              <label for='username'>Username:</label>
              <input type='text' name='username' />
            </li>
            <li>
              <label for='password'>Password:</label>
              <input type='password' name='password' />
            </li>
            <li>
              <input type='submit' class='hidden'/>
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
