function insertTag(tag1, tag2) {
  var textarea = document.getElementById('textarea');
  var selectionStart = textarea.selectionStart;
  var selectionEnd = textarea.selectionEnd;
  var text = textarea.value.substring(0,selectionStart);
  text += tag1;
  text += textarea.value.substring(selectionStart, selectionEnd);
  text += tag2;
  text += textarea.value.substring(selectionEnd);
  textarea.value = text;
  textarea.focus();
  textarea.selectionStart = selectionStart+tag1.length;
  textarea.selectionEnd = selectionEnd+tag1.length;
}

function bold() {
  insertTag('[b]','[/b]');
}

function italic() {
  insertTag('[i]','[/i]');
}

function underlined() {
  insertTag('[u]','[/u]');
}

function h() {
  var select = document.getElementById('headline');
  if(select.selectedIndex === 0) return;
  insertTag('['+select.value+']','[/'+select.value+']');
  select.selectedIndex = 0;
}

function changeColor() {
  var select = document.getElementById('color');
  if(select.selectedIndex === 0) return;
  insertTag('[color='+select.value+']','[/color]');
  select.selectedIndex = 0;
}

function changeSize() {
  var select = document.getElementById('size');
  if(select.selectedIndex === 0) return;
  insertTag('[size='+select.value+']','[/size]');
  select.selectedIndex = 0;
}

function changeFont() {
  var select = document.getElementById('font');
  if(select.selectedIndex === 0) return;
  insertTag('[font='+select.value+']','[/font]');
  select.selectedIndex = 0;
}

function insertHyperlink() {
  var hyperlink = prompt('Insert hyperlink','http://');
  insertTag('[url='+hyperlink+']','[/url]');
}
