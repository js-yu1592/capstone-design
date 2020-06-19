package com.example.teamproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.teamproject.models.Comment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import android.os.Handler;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static java.sql.DriverManager.println;

public class PostViewActivity extends AppCompatActivity {
    private static final String TAG="BAAM";
    private  commentList commentList;
    private ListView comment_list;
    public static CommentAdapter adapter;
    private final Handler handler=new Handler();
    public static ArrayList<commentResult> commentArr= new ArrayList<commentResult>();
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    private Runnable runnable;
    FirebaseUser user=mAuth.getCurrentUser();
    EditText editText;
    String nickname;
    static RequestQueue requestQueue;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);

        Intent intent=getIntent();
        pos=intent.getIntExtra("pos",0);

        TextView titleText=(TextView)findViewById(R.id.titleText);
        TextView writerText=(TextView)findViewById(R.id.writerText);
        TextView contentText=(TextView)findViewById(R.id.contentText);
        Button button=(Button)findViewById(R.id.saveComment);
        editText=(EditText)findViewById(R.id.Writecomment);

        titleText.setText(BoardActivity.boardArr.get(pos).getTitle());
        writerText.setText(BoardActivity.boardArr.get(pos).getNickname());
        contentText.setText(BoardActivity.boardArr.get(pos).getContents());
        comment_list=(ListView)findViewById(R.id.comment_list);
        if(requestQueue==null){
            requestQueue= Volley.newRequestQueue(getApplicationContext());
            Log.d(TAG,"requestQueue:"+requestQueue);
        }

        makeRequest();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makeRequest1();

                //Toast.makeText(getApplicationContext(),"댓글작성이 완료되었습니다.",Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder=new AlertDialog.Builder(PostViewActivity.this);
                builder.setMessage("댓글을 등록합니다.")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                makeRequest();
                                finish();
                                startActivity(getIntent());

                            }
                        });
                builder.create();
                builder.show();


                editText.setText(null);
            }
        });


    }
//    private void doTheAutoRefresh(){
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                doTheAutoRefresh();
//                makeRequest();
//
//            }
//        },3000);
//    }
    public void makeRequest1(){
        String uid=user.getUid();
//        Log.d(TAG,"댓글 : "+ editText.getText().toString());
//        Log.d(TAG,"title : "+Board.boardArr.get(pos).getTitle());
//        Log.d(TAG,"닉네임 : "+Board.boardArr.get(pos).getNickname());
        try{
            OkHttpClient client=new OkHttpClient();
            Gson gson=new Gson();

            RequestBody formBody= new FormBody.Builder()
                    .add("comment",editText.getText().toString())
                    .add("nickname",BoardActivity.boardArr.get(pos).getNickname())
                    .add("title",BoardActivity.boardArr.get(pos).getTitle())
                    .build();
            final okhttp3.Request request1=new okhttp3.Request.Builder()
                    //.url("http://10.0.2.2:3000/comment/save_comment")
                    .url("https://kpu-lastproject.herokuapp.com/comment/save_comment")
                    .post(formBody)
                    .build();
            client.newCall(request1).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.d(TAG,"fail:"+e.toString());
                    System.out.println("error + Connection Server Error is"+e.toString());
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) throws IOException {

                    Log.d(TAG,"success:"+response.body().toString());
                    System.out.println("Response Body is "+ response.body().string());

                }
            });
        }catch(Exception e){

        }

    }

    public void makeRequest(){

        String comment_url= "https://kpu-lastproject.herokuapp.com/comment/getCmt?title="+BoardActivity.boardArr.get(pos).getTitle();

        StringRequest request=new StringRequest(Request.Method.GET,comment_url,new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                processResponse(response);
            }
            public void processResponse(String response){
                Gson gson=new Gson();
                // commentArr.clear();
                commentList commentList =gson.fromJson(response, commentList.class);
                commentArr.clear();
                commentArr=commentList.comment;
                if(commentList.comment.size()==0){
                    Toast.makeText(getApplicationContext(),"작성된 댓글이 없습니다.",Toast.LENGTH_LONG).show();
                }
                else {
//                    println("글 제목 : " + commentList.comment.get(0).board_title);
//                    println("닉네임 : " + commentList.comment.get(0).cmt_nickname);
//                    println("댓글내용 : " + commentList.comment.get(0).cmt_context);
                    Log.d(TAG,"글 제목 파싱 : "+commentList.comment.get(0).board_title);
                    Log.d(TAG,"닉네임 파싱: "+commentList.comment.get(0).cmt_nickname);
                    Log.d(TAG,"댓글 내용 파싱: "+commentList.comment.get(0).cmt_context);
                    adapter=new CommentAdapter();


                    for(int i=0; i<commentArr.size(); i++){


                        adapter.addItem(commentList.comment.get(i).cmt_nickname,commentList.comment.get(i).cmt_context);


                    }
                    adapter.notifyDataSetChanged();

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
        //doTheAutoRefresh();
    }



}