
const users=require('../../models');
const repository = require('./repository')




function getFish(req,res){
  console.log(req);
  var page=req.query.page;
  var keyword=req.query.keyword;


  repository.getFishInfo(page,keyword)
  .then(result=>{
    res.json(result);
    console.log(result);
  })

}

exports.getFish=getFish;