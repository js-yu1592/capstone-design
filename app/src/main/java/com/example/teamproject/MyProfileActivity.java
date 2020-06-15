package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.teamproject.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MyProfileActivity extends AppCompatActivity {
    static RequestQueue requestQueue;
    TextView textView;
    File tempSelectFile;
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    public static String UserNickname;
    ImageView imgVwSelected;
    private static final String TAG="BAAM";
    Button btnImageSend, btnImageSelection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_my_profile);
        textView=(TextView)findViewById(R.id.viewMyProfile);
        Button btn_Mypost=(Button)findViewById(R.id.btn_MyPost);
        Button btn_MyFishTank=(Button)findViewById(R.id.btn_MyFishTank);

        imgVwSelected=findViewById(R.id.imgVwSelected);

        btnImageSend=findViewById(R.id.btnImageSend);
        btnImageSend.setEnabled(false);
        btnImageSend.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                FileUploadUtils.send2Server(tempSelectFile);
            }
        });
        btnImageSelection=findViewById(R.id.btnImageSelection);

        btnImageSelection.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                Intent intent =new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });
        imgVwSelected = findViewById(R.id.imgVwSelected);

        if(requestQueue==null){
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }
        makeRequest();

        btn_MyFishTank.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent=new Intent(MyProfileActivity.this, FishRegistActivity.class);
                intent.putExtra("nickname",UserNickname);
                startActivity(intent);

            }
        });

        btn_Mypost.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent=new Intent(MyProfileActivity.this, MyPostActivity.class);
                //intent.putExtra("uid",uid);
                startActivity(intent);

            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode != 1 || resultCode !=RESULT_OK){
            return;
        }

        //비트맵으로 받는 방법
        try {
            //ImageView에 이미지 출력
            InputStream in = getContentResolver().openInputStream(data.getData());
            Bitmap image = BitmapFactory.decodeStream(in);
            Log.d(TAG," GET data: "+data.getData());

            imgVwSelected.setImageBitmap(image);
            in.close();

            String date = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date());
            tempSelectFile = new File(Environment.getExternalStorageDirectory()+"/Pictures", "temp_" + date + ".jpeg");
            Log.d(TAG, "tempSelectFile : "+Environment.getExternalStorageDirectory().getAbsolutePath());
            OutputStream out = new FileOutputStream(tempSelectFile);
            image.compress(Bitmap.CompressFormat.JPEG, 100, out);



        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        //imgVwSelected.setImageURI(data.getData());
       btnImageSend.setEnabled(true);
    }

    public void makeRequest(){
        FirebaseUser user=mAuth.getCurrentUser();
        String email=user.getEmail();
        String myboard_url= "https://kpu-lastproject.herokuapp.com/user_info/my?email="+email;
        Log.d("TAG","email : "+email);
        StringRequest request=new StringRequest(Request.Method.GET,myboard_url,new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                processResponse(response);
            }
            public void processResponse(String response){
                Gson gson=new Gson();
                myProfileList myProfileList =gson.fromJson(response, myProfileList.class);

                if(myProfileList.my_profile.size()==0){
                    Toast.makeText(getApplicationContext(),"json파싱 실패",Toast.LENGTH_LONG).show();
                    println("json파싱 실패1111");
                }
                else {
                    println("이름 : " + myProfileList.my_profile.get(0).user_name);
                    println("닉네임 : " + myProfileList.my_profile.get(0).user_nickname);
                    println("이메일 : " + myProfileList.my_profile.get(0).user_email);
                    println("핸드폰 번호 : " + myProfileList.my_profile.get(0).user_phone);
                    UserNickname = myProfileList.my_profile.get(0).user_nickname;
                    Log.d("TAG","UserNickname : "+UserNickname);
                }
            }
        },
                new Response.ErrorListener(){
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
    public void println(String data){
        textView.append(data+"\n");
    }


}