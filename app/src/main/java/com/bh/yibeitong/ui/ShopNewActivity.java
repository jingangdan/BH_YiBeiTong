package com.bh.yibeitong.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bh.yibeitong.R;
import com.bh.yibeitong.adapter.GoodsClassilyAdapter;
import com.bh.yibeitong.adapter.GoodsClassilySecondAdapter;
import com.bh.yibeitong.bean.AddShopCart;
import com.bh.yibeitong.bean.ShopCart;
import com.bh.yibeitong.bean.ShopCartReturn;
import com.bh.yibeitong.bean.ShopNew;
import com.bh.yibeitong.refresh.MyGridView;
import com.bh.yibeitong.ui.search.SearchActivity;
import com.bh.yibeitong.utils.CodeUtils;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.utils.MD5Util;
import com.bh.yibeitong.view.CustomDialog;
import com.bh.yibeitong.view.HorizontalListView;
import com.bh.yibeitong.view.UserInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.BuildConfig;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingang on 2017/6/13.
 * 新版 进入商店后
 */
public class ShopNewActivity extends Activity implements View.OnClickListener {
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
    private ShopNewUtils shopNewUtils;

    private ScrollView sv_goods;
    private MyGridView myGridView;

    //本地缓存
    UserInfo userInfo;
    String jingang;

    DecimalFormat df;

    //购物车
    private double totalPrice = 0;

//    private TextView tv_sg_all_price;
//    private Button but_sg_pay;

    /*购物车UI*/
    private Button but_pay;
    private TextView tv_all_pay, tv_shopcart_num;
    private FrameLayout fl_shopcart;
    private int cartnum = 0;

    /*起送费*/
    private double limitcost = 0;
    private TextView tv_limitcost;

    /*接收页面传值*/
    private Intent intent;
    private String shopid = "", shopname, startTime, mapphone, address, lat, lng;

    /*店铺信息*/
    private LinearLayout lin_shop_manage;
    private ImageView iv_isVisibile;
    private boolean isVisibile = false;

    private TextView tv_shop_name, tv_shop_starttime, tv_shop_mapphone, tv_shop_address;

    /*返回上一层  搜索*/
    private ImageView iv_back, iv_search;

    private List<ShopNew.MsgBean.ChildBean.DetBean> foodList = new ArrayList<>();

    /*购物车商品id字符串*/
    private String gid = "";
    private List<ShopCart.MsgBean.ListBean> shopMsg = new ArrayList<>();

    /*接口地址*/
    private String PATH = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_shop);

        x.Ext.init(getApplication());
        x.Ext.setDebug(BuildConfig.DEBUG);

        initData();

        if (!(shopid.equals(""))) {
            isShopID(shopid);
        } else {
        }

        getShopCart(shopid);

    }

    /**
     * 组件 初始化
     */
    public void initData() {

        intent = getIntent();
        shopid = intent.getStringExtra("shopid");
        shopname = intent.getStringExtra("shopname");
        startTime = intent.getStringExtra("startTime");
        mapphone = intent.getStringExtra("mapphone");
        address = intent.getStringExtra("address");

        lat = intent.getStringExtra("lat");
        lng = intent.getStringExtra("lng");


        userInfo = new UserInfo(getApplication());
        df = new DecimalFormat("###.00");

        jingang = userInfo.getLogin();

        limitcost = Double.parseDouble(userInfo.getShopDet().trim());

        pd = ProgressDialog.show(this, "", "请稍候");
        pd.setProgress(100);

        /*pd显示5秒*/
        new Thread(new Runnable() {

            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                int progress = 0;

                while (System.currentTimeMillis() - startTime < 5000) {
                    try {
                        progress += 10;
                        pd.setProgress(progress);
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        pd.dismiss();
                    }
                }

                pd.dismiss();
            }
        }).start();

        shopNewUtils = new ShopNewUtils();
        //一级分类
        lv_shop_goods = (ListView) findViewById(R.id.lv_shop_goods);

        //二级分类
        hlv_shop_goods_second = (HorizontalListView) findViewById(R.id.hlv_shop_goods_second);

        myGridView = (MyGridView) findViewById(R.id.myGridView_goods);

        sv_goods = (ScrollView) findViewById(R.id.sv_goods);

        /*店铺信息*/
        lin_shop_manage = (LinearLayout) findViewById(R.id.lin_shop_manage);
        iv_isVisibile = (ImageView) findViewById(R.id.iv_manage_visibile);
        iv_isVisibile.setOnClickListener(this);

        tv_shop_name = (TextView) findViewById(R.id.tv_shop_name);
        tv_shop_starttime = (TextView) findViewById(R.id.tv_shop_starttime);
        tv_shop_mapphone = (TextView) findViewById(R.id.tv_shop_mapphone);
        tv_shop_address = (TextView) findViewById(R.id.tv_shop_address);

        tv_shop_name.setText("" + shopname);
        tv_shop_starttime.setText("营业时间：" + startTime);
        tv_shop_mapphone.setText("店铺电话：" + mapphone);
        tv_shop_address.setText("店铺地址：" + address);

        //购物车
