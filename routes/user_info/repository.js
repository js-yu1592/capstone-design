var qs = require('querystring');
const {  users} = require('../../models')

function getData(req,cb){

   User_Info.findAll().then(function(result){
            cb(result)
    });

}


function saveUserInfo(req){
console.log(req);

return users.create({
   user_id:req.id,
   user_name:req.name,
   user_nickname:req.nickname,
   user_password:req.password,
   user_email:req.email,
   user_phone:req.phone
})
}

exports.saveUserInfo=saveUserInfo
exports.getData = getData

