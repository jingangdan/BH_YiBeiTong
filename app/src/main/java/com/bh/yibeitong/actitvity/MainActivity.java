package com.bh.yibeitong.actitvity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.bh.yibeitong.BuildConfig;
import com.bh.yibeitong.R;
import com.bh.yibeitong.appupdate.ApkUpdateUtils;
import com.bh.yibeitong.base.BaseActivity;
import com.bh.yibeitong.bean.GoodsIndex;
import com.bh.yibeitong.bean.Register;
import com.bh.yibeitong.bean.VerCode;
import com.bh.yibeitong.fragment.FMHomePage;
import com.bh.yibeitong.fragment.FMOrderTest;
import com.bh.yibeitong.fragment.FMPerCen;
import com.bh.yibeitong.fragment.FMShopCar;
import com.bh.yibeitong.updateversion.SDCardUtils;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.utils.NetworkUtils;
import com.bh.yibeitong.utils.UpdataUtils;
import com.bh.yibeitong.view.UserInfo;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    //Linearlayout
    private LinearLayout lin_home_page, lin_orders, lin_shopping, lin_personal_center;

    //ImageView
    private ImageView iv_home_page, iv_orders, iv_shopping, iv_personal_center;

    //TextView
    private TextView tv_home_page, tv_orders, tv_shopping, tv_personal_center;

    //使用Fragment组件  简单开启事务

    FragmentManager fm;
    FragmentTransaction ft;

    //用于展示的Fragment  首页 我的订单 购物车 个人中心
    public FMHomePage fmHomePage;
    //public FMHomePageTest fmHomePageTest;

    //public FMOrders fmOrders;
    public FMOrderTest fmOrderTest;
    public FMShopCar fmShopping;
    public FMPerCen fmPerCen;

    public static String str_latitude;//纬度
    public static String str_longtitude;//经度

    //接收注册/登录返回值
    Intent getIntent;
    String login;

    private boolean isLogin = false;

    UserInfo userInfo;

    private List<GoodsIndex.MsgBean.ShopdetBean.PostdateBean> postdateList = new ArrayList<>();

    /*String 数组，保存需要检查的权限*/
    private static final String[] PERMISSIONS_CONTACT = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};

    /*定义一个请求码*/
    private static final int REQUEST_CONTACTS = 1000;

    private View view;

    /*是否加载数据成功*/
    private boolean isLoading = false;

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = LayoutInflater.from(this).inflate(R.layout.activity_main, null);

        setContentView(view);

        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数

        /*开始定位*/
        //getLocation();

        x.Ext.init(getApplication());
        x.Ext.setDebug(BuildConfig.DEBUG);

        userInfo = new UserInfo(getApplication());


        initData();
        //m_appNameStr = "ybt.apk";

        init();

    }

    private void requestSetPermissions(View view) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
            Snackbar.make(view, "permission_contacts_rationale",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            System.out.println("okokokokok");
                            ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS_CONTACT, REQUEST_CONTACTS);


                        }
                    }).setAction("no", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("nonononono");
                }
            })
                    .show();
        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS_CONTACT, REQUEST_CONTACTS);
        }
    }


    /*com.huawei.lcagent.util.FileUtils  MethodName:runCommand*/
    public void init() {
        if (Build.VERSION.SDK_INT > 22) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                System.out.println("没有");
                requestSetPermissions(view);
            } else {
                //mLocClient.start();
                System.out.println("有");

                isNetworkUtil();//判断网络连接状况

                getIntent = getIntent();
                login = getIntent.getStringExtra("login");

                if (login != null) {
                    Register register = GsonUtil.gsonIntance().gsonToBean(login, Register.class);
                    userInfo.saveUserInfo(login);

                }

                //fm = getFragmentManager();
                fm = getSupportFragmentManager();
                setTabSelection(0);
            }

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if (!Settings.System.canWrite(this)) {
//                    Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
//                            Uri.parse("package:" + getPackageName()));
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                } else {
//                    //有了权限，你要做什么呢？具体的动作
//                    System.out.println("已经有了ACTION_MANAGE_WRITE_SETTINGS");
//                }
//            }


//            if (hasExternalStoragePermission(MainActivity.this)) {
//                //开启
//
//                System.out.println("开了");
//                isNetworkUtil();//判断网络连接状况
//
//                getIntent = getIntent();
//                login = getIntent.getStringExtra("login");
//
//                if (login != null) {
//                    Register register = GsonUtil.gsonIntance().gsonToBean(login, Register.class);
//                    userInfo.saveUserInfo(login);
//
//                }
//
//                //fm = getFragmentManager();
//                fm = getSupportFragmentManager();
//                setTabSelection(0);
//
//            } else {
//                //未开启
//                System.out.println("没开");
//                if (ContextCompat.checkSelfPermission(MainActivity.this,
//                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//                    ActivityCompat.requestPermissions(
//                            MainActivity.this, new String[]{
//                                    Manifest.permission.ACCESS_COARSE_LOCATION
//                            }, 8
//                    );
//
//                }
//
//            }
        } else {
            //6.0版本以下

            isNetworkUtil();//判断网络连接状况

            getIntent = getIntent();
            login = getIntent.getStringExtra("login");

            if (login != null) {
                Register register = GsonUtil.gsonIntance().gsonToBean(login, Register.class);
                userInfo.saveUserInfo(login);

            }

            //fm = getFragmentManager();
            fm = getSupportFragmentManager();
            setTabSelection(0);
        }
    }

    /*三星可以*/
    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.ACCESS_COARSE_LOCATION";

    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
        return perm == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public int checkSelfPermission(String permission) {
        //
        return super.checkSelfPermission(permission);
    }

    private static boolean checkPermission(Context context, String permName, String pkgName) {
        PackageManager pm = context.getPackageManager();


        if (PackageManager.PERMISSION_GRANTED == pm.checkPermission(permName, pkgName)) {
            System.out.println(pkgName + "has permission : " + permName);
            return true;
        } else {
            //PackageManager.PERMISSION_DENIED == pm.checkPermission(permName, pkgName)
            System.out.println(pkgName + "not has permission : " + permName);
            return false;
        }
    }


    /**
     * 组件 初始化
     */
    private void initData() {

        //脚部
        lin_home_page = (LinearLayout) findViewById(R.id.lin_home_page);
        lin_orders = (LinearLayout) findViewById(R.id.lin_orders);
        lin_shopping = (LinearLayout) findViewById(R.id.lin_shopping);
        lin_personal_center = (LinearLayout) findViewById(R.id.lin_personal_center);

        lin_home_page.setOnClickListener(this);
        lin_orders.setOnClickListener(this);
        lin_shopping.setOnClickListener(this);
        lin_personal_center.setOnClickListener(this);

        iv_home_page = (ImageView) findViewById(R.id.iv_home_page);
        iv_orders = (ImageView) findViewById(R.id.iv_orders);
        iv_shopping = (ImageView) findViewById(R.id.iv_shopping);
        iv_personal_center = (ImageView) findViewById(R.id.iv_personal_center);

        tv_home_page = (TextView) findViewById(R.id.tv_home_page);
        tv_orders = (TextView) findViewById(R.id.tv_orders);
        tv_shopping = (TextView) findViewById(R.id.tv_shopping);
        tv_personal_center = (TextView) findViewById(R.id.tv_personal_center);

        int vercode = UpdataUtils.getVerCode(getApplicationContext()); // 用到前面第一节写的方法

        int verCode = UpdataUtils.getVerCode(this);
        String verName = UpdataUtils.getVerName(this);

        System.out.println("vercode = " + vercode);

        System.out.println("verCode = " + verCode);

        System.out.println("verName = " + verName);

        isGrantExternalRW(this);

        getLoadVersion(verName, verCode);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_home_page:
                //首页
                setTabSelection(0);
                break;

            case R.id.lin_orders:
                setTabSelection(1);
                break;

            case R.id.lin_shopping:
                setTabSelection(2);

                break;

            case R.id.lin_personal_center:
                setTabSelection(3);

                break;

            default:
                break;
        }


    }

    /**
     * 显示请求字符串
     */
    public void logMsg(String string) {
        //getShopAndGoods(str_latitude, str_longtitude);
    }


    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index
     */
    private void setTabSelection(int index) {
        int mycolor = getResources().getColor(R.color.mycolor);
        // 每次选中之前先清楚掉上次的选中状态
        clearSelection();
        // 开启一个Fragment事务
        FragmentTransaction ft = fm.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(ft);

        Bundle bundle = new Bundle();

        bundle.putString("login", userInfo.getUserInfo());

        bundle.putString("isLogin", String.valueOf(isLogin));

        switch (index) {
            case 0:

                iv_home_page.setImageResource(R.mipmap.home002);
                tv_home_page.setTextColor(mycolor);

                if (fmHomePage == null) {
                    // 如果HomePageFragment为空，则创建一个并添加到界面上
                    fmHomePage = new FMHomePage();
                    fmHomePage.setArguments(bundle);
                    ft.add(R.id.content, fmHomePage);
                } else {
                    // 如果HomePageFragment不为空，则直接将它显示出来
                    ft.show(fmHomePage);
                }


//                if (fmHomePageTest == null) {
//                    // 如果HomePageFragment为空，则创建一个并添加到界面上
//                    fmHomePageTest = new FMHomePageTest();
//                    fmHomePageTest.setArguments(bundle);
//                    ft.add(R.id.content, fmHomePageTest);
//                } else {
//                    // 如果HomePageFragment不为空，则直接将它显示出来
//                    ft.show(fmHomePageTest);
//                }


                break;
            case 1:

                iv_orders.setImageResource(R.mipmap.order002);
                tv_orders.setTextColor(mycolor);

                /*if (fmOrders == null) {
                    fmOrders = new FMOrders();
                    fmOrders.setArguments(bundle);
                    ft.add(R.id.content, fmOrders);
                } else {
                    ft.show(fmOrders);
                }*/
                if (fmOrderTest == null) {
                    fmOrderTest = new FMOrderTest();
                    fmOrderTest.setArguments(bundle);
                    ft.add(R.id.content, fmOrderTest);
                } else {
                    ft.show(fmOrderTest);
                }
                break;
            case 2:

                iv_shopping.setImageResource(R.mipmap.shopcar002);
                tv_shopping.setTextColor(mycolor);

                if (fmShopping == null) {
                    fmShopping = new FMShopCar();
                    fmShopping.setArguments(bundle);
                    ft.add(R.id.content, fmShopping);
                } else {
                    ft.show(fmShopping);
                }
                break;
            case 3:

                iv_personal_center.setImageResource(R.mipmap.mine002);
                tv_personal_center.setTextColor(mycolor);

                if (fmPerCen == null) {
                    fmPerCen = new FMPerCen();
                    fmPerCen.setArguments(bundle);
                    ft.add(R.id.content, fmPerCen);
                } else {
                    ft.show(fmPerCen);
                }
                break;

            default:

                break;
        }
        ft.commit();
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        //还原初始状态

        iv_home_page.setImageResource(R.mipmap.home001);
        iv_orders.setImageResource(R.mipmap.order001);
        iv_shopping.setImageResource(R.mipmap.shopcar001);
        iv_personal_center.setImageResource(R.mipmap.mine001);

        tv_home_page.setTextColor(Color.GRAY);
        tv_orders.setTextColor(Color.GRAY);
        tv_shopping.setTextColor(Color.GRAY);
        tv_personal_center.setTextColor(Color.GRAY);

    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    public void hideFragments(FragmentTransaction transaction) {
        if (fmHomePage != null) {
            transaction.hide(fmHomePage);
        }

//        if (fmHomePageTest != null) {
//            transaction.hide(fmHomePageTest);
//        }


       /* if (fmOrders != null) {
            transaction.hide(fmOrders);
        }*/

        if (fmOrderTest != null) {
            transaction.hide(fmOrderTest);
        }
        if (fmShopping != null) {
            transaction.hide(fmShopping);
        }
        if (fmPerCen != null) {
            transaction.hide(fmPerCen);
        }

    }


    //双击的时间间隔
    private long millis = 0;

    /**
     * 监听返回键 双击退出程序
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //
            boolean is;
            //双击退出
            if (System.currentTimeMillis() - millis < 1000) {

                //FMHomePage.mLocationClient.stop();
                return super.onKeyDown(keyCode, event);
            } else {
                Toast.makeText(this, "再次点击退出程序", Toast.LENGTH_SHORT).show();
                millis = System.currentTimeMillis();
                return false;
            }

        }
        return false;
    }

    /**
     * 根据经纬度获取商店以及商店商品的信息
     *
     * @param latitude
     * @param longtitude
     */
    public void getShopAndGoods(String latitude, String longtitude) {

        String PATH = HttpPath.PATH + HttpPath.GOODS_INDEX + "lat=" + latitude + "&lng=" + longtitude;
        RequestParams params = new RequestParams(PATH);
        System.out.println("主界面" + PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("主界面：" + result);

                        GoodsIndex goodsIndex = GsonUtil.gsonIntance().gsonToBean(result, GoodsIndex.class);
                        postdateList = goodsIndex.getMsg().getShopdet().getPostdate();

                        //PostData
                        userInfo.savePostData(new Gson().toJson(postdateList));

                        //首页商品cateFoodList
                        userInfo.saveMeetings(new Gson().toJson(goodsIndex.getMsg().getCatefoodslist()));

                        //首页商店fuck
                        //userInfo.saveUserInfo(new Gson().toJson(goodsIndex.getMsg().getShopinfo()));
                        userInfo.saveShopInfo(goodsIndex.getMsg().getShopinfo().getId().toString());


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
     * 开启定位
     */
    public void getLocation() {
        super.onStart();

        initLocation();
        mLocationClient.start();

    }

    /*设置定位属性*/
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        option.setScanSpan(0);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集

        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        mLocationClient.setLocOption(option);
    }


    /*获取定位结果*/
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            /*定位成功结束定位*/
            mLocationClient.start();

            //获取定位结果
            StringBuffer sb = new StringBuffer(256);

            sb.append("time : ");
            sb.append(location.getTime());    //获取定位时间

            sb.append("\nerror code : ");
            sb.append(location.getLocType());    //获取类型类型

            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());    //获取纬度信息

            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());    //获取经度信息

            sb.append("\nradius : ");
            sb.append(location.getRadius());    //获取定位精准度

            if (location.getLocType() == BDLocation.TypeGpsLocation) {

                // GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());    // 单位：公里每小时

                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());    //获取卫星数

                sb.append("\nheight : ");
                sb.append(location.getAltitude());    //获取海拔高度信息，单位米

                sb.append("\ndirection : ");
                sb.append(location.getDirection());    //获取方向信息，单位度

                sb.append("\naddr : ");
                sb.append(location.getAddrStr());    //获取地址信息

                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {

                System.out.println("" + location.getStreet() + "   " + location.getCity());


                // 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());    //获取地址信息

                sb.append("\noperationers : ");
                sb.append(location.getOperators());    //获取运营商信息

                sb.append("\ndescribe : ");
                sb.append("网络定位成功");

            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {

                // 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");

            } else if (location.getLocType() == BDLocation.TypeServerError) {

                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");

            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {

                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");

            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {

                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");

            }

            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());    //位置语义化信息

            List<Poi> list = location.getPoiList();    // POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }

            System.out.println("首页定位" + sb.toString());

            //logMsg("");
        }
    }


    /**
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     */
//    private BDLocationListener mListener = new BDLocationListener() {
//
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//
//            System.out.println("location stop");
//
//            locationService.stop();//出现结果即停止定位
//
//            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
//                StringBuffer sb = new StringBuffer(512);
//                sb.append("time : ");
//                /**
//                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
//                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
//                 */
//                sb.append(location.getTime());
//                sb.append("\nlocType : ");// 定位类型
//                sb.append(location.getLocType());
//                sb.append("\nlocType description : ");// *****对应的定位类型说明*****
//                //sb.append(location.getLocTypeDescription());
//                sb.append("\nlatitude : ");// 纬度
//                sb.append(location.getLatitude());
//
//                //获取纬度
//                str_latitude = Double.toString(location.getLatitude());
//
//                sb.append("\nlontitude : ");// 经度
//                sb.append(location.getLongitude());
//
//                //获取经度
//                str_longtitude = Double.toString(location.getLongitude());
//
//                sb.append("\nradius : ");// 半径
//                sb.append(location.getRadius());
//                sb.append("\nCountryCode : ");// 国家码
//                sb.append(location.getCountryCode());
//                sb.append("\nCountry : ");// 国家名称
//                sb.append(location.getCountry());
//                sb.append("\ncitycode : ");// 城市编码
//                sb.append(location.getCityCode());
//                sb.append("\ncity : ");// 城市
//                sb.append(location.getCity());
//                sb.append("\nDistrict : ");// 区
//                sb.append(location.getDistrict());
//                sb.append("\nStreet : ");// 街道
//                sb.append(location.getStreet());
//                sb.append("\naddr : ");// 地址信息
//                sb.append(location.getAddrStr());
//                sb.append("\nUserIndoorState: ");// *****返回用户室内外判断结果*****
//                //sb.append(location.getUserIndoorState());
//                sb.append("\nDirection(not all devices have value): ");
//                sb.append(location.getDirection());// 方向
//                sb.append("\nlocationdescribe: ");
//                sb.append(location.getLocationDescribe());// 位置语义化信息
//                sb.append("\nPoi: ");// POI信息
//                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
//                    for (int i = 0; i < location.getPoiList().size(); i++) {
//                        Poi poi = (Poi) location.getPoiList().get(i);
//                        sb.append(poi.getName() + ";");
//                    }
//                }
//                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
//                    sb.append("\nspeed : ");
//                    sb.append(location.getSpeed());// 速度 单位：km/h
//                    sb.append("\nsatellite : ");
//                    sb.append(location.getSatelliteNumber());// 卫星数目
//                    sb.append("\nheight : ");
//                    sb.append(location.getAltitude());// 海拔高度 单位：米
//                    sb.append("\ngps status : ");
//                    //sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
//                    sb.append("\ndescribe : ");
//                    sb.append("gps定位成功");
//                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
//                    // 运营商信息
//                    if (location.hasAltitude()) {// *****如果有海拔高度*****
//                        sb.append("\nheight : ");
//                        sb.append(location.getAltitude());// 单位：米
//                    }
//                    sb.append("\noperationers : ");// 运营商信息
//                    sb.append(location.getOperators());
//                    sb.append("\ndescribe : ");
//                    sb.append("网络定位成功");
//                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
//                    sb.append("\ndescribe : ");
//                    sb.append("离线定位成功，离线定位结果也是有效的");
//                } else if (location.getLocType() == BDLocation.TypeServerError) {
//                    sb.append("\ndescribe : ");
//                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
//                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
//                    sb.append("\ndescribe : ");
//                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
//                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
//                    sb.append("\ndescribe : ");
//                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
//                }
//
//                System.out.println("描述" + sb.toString());
//
//                logMsg(sb.toString());
//
//            }
//        }
//
//    };

    /**
     * 判断网络连接情况
     */
    public void isNetworkUtil() {
        if (NetworkUtils.isNotWorkAvilable(this)) {
            if (NetworkUtils.getCurrentNetType(this) != null) {
                //有网络连接 判断网络的连接类型是否符合标准
                //可以根据规定进行数据的刷新

                System.out.println("判断网络情况");
                //toast(NetworkUtils.getCurrentNetType(this));

                //getLocation();//获取经纬度
                //getShopAndGoods("35.111029", "118.35174");

            }
        } else {
            //无网络连接  显示缓存数据
            //toast("无网络连接");

            //缓存本地数据

        }

    }

    /**
     * 检查版本更新
     *
     * @param v 当前版本号
     */
    public void getLoadVersion(final String v, final int verCoder) {
        String PATH = HttpPath.path + HttpPath.ANDROID_CHECKV + "" +
                "v=" + v;

        RequestParams params = new RequestParams(PATH);

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("检查版本号" + result);

                VerCode verCode = GsonUtil.gsonIntance().gsonToBean(result, VerCode.class);

                boolean error = verCode.isError();

                if (error == false) {
                    System.out.println("最新" + verCode.getMsg());
                    //notNewVersionDlgShow(verCode.getMsg().getUrl(),verCode.getMsg().getMsg());
                } else if (error == true) {
                    System.out.println("旧的" + verCode.getMsg());
                    //doNewVersionUpdate(verCode.getMsg().getUrl(), verCode.getMsg().getMsg());


                    if ((verCoder + 100) >= Integer.parseInt(verCode.getMsg().getVersion())) {
                        //toast(verCode.getMsg().getMsg().toString());
                        System.out.println("最新" + verCode.getMsg());
                    } else {
                        final Dialog dialog = new AlertDialog.Builder(MainActivity.this).create();
                        final File file = new File(SDCardUtils.getRootDirectory() + "/ybt_updateVersion/ybt.apk");
                        dialog.setCancelable(true);// 可以用“返回键”取消
                        dialog.setCanceledOnTouchOutside(false);//
                        dialog.show();
                        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.version_update_dialog, null);
                        dialog.setContentView(view);

                        final Button btnOk = (Button) view.findViewById(R.id.btn_update_id_ok);
                        Button btnCancel = (Button) view.findViewById(R.id.btn_update_id_cancel);
                        TextView tvContent = (TextView) view.findViewById(R.id.tv_update_content);
                        TextView tvUpdateTile = (TextView) view.findViewById(R.id.tv_update_title);
                        final TextView tvUpdateMsgSize = (TextView) view.findViewById(R.id.tv_update_msg_size);

                        //tvContent.setText(versionInfo.getVersionDesc());
                        tvContent.setText("");//更新内容
                        tvUpdateTile.setText("当前版本：" + v);
                        tvUpdateMsgSize.setText("新版本：" + verCode.getMsg().getVersion());

                        if (file.exists() && file.getName().equals("ybt.apk")) {
                            //tvUpdateMsgSize.setText("新版本已经下载，是否安装？");
                        } else {
                            //tvUpdateMsgSize.setText("新版本大小：" + versionInfo.getVersionSize());
                        }

                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            if (v.getId() == R.id.btn_update_id_ok) {

                                ApkUpdateUtils.download(MainActivity.this, "http://www.ybt9.com/app/ybt.apk", getResources().getString(R.string.app_name));

//新版本已经下载
//                                if (file.exists() && file.getName().equals("ybt.apk")) {
//                                    Intent intent = ApkUtils.getInstallIntent(file);
//                                    startActivity(intent);
//                                } else {
//                                    //没有下载，则开启服务下载新版本
//                                    Intent intent = new Intent(MainActivity.this, UpdateVersionService.class);
//                                    intent.putExtra("downloadUrl", "http://www.ybt9.com/app/ybt.apk");
//                                    startService(intent);
//                                }
                            }
                        }
                    });

                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });


