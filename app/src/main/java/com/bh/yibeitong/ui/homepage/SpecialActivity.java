package com.bh.yibeitong.ui.homepage;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.homepage.Tese;
import com.bh.yibeitong.ui.LoginRegisterActivity;
import com.bh.yibeitong.ui.express.ExpressActivity;
import com.bh.yibeitong.ui.sendwater.SendWaterActivity;
import com.bh.yibeitong.ui.village.SecondHandActivity;
import com.bh.yibeitong.ui.village.VillageActivity;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.UserInfo;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;


/**
 * Created by jingang on 2017/6/20.
 * 主页特色服务
 */
public class SpecialActivity extends BaseTextActivity {
    /*接口地址*/
    private String PATH = "";

    /*UI显示*/
    private ListView listView;
    private TeseListAdapter teseListAdapter;

    /*接收页面差传值*/
    private Intent intent;
    private String shopid;

    /*查看本地存储*/
    UserInfo userInfo;
    private String jingang;

    //尝试一下RecyclerView
    private RecyclerView recyclerView;
    private HomeAdapter adapter;

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

        setListener();

    }
    private GridLayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager linearLayoutManager;

    /*组件初始化*/
    public void initData() {
        userInfo = new UserInfo(getApplication());
        jingang = userInfo.getLogin();

        intent = getIntent();
        shopid = intent.getStringExtra("shopid");

        listView = (ListView) findViewById(R.id.lv_tese);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


//        linearLayoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(linearLayoutManager);

        swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.line_swipe_refresh) ;
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

    private void setListener(){
        //swipeRefreshLayout刷新监听
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLtmkList();
            }
        });
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
                        System.out.println("特色服务" + result);

                        Tese tese = GsonUtil.gsonIntance().gsonToBean(result, Tese.class);
//                        teseListAdapter = new TeseListAdapter(SpecialActivity.this, tese.getMsg());
//                        listView.setAdapter(teseListAdapter);

                        recyclerView.setAdapter(
                                adapter = new HomeAdapter(SpecialActivity.this,tese.getMsg()));
                        //recyclerview设置适配器

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
    public class TeseListAdapter extends BaseAdapter {
        private Context mContext;
        private List<Tese.MsgBean> msgBeenList;

        public TeseListAdapter(Context mContext, List<Tese.MsgBean> msgBeenList) {
            this.mContext = mContext;
            this.msgBeenList = msgBeenList;
        }

        @Override
        public int getCount() {
            return msgBeenList.size();
        }

        @Override
        public Object getItem(int i) {
            return msgBeenList.get(i);
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
                view = LayoutInflater.from(mContext).inflate(R.layout.item_tese, null);

                vh.iv_img = (ImageView) view.findViewById(R.id.iv_item_tese_img);

                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }

            String img = msgBeenList.get(i).getImg();

            System.out.println("img = " + img);

            if (img.equals("")) {
                vh.iv_img.setImageResource(R.mipmap.yibeitong001);

            } else {
                x.image().bind(vh.iv_img, "http://www.ybt9.com/" + img);
            }

            /*点击listView的item*/
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String id = msgBeenList.get(i).getOrderid();
                    String action = msgBeenList.get(i).getAction();
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

                    } else {
                        toast("错误   ");
                    }
                }
            });

            return view;
        }

        public class ViewHolder {
            ImageView iv_img;
        }
    }


    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
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
        public void onBindViewHolder(MyViewHolder holder, int position) {

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

        class MyViewHolder extends ViewHolder {

            ImageView img;

            public MyViewHolder(View view) {
                super(view);
                img = (ImageView) view.findViewById(R.id.iv_item_tese_img);
            }
        }
    }

}