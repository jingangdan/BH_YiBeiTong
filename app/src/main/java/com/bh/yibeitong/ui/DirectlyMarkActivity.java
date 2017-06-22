package com.bh.yibeitong.ui;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.adapter.ScreenFoodAdapter;
import com.bh.yibeitong.adapter.ShopAdapter;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.Shop;
import com.bh.yibeitong.bean.ShopType;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.DropDownMenu;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jingang on 2016/10/20.
 * 自营专区
 */
public class DirectlyMarkActivity extends BaseTextActivity {

    private DropDownMenu mDropDownMenu;

    private String headers[] = {"直营", "默认排序", "起送价"};
    private List<View> popupViews = new ArrayList<>();

    //private GirdDropDownAdapter cityAdapter;
    private ScreenFoodAdapter sfAdapter;
    //private String foods[] = {"全部", "果蔬市场", "粮油市场", "调料市场", "肉类市场",};
    private String orders[] = {"默认", "距离", "起送价", "推荐"};
    private String sprice[] = {"不限", "低于5元", "5元到10元", "10元以上"};

    private List<String> stringList = new ArrayList<>();

    private ShopAdapter shopAdapter;
    private List<Shop.MsgBean> shopBeenList = new ArrayList<>();

    ListView listView;


    //获取页面传值
    Intent intent;
    String shopid, shopName;

    String latitude, longitude;

