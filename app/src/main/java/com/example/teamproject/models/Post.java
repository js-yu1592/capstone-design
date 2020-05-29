package com.example.teamproject.models;

import android.widget.Button;

import com.example.teamproject.R;

public class Post {
    private String documentId;
    private String title;
    private String contents;
     private String uid;
    public Post(String documentId) {
        this.documentId = documentId;
    }

    private Button button;
    public Post() {

    }

    public Post(String documentId, String title, String contents, String uid) {
        this.documentId = documentId;
        this.title = title;
        this.contents = contents;
        this.uid=uid;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
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
                "documentId='" + documentId + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }


}
