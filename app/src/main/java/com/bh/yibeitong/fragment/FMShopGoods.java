package com.bh.yibeitong.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bh.yibeitong.Interface.CallbackCart;
import com.bh.yibeitong.R;
import com.bh.yibeitong.adapter.GoodsClassilyAdapter;
import com.bh.yibeitong.adapter.GoodsClassilySecondAdapter;
import com.bh.yibeitong.bean.AddShopCart;
import com.bh.yibeitong.bean.ShopCart;
import com.bh.yibeitong.bean.ShopCartReturn;
import com.bh.yibeitong.bean.ShopNew;
import com.bh.yibeitong.refresh.MyGridView;
import com.bh.yibeitong.ui.CateFoodDetailsActivity;
import com.bh.yibeitong.ui.LoginRegisterActivity;
import com.bh.yibeitong.ui.OrderActivity;
import com.bh.yibeitong.ui.ShopActivity;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.utils.MD5Util;
import com.bh.yibeitong.utils.NetworkUtils;
import com.bh.yibeitong.view.CustomDialog;
import com.bh.yibeitong.view.HorizontalListView;
import com.bh.yibeitong.view.UserInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.BitmapUtils;

import org.xutils.BuildConfig;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.bh.yibeitong.R.id.myGridView_goods;

/**
 * Created by jingang on 2016/11/2.
 * 进入商店 商品
 */

/*public class FMShopGoods extends Fragment implements PullToRefreshView.OnFooterRefreshListener,
        PullToRefreshView.OnHeaderRefreshListener, View.OnClickListener {*/

public class FMShopGoods extends Fragment implements View.OnClickListener {
    private View view;

    private ProgressDialog pd;

    private ShopNewAdapter snAdapter;

    int index_msg = 0;
    int index_child = 0;

    /**
     * 一级分类
     */
    private ListView lv_shop_goods;
    private GoodsClassilyAdapter gcAdapter;

    /**
     * 二级分类
     */
    private HorizontalListView hlv_shop_goods_second;
    private GoodsClassilySecondAdapter gcsAdapter;

    //显示商品主要信息
    //private GridView gv_shop_goods;

    //
    private ShopNewUtils shopNewUtils;

    //private PullToRefreshView pullToRefreshView;
    private ScrollView sv_goods;
    private MyGridView myGridView;

    //购物车
    private double totalPrice = 0;
    private TextView tv_sg_all_price;
    private Button but_sg_pay;

    private double limitcost = 0;

    //本地缓存
    UserInfo userInfo;

    String jingang;

    DecimalFormat df;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shop_goods, null);

        x.Ext.init(getActivity().getApplication());
        x.Ext.setDebug(BuildConfig.DEBUG);

        initData();

        //获取商店id 根据activity
        String shopid = ShopActivity.shopid;

        if (!(shopid.equals(""))) {
            isShopID(shopid);
        } else {
        }
        //isNetworkUtil(shopid);

        getShopCart(shopid);

        hlv_shop_goods_second.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //System.out.println("actiion_down");

                        break;
                    case MotionEvent.ACTION_UP:
                        //System.out.println("actiion_up");
                        break;
                }

                return false;
            }
        });

