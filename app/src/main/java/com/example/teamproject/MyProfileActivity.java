package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class MyProfileActivity extends AppCompatActivity {
    static RequestQueue requestQueue;
    TextView textView;
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    public static String UserNickname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        textView=(TextView)findViewById(R.id.viewMyProfile);
        Button btn_Mypost=(Button)findViewById(R.id.btn_MyPost);
        Button btn_MyFishTank=(Button)findViewById(R.id.btn_MyFishTank);
        if(requestQueue==null){
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }
        makeRequest();
        btn_MyFishTank.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent=new Intent(MyProfileActivity.this, FishRegistActivity.class);
                //intent.putExtra("uid",uid);
                startActivity(intent);

            }
        });
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
        FirebaseUser user=mAuth.getCurrentUser();
        String email=user.getEmail();
        String myboard_url= "https://kpu-lastproject.herokuapp.com/user_info/my?email="+email;
        Log.d("TAG","email : "+email);
        StringRequest request=new StringRequest(Request.Method.GET,myboard_url,new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                processResponse(response);
            }
            public void processResponse(String response){
                Gson gson=new Gson();
                myProfileList myProfileList =gson.fromJson(response, myProfileList.class);

                if(myProfileList.my_profile.size()==0){
                    Toast.makeText(getApplicationContext(),"json파싱 실패",Toast.LENGTH_LONG).show();
                    println("json파싱 실패1111");
                }
                else {
                    println("이름 : " + myProfileList.my_profile.get(0).user_name);
                    println("닉네임 : " + myProfileList.my_profile.get(0).user_nickname);
                    println("이메일 : " + myProfileList.my_profile.get(0).user_email);
                    println("핸드폰 번호 : " + myProfileList.my_profile.get(0).user_phone);
                    UserNickname = myProfileList.my_profile.get(0).user_nickname;
                    Log.d("TAG","UserNickname : "+UserNickname);
                }
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