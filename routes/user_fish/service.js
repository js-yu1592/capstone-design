
const users=require('../../models');
const repository = require('./repository')


function photoUpload(req,res){

}

function getFish(req,res){
  console.log(req);
  var page=req.query.page;
  var keyword=req.query.keyword;


  repository.getFishInfo(page,keyword)
  .then(result=>{
    res.json(
   { fish:result}
    );
    console.log(result);
  })

}

function saveFish(req,res){

  var lat=req.body.lat
  var lon=req.body.lon
  var uid=req.body.uid
  var fishing=req.body.fishing
  var name=req.body.name
  var length=req.body.length
  var weight=req.body.weight
  var comment=req.body.comment
  var nickname=req.body.nickname
  console.log(lat)
  console.log(nickname)
  console.log(lon)

  repository.saveFishInfo(uid,nickname,name, length, weight, lat, lon, fishing, comment);
}

function getUserFish(req,res){

  var nickname=req.query.nickname;

  repository.getUserFishInfo(nickname)
  .then(result =>{
    res.json({
      fish: result
    })
  })

}
exports.getUserFish=getUserFish
exports.getFish=getFish;
exports.saveFish=saveFish;
exports.photoUpload=photoUpload;