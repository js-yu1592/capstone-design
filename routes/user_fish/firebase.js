
var admin = require("firebase-admin");



function authCheck(token) {
    
    return admin.auth().verifyIdToken(String(token))
};
function authCheck1(token) {
    
    return admin.auth().verifyIdToken(String(token))
};
function userCheck(uid){
    console.log("userCheck uid: "+uid)
    return admin.auth().getUser(uid)
}
exports.authCheck1=authCheck1;
exports.authCheck = authCheck;
 exports.userCheck=userCheck;