package com.example.teamproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.teamproject.adapters.CustomAdapter;
import com.example.teamproject.adapters.MyPostAdapter;
import com.example.teamproject.models.Post;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyPostActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase database=FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase=database.getReference(); //데이터를 데이터베이스에 쓰기 위해

    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    FirebaseUser user=mAuth.getCurrentUser();
    private ArrayList<Post> arrayList;


    static RequestQueue requestQueue;
    private static final String TAG="BAAM";
    private RecyclerDecoration spaceDecoration;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);
        Button button=findViewById(R.id.btn_delete);
        if(requestQueue==null){
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }
        makeRequest();

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


        arrayList=new ArrayList<>();   // Boardd 객체담을 어레이 리스트 (어댑터쪽으로 날리기위해)

        Intent intent = getIntent();
        int pos = intent.getIntExtra("pos", 0);
        Log.d(TAG,"MY POST ACTIVITY POS:"+MyPostAdapter.position);
        button.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                uid=user.getUid();
                String title=MyPostAdapter.myPostArr.get(pos).getBoard_title();
                Log.d(TAG,"MY BOARD TITLE: "+title);
                mDatabase.child("USER").child("Board").child(uid).child(title).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MyPostActivity.this,"삭제 성공",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MyPostActivity.this,"삭제 실패",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    };
    public void makeRequest(){
        FirebaseUser user=mAuth.getCurrentUser();
        String email=user.getEmail();
      String mypost_url= "https://kpu-lastproject.herokuapp.com/board/searchFeed?email="+email;
       // String mypost_url= "http://10.0.2.2/board/searchFeed?email="+email;
        Log.d(TAG,"here email111:"+email);
        StringRequest request=new StringRequest(Request.Method.GET,mypost_url,new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                processResponse(response);
            }
            public void processResponse(String response){
                Gson gson=new Gson();
                myPostList myPostList=gson.fromJson(response, com.example.teamproject.myPostList.class);

                if(myPostList.my_board.size()==0){
                    Toast.makeText(getApplicationContext(), "에러1", Toast.LENGTH_LONG).show();
                }

                adapter=new MyPostAdapter(myPostList.my_board, getApplicationContext()); //CustomAdapter로 설정.
                //어댑터는 담긴 리스트들을 리사이클러 뷰에 바인딩 시켜주기 위한 사전작업이 이루어지는 객체
                recyclerView.setAdapter(adapter); //리사이클러뷰 어댑터 연결


            }
        },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(MyPostActivity.this, "에러2", Toast.LENGTH_SHORT).show();
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

    }



}