//                    ApkUpdateUtils.download(MainActivity.this, "http://www.ybt9.com/app/ybt.apk", getResources().getString(R.string.app_name));


//                    UpdateVersionUtil.localCheckedVersion(MainActivity.this,
//                            new UpdateVersionUtil.UpdateListener() {
//                                @Override
//                                public void onUpdateReturned(int updateStatus, VersionInfo versionInfo) {
//                                    //判断回调过来的版本检测状态
//                                    switch (updateStatus) {
//                                        case UpdateStatus.YES:
//                                            //弹出更新提示
//                                            UpdateVersionUtil.showDialog(MainActivity.this, versionInfo, v);
//                                            break;
//                                        case UpdateStatus.NO:
//                                            //没有新版本
//                                            toast("已经是最新版本了!");
//                                            break;
//                                        case UpdateStatus.NOWIFI:
//                                            //当前是非wifi网络
//                                            toast("只有在wifi下更新！");
//                                            break;
//                                        case UpdateStatus.ERROR:
//                                            //检测失败
//                                            toast("检测失败，请稍后重试！");
//                                            break;
//                                        case UpdateStatus.TIMEOUT:
//                                            //链接超时
//                                            toast("链接超时，请检查网络设置!");
//                                            break;
//                                    }
//                                }
//                            }, v);

                    }
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

