package com.bh.yibeitong.utils;

/**
 * Created by jingang on 2017/7/29.
 * 页面跳转请求码
 */

public class CodeUtils {
    /*首页*/
    public static final int REQUEST_CODE_HOMEPAGE = 0x01;

    /*商品详情*/
    public static final int REQUEST_CODE_CATEFOOD = 0x02;

    /*购物车（首页）*/
    public static final int REQUEST_CODE_SHOPPING = 0x03;

    /*购物车下单*/
    public static final  int REQUEST_CODE_ORDER  = 0x04;

    /*进入店铺（进入产品分类）*/
    public static final int REQUEST_CODE_NEWSHOP = 0x05;

    /*首页商品分类*/
    public static final int REQUEST_CODE_CATEINFO = 0x06;

    /*特色服务*/
    public static final int REQUEST_CODE_TESE = 0x07;

    /*购物车*/
    public static final int REQUEST_CODE_SHOPCART = 0x08;

    /*登录*/
    public static final int REQUEST_CODE_LOGIN = 0x09;

    /*注册*/
    public static final int REQUEST_CODE_REGIST = 0x10;

    /*订单页*/
    public static final int REQUEST_CODE_HOME_ORDER = 0x11;

    /*个人中心页*/
    public static final int REQUEST_CODE_PERCEN = 0x12;

    /*设置*/
    public static final int REQUEST_CODE_SETTING = 0x13;

    /*扫一扫结果*/
    public static final int REQUEST_CODE_ZXING_RESULT = 0x14;

    /*收发快递*/
    public static final int REQUEST_CODE_EXPRESS = 0x15;

    /*快速登录*/
    public static final int REQUEST_CODE_QUICK_LOGIN = 0x16;

    /*选择收货地址*/
    public static final int REQUEST_CODE_LOCATION_ADDRESS = 0x17;

    /*输入选择地址*/
    public static final int REQUEST_CODE_LOCATION = 0x18;

    /*订单评价*/
    public static final int REQUEST_CODE_ORDER_COMMENT = 0x19;

    /*订单继续支付*/
    public static final int REUEST_CODE_PAY = 0x20;

    /*订单详情*/
    public static final int REQUEST_CODER_ORDER_STATE = 0x21;

    /*生情退款*/
    public static final int REQUEST_CODE_ORDER_CONTROL = 0x22;


    /*
    * 商家端 从0x50开始吧
    * */

    /*首页*/
    public static final int CODE_SELLER = 0x50;

    /*订单管理*/
    public static final int CODE_SELLER_ORDER = 0x51;

    /*商店管理（商品管理）*/
    public static final int CODE_SELLER_GOODS_ONE = 0x52;

    /*添加（修改）一级分类*/
    public static final int CODE_SELLER_CLASSIFY_ADD = 0x53;

    /*商店管理（商品二级分类管理）*/
    public static final int CODE_SELLER_GOODS_TWO = 0x54;

    /*商店管理（商品一级分类管理）*/
    public static final int CODE_SELLER_GOODS = 0x55;

    /*添加（修改）商品*/
    public static final int CODE_SELLER_GOODS_ADD = 0x56;


}
