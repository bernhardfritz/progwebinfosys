var express = require('express');
var router = express.Router();
var _ = require('lodash');

function getIndex(req, res) {
  res.render('index', {});
}
router.get('/', getIndex);

function getMap(req, res) {
  res.render('map', {});
}
router.get('/map', getMap);

function getChat(req, res) {
  res.render('chat', {});
}
router.get('/chat', getChat);

module.exports = router;
