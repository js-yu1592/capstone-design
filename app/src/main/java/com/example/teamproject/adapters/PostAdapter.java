package com.example.teamproject.adapters;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamproject.R;
import com.example.teamproject.models.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    public PostAdapter(List<Post> datas) {
        this.datas = datas;
    }

    private List<Post> datas;

    @NonNull
    @Override

    //LayoutManager에 의해 새로운 아이템을 만들 때 호출하며, 이 아이템뷰의 ViewHolder을 만들어준다.
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post,parent,false));
}

    //LayoutManger에 의해 ViewHolder에 position에 맞는 데이터를 이용하여 리스트를 채우게된다.
    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {

        Post data= datas.get(position);   //position은 위에서부터 아래까지 0,1,2,3,4 등 위치
        holder.title.setText(data.getTitle());  //각각 position에 data.getTitle()을 넣어준다. 즉 제목을 넣어준다.
        holder.contents.setText(data.getContents());
    }

    //리사이클러뷰의 표시할 데이터의 개수 리턴
    @Override
    public int getItemCount() {
        return datas.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView contents;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.item_post_title);
            contents=itemView.findViewById(R.id.item_post_contents);
        }
    }
}
