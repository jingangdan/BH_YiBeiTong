package com.bh.yibeitong.utils;

import com.bh.yibeitong.bean.ShopCart;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by jingang on 2016/11/21.
 * 测试一下  关于购物车的一些操作方法
 */

public class ShopCartWay {

    public String getShopCart(String shopid) {
        final String[] string = new String[1];

        String PATH = HttpPath.PATH + HttpPath.GETCART + "shopid=" + shopid;
        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        string[0] = result;

                        System.out.print("smwanyi = " + result);

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

        return string[0];
    }
}
