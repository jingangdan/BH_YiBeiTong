package com.bh.yibeitong.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.bh.yibeitong.Interface.OnItemLongClickListener;
import com.bh.yibeitong.R;
import com.bh.yibeitong.adapter.CatefoodslistAdapter;
import com.bh.yibeitong.base.BaseFragment;
import com.bh.yibeitong.bean.Errors;
import com.bh.yibeitong.bean.GoodsIndex;
import com.bh.yibeitong.bean.Register;
import com.bh.yibeitong.bean.ShopCart;
import com.bh.yibeitong.bean.WxADV;
import com.bh.yibeitong.bean.homepage.AdvByType;
import com.bh.yibeitong.bean.homepage.GetSign;
import com.bh.yibeitong.bean.shopbean.Recommed;
import com.bh.yibeitong.dialog.CustomProgress;
import com.bh.yibeitong.lunbotu.ADInfo;
import com.bh.yibeitong.lunbotu.CycleViewPager;
import com.bh.yibeitong.lunbotu.ViewFactory;
import com.bh.yibeitong.refresh.GridViewAdapter;
import com.bh.yibeitong.refresh.MyGridView;
import com.bh.yibeitong.refresh.PullToRefreshView;
import com.bh.yibeitong.refresh.recyclerview.BaseRecyclerViewHolder;
import com.bh.yibeitong.ui.CateFoodDetailsActivity;
import com.bh.yibeitong.ui.CateInfoActivity;
import com.bh.yibeitong.ui.LoginRegisterActivity;
import com.bh.yibeitong.ui.ShopNewActivity;
import com.bh.yibeitong.ui.homepage.JoinActivity;
import com.bh.yibeitong.ui.homepage.LocationAddressActivity;
import com.bh.yibeitong.ui.homepage.SpecialActivity;
import com.bh.yibeitong.ui.percen.JiFenActivity;
import com.bh.yibeitong.ui.percen.YuEActivity;
import com.bh.yibeitong.ui.search.SearchActivity;
import com.bh.yibeitong.utils.CodeUtils;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.utils.MD5Util;
import com.bh.yibeitong.utils.NetworkUtils;
import com.bh.yibeitong.view.CustomDialog;
import com.bh.yibeitong.view.MarqueeView;
import com.bh.yibeitong.view.MyScrollView;
import com.bh.yibeitong.view.NoDoubleClickListener;
import com.bh.yibeitong.view.UserInfo;
import com.bh.yibeitong.zxing.ZXingCaptureActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jingang on 2016/10/18.
 * 首页
 */
