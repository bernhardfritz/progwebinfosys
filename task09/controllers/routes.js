var express = require('express');
var router = express.Router();
var _ = require('lodash');

function getIndex(req, res) {
  res.render('index', {});
}
router.get('/', getIndex);

module.exports = router;
