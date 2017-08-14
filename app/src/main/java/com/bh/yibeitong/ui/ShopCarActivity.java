package com.bh.yibeitong.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
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
import com.bh.yibeitong.bean.ShopCart;
import com.bh.yibeitong.refresh.MyListView;
import com.bh.yibeitong.utils.CodeUtils;
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
 * Created by jingang on 2017/3/17.
 * 购物车
 */
public class ShopCarActivity extends BaseTextActivity{

    private MyListView myListView;
    private ShopCartAdapter shopCartAdapter;

    /*购物车UI*/
    private Button but_pay;
    private TextView tv_all_pay, tv_shopcart_num;

    private double totalPrice = 0; // 商品总价
    private int sumCount = 0;//商品总数量
    private double add = 0;

    /**/
    private int count = 0;
    private int sc_count = 0;

    //接收主页传值
    private String login;

    /*本地存储*/
    UserInfo userInfo;

    private String jingang;

    List<ShopCart.MsgBean.ListBean> listBean;

    /*起送费*/
    double limitcost;

    DecimalFormat df;

    /*接收页面传值*/
    Intent intent;
    String shopid;


    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_shopcar);

        x.Ext.init(getApplication());
        x.Ext.setDebug(BuildConfig.DEBUG);


        initData();

        limitcost = Double.parseDouble(userInfo.getShopDet());

        getShopCart(shopid);

        jingang = userInfo.getLogin();
        if (jingang.equals("")) {
            //没有登录
            //System.out.println("购物车 空" + jingang + "未登录");
            //Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
        } else if (jingang.equals("0")) {
            //没有登录
            //System.out.println("购物车" + jingang + "未登录");
            //Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
        } else if (jingang.equals("1")) {
            //已登录
            //System.out.println("购物车" + jingang + "已登录");
            getShopCart(shopid);
        }

    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleBack(true, 0);
        setTitleName("购物车");
    }

    /**
     * 组件初始化
     */
    public void initData() {
        userInfo = new UserInfo(getApplication());
        df = new DecimalFormat("###.00");

        myListView = (MyListView) findViewById(R.id.myListView_shopCarts);

        but_pay = (Button) findViewById(R.id.but_pay);
        but_pay.setOnClickListener(this);

        //合计
        tv_all_pay = (TextView) findViewById(R.id.tv_all_pay);
        tv_shopcart_num = (TextView) findViewById(R.id.tv_shopcart_num);

        intent = getIntent();
        shopid = intent.getStringExtra("shopid");


    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.but_pay:
                System.out.println("jingang = "+jingang);
                if (but_pay.getText().toString().equals("去支付")) {
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
                        intent = new Intent(ShopCarActivity.this, OrderActivity.class);
                        //startActivity(intent);
                        startActivityForResult(intent, CodeUtils.REQUEST_CODE_SHOPCART);
                    }


                }

                break;

            default:
                break;
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
        setResult(CodeUtils.REQUEST_CODE_SHOPCART, intent);
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
     * 未登录提示框
     */
    protected void dialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setMessage("还未登录，确定要登录吗？");
        //builder.setTitle("还未登录");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //设置你的操作事项
                startActivity(new Intent(ShopCarActivity.this, LoginRegisterActivity.class));
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
     * 获取购物车信息
     *
     * @param shopid
     */

    public void getShopCart(String shopid) {
        if (listBean == null) {
        } else {
            listBean.clear();
        }
        String Path = HttpPath.PATH + HttpPath.GETCART + "shopid=" + shopid;

        System.out.println("PATH = " + Path);

        RequestParams params = new RequestParams(Path);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("购物车 = " + result);
                        totalPrice = 0;
                        /**/
                        ShopCart shopCart = GsonUtil.gsonIntance().gsonToBean(result, ShopCart.class);

                        totalPrice = shopCart.getMsg().getSurecost();

                        sumCount = shopCart.getMsg().getSumcount();
                        //显示购物车数量
                        tv_shopcart_num.setText("" + sumCount);

                        /*判断快递情况*/
                        if (shopCart.getMsg().isOnlynewtype() == true) {
                            System.out.println("只有快递");
                            listBean = shopCart.getMsg().getList();

                            //判断总计小于1的情况
                            if (totalPrice < 1) {
                                tv_all_pay.setText("￥" + "0" + df.format(totalPrice) + "元");
                            } else {
                                tv_all_pay.setText("￥" + df.format(totalPrice) + "元");
                            }

//                            but_pay.setVisibility(View.VISIBLE);
//                            but_pay.setText("去支付");
//                            but_pay.setTextColor(Color.RED);

                            goPay();

                        } else {
                            System.out.println("不是只有快递");

                            if (shopCart.getMsg().getList().size() == 0) {
//                                tv_shopcart_num.setText("" + 0);
//                                tv_all_pay.setText("购物车为空");
//                                but_pay.setVisibility(View.INVISIBLE);

                                but_pay.setText("购物车为空");
                                noGoPay();

                            } else if (shopCart.getMsg().getList().size() > 0) {

                                listBean = shopCart.getMsg().getList();

                                if (limitcost == 0) {
//                                    but_pay.setText("去支付");
//                                    but_pay.setTextColor(Color.RED);
                                    goPay();
                                } else if (totalPrice >= limitcost) {
//                                    but_pay.setText("去支付");
//                                    but_pay.setTextColor(Color.RED);
                                    goPay();
                                } else if (totalPrice > 0 && totalPrice < limitcost) {
                                    double add = limitcost - totalPrice;
//                                    but_pay.setText("还差" + df.format(add) + "元");
//                                    but_pay.setTextColor(Color.GRAY);
                                    but_pay.setText("还差" + df.format(add) + "元");
                                    noGoPay();
                                } else if (totalPrice == 0) {
//                                    but_pay.setText("购物车为空");
//                                    but_pay.setTextColor(Color.GRAY);
                                    but_pay.setText("购物车为空");
                                    noGoPay();
                                } else {

                                }
                                //判断总计小于1的情况
                                if (totalPrice < 1) {
                                    tv_all_pay.setText("￥" + "0" + df.format(totalPrice) + "元");
                                } else {
                                    tv_all_pay.setText("￥" + df.format(totalPrice) + "元");
                                }
                            }

                        }
                        shopCartAdapter = new ShopCartAdapter(ShopCarActivity.this, listBean);
                        myListView.setAdapter(shopCartAdapter);

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        System.out.println("错误");

                        tv_shopcart_num.setText("" + 0);
                        tv_all_pay.setText("0.00");

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
        if(requestCode == CodeUtils.REQUEST_CODE_SHOPCART){
            if(resultCode == CodeUtils.REQUEST_CODE_ORDER){
                listBean.clear();
                shopCartAdapter.notifyDataSetChanged();
                setResult("000", 0, 0, 0.00);
            }
        }
    }

    /*购物车适配器（非首页）*/
    public class ShopCartAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {

        private Context mContext;
        private List<ShopCart.MsgBean.ListBean> listBean = new ArrayList<>();

        public ShopCartAdapter(Context mContext, List<ShopCart.MsgBean.ListBean> listBean) {
            this.mContext = mContext;
            this.listBean = listBean;
        }

        @Override
        public int getCount() {
            return listBean.size();
        }

        @Override
        public Object getItem(int position) {
            return listBean.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder vh;
            View c_view = convertView;
            if (c_view == null) {
                vh = new ViewHolder();
                c_view = LayoutInflater.from(mContext).inflate(R.layout.item_shopcart, null);

                vh.img = (ImageView) c_view.findViewById(R.id.iv_item_sc_img);
                vh.gname = (TextView) c_view.findViewById(R.id.tv_item_sc_gname);
                vh.cost = (TextView) c_view.findViewById(R.id.tv_item_sc_cost);

                vh.count = (TextView) c_view.findViewById(R.id.tv_item_sc_num);

                vh.add = (ImageView) c_view.findViewById(R.id.iv_item_sc_add);
                vh.sub = (ImageView) c_view.findViewById(R.id.iv_item_sc_sub);

                c_view.setTag(vh);
            } else {
                vh = (ViewHolder) c_view.getTag();
            }

            String img = listBean.get(position).getImg();
            String gname = listBean.get(position).getName();
            String cost = listBean.get(position).getCost();

            count = listBean.get(position).getCount();

            final int id = listBean.get(position).getId();
            final String str_id = String.valueOf(id);

            if (img.equals("")) {
                vh.img.setImageResource(R.mipmap.yibeitong001);
            } else {
                BitmapUtils bitmapUtils = new BitmapUtils(mContext);
                bitmapUtils.configDiskCacheEnabled(true);
                bitmapUtils.configMemoryCacheEnabled(false);

                bitmapUtils.display(vh.img, "http://www.ybt9.com/" + img);
            }

            vh.gname.setText(gname);
            vh.cost.setText("￥" + cost);
            vh.count.setText("" + count);

            /**
             * 添加购物车
             */
            vh.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vh.add.setClickable(false);

                    count = Integer.valueOf(vh.count.getText().toString());

                    sc_count = Integer.parseInt(tv_shopcart_num.getText().toString());

                    String PATH = HttpPath.PATH + HttpPath.ADD_SHOPCART + "&shopid=" + shopid + "&num=1" + "&gid=" + str_id;
                    RequestParams params = new RequestParams(PATH);
                    x.http().post(params,
                            new Callback.CommonCallback<String>() {
                                @Override
                                public void onSuccess(String result) {
                                    vh.add.setClickable(true);
                                    System.out.println("购物车操作" + result);

                                    double a_cost = Double.parseDouble(listBean.get(position).getCost());

                                    totalPrice += a_cost;

                                    count++;

                                    sc_count++;
                                    //
                                    if (limitcost == 0) {
//                                        but_pay.setText("去支付");
//                                        but_pay.setTextColor(Color.RED);
                                        goPay();

                                    } else if (totalPrice >= limitcost) {
//                                        but_pay.setText("去支付");
//                                        but_pay.setTextColor(Color.RED);
                                        goPay();

                                    } else if (totalPrice > 0 && totalPrice < limitcost) {
                                        add = limitcost - totalPrice;
//                                        but_pay.setText("还差" + df.format(add) + "元");
//                                        but_pay.setTextColor(Color.GRAY);
                                        but_pay.setText("还差" + df.format(add) + "元");
                                        noGoPay();
                                    } else if (totalPrice == 0) {
//                                        but_pay.setText("购物车为空");
//                                        but_pay.setTextColor(Color.GRAY);
                                        but_pay.setText("购物车为空");
                                        noGoPay();
                                    } else {
                                        //toast("错误");
                                    }

                                    tv_all_pay.setText("￥" + df.format(totalPrice) + "元");

                                    vh.count.setText("" + count);

                                    tv_shopcart_num.setText("" + sc_count);

                                    setResult(str_id, count, sc_count, totalPrice);

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

            /**
             * 减少购物车
             */
            vh.sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vh.sub.setClickable(false);

                    String str_num = vh.count.getText().toString();
                    count = Integer.valueOf(str_num);

                    String sc_num = tv_shopcart_num.getText().toString();
                    sc_count = Integer.parseInt(sc_num);

                    if (count > 0) {
                        String PATH = HttpPath.PATH + HttpPath.ADD_SHOPCART + "&shopid=" + shopid + "&num=-1" + "&gid=" + str_id;
                        RequestParams params = new RequestParams(PATH);
                        x.http().post(params,
                                new Callback.CommonCallback<String>() {
                                    @Override
                                    public void onSuccess(String result) {
                                        vh.sub.setClickable(true);
                                        System.out.println("购物车操作" + result);

                                        double a_cost = Double.parseDouble(listBean.get(position).getCost());

                                        totalPrice -= a_cost;
                                        count--;

                                        sc_count--;

                                        //
                                        if (limitcost == 0) {
//                                            but_pay.setText("去支付");
//                                            but_pay.setTextColor(Color.RED);
                                            goPay();

                                        } else if (totalPrice >= limitcost) {
//                                            but_pay.setText("去支付");
//                                            but_pay.setTextColor(Color.RED);
                                            goPay();
                                        } else if (totalPrice > 0 && totalPrice < limitcost) {
                                            double add = limitcost - totalPrice;
//                                            but_pay.setText("还差" + df.format(add) + "元");
//                                            but_pay.setTextColor(Color.GRAY);
                                            but_pay.setText("还差" + df.format(add) + "元");
                                            noGoPay();
                                        } else if (totalPrice == 0) {
                                            but_pay.setText("购物车为空");
                                            //but_pay.setTextColor(Color.GRAY);
                                            noGoPay();
                                        } else {
                                            //toast("错误");
                                        }

                                        //判断总计小于1的情况
                                        if (totalPrice < 1) {
                                            tv_all_pay.setText("￥" + "0" + df.format(totalPrice) + "元");
                                        } else {
                                            tv_all_pay.setText("￥" + df.format(totalPrice) + "元");
                                        }

                                        vh.count.setText("" + count);

                                        tv_shopcart_num.setText("" + sc_count);

                                        if (count == 0) {
                                            listBean.remove(position);
                                            shopCartAdapter.notifyDataSetChanged();
                                        }

                                        setResult(str_id, count, sc_count, totalPrice);
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


                    } else if (count == 0) {
                        vh.sub.setClickable(true);
                    }

                }
            });

            return c_view;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            com.bh.yibeitong.adapter.ShopCartAdapter.ViewHolder holder = (com.bh.yibeitong.adapter.ShopCartAdapter.ViewHolder) view.getTag();

        }

        public class ViewHolder {
            private TextView gname, cost;
            private ImageView img;
            private TextView count;
            private ImageView add, sub;

        }
    }

}
