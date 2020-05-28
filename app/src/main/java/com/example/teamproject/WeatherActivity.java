package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
public class WeatherActivity extends AppCompatActivity {

    TextView textView;
    private String lat;
    private String lon;
    static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);


        textView=findViewById(R.id.textView);
        Intent intent=getIntent();

        lat=intent.getExtras().getString("lat");
        lon=intent.getExtras().getString("lon");


        if(requestQueue==null){
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }
        makeRequest();
    }
    public void makeRequest(){
        String weather_url= "http://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&appid=7ebc7734dc3a26afb1175d5b3760e122";
        String fish_url="https://kpu-lastproject.herokuapp.com/user_fish/fish";
        StringRequest request=new StringRequest(Request.Method.GET,weather_url,new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                processResponse(response);
            }
            public void processResponse(String response){
                Gson gson=new Gson();
                weatherList weatherList=gson.fromJson(response, weatherList.class);

                println("위도 : "+ weatherList.coord.lat);
                println("경도 : "+weatherList.coord.lon);
                println("main : "+weatherList.weather.get(0).main);
                println("desc : "+weatherList.weather.get(0).description);

                println("temp : "+weatherList.main.temp);
                println("feels_like : "+weatherList.main.feels_like);

                println("temp_min : "+weatherList.main.temp_min);
                println("temp_max : "+weatherList.main.temp_max);
                println("humidity : "+weatherList.main.humidity);
                println("wind spped : "+weatherList.wind.speed);
                println("wind deg : "+weatherList.wind.deg);
                println("country : "+weatherList.sys.country);
                println("sunrise : "+weatherList.sys.sunrise);
                println("sunset : "+weatherList.sys.sunset);
                println("name : "+weatherList.name);
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
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String,String> params=new HashMap<String,String>();
                return params;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);
        println("요청 보냄");
    }
    public void println(String data){
        textView.append(data+"\n");
    }
}
