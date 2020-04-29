package com.example.a1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Ahang extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ahang);
    }

    public void button17Clicked(View v){
        Intent intent = new Intent(Ahang.this, MyPage.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {

        //super.onBackPressed(); }
    }

}