//    /**
//     * 提示更新新版本
//     */
//    private void doNewVersionUpdate(final String str_url, String str_msg) {
//
//        Dialog dialog = new AlertDialog.
//                Builder(this).
//                setTitle("软件更新").
//                setMessage(str_msg)
//                // 设置内容
//                .setPositiveButton("更新",
//                        // 设置确定按钮
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog,
//                                                int which) {
//                                downFile("http://www.ybt9.com/app/ybt.apk");
//                            }
//                        })
//                .setNegativeButton("暂不更新",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog,
//                                                int whichButton) {
//                                // 点击"取消"按钮之后退出程序
//                                dialog.dismiss();
//                            }
//                        }).create();// 创建
//        // 显示对话框
//        dialog.show();
//    }

//    /**
//     * 提示当前为最新版本
//     */
//    private void notNewVersionDlgShow(String str_url, String str_msg) {
//
//        Dialog dialog = new AlertDialog.Builder(this).setTitle("软件更新")
//                .setMessage(str_msg)// 设置内容
//                .setPositiveButton("确定",// 设置确定按钮
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog,
//                                                int which) {
//                                dialog.dismiss();
//                                //finish();
//                            }
//                        }).create();// 创建
//        // 显示对话框
//        dialog.show();
//    }

    /*显示版本情况*/
