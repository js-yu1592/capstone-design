package com.example.gps5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String[] permissionArr = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private static final String[] permissionArr2 = new String[]{
            Manifest.permission.RECORD_AUDIO
    };


    private static final String[] permissionArr3 = new String[]{
            Manifest.permission.CAMERA
    };

    private static final String[] permissionArr4 = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.switch1:
                TedPermission.with(this)
                    .setRationaleMessage("권한 요청을 허락하시겠습니까?")
                    .setDeniedMessage("권한을 거부하였습니다.")
                    .setPermissions(permissionArr)
                    .setPermissionListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted() {
                            Toast.makeText(getApplicationContext(), "권한 허가", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onPermissionDenied(List<String> deniedPermissions) {
                            Toast.makeText(getApplicationContext(), "권한 거부 \n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }).check();
                    break;

            case R.id.switch2:
                TedPermission.with(this)
                        .setRationaleMessage("권한 요청을 허락하시겠습니까?")
                        .setDeniedMessage("권한을 거부하였습니다.")
                        .setPermissions(permissionArr2)
                        .setPermissionListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted() {
                                Toast.makeText(getApplicationContext(), "권한 허가", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPermissionDenied(List<String> deniedPermissions) {
                                Toast.makeText(getApplicationContext(), "권한 거부 \n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }).check();
                break;

            case R.id.switch3:
                TedPermission.with(this)
                        .setRationaleMessage("권한 요청을 허락하시겠습니까?")
                        .setDeniedMessage("권한을 거부하였습니다.")
                        .setPermissions(permissionArr3)
                        .setPermissionListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted() {
                                Toast.makeText(getApplicationContext(), "권한 허가", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPermissionDenied(List<String> deniedPermissions) {
                                Toast.makeText(getApplicationContext(), "권한 거부 \n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }).check();

                break;

            case R.id.switch4:
                TedPermission.with(this)
                        .setRationaleMessage("권한 요청을 허락하시겠습니까?")
                        .setDeniedMessage("권한을 거부하였습니다.")
                        .setPermissions(permissionArr4)
                        .setPermissionListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted() {
                                Toast.makeText(getApplicationContext(), "권한 허가", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPermissionDenied(List<String> deniedPermissions) {
                                Toast.makeText(getApplicationContext(), "권한 거부 \n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }).check();

                break;
        }

    }
}