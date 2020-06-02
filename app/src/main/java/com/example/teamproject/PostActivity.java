package com.example.teamproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

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
import com.google.firebase.database.ValueEventListener;

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

public class PostActivity extends AppCompatActivity implements View.OnClickListener {
    private final String APPID = "com.example.teamproject";
    private final String CAFE_URL = "fishing0128";
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    private static final String TAG="BAAM";
    private FirebaseDatabase database=FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase=database.getReference(); //데이터를 데이터베이스에 쓰기 위해
    private CheckBox checkBox;
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
            String email=user.getEmail();
            Log.d(TAG,"user email:"+user.getEmail());
            Post post=new Post(email,mTitle.getText().toString(), mContents.getText().toString(),uid);
            mDatabase.child("USER").child("Board").push().setValue(post);
            Toast.makeText(PostActivity.this,"DB에 저장완료",Toast.LENGTH_SHORT).show();

            mAuth.getCurrentUser().getIdToken(true)
                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        @Override
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if(task.isSuccessful()){
                                String idToken=task.getResult().getToken();
                                Log.d(TAG,"TOKEN:"+idToken);
                                FirebaseUser user=mAuth.getCurrentUser();
                                String uid=user.getUid();
                                String email=user.getEmail();
                                String title=mTitle.getText().toString();
                                String content= mContents.getText().toString();
                                try{
                                    Log.d(TAG,"start:"+"dsafsfasfdafsadfadfas");
                                    OkHttpClient client=new OkHttpClient();
                                    RequestBody formBody = new FormBody.Builder()
                                            .add("idToken", idToken)
                                            .add("email",email)
                                            .add("uid",uid)
                                            .add("title",title)
                                            .add("content",content)
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


            Intent intent=new Intent(PostActivity.this, BoardActivity.class);
            startActivity(intent);



        }
    }
}