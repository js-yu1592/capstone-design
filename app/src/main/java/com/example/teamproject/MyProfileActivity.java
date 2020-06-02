package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class MyProfileActivity extends AppCompatActivity {
    static RequestQueue requestQueue;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        textView=(TextView)findViewById(R.id.viewMyProfile);
        Button btn_Mypost=(Button)findViewById(R.id.btn_MyPost);

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

                println("id : "+ myProfileList.my_profile.get(0).user_id);

                println("이름 : "+ myProfileList.my_profile.get(0).user_name);
                println("닉네임 : "+ myProfileList.my_profile.get(0).user_nickname);
                println("이메일 : "+ myProfileList.my_profile.get(0).user_email);
                println("핸드폰 번호 : "+ myProfileList.my_profile.get(0).user_phone);

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


}
