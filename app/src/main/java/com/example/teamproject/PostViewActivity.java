package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.teamproject.adapters.CommentAdapter;
import com.example.teamproject.models.comment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class PostViewActivity extends AppCompatActivity {
    private static final String TAG="BAAM";
    private ListView comment_list;
    public static CommentAdapter adapter;
    public static ArrayList<comment> commentArr= new ArrayList<comment>();

    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    FirebaseUser user=mAuth.getCurrentUser();
   EditText editText;
   String nickname;
   int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);

        Intent intent=getIntent();
        pos=intent.getIntExtra("pos",0);



         Log.d(TAG,"PostView Nick :"+Board.boardArr.get(pos).getNickname());
        TextView titleText=(TextView)findViewById(R.id.titleText);
        TextView writerText=(TextView)findViewById(R.id.writerText);
        TextView contentText=(TextView)findViewById(R.id.contentText);
         Button button=(Button)findViewById(R.id.saveComment);
         editText=(EditText)findViewById(R.id.Writecomment);

        titleText.setText(Board.boardArr.get(pos).getTitle());
        writerText.setText(Board.boardArr.get(pos).getNickname());
        contentText.setText(Board.boardArr.get(pos).getContents());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          makeRequest1();
            }
        });
        adapter=new CommentAdapter();

        comment_list=(ListView)findViewById(R.id.comment_list);
        comment_list.setAdapter(adapter);

//        adapter.addItem(nickname,editText.getText().toString());
//        adapter.addItem("제목1:","내용1");
//        adapter.addItem("제목1:","내용1");
//        adapter.addItem("제목1:","내용1");
//        adapter.addItem("제목1:","내용1");
//
//        adapter.notifyDataSetChanged();
    }

    public void makeRequest1(){
      String uid=user.getUid();
        Log.d(TAG,"댓글 : "+ editText.getText().toString());
        Log.d(TAG,"uid : "+uid);
        Log.d(TAG,"닉네임 : "+Board.boardArr.get(pos).getNickname());
        try{
            OkHttpClient client=new OkHttpClient();
            Gson gson=new Gson();

            RequestBody formBody= new FormBody.Builder()
                    .add("comment",editText.getText().toString())
                    .add("uid",uid)
                    .add("nickname",Board.boardArr.get(pos).getNickname())
                    .build();
            final okhttp3.Request request1=new okhttp3.Request.Builder()
                    .url("http://10.0.2.2:3000/comment/save_comment")
                   //.url("https://kpu-lastproject.herokuapp.com/comment/save")
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


}