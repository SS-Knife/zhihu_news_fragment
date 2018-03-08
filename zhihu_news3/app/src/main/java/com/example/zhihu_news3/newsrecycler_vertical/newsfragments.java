package com.example.zhihu_news3.newsrecycler_vertical;

import com.example.zhihu_news3.Webconnection.webconnection;

/**
 * Created by 郝书逸 on 2018/3/7.
 */

public class newsfragments {
    String title;
    String images;
    int ga_prefix;
    int type;
    int id;
    int date;
    public newsfragments(int position,String path){
        this.type=new webconnection(path,0).getJsonAnalyze().getAnalyzeData().getType()[position];
        this.title=new webconnection(path,0).getJsonAnalyze().getAnalyzeData().getTitle()[position];
        this.date=new webconnection(path,0).getJsonAnalyze().getAnalyzeData().getDate();
        this.ga_prefix=new webconnection(path,0).getJsonAnalyze().getAnalyzeData().getGa_prefix()[position];
        this.id=new webconnection(path,0).getJsonAnalyze().getAnalyzeData().getId()[position];
        this.images=new webconnection(path,0).getJsonAnalyze().getAnalyzeData().getImages()[position];
    }

    public int getDate() {
        return date;
    }

    public int getGa_prefix() {
        return ga_prefix;
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public String getImages() {
        return images;
    }

    public String getTitle() {
        return title;
    }
}
