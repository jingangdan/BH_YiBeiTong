package com.bh.yibeitong.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bh.yibeitong.R;
import com.bh.yibeitong.actitvity.MainActivity;
import com.bh.yibeitong.base.BaseFragment;
import com.bh.yibeitong.bean.ShopCart;
import com.bh.yibeitong.bean.ShopCartReturn;
import com.bh.yibeitong.refresh.MyListView;
import com.bh.yibeitong.refresh.PullToRefreshView;
import com.bh.yibeitong.ui.LoginRegisterActivity;
import com.bh.yibeitong.ui.OrderActivity;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.CustomDialog;
import com.bh.yibeitong.view.UserInfo;
import com.lidroid.xutils.BitmapUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.BuildConfig;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * *Created by jingang on 2016/9/3.
 * 购物车
 */
public class FMShopCar extends BaseFragment implements PullToRefreshView.OnHeaderRefreshListener,
        PullToRefreshView.OnFooterRefreshListener, View.OnClickListener {
    private View view;
    // 标志位，标志已经初始化完成。
    private boolean isPrepared;

    private TextView subtitle;//编辑

    private PullToRefreshView puToRefreshView;
    private MyListView myListView;
    private ShopCartAdapter shopCartAdapter;

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

    private String shopid;

    List<ShopCart.MsgBean.ListBean> listBean;

    /*起送费*/
    double limitcost;

    DecimalFormat df;

    /*接口地址*/
    private String PATH = "";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("购物车 加载");
        view = inflater.inflate(R.layout.fragment_shopping, container, false);
        x.Ext.init(getActivity().getApplication());
        x.Ext.setDebug(BuildConfig.DEBUG);

        //isPrepared = true;
        initData();

        shopid = userInfo.getShopInfo();

        System.out.println("a ="+userInfo.getShopDet());
        if(!userInfo.getShopDet().equals("")){
            limitcost = Double.parseDouble(userInfo.getShopDet());
        }else{

        }


        /*Bundle bundle = getArguments();
        //从activity传过来的Bundle
        if (bundle != null) {
            login = bundle.getString("login");
            if (!(login == null)) {
                getShopCart(shopid);
            } else {
                Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                dialog();
            }

        }*/

        jingang = userInfo.getLogin();
        /*if (jingang.equals("")) {
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

        }*/
        getShopCart(shopid);

        puToRefreshView.setOnHeaderRefreshListener(this);
        puToRefreshView.setOnFooterRefreshListener(this);
        puToRefreshView.setLastUpdated(new Date().toLocaleString());


        //lazyLoad();

        return view;
    }


    /**
     * 组件初始化
     */
    public void initData() {


        userInfo = new UserInfo(getActivity().getApplication());
        df = new DecimalFormat("###.00");

        subtitle = (TextView) view.findViewById(R.id.subtitle);
        subtitle.setOnClickListener(this);

        puToRefreshView = (PullToRefreshView) view.findViewById(R.id.puToRefreshView_shopCart);
        myListView = (MyListView) view.findViewById(R.id.myListView_shopCart);

        but_pay = (Button) view.findViewById(R.id.but_pay);
        but_pay.setOnClickListener(this);

        //合计
        tv_all_pay = (TextView) view.findViewById(R.id.tv_all_pay);
        tv_shopcart_num = (TextView) view.findViewById(R.id.tv_shopcart_num);


    }

    @Override
    protected void lazyLoad() {
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            System.out.println("离开FMShopCart");

        } else {
            System.out.println("刷新FMShopCart");

            userInfo = new UserInfo(getActivity().getApplication());

            System.out.println("a ="+userInfo.getShopDet());

            //initData();

            //店铺id
            shopid = userInfo.getShopInfo();

            //店铺起送价格

            if(!"".equals(""+userInfo.getShopDet())){
                limitcost = Double.parseDouble(userInfo.getShopDet());
            }else{

            }


//            //**Bundle bundle = getArguments();
//            //从activity传过来的Bundle
//            if (bundle != null) {
//                login = bundle.getString("login");
//                if (!(login == null)) {
//                    getShopCart(shopid);
//                } else {
//                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
//                }
//
//            }*/

//            jingang = userInfo.getLogin();
//            if (jingang.equals("")) {
//                //没有登录
//                //System.out.println("购物车 空" + jingang + "未登录");
//                //dialog();
//                //Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
//            } else if (jingang.equals("0")) {
//                //没有登录
//                //System.out.println("购物车" + jingang + "未登录");
//                //dialog();
//                //Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
//            } else if (jingang.equals("1")) {
//                //已登录
//                //System.out.println("购物车" + jingang + "已登录");
//
//            }


            getShopCart(shopid);

            puToRefreshView.setOnHeaderRefreshListener(this);
            puToRefreshView.setOnFooterRefreshListener(this);
            puToRefreshView.setLastUpdated(new Date().toLocaleString());
        }

    }

    /**
     * 提示框
     */
    protected void dialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
        builder.setMessage("还未登录，确定要登录吗？");
        //builder.setTitle("还未登录");
        builder.setPositiveButton("登录/注册", new DialogInterface.OnClickListener() {
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

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        puToRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                puToRefreshView.onFooterRefreshComplete();
                //Toast.makeText(getActivity(), "加载更多数据", Toast.LENGTH_SHORT).show();
            }

        }, 1000);

    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        puToRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                puToRefreshView.onHeaderRefreshComplete("更新于："
                        + Calendar.getInstance().getTime().toLocaleString());
                puToRefreshView.onHeaderRefreshComplete();

                totalPrice = 0;

                if (jingang.equals("1")) {
                    getShopCart(shopid);
                } else {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }

                //Toast.makeText(getActivity(), "刷新成功!", Toast.LENGTH_SHORT).show();

            }

        }, 1000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_pay:
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
                        Intent intent = new Intent(getActivity(), OrderActivity.class);
                        startActivity(intent);
                    }


                }

                break;

            default:
                break;
        }

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

        System.out.println("购物车 = " + Path);

        RequestParams params = new RequestParams(Path);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("购物车 = " + result);
                        totalPrice = 0;
                        /**/
                        //System.out.println("222222222");

                        try {
                            JSONObject response = new JSONObject(result);

                            if(response.get("msg").toString().equals("[]")){
                                System.out.println("没有数据");
                            }else{
                                ShopCart shopCart = GsonUtil.gsonIntance().gsonToBean(result, ShopCart.class);

                                listBean = shopCart.getMsg().getList();

                                //System.out.println("11111111111111");

                                totalPrice = shopCart.getMsg().getSurecost();

                                sumCount = shopCart.getMsg().getSumcount();
                                //显示购物车数量
                                tv_shopcart_num.setText("" + sumCount);

                        /*判断快递情况*/
                                if (shopCart.getMsg().isOnlynewtype() == true) {
                                    System.out.println("只有快递");


                                    //判断总计小于1的情况
                                    if (totalPrice < 1) {
                                        tv_all_pay.setText("合计：￥" + "0" + df.format(totalPrice) + "元");
                                    } else {
                                        tv_all_pay.setText("合计：￥" + df.format(totalPrice) + "元");
                                    }

                                    but_pay.setVisibility(View.VISIBLE);
                                    but_pay.setText("去支付");
                                    but_pay.setTextColor(Color.RED);

                                } else {
                                    System.out.println("不是只有快递");

                                    if (shopCart.getMsg().getList().size() == 0) {
                                        tv_shopcart_num.setText("" + 0);
                                        tv_all_pay.setText("购物车为空");
                                        but_pay.setVisibility(View.INVISIBLE);

                                    } else if (shopCart.getMsg().getList().size() > 0) {

                                        but_pay.setVisibility(View.VISIBLE);
                                        //listBean = shopCart.getMsg().getList();

                                        //
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

                                        }
                                        //判断总计小于1的情况
                                        System.out.println("totalPrice=" + totalPrice);
                                        if (totalPrice < 1) {
                                            tv_all_pay.setText("合计：￥" + "0" + df.format(totalPrice) + "元");
                                        } else {
                                            tv_all_pay.setText("合计：￥" + df.format(totalPrice) + "元");
                                        }
                                    }

                                }
                                shopCartAdapter = new ShopCartAdapter(getActivity(), listBean);
                                myListView.setAdapter(shopCartAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        System.out.println("错误"+ex);

                        System.out.println("错误"+isOnCallback);

                        tv_shopcart_num.setText("" + 0);
                        tv_all_pay.setText("购物车为空");
                        but_pay.setVisibility(View.INVISIBLE);

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        System.out.println("onCancelled"+cex);

                    }

                    @Override
                    public void onFinished() {
                        System.out.println("onFinished");

                    }
                });

    }


    private FMHomePage fmHomePage;
    MainActivity mActivity;
    /**
     * 试一试 当前写adapter
     */
    public class ShopCartAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {

        private Context mContext;
        private List<ShopCart> shopCarts = new ArrayList<>();

        private List<ShopCart.MsgBean.ListBean> listBean = new ArrayList<>();

        //ViewHolder vh = new ViewHolder();

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
                vh = new ShopCartAdapter.ViewHolder();
                c_view = LayoutInflater.from(mContext).inflate(R.layout.item_shopcart, null);

                vh.img = (ImageView) c_view.findViewById(R.id.iv_item_sc_img);
                vh.gname = (TextView) c_view.findViewById(R.id.tv_item_sc_gname);
                vh.cost = (TextView) c_view.findViewById(R.id.tv_item_sc_cost);

                vh.count = (TextView) c_view.findViewById(R.id.tv_item_sc_num);

                vh.add = (ImageView) c_view.findViewById(R.id.iv_item_sc_add);
                vh.sub = (ImageView) c_view.findViewById(R.id.iv_item_sc_sub);

                c_view.setTag(vh);
            } else {
                vh = (ShopCartAdapter.ViewHolder) c_view.getTag();
            }

            String img = listBean.get(position).getImg();
            String gname = listBean.get(position).getName();
            String cost = listBean.get(position).getCost();

            count = listBean.get(position).getCount();

            int id = listBean.get(position).getId();
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

                    String str_num = vh.count.getText().toString();
                    count = Integer.valueOf(str_num);

                    String sc_num = tv_shopcart_num.getText().toString();
                    sc_count = Integer.parseInt(sc_num);

                    String PATH = HttpPath.PATH + HttpPath.ADD_SHOPCART + "&shopid=" + shopid + "&num=1" + "&gid=" + str_id;
                    RequestParams params = new RequestParams(PATH);
                    x.http().post(params,
                            new Callback.CommonCallback<String>() {
                                @Override
                                public void onSuccess(String result) {

                                    ShopCartReturn shopCartReturn =
                                            GsonUtil.gsonIntance().gsonToBean(result, ShopCartReturn.class);

                                    System.out.println("1111111111" + shopCartReturn.getMsg().isResult());

                                    vh.add.setClickable(true);
                                    System.out.println("购物车操作" + result);

                                    double a_cost = Double.parseDouble(listBean.get(position).getCost());

                                    totalPrice += a_cost;

                                    count++;

                                    sc_count++;
                                    //
                                    if (limitcost == 0) {
                                        but_pay.setText("去支付");
                                        but_pay.setTextColor(Color.RED);

                                    } else if (totalPrice >= limitcost) {
                                        but_pay.setText("去支付");
                                        but_pay.setTextColor(Color.RED);

                                    } else if (totalPrice > 0 && totalPrice < limitcost) {
                                        add = limitcost - totalPrice;
                                        but_pay.setText("还差" + df.format(add) + "元");
                                        but_pay.setTextColor(Color.GRAY);
                                    } else if (totalPrice == 0) {
                                        but_pay.setText("购物车为空");
                                        but_pay.setTextColor(Color.GRAY);
                                    } else {
                                        toast("错误");
                                    }

                                    System.out.println("totalPrice = " + totalPrice);
                                    tv_all_pay.setText("合计：￥" + df.format(totalPrice) + "元");

                                    vh.count.setText("" + count);

                                    tv_shopcart_num.setText("" + sc_count);

                                    /*fmHomePage = new FMHomePage();
                                    fmHomePage.onHiddenChanged(false);*/
                                    Intent intent = new Intent("jerry");
                                    intent.putExtra("change", "yes");
                                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                                    //mActivity.popFragment();


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

                    System.out.println("count=" + count);

                    String sc_num = tv_shopcart_num.getText().toString();
                    sc_count = Integer.parseInt(sc_num);

                    if (count > 0) {
                        //addShopCart(shopid, -1, str_id);
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
                                            toast("错误");
                                        }

                                        //判断总计小于1的情况
                                        System.out.println("totalPrice=" + totalPrice);
                                        if (totalPrice < 1) {
                                            tv_all_pay.setText("合计：￥" + "0" + df.format(totalPrice) + "元");
                                        } else {
                                            tv_all_pay.setText("合计：￥" + df.format(totalPrice) + "元");
                                        }

                                        vh.count.setText("" + count);

                                        tv_shopcart_num.setText("" + sc_count);

                                        if (count == 0) {
                                            listBean.remove(position);
                                            shopCartAdapter.notifyDataSetChanged();
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


        /**
         * 购物车操作
         * num = 1 为添加
         * num = -1 为减少
         *
         * @param shopid
         * @param num
         * @param gid
         */

        /*public void addShopCart(String shopid, final int num, String gid) {
            //final ViewHolder vh = new ViewHolder();

            String PATH = HttpPath.PATH + HttpPath.ADD_SHOPCART + "&shopid=" + shopid + "&num=" + num + "&gid=" + gid;
            RequestParams params = new RequestParams(PATH);
            x.http().post(params,
                    new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            vh.add.setClickable(true);
                            System.out.println("购物车操作" + result);
                            *//*if (num == 1) {
                                Toast.makeText(mContext, "添加购物车成功", Toast.LENGTH_SHORT).show();
                            } else if (num == -1) {
                                Toast.makeText(mContext, "减少购物车成功", Toast.LENGTH_SHORT).show();
                            }*//*
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            *//*if (num == 1) {
                                Toast.makeText(mContext, "添加购物车失败", Toast.LENGTH_SHORT).show();
                            } else if (num == -1) {
                                Toast.makeText(mContext, "减少购物车失败", Toast.LENGTH_SHORT).show();
                            }*//*
                        }

                        @Override
                        public void onCancelled(CancelledException cex) {

                        }

                        @Override
                        public void onFinished() {

                        }
                    });

        }*/
    }


}
