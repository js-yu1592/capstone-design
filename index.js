const express = require('express')
const bodyParser = require('body-parser');
require('dotenv').config();


const user_info=require('./routes/user_info')

const home=require('./routes/home')
// const login=require('./routes/login')
const { sequelize } = require('./models');


var admin = require("firebase-admin");
var serviceAccount = require("./graduation-f5a8d-firebase-adminsdk-3a1to-44789ec1c4.json");
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://graduation-f5a8d.firebaseio.com"
});

const app=express()



// app.set('views', path.join(__dirname, 'views'));
// app.set('view engine', 'pug');


app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json())




//app.use('/login',login)
app.use('/user_info',user_info);
app.use('/',home)
app.listen(process.env.port || 3000);
