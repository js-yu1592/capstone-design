package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.teamproject.adapters.CommentAdapter;
import com.example.teamproject.adapters.MyPostAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.sql.DriverManager.println;

public class MyPostViewActivity extends AppCompatActivity {
    private static final String TAG="BAAM";
    private FirebaseDatabase database=FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase=database.getReference(); //데이터를 데이터베이스에 쓰기 위해
    private RecyclerView recyclerView;

    private RecyclerView.LayoutManager layoutManager;
    private  CommentList commentList;
    private ListView comment_list;
    public static CommentAdapter adapter;
    private final Handler handler=new Handler();
    public static ArrayList<CommentResult> commentArr= new ArrayList<CommentResult>();
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    FirebaseUser user=mAuth.getCurrentUser();
    static RequestQueue requestQueue;
    String uid;
   int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post_view);
        Button button=findViewById(R.id.btn_delete);
        comment_list=(ListView)findViewById(R.id.comment_list);
        Intent intent = getIntent();
         pos = intent.getIntExtra("pos", 0);
        if(requestQueue==null){
            requestQueue= Volley.newRequestQueue(getApplicationContext());
            Log.d(TAG,"requestQueue:"+requestQueue);
        }

        Log.d(TAG,"MY POST VIE title : "+MyPostAdapter.myPostArr.get(pos).getBoard_title());

            TextView titleText = (TextView) findViewById(R.id.titleText);
            TextView writerText = (TextView) findViewById(R.id.writerText);
            TextView contentText = (TextView) findViewById(R.id.contentText);

            titleText.setText(MyPostAdapter.myPostArr.get(pos).getBoard_title());
            writerText.setText(MyProfileActivity.UserNickname);
            Log.d(TAG,"NICKNAME11 : "+MyProfileActivity.UserNickname);
            contentText.setText(MyPostAdapter.myPostArr.get(pos).getBoard_content());

        makeRequest();

    }
    private void doTheAutoRefresh(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                doTheAutoRefresh();
                makeRequest();

            }
        },3000);
    }

    public void makeRequest(){
        Log.d(TAG,"MY POSTIVEW makeReuqest 시작");
        String comment_url= "https://kpu-lastproject.herokuapp.com/comment/getCmt?title="+MyPostAdapter.myPostArr.get(pos).getBoard_title();

        StringRequest request=new StringRequest(Request.Method.GET,comment_url,new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                processResponse(response);
            }
            public void processResponse(String response){
                Gson gson=new Gson();
                // commentArr.clear();
                CommentList commentList =gson.fromJson(response, CommentList.class);
                commentArr.clear();
                commentArr=commentList.comment;
                if(commentList.comment.size()==0){
                    Toast.makeText(getApplicationContext(),"작성된 댓글이 없습니다.",Toast.LENGTH_LONG).show();
                }
                else {
//                    println("글 제목 : " + commentList.comment.get(0).board_title);
//                    println("닉네임 : " + commentList.comment.get(0).cmt_nickname);
//                    println("댓글내용 : " + commentList.comment.get(0).cmt_context);
                    Log.d(TAG,"글 제목 파싱 mypostview: "+commentList.comment.get(0).board_title);
                    Log.d(TAG,"닉네임 파싱 mypostview: "+commentList.comment.get(0).cmt_nickname);
                    Log.d(TAG,"댓글 내용 파싱 mypostview: "+commentList.comment.get(0).cmt_context);
                    adapter=new CommentAdapter();


                    for(int i=0; i<commentArr.size(); i++){


                        adapter.addItem(commentList.comment.get(i).cmt_nickname,commentList.comment.get(i).cmt_context);


                    }


                    comment_list.setAdapter(adapter);


                }

            }
        },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        println("댓글이 없습니다. -> "+error.getMessage());
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
        doTheAutoRefresh();
    }



}