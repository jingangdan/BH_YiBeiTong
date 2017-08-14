package com.bh.yibeitong.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by jingang on 2017/6/10.
 */

public class MyScrollViewTwo extends ScrollView {

    private boolean isScrolledToTop = true;// 初始化的时候设置一下值

    public MyScrollViewTwo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public static ISmartScrollChangedListener mSmartScrollChangedListener;

    /**/
    private int downX;
    private int downY;
    private int mTouchSlop;


    /**
     * 定义监听接口
     */
    public interface ISmartScrollChangedListener {

        void onScrolledToTop(boolean isToTop);

    }

    public static void setScanScrollChangedListener(ISmartScrollChangedListener smartScrollChangedListener) {
        mSmartScrollChangedListener = smartScrollChangedListener;
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (scrollY == 0) {
            isScrolledToTop = clampedY;
            //System.out.println("onOverScrolled 顶部:" + isScrolledToTop);
        } else if (scrollY > 0) {
            isScrolledToTop = false;
        } else if (scrollY < 0) {
            isScrolledToTop = false;
        }
        //notifyScrollChangedListeners();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (getScrollY() == 0) {
            isScrolledToTop = true;
        } else if (getScrollY() + getHeight() - getPaddingTop() - getPaddingBottom() == getChildAt(0).getHeight()) {
            isScrolledToTop = false;
        } else if(getScrollY() > 600){
        }else {
            isScrolledToTop = false;
        }

        notifyScrollChangedListeners();
    }

    private void notifyScrollChangedListeners() {
        if (mSmartScrollChangedListener != null) {
            mSmartScrollChangedListener.onScrolledToTop(isScrolledToTop);
        } else {
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) e.getRawX();
                downY = (int) e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) e.getRawY();
                if (Math.abs(moveY - downY) > mTouchSlop) {
                    return true;
                }
        }
        return super.onInterceptTouchEvent(e);
    }

}
