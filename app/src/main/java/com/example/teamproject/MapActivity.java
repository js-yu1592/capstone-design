package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private String lat;
    private String lon;


    private GpsTracker gpsTracker;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync( this);



    }

    //지도 표시
    @Override
    public void onMapReady(final GoogleMap googleMap) {

        mMap = googleMap;

        LatLng SEOUL =new LatLng(37.56,126.97);
        mMap.moveCamera(CameraUpdateFactory.newLatLng((SEOUL)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                lat=String.valueOf(point.latitude);
                lon=String.valueOf(point.longitude);
                //Toast.makeText(getApplicationContext(),lat+","+lon,Toast.LENGTH_LONG).show();

                Intent intent=new Intent(getApplicationContext(), FishRegistActivity.class);
                //intent.putExtra("fishArr",fishArr);
                intent.putExtra("lat",lat);
                intent.putExtra("lon",lon);
                setResult(RESULT_OK,intent);
                finish();

            }
        });
    }



}
