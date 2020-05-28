package com.example.teamproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.teamproject.FishTankList;
import com.example.teamproject.R;
import com.example.teamproject.fishListResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Marker extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private String lat;
    private String lon;
    static RequestQueue requestQueue;
    public ArrayList<fishListResult> fishArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync( this);

        if(requestQueue==null){
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }
        makeRequest();

    }
    public void makeRequest(){
        String fish_url="https://kpu-lastproject.herokuapp.com/user_fish/fish";
        StringRequest request=new StringRequest(Request.Method.GET,fish_url,new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                processResponse(response);
            }
            public void processResponse(String response){
                Gson gson=new Gson();
                FishTankList FishTankList=gson.fromJson(response, FishTankList.class);
                fishArr=new ArrayList<fishListResult>();

                fishArr=FishTankList.fish;
                Toast.makeText(getApplicationContext(),FishTankList.fish.get(0).fish_comment,Toast.LENGTH_LONG).show();
            }
        },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(getApplicationContext(),"에러",Toast.LENGTH_LONG).show();
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

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        mMap = googleMap;

        LatLng SEOUL =new LatLng(37.56,126.97);
        mMap.moveCamera(CameraUpdateFactory.newLatLng((SEOUL)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
//        for(int i=0;i<fishArr.size();i++){
//            MarkerOptions markerOptions=new MarkerOptions();
//            markerOptions.position(new LatLng(Double.valueOf(fishArr.get(i).fish_lat),Double.valueOf(fishArr.get(i).fish_lon))).title(fishArr.get(i).fish_fishing);
//            mMap.addMarker(markerOptions);
//        }
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(37.52487, 126.92723)));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                lat=String.valueOf(point.latitude);
                lon=String.valueOf(point.longitude);

                Intent intent=new Intent(getApplicationContext(),WeatherActivity.class);
                intent.putExtra("fishArr",fishArr);
                startActivity(intent);
//                String text = "latitude =" + point.latitude + ", longitude ="
//                        + point.longitude;
//                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG)
//                        .show();
            }
        });
    }

}
