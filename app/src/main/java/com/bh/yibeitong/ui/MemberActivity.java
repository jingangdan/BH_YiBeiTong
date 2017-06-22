package com.bh.yibeitong.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseActivity;

/**
 * Created by jingang on 2016/11/9.
 * 会员积分 积分详情
 */

public class MemberActivity extends BaseActivity implements View.OnClickListener{
    private ImageView iv_member_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        initData();
    }

    /**
     *
     */
    public void initData(){
        iv_member_back = (ImageView) findViewById(R.id.iv_member_back);
        iv_member_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_member_back:
                finish();
                break;
        }
    }
}
