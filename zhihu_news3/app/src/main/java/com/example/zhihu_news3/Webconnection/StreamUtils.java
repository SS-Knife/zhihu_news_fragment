package com.example.zhihu_news3.Webconnection;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by 郝书逸 on 2018/2/12.
 */

public class StreamUtils {
    public static String parseSteam(InputStream inputStream) {
        try {
            //定义一个字节数组输出流
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            //定义一个字节数组
            byte[] buffer = new byte[1024];
            //定义初始长度
            int len = 0;
            while((len = inputStream.read(buffer))!=-1){
                //将读的内容写到字节数组输出流中
                outputStream.write(buffer, 0, len);
            }
            //将字节输出流转化成字符串
            return outputStream.toString("utf-8");
        } catch (Exception e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
