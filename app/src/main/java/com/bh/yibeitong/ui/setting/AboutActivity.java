package com.bh.yibeitong.ui.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseActivity;

/**
 * Created by jingang on 2016/11/11.
 * 关于我们
 */

public class AboutActivity extends BaseActivity implements View.OnClickListener{
    private ImageView iv_about_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initData();
    }

    public void initData(){
        iv_about_back = (ImageView) findViewById(R.id.iv_about_back);
        iv_about_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_about_back:
                finish();
                break;

            default:
                break;
        }

    }
}
