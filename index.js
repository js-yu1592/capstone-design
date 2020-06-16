const express = require('express')
const port =process.env.PORT||3000;
const app=express()
const bodyParser = require('body-parser');
require('dotenv').config();



// const login=require('./routes/login')
const { sequelize } = require('./models');


var admin = require('firebase-admin');
var serviceAccount = require("./graduation-f5a8d-firebase-adminsdk-3a1to-44789ec1c4.json");
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://graduation-f5a8d.firebaseio.com"
});
var comment=require('./routes/comment')
var user_info=require('./routes/user_info')
var user_fish=require('./routes/user_fish')
var home=require('./routes/home')
var board=require('./routes/board')
// app.set('views', path.join(__dirname, 'views'));
// app.set('view engine', 'pug');
console.log(app.get('env'));

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json())
app.use(express.static('public'));





//app.use('/login',login)
app.use('/user_fish',user_fish);
app.use('/user_info',user_info);
app.use('/',home)
app.use('/board',board)
app.use('/comment',comment)
app.listen(port, ()=>console.log('Example app listening on port'))
