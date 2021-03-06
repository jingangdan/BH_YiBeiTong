package com.bh.yibeitong.ui;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;

/**
 * Created by jingang on 2016/10/21.
 * 我的跑腿
 */

public class MyRunLegActivity extends BaseTextActivity {
    private Intent getIntent;
    private String str_intent;
    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_my_run_leg);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        getIntent = getIntent();
        str_intent = getIntent.getStringExtra("title");

        setTitleName(str_intent);//title

        setTitleBack(true, 0);//返回上一层

//        setTitleRightText("保存");//右侧文字
        showTitleRes(R.id.title_message);//扩展menu（图标）
        //goneTitleRes(R.id.title_add);隐藏图标，一般用不到


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.title_back){
            //workFragment.titleSearch();
            finish();
        }

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.title_message://添加
                toast("消息");

                break;
            //case R.id.title_apps://应用break;
            case R.id.title_setting://设置
                toast("设置");
                break;
        }
        return super.onMenuItemClick(item);
    }
}
