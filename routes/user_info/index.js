
const sequelize =require('sequelize')
const express = require('express')
const router = express.Router()
const service = require('./service.js')
var template=require('../../template1/template.js');

//new data로 들어가면 크롤링하게
router


.get('/join',service.saveUserInfo)
.post('/join',service.setUserInfo)
.get('/login',service.login)
.post('/login',service.loginprocess)

module.exports = router