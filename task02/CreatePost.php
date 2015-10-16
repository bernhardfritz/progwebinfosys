<!DOCTYPE html>
<html>
  <head>
    <link rel='stylesheet' href='css/style.css'>
  </head>
  <body>
    <div id='wrapper'>
      <div id='header'>
        <h1>
          <a class='nohover' href='index.php'>MyBlog</a>
        </h1>
      </div>
      <div id='content'>
        <form action='SavePost.php' method='post'>
          <ul>
            <li>
              <label for='title'>Title:</label>
              <input class='field' type='text' name='title' />
            </li>
            <br/>
            <li>
              <label for='text'>Text:</label>
              <textarea name='text' rows='10' cols='50'></textarea>
            </li>
            <br/>
            <li>
              <label for='keywords'>Keywords:</label>
              <input class='field' type='text' name='keywords' />
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
