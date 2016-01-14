#!/bin/env node
var express = require('express');
var path = require('path');
var bodyParser = require('body-parser');
var routes = require('./controllers/routes');
var session = require('express-session');
var app = express();
var http = require('http').Server(app);
var io = require('socket.io')(http);

app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');
app.use(require('stylus').middleware(path.join(__dirname, 'public')));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: false}));
app.use(session({
  resave: false,
  saveUninitialized: false, // don't create session until something stored
  secret: 'keyboard cat'
}));
app.use(express.static(path.join(__dirname, 'public')));
app.use('/', routes);
var port = process.env.OPENSHIFT_NODEJS_PORT || 8080;
var ip = process.env.OPENSHIFT_NODEJS_IP || '127.0.0.1';

io.on('connection', function(socket){
  console.log('a user connected');

  socket.on('chatmessage', function(msg){
    console.log('message: ' + msg);
    io.emit('chatmessage', msg);
  });
  socket.on('disconnect', function(){
    console.log('user disconnected');
  });
});

http.listen(8080, function(){
  console.log('listening on *:8080');
});
