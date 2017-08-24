package com.bh.yibeitong.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.adapter.OrderDetaileAdapter;
import com.bh.yibeitong.bean.OrderDetaile;
import com.bh.yibeitong.bean.Register;
import com.bh.yibeitong.refresh.MyListView;
import com.bh.yibeitong.ui.order.OrderDetaileActivity;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.UserInfo;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by jingang on 2016/11/25.
 * 订单详情
 */

public class FMOrderDetaile extends Fragment implements View.OnClickListener {
    private View view;

    //内容滑动
    //private PullToRefreshView pullToRefreshView;
    private MyListView myListView;
    private OrderDetaileAdapter odAdapter;

    String orderid = OrderDetaileActivity.orderid;

    String phone;

//    public FMOrderDetaile(String orderid) {
//        this.orderid = orderid;
//    }

    /*一下数据的显示控件*/
    private TextView order_number, order_time, order_payment,
            order_contactname, order_phone, order_address, tv_order_distribution,
    tv_shopname;

    /*再来一单*/
    private Button but_more_one;

    UserInfo userInfo;

    private LinearLayout linearLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order_detaile, null);

        initData();
        if (!(userInfo.getUserInfo().equals(""))) {
            Register register = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), Register.class);

            phone = register.getMsg().getPhone();
        }


        getOrderDetaile(orderid, phone);

        //pullToRefreshView.setOnHeaderRefreshListener(this);
        //pullToRefreshView.setOnFooterRefreshListener(this);
        //pullToRefreshView.setLastUpdated(new Date().toLocaleString());

        return view;
    }

    public static FMOrderDetaile newInstance(String name) {
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        FMOrderDetaile fragment = new FMOrderDetaile();
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * 组建  初始化
     */
    public void initData() {

        userInfo = new UserInfo(getActivity().getApplication());
        //pullToRefreshView = (PullToRefreshView) view.findViewById(R.id.ptrv_order_detaile);
        myListView = (MyListView) view.findViewById(R.id.mlv_order_detaile);

        order_number = (TextView) view.findViewById(R.id.tv_order_number);
        order_time = (TextView) view.findViewById(R.id.tv_order_time);
        order_payment = (TextView) view.findViewById(R.id.tv_order_payment);
        order_contactname = (TextView) view.findViewById(R.id.tv_order_contactname);
        order_phone = (TextView) view.findViewById(R.id.tv_order_phone);
        order_address = (TextView) view.findViewById(R.id.tv_order_address);
        tv_order_distribution = (TextView) view.findViewById(R.id.tv_order_distribution);

        tv_shopname = (TextView) view.findViewById(R.id.tv_order_detailed_shopname);

        linearLayout = (LinearLayout) view.findViewById(R.id.lin_order_include);
        linearLayout.setVisibility(View.GONE);

        but_more_one = (Button) view.findViewById(R.id.but_more_one);
        but_more_one.setOnClickListener(this);

    }

    /**
     * 获取订单详细信息
     *
     * @param orderid
     */
    public void getOrderDetaile(String orderid, String phone) {
        String PATH = "";
        if (userInfo.getCode().equals("0")) {
            PATH = HttpPath.PATH + HttpPath.ORDER_NEWDET + "orderid=" + orderid;
        } else {
            PATH = HttpPath.PATH + HttpPath.ORDER_NEWDET + "orderid=" + orderid +
                    "&logintype=phone" + "&loginphone=" + phone;
        }


        final RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("订单详情" + result);
                        OrderDetaile orderDetaile = GsonUtil.gsonIntance().gsonToBean(result, OrderDetaile.class);

                        //组件赋值
                        String dno = orderDetaile.getMsg().getDno();
                        String addtime = orderDetaile.getMsg().getAddtime();
                        String paystatusintro = orderDetaile.getMsg().getPaystatusintro();
                        String buyername = orderDetaile.getMsg().getBuyername();
                        String buyerphone = orderDetaile.getMsg().getBuyerphone();
                        String buyeraddress = orderDetaile.getMsg().getBuyeraddress();

                        String shopname = orderDetaile.getMsg().getShopname();

                        order_number.setText(dno);
                        order_time.setText(addtime);
                        order_payment.setText(paystatusintro);
                        order_contactname.setText(buyername);
                        order_phone.setText(buyerphone);
                        order_address.setText(buyeraddress);

                        tv_shopname.setText(""+shopname);

                        orderDetaile.getMsg().getGdlist();

                        odAdapter = new OrderDetaileAdapter(getActivity(), orderDetaile.getMsg().getGdlist());
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

//    @Override
//    public void onHeaderRefresh(PullToRefreshView view) {
//        pullToRefreshView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                pullToRefreshView.onHeaderRefreshComplete("更新于："
//                        + Calendar.getInstance().getTime().toLocaleString());
//                pullToRefreshView.onHeaderRefreshComplete();
//
//                if (userInfo.getCode().equals("0")) {
//                    getOrderDetaile(orderid, "");
//                } else {
//                    getOrderDetaile(orderid, phone);
//                }
//
//
//                //更新
//                Toast.makeText(getActivity(), "刷新成功!", Toast.LENGTH_SHORT).show();
//            }
//
//        }, 3000);
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_more_one:
                //再来一单 直接跳转到商店
                //startActivity(new Intent(getActivity(), ShopActivity.class));
                break;

            default:
                break;
        }
    }

//    @Override
//    public void onFooterRefresh(PullToRefreshView view) {
//        pullToRefreshView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                pullToRefreshView.onFooterRefreshComplete();
//                //Toast.makeText(getActivity(), "加载更多数据", Toast.LENGTH_SHORT).show();
//            }
//
//        }, 1000);
//    }
}