var qs = require('querystring');
const {  users} = require('../../models')
const {user_fish}=require('../../models')
const { Op } = require("sequelize");


function getFishInfo(page,keyword){

   let offset1=0;
   console.log(keyword);
   console.log(page);
   console.log("Baam");
   if(page>1){
      offset1=10*(page-1); 
   }

   let param={}
   if(keyword!=undefined){
      param={
         fish_name:{
            [Op.like]:"%"+keyword+"%"
         }
      }
   }
   return user_fish.findAll({
    
      where:param,
      limit:10,
      offset:offset1,
    
   })

    

}

exports.getFishInfo=getFishInfo;