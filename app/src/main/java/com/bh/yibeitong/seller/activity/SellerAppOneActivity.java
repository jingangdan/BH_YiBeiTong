package com.bh.yibeitong.seller.activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.adapter.OrderDetaileAdapter;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.seller.AppOne;
import com.bh.yibeitong.refresh.MyListView;
import com.bh.yibeitong.refresh.PullToRefreshView;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by jingang on 2017/7/14.
 * 订单详情（商家端）
 */

public class SellerAppOneActivity extends BaseTextActivity {
    /*接口地址*/
    private String PATH = "";

    /*接收页面传值*/
    private Intent intent;
    private String uid, pwd, orderid;

    //内容滑动
    //private PullToRefreshView pullToRefreshView;
    private MyListView myListView;
    private AppOneAdapter odAdapter;

    private Button but_more_one;

    /*一下数据的显示控件*/
    private TextView order_number, order_time, order_payment,
            order_contactname, order_phone, order_address, tv_shopname;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.fragment_order_detaile);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("订单详情");
        setTitleBack(true, 0);

        initData();
    }

    public void initData() {
        intent = getIntent();
        uid = intent.getStringExtra("uid");
        pwd = intent.getStringExtra("pwd");
        orderid = intent.getStringExtra("orderid");

        //pullToRefreshView = (PullToRefreshView) findViewById(R.id.ptrv_order_detaile);
        myListView = (MyListView) findViewById(R.id.mlv_order_detaile);

        order_number = (TextView) findViewById(R.id.tv_order_number);
        order_time = (TextView) findViewById(R.id.tv_order_time);
        order_payment = (TextView) findViewById(R.id.tv_order_payment);
        order_contactname = (TextView) findViewById(R.id.tv_order_contactname);
        order_phone = (TextView) findViewById(R.id.tv_order_phone);
        order_address = (TextView) findViewById(R.id.tv_order_address);

        tv_shopname = (TextView) findViewById(R.id.tv_order_detailed_shopname);

        but_more_one = (Button) findViewById(R.id.but_more_one);
        but_more_one.setVisibility(View.GONE);


        getAppOne(uid, pwd, orderid);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    /**
     * 获取订单详情
     *
     * @param uid
     * @param pwd
     * @param orderid
     */
    public void getAppOne(String uid, String pwd, String orderid) {
        PATH = HttpPath.PATH + HttpPath.APP_ONE +
                "uid=" + uid + "&pwd=" + pwd + "&orderid=" + orderid;

        RequestParams params = new RequestParams(PATH);

        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("订单详情" + result);
                        AppOne appOne = GsonUtil.gsonIntance().gsonToBean(result, AppOne.class);

                        //组件赋值
                        String dno = appOne.getMsg().getDno();
                        String addtime = appOne.getMsg().getAddtime();
                        String paystatusintro = appOne.getMsg().getPaystatustype();
                        String buyername = appOne.getMsg().getBuyername();
                        String buyerphone = appOne.getMsg().getBuyerphone();
                        String buyeraddress = appOne.getMsg().getBuyeraddress();
                        String shopname = appOne.getMsg().getShopname();

                        order_number.setText(dno);
                        order_time.setText(addtime);
                        order_payment.setText(paystatusintro);
                        order_contactname.setText(buyername);
                        order_phone.setText(buyerphone);
                        order_address.setText(buyeraddress);

                        tv_shopname.setText(""+shopname);


                        odAdapter = new AppOneAdapter(SellerAppOneActivity.this, appOne.getMsg().getDet());
                        myListView.setAdapter(odAdapter);

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

    public class AppOneAdapter extends BaseAdapter {
        private Context mContext;
        private List<AppOne.MsgBean.DetBean> detBeen;

        public AppOneAdapter(Context mContext, List<AppOne.MsgBean.DetBean> detBeen) {
            this.mContext = mContext;
            this.detBeen = detBeen;
        }

        @Override
        public int getCount() {
            return detBeen.size();
        }

        @Override
        public Object getItem(int position) {
            return detBeen.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh;
            if (convertView == null) {
                vh = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_order_shopcart, null);

                vh.name = (TextView) convertView.findViewById(R.id.tv_item_od_name);
                vh.count = (TextView) convertView.findViewById(R.id.tv_item_od_count);
                vh.cost = (TextView) convertView.findViewById(R.id.tv_item_od_cost);

                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }

            String name = detBeen.get(position).getGoodsname();
            String count = detBeen.get(position).getGoodscount();
            String cost = detBeen.get(position).getGoodscost();

            vh.name.setText(name);
            vh.count.setText("×"+count);
            vh.cost.setText(cost);

            return convertView;
        }

        public class ViewHolder {
            TextView name, count, cost;
        }
    }


}
