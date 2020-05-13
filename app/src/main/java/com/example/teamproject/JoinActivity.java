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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class JoinActivity extends AppCompatActivity {
    // 비밀번호 정규식
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");
    private DatabaseReference mDatabase; //데이터를 데이터베이스에 쓰기 위해
    private FirebaseDatabase database;
    private Query applesQuery;
    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;
    private static final String TAG="BAAM";
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

                String email=editTextEmail.getText().toString();
                String pwd=editTextPassword.getText().toString();
                String name=editTextname.getText().toString();
                String phone=editTextPhone.getText().toString();
                String nickname=editTextNickname.getText().toString();

                //이메일 비번 이름 핸드폰 닉네임 공백 아닐경우ㅠ
                if(!email.equals("")&&!pwd.equals("")&&!name.equals("")&&!phone.equals("")&&!nickname.equals("")){
                    createUser(email,pwd,name,phone,nickname);
                }   else{
                    Toast.makeText(JoinActivity.this, "기입하지 않은 부분이 있습니다.",Toast.LENGTH_SHORT).show();
                }



            }
        });



    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    private void createUser(String email, String password,String name, String phone, String nickname) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser user=firebaseAuth.getCurrentUser();
                        String uid=user.getUid();
                        if (task.isSuccessful()) {    //회원가입 성공시


                            Toast.makeText(JoinActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            mDatabase.addValueEventListener(new ValueEventListener() {

                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String email=editTextEmail.getText().toString().trim();
                                    FirebaseUser user=firebaseAuth.getCurrentUser();

                                    String uid=user.getUid();
                                    if(dataSnapshot.child("USER").child("user_info").exists()){
                                        Toast.makeText(JoinActivity.this,"DB에 이미존재",Toast.LENGTH_SHORT).show();
                                    }else{


                                        UserList user_info=new UserList(uid,editTextId.getText().toString(),editTextEmail.getText().toString(),editTextPassword.getText().toString(),
                                                editTextname.getText().toString(), editTextNickname.getText().toString(),editTextPhone.getText().toString());

                                //전체 데이터삭제
                                     // mDatabase.setValue(null);
                                        mDatabase.child("USER").child("user_info").setValue(user_info);
                                        Toast.makeText(JoinActivity.this,"DB에 저장완료",Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            Intent intent = new Intent(JoinActivity.this, MainActivity.class);
                            startActivity(intent);

                        } else {  //계정중복된경우
                            Toast.makeText(JoinActivity.this, "이미 존재하는 계정입니다", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                });
    }

}
