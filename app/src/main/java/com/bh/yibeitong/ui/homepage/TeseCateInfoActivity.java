package com.bh.yibeitong.ui.homepage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bh.yibeitong.R;
import com.bh.yibeitong.adapter.homepage.TeseClassifyAdapter;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.AddShopCart;
import com.bh.yibeitong.bean.ShopCart;
import com.bh.yibeitong.bean.ShopCartReturn;
import com.bh.yibeitong.bean.homepage.TeseCate;
import com.bh.yibeitong.bean.homepage.TeseGoods;
import com.bh.yibeitong.refresh.MyGridView;
import com.bh.yibeitong.ui.CateFoodDetailsActivity;
import com.bh.yibeitong.ui.LoginRegisterActivity;
import com.bh.yibeitong.ui.OrderActivity;
import com.bh.yibeitong.ui.ShopCarActivity;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.CustomDialog;
import com.bh.yibeitong.view.HorizontalListView;
import com.bh.yibeitong.view.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.DecimalFormat;
import java.util.List;

import static com.bh.yibeitong.R.id.but_sg_pay;

/**
 * Created by jingang on 2017/6/29.
 */

public class TeseCateInfoActivity extends BaseTextActivity {
    /*小分类*/
    private HorizontalListView horizontalListView;
    private TeseClassifyAdapter teseClassifyAdapter;

    /*列表刷新回到顶部操作*/
    private ScrollView scrollView;

    /*分类下商品*/
    private MyGridView myGridView;
    private TeseCateInfoAdapter teseCateInfoAdapter;

    /*接收页面传值  服务名称 action id shopid*/
    private Intent intent;
    private String title, action, id, shopid;

    /*接口地址*/
    String PATH = "";

    /*头部图片*/
    private ImageView imageView;

    //购物车
    private double totalPrice = 0;
//    private TextView tv_sg_all_price;
//    private Button but_sg_pay;
    DecimalFormat df;

    private Button but_pay;
    private TextView tv_all_pay, tv_shopcart_num;
    private FrameLayout fl_shopcart;
    private int cartnum = 0;

    /*起送费*/
    private double limitcost = 0;
    private TextView tv_limitcost;

