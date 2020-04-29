package com.example.a1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Writing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);
    }

    public void button23Clicked(View v){
        Intent intent = new Intent(Writing.this, Board.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        //super.onBackPressed(); }
    }
}
