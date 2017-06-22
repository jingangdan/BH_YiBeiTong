package com.bh.yibeitong.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bh.yibeitong.R;
import com.bh.yibeitong.adapter.SimpleFragmentPagerAdapter;
import com.bh.yibeitong.fragment.FMShopComment;
import com.bh.yibeitong.fragment.FMShopGoods;
import com.bh.yibeitong.fragment.FMShopSeller;
import com.bh.yibeitong.seller.fragment.FMSellerCollect;
import com.bh.yibeitong.seller.fragment.FMSellerOK;
import com.bh.yibeitong.seller.fragment.FMSellerSend;
import com.bh.yibeitong.utils.NetworkUtils;
import com.bh.yibeitong.view.NoScrollViewPager;
import com.bh.yibeitong.view.NoScrollViewPagerSG;

import org.xutils.BuildConfig;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingang on 2016/11/01.
 * 进入 店铺
 */
public class ShopActivity extends FragmentActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    //头部控件
    private TextView tv_header_title;
    private ImageView iv_header_left, iv_header_right;

    private TabLayout tl_shop;
    /**
     * 自定义ViewPager 可修改NoScrollViewPager中 boolean noScroll的
     * false和true选择是否禁止左右滑动 true为禁止滑动
     */
    private NoScrollViewPagerSG vp_shop;

    private String[] titles = new String[]{"商品", "评价", "商家"};

//    Fragment[] fragments = new Fragment[]{new FMShopGoods(),
//            new FMShopComment(), new FMShopSeller()};

    private List<Fragment> fragments = new ArrayList<>();

    private SimpleFragmentPagerAdapter sfpAdapter;

    private Intent intent;
    public static String shopid, shopname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        intent = getIntent();
        shopid = intent.getStringExtra("shopid");
        shopname = intent.getStringExtra("shopname");

        fragments.add(FMShopGoods.newInstance("FMShopGoods"));
        fragments.add(FMShopComment.newInstance("FMShopComment"));
        fragments.add(FMShopSeller.newInstance("FMShopSeller"));


        initData();
        tv_header_title.setText(shopname);//显示标题
        //iv_header_right.setImageResource();//显示右侧图标  收藏（爱心）

        sfpAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), this,
                fragments, titles);

        vp_shop.setAdapter(sfpAdapter);
        vp_shop.setOffscreenPageLimit(titles.length);

        //viewPager 切换监听
        vp_shop.setOnPageChangeListener(this);

        tl_shop.setupWithViewPager(vp_shop);


    }

    /**
     * 组件 初始化
     */
    public void initData() {
        tv_header_title = (TextView) findViewById(R.id.tv_header_title);
        iv_header_left = (ImageView) findViewById(R.id.iv_header_left);
        iv_header_right = (ImageView) findViewById(R.id.iv_header_right);

        iv_header_left.setOnClickListener(this);
        iv_header_right.setOnClickListener(this);


        tl_shop = (TabLayout) findViewById(R.id.tl_shop);
        vp_shop = (NoScrollViewPagerSG) findViewById(R.id.vp_shop);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_header_left:
                //左
                finish();
                break;

            case R.id.iv_header_right:
                //右
                //Toast.makeText(ShopActivity.this, "消息", Toast.LENGTH_SHORT).show();
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

    public void isNetworkUtil() {
        if (NetworkUtils.isNotWorkAvilable(this)) {
            if (NetworkUtils.getCurrentNetType(this) != null) {
                //有网络连接
            }
        } else {
            //无网络连接

        }
    }


}