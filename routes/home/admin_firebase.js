var admin=require('firebase-admin');

admin.initializeApp({
    credential: admin.credential.applicationDefault(),
    databaseURL:'https://graduation-f5a8d.firebaseio.com'
});


var app=admin.initializeApp();

admin.auth().getUser(uid)
.then(function(userRecord){
    console.log('Succesfully fetched user data:',userRecord.toJSON());
}).catch(function(error){
    console.log('Error fetching user data:',error);
})

admin.auth().createUser({
    email: 'user@example.com',
    emailVerified: false,
    phoneNumber: '+11234567890',
    password: 'secretPassword',
    displayName: 'John Doe',
    photoURL: 'http://www.example.com/12345678/photo.png',
    disabled: false
  })
    .then(function(userRecord) {
      // See the UserRecord reference doc for the contents of userRecord.
      console.log('Successfully created new user:', userRecord.uid);
    })
    .catch(function(error) {
      console.log('Error creating new user:', error);
    });