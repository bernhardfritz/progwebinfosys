var neo4j = require('neo4j');
var _ = require('lodash');

var db = new neo4j.GraphDatabase({
    url: 'http://localhost:7474',
    auth: {username: 'neo4j', password: 'secret'},
});

var queryTemplate = 'CREATE (p:Person { firstname: {firstname}, lastname: {lastname}, sex: {sex}, age: {age} })-[r:LIVES_AT]->(a:Address { housenumber: {housenumber}, streetname: {streetname}, city: {city}, state: {state}, country: {country} })';

function init() {
  db.cypher({
      query:  'MATCH (n) DETACH DELETE n',
  }, function(err, results) {
    if (err) throw err;
    db.cypher({
        query: queryTemplate,
        params: {
          firstname: 'John',
          lastname: 'Doe',
          sex: 'male',
          age: 45,
          housenumber: 15,
          streetname: 'Technikerstraße',
          city: 'Innsbruck',
          state: 'Tirol',
          country: 'Österreich',
        }
    }, function(err, results) {
      if (err) throw err;
      db.cypher({
        query: queryTemplate,
        params: {
          firstname: 'Bill',
          lastname: 'Smith',
          sex: 'male',
          age: 33,
          housenumber: 100,
          streetname: 'Innstraße',
          city: 'Innsbruck',
          state: 'Tirol',
          country: 'Österreich',
        }
      }, function(err, results) {
        if (err) throw err;
        db.cypher({
          query: queryTemplate,
          params: {
            firstname: 'Alice',
            lastname: 'Jane',
            sex: 'female',
            age: 31,
            housenumber: 12,
            streetname: 'Ursulinenweg',
            city: 'Innsbruck',
            state: 'Tirol',
            country: 'Österreich',
          }
        }, function(err, results) {
          console.log('All persons created successfully.');
        });
      });
    });
  });
}

function getAllPersonsWithAddress(callback) {
  db.cypher({
      query:  'MATCH (person:Person)-[r:LIVES_AT]->(address:Address) RETURN person,address',
      lean: true,
  }, callback);
}

//init();

/*
function callback(err, results) {
    if (err) throw err;
    var result = results[0];
    if (!result) {
        console.log('No result found.');
    } else {
      _.forEach(results, function(result) {
        console.log(result);
      });
    }
}
*/

module.exports = {
  init: init,
  getAllPersonsWithAddress: getAllPersonsWithAddress
};
