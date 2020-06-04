package com.example.teamproject;

import android.widget.CheckBox;

public class myDeleteResult {
    String board_title;

    String board_nickname;
    String board_content;
    String status;


    public String getBoard_title() {
        return board_title;
    }

    public void setBoard_title(String board_title) {
        this.board_title = board_title;
    }

    public String getBoard_nickname() {
        return board_nickname;
    }

    public void setBoard_nickname(String board_nickname) {
        this.board_nickname = board_nickname;
    }

    public String getBoard_content() {
        return board_content;
    }

    public void setBoard_content(String board_content) {
        this.board_content = board_content;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getStatus() {
        return status;
    }

}
