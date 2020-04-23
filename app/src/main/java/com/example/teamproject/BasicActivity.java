package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BasicActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    private static final String TAG="BAAM";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        Button button_Gps=(Button)findViewById(R.id.btn_Gps);
        Button button_Board=(Button)findViewById(R.id.btn_board);
        button_Gps.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(BasicActivity.this, Location.class);
                startActivity(intent);
                finish();
            }
        });

        button_Board.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent secondIntent=getIntent();



                String uid=secondIntent.getStringExtra("uid");

                Log.d(TAG,"basic온  uid:"+uid);
                Intent intent=new Intent(BasicActivity.this,Board.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
                finish();
            }
        });
    }
}
