package com.bh.yibeitong.ui.percen;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;

/**
 * Created by jingang on 2016/12/8.
 * 账户余额
 */

public class YuEActivity extends BaseTextActivity{
    //立即充值
    private Button but_recharge;

    private TextView tv_yue;

    /*接收页面传值*/
    private Intent intent;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_yue);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("余额");
        setTitleBack(true, 0);
        setRightRes();

        initData();
    }

    /**
     *
     */
    public void initData(){
        but_recharge = (Button) findViewById(R.id.but_recharge);
        but_recharge.setOnClickListener(this);

        tv_yue = (TextView) findViewById(R.id.tv_yue_yue);

        //intent = getIntent();

        //tv_yue.setText("￥"+intent.getStringExtra("yue"));
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()){
            case R.id.but_recharge:
                toast("暂未开通");
                break;

            default:
                break;
        }
    }
}
