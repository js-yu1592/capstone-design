package com.example.teamproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class JoinActivity extends AppCompatActivity {
    // 비밀번호 정규식
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");
    private DatabaseReference mDatabase; //데이터를 데이터베이스에 쓰기 위해
    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;

    // 이메일과 비밀번호
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPhone;
    private EditText editTextNickname;
    private EditText editTextId;
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
        Button button=findViewById(R.id.btn_finish);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String email=editTextEmail.getText().toString().trim();
                String pwd=editTextPassword.getText().toString().trim();

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
