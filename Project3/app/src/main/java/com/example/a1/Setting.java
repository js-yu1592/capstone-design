package com.example.a1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;

public class Setting extends AppCompatActivity implements View.OnClickListener {


    private static final String[] permissionArr = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private static final String[] permissionArr2 = new String[]{
            Manifest.permission.CAMERA
    };

    private static final String[] permissionArr3 = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,

    };

    private static final String[] permissionArr4 = new String[]{
            Manifest.permission.RECORD_AUDIO
    };

    private static final String TAG = "Setting";

    private FirebaseAuth firebaseAuth;

    private Button buttonFind;
    private TextView textviewDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        buttonFind = (Button) findViewById(R.id.buttonFind);
        textviewDelete = (TextView) findViewById(R.id.textviewDelete);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
//            finish();
            startActivity(new Intent(this, First.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        buttonFind.setOnClickListener(this);
        textviewDelete.setOnClickListener(this);

        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);

        final ToggleButton toggleButton1=(ToggleButton)findViewById(R.id.toggleButton1);
        final ToggleButton toggleButton2=(ToggleButton)findViewById(R.id.toggleButton2);
        final ToggleButton toggleButton3=(ToggleButton)findViewById(R.id.toggleButton3);
        final ToggleButton toggleButton4=(ToggleButton)findViewById(R.id.toggleButton4);


        TextView textView = (TextView)findViewById(R.id.textView);
        TextView textView3 = (TextView)findViewById(R.id.textView3);
        TextView textView4 = (TextView)findViewById(R.id.textView4);
        TextView textView5 = (TextView)findViewById(R.id.textView5);


        Boolean tb1 = pref.getBoolean("toggleButton1", false);
        Boolean tb2 = pref.getBoolean("toggleButton2", false);
        Boolean tb3 = pref.getBoolean("toggleButton3", false);
        Boolean tb4 = pref.getBoolean("toggleButton4", false);

        String text = pref.getString("textView", "");
        String text3 = pref.getString("textView3", "");
        String text4 = pref.getString("textView4", "");
        String text5 = pref.getString("textView5", "");


        toggleButton1.setChecked(tb1);
        toggleButton2.setChecked(tb2);
        toggleButton3.setChecked(tb3);
        toggleButton4.setChecked(tb4);

        textView.setText(text);
        textView3.setText(text3);
        textView4.setText(text4);
        textView5.setText(text5);

    }

    public void onClick(View view) {
        final TextView textView = findViewById(R.id.textView);
        final TextView textView3 = findViewById(R.id.textView3);
        final TextView textView4 = findViewById(R.id.textView4);
        final TextView textView5 = findViewById(R.id.textView5);


        switch (view.getId())
        {
            case R.id.toggleButton1:

                TedPermission.with(this)
                        .setRationaleMessage("권한 요청을 허락하시겠습니까?")
                        .setDeniedMessage("권한을 거부하였습니다.")
                        .setPermissions(permissionArr)
                        .setPermissionListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted() {
                                Toast.makeText(getApplicationContext(), "권한이 이미 허가되었습니다. 거절하려면 설정 창에 들어가세요", Toast.LENGTH_LONG).show();

//                                textView.setText("권한이 허가되었습니다. 거절하려면 설정 button -->App Info --> s1 --> Permissions.");

                            }

                            @Override
                            public void onPermissionDenied(List<String> deniedPermissions) {
                                Toast.makeText(getApplicationContext(), "권한 거부 \n" , Toast.LENGTH_SHORT).show();
//                                textView.setText("권한이 거절되었습니다. 허용하려면 버튼을 다시 눌러주세요.");

                            }
                        }).check();
                break;

            case R.id.toggleButton2:
                TedPermission.with(this)
                        .setRationaleMessage("권한 요청을 허락하시겠습니까?")
                        .setDeniedMessage("권한을 거부하였습니다.")
                        .setPermissions(permissionArr2)
                        .setPermissionListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted() {
                                Toast.makeText(getApplicationContext(), "권한이 이미 허가되었습니다. 거절하려면 설정 창에 들어가세요", Toast.LENGTH_LONG).show();

//                                textView3.setText("권한이 허가되었습니다. 거절하려면 설정 button -->App Info --> s1 --> Permissions.");

                            }

                            @Override
                            public void onPermissionDenied(List<String> deniedPermissions) {
                                Toast.makeText(getApplicationContext(), "권한 거부 \n" , Toast.LENGTH_SHORT).show();
//                                textView3.setText("권한이 거절되었습니다. 허용하려면 버튼을 다시 눌러주세요.");

                            }
                        }).check();
                break;

            case R.id.toggleButton3:

                TedPermission.with(this)
                        .setRationaleMessage("권한 요청을 허락하시겠습니까?")
                        .setDeniedMessage("권한을 거부하였습니다.")
                        .setPermissions(permissionArr3)
                        .setPermissionListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted() {
                                Toast.makeText(getApplicationContext(), "권한이 이미 허가되었습니다. 거절하려면 설정 창에 들어가세요", Toast.LENGTH_LONG).show();

//                                textView4.setText("권한이 허가되었습니다. 거절하려면 설정 button -->App Info --> s1 --> Permissions.");

                            }

                            @Override
                            public void onPermissionDenied(List<String> deniedPermissions) {
                                Toast.makeText(getApplicationContext(), "권한 거부 \n" , Toast.LENGTH_SHORT).show();
//                                textView4.setText("권한이 거절되었습니다. 허용하려면 버튼을 다시 눌러주세요.");

                            }
                        }).check();
                break;

            case R.id.toggleButton4:

                TedPermission.with(this)
                        .setRationaleMessage("권한 요청을 허락하시겠습니까?")
                        .setDeniedMessage("권한을 거부하였습니다.")
                        .setPermissions(permissionArr4)
                        .setPermissionListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted() {
                                Toast.makeText(getApplicationContext(), "권한이 이미 허가되었습니다. 거절하려면 설정 창에 들어가세요", Toast.LENGTH_LONG).show();

//                                textView5.setText("권한이 허가되었습니다. 거절하려면 설정 button -->App Info --> s1 --> Permissions.");

                            }

                            @Override
                            public void onPermissionDenied(List<String> deniedPermissions) {
                                Toast.makeText(getApplicationContext(), "권한 거부 \n" , Toast.LENGTH_SHORT).show();
//                                textView5.setText("권한이 거절되었습니다. 허용하려면 버튼을 다시 눌러주세요.");

                            }
                        }).check();
                break;

            case R.id.button:
                Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
                startActivityForResult(intent, 0);

                break;

        }


        if (view == buttonFind) {
            firebaseAuth.signOut();
//            finish();
            startActivity(new Intent(this, First.class));
        }


        if (view == textviewDelete) {
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(Setting.this);
            alert_confirm.setMessage("정말 계정을 삭제 할까요?").setCancelable(false).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            user.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(Setting.this, "계정이 삭제 되었습니다.", Toast.LENGTH_LONG).show();
//                                            finish();
                                            startActivity(new Intent(getApplicationContext(), First.class));
                                        }
                                    });
                        }
                    }
            );
            alert_confirm.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
