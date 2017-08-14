package com.bh.yibeitong.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bh.yibeitong.R;
import com.bh.yibeitong.adapter.OrderStateAdapter;
import com.bh.yibeitong.bean.OrderDetaile;
import com.bh.yibeitong.bean.Register;
import com.bh.yibeitong.refresh.MyListView;
import com.bh.yibeitong.refresh.PullToRefreshView;
import com.bh.yibeitong.ui.OrderActivity;
import com.bh.yibeitong.ui.OrderDetaileActivity;
import com.bh.yibeitong.ui.PayActivity;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.UserInfo;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by jingang on 2016/11/25.
 * 订单状态
 */

public class FMOrderState extends Fragment implements PullToRefreshView.OnHeaderRefreshListener,
        PullToRefreshView.OnFooterRefreshListener {
    private View view;

    //滑动
    private PullToRefreshView pullToRefreshView;
    private MyListView myListView;
    private OrderStateAdapter osAdapter;

    UserInfo userInfo;
    String phone;

    String orderid = OrderDetaileActivity.orderid;
    String status = OrderDetaileActivity.status;

    /*public FMOrderState(String orderid) {
        this.orderid = orderid;
    }*/

    //继续支付
    private Button cPay;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order_state, null);
        initData();

        if(!(userInfo.getUserInfo().equals(""))){
            Register register = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), Register.class);

            phone = register.getMsg().getPhone();
        }
        if(userInfo.getCode().equals("0")){
            getOrderDetaile(orderid, "");
        }else{
            getOrderDetaile(orderid, phone);
        }


        pullToRefreshView.setOnHeaderRefreshListener(this);
        pullToRefreshView.setOnFooterRefreshListener(this);
        pullToRefreshView.setLastUpdated(new Date().toLocaleString());

        return view;
    }
    public static FMOrderState newInstance(String name) {
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        FMOrderState fragment = new FMOrderState();
        fragment.setArguments(bundle);
        return fragment;
    }


    /**
     * 组件 初始化
     */
    public void initData() {

        System.out.println("订单状态status="+status);
        userInfo = new UserInfo(getActivity().getApplication());
        pullToRefreshView = (PullToRefreshView) view.findViewById(R.id.ptrv_order_state);
        myListView = (MyListView) view.findViewById(R.id.mlv_order_state);

        cPay = (Button) view.findViewById(R.id.but_continue_pay);

    }

    /**
     * 获取订单详细信息
     *
     * @param orderid
     */
    public void getOrderDetaile(final String orderid, String phone) {
        String PATH = "";
        if(userInfo.getCode().equals("0")){
            PATH = HttpPath.PATH + HttpPath.ORDER_NEWDET + "orderid=" + orderid;
        }else{
            PATH = HttpPath.PATH + HttpPath.ORDER_NEWDET + "orderid=" + orderid+
            "&logintype=phone"+"&loginphone="+phone;
        }


        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("订单详情" + result);

                        OrderDetaile orderDetaile = GsonUtil.gsonIntance().gsonToBean(result, OrderDetaile.class);

                        final String dno = orderDetaile.getMsg().getDno();
                        //final String shopcost = orderDetaile.getMsg().getShopcost();
                        final String orderids = orderDetaile.getMsg().getId();

                        final String shopcost = orderDetaile.getMsg().getAllcost();

                        osAdapter = new OrderStateAdapter(getActivity(), orderDetaile.getMsg().getStatuslist());
                        myListView.setAdapter(osAdapter);

                        //判断支付情况
                        if(orderDetaile.getMsg().getPaystatus().toString().equals("0")){
                            //未支付
                            cPay.setVisibility(View.VISIBLE);
                            cPay.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(getActivity(), PayActivity.class);
                                    //传值
                                    intent.putExtra("dno", dno);
                                    intent.putExtra("shopcost", shopcost);
                                    intent.putExtra("orderid", orderids);
                                    intent.putExtra("type", "order");

                                    startActivity(intent);
                                }
                            });
                        }

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

   /* @Override
    public void onFooterRefresh(PullToRefreshView view) {
        pullToRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
            //暂时不用上拉加载
                pullToRefreshView.onFooterRefreshComplete();
                Toast.makeText(getActivity(), "加载更多数据", Toast.LENGTH_SHORT).show();
            }

        }, 3000);
    }*/

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        pullToRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                pullToRefreshView.onHeaderRefreshComplete("更新于："
                        + Calendar.getInstance().getTime().toLocaleString());
                pullToRefreshView.onHeaderRefreshComplete();
                if(userInfo.getCode().equals("0")){
                    getOrderDetaile(orderid, "");
                }else{
                    getOrderDetaile(orderid, phone);
                }


                //更新
                //Toast.makeText(getActivity(), "刷新成功!", Toast.LENGTH_SHORT).show();
            }

        }, 3000);
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        pullToRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                pullToRefreshView.onFooterRefreshComplete();
                //Toast.makeText(getActivity(), "加载更多数据", Toast.LENGTH_SHORT).show();
            }

        }, 1000);
    }
}
