package com.bh.yibeitong.ui.order;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.utils.CodeUtils;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.UserInfo;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by jingang on 2017/8/23.
 * 申请退款
 */

public class OrderControlActivity extends BaseTextActivity {

    /*接口地址*/
    private String PATH = "";

    /*页面传值*/
    private Intent intent;
    private String uid = "", pwd = "", orderid = "", allcost = "", pstype = "";

    /*本地轻量型缓存*/
    private UserInfo userInfo;

    /*UI显示*/
    private TextView tv_allcost;
    private CheckBox cb1, cb2, cb3, cb4, cb5, cb6, cb7;
    private Button but_repay;
    private String reason = "", content = "";

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_order_control);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("申请退款");
        setTitleBack(true, 0);

        initData();
    }

    /**/
    public void initData() {
        userInfo = new UserInfo(getApplication());

        intent = getIntent();
        uid = intent.getStringExtra("uid");
        pwd = intent.getStringExtra("pwd");
        orderid = intent.getStringExtra("orderid");
        allcost = intent.getStringExtra("allcost");
        pstype = intent.getStringExtra("pstype");

        tv_allcost = (TextView) findViewById(R.id.tv_control_allcost);
        cb1 = (CheckBox) findViewById(R.id.cb_control1);
        cb2 = (CheckBox) findViewById(R.id.cb_control2);
        cb3 = (CheckBox) findViewById(R.id.cb_control3);
        cb4 = (CheckBox) findViewById(R.id.cb_control4);
        cb5 = (CheckBox) findViewById(R.id.cb_control5);
        cb6 = (CheckBox) findViewById(R.id.cb_control6);
        cb7 = (CheckBox) findViewById(R.id.cb_control7);

        but_repay = (Button) findViewById(R.id.but_control_repay);
        but_repay.setOnClickListener(this);


        tv_allcost.setText("￥" + allcost);
        /**/
        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    setCheckBox();
                    cb1.setChecked(true);
                    reason = "买错了，买多了";
                } else {
                    reason = "";
                }
            }
        });

        cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    setCheckBox();
                    cb2.setChecked(true);
                    reason = "地址、电话填写有误";
                } else {
                    reason = "";
                }
            }
        });

        cb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    setCheckBox();
                    cb3.setChecked(true);
                    reason = "计划有变，不想买了";
                } else {
                    reason = "";
                }
            }
        });

        cb4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    setCheckBox();
                    cb4.setChecked(true);
                    reason = "商品品质有问题";
                } else {
                    reason = "";
                }
            }
        });

        cb5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    setCheckBox();
                    cb5.setChecked(true);
                    reason = "送的太慢，等太久了";
                } else {
                    reason = "";
                }
            }
        });

        cb6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    setCheckBox();
                    cb6.setChecked(true);
                    reason = "没有给承诺的优惠";
                } else {
                    reason = "";
                }
            }
        });

        cb7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    setCheckBox();
                    cb7.setChecked(true);
                    reason = "买其他的了";
                } else {
                    reason = "";
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.but_control_repay:
                System.out.println("uid = " + uid + "  pwd = " + pwd + "  orderid = " + orderid + "  reason = " + reason + "  content =" + content + "  pstype =" + pstype);
                if (reason.equals("")) {
                    toast("请选择退款原因");
                } else {
                    if (userInfo.getCode().equals("0")) {
                        getOrderControl(uid, pwd, orderid, reason, content, pstype);
                    } else if (userInfo.getCode().equals("1")) {
                        getOrderControl("phone", pwd, orderid, reason, content, pstype);
                    } else {
                    }
                }


                break;

            default:
                break;
        }
    }

    public void setCheckBox() {
        cb1.setChecked(false);
        cb2.setChecked(false);
        cb3.setChecked(false);
        cb4.setChecked(false);
        cb5.setChecked(false);
        cb6.setChecked(false);
        cb7.setChecked(false);
    }

    /**
     * 申请退款
     *
     * @param uid
     * @param pwd
     * @param reason  退款原因
     * @param content 退款详细内容说明
     * @param typeid  支付类型  0支付宝  1  账号余额
     */
    public void getOrderControl(String uid, String pwd, String orderid, String reason, String content, String typeid) {
        if (userInfo.getCode().equals("0")) {
            PATH = HttpPath.PATH + HttpPath.ORDER_NEW_ORDERCONTROL +
                    "uid=" + uid + "&pwd=" + pwd + "&orderid=" + orderid +
                    "&reason=" + reason + "&content=" + content + "typeid=" + typeid;
        } else if (userInfo.getCode().equals("1")) {
            PATH = HttpPath.PATH + HttpPath.ORDER_NEW_ORDERCONTROL +
                    "uid=" + uid + "&pwd=" + pwd + "&reason=" + reason
                    + "&content=" + content + "typeid=" + typeid;
        } else {
        }

        RequestParams params = new RequestParams(PATH);

        System.out.println("申请退款" + PATH);

        x.http().post(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("申请退款" + result);


                        intent = new Intent();
                        setResult(CodeUtils.REQUEST_CODE_ORDER_CONTROL, intent);
                        OrderControlActivity.this.finish();

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
