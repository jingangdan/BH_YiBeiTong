package com.bh.yibeitong.zxing;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.AddShopCart;
import com.bh.yibeitong.bean.ScanninGoodIndex;
import com.bh.yibeitong.bean.ShopCart;
import com.bh.yibeitong.bean.ShopCartReturn;
import com.bh.yibeitong.ui.LoginRegisterActivity;
import com.bh.yibeitong.ui.OrderActivity;
import com.bh.yibeitong.ui.ShopCarActivity;
import com.bh.yibeitong.utils.CodeUtils;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.CustomDialog;
import com.bh.yibeitong.view.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.DecimalFormat;

/**
 * Created by jingang on 2016/11/2.
 * 显示扫描结果
 */
public class ZXingResultActivity extends BaseTextActivity {
    /*页面传值*/
    private Intent intent;
    String result, shopid;

    /*接口地址*/
    String PATH = "";

    /*UI*/
    private ImageView imageView, iv_add, iv_sub;
    private TextView tv_title, tv_sellcout, tv_cost, tv_cartNum;

    /**/
    //private int good_num = 0;
    private String cost = "0";
    private double totalPrice = 0;

    DecimalFormat df;

    /**/
    UserInfo userInfo;
    String jingang;//登录状态
    private double limitcost = 0;

    /*购物车*/
    private TextView tv_all_price, tv_shopcart_num;
    private Button but_pay;
    private FrameLayout fl_shopcart;

    /*购物车总数*/
    private int cartnum = 0;
    /*该商品购物车数量*/
    private int cartNum = 0;

    /*商品id*/
    private String gid;

