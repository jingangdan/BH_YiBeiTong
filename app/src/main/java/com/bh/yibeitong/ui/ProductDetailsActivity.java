package com.bh.yibeitong.ui;

import android.app.AlertDialog;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;

/**
 * Created by jingang on 2016/10/24.
 * 商品详情页
 */

public class ProductDetailsActivity extends BaseTextActivity{
    private RelativeLayout rel_product_parameter, rel_product_color;
    private TextView tv_product_paraneter, tv_product_color;

    private AlertDialog alertDialog;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_product_details);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        initData();
        setTitleName("商品详情");
        setTitleBack(true, 0);
        setRightRes();

    }

    /**
     * 组件 初始化
     */
    public void initData(){
        rel_product_parameter = (RelativeLayout) findViewById(R.id.rel_product_parameter);
        rel_product_color = (RelativeLayout) findViewById(R.id.rel_product_color);

        rel_product_parameter.setOnClickListener(this);
        rel_product_color.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.rel_product_parameter:
                toast("选择产品参数");
                getAlertPP();
                break;

            case R.id.rel_product_color:
                toast("选择产品颜色");
                getAlertPC();
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.title_message:
                toast("消息");
                break;
        }
        return super.onMenuItemClick(item);
    }

    /**
     *
     */
    public void getAlertPP(){
        alertDialog = new AlertDialog.Builder(this).create();
        View localView = this.getLayoutInflater()
                .inflate(R.layout.alert_product_parameter, null);
        localView.setAnimation(AnimationUtils.loadAnimation(
                this, R.anim.slide_bottom_to_top));
        Window localWindow = alertDialog.getWindow();
        localWindow.getAttributes();
        alertDialog.show();
        localWindow.setContentView(localView);
        localWindow.setGravity(Gravity.BOTTOM);
        //localWindow.setLayout(-1, 800);
    }

    /**
     *
     */
    public void getAlertPC(){

        alertDialog = new AlertDialog.Builder(this).create();
        View localView = this.getLayoutInflater()
                .inflate(R.layout.alert_product_color, null);
        localView.setAnimation(AnimationUtils.loadAnimation(
                this, R.anim.slide_bottom_to_top));
        Window localWindow = alertDialog.getWindow();
        localWindow.getAttributes();
        alertDialog.show();
        localWindow.setContentView(localView);
        localWindow.setGravity(Gravity.BOTTOM);
        //localWindow.setLayout(-1, 800);

    }
}
