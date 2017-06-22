package com.bh.yibeitong.view;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by jingang on 2016/11/23.
 * 轻量型的本地缓存数据库
 */

public class UserInfo {
    Application app;

    public UserInfo(Application application) {
        this.app = application;
    }

    /*存储用户登录密码*/
    public String getPwd() {
        SharedPreferences sp = app.getSharedPreferences("pwdList", 0);
        return sp.getString("pwd", "");
    }

    //存
    public boolean savePwd(String info) {
        SharedPreferences sp = app.getSharedPreferences("pwdList", 0);
        return sp.edit().putString("pwd", info).commit();
    }

    /*存储用户登录状态*/
    public String getLogin() {
        SharedPreferences sp = app.getSharedPreferences("loginList", 0);
        return sp.getString("login", "");
    }

    //存
    public boolean saveLogin(String info) {
        SharedPreferences sp = app.getSharedPreferences("loginList", 0);
        return sp.edit().putString("login", info).commit();
    }

    /*存储用户登录信息*/
    public String getUserInfo() {
        SharedPreferences sp = app.getSharedPreferences("userInfoList", 0);
        return sp.getString("userInfo", "");
    }

    //存
    public boolean saveUserInfo(String info) {
        SharedPreferences sp = app.getSharedPreferences("userInfoList", 0);
        return sp.edit().putString("userInfo", info).commit();
    }

    /**
     * 用户获取的订单配送时间
     *
     * @return
     */
    public String getPostData() {
        SharedPreferences sp = app.getSharedPreferences("postDataList", 0);
        return sp.getString("postData", "");
    }

    //存
    public boolean savePostData(String info) {
        SharedPreferences sp = app.getSharedPreferences("postDataList", 0);
        return sp.edit().putString("postData", info).commit();
    }


    /**
     * 缓存主页商品数据
     */
    //取
    public String getMeetings() {
        SharedPreferences sp = app.getSharedPreferences("meetingList", 0);

        return sp.getString("meeting", "");
    }

    public boolean saveMeetings(String info) {
        //存
        SharedPreferences sp = app.getSharedPreferences("meetingList", 0);
        return sp.edit().putString("meeting", info).commit();
    }

    /**
     * 首页商店的信息
     *
     * @return
     */
    public String getShopInfo() {
        SharedPreferences sp = app.getSharedPreferences("shopInfoList", 0);

        return sp.getString("shopInfo", "");
    }

    public boolean saveShopInfo(String info) {
        //存
        SharedPreferences sp = app.getSharedPreferences("shopInfoList", 0);
        return sp.edit().putString("shopInfo", info).commit();
    }


    /**
     * 订单信息
     *
     * @return
     */
    public String getOrder() {
        SharedPreferences sp = app.getSharedPreferences("orderList", 0);

        return sp.getString("order", "");
    }

    public boolean saveSOrder(String info) {
        //存
        SharedPreferences sp = app.getSharedPreferences("orderList", 0);
        return sp.edit().putString("order", info).commit();
    }

    /**
     * code
     * @return
     */
    public String getCode() {
        SharedPreferences sp = app.getSharedPreferences("codeList", 0);

        return sp.getString("code", "");
    }

    public boolean saveCoder(String info) {
        //存
        SharedPreferences sp = app.getSharedPreferences("codeList", 0);
        return sp.edit().putString("code", info).commit();
    }

    /**
     *  获取商店 起送费
     * @return
     */
    public String getShopDet() {
        SharedPreferences sp = app.getSharedPreferences("shopDetList", 0);

        return sp.getString("shopDet", "");
    }

    public boolean saveShopDet(String info) {
        //存
        SharedPreferences sp = app.getSharedPreferences("shopDetList", 0);
        return sp.edit().putString("shopDet", info).commit();
    }


    /**
     * 上传图片返回的数据（图片地址）
     * @return
     */
    public String getImgMedia(){
        //取
        SharedPreferences sp = app.getSharedPreferences("imgMediaList", 0);

        return sp.getString("imgMedia", "");
    }

    public boolean saveImgMedia(String info){
        //存
        SharedPreferences sp = app.getSharedPreferences("imgMediaList", 0);
        return sp.edit().putString("imgMedia", info).commit();
    }

    /*首页商品分类中等图标*/
    public String getClassify(){
        //取
        SharedPreferences sp = app.getSharedPreferences("classifyList", 0);

        return sp.getString("classify", "");
    }

    public boolean saveClassify(String info){
        //存
        SharedPreferences sp = app.getSharedPreferences("classifyList", 0);
        return sp.edit().putString("classify", info).commit();
    }

    /**/
    public String getADV(){
        //取
        SharedPreferences sp = app.getSharedPreferences("advList", 0);

        return sp.getString("adv", "");
    }

    public boolean saveADV(String info){
        //存
        SharedPreferences sp = app.getSharedPreferences("advList", 0);
        return sp.edit().putString("adv", info).commit();
    }


}
