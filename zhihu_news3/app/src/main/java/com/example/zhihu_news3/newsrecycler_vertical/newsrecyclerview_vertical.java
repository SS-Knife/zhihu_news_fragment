package com.example.zhihu_news3.newsrecycler_vertical;

/**
 * Created by 郝书逸 on 2018/2/2.
 */

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.zhihu_news3.MainActivity;
import com.example.zhihu_news3.NewsdetailActivity;
import com.example.zhihu_news3.R;
import com.example.zhihu_news3.Webconnection.webconnection;

import java.util.ArrayList;
import java.util.List;

public class newsrecyclerview_vertical extends Fragment {

    private RecyclerView mRecyclerView;

    private MyAdapter_recyclerview mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View  onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview,container,false);
        mLayoutManager = new LinearLayoutManager(this.getActivity() );
        mAdapter = new MyAdapter_recyclerview(getData());
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MyAdapter_recyclerview.OnItemClickListener(){
            @Override
            public void onItemClick(View view , int position){
                Intent intent=new Intent();
                intent.setClass(getActivity(), NewsdetailActivity.class);
                intent.putExtra("id",getData().get(position).getId());
                startActivity(intent);

            }
        });
        return view;
    }
    private List<newsfragments> getData(){
        int l= new webconnection(getPath(),0).getJsonAnalyze().getAnalyzeData().getTitle().length;
        List<newsfragments> data = new ArrayList<>();
        for (int i = 0; i <l ; i++) {
            data.add(new newsfragments(i,getPath()));
        }
        return  data;
    }
    private String getPath() {
        String path;
        String temp = "https://news-at.zhihu.com/api/4/news/";
        path=temp+"latest";
        // TODO: 2018/3/5 改上一行
        return path;
    }
}
