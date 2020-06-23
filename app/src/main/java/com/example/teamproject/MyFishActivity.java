package com.example.teamproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.teamproject.adapters.MyfishAdapter;
import com.example.teamproject.models.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyFishActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private String nickname;

    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    FirebaseUser user=mAuth.getCurrentUser();
    private ArrayList<Post> arrayList;


    static RequestQueue requestQueue;
    private static final String TAG="BAAM";

    private RecyclerDecoration spaceDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fish);
        Button btn_regist=(Button)findViewById(R.id.btn_regist);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(requestQueue==null){
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }
        makeRequest();
        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyFishActivity.this, FishRegistActivity.class);
                startActivity(intent);

            }
        });

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

    }
    public void makeRequest(){
        String email=user.getEmail();
        String myfish_url= "https://kpu-lastproject.herokuapp.com/user_fish/fishbowl?nickname="+MyProfileActivity.UserNickname;
        //                  https://kpu-lastproject.herokuapp.com/user_fish/fishbowl?nickname=zzoon
//        Toast.makeText(this,MyProfileActivity.UserNickname , Toast.LENGTH_SHORT).show();
       // String myfish_url= "https://kpu-lastproject.herokuapp.com/user_fish/fish";
        // String mypost_url= "http://10.0.2.2/board/searchFeed?email="+email;

        StringRequest request=new StringRequest(Request.Method.GET,myfish_url,new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                processResponse(response);
            }
            public void processResponse(String response){
                Gson gson=new Gson();
                fishTankList fishTankList=gson.fromJson(response, com.example.teamproject.fishTankList.class);
                if(fishTankList.fish.size()==0){
                    Toast.makeText(getApplicationContext(), "등록된 물고기가 없습니다.", Toast.LENGTH_LONG).show();
                }

                adapter=new MyfishAdapter(fishTankList.fish, getApplicationContext()); //CustomAdapter로 설정.
                //어댑터는 담긴 리스트들을 리사이클러 뷰에 바인딩 시켜주기 위한 사전작업이 이루어지는 객체
                recyclerView.setAdapter(adapter); //리사이클러뷰 어댑터 연결


            }
        },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(MyFishActivity.this, "에러2", Toast.LENGTH_SHORT).show();
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.join, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
