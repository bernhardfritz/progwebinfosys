<script type="text/javascript" src="js/toolbox.js"></script>
<label>&nbsp;</label>
<button type='button' onClick='bold()'><font size='2'><b>B</b></font></button>
<button type='button' onClick='italic()'><font size='2'><i>I</i></font></button>
<button type='button' onClick='underlined()'><font size='2'><u>U</u></font></button>
<button type='button' onClick='insertHyperlink()'><font size='2'>@</font></button>
<select id='font' onChange='changeFont()'>
  <option>Choose font:</option>
  <option value='serif'>serif</option>
  <option value='sans-serif'>sans-serif</option>
  <option value='cursive'>cursive</option>
  <option value='fantasy'>fantasy</option>
  <option value='monospace'>monospace</option>
</select>
<select id='size' onChange='changeSize()'>
  <option>Choose size:</option>
  <option value='1'>1</option>
  <option value='2'>2</option>
  <option value='3'>3</option>
  <option value='4'>4</option>
  <option value='5'>5</option>
  <option value='6'>6</option>
  <option value='7'>7</option>
</select>
<select id='color' onChange='changeColor()'>
  <option>Choose color:</option>
  <option value='white'>White</option>
  <option value='silver'>Silver</option>
  <option value='gray'>Gray</option>
  <option value='black'>Black</option>
  <option value='red'>Red</option>
  <option value='maroon'>Maroon</option>
  <option value='yellow'>Yellow</option>
  <option value='olive'>Olive</option>
  <option value='lime'>Lime</option>
  <option value='green'>Green</option>
  <option value='aqua'>Aqua</option>
  <option value='teal'>Teal</option>
  <option value='blue'>Blue</option>
  <option value='navy'>Navy</option>
  <option value='fuchsia'>Fuchsia</option>
  <option value='purple'>Purple</option>
</select>
<select id='headline' onChange='h()'>
  <option>Choose headline:</option>
  <option value='h1'>h1</option>
  <option value='h2'>h2</option>
  <option value='h3'>h3</option>
  <option value='h4'>h4</option>
  <option value='h5'>h5</option>
  <option value='h6'>h6</option>
</select>
