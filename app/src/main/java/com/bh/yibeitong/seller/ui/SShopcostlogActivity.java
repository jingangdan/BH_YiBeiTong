package com.bh.yibeitong.seller.ui;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.seller.adapter.ShopCostLogAdapter;
import com.bh.yibeitong.seller.bean.AppShopCostLog;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by jingang on 2017/8/5.
 * 商家结算列表
 */
public class SShopcostlogActivity extends BaseTextActivity implements
        SwipeRefreshLayout.OnRefreshListener {
    /*接口地址*/
    private String PATH = "";

    /*接收页面传值*/
    private Intent intent;
    private String uid = "", pwd = "";

    /*UI显示*/
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ShopCostLogAdapter shopCostLogAdapter;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_s_shopcostlog);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("商家结算");
        setTitleBack(true, 0);

        initData();
    }

    /*组件 初始化*/
    public void initData() {
        intent = getIntent();
        uid = intent.getStringExtra("uid");
        pwd = intent.getStringExtra("pwd");

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.sr_s_shopcostlog);
        recyclerView = (RecyclerView) findViewById(R.id.rv_s_shopcostlog);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout.setOnRefreshListener(this);

        getShopcostlog(uid, pwd);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    /**
     * 商家结算列表
     *
     * @param uid
     * @param pwd
     */
    public void getShopcostlog(String uid, String pwd) {
        PATH = HttpPath.PATH + HttpPath.APP_SHOPCOSTLOG + "" +
                "uid=" + uid + "&pwd=" + pwd;
        RequestParams params = new RequestParams(PATH);
        System.out.println("商家结算列表" + PATH);

        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("商家结算列表" + result);

                        swipeRefreshLayout.setRefreshing(false);

                        AppShopCostLog appShopCostLog = GsonUtil.gsonIntance().gsonToBean(result, AppShopCostLog.class);
                        shopCostLogAdapter = new ShopCostLogAdapter(SShopcostlogActivity.this, appShopCostLog.getMsg());

                        recyclerView.setAdapter(shopCostLogAdapter);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        System.out.println("" + ex);
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });

    }

    @Override
    public void onRefresh() {
        getShopcostlog(uid, pwd);
    }
}
