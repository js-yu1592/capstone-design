package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.teamproject.models.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class PostActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    private static final String TAG="BAAM";
    private FirebaseDatabase database=FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase=database.getReference(); //데이터를 데이터베이스에 쓰기 위해

    private EditText mTitle, mContents;
    private String muid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
 String uid;
        mTitle=findViewById(R.id.post_title_edit);
        mContents=findViewById(R.id.post_contents_edit);


       Intent intent=getIntent();
        findViewById(R.id.post_save_button).setOnClickListener(this);

        if(intent.hasExtra("uid")){
            muid=intent.getStringExtra("uid");

        }

    }

    @Override
    public void onClick(View v) {

        if(mAuth.getCurrentUser()!=null){
            FirebaseUser user=mAuth.getCurrentUser();
            String uid=user.getUid();
            String email=user.getEmail();
            Log.d(TAG,"user uid:"+user.getEmail());
            Toast.makeText(this,uid,Toast.LENGTH_SHORT).show();
            Post post=new Post(email,mTitle.getText().toString(), mContents.getText().toString());
            mDatabase.child("USER").child("user_info").child("Board").push().setValue(post);
            Toast.makeText(PostActivity.this,"DB에 저장완료",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(PostActivity.this, Board.class);
            startActivity(intent);



        }
    }
}
