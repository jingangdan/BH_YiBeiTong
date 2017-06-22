package com.bh.yibeitong.view;

import android.content.Context;
import android.util.AttributeSet;
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
        } else {
            isScrolledToTop = false;
            isScrolledToBottom = clampedY;
            //System.out.println("onOverScrolled 底部:" + isScrolledToBottom);
        }
        notifyScrollChangedListeners();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        // 这个log可以研究ScrollView的上下padding对结果的影响
        //System.out.println("onScrollChanged getScrollY():" + getScrollY() + " t: " + t + " paddingTop: " + getPaddingTop());
        if (getScrollY() == 0) {
            isScrolledToTop = true;
            isScrolledToBottom = false;
            //System.out.println("onScrollChanged 顶部:" + isScrolledToTop);

        } else if (getScrollY() + getHeight() - getPaddingTop() - getPaddingBottom() == getChildAt(0).getHeight()) {
            isScrolledToBottom = true;
            //System.out.println("onScrollChanged 底部:" + isScrolledToBottom);
            isScrolledToTop = false;
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
        mSmartScrollChangedListener.onScrolledToBottom(true);
    }

    public boolean isScrolledToTop() {
        return isScrolledToTop;
    }

    public boolean isScrolledToBottom() {
        return isScrolledToBottom;
    }
}
