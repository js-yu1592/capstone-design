
var admin = require("firebase-admin");



function authCheck(token) {
    
    return admin.auth().verifyIdToken(String(token))
};

// function userCheck(uid){
//     return admin.auth().getUser(uid)
// }

exports.authCheck = authCheck;
// exports.userCheck=userCheck;