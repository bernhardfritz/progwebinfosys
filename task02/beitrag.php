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
        <div>created on 01.01.2015</div>
      </span>
    </div>
    <p id='blogtext'>
      <?php
        $text = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.";
        if(strlen($text) <= 120) {
          echo $text;
        } else {
          echo substr($text, 0, 120) . "... <a href='#'>(read more)</a>";
        }
      ?>
    </p>
    <div>
      <span class='left'>
        <div id='keyword'>
          <a href='#'>keyword1</a>
          <a href='#'>keyword2</a>
        </div>
      </span>
      <span class='right'>
        <div>updated on 01.01.2015</div>
      </span>
    </div>
  </div>
</div>
<br/>
