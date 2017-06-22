package com.bh.yibeitong.ui.search;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.search.ShopSearch;
import com.bh.yibeitong.refresh.MyListView;
import com.bh.yibeitong.refresh.PullToRefreshView;
import com.bh.yibeitong.ui.CateFoodDetailsActivity;
import com.bh.yibeitong.ui.SelfSupportActivity;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.lidroid.xutils.BitmapUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jingang on 2016/12/22.
 * 搜索结果
 */
public class SearchListActivity extends BaseTextActivity implements
        PullToRefreshView.OnFooterRefreshListener,
        PullToRefreshView.OnHeaderRefreshListener {

    /*接口地址*/
    private String PATH;

    /*接收页面传值*/
    private Intent intent;
    String searchvalue, latitude, longtitude, shopid;
    String UTF_searchvalue = null;

    /*显示搜索结果*/
    private MyListView mlv_seller, mlv_goods;
    //private SellerSearchAdapter sellerSearchAdapter;
    private GoodsSearchAdapter goodsSearchAdapter;
    //private List<ShopSearch.MsgBean.ShoplistBean> shoplistBeen = new ArrayList<>();
    private List<ShopSearch.MsgBean.GoodslistBean> goodslistBeen = new ArrayList<>();

    /**/
    private TextView seller_null, goods_null;

    private PullToRefreshView pullToRefreshView;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_search_list);

    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("搜索结果");
        setTitleBack(true, 0);

        initData();


    }

    public void initData() {
        mlv_seller = (MyListView) findViewById(R.id.myListView_search_seller);
        mlv_goods = (MyListView) findViewById(R.id.myListView_search_good);

        seller_null = (TextView) findViewById(R.id.tv_seller_muyou);
        goods_null = (TextView) findViewById(R.id.tv_goods_muyou);

        pullToRefreshView = (PullToRefreshView) findViewById(R.id.puToRefreshView_search);

        pullToRefreshView.setOnHeaderRefreshListener(this);
        pullToRefreshView.setOnFooterRefreshListener(this);
        pullToRefreshView.setLastUpdated(new Date().toLocaleString());

        intent = getIntent();
        searchvalue = intent.getStringExtra("searchvalue");
        latitude = intent.getStringExtra("lat");
        longtitude = intent.getStringExtra("lng");
        shopid = intent.getStringExtra("shopid");

        try {
            UTF_searchvalue = URLEncoder.encode(searchvalue, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        postSearchShop(UTF_searchvalue, latitude, longtitude, shopid);
    }

    /**
     * 搜索店铺、商品
     *
     * @param searchvalue
     * @param latitude
     * @param longtitude
     */
    public void postSearchShop(String searchvalue, String latitude, String longtitude, String shopid) {
        PATH = HttpPath.PATH + HttpPath.SHOP_NEW_SRARCH +
                "searchvalue=" + searchvalue + "&lat=" + latitude + "&lng=" + longtitude
        +"&shopid="+shopid;

        System.out.println("" + PATH);

        RequestParams params = new RequestParams(PATH);
        params.setConnectTimeout(1000 * 10);//超时10秒

        x.http().post(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("搜索结果" + result);

                        ShopSearch shopSearch = GsonUtil.gsonIntance().gsonToBean(result, ShopSearch.class);
                        //shoplistBeen = shopSearch.getMsg().getShoplist();
                        goodslistBeen = shopSearch.getMsg().getGoodslist();

                        /*判断有无数据*/
                        /*if (shoplistBeen.size() == 0) {
                            seller_null.setVisibility(View.VISIBLE);
                        } else if (shoplistBeen.size() > 0) {
                            sellerSearchAdapter = new SellerSearchAdapter(SearchListActivity.this, shoplistBeen);
                            mlv_seller.setAdapter(sellerSearchAdapter);
                        } else {

                        }*/

                        if (goodslistBeen.size() == 0) {
                            goods_null.setVisibility(View.VISIBLE);
                        } else if (goodslistBeen.size() > 0) {
                            goodsSearchAdapter = new GoodsSearchAdapter(SearchListActivity.this, goodslistBeen);
                            mlv_goods.setAdapter(goodsSearchAdapter);
                        } else {

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

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        pullToRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                pullToRefreshView.onFooterRefreshComplete();
                toast("加载完毕");
            }
        }, 1000);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        pullToRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                pullToRefreshView.onHeaderRefreshComplete("更新于："
                        + Calendar.getInstance().getTime().toLocaleString());
                pullToRefreshView.onHeaderRefreshComplete();

                toast("刷新成功");
            }
        }, 1000);

    }

    /**
     * 搜索商家适配器
     */
