
const users=require('../../models');
const repository = require('./repository')
const { authCheck } = require('./firebase')
var template=require('../../template1/template.js');

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

// function CheckToken(req) {
//   let token = req.body.token;

//   //console.log(token)
//   authCheck(token)
//     .then(decodedToken => {
//       let uid = decodedToken.uid
//       console.log(uid)
//       //repository.saveUser_uid(uid)

//     }

//     )
//   return authCheck(token)
// }



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
        <p><input type="text" name="fishing" placeholder="fishing"></p>
        <p><input type="submit" value="전송"></p>
        </form>
        `,'')
        res.end(template1)
}
function CheckToken(req) {
let token=req.credential
 console.log(req)
  authCheck(token)
    .then(decodedToken => {
      let uid = decodedToken.uid

      repository.saveUser_uid(uid)

    }

    )
  return authCheck(token)
}


function setUserInfo(req,res,next){

console.log(req.credential)
  CheckToken(req)
  .then(user=>{
    if(user){
      console.log("stop")
      console.log(user)
    }
    else{
      console.log("user undefined")

    }
  })
  repository.saveUserInfo(req.body);
  firebase
  .auth()
  .createUserWithEmailAndPassword(
    req.body.email, req.body.password
  
  ).then(userRecord=>{
    
    console.log("Succesfully created");
    res.redirect("/");
  }).catch(err=>{
    console.log('error while singup',err);
  })

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
function loginprocess(req,res,next){
firebase
.auth()
.signInWithEmailAndPassword(
  req.body.id, req.body.password
)
.then(userRecord=>{
  console.log("Successfully fetched user data:");
  res.redirect('/')
}).catch(err=>{
  console.log('error login in with email and password')
})

}
exports.login=login
exports.loginprocess=loginprocess
exports.saveUserInfo = saveUserInfo
exports.setUserInfo=setUserInfo


