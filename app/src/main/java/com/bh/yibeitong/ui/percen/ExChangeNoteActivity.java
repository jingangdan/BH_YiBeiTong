package com.bh.yibeitong.ui.percen;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;

/**
 * Created by jingang on 2016/12/7.
 * 兑换记录
 */

public class ExChangeNoteActivity extends BaseTextActivity{
    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_exchange_note);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("兑换记录");
        setTitleBack(true,0);
    }
}
