package com.example.zhihu_news3;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.zhihu_news3.Webconnection.webconnection;
import com.example.zhihu_news3.newsrecycler_vertical.newsrecyclerview_vertical;
import com.example.zhihu_news3.newsviewpager.newsviewpager;

import java.util.ArrayList;

import static com.example.zhihu_news3.Webconnection.webconnection.jsontext;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

}
