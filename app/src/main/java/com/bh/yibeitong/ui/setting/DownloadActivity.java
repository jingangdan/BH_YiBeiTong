package com.bh.yibeitong.ui.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseActivity;

import static com.bh.yibeitong.R.id.iv_download_back;

/**
 * Created by jingang on 2016/11/11.
 * 自动下载安装包
 */

public class DownloadActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_download_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        initData();
    }

    public void initData() {
        iv_download_back = (ImageView) findViewById(R.id.iv_download_back);
        iv_download_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_download_back:
                finish();
                break;

            default:
                break;
        }


    }
}
