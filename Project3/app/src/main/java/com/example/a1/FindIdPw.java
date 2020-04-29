package com.example.a1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FindIdPw extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findidpw);
    }

    public void buttonBClicked(View v){
        Intent intent = new Intent(FindIdPw.this, First.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        //super.onBackPressed(); }
    }
}
