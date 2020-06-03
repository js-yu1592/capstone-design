package com.example.teamproject.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.example.teamproject.HeaderViewHolder;
import com.example.teamproject.BasicActivity;
import com.example.teamproject.MyPostViewActivity;
import com.example.teamproject.MyProfileActivity;
import com.example.teamproject.PostViewActivity;
import com.example.teamproject.R;
import com.example.teamproject.models.Post;
import com.example.teamproject.myPostResult;

import java.util.ArrayList;
import java.util.logging.Handler;

public class MyPostAdapter extends RecyclerView.Adapter<MyPostAdapter.CustomViewHolder> {

    private SparseBooleanArray mSelectedItems=new SparseBooleanArray(0); //Item의 클릭 상태를 저장할 array 객체
    private ArrayList<myPostResult> arrayList;
    public static ArrayList<myPostResult> myPostArr=new ArrayList<myPostResult>();
    private Context context;
    private final int TYPE_HEADER = 0;
    private final int TYPE_ITEM = 1;
    private final int TYPE_FOOTER = 2;
    private Button button ;

    private static final String TAG="BAAM";

    public MyPostAdapter(ArrayList<myPostResult> arrayList, Context context ) {
        this.arrayList = arrayList;
        this.context = context;

    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //리스트뷰가 어댑터에 연결된다음에 최초로 뷰홀더만듬

        context=parent.getContext();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post,parent,false);
        CustomViewHolder holder=new CustomViewHolder(view);



        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
//        final Post post=arrayList.get(position);

        holder.email.setText("작성자 : "+ MyProfileActivity.UserNickname);
        Log.d(TAG,"POSTADATER : "+MyProfileActivity.UserNickname);
        holder.title.setText(arrayList.get(position).getBoard_title());
        holder.contents.setText(arrayList.get(position).getBoard_content());
        myPostArr=arrayList;
    }



    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;
        else if (position == arrayList.size() + 1)
            return TYPE_FOOTER;
        else
            return TYPE_ITEM;
    }



    @Override
    public int getItemCount() {
        return(arrayList!=null?arrayList.size():0);
    }



    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView contents;
        private TextView email;

        public CustomViewHolder(@NonNull View ItemView){
            super(ItemView);
            this.email=ItemView.findViewById(R.id.item_post_email);
            this.title=ItemView.findViewById(R.id.item_post_title);
            this.contents=ItemView.findViewById(R.id.item_post_contents);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){

                    final int position=getAdapterPosition(); //현재 클릭된 리사이클러뷰의 위치 파악

                    if(position!=RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(context, MyPostViewActivity.class);
                        Log.d(TAG,"POSTADATER : "+MyProfileActivity.UserNickname);
                        Log.d(TAG,"POSTADATER pos : "+position);
                        intent.putExtra("pos",position);
                        context.startActivity(intent);
                    }

//                    if(mSelectedItems.get(position,false)){
//                        mSelectedItems.put(position,false);
//                        v.setBackgroundColor(Color.WHITE);
//
//                    }
//                    else{
//                        mSelectedItems.put(position,true);
//                        v.setBackgroundColor(Color.BLUE);
//
//
//                    }

                }
            });


        }

        //컨텍스트 메뉴를 생성하고 메뉴 항목 선택시 호출되는 리스너를 등록
//        public void onCreateContextMenu(Context menu, View v, ContextMenu.ContextMenuInfo menuInfo){
//
//            MenuItem Edit =menu.add(Menu.NONE,1001,1,"편집");
//            MenuItem Delete=menu.add(Menu.NONE,1002,2,"삭제");
//            Edit.setOnMenuItemClickListener(onEditMenu);
//        }
    }
}