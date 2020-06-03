
const users=require('../../models');
const repository = require('./repository')
const { authCheck } = require('./firebase')
var template=require('../../template1/template.js');
const { fireabseAuth } = require('../middleware')
const firebase=require("firebase")

var firebaseConfig = {
  apiKey: "AIzaSyA5I8jNkgH19JGRXPVz523OCAQHGjAxSYw",
  authDomain: "graduation-f5a8d.firebaseapp.com",
  databaseURL: "https://graduation-f5a8d.firebaseio.com",
  projectId: "graduation-f5a8d",
  storageBucket: "graduation-f5a8d.appspot.com",
  messagingSenderId: "767170513426",
  appId: "1:767170513426:web:3926482e507c84c72d26cb",
  measurementId: "G-J08EVYWLEL"
};
// Initialize Firebase
firebase.initializeApp(firebaseConfig);




function saveUserInfo(req, res) {
     
       var title=`WEB-create`;
       var template1=template.HTML(title,
        `<form action="/user_info/join" method="post">
        <p><input type="text" name="id", placeholder="id"></p>
        <p><input type="password" name="password" placeholder="password"></p>
        <p><input type="text" name="name" placeholder="name"></p>
        <p><input type="text" name="nickname" placeholder="nickname"></p>
        <p><input type="text" name="email" placeholder="email"></p>
        <p><input type="text" name="phone" placeholder="phone"></p>
    </p>
        <p><input type="submit" value="전송"></p>
        </form>
        `,'')
        res.send(template1)
}






function login(req,res){
  var title=`WEB-create`;
  var template1=template.HTML(title,
   `<form action="/user_info/login" method="post">
   <p><input type="text" name="id", placeholder="id"></p>
   <p><input type="password" name="password" placeholder="password"></p>
   <p><input type="submit" value="전송"></p>
   </form>
   `,'')
   res.end(template1)
}


function loginprocess(req,res){
  console.log("here1")
 // console.log(req.body.idToken)
//  console.log(req.body)
firebase
.auth()
.signInWithEmailAndPassword(
  
  req.body.id, req.body.password
)

.then(userRecord=>{
 // console.log(req.body.id);
  console.log("Successfully fetched user data:");
  res.json({email:req.body.id, password:req.body.password});
  res.redirect('/')
}).catch(err=>{
  console.log('error login in with email and password')
})

}






function getProfile(req, res) {
  let email=req.query.email
  console.log(email)
repository.getInfo(email)
.then(result=>{
  console.log("result : "+result)
  res.json({
    my_profile:result
  })
})

}

function getItemDetail(req, res) {
  var email = req.query.email

  console.log(email)
  repository.getItem(email)
    .then(result => {
      res.json({
        my_nickname:result
      
      })
    })

}

exports.getProfile=getProfile;
exports.getItemDetail=getItemDetail
exports.loginprocess=loginprocess
