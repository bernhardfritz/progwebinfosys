var express = require('express');
var router = express.Router();
var _ = require('lodash');
var dbman = require('../controllers/dbManager');

function getIndex(req, res) {
  res.render('index', {});
}
router.get('/', getIndex);

function getAllPersonsWithAddress(req, res) {
  dbman.getAllPersonsWithAddress(function(err, results) {
      if (err) throw err;
      res.json(JSON.stringify(results));
  });
}
router.get('/personswithaddress', getAllPersonsWithAddress);

module.exports = router;
