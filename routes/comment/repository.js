

var qs = require('querystring');
const {comment} = require('../../models')
const { Sequelize } = require("sequelize");

function saveComment1(uid,nickname,comment1,title) {
console.log(uid)
    console.log(nickname);
    console.log(comment1)

return comment.create({
  board_title:title,
  cmt_uid:uid,
  cmt_context:comment1,
  cmt_nickname:nickname
})

}


function getComment1(title){
  
  return comment.findAll({
    where:{
      board_title:title
    }
  })

}
exports.getComment1=getComment1
exports.saveComment1=saveComment1