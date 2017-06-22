package com.bh.yibeitong.ui;

import android.content.Intent;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;

/**
 * Created by jingang on 2016/11/9.
 * 待支付 待收货 待评价
 */

public class StayToDoActivity extends BaseTextActivity{
    Intent intent;
    String title;
    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_staytodo);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        intent = getIntent();
        title = intent.getStringExtra("title");

        setTitleName(title);
        setTitleBack(true, 0);
    }
}
