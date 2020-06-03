package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teamproject.adapters.MyPostAdapter;

public class MyPostViewActivity extends AppCompatActivity {
    private static final String TAG="BAAM";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post_view);

        Intent intent = getIntent();
        int pos = intent.getIntExtra("pos", 0);
//        if (pos == 0) {
//            Toast.makeText(getApplicationContext(), "에러", Toast.LENGTH_LONG).show();
//        } else {
            TextView titleText = (TextView) findViewById(R.id.titleText);
            TextView writerText = (TextView) findViewById(R.id.writerText);
            TextView contentText = (TextView) findViewById(R.id.contentText);

            titleText.setText(MyPostAdapter.myPostArr.get(pos).getBoard_title());
            writerText.setText(MyProfileActivity.UserNickname);
            Log.d("TAG","NICKNAME11 : "+MyProfileActivity.UserNickname);
            contentText.setText(MyPostAdapter.myPostArr.get(pos).getBoard_content());
       // }
    }
}