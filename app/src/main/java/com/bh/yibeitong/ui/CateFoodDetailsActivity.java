package com.bh.yibeitong.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bh.yibeitong.Interface.CallbackCart;
import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.AddShopCart;
import com.bh.yibeitong.bean.CeShi;
import com.bh.yibeitong.bean.GoodsDetails;
import com.bh.yibeitong.bean.ShopCart;
import com.bh.yibeitong.bean.ShopCartReturn;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.CustomDialog;
import com.bh.yibeitong.view.UserInfo;
import com.lidroid.xutils.BitmapUtils;

import org.xutils.BuildConfig;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingang on 2016/10/25.
 * 主页商品详情
 */
public class CateFoodDetailsActivity extends BaseTextActivity {
    private WebView contentWeb;

    private WebSettings webSettings;

    private Intent intent;

    private CallbackCart callbackCart;

    //接收页面跳转传值
    String instro, foodName, foodSellCount, foodPoint, foodCost, foodGoodattr;
    int cartNum;
    String str_id;

    //
    private TextView tv_foodderails_name, tv_fooddetails_sellcount, tv_foodderails_price, tv_fooddetails_point;

    private List<CeShi.MsgBean.CatefoodslistBean> catefoodslistBeanList = new ArrayList<>();

    private List<GoodsDetails.MsgBean.GoodsBean.ImgBean> imgBeen = new ArrayList<>();

    private List<ShopCart.MsgBean.ListBean> listBean;

    /**
     * ViewPager实现图片左右滑动查看
     */
    private ViewPager viewPager;
    private ImageView[] tips;//提示性点点数组
    //private int[] images;//图片ID数组
    private int currentPage = 0;//当前展示的页码
    private LinearLayout tipsBox;

    //本地缓存一哈子
    private ShopFoodDetailsUtils shopFoodDetailsUtils;

    //添加购物车
    private TextView tv_catefood_num;
    private ImageView iv_catefood_add, iv_catefood_sub;

    private double totalPrice = 0;//商品总价
    private int sumCount = 0;//商品总数量
    private double limitcost = 0;

    /**/
    private TextView tv_catefood_all_price;
    private TextView tv_catefood_all_num;
    private Button but_catefood_pay;

    //本地缓存
    UserInfo userInfo;
    private String shopid;

    String jingang;

    DecimalFormat df;

