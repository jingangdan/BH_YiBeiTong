package com.bh.yibeitong.ui.percen;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.adapter.SimpleFragmentPagerAdapter;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.Register;
import com.bh.yibeitong.bean.UserInfos;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.NoScrollViewPager;
import com.bh.yibeitong.view.UserInfo;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingang on 2016/12/8.
 * 账户余额
 */

public class YuEActivity extends BaseTextActivity implements ViewPager.OnPageChangeListener {
    /*接收页面传值*/
    private Intent intent;
    private String s_yue;

    private TextView tv_yue_yue;

    /**/
    private List<Fragment> fragments = new ArrayList<>();
    private String[] titles = new String[]{"充值", "充值卡充值"};
    private SimpleFragmentPagerAdapter sfpAdapter;

    private TabLayout tabLayout;
    private NoScrollViewPager noScrollViewPager;

    /*余额明细*/
    private TextView tv_yue_manage;

    private UserInfo userInfo;
    private String uid, pwd, phone, jingang;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_yue);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("余额");
        setTitleBack(true, 0);

        fragments.add(FMRecharge.newInstance("FMRecharge"));
        fragments.add(FMRechargeCard.newInstance("FMRechargeCard"));

        initData();

    }

    /**
     * 组件初始化
     */
    public void initData(){
        intent = getIntent();
        //s_yue = intent.getStringExtra("yue");
        userInfo = new UserInfo(getApplication());
        jingang = userInfo.getCode();

        tabLayout = (TabLayout) findViewById(R.id.tl_yue);
        noScrollViewPager = (NoScrollViewPager) findViewById(R.id.vp_yue);
        tv_yue_manage = (TextView) findViewById(R.id.tv_yue_manage);
        tv_yue_manage.setOnClickListener(this);

        tv_yue_yue = (TextView) findViewById(R.id.tv_yue_yue);
        //tv_yue_yue.setText(""+s_yue);


        sfpAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), this, fragments, titles);
        noScrollViewPager.setAdapter(sfpAdapter);

        noScrollViewPager.setOffscreenPageLimit(titles.length);

        noScrollViewPager.setOnPageChangeListener(this);
        tabLayout.setupWithViewPager(noScrollViewPager);

        isLoginOrLogintype();
    }


    public void isLoginOrLogintype() {
        if (!(userInfo.getUserInfo().equals(""))) {
            Register register = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), Register.class);
            uid = register.getMsg().getUid();
            phone = register.getMsg().getPhone();
            if (!(userInfo.getPwd().equals(""))) {
                pwd = userInfo.getPwd();
            }

            if (jingang.equals("0")) {
                getAppMem(uid, pwd);

            } else {
                getAppMem("phone", phone);
            }
        } else {
            //toast("未登录");
        }
    }

    /**
     * 获取用户信息
     *
     * @param uid
     * @param pwd
     */
    public void getAppMem(String uid, String pwd) {
        String PATH;
        if (userInfo.getCode().toString().equals("0")) {
            PATH = HttpPath.PATH + HttpPath.USER_GETAPPMEM +
                    "uid=" + uid + "&pwd=" + pwd;
        } else {
            PATH = HttpPath.PATH + HttpPath.USER_GETAPPMEM +
                    "logintype=" + uid + "&loginphone=" + pwd;
        }
        System.out.println("获取用户信息" + PATH);

        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("获取用户信息" + result);

                        UserInfos userInfos = GsonUtil.gsonIntance().gsonToBean(result, UserInfos.class);

                        s_yue = userInfos.getMsg().getCost();
                        tv_yue_yue.setText("" + s_yue);
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


    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()){

            case R.id.tv_yue_manage:
                startActivity(new Intent(YuEActivity.this, PayLogActivity.class));

                break;

            default:
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
