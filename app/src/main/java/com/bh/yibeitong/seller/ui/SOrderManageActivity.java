package com.bh.yibeitong.seller.ui;

import android.app.NotificationManager;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.bh.yibeitong.R;
import com.bh.yibeitong.adapter.SimpleFragmentPagerAdapter;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.seller.SellerOrder;
import com.bh.yibeitong.seller.fragment.FMSellerCollect;
import com.bh.yibeitong.seller.fragment.FMSellerOK;
import com.bh.yibeitong.seller.fragment.FMSellerSend;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.NoScrollViewPager;
import com.bh.yibeitong.view.UserInfo;

import org.xutils.BuildConfig;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingang on 2017/3/30.
 * 订单管理（商家端）
 */

public class SOrderManageActivity extends BaseTextActivity implements ViewPager.OnPageChangeListener {
    /*请求地址*/
    private String PATH = "";

    private String str_result;

    /*本地轻量型缓存 短时间保存pwd*/
    UserInfo userInfo;
    String uid, pwd;

    /*TabLayout*/
    private TabLayout tabLayout;
    private NoScrollViewPager noScrollViewPager;

    private String[] titles = new String[]{"待确认", "待发货", "待收货"};
    /*private Fragment[] fragments = new Fragment[]{
            new FMSellerOK(),
            new FMSellerSend(),
            new FMSellerCollect()
    };*/
    private List<Fragment> fragments = new ArrayList<>();

    private SimpleFragmentPagerAdapter sfpAdapter;


    private SellerOrder sellerOrder;


    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_s_order_manage);

        x.Ext.init(getApplication());
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.

    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("订单管理");
        setTitleBack(true, 0);

        initData();

    }

    /*组件 初始化*/
    public void initData() {
        userInfo = new UserInfo(getApplication());
        pwd = userInfo.getPwd();

        /*此处需验证 数据是否为空*/
        /*SellerLogin sellerLogin = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), SellerLogin.class);
        uid = sellerLogin.getMsg().getUid();

        tabLayout = (TabLayout) findViewById(R.id.tl_seller_order_manage);
        noScrollViewPager = (NoScrollViewPager) findViewById(R.id.vp_seller_order_manage);


        getSellerOrder(uid, pwd, "", "wait");*/

        noScrollViewPager = (NoScrollViewPager) findViewById(R.id.vp_seller_order_manage);
        tabLayout = (TabLayout) findViewById(R.id.tl_seller_order_manage);

        fragments.add(FMSellerOK.newInstance("FMSellerOK"));

        fragments.add(FMSellerSend.newInstance("FMSellerSend"));
        fragments.add(FMSellerCollect.newInstance("FMSellerCollect"));

        sfpAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), this, fragments, titles);
        noScrollViewPager.setAdapter(sfpAdapter);


        noScrollViewPager.setOffscreenPageLimit(titles.length);

        noScrollViewPager.setOnPageChangeListener(this);
        tabLayout.setupWithViewPager(noScrollViewPager);

        notificationMethod();


    }


    /*清除通知栏*/
    public void notificationMethod() {
        // 在Android进行通知处理，首先需要重系统哪里获得通知管理器NotificationManager，它是一个系统Service。
        NotificationManager manager = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE);

        manager.cancel(1);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case 1:
                break;

            default:
                break;
        }
    }

    /**
     * @param uid
     * @param pwd
     * @param searchday 查询日期
     * @param gettype   订单类型 wait waitsend is_send
     */
    public void getSellerOrder(String uid, String pwd, String searchday, String gettype) {
        PATH = HttpPath.path + HttpPath.APP_ORDER +
                "uid=" + uid + "&pwd=" + pwd + "&searchday=" + searchday + "&gettype=" + gettype;

        System.out.println("" + PATH);
        RequestParams params = new RequestParams(PATH);

        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("商家订单 = " + result);
                        //SellerOrder sellerOrder = GsonUtil.gsonIntance().gsonToBean(result, SellerOrder.class);

                        fragments.add(FMSellerOK.newInstance(result));

                        fragments.add(FMSellerSend.newInstance("FMSellerSend"));
                        fragments.add(FMSellerCollect.newInstance("FMSellerCollect"));

                        sfpAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), SOrderManageActivity.this, fragments, titles);
                        noScrollViewPager.setAdapter(sfpAdapter);


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


    }

    /*ViewPager 滑动控制*/
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}