    //本地缓存
    UserInfo userInfo;
    private String jingang;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_tesecateinfo);
    }

    @Override
    protected void initWidght() {
        super.initWidght();

        initData();

        setTitleName("" + title);
        setTitleBack(true, 0);
    }

    public void initData() {
        intent = getIntent();
        title = intent.getStringExtra("title");
        action = intent.getStringExtra("action");
        id = intent.getStringExtra("id");
        shopid = intent.getStringExtra("shopid");

        userInfo = new UserInfo(getApplication());
        df = new DecimalFormat("###.00");

        jingang = userInfo.getLogin();


        limitcost = Double.parseDouble(userInfo.getShopDet());

        horizontalListView = (HorizontalListView) findViewById(R.id.hlv_tese_cateinfo);
        myGridView = (MyGridView) findViewById(R.id.mgv_tese_cateinfo);

        imageView = (ImageView) findViewById(R.id.iv_tese_cate);
        scrollView = (ScrollView) findViewById(R.id.sv_tese_cateinfo);

//        tv_sg_all_price = (TextView) findViewById(R.id.tv_shopcar_all_price);
//        but_sg_pay = (Button) findViewById(R.id.but_shopcar_pay);
//
//        but_sg_pay.setOnClickListener(this);
        but_pay = (Button) findViewById(R.id.but_pay);
        tv_all_pay = (TextView) findViewById(R.id.tv_all_pay);
        tv_shopcart_num = (TextView) findViewById(R.id.tv_shopcart_num);
        fl_shopcart = (FrameLayout) findViewById(R.id.fl_shopcart);

        but_pay.setOnClickListener(this);
        fl_shopcart.setOnClickListener(this);

        teseCate(action, id, shopid);

        getShopCart(shopid);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
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
                        Intent intent = new Intent(TeseCateInfoActivity.this, OrderActivity.class);
                        startActivity(intent);
                    }
                }

                break;

            case R.id.fl_shopcart:
                //跳转到购物车
                intent = new Intent(TeseCateInfoActivity.this, ShopCarActivity.class);
                intent.putExtra("shopid", shopid);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    /*支付按钮状态*/
    public void goPay() {
        but_pay.setTextColor(Color.WHITE);
        but_pay.setBackgroundColor(Color.rgb(162, 203, 52));
    }

    /*未达到支付要求状态*/
    public void noGoPay() {
        but_pay.setTextColor(Color.GRAY);
        but_pay.setBackgroundColor(Color.rgb(204, 204, 204));
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

                        JSONObject response = null;
                        try {
                            response = new JSONObject(result);

                            if (response.get("msg").toString().equals("[]")) {
                                System.out.println("没有数据");
                                but_pay.setText("购物车为空");
                                noGoPay();
                            } else {
                                ShopCart shopCart = GsonUtil.gsonIntance().gsonToBean(result, ShopCart.class);

                                cartnum = shopCart.getMsg().getSumcount();

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

                                if (limitcost == 0) {
//                                    but_sg_pay.setText("去支付");
//                                    but_sg_pay.setTextColor(Color.RED);
                                    goPay();
                                } else if (totalPrice >= limitcost) {
//                                    but_sg_pay.setText("去支付");
//                                    but_sg_pay.setTextColor(Color.RED);
                                    goPay();
                                } else if (totalPrice > 0 && totalPrice < limitcost) {
                                    double add = limitcost - totalPrice;
                                    but_pay.setText("还差" + df.format(add) + "元");
                                    //but_sg_pay.setTextColor(Color.GRAY);
                                    noGoPay();
                                } else if (totalPrice == 0) {
                                    but_pay.setText("购物车为空");
                                    //but_sg_pay.setTextColor(Color.GRAY);
                                    noGoPay();
                                } else {
                                    //Toast.makeText(TeseCateInfoActivity.this, "错误", Toast.LENGTH_SHORT).show();
                                }
                                //tv_sg_all_price.setText("合计：￥" + df.format(totalPrice) + "元");
                                tv_shopcart_num.setText(""+cartnum);
                                tv_all_pay.setText("￥" + df.format(totalPrice) + "元");

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
     * 获取分类信息和头部广告
     *
     * @param action
     * @param id
     * @param shopid
     */
    public void teseCate(String action, String id, final String shopid) {
        PATH = HttpPath.PATH +
                "action=" + action + "&tsid=" + id + "&shopid=" + shopid;

        RequestParams params = new RequestParams(PATH);
        System.out.println("获取分类信息和头部广告" + PATH);

        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("获取分类信息和头部广告" + result);
                        final TeseCate teseCate = GsonUtil.gsonIntance().gsonToBean(result, TeseCate.class);
                        String img = teseCate.getMsg().getTimg();

                        if (img.equals("")) {
                            imageView.setImageResource(R.mipmap.yibeitong001);

                        } else {
                            x.image().bind(imageView, "http://www.ybt9.com/" + img);
                        }

                        teseClassifyAdapter = new TeseClassifyAdapter(TeseCateInfoActivity.this, teseCate.getMsg().getChildcate());
                        horizontalListView.setAdapter(teseClassifyAdapter);

                        String cateid = teseCate.getMsg().getChildcate().get(0).getId();
                        String parentid = teseCate.getMsg().getChildcate().get(0).getParent_id();

                        /*获取商品列表的第一条数据*/
                        teseGoods(shopid, cateid, parentid);

                        /*点击小分类*/
                        horizontalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                teseClassifyAdapter.changeSelected(i);

                                horizontalListView.setClickable(false);
                                String cateid1 = teseCate.getMsg().getChildcate().get(i).getId();
                                String parentid1 = teseCate.getMsg().getChildcate().get(i).getParent_id();

                                teseGoods(shopid, cateid1, parentid1);
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
     * 获取商品列表
     *
     * @param shopid
     * @param cateid
     * @param parentid
     */
    public void teseGoods(String shopid, String cateid, String parentid) {
        PATH = HttpPath.PATH + "action=tesegoods&" +
                "shopid=" + shopid + "&cateid=" + cateid + "&parentid=" + parentid;
        RequestParams params = new RequestParams(PATH);

        System.out.println("获取商品列表" + PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        horizontalListView.setClickable(true);
                        System.out.println("获取商品列表" + result);
                        final TeseGoods teseGoods = GsonUtil.gsonIntance().gsonToBean(result, TeseGoods.class);

                        teseCateInfoAdapter = new TeseCateInfoAdapter(TeseCateInfoActivity.this, teseGoods.getMsg().getGoodslist());
                        myGridView.setAdapter(teseCateInfoAdapter);

                        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent = new Intent(TeseCateInfoActivity.this, CateFoodDetailsActivity.class);
                                intent.putExtra("id", teseGoods.getMsg().getGoodslist().get(i).getId());//商品id
                                intent.putExtra("instro", teseGoods.getMsg().getGoodslist().get(i).getInstro());

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
     * 提示框
     */
    protected void dialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(TeseCateInfoActivity.this);
        builder.setMessage("还未登录，确定要登录吗？");
        builder.setTitle("还未登录");
        builder.setPositiveButton("登录/注册", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //设置你的操作事项
                startActivity(new Intent(TeseCateInfoActivity.this, LoginRegisterActivity.class));
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

    private int good_num = 0;

    /*获取商品列表适配器*/
    public class TeseCateInfoAdapter extends BaseAdapter {
        private Context mContext;
        private List<TeseGoods.MsgBean.GoodslistBean> goodslistBeen;

        public TeseCateInfoAdapter(Context mContext, List<TeseGoods.MsgBean.GoodslistBean> goodslistBeen) {
            this.mContext = mContext;
            this.goodslistBeen = goodslistBeen;
        }


        @Override
        public int getCount() {
            return goodslistBeen.size();
        }

        @Override
        public Object getItem(int i) {
            return goodslistBeen.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final ViewHolder vh;
            if (view == null) {
                vh = new TeseCateInfoAdapter.ViewHolder();
                view = LayoutInflater.from(mContext).inflate(R.layout.item_self_support, null);

                vh.img = (ImageView) view.findViewById(R.id.iv_item_ss);
                vh.add = (ImageView) view.findViewById(R.id.iv_add);
                vh.sub = (ImageView) view.findViewById(R.id.iv_sub);
                vh.name = (TextView) view.findViewById(R.id.tv_item_ss_title);
                vh.cost = (TextView) view.findViewById(R.id.tv_item_ss_price);
                vh.num = (TextView) view.findViewById(R.id.tv_shop_num);

                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }

            String img = goodslistBeen.get(i).getImg();
            String name = goodslistBeen.get(i).getName();
            final String cost = goodslistBeen.get(i).getCost();
            final String goodId = goodslistBeen.get(i).getId();

            final int count = goodslistBeen.get(i).getCartnum();

            if (img.equals("")) {
                vh.img.setImageResource(R.mipmap.yibeitong001);

            } else {
                x.image().bind(vh.img, "http://www.ybt9.com/" + img);
            }

            vh.name.setText("" + name);
            vh.cost.setText("￥" + cost);

            vh.num.setText("" + count);

            if (count == 0) {
                vh.sub.setVisibility(View.INVISIBLE);
                vh.num.setVisibility(View.INVISIBLE);
            } else if (count > 0) {
                vh.sub.setVisibility(View.VISIBLE);
                vh.num.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(mContext, "格式不正确", Toast.LENGTH_SHORT).show();
            }

            //添加购物车
            vh.add.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    good_num = Integer.valueOf(vh.num.getText().toString());
                    cartnum = Integer.parseInt(tv_shopcart_num.getText().toString());

                    vh.add.setClickable(false);
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

                                    vh.add.setClickable(true);

                                    ShopCartReturn shopCartReturn =
                                            GsonUtil.gsonIntance().gsonToBean(result, ShopCartReturn.class);

                                    if (shopCartReturn.getMsg().isResult() == true) {
                                        AddShopCart addShopCart = GsonUtil.gsonIntance().gsonToBean(result, AddShopCart.class);

                                        double d_cost = Double.parseDouble(cost);

                                        if (addShopCart.isError() == false) {
                                            System.out.println("添加成功" + addShopCart.getMsg());
                                            if (good_num >= 0 && good_num < 100) {
                                                vh.num.setVisibility(View.VISIBLE);
                                                vh.sub.setVisibility(View.VISIBLE);
                                                good_num++;
                                                cartnum++;

                                                totalPrice += d_cost;
                                            }

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
                                                but_pay.setText("还差" + df.format(add) + "元");
                                                //but_sg_pay.setTextColor(Color.GRAY);
                                                noGoPay();
                                            } else if (totalPrice == 0) {
                                                but_pay.setText("购物车为空");
                                                //but_sg_pay.setTextColor(Color.GRAY);
                                                noGoPay();
                                            } else {
                                                //Toast.makeText(TeseCateInfoActivity.this, "错误", Toast.LENGTH_SHORT).show();
                                            }

                                            vh.num.setText("" + good_num);
                                            tv_all_pay.setText("￥" + df.format(totalPrice) + "元");

                                            tv_shopcart_num.setText(""+cartnum);

                                            //tv_sg_all_price.setText("合计：￥" + df.format(totalPrice) + "元");


                                        } else {
                                            System.out.println("添加失败");
                                        }

                                    } else if (shopCartReturn.getMsg().isResult() == false) {
                                        Toast.makeText(TeseCateInfoActivity.this, "添加失败，库存不足", Toast.LENGTH_SHORT).show();
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
            vh.sub.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    vh.sub.setClickable(false);
                    //减少购物车  请求接口 成功则减少 反之 不减少

                    good_num = Integer.valueOf(vh.num.getText().toString());
                    cartnum = Integer.parseInt(tv_shopcart_num.getText().toString());

                    if (good_num > 0) {
                        String PATH = HttpPath.PATH + HttpPath.ADD_SHOPCART + "&shopid=" + shopid + "&num=-1" + "&gid=" + goodId;
                        RequestParams params = new RequestParams(PATH);
                        x.http().post(params,
                                new Callback.CommonCallback<String>() {
                                    @Override
                                    public void onSuccess(String result) {
                                        vh.sub.setClickable(true);
                                        System.out.println("购物车操作" + result);

                                        ShopCartReturn shopCartReturn =
                                                GsonUtil.gsonIntance().gsonToBean(result, ShopCartReturn.class);

                                        if (shopCartReturn.getMsg().isResult() == true) {

                                            double a_cost = Double.parseDouble(goodslistBeen.get(i).getCost());

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
                                                but_pay.setText("还差" + df.format(add) + "元");
                                                //but_sg_pay.setTextColor(Color.GRAY);
                                                noGoPay();
                                            } else if (totalPrice == 0) {
                                                but_pay.setText("购物车为空");
                                                //but_sg_pay.setTextColor(Color.GRAY);
                                                noGoPay();
                                            } else {
                                                Toast.makeText(TeseCateInfoActivity.this, "错误", Toast.LENGTH_SHORT).show();
                                            }

                                            //tv_sg_all_price.setText("合计：￥" + df.format(totalPrice) + "元");


                                            vh.num.setText("" + good_num);
                                            tv_shopcart_num.setText(""+cartnum);
                                            tv_all_pay.setText("￥" + df.format(totalPrice) + "元");

                                        } else if (shopCartReturn.getMsg().isResult() == false) {
                                            Toast.makeText(TeseCateInfoActivity.this, "减少失败，库存不足", Toast.LENGTH_SHORT).show();
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
                        vh.sub.setClickable(true);
                    }

                }
            });

            return view;
        }

        public class ViewHolder {
            ImageView img, add, sub;
            TextView name, cost, num;

        }
    }


}
