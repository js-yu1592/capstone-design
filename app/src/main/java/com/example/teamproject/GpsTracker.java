package com.example.teamproject;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class GpsTracker extends Service  {
    private final Context mContext;
    private static final String TAG="BAAM";
    Location location;
    double latitude;
    double longitude;
    String result;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    protected LocationManager locationManager;


    public GpsTracker(Context context) {
        this.mContext = context;
        getLocation();
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            //현재 GPS 정보가져오기
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            //현재 네트워크 상태값 알아오기
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
            //GPS와 네트워크 사용이 가능하지 않을 때 소스구현
            } else {

                int hasFineLocationPermission = ContextCompat.checkSelfPermission(mContext,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(mContext,
                        Manifest.permission.ACCESS_COARSE_LOCATION);


                if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                        hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

                    ;
                } else
                    return null;

                //네트워크 정보로부터 위치값 가져오기
                if (isNetworkEnabled) {


                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);

                    if (locationManager != null)
                    {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null)

                        {
                            //위도경도저장
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }


                if (isGPSEnabled)
                {
                    if (location == null)
                    {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
                        if (locationManager != null)
                        {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null)
                            {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            Log.d("@@@", ""+e.toString());
        }

        return location;
    }

    public double getLatitude()
    {
        if(location != null)
        {
            latitude = location.getLatitude();
        }

        return latitude;
    }

    public double getLongitude()
    {
        if(location != null)
        {
            longitude = location.getLongitude();
        }

        return longitude;
    }
private LocationListener locationListener=new LocationListener() {
    @Override
    public void onLocationChanged(Location location) {
   // getWeatherData();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

};
    @Override
    public IBinder onBind(Intent arg0)
    {
        return null;
    }




    public void getWeatherData(double lat, double lon){

        String url="https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&appid=7ebc7734dc3a26afb1175d5b3760e122";
          Log.d(TAG,"SDFDS");

       ReceiveWeatherTask receiveWeatherTask=new ReceiveWeatherTask();
     receiveWeatherTask.execute(url);
    }
    public class ReceiveWeatherTask extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        public JSONObject doInBackground(String...datas){

            try{
                HttpURLConnection conn=(HttpURLConnection)new URL(datas[0]).openConnection();
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(10000);
                conn.connect();
                 Log.d(TAG,"13123123");
                if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
                    String res=Integer.toString(conn.getResponseCode());
                    String http=Integer.toString(HttpURLConnection.HTTP_OK);
                    Log.d(TAG,res);
                    Log.d(TAG,http);
                    InputStream is=conn.getInputStream();
                    InputStreamReader reader=new InputStreamReader(is);
                    BufferedReader in=new BufferedReader(reader);

                    String read;
                    while ((read = in.readLine()) != null) {
                        JSONObject jObject=new JSONObject(read);
                      // String result =jObject.getJSONArray("weather").getJSONObject(0).getString("icon");
                        return jObject;
                    }
                }else{
                    Log.d(TAG,"abcbvbxcbxc");
                    return null;
                }
                return null;

            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onPostExecute(JSONObject result){
            final String TAG = "HYOGIL";

            Log.i(TAG,result.toString());

            if(result!=null){
                String iconName="";
                String nowTemp="";
                String maxTemp="";
                String minTemp="";
                String humidity="";
                String speed="";
                String main="";
                String description="";

                try{
                    iconName=result.getJSONArray("weather").getJSONObject(0).getString("icon");
                    nowTemp=result.getJSONObject("main").getString("temp");
                    humidity=result.getJSONObject("main").getString("humidity");
                    minTemp=result.getJSONObject("main").getString("temp_min");
                    maxTemp=result.getJSONObject("main").getString("temp_max");
                    speed=result.getJSONObject("wind").getString("speed");
                    main=result.getJSONArray("weather").getJSONObject(0).getString("main");
                    description=result.getJSONArray("weather").getJSONObject(0).getString("description");
                    Log.i(TAG,humidity);

                }catch(JSONException e){
                    e.printStackTrace();
                }
                description=transferWeather(description);

                final String msg=description+"습도"+humidity+"%, 풍속 "
                        +speed+"m/s"+"온도 현재:"+nowTemp+" / 최저:"+minTemp+" /최고:"+maxTemp;
                Log.i(TAG,msg);
     Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();

            }

        }
        public String transferWeather(String weather){
            weather=weather.toLowerCase();

            if(weather.equals("haze")){
                return"안개";
            }
            if(weather.equals("light rain")){
                return "비";
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

    }



