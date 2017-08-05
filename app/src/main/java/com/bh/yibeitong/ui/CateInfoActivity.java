package com.bh.yibeitong.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.AddShopCart;
import com.bh.yibeitong.bean.ShopCart;
import com.bh.yibeitong.bean.ShopCartReturn;
import com.bh.yibeitong.bean.shopbean.CateInfo;
import com.bh.yibeitong.bean.shopbean.CateInfoGoods;
import com.bh.yibeitong.utils.CodeUtils;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingang on 2017/2/28.
 * 店铺优选 分类
 */
public class CateInfoActivity extends BaseTextActivity {
    public static final int CATEINFO_REQUEST_CODE = 0x05;
    /*接收页面传值*/
    private Intent intent;
    String shopid, cateid, name, param;
    String parentid;

    /*排序 筛选*/
    private String price, sell;
    private LinearLayout lin_zonghe, lin_price, lin_sell;
    private TextView tv_zonghe, tv_price, tv_sell, tv_pinpai;
    private ImageView iv_plrice, iv_sell;
    private boolean isPrice = false, isSell = false;

    /*分类下子分类*/
    private HorizontalListView hlv_cateinfo;
    private CateInfoAdapter cateInfoAdapter;
    private List<CateInfo.MsgBean.ChildcateBean> childcateBeen;

    /*分类下商品分类*/
    //private MyGridView mgv_cateinfo;
    private GridView gridView;
    private CateInfoGoodsAdapter cateInfoGoodsAdapter;
    private List<CateInfoGoods.MsgBean.GoodslistBean> goodslistBeen;

    //购物车
    private double totalPrice = 0;

//    private TextView tv_sg_all_price;
//    private Button but_sg_pay;

    /*购物车UI*/
    private Button but_pay;
    private TextView tv_all_pay, tv_shopcart_num;
    private FrameLayout fl_shopcart;
    private int cartnum = 0;

    private double limitcost = 0;

    //本地缓存
    UserInfo userInfo;

    String jingang;

