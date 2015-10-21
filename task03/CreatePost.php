<!DOCTYPE html>
<html>
  <head>
    <link rel='stylesheet' href='css/style.css'>
    <title>MyBlog - Create Posting</title>
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
            <li>
              <label for='title'>Title:<sup>*</sup></label>
              <input class='field' type='text' name='title' required="true" />
            </li>
            <br/>
            <li>
              <label for='text'>Text:<sup>*</sup></label>
              <textarea name='text' rows='10' cols='50' required="true"></textarea>
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
