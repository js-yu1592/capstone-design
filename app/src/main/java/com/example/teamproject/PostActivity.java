package com.example.teamproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.teamproject.models.Post;
import com.example.teamproject.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.sql.DriverManager.println;

public class PostActivity extends AppCompatActivity implements View.OnClickListener {
    private final String APPID = "com.example.teamproject";
    private final String CAFE_URL = "fishing0128";
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    private static final String TAG="BAAM";
    private FirebaseDatabase database=FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase=database.getReference(); //데이터를 데이터베이스에 쓰기 위해
    private DatabaseReference userRef=database.getReference("USER/user_info"); //데이터 가져오기용
    private CheckBox checkBox;
    private EditText mTitle, mContents;
    private String muid;
    static RequestQueue requestQueue;
    public static String nickname;
    String email;
    String uid;
    FirebaseUser user=mAuth.getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        String uid;
        mTitle = findViewById(R.id.post_title_edit);
        mContents = findViewById(R.id.post_contents_edit);
        TextView textView;
        ValueEventListener mValueEventListener;
        Intent intent = getIntent();
        findViewById(R.id.post_save_button).setOnClickListener(this);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    public void naverCafeOnclick(View v){

        new NaverCafe(PostActivity.this, APPID).write(CAFE_URL, "1", mTitle.getText().toString(), mContents.getText().toString());


    }

    @Override
    public void onClick(View v) {
        FirebaseUser user=mAuth.getCurrentUser();

        if(mAuth.getCurrentUser()!=null){


            String uid=user.getUid();
            email=user.getEmail();
            if(requestQueue==null){
                requestQueue= Volley.newRequestQueue(getApplicationContext());
            }
            makeRequest();





            Toast.makeText(PostActivity.this,"DB에 저장완료",Toast.LENGTH_SHORT).show();



            Intent intent=new Intent(PostActivity.this, BoardActivity.class);
            startActivity(intent);



        }
    }
    public void makeRequest(){
        String nickname_url= "https://kpu-lastproject.herokuapp.com/user_info/nickname?email="+email;
        Log.d(TAG,"Current email : "+email);
        StringRequest request=new StringRequest(com.android.volley.Request.Method.GET, nickname_url,new com.android.volley.Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                processResponse(response);
                Log.d(TAG,"asdfa email : "+"here");
            }
            public void processResponse(String response){
                Gson gson=new Gson();
                myNicknameList myNicknameList =gson.fromJson(response, myNicknameList.class);

                if(myNicknameList.my_nickname.size()==0){
                    Log.d(TAG,"asdfa email : "+"here111");
                    Toast.makeText(getApplicationContext(),"json파싱 실패",Toast.LENGTH_LONG).show();
                    println("json파싱 실패");
                }
                else {
                    uid=user.getUid();
                    nickname = myNicknameList.my_nickname.get(0).user_nickname;

                    Post post=new Post(nickname,mTitle.getText().toString(), mContents.getText().toString(),user.getUid());

                    mDatabase.child("USER").child("Board").push().setValue(post);

                    mAuth.getCurrentUser().getIdToken(true)
                            .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                @Override
                                public void onComplete(@NonNull Task<GetTokenResult> task) {
                                    if(task.isSuccessful()){
                                        String idToken=task.getResult().getToken();
                                        Log.d(TAG,"TOKEN:"+idToken);
                                        FirebaseUser user=mAuth.getCurrentUser();

                                        //String email=user.getEmail();
                                        String title=mTitle.getText().toString();
                                        String content= mContents.getText().toString();


                                        email=user.getEmail();
                                        try{
                                            Log.d(TAG,"start:"+"dsafsfasfdafsadfadfas");
                                            Log.d(TAG,"okhttpNICKNAME : "+nickname);
                                            OkHttpClient client=new OkHttpClient();
                                            RequestBody formBody = new FormBody.Builder()
                                                    .add("idToken", idToken)
                                                    .add("nickname",nickname)
                                                    .add("uid",uid)
                                                    .add("title",title)
                                                    .add("content",content)
                                                    .add("email",email)
                                                    .build();

                                            Request request=new Request.Builder()
                                                    .url("https://kpu-lastproject.herokuapp.com/board/writeFeed")
                                                    .post(formBody)
                                                    .build();
                                            //바동기 처리
                                            client.newCall(request).enqueue(new Callback() {
                                                @Override
                                                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                                    Log.d(TAG,"fail:"+e.toString());
                                                    System.out.println("error + Connection Server Error is"+e.toString());
                                                }

                                                @Override
                                                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                                    Log.d(TAG,"success:"+response.body().toString());
                                                    System.out.println("Response Body is "+ response.body().string());
                                                }
                                            });
                                        }catch(Exception e){

                                        }
                                    }
                                }
                            });

                }
            }
        },
                new com.android.volley.Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        println("에러 -> "+error.getMessage());
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