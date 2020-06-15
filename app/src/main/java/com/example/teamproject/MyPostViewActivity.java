package com.example.teamproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.teamproject.adapters.MyPostAdapter;

public class MyPostViewActivity extends AppCompatActivity {
    private static final String TAG="BAAM";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post_view);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        actionBar.setDisplayHomeAsUpEnabled(true);


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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.join, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}