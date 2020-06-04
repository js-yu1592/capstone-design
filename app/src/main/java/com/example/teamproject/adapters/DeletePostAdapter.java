//package com.example.teamproject.adapters;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.ClipData;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Color;
//import android.util.Log;
//import android.util.SparseBooleanArray;
//import android.view.ContextMenu;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.android.volley.Response;
//import com.example.teamproject.HeaderViewHolder;
//import com.example.teamproject.BasicActivity;
//import com.example.teamproject.MyPostActivity;
//import com.example.teamproject.MyPostViewActivity;
//import com.example.teamproject.MyProfileActivity;
//import com.example.teamproject.PostViewActivity;
//import com.example.teamproject.R;
//import com.example.teamproject.models.Post;
//import com.example.teamproject.myDeleteResult;
//import com.example.teamproject.myPostResult;
//
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.logging.Handler;
//
//public class DeletePostAdapter extends RecyclerView.Adapter<DeletePostAdapter.CustomViewHolder> {
//    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener;
//    private SparseBooleanArray mSelectedItems=new SparseBooleanArray(0); //Item의 클릭 상태를 저장할 array 객체
//    private ArrayList<myDeleteResult> arrayList;
//    public static ArrayList<myDeleteResult> myDeleteArr=new ArrayList<myDeleteResult>();
//    ArrayList<String> checked_items=new ArrayList<>();
//    private Context context;
//    private final int TYPE_HEADER = 0;
//    private final int TYPE_ITEM = 1;
//    private final int TYPE_FOOTER = 2;
//    private Button button ;
//
//    private static final String TAG="BAAM";
//
//    public DeletePostAdapter(ArrayList<myDeleteResult> arrayList, Context context ) {
//        this.arrayList = arrayList;
//        this.context = context;
//
//
//    }
//
//    @NonNull
//    @Override
//    public DeletePostAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //리스트뷰가 어댑터에 연결된다음에 최초로 뷰홀더만듬
//
//        context=parent.getContext();
//        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_delete,parent,false);
//        DeletePostAdapter.CustomViewHolder holder=new DeletePostAdapter.CustomViewHolder(view);
//
//
//
//        return holder;
//    }
//
//    public void onBindViewHolder(@NonNull DeletePostAdapter.CustomViewHolder holder, int position) {
////        final Post post=arrayList.get(position);
//
//        final myDeleteResult objectIncome=arrayList.get(position);
//        holder.email.setText("작성자 : "+ MyProfileActivity.UserNickname);
//        Log.d(TAG,"POSTADATER : "+MyProfileActivity.UserNickname);
//        holder.title.setText(arrayList.get(position).getBoard_title());
//        holder.contents.setText(arrayList.get(position).getBoard_content());
//       holder.cvSelect.setText(arrayList.get(position).getBoard_title());
//
//
//
//        myDeleteArr=arrayList;
//
//
//    };
//
//    @Override
//    public int getItemViewType(int position) {
//        if (position == 0)
//            return TYPE_HEADER;
//        else if (position == arrayList.size() + 1)
//            return TYPE_FOOTER;
//        else
//            return TYPE_ITEM;
//    }
//    public int getItemCount() {
//        return(arrayList!=null?arrayList.size():0);
//    }
//
//
//
//    public class CustomViewHolder extends RecyclerView.ViewHolder {
//        private TextView title;
//        private TextView contents;
//        private TextView email;
//        public CheckBox cvSelect;
//
//        public CustomViewHolder(@NonNull View ItemView){
//            super(ItemView);
//            this.email=ItemView.findViewById(R.id.item_post_email);
//            this.title=ItemView.findViewById(R.id.item_post_title);
//            this.contents=ItemView.findViewById(R.id.item_post_contents);
//            cvSelect=(CheckBox) ItemView.findViewById(R.id.checkBox);
//
//            itemView.setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick(View v){
//
//                    final int position=getAdapterPosition(); //현재 클릭된 리사이클러뷰의 위치 파악
//
//                    if(position!=RecyclerView.NO_POSITION) {
//                        Intent intent = new Intent(context, MyPostViewActivity.class);
//
//
//                        Log.d(TAG,"POSTADATER pos : "+position);
//                        intent.putExtra("pos",position);
//
//                        context.startActivity(intent);
//                    }
//
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
//
//                }
//            });
//
//
//        }
//
//
//
//}
