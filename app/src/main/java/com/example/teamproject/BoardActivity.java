package com.example.teamproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
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
//import com.example.teamproject.adapters.PostAdapter;
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

public class BoardActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<Post> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private Button button;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();

    public static final ArrayList<Post> boardArr=new ArrayList<Post>();
    private FirebaseAuth firebaseAuth;

    private static final String TAG="BAAM";
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private RecyclerDecoration spaceDecoration;
    private String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);



        //Board는 게시판

        recyclerView=findViewById(R.id.main_recyclerview); //아이디 연결
        recyclerView.setHasFixedSize(true); //리사이클러뷰 기존 성능강화
        layoutManager=new LinearLayoutManager(this);
        spaceDecoration=new RecyclerDecoration(40);     //위아래 간격조정
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getResources())); // 왼쪽 오른쪽 마진
        recyclerView.addItemDecoration(spaceDecoration);
        // recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setLayoutManager(layoutManager);

        //layoutManager은 많은 역할을 하지만 간단하게 스크롤을 위아래로 할지 좌우로 할지 결정하는것

        Log.d("TAG","Board오는 UID:"+uid);

        arrayList=new ArrayList<>();   // Boardd 객체담을 어레이 리스트 (어댑터쪽으로 날리기위해)

        FirebaseUser user=mAuth.getCurrentUser();

        uid=user.getUid();
        database=FirebaseDatabase.getInstance();  //Firebase 데이터베이스 연동
        databaseReference=database.getReference("USER").child("Board"); //DB 테이블 연결

        findViewById(R.id.main_post_edit).setOnClickListener(this);

//        button=findViewById(R.id.main_post_delete);



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange( DataSnapshot dataSnapshot) {



                //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arrayList.clear();  //기존 배열리스트가 존재하지 않게 초기화
                boardArr.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    //반복문으로 데이터 리스트 추출
                    Log.d(TAG,"BoardActivity"+snapshot.getValue());
                    Post post=snapshot.getValue(Post.class);   //만들어뒀던 post 객체의 데이터들을 담는다
                    arrayList.add(post);  //담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                    boardArr.add(post);
                }
                adapter.notifyDataSetChanged(); //리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled( DatabaseError databaseError) {
                //디비 가져오던 중 에러발생 시

                Log.e("BoardAcitivity", String.valueOf(databaseError.toException()));
            }
        });
        adapter=new CustomAdapter(arrayList,this); //CustomAdapter로 설정.
        //어댑터는 담긴 리스트들을 리사이클러 뷰에 바인딩 시켜주기 위한 사전작업이 이루어지는 객체
        recyclerView.setAdapter(adapter); //리사이클러뷰 어댑터 연결

    };
    @Override
    public void onClick(View v) {

        startActivity(new Intent(this,PostActivity.class));
    }
    public void btnBackClicked(View v){
        Intent intent = new Intent(BoardActivity.this, Main2Activity.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