public class FMHomePage extends BaseFragment implements
        PullToRefreshView.OnHeaderRefreshListener,
        PullToRefreshView.OnFooterRefreshListener,
        ActivityCompat.OnRequestPermissionsResultCallback,
        MyScrollView.ISmartScrollChangedListener,
        BDLocationListener,
        OnGetGeoCoderResultListener {
    public static final int READ_EXTERNAL_STORAGE = 0x01;

    private View view;

    //private TextView tv_gointo_shop;//进入店铺
    private RelativeLayout rel_gointo_shop;

    /**
     * 商品列表适配器
     */
    private CatefoodslistAdapter cAdapter;
    private List<GoodsIndex.MsgBean.CatefoodslistBean> ceFoodList = new ArrayList<>();

    /**
     * 页面之间的跳转传值
     */
    private Intent intent;
    public static String shopid = "", startTime, mapphone, address;
    public static String latitude, longtitude;//经纬度

    public static String shopName;

    /**
     * 垂直跑马灯
     */
    private MarqueeView marqueeView;
    private List<String> str_marqueeView = new ArrayList<>();

    // 自定义的GridView的上下拉动刷新
    private PullToRefreshView mPullToRefreshView;

    private MyGridView myGridView;
    //mgv_classifly;
    private GridViewAdapter gridViewAdapter;

    /*页面*/
    private int page = 1;

    /*查看本地存储*/
    UserInfo userInfo;
    private String jingang = "";//是否登录  “空”未登录  0未登录  1登录

    /*界面修改*/
    private RelativeLayout rel_new_header;//背景
    private ImageView iv_new_scaning, iv_new_search;//扫一扫 搜索
    private EditText et_new_address;//定位地址
    private TextView tv_shopname;

    /*积分抢购 抢购专区 商家入驻 跑腿服务*/
    private LinearLayout lin_jifen, lin_qianggou, lin_ruzhu, lin_paotui;

    /*接口地址*/
    private String PATH = "";
    private String uid, pwd, phone;

    private List<ImageView> views = new ArrayList<ImageView>();
    private List<ADInfo> infos;
    private ADInfo info;
    private CycleViewPager cycleViewPager;

    private ImageView iv_adv001, iv_adv002;
    private String linkurl;

    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;

    private CustomProgress progressDialog = null;

    /*定位服务*/
    private LocationClient mLocationClient = null;

    //private BDLocationListener myListener = new MyLocationListener();

    /*判断是否定位成功*/
    public boolean isLocation = false;

    /*无网络或网络不佳时显示*/
    private LinearLayout lin_network, lin_no_network;
    private Button but_refresh, but_setting;//刷新 设置

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        //推送runnable，定期2s执行一次
        @Override
        public void run() {
            Log.i("开始", printCurTime());

            handler.postDelayed(runnable, 2000);

            //mLocationClient.start();

            startLocate();
        }

    };
    private Runnable runRemove = new Runnable() {
        //移除runnable，在6s后移除
        @Override
        public void run() {
            Log.i("移除", printCurTime());
            handler.removeCallbacks(runnable);

            mLocationClient.stop();

        }

    };

    private String printCurTime() {
        //获取当前时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//格式样式
        Date date = new Date(System.currentTimeMillis());//建立当前日期
        //format.format(date)格式化日期时间
        return format.format(date);
    }

    private Handler handlers = new Handler() {
        // 该方法运行在主线程中
        // 接收到handler发送的消息，对UI进行操作
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                //textView.setText("呵呵。。");
                et_new_address.setText("定位失败");

                lin_network.setVisibility(View.GONE);
                lin_no_network.setVisibility(View.VISIBLE);
            }
        }
    };

    /*自定义ScrollView*/
    private MyScrollView myScrollView;

    /**/
    private RecyclerView rv_horizontal;

    /*记录购物车商品id字符串*/
    private List<ShopCart.MsgBean.ListBean> shopMsg = new ArrayList<>();
    private String str_id2 = "";//记录购物车商品数量

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("首页 加载onCreate");
        //startLocate();
        initDatas();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("首页 加载onCreateView");
        view = inflater.inflate(R.layout.fragment_home_page, null);
        //handler.post(runnable);//定期执行

        pdStyle();

        initData();

        /*开始定位*/
        //startLocate();

        if (!(userInfo.getUserInfo().equals(""))) {
            Register register = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), Register.class);
            uid = register.getMsg().getUid();
            phone = register.getMsg().getPhone();
            if (!(userInfo.getPwd().equals(""))) {
                pwd = userInfo.getPwd();
            }

            if (userInfo.getCode().equals("0")) {
                getSignToDay(uid, pwd);
            } else {
                getSignToDay("phone", phone);
            }
        } else {
            System.out.println("未登录");
        }

        return view;
    }

    /*开始dialog*/
    private void startProgressDialog() {
        if (progressDialog == null) {
            progressDialog = CustomProgress.createDialog(getActivity());
            progressDialog.setMessage("请稍候...");
        }

        progressDialog.show();
    }

    /*结束dialog*/
    private void stopProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    /**
     * 组件 初始化
     */
    private void initData() {

        lin_network = (LinearLayout) view.findViewById(R.id.lin_hp_network);
        lin_no_network = (LinearLayout) view.findViewById(R.id.lin_hp_nonetwork);

        but_refresh = (Button) view.findViewById(R.id.but_hp_refresh);
        but_setting = (Button) view.findViewById(R.id.but_hp_setting);
        but_refresh.setOnClickListener(this);
        but_setting.setOnClickListener(this);

        userInfo = new UserInfo(getActivity().getApplication());
        jingang = userInfo.getLogin();

        gridViewAdapter = new GridViewAdapter();

//        tv_gointo_shop = (TextView) view.findViewById(R.id.tv_gointo_shop);
//        tv_gointo_shop.setOnClickListener(this);

        rel_gointo_shop = (RelativeLayout) view.findViewById(R.id.rel_gointo_shop);
        rel_gointo_shop.setOnClickListener(this);

        //垂直跑马灯
        marqueeView = (MarqueeView) view.findViewById(R.id.marqueeView);

        // Android ScrollView嵌套ListView嵌套GridView的上下拉以及加载更多
        mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.main_pull_refresh_view);

        myGridView = (MyGridView) view.findViewById(R.id.myGridView);

        /*界面修改*/
        rel_new_header = (RelativeLayout) view.findViewById(R.id.rel_new_header);
        iv_new_scaning = (ImageView) view.findViewById(R.id.iv_new_scanning);
        iv_new_search = (ImageView) view.findViewById(R.id.iv_new_search);
        et_new_address = (EditText) view.findViewById(R.id.et_new_address);
        tv_shopname = (TextView) view.findViewById(R.id.tv_hp_shopname);

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

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_goods_classify);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        cycleViewPager = (CycleViewPager) getActivity().getFragmentManager().findFragmentById(R.id.fragment_cycle_viewpager_content);

        /*广告位*/
        iv_adv001 = (ImageView) view.findViewById(R.id.iv_adv001);
        iv_adv002 = (ImageView) view.findViewById(R.id.iv_adv002);
        iv_adv001.setOnClickListener(this);
        iv_adv002.setOnClickListener(this);

        /*滑动控件*/
        myScrollView = (MyScrollView) view.findViewById(R.id.sv_home_page);

        /*横向的RecyclerView*/
        rv_horizontal = (RecyclerView) view.findViewById(R.id.rv_classify_horizontal);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_horizontal.setLayoutManager(linearLayoutManager);


        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);
        mPullToRefreshView.setLastUpdated(new Date().toLocaleString());

        str_marqueeView.add("易贝通便利店欢迎您！");

        marqueeView.startWithList(str_marqueeView);

        /*判断网络连接状态*/
        isNetworkUtil();

        configImageLoader();

        MyScrollView.setScanScrollChangedListener(this);

        MyScrollView.doSomeThing();

        /*商品列表点击事件*/
