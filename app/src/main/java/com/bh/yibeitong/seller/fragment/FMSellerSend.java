package com.bh.yibeitong.seller.fragment;

import android.content.Context;
import android.content.Intent;
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
import com.bh.yibeitong.seller.activity.SellerAppOneActivity;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.UserInfo;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by jingang on 2017/4/8.
 * 待发货（商家端 订单）
 */

public class FMSellerSend extends BaseFragment {

    private View view;

    String PATH = "";

    private ListView listView;
    private SellerOrderAdapter sellerOrderAdapter;
    private List<SellerOrder.MsgBean> msgBeen;

    /*本地轻量型缓存*/
    UserInfo userInfo;
    String uid, pwd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fm_seller_order, null);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String name = bundle.get("name").toString();
            System.out.println(name);
        }

        initData();
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void lazyLoad() {

    }

    public static FMSellerSend newInstance(String name) {
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        FMSellerSend fragment = new FMSellerSend();
        fragment.setArguments(bundle);
        return fragment;
    }


    public void initData() {
        userInfo = new UserInfo(getActivity().getApplication());

        listView = (ListView) view.findViewById(R.id.lv_seller_order);

        SellerLogin sellerLogin = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), SellerLogin.class);
        uid = sellerLogin.getMsg().getUid();

        pwd = userInfo.getPwd();

        waitsendSellerOrder(uid, pwd, "", "waitsend");

    }

    @Override
    public void onClick(View view) {

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            System.out.println("in FMSellerSend");
            waitsendSellerOrder(uid, pwd, "", "waitsend");
        }else{
            System.out.println("move FMSellerSend");

            /*userInfo = new UserInfo(getActivity().getApplication());

            SellerLogin sellerLogin = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), SellerLogin.class);
            uid = sellerLogin.getMsg().getUid();

            pwd = userInfo.getPwd();

            waitsendSellerOrder(uid, pwd, "", "waitsend");*/

        }
    }

    /**
     * @param uid
     * @param pwd
     * @param searchday 查询日期
     * @param gettype   订单类型 wait waitsend is_send
     */
    public void waitsendSellerOrder(final String uid, final String pwd, String searchday, String gettype) {
        PATH = HttpPath.path + HttpPath.APP_ORDER +
                "uid=" + uid + "&pwd=" + pwd + "&searchday=" + searchday + "&gettype=" + gettype;

        System.out.println("" + PATH);
        RequestParams params = new RequestParams(PATH);

        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("商家订单 待发货 = " + result);
                        final SellerOrder sellerOrder = GsonUtil.gsonIntance().gsonToBean(result, SellerOrder.class);
                        msgBeen = sellerOrder.getMsg();

                        sellerOrderAdapter = new SellerOrderAdapter(getActivity(), msgBeen);
                        listView.setAdapter(sellerOrderAdapter);
                        /*点击*/
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent = new Intent(getActivity(), SellerAppOneActivity.class);
                                intent.putExtra("uid", uid);
                                intent.putExtra("pwd", pwd);
                                intent.putExtra("orderid", sellerOrder.getMsg().get(i).getId());
                                startActivity(intent);
                            }
                        });



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
            final SellerOrderAdapter.ViewHolder vh;
            if (view == null) {
                vh = new SellerOrderAdapter.ViewHolder();
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

                vh.send = (Button) view.findViewById(R.id.but_item_so_send);

                view.setTag(vh);

            } else {
                vh = (SellerOrderAdapter.ViewHolder) view.getTag();
            }

            vh.lin_wait.setVisibility(View.INVISIBLE);
            vh.lin_waitsend.setVisibility(View.VISIBLE);

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

            /*确认发货*/
            vh.send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    vh.send.setClickable(false);
                    final String PATH = HttpPath.PATH + HttpPath.APP_ORDER_CONTROL + "" +
                            "uid=" + uid + "&pwd=" + pwd + "&orderid=" + orderid + "&dostring=send";

                    RequestParams params = new RequestParams(PATH);
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            vh.send.setClickable(true);

                            System.out.println("确认收货" + PATH);
                            System.out.println("确认收货" + result);

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


            return view;
        }

        public class ViewHolder {
            TextView posttime, buyeraddress, buyername, dno, payresult, allcost;
            Button ok, no, send;

            LinearLayout lin_wait, lin_waitsend;

        }
    }

}