    DecimalFormat df;


    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_cateinfo);

    }

    @Override
    protected void initWidght() {
        super.initWidght();
        initData();

        setTitleName(name + "");
        setTitleBack(true, 0);

        getShopCart(shopid);

    }

    public void initData() {
        userInfo = new UserInfo(getApplication());
        df = new DecimalFormat("###.00");

        jingang = userInfo.getLogin();

        limitcost = Double.parseDouble(userInfo.getShopDet());

        hlv_cateinfo = (HorizontalListView) findViewById(R.id.hlv_cateinfo);
        //mgv_cateinfo = (MyGridView) findViewById(R.id.mgv_cateinfo);
        gridView = (GridView) findViewById(R.id.gv_cateinfo);

        lin_zonghe = (LinearLayout) findViewById(R.id.lin_cateinfo_zonghe);
        lin_price = (LinearLayout) findViewById(R.id.lin_cateinfo_price);
        lin_sell = (LinearLayout) findViewById(R.id.lin_cateinfo_sell);
        //lin_pinpai = (LinearLayout) findViewById(R.id.lin_cateinfo_pinpai);

        lin_zonghe.setOnClickListener(this);
        lin_price.setOnClickListener(this);
        lin_sell.setOnClickListener(this);
        //lin_pinpai.setOnClickListener(this);

        tv_zonghe = (TextView) findViewById(R.id.tv_cateinfo_zonghe);
        tv_price = (TextView) findViewById(R.id.tv_cateinfo_price);
        tv_sell = (TextView) findViewById(R.id.tv_cateinfo_sell);
        tv_pinpai = (TextView) findViewById(R.id.tv_cateinfo_pinpai);

        iv_plrice = (ImageView) findViewById(R.id.iv_cateinfo_price);
        iv_sell = (ImageView) findViewById(R.id.iv_cateinfo_sell);

        //购物车
//        tv_sg_all_price = (TextView) findViewById(R.id.tv_cateinfo_price_all);
//        but_sg_pay = (Button) findViewById(R.id.but_cateinfo_pay);
//
//        but_sg_pay.setOnClickListener(this);
        but_pay = (Button) findViewById(R.id.but_pay);
        tv_all_pay = (TextView) findViewById(R.id.tv_all_pay);
        tv_shopcart_num = (TextView) findViewById(R.id.tv_shopcart_num);
        fl_shopcart = (FrameLayout) findViewById(R.id.fl_shopcart);

        but_pay.setOnClickListener(this);
        fl_shopcart.setOnClickListener(this);

        intent = getIntent();
        shopid = intent.getStringExtra("shopid");
        param = intent.getStringExtra("param");
        cateid = intent.getStringExtra("cateid");
        name = intent.getStringExtra("name");

        getCateInfo(shopid, param);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.lin_cateinfo_zonghe:
                //综合
                setTextColor();
                tv_zonghe.setTextColor(Color.rgb(162, 203, 52));

                getCateInfoGoods(shopid, cateid, parentid, "", "");

                break;

            case R.id.lin_cateinfo_price:
                //价格
                setTextColor();
                tv_price.setTextColor(Color.rgb(162, 203, 52));

                if (!isPrice) {
                    iv_plrice.setImageResource(R.mipmap.ic_sift_down);
                    isPrice = true;
                    getCateInfoGoods(shopid, cateid, parentid, "1", "");
                } else {
                    iv_plrice.setImageResource(R.mipmap.ic_sift_up);
                    isPrice = false;
                    getCateInfoGoods(shopid, cateid, parentid, "2", "");
                }

                break;

            case R.id.lin_cateinfo_sell:
                //销量
                setTextColor();
                tv_sell.setTextColor(Color.rgb(162, 203, 52));

                if (!isSell) {
                    iv_sell.setImageResource(R.mipmap.ic_sift_down);
                    isSell = true;

                    getCateInfoGoods(shopid, cateid, parentid, "1", "");
                } else {
                    iv_sell.setImageResource(R.mipmap.ic_sift_up);
                    isSell = false;
                    getCateInfoGoods(shopid, cateid, parentid, "2", "");
                }

                break;

//            case R.id.lin_cateinfo_pinpai:
//                //品牌
//                setTextColor();
//                tv_pinpai.setTextColor(Color.rgb(162, 203, 52));
//
//                break;

            case R.id.but_pay:
                //去支付
                if (but_pay.getText().toString().equals("去支付")) {

                    if (jingang.equals("")) {
                        //没有登录
                        dialog();
                        //Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                    } else if (jingang.equals("0")) {
                        //没有登录
                        dialog();
                        //Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                    } else if (jingang.equals("1")) {
                        //已登录
                        Intent intent = new Intent(CateInfoActivity.this, OrderActivity.class);
                        startActivity(intent);
                    }
                }

                break;

            case R.id.fl_shopcart:
                //跳转到购物车
                intent = new Intent(CateInfoActivity.this, ShopCarActivity.class);
                intent.putExtra("shopid", shopid);
                //startActivity(intent);
                startActivityForResult(intent, CodeUtils.REQUEST_CODE_CATEINFO);
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

    /*未达到支付要求按钮状态*/
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
        builder.setTitle("还未登录");
        builder.setPositiveButton("登录/注册", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //设置你的操作事项
                startActivity(new Intent(CateInfoActivity.this, LoginRegisterActivity.class));
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

    /**
     * 重置字体颜色和图片
     */
    public void setTextColor() {
        tv_zonghe.setTextColor(Color.rgb(153, 153, 153));
        tv_price.setTextColor(Color.rgb(153, 153, 153));
        tv_sell.setTextColor(Color.rgb(153, 153, 153));
        tv_pinpai.setTextColor(Color.rgb(153, 153, 153));

        iv_plrice.setImageResource(R.mipmap.ic_sift_normal);
        iv_sell.setImageResource(R.mipmap.ic_sift_normal);
    }

    /**
     * 获取购物车
     * 多此一举
     *
     * @param shopid
     */
    public void getShopCart(String shopid) {
        String PATH = HttpPath.PATH + HttpPath.GETCART + "shopid=" + shopid;

        System.out.println("购物车" + PATH);
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
//                                    but_sg_pay.setText("还差" + df.format(add) + "元");
//                                    but_sg_pay.setTextColor(Color.GRAY);
                                    but_pay.setText("还差" + df.format(add) + "元");
                                    noGoPay();
                                } else if (totalPrice == 0) {
//                                    but_sg_pay.setText("购物车为空");
//                                    but_sg_pay.setTextColor(Color.GRAY);
                                    but_pay.setText("购物车为空");
                                    noGoPay();
                                } else {
                                    //Toast.makeText(C, "错误", Toast.LENGTH_SHORT).show();
                                    //toast("错误");
                                }

                                tv_all_pay.setText("￥" + df.format(totalPrice) + "元");
                                tv_shopcart_num.setText(""+cartnum);
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
     * 获取分类下子分类
     *
     * @param shopid
     * @param cateid
     */
    public void getCateInfo(final String shopid, final String cateid) {
        String PATH = HttpPath.path + HttpPath.CATE_INFO +
                "shopid=" + shopid + "&cateid=" + cateid;

        System.out.println("分类下子分类" + PATH);

        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("分类下子分类" + result);
                        final CateInfo cateInfo = GsonUtil.gsonIntance().gsonToBean(result, CateInfo.class);

                        childcateBeen = cateInfo.getMsg().getChildcate();

                        cateInfoAdapter = new CateInfoAdapter(CateInfoActivity.this, childcateBeen);
                        hlv_cateinfo.setAdapter(cateInfoAdapter);

                        String cateid = cateInfo.getMsg().getChildcate().get(0).getId();
                        parentid = cateInfo.getMsg().getCateinfo().getNewid();

                        getCateInfoGoods(shopid, cateid, parentid, "", "");

                        hlv_cateinfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                cateInfoAdapter.changeSelected(i);

                                String cateid = cateInfo.getMsg().getChildcate().get(i).getId();

                                parentid = cateInfo.getMsg().getCateinfo().getNewid();

                                getCateInfoGoods(shopid, cateid, parentid, "", "");

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
     * 分类下商品分类
     * shopid	是	int	店铺名
     * cateid	否	int	分类id 如果不传分类id默认查询上级分类下的所有商品
     * parentid	是	int	上级分类id
     * price	否	int	排序——价格 0或不传 不排序，1从低到高 2从高到低
     * sell	是	int	排序——销量 0或不传 不排序，1从低到高 2从高到低
     * 两个只能选择一个可以都不传，如果两个都传，价格排序优先
     *
     * @param shopid
     * @param cateid
     * @param parentid
     * @param price
     * @param sell
     */
    public void getCateInfoGoods(String shopid, String cateid, String parentid, String price, String sell) {
        String PATH = HttpPath.path + HttpPath.CATE_RECOMMEDGOODS +
                "shopid=" + shopid + "&cateid=" + cateid + "&parentid=" + parentid +
                "&price=" + price + "&sell=" + sell;

        System.out.println("分类下商品分类" + PATH);

        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("分类下商品分类" + result);
                        CateInfoGoods cateInfoGoods = GsonUtil.gsonIntance().gsonToBean(result, CateInfoGoods.class);

                        goodslistBeen = cateInfoGoods.getMsg().getGoodslist();
                        cateInfoGoodsAdapter = new CateInfoGoodsAdapter(CateInfoActivity.this, goodslistBeen);
                        //mgv_cateinfo.setAdapter(cateInfoGoodsAdapter);
                        gridView.setAdapter(cateInfoGoodsAdapter);

                        /*查看商品详情*/
                        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                                /*id 详情图片 名称 已售 评价 单价 单位 购物车数量*/
                                final String str_id = goodslistBeen.get(position).getId();
                                final String instro = goodslistBeen.get(position).getInstro();
                                final String foodName = goodslistBeen.get(position).getName();
                                final int str_foodSellCount = goodslistBeen.get(position).getSellcount();
                                final String foodPoint = goodslistBeen.get(position).getPoint();
                                final String foodCost = goodslistBeen.get(position).getCost();
                                final String foodGoodattr = goodslistBeen.get(position).getGoodattr();
                                final int str_cartNum = goodslistBeen.get(position).getCartnum();

                                Intent intent = new Intent(CateInfoActivity.this, CateFoodDetailsActivity.class);
                                intent.putExtra("id", str_id);//商品id

                                intent.putExtra("instro", instro);
                                intent.putExtra("foodName", foodName);
                                intent.putExtra("foodSellCount", String.valueOf(str_foodSellCount));
                                intent.putExtra("foodPoint", foodPoint);
                                intent.putExtra("foodCost", foodCost);
                                intent.putExtra("foodGoodattr", foodGoodattr);

                                intent.putExtra("cartNum", String.valueOf(str_cartNum));

                                startActivityForResult(intent, CodeUtils.REQUEST_CODE_CATEINFO);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CodeUtils.REQUEST_CODE_CATEINFO){
            if(resultCode == CodeUtils.REQUEST_CODE_CATEFOOD || resultCode == CodeUtils.REQUEST_CODE_SHOPCART){
                Bundle bundle = data.getExtras();
                String gid = bundle.getString("gid");
                int cartNum = bundle.getInt("cartNum");
                int cartnum = bundle.getInt("cartnum");

                tv_shopcart_num.setText(""+cartnum);

                for (int i = 0; i < goodslistBeen.size(); i++){
                    if(gid.equals(goodslistBeen.get(i).getId())){
                        goodslistBeen.get(i).setCartnum(cartNum);
                    }
                }
                cateInfoGoodsAdapter.notifyDataSetChanged();
            }
        }

//        if (requestCode == CodeUtils.REQUEST_CODE_CATEINFO
//                && resultCode == CodeUtils.REQUEST_CODE_CATEFOOD){
//            Bundle bundle = data.getExtras();
//            String gid = bundle.getString("gid");
//            int cartNum = bundle.getInt("cartNum");
//            int cartnum = bundle.getInt("cartnum");
//
//            tv_shopcart_num.setText(""+cartnum);
//
//            for (int i = 0; i < goodslistBeen.size(); i++){
//                if(gid.equals(goodslistBeen.get(i).getId())){
//                    goodslistBeen.get(i).setCartnum(cartNum);
//                }
//            }
//            cateInfoGoodsAdapter.notifyDataSetChanged();
//        }

    }

    /**
     * 分类下子分类适配器
     */
    public class CateInfoAdapter extends BaseAdapter {
        private Context mContext;
        private List<CateInfo.MsgBean.ChildcateBean> childcateBeanList = new ArrayList<>();
        private int mSelect = 0;

        public CateInfoAdapter(Context mContext, List<CateInfo.MsgBean.ChildcateBean> childcateBeanList) {
            this.mContext = mContext;
            this.childcateBeanList = childcateBeanList;
        }

        /**
         * 刷新方法
         *
         * @param positon
         */
        public void changeSelected(int positon) {
            if (positon != mSelect) {
                mSelect = positon;
                notifyDataSetChanged();
            }
        }

        @Override
        public int getCount() {
            return childcateBeanList.size();
        }

        @Override
        public Object getItem(int i) {
            return childcateBeanList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder vh;
            if (view == null) {
                vh = new ViewHolder();
                view = LayoutInflater.from(mContext).inflate(R.layout.item_good_classily_second, null);

                vh.name = (TextView) view.findViewById(R.id.tv_item_good_classily_second);
                vh.lin_item_good_classily_second = (LinearLayout) view.findViewById(R.id.lin_item_good_classily_second);

                view.setTag(vh);

            } else {
                vh = (ViewHolder) view.getTag();
            }

            //ListView点击改变背景 文字
            if (mSelect == i) {
                vh.name.setTextColor(Color.rgb(255, 255, 255));
                vh.lin_item_good_classily_second.setBackgroundResource(R.drawable.linstyle_green);
            } else {

                vh.name.setTextColor(Color.rgb(162, 203, 52));
                vh.lin_item_good_classily_second.setBackgroundResource(R.drawable.linstyle_white);
            }
            String name = childcateBeanList.get(i).getName();
            vh.name.setText(name + "");

            return view;
        }

        public class ViewHolder {
            TextView name;
            LinearLayout lin_item_good_classily_second;
        }

    }

    /*购物车数量*/
    private int good_num;

    /**
     * 分类下商品筛选
     */
    public class CateInfoGoodsAdapter extends BaseAdapter {
        private Context mContext;
        private List<CateInfoGoods.MsgBean.GoodslistBean> goodslistBeanList;

        public CateInfoGoodsAdapter(Context mContext, List<CateInfoGoods.MsgBean.GoodslistBean> goodslistBeanList) {
            this.mContext = mContext;
            this.goodslistBeanList = goodslistBeanList;
        }

        @Override
        public int getCount() {
            return goodslistBeanList.size();
        }

        @Override
        public Object getItem(int i) {
            return goodslistBeanList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup viewGroup) {

            final ViewHolder vh;
            if (convertView == null) {
                vh = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_shopnew, null);

                vh.imager = (ImageView) convertView.findViewById(R.id.iv_item_shopnew_ss);
                vh.name = (TextView) convertView.findViewById(R.id.tv_item_shopnew_ss_title);
                vh.cost = (TextView) convertView.findViewById(R.id.tv_item_shopnew_ss_price);
                vh.sellcount = (TextView) convertView.findViewById(R.id.tv_item_shopnew_ss_num);

                //添加购物车
                vh.shopCart_num = (TextView) convertView.findViewById(R.id.tv_shopnew_num);
                vh.shopCart_add = (ImageView) convertView.findViewById(R.id.iv_shopnew_add);
                vh.shopCart_sub = (ImageView) convertView.findViewById(R.id.iv_shopnew_sub);

                /*新版 商品展示*/
//                vh.iv_img = (ImageView) convertView.findViewById(R.id.iv_item_shopnew_img);
//                vh.iv_add = (ImageView) convertView.findViewById(R.id.iv_item_shopnew_add);
//                vh.iv_sub = (ImageView) convertView.findViewById(R.id.iv_item_shopnew_sub);
//                vh.tv_name = (TextView) convertView.findViewById(R.id.tv_item_shopnew_name);
//                vh.tv_cost = (TextView) convertView.findViewById(R.id.tv_item_shopnew_cost);
//                vh.tv_num = (TextView) convertView.findViewById(R.id.tv_item_shopnew_num);

                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }

            // 组件赋值
            String imgPath = goodslistBeanList.get(position).getImg();
            String name = goodslistBeanList.get(position).getName();
            final String cost = goodslistBeanList.get(position).getCost();
            String sellcount = String.valueOf(goodslistBeanList.get(position).getSellcount());
            final String shopid = goodslistBeanList.get(position).getShopid();
            final int cartNum = goodslistBeanList.get(position).getCartnum();

            if (imgPath.equals("")) {
                vh.imager.setImageResource(R.mipmap.yibeitong001);

            } else {
                try {
                    x.image().bind(vh.imager, "http://www.ybt9.com/" + imgPath);
                } catch (OutOfMemoryError e) {
                    System.out.println("手机内存不足！");
                }

            }

            vh.name.setText("" + name);
            vh.sellcount.setText("月售  " + sellcount);
            vh.cost.setText("￥" + cost);

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

            final String goodId = goodslistBeanList.get(position).getId();


            //添加购物车
            vh.shopCart_add.setOnClickListener(new View.OnClickListener() {
                //int cartNum = detBeen.get(position).getCartnum();

                @Override
                public void onClick(View v) {
                    vh.shopCart_add.setClickable(false);
                    good_num = Integer.valueOf(vh.shopCart_num.getText().toString());
                    cartnum = Integer.parseInt(tv_shopcart_num.getText().toString());

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

                                        double d_cost = Double.parseDouble(cost);

                                        if (addShopCart.isError() == false) {
                                            System.out.println("添加成功" + addShopCart.getMsg());
                                            if (good_num >= 0 && good_num < 100) {
                                                vh.shopCart_num.setVisibility(View.VISIBLE);
                                                vh.shopCart_sub.setVisibility(View.VISIBLE);
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
                                                //Toast.makeText(mContext, "错误", Toast.LENGTH_SHORT).show();
                                            }

                                            vh.shopCart_num.setText("" + good_num);

                                            tv_all_pay.setText("￥" + df.format(totalPrice) + "元");
                                            tv_shopcart_num.setText(""+cartnum);

                                            //响应按钮点击事件,调用子定义接口，并传入View
                                            //callbackCart.click(position, 2);

                                        } else {
                                            toast("添加失败 请重试");
                                            System.out.println("添加失败");
                                        }

                                    } else if (shopCartReturn.getMsg().isResult() == false) {
                                        Toast.makeText(mContext, "添加失败，库存不足", Toast.LENGTH_SHORT).show();
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
                int cartNum = goodslistBeanList.get(position).getCartnum();
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
                                            double a_cost = Double.parseDouble(goodslistBeanList.get(position).getCost());

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
                                                //Toast.makeText(mContext, "错误", Toast.LENGTH_SHORT).show();
                                            }

                                            tv_all_pay.setText("合计：￥" + df.format(totalPrice) + "元");

                                            vh.shopCart_num.setText("" + good_num);
                                            tv_shopcart_num.setText(""+cartnum);

                                        } else if (shopCartReturn.getMsg().isResult() == false) {
                                            Toast.makeText(mContext, "减少失败，库存不足", Toast.LENGTH_SHORT).show();
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

            return convertView;
        }

        public class ViewHolder {
            private ImageView imager;
            private TextView name, cost, sellcount;

            private TextView shopCart_num;
            private ImageView shopCart_add, shopCart_sub;

//            private ImageView iv_img, iv_add, iv_sub;
//            private TextView tv_name, tv_cost, tv_num;

        }
    }

}