//        pullToRefreshView.setOnHeaderRefreshListener(this);
//        pullToRefreshView.setOnFooterRefreshListener(this);
//        pullToRefreshView.setLastUpdated(new Date().toLocaleString());

        return view;
    }

    public static FMShopGoods newInstance(String name) {
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        FMShopGoods fragment = new FMShopGoods();
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * 组件 初始化
     */
    public void initData() {

        userInfo = new UserInfo(getActivity().getApplication());
        df = new DecimalFormat("###.00");

        jingang = userInfo.getLogin();

        limitcost = Double.parseDouble(userInfo.getShopDet());

        pd = ProgressDialog.show(getActivity(), "", "请稍候");
        shopNewUtils = new ShopNewUtils();
        //一级分类
        lv_shop_goods = (ListView) view.findViewById(R.id.lv_shop_goods);

        //二级分类
        hlv_shop_goods_second = (HorizontalListView) view.findViewById(R.id.hlv_shop_goods_second);

        //gv_shop_goods = (GridView) view.findViewById(R.id.gv_shop_goods);

//        pullToRefreshView = (PullToRefreshView) view.findViewById(R.id.puToRefreshView_goods);
        myGridView = (MyGridView) view.findViewById(myGridView_goods);

        //购物车
        tv_sg_all_price = (TextView) view.findViewById(R.id.tv_sg_all_price);
        but_sg_pay = (Button) view.findViewById(R.id.but_sg_pay);

        but_sg_pay.setOnClickListener(this);

        sv_goods = (ScrollView) view.findViewById(R.id.sv_goods);
    }

    /**
     * 获取商店  商品的详细信息
     * HttpPath.PATH_REALM + HttpPath.PATH_MODE + HttpPath.PATH_SHOPALL + shopid
     */
    public void getShopAll(String shopid) {
        final String PATH = HttpPath.PATH + HttpPath.GETSHOPNEW +
                "&shopid=" + shopid;

        RequestParams params = new RequestParams(PATH);

        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {

                        System.out.println("" + PATH);

                        System.out.println("商店 商品" + result);
                        pd.dismiss();

                        String results = MD5Util.getUnicode(result);

                        final ShopNew shopnew = GsonUtil.gsonIntance().gsonToBean(results, ShopNew.class);

                        shopNewUtils.saveMeetings(new Gson().toJson(shopnew.getMsg()));//缓存本地

                        //适配器 一级分类
                        gcAdapter = new GoodsClassilyAdapter(getActivity(), shopnew.getMsg());
                        lv_shop_goods.setAdapter(gcAdapter);

                        //二级分类

                        gcsAdapter = new GoodsClassilySecondAdapter(getActivity(), shopnew.getMsg().get(0).getChild());
                        hlv_shop_goods_second.setAdapter(gcsAdapter);


                        //默认第一条数据 主要内容
                        snAdapter = new ShopNewAdapter(getActivity(), shopnew.getMsg().get(0).getChild().get(0).getDet(), new CallbackCart() {
                            @Override
                            public void click(int position, int index) {

                            }
                        });
                        myGridView.setAdapter(snAdapter);

                        //点击一级分类
                        lv_shop_goods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                gcAdapter.changeSelected(position);//刷新

                                index_msg = position;

                                sv_goods.scrollTo(0, 0);

                                //二级分类
                                gcsAdapter = new GoodsClassilySecondAdapter(getActivity(), shopnew.getMsg().get(position).getChild());
                                hlv_shop_goods_second.setAdapter(gcsAdapter);

                                //默认第一条数据 主要内容
                                snAdapter = new ShopNewAdapter(getActivity(), shopnew.getMsg().get(position).getChild().get(0).getDet(), new CallbackCart() {
                                    @Override
                                    public void click(int position, int index) {

                                    }
                                });
                                //gv_shop_goods.setAdapter(snAdapter);
                                myGridView.setAdapter(snAdapter);


                            }
                        });
                        //点击二级分类
                        hlv_shop_goods_second.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                gcsAdapter.changeSelected(position);

                                index_child = position;

                                sv_goods.scrollTo(0, 0);

                                //默认第一条数据 主要内容
                                snAdapter = new ShopNewAdapter(getActivity(), shopnew.getMsg().get(index_msg).getChild().get(position).getDet(), new CallbackCart() {
                                    @Override
                                    public void click(int position, int index) {

                                    }
                                });
                                myGridView.setAdapter(snAdapter);

                            }
                        });

                        //点击 主要内容
                        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                ShopNew.MsgBean.ChildBean.DetBean detBean = shopnew.getMsg().get(index_msg).
                                        getChild().get(index_child).getDet().get(position);

                                //传值
                                String str_id = detBean.getId();
                                String str_instro = detBean.getInstro();
                                String str_foodName = detBean.getName();
                                String str_foodSellCount = detBean.getSellcount();
                                String str_foodPoint = detBean.getPoint();
                                String str_foodCost = detBean.getCost();

                                int cartNum = detBean.getCartnum();
                                String str_cartNum = String.valueOf(cartNum);

                                Intent intent = new Intent(getActivity(), CateFoodDetailsActivity.class);

                                intent.putExtra("id", str_id);
                                intent.putExtra("instro", str_instro);
                                intent.putExtra("foodName", str_foodName);
                                intent.putExtra("foodSellCount", str_foodSellCount);
                                intent.putExtra("foodPoint", str_foodPoint);
                                intent.putExtra("foodCost", str_foodCost);

                                intent.putExtra("cartNum", str_cartNum);

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

    /**
     * 获取购物车
     * 多此一举
     *
     * @param shopid
     */
    public void getShopCart(String shopid) {
        String PATH = HttpPath.PATH + HttpPath.GETCART + "shopid=" + shopid;

        System.out.println("" + PATH);
        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {

                        System.out.println("店铺" + result);
                        ShopCart shopCart = GsonUtil.gsonIntance().gsonToBean(result, ShopCart.class);

                        int size = shopCart.getMsg().getList().size();
                        double d_cost = 0;
                        int count = 0;
                        if (size == 0) {
                            return;
                        }
                        for (int i = 0; i < size; i++) {
                            d_cost = Double.parseDouble(shopCart.getMsg().getList().get(i).getCost());
                            count = shopCart.getMsg().getList().get(i).getCount();

                            totalPrice += d_cost * count;

                            count += count;
                        }

                        System.out.println("totalPrice = " + totalPrice);

                        //

                        if (limitcost == 0) {
                            but_sg_pay.setText("去支付");
                            but_sg_pay.setTextColor(Color.RED);
                        } else if (totalPrice >= limitcost) {
                            but_sg_pay.setText("去支付");
                            but_sg_pay.setTextColor(Color.RED);
                        } else if (totalPrice > 0 && totalPrice < limitcost) {
                            double add = limitcost - totalPrice;
                            but_sg_pay.setText("还差" + df.format(add) + "元");
                            but_sg_pay.setTextColor(Color.GRAY);
                        } else if (totalPrice == 0) {
                            but_sg_pay.setText("购物车为空");
                            but_sg_pay.setTextColor(Color.GRAY);
                        } else {
                            Toast.makeText(getActivity(), "错误", Toast.LENGTH_SHORT).show();
                        }
                        tv_sg_all_price.setText("合计：￥" + df.format(totalPrice) + "元");
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
     * 判断shopid是否正确 间接判断首页 是否请求数据成功
     *
     * @param shopid
     */
    public void isShopID(String shopid) {
        if (shopid == null) {
            pd.dismiss();
            System.out.println("没有定位成功");
            //定位没成功 加载缓存数据
            Gson gson = new Gson();

            Type listType = new TypeToken<List<ShopNew.MsgBean>>() {
            }.getType();

            final List<ShopNew.MsgBean> msgBeen = gson.fromJson(shopNewUtils.getMeetings(), listType);

            System.out.println("进入商铺 缓存数据 = " + msgBeen);

            //适配器 一级分类
            gcAdapter = new GoodsClassilyAdapter(getActivity(), msgBeen);
            lv_shop_goods.setAdapter(gcAdapter);

            //二级分类
            gcsAdapter = new GoodsClassilySecondAdapter(getActivity(), msgBeen.get(0).getChild());
            hlv_shop_goods_second.setAdapter(gcsAdapter);


            //默认第一条数据 主要内容
            snAdapter = new ShopNewAdapter(getActivity(), msgBeen.get(0).getChild().get(0).getDet(), new CallbackCart() {
                @Override
                public void click(int position, int index) {

                }
            });
            myGridView.setAdapter(snAdapter);

            //点击一级分类
            lv_shop_goods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    gcAdapter.changeSelected(position);//刷新

                    index_msg = position;

                    //二级分类
                    gcsAdapter = new GoodsClassilySecondAdapter(getActivity(), msgBeen.get(position).getChild());
                    hlv_shop_goods_second.setAdapter(gcsAdapter);

                    //默认第一条数据 主要内容
                    snAdapter = new ShopNewAdapter(getActivity(), msgBeen.get(position).getChild().get(0).getDet(), new CallbackCart() {
                        @Override
                        public void click(int position, int index) {

                        }
                    });
                    myGridView.setAdapter(snAdapter);


                }
            });

            //点击二级分类
            hlv_shop_goods_second.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    gcsAdapter.changeSelected(position);

                    //默认第一条数据 主要内容
                    snAdapter = new ShopNewAdapter(getActivity(), msgBeen.get(index_msg).getChild().get(position).getDet(), new CallbackCart() {
                        @Override
                        public void click(int position, int index) {

                        }
                    });
                    myGridView.setAdapter(snAdapter);

                }
            });

            //点击 主要内容
            myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    /*ShopNew.MsgBean.ChildBean.DetBean detBean = shopnew.getMsg().get(index_msg).
                            getChild().get(index_child).getDet().get(position);*/

                    ShopNew.MsgBean.ChildBean.DetBean detBean = msgBeen.get(index_msg).
                            getChild().get(index_child).getDet().get(position);

                    //传值
                    String str_id = "";
                    String str_instro = detBean.getInstro();
                    String str_foodName = detBean.getName();
                    String str_foodSellCount = detBean.getSellcount();
                    String str_foodPoint = detBean.getPoint();
                    String str_foodCost = detBean.getCost();

                    Intent intent = new Intent(getActivity(), CateFoodDetailsActivity.class);

                    intent.putExtra("id", str_id);
                    intent.putExtra("instro", str_instro);
                    intent.putExtra("foodName", str_foodName);
                    intent.putExtra("foodSellCount", str_foodSellCount);
                    intent.putExtra("foodPoint", str_foodPoint);
                    intent.putExtra("foodCost", str_foodCost);

                    startActivity(intent);
                }
            });


        } else {
            System.out.println("定位成功");
            //定位成功 加载网络数据
            getShopAll(shopid);
        }

    }

    /**
     * 判断网络连接
     */
    public void isNetworkUtil(String shopid) {
        if (NetworkUtils.isNotWorkAvilable(getActivity())) {
            if (NetworkUtils.getCurrentNetType(getActivity()) != null) {
                //由网络连接
                getShopAll(shopid);
            }
        } else {
            //无网络连接  加载本地存储数据
        }
    }

