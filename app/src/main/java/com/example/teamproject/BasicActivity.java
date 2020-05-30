package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class BasicActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    private static final String TAG="BAAM";
    static RequestQueue requestQueue;
    public static ArrayList<fishListResult> fishArr=new ArrayList<fishListResult>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        Button button_Gps = (Button) findViewById(R.id.btn_Gps);
        Button button_Board = (Button) findViewById(R.id.btn_board);
        Button button_inform = (Button) findViewById(R.id.btn_inform);
        Button button_stream = (Button) findViewById(R.id.btn_stream);
        Button button_fishing = (Button) findViewById(R.id.btn_fishing);
        Button button_set = (Button) findViewById(R.id.btn_set);


        button_fishing.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(BasicActivity.this, SettingActivity.class);
                startActivity(intent);

            }
        });

        button_stream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BasicActivity.this, StreamActivity.class);
                startActivity(intent);

            }
        });
        button_set.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(BasicActivity.this, SettingActivity.class);
                startActivity(intent);

            }
        });
        button_Board.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent secondIntent=getIntent();



                String uid=secondIntent.getStringExtra("uid");

                Log.d(TAG,"basic온  uid:"+uid);
                Intent intent=new Intent(BasicActivity.this,Board.class);
                //  intent.putExtra("uid",uid);
                startActivity(intent);
                finish();
            }
        });
        button_inform.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(BasicActivity.this, InformActivity.class);
                startActivity(intent);

            }
        });
//        if (requestQueue == null) {
//            requestQueue = Volley.newRequestQueue(getApplicationContext());
//        }
////        makeRequest();
//    }


//    public void makeRequest(){
//        String fish_url="https://kpu-lastproject.herokuapp.com/user_fish/fish";
//        StringRequest request=new StringRequest(Request.Method.GET,fish_url,new Response.Listener<String>(){
//            @Override
//            public void onResponse(String response){
//                processResponse(response);
//            }
//            public void processResponse(String response){
//                Gson gson=new Gson();
//                FishTankList FishTankList=gson.fromJson(response, FishTankList.class);
//
//
//                fishArr=FishTankList.fish;
//                fishArr.get(0).fish_lat="37.2836834";
//                fishArr.get(0).fish_lon="126.9024348";
//                fishArr.get(1).fish_lat="37.3657562";
//                fishArr.get(1).fish_lon="126.8555483";
//            }
//        },
//                new Response.ErrorListener(){
//                    @Override
//                    public void onErrorResponse(VolleyError error){
//                        Toast.makeText(getApplicationContext(),"에러",Toast.LENGTH_LONG).show();
//                    }
//                }
//        ){
//            @Override
//            protected Map<String,String> getParams() throws AuthFailureError {
//                Map<String,String> params=new HashMap<String,String>();
//                return params;
//            }
//        };
//        request.setShouldCache(false);
//        requestQueue.add(request);
    }
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }



    }

