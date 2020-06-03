package com.example.teamproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teamproject.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.GoogleAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
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
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();

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

        Button button=findViewById(R.id.btn_finish);


        //파이어베이스 데이터베이스 객체선언
        database=FirebaseDatabase.getInstance();
        mDatabase=database.getReference();



        String name=editTextname.getText().toString();
        String nickname=editTextNickname.getText().toString();
        String phone=editTextPhone.getText().toString();




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

    private void createUser(String email, String password,String name, String phone, String nickname) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {    //회원가입 성공시

                            FirebaseUser mUser=FirebaseAuth.getInstance().getCurrentUser();
                            Log.d(TAG,"uid here : "+mUser.getUid());
                            Log.d(TAG,"displayname : "+mUser.getDisplayName());
                            Toast.makeText(JoinActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();

                            mUser.getIdToken(true)
                                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                                            if(task.isSuccessful()){
                                                String idToken=task.getResult().getToken();
                                                String email=editTextEmail.getText().toString();
                                                String pwd=editTextPassword.getText().toString();
                                                String nickname=editTextNickname.getText().toString();
                                                String phone=editTextPhone.getText().toString();
                                                String name=editTextname.getText().toString();
                                                try{

                                                    OkHttpClient client=new OkHttpClient();
                                                    RequestBody formBody = new FormBody.Builder()
                                                            .add("idToken", idToken)
                                                            .add("email",email)
                                                            .add("password", pwd)
                                                            .add("name",name)
                                                            .add("phone",phone)
                                                            .add("nickname",nickname)
                                                            .build();

                                                    Request request=new Request.Builder()
                                                            .url("http://kpu-lastproject.herokuapp.com/user_info/login")
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
                            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(firebaseAuth.getCurrentUser()!=null) {
                                        String email=editTextEmail.getText().toString().trim();
                                        FirebaseUser user=firebaseAuth.getCurrentUser();

                                        String uid=user.getUid();
                                        if(dataSnapshot.child("USER").child("user_info").child(uid).exists()){
                                            Toast.makeText(JoinActivity.this,"DB에 이미존재",Toast.LENGTH_SHORT).show();
                                        }else{


                                            User user_info = new User(uid, editTextEmail.getText().toString(), editTextPassword.getText().toString(),
                                                    editTextname.getText().toString(), editTextNickname.getText().toString(), editTextPhone.getText().toString());

                                            //전체 데이터삭제
                                            //  mDatabase.setValue(null);
                                            //String key = mDatabase.child("USER").push().getKey();
                                            mDatabase.child("USER").child("user_info").push().setValue(user_info);
                                            Toast.makeText(JoinActivity.this, "DB에 저장완료", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            Intent intent = new Intent(JoinActivity.this, MainActivity.class);
                            startActivity(intent);

                        } else {  //계정중복된경우
                            Toast.makeText(JoinActivity.this, "이미 존재하는 계정이거나 비밀번호를 6자리 이상으로 적으십셔!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                });
    }

}