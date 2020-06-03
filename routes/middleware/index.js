
const { authCheck } = require('../user_fish/firebase');

const { authCheck1 } = require('../user_fish/firebase')

const { userCheck } = require('../user_fish/firebase');
const user_infoRepository = require('../user_info/repository')
const board_Repository= require('../board/repository')
var admin = require("firebase-admin");

function firebaseAuth(req, res, next) {
    let token =req.body.idToken;
    
//    console.log(req.body)
//    board_Repository.writeBoard(uid,req.body)
   console.log("here")
     console.log(req.body)
  console.log(token)
  
     authCheck(token)
  
        .then(decodedToken => {
            let uid=decodedToken.uid;
             console.log("decodedToken: "+decodedToken)
       
            // console.log(uid)
         //    return board_Repository.writeBoard(uid,req.body)
         return user_infoRepository.userFindOrCreate(uid, req.body)
          
    
        })
     
    
        .catch(e => {
    
            res.json(e)
        })
    
    
        // .catch(e => {
    
        //     res.json(e)
        // })
        // userCheck(req.body.uid)
        // .then(userRecord=>{
        //     console.log(userRecord)
        //    console.log("userRecord : "+userRecord.UserRecord.title)

        //     return board_Repository.writeBoard(userRecord.uid, userRecord.email, userRecord.title,userRecord.content)
        //     //console.log('Successfully fetched user data:', userRecord.toJSON())
        // }).catch(e=>{
        //     console.log('Error fetching user data:', e);
        // })

}

exports.firebaseAuth = firebaseAuth;