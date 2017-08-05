package com.bh.yibeitong.seller.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.seller.bean.AppShop;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by jingang on 2017/8/4.
 * 2 店铺管理（商家端）
 */

public class SAppShopActivity extends BaseTextActivity {
    /*接口地址*/
    private String PATH = "";

    /*接收页面传值*/
    private Intent intent;
    private String uid = "", pwd = "";

    /*UI显示*/
    private ImageView iv_shoplogo;
    private TextView tv_shopname, tv_shopphone, tv_opentime, tv_limitcost;

    private String shoplogo = "", shopname = "", shopphone = "", opentime = "", limitcost = "";

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_s_appshop);
    }

    @Override
    protected void initWidght() {
        super.initWidght();

        setTitleName("店铺管理");
        setTitleBack(true, 0);

        initData();
    }

    public void initData() {
        intent = getIntent();
        uid = intent.getStringExtra("uid");
        pwd = intent.getStringExtra("pwd");

        iv_shoplogo = (ImageView) findViewById(R.id.iv_sas_shoplogo);
        tv_shopname = (TextView) findViewById(R.id.tv_sas_shopname);
        tv_shopphone = (TextView) findViewById(R.id.tv_sas_shopphone);
        tv_opentime = (TextView) findViewById(R.id.tv_sas_opentime);
        tv_limitcost = (TextView) findViewById(R.id.tv_sas_limitcost);

        getAppShop(uid, pwd);

    }

    /**
     * 店铺信息
     *
     * @param uid
     * @param pwd
     */
    public void getAppShop(String uid, String pwd) {
        PATH = HttpPath.PATH + HttpPath.APP_SHOP +
                "uid=" + uid + "&pwd=" + pwd;
        RequestParams params = new RequestParams(PATH);

        System.out.println("店铺信息" + PATH);

        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("店铺信息" + result);
                        AppShop appShop = GsonUtil.gsonIntance().gsonToBean(result, AppShop.class);

                        shoplogo = appShop.getMsg().getShoplogo();
                        shopname = appShop.getMsg().getShopname();
                        shopphone = appShop.getMsg().getShopphone();
                        opentime = appShop.getMsg().getOpentime();
                        limitcost = appShop.getMsg().getLimitcost();

                        if (shoplogo.equals("")) {
                            iv_shoplogo.setImageResource(R.mipmap.yibeitong001);
                        } else {
                            x.image().bind(iv_shoplogo, shoplogo);
                        }

                        tv_shopname.setText("" + shopname);
                        tv_shopphone.setText("" + shopphone);
                        tv_opentime.setText("" + opentime);
                        tv_limitcost.setText("" + limitcost);

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

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }


}
