package com.bh.yibeitong.ui;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;

/**
 * Created by jingang on 2016/11/14.
 * 商家入驻
 */

public class BusinessActivity extends BaseTextActivity {
    //填写商家资料  填写账户信息  等待审核
    private LinearLayout lin_business_data, lin_business_manage, lin_business_examine;

    private EditText et_bs_phone, et_bs_shop, et_bs_address;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_business);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("商家入驻");
        setTitleBack(true, 0);
    }

    /**
     * 组件 初始化
     */
    public void initData() {
        lin_business_data = (LinearLayout) findViewById(R.id.lin_business_data);
        lin_business_manage = (LinearLayout) findViewById(R.id.lin_business_manage);
        lin_business_examine = (LinearLayout) findViewById(R.id.lin_business_examine);

        lin_business_data.setOnClickListener(this);
        lin_business_manage.setOnClickListener(this);
        lin_business_examine.setOnClickListener(this);

        et_bs_phone = (EditText) findViewById(R.id.et_bs_phone);
        et_bs_shop = (EditText) findViewById(R.id.et_bs_shop);
        et_bs_address = (EditText) findViewById(R.id.et_bs_address);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case 1:

                break;

            default:
                break;
        }
    }

    /**
     * 隐藏布局
     */
    public void isVisibleLin() {
        lin_business_data.setVisibility(View.INVISIBLE);
        lin_business_manage.setVisibility(View.INVISIBLE);
        lin_business_examine.setVisibility(View.INVISIBLE);

    }

}
