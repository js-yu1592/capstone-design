package com.example.teamproject;

import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public void onNewToken(String token){
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token){

    }
}
