

var qs = require('querystring');
const {comment} = require('../../models')
const { Sequelize } = require("sequelize");

function saveComment1(uid,nickname,comment) {
console.log(uid)
    console.log(nickname);
    console.log(comment)
  return comment.create({

      cmt_uid: uid,
      cmt_nickname: nickname,
    cmt_context: comment
   
  })

}

exports.saveComment1=saveComment1