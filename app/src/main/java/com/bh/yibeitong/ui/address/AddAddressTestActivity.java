package com.bh.yibeitong.ui.address;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.Register;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.UserInfo;

import org.apache.http.entity.mime.content.StringBody;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Created by binbin on 2016/11/25.
 */

public class AddAddressTestActivity extends BaseTextActivity {
    private TextView tv_mamage_address;

    private EditText et_delivery_name, et_delivery_phone, et_delivery_address;

    private Button but_delivery;

    UserInfo userInfo;

    String uid, pwd, addressid, phone,
            contactname, defaults, sex,
            bigadr, detailadr, logintype,
            loginphone, lat, lng;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_add_address);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("管理地址");
        setTitleBack(true, 0);
        initData();

        pwd = userInfo.getPwd();


    }

    public void initData() {
        userInfo = new UserInfo(getApplication());

        tv_mamage_address = (TextView) findViewById(R.id.tv_mamage_address);
        tv_mamage_address.setOnClickListener(this);

        et_delivery_name = (EditText) findViewById(R.id.et_delivery_name);
        et_delivery_phone = (EditText) findViewById(R.id.et_delivery_phone);
        et_delivery_address = (EditText) findViewById(R.id.et_delivery_address);

        but_delivery = (Button) findViewById(R.id.but_delivery);
        but_delivery.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_mamage_address:
                //startActivity(new Intent(AddAddressActivity.this, MoreAddressTestActivity.class));
                //startActivity(new Intent(AddAddressActivity.this, MoreAddressActivity.class));
                break;
            case R.id.but_delivery:
                Register register = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), Register.class);

                uid = register.getMsg().getUid();

                loginphone = register.getMsg().getPhone();

                //uid = "184";
                //pwd = "123456";
                addressid = "";
                //phone = "17865069350";
                contactname = "jingang";

                phone = et_delivery_phone.getText().toString();
                contactname = et_delivery_name.getText().toString();

                detailadr = et_delivery_address.getText().toString();

                defaults = "";
                sex = "0";
                bigadr = "颐高上海街";
                try {
                    StringBody contentBody = new StringBody(bigadr, Charset.forName("UTF-8"));

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //detailadr = "一期A座2006";
                logintype = "";
                //loginphone = "17865069350";

                lat = "35.11121798058952";
                lng = "35.11121798058952";
                //保存地址 判断各个参数是否为空

                System.out.println("phone= " + phone + "name = " + contactname + "detailadr =" + detailadr);

                if (!contactname.equals("")) {
                    if (!phone.equals("")) {
                        if (!detailadr.equals("")) {

                            postAddAddress(uid, pwd, addressid, phone, contactname, defaults, sex,
                                    bigadr, detailadr, logintype, loginphone, lat, lng);

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

    public void postAddAddress(String uid, String pwd, String addressid, String phone,
                               String contactname, String defaults, String sex,
                               String bigadr, String detailadr, String logintype,
                               String loginphone, String lat, String lng) {

        String PATH = HttpPath.PATH + HttpPath.ADDRESS_ADD + "uid=" + uid + "&pwd=" + pwd +
                "&addresid=" + addressid + "&phone=" + phone +
                "&contactname=" + contactname + "&default=" + defaults + "&sex=" + sex +
                "&bigadr=" + bigadr + "&detailadr=" + detailadr + "&logintype=" + logintype +
                "&loginphone=" + loginphone + "&lat=" + lat + "&lng=" + lng;

        RequestParams params = new RequestParams(PATH);

        x.http().post(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
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
