package com.example.zhihu_news3.newsrecycler_vertical;

import com.example.zhihu_news3.Webconnection.AnalyzeData;
import com.example.zhihu_news3.Webconnection.JsonAnalyze;
import com.example.zhihu_news3.Webconnection.webconnection;

/**
 * Created by 郝书逸 on 2018/3/7.
 */

public class newsfragments {
    webconnection webconnection;
    AnalyzeData analyzeData;
    String title;
    String images;
    int ga_prefix;
    int type;
    String id;
    int date;
    public newsfragments(int position,String path){
        webconnection = new webconnection(path,0);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        analyzeData = new JsonAnalyze(webconnection.getJsonData(),0).getAnalyzeData();
        this.type=analyzeData.getType()[position];
        this.title=analyzeData.getTitle()[position];
        this.date=analyzeData.getDate();
        this.ga_prefix=analyzeData.getGa_prefix()[position];
        this.id=analyzeData.getId()[position];
        this.images=analyzeData.getImages()[position];
    }

    public int getDate() {
        return date;
    }

    public int getGa_prefix() {
        return ga_prefix;
    }

    public String getId() {
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
