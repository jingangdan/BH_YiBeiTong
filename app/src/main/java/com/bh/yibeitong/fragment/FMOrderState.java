package com.bh.yibeitong.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bh.yibeitong.R;
import com.bh.yibeitong.adapter.OrderStateAdapter;
import com.bh.yibeitong.bean.OrderDetaile;
import com.bh.yibeitong.bean.Register;
import com.bh.yibeitong.refresh.MyListView;
import com.bh.yibeitong.refresh.PullToRefreshView;
import com.bh.yibeitong.ui.PayActivity;
import com.bh.yibeitong.ui.ShopNewActivity;
import com.bh.yibeitong.ui.order.OrderBackLogActivity;
import com.bh.yibeitong.ui.order.OrderCommentActivity;
import com.bh.yibeitong.ui.order.OrderControlActivity;
import com.bh.yibeitong.ui.order.OrderDetaileActivity;
import com.bh.yibeitong.utils.CodeUtils;
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

public class FMOrderState extends Fragment implements
        PullToRefreshView.OnHeaderRefreshListener,
        PullToRefreshView.OnFooterRefreshListener {
    private View view;

    //滑动
    private PullToRefreshView pullToRefreshView;
    private MyListView myListView;
    private OrderStateAdapter osAdapter;

    UserInfo userInfo;
    String phone, uid, pwd;

    String orderid = OrderDetaileActivity.orderid;
    //String status = OrderDetaileActivity.status;
    private String status = "", allcost = "", pstype = "", is_reback = "";

    //继续支付
    private Button cPay;

    /*接口地址*/
    private String PATH = "";

    /*页面传值*/
    private Intent intent;
    private String dno = "", orderids = "", shopcost = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order_state, null);
        initData();

        if(!(userInfo.getUserInfo().equals(""))){
            Register register = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), Register.class);

            phone = register.getMsg().getPhone();
            uid = register.getMsg().getUid();
        }

        pwd = userInfo.getPwd();

        if(userInfo.getCode().equals("0")){
            getOrderDetaile(uid, pwd, orderid);
        } else if (userInfo.getCode().equals("1")) {
            getOrderDetaile("phone", phone, orderid);
        } else {
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

        userInfo = new UserInfo(getActivity().getApplication());
        pullToRefreshView = (PullToRefreshView) view.findViewById(R.id.ptrv_order_state);
        myListView = (MyListView) view.findViewById(R.id.mlv_order_state);

        cPay = (Button) view.findViewById(R.id.but_continue_pay);

        cPay.setOnClickListener(new OnClickStatus());


    }

    public void setResult() {
        intent = new Intent();
        getActivity().setResult(CodeUtils.REQUEST_CODER_ORDER_STATE, intent);

        getActivity().finish();
    }

    /**
     * 订单状态
     *
     * @param status
     */
    public void getStatus(String status) {
        if (status.equals("0")) {
            //新订单（未支付）
            cPay.setText("继续支付");

        } else if (status.equals("1")) {
            //待发货（支付已完成）
            if (is_reback.equals("0")) {
                cPay.setText("取消订单");
            } else if (is_reback.equals("1")) {
                cPay.setText("查看退款详情");
            } else {
            }


        } else if (status.equals("2")) {
            //待确认
            cPay.setText("确认收货");

        } else if (status.equals("3")) {
            //已完成（已确认收货） 订单取消
            cPay.setText("评价订单");

        } else if (status.equals("4") || status.equals("5")) {
            //已完成（已确认收货） 订单取消
            cPay.setText("再来一单");

        } else {
            //错误
        }
    }

    /*根据订单状态 判断点击执行的动作*/
    public class OnClickStatus implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            System.out.println("status = " + status);
            if (status.equals("0")) {
                //新订单（未支付）
                System.out.println("继续支付");
                intent = new Intent(getActivity(), PayActivity.class);
                //传值
                intent.putExtra("dno", dno);
                intent.putExtra("shopcost", shopcost);
                intent.putExtra("orderid", orderids);
                intent.putExtra("type", "order");

                startActivityForResult(intent, CodeUtils.REQUEST_CODER_ORDER_STATE);

            } else if (status.equals("1")) {
                //待发货（支付已完成）
                System.out.println("申请退款");

                if (is_reback.equals("0")) {
                    if (userInfo.getCode().equals("0")) {
                        intent = new Intent(getActivity(), OrderControlActivity.class);
                        intent.putExtra("uid", uid);
                        intent.putExtra("pwd", pwd);
                        intent.putExtra("orderid", orderids);
                        intent.putExtra("allcost", allcost);
                        intent.putExtra("pstype", pstype);
                        startActivityForResult(intent, CodeUtils.REQUEST_CODER_ORDER_STATE);
                    } else {
                        intent = new Intent(getActivity(), OrderControlActivity.class);
                        intent.putExtra("uid", "phone");
                        intent.putExtra("pwd", phone);
                        intent.putExtra("orderid", orderids);
                        intent.putExtra("allcost", allcost);
                        intent.putExtra("pstype", pstype);
                        startActivityForResult(intent, CodeUtils.REQUEST_CODER_ORDER_STATE);
                    }
                } else if (is_reback.equals("1")) {
                    //查看退款详情

                    if (userInfo.getCode().equals("0")) {
                        intent = new Intent(getActivity(), OrderBackLogActivity.class);
                        intent.putExtra("uid", uid);
                        intent.putExtra("pwd", pwd);
                        intent.putExtra("orderid", orderids);
                        intent.putExtra("allcost", allcost);
                        intent.putExtra("pstype", pstype);
                        startActivityForResult(intent, CodeUtils.REQUEST_CODER_ORDER_STATE);
                    } else {
                        intent = new Intent(getActivity(), OrderBackLogActivity.class);
                        intent.putExtra("uid", "phone");
                        intent.putExtra("pwd", phone);
                        intent.putExtra("orderid", orderids);
                        intent.putExtra("allcost", allcost);
                        intent.putExtra("pstype", pstype);
                        startActivityForResult(intent, CodeUtils.REQUEST_CODER_ORDER_STATE);
                    }


                } else {
                }


            } else if (status.equals("2")) {
                //待确认
                System.out.println("确认收货");

            } else if (status.equals("3")) {
                //已完成（已确认收货） 订单取消
                System.out.println("评价订单");

                intent = new Intent(getActivity(), OrderCommentActivity.class);
                intent.putExtra("orderid", orderids);
                startActivityForResult(intent, CodeUtils.REQUEST_CODER_ORDER_STATE);

            } else if (status.equals("4") || status.equals("5")) {
                //已完成（已确认收货） 订单取消
                cPay.setText("再来一单");

                intent = new Intent(getActivity(), ShopNewActivity.class);
                intent.putExtra("shopid", FMHomePage.shopid);
                intent.putExtra("shopname", FMHomePage.shopName);
                intent.putExtra("startTime", FMHomePage.startTime);
                intent.putExtra("mapphone", FMHomePage.mapphone);
                intent.putExtra("address", FMHomePage.address);

                intent.putExtra("lat", FMHomePage.latitude);
                intent.putExtra("lng", FMHomePage.longtitude);
                startActivity(intent);


            } else {
                //错误
            }

        }
    }


    /**
     * 获取订单详细信息
     *
     * @param orderid
     */
    public void getOrderDetaile(String uid, String pwd, final String orderid) {

//        PATH = HttpPath.PATH + HttpPath.ORDER_NEWDET + "orderid=" + orderid;

        if (userInfo.getCode().equals("0")) {
            PATH = HttpPath.PATH + HttpPath.ORDER_NEWDET +
                    "uid=" + uid + "&pwd=" + pwd + "&orderid=" + orderid;
        } else {
            PATH = HttpPath.PATH + HttpPath.ORDER_NEWDET +
                    "logintype=" + uid + "&loginphone=" + phone + "&orderid=" + orderid;
        }

        System.out.println("订单详情" + PATH);

        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("订单详情" + result);

                        OrderDetaile orderDetaile = GsonUtil.gsonIntance().gsonToBean(result, OrderDetaile.class);

                        dno = orderDetaile.getMsg().getDno();
                        orderids = orderDetaile.getMsg().getId();
                        shopcost = orderDetaile.getMsg().getAllcost();
                        status = orderDetaile.getMsg().getStatus();
                        is_reback = orderDetaile.getMsg().getIs_reback();

                        allcost = orderDetaile.getMsg().getAllcost();
                        pstype = orderDetaile.getMsg().getPstype();

                        osAdapter = new OrderStateAdapter(getActivity(), orderDetaile.getMsg().getStatuslist());
                        myListView.setAdapter(osAdapter);

                        getStatus(status);

                        //判断支付情况
//                        if(orderDetaile.getMsg().getPaystatus().toString().equals("0")){
//                            //未支付
//                            cPay.setVisibility(View.VISIBLE);
//                            cPay.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    Intent intent = new Intent(getActivity(), PayActivity.class);
//                                    //传值
//                                    intent.putExtra("dno", dno);
//                                    intent.putExtra("shopcost", shopcost);
//                                    intent.putExtra("orderid", orderids);
//                                    intent.putExtra("type", "order");
//
//                                    startActivity(intent);
//                                }
//                            });
//                        }

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
    public void onHeaderRefresh(PullToRefreshView view) {
        pullToRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                pullToRefreshView.onHeaderRefreshComplete("更新于："
                        + Calendar.getInstance().getTime().toLocaleString());
                pullToRefreshView.onHeaderRefreshComplete();
                if(userInfo.getCode().equals("0")){
                    getOrderDetaile(orderid, pwd, orderid);
                } else if (userInfo.getCode().equals("1")) {
                    getOrderDetaile("phone", phone, orderid);
                } else {
                }


            }

        }, 3000);
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        pullToRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                pullToRefreshView.onFooterRefreshComplete();
            }

        }, 1000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CodeUtils.REQUEST_CODER_ORDER_STATE) {
            if (resultCode == CodeUtils.REUEST_CODE_PAY
                    || resultCode == CodeUtils.REQUEST_CODE_ORDER_COMMENT
                    || resultCode == CodeUtils.REQUEST_CODE_ORDER_CONTROL) {

                if (userInfo.getCode().equals("0")) {
                    getOrderDetaile(uid, pwd, orderid);
                } else if (userInfo.getCode().equals("1")) {
                    getOrderDetaile("phone", phone, orderid);
                } else {
                }

                setResult();
            }
        }
    }
}