    private RelativeLayout linearLayout;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_zxing_result);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("扫描结果");
        setTitleBack(true, 0);

        initData();

    }

    /**
     * 组件 初始化
     */
    public void initData() {

        userInfo = new UserInfo(getApplication());

        df = new DecimalFormat("###.00");
        limitcost = Double.parseDouble(userInfo.getShopDet());

        jingang = userInfo.getLogin();

        intent = getIntent();
        result = intent.getStringExtra("result");

        imageView = (ImageView) findViewById(R.id.iv_zxing_imager);
        tv_title = (TextView) findViewById(R.id.tv_zxing_title);
        tv_sellcout = (TextView) findViewById(R.id.tv_zxing_sellcount);
        tv_cost = (TextView) findViewById(R.id.tv_zxing_cost);
        tv_cartNum = (TextView) findViewById(R.id.tv_zxing_shopnew_num);

        iv_sub = (ImageView) findViewById(R.id.iv_zxing_sub);
        iv_add = (ImageView) findViewById(R.id.iv_zxing_add);
        iv_sub.setOnClickListener(this);
        iv_add.setOnClickListener(this);

        /*购物车操作*/
        but_pay = (Button) findViewById(R.id.but_pay);
        tv_all_price = (TextView) findViewById(R.id.tv_all_pay);
        tv_shopcart_num = (TextView) findViewById(R.id.tv_shopcart_num);
        fl_shopcart = (FrameLayout) findViewById(R.id.fl_shopcart);

        fl_shopcart.setOnClickListener(this);
        but_pay.setOnClickListener(this);

        linearLayout = (RelativeLayout) findViewById(R.id.lin_zxing_catefood_foot);
        linearLayout.setOnClickListener(this);

        ScanninGoodIndex goods = GsonUtil.gsonIntance().gsonToBean(result, ScanninGoodIndex.class);

        String img = goods.getMsg().getImg();
        String name = goods.getMsg().getName();
        String sellcount = goods.getMsg().getSellcount();
        cost = goods.getMsg().getCost();
        String goodattr = goods.getMsg().getGoodattr();
        shopid = goods.getMsg().getShopid();
        gid = goods.getMsg().getId();

        if (img.equals("")) {
            imageView.setImageResource(R.mipmap.yibeitong001);

        } else {
            x.image().bind(imageView, "http://www.ybt9.com/" + img);
        }

        tv_title.setText("" + name);
        tv_sellcout.setText("月售" + sellcount + "笔");

        tv_cost.setText("" + cost + "/" + goodattr);

        getShopCart(shopid);

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

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_zxing_sub:
                iv_sub.setClickable(false);
                subCart(shopid, gid);

                break;

            case R.id.iv_zxing_add:
                iv_add.setClickable(false);
                addCart(shopid, gid);

                break;

            case R.id.fl_shopcart:
                //跳转到购物车

                intent = new Intent(ZXingResultActivity.this, ShopCarActivity.class);
                intent.putExtra("shopid", shopid);
                startActivityForResult(intent, CodeUtils.REQUEST_CODE_ZXING_RESULT);
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
                        intent = new Intent(ZXingResultActivity.this, OrderActivity.class);
                        startActivity(intent);
                        startActivityForResult(intent, CodeUtils.REQUEST_CODE_ZXING_RESULT);

                        ZXingResultActivity.this.finish();
                    }
                }

                break;

            default:
                break;

        }
    }

    /**
     * 提示框（未登录）
     */
    protected void dialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setMessage("还未登录，确定要登录吗？");
        builder.setTitle("还未登录");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //设置你的操作事项
                intent = new Intent(ZXingResultActivity.this, LoginRegisterActivity.class);
                startActivityForResult(intent, CodeUtils.REQUEST_CODE_ZXING_RESULT);
                //startActivity(new Intent(ZXingResultActivity.this, LoginRegisterActivity.class));
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

                        try {
                            JSONObject response = new JSONObject(result);
                            if (response.get("msg").toString().equals("[]")) {
                                System.out.println("没有数据");
                                but_pay.setText("购物车为空");
                                noGoPay();
                            } else {
                                ShopCart shopCart = GsonUtil.gsonIntance().gsonToBean(result, ShopCart.class);

                                totalPrice = shopCart.getMsg().getSurecost();

                                cartnum = shopCart.getMsg().getSumcount();
                                //显示购物车数量
                                tv_shopcart_num.setText("" + cartnum);

                                if (shopCart.getMsg().isOnlynewtype() == true) {
                                    System.out.println("只有快递");

                                    //判断总计小于1的情况
                                    if (totalPrice < 1) {
                                        tv_all_price.setText("￥" + "0" + df.format(totalPrice) + "元");
                                    } else {
                                        tv_all_price.setText("￥" + df.format(totalPrice) + "元");
                                    }

                                    goPay();

                                } else {
                                    System.out.println("不是只有快递");

                                    if (shopCart.getMsg().getList().size() == 0) {
                                        tv_shopcart_num.setText("" + 0);
                                        tv_cartNum.setText("" + 0);
                                        tv_all_price.setText("￥0.00");
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
                                            tv_all_price.setText("￥" + "0" + df.format(totalPrice) + "元");
                                        } else {
                                            tv_all_price.setText("￥" + df.format(totalPrice) + "元");
                                        }

                                        for (int i = 0; i < shopCart.getMsg().getList().size(); i++) {
                                            if (gid.equals(String.valueOf(shopCart.getMsg().getList().get(i).getId()))) {
                                                tv_cartNum.setText("" + shopCart.getMsg().getList().get(i).getCount());
                                            }
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

    //添加购物车
    public void addCart(String shopid, String gid) {

        cartNum = Integer.parseInt(tv_cartNum.getText().toString());

        RequestParams params = new RequestParams(
                HttpPath.PATH + HttpPath.ADD_SHOPCART
                        + "shopid=" + shopid + "&num=" + 1 + "&gid=" + gid
        );
        x.http().post(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {

                        iv_add.setClickable(true);
                        System.out.println("添加购物车" + result);
                        ShopCartReturn shopCartReturn =
                                GsonUtil.gsonIntance().gsonToBean(result, ShopCartReturn.class);

                        if (shopCartReturn.getMsg().isResult() == true) {
                            AddShopCart addShopCart = GsonUtil.gsonIntance().gsonToBean(result, AddShopCart.class);

                            double d_cost = Double.parseDouble(cost);

                            if (addShopCart.isError() == false) {
                                System.out.println("添加成功" + addShopCart.getMsg());
                                if (cartNum >= 0 && cartNum < 100) {
                                    cartNum++;
                                    cartnum++;
                                    totalPrice += d_cost;
                                }

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

                                tv_cartNum.setText("" + cartNum);
                                tv_shopcart_num.setText("" + cartnum);
                                tv_all_price.setText("合计：￥" + df.format(totalPrice) + "元");


                            } else {
                                Toast.makeText(ZXingResultActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                            }

                        } else if (shopCartReturn.getMsg().isResult() == false) {
                            Toast.makeText(ZXingResultActivity.this, "添加失败，库存不足", Toast.LENGTH_SHORT).show();
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

    //减少购物车

    public void subCart(String shopid, String gid) {
        //减少购物车  请求接口 成功则减少 反之 不减少
//        String str_num = tv_cartNum.getText().toString();
//        good_num = Integer.valueOf(str_num);
        cartNum = Integer.parseInt(tv_cartNum.getText().toString());
        if (cartNum > 0) {
            String PATH = HttpPath.PATH + HttpPath.ADD_SHOPCART + "" +
                    "&shopid=" + shopid + "&num=-1" + "&gid=" + gid;
            RequestParams params = new RequestParams(PATH);
            x.http().post(params,
                    new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            iv_sub.setClickable(true);
                            System.out.println("购物车操作" + result);

                            ShopCartReturn shopCartReturn =
                                    GsonUtil.gsonIntance().gsonToBean(result, ShopCartReturn.class);

                            if (shopCartReturn.getMsg().isResult() == true) {
                                double a_cost = Double.parseDouble(cost);

                                totalPrice -= a_cost;
                                cartNum--;
                                cartnum--;

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

                                tv_all_price.setText("合计：￥" + df.format(totalPrice) + "元");
                                tv_cartNum.setText("" + cartNum);
                                tv_shopcart_num.setText("" + cartnum);


                            } else if (shopCartReturn.getMsg().isResult() == false) {
                                Toast.makeText(ZXingResultActivity.this, "减少失败，请刷新重试", Toast.LENGTH_SHORT).show();
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


        } else if (cartNum == 0) {
            iv_sub.setClickable(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CodeUtils.REQUEST_CODE_ZXING_RESULT) {
            if (resultCode == CodeUtils.REQUEST_CODE_SHOPCART) {
                Bundle bundle = data.getExtras();
                cartnum = bundle.getInt("cartnum");
                cartNum = bundle.getInt("cartNum");
                String str_gid = bundle.getString("gid");

                tv_shopcart_num.setText("" + cartnum);

                if (str_gid.equals(gid)) {
                    tv_cartNum.setText("" + cartNum);
                }

                totalPrice = bundle.getDouble("allpay");

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

                tv_all_price.setText("￥" + df.format(totalPrice) + "元");

            }else if(resultCode == CodeUtils.REQUEST_CODE_LOGIN){
                Bundle bundle = data.getExtras();
                jingang = bundle.getString("jingang");
            }
        }
    }
}