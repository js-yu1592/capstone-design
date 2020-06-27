package com.example.teamproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FishTankActivity extends Fragment {
    TextView textView;
    TextView textView21;
    TextView textView22;
    TextView textView23;
    TextView textView24;
    TextView textView25;


    private View view;
    private String lat;
    private String lon;
    private double lati;
    private double longi;

    @Nullable
    @Override
    //프레그먼트는 onCreateView로 생성
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_fish_tank,container, false);

        textView=view.findViewById(R.id.fish_text);
        textView21=view.findViewById(R.id.textView21);
        textView22=view.findViewById(R.id.textView22);
        textView23=view.findViewById(R.id.textView23);
        textView24=view.findViewById(R.id.textView24);
        textView25=view.findViewById(R.id.textView25);



        lat= getArguments().getString("lat");
        lon= getArguments().getString("lon");

        lati=Double.parseDouble(lat);
        longi=Double.parseDouble(lon);

        for(int i=0;i<BasicActivity.fishArr.size();i++) {
            if((lati-0.05)<=Double.parseDouble(BasicActivity.fishArr.get(i).fish_lat)&&
                    (lati+0.05)>=Double.parseDouble(BasicActivity.fishArr.get(i).fish_lat)&&
                    (longi-0.05)<=Double.parseDouble(BasicActivity.fishArr.get(i).fish_lon)&&
                    (longi+0.05)>=Double.parseDouble(BasicActivity.fishArr.get(i).fish_lon)) {

                textView.setText("물고기 이름 : " + BasicActivity.fishArr.get(i).fish_name);
                textView21.setText("물고기 길이 : " + BasicActivity.fishArr.get(i).fish_length);
                textView22.setText("물고기 무게 : " + BasicActivity.fishArr.get(i).fish_weight);
                textView23.setText("낚시터 위치  : " + BasicActivity.fishArr.get(i).fish_lat+ ","+"\n"+BasicActivity.fishArr.get(i).fish_lon);
                textView24.setText("낚시터 : " + BasicActivity.fishArr.get(i).fish_fishing);
                textView25.setText("코멘트 : " + BasicActivity.fishArr.get(i).fish_comment);

//                println("fish_name : " + Main2Activity.fishArr.get(i).fish_name);
//                println("fish_length : " + Main2Activity.fishArr.get(i).fish_length);
//                println("fish_weight : " + Main2Activity.fishArr.get(i).fish_weight);
//                println("fish_lat : " + Main2Activity.fishArr.get(i).fish_lat);
//                println("fish_lon : " + Main2Activity.fishArr.get(i).fish_lon);
//                println("fish_fishing : " + Main2Activity.fishArr.get(i).fish_fishing);
            }
        }
        return view;
    }
    public void println(String data){
        textView.append(data+"\n");
    }



}

