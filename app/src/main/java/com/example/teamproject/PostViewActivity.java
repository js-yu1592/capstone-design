package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class PostViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);

        Intent intent=getIntent();
        int pos=intent.getIntExtra("pos",0);


        TextView titleText=(TextView)findViewById(R.id.titleText);
        TextView writerText=(TextView)findViewById(R.id.writerText);
        TextView contentText=(TextView)findViewById(R.id.contentText);

        titleText.setText(BoardActivity.boardArr.get(pos).getTitle());
        writerText.setText(BoardActivity.boardArr.get(pos).getDocumentId());
        contentText.setText(BoardActivity.boardArr.get(pos).getContents());

    }
}
