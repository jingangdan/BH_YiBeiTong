package com.bh.yibeitong.ui.percen;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bh.yibeitong.Interface.OnItemClickListener;
import com.bh.yibeitong.Interface.OnItemLongClickListener;
import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.Register;
import com.bh.yibeitong.bean.percen.PayLog;
import com.bh.yibeitong.utils.BaseRecyclerViewHolder;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.UserInfo;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by jingang on 2017/7/5.
 * 余额明细
 */

public class PayLogActivity extends BaseTextActivity implements SwipeRefreshLayout.OnRefreshListener {
    /*接口地址*/
    private String PATH = "";

    /*本地轻量缓存*/
    private UserInfo userInfo;
    private String uid, pwd, phone, jingang;

    /*UI显示数据*/
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private PayLogAdapter payLogAdapter;
    private LinearLayoutManager mLayoutManager;


    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_paylog);

    }

    @Override
    protected void initWidght() {
        super.initWidght();

        setTitleName("余额明细");
        setTitleBack(true, 0);

        initData();

    }

    /**/
    public void initData() {
        userInfo = new UserInfo(getApplication());
        jingang = userInfo.getCode();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.sr_paylog);
        recyclerView = (RecyclerView) findViewById(R.id.rv_paylog);
        swipeRefreshLayout.setOnRefreshListener(this);

        mLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(mLayoutManager);

        //调整SwipeRefreshLayout的位置
        swipeRefreshLayout.setProgressViewOffset(false,
                0,
                (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        24,
                        getResources().getDisplayMetrics()));

        isLoginOrLogintype();


    }

    /*接口参数的获取*/
    public void isLoginOrLogintype() {
        if (!(userInfo.getUserInfo().equals(""))) {
            Register register = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), Register.class);
            uid = register.getMsg().getUid();
            phone = register.getMsg().getPhone();
            if (!(userInfo.getPwd().equals(""))) {
                pwd = userInfo.getPwd();
            }

            if (jingang.equals("0")) {
                getPayLog(uid, pwd);

            } else {
                getPayLog("phone", phone);
            }
        } else {
            toast("未登录");
        }
    }

    /**
     * 余额明细
     *
     * @param uid
     * @param pwd
     */
    public void getPayLog(String uid, String pwd) {
        if (jingang.equals("0")) {
            PATH = HttpPath.PATH + HttpPath.PAY_LOG +
                    "uid=" + uid + "&pwd=" + pwd;
        } else {
            PATH = HttpPath.PATH + HttpPath.PAY_LOG +
                    "logintype=" + uid + "&loginphone=" + pwd;
        }

        System.out.println("余额明细" + PATH);

        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        swipeRefreshLayout.setRefreshing(false);
                        System.out.println("余额明细" + result);

                        PayLog payLog = GsonUtil.gsonIntance().gsonToBean(result, PayLog.class);
                        payLogAdapter = new PayLogAdapter(PayLogActivity.this, payLog.getMsg());
                        recyclerView.setAdapter(payLogAdapter);

                        /**/
                        payLogAdapter.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

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
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    public void onRefresh() {
        payLogAdapter.notifyDataSetChanged();
        isLoginOrLogintype();
    }

    /**/
    class PayLogAdapter extends RecyclerView.Adapter<PayLogAdapter.MyViewHolder> {

        private OnItemClickListener mOnItemClickListener;
        private OnItemLongClickListener mOnItemLongClickListener;

        public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
            this.mOnItemClickListener = mOnItemClickListener;
        }

        public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
            this.mOnItemLongClickListener = mOnItemLongClickListener;
        }

        private Context mContext;
        private List<PayLog.MsgBean> msgBeanList;

        public PayLogAdapter(Context mContext, List<PayLog.MsgBean> msgBeanList) {
            this.mContext = mContext;
            this.msgBeanList = msgBeanList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            MyViewHolder vh = new MyViewHolder(LayoutInflater.from(
                    PayLogActivity.this).inflate(R.layout.item_paylog, parent,
                    false));
            return vh;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            if (mOnItemClickListener != null) {
                //为ItemView设置监听器
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition(); // 1
                        mOnItemClickListener.onItemClick(holder.itemView, position); // 2
                    }
                });
            }
            if (mOnItemLongClickListener != null) {
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int position = holder.getLayoutPosition();
                        mOnItemLongClickListener.onItemLongClick(holder.itemView, position);
                        //返回true 表示消耗了事件 事件不会继续传递
                        return true;
                    }
                });
            }

            String title = msgBeanList.get(position).getTitle();
            String addtime = msgBeanList.get(position).getAddtime();
            String result = msgBeanList.get(position).getResult();

            String addtype = msgBeanList.get(position).getAddtype();
            if(addtype.equals("1")){
                //增加
                holder.tv_result.setText("+" + result);
                holder.tv_result.setTextColor(Color.rgb(192,220,114));

            }else if(addtype.equals("2")){
                //减少
                holder.tv_result.setText("-" + result);
                holder.tv_result.setTextColor(Color.rgb(216,217,217));
            }

            holder.tv_title.setText("" + title);
            holder.tv_addtime.setText("" + addtime);


        }

        @Override
        public int getItemCount() {
            return msgBeanList.size();
        }

        class MyViewHolder extends BaseRecyclerViewHolder {

            TextView tv_title, tv_addtime, tv_result;

            public MyViewHolder(View view) {
                super(view);
                tv_title = (TextView) view.findViewById(R.id.tv_item_paylog_title);
                tv_addtime = (TextView) view.findViewById(R.id.tv_item_paylog_addtime);
                tv_result = (TextView) view.findViewById(R.id.tv_item_paylog_result);
            }
        }
    }

}
