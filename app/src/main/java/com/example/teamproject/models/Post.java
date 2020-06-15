package com.example.teamproject.models;

import android.widget.Button;

import com.example.teamproject.R;

public class Post {
    private String documentId;
    private String title;
    private String contents;

    public Post(String documentId) {
        this.documentId = documentId;
    }

    private Button button;
    public Post() {

    }

    public Post(String documentId, String title, String contents) {
        this.documentId = documentId;
        this.title = title;
        this.contents = contents;
    }

    public Post(String nickname, String toString, String toString1, String uid) {

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

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "Post{" +
                "documentId='" + documentId + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }


}
