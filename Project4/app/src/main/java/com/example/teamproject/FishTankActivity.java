package com.example.teamproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FishTankActivity extends Fragment {
    TextView textView;
    private View view;


    @Nullable
    @Override
    //프레그먼트는 onCreateView로 생성
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_fish_tank,container, false);
        textView=view.findViewById(R.id.fish_text);
        for(int i=0;i<Main2Activity.fishArr.size();i++) {
            println("fish_name : "+Main2Activity.fishArr.get(i).fish_name);
            println("fish_length : "+Main2Activity.fishArr.get(i).fish_length);
            println("fish_weight : "+Main2Activity.fishArr.get(i).fish_weight);
            println("fish_lat : "+Main2Activity.fishArr.get(i).fish_lat);
            println("fish_lon : "+Main2Activity.fishArr.get(i).fish_lon);
            println("fish_fishing : "+Main2Activity.fishArr.get(i).fish_fishing);
        }
        return view;
    }
    public void println(String data){
        textView.append(data+"\n");
    }
}

