package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teamproject.adapters.CommentAdapter;
import com.example.teamproject.adapters.MyPostAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyPostViewActivity extends AppCompatActivity {
    private static final String TAG="BAAM";
    private FirebaseDatabase database=FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase=database.getReference(); //데이터를 데이터베이스에 쓰기 위해
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    FirebaseUser user=mAuth.getCurrentUser();
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post_view);
        Button button=findViewById(R.id.btn_delete);

        Intent intent = getIntent();
        int pos = intent.getIntExtra("pos", 0);
        Log.d(TAG,"MY POST VIE pos : "+pos);

            TextView titleText = (TextView) findViewById(R.id.titleText);
            TextView writerText = (TextView) findViewById(R.id.writerText);
            TextView contentText = (TextView) findViewById(R.id.contentText);

            titleText.setText(MyPostAdapter.myPostArr.get(pos).getBoard_title());
            writerText.setText(MyProfileActivity.UserNickname);
            Log.d("TAG","NICKNAME11 : "+MyProfileActivity.UserNickname);
            contentText.setText(MyPostAdapter.myPostArr.get(pos).getBoard_content());

//
//            adapter = new CommentAdapter();
//
//            listView=(ListView) findViewById(R.id.listview);
//        listView.setAdapter(adapter);
//        adapter.addItem("제목 1 ", "내용1");

    }



}