package com.bh.yibeitong.seller.activity;

import android.content.Intent;
import android.view.View;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.utils.HttpPath;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by jingang on 2017/9/25.
 * 店铺管理
 */

public class SAppGoodsActivity extends BaseTextActivity {

    /*接收页面传值*/
    private Intent intent;
    private String uid = "", pwd = "";

    /*接口地址*/
    private String PATH = "";

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_aboutus);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleBack(true, 0);
        setTitleName("店铺管理");

        initData();
    }

    public void initData() {
        intent = getIntent();
        uid = intent.getStringExtra("uid");
        pwd = intent.getStringExtra("pwd");

        getMarketFgoodstype(uid, pwd);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    /**
     * 超市商家获取一级商品分类
     *
     * @param uid
     * @param pwd
     */
    public void getMarketFgoodstype(String uid, String pwd) {
        PATH = HttpPath.PATH + HttpPath.APP_MARKERFOODSTYPE +
                "uid=" + uid + "&pwd=" + pwd;
        RequestParams params = new RequestParams(PATH);
        System.out.println("超市商家获取一级商品分类" + PATH);

        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("超市商家获取一级商品分类" + result);

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

    /**
     * 超市商家获取二级商品分类
     *
     * @param uid
     * @param pwd
     * @param ftype 上级分类
     */
    public void getMarketTgoodstype(String uid, String pwd, String ftype) {
        PATH = HttpPath.PATH + HttpPath.APP_MARKERTGOODSTYPE +
                "uid=" + uid + "&pwd=" + pwd + "&ftype=" + ftype;
        RequestParams params = new RequestParams(PATH);
        System.out.println("超市商家获取二级商品分类" + PATH);

        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("超市商家获取二级商品分类" + result);
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
}
