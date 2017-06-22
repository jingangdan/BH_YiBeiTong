package com.bh.yibeitong.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Created by jingang on 2016/10/30.
 * 使用SharedPerferences和Gson搭建本地数据库
 */

public class LocalStorageUtils {

    public static LocalStorageUtils localStorageUtils = null;

    public static LocalStorageUtils localIntance() {
        if (localStorageUtils == null) {
            localStorageUtils = new LocalStorageUtils();
        }
        return localStorageUtils;
    }

    private Context mContext;

    //取
    public String getMeetings() {
        SharedPreferences sp = mContext.getSharedPreferences("shopInfoList", 0);
        return sp.getString("shopInfo", "");
    }

    public boolean saveMeetings(String info) {
        //存
        SharedPreferences sp = mContext.getSharedPreferences("shopInfoList", 0);
        return sp.edit().putString("shopInfo", info).commit();
    }
}
