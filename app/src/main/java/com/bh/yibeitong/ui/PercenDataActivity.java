package com.bh.yibeitong.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseActivity;

/**
 * Created by jingang on 2016/11/9.
 * 基本资料
 */

public class PercenDataActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_data_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percen_data);
        initData();
    }

    /**
     *组件 初始化
     */
    public void initData() {
        iv_data_back = (ImageView) findViewById(R.id.iv_data_back);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_data_back:
                finish();
                break;

            default:
                break;
        }
    }
}
