
const users=require('../../models');
const repository = require('./repository')




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
  console.log(lat)
  console.log(lon)

  repository.saveFishInfo(uid,name, length, weight, lat, lon, fishing, comment);
}
exports.getFish=getFish;
exports.saveFish=saveFish;