//        tv_sg_all_price = (TextView) findViewById(R.id.tv_sg_all_price);
//        but_sg_pay = (Button) findViewById(R.id.but_sg_pay);
//
//        but_sg_pay.setOnClickListener(this);

        /*购物车UI*/
        but_pay = (Button) findViewById(R.id.but_pay);
        tv_all_pay = (TextView) findViewById(R.id.tv_all_pay);
        tv_shopcart_num = (TextView) findViewById(R.id.tv_shopcart_num);
        fl_shopcart = (FrameLayout) findViewById(R.id.fl_shopcart);

        but_pay.setOnClickListener(this);
        fl_shopcart.setOnClickListener(this);

        iv_back = (ImageView) findViewById(R.id.iv_shop_back);
        iv_back.setOnClickListener(this);

        iv_search = (ImageView) findViewById(R.id.iv_shop_search);
        iv_search.setOnClickListener(this);

        tv_limitcost = (TextView) findViewById(R.id.tv_new_shop_limist);
        tv_limitcost.setText("满" + limitcost + "元起送");


    }

    /*支付按钮状态*/
    public void goPay() {
        but_pay.setText("去支付");
        but_pay.setTextColor(Color.WHITE);
        but_pay.setBackgroundColor(Color.rgb(162, 203, 52));
    }

    /*未达到支付要求按钮状态*/
    public void noGoPay() {
        but_pay.setTextColor(Color.GRAY);
        but_pay.setBackgroundColor(Color.rgb(204, 204, 204));
    }

    /*数据改变时向上一层传递信息*/
    public void setResult(String gid, int cartNum, int cartnum, double allpay) {
        intent = new Intent();
        intent.putExtra("gid", gid);
        intent.putExtra("cartNum", cartNum);
        intent.putExtra("cartnum", cartnum);
        intent.putExtra("allpay", allpay);

        setResult(CodeUtils.REQUEST_CODE_NEWSHOP, intent);
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
                        System.out.println("购物车" + result);

                        gid = "";
                        shopMsg.clear();

                        totalPrice = 0;
                        try {
                            JSONObject response = new JSONObject(result);

                            if (response.get("msg").toString().equals("[]")) {
                                System.out.println("没有数据");
                                but_pay.setText("购物车为空");
                                noGoPay();
                            } else {
                                ShopCart shopCart = GsonUtil.gsonIntance().gsonToBean(result, ShopCart.class);
                                shopMsg = shopCart.getMsg().getList();

                                totalPrice = shopCart.getMsg().getSurecost();

                                //显示购物车数量
                                tv_shopcart_num.setText("" + shopCart.getMsg().getSumcount());

                                /*判断快递情况*/
                                if (shopCart.getMsg().isOnlynewtype() == true) {
                                    System.out.println("只有快递");

                                    //判断总计小于1的情况
                                    if (totalPrice < 1) {
                                        tv_all_pay.setText("￥" + "0" + df.format(totalPrice) + "元");
                                    } else {
                                        tv_all_pay.setText("￥" + df.format(totalPrice) + "元");
                                    }
                                    goPay();

                                } else {
                                    System.out.println("不是只有快递");

                                    if (shopCart.getMsg().getList().size() == 0) {
                                        tv_shopcart_num.setText("" + 0);
                                        tv_all_pay.setText("￥0.00");
                                        but_pay.setText("购物车为空");

                                        noGoPay();

                                    } else if (shopCart.getMsg().getList().size() > 0) {

                                        if (limitcost == 0) {
                                            goPay();
                                        } else if (totalPrice >= limitcost) {
                                            goPay();
                                        } else if (totalPrice > 0 && totalPrice < limitcost) {
                                            double add = limitcost - totalPrice;
                                            but_pay.setText("还差" + df.format(add) + "元");
                                            noGoPay();

                                        } else if (totalPrice == 0) {
                                            but_pay.setText("购物车为空");
                                            noGoPay();
                                        } else {

                                        }
                                        //判断总计小于1的情况
                                        System.out.println("totalPrice=" + totalPrice);
                                        if (totalPrice < 1) {
                                            tv_all_pay.setText("￥" + "0" + df.format(totalPrice) + "元");
                                        } else {
                                            tv_all_pay.setText("￥" + df.format(totalPrice) + "元");
                                        }

                                        if (shopMsg.size() > 0) {
                                            //遍历首页商品列表
                                            for (int j = 0; j < shopMsg.size(); j++) {
                                                gid = gid + shopMsg.get(j).getId();
                                            }
                                        }

                                        /**
                                         * 1.找出首页购物车数量num1 >0的，并记录id1
                                         * 2.将id1于购物车id2作比
                                         * 较（不同：首页购物车数量清零  相同：将num2赋值给num1）
                                         */

                                        /*遍历商品列表*/
                                        for (int i = 0; i < foodList.size(); i++) {

                                            int num1 = foodList.get(i).getCartnum();//首页数量
                                            String id1 = foodList.get(i).getId();//首页

                                            if (num1 > 0) {
                                                if (gid.indexOf(foodList.get(i).getId()) == -1) {
                                                    foodList.get(i).setCartnum(0);
                                                }

                                            }

                                            if (shopMsg.size() > 0) {
                                                //遍历商品列表
                                                for (int j = 0; j < shopMsg.size(); j++) {
                                                    int num2 = shopMsg.get(j).getCount();//购物车数量
                                                    String id2 = "" + shopMsg.get(j).getId(); //购物车

                                                    if (id1.equals(id2)) {
                                                        if (num1 == num2) {
                                                            //相同 不动
                                                        } else {
                                                            //不同且大于0 修改
                                                            foodList.get(i).setCartnum(num2);
                                                        }
                                                    }
                                                }
                                            } else {
                                                int cartNum = foodList.get(i).getCartnum();
                                                if (cartNum > 0) {
                                                    foodList.get(i).setCartnum(0);
                                                }
                                            }

                                        }

                                    }

                                    tv_all_pay.setText("￥" + df.format(totalPrice) + "元");

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //teseCateInfoAdapter.notifyDataSetChanged();
                        snAdapter.notifyDataSetChanged();


//
//
//                        JSONObject response = null;
//                            try {
//                                response = new JSONObject(result);
//                                if(response.get("msg").toString().equals("[]")){
//                                    System.out.println("没有数据");
//                                }else{
//                                    ShopCart shopCart = GsonUtil.gsonIntance().gsonToBean(result, ShopCart.class);
//
//                                    cartnum = shopCart.getMsg().getSumcount();
//
//                                    int size = shopCart.getMsg().getList().size();
//                                    double d_cost = 0;
//                                    int count = 0;
//                                    if (size == 0) {
//                                        return;
//                                    }
//                                    for (int i = 0; i < size; i++) {
//                                        d_cost = Double.parseDouble(shopCart.getMsg().getList().get(i).getCost());
//                                        count = shopCart.getMsg().getList().get(i).getCount();
//                                        totalPrice += d_cost * count;
//
//                                        count += count;
//                                    }
//
//                                    if (limitcost == 0) {
//                                        goPay();
//                                    } else if (totalPrice >= limitcost) {
//                                        goPay();
//                                    } else if (totalPrice > 0 && totalPrice < limitcost) {
//                                        double add = limitcost - totalPrice;
//                                        but_pay.setText("还差" + df.format(add) + "元");
//                                        noGoPay();
//                                    } else if (totalPrice == 0) {
//                                        but_pay.setText("购物车为空");
//                                        noGoPay();
//                                    } else {
//                                        Toast.makeText(ShopNewActivity.this, "错误", Toast.LENGTH_SHORT).show();
//                                    }
//                                    tv_all_pay.setText("￥" + df.format(totalPrice) + "元");
//                                    tv_shopcart_num.setText(""+cartnum);
//
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }

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
     * 获取商店  商品的详细信息
     * HttpPath.PATH_REALM + HttpPath.PATH_MODE + HttpPath.PATH_SHOPALL + shopid
     */
    public void getShopAll(String shopid) {
//        String PATH = HttpPath.PATH + HttpPath.GETSHOPNEW +
//                "&shopid=" + shopid;

        PATH = HttpPath.PATH_HEAD+HttpPath.PATH_DATA+(System.currentTimeMillis()/1000)+"&"+HttpPath.GETSHOPNEW+
                "shopid=" + shopid+"&sign="+
        MD5Util.getMD5String(HttpPath.PATH_DATA+(System.currentTimeMillis()/1000)+"&"+HttpPath.GETSHOPNEW+
                "shopid=" + shopid+"&"+HttpPath.PATH_BAIHAI);


        RequestParams params = new RequestParams(PATH);

        System.out.println("商店 商品" + PATH);

        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("商店 商品" + result);
                        pd.dismiss();

                        String results = MD5Util.getUnicode(result);

                        final ShopNew shopnew = GsonUtil.gsonIntance().gsonToBean(results, ShopNew.class);

                        foodList = shopnew.getMsg().get(index_msg).getChild().get(0).getDet();

                        shopNewUtils.saveMeetings(new Gson().toJson(shopnew.getMsg()));//缓存本地

                        //适配器 一级分类
                        gcAdapter = new GoodsClassilyAdapter(ShopNewActivity.this, shopnew.getMsg());
                        lv_shop_goods.setAdapter(gcAdapter);

                        //二级分类
                        gcsAdapter = new GoodsClassilySecondAdapter(ShopNewActivity.this, shopnew.getMsg().get(0).getChild());
                        hlv_shop_goods_second.setAdapter(gcsAdapter);

                        //默认第一条数据 主要内容
                        snAdapter = new ShopNewAdapter(ShopNewActivity.this,
                                shopnew.getMsg().get(0).getChild().get(0).getDet());
                        myGridView.setAdapter(snAdapter);

                        //点击一级分类
                        lv_shop_goods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                gcAdapter.changeSelected(position);//刷新

                                index_child = 0;

                                index_msg = position;
                                foodList = shopnew.getMsg().get(position).getChild().get(0).getDet();

                                sv_goods.scrollTo(0, 0);

                                //二级分类
                                gcsAdapter = new GoodsClassilySecondAdapter(ShopNewActivity.this,
                                        shopnew.getMsg().get(position).getChild());
                                hlv_shop_goods_second.setAdapter(gcsAdapter);

                                //默认第一条数据 主要内容
                                snAdapter = new ShopNewAdapter(ShopNewActivity.this,
                                        shopnew.getMsg().get(position).getChild().get(0).getDet());
                                myGridView.setAdapter(snAdapter);

                            }
                        });
                        //点击二级分类
                        hlv_shop_goods_second.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                gcsAdapter.changeSelected(position);

                                index_child = position;

                                foodList = shopnew.getMsg().get(index_msg).getChild().get(position).getDet();

                                sv_goods.scrollTo(0, 0);

                                //默认第一条数据 主要内容
                                snAdapter = new ShopNewAdapter(ShopNewActivity.this,
                                        shopnew.getMsg().get(index_msg).getChild().get(position).getDet());
                                myGridView.setAdapter(snAdapter);

                            }
                        });


                        //点击 主要内容
                        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                                ShopNew.MsgBean.ChildBean.DetBean detBean = shopnew.getMsg().get(index_msg).
