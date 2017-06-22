package com.bh.yibeitong.ui;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;

/**
 * Created by jingang on 2016/10/21.
 * 帮我送
 */

public class HelpSendActivity extends BaseTextActivity{
    private Intent getIntent;
    private String str_intent;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_help_send);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        getIntent = getIntent();
        str_intent = getIntent.getStringExtra("title");


        setTitleName(str_intent);//title
//                  setTitleBack(false,R.mipmap.ic_search);//有图标，但不是返回
        setTitleBack(true,0);//返回
//                  setTitleRightText("保存");//右侧文字
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

                Toast.makeText(HelpSendActivity.this, "消息", Toast.LENGTH_SHORT).show();

                break;
            //case R.id.title_apps://应用break;
            case R.id.title_setting://设置
                Toast.makeText(HelpSendActivity.this, "设置", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onMenuItemClick(item);
    }
}
