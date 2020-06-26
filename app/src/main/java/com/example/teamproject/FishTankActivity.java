package com.example.teamproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teamproject.Main2Activity;
import com.example.teamproject.R;
import com.example.teamproject.adapters.MapfishAdapter;
import com.example.teamproject.adapters.MyfishAdapter;

import java.util.ArrayList;
import java.util.List;



public class FishTankActivity extends Fragment {
    TextView textView;
    private View view;
    private String lat;
    private String lon;
    private double lati;
    private double longi;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerDecoration spaceDecoration;

    @Nullable
    @Override
    //프레그먼트는 onCreateView로 생성
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_fish_tank,container, false);



        recyclerView=(RecyclerView)view.findViewById(R.id.main_recyclerview); //아이디 연결
        recyclerView.setHasFixedSize(true); //리사이클러뷰 기존 성능강화
        layoutManager=new LinearLayoutManager(getActivity().getApplicationContext());
        spaceDecoration=new RecyclerDecoration(40);     //위아래 간격조정
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getResources())); // 왼쪽 오른쪽 마진
        recyclerView.addItemDecoration(spaceDecoration);
        ArrayList<fishListResult> mapfish=new ArrayList<fishListResult>();
        recyclerView.setLayoutManager(layoutManager);

        //layoutManager은 많은 역할을 하지만 간단하게 스크롤을 위아래로 할지 좌우로 할지 결정하는것




//        textView=view.findViewById(R.id.fish_text);
//
        lat= getArguments().getString("lat");
        lon= getArguments().getString("lon");

        lati=Double.parseDouble(lat);
        longi=Double.parseDouble(lon);

        for(int i = 0; i< Main2Activity.fishArr.size(); i++) {
            if((lati-0.05)<=Double.parseDouble(Main2Activity.fishArr.get(i).fish_lat)&&
                    (lati+0.05)>=Double.parseDouble(Main2Activity.fishArr.get(i).fish_lat)&&
                    (longi-0.05)<=Double.parseDouble(Main2Activity.fishArr.get(i).fish_lon)&&
                    (longi+0.05)>=Double.parseDouble(Main2Activity.fishArr.get(i).fish_lon)) {
                    mapfish.add(Main2Activity.fishArr.get(i));
                    if(mapfish.size()==0){
                        Toast.makeText(getActivity().getApplicationContext(),"주변에서 잡힌 물고기가 없습니다.",Toast.LENGTH_LONG).show();
                    }

            }
        }
        adapter=new MapfishAdapter(mapfish, getActivity().getApplicationContext()); //CustomAdapter로 설정.
        //어댑터는 담긴 리스트들을 리사이클러 뷰에 바인딩 시켜주기 위한 사전작업이 이루어지는 객체
        recyclerView.setAdapter(adapter); //리사이클러뷰 어댑터 연결
        return view;
    }
    public void println(String data){
        textView.append(data+"\n");
    }

}

