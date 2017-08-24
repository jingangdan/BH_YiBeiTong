package com.bh.yibeitong.ui.express;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bh.yibeitong.R;
import com.bh.yibeitong.actitvity.PerCenActivity;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.AddShopCart;
import com.bh.yibeitong.bean.ShopCart;
import com.bh.yibeitong.bean.ShopCartReturn;
import com.bh.yibeitong.bean.express.Express;
import com.bh.yibeitong.refresh.MyGridView;
import com.bh.yibeitong.ui.CateFoodDetailsActivity;
import com.bh.yibeitong.ui.LoginRegisterActivity;
import com.bh.yibeitong.ui.OrderActivity;
import com.bh.yibeitong.ui.ShopCarActivity;
import com.bh.yibeitong.utils.CodeUtils;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.CustomDialog;
import com.bh.yibeitong.view.NoDoubleClickListener;
import com.bh.yibeitong.view.UserInfo;
import com.lidroid.xutils.BitmapUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingang on 2017/1/20.
 * 收发快递
 */

public class ExpressActivity extends BaseTextActivity {
    /*接口地址*/
    private String PATH = "";

    /*本地缓存*/
    private UserInfo userInfo;
    private String jingang;

    /*获取页面传值*/
    Intent intent;
    String shopid = "";

    //    private ListView listView;
    private ExpressAdapter expressAdapter;
    private MyGridView myGridView;
    private List<Express.MsgBean.GoodsBean> goodsBeen = new ArrayList<>();

    //购物车
    private double totalPrice = 0;

    private Button but_pay;
    private TextView tv_all_pay, tv_shopcart_num;
    private FrameLayout fl_shopcart;
    private int cartnum = 0;

    private double limitcost = 0;

    DecimalFormat df;

