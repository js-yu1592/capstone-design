package com.example.teamproject.models;

import android.widget.Button;

import com.example.teamproject.R;

public class Post {
    private String nickname;
    private String title;
    private String contents;
    private String uid;
    public Post(String nickname) {
        this.nickname = nickname;
    }

    private Button button;
    public Post() {

    }

    public Post(String nickname, String title, String contents, String uid) {
        this.nickname = nickname;
        this.title = title;
        this.contents = contents;
        this.uid=uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "Post{" +
                "uid='" + uid+'\''+
                "nickname='" + nickname + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }


}
