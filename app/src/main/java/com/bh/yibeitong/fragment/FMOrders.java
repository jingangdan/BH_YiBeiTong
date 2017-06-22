package com.bh.yibeitong.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bh.yibeitong.R;
import com.bh.yibeitong.actitvity.MainActivity;
import com.bh.yibeitong.adapter.OrderAdapter;
import com.bh.yibeitong.base.BaseFragment;
import com.bh.yibeitong.bean.GoodsIndex;
import com.bh.yibeitong.bean.Order;
import com.bh.yibeitong.bean.Register;
import com.bh.yibeitong.refresh.MyListView;
import com.bh.yibeitong.refresh.PullToRefreshView;
import com.bh.yibeitong.ui.OrderDetaileActivity;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.UserInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jingang on 2016/10/18.
 * 我的订单页
 */

public class FMOrders extends BaseFragment implements PullToRefreshView.OnHeaderRefreshListener,
        PullToRefreshView.OnFooterRefreshListener {
    private View view;

    private TextView tv_header_title;
    private ImageView iv_header_left, iv_header_right;

    private String login;

    public static String uid, pwd;

    public static String phone;

    private PullToRefreshView pullToRefreshView;
    private MyListView myListView;

    /*本地存储*/
    UserInfo userInfo;
    private String jingang;//有请我的大名！

    /*适配器*/
    private OrderAdapter orderAdapter;
    private List<Order.MsgBean> msgBeanList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("我的订单页 加载");
        view = inflater.inflate(R.layout.fragment_orders, container, false);

        initData();

        userInfo = new UserInfo(getActivity().getApplication());

        Gson gson = new Gson();
        Type listType = new TypeToken<List<GoodsIndex.MsgBean.ShopdetBean.PostdateBean>>() {
        }.getType();

        final List<GoodsIndex.MsgBean.ShopdetBean.PostdateBean> catefoodslist =
                gson.fromJson(userInfo.getPostData(), listType);

        //XXX初始化view的各控件
        lazyLoad();

        Bundle bundle = getArguments();
        //从activity传过来的Bundle
        if (bundle != null) {
            login = bundle.getString("login");
            System.out.println("主页传值：" + login);
            if(!(login.equals(""))){
           //if (!(login == null)) {
                Register register = GsonUtil.gsonIntance().gsonToBean(login, Register.class);
                phone = register.getMsg().getPhone();

                getOrder("phone", phone);

            }
        }

        if(!(userInfo.getUserInfo().equals(""))){
            Register register = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), Register.class);
            uid = register.getMsg().getUid();
            if(!(userInfo.getPwd().equals(""))){
                pwd = userInfo.getPwd();

                if(userInfo.getCode().equals("0")){
                    //System.out.println("我的验证码"+userInfo.getCode());
                    getOrder(uid, pwd);
                }else{
                    //System.out.println("我的手机号"+phone);
                    getOrder("phone", phone);
                }

            }
        }else{
            toast("未登录");
        }

        //响应pullToRefreshView上拉下拉事件

        pullToRefreshView.setOnHeaderRefreshListener(this);
        pullToRefreshView.setOnFooterRefreshListener(this);
        pullToRefreshView.setLastUpdated(new Date().toLocaleString());

        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&");

        } else {
            //相当于Fragment的onPause
            System.out.println("**************************");


        }
    }

    /**
     * 组件 初始化
     */

    public void initData() {
        tv_header_title = (TextView) view.findViewById(R.id.tv_header_title);
        iv_header_left = (ImageView) view.findViewById(R.id.iv_header_left);
        iv_header_right = (ImageView) view.findViewById(R.id.iv_header_right);

        tv_header_title.setText("我的订单");
        iv_header_left.setVisibility(View.INVISIBLE);

        iv_header_right.setOnClickListener(this);

        pullToRefreshView = (PullToRefreshView) view.findViewById(R.id.puToRefreshView_order);
        myListView = (MyListView) view.findViewById(R.id.myListView_order);

    }

    @Override
    protected void lazyLoad() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_header_right:
                //
                toast("消息");
                break;
        }

    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        pullToRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                pullToRefreshView.onFooterRefreshComplete();
                Toast.makeText(getActivity(), "加载更多数据", Toast.LENGTH_SHORT).show();
            }

        }, 3000);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        pullToRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                pullToRefreshView.onHeaderRefreshComplete("更新于："
                        + Calendar.getInstance().getTime().toLocaleString());
                pullToRefreshView.onHeaderRefreshComplete();

                /*判断用户登录状态*/
                jingang = userInfo.getLogin();
                if (jingang.equals("")) {
                    System.out.println("订单页 空" + jingang + "未登录");

                } else if (jingang.equals("0")) {
                    System.out.println("订单页" + jingang + "未登录");

                } else if (jingang.equals("1")) {
                    System.out.println("订单页" + jingang + "已登录");
                }

                if(userInfo.getCode().equals("0")){
                    getOrder(uid, pwd);
                }else{
                    getOrder("", pwd);
                }


                //更新
                Toast.makeText(getActivity(), "刷新成功!", Toast.LENGTH_SHORT).show();
            }

        }, 3000);
    }

    /**
     * 获取订单
     * @param uid
     * @param pwd
     */
    public void getOrder(String uid, String pwd) {
        String PATH = null;
        if(userInfo.getCode().equals("0")){
            PATH = HttpPath.PATH + HttpPath.ORDER_NEW +
                    "uid="+uid + "&pwd="+pwd;
        }else{
            PATH = HttpPath.PATH + HttpPath.ORDER_NEW +
                    "logintype="+uid + "&loginphone="+pwd;
        }


        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("订单列表" + result);

                        Order order = GsonUtil.gsonIntance().gsonToBean(result, Order.class);
                        msgBeanList = order.getMsg();

                        orderAdapter = new OrderAdapter(getActivity(), msgBeanList);
                        myListView.setAdapter(orderAdapter);

                        //订单详情
                        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //
                                Intent intent = new Intent(getActivity(), OrderDetaileActivity.class);
                                intent.putExtra("orderid", msgBeanList.get(position).getId());
                                startActivity(intent);
                            }
                        });

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        System.out.println("订单列表onError");
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
