

var qs = require('querystring');
const { users, board } = require('../../models')
const { Sequelize } = require("sequelize");
var moment = require('moment');
require('moment-timezone');
moment.tz.setDefault("Asia/Seoul");

const { Op } = require("sequelize");


function getUseralldata(page) {
  let offset1 = 0;
  if (page > 1) {
    offset1 = 20 * (page - 1);
  }
  return board.findAll({
    order: [['board_num', 'desc']],
    // include: [{
    //   model: users,
    //   attributes: ['user_id', 'user_name', 'user_nickname', 'user_password','user_email','user_phone','createdAt']
    //   //  where:{user_uid :Sequelize.col('users.uid')}
    // }],
    attributes: ['board_num', 'board_title', 'board_content','board_nickname','createdAt'],
    limit: 20,
    offset: offset1

  });

}


function writeBoard(uid,nickname,content,title,email) {

    console.log("writebOARD")

  return board.create({

      board_uid: uid,
      board_title: title,
      board_nickname:nickname,
    board_content: content,
    board_email:email
  })

}



function getUserFeed(page,email) {
let offset1=0;


    if(page>1){
      offset1=20*(page)-1
    }


       
    console.log(email)
     
         return board.findAll({
            attributes:['board_nickname','board_content','board_title'],
            where:{
              board_email:{
                [Op.like]:"%"+email+"%"
              }
              
            },
          
          })
        
      
  

  

}

function getUserInfo(uid) {

  return board.findAll({
    where: {
      user_uid: uid
    },
    attributes: ['id']
  })
}

function removeFeed(title){
  return board.destroy({
    where:{
      board_title : title
    }
  })
}

exports.getUseralldata = getUseralldata
exports.removeFeed=removeFeed
exports.getUserInfo = getUserInfo
exports.writeBoard = writeBoard
exports.getUserFeed = getUserFeed






  //    User_Info.findAll().then(function(result){
  //             cb(result)
  //     });
  //    db.query(sql,function(error,result){

  //       cb(result);
  //    });\

  //var result= await respository.getUseralldata();
  //res.json(result);  위에 promise랑 같은기능  await 사용하려면 async함수로 선언되어야 한다.