package com.bh.yibeitong.ui.homepage;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bh.yibeitong.Interface.OnItemClickListener;
import com.bh.yibeitong.Interface.OnItemLongClickListener;
import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.homepage.Link;
import com.bh.yibeitong.bean.homepage.Tese;
import com.bh.yibeitong.ui.LoginRegisterActivity;
import com.bh.yibeitong.ui.express.ExpressActivity;
import com.bh.yibeitong.ui.sendwater.SendWaterActivity;
import com.bh.yibeitong.ui.village.SecondHandActivity;
import com.bh.yibeitong.ui.village.VillageActivity;
import com.bh.yibeitong.utils.BaseRecyclerViewHolder;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.utils.RecyclerViewUtils;
import com.bh.yibeitong.view.UserInfo;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by jingang on 2017/6/20.
 * 主页特色服务
 */
public class SpecialActivity extends BaseTextActivity implements SwipeRefreshLayout.OnRefreshListener{
    /*接口地址*/
    private String PATH = "";

    /*接收页面差传值*/
    private Intent intent;
    private String shopid;

    /*查看本地存储*/
    UserInfo userInfo;
    private String jingang;

    //尝试一下RecyclerView
    private RecyclerView recyclerView;
    private HomeAdapter adapter;

    /*刷新控件*/
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager mLayoutManager;


    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_special);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        initData();

        setTitleBack(true, 0);
        setTitleName("特色服务");

    }

    /*组件初始化*/
    public void initData() {
        userInfo = new UserInfo(getApplication());
        jingang = userInfo.getLogin();

        intent = getIntent();
        shopid = intent.getStringExtra("shopid");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(mLayoutManager);

        swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.line_swipe_refresh) ;
        swipeRefreshLayout.setOnRefreshListener(this);

        //调整SwipeRefreshLayout的位置
        swipeRefreshLayout.setProgressViewOffset(false,
                0,
                (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        24,
                        getResources().getDisplayMetrics()));

        getLtmkList();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    public void onRefresh() {
        //
        System.out.println("刷新");
        getLtmkList();

    }

    /**
     * 获取特色服务模块
     */
    public void getLtmkList() {
        PATH = HttpPath.PATH + HttpPath.GETTESE;

        RequestParams params = new RequestParams(PATH);

        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        swipeRefreshLayout.setRefreshing(false);
                        System.out.println("特色服务" + result);

                        final Tese tese = GsonUtil.gsonIntance().gsonToBean(result, Tese.class);

                        //recyclerview设置适配器
                        adapter = new HomeAdapter(SpecialActivity.this, tese.getMsg());

                        GridLayoutManager llmv;
                        llmv = new GridLayoutManager(SpecialActivity.this, 2, GridLayoutManager.VERTICAL, false);
                        llmv.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                            @Override
                            public int getSpanSize(int position) {
                                String imgwidth = tese.getMsg().get(position).getImgwidth();
                                if (imgwidth.equals("50")) {
                                    return 1;
                                    } else if (imgwidth.equals("100")) {
                                    return 2;
                                }
                                return 1;
                            }
                        });
                        recyclerView.setLayoutManager(llmv);

                        recyclerView.setAdapter(adapter);

                        adapter.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                String id = tese.getMsg().get(position).getId();
                                String action = tese.getMsg().get(position).getAction();
                                if (action.equals("sssm")) {
                                    //送水上门
                                    intent = new Intent(SpecialActivity.this, SendWaterActivity.class);
                                    intent.putExtra("shopid", shopid);
                                    startActivity(intent);

                                } else if (action.equals("mkkd")) {
                                    //收发快递
                                    intent = new Intent(SpecialActivity.this, ExpressActivity.class);
                                    intent.putExtra("shopid", shopid);
                                    startActivity(intent);

                                } else if (action.equals("luntan")) {
                                    //小区信息
                                    //进入之前判断是否已经登录
                                    if (jingang.equals("")) {
                                        //未登录
                                        startActivity(new Intent(SpecialActivity.this, LoginRegisterActivity.class));
                                    } else if (jingang.equals("0")) {
                                        //未登录
                                        startActivity(new Intent(SpecialActivity.this, LoginRegisterActivity.class));
                                    } else if (jingang.equals("1")) {
                                        //已登录
                                        intent = new Intent(SpecialActivity.this, VillageActivity.class);
                                        intent.putExtra("mkid", id);
                                        startActivity(intent);
                                    }

                                } else if (action.equals("togethersay")) {
                                    //二手交易
                                    //进入之前判断是否已经登录
                                    if (jingang.equals("")) {
                                        //未登录
                                        startActivity(new Intent(SpecialActivity.this, LoginRegisterActivity.class));
                                    } else if (jingang.equals("0")) {
                                        //未登录
                                        startActivity(new Intent(SpecialActivity.this, LoginRegisterActivity.class));
                                    } else if (jingang.equals("1")) {
                                        //已登录
                                        intent = new Intent(SpecialActivity.this, SecondHandActivity.class);
                                        intent.putExtra("id", "1");
                                        startActivity(intent);
                                    }

                                } else if (action.equals("#")) {
                                    toast("暂未开发");
                                    /*intent = new Intent(SpecialActivity.this, TeseCateInfoActivity.class);
                                    intent.putExtra("title",tese.getMsg().get(position).getName());
                                    intent.putExtra("action", action);
                                    intent.putExtra("id", id);
                                    intent.putExtra("shopid", shopid);*/
                                    //startActivity(intent);

                                } else if (action.equals("tesecate")) {
                                    //getTscar(action);
                                    //商品分类

                                    intent = new Intent(SpecialActivity.this, TeseCateInfoActivity.class);
                                    intent.putExtra("title",tese.getMsg().get(position).getName());
                                    intent.putExtra("action", action);
                                    intent.putExtra("id", id);
                                    intent.putExtra("shopid", shopid);
                                    startActivity(intent);

                                } else {
                                    toast("  ");
                                }

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

    /**
     * 获取外链
     * @param action
     */
    public void getTscar(String action) {
        PATH = HttpPath.PATH + "action=" + action;
        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("获取定汽车外链");
                        Link link = GsonUtil.gsonIntance().gsonToBean(result, Link.class);

                        if(link.isError() == false){
                            intent = new Intent(SpecialActivity.this, JoinActivity.class);
                            intent.putExtra("title", "定汽车");
                            intent.putExtra("url", link.getMsg().toString());
                            startActivity(intent);
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

    /**
     * 特色服务模块适配器
     */
    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        private OnItemClickListener mOnItemClickListener;
        private OnItemLongClickListener mOnItemLongClickListener;

        public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
            this.mOnItemClickListener = mOnItemClickListener;
        }

        public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
            this.mOnItemLongClickListener = mOnItemLongClickListener;
        }

        private Context mContext;
        private List<Tese.MsgBean> msgBeen;

        public HomeAdapter(Context mContext, List<Tese.MsgBean> msgBeen){
            this.mContext = mContext;
            this.msgBeen = msgBeen;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            MyViewHolder vh = new MyViewHolder(LayoutInflater.from(
                    SpecialActivity.this).inflate(R.layout.item_tese, parent,
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

            String img = msgBeen.get(position).getImg();

            if (img.equals("")) {
                holder.img.setImageResource(R.mipmap.yibeitong001);

            } else {
                x.image().bind(holder.img, "http://www.ybt9.com/" + img);
            }

        }

        @Override
        public int getItemCount() {
            return msgBeen.size();
        }

        class MyViewHolder extends BaseRecyclerViewHolder {

            ImageView img;

            public MyViewHolder(View view) {
                super(view);
                img = (ImageView) view.findViewById(R.id.iv_item_tese_img);
            }
        }
    }

}