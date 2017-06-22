package com.bh.yibeitong.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseActivity;

/**
 * Created by jingang on 2016/10/20.
 *  跑腿服务
 */

public class RunLegActivity extends BaseActivity implements View.OnClickListener{
    private Intent intent;
    //头部
    private ImageView iv_header_left, iv_header_right;
    private TextView tv_header_title;

    //
    private LinearLayout lin_help_send, lin_help_buy, lin_my_run;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_leg);

        initData();
    }

    /**
     * \组件 初始化
     */
    public void initData(){
        iv_header_left = (ImageView) findViewById(R.id.iv_header_left);
        iv_header_right = (ImageView) findViewById(R.id.iv_header_right);

        iv_header_left.setOnClickListener(this);
        iv_header_right.setOnClickListener(this);

        tv_header_title = (TextView) findViewById(R.id.tv_header_title);
        tv_header_title.setText("跑腿服务");

        lin_help_send = (LinearLayout) findViewById(R.id.lin_help_send);
        lin_help_buy = (LinearLayout) findViewById(R.id.lin_help_buy);
        lin_my_run = (LinearLayout) findViewById(R.id.lin_my_run);

        lin_help_send.setOnClickListener(this);
        lin_help_buy.setOnClickListener(this);
        lin_my_run.setOnClickListener(this);

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

            case R.id.lin_help_send:
                //帮我送
                intent = new Intent(RunLegActivity.this, HelpSendActivity.class);
                intent.putExtra("title","帮我送");
                startActivity(intent);
                break;

            case R.id.lin_help_buy:
                //帮我买
                intent = new Intent(RunLegActivity.this, HelpSendActivity.class);
                intent.putExtra("title","帮我买");
                startActivity(intent);

                break;

            case R.id.lin_my_run:
                //我的跑腿
                intent = new Intent(RunLegActivity.this, MyRunLegActivity.class);
                intent.putExtra("title","我的跑腿");
                startActivity(intent);
                break;

            default:
                break;
        }

    }
}
