package com.example.teamproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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


import com.android.volley.AuthFailureError;
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

import okhttp3.Request;

public class BasicActivity extends AppCompatActivity {
    Toolbar toolbar;

    FirebaseAuth firebaseAuth;
    private static final String TAG = "BAAM";
    static RequestQueue requestQueue;
    public static ArrayList<fishListResult> fishArr = new ArrayList<fishListResult>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.

        ImageButton button_board = (ImageButton) findViewById(R.id.btn_board);
        ImageButton button_inform = (ImageButton) findViewById(R.id.btn_inform);
        ImageButton button_stream = (ImageButton) findViewById(R.id.btn_stream);
        ImageButton button_fishing = (ImageButton) findViewById(R.id.btn_fishing);


        if(requestQueue==null){
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }

        button_fishing.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(BasicActivity.this, bluetoothActivity.class);
                startActivity(intent);

            }
        });

        button_stream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BasicActivity.this, SearchYoutubeActivity.class);
                startActivity(intent);

            }
        });

        button_board.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent secondIntent = getIntent();


                String uid = secondIntent.getStringExtra("uid");

                Log.d(TAG, "basic온  uid:" + uid);
                Intent intent = new Intent(BasicActivity.this, Board.class);
                //  intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });
        button_inform.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(BasicActivity.this, InformActivity.class);
                makeRequest();
                Log.d(TAG,"물고기 파싱 : "+fishArr);
                startActivity(intent);

            }
        });

    }

    private void setSupportActionBar(Toolbar toolbar) {

    }

    public void makeRequest() {
        String fish_url = "https://kpu-lastproject.herokuapp.com/user_fish/fish";
        StringRequest request = new StringRequest(com.android.volley.Request.Method.GET, fish_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                processResponse(response);
            }

            public void processResponse(String response) {
                Gson gson = new Gson();
                FishTankList fishTankList = gson.fromJson(response, FishTankList.class);

                fishArr = fishTankList.fish;


            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "에러", Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
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
                Intent intent = new Intent(BasicActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.page:
                Intent intent2 = new Intent(BasicActivity.this, MyProfileActivity.class);
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
    public void onBackPressed () {
        super.onBackPressed();
    }

}