//                                        getChild().get(index_child).getDet().get(position);

                                foodList = shopnew.getMsg().get(index_msg).getChild().get(index_child).getDet();

                                //传值
                                String str_id = foodList.get(position).getId();
                                String str_instro = foodList.get(position).getInstro();
                                String str_foodName = foodList.get(position).getName();
                                String str_foodSellCount = foodList.get(position).getSellcount();
                                String str_foodPoint = foodList.get(position).getPoint();
                                String str_foodCost = foodList.get(position).getCost();

                                int cartNum = foodList.get(position).getCartnum();
                                String str_cartNum = String.valueOf(cartNum);

//                                String str_id = detBean.getId();
//                                String str_instro = detBean.getInstro();
//                                String str_foodName = detBean.getName();
//                                String str_foodSellCount = detBean.getSellcount();
//                                String str_foodPoint = detBean.getPoint();
//                                String str_foodCost = detBean.getCost();
//
//                                int cartNum = detBean.getCartnum();
//                                String str_cartNum = String.valueOf(cartNum);

                                Intent intent = new Intent(ShopNewActivity.this, CateFoodDetailsActivity.class);

                                intent.putExtra("id", str_id);
                                intent.putExtra("instro", str_instro);
                                intent.putExtra("foodName", str_foodName);
                                intent.putExtra("foodSellCount", str_foodSellCount);
                                intent.putExtra("foodPoint", str_foodPoint);
                                intent.putExtra("foodCost", str_foodCost);

                                intent.putExtra("cartNum", str_cartNum);

                                //startActivity(intent);
                                startActivityForResult(intent, CodeUtils.REQUEST_CODE_NEWSHOP);
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
            gcAdapter = new GoodsClassilyAdapter(ShopNewActivity.this, msgBeen);
            lv_shop_goods.setAdapter(gcAdapter);

            //二级分类
            gcsAdapter = new GoodsClassilySecondAdapter(ShopNewActivity.this, msgBeen.get(0).getChild());
            hlv_shop_goods_second.setAdapter(gcsAdapter);


            //默认第一条数据 主要内容
            snAdapter = new ShopNewAdapter(ShopNewActivity.this,
                    msgBeen.get(0).getChild().get(0).getDet());
            myGridView.setAdapter(snAdapter);

            //点击一级分类
            lv_shop_goods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    gcAdapter.changeSelected(position);//刷新

                    index_msg = position;

                    //二级分类
                    gcsAdapter = new GoodsClassilySecondAdapter(ShopNewActivity.this, msgBeen.get(position).getChild());
                    hlv_shop_goods_second.setAdapter(gcsAdapter);

                    //默认第一条数据 主要内容
                    snAdapter = new ShopNewAdapter(ShopNewActivity.this,
                            msgBeen.get(position).getChild().get(0).getDet());
                    myGridView.setAdapter(snAdapter);


                }
            });

            //点击二级分类
            hlv_shop_goods_second.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    gcsAdapter.changeSelected(position);

                    //默认第一条数据 主要内容
                    snAdapter = new ShopNewAdapter(ShopNewActivity.this,
                            msgBeen.get(index_msg).getChild().get(position).getDet());
                    myGridView.setAdapter(snAdapter);

                }
            });

            //点击 主要内容
            myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    ShopNew.MsgBean.ChildBean.DetBean detBean = msgBeen.get(index_msg).
                            getChild().get(position).getDet().get(position);

                    //传值
                    String str_id = "";
                    String str_instro = detBean.getInstro();
                    String str_foodName = detBean.getName();
                    String str_foodSellCount = detBean.getSellcount();
                    String str_foodPoint = detBean.getPoint();
                    String str_foodCost = detBean.getCost();

                    Intent intent = new Intent(ShopNewActivity.this, CateFoodDetailsActivity.class);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_shop_back:
                //返回上一层
                ShopNewActivity.this.finish();
                break;

            case R.id.iv_shop_search:
                //搜索
                intent = new Intent(ShopNewActivity.this, SearchActivity.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                intent.putExtra("shopid", shopid);
                startActivity(intent);
                break;

            case R.id.iv_manage_visibile:
                //是否显示店铺信息
                if (!isVisibile) {
                    lin_shop_manage.setVisibility(View.VISIBLE);
                    isVisibile = true;
                } else {
                    lin_shop_manage.setVisibility(View.GONE);
                    isVisibile = false;
                }

                break;

            case R.id.but_pay:
                //去支付
                if (but_pay.getText().toString().equals("去支付")) {

                    if (jingang.equals("")) {
                        //没有登录
                        dialog();
                    } else if (jingang.equals("0")) {
                        //没有登录
                        dialog();
                    } else if (jingang.equals("1")) {
                        //已登录
                        Intent intent = new Intent(ShopNewActivity.this, OrderActivity.class);
                        startActivity(intent);
                    }
                }

                break;

            case R.id.fl_shopcart:
                intent = new Intent(ShopNewActivity.this, ShopCarActivity.class);
                intent.putExtra("shopid", shopid);
                //startActivity(intent);
                startActivityForResult(intent, CodeUtils.REQUEST_CODE_NEWSHOP);
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
            SharedPreferences sp = getSharedPreferences("shopNewList", 0);
            return sp.getString("shopNew", "");
        }

        public boolean saveMeetings(String info) {
            //存
            SharedPreferences sp = getSharedPreferences("shopNewList", 0);
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

        public ShopNewAdapter(Context mContext, List<ShopNew.MsgBean.ChildBean.DetBean> detBeen) {
            this.mContext = mContext;
            this.detBeen = detBeen;
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

            final String shopid = detBeen.get(position).getShopid();
            if (imgPath.equals("")) {
                vh.imager.setImageResource(R.mipmap.yibeitong001);

            } else {
                x.image().bind(vh.imager, imgPath);
            }
            final int cartNum = detBeen.get(position).getCartnum();

            vh.name.setText(name);
            vh.sellcount.setText("月售  " + sellcount);
            vh.cost.setText("￥" + cost);


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

                @Override
                public void onClick(View v) {
                    vh.shopCart_add.setClickable(false);

                    good_num = Integer.valueOf(vh.shopCart_num.getText().toString());
                    cartnum = Integer.parseInt(tv_shopcart_num.getText().toString());
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
                                                cartnum++;

                                                totalPrice += d_cost;

                                            }

                                            //
                                            if (limitcost == 0) {
//                                                but_sg_pay.setText("去支付");
//                                                but_sg_pay.setTextColor(Color.RED);
                                                goPay();
                                            } else if (totalPrice >= limitcost) {
//                                                but_sg_pay.setText("去支付");
//                                                but_sg_pay.setTextColor(Color.RED);
                                                goPay();
                                            } else if (totalPrice > 0 && totalPrice < limitcost) {
                                                double add = limitcost - totalPrice;
//                                                but_sg_pay.setText("还差" + df.format(add) + "元");
//                                                but_sg_pay.setTextColor(Color.GRAY);
                                                but_pay.setText("还差" + df.format(add) + "元");
                                                noGoPay();
                                            }

                                            vh.shopCart_num.setText("" + good_num);
                                            tv_shopcart_num.setText(""+cartnum);
                                            tv_all_pay.setText("￥" + df.format(totalPrice) + "元");

                                            setResult(goodId, good_num, cartnum, totalPrice);

                                            //tv_sg_all_price.setText("合计：￥" + df.format(totalPrice) + "元");

                                        } else {
                                            System.out.println("添加失败");
                                        }

                                    } else if (shopCartReturn.getMsg().isResult() == false) {
                                        Toast.makeText(ShopNewActivity.this, "添加失败，库存不足", Toast.LENGTH_SHORT).show();
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
                @Override
                public void onClick(View v) {
                    vh.shopCart_sub.setClickable(false);
                    //减少购物车  请求接口 成功则减少 反之 不减少

                    good_num = Integer.valueOf(vh.shopCart_num.getText().toString());
                    cartnum = Integer.parseInt(tv_shopcart_num.getText().toString());

                    if (good_num > 0) {
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
                                            cartnum--;

                                            if (limitcost == 0) {
//                                                but_sg_pay.setText("去支付");
//                                                but_sg_pay.setTextColor(Color.RED);
                                                goPay();
                                            } else if (totalPrice >= limitcost) {
//                                                but_sg_pay.setText("去支付");
//                                                but_sg_pay.setTextColor(Color.RED);
                                                goPay();
                                            } else if (totalPrice > 0 && totalPrice < limitcost) {
                                                double add = limitcost - totalPrice;
//                                                but_sg_pay.setText("还差" + df.format(add) + "元");
//                                                but_sg_pay.setTextColor(Color.GRAY);
                                                but_pay.setText("还差" + df.format(add) + "元");
                                                noGoPay();
                                            } else if (totalPrice == 0) {
//                                                but_sg_pay.setText("购物车为空");
//                                                but_sg_pay.setTextColor(Color.GRAY);
                                                but_pay.setText("购物车为空");
                                                noGoPay();
                                            } else {
                                                Toast.makeText(ShopNewActivity.this, "错误", Toast.LENGTH_SHORT).show();
                                            }
                                            //tv_sg_all_price.setText("合计：￥" + df.format(totalPrice) + "元");

                                            vh.shopCart_num.setText("" + good_num);
                                            tv_shopcart_num.setText(""+cartnum);
                                            tv_all_pay.setText("￥" + df.format(totalPrice) + "元");

                                            setResult(goodId, good_num, cartnum, totalPrice);

                                        } else if (shopCartReturn.getMsg().isResult() == false) {
                                            Toast.makeText(ShopNewActivity.this, "减少失败，库存不足", Toast.LENGTH_SHORT).show();
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
//                        vh.shopCart_sub.setVisibility(View.GONE);
//                        vh.shopCart_num.setText(View.GONE);
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

            //改版样式
//            private ImageView iv_img, iv_sub, iv_add;
//            private TextView tv_good_name, tv_good_cost, tv_good_num;

        }

    }

    /**
     * 提示框
     */
    protected void dialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(ShopNewActivity.this);
        builder.setMessage("还未登录，确定要登录吗？");
        builder.setTitle("还未登录");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //设置你的操作事项
                startActivityForResult(new Intent(ShopNewActivity.this, LoginRegisterActivity.class), CodeUtils.REQUEST_CODE_NEWSHOP);
            }
        });

        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.create().show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CodeUtils.REQUEST_CODE_NEWSHOP) {
            if (resultCode == CodeUtils.REQUEST_CODE_CATEFOOD ||
                    resultCode == CodeUtils.REQUEST_CODE_SHOPCART) {

                getShopCart(shopid);

//                Bundle bundle = data.getExtras();
//                String gid = bundle.getString("gid");
//                int cartNum = bundle.getInt("cartNum");
//                int cartnum = bundle.getInt("cartnum");
//
//                tv_shopcart_num.setText("" + cartnum);
//
//
//                for (int i = 0; i < foodList.size(); i++) {
//                    if (gid.equals(foodList.get(i).getId())) {
//                        foodList.get(i).setCartnum(cartNum);
//                    } else if (gid.equals("000")) {
//                        foodList.get(i).setCartnum(cartNum);
//                    }
//                }
//
//                totalPrice = bundle.getDouble("allpay");
//
//
//                System.out.println("ssssss"+totalPrice);
//                System.out.println("ssssssss"+limitcost);
//                if (limitcost == 0) {
//                    goPay();
//
//                } else if (totalPrice >= limitcost) {
//                    goPay();
//                } else if (totalPrice > 0 && totalPrice < limitcost) {
//                    double add = limitcost - totalPrice;
//
//                    but_pay.setText("还差" + df.format(add) + "元");
//                    noGoPay();
//                } else if (totalPrice == 0) {
//                    but_pay.setText("购物车为空");
//                    noGoPay();
//                } else {
//                    //toast("错误");
//                }
//
//                tv_all_pay.setText("￥" + df.format(totalPrice) + "元");
//
//                snAdapter.notifyDataSetChanged();

//                setResult(gid, cartNum, cartnum, totalPrice);

                setResult("", 0, cartnum, totalPrice);
            } else if (resultCode == CodeUtils.REQUEST_CODE_LOGIN
                    || resultCode == CodeUtils.REQUEST_CODE_QUICK_LOGIN) {
                Bundle bundle = data.getExtras();
                //jingang = bundle.getString("jingang");
                jingang = userInfo.getLogin();
                //setResult("", cartNum, cartnum, totalPrice);
            }
        }

    }

}
