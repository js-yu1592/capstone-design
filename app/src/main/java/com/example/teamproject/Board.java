package com.example.teamproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.teamproject.adapters.CustomAdapter;
import com.example.teamproject.adapters.PostAdapter;
import com.example.teamproject.models.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Board extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<Post> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private FirebaseAuth mAuth=FirebaseAuth.getInstance();


    private FirebaseAuth firebaseAuth;

    private static final String TAG="BAAM";
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

     private String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);


       recyclerView=findViewById(R.id.main_recyclerview); //아이디 연결
        recyclerView.setHasFixedSize(true); //리사이클러뷰 기존 성능강화
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Log.d("TAG","Board오는 UID:"+uid);

        arrayList=new ArrayList<>();   // Boardd 객체담을 어레이 리스트 (어댑터쪽으로 날리기위해)
        Intent secondIntent=getIntent();
        uid=secondIntent.getStringExtra("uid");
        database=FirebaseDatabase.getInstance();  //Firebase 데이터베이스 연동
        databaseReference=database.getReference("USER").child("user_info").child("Board"); //DB 테이블 연결

        findViewById(R.id.main_post_edit).setOnClickListener(this);


       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override

           public void onDataChange( DataSnapshot dataSnapshot) {
               Log.d(TAG,"user:"+"sada");


               //파이어베이스 데이터베이스의 데이터를 받아오는 곳
               arrayList.clear();  //기존 배열리스트가 존재하지 않게 초기화
               for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                   //반복문으로 데이터 리스트 추출

                   Post post=snapshot.getValue(Post.class);   //만들어뒀던 post 객체의 데이터들을 담는다
                   arrayList.add(post);  //담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비



               }
                adapter.notifyDataSetChanged(); //리스트 저장 및 새로고침
           }

           @Override
           public void onCancelled( DatabaseError databaseError) {
         //디비 가져오던 중 에러발생 시

               Log.e("BoardAcitivity", String.valueOf(databaseError.toException()));
           }
       });
            adapter=new CustomAdapter(arrayList,this);
            recyclerView.setAdapter(adapter); //리사이클러뷰 어댑터 연결








    };






    @Override
    public void onClick(View v) {

       startActivity(new Intent(this,PostActivity.class));
    }

}
