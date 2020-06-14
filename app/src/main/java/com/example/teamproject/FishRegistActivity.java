package com.example.teamproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class FishRegistActivity extends AppCompatActivity {
    private GoogleMap mMap;
    private String lat;
    private String lon;
    private String fish_name;
    private String fish_len;
    private String fish_weight;
    private String fish_spot;
    private String fishing;
    private String fish_comment;
    private static final String TAG="BAAM";
    EditText fish_name_edit;
    EditText fish_len_edit;
    EditText fish_weight_edit;
    EditText fish_spot_edit;
    EditText fishing_edit;
    EditText fish_com_edit;
    String uid;
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    FirebaseUser user=mAuth.getCurrentUser();
    private GpsTracker gpsTracker;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish_regist);

        fish_name_edit= (EditText) findViewById(R.id.fish_name_edit);
        fish_len_edit= (EditText) findViewById(R.id.fish_len_edit);
        fish_weight_edit= (EditText) findViewById(R.id.fish_weight_edit);

        fish_spot_edit = (EditText) findViewById(R.id.fish_spot_edit);
        fishing_edit= (EditText) findViewById(R.id.fishing_edit);
        fish_com_edit= (EditText) findViewById(R.id.fish_com_edit);

        if (!checkLocationServicesStatus()) {

            showDialogForLocationServiceSetting();
        } else {

            checkRunTimePermission();
        }
        Button btn_myLoca = (Button) findViewById(R.id.btn_myLoca);
        Button btn_map = (Button) findViewById(R.id.btn_map);
        Button btn_regist=(Button)findViewById(R.id.btn_regist);

        btn_myLoca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gpsTracker = new GpsTracker(FishRegistActivity.this);

                double latitude = gpsTracker.getLatitude();
                double longitude = gpsTracker.getLongitude();

                lat = String.valueOf(latitude);
                lon = String.valueOf(longitude);

                String address = getCurrentAddress(latitude, longitude);

                //Toast.makeText(FishRegistActivity.this,address, Toast.LENGTH_LONG).show();
                fish_spot_edit.setText(address);

            }
        });
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FishRegistActivity.this, MapActivity.class);
                startActivityForResult(intent, 1);


            }
        });
        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeRequest1();
                Intent intent = new Intent(FishRegistActivity.this, MyProfileActivity.class);
                startActivity(intent);

            }
        });
//        Intent intent2=getIntent();
//        lat=intent2.getExtras().getString("lat");
//        lon=intent2.getExtras().getString("lon");
//
//        double latitude=Double.valueOf(lat);
//        double longitude=Double.valueOf(lon);
//
//        String address = getCurrentAddress(latitude, longitude);
//        fish_spot_edit.setText(address);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if (check_result) {

                //위치 값을 가져올 수 있음
            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(FishRegistActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();


                } else {

                    Toast.makeText(FishRegistActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    void checkRunTimePermission() {

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(FishRegistActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(FishRegistActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음


        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(FishRegistActivity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(FishRegistActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(FishRegistActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(FishRegistActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }
    public void makeRequest1(){
        FirebaseUser user=mAuth.getCurrentUser();

        uid=user.getUid();

        try{
            OkHttpClient client=new OkHttpClient();
            Gson gson=new Gson();


            RequestBody formBody= new FormBody.Builder()
                    .add("uid",uid)
                    .add("lat",lat)
                    .add("lon",lon)
                    .add("name",fish_name_edit.getText().toString())
                    .add("length",fish_len_edit.getText().toString())
                    .add("weight",fish_weight_edit.getText().toString())
                    .add("fishing",   fishing_edit.getText().toString())
                    .add("comment",fish_com_edit.getText().toString())
                    .build();
            final okhttp3.Request request1=new okhttp3.Request.Builder()
                    //.url("http://10.0.2.2:3000/user_fish/saveFish")
                    .url("https://kpu-lastproject.herokuapp.com/user_fish/saveFish")
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

    public String getCurrentAddress(double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }


        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString() + "\n";

    }


    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(FishRegistActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
        if(resultCode==RESULT_OK){
            lat=data.getExtras().getString("lat");
            lon=data.getExtras().getString("lon");
            double latitude=Double.valueOf(lat);
            double longitude=Double.valueOf(lon);
            //Toast.makeText(getApplicationContext(),latitude+","+longitude,Toast.LENGTH_LONG).show();
            String address = getCurrentAddress(latitude, longitude);
            fish_spot_edit.setText(address);
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

}