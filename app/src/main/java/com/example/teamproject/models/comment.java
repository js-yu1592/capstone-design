package com.example.teamproject.models;

import android.util.Log;

public class comment {
    private static final String TAG = "BAAM";
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNickname() {

        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    private String content;
        private String nickname;




}
