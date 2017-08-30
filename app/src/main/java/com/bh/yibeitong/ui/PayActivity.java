package com.bh.yibeitong.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.Errors;
import com.bh.yibeitong.bean.PayDataZFB;
import com.bh.yibeitong.bean.Register;
import com.bh.yibeitong.ui.order.OrderDetaileActivity;
import com.bh.yibeitong.utils.CodeUtils;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.UserInfo;
import com.bh.yibeitong.zhifubao.H5PayDemoActivity;
import com.bh.yibeitong.zhifubao.PayResult;
import com.bh.yibeitong.zhifubao.SignUtils;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by jingang on 2016/11/27.
 * 订单支付
 */
public class PayActivity extends BaseTextActivity {
    private TextView oName, oMoney, oPrice;
    private CheckBox weixin, zhifubao, yue;

    private Button pay;

    /*接收页面传值*/
    Intent intent;
    //订单名称 订单金额
    String dno, shopcost, type;

    String wmrid;
    private String notfyUrl;

    UserInfo userInfo;
    String uid, pwd, phone, orderid;

    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        //成功以后跳转到订单详情

                        Intent intent = new Intent(PayActivity.this, OrderDetaileActivity.class);
                        intent.putExtra("orderid", orderid);
                        startActivity(intent);

                        //startActivity(new Intent(PayActivity.this, OrderDetaileActivity.class));

                        PayActivity.this.finish();

                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(PayActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    private RelativeLayout rel_pay_yue;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_pay);
        initData();

        intent = getIntent();
        dno = intent.getStringExtra("dno");
        shopcost = intent.getStringExtra("shopcost");
        orderid = intent.getStringExtra("orderid");
        type = intent.getStringExtra("type");

        if (type.equals("acount")) {
            rel_pay_yue.setVisibility(View.GONE);
        } else {
            rel_pay_yue.setVisibility(View.VISIBLE);
        }

        oName.setText(dno);
        oMoney.setText("￥" + shopcost);
        oPrice.setText("￥" + shopcost);

