package com.bh.yibeitong.zxing;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
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
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.CustomDialog;
import com.bh.yibeitong.view.UserInfo;

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
    private int good_num = 0;
    private String cost = "0";
    private double totalPrice = 0;

    DecimalFormat df;

    /**/
    UserInfo userInfo;
    String jingang;//登录状态
    private double limitcost = 0;

    private TextView tv_all_price;
    private Button but_pay;

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
        //shopid = intent.getStringExtra("shopid");

        imageView = (ImageView) findViewById(R.id.iv_zxing_imager);
        tv_title = (TextView) findViewById(R.id.tv_zxing_title);
        tv_sellcout = (TextView) findViewById(R.id.tv_zxing_sellcount);
        tv_cost = (TextView) findViewById(R.id.tv_zxing_cost);
        tv_cartNum = (TextView) findViewById(R.id.tv_zxing_shopnew_num);

        iv_sub = (ImageView) findViewById(R.id.iv_zxing_sub);
        iv_add = (ImageView) findViewById(R.id.iv_zxing_add);
        iv_sub.setOnClickListener(this);
        iv_add.setOnClickListener(this);


        tv_all_price = (TextView) findViewById(R.id.tv_zxing_sg_all_price);
        but_pay = (Button) findViewById(R.id.but_zxing_sg_pay);
        but_pay.setOnClickListener(this);

        linearLayout = (RelativeLayout) findViewById(R.id.lin_zxing_catefood_foot);
        linearLayout.setOnClickListener(this);

        //getGoods(str_dno, shopid);

        ScanninGoodIndex goods = GsonUtil.gsonIntance().gsonToBean(result, ScanninGoodIndex.class);


        String img = goods.getMsg().getImg();
        String name = goods.getMsg().getName();
        String sellcount = goods.getMsg().getSellcount();
        cost = goods.getMsg().getCost();
        String goodattr = goods.getMsg().getGoodattr();
        String newtype = goods.getMsg().getNewtype();
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
        //v_cartNum.setText("" + newtype);

        getShopCart(shopid);


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

            case R.id.lin_zxing_catefood_foot:
                //跳转到购物车
                System.out.println("跳到购物车");

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
                    intent = new Intent(ZXingResultActivity.this, ShopCarActivity.class);
                    intent.putExtra("shopid", shopid);
                    startActivity(intent);
                }

                break;

            case R.id.but_zxing_sg_pay:
                //去支付
                if (but_pay.getText().toString().equals("去支付")) {

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
                        Intent intent = new Intent(ZXingResultActivity.this, OrderActivity.class);
                        startActivity(intent);

                        ZXingResultActivity.this.finish();
                    }
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
                startActivity(new Intent(ZXingResultActivity.this, LoginRegisterActivity.class));
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
     * 获取商品信息
     *
     * @param gno
     * @param shopid
     */
    public void getGoods(String gno, String shopid) {
        PATH = HttpPath.PATH + HttpPath.GETGOODS + "" +
                "gno=" + gno + "&shopid=" + shopid;

        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("根据货号获取商品信息 = " + result);

                        ScanninGoodIndex goods = GsonUtil.gsonIntance().gsonToBean(result, ScanninGoodIndex.class);

                        String img = goods.getMsg().getImg();
                        String name = goods.getMsg().getName();
                        String sellcount = goods.getMsg().getSellcount();
                        cost = goods.getMsg().getCost();
                        String goodattr = goods.getMsg().getGoodattr();
                        String newtype = goods.getMsg().getNewtype();

                        if (img.equals("")) {
                            imageView.setImageResource(R.mipmap.yibeitong001);

                        } else {
                            x.image().bind(imageView, "http://www.ybt9.com/" + img);

                        }

                        tv_title.setText("" + name);
                        tv_sellcout.setText("月售" + sellcount + "笔");

                        tv_cost.setText("" + cost + "/" + goodattr);
                        tv_cartNum.setText("" + newtype);


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

                        System.out.println("购物车" + result);
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
                        if (limitcost == 0) {
                            but_pay.setText("去支付");
                            but_pay.setTextColor(Color.RED);
                        } else if (totalPrice >= limitcost) {
                            but_pay.setText("去支付");
                            but_pay.setTextColor(Color.RED);
                        } else if (totalPrice > 0 && totalPrice < limitcost) {
                            double add = limitcost - totalPrice;
                            but_pay.setText("还差" + df.format(add) + "元");
                            but_pay.setTextColor(Color.GRAY);
                        } else if (totalPrice == 0) {
                            but_pay.setText("购物车为空");
                            but_pay.setTextColor(Color.GRAY);
                        } else {
                            Toast.makeText(ZXingResultActivity.this, "错误", Toast.LENGTH_SHORT).show();
                        }

                        tv_all_price.setText("合计：￥" + df.format(totalPrice) + "元");
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

        String str_num = tv_cartNum.getText().toString();
        good_num = Integer.valueOf(str_num);

        RequestParams params = new RequestParams(
                HttpPath.PATH_REALM + HttpPath.PATH_MODE + HttpPath.PATH_ADD_SHOPCART
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
                            System.out.println("111111111");

                            System.out.println("111111111");
                            AddShopCart addShopCart = GsonUtil.gsonIntance().gsonToBean(result, AddShopCart.class);

                            System.out.println("22222222");

                            double d_cost = Double.parseDouble(cost);

                            System.out.println("33333333");

                            if (addShopCart.isError() == false) {
                                System.out.println("44444444");
                                System.out.println("添加成功" + addShopCart.getMsg());
                                if (good_num >= 0 && good_num < 100) {
                                    good_num++;
                                    totalPrice += d_cost;
                                }
                                if (limitcost == 0) {
                                    but_pay.setText("去支付");
                                    but_pay.setTextColor(Color.RED);
                                } else if (totalPrice >= limitcost) {
                                    but_pay.setText("去支付");
                                    but_pay.setTextColor(Color.RED);
                                } else if (totalPrice > 0 && totalPrice < limitcost) {
                                    double add = limitcost - totalPrice;
                                    but_pay.setText("还差" + df.format(add) + "元");
                                    but_pay.setTextColor(Color.GRAY);
                                } else if (totalPrice == 0) {
                                    but_pay.setText("购物车为空");
                                    but_pay.setTextColor(Color.GRAY);
                                } else {
                                    Toast.makeText(ZXingResultActivity.this, "错误", Toast.LENGTH_SHORT).show();
                                }

                                tv_cartNum.setText("" + good_num);
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
        //vh.shopCart_sub.setClickable(false);
        //减少购物车  请求接口 成功则减少 反之 不减少
        String str_num = tv_cartNum.getText().toString();
        good_num = Integer.valueOf(str_num);
        if (good_num > 0) {
            //addShopCart(shopid, -1, str_id);
            String PATH = HttpPath.PATH + HttpPath.ADD_SHOPCART + "" +
                    "&shopid=" + shopid + "&num=-1" + "&gid=" + gid;
            RequestParams params = new RequestParams(PATH);
            x.http().post(params,
                    new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            //vh.shopCart_sub.setClickable(true);
                            iv_sub.setClickable(true);
                            System.out.println("购物车操作" + result);

                            ShopCartReturn shopCartReturn =
                                    GsonUtil.gsonIntance().gsonToBean(result, ShopCartReturn.class);

                            if (shopCartReturn.getMsg().isResult() == true) {
                                double a_cost = Double.parseDouble(cost);

                                totalPrice -= a_cost;
                                good_num--;

                                if (limitcost == 0) {
                                    but_pay.setText("去支付");
                                    but_pay.setTextColor(Color.RED);
                                } else if (totalPrice >= limitcost) {
                                    but_pay.setText("去支付");
                                    but_pay.setTextColor(Color.RED);
                                } else if (totalPrice > 0 && totalPrice < limitcost) {
                                    double add = limitcost - totalPrice;
                                    but_pay.setText("还差" + df.format(add) + "元");
                                    but_pay.setTextColor(Color.GRAY);
                                } else if (totalPrice == 0) {
                                    but_pay.setText("购物车为空");
                                    but_pay.setTextColor(Color.GRAY);
                                } else {
                                    Toast.makeText(ZXingResultActivity.this, "错误", Toast.LENGTH_SHORT).show();
                                }

                                tv_all_price.setText("合计：￥" + df.format(totalPrice) + "元");

                                System.out.println("count=" + good_num);
                                tv_cartNum.setText("" + good_num);

                                //tv_shopcart_num.setText(""+sc_count);

                                if (good_num == 0) {

                                }
                            } else if (shopCartReturn.getMsg().isResult() == false) {
                                Toast.makeText(ZXingResultActivity.this, "减少失败，库存不足", Toast.LENGTH_SHORT).show();
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
            //vh.shopCart_sub.setClickable(true);
            iv_sub.setClickable(true);
        }

    }


}