package com.bh.yibeitong.ui.percen;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.adapter.GiftAdapter;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.Gift;
import com.bh.yibeitong.refresh.MyGridView;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingang on 2016/12/7.
 * 兑换礼品
 */

public class ExChangeActivity extends BaseTextActivity {

    private TextView myJifen, notes;

    /*兑换礼品列表UI*/
    private GiftAdapter giftAdapter;
    private MyGridView myGridView;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_exchange);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("积分兑换");
        setTitleBack(true, 0);

        initData();

        getGiftList();
    }

    /**
     * 组件 初始化
     */
    public void initData() {

        myJifen = (TextView) findViewById(R.id.tv_gift_jifen);
        notes = (TextView) findViewById(R.id.tv_exchange_notes);

        notes.setOnClickListener(this);

        myGridView = (MyGridView) findViewById(R.id.mgv_exchange);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_exchange_notes:
                //点击产看记录
                Intent intent = new Intent(ExChangeActivity.this, ExChangeNoteActivity.class);

                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * 获取礼品列表  无参数
     */
    public void getGiftList() {
        String PATH = HttpPath.PATH + HttpPath.GIFT_LIST;

        RequestParams params = new RequestParams(PATH);

        System.out.println("礼品列表" + PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("礼品列表" + result);

                        Gift gift = GsonUtil.gsonIntance().gsonToBean(result, Gift.class);

                        giftAdapter = new GiftAdapter(ExChangeActivity.this, gift.getMsg());

                        myGridView.setAdapter(giftAdapter);


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
