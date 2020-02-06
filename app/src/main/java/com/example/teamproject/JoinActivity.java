package com.example.teamproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class JoinActivity extends AppCompatActivity {
    // 비밀번호 정규식
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");
    private DatabaseReference mDatabase; //데이터를 데이터베이스에 쓰기 위해
    private FirebaseDatabase database;
    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;

    // 이메일과 비밀번호
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPhone;
    private EditText editTextNickname;
    private EditText editTextId;
    private EditText editTextname;
    private String email = "";
    private String password = "";
    private String phone = "";
    private String id = "";
    private String nickname="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        // 파이어베이스 인증 객체 선언
        firebaseAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText)findViewById(R.id.join_email);
        editTextPassword = (EditText)findViewById(R.id.join_password);
       editTextPhone=(EditText)findViewById(R.id.join_phone);
       editTextNickname=(EditText)findViewById(R.id.join_nickname);
       editTextname=(EditText)findViewById(R.id.join_name);
       editTextId=(EditText) findViewById(R.id.join_id);
        Button button=findViewById(R.id.btn_finish);


        //파이어베이스 데이터베이스 객체선언
        database=FirebaseDatabase.getInstance();
        mDatabase=database.getReference();


        String name=editTextname.getText().toString();
        String nickname=editTextNickname.getText().toString();
        String phone=editTextPhone.getText().toString();
        String id=editTextId.getText().toString();



        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String email=editTextEmail.getText().toString().trim();
                String pwd=editTextPassword.getText().toString().trim();
                User user_info=new User(editTextId.getText().toString(),editTextEmail.getText().toString(),editTextPassword.getText().toString(),
                        editTextname.getText().toString(), editTextNickname.getText().toString(),editTextPhone.getText().toString());
                mDatabase.child("USER").setValue(user_info);
                Toast.makeText(JoinActivity.this,"Success",Toast.LENGTH_SHORT).show();
                firebaseAuth.createUserWithEmailAndPassword(email, pwd)
                        .addOnCompleteListener(JoinActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Intent intent=new Intent(JoinActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(JoinActivity.this, "등록에러",Toast.LENGTH_SHORT).show();
                                    return ;
                                }
                            }

                        });

                       mDatabase.addValueEventListener(new ValueEventListener() {

                           @Override
                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                               if(dataSnapshot.child(editTextEmail.getText().toString()).exists()){
                                   Toast.makeText(JoinActivity.this,"already register",Toast.LENGTH_SHORT).show();
                               }else{
                                   User user_info=new User(editTextId.getText().toString(),editTextEmail.getText().toString(),editTextPassword.getText().toString(),
                                           editTextname.getText().toString(), editTextNickname.getText().toString(),editTextPhone.getText().toString());
                                   mDatabase.child("USER").setValue(user_info);
                                   Toast.makeText(JoinActivity.this,"Success",Toast.LENGTH_SHORT).show();
                               }
                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError databaseError) {

                           }
                       });
//                public void postFirebaseDatabase(boolean add){
//                    Map<String, Object> childUpdates=new HashMap<>();
//                    Map<String, Object> postValues=null;
//                    if(add){
//                        User user_info=new User(editTextId.getText().toString(),editTextEmail.getText().toString(),editTextPassword.getText().toString(),
//                                editTextname.getText().toString(), editTextNickname.getText().toString(),editTextPhone.getText().toString());
//                        postValues=user_info.toMap();
//                    }
//                    childUpdates.put("User",postValues);
//                    mDatabase.updateChildren(childUpdates);
//                }

            }
        });





//        button.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//
//                Intent intent =new Intent(JoinActivity.this,MainActivity.class);
//
//                startActivity(intent);
//            }
//        });

    }

}
