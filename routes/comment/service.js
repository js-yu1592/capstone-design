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
    var comment=req.body.comment;
    var uid=req.body.uid;


    repository.saveComment1(uid,nickname,comment)
    .then(result=>{
        res.json({
            status: "ok", message: "글수정 완료" 
          })
    })
  
}

exports.saveComment = saveComment

