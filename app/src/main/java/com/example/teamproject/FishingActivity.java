package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FishingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fishing);
    }

    public void btnBackClicked(View v){
        Intent intent = new Intent(FishingActivity.this, BasicActivity.class);
        startActivity(intent);
    }

    public void btnHomeClicked(View v){
        Intent intent = new Intent(FishingActivity.this, BasicActivity.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
