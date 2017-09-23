package com.bh.yibeitong.ui.order;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;

import com.bh.yibeitong.R;
import com.bh.yibeitong.actitvity.ActivityCollector;
import com.bh.yibeitong.adapter.SimpleFragmentPagerAdapter;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.fragment.FMOrderDetaile;
import com.bh.yibeitong.fragment.FMOrderState;
import com.bh.yibeitong.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingang on 2016/11/25.
 * 订单详情
 */

public class OrderDetaileActivity extends BaseTextActivity implements
        ViewPager.OnPageChangeListener {
    private TabLayout tl_order_detaile;
    private NoScrollViewPager vp_order_detaile;

    String order_reslt;

    private String[] titles = new String[]{"订单状态", "订单详情"};
    //private Fragment[] fragments;
    private List<Fragment> fragments = new ArrayList<>();
    private SimpleFragmentPagerAdapter sfpAdapter;

    Intent intent;
    public static String orderid, status ;


    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_order_detaile);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("关于订单");
        setTitleBack(true, 0);
        ActivityCollector.addActivity(this);

        initData();

        intent = getIntent();
        orderid = intent.getStringExtra("orderid");
        status = intent.getStringExtra("status");

        //getOrderDetaile(orderid);

        //fragments = new Fragment[]{new FMOrderState(), new FMOrderDetaile()};

        fragments.add(FMOrderState.newInstance("FMOrderState"));
        fragments.add(FMOrderDetaile.newInstance("FMOrderDetaile"));

        sfpAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), this, fragments, titles);
        vp_order_detaile.setAdapter(sfpAdapter);

        vp_order_detaile.setOffscreenPageLimit(titles.length);

        vp_order_detaile.setOnPageChangeListener(this);
        tl_order_detaile.setupWithViewPager(vp_order_detaile);

    }

    /**
     * 组件 初始化
     */
    public void initData() {
        tl_order_detaile = (TabLayout) findViewById(R.id.tl_order_detaile);
        vp_order_detaile = (NoScrollViewPager) findViewById(R.id.vp_order_detaile);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        toast("0000000");

        intent = new Intent();
        setResult(100, intent);
        OrderDetaileActivity.this.finish();
        return false;
    }

    /**
     * 获取订单详情
     *
     * @param orderid
     */
    /*public void getOrderDetaile(String orderid) {
        String PATH = HttpPath.PATH + HttpPath.ORDER_NEWDET + "orderid=" + orderid;

        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("订单详情" + result);
                        order_reslt = result;
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
    }*/
}