//    ProgressDialog m_progressDlg;
//    Handler m_mainHandler;
//
//
//    String m_appNameStr; //下载到本地要给这个APP命的名字
//
//    private void downFile(final String url) {
//        m_progressDlg.setMessage("正在下载，请稍候。。。");
//        m_progressDlg.show();
//        new Thread() {
//            public void run() {
//                HttpClient client = new DefaultHttpClient();
//                HttpGet get = new HttpGet(url);
//                HttpResponse response;
//                try {
//                    response = client.execute(get);
//                    HttpEntity entity = response.getEntity();
//                    long length = entity.getContentLength();
//
//                    m_progressDlg.setMax((int) length);//设置进度条的最大值
//
//                    InputStream is = entity.getContent();
//                    FileOutputStream fileOutputStream = null;
//                    if (is != null) {
//                        File file = new File(
//                                Environment.getExternalStorageDirectory(),
//                                m_appNameStr);
//                        fileOutputStream = new FileOutputStream(file);
//                        byte[] buf = new byte[1024];
//                        int ch = -1;
//                        int count = 0;
//                        while ((ch = is.read(buf)) != -1) {
//                            fileOutputStream.write(buf, 0, ch);
//                            count += ch;
//                            if (length > 0) {
//                                m_progressDlg.setProgress(count);
//                            }
//                        }
//                    }
//                    fileOutputStream.flush();
//                    if (fileOutputStream != null) {
//                        fileOutputStream.close();
//                    }
//                    down();  //告诉HANDER已经下载完成了，可以安装了
//                } catch (ClientProtocolException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }

