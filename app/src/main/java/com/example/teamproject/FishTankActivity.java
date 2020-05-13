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
        println("fish_name : 광어");
        println("fish_length : 7.80cm");
        println("fish_weight : 5.80kg");
        println("fish_lat : 37.3062275");
        println("fish_lon : 126.8149098");
        println("fish_fishing : 송라 낚시터");

        return view;
    }
    public void println(String data){
        textView.append(data+"\n");
    }
}

