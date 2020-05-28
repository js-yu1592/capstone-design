
const { authCheck } = require('../user_fish/firebase');
const user_infoRepository = require('../user_info/repository')

var admin = require("firebase-admin");

function firebaseAuth(req, res, next) {
    let token =req.body.idToken;
     console.log("here")
   //   console.log(req)
 //   console.log(token)
     admin.auth().verifyIdToken(String(token))
        .then(decodedToken => {
            let uid=decodedToken.uid;
            console.log("decodedToken: "+decodedToken)
       
            console.log(uid)
             // next();
          return user_infoRepository.userFindOrCreate(uid, req.body)
          
        })
        .then(user => {
            console.log("here2")
           // console.log(user)
            req.user = user[0]
            next();
        })
        .catch(e => {
    
            res.json(e)
        })

}

exports.firebaseAuth = firebaseAuth;