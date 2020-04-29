package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Main2Activity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    private static final String TAG="BAAM";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //Button button_Gps=(Button)findViewById(R.id.btn_Gps);
        Button button_board=(Button)findViewById(R.id.btn_board);
        Button button_inform=(Button)findViewById(R.id.btn_inform);
        Button button_stream=(Button)findViewById(R.id.btn_stream);
        Button button_fishing=(Button)findViewById(R.id.btn_fishing);
        Button button_set=(Button)findViewById(R.id.btn_set);
//        button_Gps.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                Intent intent = new Intent(Main2Activity.this, Location.class);
//                startActivity(intent);
//                finish();
//            }
//        });


//스트리밍 화면
        button_stream.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                Intent intent = new Intent(Main2Activity.this, StreamActivity.class);
                startActivity(intent);

            }
        });
//낚시 화면
        button_fishing.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                Intent intent=new Intent(Main2Activity.this, FishingActivity.class);
                startActivity(intent);

            }
        });
//설정 화면
        button_set.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                Intent intent=new Intent(Main2Activity.this, SettingActivity.class);
                startActivity(intent);

            }
        });
//게시판 화면
        button_board.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent secondIntent=getIntent();



                String uid=secondIntent.getStringExtra("uid");

                Log.d(TAG,"basic온  uid:"+uid);
                Intent intent=new Intent(Main2Activity.this, BoardActivity.class);
                intent.putExtra("uid",uid);
                startActivity(intent);

            }
        });
// 정보 화면
        button_inform.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                Intent intent=new Intent(Main2Activity.this, InformActivity.class);
                startActivity(intent);

            }
        });
    }
}
