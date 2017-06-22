package com.bh.yibeitong.ui.setting;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.adapter.SimpleFragmentPagerAdapter;
import com.bh.yibeitong.base.BaseActivity;
import com.bh.yibeitong.fragment.setting.FMComplain;
import com.bh.yibeitong.fragment.setting.FMProblem;
import com.bh.yibeitong.seller.fragment.FMSellerCollect;
import com.bh.yibeitong.seller.fragment.FMSellerOK;
import com.bh.yibeitong.seller.fragment.FMSellerSend;
import com.bh.yibeitong.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingang on 2016/11/11.
 * 意见反馈
 */

public class FeedbackActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ImageView iv_feed_back;
    private TabLayout tl_setting_feedback;
    private NoScrollViewPager vp_setting_feedback;
    private SimpleFragmentPagerAdapter sfpAdapter;

    private String[] titles = new String[]{"体验问题", "商品/商家投诉"};
    //private Fragment[] fragments = new Fragment[]{new FMProblem(), new FMComplain()};

    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initData();

        fragments.add(FMProblem.newInstance("FMProblem"));
        fragments.add(FMComplain.newInstance("FMComplain"));

        sfpAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), this, fragments, titles);
        vp_setting_feedback.setAdapter(sfpAdapter);

        vp_setting_feedback.setOffscreenPageLimit(titles.length);

        vp_setting_feedback.setOnPageChangeListener(this);
        tl_setting_feedback.setupWithViewPager(vp_setting_feedback);

    }

    public void initData() {
        iv_feed_back = (ImageView) findViewById(R.id.iv_feed_back);
        iv_feed_back.setOnClickListener(this);

        tl_setting_feedback = (TabLayout) findViewById(R.id.tl_setting_feedback);
        vp_setting_feedback = (NoScrollViewPager) findViewById(R.id.vp_setting_feedback);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_feed_back:
                finish();
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
