package com.bh.yibeitong.seller.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.seller.bean.ManagesCommt;
import com.bh.yibeitong.utils.BaseRecyclerViewHolder;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnItemLongClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingang on 2017/8/9.
 * 评价留言（商家端）
 */
public class SManageCommtActivity extends BaseTextActivity {

    /*接收页面传值*/
    private Intent intent;
    private String uid = "", pwd = "";

    /*页数 每页个数*/
    private int page = 1, pagesize = 10;

    /*接口地址*/
    private String PATH = "";

    /*UI展示*/
    private LRecyclerView lRecyclerView;
    private ManagesCommtAdapter managesCommtAdapter;
    private List<ManagesCommt.MsgBean> commtList = new ArrayList<>();
    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_s_managescommt);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();

        managesCommtAdapter = new ManagesCommtAdapter(SManageCommtActivity.this, commtList);

        mLRecyclerViewAdapter = new LRecyclerViewAdapter(managesCommtAdapter);
        lRecyclerView.setAdapter(mLRecyclerViewAdapter);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("评价留言");
        setTitleBack(true, 0);

    }

    /*组件 初始化*/
    public void initData() {
        intent = getIntent();
        uid = intent.getStringExtra("uid");
        pwd = intent.getStringExtra("pwd");

        lRecyclerView = (LRecyclerView) findViewById(R.id.lrecycylerview);
        lRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        getMamagesCommt(page, pagesize, uid, pwd);

        lRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                pagesize = 10;
                getMamagesCommt(page, pagesize, uid, pwd);
            }
        });
        lRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                System.out.println("加载");
                //此处需判断数据是否加载完
                page++;
                pagesize += 10;
//                if (mCurrentCounter < TOTAL_COUNTER) {
//                    // loading more
//                    requestData();
//                } else {
//                    //the end
//                    mRecyclerView.setNoMore(true);
//                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    /**
     * 评价留言列表
     *
     * @param page
     * @param pagesize
     * @param uid
     * @param pwd
     */
    public void getMamagesCommt(int page, final int pagesize, String uid, String pwd) {
        PATH = HttpPath.PATH + HttpPath.APP_MANAGESCOMMT +
                "page=" + page + "&pagesize=" + pagesize + "&uid=" + uid + "&pwd=" + pwd;

        RequestParams params = new RequestParams(PATH);
        System.out.println("评价留言" + PATH);

        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        lRecyclerView.refreshComplete(pagesize);

                        System.out.println("评价留言" + result);
                        ManagesCommt managesCommt = GsonUtil.gsonIntance().gsonToBean(result, ManagesCommt.class);
                        commtList = managesCommt.getMsg();

                        managesCommtAdapter.setManagesCommtAdapter(SManageCommtActivity.this, commtList);
                        managesCommtAdapter.notifyDataSetChanged();


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

    /**/
    public class ManagesCommtAdapter extends RecyclerView.Adapter<ManagesCommtAdapter.MyViewHolder> {
        private Context mContext;
        private List<ManagesCommt.MsgBean> msgBeanList;

        private OnItemClickListener mOnItemClickListener;
        private OnItemLongClickListener mOnItemLongClickListener;

        public ManagesCommtAdapter(Context mContext, List<ManagesCommt.MsgBean> msgBeanList) {
            this.mContext = mContext;
            this.msgBeanList = msgBeanList;
        }

        public void setManagesCommtAdapter(Context mContext, List<ManagesCommt.MsgBean>  msgBeanList) {
            this.mContext = mContext;
            this.msgBeanList = msgBeanList;
        }

        public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
            this.mOnItemClickListener = mOnItemClickListener;
        }

        public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
            this.mOnItemLongClickListener = mOnItemLongClickListener;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder vh = new MyViewHolder(LayoutInflater.from(
                    mContext).inflate(R.layout.item_seller_managescommt, parent,
                    false));

            if (viewType == 0) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_seller_managescommt, parent, false);
                return new MyViewHolder(view);

            } else if (viewType == 1) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_foot, parent, false);
                return new MyViewHolder(view);

            }
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

            String name = (String) msgBeanList.get(position).getName();
            String addtime = msgBeanList.get(position).getAddtime();
            String content = msgBeanList.get(position).getContent();

            holder.name.setText("" + name);
            holder.addtime.setText("" + addtime);
            holder.content.setText("" + content);

        }

        @Override
        public int getItemCount() {
            return msgBeanList.size();
        }

        class MyViewHolder extends BaseRecyclerViewHolder {

            TextView name, addtime, content;

            public MyViewHolder(View view) {
                super(view);
                name = (TextView) view.findViewById(R.id.tv_item_sm_name);
                addtime = (TextView) view.findViewById(R.id.tv_item_sm_addtime);
                content = (TextView) view.findViewById(R.id.tv_item_sm_content);

            }
        }
    }


}
