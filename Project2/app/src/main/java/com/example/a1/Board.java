package com.example.a1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Board extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
    }
    public void button5Clicked(View v){
        Intent intent = new Intent(Board.this, Main.class);
        startActivity(intent);
    }

    public void button25Clicked(View v){
        Intent intent = new Intent(Board.this, Writing.class);
        startActivity(intent);
    }

}