    /*跳转到购物车*/
    private FrameLayout fml_shopcar;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_catefood_details);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        x.Ext.init(this.getApplication());
        x.Ext.setDebug(BuildConfig.DEBUG);

        setTitleName("详情");
        setTitleBack(true, 0);
        initData();

        intent = getIntent();
        str_id = intent.getStringExtra("id");

        instro = intent.getStringExtra("instro");//详细图片

        /*foodName = intent.getStringExtra("foodName");//商品名称
        foodSellCount = intent.getStringExtra("foodSellCount");//商品销量

        foodPoint = intent.getStringExtra("foodPoint");
        foodCost = intent.getStringExtra("foodCost");
        foodGoodattr = intent.getStringExtra("foodGoodattr");*/

        if (cartNum == 0) {
            //购物车为0  隐藏减少按钮和数量
            iv_catefood_sub.setVisibility(View.INVISIBLE);
            tv_catefood_num.setVisibility(View.INVISIBLE);
        } else if (cartNum > 0) {
            iv_catefood_sub.setVisibility(View.VISIBLE);
            tv_catefood_num.setVisibility(View.VISIBLE);
        } else {
            toast("格式不正确");
        }

        //shopid = userInfo.getShopInfo();

        //limitcost = Double.parseDouble(userInfo.getShopDet());

        /*tv_foodderails_name.setText(foodName);
        tv_fooddetails_sellcount.setText("已售" + foodSellCount + "份");

        tv_fooddetails_point.setText(foodPoint + "人评价");

        //进入商铺 商品没有规格啊！！！
        if (foodGoodattr == null) {
            tv_foodderails_price.setText("￥" + foodCost);
        } else {
            tv_foodderails_price.setText("￥" + foodCost + "/" + foodGoodattr);
        }*/

        //getCateFoodDetails(str_id);
        isShopGoodId(str_id);

        getWebHTML(instro.toString());


    }

    /**
     * 组件 初始化
     */
    public void initData() {
        userInfo = new UserInfo(getApplication());

        shopFoodDetailsUtils = new ShopFoodDetailsUtils();
        df = new DecimalFormat("###.00");

        jingang = userInfo.getLogin();

        tv_foodderails_name = (TextView) findViewById(R.id.tv_foodderails_name);
        tv_fooddetails_sellcount = (TextView) findViewById(R.id.tv_fooddetails_sellcount);
        tv_foodderails_price = (TextView) findViewById(R.id.tv_foodderails_price);
        tv_fooddetails_point = (TextView) findViewById(R.id.tv_fooddetails_point);

        viewPager = (ViewPager) findViewById(R.id.viewpager1);
        //存放点点的容器
        tipsBox = (LinearLayout) findViewById(R.id.tipsBox);

        //添加购物车
        tv_catefood_num = (TextView) findViewById(R.id.tv_catefood_num);
        iv_catefood_add = (ImageView) findViewById(R.id.iv_catefood_add);
        iv_catefood_sub = (ImageView) findViewById(R.id.iv_catefood_sub);

        iv_catefood_add.setOnClickListener(this);
        iv_catefood_sub.setOnClickListener(this);

        tv_catefood_all_price = (TextView) findViewById(R.id.tv_catefood_all_price);
        but_catefood_pay = (Button) findViewById(R.id.but_catefood_pay);

        tv_catefood_all_num = (TextView) findViewById(R.id.tv_catefood_details_num);

        but_catefood_pay.setOnClickListener(this);

        fml_shopcar = (FrameLayout) findViewById(R.id.fml_catefood_foot_left);
        fml_shopcar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //long lastClick = 0;
        super.onClick(v);
        switch (v.getId()) {

            case R.id.iv_catefood_add:
                //添加购物车
                iv_catefood_add.setClickable(false);
                addShopCart(shopid, str_id);

                break;

            case R.id.iv_catefood_sub:
                //减少购物车
                iv_catefood_sub.setClickable(false);
                subShopCart(shopid, str_id);

                break;

            case R.id.but_catefood_pay:
                //去支付
                if (but_catefood_pay.getText().toString().equals("去支付")) {

                    if (jingang.equals("")) {
                        //没有登录
                        //toast("请先登录");
                        dialog();
                        //Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                    } else if (jingang.equals("0")) {
                        //没有登录
                        //toast("请先登录");
                        dialog();
                        //Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                    } else if (jingang.equals("1")) {
                        //已登录
                        Intent intent = new Intent(CateFoodDetailsActivity.this, OrderActivity.class);
                        startActivity(intent);
                    }
                }

                break;

            case R.id.fml_catefood_foot_left:
                //跳转到购物车

                //判断登录状态
                if (jingang.equals("")) {
                    //没有登录
                    //Toast.makeText(getActivity(
                    dialog();
                } else if (jingang.equals("0")) {
                    //没有登录
                    //Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                    dialog();
                } else if (jingang.equals("1")) {
                    //已登录
                    intent = new Intent(CateFoodDetailsActivity.this, ShopCarActivity.class);
                    intent.putExtra("shopid", shopid);
                    startActivity(intent);
                }

                break;

            default:
                break;
        }
    }

    /**
     * 提示框 （登录）
     */
    protected void dialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setMessage("还未登录，确定要登录吗？");
        builder.setTitle("还未登录");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //设置你的操作事项
                startActivity(new Intent(CateFoodDetailsActivity.this, LoginRegisterActivity.class));
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

    /**
     * viewPager实现左右滑动查看图片
     *
     * @param index 图片索引值
     */
    public void getViewPager(final int index) {
        //初始化图片资源
        //images = new int[]{R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
        //初始化 提示点点
        tips = new ImageView[index];
        for (int i = 0; i < index; i++) {
            ImageView img = new ImageView(this);
            img.setLayoutParams(new ViewGroup.LayoutParams(0, 0));
            tips[i] = img;
            if (i == 0) {
                img.setBackgroundResource(R.mipmap.ic_point_gray);
            } else {
                img.setBackgroundResource(R.mipmap.ic_point_white);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            params.leftMargin = 0;
            params.rightMargin = 0;
            tipsBox.addView(img, params);
        }
        //-----初始化PagerAdapter------
        PagerAdapter adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return index;
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object o) {
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView im = new ImageView(CateFoodDetailsActivity.this);

                if (imgBeen.toString().equals("[]")) {
                    im.setImageResource(R.mipmap.yibeitong001);
                } else {
                    String str_url = imgBeen.get(position).getImgurl();

                    if (str_url.equals("https://www.ybt9.com")) {
                        im.setImageResource(R.mipmap.yibeitong001);
                    } else {
                        BitmapUtils bitmapUtils = new BitmapUtils(CateFoodDetailsActivity.this);

                        bitmapUtils.configDiskCacheEnabled(true);
                        bitmapUtils.configMemoryCacheEnabled(false);

                        bitmapUtils.display(im, str_url);
                    }
                }

                container.addView(im);
                return im;
            }

        };

        viewPager.setAdapter(adapter);
        //更改当前tip
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tips[currentPage].setBackgroundResource(R.mipmap.ic_point_white);
                currentPage = position;
                tips[position].setBackgroundResource(R.mipmap.ic_point_gray);
            }

        });

    }

    /**
     * @param html_bady
     */
    public void getWebHTML(String html_bady) {
        contentWeb = (WebView) findViewById(R.id.webView);
        contentWeb.getSettings().setJavaScriptEnabled(true);
        webSettings = contentWeb.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(false);
        String baseUrl = "http://www.ybt9.com/";
        //拼接HTML
        String css = "<style type=\"text/css\"> img {" +
                "width:100%;" +
                "height:auto;" +
                "}" +
                "body {" +
                "margin-right:15px;" +
                "margin-left:15px;" +
                "margin-top:15px;" +
                "font-size:45px;" +
                "}" +
                "</style>";
        String html = "<html><header>" + css + "</header><body>" + html_bady + "</body></html>";
        contentWeb.loadDataWithBaseURL(baseUrl, html, "text/html", "utf-8", null);

    }

    /**
     * 根据商品id 获取商品的详细信息
     *
     * @param foodId
     */
    public void getCateFoodDetails(String foodId) {

        String Path = HttpPath.PATH_REALM + HttpPath.PATH_MODE + HttpPath.PATH_GOOD_DETAILS + "goodsid=" + foodId;
        RequestParams params = new RequestParams(Path);

        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("商品详情" + result);

                        GoodsDetails goodsDetails = GsonUtil.gsonIntance().gsonToBean(result, GoodsDetails.class);

                        /*UI显示*/
                        shopid = goodsDetails.getMsg().getShopinfo().getShopid();

                        limitcost = Double.parseDouble(goodsDetails.getMsg().getShopinfo().getLimitcost());

                        foodName = goodsDetails.getMsg().getGoods().getName();
                        foodSellCount = goodsDetails.getMsg().getGoods().getSellcount();
                        foodPoint = goodsDetails.getMsg().getGoods().getPoint();
                        foodCost = goodsDetails.getMsg().getGoods().getCost();
                        foodGoodattr = goodsDetails.getMsg().getGoods().getGoodattr();

                        tv_foodderails_name.setText(foodName);
                        tv_fooddetails_sellcount.setText("已售" + foodSellCount + "份");

                        tv_fooddetails_point.setText(foodPoint + "人评价");

                        //进入商铺 商品没有规格啊！！！
                        if (foodGoodattr == null) {
                            tv_foodderails_price.setText("￥" + foodCost);
                        } else {
                            tv_foodderails_price.setText("￥" + foodCost + "/" + foodGoodattr);
                        }

                        shopFoodDetailsUtils.saveMeetings(goodsDetails.getMsg().getGoods().toString());

                        imgBeen = goodsDetails.getMsg().getGoods().getImg();

                        getViewPager(imgBeen.size());
                        cartNum = goodsDetails.getMsg().getGoods().getCartnum();

                        if (cartNum == 0) {
                            tv_catefood_num.setVisibility(View.INVISIBLE);
                            iv_catefood_sub.setVisibility(View.INVISIBLE);
                        } else if (cartNum > 0) {
                            tv_catefood_num.setVisibility(View.VISIBLE);
                            iv_catefood_sub.setVisibility(View.VISIBLE);
                        }

                        tv_catefood_num.setText("" + cartNum);

                        getShopCart(shopid);

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
     * @param shopids
     */
    public void getShopCart(String shopids) {
        String Path = HttpPath.PATH + HttpPath.GETCART + "shopid=" + shopids;

        RequestParams params = new RequestParams(Path);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("获取购物车" + result);
                        totalPrice = 0;
                        ShopCart shopCart = GsonUtil.gsonIntance().gsonToBean(result, ShopCart.class);

                        totalPrice = shopCart.getMsg().getSurecost();
                        sumCount = shopCart.getMsg().getSumcount();

                        listBean = shopCart.getMsg().getList();

                        if (limitcost == 0) {
                            but_catefood_pay.setText("去支付");
                            but_catefood_pay.setTextColor(Color.RED);

                        } else if (totalPrice >= limitcost) {
                            but_catefood_pay.setText("去支付");
                            but_catefood_pay.setTextColor(Color.RED);
                        } else if (totalPrice > 0 && totalPrice < limitcost) {
                            double add = limitcost - totalPrice;
                            but_catefood_pay.setText("还差" + df.format(add) + "元");
                            but_catefood_pay.setTextColor(Color.GRAY);
                        } else if (totalPrice == 0) {
                            but_catefood_pay.setText("购物车为空");
                            but_catefood_pay.setTextColor(Color.GRAY);
                        } else {
                            toast("错误");
                        }


                        tv_catefood_all_num.setText("" + sumCount);
                        tv_catefood_all_price.setText("合计：￥" + df.format(totalPrice) + "元");

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
     * 添加购物车
     *
     * @param shopid
     * @param gid
     */
    public void addShopCart(String shopid, String gid) {
        String str_num = tv_catefood_num.getText().toString();
        cartNum = Integer.valueOf(str_num);

        String PATH = HttpPath.PATH_REALM + HttpPath.PATH_MODE + HttpPath.PATH_ADD_SHOPCART
                + "shopid=" + shopid + "&num=1" + "&gid=" + gid;

        RequestParams params = new RequestParams(PATH);
        x.http().post(params,
                new org.xutils.common.Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        iv_catefood_add.setClickable(true);
                        System.out.println("添加成功 返回" + result);

                        ShopCartReturn shopCartReturn =
                                GsonUtil.gsonIntance().gsonToBean(result, ShopCartReturn.class);
                        if (shopCartReturn.getMsg().isResult() == true) {

                            AddShopCart addShopCart = GsonUtil.gsonIntance().gsonToBean(result, AddShopCart.class);

                            double d_foodcost = Double.parseDouble(foodCost);

                            if (addShopCart.isError() == false) {
                                if (cartNum >= 0 && cartNum < 100) {
                                    tv_catefood_num.setVisibility(View.VISIBLE);
                                    iv_catefood_sub.setVisibility(View.VISIBLE);
                                    cartNum++;

                                    totalPrice += d_foodcost;

                                }
                            }

                            //
                            if (limitcost == 0) {
                                but_catefood_pay.setText("去支付");
                                but_catefood_pay.setTextColor(Color.RED);

                            } else if (totalPrice >= limitcost) {
                                but_catefood_pay.setText("去支付");
                                but_catefood_pay.setTextColor(Color.RED);
                            } else if (totalPrice > 0 && totalPrice < limitcost) {
                                double add = limitcost - totalPrice;
                                but_catefood_pay.setText("还差" + df.format(add) + "元");
                                but_catefood_pay.setTextColor(Color.GRAY);
                            } else if (totalPrice == 0) {
                                but_catefood_pay.setText("购物车为空");
                                but_catefood_pay.setTextColor(Color.GRAY);
                            } else {
                                toast("错误");
                            }
                            tv_catefood_num.setText("" + cartNum);

                            tv_catefood_all_price.setText("合计：￥" + df.format(totalPrice) + "元");

                        } else if (shopCartReturn.getMsg().isResult() == false) {
                            toast("添加失败，库存不足");
                        }

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        System.out.println("添加购物车失败" + isOnCallback);
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
     * 减少购物车
     *
     * @param shopid
     * @param gid
     */
    public void subShopCart(String shopid, String gid) {
        String str_num = tv_catefood_num.getText().toString();
        cartNum = Integer.valueOf(str_num);

        String PATH = HttpPath.PATH_REALM + HttpPath.PATH_MODE + HttpPath.PATH_ADD_SHOPCART
                + "shopid=" + shopid + "&num=-1" + "&gid=" + gid;

        RequestParams params = new RequestParams(PATH);
        x.http().post(params,
                new org.xutils.common.Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        iv_catefood_sub.setClickable(true);
                        System.out.println("减少成功 返回" + result);


                        ShopCartReturn shopCartReturn =
                                GsonUtil.gsonIntance().gsonToBean(result, ShopCartReturn.class);

                        if (shopCartReturn.getMsg().isResult() == true) {
                            AddShopCart addShopCart = GsonUtil.gsonIntance().gsonToBean(result, AddShopCart.class);

                            double d_foodcost = 0;

                            d_foodcost = Double.parseDouble(foodCost);

                            if (addShopCart.isError() == false) {
                                if (cartNum > 1 && cartNum < 100) {
                                    tv_catefood_num.setVisibility(View.VISIBLE);
                                    iv_catefood_sub.setVisibility(View.VISIBLE);
                                    cartNum--;

                                    totalPrice -= d_foodcost;

                                } else if (cartNum == 1) {
                                    tv_catefood_num.setVisibility(View.INVISIBLE);
                                    iv_catefood_sub.setVisibility(View.INVISIBLE);
                                    cartNum--;

                                    totalPrice -= d_foodcost;

                                }
                            }
                            //
                            if (limitcost == 0) {
                                but_catefood_pay.setText("去支付");
                                but_catefood_pay.setTextColor(Color.RED);

                            } else if (totalPrice >= limitcost) {
                                but_catefood_pay.setText("去支付");
                                but_catefood_pay.setTextColor(Color.RED);
                            } else if (totalPrice > 0 && totalPrice < limitcost) {
                                double add = limitcost - totalPrice;
                                but_catefood_pay.setText("还差" + df.format(add) + "元");
                                but_catefood_pay.setTextColor(Color.GRAY);
                            } else if (totalPrice == 0) {
                                but_catefood_pay.setText("购物车为空");
                                but_catefood_pay.setTextColor(Color.GRAY);
                            } else {
                                toast("错误");
                            }

                            tv_catefood_num.setText("" + cartNum);

                            tv_catefood_all_price.setText("合计：￥" + df.format(totalPrice) + "元");

                        } else if (shopCartReturn.getMsg().isResult() == false) {
                            toast("减少失败， 库存不足");
                        }

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        System.out.println("减少购物车失败" + isOnCallback);
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
     * 使用SharedPerferences和Gson搭建本地数据库
     */
    public class ShopFoodDetailsUtils {
        //取
        public String getMeetings() {
            SharedPreferences sp = getSharedPreferences("shopFoodDetailsList", 0);
            return sp.getString("shopFoodDetails", "");
        }

        public boolean saveMeetings(String info) {
            //存
            SharedPreferences sp = getSharedPreferences("shopFoodDetailsList", 0);
            return sp.edit().putString("shopFoodDetails", info).commit();
        }
    }

    /**
     * 判断上层是否请求数据成功
     *
     * @param goodId
     */
    public void isShopGoodId(String goodId) {
        if (goodId.equals("")) {
            //没有获取到gid 显示本地缓存数据
            System.out.println("首页 商品详情 ：" + shopFoodDetailsUtils.getMeetings());
            getViewPager(1);

        } else {
            //加载网络数据
            getCateFoodDetails(goodId);
        }
    }

}
