package com.example.zhihu_news3.newsrecycler_vertical;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.zhihu_news3.R;
import com.example.zhihu_news3.Webconnection.webconnection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 郝书逸 on 2018/2/2.
 */

public class MyAdapter_recyclerview extends RecyclerView.Adapter<MyAdapter_recyclerview.ViewHolder>implements View.OnClickListener{
    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }
    private OnItemClickListener mOnItemClickListener = null;
    private List<newsfragments> data;
    public MyAdapter_recyclerview(List<newsfragments> data) {
        this.data = data;
    }

    public void updateData( List<newsfragments> data) {
        this.data =data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.newsfragment, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 绑定数据
        holder.mTv.setText(data.get(position).getTitle());
        new webconnection(data.get(position).getImages(),holder.mIv);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTv;
        ImageView mIv;

        public ViewHolder(View itemView) {
            super(itemView);
            mTv = (TextView) itemView.findViewById(R.id.newstitle);
            mIv = (ImageView) itemView.findViewById(R.id.newsimage);
        }
    }
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
