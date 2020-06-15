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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.teamproject.models.User;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class MyProfileActivity extends AppCompatActivity {
    static RequestQueue requestQueue;
    TextView textView;
    TextView textView2;
    TextView textView6;
    TextView textView7;
    TextView textView8;


    public static String UserNickname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

//        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowCustomEnabled(true);
//        actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
//        actionBar.setDisplayHomeAsUpEnabled(true);


        textView=(TextView)findViewById(R.id.myProfile);
        textView2=(TextView)findViewById(R.id.textView2);
        textView6=(TextView)findViewById(R.id.textView6);
        textView7=(TextView)findViewById(R.id.textView7);
        textView8=(TextView)findViewById(R.id.textView8);

        //이런식으로 바꿀 텍스트를 전부 다 만들어 ㅇ ㅇㅇ ㄱ클?ㅣ꼬 그리고?
        Button btn_Mypost=(Button)findViewById(R.id.btn_MyPost);
        Button btn_MyFishTank=(Button)findViewById(R.id.btn_MyFishTank);
        if(requestQueue==null){
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }
        makeRequest();

        btn_Mypost.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent=new Intent(MyProfileActivity.this, MyPostActivity.class);
                //intent.putExtra("uid",uid);
                startActivity(intent);

            }
        });
        btn_MyFishTank.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent=new Intent(MyProfileActivity.this, MyFishActivity.class);
                //intent.putExtra("uid",uid);
                startActivity(intent);

            }
        });
    }

    public void makeRequest(){
        String myboard_url= "https://kpu-lastproject.herokuapp.com/user_info/my?email="+MainActivity.UserEmail;

        StringRequest request=new StringRequest(Request.Method.GET,myboard_url,new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                processResponse(response);
            }
            public void processResponse(String response){
                Gson gson=new Gson();
                myProfileList myProfileList =gson.fromJson(response, myProfileList.class);

                //만약 그 바꿀꺼 이름이 textView 면 이런식으로 하면
                //바껴서 채워짐 끝?ㅇㅇ ㅈ밥이네 ㄱ끄너ㅣㅈ껴ㅏ꺼져 이제 제발 내가꺼지는게 아니라 형이
                textView2.setText("이름 : " +myProfileList.my_profile.get(0).user_name);
                textView6.setText("닉네임 : " +myProfileList.my_profile.get(0).user_nickname);
                textView7.setText("이메일 : " +myProfileList.my_profile.get(0).user_email);
                textView8.setText("핸드폰 번호 : " +myProfileList.my_profile.get(0).user_phone);

//                println("이름 : " + myProfileList.my_profile.get(0).user_name);
//                println("닉네임 : " + myProfileList.my_profile.get(0).user_nickname);
//                println("이메일 : " + myProfileList.my_profile.get(0).user_email);
//                println("핸드폰 번호 : " + myProfileList.my_profile.get(0).user_phone);
                UserNickname = myProfileList.my_profile.get(0).user_nickname;

            }
        },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        println("에러 -> "+error.getMessage());
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
    public void println(String data){
        textView.append(data+"\n");
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.join, menu);
//        return true;
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        switch(item.getItemId()){
//            case android.R.id.home:
//                finish();
//                return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

}