    /*购物车商品id字符串*/
    private List<ShopCart.MsgBean.ListBean> shopMsg = new ArrayList<>();
    private String gid = "";

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_express);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("收发快递");
        setTitleBack(true, 0);

        initData();
    }

    public void initData() {
        userInfo = new UserInfo(getApplication());
        jingang = userInfo.getLogin();

        myGridView = (MyGridView) findViewById(R.id.myGridView_express);

        df = new DecimalFormat("###.00");

        but_pay = (Button) findViewById(R.id.but_pay);
        tv_all_pay = (TextView) findViewById(R.id.tv_all_pay);
        tv_shopcart_num = (TextView) findViewById(R.id.tv_shopcart_num);
        fl_shopcart = (FrameLayout) findViewById(R.id.fl_shopcart);

        but_pay.setOnClickListener(this);
        fl_shopcart.setOnClickListener(this);


        //获取页面传值
        intent = getIntent();
        shopid = intent.getStringExtra("shopid");
        getExpress(shopid);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.but_pay:
                if (but_pay.getText().toString().equals("去支付")) {
                    if (jingang.equals("")) {
                        //没有登录
                        dialog();
                    } else if (jingang.equals("0")) {
                        //没有登录
                        dialog();
                    } else if (jingang.equals("1")) {
                        //已登录
                        Intent intent = new Intent(ExpressActivity.this, OrderActivity.class);
                        startActivityForResult(intent, CodeUtils.REQUEST_CODE_EXPRESS);

                    }
                }

                break;

            case R.id.fl_shopcart:
                //跳到购物车
                intent = new Intent(ExpressActivity.this, ShopCarActivity.class);
                intent.putExtra("shopid", shopid);

                startActivityForResult(intent, CodeUtils.REQUEST_CODE_EXPRESS);

                break;

            default:
                break;

        }
    }

    /*支付按钮状态*/
    public void goPay() {
        but_pay.setText("去支付");
        but_pay.setTextColor(Color.WHITE);
        but_pay.setBackgroundColor(Color.rgb(162, 203, 52));
    }

    /*未达到支付要求状态*/
    public void noGoPay() {
        but_pay.setTextColor(Color.GRAY);
        but_pay.setBackgroundColor(Color.rgb(204, 204, 204));
    }

    /**
     * 提示框
     */
    protected void dialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setMessage("还未登录，确定要登录吗？");
        //builder.setTitle("还未登录");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //设置你的操作事项
                startActivityForResult(new Intent(ExpressActivity.this, LoginRegisterActivity.class), CodeUtils.REQUEST_CODE_EXPRESS);
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
     * 获取快递列表
     *
     * @param shopid
     */
    public void getExpress(final String shopid) {
        PATH = HttpPath.PATH + HttpPath.MKKD + "shopid=" + shopid;
        RequestParams params = new RequestParams(PATH);

        System.out.println("收发快递" + PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("收发快递" + result);
                        Express express = GsonUtil.gsonIntance().gsonToBean(result, Express.class);

                        goodsBeen = express.getMsg().getGoods();

                        expressAdapter = new ExpressAdapter(ExpressActivity.this, goodsBeen);
                        myGridView.setAdapter(expressAdapter);

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

                                cartnum = shopCart.getMsg().getSumcount();

                                //显示购物车数量
                                tv_shopcart_num.setText("" + cartnum);

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
                                        for (int i = 0; i < goodsBeen.size(); i++) {

                                            int num1 = Integer.parseInt(goodsBeen.get(i).getSellcount());//首页数量
                                            String id1 = goodsBeen.get(i).getId();//首页

                                            if (num1 > 0) {
                                                if (gid.indexOf(goodsBeen.get(i).getId()) == -1) {
                                                    goodsBeen.get(i).setSellcount(""+0);
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
                                                            goodsBeen.get(i).setSellcount(""+num2);
                                                        }
                                                    }
                                                }
                                            } else {
                                                int cartNum = Integer.parseInt(goodsBeen.get(i).getSellcount());
                                                if (cartNum > 0) {
                                                    goodsBeen.get(i).setSellcount(""+0);
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
                        expressAdapter.notifyDataSetChanged();

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CodeUtils.REQUEST_CODE_EXPRESS){
            if(resultCode == CodeUtils.REQUEST_CODE_SHOPCART ||
                    resultCode == CodeUtils.REQUEST_CODE_CATEFOOD){
                jingang = userInfo.getLogin();
                getShopCart(shopid);

            }else if(resultCode == CodeUtils.REQUEST_CODE_LOGIN){
                Bundle bundle = data.getExtras();
                jingang = bundle.getString("jingang");
            }
        }
    }

    /**
     * 收发快递 主页适配器
     */
    public class ExpressAdapter extends BaseAdapter {
        private Context mContext;
        private List<Express.MsgBean.GoodsBean> msgBeanList = new ArrayList<>();

        public ExpressAdapter(Context mContext, List<Express.MsgBean.GoodsBean> msgBeanList) {
            this.mContext = mContext;
            this.msgBeanList = msgBeanList;
        }

        @Override
        public int getCount() {
            return msgBeanList.size();
        }

        @Override
        public Object getItem(int i) {
            return msgBeanList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final ViewHolder vh;
            if (view == null) {
                vh = new ViewHolder();
                view = LayoutInflater.from(mContext).inflate(R.layout.item_self_support, null);

                vh.imager = (ImageView) view.findViewById(R.id.iv_item_ss);
                vh.title = (TextView) view.findViewById(R.id.tv_item_ss_title);
                vh.price = (TextView) view.findViewById(R.id.tv_item_ss_price);
                vh.num = (TextView) view.findViewById(R.id.tv_item_ss_num);

                //添加购物车
                vh.shopCart_num = (TextView) view.findViewById(R.id.tv_shop_num);
                vh.shopCart_add = (ImageView) view.findViewById(R.id.iv_add);
                vh.shopCart_sub = (ImageView) view.findViewById(R.id.iv_sub);

                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }

            String imgPath = msgBeanList.get(i).getImg();
            String name = msgBeanList.get(i).getName();
            String num = msgBeanList.get(i).getSellcount();
            final String price = msgBeanList.get(i).getCost();

            final String goodId = msgBeanList.get(i).getId();

            int cartNum = Integer.parseInt(msgBeanList.get(i).getSellcount());

            if (imgPath.equals("")) {
                vh.imager.setImageResource(R.mipmap.yibeitong001);

            } else {
                BitmapUtils bitmapUtils = new BitmapUtils(mContext);

                bitmapUtils.configDiskCacheEnabled(true);
                bitmapUtils.configMemoryCacheEnabled(false);

                bitmapUtils.display(vh.imager, "http://www.ybt9.com/" + imgPath);

            }

            vh.imager.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intent = new Intent(ExpressActivity.this, CateFoodDetailsActivity.class);
                    intent.putExtra("id", msgBeanList.get(i).getId());//商品id
                    intent.putExtra("instro", msgBeanList.get(i).getInstro());

                    startActivityForResult(intent, CodeUtils.REQUEST_CODE_EXPRESS);
                }


            });

            vh.title.setText("" + name);
            vh.num.setText("月售" + num + "个");
            vh.price.setText("￥" + price);

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

            //final String goodId = detBeen.get(position).getId();


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

                                        double d_cost = Double.parseDouble(price);
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
                                                Toast.makeText(ExpressActivity.this, "错误", Toast.LENGTH_SHORT).show();
                                            }

                                            vh.shopCart_num.setText("" + good_num);
                                            tv_shopcart_num.setText(""+cartnum);

                                            tv_all_pay.setText("￥" + df.format(totalPrice) + "元");

                                        } else {
                                            System.out.println("添加失败");
                                        }

                                    } else if (shopCartReturn.getMsg().isResult() == false) {
                                        Toast.makeText(ExpressActivity.this, "添加失败，库存不足", Toast.LENGTH_SHORT).show();
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
                //int cartNum = Integer.parseInt(price);
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
                                            double a_cost = Double.parseDouble(msgBeanList.get(i).getCost());

                                            totalPrice -= a_cost;
                                            good_num--;
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
                                                Toast.makeText(ExpressActivity.this, "错误", Toast.LENGTH_SHORT).show();
                                            }

                                            tv_all_pay.setText("￥" + df.format(totalPrice) + "元");

                                            System.out.println("count=" + good_num);
                                            vh.shopCart_num.setText("" + good_num);

                                            tv_shopcart_num.setText(""+cartnum);

                                            if (good_num == 0) {

                                            }
                                        } else if (shopCartReturn.getMsg().isResult() == false) {
                                            Toast.makeText(ExpressActivity.this, "减少失败，库存不足", Toast.LENGTH_SHORT).show();
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

            return view;
        }

        public class ViewHolder {
            private ImageView imager;
            private TextView title, price, num;

            private ImageView shopCart_add, shopCart_sub;
            private TextView shopCart_num;
        }
    }

    private int good_num;

}
