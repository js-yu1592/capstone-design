var template=require('../../template1/template.js');
// // const passport=require('passport');
// const LocalStrategy=require('passport-local').Strategy;
const user_info=require('../../models');
const repository = require('./repository')


const { board } = require('../../models')
const { users } = require('../../models')
const { Sequelize } = require("../../models");

function saveComment(req, res) {

    console.log("hi")
    console.log(req.body)
    var nickname=req.body.nickname;
    var comment1=req.body.comment;
    var uid=req.body.uid;
    var title=req.body.title;


    repository.saveComment1(uid,nickname,comment1,title)
    .then(result=>{
        res.json({
            status: "ok", message: "글수정 완료" 
          })
    })
  
}

function getComment(req,res){

   title=req.query.title;
    console.log(title)
   repository.getComment1(title)
   .then(result=>{
       res.json({
           comment : result
       })
   })


}
exports.saveComment = saveComment
exports.getComment=getComment
