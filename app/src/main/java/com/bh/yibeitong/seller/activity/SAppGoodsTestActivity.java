package com.bh.yibeitong.seller.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.seller.GoodsList;
import com.bh.yibeitong.bean.seller.MarketFgoodstype;
import com.bh.yibeitong.bean.seller.MarketTgoodstype;
import com.bh.yibeitong.refresh.MyGridView;
import com.bh.yibeitong.refresh.PullToRefreshView;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingang on 2017/9/25.
 * 店铺管理
 */

public class SAppGoodsTestActivity extends BaseTextActivity implements
        PullToRefreshView.OnHeaderRefreshListener{

    private SAppGoodsTestActivity TAG = SAppGoodsTestActivity.this;

    /*接收页面传值*/
    private Intent intent;
    private String uid = "", pwd = "";

    /*接口地址*/
    private String PATH = "";

    /*UI展示*/
    private MyGridView gv_marketf, gv_markett, gv_goodslist;
    private List<MarketFgoodstype.MsgBean> marketfList = new ArrayList<>();
    private MarketFAdapter marketFAdapter;

    private List<MarketTgoodstype.MsgBean> markettList = new ArrayList<>();
    private MarketTAdapter marketTAdapter;

    private List<GoodsList.MsgBean> goodsList = new ArrayList<>();
    private GoodsListAdapter goodsListAdapter;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_s_appgoods_test);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleBack(true, 0);
        setTitleName("店铺管理");

        initData();
    }

    public void initData() {
        intent = getIntent();
        uid = intent.getStringExtra("uid");
        pwd = intent.getStringExtra("pwd");

        gv_marketf = (MyGridView) findViewById(R.id.gv_marketfgoods);
        gv_markett = (MyGridView) findViewById(R.id.gv_markettgoods);
        gv_goodslist = (MyGridView) findViewById(R.id.gv_goodslist);

        getMarketFgoodstype(uid, pwd);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    /**
     * 超市商家获取一级商品分类
     *
     * @param uid
     * @param pwd
     */
    public void getMarketFgoodstype(final String uid, final String pwd) {
        PATH = HttpPath.PATH + HttpPath.APP_MARKERFGOODSTYPE +
                "uid=" + uid + "&pwd=" + pwd;
        RequestParams params = new RequestParams(PATH);
        System.out.println("超市商家获取二级商品分类" + PATH);

        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("超市商家获取二级商品分类" + result);
                        MarketFgoodstype marketFgoodstype = GsonUtil.gsonIntance().gsonToBean(result, MarketFgoodstype.class);

                        marketfList = marketFgoodstype.getMsg();
                        marketFAdapter = new MarketFAdapter(marketfList, TAG);
                        gv_marketf.setAdapter(marketFAdapter);

                        getMarketTgoodstype(uid, pwd, marketfList.get(0).getId());

                        gv_marketf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                marketFAdapter.changeSelected(i);
                                getMarketTgoodstype(uid, pwd, marketfList.get(i).getId());
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
     * 超市商家获取二级商品分类
     *
     * @param uid
     * @param pwd
     * @param ftype 上级分类
     */
    public void getMarketTgoodstype(final String uid, final String pwd, String ftype) {
        PATH = HttpPath.PATH + HttpPath.APP_MARKERTGOODSTYPE +
                "uid=" + uid + "&pwd=" + pwd + "&ftype=" + ftype;
        RequestParams params = new RequestParams(PATH);
        System.out.println("超市商家获取二级商品分类" + PATH);

        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("超市商家获取二级商品分类" + result);
                        MarketTgoodstype marketTgoodstype = GsonUtil.gsonIntance().gsonToBean(result, MarketTgoodstype.class);

                        markettList = marketTgoodstype.getMsg();
                        marketTAdapter = new MarketTAdapter(markettList, TAG);
                        gv_markett.setAdapter(marketTAdapter);

                        getGoodsList(uid, pwd, marketTgoodstype.getMsg().get(0).getId(), 1, 100);

                        gv_markett.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                marketTAdapter.changeSelected(i);
                                getGoodsList(uid, pwd, markettList.get(i).getId(), 1, 100);
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
     * 商家获取商品
     *
     * @param typeid
     * @param page
     * @param pagesize
     */
    public void getGoodsList(String uid, String pwd, String typeid, int page, int pagesize) {
        PATH = HttpPath.PATH + HttpPath.APP_GOODSLIST +
                "uid=" + uid + "&pwd=" + pwd + "&typeid=" + typeid +
                "&page=" + page + "&pagesize=" + pagesize;
        RequestParams params = new RequestParams(PATH);
        System.out.println("商家获取商品" + PATH);

        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("商家获取商品" + result);
                        GoodsList goodsLists = GsonUtil.gsonIntance().gsonToBean(result, GoodsList.class);

                        goodsList = goodsLists.getMsg();

                        goodsListAdapter = new GoodsListAdapter(goodsList, TAG);
                        gv_goodslist.setAdapter(goodsListAdapter);

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
    public void onHeaderRefresh(PullToRefreshView view) {

    }

    /*
    * 超市商家获取一级商品分类适配器
    * */
    private class MarketFAdapter extends BaseAdapter {
        private List<MarketFgoodstype.MsgBean> marketfList;
        private Context mContent;

        private int mSelect = 0;

        public MarketFAdapter(List<MarketFgoodstype.MsgBean> marketfList, Context mContent) {
            this.marketfList = marketfList;
            this.mContent = mContent;
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
            return marketfList.size();
        }

        @Override
        public Object getItem(int i) {
            return marketfList.get(i);
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
                view = LayoutInflater.from(mContent).inflate(R.layout.item_good_classily_second, null);

                vh.tv_name = (TextView) view.findViewById(R.id.tv_item_good_classily_second);
                vh.linearLayout = (LinearLayout) view.findViewById(R.id.lin_item_good_classily_second);


                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }

            if (mSelect == i) {
                vh.tv_name.setTextColor(Color.rgb(255, 255, 255));
                vh.linearLayout.setBackgroundResource(R.drawable.linstyle_green);
            } else {
                vh.tv_name.setTextColor(Color.rgb(153, 153, 153));
                vh.linearLayout.setBackgroundResource(R.drawable.linstyle_white);
            }

            String name = marketfList.get(i).getName();
            vh.tv_name.setText("" + name);

            return view;
        }

        public class ViewHolder {
            TextView tv_name;
            LinearLayout linearLayout;
        }
    }

    /*
    * 超市商家获取二级商品分类适配器
    * */
    private class MarketTAdapter extends BaseAdapter {
        private List<MarketTgoodstype.MsgBean> markettList;
        private Context mContent;

        private int mSelect = 0;

        public MarketTAdapter(List<MarketTgoodstype.MsgBean> markettList, Context mContent) {
            this.markettList = markettList;
            this.mContent = mContent;
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
            return markettList.size();
        }

        @Override
        public Object getItem(int i) {
            return markettList.get(i);
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
                view = LayoutInflater.from(mContent).inflate(R.layout.item_good_classily_second, null);

                vh.tv_name = (TextView) view.findViewById(R.id.tv_item_good_classily_second);
                vh.linearLayout = (LinearLayout) view.findViewById(R.id.lin_item_good_classily_second);


                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }

            if (mSelect == i) {
                vh.tv_name.setTextColor(Color.rgb(255, 255, 255));
                vh.linearLayout.setBackgroundResource(R.drawable.linstyle_green);
            } else {
                vh.tv_name.setTextColor(Color.rgb(153, 153, 153));
                vh.linearLayout.setBackgroundResource(R.drawable.linstyle_white);
            }

            String name = markettList.get(i).getName();
            vh.tv_name.setText("" + name);

            return view;
        }

        public class ViewHolder {
            TextView tv_name;
            LinearLayout linearLayout;
        }
    }

    /*
    * 商家获取商品适配器
    * */
    public class GoodsListAdapter extends BaseAdapter {
        private List<GoodsList.MsgBean> goodsList;
        private Context mContent;

        public GoodsListAdapter(List<GoodsList.MsgBean> goodsList, Context mContent) {
            this.goodsList = goodsList;
            this.mContent = mContent;
        }


        @Override
        public int getCount() {
            return goodsList.size();
        }

        @Override
        public Object getItem(int position) {
            return goodsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder vh;
            if (convertView == null) {
                vh = new ViewHolder();
                convertView = LayoutInflater.from(mContent).inflate(R.layout.item_self_support, null);

                //获取item中的组件
                vh.imager = (ImageView) convertView.findViewById(R.id.iv_item_ss);
                vh.title = (TextView) convertView.findViewById(R.id.tv_item_ss_title);
                vh.price = (TextView) convertView.findViewById(R.id.tv_item_ss_price);
                vh.num = (TextView) convertView.findViewById(R.id.tv_item_ss_num);

                //添加购物车
                vh.tv_shop_num = (TextView) convertView.findViewById(R.id.tv_shop_num);
                vh.iv_add_button = (ImageView) convertView.findViewById(R.id.iv_add);
                vh.iv_sub_botton = (ImageView) convertView.findViewById(R.id.iv_sub);

                convertView.setTag(vh);

            } else {
                vh = (ViewHolder) convertView.getTag();
            }

            // 组件赋值
            String imgPath = goodsList.get(position).getImg();


            if (goodsList.get(position).getImg().equals("")) {
                vh.imager.setImageResource(R.mipmap.yibeitong001);

            } else {
                //加载图片
                //XUtilsImageUtils.display(vh.imager, "http://www.ybt9.com/" + imgPath);
                x.image().bind(vh.imager, "http://www.ybt9.com/" + imgPath);
            }

        /*id 详情图片 名称 已售 评价 单价 单位 购物车数量*/
            final String str_id = goodsList.get(position).getId();
            final String foodName = goodsList.get(position).getName();
            final String foodCost = goodsList.get(position).getCost();

            vh.title.setText("" + foodName);

            vh.price.setText("￥" + foodCost);

            vh.imager.setTag(position);//记录位置

            return convertView;

        }

        public class ViewHolder {
            private ImageView imager;
            private TextView title, price, num;

            private ImageView iv_add_button, iv_sub_botton;
            private TextView tv_shop_num;
        }

    }


}
