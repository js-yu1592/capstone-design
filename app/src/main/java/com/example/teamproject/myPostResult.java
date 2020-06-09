    package com.example.teamproject;

    public class myPostResult {
        String board_title;
        String board_nickname;
        String board_content;
        boolean isSelected;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public void setBoard_title(String board_title) {
            this.board_title = board_title;
        }


        public void setBoard_nickname(String board_nickname) {
            this.board_nickname = board_nickname;
        }

        public void setBoard_content(String board_content) {
            this.board_content = board_content;
        }



        public String getBoard_content() {
            return board_content;
        }

        public String getBoard_nickname() {
            return board_nickname;
        }

        public String getBoard_title() {
            return board_title;
        }
    }