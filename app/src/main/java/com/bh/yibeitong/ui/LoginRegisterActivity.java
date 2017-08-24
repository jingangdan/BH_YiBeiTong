package com.bh.yibeitong.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;

import com.bh.yibeitong.R;
import com.bh.yibeitong.actitvity.ActivityCollector;
import com.bh.yibeitong.adapter.SimpleFragmentPagerAdapter;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.fragment.FMLongin;
import com.bh.yibeitong.fragment.FMRegister;
import com.bh.yibeitong.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jingang on 2016/11/3.
 * 登录注册
 */
public class LoginRegisterActivity extends BaseTextActivity implements ViewPager.OnPageChangeListener {
    private TabLayout tl_login_register;
    private NoScrollViewPager vp_login_register;

    private String[] titles = new String[]{"登录", "注册"};
    private List<Fragment> fragments = new ArrayList<>();
    private SimpleFragmentPagerAdapter sfpAdapter;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_login_register);
    }

    @Override
    protected void initWidght() {
        super.initWidght();

        setTitleName("登录注册");
        setTitleBack(true, 0);

        ActivityCollector.addActivity(this);

        fragments.add(FMLongin.newInstance("FMLongin"));
        fragments.add(FMRegister.newInstance("FMRegister"));

        initData();

        sfpAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), this, fragments, titles);
        vp_login_register.setAdapter(sfpAdapter);

        vp_login_register.setOffscreenPageLimit(titles.length);

        vp_login_register.setOnPageChangeListener(this);
        tl_login_register.setupWithViewPager(vp_login_register);

    }

    /**
     * 组件 初始化
     */
    public void initData() {
        tl_login_register = (TabLayout) findViewById(R.id.tl_login_register);
        vp_login_register = (NoScrollViewPager) findViewById(R.id.vp_login_register);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return super.onMenuItemClick(item);
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
