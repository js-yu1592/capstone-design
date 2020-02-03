
const express = require('express')
const router = express.Router()
const service = require('./service')

router
    
    .get('/', service.getShow)
    
   
module.exports = router