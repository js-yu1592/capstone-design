package com.example.teamproject.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teamproject.PostViewActivity;
import com.example.teamproject.R;
import com.example.teamproject.models.comment;


import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter  {
    private Context mContext;
    private TextView nickname;
    private TextView content;
    private Activity mActivity;
    private static final String TAG = "BAAM";
    //Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<comment> commetItem = new ArrayList<comment>();

    public CommentAdapter() {

    }

    public int getCount() {
        return commetItem.size();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.comment_item, parent, false);
        }
        nickname = (TextView) convertView.findViewById(R.id.nickname);
        content = (TextView) convertView.findViewById(R.id.content);


        comment comment1= commetItem.get(position);

        nickname.setText(comment1.getNickname());

        content.setText(comment1.getContent());

        return convertView;


    }

    public long getItemId(int position) {
        return position;
    }

    public Object getItem(int positon){
              return commetItem.get(positon);
    }
   public void addItem(String nickname, String content){


        comment item=new comment();
        item.setNickname(nickname);

        item.setContent(content);


        commetItem.add(item);

   }
        }

