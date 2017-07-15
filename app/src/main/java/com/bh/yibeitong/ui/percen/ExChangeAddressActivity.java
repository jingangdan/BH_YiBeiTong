package com.bh.yibeitong.ui.percen;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.Error;
import com.bh.yibeitong.bean.Register;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.UserInfo;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by jingang on 2017/7/7.
 * 积分兑换 添加用户信息
 */

public class ExChangeAddressActivity extends BaseTextActivity {
    /*UI*/
    private EditText et_contactname, et_phone, et_address, et_beizhu;
    private Button but_ok;
    private String contactname = "", s_phone = "", address = "", beizhu = "";

    /*接口地址*/
    private String PATH = "";

    /*接收页面传值*/
    private Intent intent;
    private String giftid, giftscore;

    /*本地轻量缓存*/
    private UserInfo userInfo;
    private String uid, pwd, phone, jingang;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_exchangeaddress);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("积分兑换");
        setTitleBack(true, 0);

        initData();
    }

    public void initData() {
        userInfo = new UserInfo(getApplication());
        jingang = userInfo.getCode();

        intent = getIntent();
        giftid = intent.getStringExtra("giftid");
        giftscore = intent.getStringExtra("giftscore");

        et_contactname = (EditText) findViewById(R.id.et_exchangeaddress_contactname);
        et_phone = (EditText) findViewById(R.id.et_exchangeaddress_phone);
        et_address = (EditText) findViewById(R.id.et_exchangeaddress_address);
        et_beizhu = (EditText) findViewById(R.id.et_exchangeaddress_beizhu);

        but_ok = (Button) findViewById(R.id.but_exchangeaddress_ok);
        but_ok.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.but_exchangeaddress_ok:
                //立即兑换
                contactname = et_contactname.getText().toString();
                s_phone = et_phone.getText().toString();
                address = et_address.getText().toString();

                try {
                    contactname = URLEncoder.encode(contactname, "UTF-8");
                    address = URLEncoder.encode(address, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                if (!contactname.equals("")) {
                    if (!s_phone.equals("")) {
                        if (!address.equals("")) {
                            isLoginOrLogintype(contactname, s_phone, address);
                        } else {
                            toast("收货地址不可为空");
                        }
                    } else {
                        toast("收货人电话不可为空");
                    }
                } else {
                    toast("收货人姓名不可为空");
                }

                break;

            default:
                break;
        }
    }

    /*接口参数的获取*/
    public void isLoginOrLogintype(String contactname, String s_phone, String address) {
        if (!(userInfo.getUserInfo().equals(""))) {
            Register register = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), Register.class);
            uid = register.getMsg().getUid();
            phone = register.getMsg().getPhone();
            if (!(userInfo.getPwd().equals(""))) {
                pwd = userInfo.getPwd();
            }

            if (jingang.equals("0")) {
                postExChange(uid, pwd, giftid, contactname, s_phone, address);

            } else {
                postExChange("phone", phone, giftid, contactname, s_phone, address);
            }
        } else {
            toast("未登录");
        }
    }

    /**
     * 添加用户信息（积分兑换）
     *
     * @param uid
     * @param pwd
     * @param id
     * @param contactname
     * @param phone
     * @param address
     */
    private String str_result = "";
    public void postExChange(
            String uid, String pwd, String id, String contactname, String phone, String address) {

        if (jingang.equals("0")) {
            PATH = HttpPath.PATH + HttpPath.GIFT_EXCHANGE +
                    "uid=" + uid + "&pwd=" + pwd + "&id=" + id +
                    "&contactman=" + contactname + "&telphone=" + phone + "&address=" + address;
        } else {
            PATH = HttpPath.PATH + HttpPath.GIFT_EXCHANGE +
                    "logintype=" + uid + "&loginphone=" + pwd + "&id=" + id +
                    "&contactman=" + contactname + "&telphone=" + phone + "&address=" + address;
        }

        System.out.println("添加用户信息（积分兑换）" + PATH);

        RequestParams params = new RequestParams(PATH);

        x.http().post(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        str_result = result;
                        System.out.println("添加用户信息（积分兑换）" + result);
                        Error error = GsonUtil.gsonIntance().gsonToBean(result, Error.class);
                        if(error.isError() == false){
                            toast(error.getMsg().toString());

                            intent = new Intent();
                            intent.putExtra("jifen", "1");
                            intent.putExtra("giftscore",giftscore);
                            setResult(33, intent);
                            ExChangeAddressActivity.this.finish();
                        }

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Error error = GsonUtil.gsonIntance().gsonToBean(str_result, Error.class);
                        toast(error.getMsg().toString());
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
