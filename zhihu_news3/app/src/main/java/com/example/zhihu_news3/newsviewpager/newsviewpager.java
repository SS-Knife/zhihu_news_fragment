package com.example.zhihu_news3.newsviewpager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhihu_news3.MainActivity;
import com.example.zhihu_news3.R;
import com.example.zhihu_news3.Webconnection.AnalyzeData;
import com.example.zhihu_news3.Webconnection.JsonAnalyze;
import com.example.zhihu_news3.Webconnection.webconnection;
import com.example.zhihu_news3.detailActivity;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by 郝书逸 on 2018/2/4.
 */

public class newsviewpager extends Fragment {
    final String myurl ="https://news-at.zhihu.com/api/4/news/latest";
    private ViewPager viewPager;//图片滑动的控件
    private TextView textView;//图片上显示的文字
    private LinearLayout pointGroup;//滑动的指示
    webconnection webconnection;
    JsonAnalyze jsonAnalyze;
    AnalyzeData analyzeData= new AnalyzeData(20);
    private String[] imageArr;
    private String[] textArr;
    private String[] idArr;
    private ArrayList<ImageView> imgList;
    private int lastPointPosition;//上一个页面的位置索引,也是上一个指示器的索引
    /**
     * 为true才发生消息给handler,避免程序退出后还一直发送消息给handler
     */
    private boolean isRuning=true;
    /**
     * 用于实现自动滑动
     */
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1 && isRuning){
                //收到消息,开始滑动
                int currentItem = viewPager.getCurrentItem();//获取当前显示的界面的索引
                //如果当前显示的是最后一个页面,就显示第一张,否则显示下一张
                if(currentItem==imgList.size()-1){
                    viewPager.setCurrentItem(0);
                }else{
                    viewPager.setCurrentItem(currentItem+1);
                }
                //2ms后再发送消息,实现循环
                handler.sendEmptyMessageDelayed(1, 5000);
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewpager, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        textView = (TextView) view.findViewById(R.id.textView);
        pointGroup = (LinearLayout) view.findViewById(R.id.point_group);
        webconnection = new webconnection(myurl,1);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        jsonAnalyze= new JsonAnalyze(webconnection.getJsonData(),1);
        analyzeData =jsonAnalyze.getAnalyzeData();
        textArr = analyzeData.getTitle();
        imageArr = analyzeData.getImages();
        idArr = analyzeData.getId();

        initData();//初始化

        //添加适配器
        viewPager.setAdapter(new MyPagerAdapter());
        //为viewPager设置监听
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //页面改变的时候调用(稳定),positon表示被选中的索引
            @Override
            public void onPageSelected(int position) {
                textView.setText(textArr[position]);//设置文字描述内容
                //改变指示点的状态
                pointGroup.getChildAt(position).setEnabled(false);//将当前点enbale设置为false,让当前的指示器点击无效
                pointGroup.getChildAt(lastPointPosition).setEnabled(true);//将上一个点设置为true
                lastPointPosition=position;
            }
            /*当页面正在滚动的时候调用
             * int position, 页面的索引,指向的是左侧页面的索引
             * float positionOffset, 表示偏移量,  值为 0- 1 ,0表示相对于左侧页面没有偏移 , 1表示相对于左侧页面偏移1个单位长度(完全偏移)
              int positionOffsetPixels  表示偏移量,  值为 0-控件的绝对长度
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            /* 当页面滚动状态改变的时候调用
             * 当手指点击屏幕在屏幕上滚动的时候 状态为1
             * 当手指离开,viewpager自动  滚动   选中页面时  状态为2
             * 当手指离开,自动 选中上页面时    状态为0
             */
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //发送第一条消息给handler,实现循环
        handler.sendEmptyMessageDelayed(1, 5000);
        return view;
    }


    /**
     * 初始化数据,将图片和指示器都创建出来添加到集合
     * 这里通过数组的长度,动态添加图片和指示器,有利于从网络上获取数据显示
     */
    private void initData(){
        Log.d("Test", "initData: ");
        imgList=new ArrayList<ImageView>();
        for (int i=0;i<imageArr.length;i++) {
            //初始化图片
            ImageView image=new ImageView(getActivity());
            new webconnection(imageArr[i],image).getdetail_drawable();
            final String id = idArr[i];
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent();
                    intent.putExtra("id",id);
                    intent.setClass(getActivity(), detailActivity.class);
                    startActivity(intent);
                }
            });
            //image.setBackgroundResource(imageArr[i]);
            imgList.add(image);
            //添加图片的指示点
            final ImageView point=new ImageView(getActivity());
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(20,20);//布局参数,point的布局宽与高
            params.rightMargin=10;//右边距
            point.setLayoutParams(params);//设置布局参数
            point.setBackgroundResource(R.drawable.point_forced);//point_bg是根据setEnabled的值来确定形状的
            if(i==0){
                point.setEnabled(false);//初始化的时候设置第一张图片的形状
            }else{
                point.setEnabled(true);//根据该属性来确定这个图片的显示形状
            }
            point.setClickable(true);//设置为可点击
            //为point设置单击事件,实现点击后ViewPager显示对应的图片
            point.setTag(i);//设置标记,好知道点击的指示器的索引
            point.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (Integer) point.getTag();//获取这个指示器的标记
                    viewPager.setCurrentItem(tag);//设置当前显示的为第tag张图片,执行后会调用监听事件,所以指示器颜色的变化不需要在这里处理
                }
            });
            pointGroup.addView(point);//将该指示的图片添加到布局中
        }
        textView.setText(textArr[0]);//将第一次显示的文本初始化到视图中
    }

    /**
     *ViewPager的适配器，原理:
     *它最多只会创建三个图片(即当前屏幕显示的这张图片与该图片的左右两张图片，当滑动的时候，就会把多余的销毁掉，需要的创建出来)
     */
    private class MyPagerAdapter extends PagerAdapter {
        /**
         * 获得页面的总数
         */
        @Override
        public int getCount() {
            return imgList.size();
        }
        /**
         * 判断view和object的对应关系,如果当前要显示的控件是来之于instantiateItem方法创建的就显示,否则不显示
         * object 为instantiateItem方法返回的对象
         * 如果为false就不会显示该视图
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
        /**
         * 实例化下一个要显示的子条目,获取相应位置上的view,这个为当前显示的视图的下一个需要显示的控件
         * container  view的容器,其实就是viewager自身
         * position   ViewPager相应的位置
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imgList.get(position));
            return imgList.get(position);
        }
        /**
         * 销毁一个子条目,object就为instantiateItem方法创建的返回的对象,也是滑出去需要销毁了的视图对象
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            object=null;
        }
    }

    /**
     * 当程序退出时,结束handler消息的发送
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        isRuning=false;
    }


}

