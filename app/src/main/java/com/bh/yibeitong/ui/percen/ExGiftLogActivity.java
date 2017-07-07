package com.bh.yibeitong.ui.percen;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.Register;
import com.bh.yibeitong.bean.percen.ExGiftLog;
import com.bh.yibeitong.utils.BaseRecyclerViewHolder;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.UserInfo;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by jingang on 2016/12/7.
 * 兑换记录
 */

public class ExGiftLogActivity extends BaseTextActivity {
    /*接口地址*/
    private String PATH = "";

    /*本地轻量缓存*/
    private UserInfo userInfo;
    private String uid, pwd, phone, jingang;

    /*UI显示*/
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ExGiftLogAdapter exGiftLogAdapter;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_exgiftlog);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("兑换记录");
        setTitleBack(true,0);

        initData();
    }

    public void initData() {
        userInfo = new UserInfo(getApplication());
        jingang = userInfo.getCode();

        recyclerView = (RecyclerView) findViewById(R.id.rv_exgiftlog);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        isLoginOrLogintype();

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
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
                getExGiftLog(uid, pwd, 1);
            } else {
                getExGiftLog("phone", phone, 1);
            }
        } else {
            toast("未登录");
        }
    }

    /**
     * 礼品兑换记录
     *
     * @param uid
     * @param pwd
     * @param page
     */
    public void getExGiftLog(String uid, String pwd, int page) {
        if (jingang.equals("0")) {
            PATH = HttpPath.PATH + HttpPath.GIFT_EXLOG +
                    "uid=" + uid + "&pwd=" + "&page=" + page;
        } else {
            PATH = HttpPath.PATH + HttpPath.GIFT_EXLOG +
                    "logintype=" + uid + "&loginphone=" + "&page=" + page;
        }

        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("礼品兑换记录" + result);
                        ExGiftLog exGiftLog = GsonUtil.gsonIntance().gsonToBean(result, ExGiftLog.class);

                        exGiftLogAdapter = new ExGiftLogAdapter(ExGiftLogActivity.this, exGiftLog.getMsg());
                        recyclerView.setAdapter(exGiftLogAdapter);

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

    class ExGiftLogAdapter extends RecyclerView.Adapter<ExGiftLogAdapter.MyViewHolder> {

        private Context mContext;
        private List<ExGiftLog.MsgBean> msgBeanList;

        public ExGiftLogAdapter(Context mContext, List<ExGiftLog.MsgBean> msgBeanList) {
            this.mContext = mContext;
            this.msgBeanList = msgBeanList;
        }

        @Override
        public ExGiftLogAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            ExGiftLogAdapter.MyViewHolder vh = new ExGiftLogAdapter.MyViewHolder(LayoutInflater.from(
                    ExGiftLogActivity.this).inflate(R.layout.item_exgiftlog, parent,
                    false));
            return vh;
        }

        @Override
        public void onBindViewHolder(final ExGiftLogAdapter.MyViewHolder holder, int position) {
            String title = msgBeanList.get(position).getTitle();
            String addtime = msgBeanList.get(position).getAddtime();
            String score = msgBeanList.get(position).getScore();
            String status = msgBeanList.get(position).getStatus();

            holder.tv_title.setText("" + title);
            holder.tv_addtime.setText("" + addtime);
            holder.tv_score.setText("-" + score);


            if(status.equals("0")){
                holder.tv_status.setText("待处理");
            }else if(status.equals("1")){
                holder.tv_status.setText("配送中");
            }else if(status.equals("2")){
                holder.tv_status.setText("已发货");
            }else{
                System.out.println("错误！");
            }

            /*0待处理
             1已处理，配送中
             2已发货*/

        }

        @Override
        public int getItemCount() {
            return msgBeanList.size();
        }

        class MyViewHolder extends BaseRecyclerViewHolder {

            TextView tv_title, tv_addtime, tv_score, tv_status;

            public MyViewHolder(View view) {
                super(view);
                tv_title = (TextView) view.findViewById(R.id.tv_item_exgiftlog_title);
                tv_addtime = (TextView) view.findViewById(R.id.tv_item_exgiftlog_addtime);
                tv_score = (TextView) view.findViewById(R.id.tv_item_exgiftlog_score);
                tv_status = (TextView) view.findViewById(R.id.tv_item_exgiftlog_status);
            }
        }
    }
}
