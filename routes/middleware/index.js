
const { authCheck } = require('../feed/firebase');
const feedRepository = require('../feed/repository')

function firebaseAuth(req, res, next) {
    let token = req.body.token;

    authCheck(token)
        .then(decodedToken => {
            return feedRepository.saveUser_uid(decodedToken.uid)
        })
        .then(user => {
            
            req.user = user[0]
            next();
        })
        .catch(e => {
            res.json(e)
        })

}

exports.firebaseAuth = firebaseAuth