//        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                intent = new Intent(getActivity(), CateFoodDetailsActivity.class);
//                intent.putExtra("id", ceFoodList.get(i).getId());//商品id
//                intent.putExtra("instro", ceFoodList.get(i).getInstro());
//
//                startActivityForResult(intent, CodeUtils.REQUEST_CODE_HOMEPAGE);
//            }
//        });

    }

    /*防止多次点击造成多次事件*/
    public NoDoubleClickListener mListener = new NoDoubleClickListener() {
        @Override
        protected void onNoDoubleClick(int postion, View v) {
            //System.out.println("啊  我被点了");
            intent = new Intent(getActivity(), CateFoodDetailsActivity.class);
            intent.putExtra("id", ceFoodList.get(postion).getId());//商品id
            intent.putExtra("instro", ceFoodList.get(postion).getInstro());

            startActivityForResult(intent, CodeUtils.REQUEST_CODE_HOMEPAGE);
        }

    };

    @Override
    public void onScrolledToBottom(boolean isToButtom) {
        //isToBrttom = ture到达底部
        if (isToButtom) {
            if (isToTops) {
                System.out.println("----------------------" + isToButtom);
//            //到达底部
                if (page < 10) {
                    page++;

                    getLoadingShopGood(latitude, longtitude, page);
                    System.out.println("到达底部 加载数据");

                    isToTops = false;
                } else {
                    toast("没有更多数据");
                }

            }

        } else {

        }

    }

    /*是否到达顶部*/
    public boolean isToTops = false;

    @Override
    public void onScrolledToTop(boolean isToTop) {
        if (isToTop) {
            //透明
            rel_new_header.getBackground().setAlpha(55);

            iv_new_scaning.setImageResource(R.mipmap.ic_new_scanning);
            iv_new_search.setImageResource(R.mipmap.ic_new_search);

            et_new_address.setTextColor(Color.WHITE);
            et_new_address.setBackgroundResource(R.drawable.editshape);

        } else {
            //白色

            rel_new_header.setBackgroundColor(Color.WHITE);
            iv_new_scaning.setImageResource(R.mipmap.ic_new_scanning2);
            iv_new_search.setImageResource(R.mipmap.ic_new_search2);

            et_new_address.setTextColor(Color.BLACK);
            et_new_address.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public void onJingang(boolean isToJingang) {
        if (isToJingang) {
            rv_horizontal.setVisibility(View.VISIBLE);
        } else {
            rv_horizontal.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (mLocationClient != null && myListener != null) {
//            mLocationClient.unRegisterLocationListener(myListener);
//            //mLocationClient.unregisterListener(myListener);
//        }

//        if (mLocationClient.isStarted()) {
//            mLocationClient.stop();
//        }
    }

    private List<WxADV.MsgBean> advList = new ArrayList<>();

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

    /**
     * 配置ImageLoder
     */
    private void configImageLoader() {
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


                        /*轮播图(old)*/

//                        HashMap<String, String> url_maps = new HashMap<String, String>();
//                        for (int i = 0; i < wxADV.getMsg().size(); i++) {
//                            //url_maps.put("易贝通开业了" + i, "http://www.ybt9.com/" + wxADV.getMsg().get(i).getImg());
//                            url_maps.put(wxADV.getMsg().get(i).getTitle(), "http://www.ybt9.com/" + wxADV.getMsg().get(i).getImg());
//                        }
//
//                        TextSliderView textSliderView;
//                        for (String name : url_maps.keySet()) {
//                            textSliderView = new TextSliderView(getActivity());
//                            // initialize a SliderLayout
//                            textSliderView
//                                    .description("")
//                                    .image(url_maps.get(name))
//                                    .setScaleType(BaseSliderView.ScaleType.Fit);
//                            // .setOnSliderClickListener(this);
//
//                            //add your extra information
//                            textSliderView.getBundle()
//                                    .putString("", name);
//
//                            sliderLayout.addSlider(textSliderView);
//                        }
//
//                        //sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
//                        //sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
//                        //sliderLayout.setCustomAnimation(new DescriptionAnimation());
//                        sliderLayout.setDuration(4000);


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

                        x.image().bind(iv_adv002, "http://www.ybt9.com/" + advByType.getMsg().get(0).getImg());


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

                        x.image().bind(iv_adv001, "http://www.ybt9.com/" + advByType.getMsg().get(0).getImg());

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
     * 查询今天的签到状态
     *
     * @param uid
     * @param pwd
     */
    public void getSignToDay(String uid, String pwd) {

        if (userInfo.getCode().equals("0")) {
            PATH = HttpPath.PATH + HttpPath.GETSIGN +
                    "uid=" + uid + "&pwd=" + pwd;
        } else {
            PATH = HttpPath.PATH + HttpPath.GETSIGN +
                    "logintype=" + uid + "&loginphone=" + pwd;
        }

        System.out.println("今日签到状态" + PATH);
        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("今日签到状态" + result);
                        GetSign getSign = GsonUtil.gsonIntance().gsonToBean(result, GetSign.class);
                        System.out.println("" + getSign.isError());
                        if (getSign.isError() == true) {
                            //but_sign.setText("已签到");
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
     * 签到
     *
     * @param uid
     * @param pwd
     */
    public void signToDay(String uid, String pwd) {
        String PATH = null;
        if (userInfo.getCode().equals("0")) {
            PATH = HttpPath.PATH + HttpPath.SIGN +
                    "uid=" + uid + "&pwd=" + pwd;
        } else {
            PATH = HttpPath.PATH + HttpPath.SIGN +
                    "logintype=" + uid + "&loginphone=" + pwd;
        }

        System.out.println("签到" + PATH);
        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("签到" + result);
                        lin_ruzhu.setClickable(true);
                        Errors error = GsonUtil.gsonIntance().gsonToBean(result, Errors.class);
                        if (error.isError() == true) {
                            toast("" + error.getMsg().toString());
                        } else {
                            //but_sign.setText("已签到");
                            toast("签到成功");
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

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        page++;
        mPullToRefreshView.postDelayed(new Runnable() {

            @Override
            public void run() {
                //加载更多数据
                mPullToRefreshView.onFooterRefreshComplete();
                //gridViewData.add(R.mipmap.ic_adai);
                //gridViewAdapter.setData(gridViewData);
                //getShopGoodss(str_latitude, str_longtitude, page);
                //getLoadingShopGood(latitude, longtitude, page);

            }

        }, 1000);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        mPullToRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                //刷新数据
                mPullToRefreshView.onHeaderRefreshComplete("更新于:"
                        + Calendar.getInstance().getTime().toLocaleString());
                mPullToRefreshView.onHeaderRefreshComplete();

                pdStyle();
                exit = false;
                ceFoodList.clear();

                getShopGoods(latitude, longtitude, 1);

                //isNetworkUtil();
            }

        }, 1000);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (hidden) {
            System.out.println("离开FMShopCart");

        } else {
            System.out.println("刷新FMShopCart");

            jingang = userInfo.getLogin();

            /*执行局部刷新 更改购物车数量cartNum*/
            /**
             * 1.获取购物车（得到数量和商品唯一标示符）
             * 2.利用唯一标示符对比首页商品列表找到该商品
             * 3.对比数量
             * 4.相同：不操作  不同：修改（刷新此item）
             * */

            getShopCart(shopid);

        }
    }

    /**
     * 获取购物车 （用于首页和购物车数据共享）
     *
     * @param shopid
     */
    public void getShopCart(String shopid) {
        PATH = HttpPath.PATH + HttpPath.GETCART + "shopid=" + shopid;

        RequestParams params = new RequestParams(PATH);
        System.out.println("购物车" + PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("购物车" + result);
                        str_id2 = "";

                        try {
                            JSONObject response = new JSONObject(result);
                            if (response.get("msg").toString().equals("[]")) {
                                System.out.println("没有数据");

                                for (int i = 0; i < ceFoodList.size(); i++) {
                                    int cartNum = ceFoodList.get(i).getCartnum();
                                    if (cartNum > 0) {
                                        ceFoodList.get(i).setCartnum(0);
                                    }
                                }

                            } else {
                                ShopCart shopCart = GsonUtil.gsonIntance().gsonToBean(result, ShopCart.class);
                                shopMsg = shopCart.getMsg().getList();

                                if (shopMsg.size() > 0) {
                                    //遍历首页商品列表
                                    for (int j = 0; j < shopMsg.size(); j++) {
                                        str_id2 = str_id2 + shopMsg.get(j).getId();
                                    }
                                }

                                /**
                                 * 1.找出首页购物车数量num1 >0的，并记录id1
                                 * 2.将id1于购物车id2作比
                                 * 较（不同：首页购物车数量清零  相同：将num2赋值给num1）
                                 */

                                /*遍历首页商品列表*/
                                for (int i = 0; i < ceFoodList.size(); i++) {

                                    int num1 = ceFoodList.get(i).getCartnum();//首页数量
                                    String id1 = ceFoodList.get(i).getId();//首页

                                    if (num1 > 0) {
                                        if (str_id2.indexOf(ceFoodList.get(i).getId()) == -1) {
                                            //System.out.println("数量大于 0  " + ceFoodList.get(i).getName());
                                            ceFoodList.get(i).setCartnum(0);
                                        }

                                    }

                                    if (shopMsg.size() > 0) {
                                        //遍历首页商品列表
                                        for (int j = 0; j < shopMsg.size(); j++) {
                                            int num2 = shopMsg.get(j).getCount();//购物车数量
                                            String id2 = "" + shopMsg.get(j).getId(); //购物车

                                            if (id1.equals(id2)) {
                                                if (num1 == num2) {
                                                    //相同 不动
                                                } else {
                                                    //不同且大于0 修改
                                                    ceFoodList.get(i).setCartnum(num2);
                                                }
                                            }
                                        }
                                    } else {
                                        int cartNum = ceFoodList.get(i).getCartnum();
                                        if (cartNum > 0) {
                                            ceFoodList.get(i).setCartnum(0);
                                        }
                                    }

                                }


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        /**/
                        gridViewAdapter.notifyDataSetChanged();
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
                }

        );

    }

    /**
     * 获取首页数据
     *
     * @param latitude
     * @param longtitude 118.351852   35.111124
     */
    public void getShopGoods(String latitude, String longtitude, final int pages) {
//        String Path = HttpPath.PATH + HttpPath.GOODSINDEX +
//                "lat=" + latitude + "&lng=" + longtitude + "" + "&page=" + pages;

        /*ctrl=app&datatype=json&timestamp=1503020032&action=goodsindex&lat=35.111117&lng=118.352&page=1&sign*/

        PATH = HttpPath.PATH_HEAD + HttpPath.PATH_DATA + (System.currentTimeMillis() / 1000) + "&" + HttpPath.GOODSINDEX +
                "lat=" + latitude + "&lng=" + longtitude + "" + "&page=" + pages + "&sign=" +
                MD5Util.getMD5String(HttpPath.PATH_DATA + (System.currentTimeMillis() / 1000) + "&" + HttpPath.GOODSINDEX +
                        "lat=" + latitude + "&lng=" + longtitude + "" + "&page=" + pages
                        + "&" + HttpPath.PATH_BAIHAI);

        System.out.println("" + PATH);

        RequestParams params = new RequestParams(PATH);
        x.http().post(params,
                new Callback.CommonCallback<String>() {
                    public void onSuccess(String result) {
                        //pd.dismiss();
                        page = 1;

                        isLocation = true;
                        isToTops = true;

                        stopProgressDialog();

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
                        /*送快递时间段*/
                        userInfo.savePostData(new Gson().toJson(goodsIndex.getMsg().getShopdet().getPostdate()));

                        //et_new_address.setText("" + shopName);
                        tv_shopname.setText("当前店铺：" + shopName);

                        if (goodsIndex.getMsg().getCatefoodslist().toString().equals("[]")) {
                            toast("没有更多数据");

                        } else {
                            //获取信息列表
                            ceFoodList.addAll(goodsIndex.getMsg().getCatefoodslist());

                            gridViewAdapter.setCatefoodslistBeanList(getActivity(), ceFoodList, mListener);

                            myGridView.setAdapter(gridViewAdapter);

                        }

                        lin_network.setVisibility(View.VISIBLE);
                        lin_no_network.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        System.out.println("首页onError" + ex.toString());
                        //pd.dismiss();
                        stopProgressDialog();
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
     * 获取首页数据(加载用)
     *
     * @param latitude
     * @param longtitude 118.351852   35.111124
     */
    public void getLoadingShopGood(String latitude, String longtitude, final int pages) {
//        String Path = HttpPath.PATH + HttpPath.GOODSINDEX +
//                "lat=" + latitude + "&lng=" + longtitude + "" + "&page=" + pages;

        PATH = HttpPath.PATH_HEAD + HttpPath.PATH_DATA + (System.currentTimeMillis() / 1000) + "&" + HttpPath.GOODSINDEX +
                "lat=" + latitude + "&lng=" + longtitude + "" + "&page=" + pages + "&sign=" +
                MD5Util.getMD5String(HttpPath.PATH_DATA + (System.currentTimeMillis() / 1000) + "&" + HttpPath.GOODSINDEX +
                        "lat=" + latitude + "&lng=" + longtitude + "" + "&page=" + pages
                        + "&" + HttpPath.PATH_BAIHAI);

//        PATH = HttpPath.PATH_HEAD + HttpPath.PATH_DATA_MD5 + HttpPath.GOODSINDEX +
//                "lat=" + latitude + "&lng=" + longtitude + "" + "&page=" + pages + "&sign=" +
//                MD5Util.getMD5String(HttpPath.PATH_DATA_MD5 + HttpPath.GOODSINDEX +
//                        "lat=" + latitude + "&lng=" + longtitude + "" + "&page=" + pages
//                        + "&" + HttpPath.PATH_BAIHAI);

        System.out.println("" + PATH);

        RequestParams params = new RequestParams(PATH);
        x.http().post(params,
                new Callback.CommonCallback<String>() {
                    public void onSuccess(String result) {
                        isToTops = true;
                        System.out.println("加载数据" + result);
                        //MD5加密数据
                        String results = MD5Util.getUnicode(result);

                        GoodsIndex goodsIndex = GsonUtil.gsonIntance().gsonToBean(results, GoodsIndex.class);

                        if (goodsIndex.getMsg().getCatefoodslist().toString().equals("[]")) {
                            toast("没有更多数据");

                        } else {
                            //ceFood = goodsIndex.getMsg().getCatefoodslist();
                            //获取信息列表

                            ceFoodList.addAll(goodsIndex.getMsg().getCatefoodslist());
                            gridViewAdapter.notifyDataSetChanged();


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
     * 显示请求字符串
     */
    public void logMsg(String string) {

        ceFoodList.clear();
        page = 1;

        getShopGoods(latitude, longtitude, page);

        System.out.println("经纬度" + longtitude + "   " + latitude);

        //轮播图
        getWxAdv();

        /*广告位*/
        getAdvByType("weixinmid");
        getAdvByTypes("weixinmid2");

    }

    private GeoCoder geoCoder;

    private LatLng locationLatLng;

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        //locationLatLng = new LatLng(bdLocation.getAltitude(), bdLocation.getLongitude());

        locationLatLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());

        latitude = String.valueOf(locationLatLng.latitude);

        longtitude = String.valueOf(locationLatLng.longitude);

        // 创建GeoCoder实例对象
        geoCoder = GeoCoder.newInstance();
        // 发起反地理编码请求(经纬度->地址信息)
        ReverseGeoCodeOption reverseGeoCodeOption = new ReverseGeoCodeOption();
        // 设置反地理编码位置坐标
        reverseGeoCodeOption.location(new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude()));
        geoCoder.reverseGeoCode(reverseGeoCodeOption);

        // 设置查询结果监听者
        geoCoder.setOnGetGeoCodeResultListener(this);

        mLocationClient.stop();//停止定位
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        final List<PoiInfo> poiInfos = reverseGeoCodeResult.getPoiList();
        stopProgressDialog();
        mLocationClient.stop();//停止定位
        handler.postDelayed(runRemove, 0);//过6秒后执行
        isLocation = true;

        if (poiInfos != null && !"".equals(poiInfos)) {
            if (poiInfos.size() > 0) {
                System.out.println("首页 定位:" + poiInfos.get(0).address);
                et_new_address.setText("送至：" + poiInfos.get(0).name);

//                for (int i = 0; i < poiInfos.size(); i++) {
//                    System.out.println("555 = " + poiInfos.get(i).name);
//                }

                logMsg("");

            } else {
            }

        } else {
            isLocation = false;
        }

        //
    }

    /**
     * gridView点击事件
     */
    public class OnItemClickListener implements AdapterView.OnItemClickListener {
        String instro;

        public OnItemClickListener(String string) {
            this.instro = string;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            intent = new Intent(getActivity(), CateFoodDetailsActivity.class);
            intent.putExtra("instro", instro);
        }
    }

    /**
     * 开启location
     */
    public void getLocation() {
        super.onStart();
//        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
//                && ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
//
//            pdStyle();
//
//            super.onStart();
//
//            initLocation();
//
//            mLocationClient = new LocationClient(getActivity().getApplicationContext());
//            //声明LocationClient类
//            mLocationClient.registerLocationListener( myListener );
//            //注册监听函数
//
//            mLocationClient.start();
//
//        } else {
//            ActivityCompat.requestPermissions(getActivity(), new String[]{
//                    Manifest.permission.ACCESS_COARSE_LOCATION,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//            }, READ_EXTERNAL_STORAGE);
//        }
    }

    /*地图检索*/
    private void initDatas() {
        mLocationClient = new LocationClient(getActivity());

        //mLocationService = ((CatchExcep) getApplication()).locationService;
        mLocationClient.registerLocationListener(this);

        // mLocClient = new LocationClient(this);
        // 注册定位监听
        //mLocClient.registerLocationListener(this);

        // 定位选项
        LocationClientOption option = new LocationClientOption();

        /*
        coorType - 取值有3个：
        返回国测局经纬度坐标系：gcj02
         返回百度墨卡托坐标系 ：bd09
         返回百度经纬度坐标系：bd09ll
        */
        option.setCoorType("bd09ll");
        // 设置是否需要地址信息，默认为无地址
        option.setIsNeedAddress(true);
        // 设置是否需要返回位置语义化信息，可以在BDLocation.getLocationDescribe()中得到数据，ex:"在天安门附近"，
        // 可以用作地址信息的补充
        option.setIsNeedLocationDescribe(true);
        // 设置是否需要返回位置POI信息，可以在BDLocation.getPoiList()中得到数据
        option.setIsNeedLocationPoiList(true);

       /* 设置定位模式 Battery_Saving 低功耗模式 Device_Sensors 仅设备(Gps) 模式 Hight_Accuracy
        高精度模式*/

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        // 设置是否打开gps进行定位
        option.setOpenGps(true);
        // 设置扫描间隔，单位是毫秒 当<1000(1s)时，定时定位无效
        option.setScanSpan(1000);
        // 设置 LocationClientOption
        //mLocClient.setLocOption(option);

        mLocationClient.setLocOption(option);
        //mLocClient.setLocOption(mLocationService.getDefaultLocationClientOption());
        // 开始定位
        //mLocClient.start();
        mLocationClient.start();

    }

    /*设置location*/
    private void startLocate() {

        mLocationClient = new LocationClient(getActivity());
        // 注册定位监听
        mLocationClient.registerLocationListener(this);

        // 定位选项
        LocationClientOption option = new LocationClientOption();

        /*
        coorType - 取值有3个：
        返回国测局经纬度坐标系：gcj02
         返回百度墨卡托坐标系 ：bd09
         返回百度经纬度坐标系：bd09ll
        */
        option.setCoorType("bd09ll");
        // 设置是否需要地址信息，默认为无地址
        option.setIsNeedAddress(true);
        // 设置是否需要返回位置语义化信息，可以在BDLocation.getLocationDescribe()中得到数据，ex:"在天安门附近"，
        // 可以用作地址信息的补充
        option.setIsNeedLocationDescribe(true);
        // 设置是否需要返回位置POI信息，可以在BDLocation.getPoiList()中得到数据
        option.setIsNeedLocationPoiList(true);

       /* 设置定位模式 Battery_Saving 低功耗模式 Device_Sensors 仅设备(Gps) 模式 Hight_Accuracy
        高精度模式*/

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        // 设置是否打开gps进行定位
        option.setOpenGps(true);
        // 设置扫描间隔，单位是毫秒 当<1000(1s)时，定时定位无效
        option.setScanSpan(0);
        // 设置 LocationClientOption
        mLocationClient.setLocOption(option);
        System.out.println("location");

        // 开始定位
        mLocationClient.start();
        System.out.println("Location start");

//        mLocationClient = new LocationClient(getActivity());
//
//        //声明LocationClient类
//        LocationClientOption option = new LocationClientOption();
//        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
//
//        option.setCoorType("bd09ll");
//        //可选，默认gcj02，设置返回的定位结果坐标系
//
//        option.setScanSpan(0);
//        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
//
//        option.setIsNeedAddress(true);
//        //可选，设置是否需要地址信息，默认不需要
//
//        option.setOpenGps(true);
//        //可选，默认false,设置是否使用gps
//
//        option.setLocationNotify(true);
//        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
//
//        option.setIsNeedLocationDescribe(true);
//        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
//
//        option.setIsNeedLocationPoiList(true);
//        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
//
//        option.setIgnoreKillProcess(false);
//        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
//
//        option.SetIgnoreCacheException(false);
//        //可选，默认false，设置是否收集CRASH信息，默认收集
//
////        option.setEnableSimulateGps(false);
////        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
//
//        //mLocationClient.setLocationOption(mLocationClient.getDefaultLocationClientOption());
//        mLocationClient.setLocOption(option);
//        //mLocationClient.registerListener(myListener);
//
//        mLocationClient.registerLocationListener(myListener);
//        //注册监听函数
//
//        mLocationClient.start();
//        System.out.println("location start");

    }

    /*location输出结果*/
//    private BDLocationListener myListener = new BDLocationListener() {
//
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//
//            handler.postDelayed(runRemove, 0);//过6秒后执行
//
//            //获取定位结果
//            StringBuffer sb = new StringBuffer(256);
//
//            sb.append("time : ");
//            sb.append(location.getTime());    //获取定位时间
//
//            sb.append("\nerror code : ");
//            sb.append(location.getLocType());    //获取类型类型
//
//            sb.append("\nlatitude : ");
//            sb.append(location.getLatitude());    //获取纬度信息
//
//            sb.append("\nlontitude : ");
//            sb.append(location.getLongitude());    //获取经度信息
//
//            latitude = Double.toString(location.getLatitude());
//            longtitude = Double.toString(location.getLongitude());
//
//            sb.append("\nradius : ");
//            sb.append(location.getRadius());    //获取定位精准度
//
//            if (location.getLocType() == BDLocation.TypeGpsLocation) {
//
//                // GPS定位结果
//                sb.append("\nspeed : ");
//                sb.append(location.getSpeed());    // 单位：公里每小时
//
//                sb.append("\nsatellite : ");
//                sb.append(location.getSatelliteNumber());    //获取卫星数
//
//                sb.append("\nheight : ");
//                sb.append(location.getAltitude());    //获取海拔高度信息，单位米
//
//                sb.append("\ndirection : ");
//                sb.append(location.getDirection());    //获取方向信息，单位度
//
//                sb.append("\naddr : ");
//                sb.append(location.getAddrStr());    //获取地址信息
//
//                sb.append("\ndescribe : ");
//                sb.append("gps定位成功");
//
//            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
//
//                System.out.println("" + location.getStreet() + "   " + location.getCity());
//
//                // 网络定位结果
//                sb.append("\naddr : ");
//                sb.append(location.getAddrStr());    //获取地址信息
//
//                sb.append("\noperationers : ");
//                sb.append(location.getOperators());    //获取运营商信息
//
//                sb.append("\ndescribe : ");
//                sb.append("网络定位成功");
//
//            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {
//
//                // 离线定位结果
//                sb.append("\ndescribe : ");
//                sb.append("离线定位成功，离线定位结果也是有效的");
//
//            } else if (location.getLocType() == BDLocation.TypeServerError) {
//
//                sb.append("\ndescribe : ");
//                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
//
//            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
//
//                sb.append("\ndescribe : ");
//                sb.append("网络不同导致定位失败，请检查网络是否通畅");
//
//                System.out.println("");
//
//            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
//
//                sb.append("\ndescribe : ");
//                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
//
//            }
//
//            sb.append("\nlocationdescribe : ");
//            sb.append(location.getLocationDescribe());    //位置语义化信息
//
//            List<Poi> list = location.getPoiList();    // POI数据
//            if (list != null) {
//                sb.append("\npoilist size = : ");
//                sb.append(list.size());
//
//                System.out.println("1111111 = " + list.get(0).getName());
//
//                et_new_address.setText("送至：" + list.get(0).getName());
//                for (Poi p : list) {
//                    sb.append("\npoi= : ");
//                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
//                }
//            }
//
//            mLocationClient.stop();
//
//
//            Log.i("描述：", sb.toString());
//
//            logMsg("");
//        }
//    };

    /*ProgressDialog样式*/
    public void pdStyle() {

        startProgressDialog();

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
                        System.out.println("aaaaaaaaaaaa");
                        stopProgressDialog();
                    }

                }

                System.out.println("啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊" + isLocation);

                if (isLocation) {
                    stopProgressDialog();
                    handler.postDelayed(runRemove, 0);
                } else {
                    handler.postDelayed(runRemove, 0);//过6秒后执行
                    stopProgressDialog();

                    handlers.sendEmptyMessage(0x123);//发送消息

                    //弹出框提示
                    //dialogNetWork();
                    //dialog();
                }


            }
        }).start();
    }

    public boolean exit = false;

    private List<Recommed.MsgBean> msgBeanCate = new ArrayList<>();

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
                        Recommed recommed = GsonUtil.gsonIntance().gsonToBean(result, Recommed.class);
                        msgBeanCate = recommed.getMsg();

                        userInfo.saveClassify(new Gson().toJson(recommed.getMsg()));

                        homeAdapter = new HomeAdapter(getActivity(), msgBeanCate);

                        GridLayoutManager llmv;

                        llmv = new GridLayoutManager(getActivity(), 4, GridLayoutManager.VERTICAL, false);
                        //设置为一个4列的纵向网格布局

                        recyclerView.setLayoutManager(llmv);
                        recyclerView.setAdapter(homeAdapter);

                        rv_horizontal.setAdapter(homeAdapter);

                        homeAdapter.setOnItemClickListener(new com.bh.yibeitong.Interface.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int i) {
                                String type = msgBeanCate.get(i).getType();
                                String cateid = msgBeanCate.get(i).getOrderid();
                                String name = msgBeanCate.get(i).getName();
                                String param = msgBeanCate.get(i).getParam();
                                String url = (String) msgBeanCate.get(i).getUrl();
                                if (type.equals("cate")) {
                                    //toast("商品分类" + msgBeanList.get(i).getName());
                                    if (isNetworkUtils()) {
                                        intent = new Intent(getActivity(), CateInfoActivity.class);
                                        intent.putExtra("shopid", shopid);
                                        intent.putExtra("cateid", cateid);
                                        intent.putExtra("param", param);
                                        intent.putExtra("name", name);
                                        startActivity(intent);
                                    } else {
                                        toast("无网络连接");
                                    }


                                } else if (type.equals("jiameng")) {
                                    //toast("jiameng" + msgBeanList.get(i).getName());
                                    if (isNetworkUtils()) {
                                        intent = new Intent(getActivity(), JoinActivity.class);
                                        intent.putExtra("title", "我要加盟");
                                        intent.putExtra("url", url);
                                        startActivity(intent);
                                    } else {
                                        toast("无网络连接");
                                    }


                                } else if (type.equals("tesefuwu")) {
                                    //toast("tesefuwu" + msgBeanList.get(i).getName());
                                    if (isNetworkUtils()) {
                                        intent = new Intent(getActivity(), SpecialActivity.class);
                                        intent.putExtra("shopid", shopid);
                                        startActivity(intent);
                                    } else {
                                        toast("无网络连接");
                                    }


                                } else if (type.equals("fenlei")) {
                                    //toast("fenlei" + msgBeanList.get(i).getName());

                                    if (isNetworkUtils()) {
                                        intent = new Intent(getActivity(), ShopNewActivity.class);
                                        intent.putExtra("shopid", shopid);
                                        intent.putExtra("shopname", shopName);
                                        intent.putExtra("startTime", startTime);
                                        intent.putExtra("mapphone", mapphone);
                                        intent.putExtra("address", address);

                                        intent.putExtra("lat", latitude);
                                        intent.putExtra("lng", longtitude);

                                        startActivity(intent);
                                    } else {
                                        toast("无网络连接");
                                    }


                                } else {
                                    toast("啥啥啥");
                                }
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

    /*新商品分类按钮（八个）*/
    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        private com.bh.yibeitong.Interface.OnItemClickListener mOnItemClickListener;
        private OnItemLongClickListener mOnItemLongClickListener;

        public void setOnItemClickListener(com.bh.yibeitong.Interface.OnItemClickListener mOnItemClickListener) {
            this.mOnItemClickListener = mOnItemClickListener;
        }

        public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
            this.mOnItemLongClickListener = mOnItemLongClickListener;
        }

        private Context mContext;
        private List<Recommed.MsgBean> msgBeanList;

        public HomeAdapter(Context mContext, List<Recommed.MsgBean> msgBeanList) {
            this.mContext = mContext;
            this.msgBeanList = msgBeanList;
        }

        @Override
        public HomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            HomeAdapter.MyViewHolder vh = new HomeAdapter.MyViewHolder(LayoutInflater.from(
                    mContext).inflate(R.layout.item_goods_classily, parent,
                    false));
            return vh;
        }

        @Override
        public void onBindViewHolder(final HomeAdapter.MyViewHolder holder, int position) {
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

            if (imgPath.equals("")) {
                holder.imageView.setImageResource(R.mipmap.yibeitong001);

            } else {
                x.image().bind(holder.imageView, "http://www.ybt9.com/" + imgPath);

            }

            holder.title.setText("" + name);

        }

        @Override
        public int getItemCount() {
            return msgBeanList.size();
        }

        class MyViewHolder extends BaseRecyclerViewHolder {

            public ImageView imageView;
            public TextView title;

            public MyViewHolder(View view) {
                super(view);
                //img = (ImageView) view.findViewById(R.id.iv_item_tese_img);
                imageView = (ImageView) view.findViewById(R.id.iv_item_gc_img);
                title = (TextView) view.findViewById(R.id.tv_item_gc_title);
            }
        }
    }

    @Override
    protected void lazyLoad() {
        //Fragment懒加载
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rel_gointo_shop:
                //进入店铺
                if (isNetworkUtils()) {
                    intent = new Intent(getActivity(), ShopNewActivity.class);
                    intent.putExtra("shopid", shopid);
                    intent.putExtra("shopname", shopName);
                    intent.putExtra("startTime", startTime);
                    intent.putExtra("mapphone", mapphone);
                    intent.putExtra("address", address);

                    intent.putExtra("lat", latitude);
                    intent.putExtra("lng", longtitude);

                    startActivityForResult(intent, CodeUtils.REQUEST_CODE_HOMEPAGE);
                } else {
                    toast("无网络连接");
                }
                break;

            case R.id.iv_new_scanning:
                //新版扫一扫

                if (isNetworkUtils()) {
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(
                                getActivity(), new String[]{
                                        Manifest.permission.CAMERA
                                }, 9
                        );

                    } else {

                        intent = new Intent(getActivity(), ZXingCaptureActivity.class);
                        intent.putExtra("shopid", shopid);
                        intent.putExtra("coder", "2");
                        startActivityForResult(intent, 2);
                        //startActivity(intent);

                    }
                } else {
                    toast("无网络连接");
                }


                break;
            case R.id.iv_new_search:
                //新版搜索
                if (isNetworkUtils()) {
                    intent = new Intent(getActivity(), SearchActivity.class);
                    intent.putExtra("lat", latitude);
                    intent.putExtra("lng", longtitude);
                    intent.putExtra("shopid", shopid);
                    startActivity(intent);
                } else {
                    toast("无网络连接");
                }

                break;

            case R.id.et_new_address:
                //新版选择地址

                handler.postDelayed(runRemove, 0);//过6秒后执行
                mLocationClient.stop();

                if (isNetworkUtils()) {
                    intent = new Intent(getActivity(), LocationAddressActivity.class);
                    startActivityForResult(intent, CodeUtils.REQUEST_CODE_HOMEPAGE);

                } else {
                    toast("无网络连接");
                }


//                if (isNetworkUtils()) {
//                    intent = new Intent(getActivity(), LocationActivity.class);
//                    intent.putExtra("lat", latitude);
//                    intent.putExtra("lng", longtitude);
//                    startActivityForResult(intent, 10);
//
//                } else {
//                    toast("无网络连接");
//                }

                break;

            case R.id.lin_jinfen:
                //新版积分专区
                if (isNetworkUtils()) {
                    if (isLogin()) {
                        intent = new Intent(getActivity(), JiFenActivity.class);
                        intent.putExtra("jifen", "");
                        startActivityForResult(intent, 10);

                    } else {
                        dialog();
                    }
                } else {
                    toast("无网络连接");
                }


                break;

            case R.id.lin_qianggou:
                //新版 抢购专区

                if (isNetworkUtils()) {
                    String qianggou = "https://www.ybt9.com/index.php?ctrl=app&action=specialpage&&id=4&" +
                            "lat=" + latitude + "&lng=" + longtitude + "&mapname=" + address;
                    intent = new Intent(getActivity(), JoinActivity.class);
                    intent.putExtra("title", "抢购专区");
                    intent.putExtra("url", qianggou);
                    startActivity(intent);
                } else {
                    toast("无网络连接");
                }


                break;

            case R.id.lin_ruzhu:
                //改为签到

                if (isNetworkUtils()) {
                    if (isLogin()) {
                        Register register = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), Register.class);
                        uid = register.getMsg().getUid();
                        if (!(userInfo.getPwd().equals(""))) {
                            pwd = userInfo.getPwd();
                        }

                        if (userInfo.getCode().equals("0")) {
                            signToDay(uid, pwd);
                        } else {
                            System.out.println("1111111");
                            signToDay("phone", phone);
                        }
                    } else {
                        dialog();
                    }
                } else {
                    toast("无网络连接");
                }

                break;

            case R.id.lin_paotui:
                //新版 跑腿服务
                //startActivity(new Intent(getActivity(), WillOpenActivity.class));
                //改为充值

                if (isNetworkUtils()) {
                    if (isLogin()) {
                        intent = new Intent(getActivity(), YuEActivity.class);
                        intent.putExtra("yue", "100.00");
                        startActivity(intent);
                    } else {
                        dialog();
                    }
                } else {
                    toast("无网络连接");
                }


                break;

            case R.id.iv_adv001:

                if (isNetworkUtils()) {
                    intent = new Intent(getActivity(), JoinActivity.class);
                    intent.putExtra("title", "我要加盟");
                    intent.putExtra("url", "https://www.ybt9.com" + linkurl);
                    startActivity(intent);
                } else {
                    toast("无网络连接");
                }

                break;

            case R.id.iv_adv002:
                //toast("iv_adv002");
                break;

            case R.id.but_hp_refresh:
                //无网络或网络状况不佳 刷新按钮
                pdStyle();
                isLocation = false;

                ceFoodList.clear();
                //handler.post(runnable);//定期执行
                //startLocate();
                initDatas();
                break;

            case R.id.but_hp_setting:
                //无网络或网络状况不佳 设置按钮 跳转到wifi设置
                Intent wifiSettingsIntent = new Intent("android.settings.WIFI_SETTINGS");
                startActivity(wifiSettingsIntent);
                break;

            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 10 && resultCode == 2) {
//            Bundle bundle = data.getExtras();
//            String strResult = bundle.getString("selectAddress");
//            latitude = bundle.getString("lat");
//            longtitude = bundle.getString("lng");
//            ceFoodList.clear();
//            myScrollView.scrollTo(0, 0);
//            getShopGoods(latitude, longtitude, 1);
//
//            rv_horizontal.setVisibility(View.GONE);
//
//        } else
        if (requestCode == CodeUtils.REQUEST_CODE_HOMEPAGE) {
            if (resultCode == CodeUtils.REQUEST_CODE_CATEFOOD || resultCode == CodeUtils.REQUEST_CODE_NEWSHOP) {

                jingang = userInfo.getLogin();
                getShopCart(shopid);
            } else if (resultCode == CodeUtils.REQUEST_CODE_LOGIN) {
            /*登录之后返回登录唯一标识*/
                Bundle bundle = data.getExtras();
                jingang = bundle.getString("jingang");
            } else if (resultCode == CodeUtils.REQUEST_CODE_LOCATION_ADDRESS) {
                Bundle bundle = data.getExtras();
                latitude = bundle.getString("lat");
                longtitude = bundle.getString("lng");
                address = bundle.getString("address");

                if (!address.equals("")) {
                    et_new_address.setText("送至：" + address);

                    ceFoodList.clear();
                    myScrollView.scrollTo(0, 0);
                    getShopGoods(latitude, longtitude, 1);

                    rv_horizontal.setVisibility(View.GONE);
                }


            }
        }

    }

    /*判断登录状态*/
    public boolean isLogin() {
        if (jingang.equals("")) {
            //没有登录
            return false;
        } else if (jingang.equals("0")) {
            //没有登录
            return false;
        } else if (jingang.equals("1")) {
            //已登录
            return true;
        }

        return false;
    }

    /**
     * 提示框（未登录）
     */
    protected void dialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
        builder.setMessage("还未登录，确定要登录吗？");
        builder.setPositiveButton("登录/注册", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //设置你的操作事项
                startActivityForResult(new Intent(getActivity(), LoginRegisterActivity.class), CodeUtils.REQUEST_CODE_HOMEPAGE);
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
     * 判断网络连接情况 灵需要考虑请求超时的问题
     */
    public void isNetworkUtil() {

        if (NetworkUtils.isNotWorkAvilable(getActivity())) {
            if (NetworkUtils.getCurrentNetType(getActivity()) != null) {
                //有网络连接 判断网络的连接类型是否符合标准
                //可以根据规定进行数据的刷新
                configImageLoader();

                //getLocation();

            }
        } else {
            stopProgressDialog();

            lin_network.setVisibility(View.GONE);
            lin_no_network.setVisibility(View.VISIBLE);
            et_new_address.setText("定位失败");

            configImageLoader();

            //无网络连接  显示缓存数 据

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
                    cAdapter = new CatefoodslistAdapter(getActivity(), catefoodslist);

                    System.out.println("catefoodslist = " + catefoodslist);

                    myGridView.setAdapter(cAdapter);
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

//                    cateInfoAdapter = new CateInfoAdapter(getActivity(), classifyList);
//                    mgv_classifly.setAdapter(cateInfoAdapter);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == 9) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(getActivity(), "开启摄像头权限", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), ZXingCaptureActivity.class));

                System.out.println("开启");

            } else {
                // Permission Denied
                System.out.println("未开启");
                Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        } else if (READ_EXTERNAL_STORAGE == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //getLocation();

            } else {
                // Permission Denied
                System.out.println("未开启位置");
                Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 针对6.0动态请求权限问题
     * 判断是否允许此权限
     *
     * @param permissions
     * @return
     */
    protected boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(getActivity(), permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 动态请求权限
     *
     * @param code
     * @param permissions
     */
    protected void requestPermission(int code, String... permissions) {
        ActivityCompat.requestPermissions(getActivity(), permissions, code);
    }

}