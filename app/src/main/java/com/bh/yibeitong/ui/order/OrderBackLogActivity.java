package com.bh.yibeitong.ui.order;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.BackLog;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.UserInfo;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by jingang on 2017/8/30.
 * 退款详情
 */

public class OrderBackLogActivity extends BaseTextActivity {
    /*页面传值*/
    private Intent intent;
    private String uid = "", pwd = "", orderid = "";

    /*本地轻量型缓存*/
    private UserInfo userInfo;

    /*UI展示*/
    private TextView tv_allcost, tv_status, tv_reason, tv_content;
    private String allcost = "", status = "", reason = "", content = "";
    private Button but_back;

    /*接口地址*/
    private String PATH = "";


    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_order_backlog);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("退款详情");
        setTitleBack(true, 0);

        initData();
    }

    public void initData() {
        userInfo = new UserInfo(getApplication());

        intent = getIntent();
        uid = intent.getStringExtra("uid");
        pwd = intent.getStringExtra("pwd");
        orderid = intent.getStringExtra("orderid");

        tv_allcost = (TextView) findViewById(R.id.tv_order_backlog_allcost);
        tv_status = (TextView) findViewById(R.id.tv_order_backlog_status);
        tv_reason = (TextView) findViewById(R.id.tv_order_backlog_reason);
        tv_content = (TextView) findViewById(R.id.tv_order_backlog_content);

        but_back = (Button) findViewById(R.id.but_order_backlog_back);
        but_back.setOnClickListener(this);


        if (userInfo.getCode().equals("0")) {
            getDrawBackLog(uid, pwd, orderid);
        } else {
            getDrawBackLog("phone", pwd, orderid);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.but_order_backlog_back:
                //返回上层
                OrderBackLogActivity.this.finish();
                break;

            default:
                break;
        }
    }

    /**
     * 退款详情
     *
     * @param uid
     * @param pwd
     * @param orderid
     */
    public void getDrawBackLog(String uid, String pwd, String orderid) {
        if (userInfo.getCode().equals("0")) {
            PATH = HttpPath.PATH + HttpPath.ORDER_DRAWBACKLOG +
                    "uid=" + uid + "&pwd=" + pwd + "&orderid=" + orderid;
        } else if (userInfo.getCode().equals("1")) {
            PATH = HttpPath.PATH + HttpPath.ORDER_DRAWBACKLOG +
                    "logintype=" + uid + "&loginphone=" + pwd + "&orderid=" + orderid;
        } else {
        }

        System.out.println("退款详情" + PATH);
        RequestParams params = new RequestParams(PATH);

        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("退款详情" + result);

                        BackLog backLog = GsonUtil.gsonIntance().gsonToBean(result, BackLog.class);
                        allcost = backLog.getMsg().getCost();
                        status = backLog.getMsg().getStatus();
                        reason = backLog.getMsg().getReason();
                        content = backLog.getMsg().getContent();

                        tv_allcost.setText("￥" + allcost);
                        tv_reason.setText("" + reason);
                        tv_content.setText("" + content);

                        if (status.equals("0")) {
                            tv_status.setText("待处理");
                        } else if (status.equals("1")) {
                            tv_status.setText("退款成功");
                        }


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
