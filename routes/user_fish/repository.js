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

function getUserFishInfo(nickname){

   return user_fish.findAll({
      where : {
         fish_nickname :nickname
      }
   })
}

function saveFishInfo(uid,nickname,name, length, weight, lat, lon, fishing, comment){

   return user_fish.create({

      fish_uid: uid,
      fish_nickname: nickname,
      fish_name: name,
      fish_length:length,
    fish_weight: weight,
    fish_comment:comment,
    fish_fishing:fishing,
    fish_lat:lat,
    fish_lon:lon,
   
  })

}

exports.getUserFishInfo=getUserFishInfo;

exports.getFishInfo=getFishInfo;
exports.saveFishInfo=saveFishInfo