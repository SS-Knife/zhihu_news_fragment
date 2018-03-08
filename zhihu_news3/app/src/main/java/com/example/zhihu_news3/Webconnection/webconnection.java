package com.example.zhihu_news3.Webconnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhihu_news3.R;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static android.net.sip.SipErrorCode.SERVER_ERROR;

/**
 * Created by 郝书逸 on 2018/2/4.
 */
// TODO: 2018/2/10 修改 
public class webconnection {
    String path;
    int linktype;
    ImageView imageView;
    JsonAnalyze jsonAnalyze;
    public static String jsontext;
    public static final int GET_DATA_SUCCESS = 1;
    public static final int NETWORK_ERROR = 2;
    public static final int SERVER_ERROR = 3;
    public webconnection(String path,ImageView imageView){
        this.path = path;
        this.imageView=imageView;
        getdetail_drawable();
    }
    public webconnection(String path,int linktype){
        this.path = path;
        getdetail_json();
    }
    /*private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==GET_DATA_SUCCESS && imageView!=null) {
                Bitmap bitmap = (Bitmap) msg.obj;
                imageView.setImageBitmap(bitmap);
            }else{
                // TODO: 2018/3/8 删下面一行
                jsontext = msg.obj.toString();
                jsonAnalyze = new JsonAnalyze(msg.obj.toString(),linktype);
            }
        }
    };*/
    public void getdetail_drawable() {
        new Thread() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(path);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream;
                        inputStream = connection.getInputStream();
                        //Message msg = Message.obtain();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        //msg.obj = bitmap;
                        //msg.what = GET_DATA_SUCCESS;
                        //handler.sendMessage(msg);
                        imageView.setImageBitmap(bitmap);
                        inputStream.close();
                    } else {
                        //handler.sendEmptyMessage(SERVER_ERROR);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    //handler.sendEmptyMessage(NETWORK_ERROR);
                }finally {
                    if(connection!=null){
                        connection.disconnect();
                    }
                }
            }
        }.start();
    }
    public void getdetail_json() {

        new Thread() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(path);
                    connection = (HttpURLConnection) url.openConnection();
                    BufferedReader reader=null;
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream;
                        inputStream = connection.getInputStream();
                        //Message msg = Message.obtain();
                        reader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder json=new StringBuilder();
                        String line;
                        while((line =reader.readLine())!=null){
                            json.append(line);
                        }
                        jsontext = json.toString();
                        jsonAnalyze = new JsonAnalyze(json.toString(),linktype);
                        //msg.obj = json.toString();
                        //handler.sendMessage(msg);
                        inputStream.close();
                    } else {
                        //handler.sendEmptyMessage(SERVER_ERROR);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    //handler.sendEmptyMessage(NETWORK_ERROR);
                }finally {
                    if(connection!=null){
                        connection.disconnect();
                    }
                }

            }
        }.start();
    }

    public JsonAnalyze getJsonAnalyze() {
        return jsonAnalyze;
    }
}
