package com.example.zhihu_news3.Webconnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
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
import java.net.URLConnection;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;


import javax.net.ssl.HttpsURLConnection;

import static android.content.ContentValues.TAG;
import static android.net.sip.SipErrorCode.SERVER_ERROR;

/**
 * Created by 郝书逸 on 2018/2/4.
 */
// TODO: 2018/2/10 修改 
public class webconnection {
    private String path;
    private int linktype;
    private ImageView imageView;
    private String jsonData;
    private JsonAnalyze jsonAnalyze;
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
        this.linktype = linktype;
        jsonData=get(path);
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==GET_DATA_SUCCESS && imageView!=null) {
                Bitmap bitmap = (Bitmap) msg.obj;
                imageView.setImageBitmap(bitmap);
            }else{
                // TODO: 2018/3/8 删下面一行
                //jsonData = msg.obj.toString();
                //jsonAnalyze = new JsonAnalyze(msg.obj.toString(),linktype);
            }
        }
    };
    public void getdetail_drawable() {
        new Thread() {
            @Override
            public void run() {
                HttpsURLConnection connection = null;
                try {
                    URL url = new URL(path);
                    connection = (HttpsURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    Log.d(TAG, "run: ");
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream;
                        inputStream = connection.getInputStream();
                        Message msg = Message.obtain();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        msg.obj = bitmap;
                        msg.what = GET_DATA_SUCCESS;
                        handler.sendMessage(msg);
                        inputStream.close();
                    } else {
                        handler.sendEmptyMessage(SERVER_ERROR);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(NETWORK_ERROR);
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
                        Message msg = Message.obtain();
                        reader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder json=new StringBuilder();
                        String line;
                        while((line =reader.readLine())!=null){
                            json.append(line);
                        }
                        msg.obj = json.toString();
                        handler.sendMessage(msg);
                        inputStream.close();
                    } else {
                        handler.sendEmptyMessage(SERVER_ERROR);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(NETWORK_ERROR);
                }finally {
                    if(connection!=null){
                        connection.disconnect();
                    }
                }

            }
        }.start();
    }
    public static String get(final String url) {
        final StringBuilder sb = new StringBuilder();
        FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                BufferedReader br = null;
                InputStreamReader isr = null;
                URLConnection conn;
                try {
                    URL geturl = new URL(url);
                    conn = geturl.openConnection();//创建连接
                    conn.connect();//get连接
                    isr = new InputStreamReader(conn.getInputStream());//输入流
                    br = new BufferedReader(isr);
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);//获取输入流数据
                    }
                    System.out.println(sb.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {//执行流的关闭
                    if (br != null) {
                        try {
                            if (br != null) {
                                br.close();
                            }
                            if (isr != null) {
                                isr.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } }}
                return sb.toString();
            }
        });
        new Thread(task).start();
        String s = null;
        try {
            s = task.get();//异步获取返回值
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public String getJsonData(){return jsonData;}
    public JsonAnalyze getJsonAnalyze() {
        return jsonAnalyze;
    }
}