//                    Toast.makeText(Setting.this, "취소", Toast.LENGTH_LONG).show();
                }
            });
            alert_confirm.show();


        }

    }
    public void onStop(){
        super.onStop();
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        ToggleButton toggleButton1 = (ToggleButton)findViewById(R.id.toggleButton1);
        ToggleButton toggleButton2 = (ToggleButton)findViewById(R.id.toggleButton2);
        ToggleButton toggleButton3 = (ToggleButton)findViewById(R.id.toggleButton3);
        ToggleButton toggleButton4 = (ToggleButton)findViewById(R.id.toggleButton4);


        TextView textView = (TextView)findViewById(R.id.textView);
        TextView textView3 = (TextView)findViewById(R.id.textView3);
        TextView textView4 = (TextView)findViewById(R.id.textView4);
        TextView textView5 = (TextView)findViewById(R.id.textView5);

        editor.putBoolean("toggleButton1", toggleButton1.isChecked());
        editor.putBoolean("toggleButton2", toggleButton2.isChecked());
        editor.putBoolean("toggleButton3", toggleButton3.isChecked());
        editor.putBoolean("toggleButton4", toggleButton4.isChecked());

        editor.putString("textView", textView.getText().toString());
        editor.putString("textView3", textView3.getText().toString());

        editor.putString("textView4", textView4.getText().toString());
        editor.putString("textView5", textView5.getText().toString());

        editor.commit();

    }

    public void button8Clicked(View v) {
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        //super.onBackPressed(); }
    }
}
