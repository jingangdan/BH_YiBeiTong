package com.bh.yibeitong.ui.address;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
 * Created by jingang on 2016/11/21.
 * 管理我的收货地址
 */

public class AddAddressActivity extends BaseTextActivity {
    private TextView tv_mamage_address;

    //收货人姓名 电话 地址
    private EditText et_delivery_name, et_delivery_phone, et_delivery_address;

    private Button but_delivery;

    /*本地存储*/
    UserInfo userInfo;

    //请求数据的参数
    String uid, pwd, addresid, phone,
            contactname, defaults, sex,
            bigadr, detailadr, logintype,
            loginphone, lat, lng;

    //接收页面传值
    private Intent intent;

    private String str_contactname, str_phone, str_bigadr, str_detailadr;
    private String str_title;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_add_address);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        initData();

        setTitleBack(true, 0);
        setTitleName(str_title);

        pwd = userInfo.getPwd();

    }

    /**
     * 组件 初始化
     */
    public void initData() {
        userInfo = new UserInfo(getApplication());

        tv_mamage_address = (TextView) findViewById(R.id.tv_mamage_address);
        tv_mamage_address.setOnClickListener(this);

        et_delivery_name = (EditText) findViewById(R.id.et_delivery_name);
        et_delivery_phone = (EditText) findViewById(R.id.et_delivery_phone);
        et_delivery_address = (EditText) findViewById(R.id.et_delivery_address);

        but_delivery = (Button) findViewById(R.id.but_delivery);
        but_delivery.setOnClickListener(this);

        intent = getIntent();
        str_contactname = intent.getStringExtra("contactname");
        str_phone = intent.getStringExtra("phone");
        str_bigadr = intent.getStringExtra("bigadr");
        str_detailadr = intent.getStringExtra("detailadr");
        str_title = intent.getStringExtra("title");

        et_delivery_name.setText(str_contactname);
        et_delivery_phone.setText(str_phone);

        if (null == str_bigadr) {
            tv_mamage_address.setText("点击选择地址");
        } else {
            tv_mamage_address.setText(str_bigadr);
        }

        et_delivery_address.setText(str_detailadr);

        addresid = intent.getStringExtra("addresid");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_mamage_address:
                Intent intent = new Intent(AddAddressActivity.this, MoreAddressActivity.class);
                startActivityForResult(intent, 3);
                //startActivity(new Intent(AddAddressActivity.this, MoreAddressTestActivity.class));
                //startActivity(new Intent(AddAddressActivity.this, MoreAddressActivity.class));
                break;

            case R.id.but_delivery:

                System.out.println("用户登录返回信息" + userInfo.getUserInfo());

                Register register = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), Register.class);

                uid = register.getMsg().getUid();

                String s_phone = register.getMsg().getPhone();

                loginphone = register.getMsg().getPhone();

                //uid = "184";
                //pwd = "123456";
                //addressid = "";
                //phone = "17865069350";
                contactname = "jingang";

                phone = et_delivery_phone.getText().toString();
                //contactname = et_delivery_name.getText().toString();

                //detailadr = et_delivery_address.getText().toString();

                defaults = "1";
                sex = "0";

                //bigadr = "颐高上海街";

                bigadr = tv_mamage_address.getText().toString();

                try {
                    bigadr = URLEncoder.encode(bigadr, "UTF-8");
                    contactname = URLEncoder.encode(et_delivery_name.getText().toString(), "UTF-8");
                    detailadr = URLEncoder.encode(et_delivery_address.getText().toString(), "UTF-8");

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //detailadr = "一期A座2006";
                logintype = "";
                //loginphone = "17865069350";

                //lat = "35.11121798058952";
                //lng = "35.11121798058952";
                //保存地址 判断各个参数是否为空

                System.out.println("lat=" + lat + "  lng=" + lng);

                System.out.println("phone= " + phone + "name = " + contactname + "detailadr =" + detailadr);

                if (!contactname.equals("")) {
                    if (!phone.equals("")) {
                        if (!detailadr.equals("")) {

                            if (userInfo.getCode().equals("0")) {
                                System.out.println("addresid="+addresid);
                                postAddAddress(uid, pwd, addresid, phone, contactname, defaults, sex,
                                        bigadr, detailadr, logintype, loginphone, lat, lng);
                            } else {
                                postAddAddress("phone", s_phone, addresid, phone, contactname, defaults, sex,
                                        bigadr, detailadr, "phone", loginphone, lat, lng);
                            }


                        } else {
                            System.out.println("收货人详细地址不可为空");
                            toast("收货人详细地址不可为空");

                        }

                    } else {
                        System.out.println("收货人电话不可为空");
                        toast("收货人电话不可为空");
                    }
                } else {
                    System.out.println("收货人姓名不可为空");
                    toast("收货人姓名不可为空");
                }


                break;

            default:
                break;
        }
    }

    /**
     * 添加收货地址
     *
     * @param uid
     * @param pwd
     * @param addressid
     * @param phone
     * @param contactname
     * @param defaults
     * @param sex
     * @param bigadr
     * @param detailadr
     * @param logintype
     * @param loginphone
     * @param lat
     * @param lng
     */
    public void postAddAddress(String uid, String pwd, String addressid, final String phone,
                               final String contactname, String defaults, String sex,
                               final String bigadr, final String detailadr, String logintype,
                               String loginphone, final String lat, final String lng) {

        String PATH = "";
        if (userInfo.getCode().equals("0")) {
            PATH = HttpPath.PATH + HttpPath.ADDRESS_ADD + "uid=" + uid + "&pwd=" + pwd +
                    "&addresid=" + addressid + "&phone=" + phone +
                    "&contactname=" + contactname + "&default=" + defaults + "&sex=" + sex +
                    "&bigadr=" + bigadr + "&detailadr=" + detailadr + "&logintype=" + logintype +
                    "&loginphone=" + loginphone + "&lat=" + lat + "&lng=" + lng;
        } else {
            PATH = HttpPath.PATH + HttpPath.ADDRESS_ADD + "logintype=" + uid + "&loginphone=" + pwd +
                    "&addresid=" + addressid + "&phone=" + phone +
                    "&contactname=" + contactname + "&default=" + defaults + "&sex=" + sex +
                    "&bigadr=" + bigadr + "&detailadr=" + detailadr + "&logintype=" + logintype +
                    "&loginphone=" + loginphone + "&lat=" + lat + "&lng=" + lng;
        }

        System.out.println("address"+PATH);
        RequestParams params = new RequestParams(PATH);

        x.http().post(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("添加收货地址 =" + result);

                        String contactname = et_delivery_name.getText().toString();
                        String phone = et_delivery_phone.getText().toString();
                        String bigadr = tv_mamage_address.getText().toString();

                        if (bigadr.equals("")) {
                            bigadr = "颐高上海街(默认)";
                        }
                        String detailadr = et_delivery_address.getText().toString();

                        Error error = GsonUtil.gsonIntance().gsonToBean(result, Error.class);
                        if(error.isError() == true){
                            toast(error.getMsg().toString());

                        }else if(error.isError() == false){
                            toast("添加成功！");
                            Intent intent = new Intent();
                            intent.putExtra("contactname", contactname);
                            intent.putExtra("phone", phone);
                            intent.putExtra("address", bigadr + "" + detailadr);
                            intent.putExtra("bigadr", bigadr);
                            intent.putExtra("detailadr", detailadr);
                            intent.putExtra("lat", lat);
                            intent.putExtra("lng", lng);
                            //intent.putExtra("result", contactname + "  " + phone + "\n" + bigadr + detailadr);
                            setResult(3, intent);// 设置resultCode，onActivityResult()中能获取到
                            AddAddressActivity.this.finish();
                        }else{

                        }


                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        System.out.println("错误");

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3 && resultCode == 4) {
            Bundle bundle = data.getExtras();
            String strResult = bundle.getString("selectAddress");
            contactname = bundle.getString("contactname");
            phone = bundle.getString("phone");
            bigadr = bundle.getString("bigadr");
            detailadr = bundle.getString("detailadr");


            lat = bundle.getString("lat");
            lng = bundle.getString("lng");

            addresid = bundle.getString("addresid");

            tv_mamage_address.setText(strResult);

        }

    }

}