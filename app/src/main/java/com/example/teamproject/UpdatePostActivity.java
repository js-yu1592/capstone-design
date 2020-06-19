package com.example.teamproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.teamproject.adapters.MyPostAdapter;
import com.example.teamproject.models.Post;
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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class UpdatePostActivity extends AppCompatActivity {
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
    public  String nickname;
    String uid, oldtitle;
    String newTitle, newContent;
    FirebaseUser user=mAuth.getCurrentUser();
    private final Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_post);
        TextView titleText=(TextView)findViewById(R.id.update_title);
        TextView contentText=(TextView)findViewById(R.id.update_contents);

        Intent intent=getIntent();
        int pos= intent.getExtras().getInt("pos");
        contentText.setText(MyPostAdapter.myPostArr.get(pos).getBoard_content());
        titleText.setText(MyPostAdapter.myPostArr.get(pos).getBoard_title());
        Button updateBtn=findViewById(R.id.update_save_button);


        updateBtn.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                Log.d(TAG,"updateBtn start : ");
                Log.d(TAG,"updateBtn start pos : "+pos);
                uid=user.getUid();
                oldtitle=MyPostAdapter.arrayList.get(pos).getBoard_title();
                final DatabaseReference updateRef=mDatabase.child("USER").child("Board");
                Query boardQuery=mDatabase.child("USER").child("Board").orderByChild("title").equalTo(oldtitle);
                boardQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot child: dataSnapshot.getChildren()){
                                String postkey=child.getRef().getKey();

                                newTitle=titleText.getText().toString();
                                newContent=contentText.getText().toString();
                                updateRef.child(postkey).child("title").setValue(newTitle);
                                updateRef.child(postkey).child("contents").setValue(newContent);

                                Log.d(TAG,"update title: "+newTitle);
                                Log.d(TAG,"update content:"+newContent);
                                makeRequest1();

                            }
                            AlertDialog.Builder builder=new AlertDialog.Builder(UpdatePostActivity.this);
                            builder.setMessage("게시글을 수정합니다.")
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent=new Intent(getApplicationContext(), MyProfileActivity.class);
                                            startActivity(intent);

                                        }
                                    });
                            builder.create();
                            builder.show();



                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }
        });

    }


    public void onBackPressed() {
        super.onBackPressed();
    }
    public void naverCafeOnclick(View v){

        new NaverCafe(UpdatePostActivity.this, APPID).write(CAFE_URL, "1", mTitle.getText().toString(), mContents.getText().toString());


    }

    public void makeRequest1(){
        FirebaseUser user=mAuth.getCurrentUser();



        Log.d(TAG,"here update title:"+newTitle);

        try{
            OkHttpClient client=new OkHttpClient();
            Gson gson=new Gson();


            RequestBody formBody= new FormBody.Builder()
                    .add("oldtitle",oldtitle)
                    .add("title",newTitle)
                    .add("content",newContent)
                    .build();
            final okhttp3.Request request1=new okhttp3.Request.Builder()
                    //.url("http://10.0.2.2:3000/board/updateFeed")
                    .url("https://kpu-lastproject.herokuapp.com/board/updateFeed")
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
}