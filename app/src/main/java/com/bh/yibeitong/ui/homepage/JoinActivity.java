package com.bh.yibeitong.ui.homepage;

import android.content.Intent;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;

/**
 * Created by jingang on 2017/6/19.
 * 加盟
 */
public class JoinActivity extends BaseTextActivity {
    private WebView webView;

    /*接收页面传值*/
    Intent intent;
    private String url, title;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_join);
    }

    @Override
    protected void initWidght() {
        super.initWidght();

        initData();

        setTitleBack(true, 0);
        setTitleName("" + title);


    }

    /*组件 初始化*/
    public void initData() {
        intent = getIntent();
        url = intent.getStringExtra("url");
        title = intent.getStringExtra("title");

        System.out.println("aaaaaaa = "+url);
        webView = (WebView) findViewById(R.id.webView_join);
        webView.loadUrl(url);

        webView.setHorizontalScrollBarEnabled(false);//水平不显示
        webView.setVerticalScrollBarEnabled(false); //垂直不显示

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
    }
}
