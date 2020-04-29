package com.example.a1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void buttonAClicked(View v) {
        Intent intent = new Intent(Main.this, Fishing.class);
        startActivity(intent);
    }

        public void buttonBClicked(View v){
            Intent intent = new Intent(Main.this, Board.class);
            startActivity(intent);
        }

        public void buttonCClicked(View v){
            Intent intent = new Intent(Main.this, Streaming.class);
            startActivity(intent);
        }


        public void buttonDClicked(View v){
            Intent intent = new Intent(Main.this, Information.class);
            startActivity(intent);
        }


        public void buttonEClicked(View v){
            Intent intent = new Intent(Main.this, Setting.class);
            startActivity(intent);
        }


//    public void buttonFClicked(View v){
//        Intent intent = new Intent(Main.this, First.class);
//        startActivity(intent);
//    }


    public void buttonGClicked(View v){
        Intent intent = new Intent(Main.this, MyPage.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        //super.onBackPressed(); }
    }


}




