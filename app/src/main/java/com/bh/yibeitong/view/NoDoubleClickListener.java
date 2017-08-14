package com.bh.yibeitong.view;

import android.view.View;

import java.util.Calendar;

/**
 * Created by jingang on 2017/8/14.
 * 防止多次点击造成多次事件
 */

public abstract class NoDoubleClickListener implements View.OnClickListener {
    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick((Integer) v.getTag(), v);
        }
    }

    protected abstract void onNoDoubleClick(int postion, View v);

}
