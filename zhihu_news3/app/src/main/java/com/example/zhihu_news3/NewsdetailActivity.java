package com.example.zhihu_news3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URL;

/**
 * Created by 郝书逸 on 2018/2/10.
 */

public class NewsdetailActivity extends AppCompatActivity {
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsdetail);
        path = "http://daily.zhihu.com/"+ getIntent().getStringExtra("id");
        WebView wv = (WebView)findViewById(R.id.mwebview);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient());
        wv.loadUrl(path);
    }
}