//    @Override
//    public void onFooterRefresh(PullToRefreshView view) {
//        pullToRefreshView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                pullToRefreshView.onFooterRefreshComplete();
//                //Toast.makeText(getActivity(), "加载更多数据", Toast.LENGTH_SHORT).show();
//            }
//
//        }, 1000);
//    }

//    @Override
//    public void onHeaderRefresh(PullToRefreshView view) {
//        pullToRefreshView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                pullToRefreshView.onHeaderRefreshComplete("更新于："
//                        + Calendar.getInstance().getTime().toLocaleString());
//                pullToRefreshView.onHeaderRefreshComplete();
//                //isShopID(shopid);
//
//
//                //Toast.makeText(getActivity(), "刷新成功!", Toast.LENGTH_SHORT).show();
//            }
//
//        }, 1000);
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_sg_pay:
                //去支付
                if (but_sg_pay.getText().toString().equals("去支付")) {

                    if (jingang.equals("")) {
                        //没有登录
                        dialog();
                        //Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                    } else if (jingang.equals("0")) {
                        //没有登录
                        dialog();
                        //Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                    } else if (jingang.equals("1")) {
                        //已登录
                        Intent intent = new Intent(getActivity(), OrderActivity.class);
                        startActivity(intent);
                    }
//                    Intent intent = new Intent(getActivity(), OrderActivity.class);
//                    startActivity(intent);
                }

                //startActivity(new Intent(getActivity(), OrderActivity.class));
                break;
            default:
                break;
        }

    }

    /**
     * 使用SharedPerferences和Gson搭建本地数据库
     */
    public class ShopNewUtils {
        //取
        public String getMeetings() {
            SharedPreferences sp = getActivity().getSharedPreferences("shopNewList", 0);
            return sp.getString("shopNew", "");
        }

        public boolean saveMeetings(String info) {
            //存
            SharedPreferences sp = getActivity().getSharedPreferences("shopNewList", 0);
            return sp.edit().putString("shopNew", info).commit();
        }
    }


    private int good_num = 0;

    /**
     * 三级分类（商品内容） 适配器
     */
    public class ShopNewAdapter extends BaseAdapter {
        private Context mContext;
        private List<ShopNew.MsgBean.ChildBean.DetBean> detBeen = new ArrayList<>();

        private CallbackCart callbackCart;
        //private int good_num;

        public ShopNewAdapter(Context mContext, List<ShopNew.MsgBean.ChildBean.DetBean> detBeen,
                              CallbackCart callbackCart) {
            this.mContext = mContext;
            this.detBeen = detBeen;
            this.callbackCart = callbackCart;
        }

        @Override
        public int getCount() {
            return detBeen.size();
        }

        @Override
        public Object getItem(int position) {
            return detBeen.get(position);
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
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_shopnew, null);

                vh.imager = (ImageView) convertView.findViewById(R.id.iv_item_shopnew_ss);
                vh.name = (TextView) convertView.findViewById(R.id.tv_item_shopnew_ss_title);
                vh.cost = (TextView) convertView.findViewById(R.id.tv_item_shopnew_ss_price);
                vh.sellcount = (TextView) convertView.findViewById(R.id.tv_item_shopnew_ss_num);

                //添加购物车
                vh.shopCart_num = (TextView) convertView.findViewById(R.id.tv_shopnew_num);
                vh.shopCart_add = (ImageView) convertView.findViewById(R.id.iv_shopnew_add);
                vh.shopCart_sub = (ImageView) convertView.findViewById(R.id.iv_shopnew_sub);

                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }

            // 组件赋值
            String imgPath = detBeen.get(position).getImg();
            String name = detBeen.get(position).getName();
            final String cost = detBeen.get(position).getCost();
            String sellcount = detBeen.get(position).getSellcount();
            String point = detBeen.get(position).getPoint();//不知道代表什么 先代表购物车内商品数量吧

            final String shopid = detBeen.get(position).getShopid();
            if (imgPath.equals("")) {
                vh.imager.setImageResource(R.mipmap.yibeitong001);

            } else {
                BitmapUtils bitmapUtils = new BitmapUtils(mContext);

                bitmapUtils.configDiskCacheEnabled(true);
                bitmapUtils.configMemoryCacheEnabled(false);
                bitmapUtils.display(vh.imager, imgPath);
            }
            final int cartNum = detBeen.get(position).getCartnum();


            vh.name.setText(name);
            vh.sellcount.setText("月售  " + sellcount);
            vh.cost.setText("￥" + cost);

            vh.shopCart_num.setText(point);

            vh.shopCart_num.setText("" + cartNum);
            //购物车为0  隐藏减少按钮和数量
            if (cartNum == 0) {
                vh.shopCart_sub.setVisibility(View.INVISIBLE);
                vh.shopCart_num.setVisibility(View.INVISIBLE);
            } else if (cartNum > 0) {
                vh.shopCart_sub.setVisibility(View.VISIBLE);
                vh.shopCart_num.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(mContext, "格式不正确", Toast.LENGTH_SHORT).show();
            }

            final String goodId = detBeen.get(position).getId();


            //添加购物车
            vh.shopCart_add.setOnClickListener(new View.OnClickListener() {
                //int cartNum = detBeen.get(position).getCartnum();

                @Override
                public void onClick(View v) {
                    String str_num = vh.shopCart_num.getText().toString();
                    good_num = Integer.valueOf(str_num);
                    vh.shopCart_add.setClickable(false);
                    //添加购物车  请求接口 成功则添加 反之不添加
                    //添加购物车

                    RequestParams params = new RequestParams(
                            HttpPath.PATH + HttpPath.ADD_SHOPCART
                                    + "shopid=" + shopid + "&num=" + 1 + "&gid=" + goodId
                    );
                    x.http().post(params,
                            new Callback.CommonCallback<String>() {
                                @Override
                                public void onSuccess(String result) {

                                    vh.shopCart_add.setClickable(true);
                                    System.out.println("添加购物车" + result);

                                    ShopCartReturn shopCartReturn =
                                            GsonUtil.gsonIntance().gsonToBean(result, ShopCartReturn.class);

                                    if (shopCartReturn.getMsg().isResult() == true) {
                                        AddShopCart addShopCart = GsonUtil.gsonIntance().gsonToBean(result, AddShopCart.class);

                                        double d_cost = Double.parseDouble(cost);

                                        if (addShopCart.isError() == false) {
                                            System.out.println("添加成功" + addShopCart.getMsg());
                                            if (good_num >= 0 && good_num < 100) {
                                                vh.shopCart_num.setVisibility(View.VISIBLE);
                                                vh.shopCart_sub.setVisibility(View.VISIBLE);
                                                good_num++;

                                                totalPrice += d_cost;

                                            }

                                            //
                                            if (limitcost == 0) {
                                                but_sg_pay.setText("去支付");
                                                but_sg_pay.setTextColor(Color.RED);
                                            } else if (totalPrice >= limitcost) {
                                                but_sg_pay.setText("去支付");
                                                but_sg_pay.setTextColor(Color.RED);
                                            } else if (totalPrice > 0 && totalPrice < limitcost) {
                                                double add = limitcost - totalPrice;
                                                but_sg_pay.setText("还差" + df.format(add) + "元");
                                                but_sg_pay.setTextColor(Color.GRAY);
                                            } else if (totalPrice == 0) {
                                                but_sg_pay.setText("购物车为空");
                                                but_sg_pay.setTextColor(Color.GRAY);
                                            } else {
                                                Toast.makeText(getActivity(), "错误", Toast.LENGTH_SHORT).show();
                                            }

                                            vh.shopCart_num.setText("" + good_num);

                                            tv_sg_all_price.setText("合计：￥" + df.format(totalPrice) + "元");

                                            //响应按钮点击事件,调用子定义接口，并传入View
                                            callbackCart.click(position, 2);

                                        } else {
                                            System.out.println("添加失败");
                                        }

                                    } else if (shopCartReturn.getMsg().isResult() == false) {
                                        Toast.makeText(getActivity(), "添加失败，库存不足", Toast.LENGTH_SHORT).show();
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
            });

            //减少购物车
            vh.shopCart_sub.setOnClickListener(new View.OnClickListener() {
                int cartNum = detBeen.get(position).getCartnum();
                //long lastClick;

                @Override
                public void onClick(View v) {
                    vh.shopCart_sub.setClickable(false);
                    //减少购物车  请求接口 成功则减少 反之 不减少

                    String str_num = vh.shopCart_num.getText().toString();
                    good_num = Integer.valueOf(str_num);

                    if (good_num > 0) {
                        //addShopCart(shopid, -1, str_id);
                        String PATH = HttpPath.PATH + HttpPath.ADD_SHOPCART + "&shopid=" + shopid + "&num=-1" + "&gid=" + goodId;
                        RequestParams params = new RequestParams(PATH);
                        x.http().post(params,
                                new Callback.CommonCallback<String>() {
                                    @Override
                                    public void onSuccess(String result) {
                                        vh.shopCart_sub.setClickable(true);
                                        System.out.println("购物车操作" + result);

                                        ShopCartReturn shopCartReturn =
                                                GsonUtil.gsonIntance().gsonToBean(result, ShopCartReturn.class);

                                        if (shopCartReturn.getMsg().isResult() == true) {
                                            double a_cost = Double.parseDouble(detBeen.get(position).getCost());

                                            totalPrice -= a_cost;
                                            good_num--;

                                            if (limitcost == 0) {
                                                but_sg_pay.setText("去支付");
                                                but_sg_pay.setTextColor(Color.RED);
                                            } else if (totalPrice >= limitcost) {
                                                but_sg_pay.setText("去支付");
                                                but_sg_pay.setTextColor(Color.RED);
                                            } else if (totalPrice > 0 && totalPrice < limitcost) {
                                                double add = limitcost - totalPrice;
                                                but_sg_pay.setText("还差" + df.format(add) + "元");
                                                but_sg_pay.setTextColor(Color.GRAY);
                                            } else if (totalPrice == 0) {
                                                but_sg_pay.setText("购物车为空");
                                                but_sg_pay.setTextColor(Color.GRAY);
                                            } else {
                                                Toast.makeText(getActivity(), "错误", Toast.LENGTH_SHORT).show();
                                            }

                                            tv_sg_all_price.setText("合计：￥" + df.format(totalPrice) + "元");

                                            vh.shopCart_num.setText("" + good_num);

                                            if (good_num == 0) {

                                            }
                                        } else if (shopCartReturn.getMsg().isResult() == false) {
                                            Toast.makeText(getActivity(), "减少失败，库存不足", Toast.LENGTH_SHORT).show();
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


                    } else if (good_num == 0) {
                        vh.shopCart_sub.setClickable(true);
                    }

                }
            });

            return convertView;
        }

        public class ViewHolder {
            private ImageView imager;
            private TextView name, cost, sellcount;

            private TextView shopCart_num;
            private ImageView shopCart_add, shopCart_sub;

        }

        /**
         * 购物车操作
         * num = 1 为添加
         * num = -1 为减少
         *
         * @param shopid
         * @param num
         * @param gid
         */
        public void addShopCart(String shopid, final int num, String gid) {
            String PATH = HttpPath.PATH + HttpPath.ADD_SHOPCART + "shopid=" + shopid + "&num=" + num + "&gid=" + gid;

            RequestParams params = new RequestParams(PATH);
            x.http().post(params,
                    new Callback.CommonCallback<String>() {

                        @Override
                        public void onSuccess(String result) {
                            if (num == 1) {
                                System.out.println("添加购物车成功");
                            } else if (num == -1) {
                                System.out.println("减少购物车成功");
                            }

                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            if (num == 1) {
                                System.out.println("添加购物车失败");
                            } else if (num == -1) {
                                System.out.println("减少购物车失败");
                            }
                        }

                        @Override
                        public void onCancelled(CancelledException cex) {

                        }

                        @Override
                        public void onFinished() {

                        }
                    });

        }
    }

    /**
     *
     */
    /**
     * 提示框
     */
    protected void dialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
        builder.setMessage("还未登录，确定要登录吗？");
        builder.setTitle("还未登录");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //设置你的操作事项
                startActivity(new Intent(getActivity(), LoginRegisterActivity.class));
            }
        });

        builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.create().show();

    }

}
