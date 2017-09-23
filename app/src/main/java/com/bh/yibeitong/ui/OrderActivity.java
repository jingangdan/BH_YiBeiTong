package com.bh.yibeitong.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.Address;
import com.bh.yibeitong.bean.Errors;
import com.bh.yibeitong.bean.GoodsIndex;
import com.bh.yibeitong.bean.OrderReturn;
import com.bh.yibeitong.bean.PSCost;
import com.bh.yibeitong.bean.Register;
import com.bh.yibeitong.bean.ShopCart;
import com.bh.yibeitong.bean.YHQuan;
import com.bh.yibeitong.fragment.FMHomePage;
import com.bh.yibeitong.refresh.MyListView;
import com.bh.yibeitong.ui.address.AddAddressActivity;
import com.bh.yibeitong.ui.address.ManageAddressActivity;
import com.bh.yibeitong.utils.CodeUtils;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.UserInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.BuildConfig;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by jingang on 2016/11/20.
 * 支付 购物车下单
 */
public class OrderActivity extends BaseTextActivity {
    /**
     * 我的收货地址
     */
    private RelativeLayout rel_my_address;

    /*优惠券*/
    private RelativeLayout rel_quan;
    private TextView tv_quan;
    private List<String> ls_yhquan;
    private List<String> ls_yhqid;
    private List<Integer> i_yhqLimicost;
    private List<Integer> i_yhqCost;
    private String yhq_id, yhq_cost, yhq_status, yhq_limitcost, yhq_endtime;
    private TextView tv_order_quan;

    private int i_limitcost, i_cost;

    /**
     * 下单
     */
    private Button but_makeorder;

    /**
     * 订单列表
     */
    private MyListView mlv_shopcart;

    /**
     * 配送费
     */
    private TextView distribution_fee;
    private TextView tv_order_allprice;

    private List<ShopCart.MsgBean.ListBean> shopCartList = new ArrayList<>();
    private ShopCart shopCart;
    private Adapter adapter;
    private double totalPrice = 0;

    private List<Address.MsgBean> msgBean = new ArrayList<>();

    //我的收货地址
    private Button but_my_address;

    /*配送时间*/
    private RelativeLayout rel_send_time;

    String my_address;

    String idscount = "";
    //生成订单参数
    String shopid, lat, lng, ids, pids, pnum, payline, uid, pwd,
            mobile, address = "", contactname = "", ordertype, pstime, beizhu, yhjid;

    //拼接字符串
    String string = "";//商品id

    String str_count = "";//商品数量

    /*当前时间戳*/
    int currentTime;

    /*零点的时间戳*/
    int zeroTime;

    /*当前减零点*/
    int difTime;

    /*获取缓存数据*/
    UserInfo userInfo;

    /*配送时间*/
    private TextView tv_send_time;

    /*支付方式 在线支付 货到付款*/
    private CheckBox cb_online_payment, cb_cash_ondelivery;

    private Intent intent;
    List<String> ls;
    List<Integer> integers;
    List<GoodsIndex.MsgBean.ShopdetBean.PostdateBean> catefoodslist;

    /*配送费*/
    private int pscost;

    //保留double小数点后两位
    DecimalFormat df;

    /*取货方式 到店自取 商家配送*/
    private Button but_seller, but_oneself;
    private int pstype = 1;//2为到店自取  其他不知道

