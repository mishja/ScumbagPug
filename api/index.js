const mysql = require('mysql');
const pool = mysql.createPool({
host: "iosense.tudelft.nl",
port: "3306",
user: "user11",
password: "0WXMB3sO",
database: "database11",
connectionLimit: 100,
multipleStatements: true,
debug: false
});

const express = require('express');
const path = require('path');
const app = express();
const bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({limit: '50mb', extended: true}));
app.use(bodyParser.json({limit: '50mb'}));

/*** me creating api ***/

app.get('/',(request,response)=>{
response.send("Hello Mischa!");
});

app.listen(8081, 'localhost');
console.info('Running on http://localhost:8081');

// in terminal: node index.js    opvragen werkt niet.
// http://localhost:8081/     in browers geeft ook geen tekst