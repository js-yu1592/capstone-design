package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

//    // 비밀번호 정규식
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");

    // 구글로그인 result 상수 , 구글로그인 버튼을 클릭하여 startactivityforresult 응답코드로 사용
    private static final int RC_SIGN_IN = 900;

    // 구글api클라이언트
    private GoogleSignInClient googleSignInClient;
    private static final String TAG="BAAM";
    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;

    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    // 구글  로그인 버튼
    private SignInButton buttonGoogle;
    // 이메일과 비밀번호
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button btn_signUp;
    private Button btn_signIn;
    private EditText editTextPhone;
    private EditText editTextNickname;
    private EditText editTextId;
    private EditText editTextname;
    private String email = "";
    private String password = "";
    private String uid = "";
    public static String UserEmail;
//    public static final String apiKey="AAAAsp7tChI:APA91bEXF0BtJoSRSkJf1xe6MJHXltY_Rl15Nf-g4_-vL9xuPeFnpiDDoCapkJ8VG24x9xdwnJ1ZFqTbQkyTNP7z01cffkTT5jHu_J_iZwqV35TVxtvVv-sWAXM2xOEMvLnmbOPqUaV2";
  //  public static final String senderid="767170513426";
public static ArrayList<fishListResult> fishArr=new ArrayList<fishListResult>();
    static RequestQueue requestQueue;

    //Retrofit
//      private RetrofitInterface retrofitInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //파이어베이스 인증객체 선언
        firebaseAuth = FirebaseAuth.getInstance();
        btn_signUp=findViewById(R.id.btn_signUp);
        btn_signIn=findViewById(R.id.btn_signIn);

        editTextEmail = findViewById(R.id.et_eamil);
        editTextPassword = findViewById(R.id.et_password);




        //Google 로그인을 앱에 통합
        //GoogleSignInOptions 개체를 구성할 때 requestIdToken을 호출
// 사용자의 ID, 이메일 주소 및 기본 // 프로필 을 요청하도록 로그인을 구성 합니다. ID 및 기본 프로필은 DEFAULT_SIGN_IN에 포함되어 있습니다.
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        //구글 api clinet
        // googleSignInOptions에서 지정한 옵션으로 GoogleSignInClient를 빌드합니다
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);



        btn_signIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String email=editTextEmail.getText().toString();
                String pwd=editTextPassword.getText().toString();

                if(!email.equals("")&&!pwd.equals("")){
                    loginUser(email,pwd);
                }else{
                    Toast.makeText(MainActivity.this, "이메일과 비밀번호를 입력하세요",Toast.LENGTH_SHORT).show();
                }


            }
        });


        firebaseAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override

            //인증살태가 변경될 때 발생
            //사용자가 로그인한 경우 , 사용자가 로그아웃한 경우 사용자가 변경될때 발생
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
            UserEmail=user.getEmail();
                final String uid = user.getUid();
                Log.d(TAG,"user uid:"+user.getUid());


                    Intent intent=new Intent(MainActivity.this, BasicActivity.class);

                   // intent.putExtra("uid",uid);

                  //  Toast.makeText(MainActivity.this,uid, Toast.LENGTH_SHORT).show();
                    startActivity(intent);

                }
            };


        btn_signUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });




    }

    private void loginUser(String email, String pwd) {

        firebaseAuth.signInWithEmailAndPassword(email,pwd)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        //login
                        if(task.isSuccessful()){




                            firebaseAuth.addAuthStateListener(firebaseAuthListener);


                        }else{
                            //로그인 실패
                            Toast.makeText(MainActivity.this, "이메일 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }




//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        //구글 로그인 버튼응답
//        if (requestCode == RC_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                //구글로그인 성공
//
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                firebaseAuthWithGoogle(account);
//            } catch (ApiException e) {
//
//            }
//        }
//
//    }

    //사용자가 정상적으로 로그인한 후에 GoogleSignInAccount 개체에서 ID Token을 가져와서
    //수정
    //Firebase 사용자 인증정보로 교환하고 Firebase 사용자 인증정보를 사용해 Firebase에 인증한다.

//    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
//        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
//        firebaseAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            //로그인 성공
//                            Toast.makeText(MainActivity.this, R.string.success_login, Toast.LENGTH_SHORT).show();
//
//                                Intent intent=new Intent(MainActivity.this, BasicActivity.class);
//                                startActivity(intent);
//                                finish();
//
//                        } else {
//                            //로그인 실패
//                            Toast.makeText(MainActivity.this, R.string.failed_login, Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }
//



   protected void onStop(){
        super.onStop();
        if(firebaseAuthListener!=null){
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
   }






}