//    /**
//     * 告诉HANDER已经下载完成了，可以安装了
//     */
//    private void down() {
//        m_mainHandler.post(new Runnable() {
//            public void run() {
//                m_progressDlg.cancel();
//                update();
//            }
//        });
//    }

    /**
     * 安装程序
     */

//    void update() {
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setDataAndType(Uri.fromFile(new File(Environment
//                        .getExternalStorageDirectory(), m_appNameStr)),
//                "application/vnd.android.package-archive");
//        startActivity(intent);
//    }

    /**
     * 解决安卓6.0以上版本不能读取外部存储权限的问题
     *
     * @param activity
     * @return
     */
    public static boolean isGrantExternalRW(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);

            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CONTACTS) {
            if (PackageManager.PERMISSION_GRANTED == grantResults[0]) {
                //mLocClient.start();
                //getLocation();
                isNetworkUtil();//判断网络连接状况

                getIntent = getIntent();
                login = getIntent.getStringExtra("login");

                if (login != null) {
                    Register register = GsonUtil.gsonIntance().gsonToBean(login, Register.class);
                    userInfo.saveUserInfo(login);

                }

                //fm = getFragmentManager();
                fm = getSupportFragmentManager();
                setTabSelection(0);


            } else {
                isNetworkUtil();//判断网络连接状况

                getIntent = getIntent();
                login = getIntent.getStringExtra("login");

                if (login != null) {
                    Register register = GsonUtil.gsonIntance().gsonToBean(login, Register.class);
                    userInfo.saveUserInfo(login);

                }

                //fm = getFragmentManager();
                fm = getSupportFragmentManager();
                setTabSelection(0);
                //Toast.makeText(getApplicationContext(), "授权不通过", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//
//        if (requestCode == 8) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                //callPhone();
//                isNetworkUtil();//判断网络连接状况
//
//                getIntent = getIntent();
//                login = getIntent.getStringExtra("login");
//
//                if (login != null) {
//                    Register register = GsonUtil.gsonIntance().gsonToBean(login, Register.class);
//                    userInfo.saveUserInfo(login);
//
//                }
//
//                //fm = getFragmentManager();
//                fm = getSupportFragmentManager();
//                setTabSelection(0);
//                //Toast.makeText(GuideActivity.this, "开启定位权限", Toast.LENGTH_SHORT).show();
//
//            } else {
//                isNetworkUtil();//判断网络连接状况
//
//                getIntent = getIntent();
//                login = getIntent.getStringExtra("login");
//
//                if (login != null) {
//                    Register register = GsonUtil.gsonIntance().gsonToBean(login, Register.class);
//                    userInfo.saveUserInfo(login);
//
//                }
//
//                //fm = getFragmentManager();
//                fm = getSupportFragmentManager();
//                setTabSelection(0);
//                // Permission Denied
//                //Toast.makeText(GuideActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
//            }
//            return;
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }

}



/*
* 0x01
* 0x02首页商品详情
* 0x03 购物车
* 0x04 购物车下单
*
*
* */