package com.bh.yibeitong.seller.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseFragment;
import com.bh.yibeitong.bean.seller.SellerLogin;
import com.bh.yibeitong.bean.seller.SellerOrder;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.UserInfo;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by jingang on 2017/4/8.
 * 待确认 （商家端 订单）
 */

public class FMSellerOK extends BaseFragment {
    private View view;

    /*接口地址*/
    private String PATH = "";

    /*本地轻量型缓存*/
    UserInfo userInfo;
    String uid, pwd;

    /*UI显示*/
    private ListView listView;
    private SellerOrderAdapter sellerOrderAdapter;
    private List<SellerOrder.MsgBean> msgBeen;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fm_seller_order, null);

        initData();

        return view;
    }

    @Override
    protected void lazyLoad() {

    }

    public static FMSellerOK newInstance(String sellerLogin) {
        Bundle bundle = new Bundle();
        bundle.putString("sellerLogin", sellerLogin);
        FMSellerOK fragment = new FMSellerOK();
        fragment.setArguments(bundle);
        return fragment;
    }

    /*组件初始化*/
    public void initData() {

        userInfo = new UserInfo(getActivity().getApplication());

        listView = (ListView) view.findViewById(R.id.lv_seller_order);
        listView.setOnItemClickListener(new ListViewClickListener());


        SellerLogin sellerLogin = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), SellerLogin.class);
        uid = sellerLogin.getMsg().getUid();

        pwd = userInfo.getPwd();

        getSellerOrder(uid, pwd, "", "wait");

        Bundle bundle = getArguments();
        if (bundle != null) {
            String name = bundle.get("sellerLogin").toString();
            System.out.println("ok = " + name);

        }


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            System.out.println("in FMSellerOK");
            getSellerOrder(uid, pwd, "", "wait");

        } else {
            System.out.println("move FMSellerOK");

        }

    }

    @Override
    public void onClick(View view) {

    }

    /*listView点击事件 查看订单详情*/
    public class ListViewClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            System.out.println("点击listView" + i);

        }
    }

    /**
     * 获取商家订单
     * uid	是	string	用户名
     * pwd	是	string	密码
     * searchday	否	string	查询日期
     * gettype	否	string	订单类型 wait waitsend is_send
     */
    public void getSellerOrder(String uid, String pwd, String searchday, String gettype) {
        PATH = HttpPath.PATH + HttpPath.APP_ORDER +
                "uid=" + uid + "&pwd=" + pwd + "&searchday=" + searchday + "&gettype=" + gettype;

        RequestParams params = new RequestParams(PATH);

        System.out.println("" + PATH);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("商家端订单 待确认" + result);
                SellerOrder sellerOrder = GsonUtil.gsonIntance().gsonToBean(result, SellerOrder.class);

                msgBeen = sellerOrder.getMsg();
                sellerOrderAdapter = new SellerOrderAdapter(getActivity(), msgBeen);
                listView.setAdapter(sellerOrderAdapter);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                toast("网络请求错误，错误原因：" + ex);

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    /*ListView适配器*/
    public class SellerOrderAdapter extends BaseAdapter {
        private Context mContext;
        private List<SellerOrder.MsgBean> msgBeanList;

        public SellerOrderAdapter(Context mContext, List<SellerOrder.MsgBean> msgBeanList) {
            this.mContext = mContext;
            this.msgBeanList = msgBeanList;
        }

        @Override
        public int getCount() {
            return msgBeanList.size();
        }

        @Override
        public Object getItem(int i) {
            return msgBeanList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final ViewHolder vh;
            if (view == null) {
                vh = new ViewHolder();
                view = LayoutInflater.from(mContext).inflate(R.layout.item_seller_order, null);

                vh.posttime = (TextView) view.findViewById(R.id.tv_item_so_posttime);
                vh.buyeraddress = (TextView) view.findViewById(R.id.tv_item_so_buyeraddress);
                vh.buyername = (TextView) view.findViewById(R.id.tv_item_so_buyername);
                vh.dno = (TextView) view.findViewById(R.id.tv_item_so_dno);
                vh.payresult = (TextView) view.findViewById(R.id.tv_item_so_payresult);
                vh.allcost = (TextView) view.findViewById(R.id.tv_item_so_allcost);

                vh.ok = (Button) view.findViewById(R.id.but_item_so_ok);
                vh.no = (Button) view.findViewById(R.id.but_item_so_no);

                vh.lin_wait = (LinearLayout) view.findViewById(R.id.lin_item_so_wait);
                vh.lin_waitsend = (LinearLayout) view.findViewById(R.id.lin_item_so_waitsend);

                view.setTag(vh);

            } else {
                vh = (ViewHolder) view.getTag();
            }
            vh.lin_wait.setVisibility(View.VISIBLE);
            vh.lin_waitsend.setVisibility(View.INVISIBLE);

            final String orderid = msgBeanList.get(i).getId();

            String posttime = msgBeanList.get(i).getPosttime();
            String buyeraddress = msgBeanList.get(i).getBuyeraddress();
            String buyername = msgBeanList.get(i).getBuyername();
            String dno = msgBeanList.get(i).getDno();
            String payresult = msgBeanList.get(i).getPayresult();
            String allcost = msgBeanList.get(i).getAllcost();

            vh.posttime.setText("送达时间：" + posttime);

            vh.buyeraddress.setText("" + buyeraddress);

            vh.buyername.setText("" + buyername);
            vh.dno.setText("" + dno);

            vh.payresult.setText("" + payresult);
            vh.allcost.setText("￥" + allcost);

            /*确认订单*/
            vh.ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    vh.ok.setClickable(false);
                    final String PATH = HttpPath.PATH + HttpPath.APP_ORDER_CONTROL + "" +
                            "uid=" + uid + "&pwd=" + pwd + "&orderid=" + orderid + "&dostring=domake";

                    RequestParams params = new RequestParams(PATH);
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            vh.ok.setClickable(true);

                            System.out.println("确认订单" + PATH);
                            System.out.println("确认订单" + result);

                            msgBeanList.remove(i);

                            sellerOrderAdapter.notifyDataSetChanged();

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
            });

            /*取消订单*/
            vh.no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    vh.no.setClickable(false);

                    final String PATH = HttpPath.PATH + HttpPath.APP_ORDER_CONTROL + "" +
                            "uid=" + uid + "&pwd=" + pwd + "&orderid=" + orderid + "&dostring=unmake";

                    RequestParams params = new RequestParams(PATH);
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            vh.no.setClickable(true);

                            System.out.println("取消订单" + PATH);
                            System.out.println("取消订单" + result);

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
            });

            return view;
        }

        public class ViewHolder {
            TextView posttime, buyeraddress, buyername, dno, payresult, allcost;
            Button ok, no;

            LinearLayout lin_wait, lin_waitsend;

        }


    }
}
