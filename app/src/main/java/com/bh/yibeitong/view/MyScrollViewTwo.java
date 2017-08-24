package com.bh.yibeitong.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by jingang on 2017/6/10.
 */

public class MyScrollViewTwo extends ScrollView {
    private boolean isScrolledToTop = true;// 初始化的时候设置一下值

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


//    private boolean isScrolledToTop = true;// 初始化的时候设置一下值
//
//    public MyScrollViewTwo(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public static ISmartScrollChangedListener mSmartScrollChangedListener;
//
//    /**/
//    private int downX;
//    private int downY;
//    private int mTouchSlop;
//
//
//    /**
//     * 定义监听接口
//     */
//    public interface ISmartScrollChangedListener {
//
//        void onScrolledToTop(boolean isToTop);
//
//    }
//
//    public static void setScanScrollChangedListener(ISmartScrollChangedListener smartScrollChangedListener) {
//        mSmartScrollChangedListener = smartScrollChangedListener;
//    }
//
//    @Override
//    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
//        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
//        if (scrollY == 0) {
//            isScrolledToTop = clampedY;
//            //System.out.println("onOverScrolled 顶部:" + isScrolledToTop);
//        } else if (scrollY > 0) {
//            isScrolledToTop = false;
//        } else if (scrollY < 0) {
//            isScrolledToTop = false;
//        }
//        //notifyScrollChangedListeners();
//    }
//
//    @Override
//    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        super.onScrollChanged(l, t, oldl, oldt);
//        if (getScrollY() == 0) {
//            isScrolledToTop = true;
//        } else if (getScrollY() + getHeight() - getPaddingTop() - getPaddingBottom() == getChildAt(0).getHeight()) {
//            isScrolledToTop = false;
//        } else if(getScrollY() > 600){
//        }else {
//            isScrolledToTop = false;
//        }
//
//        notifyScrollChangedListeners();
//    }
//
//    private void notifyScrollChangedListeners() {
//        if (mSmartScrollChangedListener != null) {
//            mSmartScrollChangedListener.onScrolledToTop(isScrolledToTop);
//        } else {
//        }
//    }
//
//    private float mDownPosX = 0;
//    private float mDownPosY = 0;
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent e) {
//
//        int action = e.getAction();
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int moveY = (int) e.getRawY();
//                if (Math.abs(moveY - downY) > mTouchSlop) {
//                    return true;
//                }
//
//        }
//        return super.onInterceptTouchEvent(e);
//    }

    private GestureDetector mGestureDetector;

    public MyScrollViewTwo(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGestureDetector = new GestureDetector(context, new YScrollDetector());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //防止ScrollView嵌套ViewPager滑动冲突
        return super.onInterceptTouchEvent(ev)
                && mGestureDetector.onTouchEvent(ev);
    }

    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            //如果我们滚动更接近水平方向,返回false,让子视图来处理它
            return (Math.abs(distanceY) > Math.abs(distanceX));
        }
    }

}
