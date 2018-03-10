package com.example.zhihu_news3.Webconnection;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

import static android.content.ContentValues.TAG;

/**
 * Created by 郝书逸 on 2018/2/14.
 */

public class JsonAnalyze {
    String jsonData;
    JSONObject jsonObject;
    AnalyzeData analyzeData;
    JSONArray jsonArray_stories;
    JSONArray jsonArray_top_stories;
    int l;
    public JsonAnalyze(String jsonData,int linktype){
        this.jsonData=jsonData;
        try {
            jsonObject = new JSONObject(jsonData);
            switch (linktype){
                case 0:
                    jsonArray_stories = jsonObject.getJSONArray("stories");
                    l=jsonArray_stories.length();
                    break;
                case 1:
                    jsonArray_top_stories = jsonObject.getJSONArray("top_stories");
                    l=jsonArray_top_stories.length();
                    break;
                default:
                    l=1;
                    break;
            }
            analyzeData = new AnalyzeData(l);
            parseJSONWithJSONObject(linktype);
            //analyzeData = new AnalyzeData(l);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void parseJSONWithJSONObject(int linktype){
        try{
            String[]title = new String[l];
            String[]images = new String[l];
            String[]body = new String[l];
            String[]image_source = new String[l];
            String[]js = new String[l];
            String[]share_url = new String[l];
            String[]thumbnail = new String[l];
            String[]css = new String[l];
            String[]editor_name = new String[l];
            int[]type = new int[l];
            int[]ga_prefix = new int[l];
            String[]id = new String[l];
            int status = 0;
            int date = 0;
            String latest = " ";
            String msg = " ";
            switch(linktype){
                //最新消息
                case 0:
                    date = jsonObject.getInt("date");

                    for (int i = 0; i <jsonArray_stories.length(); i++) {
                        JSONObject jsonObject_story = jsonArray_stories.getJSONObject(i);
                        title[i]=jsonObject_story.getString("title");
                        ga_prefix[i]=jsonObject_story.getInt("ga_prefix");
                        images[i]=jsonObject_story.getJSONArray("images").getString(0);
                        type[i]=jsonObject_story.getInt("type");
                        id[i]=jsonObject_story.getString("id");
                    }


                    break;
                //最新消息滚动条
                case 1:

                    for (int i = 0; i <jsonArray_top_stories.length(); i++) {
                        JSONObject jsonObject_top_story = jsonArray_top_stories.getJSONObject(i);
                        title[i]=jsonObject_top_story.getString("title");
                        ga_prefix[i]=jsonObject_top_story.getInt("ga_prefix");
                        images[i]=jsonObject_top_story.getString("image");
                        type[i]=jsonObject_top_story.getInt("type");
                        id[i]=jsonObject_top_story.getString("id");
                    }

                    break;
                //消息内容
                case 2:
                    title[0]=jsonObject.getString("title");
                    ga_prefix[0]=jsonObject.getInt("ga_prefix");
                    images[0]=jsonObject.getString("image");
                    type[0]=jsonObject.getInt("type");
                    id[0]=jsonObject.getString("id");
                    body[0]=jsonObject.getString("body");
                    image_source[0]=jsonObject.getString("image_source");
                    js[0]=jsonObject.getString("js");
                    share_url[0]=jsonObject.getString("share_url");
                    thumbnail[0]=jsonObject.getString("thumbnail");
                    css[0]=jsonObject.getString("css");
                    editor_name[0]=jsonObject.getString("editor_name");
                    break;
                //版本查询
                case 3:
                    status=jsonObject.getInt("status");
                    latest=jsonObject.getString("latest");
                    break;
            }
            analyzeData = new AnalyzeData(13);
            analyzeData.setDate(date);
            analyzeData.setBody(body);
            analyzeData.setCss(css);
            analyzeData.setEditor_name(editor_name);
            analyzeData.setGa_prefix(ga_prefix);
            analyzeData.setId(id);
            analyzeData.setImage_source(image_source);
            analyzeData.setImages(images);
            analyzeData.setJs(js);
            analyzeData.setLatest(latest);
            analyzeData.setMsg(msg);
            analyzeData.setShare_url(share_url);
            analyzeData.setStatus(status);
            analyzeData.setThumbnail(thumbnail);
            analyzeData.setTitle(title);
            analyzeData.setType(type);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public AnalyzeData getAnalyzeData() {
        return analyzeData;
    }
}
