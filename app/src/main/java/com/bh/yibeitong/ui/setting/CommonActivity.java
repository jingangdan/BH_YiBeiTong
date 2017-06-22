package com.bh.yibeitong.ui.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseActivity;

/**
 * Created by jingang on 2016/11/10.
 * 通用设置
 */

public class CommonActivity extends BaseActivity implements View.OnClickListener{
    private ImageView iv_common_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        initData();
    }

    public void initData(){
        iv_common_back = (ImageView) findViewById(R.id.iv_common_back);
        iv_common_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_common_back:
                finish();
                break;

            default:
                break;
        }
    }
}