    //筛选参数
    String ordertype = "", limitcosttype = "";

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_food_out);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("自营专区");
        setTitleBack(true, 0);

        inidData();

        intent = getIntent();
        shopid = intent.getStringExtra("shopid");
        shopName = intent.getStringExtra("shopName");

        latitude = intent.getStringExtra("latitude");
        longitude = intent.getStringExtra("longitude");

        System.out.println("latitude=" + latitude + "  " + longitude);
        //获取商店列表
        //getShopList(latitude, longitude, "", "1", "", 1);

        /*getNewShopList(String shopopentype, String ordertype, String areaid,
                String limitcosttype, String is_waimai, String is_goshop,
                String searchvalue, String lat, String lng,
                String shoptype, String is_com, String is_hot,
                String is_new, int page, int pagesize)*/

        initView();

        //1 代表是超市
        getShopType("1");

        getNewShopList("1", ordertype, "",
                limitcosttype, "", "",
                "", latitude, longitude,
                "0", "", "",
                "", 1, 10);
    }

    /**
     * 组建 初始化
     */
    public void inidData() {
        mDropDownMenu = (DropDownMenu) findViewById(R.id.foodout_dropDownMenu);
    }

    private void initView() {
        //init food menu
        final ListView foodView = new ListView(this);
        //sfAdapter = new ScreenFoodAdapter(this, Arrays.asList(foods));
        sfAdapter = new ScreenFoodAdapter(this, stringList);
        foodView.setDividerHeight(0);
        foodView.setAdapter(sfAdapter);

        //init order menu
        final ListView orderView = new ListView(this);
        sfAdapter = new ScreenFoodAdapter(this, Arrays.asList(orders));
        orderView.setDividerHeight(0);
        orderView.setAdapter(sfAdapter);

        //init price menu
        final ListView spriceView = new ListView(this);
        sfAdapter = new ScreenFoodAdapter(this, Arrays.asList(sprice));
        spriceView.setDividerHeight(0);
        spriceView.setAdapter(sfAdapter);


        //init popupViews
        popupViews.add(foodView);
        popupViews.add(orderView);
        popupViews.add(spriceView);

        //add item click event
        foodView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sfAdapter.setCheckItem(position);
                //mDropDownMenu.setTabText(position == 0 ? headers[0] : foods[position]);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : stringList.get(position));
                mDropDownMenu.closeMenu();

                //System.out.println("foodView=" + foods[position]);

                System.out.println(stringList.get(position));

                getNewShopList("1", ordertype, "",
                        limitcosttype, "", "",
                        "", latitude, longitude,
                        "0", "", "",
                        "", 1, 10);

            }
        });
        //add item click event
        orderView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sfAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : orders[position]);
                mDropDownMenu.closeMenu();

                System.out.println("orderView=" + orders[position]);
                /*if(position == 0){
                    ordertype = "0";
                }*/

                getNewShopList("1", String.valueOf(position), "",
                        limitcosttype, "", "",
                        "", latitude, longitude,
                        "0", "", "",
                        "", 1, 10);
            }
        });

        //add item click event
        spriceView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sfAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : sprice[position]);
                mDropDownMenu.closeMenu();

                System.out.println("spriceView=" + sprice[position]);

                getNewShopList("1", ordertype, "",
                        String.valueOf(position), "", "",
                        "", latitude, longitude,
                        "0", "", "",
                        "", 1, 10);

            }
        });


        //init context view
        //TextView contentView = new TextView(this);
        listView = new ListView(this);

        listView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        /*shopAdapter = new ShopAdapter(this, shopBeenList);
        listView.setAdapter(shopAdapter);*/

        //init dropdownview
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, listView);

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(SelfSupportActivity.this, ShopActivity.class);
                intent.putExtra("shopid", shopid);
                intent.putExtra("shopname", shopName);
                startActivity(intent);
            }
        });*/

    }

    @Override
    public void onBackPressed() {
        //退出activity前关闭菜单
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 获取商店类型
     *
     * @param is_market 是否是超市 0  1
     */
    public void getShopType(String is_market) {
        String PATH = HttpPath.PATH + HttpPath.SHOP_TYPE +
                "is_market=" + is_market;

        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("店铺类型" + result);

                        ShopType shopType = GsonUtil.gsonIntance().gsonToBean(result, ShopType.class);
                        //shopType.getMsg().get(0).getName();

                        int size = shopType.getMsg().size();
                        for (int i = 0; i < size; i++){
                            stringList.add(shopType.getMsg().get(i).getName());
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
     * shopopentype	是	int	0 表示 获取外卖，订台 预订 1表示商城
     * ordertype	是	string	0默认 1距离 2起送 3推荐
     * areaid	否	string	表示配送ID
     * limitcosttype	是	string	起送价格类型 0不限制 1低于 5元 2 5到10元 3 10元以上
     * is_waimai	否	string	表示外送
     * is_goshop	否	string	表示到店
     * searchvalue	否	string	搜索关键字
     * lat	是	string	经纬度
     * lng	是	string	经纬度
     * shoptype	否	string	店铺类型
     * is_com	否	string	推荐
     * is_hot	否	string	热卖
     * is_new	否	string	新店
     * page	否	string	页码
     * pagesize	否	string	每页个数
     */
    public void getNewShopList(String shopopentype, String ordertype, String areaid,
                               String limitcosttype, String is_waimai, String is_goshop,
                               String searchvalue, String lat, String lng,
                               String shoptype, String is_com, String is_hot,
                               String is_new, int page, int pagesize) {

        String PATH = HttpPath.PATH + HttpPath.SHOP_NEW_LIST +
                "shopopentype=" + shopopentype + "&ordertype=" + ordertype + "&areaid=" + areaid +
                "&limitcosttype=" + limitcosttype + "&is_waimai=" + is_waimai + "&is_goshop=" + is_goshop +
                "&searchvalue=" + searchvalue + "&lat=" + lat + "&lng=" + lng +
                "&shoptype=" + shoptype + "&is_com=" + is_com + "&is_hot=" + is_hot +
                "&is_new=" + is_new + "&page=" + page + "&pagesize=" + pagesize;

        System.out.println("" + PATH);

        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("新店铺列表" + result);
                        final Shop shop = GsonUtil.gsonIntance().gsonToBean(result, Shop.class);


                        shopAdapter = new ShopAdapter(DirectlyMarkActivity.this, shop.getMsg());

                        listView.setAdapter(shopAdapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                latitude = shop.getMsg().get(i).getLat();
                                longitude = shop.getMsg().get(i).getLng();
                                Intent intent = new Intent();

                                intent.putExtra("lat", latitude);
                                intent.putExtra("lng", longitude);
                                setResult(2, intent);// 设置resultCode，onActivityResult()中能获取到
                                DirectlyMarkActivity.this.finish();
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
}




/*
package com.bh.yibeitong.ui;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.adapter.ShopAdapter;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.Shop;
import com.bh.yibeitong.bean.ShopBean;

import java.util.ArrayList;
import java.util.List;

*/
/**
 * Created by jingang on 2016/10/30.
 * 直营便利店
 * <p>
 * 组件 初始化
 * <p>
 * PopupWindow 选择器
 * <p>
 * 组件 初始化
 * <p>
 * PopupWindow 选择器
 *//*

public class DirectlyMarkActivity extends BaseTextActivity {
    private Button but_diorectly_classify, but_directly_reorder, but_directly_sprice;

    //获取页面传值
    Intent intent;
    String shopid, shopName;

    private ListView lv_dm;
    private ShopAdapter shopAdapter;
    private List<Shop.MsgBean> shopBeanList = new ArrayList<>();

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_directly_mark);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("直营便利店");
        setTitleBack(true, 0);

        initData();

        shopAdapter = new ShopAdapter(this, shopBeanList);
        lv_dm.setAdapter(shopAdapter);

        intent = getIntent();
        shopid = intent.getStringExtra("shopid");
        shopName = intent.getStringExtra("shopName");

        //简单跳转
        lv_dm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(DirectlyMarkActivity.this, ShopActivity.class);
                intent.putExtra("shopid", shopid);
                intent.putExtra("shopname", shopName);
                startActivity(intent);
            }
        });


    }

    */
/**
 * 组件 初始化
 *//*

    public void initData() {
        lv_dm= (ListView) findViewById(R.id.lv_dm);

        but_diorectly_classify = (Button) findViewById(R.id.but_directly_classify);
        but_directly_reorder = (Button) findViewById(R.id.but_directly_reorder);
        but_directly_sprice = (Button) findViewById(R.id.but_directly_sprice);

        but_diorectly_classify.setOnClickListener(this);
        but_directly_reorder.setOnClickListener(this);
        but_directly_sprice.setOnClickListener(this);

    }

    */
/**
 * PopupWindow 选择器
 *//*

    private LinearLayout lin_directly_first, lin_directly_second, lin_directly_third;
    private View view;
    private PopupWindow popupWindow;
    private int[] location = new int[2];

    private TextView tv_first_all, tv_second_all, tv_third_all;

    public void setPopupWindow(View v) {
        popupWindow.setOutsideTouchable(true);

        // 允许点击外部消失
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        int[] location = new int[2];


        // 获得位置
        v.getLocationOnScreen(location);
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        popupWindow.showAsDropDown(v);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.but_directly_classify:

                view = getLayoutInflater().inflate(R.layout.popup_directly_classily, null);
                popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

                lin_directly_second = (LinearLayout) view.findViewById(R.id.lin_directly_second);
                lin_directly_second.setVisibility(View.INVISIBLE);

                lin_directly_third = (LinearLayout) view.findViewById(R.id.lin_directly_third);
                lin_directly_third.setVisibility(View.INVISIBLE);

                //找到组件
                tv_first_all = (TextView) view.findViewById(R.id.tv_first_all);
                //tv_first_all.setOnClickListener(this);

                setPopupWindow(v);

                tv_first_all.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        but_diorectly_classify.setText("" + tv_first_all.getText());

                    }
                });

                break;

            case R.id.but_directly_reorder:
                view = getLayoutInflater().inflate(R.layout.popup_directly_classily, null);
                popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

                lin_directly_first = (LinearLayout) view.findViewById(R.id.lin_directly_first);
                lin_directly_first.setVisibility(View.INVISIBLE);

                lin_directly_third = (LinearLayout) view.findViewById(R.id.lin_directly_third);
                lin_directly_third.setVisibility(View.INVISIBLE);

                setPopupWindow(v);

                tv_second_all = (TextView) view.findViewById(R.id.tv_second_all);

                tv_second_all.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        but_directly_reorder.setText("" + tv_second_all.getText());
                    }
                });

                break;

            case R.id.but_directly_sprice:
                view = getLayoutInflater().inflate(R.layout.popup_directly_classily, null);
                popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

                lin_directly_first = (LinearLayout) view.findViewById(R.id.lin_directly_first);
                lin_directly_first.setVisibility(View.INVISIBLE);

                lin_directly_second = (LinearLayout) view.findViewById(R.id.lin_directly_second);
                lin_directly_second.setVisibility(View.INVISIBLE);

                setPopupWindow(v);

                tv_third_all = (TextView) view.findViewById(R.id.tv_third_all);
                tv_third_all.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        but_directly_sprice.setText("" + tv_third_all.getText());
                    }
                });


                break;

            default:
                break;
        }

    }

}*/
