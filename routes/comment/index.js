
const express = require('express')
const router = express.Router()
const service = require('./service')

router
    
    .post('/save_comment',service.saveComment)
    .get('/getCmt',service.getComment)
    
   
module.exports = router