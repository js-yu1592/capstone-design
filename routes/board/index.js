
const express = require('express')
const router = express.Router()
const service = require('./service')
var template=require('../../template1/template.js');
const { firebaseAuth } = require('../middleware')

    
    router.post('/writeFeed',service.getWriteFeed)
 
    router.get('/create', (req, res) => {
    var title='User-Create';
    var template1=template.HTML(title,   `<form action="/feed/create" method="post">
  
    
    <p><textarea name="board" cols="40" rows="20"  placeholder="게시판"></textarea> </p>
    <p><input type="submit" value="전송"></p>
    </form>`,'');
  
      console.log("fuck")
      res.send(template1);
  })
  
  
  
router.get('/', service.getAllData) 
 router.get('/searchFeed',service.getUserContent)
 router.post('/removeFeed',service.getRemoveContent)
module.exports = router
