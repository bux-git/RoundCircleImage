package com.dqr.www.roundcircleimage;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.dqr.www.roundcircleimage.custview.RoundImageView;

import java.util.Random;

/**
 * Description：
 * Author：LiuYM
 * Date： 2017-03-02 17:34
 */

public class RoundAdapter extends RecyclerView.Adapter<RoundAdapter.ViewHolder> {

private static final int [] imgRes={R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.four,R.drawable.five};
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RoundImageView view = new RoundImageView(parent.getContext());
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        params.setMargins(10, 10, 10, 10);
        int rand=new Random().nextInt(5);
        if(rand==0)rand=1;
        params.width=rand*150;

        int rand1=new Random().nextInt(5);
        if(rand1==0)rand1=1;
        params.height=rand1*150;
        view.setLayoutParams(params);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mView.setImageResource(imgRes[position%imgRes.length]);
        holder.mView.setType(position%2);
        holder.mView.setBorderRadius(position);
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RoundImageView mView;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = (RoundImageView) itemView;
        }
    }
}
