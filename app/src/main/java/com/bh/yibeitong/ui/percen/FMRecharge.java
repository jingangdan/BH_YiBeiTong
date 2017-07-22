package com.bh.yibeitong.ui.percen;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bh.yibeitong.R;
import com.bh.yibeitong.bean.Register;
import com.bh.yibeitong.bean.homepage.Recharge;
import com.bh.yibeitong.ui.PayActivity;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.UserInfo;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;


/**
 * Created by jingang on 2017/7/5.
 * 我要充值（充值）
 */

public class FMRecharge extends Fragment implements View.OnClickListener {
    private View view;

    /*接口地址*/
    private String PATH = "";

    /*本地轻量缓存*/
    private UserInfo userInfo;
    private String jingang, uid, pwd, phone;

    /*充值列表显示*/
    private GridView gridView;
    private RechargeAdapter rechargeAdapter;
    private TextView tv_hint;

    /*GridView选中状态*/
    private boolean isSelect = false;
    private LinearLayout linearLayout;
    private String cost = "", send = "";

    /*充值金额*/
    private EditText mEditText;

    /*立即充值*/
    private Button but_recharge;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recharge, null);
        initData();

        return view;
    }

    public static FMRecharge newInstance(String name) {
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        FMRecharge fragment = new FMRecharge();
        fragment.setArguments(bundle);
        return fragment;
    }

    /**/
    public void initData() {

        gridView = (GridView) view.findViewById(R.id.gv_recharge);
        tv_hint = (TextView) view.findViewById(R.id.tv_item_recharge_hint);
        linearLayout = (LinearLayout) view.findViewById(R.id.lin_recharge_hint);

        mEditText = (EditText) view.findViewById(R.id.et_recharge);
        but_recharge = (Button) view.findViewById(R.id.but_recharge);
        but_recharge.setOnClickListener(this);

        userInfo = new UserInfo(getActivity().getApplication());
        jingang = userInfo.getCode();//登录方式

        if (!(userInfo.getUserInfo().equals(""))) {
            Register register = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), Register.class);
            uid = register.getMsg().getUid();
            phone = register.getMsg().getPhone();
            if (!(userInfo.getPwd().equals(""))) {
                pwd = userInfo.getPwd();
            }

            if (jingang.equals("0")) {
                getMemcard(uid, pwd);
            } else {
                getMemcard("phone", phone);
            }
        } else {
            //Toast.makeText(getActivity(), "未登录", Toast.LENGTH_SHORT).show();
            //toast("未登录");
        }


    }

    /**
     * 充值列表
     *
     * @param uid
     * @param pwd
     */
    public void getMemcard(String uid, String pwd) {
        if (jingang.equals("0")) {
            //账号密码登录
            PATH = HttpPath.PATH + HttpPath.MEMCARD +
                    "uid=" + uid + "&pwd=" + pwd;
        } else {
            //快捷登录
            PATH = HttpPath.PATH + HttpPath.MEMCARD +
                    "logintype=" + uid + "&loginphone=" + pwd;
        }

        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("充值列表" + result);
                        final Recharge recharge = GsonUtil.gsonIntance().gsonToBean(result, Recharge.class);

                        rechargeAdapter = new RechargeAdapter(getActivity(), recharge.getMsg().getRechargelist());
                        gridView.setAdapter(rechargeAdapter);

                        /*列表点击操作*/
                        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                if (isSelect) {
                                    linearLayout.setVisibility(View.GONE);
                                    rechargeAdapter.changeSelected(-1);
                                    isSelect = false;
                                } else {
                                    linearLayout.setVisibility(View.VISIBLE);
                                    rechargeAdapter.changeSelected(i);

                                    cost = recharge.getMsg().getRechargelist().get(i).getCost();
                                    send = recharge.getMsg().getRechargelist().get(i).getSendcost();

                                }

                                tv_hint.setText("充值" + cost + "元赠送" + send + "元");

                                mEditText.setText("" + cost);

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.but_recharge:
                //立即充值
                if (mEditText.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "充值金额不可为空", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getActivity(), PayActivity.class);
                    intent.putExtra("dno", "余额充值");
                    intent.putExtra("shopcost", cost);
                    intent.putExtra("orderid", "");
                    intent.putExtra("type", "acount");
                    startActivity(intent);
                }


                break;

            default:
                break;
        }
    }

    /**/
    public class RechargeAdapter extends BaseAdapter {
        private Context mContext;
        private List<Recharge.MsgBean.RechargelistBean> rechargelistBeen;
        private int mSelect = -1;

        public RechargeAdapter(Context mContext, List<Recharge.MsgBean.RechargelistBean> rechargelistBeen) {
            this.mContext = mContext;
            this.rechargelistBeen = rechargelistBeen;
        }

        /**
         * 刷新方法
         *
         * @param positon
         */
        public void changeSelected(int positon) {
            if (positon != mSelect) {
                mSelect = positon;
                notifyDataSetChanged();
            }
        }


        @Override
        public int getCount() {
            return rechargelistBeen.size();
        }

        @Override
        public Object getItem(int i) {
            return rechargelistBeen.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder vh;
            if (view == null) {
                vh = new ViewHolder();
                view = LayoutInflater.from(mContext).inflate(R.layout.item_recharge, null);

                vh.tv_cost = (TextView) view.findViewById(R.id.tv_item_recharge_cost);
                vh.tv_send = (TextView) view.findViewById(R.id.tv_item_recharge_send);
                vh.tv_rmb = (TextView) view.findViewById(R.id.tv_item_recharge_rmb);
                vh.linearLayout = (LinearLayout) view.findViewById(R.id.lin_item_recharge);

                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }
            //ListView点击改变背景 文字
            if (mSelect == i) {
                vh.tv_rmb.setTextColor(Color.rgb(255, 157, 7));
                vh.tv_cost.setTextColor(Color.rgb(255, 157, 7));
                vh.tv_send.setTextColor(Color.rgb(255, 157, 7));
                vh.linearLayout.setBackgroundResource(R.drawable.kuang_style);
            } else if (mSelect == -1) {
                vh.tv_rmb.setTextColor(Color.rgb(128, 128, 128));
                vh.tv_cost.setTextColor(Color.rgb(51, 51, 51));
                vh.tv_send.setTextColor(Color.rgb(128, 128, 128));
                vh.linearLayout.setBackgroundResource(R.drawable.kuang_black);
            } else {
                vh.tv_rmb.setTextColor(Color.rgb(128, 128, 128));
                vh.tv_cost.setTextColor(Color.rgb(51, 51, 51));
                vh.tv_send.setTextColor(Color.rgb(128, 128, 128));
                vh.linearLayout.setBackgroundResource(R.drawable.kuang_black);
            }


            String cost = rechargelistBeen.get(i).getCost();
            String send = rechargelistBeen.get(i).getSendcost();

            vh.tv_cost.setText("" + cost);
            vh.tv_send.setText("送" + send + "元");
            return view;
        }

        public class ViewHolder {
            TextView tv_cost, tv_send, tv_rmb;
            LinearLayout linearLayout;

        }
    }
}
