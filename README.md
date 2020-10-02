# MongoDB-Selenium-WebScrapping
MongoDB-Selenium-WebScrapping

# setup the ExpressJs - MongoDB project:
npm init -y
npm install --save express mongodb
touch index.js



# Script to use ExpressJS with MongoDB:

// Import express
var express = require('express');
var app = express();

// Get MongoClient
var MongoClient = require('mongodb').MongoClient;

// db url, collection name and db name
const dburl = 'mongodb://localhost:27017';
const dbname = 'automationdb';
const collname = 'live';

// process root url '/'
app.get('/', function(req, res) {

  // connect to DB
  MongoClient.connect(dburl, function(err, client) {
    if (!err) {

      // Get db
      const db = client.db(dbname);

      // Get collection
      const collection = db.collection(collname);

      // Find all documents in the collection
      collection.find({}).toArray(function(err, todos) {
        if (!err) {

          // write HTML output
          var output = '<html><header><title>Automation Report</title></header><body>';
          output += '<h1>My Test Automation Live Report</h1>';
          output += '<table border="1"><tr> <td><b>' + 'Method Name' + '</b></td> <td><b>' + 'Class Name' + '</b></td> <td><b>' + 'Status' + '</b>   </td></tr>';

          // process todo list
          todos.forEach(function(todo){
            output += '<tr><td>' + todo.methodName + '</td><td>' + todo.className + '</td><td>' + todo.status + '</td></tr>';
          });

          // write HTML output (ending)
          output += '</table></body></html>'

          // send output back
          res.send(output);

          // log data to the console as well
          console.log(todos);
        }
      });

      // close db client
      client.close();
    }
  });
});

// listen on port 3000
app.listen(3000, function() {
  console.log('Example app listening on port 3000!')
});

