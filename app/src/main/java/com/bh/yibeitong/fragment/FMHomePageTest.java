package com.bh.yibeitong.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.bh.yibeitong.Interface.OnItemClickListener;
import com.bh.yibeitong.Interface.OnItemLongClickListener;
import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseFragment;
import com.bh.yibeitong.bean.GoodsIndex;
import com.bh.yibeitong.bean.WxADV;
import com.bh.yibeitong.bean.homepage.AdvByType;
import com.bh.yibeitong.bean.shopbean.Recommed;
import com.bh.yibeitong.dialog.CustomProgress;
import com.bh.yibeitong.lunbotu.ADInfo;
import com.bh.yibeitong.lunbotu.CycleViewPager;
import com.bh.yibeitong.lunbotu.ViewFactory;
import com.bh.yibeitong.refresh.MyGridView;
import com.bh.yibeitong.refresh.recyclerview.BaseRecyclerViewHolder;
import com.bh.yibeitong.ui.homepage.JoinActivity;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.utils.MD5Util;
import com.bh.yibeitong.utils.NetworkUtils;
import com.bh.yibeitong.utils.XUtilsImageUtils;
import com.bh.yibeitong.view.MyScrollView;
import com.bh.yibeitong.view.UserInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FMHomePageTest extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener,
        MyScrollView.ISmartScrollChangedListener {
    private View view;

    /*接口地址*/
    private String PATH = "";

    /*轮播图*/
    private CycleViewPager cycleViewPager;
    private List<WxADV.MsgBean> advList = new ArrayList<>();
    private List<ImageView> views = new ArrayList<ImageView>();
    private List<ADInfo> infos;
    private ADInfo info;

    /*首页广告位*/
    private ImageView iv_adv001, iv_adv002;
    private String linkurl;

    /*UI显示*/
    private RelativeLayout rel_new_header;//背景
    private ImageView iv_new_scaning, iv_new_search;//扫一扫 搜索
    private EditText et_new_address;//定位地址
    private String shopid = "", startTime, mapphone, address;

    public static String shopName;

    /*积分抢购 抢购专区 商家入驻 跑腿服务*/
    private LinearLayout lin_jifen, lin_qianggou, lin_ruzhu, lin_paotui;

    /*页面之间的跳转传值*/
    private Intent intent;
    private String latitude = "", longtitude = "";//经纬度
    private int page = 0;//数据页数

    /*本地轻量型缓存*/
    private UserInfo userInfo;
    private String jingang = "";//“空”未登录  0未登录  1登录

    /*商品分类（八个按钮）*/
    private List<Recommed.MsgBean> msgBeen = new ArrayList<>();
    private MyGridView myGridView;
    private RecommedAdapter recommedAdapter;

    /*首页商品展示*/
    private RecyclerView rv_classify;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<GoodsIndex.MsgBean.CatefoodslistBean> ceFoodList = new ArrayList<>();
    private List<GoodsIndex.MsgBean.CatefoodslistBean> ceFood = new ArrayList<>();

    /*加载提示框*/
    private CustomProgress progressDialog = null;

    /*是否加载数据成功*/
    private boolean isLoading = false;

    public boolean isToTops = false;

    /*用于定位*/
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home_page_test, container, false);

        mLocationClient = new LocationClient(getActivity().getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数

        initData();

        return view;
    }

    /*组件初始化*/
    public void initData() {
        userInfo = new UserInfo(getActivity().getApplication());
        jingang = userInfo.getLogin();

        rel_new_header = (RelativeLayout) view.findViewById(R.id.rel_new_header);
        iv_new_scaning = (ImageView) view.findViewById(R.id.iv_new_scanning);
        iv_new_search = (ImageView) view.findViewById(R.id.iv_new_search);
        et_new_address = (EditText) view.findViewById(R.id.et_new_address);

        iv_new_scaning.setOnClickListener(this);
        iv_new_search.setOnClickListener(this);
        et_new_address.setOnClickListener(this);

        lin_jifen = (LinearLayout) view.findViewById(R.id.lin_jinfen);
        lin_qianggou = (LinearLayout) view.findViewById(R.id.lin_qianggou);
        lin_ruzhu = (LinearLayout) view.findViewById(R.id.lin_ruzhu);
        lin_paotui = (LinearLayout) view.findViewById(R.id.lin_paotui);

        lin_jifen.setOnClickListener(this);
        lin_qianggou.setOnClickListener(this);
        lin_ruzhu.setOnClickListener(this);
        lin_paotui.setOnClickListener(this);

        cycleViewPager = (CycleViewPager) getActivity().getFragmentManager().findFragmentById(R.id.fragment_cycle_viewpager_content);

        iv_adv001 = (ImageView) view.findViewById(R.id.iv_adv001);
        iv_adv002 = (ImageView) view.findViewById(R.id.iv_adv002);
        iv_adv001.setOnClickListener(this);
        iv_adv002.setOnClickListener(this);

        myGridView = (MyGridView) view.findViewById(R.id.mgv_test_homepage);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.sr_home_page_test);

        swipeRefreshLayout.setOnRefreshListener(this);

        rv_classify = (RecyclerView) view.findViewById(R.id.rv_classify);
        rv_classify.setLayoutManager(new LinearLayoutManager(getActivity()));

        /*判断网络连接状态*/
        isNetworkUtil();

        configImageLoader();//轮播图显示

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onClick(View view) {

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

    /*输出定位结果*/
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            //获取定位结果
            StringBuffer sb = new StringBuffer(256);

            sb.append("time : ");
            sb.append(location.getTime());    //获取定位时间

            sb.append("\nerror code : ");
            sb.append(location.getLocType());    //获取类型类型

            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());    //获取纬度信息

            latitude = Double.toString(location.getLatitude());

            longtitude = Double.toString(location.getLongitude());

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

            Log.i("BaiduLocationApiDem", sb.toString());

            logMsg("");
        }
    }


    public void logMsg(String string) {
        getShopGoods(latitude, longtitude, page);

    }

    /*轮播图点击事件*/
    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener =
            new CycleViewPager.ImageCycleViewListener() {

                @Override
                public void onImageClick(ADInfo info, int position, View imageView) {
                    if (cycleViewPager.isCycle()) {
                        //toast("positon = "+position);
                        //此处position是从1开始 故需要 - 1 操作
                        if (isNetworkUtils()) {
                            intent = new Intent(getActivity(), JoinActivity.class);
                            intent.putExtra("title", advList.get(position - 1).getTitle());
                            intent.putExtra("url", "http://www.ybt9.com/" + advList.get(position - 1).getLinkurl());
                            startActivity(intent);
                        } else {
                            toast("无网络连接");
                        }

                    }

                }

            };


    @Override
    public void onRefresh() {

        ceFoodList.clear();

        pdStyle();

        getShopGoods(latitude, longtitude, 1);

    }

    /**
     * 首页推荐 店铺优选
     * 获取店铺推荐区
     *
     * @param shopid
     */
    public void getCateRecommed(final String shopid) {
        String PATH = HttpPath.path + HttpPath.CATE_GETRECOMMED + "shopid=" + shopid;

        RequestParams params = new RequestParams(PATH);
        System.out.println("" + PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("获取店铺推荐区" + result);
                        final Recommed recommed = GsonUtil.gsonIntance().gsonToBean(result, Recommed.class);

                        msgBeen = recommed.getMsg();

                        recommedAdapter = new RecommedAdapter(getActivity(), msgBeen);
                        myGridView.setAdapter(recommedAdapter);

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
     * 获取首页数据
     *
     * @param latitude
     * @param longtitude 118.351852   35.111124
     */
    public void getShopGoods(String latitude, String longtitude, final int pages) {
        String Path = HttpPath.PATH + HttpPath.GOODSINDEX +
                "lat=" + latitude + "&lng=" + longtitude + "" + "&page=" + pages;

        System.out.println("" + Path);

        RequestParams params = new RequestParams(Path);
        x.http().post(params,
                new Callback.CommonCallback<String>() {
                    public void onSuccess(String result) {

                        swipeRefreshLayout.setRefreshing(false);

                        isToTops = true;

                        isLoading = true;

                        stopProgressDialog();//

                        System.out.println("首页" + result);
                        //MD5加密数据
                        String results = MD5Util.getUnicode(result);

                        GoodsIndex goodsIndex = GsonUtil.gsonIntance().gsonToBean(results, GoodsIndex.class);

                        shopName = goodsIndex.getMsg().getShopinfo().getShopname();
                        shopid = goodsIndex.getMsg().getShopinfo().getId();
                        startTime = goodsIndex.getMsg().getShopinfo().getStarttime();
                        mapphone = goodsIndex.getMsg().getShopinfo().getMaphone();
                        address = goodsIndex.getMsg().getShopinfo().getAddress();

                        //获取商品分类（八个按钮）
                        getCateRecommed(shopid);

                        //商店id
                        userInfo.saveShopInfo(shopid);

                        userInfo.saveShopDet(goodsIndex.getMsg().getShopdet().getLimitcost());

                        et_new_address.setText("" + shopName);

                        et_new_address.setTextColor(Color.WHITE);

                        et_new_address.setBackgroundResource(R.drawable.editshape);

                        if (goodsIndex.getMsg().getCatefoodslist().toString().equals("[]")) {
                            toast("没有更多数据");

                        } else {

                            ceFood = goodsIndex.getMsg().getCatefoodslist();
                            ceFoodList.addAll(ceFood);
                            recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), ceFoodList);

                            GridLayoutManager llmv = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
                            //设置为一个4列的纵向网格布局

                            rv_classify.setLayoutManager(llmv);

                            rv_classify.setAdapter(recyclerViewAdapter);


                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        System.out.println("首页onError" + ex.toString());
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        System.out.println("onCancelled");
                    }

                    @Override
                    public void onFinished() {
                        System.out.println("onFinished");
                    }
                });
    }


    /**
     * 获取轮播图
     */
    public void getWxAdv() {
        String PATH = HttpPath.PATH + HttpPath.ADV +
                "type=weixinlb";
        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onSuccess(String result) {
                        //pd.dismiss();
                        System.out.println("获取轮播图" + result);
                        WxADV wxADV = GsonUtil.gsonIntance().gsonToBean(result, WxADV.class);

                        advList = wxADV.getMsg();

                        userInfo.saveADV(new Gson().toJson(wxADV.getMsg()));

                        views.clear();//刷新之前先清除一下

                        infos = new ArrayList<>();

                        for (int i = 0; i < wxADV.getMsg().size(); i++) {
                            info = new ADInfo();
                            info.setUrl("http://www.ybt9.com/" + wxADV.getMsg().get(i).getImg());
                            info.setContent("" + wxADV.getMsg().get(i).getTitle());
                            info.setImg("http://www.ybt9.com/" + wxADV.getMsg().get(i).getLinkurl());
                            infos.add(info);
                        }

                        // 将最后一个ImageView添加进来
                        views.add(ViewFactory.getImageView(getActivity(), infos.get(infos.size() - 1).getUrl()));
                        for (int i = 0; i < infos.size(); i++) {
                            views.add(ViewFactory.getImageView(getActivity(), infos.get(i).getUrl()));
                        }
                        // 将第一个ImageView添加进来
                        views.add(ViewFactory.getImageView(getActivity(), infos.get(0).getUrl()));

                        // 设置循环，在调用setData方法前调用
                        cycleViewPager.setCycle(true);

                        // 在加载数据前设置是否循环
                        cycleViewPager.setData(views, infos, mAdCycleViewListener);
                        //设置轮播
                        cycleViewPager.setWheel(true);

                        // 设置轮播时间，默认5000ms
                        cycleViewPager.setTime(3000);
                        //设置圆点指示图标组居中显示，默认靠右
                        cycleViewPager.setIndicatorCenter();

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
     * 广告位图片1
     *
     * @param type
     */
    public void getAdvByType(String type) {
        PATH = HttpPath.PATH + HttpPath.ADV + "type=" + type;

        RequestParams params = new RequestParams(PATH);
        System.out.println("广告位一" + PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("广告位一" + result);
                        AdvByType advByType = GsonUtil.gsonIntance().gsonToBean(result, AdvByType.class);

                        //x.image().bind(iv_adv002, "http://www.ybt9.com/" + advByType.getMsg().get(0).getImg());

                        XUtilsImageUtils.display(iv_adv002, "http://www.ybt9.com/" + advByType.getMsg().get(0).getImg(), 0);

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
     * 广告位图片2
     *
     * @param type
     */
    public void getAdvByTypes(String type) {
        PATH = HttpPath.PATH + HttpPath.ADV + "type=" + type;

        RequestParams params = new RequestParams(PATH);
        System.out.println("广告位二" + PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("广告位二" + result);
                        AdvByType advByType = GsonUtil.gsonIntance().gsonToBean(result, AdvByType.class);

                        linkurl = advByType.getMsg().get(0).getLinkurl();

                        //x.image().bind(iv_adv001, "http://www.ybt9.com/" + advByType.getMsg().get(0).getImg());

                        XUtilsImageUtils.display(iv_adv001, "http://www.ybt9.com/" + advByType.getMsg().get(0).getImg(), 0);
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

    /*轮播图显示图片*/
    public void configImageLoader() {
        // 初始化ImageLoader
        @SuppressWarnings("deprecation")
        DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.mipmap.icon_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.icon_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.icon_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .build(); // 创建配置过得DisplayImageOption对象

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity().getApplicationContext()).defaultDisplayImageOptions(options)
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }

    /*ProgressDialog样式*/
    public void pdStyle() {
        //pd = ProgressDialog.show(getActivity(), "正在刷新数据", "请稍候");

        startProgressDialog();

        //pd.setProgress(100);

        /*pd显示5秒*/
        new Thread(new Runnable() {

            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                int progress = 0;

                while (System.currentTimeMillis() - startTime < 10000) {
                    try {
                        progress += 10;
                        //pd.setProgress(progress);
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        //pd.dismiss();
                        stopProgressDialog();
                    }
                }
                if (System.currentTimeMillis() - startTime == 10000) {
                    if (isLoading) {
                        et_new_address.setText("重新加载");
                        et_new_address.setTextColor(Color.RED);
                        et_new_address.setBackgroundColor(Color.RED);
                    } else {
                        et_new_address.setTextColor(Color.WHITE);
                    }
                }

                //pd.dismiss();
                stopProgressDialog();

            }
        }).start();
    }

    /*开启pd*/
    private void startProgressDialog() {
        if (progressDialog == null) {
            progressDialog = CustomProgress.createDialog(getActivity());
            progressDialog.setMessage("请稍候...");
        }

        progressDialog.show();
    }

    /*关闭pd*/
    private void stopProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    /*判断联网状态*/
    public boolean isNetworkUtils() {
        if (NetworkUtils.isNotWorkAvilable(getActivity())) {
            if (NetworkUtils.getCurrentNetType(getActivity()) != null) {
                System.out.println("联网类型 = " + NetworkUtils.getCurrentNetType(getActivity()));
                return true;
            }
        } else {
            return false;
        }
        return false;
    }

    /**
     * 判断网络连接情况 需要考虑请求超时的问题
     */
    public void isNetworkUtil() {

        if (NetworkUtils.isNotWorkAvilable(getActivity())) {
            if (NetworkUtils.getCurrentNetType(getActivity()) != null) {
                //有网络连接 判断网络的连接类型是否符合标准
                //可以根据规定进行数据的刷新
                configImageLoader();

                getLocation();

                //轮播图
                getWxAdv();

                /*广告位*/
                getAdvByType("weixinmid");
                getAdvByTypes("weixinmid2");

            }
        } else {

            isLoading = false;

            et_new_address.setText("重新加载");
            et_new_address.setTextColor(Color.RED);
            configImageLoader();

            /*首页商品展示*/
            String cateFoodList = userInfo.getMeetings();

            if (cateFoodList.equals("")) {
                //没有缓存数据
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<GoodsIndex.MsgBean.CatefoodslistBean>>() {
                }.getType();
                final List<GoodsIndex.MsgBean.CatefoodslistBean> catefoodslist =
                        gson.fromJson(userInfo.getMeetings(), listType);

                if (catefoodslist.size() == 0) {

                } else if (catefoodslist.size() > 0) {
                    recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), catefoodslist);
                    rv_classify.setAdapter(recyclerViewAdapter);
                }

            }

            /*首页商品分类 */
            String classify = userInfo.getClassify();
            if (classify.equals("")) {
                //没有缓存数据
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Recommed.MsgBean>>() {
                }.getType();
                final List<Recommed.MsgBean> classifyList =
                        gson.fromJson(classify, listType);

                if (classifyList.size() == 0) {

                } else if (classifyList.size() > 0) {
                    System.out.println("aaaaa" + classifyList);
                    recommedAdapter = new RecommedAdapter(getActivity(), classifyList);
                    myGridView.setAdapter(recommedAdapter);
                }
            }

            /*首页轮播图*/
            String adv = userInfo.getADV();
            if (adv.equals("")) {
                //没有缓存数据
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<WxADV.MsgBean>>() {
                }.getType();
                final List<WxADV.MsgBean> wxAdvList =
                        gson.fromJson(adv, listType);

                if (wxAdvList.size() == 0) {

                } else if (wxAdvList.size() > 0) {

                    views.clear();

                    infos = new ArrayList<>();


                    for (int i = 0; i < wxAdvList.size(); i++) {
                        info = new ADInfo();
                        info.setUrl("http://www.ybt9.com/" + wxAdvList.get(i).getImg());
                        info.setContent("图片-->" + i);
                        infos.add(info);
                    }

                    // 将最后一个ImageView添加进来
                    views.add(ViewFactory.getImageView(getActivity(), infos.get(infos.size() - 1).getUrl()));
                    for (int i = 0; i < infos.size(); i++) {
                        views.add(ViewFactory.getImageView(getActivity(), infos.get(i).getUrl()));
                    }
                    // 将第一个ImageView添加进来
                    views.add(ViewFactory.getImageView(getActivity(), infos.get(0).getUrl()));

                    // 设置循环，在调用setData方法前调用
                    cycleViewPager.setCycle(true);

                    // 在加载数据前设置是否循环
                    cycleViewPager.setData(views, infos, mAdCycleViewListener);
                    //设置轮播
                    cycleViewPager.setWheel(true);

                    // 设置轮播时间，默认5000ms
                    cycleViewPager.setTime(3000);
                    //设置圆点指示图标组居中显示，默认靠右
                    cycleViewPager.setIndicatorCenter();


                }
            }

        }
    }

    /**/
    @Override
    public void onScrolledToBottom(boolean isToButom) {
        System.out.println("到达底部");
        if (isToButom) {
            if (isToTops) {
                System.out.println("----------------------" + isToButom);
//            //到达底部
                page++;
                //getLoadingShopGood(latitude, longtitude, page);
                System.out.println("到达底部 加载数据");

                isToTops = false;
            }

        } else {

        }
    }

    @Override
    public void onScrolledToTop(boolean isToTop) {
        System.out.println("到达顶部");
        if (isToTop) {
            //透明
            //rel_jingang.setBackgroundColor(Color.BLACK);
            rel_new_header.getBackground().setAlpha(55);

            iv_new_scaning.setImageResource(R.mipmap.ic_new_scanning);
            iv_new_search.setImageResource(R.mipmap.ic_new_search);

            et_new_address.setTextColor(Color.WHITE);
            et_new_address.setBackgroundResource(R.drawable.editshape);

        } else {
            //白色
            //rel_jingang.setBackgroundColor(Color.WHITE);

            rel_new_header.setBackgroundColor(Color.WHITE);
            iv_new_scaning.setImageResource(R.mipmap.ic_new_scanning2);
            iv_new_search.setImageResource(R.mipmap.ic_new_search2);

            et_new_address.setTextColor(Color.BLACK);
            et_new_address.setBackgroundColor(Color.WHITE);
        }

    }

    @Override
    public void onJingang(boolean isToJingang) {

    }

    /**/
    public class RecommedAdapter extends BaseAdapter {
        private Context mContext;
        private List<Recommed.MsgBean> msgBeen;

        public RecommedAdapter(Context mContext, List<Recommed.MsgBean> msgBeen) {
            this.mContext = mContext;
            this.msgBeen = msgBeen;
        }

        @Override
        public int getCount() {
            return msgBeen.size();
        }

        @Override
        public Object getItem(int i) {
            return msgBeen.get(i);
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
                view = LayoutInflater.from(mContext).inflate(R.layout.item_goods_classily, viewGroup, false);

                vh.iv_img = (ImageView) view.findViewById(R.id.iv_item_gc_img);
                vh.tv_title = (TextView) view.findViewById(R.id.tv_item_gc_title);

                view.setTag(vh);

            } else {
                vh = (ViewHolder) view.getTag();

            }

            String img = msgBeen.get(i).getImg();
            String title = msgBeen.get(i).getName();

            if (img.equals("")) {
                vh.iv_img.setImageResource(R.mipmap.yibeitong001);

            } else {
                //x.image().bind(vh.iv_img, "http://www.ybt9.com/" + img);

                XUtilsImageUtils.display(vh.iv_img, "http://www.ybt9.com/" + img, 0);

            }

            vh.tv_title.setText("" + title);
            return view;
        }

        public class ViewHolder {
            ImageView iv_img;
            TextView tv_title;
        }
    }


    /*商品适配器*/
    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

        private OnItemClickListener mOnItemClickListener;
        private OnItemLongClickListener mOnItemLongClickListener;

        public void setOnItemClickListener(com.bh.yibeitong.Interface.OnItemClickListener mOnItemClickListener) {
            this.mOnItemClickListener = mOnItemClickListener;
        }

        public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
            this.mOnItemLongClickListener = mOnItemLongClickListener;
        }

        private Context mContext;
        private List<GoodsIndex.MsgBean.CatefoodslistBean> msgBeanList;

        public RecyclerViewAdapter(Context mContext, List<GoodsIndex.MsgBean.CatefoodslistBean> msgBeanList) {
            this.mContext = mContext;
            this.msgBeanList = msgBeanList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            MyViewHolder vh = new MyViewHolder(LayoutInflater.from(
                    mContext).inflate(R.layout.item_self_support, parent,
                    false));
            return vh;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            if (mOnItemClickListener != null) {
                //为ItemView设置监听器
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition(); // 1
                        mOnItemClickListener.onItemClick(holder.itemView, position); // 2
                    }
                });
            }
            if (mOnItemLongClickListener != null) {
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int position = holder.getLayoutPosition();
                        mOnItemLongClickListener.onItemLongClick(holder.itemView, position);
                        //返回true 表示消耗了事件 事件不会继续传递
                        return true;
                    }
                });
            }

            String name = msgBeanList.get(position).getName();
            String imgPath = msgBeanList.get(position).getImg();
            int cartnum = msgBeanList.get(position).getCartnum();

            if (imgPath.equals("")) {
                holder.imageView.setImageResource(R.mipmap.yibeitong001);

            } else {
                x.image().bind(holder.imageView, "http://www.ybt9.com/" + imgPath);

            }

            holder.title.setText("" + name);

            holder.num.setText(""+cartnum);

        }

        @Override
        public int getItemCount() {
            return msgBeanList.size();
        }

        class MyViewHolder extends BaseRecyclerViewHolder {

            public ImageView imageView;
            public TextView title, num;

            public MyViewHolder(View view) {
                super(view);
                imageView = (ImageView) view.findViewById(R.id.iv_item_ss);
                title = (TextView) view.findViewById(R.id.tv_item_ss_title);
                num = (TextView) view.findViewById(R.id.tv_shop_num);
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == 2) {
            Bundle bundle = data.getExtras();
            String strResult = bundle.getString("selectAddress");
            latitude = bundle.getString("lat");
            longtitude = bundle.getString("lng");

            System.out.println("strResult" + strResult);

            ceFoodList.clear();

            pdStyle();

            getShopGoods(latitude, longtitude, 1);


        }

    }


}