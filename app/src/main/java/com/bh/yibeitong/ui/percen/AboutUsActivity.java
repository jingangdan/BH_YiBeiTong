package com.bh.yibeitong.ui.percen;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;

/**
 * Created by jingang on 2016/12/8.
 * 关于我们
 */

public class AboutUsActivity extends BaseTextActivity {
    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_aboutus);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("关于我们");
        setTitleBack(true, 0);
    }


}
