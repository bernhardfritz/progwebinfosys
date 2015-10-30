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
    <?php include 'Base.php'; ?>
    <link rel='stylesheet' href='css/style.css'>
    <script type="text/javascript" src="js/login.js"></script>
    <title>MyBlog</title>
  </head>
  <body>
    <div id='wrapper'>
      <div id='header'>
        <h1>
          <span class='left'>
            <div><a class='nohover' href='posts'>MyBlog</a></div>
          </span>
          <span class='right'>
            <div>
              <?php
                echo "Hello, " . $loggedInUser->getUsername() . "&nbsp;";
                if($loggedInUser->getId() != 4) {
                  if($loggedInUser->getWritePost()) {
                    echo "<a href='createPost' title='create post'>+&nbsp;</a>";
                  }
                  echo "<a href='logout' title='logout' class='loginbutton'><img src='img/logout.png' width='20px' height='20px'></a>";
                } else {
                  echo "<a href='javascript:void(0)' onClick='login()' class='loginbutton' title='login'><img src='img/login.png' width='20px' height='20px'></img></a>";
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
        <div class='closebutton'>
          <a href='#' onclick="login()">x&nbsp;</a>
        </div>
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
            Version 3.0
          </div>
        </span>
      </div>
    </div>
    <div id='overlay'>
    </div>
  </body>
</html>
