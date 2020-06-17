package com.example.teamproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class MyPostActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase database=FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase=database.getReference(); //데이터를 데이터베이스에 쓰기 위해

    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    FirebaseUser user=mAuth.getCurrentUser();
    private ArrayList<Post> arrayList;
    private ArrayList<myPostResult> modelArraylist;
    private CustomAdapter customAdapter;
    static RequestQueue requestQueue;
    private static final String TAG="BAAM";
    String title;
    private RecyclerDecoration spaceDecoration;
    String uid;
    public boolean pos;
    private final Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);
        Button button=findViewById(R.id.btn_delete);
        Button update=findViewById(R.id.btn_update);
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
        //  modelArraylist=getModel(false);
        // customAdapter=new CustomAdapter(this,modelArraylist);
        //recyclerView.setAdapter(customAdapter);
        // recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setLayoutManager(layoutManager);

        //layoutManager은 많은 역할을 하지만 간단하게 스크롤을 위아래로 할지 좌우로 할지 결정하는것


        arrayList=new ArrayList<>();   // Boardd 객체담을 어레이 리스트 (어댑터쪽으로 날리기위해)

//        Intent intent1 = getIntent();
//        int pos = intent1.getIntExtra("pos", 0);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uid=user.getUid();
                for(int i=0; i<MyPostAdapter.arrayList.size(); i++){
                    if(MyPostAdapter.arrayList.get(i).isSelected()){
                        title=MyPostAdapter.arrayList.get(i).getBoard_title();
                        Intent intent=new Intent(getApplicationContext(), updatePost.class);
                        intent.putExtra("pos",i);
                        startActivity(intent);

                    }

                }


            }
        });

        button.setOnClickListener(new View.OnClickListener(){


            public void onClick(View view){
                uid=user.getUid();
                // arrayList=getModel(true);


                for(int i=0; i<MyPostAdapter.arrayList.size(); i++){
                    if(MyPostAdapter.arrayList.get(i).isSelected()){
                        title=MyPostAdapter.arrayList.get(i).getBoard_title();
                        Log.d(TAG,"MY BOARD TITLE: "+title);
                        Query boardQuery = mDatabase.child("USER").child("Board").orderByChild("title").equalTo(title);
                        boardQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot boardSnapshot: dataSnapshot.getChildren())
                                {
                                    boardSnapshot.getRef().removeValue();
                                    makeRequest1();

                                    Log.d(TAG,"remove success:"+dataSnapshot.getChildren());
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.d(TAG,"remove failed:"+databaseError.toException());
                            }
                        });


                    }

                }

            }
        });


    };
//    private void doTheAutoRefresh(){
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                doTheAutoRefresh();
//                makeRequest();
//
//            }
//        },3000);
//    }
    public void makeRequest(){
        FirebaseUser user=mAuth.getCurrentUser();
        String email=user.getEmail();
        String mypost_url= "https://kpu-lastproject.herokuapp.com/board/searchFeed?email="+email;
        // String mypost_url= "http://10.0.2.2/board/searchFeed?email="+email;

        StringRequest request=new StringRequest(Request.Method.GET,mypost_url,new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                processResponse(response);
            }
            public void processResponse(String response){
                Gson gson=new Gson();
                myPostList myPostList=gson.fromJson(response, com.example.teamproject.myPostList.class);

                if(myPostList.my_board.size()==0){
                    Toast.makeText(getApplicationContext(), "게시글이 없습니다.", Toast.LENGTH_LONG).show();
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
    public void makeRequest1(){
        FirebaseUser user=mAuth.getCurrentUser();



        Log.d(TAG,"here remove title:"+title);

        try{
            OkHttpClient client=new OkHttpClient();
            Gson gson=new Gson();


            RequestBody formBody= new FormBody.Builder()
                    .add("title",title)
                    .build();
            final okhttp3.Request request1=new okhttp3.Request.Builder()
                    //.url("http://10.0.2.2:3000/board/removeFeed")
                    .url("https://kpu-lastproject.herokuapp.com/board/removeFeed")
                   // .url("http://10.0.2.2:3000/comment/removeCmt")
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
    public void btnBackClicked(View v){
        Intent intent = new Intent(MyPostActivity.this, MyProfileActivity.class);
        startActivity(intent);
    }
};
