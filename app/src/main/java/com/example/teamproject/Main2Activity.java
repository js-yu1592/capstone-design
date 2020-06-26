package com.example.teamproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {
    Toolbar toolbar;

    FirebaseAuth firebaseAuth;
    private static final String TAG = "BAAM";
    static RequestQueue requestQueue;
    public static ArrayList<fishListResult> fishArr = new ArrayList<fishListResult>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //Button button_Gps=(Button)findViewById(R.id.btn_Gps);
        ImageButton button_board = (ImageButton) findViewById(R.id.btn_board);
        ImageButton button_inform = (ImageButton) findViewById(R.id.btn_inform);
        ImageButton button_stream = (ImageButton) findViewById(R.id.btn_stream);
        ImageButton button_fishing = (ImageButton) findViewById(R.id.btn_fishing);


        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
//        actionBar.setDisplayHomeAsUpEnabled(true);

//        button_Gps.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                Intent intent = new Intent(Main2Activity.this, Location.class);
//                startActivity(intent);
//                finish();
//            }
//        });


//스트리밍 화면
        button_stream.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, SearchYoutubeActivity.class);
                startActivity(intent);

            }
        });
//낚시 화면
        button_fishing.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, bluetoothActivity.class);
                startActivity(intent);

            }
        });
//설정 화면

//게시판 화면
        button_board.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent secondIntent = getIntent();
                String uid = secondIntent.getStringExtra("uid");

                Log.d(TAG, "basic온  uid:" + uid);
                Intent intent = new Intent(Main2Activity.this, BoardActivity.class);
                //intent.putExtra("uid",uid);
                startActivity(intent);

            }
        });
// 정보 화면
        button_inform.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if (requestQueue == null) {
                    requestQueue = Volley.newRequestQueue(getApplicationContext());
                }
                makeRequest();

                Intent intent = new Intent(Main2Activity.this, InformActivity.class);
                startActivity(intent);

            }
        });

    }

    private void setSupportActionBar(Toolbar toolbar) {

    }

    public void makeRequest(){
        String fish_url="https://kpu-lastproject.herokuapp.com/user_fish/fish";
        StringRequest request=new StringRequest(Request.Method.GET,fish_url,new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                processResponse(response);
            }
            public void processResponse(String response){
                Gson gson=new Gson();
                fishTankList fishTankList=gson.fromJson(response, fishTankList.class);

                fishArr=fishTankList.fish;
               // Toast.makeText(getApplicationContext(),fishArr.get(fishArr.size()-1).getFish_name(),Toast.LENGTH_SHORT).show();
                if(fishTankList.fish.size()==0){

                }
            }
        },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(getApplicationContext(),"에러",Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String,String>();
                return params;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.setting:
                Intent intent = new Intent(Main2Activity.this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.page:
                Intent intent2 = new Intent(Main2Activity.this, MyProfileActivity.class);
                startActivity(intent2);

//            int id = item.getItemId();

//        if(id == R.id.setting){
//            Intent intent = new Intent(Main2Activity.this, SettingActivity.class);
//            startActivity(intent);
//
//        }
        }


        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

}