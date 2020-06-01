
const express = require('express');
var router = express.Router();
const service = require('./service.js')


var template=require('../../template1/template.js');
const { firebaseAuth } = require('../middleware')
//new data로 들어가면 크롤링하게


router

//.get('/',service.getbasic)


.post('/login',firebaseAuth,service.loginprocess)

.get('/my', service.getProfile)

module.exports = router;