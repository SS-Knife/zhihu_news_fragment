package com.example.zhihu_news3.newsrecycler_vertical;

/**
 * Created by 郝书逸 on 2018/2/2.
 */

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.zhihu_news3.MainActivity;
import com.example.zhihu_news3.R;
import com.example.zhihu_news3.Webconnection.AnalyzeData;
import com.example.zhihu_news3.Webconnection.JsonAnalyze;
import com.example.zhihu_news3.Webconnection.webconnection;
import com.example.zhihu_news3.detailActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class newsrecyclerview_vertical extends Fragment {

    private RecyclerView mRecyclerView;

    private MyAdapter_recyclerview mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    private AnalyzeData analyzeData;

    int[]data;

    int past;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview, container, false);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mAdapter = new MyAdapter_recyclerview(getData(), getPath());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MyAdapter_recyclerview.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), detailActivity.class);
                intent.putExtra("id", mAdapter.getItemid(position));
                startActivity(intent);

            }
        });
        return view;
    }


    private int[] getData() {
        webconnection webconnection = new webconnection(getPath(), 0);
        analyzeData = new JsonAnalyze(webconnection.getJsonData(), 0).getAnalyzeData();
        int l = analyzeData.getTitle().length;
        data = new int[l];
        for (int i = 0; i < l; i++) {
            data[i] = i;
        }
        return data;
    }

    private String getPath() {
        String path;
        path = "https://news-at.zhihu.com/api/4/news/latest";
        // TODO: 2018/3/5 改上一行
        return path;
    }

    private int[] updateData(){
        webconnection webconnection = new webconnection(updatePath(),0);
        analyzeData = new JsonAnalyze(webconnection.getJsonData(), 0).getAnalyzeData();
        int[]data=new int[this.data.length+analyzeData.getTitle().length];
        for (int i = 0; i <data.length ; i++) {
            data[i] = i;
        }
        this.data= data;
        return data;
    }
    private String updatePath(){
        String path;
        String temp = "https://news-at.zhihu.com/api/4/news/";
        path = temp + getOldDate(-past);
        return path;
    }
    public static String getOldDate(int distanceDay) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) + distanceDay);
        Date endDate = null;
        try {
            endDate = dft.parse(dft.format(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dft.format(endDate);
    }
}
