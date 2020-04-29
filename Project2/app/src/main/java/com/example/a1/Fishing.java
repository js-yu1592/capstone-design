package com.example.a1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Fishing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fishing);
    }

    public void button4Clicked(View v){
        Intent intent = new Intent(Fishing.this, Main.class);
        startActivity(intent);
    }

    public void button10Clicked(View v){
        Intent intent = new Intent(Fishing.this, First.class);
        startActivity(intent);
    }
}
