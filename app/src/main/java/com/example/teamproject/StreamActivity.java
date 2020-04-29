package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StreamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);
    }
    public void btnBackClicked(View v){
        Intent intent = new Intent(StreamActivity.this, Main2Activity.class);
        startActivity(intent);
    }
}
