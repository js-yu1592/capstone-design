var qs = require('querystring');
const {  users} = require('../../models')
const board_Repository= require('../board/repository')

function getInfo(email) {

  console.log("getInfo")
  return users.findAll({
      where: {
          user_email: email
      }
  })
}




function userFindOrCreate(uid, req) {
  console.log("--------------------------")


   return users.findOrCreate({
     where: {
       user_uid: uid,
       user_email:req.email,
       user_name:req.name,
       user_nickname:req.nickname,
       user_password:req.password,
       user_phone:req.phone
 
     }
   })

 }
 function getItem(email) {
   
  return users.findAll({

    attributes:['user_nickname'],
    where:{
      user_email:email
    }
  });

}

 exports.userFindOrCreate=userFindOrCreate;
 exports.getItem=getItem

exports.getInfo = getInfo;

