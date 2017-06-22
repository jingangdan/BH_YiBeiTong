package com.bh.yibeitong.ui.homepage;

import android.view.View;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;

/**
 * Created by jingang on 2017/6/20.
 * 主页特色服务
 */
public class SpecialActivity extends BaseTextActivity {
    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_special);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        initData();

        setTitleBack(true, 0);
        setTitleName("特色服务");


    }

    public void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }
}
