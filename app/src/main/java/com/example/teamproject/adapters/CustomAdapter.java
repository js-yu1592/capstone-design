package com.example.teamproject.adapters;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamproject.R;
import com.example.teamproject.models.Post;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {


    private ArrayList<Post> arrayList;
    private Context context;

    public CustomAdapter(ArrayList<Post> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //리스트뷰가 어댑터에 연결된다음에 최초로 뷰홀더만듬

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post,parent,false);
        CustomViewHolder holder=new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
      holder.email.setText("작성자 : "+arrayList.get(position).getDocumentId());
       holder.title.setText((arrayList.get(position).getTitle()));
        holder.contents.setText((arrayList.get(position).getContents()));

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

        }
    }
}
