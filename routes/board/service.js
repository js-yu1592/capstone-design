
const user_info=require('../../models');
const repository = require('./repository')


const { userCheck } = require('../user_fish/firebase');

function getWriteFeed(req, res) {

   let email=req.body.email
    let title=req.body.title;
    let uid=req.body.uid;
    let content =req.body.content;
    let nickname=req.body.nickname
    console.log("board here")
    console.log(req.body.nickname)
    
    
  if (uid) {
    repository.writeBoard(uid, nickname,  content, title,email)
    .then(response => {
      res.json({ status: "ok", message: "글작성 완료" })
    })
    .catch(error => {
      res.json({ status: "fail", message: error.message })
    })
  } else {
    res.status(403).json({completed:true});
  }
        
}


function getAllData(req,res){

  var page=req.query.page
  console.log(page)
  repository.getUseralldata(page)
  .then(result=>{
    res.json({
      board:result
   
    })
  })
}

function getUserContent(req, res) {

  var page=req.query.page
 var email=req.query.email

 console.log("email :"+email)
 repository.getUserFeed(page,email)
 .then(result=>{
   res.json({
    my_board:result
   })
 })

}

function getRemoveContent(req,res){

  var title=req.body.title;
  console.log(title);
  repository.removeFeed(title)
  .then(result=>{
    res.json({
      status: "ok", message: "글작성 완료" 
    })
  })

}

exports.getWriteFeed = getWriteFeed

exports.getAllData=getAllData
exports.getRemoveContent=getRemoveContent
exports.getUserContent=getUserContent