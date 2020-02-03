var template=require('../../template1/template.js');
// // const passport=require('passport');
// const LocalStrategy=require('passport-local').Strategy;
const user_info=require('../../models');
const repository = require('./repository')



function getShow(req, res) {
     
        var title='Welcome';
        var description='<h1>Welcome to our webpage</h1>';
        var template1=template.HTML(title,
                `<h2>${title}</h2>${description}
                `,
                `<a href="/user_info/create">회원가입</a>
                <a href="/user_info/login">로그인</a>`);
        res.end(template1);
}

exports.getShow = getShow



