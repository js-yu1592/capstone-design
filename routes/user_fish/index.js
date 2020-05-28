
const express = require('express');
const sequelize =require('sequelize')
var router = express.Router();
const service = require('./service.js')
var template=require('../../template1/template.js');
const { firebaseAuth } = require('../middleware')


router


.get('/fish',firebaseAuth,service.getFish)


module.exports = router