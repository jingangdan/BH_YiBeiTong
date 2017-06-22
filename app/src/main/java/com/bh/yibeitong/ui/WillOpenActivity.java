package com.bh.yibeitong.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseActivity;

/**
 * Created by jingang on 2016/11/12.
 * 即将开放
 */

public class WillOpenActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_header_left, iv_header_right;
    private TextView tv_header_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_will_open);
        initData();
        iv_header_right.setVisibility(View.INVISIBLE);
        tv_header_title.setText("即将开放");
    }

    /**
     *
     */
    public void initData() {
        iv_header_left = (ImageView) findViewById(R.id.iv_header_left);
        iv_header_right = (ImageView) findViewById(R.id.iv_header_right);
        tv_header_title = (TextView) findViewById(R.id.tv_header_title);

        iv_header_left.setOnClickListener(this);
        iv_header_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_header_left:
                finish();
                break;

            default:
                break;
        }
    }
}
