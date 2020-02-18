
const { authCheck } = require('../user_info/firebase');
const user_infoRepository = require('../user_info/repository')

function firebaseAuth(req, res, next) {
    let token = req.body.token;

    authCheck(token)
        .then(decodedToken => {
            let uid=decodedToken.uid;
            console.log(uid);
            return user_infoRepository.uidFindOrCreate(decodedToken.uid)
        })
        .then(user => {
            
            req.user = user[0]
            next();
        })
        .catch(e => {
            res.json(e)
        })

}

exports.firebaseAuth = firebaseAuth;