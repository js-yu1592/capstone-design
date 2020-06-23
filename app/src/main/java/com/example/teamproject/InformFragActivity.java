package com.example.teamproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class InformFragActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView; //바텀 네비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private String lat;
    private String lon;
    private FishTankActivity fishTankActivity;
    private WeatherActivity weatherActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inform_frag);



        Intent intent=getIntent();
        lat=intent.getExtras().getString("lat");
        lon=intent.getExtras().getString("lon");


//        Fragment fragment=new WeatherActivity();
//        Bundle bundle=new Bundle(2);
//        bundle.putString("lat",lat);
//        bundle.putString("lon",lon);
//        fragment.setArguments(bundle);

        bottomNavigationView=findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.bottom_weather:
                        setFrag(0);
                        break;
                    case R.id.bottom_fish:
                        setFrag(1);
                        break;
                }
                return true;
            }
        });
        weatherActivity= new WeatherActivity();
        fishTankActivity=new FishTankActivity();

        Bundle bundle=new Bundle();
        bundle.putString("lat",lat);
        bundle.putString("lon",lon);

        weatherActivity.setArguments(bundle);
        fishTankActivity.setArguments(bundle);

        setFrag(0);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setFrag(int n){
        fm=getSupportFragmentManager();
        ft=fm.beginTransaction();
        switch (n){
            case 0:
                ft.replace(R.id.main_frame,weatherActivity);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame,fishTankActivity);
                ft.commit();
                break;

        }

    }

}
