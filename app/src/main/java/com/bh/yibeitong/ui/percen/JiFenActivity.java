package com.bh.yibeitong.ui.percen;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.Register;
import com.bh.yibeitong.bean.percen.ScoreLog;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.UserInfo;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by jingang on 2017/7/6.
 * 积分中心
 */
public class JiFenActivity extends BaseTextActivity {
    /*UI显示*/
    private TextView tv_jifen, tv_rule;
    private Button but_detailed, but_rule, but_go;
    private LinearLayout lin_detailed, lin_rule;

    private Intent intent;

    /*接口地址*/
    private String PATH = "";

    private UserInfo userInfo;
    private String uid, pwd, phone, jingang;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_jifen);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("积分中心");
        setTitleBack(true, 0);

        initData();
    }

    /*组件初始化*/
    public void initData() {
        userInfo = new UserInfo(getApplication());
        jingang = userInfo.getCode();

        tv_jifen = (TextView) findViewById(R.id.tv_jifen_jifen);
        but_detailed = (Button) findViewById(R.id.but_jifen_detailed);
        but_rule = (Button) findViewById(R.id.but_jifen_rule);
        but_go = (Button) findViewById(R.id.but_jifen_go);

        but_detailed.setOnClickListener(this);
        but_rule.setOnClickListener(this);
        but_go.setOnClickListener(this);

        lin_detailed = (LinearLayout) findViewById(R.id.lin_jifen_detailed);
        lin_rule = (LinearLayout) findViewById(R.id.lin_jifen_rule);

        tv_rule = (TextView) findViewById(R.id.tv_jifen_rule);

        tv_rule.setText("1、积分是指成功购物即可累计金额获得的积分。消费1元积1分。\n" +
                "2、成功购物是指：活动期间内在外卖人创建并完成交易——即买家已确认收货、安付通交易状态为“交易成功”的交易。\n" +
                "3、不做累计的部分包括但不仅限于：运费，购物券金额。\n" +
                "4、无论该交易属于一口价、拍卖，均可累计。\n" +
                "5、单笔交易，最多可获得2000积分，多余积分作废处理。\n" +
                "6、消费金额不足1元的零头部分不计入积分，积分按消费金额逐笔折算累加。\n" +
                "7、积分可在积分有礼专区参加积分活动。按活动要求，参加1次扣除相应积分。\n" +
                "8、现阶段活动包括积分兑换，积分抽奖。\n");

        isLoginOrLogintype();

    }


    /*控件样式初始化*/
    public void initializationControl() {
        but_detailed.setTextColor(Color.rgb(0, 0, 0));
        but_rule.setTextColor(Color.rgb(0, 0, 0));
        lin_detailed.setVisibility(View.GONE);
        lin_rule.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.but_jifen_detailed:
                //积分明细
                initializationControl();
                but_detailed.setTextColor(Color.rgb(162, 203, 52));
                lin_detailed.setVisibility(View.VISIBLE);
                break;

            case R.id.but_jifen_rule:
                //积分规则
                initializationControl();
                but_rule.setTextColor(Color.rgb(162, 203, 52));
                lin_rule.setVisibility(View.VISIBLE);
                break;

            case R.id.but_jifen_go:
                //积分兑换
                intent = new Intent(JiFenActivity.this, ExChangeActivity.class);
                intent.putExtra("", "");//传值 积分
                startActivity(intent);
                break;
        }
    }

    /*接口参数的获取*/
    public void isLoginOrLogintype() {
        if (!(userInfo.getUserInfo().equals(""))) {
            Register register = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), Register.class);
            uid = register.getMsg().getUid();
            phone = register.getMsg().getPhone();
            if (!(userInfo.getPwd().equals(""))) {
                pwd = userInfo.getPwd();
            }

            if (jingang.equals("0")) {
                getScoreLog(uid, pwd, 1, 100);
            } else {
                getScoreLog("phone", phone, 1, 100);
            }
        } else {
            toast("未登录");
        }
    }

    /**
     * 积分明细
     *
     * @param uid
     * @param pwd
     * @param page
     * @param pagesize
     */
    public void getScoreLog(String uid, String pwd, int page, int pagesize) {
        if (jingang.equals("0")) {
            PATH = HttpPath.PATH + HttpPath.SCORELOG +
                    "uid=" + uid + "&pwd=" + pwd + "&page=" + page + "&pagesize=" + pagesize;
        } else {
            PATH = HttpPath.PATH + HttpPath.SCORELOG +
                    "logintype=" + uid + "&loginphone=" + pwd + "&page=" + page + "&pagesize=" + pagesize;
        }
        System.out.println("积分明细" + PATH);
        RequestParams params = new RequestParams(PATH);

        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("积分明细" + result);
                        ScoreLog scoreLog = GsonUtil.gsonIntance().gsonToBean(result, ScoreLog.class);

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