    private TextView tv_shopname;
    String shopname = "";


    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_order);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("购物车下单");
        setTitleBack(true, 0);

        x.Ext.init(getApplication());
        x.Ext.setDebug(BuildConfig.DEBUG);

        userInfo = new UserInfo(getApplication());

        Register register = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), Register.class);

        uid = register.getMsg().getUid();
        mobile = register.getMsg().getPhone();

        //pwd = "123456";
        pwd = userInfo.getPwd();

        shopid = userInfo.getShopInfo();

        initData();

        if (userInfo.getCode().equals("0")) {
            getAddressList(uid, pwd, "1", 1);
        } else {
            getAddressList("phone", mobile, "1", 1);
        }

        //当前时间
        currentTime = ((int) System.currentTimeMillis()) / 1000;
        //
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        zeroTime = ((int) calendar.getTimeInMillis()) / 1000;

        difTime = currentTime - zeroTime;

        cb_online_payment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cb_online_payment.setChecked(true);
                    cb_cash_ondelivery.setChecked(false);

                }
            }
        });

        cb_cash_ondelivery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cb_cash_ondelivery.setChecked(true);
                    cb_online_payment.setChecked(false);
                }

            }
        });

        Gson gson = new Gson();
        Type listType = new TypeToken<List<GoodsIndex.MsgBean.ShopdetBean.PostdateBean>>() {
        }.getType();

        catefoodslist =
                gson.fromJson(userInfo.getPostData(), listType);

        ls = new ArrayList<String>();

        integers = new ArrayList<>();

        int size = catefoodslist.size();

        for (int i = 0; i < size; i++) {
            int getS = catefoodslist.get(i).getS();
            int getE = catefoodslist.get(i).getE();

            if (getE > difTime && difTime >= getS) {
                ls.add("立即配送");
                //integers.add(difTime);
                integers.add(catefoodslist.get(i).getS());
            } else if (difTime < getE) {
                ls.add(catefoodslist.get(i).getTs() + "-" + catefoodslist.get(i).getTe());
                integers.add(catefoodslist.get(i).getS());
            }

        }
        /*获取优惠券*/
        if (userInfo.getCode().toString().equals("0")) {
            getQuan(uid, pwd);
        } else {
            getQuan("phone", mobile);
        }


    }

    private View view;
    private AlertDialog dialog;
    private ListView listView;

    /**
     * 组件 初始化
     */
    public void initData() {
        df = new DecimalFormat("###.00");

        rel_my_address = (RelativeLayout) findViewById(R.id.rel_my_address);
        rel_my_address.setOnClickListener(this);

        rel_quan = (RelativeLayout) findViewById(R.id.rel_order_youhuiquan);
        rel_quan.setOnClickListener(this);

        tv_quan = (TextView) findViewById(R.id.tv_order_youhuiquan);

        mlv_shopcart = (MyListView) findViewById(R.id.mlv_shopcart);

        distribution_fee = (TextView) findViewById(R.id.tv_order_distribution_fee);
        tv_order_allprice = (TextView) findViewById(R.id.tv_order_allprice);

        but_my_address = (Button) findViewById(R.id.but_my_address);

        but_makeorder = (Button) findViewById(R.id.but_makeorder);
        but_makeorder.setOnClickListener(this);

        rel_send_time = (RelativeLayout) findViewById(R.id.rel_send_time);
        rel_send_time.setOnClickListener(this);

        /*配送时间*/
        tv_send_time = (TextView) findViewById(R.id.tv_send_time);

        cb_online_payment = (CheckBox) findViewById(R.id.cb_online_payment);
        cb_cash_ondelivery = (CheckBox) findViewById(R.id.cb_cash_ondelivery);

        //优惠券
        tv_order_quan = (TextView) findViewById(R.id.tv_youhuiquan);

        //
        view = getLayoutInflater().inflate(R.layout.dialog_send_time, null);

        dialog = new AlertDialog.Builder(OrderActivity.this).create();
        dialog.setView(view);
        listView = (ListView) view.findViewById(R.id.lv_dialog_sendtime);

        /*取货方式*/
        but_seller = (Button) findViewById(R.id.but_order_sellers);
        but_oneself = (Button) findViewById(R.id.but_order_onedelfs);

        but_seller.setOnClickListener(this);
        but_oneself.setOnClickListener(this);

        tv_shopname = (TextView) findViewById(R.id.tv_order_shopname);
        tv_shopname.setText(""+ FMHomePage.shopName);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rel_my_address:
                //判断有无收货地址
                if (msgBean.size() == 0) {
                    intent = new Intent(OrderActivity.this, AddAddressActivity.class);
                    intent.putExtra("title", "添加收货地址");
                    startActivityForResult(intent, 2);
                } else {
                    Intent intent = new Intent(OrderActivity.this, ManageAddressActivity.class);
                    // 启动需要监听返回值的Activity，并设置请求码：requestCode
                    startActivityForResult(intent, 1);

                }

                break;

            case R.id.rel_send_time:
                //配送时间
                listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ls));

                //显示配送时间
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        tv_send_time.setText(ls.get(position).toString());

                        difTime = integers.get(position);

                        dialog.dismiss();
                    }
                });


                dialog.show();

                break;

            case R.id.rel_order_youhuiquan:
                //优惠券
                System.out.println("ls_juquan=" + ls_yhquan);
                if (null == ls_yhquan) {
                    //tv_quan.setText("木有可用优惠券");
                    toast("木有优惠券");
                    //tv_order_quan.setText("无");
                } else if (ls_yhquan.size() == 0) {
                    //tv_quan.setText("木有可用优惠券");
                    toast("木有优惠券");
                    //tv_order_quan.setText("无");
                } else {

                    listView.setAdapter(new ArrayAdapter<String>(OrderActivity.this,
                            android.R.layout.simple_list_item_1, ls_yhquan));

                    //显示优惠券
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if ((totalPrice - pscost) >= i_yhqLimicost.get(position)) {
                                tv_quan.setText(ls_yhquan.get(position).toString());

                                totalPrice = (((totalPrice - pscost) - i_cost) + pscost);

                                tv_order_allprice.setText("￥" + df.format(totalPrice) + "元");

                                tv_order_quan.setText("-" + i_cost + "元");

                            } else if ((totalPrice - pscost) < i_yhqLimicost.get(position)) {
                                Toast.makeText(OrderActivity.this, "满" + i_yhqLimicost.get(position) + "元可用", Toast.LENGTH_SHORT).show();
                            }

                            dialog.dismiss();
                        }
                    });

                    dialog.show();

                }

                break;

            case R.id.but_makeorder:
                //生成订单
                //ids = ""商品id
                System.out.println("ids=" + string);
                System.out.println("idscount=" + str_count);
                if (!("" == string)) {
                    ids = string.substring(1, string.length());
                    if (!("" == str_count)) {
                        idscount = str_count.substring(1, str_count.length());

                        pids = "";
                        pnum = "";

                        //payline 支付方式
                        if (cb_online_payment.isChecked()) {
                            payline = "1";
                        } else if (cb_cash_ondelivery.isChecked()) {
                            payline = "0";
                        }

                        //联系手机 联系地址 联系人
                        //address = "";联系人地址
                        String UTF_address = null;
                        if(!("").equals(address)){
                            try {
                                UTF_address = URLEncoder.encode(address, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }else{
                        }


                        String UTF_contactname = null;

                        if(!("").equals(contactname)){
                            try {
                                UTF_contactname = URLEncoder.encode(contactname, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }


                        ordertype = "";
                        //配送时间
                        for (int i = 0; i < catefoodslist.size(); i++) {
                            int getS = catefoodslist.get(i).getS();
                            int getE = catefoodslist.get(i).getE();

                            if (getE > difTime && difTime >= getS) {
                                difTime = catefoodslist.get(i).getS();
                            }

                        }

                        pstime = String.valueOf(difTime);

                        beizhu = "";

                        if (null == ls_yhqid) {
                            yhjid = "";
                        } else {
                            for (int i = 0; i < ls_yhqid.size(); i++) {
                                yhjid = ls_yhqid.get(i);
                            }
                        }

//                        System.out.println("shopid=" + shopid + "ids=" + ids + "idscount=" + idscount +
//                                "payline=" + payline + "mobile=" + mobile + "address=" + UTF_address +
//                                "contactname=" + UTF_contactname + "pstime=" + pstime + "uid=" + uid + "pwd=" + pwd
//                        );
//
//                        System.out.println("yhjid=" + yhjid);
//
//                        System.out.println("lat=" + lat + "  lng=" + lng);
//
//                        System.out.println("pstype = " + pstype);

                        if (userInfo.getCode().toString().equals("0")) {
                            postOrder(shopid, lat, lng, ids, idscount, pids, pnum, payline,
                                    uid, pwd, mobile, UTF_address, UTF_contactname, ordertype,
                                    pstime, beizhu, yhjid, pstype);
                        } else {
                            postOrder(shopid, lat, lng, ids, idscount, pids, pnum, payline,
                                    "phone", mobile, mobile, UTF_address, UTF_contactname, ordertype,
                                    pstime, beizhu, yhjid, pstype);
                        }


                    } else {
                        toast("不在配送区域");
                    }
                } else {
                    toast("不在配送区域");
                }

                break;

            case R.id.but_order_sellers:
                //商家配送
                pstype = 1;
                but_seller.setBackgroundResource(R.drawable.button_red_shape);
                but_oneself.setBackgroundResource(R.drawable.button_white_shape);
                but_seller.setTextColor(Color.WHITE);
                but_oneself.setTextColor(Color.BLACK);

                if (userInfo.getCode().equals("0")) {
                    getAddressList(uid, pwd, "1", pstype);
                } else {
                    getAddressList("phone", mobile, "1", pstype);
                }


                break;

            case R.id.but_order_onedelfs:
                //到店自取
                pstype = 2;
                totalPrice = 0;
                but_seller.setBackgroundResource(R.drawable.button_white_shape);
                but_oneself.setBackgroundResource(R.drawable.button_red_shape);
                but_seller.setTextColor(Color.BLACK);
                but_oneself.setTextColor(Color.WHITE);

                //此时配送费为0
                //distribution_fee.setText("￥0");
                if (userInfo.getCode().equals("0")) {
                    getAddressList(uid, pwd, "1", pstype);
                } else {
                    getAddressList("phone", mobile, "1", pstype);
                }

                break;

            default:
                break;
        }
    }

    public void setResult() {
        intent = new Intent();
        setResult(CodeUtils.REQUEST_CODE_ORDER, intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CodeUtils.REQUEST_CODE_NEWSHOP) {
            if (resultCode == CodeUtils.REUEST_CODE_PAY) {
                setResult();
                OrderActivity.this.finish();
            }
        }
        if (requestCode == 1 && resultCode == 2) {
            Bundle bundle = data.getExtras();

            contactname = bundle.getString("contactname");
            mobile = bundle.getString("phone");

            address = bundle.getString("address");

            lat = bundle.getString("lat");

            lng = bundle.getString("lng");

            getPsCost(shopid, lat, lng);

            but_my_address.setText(contactname + "  " + mobile + "\n" + address);

        } else if (requestCode == 2 && resultCode == 3) {
            Bundle bundle = data.getExtras();
            contactname = bundle.getString("contactname");
            mobile = bundle.getString("phone");

            address = bundle.getString("address");

            lat = bundle.getString("lat");

            lng = bundle.getString("lng");

            getPsCost(shopid, lat, lng);

            but_my_address.setText(contactname + "  " + mobile + "\n" + address);
        }

    }


    /**
     * 获取优惠券
     *
     * @param uid
     * @param pwd
     */
    public void getQuan(String uid, String pwd) {
        String PATH;
        if (userInfo.getCode().toString().equals("0")) {
            PATH = HttpPath.PATH + HttpPath.JUAN_LIST +
                    "uid=" + uid + "&pwd=" + pwd;
        } else {
            PATH = HttpPath.PATH + HttpPath.JUAN_LIST +
                    "logintype=" + uid + "&loginphone=" + pwd;
        }

        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        //判断优惠券是否为空
                        System.out.println("优惠券列表" + result);

                        YHQuan yhQuan = GsonUtil.gsonIntance().gsonToBean(result, YHQuan.class);

                        if (yhQuan.getMsg().size() == 0) {
                            tv_quan.setText("木有优惠券");
                        } else {
                            /*获取UI数据*/
                            ls_yhquan = new ArrayList<String>();
                            ls_yhqid = new ArrayList<String>();
                            i_yhqLimicost = new ArrayList<Integer>();
                            i_yhqCost = new ArrayList<Integer>();

                            int j = 0;
                            int size = yhQuan.getMsg().size();
                            for (int i = 0; i < size; i++) {
                                yhq_id = yhQuan.getMsg().get(i).getId();
                                yhq_cost = yhQuan.getMsg().get(i).getCost();
                                yhq_status = yhQuan.getMsg().get(i).getStatus();
                                yhq_limitcost = yhQuan.getMsg().get(i).getLimitcost();
                                yhq_endtime = yhQuan.getMsg().get(i).getEndtime();

                                if (yhq_status.equals("0")) {
                                    //优惠券未使用
                                    System.out.println("未使用");
                                } else if (yhq_status.equals("1")) {
                                    System.out.println("已绑定");
                                    //已绑定 可用
                                    //可用

                                    ls_yhquan.add("满" + yhq_limitcost + "元立减" + yhq_cost + "元");
                                    i_yhqLimicost.add(Integer.parseInt(yhq_limitcost));

                                    i_cost = Integer.parseInt(yhq_cost);
                                    i_yhqCost.add(Integer.parseInt(yhq_cost));
                                    ls_yhqid.add(yhq_id);
                                    j++;
                                } else if (yhq_status.equals("2")) {
                                    //已使用
                                    System.out.println("已使用");
                                } else if (yhq_status.equals("3")) {
                                    //无效
                                    System.out.println("无效 已过期");
                                }

                            }
                            tv_quan.setText(j + "张可用");

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
     * 获取购物车
     * 多此一举
     *
     * @param shopid
     */
    public void getShopCart(final String shopid) {

        String PATH = HttpPath.PATH + HttpPath.GETCART + "shopid=" + shopid;

        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("获取购物车" + result);

                        shopCart = GsonUtil.gsonIntance().gsonToBean(result, ShopCart.class);

                        shopCartList = shopCart.getMsg().getList();

                        adapter = new Adapter(OrderActivity.this, shopCartList);
                        mlv_shopcart.setAdapter(adapter);
                        //合计
                        int size = shopCartList.size();

                        int count = 0;
                        double d_cost = 0;
                        if (size == 0) {
                            return;
                        }
                        for (int i = 0; i < size; i++) {
                            d_cost = Double.parseDouble(shopCartList.get(i).getCost());
                            //idscount = shopCartList.get(i).getCount();
                            count = shopCartList.get(i).getCount();//商品数量

                            totalPrice += d_cost * count;//商品价格

                            string = string + "," + shopCartList.get(i).getId();//商品id

                            str_count = str_count + "," + shopCartList.get(i).getCount();//商品数量

                            //idscount += idscount;
                            idscount += count;//商品总数量

                            //int s = shopCartList.get(i).getId();

                        }

                        //判断是否有快递情况
                        if (shopCart.getMsg().isOnlynewtype() == true) {
                            //只有快递
                            distribution_fee.setText("￥0");
                        } else if (pstype == 2) {
                            //totalPrice += pscost;
                            distribution_fee.setText("￥0");
                        } else {
                            //不是只有快递
                            totalPrice += pscost;
                            distribution_fee.setText("￥" + pscost);
                        }
                        tv_order_allprice.setText("￥" + df.format(totalPrice) + "元");
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
     * 获取收货地址
     *
     * @param uid
     * @param pwd
     * @param page 页码
     */
    public void getAddressList(String uid, String pwd, String page, final int pstype) {

        String PATH = "";
        if (userInfo.getCode().equals("0")) {
            PATH = HttpPath.PATH + HttpPath.ADDRESS_LIST +
                    "uid=" + uid + "&pwd=" + pwd + "&page=" + page;
        } else {
            PATH = HttpPath.PATH + HttpPath.ADDRESS_LIST +
                    "logintype=" + uid + "&loginphone=" + pwd + "&page=" + page;
        }

        System.out.println("" + PATH);

        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("我的收货地址= " + result);

                        Address addresses = GsonUtil.gsonIntance().gsonToBean(result, Address.class);

                        int size = addresses.getMsg().size();

                        for (int i = 0; i < size; i++) {

                            if (addresses.getMsg().get(i).getDefaultX().equals("1")) {

                                contactname = addresses.getMsg().get(i).getContactname();
                                mobile = addresses.getMsg().get(i).getPhone();

                                address = addresses.getMsg().get(i).getAddress();

                                lat = addresses.getMsg().get(i).getLat();
                                lng = addresses.getMsg().get(i).getLng();

                                my_address = addresses.getMsg().get(i).getAddress();
                                //说明有默认地址
                                but_my_address.setText(contactname + "  " + mobile + "\n" + "     " + my_address);

                            } else if (addresses.getMsg().get(i).getDefaultX().equals("0")) {
                                //没有默认地址
                                but_my_address.setText("添加/选择收货地址");
                            }

                            but_my_address.setText(contactname + "  " + mobile + "\n" + my_address);


                        }

                        if (pstype == 1) {
                            getPsCost(shopid, lat, lng);
                        } else if (pstype == 2) {
                            pscost = 0;
                            getShopCart(shopid);
                        }

                        msgBean = addresses.getMsg();
                        //default == 1  为默认地址
                        if (addresses.getMsg().size() > 0) {
                            //有数据 说明有货  显示收货地址
                            /**
                             * {"error":false,"msg":[{"id":"53","username":"17865069350","address":"???????A?2006","phone":"17865069350","contactname":"jingang","default":"0","sex":"0","bigadr":"?????","detailadr":"??A?2006","lat":"35.111218","lng":"35.111218"}]}
                             */
                        } else if (addresses.getMsg().size() == 0) {
                            //
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
     * 购物车列表展示
     */
    public class Adapter extends BaseAdapter {

        private List<ShopCart.MsgBean.ListBean> listBeanList = new ArrayList<>();
        private Context mContext;

        public Adapter(Context mContext, List<ShopCart.MsgBean.ListBean> listBeanList) {
            this.mContext = mContext;
            this.listBeanList = listBeanList;
        }

        @Override
        public int getCount() {
            return listBeanList.size();
        }

        @Override
        public Object getItem(int position) {
            return listBeanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh;
            if (convertView == null) {
                vh = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_order_shopcart, null);

                vh.name = (TextView) convertView.findViewById(R.id.tv_item_od_name);
                vh.count = (TextView) convertView.findViewById(R.id.tv_item_od_count);
                vh.cost = (TextView) convertView.findViewById(R.id.tv_item_od_cost);

                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }

            String name = listBeanList.get(position).getName();
            int count = listBeanList.get(position).getCount();
            String cost = listBeanList.get(position).getCost();

            vh.name.setText("" + name);
            vh.count.setText("×" + count);
            vh.cost.setText("￥" + cost);

            return convertView;
        }
    }

    public class ViewHolder {
        TextView name, cost, count;
    }

    /**
     * 生成订单
     * shopid	是	int	店铺id
     * lat	是	string	经纬度
     * lng	是	string	经纬度
     * ids	是	string	商品id 格式1，2，3
     * idscount	是	int	商品数量
     * pids	否	string	productid 格式1，2，3
     * pnum	否	string	productcount
     * payline	是	string	是否在线支付0/1
     * uid	否	string	用户id
     * pwd	否	string	用户密码
     * mobile	是	string	联系手机
     * address	是	string	联系地址
     * contactname	是	string	联系人
     * ordertype	否	string	订单类型 订餐方式1网站，2电话，3微信，4App
     * pstime	是	int	配送时间
     * beizhu	否	string	备注
     * yhjid	否	string	优惠券id
     */

    String str_result;

    public void postOrder(final String shopid, String lat, String lng, String ids, String idscount,
                          String pids, String pnum, final String payline, String uid,
                          String pwd, String mobile, String address, String contactname,
                          final String ordertype, String pstime, String beizhu, String yhjid, int pstype) {

        String PATH = "";
        if (userInfo.getCode().equals("0")) {
            PATH = HttpPath.PATH + HttpPath.ORDER_NEWMAKE +
                    "shopid=" + shopid + "&lat=" + lat + "&lng=" + lng + "&ids=" + ids +
                    "&idscount=" + idscount + "&pids=&pnum=&payline=" + payline +
                    "&uid=" + uid + "&pwd=" + pwd + "&mobile=" + mobile + "&address=" + address +
                    "&contactname=" + contactname + "&ordertype=&pstime=" + pstime +
                    "&beizhu=" + beizhu + "&yhjid=" + yhjid + "&pstype=" + pstype;
        } else {
            PATH = HttpPath.PATH + HttpPath.ORDER_NEWMAKE +
                    "shopid=" + shopid + "&lat=" + lat + "&lng=" + lng + "&ids=" + ids +
                    "&idscount=" + idscount + "&pids=&pnum=&payline=" + payline +
                    "&logintype=" + uid + "&loginphone=" + pwd + "&mobile=" + mobile + "&address=" + address +
                    "&contactname=" + contactname + "&ordertype=&pstime=" + pstime +
                    "&beizhu=" + beizhu + "&yhjid=" + yhjid + "&pstype=" + pstype;
        }

        System.out.println("ssss" + PATH);

        RequestParams params = new RequestParams(PATH);
        x.http().post(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {

                        str_result = result;
                        System.out.println("生成订单 返回" + result);
                        //toast("添加" + result);

                        OrderReturn orderReturn = GsonUtil.gsonIntance().gsonToBean(result, OrderReturn.class);
                        orderReturn.getMsg();
                        if (orderReturn.isError() == false) {
                            //清除购物车
                            getDelShopCart(shopid);

                            //请求成功
                            String dno = orderReturn.getMsg().getDno();//订单名称
                            String shopcost = orderReturn.getMsg().getShopcost();//订单金额
                            String orderid = orderReturn.getMsg().getId();
                            toast("添加成功");

                            double d_shopcost = Double.parseDouble(orderReturn.getMsg().getShopcost());

                            String.valueOf(df.format(pscost + d_shopcost));


                            if (payline.equals("0")) {
                                //货到付款 跳到订单详情
                                //OrderActivity.this.finish();
                            } else if (payline.equals("1")) {
                                //跳到支付界面
                                Intent intent = new Intent(OrderActivity.this, PayActivity.class);
                                //传值
                                intent.putExtra("dno", dno);

                                //判断总计小于1的情况
                                if (totalPrice < 1) {
                                    intent.putExtra("shopcost", "0" + String.valueOf(df.format(totalPrice)));
                                } else {
                                    intent.putExtra("shopcost", String.valueOf(df.format(totalPrice)));
                                }
                                //intent.putExtra("shopcost", String.valueOf(df.format(totalPrice)));
                                intent.putExtra("orderid", orderid);
                                intent.putExtra("type", "order");

                                startActivityForResult(intent, CodeUtils.REQUEST_CODE_ORDER);
                                //OrderActivity.this.finish();

                            }
                            intent = new Intent();
                            setResult(CodeUtils.REQUEST_CODE_ORDER);
                        }

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        System.out.println("错误");
                        System.out.println("5555" + str_result);
                        toast("下单失败，请重试！");

//                        Errors error = GsonUtil.gsonIntance().gsonToBean(str_result, Errors.class);
//                        toast(error.getMsg().toString());

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
     * 获取配送费  收货地址的经纬度
     *
     * @param shopid
     * @param lat
     * @param lng
     */
    public void getPsCost(final String shopid, String lat, String lng) {

        String PATH = HttpPath.PATH + HttpPath.PS_COST +
                "shopid=" + shopid + "&lat=" + lat + "&lng=" + lng;

        System.out.println("" + PATH);

        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("获取配送费" + result);

                        string = "";
                        str_count = "";
                        totalPrice = 0;

                        PSCost psCost = GsonUtil.gsonIntance().gsonToBean(result, PSCost.class);

                        if (psCost.getMsg().getCanps() == 0) {

                            toast("" + psCost.getMsg().getBaidupscost());
                            //adapter.notifyDataSetChanged();
                        } else {
                            pscost = Integer.parseInt(psCost.getMsg().getBaidupscost());

                            /*获取购物车*/
                            System.out.println("shopid=" + shopid);
                            getShopCart(shopid);

                            //distribution_fee.setText("￥" + psCost.getMsg().getBaidupscost());
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
     * 删除购物车
     *
     * @param shopid
     */
    public void getDelShopCart(String shopid) {
        String PATH = HttpPath.PATH + HttpPath.SHOPCART_DEL + "shopid=" + shopid;

        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("删除购物车" + result);

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

}
