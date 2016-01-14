var socket = io();

$('#chatbutton').click(function() {
  socket.emit('chatmessage', $('#chatinput').val());
  $('#chatinput').val('');
  return false;
});

$("#chatinput").keyup(function (e) {
    if (e.keyCode == 13) {
        $('#chatbutton').click();
    }
});

socket.on('chatmessage', function(msg){
  console.log(msg);
  if($('#chattextarea').val()) {
      $("#chattextarea").append('&#10;' + msg);
  }
});