        /*获取uid和pwd*/
        if (!(userInfo.getUserInfo().equals(""))) {
            Register register = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), Register.class);

            phone = register.getMsg().getPhone();
            uid = register.getMsg().getUid();
        }

        pwd = userInfo.getPwd();


        weixin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    weixin.setChecked(true);
                    zhifubao.setChecked(false);
                    yue.setChecked(false);
                }
            }
        });

        zhifubao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    weixin.setChecked(false);
                    zhifubao.setChecked(true);
                    yue.setChecked(false);
                }
            }
        });

        yue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    weixin.setChecked(false);
                    zhifubao.setChecked(false);
                    yue.setChecked(true);
                }
            }
        });

    }

    /**
     * 组件初始化
     */
    public void initData() {
        userInfo = new UserInfo(getApplication());
        oName = (TextView) findViewById(R.id.tv_order_name);
        oMoney = (TextView) findViewById(R.id.tv_order_money);
        oPrice = (TextView) findViewById(R.id.tv_order_price);

        weixin = (CheckBox) findViewById(R.id.cb_weixin);
        zhifubao = (CheckBox) findViewById(R.id.cb_zhifubao);
        yue = (CheckBox) findViewById(R.id.cb_yue);

        pay = (Button) findViewById(R.id.but_payment);
        pay.setOnClickListener(this);

        rel_pay_yue = (RelativeLayout) findViewById(R.id.rel_pay_yue);

    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("订单支付");
        setTitleBack(true, 0);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.but_payment:
                //确认支付
                if (weixin.isChecked()) {
                    //toast("微信支付暂未开通");
                    //获取支付宝pid  什么的
                    if (userInfo.getCode().equals("0")) {
                        getPayDataZFB(type, uid, pwd, shopcost, orderid);
                    } else {
                        getPayDataZFB(type, "phone", phone, shopcost, orderid);
                    }

                } else if (zhifubao.isChecked()) {
                    //获取支付宝pid  什么的
                    if (userInfo.getCode().equals("0")) {
                        getPayDataZFB(type, uid, pwd, shopcost, orderid);
                    } else {
                        getPayDataZFB(type, "phone", phone, shopcost, orderid);
                    }

                } else if (yue.isChecked()) {
                    toast("余额支付");

                    //判断登录方式
                    if (userInfo.getCode().equals("0")) {
                        setAccount(uid, pwd, shopcost, orderid);
                    } else {
                        setAccount("phone", phone, shopcost, orderid);
                    }

                }
                break;

            default:
                break;
        }
    }

    /**
     * 余额支付
     *
     * @param uid
     * @param pwd
     * @param cost
     * @param orderid http://m6.waimairen.com/index.php?ctrl=app&source=1&
     *                action=appacoutpay
     *                &uid=9325&pwd=123456&type=order&cost=102.00&orderid=type=order&orderid=24121&datatype=json
     *                看不懂
     */
    public void setAccount(String uid, String pwd, String cost, final String orderid) {

        String PATH = "";
        if (userInfo.getCode().equals("0")) {

            PATH = HttpPath.PATH + HttpPath.PAY_APPACOUT +
                    "uid=" + uid + "&pwd=" + pwd + "&cost=" + cost + "&orderid=" + orderid;

        } else {

            PATH = HttpPath.PATH + HttpPath.PAY_APPACOUT +
                    "logintype" + uid + "&loginphone=" + pwd + "&cost" + cost + "&orderid=" + orderid;

        }

        RequestParams params = new RequestParams(PATH);
        x.http().post(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("余额支付返回" + result);
                        Intent intent = new Intent(PayActivity.this, OrderDetaileActivity.class);
                        intent.putExtra("orderid", orderid);
                        startActivity(intent);

                        PayActivity.this.finish();
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        System.out.println("支付错误");

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
     * 支付宝支付
     *
     * @param PARTNER     商户PID
     * @param SELLER      商户收款账号
     * @param RSA_PRIVATE 商户私钥，pkcs8格式
     */
    public void setZhifubao(String wmrid, String PARTNER, String SELLER, String RSA_PRIVATE, String notfyUrl) {

        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            finish();
                        }
                    }).show();
            return;
        }
        String orderInfo = getOrderInfo(wmrid, dno, "该测试商品的详细描述", shopcost,
                PARTNER, SELLER, notfyUrl);

        /**
         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
         */
        String sign = sign(orderInfo, RSA_PRIVATE);
        try {
            /**
             * 仅需对sign 做URL编码
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /**
         * 完整的符合支付宝参数规范的订单信息
         */
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(PayActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * get the sdk version. 获取SDK版本号
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(this);
        String version = payTask.getVersion();
        Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
    }

    /**
     * 原生的H5（手机网页版支付切natvie支付） 【对应页面网页支付按钮】
     *
     * @param v
     */
    public void h5Pay(View v) {
        Intent intent = new Intent(this, H5PayDemoActivity.class);
        Bundle extras = new Bundle();
        /**
         * url是测试的网站，在app内部打开页面是基于webview打开的，demo中的webview是H5PayDemoActivity，
         * demo中拦截url进行支付的逻辑是在H5PayDemoActivity中shouldOverrideUrlLoading方法实现，
         * 商户可以根据自己的需求来实现
         */
        String url = "http://m.taobao.com";
        // url可以是一号店或者淘宝等第三方的购物wap站点，在该网站的支付过程中，支付宝sdk完成拦截支付
        extras.putString("url", url);
        intent.putExtras(extras);
        startActivity(intent);
    }

    /**
     * create the order info. 创建订单信息
     */
    private String getOrderInfo(String wmrid, String subject, String body, String price,
                                String PARTNER, String SELLER, String notfyUrl) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + wmrid + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + notfyUrl + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        System.out.println("aaaaaa" + orderInfo);

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content, String RSA_PRIVATE) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }


    private String str_result;

    /**
     * 获取支付宝的一些数据
     * http://www.ybt9.com/index.php?ctrl=app&source=1&action=apppaydata
     * &uid=13
     * &pwd=aaaaaa
     * &datatype=json&type=acount
     * &cost=43
     * &orderid=type=order
     * &orderid=22670
     * http://www.ybt9.com//index.php?ctrl=app&source=1&datatype=json&
     */
    public void getPayDataZFB(String type, String uid, String pwd, String cost, String orderid) {
        String PATH = null;
        if (userInfo.getCode().equals("0")) {
            PATH = HttpPath.PATH + HttpPath.PAY_APPDATA + "type=" + type +
                    "&uid=" + uid + "&pwd=" + pwd + "&cost=" + cost + "&orderid=" + orderid;
        } else {
            PATH = HttpPath.PATH + HttpPath.PAY_APPDATA + "type=" + type +
                    "&logintype=" + uid + "&loginphone=" + pwd + "&cost=" + cost + "&orderid=" + orderid;
        }

        System.out.println("" + PATH);
        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        str_result = result;
                        //获取
                        System.out.println("获取支付宝的一些pid什么的" + result);

                        //String PARTNER, String SELLER, String RSA_PRIVATE
                        PayDataZFB payDataZFB = GsonUtil.gsonIntance().gsonToBean(result, PayDataZFB.class);
                        String PARTNER = payDataZFB.getMsg().getAlipay().getPARTNER();
                        String SELLER = payDataZFB.getMsg().getAlipay().getSELLER();
                        String RSA_PRIVATE = payDataZFB.getMsg().getAlipay().getRSA_PRIVATE();

                        wmrid = String.valueOf(payDataZFB.getMsg().getAlipay().getWmrid());

                        notfyUrl = payDataZFB.getMsg().getAlipay().getNotify_Url();

                        String appid = payDataZFB.getMsg().getWeixin().getAppid();
                        String partnerid = payDataZFB.getMsg().getWeixin().getPartnerid();
                        String prepayid = payDataZFB.getMsg().getWeixin().getPrepayid();
                        String noncestr = payDataZFB.getMsg().getWeixin().getNoncestr();
                        String timestamp = String.valueOf(payDataZFB.getMsg().getWeixin().getTimestamp());
                        String packages = payDataZFB.getMsg().getWeixin().getPackageX();
                        String sign = payDataZFB.getMsg().getWeixin().getSign();

                        //判断支付方式
                        if (weixin.isChecked()) {
                            //微信支付
                            setWeiXin(appid, partnerid, prepayid, noncestr, timestamp, packages, sign);
                        } else if (zhifubao.isChecked()) {
                            //支付宝支付
                            setZhifubao(wmrid, PARTNER, SELLER, RSA_PRIVATE, notfyUrl);
                        }

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Errors error = GsonUtil.gsonIntance().gsonToBean(str_result, Errors.class);
                        toast(error.getMsg());

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
    }


    /*
    * 微信支付*/
    public void setWeiXin(String appid, String partnerid, String prepayid,
                          String noncestr, String timestamp, String packages, String sign) {

        final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, appid, false);
        msgApi.registerApp(appid);

        //Toast.makeText(this, "获取订单中...", Toast.LENGTH_SHORT).show();
        PayReq req = new PayReq();
        req.appId = appid;
        req.partnerId = partnerid;
        req.prepayId = prepayid;
        req.packageValue = packages;
        req.nonceStr = noncestr;
        req.timeStamp = timestamp;
        req.sign = sign;

        msgApi.sendReq(req);
        intent = new Intent();
        setResult(CodeUtils.REUEST_CODE_PAY, intent);

        PayActivity.this.finish();
    }

}
