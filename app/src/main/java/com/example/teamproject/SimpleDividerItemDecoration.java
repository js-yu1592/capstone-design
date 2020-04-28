package com.example.teamproject;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivider;
    private Resources resources;

    /// 이 클래스는 왼쪽 오른쪽 마진 주기위해서 사용

    public SimpleDividerItemDecoration(Resources resources){

        this.resources=resources;
        mDivider=this.resources.getDrawable(R.drawable.divider);
    }

    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state){

        int left=this.resources.getDimensionPixelOffset(R.dimen.margin_20);  //values 폴더에 dimen 에 미리 설정해놓은값
        int right=parent.getWidth()- this.resources.getDimensionPixelSize(R.dimen.margin_20);

        int childCount=parent.getChildCount();

        Log.d("ASdasd","child count:"+childCount);
        for(int i=0; i<childCount; i++){
            View child= parent.getChildAt(i);

            RecyclerView.LayoutParams params=(RecyclerView.LayoutParams) child.getLayoutParams();

            int top=child.getBottom() + params.bottomMargin;
            int bottom=top+mDivider.getIntrinsicHeight();

            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);
        }



    }
}