//    public class SellerSearchAdapter extends BaseAdapter {
//        private Context mContext;
//        private List<ShopSearch.MsgBean.ShoplistBean> shoplistBeanList = new ArrayList<>();
//
//        public SellerSearchAdapter(Context mContext, List<ShopSearch.MsgBean.ShoplistBean> shoplistBeanList) {
//            this.mContext = mContext;
//            this.shoplistBeanList = shoplistBeanList;
//        }
//
//        @Override
//
//        public int getCount() {
//            return shoplistBeanList.size();
//        }
//
//        @Override
//        public Object getItem(int i) {
//            return shoplistBeanList.get(i);
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return i;
//        }
//
//        @Override
//        public View getView(int i, View view, ViewGroup viewGroup) {
//            ViewHolder vh;
//            if (view == null) {
//                vh = new ViewHolder();
//                view = LayoutInflater.from(mContext).inflate(R.layout.item_shop, null);
//
//                vh.img = (ImageView) view.findViewById(R.id.iv_item_shop_image);
//                vh.name = (TextView) view.findViewById(R.id.tv_item_shop_name);
//
//                vh.sellcount = (TextView) view.findViewById(R.id.tv_item_shop_sellcount);
//                vh.juli = (TextView) view.findViewById(R.id.tv_item_shop_juli);
//
//                vh.limitcost = (TextView) view.findViewById(R.id.tv_item_shop_limitcost);
//                vh.pscost = (TextView) view.findViewById(R.id.tv_item_shop_pscost);
//
//                view.setTag(vh);
//            } else {
//                vh = (ViewHolder) view.getTag();
//            }
//
//            /*图片*/
//            String img = shoplistBeanList.get(i).getShopimg();
//
//            /*名称*/
//            String name = shoplistBeanList.get(i).getShopname();
//
//            /*已售单数*/
//            //int sellcount = shoplistBeanList.get(i).getSellcount();
//
//            /*距离*/
//            String juli = shoplistBeanList.get(i).getJuli();
//
//            /*起送价  配送费*/
//            String limitcost = shoplistBeanList.get(i).getLimitcost();
//            int pscost = shoplistBeanList.get(i).getPscost();
//
//
//            if (img.equals("")) {
//                vh.img.setImageResource(R.mipmap.yibeitong001);
//
//            } else {
//                BitmapUtils bitmapUtils = new BitmapUtils(mContext);
//
//                bitmapUtils.configDiskCacheEnabled(true);
//                bitmapUtils.configMemoryCacheEnabled(false);
//
//                bitmapUtils.display(vh.img, img);
//
//            }
//
//
//            /* vh.img.setImageResource(R.mipmap.yibeitong001);
//            vh.name.setText("易贝通曹王庄店");*/
//
//            vh.name.setText(name);
//
//            vh.sellcount.setText("已售" + "单");
//
//            vh.juli.setText("距离" + juli);
//            vh.limitcost.setText("￥" + limitcost);
//
//            vh.pscost.setText("￥" + pscost);
//
//            //点击跳转
//            mlv_seller.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    latitude = shoplistBeanList.get(i).getLat();
//                    longtitude = shoplistBeanList.get(i).getLng();
//                    Intent intent = new Intent();
//
//                    intent.putExtra("lat", latitude);
//                    intent.putExtra("lng", longtitude);
//                    setResult(2, intent);// 设置resultCode，onActivityResult()中能获取到
//                    SearchListActivity.this.finish();
//                }
//            });
//
//            return view;
//        }
//
//        public class ViewHolder {
//            private ImageView img;
//            //名称 已售单数 距离
//            private TextView name, sellcount, juli;
//
//            //起送价 配送费
//            private TextView limitcost, pscost;
//        }
//    }

    /**
     * 搜索商品适配器
     */
    public class GoodsSearchAdapter extends BaseAdapter {
        private Context mContext;
        private List<ShopSearch.MsgBean.GoodslistBean> goodslistBeenList = new ArrayList<>();

        public GoodsSearchAdapter(Context mContext, List<ShopSearch.MsgBean.GoodslistBean> goodslistBeenList) {
            this.mContext = mContext;
            this.goodslistBeenList = goodslistBeenList;
        }

        @Override
        public int getCount() {
            return goodslistBeenList.size();
        }

        @Override
        public Object getItem(int i) {
            return goodslistBeenList.get(i);
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
                view = LayoutInflater.from(mContext).inflate(R.layout.item_catefoodslist, null);

                vh.img = (ImageView) view.findViewById(R.id.iv_item_goods_img);
                vh.title = (TextView) view.findViewById(R.id.tv_item_goods_name);
                vh.sellcount = (TextView) view.findViewById(R.id.tv_item_goods_sellcount);
                vh.price = (TextView) view.findViewById(R.id.tv_item_goods_price);
                vh.but_go = (Button) view.findViewById(R.id.but_item_goods_go);


                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }

            String img = goodslistBeenList.get(i).getImg();
            String name = goodslistBeenList.get(i).getName();
            String sellcount = goodslistBeenList.get(i).getSellcount();
            String price = goodslistBeenList.get(i).getCost();


            if (img.equals("")) {
                vh.img.setImageResource(R.mipmap.yibeitong001);

            } else {
                BitmapUtils bitmapUtils = new BitmapUtils(mContext);

                bitmapUtils.configDiskCacheEnabled(true);
                bitmapUtils.configMemoryCacheEnabled(false);

                bitmapUtils.display(vh.img, img);

            }

            vh.title.setText("" + name);
            vh.sellcount.setText("销量" + sellcount);
            vh.price.setText("￥" + price);

            /*点击查看商品详情*/
            mlv_goods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String str_id = goodslistBeenList.get(i).getId();
                    String instro = goodslistBeenList.get(i).getInstro();
                    String foodName = goodslistBeenList.get(i).getName();
                    String SellCount = goodslistBeenList.get(i).getSellcount();
                    String foodPoint = goodslistBeenList.get(i).getPoint();
                    String foodCost = goodslistBeenList.get(i).getCost();
                    String foodGoodattr = goodslistBeenList.get(i).getGoodattr();



                    Intent intent = new Intent(mContext, CateFoodDetailsActivity.class);
                    intent.putExtra("id", str_id);//商品id

                    intent.putExtra("instro", instro);
                    intent.putExtra("foodName", foodName);
                    intent.putExtra("foodSellCount", SellCount);
                    intent.putExtra("foodPoint", foodPoint);
                    intent.putExtra("foodCost", foodCost);
                    intent.putExtra("foodGoodattr", foodGoodattr);

                    //intent.putExtra("cartNum", String.valueOf(str_cartNum));

                    mContext.startActivity(intent);
                }
            });

            return view;
        }

        public class ViewHolder {
            ImageView img;
            TextView title, sellcount, price;
            Button but_go;

        }
    }

}
