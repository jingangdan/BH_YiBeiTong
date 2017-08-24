package com.bh.yibeitong.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.AddShopCart;
import com.bh.yibeitong.bean.GoodsDetails;
import com.bh.yibeitong.bean.ShopCart;
import com.bh.yibeitong.bean.ShopCartReturn;
import com.bh.yibeitong.utils.CodeUtils;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.CustomDialog;
import com.bh.yibeitong.view.MyScrollViewTwo;
import com.bh.yibeitong.view.UserInfo;
import com.lidroid.xutils.BitmapUtils;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;
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
public class CateFoodDetailsActivity extends BaseTextActivity implements
        MyScrollViewTwo.ISmartScrollChangedListener {
    private WebView contentWeb;

    private WebSettings webSettings;

    private Intent intent;

    //接收页面跳转传值
    String instro, foodName, foodSellCount, foodPoint, foodCost, foodGoodattr;
    int cartNum;
    String str_id;

    //
    private TextView tv_foodderails_name, tv_fooddetails_sellcount, tv_foodderails_price, tv_fooddetails_point;

    private List<GoodsDetails.MsgBean.GoodsBean.ImgBean> imgBeen = new ArrayList<>();

    /**
     * ViewPager实现图片左右滑动查看
     */
    private ViewPager viewPager;
    private ImageView[] tips;//提示性点点数组
    private int currentPage = 0;//当前展示的页码
    private LinearLayout tipsBox;

    //本地缓存一哈子
    private ShopFoodDetailsUtils shopFoodDetailsUtils;

    //添加购物车
    private TextView tv_catefood_num;
    private ImageView iv_catefood_add, iv_catefood_sub;

    private double totalPrice = 0;//商品总价
    private double limitcost = 0;

    /*购物车UI*/
    private Button but_pay;
    private TextView tv_shopcart_num;
    private FrameLayout fl_gotoShopcart;
    private int cartnum = 0;


    //本地缓存
    UserInfo userInfo;
    private String shopid;
    private String jingang;//用户是否登录的唯一标识

    DecimalFormat df;

    /*顶部*/
    private RelativeLayout relativeLayout;
    private ImageView iv_back;
    private TextView tv_title;

    /*分享（微信端）*/
    private IWXAPI api;
    private ImageView iv_share;
    private PopupWindow pop = null;
    private LinearLayout ll_popup;
    private View parentView;

    /*接口地址*/
    private String PATH = "";

    /*购物车商品id字符串*/
    private String gid = "";

    @Override
    protected void setRootView() {
        super.setRootView();
        parentView = getLayoutInflater().inflate(R.layout.activity_catefood_details, null);
        setContentView(parentView);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        x.Ext.init(this.getApplication());
        x.Ext.setDebug(BuildConfig.DEBUG);

        setTitleName("详情");
        setTitleBack(true, 0);
        setRightRes();
        initData();

        Init();

        intent = getIntent();
        str_id = intent.getStringExtra("id");

        instro = intent.getStringExtra("instro");//详细图片

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

        /*购物车UI*/
        but_pay = (Button) findViewById(R.id.but_cd_pay);
        tv_shopcart_num = (TextView) findViewById(R.id.tv_cd_shopcart_num);

        but_pay.setOnClickListener(this);

        fl_gotoShopcart = (FrameLayout) findViewById(R.id.fl_cd_shopcart);
        fl_gotoShopcart.setOnClickListener(this);

        relativeLayout = (RelativeLayout) findViewById(R.id.rel_catefoot_details);
        iv_back = (ImageView) findViewById(R.id.iv_catefood_back);
        tv_title = (TextView) findViewById(R.id.tv_catefood_title);
        iv_back.setOnClickListener(this);

        iv_share = (ImageView) findViewById(R.id.iv_share);
        iv_share.setOnClickListener(this);

        MyScrollViewTwo.setScanScrollChangedListener(this);
    }

    @Override
    public void onClick(View v) {
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

            case R.id.but_cd_pay:
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
                        intent = new Intent(CateFoodDetailsActivity.this, OrderActivity.class);
                        startActivityForResult(intent, CodeUtils.REQUEST_CODE_CATEFOOD);
                    }
                }

                break;

            case R.id.fl_cd_shopcart:
                //跳转到购物车

                intent = new Intent(CateFoodDetailsActivity.this, ShopCarActivity.class);
                intent.putExtra("shopid", shopid);
                startActivityForResult(intent, CodeUtils.REQUEST_CODE_CATEFOOD);

                break;
            case R.id.iv_catefood_back:
                //返回上一层
                CateFoodDetailsActivity.this.finish();
                break;

            case R.id.iv_share:
                //分享
                // 微信注册初始化
                api = WXAPIFactory.createWXAPI(this, "wxb8a68c0129244502", true);
                api.registerApp("wxb8a68c0129244502");

                ll_popup.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_bottom_to_top));
                pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);

                break;

            default:
                break;
        }
    }

    /*点击选择分享平台*/
    public void Init() {
        pop = new PopupWindow(CateFoodDetailsActivity.this);

        View view = getLayoutInflater().inflate(R.layout.dialog_share, null);

        view.setAnimation(AnimationUtils.loadAnimation(
                CateFoodDetailsActivity.this, R.anim.slide_bottom_to_top));

        ll_popup = (LinearLayout) view.findViewById(R.id.lin_share_popup);

        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);

        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.share_parent);
        LinearLayout lin_weixin = (LinearLayout) view.findViewById(R.id.lin_share_weixin),
                lin_friend = (LinearLayout) view.findViewById(R.id.lin_share_friend);

        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_share_cancel);

        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });

        lin_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share2weixin(0, foodName, str_id);
                pop.dismiss();
                ll_popup.clearAnimation();

            }
        });
        lin_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share2weixin(1, foodName, str_id);
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
    }

    public Bitmap getImage(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        float realWidth = options.outWidth;
        float realHeight = options.outHeight;
        int scale = (int) ((realHeight > realWidth ? realHeight : realWidth) / 100);
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }

    /*微信平台分享*/
    private void share2weixin(int flag, String title, String id) {
        if (!api.isWXAppInstalled()) {
            toast("您还未安装微信客户端");
            return;
        }

        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "https://www.ybt9.com/index.php?ctrl=wxsite&action=foodshow&id=" + id;
        WXMediaMessage msg = new WXMediaMessage(webpage);

        msg.title = title;
        msg.description = title;

        Bitmap thumb = BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher);

        msg.setThumbImage(thumb);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag;
        api.sendReq(req);
    }

    @Override
    public void onScrolledToTop(boolean isToTop) {
        if (isToTop) {
            relativeLayout.getBackground().setAlpha(85);
            tv_title.setVisibility(View.GONE);
        } else {
            relativeLayout.setBackgroundColor(Color.WHITE);
            tv_title.setVisibility(View.VISIBLE);
        }
    }

    /*
    * 参数：
    * gid：商品id
    * cartNum：该商品在购物车中的数量
    * cartnum：购物车总数量
    * */
    public void setResult(String id, int cartNum, int cartnum, double allpay) {
        intent = new Intent();
        intent.putExtra("gid", id);//商品id
        intent.putExtra("cartNum", cartNum);//该商品购物车数量
        intent.putExtra("cartnum", cartnum);//购物车总数量
        intent.putExtra("allpay", allpay);

        setResult(CodeUtils.REQUEST_CODE_CATEFOOD, intent);
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

    /**
     * 提示框 （登录）
     */
    protected void dialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setMessage("还未登录，确定要登录吗？");
        builder.setTitle("还未登录");
        builder.setPositiveButton("登录/注册", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //设置你的操作事项
                startActivityForResult(new Intent(CateFoodDetailsActivity.this, LoginRegisterActivity.class), CodeUtils.REQUEST_CODE_CATEFOOD);
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
        String css = "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
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
        PATH = HttpPath.PATH + HttpPath.GOODSONE +
                "goodsid=" + foodId;

        //Path = MD5Util.getMD5String(Path);//MD5加密

        RequestParams params = new RequestParams(PATH);

        System.out.println("" + PATH);

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
                        tv_shopcart_num.setText("" + cartNum);

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
                        gid = "";

                        JSONObject response = null;
                        try {
                            response = new JSONObject(result);

                            if (response.get("msg").toString().equals("[]")) {
                                System.out.println("没有数据");
                                but_pay.setText("购物车为空");
                                noGoPay();
                            } else {

                                ShopCart shopCart = GsonUtil.gsonIntance().gsonToBean(result, ShopCart.class);

                                totalPrice = shopCart.getMsg().getSurecost();

                                //显示购物车数量
                                tv_shopcart_num.setText("" + shopCart.getMsg().getSumcount());

                                /*判断快递情况*/
                                if (shopCart.getMsg().isOnlynewtype() == true) {
                                    System.out.println("只有快递");
                                    goPay();

                                } else {
                                    System.out.println("不是只有快递");

                                    if (shopCart.getMsg().getList().size() == 0) {
                                        tv_shopcart_num.setText("" + 0);
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

                                        for (int i = 0; i < shopCart.getMsg().getList().size(); i++) {
                                            gid = gid + shopCart.getMsg().getList().get(i).getId();
                                        }

                                        for (int i = 0; i < shopCart.getMsg().getList().size(); i++) {
                                            if (str_id.equals(String.valueOf(shopCart.getMsg().getList().get(i).getId()))) {
                                                tv_catefood_num.setText("" + shopCart.getMsg().getList().get(i).getCount());
                                            }
                                        }

                                        /*数量>0 但购物车数量为0时*/
                                        if (gid.indexOf(str_id) == -1) {
                                            tv_catefood_num.setText("" + 0);
                                        }

                                    }

                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
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
     * 添加购物车
     *
     * @param shopid
     * @param gid
     */
    public void addShopCart(String shopid, final String gid) {
        cartNum = Integer.valueOf(tv_catefood_num.getText().toString());
        cartnum = Integer.parseInt(tv_shopcart_num.getText().toString());

        PATH = HttpPath.PATH + HttpPath.ADD_SHOPCART +
                "shopid=" + shopid + "&num=1" + "&gid=" + gid;

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
                                    cartnum++;

                                    totalPrice += d_foodcost;

                                }
                            }

                            //
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
                                toast("错误");
                            }
                            tv_catefood_num.setText("" + cartNum);
                            tv_shopcart_num.setText("" + cartnum);

                            setResult(gid, cartNum, cartnum, totalPrice);//向上一页面传值（告知数量以改变）

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
    public void subShopCart(String shopid, final String gid) {
        cartNum = Integer.valueOf(tv_catefood_num.getText().toString());
        cartnum = Integer.parseInt(tv_shopcart_num.getText().toString());

        PATH = HttpPath.PATH + HttpPath.ADD_SHOPCART +
                "shopid=" + shopid + "&num=-1" + "&gid=" + gid;

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
                                    cartnum--;

                                    totalPrice -= d_foodcost;

                                } else if (cartNum == 1) {
                                    tv_catefood_num.setVisibility(View.INVISIBLE);
                                    iv_catefood_sub.setVisibility(View.INVISIBLE);
                                    cartNum--;
                                    cartnum--;

                                    totalPrice -= d_foodcost;

                                }
                            }
                            //
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
                                toast("错误");
                            }

                            tv_catefood_num.setText("" + cartNum);
                            tv_shopcart_num.setText("" + cartnum);

                            setResult(gid, cartNum, cartnum, totalPrice);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CodeUtils.REQUEST_CODE_CATEFOOD
                && resultCode == CodeUtils.REQUEST_CODE_SHOPCART) {
            getShopCart(shopid);
//            Bundle bundle = data.getExtras();
//            String gid = bundle.getString("gid");
//            int cartNum = bundle.getInt("cartNum");
//            int cartnum = bundle.getInt("cartnum");
//
//            tv_shopcart_num.setText(""+cartnum);
//
//            if(gid.equals(goodsBeen.getId())){
//                tv_catefood_num.setText("" + cartNum);;
//            }else if(gid.equals("000")){
//                tv_shopcart_num.setText(""+cartNum);
//            }
//
//            totalPrice = bundle.getDouble("allpay");
//
//
//            if (limitcost == 0) {
//                goPay();
//
//            } else if (totalPrice >= limitcost) {
//                goPay();
//            } else if (totalPrice > 0 && totalPrice < limitcost) {
//                double add = limitcost - totalPrice;
//
//                but_pay.setText("还差" + df.format(add) + "元");
//                noGoPay();
//            } else if (totalPrice == 0) {
//                but_pay.setText("购物车为空");
//                noGoPay();
//            } else {
//                //toast("错误");
//            }

            /*数据发生改变 在返回上层时即传输数据 改变上层数据*/
            setResult("", cartNum, cartnum, totalPrice);

        } else if (resultCode == CodeUtils.REQUEST_CODE_LOGIN) {
            Bundle bundle = data.getExtras();
            jingang = bundle.getString("jingang");
            setResult("", cartNum, cartnum, totalPrice);
        }
    }
}
