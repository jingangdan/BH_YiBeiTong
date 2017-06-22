package com.bh.yibeitong.ui;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.adapter.ScreenFoodAdapter;
import com.bh.yibeitong.adapter.ShopAdapter;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.Shop;
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
public class SelfSupportActivity extends BaseTextActivity {

    private DropDownMenu mDropDownMenu;

    private String headers[] = {"美食外卖", "默认排序", "起送价"};
    private List<View> popupViews = new ArrayList<>();

    //private GirdDropDownAdapter cityAdapter;
    private ScreenFoodAdapter sfAdapter;
    private String foods[] = {"全部", "果蔬市场", "粮油市场", "调料市场", "肉类市场",};
    private String orders[] = {"默认", "距离", "起送价", "推荐"};
    private String sprice[] = {"不限", "低于5元", "5元到10元", "10元以上"};

    private ShopAdapter shopAdapter;
    private List<Shop> shopBeenList = new ArrayList<>();

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

        getNewShopList("1",ordertype,"",
                limitcosttype,"","",
                "",latitude,longitude,
                "0","","",
                "",1,10);
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
        sfAdapter = new ScreenFoodAdapter(this, Arrays.asList(foods));
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
                mDropDownMenu.setTabText(position == 0 ? headers[0] : foods[position]);
                mDropDownMenu.closeMenu();

                System.out.println("foodView=" + foods[position]);

                getNewShopList("1",ordertype,"",
                        limitcosttype,"","",
                        "",latitude,longitude,
                        "0","","",
                        "",1,10);

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

                getNewShopList("1",String.valueOf(position),"",
                        limitcosttype,"","",
                        "",latitude,longitude,
                        "0","","",
                        "",1,10);
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

                getNewShopList("1",ordertype,"",
                        String.valueOf(position),"","",
                        "",latitude,longitude,
                        "0","","",
                        "",1,10);

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
     * lat	是	string	经纬度
     * lng	是	string	经纬度
     * showtype	否	string	排序方式 ：juli/cost/is_com
     * backtype	是	string	如果大于0返回店铺开店时间
     * areaid	否	string	区域id
     * page	否	string	页码
     */
    public void getShopList(String lat, String lng, String showtype, String backtype
            , String areaid, int page) {

        String PATH = HttpPath.PATH + HttpPath.SHOP_LIST +
                "lat=" + lat + "&lng=" + lng + "&showtype=" + showtype +
                "&backtype=" + "&areaid=" + areaid + "&page=" + page;

        System.out.println(PATH);

        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {

                        System.out.println("商店列表" + result);

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
     *  https://www.ybt9.com//index.php?ctrl=app&source=1&datatype=json&action=newshop&shopopentype=1&ordertype=&areaid=&limitcosttype=&is_waimai=&is_goshop=&searchvalue=&lat=null&lng=null&shoptype=0&is_com=&is_hot=&is_new=&page=1&pagesize=10
     *
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

        System.out.println(""+PATH);

        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("新店铺列表"+result);
                        final Shop shop = GsonUtil.gsonIntance().gsonToBean(result, Shop.class);

                        shopAdapter = new ShopAdapter(SelfSupportActivity.this, shop.getMsg());

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
                                SelfSupportActivity.this.finish();
                            }
                        });

                        //shop.getMsg();

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
