package com.bh.yibeitong.ui.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseActivity;

/**
 * Created by jingang on 2016/11/11.
 * 图片质量
 */

public class ImageQualityActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_image_quality_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_quality);
        initData();
    }

    public void initData() {
        iv_image_quality_back = (ImageView) findViewById(R.id.iv_image_quality_back);
        iv_image_quality_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_image_quality_back:
                finish();
                break;

            default:
                break;
        }

    }
}
