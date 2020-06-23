package com.example.teamproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class WeatherActivity extends Fragment {

    private View view;
    TextView textView;
    TextView textView9;
    TextView textView10;
    TextView textView11;
    TextView textView12;
    TextView textView13;
    TextView textView14;
    TextView textView15;
    TextView textView16;
    TextView textView17;
    TextView textView18;
    TextView textView19;
    TextView textView20;


    private String lat;
    private String lon;
    private ArrayList<String> lati;
    private ArrayList<String> longi;
    static RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_weather,container, false);


        textView=view.findViewById(R.id.textView);
        textView9=view.findViewById(R.id.textView9);
        textView10=view.findViewById(R.id.textView10);
        textView11=view.findViewById(R.id.textView11);
        textView12=view.findViewById(R.id.textView12);
        textView13=view.findViewById(R.id.textView13);
        textView14=view.findViewById(R.id.textView14);
        textView15=view.findViewById(R.id.textView15);
        textView16=view.findViewById(R.id.textView16);
        textView17=view.findViewById(R.id.textView17);
        textView18=view.findViewById(R.id.textView18);
        textView19=view.findViewById(R.id.textView19);
        textView20=view.findViewById(R.id.textView20);

        Intent intent=getActivity().getIntent();
        lati=intent.getExtras().getStringArrayList("lati");
        longi=intent.getExtras().getStringArrayList("longi");

        lat= getArguments().getString("lat");
        lon= getArguments().getString("lon");

        if(requestQueue==null){
            requestQueue= Volley.newRequestQueue(getActivity().getApplicationContext());
        }
        makeRequest();
        return view;
    }

    public void makeRequest(){
        String weather_url= "http://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&appid=7ebc7734dc3a26afb1175d5b3760e122";

        StringRequest request=new StringRequest(Request.Method.GET,weather_url,new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                processResponse(response);
            }
            public void processResponse(String response){
                Gson gson=new Gson();
                weatherList weatherList=gson.fromJson(response, weatherList.class);

                textView9.setText("위도 : " +weatherList.coord.lat);
                textView10.setText("경도 : " + weatherList.coord.lon);
                textView11.setText("desc : "+transferWeather(weatherList.weather.get(0).description));
                textView12.setText("temp : "+Math.round((Double.valueOf(weatherList.main.temp)-273.15))+"도");
                textView13.setText("feels_like : "+Math.round((Double.valueOf(weatherList.main.feels_like)-273.15))+"도");
                textView14.setText("temp_min : "+Math.round((Double.valueOf(weatherList.main.temp_min)-273.15))+"도");
                textView15.setText("temp_max : "+Math.round((Double.valueOf(weatherList.main.temp_max)-273.15))+"도");
                textView16.setText("humidity : "+weatherList.main.humidity+"%");
                textView17.setText("wind spped : "+weatherList.wind.speed+"m/s");
                textView18.setText("wind deg : "+weatherList.wind.deg);
                textView19.setText("sunrise : "+weatherList.sys.sunrise);
                textView20.setText("sunset : "+weatherList.sys.sunset);





//                println("위도 : "+ weatherList.coord.lat);
//                println("경도 : "+weatherList.coord.lon);
                // println("main : "+weatherList.weather.get(0).main);
//                println("desc : "+transferWeather(weatherList.weather.get(0).description));
//
//                println("temp : "+Math.round((Double.valueOf(weatherList.main.temp)-273.15))+"도");
//                println("feels_like : "+Math.round((Double.valueOf(weatherList.main.feels_like)-273.15))+"도");
//                println("temp_min : "+Math.round((Double.valueOf(weatherList.main.temp_min)-273.15))+"도");
//                println("temp_max : "+Math.round((Double.valueOf(weatherList.main.temp_max)-273.15))+"도");
//
//                println("humidity : "+weatherList.main.humidity+"%");
//                println("wind spped : "+weatherList.wind.speed+"m/s");
//                println("wind deg : "+weatherList.wind.deg);
                //  println("country : "+weatherList.sys.country);
//                println("sunrise : "+weatherList.sys.sunrise);
//                println("sunset : "+weatherList.sys.sunset);
                //println("name : "+weatherList.name);
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
    private String transferWeather(String weather){
        weather=weather.toLowerCase();

        if(weather.equals("haze")){
            return"안개";
        }
        else if(weather.equals("fog")){
            return "안개";
        }
        else if(weather.equals("clouds")){
            return "구름";
        }
        else if(weather.equals("few clouds")){
            return "구름 조금";
        }
        else if(weather.equals("scattered clouds")){
            return "구름 낌";
        }
        else if(weather.equals("broken clouds")){
            return "구름 많음";
        }
        else if(weather.equals("overcast clouds")){
            return "구름 많음";
        }
        else if(weather.equals("clear sky")){
            return "맑음";
        }
        return "";
    }

}
