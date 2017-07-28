package com.bh.yibeitong.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by jingang on 2017/6/10.
 */

public class MyScrollView extends ScrollView {

    private boolean isScrolledToTop = true;// 初始化的时候设置一下值
    private boolean isScrolledToBottom = false;

    public MyScrollView(Context context, AttributeSet attrs) {
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
        void onScrolledToBottom(boolean isToButom);

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
            isScrolledToBottom = false;
            //System.out.println("onOverScrolled 顶部:" + isScrolledToTop);
        } else if (scrollY > 0) {
            isScrolledToTop = false;
            isScrolledToBottom = clampedY;
        } else if (scrollY < 0) {
            isScrolledToTop = false;
            isScrolledToBottom = false;
        }
        //notifyScrollChangedListeners();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        // 这个log可以研究ScrollView的上下padding对结果的影响
        //System.out.println("11111111onScrollChanged getScrollY():" + getScrollY() + " t: " + t + " paddingTop: " + getPaddingTop());
        if (getScrollY() == 0) {
            isScrolledToTop = true;
            isScrolledToBottom = false;
            //System.out.println("onScrollChanged 顶部:" + isScrolledToBottom);

        } else if (getScrollY() + getHeight() - getPaddingTop() - getPaddingBottom() == getChildAt(0).getHeight()) {
            isScrolledToBottom = true;
            isScrolledToTop = false;
            //System.out.println("onScrollChanged 底部:" + isScrolledToBottom);
        } else {
            isScrolledToTop = false;
            isScrolledToBottom = false;
        }

        notifyScrollChangedListeners();
    }

    private void notifyScrollChangedListeners() {
        //if (isScrolledToTop) {
        if (mSmartScrollChangedListener != null) {
            mSmartScrollChangedListener.onScrolledToTop(isScrolledToTop);
        } else {
            //System.out.println("2222");
        }
        //} else if (isScrolledToBottom) {
        if (mSmartScrollChangedListener != null) {
            mSmartScrollChangedListener.onScrolledToBottom(isScrolledToBottom);
        } else {
            //System.out.println("3333");
        }
        //}
    }

    public static void doSomeThing() {
        mSmartScrollChangedListener.onScrolledToBottom(false);
    }

//    public boolean isScrolledToTop() {
//        return isScrolledToTop;
//    }
//
//    public boolean isScrolledToBottom() {
//        return isScrolledToBottom;
//    }

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
