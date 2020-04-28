
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
    </p>
        <p><input type="submit" value="전송"></p>
        </form>
        `,'')
        res.send(template1)
}



function setUserInfo(req,res){


console.log(req)

  repository.saveUserInfo(req.body);

  firebase
  .auth()
  .createUserWithEmailAndPassword(
    req.body.email, req.body.password
  
  ).then(userRecord=>{
    
    console.log("Succesfully created");
    res.redirect("/");
  }).catch(err=>{
    res.json({status:"fail",message:err.message})
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


function loginprocess(req,res){
  console.log(req.body.id);   //확실히 아이디가 뜸
firebase
.auth()
.signInWithEmailAndPassword(
  
  req.body.id, req.body.password
)

.then(userRecord=>{
  console.log(req.body.id);
  console.log("Successfully fetched user data:");
  res.json({email:req.body.id, password:req.body.password});
  res.redirect('/')
}).catch(err=>{
  console.log('error login in with email and password')
})

}

function logout(req,res){
console.log("logout 되었습니다.");
firebase
.auth().signOut()
.then(userRecord=>{

  res.redirect('/user_info/login');
  
}).catch(err=>{
  console.log('error logging out',err);
})

}

function googleLogin(req,res){
  var provider = new firebase.auth.GoogleAuthProvider();
  console.log("provider :"+provider);
  firebase.auth().signInWithPopup(provider).then(function(result) {
    console.log(result)
    // This gives you a Google Access Token. You can use it to access the Google API.
    var token = result.credential.accessToken;
    // The signed-in user info.
    var user = result.user;
    // ...
  }).catch(function(error) {
    // Handle Errors here.
    var errorCode = error.code;
    var errorMessage = error.message;
    // The email of the user's account used.
    var email = error.email;
    // The firebase.auth.AuthCredential type that was used.
    var credential = error.credential;
    // ...
  });


}


function checkGoogleToken(req,res){

  var id_token=googleUser.getAuthResponse().id_token;

 
// Build Firebase credential with the Google ID token.
var credential = firebase.auth.GoogleAuthProvider.credential(id_token);

// Sign in with credential from the Google user.
firebase.auth().signInWithCredential(credential).catch(function(error) {
  // Handle Errors here.
  var errorCode = error.code;
  var errorMessage = error.message;
  // The email of the user's account used.
  var email = error.email;
  // The firebase.auth.AuthCredential type that was used.
  var credential = error.credential;
  // ...
});
    
}
function getProfile(req, res) {

  console.log(req.user.dataValues)
  res.json(req.user)
}

function getbasic(req,res){
  console.log("Hello");
}
exports.getBasic=getbasic;
exports.getProfile=getProfile;
exports.googleLogin=googleLogin;
exports.logout=logout
exports.login=login
exports.loginprocess=loginprocess
exports.saveUserInfo = saveUserInfo
exports.setUserInfo=setUserInfo

