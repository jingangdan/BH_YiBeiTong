package com.bh.yibeitong.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bh.yibeitong.Interface.OnItemClickListener;
import com.bh.yibeitong.Interface.OnItemLongClickListener;
import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseFragment;
import com.bh.yibeitong.bean.GoodsIndex;
import com.bh.yibeitong.bean.shopbean.Recommed;
import com.bh.yibeitong.refresh.recyclerview.BaseRecyclerViewHolder;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.utils.MD5Util;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class FMHomePageTest extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener {
    private View view;

    private RecyclerView rv_goods, rv_classify;

    private SwipeRefreshLayout swipeRefreshLayout;
    private HomeAdapter homeAdapter;
    private List<Recommed.MsgBean> msgBeen = new ArrayList<>();

    private RecyclerViewAdapter recyclerViewAdapter;
    private List<GoodsIndex.MsgBean.CatefoodslistBean> ceFoodList = new ArrayList<>();
    private List<GoodsIndex.MsgBean.CatefoodslistBean> ceFood = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home_page_test, container, false);
        initData();


        return view;
    }

    public void initData() {

        rv_goods = (RecyclerView) view.findViewById(R.id.rv_goods);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.sr_home_page_test);

        swipeRefreshLayout.setOnRefreshListener(this);

        rv_goods.setLayoutManager(new LinearLayoutManager(getActivity()));

        rv_classify = (RecyclerView) view.findViewById(R.id.rv_classify);
        rv_classify.setLayoutManager(new LinearLayoutManager(getActivity()));


        getCateRecommed("24");

        getShopGoods("118.345471", "35.105038", 1);

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }

    /**
     * 首页推荐 店铺优选
     * 获取店铺推荐区
     *
     * @param shopid
     */
    public void getCateRecommed(final String shopid) {
        String PATH = HttpPath.path + HttpPath.CATE_GETRECOMMED + "shopid=" + shopid;

        RequestParams params = new RequestParams(PATH);
        System.out.println("" + PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("获取店铺推荐区" + result);
                        final Recommed recommed = GsonUtil.gsonIntance().gsonToBean(result, Recommed.class);

                        msgBeen = recommed.getMsg();

                        homeAdapter = new HomeAdapter(getActivity(), msgBeen);
                        GridLayoutManager llmv = new GridLayoutManager(getActivity(), 4, GridLayoutManager.VERTICAL, false);
                        //设置为一个4列的纵向网格布局

                        rv_goods.setLayoutManager(llmv);
                        rv_goods.setAdapter(homeAdapter);


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
     * 获取首页数据
     *
     * @param latitude
     * @param longtitude 118.351852   35.111124
     */
    public void getShopGoods(String latitude, String longtitude, final int pages) {
        String Path = HttpPath.PATH + HttpPath.GOODSINDEX +
                "lat=" + latitude + "&lng=" + longtitude + "" + "&page=" + pages;

        System.out.println("" + Path);

        RequestParams params = new RequestParams(Path);
        x.http().post(params,
                new Callback.CommonCallback<String>() {
                    public void onSuccess(String result) {
                        System.out.println("首页" + result);
                        //MD5加密数据
                        String results = MD5Util.getUnicode(result);

                        GoodsIndex goodsIndex = GsonUtil.gsonIntance().gsonToBean(results, GoodsIndex.class);

                        //tv_shopname.setText("(当前店铺：" + shopName + "）");

                        if (goodsIndex.getMsg().getCatefoodslist().toString().equals("[]")) {
                            toast("没有更多数据");

                        } else {

                            ceFood = goodsIndex.getMsg().getCatefoodslist();
                            ceFoodList.addAll(ceFood);
                            recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), ceFoodList);

                            GridLayoutManager llmv = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
                            //设置为一个4列的纵向网格布局

                            rv_classify.setLayoutManager(llmv);

                            rv_classify.setAdapter(recyclerViewAdapter);


                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        System.out.println("首页onError" + ex.toString());
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        System.out.println("onCancelled");
                    }

                    @Override
                    public void onFinished() {
                        System.out.println("onFinished");
                    }
                });
    }


    /*新商品分类按钮（八个）*/
    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        private com.bh.yibeitong.Interface.OnItemClickListener mOnItemClickListener;
        private OnItemLongClickListener mOnItemLongClickListener;

        public void setOnItemClickListener(com.bh.yibeitong.Interface.OnItemClickListener mOnItemClickListener) {
            this.mOnItemClickListener = mOnItemClickListener;
        }

        public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
            this.mOnItemLongClickListener = mOnItemLongClickListener;
        }

        private Context mContext;
        private List<Recommed.MsgBean> msgBeanList;

        public HomeAdapter(Context mContext, List<Recommed.MsgBean> msgBeanList) {
            this.mContext = mContext;
            this.msgBeanList = msgBeanList;
        }

        @Override
        public HomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            HomeAdapter.MyViewHolder vh = new HomeAdapter.MyViewHolder(LayoutInflater.from(
                    mContext).inflate(R.layout.item_goods_classily, parent,
                    false));
            return vh;
        }

        @Override
        public void onBindViewHolder(final HomeAdapter.MyViewHolder holder, int position) {
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

            String name = msgBeanList.get(position).getName();
            String imgPath = msgBeanList.get(position).getImg();

            if (imgPath.equals("")) {
                holder.imageView.setImageResource(R.mipmap.yibeitong001);

            } else {
                x.image().bind(holder.imageView, "http://www.ybt9.com/" + imgPath);

            }

            holder.title.setText("" + name);

        }

        @Override
        public int getItemCount() {
            return msgBeanList.size();
        }

        class MyViewHolder extends BaseRecyclerViewHolder {

            public ImageView imageView;
            public TextView title;

            public MyViewHolder(View view) {
                super(view);
                //img = (ImageView) view.findViewById(R.id.iv_item_tese_img);
                imageView = (ImageView) view.findViewById(R.id.iv_item_gc_img);
                title = (TextView) view.findViewById(R.id.tv_item_gc_title);
            }
        }
    }

    /*商品适配器*/
    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

        private OnItemClickListener mOnItemClickListener;
        private OnItemLongClickListener mOnItemLongClickListener;

        public void setOnItemClickListener(com.bh.yibeitong.Interface.OnItemClickListener mOnItemClickListener) {
            this.mOnItemClickListener = mOnItemClickListener;
        }

        public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
            this.mOnItemLongClickListener = mOnItemLongClickListener;
        }

        private Context mContext;
        private List<GoodsIndex.MsgBean.CatefoodslistBean> msgBeanList;

        public RecyclerViewAdapter(Context mContext, List<GoodsIndex.MsgBean.CatefoodslistBean> msgBeanList) {
            this.mContext = mContext;
            this.msgBeanList = msgBeanList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            MyViewHolder vh = new MyViewHolder(LayoutInflater.from(
                    mContext).inflate(R.layout.item_self_support, parent,
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

            String name = msgBeanList.get(position).getName();
            String imgPath = msgBeanList.get(position).getImg();
            int cartnum = msgBeanList.get(position).getCartnum();

            if (imgPath.equals("")) {
                holder.imageView.setImageResource(R.mipmap.yibeitong001);

            } else {
                x.image().bind(holder.imageView, "http://www.ybt9.com/" + imgPath);

            }

            holder.title.setText("" + name);

            holder.num.setText(""+cartnum);

        }

        @Override
        public int getItemCount() {
            return msgBeanList.size();
        }

        class MyViewHolder extends BaseRecyclerViewHolder {

            public ImageView imageView;
            public TextView title, num;

            public MyViewHolder(View view) {
                super(view);
                imageView = (ImageView) view.findViewById(R.id.iv_item_ss);
                title = (TextView) view.findViewById(R.id.tv_item_ss_title);
                num = (TextView) view.findViewById(R.id.tv_shop_num);
            }
        }
    }

}