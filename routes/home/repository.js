var qs = require('querystring');
const {  User_Info} = require('../../models')

async function getData(req,cb,next){

  let body=req.body;
  let result=await User_Info.findOne({
      where:{
               id: body.login_id,
              
      }.then(function(result){
        cb(result);
      })
  });


  

}
exports.getData = getData






