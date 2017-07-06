package com.bh.yibeitong.ui.express;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.AddShopCart;
import com.bh.yibeitong.bean.ShopCartReturn;
import com.bh.yibeitong.bean.express.Express;
import com.bh.yibeitong.refresh.MyGridView;
import com.bh.yibeitong.refresh.PullToRefreshView;
import com.bh.yibeitong.ui.LoginRegisterActivity;
import com.bh.yibeitong.ui.OrderActivity;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.CustomDialog;
import com.bh.yibeitong.view.UserInfo;
import com.lidroid.xutils.BitmapUtils;

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

    //购物车
    private double totalPrice = 0;
    private TextView tv_express_all_price;
    private Button but_express_pay;

    private double limitcost = 0;

    DecimalFormat df;

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

//        listView = (ListView) findViewById(R.id.lv_express);

        myGridView = (MyGridView) findViewById(R.id.myGridView_express);

        df = new DecimalFormat("###.00");

        tv_express_all_price = (TextView) findViewById(R.id.tv_express_all_price);
        but_express_pay = (Button) findViewById(R.id.but_express_pay);
        but_express_pay.setOnClickListener(this);


        //获取页面传值
        intent = getIntent();
        shopid = intent.getStringExtra("shopid");
        getExpress(shopid);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.but_express_pay:
                if (but_express_pay.getText().toString().equals("去支付")) {
                    if (jingang.equals("")) {
                        //没有登录
                        //Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                        dialog();
                    } else if (jingang.equals("0")) {
                        //没有登录
                        //Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                        dialog();
                    } else if (jingang.equals("1")) {
                        //已登录
                        Intent intent = new Intent(ExpressActivity.this, OrderActivity.class);
                        startActivity(intent);
                    }
                }

                break;

            default:
                break;

        }
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
                startActivity(new Intent(ExpressActivity.this, LoginRegisterActivity.class));
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
    public void getExpress(String shopid) {
        PATH = HttpPath.PATH + HttpPath.MKKD + "shopid=" + shopid;
        RequestParams params = new RequestParams(PATH);

        System.out.println("收发快递"+PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("收发快递" + result);
                        Express express = GsonUtil.gsonIntance().gsonToBean(result, Express.class);
                        expressAdapter = new ExpressAdapter(ExpressActivity.this, express.getMsg().getGoods());
                        //listView.setAdapter(expressAdapter);

                        myGridView.setAdapter(expressAdapter);

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

            int cartNum = Integer.parseInt(msgBeanList.get(i).getGood_order());

            if (imgPath.equals("")) {
                vh.imager.setImageResource(R.mipmap.yibeitong001);

            } else {
                BitmapUtils bitmapUtils = new BitmapUtils(mContext);

                bitmapUtils.configDiskCacheEnabled(true);
                bitmapUtils.configMemoryCacheEnabled(false);

                bitmapUtils.display(vh.imager, "http://www.ybt9.com/" + imgPath);

            }

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
                            HttpPath.PATH_REALM + HttpPath.PATH_MODE + HttpPath.PATH_ADD_SHOPCART
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

                                                totalPrice += d_cost;

                                            }

                                            //
                                            if (limitcost == 0) {
                                                but_express_pay.setText("去支付");
                                                but_express_pay.setTextColor(Color.RED);
                                            } else if (totalPrice >= limitcost) {
                                                but_express_pay.setText("去支付");
                                                but_express_pay.setTextColor(Color.RED);
                                            } else if (totalPrice > 0 && totalPrice < limitcost) {
                                                double add = limitcost - totalPrice;
                                                but_express_pay.setText("还差" + df.format(add) + "元");
                                                but_express_pay.setTextColor(Color.GRAY);
                                            } else if (totalPrice == 0) {
                                                but_express_pay.setText("购物车为空");
                                                but_express_pay.setTextColor(Color.GRAY);
                                            } else {
                                                Toast.makeText(ExpressActivity.this, "错误", Toast.LENGTH_SHORT).show();
                                            }

                                            vh.shopCart_num.setText("" + good_num);

                                            tv_express_all_price.setText("合计：￥" + df.format(totalPrice) + "元");

                                            //响应按钮点击事件,调用子定义接口，并传入View
                                            //callbackCart.click(position, 2);

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

                                            if (limitcost == 0) {
                                                but_express_pay.setText("去支付");
                                                but_express_pay.setTextColor(Color.RED);
                                            } else if (totalPrice >= limitcost) {
                                                but_express_pay.setText("去支付");
                                                but_express_pay.setTextColor(Color.RED);
                                            } else if (totalPrice > 0 && totalPrice < limitcost) {
                                                double add = limitcost - totalPrice;
                                                but_express_pay.setText("还差" + df.format(add) + "元");
                                                but_express_pay.setTextColor(Color.GRAY);
                                            } else if (totalPrice == 0) {
                                                but_express_pay.setText("购物车为空");
                                                but_express_pay.setTextColor(Color.GRAY);
                                            } else {
                                                Toast.makeText(ExpressActivity.this, "错误", Toast.LENGTH_SHORT).show();
                                            }

                                            tv_express_all_price.setText("合计：￥" + df.format(totalPrice) + "元");

                                            System.out.println("count=" + good_num);
                                            vh.shopCart_num.setText("" + good_num);

                                            //tv_shopcart_num.setText(""+sc_count);

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
