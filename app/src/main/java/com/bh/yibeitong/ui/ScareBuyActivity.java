package com.bh.yibeitong.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseActivity;

/**
 * Created by jingang on 2016/10/20.
 * 抢购专区
 */

public class ScareBuyActivity extends BaseActivity implements View.OnClickListener{
    private ImageView iv_header_left, iv_header_right;

    private TextView tv_header_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scare_buy);
        initData();
    }

    /**
     * 组件 初始化
     */
    public void initData(){
        //头部控件
        iv_header_left = (ImageView) findViewById(R.id.iv_header_left);
        iv_header_right = (ImageView) findViewById(R.id.iv_header_right);

        iv_header_left.setOnClickListener(this);
        iv_header_right.setOnClickListener(this);

        tv_header_title = (TextView) findViewById(R.id.tv_header_title);
        tv_header_title.setText("抢购专区");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.iv_header_left:
                //头部左边
                finish();
                break;
            case R.id.iv_header_right:
                //头部右边
                toast("消息");
                break;

            default:
                break;
        }

    }
}
