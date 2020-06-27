package com.example.teamproject;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
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
import com.example.teamproject.adapters.MyFishAdpater;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.sql.DriverManager.println;

public class MyFishViewActivity extends AppCompatActivity {
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
    TextView contentText;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fish_view);
        Button button=findViewById(R.id.btn_delete);
        Intent intent = getIntent();
        pos = intent.getIntExtra("pos", 0);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Log.d(TAG,"MY POST VIE title : "+BoardActivity.boardArr.get(pos).getTitle());

        TextView titleText = (TextView) findViewById(R.id.titleText);
        TextView writerText = (TextView) findViewById(R.id.writerText);
        contentText = (TextView) findViewById(R.id.contentText);

        titleText.setText(MyFishAdpater.myFishArr.get(pos).getFish_name());
        writerText.setText(MyProfileActivity.UserNickname);
        Log.d(TAG,"NICKNAME11 : "+MyProfileActivity.UserNickname);
        println("\n"+"fish_length : "+MyFishAdpater.myFishArr.get(pos).fish_length+"\n");
        println("fish_weight : "+MyFishAdpater.myFishArr.get(pos).fish_weight+"\n");
        println("fish_fishing : "+MyFishAdpater.myFishArr.get(pos).fish_fishing+"\n");
        println("fish_comment : "+MyFishAdpater.myFishArr.get(pos).fish_comment+"\n");


    }
    public void println(String data){
        contentText.append(data+"\n");
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}