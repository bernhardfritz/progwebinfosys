var neo4j = require('neo4j');
var _ = require('lodash');

var db = new neo4j.GraphDatabase({
    url: 'http://localhost:7474',
    auth: {username: 'neo4j', password: 'secret'},
});

var queryTemplate = 'CREATE (p:Person { firstname: {firstname}, lastname: {lastname}, sex: {sex}, age: {age} })-[r:LIVES_AT]->(a:Address { housenumber: {housenumber}, streetname: {streetname}, city: {city}, state: {state}, country: {country} })';

function init() {
  var count = 0;
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
      count++;
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
        count++;
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
          if (err) throw err;
          count++;
          db.cypher({
            query: queryTemplate,
            params: {
              firstname: 'Hans',
              lastname: 'Wurst',
              sex: 'male',
              age: 54,
              housenumber: 20,
              streetname: 'Kaiserjägerstraße',
              city: 'Innsbruck',
              state: 'Tirol',
              country: 'Österreich',
            }
          }, function(err, results) {
            if (err) throw err;
            count++;
            db.cypher({
              query: queryTemplate,
              params: {
                firstname: 'Julia',
                lastname: 'Cooper',
                sex: 'female',
                age: 29,
                housenumber: 7,
                streetname: 'Adamgasse',
                city: 'Innsbruck',
                state: 'Tirol',
                country: 'Österreich',
              }
            }, function(err, results) {
              if (err) throw err;
              count++;
              db.cypher({
                query: queryTemplate,
                params: {
                  firstname: 'Michael',
                  lastname: 'Sparrow',
                  sex: 'male',
                  age: 43,
                  housenumber: 11,
                  streetname: 'Gaswerkstraße',
                  city: 'Innsbruck',
                  state: 'Tirol',
                  country: 'Österreich',
                }
              }, function(err, results) {
                if (err) throw err;
                count++;
                db.cypher({
                  query: queryTemplate,
                  params: {
                    firstname: 'Max',
                    lastname: 'Mustermann',
                    sex: 'male',
                    age: 40,
                    housenumber: 29,
                    streetname: 'Dorfgasse',
                    city: 'Innsbruck',
                    state: 'Tirol',
                    country: 'Österreich',
                  }
                }, function(err, results) {
                  if (err) throw err;
                  count++;
                  db.cypher({
                    query: queryTemplate,
                    params: {
                      firstname: 'Michaela',
                      lastname: 'Musterfrau',
                      sex: 'female',
                      age: 38,
                      housenumber: 147,
                      streetname: 'Reichenauerstraße',
                      city: 'Innsbruck',
                      state: 'Tirol',
                      country: 'Österreich',
                    }
                  }, function(err, results) {
                    if (err) throw err;
                    count++;
                    db.cypher({
                      query: queryTemplate,
                      params: {
                        firstname: 'Julian',
                        lastname: 'Weiß',
                        sex: 'male',
                        age: 59,
                        housenumber: 15,
                        streetname: 'Fritz-Pregl-Straße',
                        city: 'Innsbruck',
                        state: 'Tirol',
                        country: 'Österreich',
                      }
                    }, function(err, results) {
                      if (err) throw err;
                      count++;
                      db.cypher({
                        query: queryTemplate,
                        params: {
                          firstname: 'Frieda',
                          lastname: 'Mentlbaum',
                          sex: 'female',
                          age: 68,
                          housenumber: 10,
                          streetname: 'Etrichgasse',
                          city: 'Innsbruck',
                          state: 'Tirol',
                          country: 'Österreich',
                        }
                      }, function(err, results) {
                        count++;
                        console.log(count + ' persons created');
                      });
                    });
                  });
                });
              });
            });
          });
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

module.exports = {
  init: init,
  getAllPersonsWithAddress: getAllPersonsWithAddress
};
