package com.bh.yibeitong.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.bh.yibeitong.LocationService;
import com.bh.yibeitong.R;
import com.bh.yibeitong.actitvity.MainActivity;
import com.bh.yibeitong.adapter.CatefoodslistAdapter;
import com.bh.yibeitong.base.BaseFragment;
import com.bh.yibeitong.bean.Error;
import com.bh.yibeitong.bean.GoodsIndex;
import com.bh.yibeitong.bean.Register;
import com.bh.yibeitong.bean.WxADV;
import com.bh.yibeitong.bean.homepage.AdvByType;
import com.bh.yibeitong.bean.homepage.GetSign;
import com.bh.yibeitong.bean.shopbean.Recommed;
import com.bh.yibeitong.lunbotu.ADInfo;
import com.bh.yibeitong.lunbotu.CycleViewPager;
import com.bh.yibeitong.lunbotu.ViewFactory;
import com.bh.yibeitong.refresh.GridViewAdapter;
import com.bh.yibeitong.refresh.MyGridView;
import com.bh.yibeitong.refresh.PullToRefreshView;
import com.bh.yibeitong.ui.CateFoodDetailsActivity;
import com.bh.yibeitong.ui.CateInfoActivity;
import com.bh.yibeitong.ui.LocationActivity;
import com.bh.yibeitong.ui.ShopNewActivity;
import com.bh.yibeitong.ui.WillOpenActivity;
import com.bh.yibeitong.ui.homepage.JoinActivity;
import com.bh.yibeitong.ui.homepage.SpecialActivity;
import com.bh.yibeitong.ui.percen.YuEActivity;
import com.bh.yibeitong.ui.search.SearchActivity;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.utils.MD5Util;
import com.bh.yibeitong.utils.NetworkUtils;
import com.bh.yibeitong.view.MarqueeView;
import com.bh.yibeitong.view.MyScrollView;
import com.bh.yibeitong.view.UserInfo;
import com.bh.yibeitong.zxing.ZXingCaptureActivity;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.xutils.BuildConfig;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jingang on 2016/10/18.
 * 首页
 */
public class FMHomePage extends BaseFragment implements PullToRefreshView.OnHeaderRefreshListener,
        PullToRefreshView.OnFooterRefreshListener, ActivityCompat.OnRequestPermissionsResultCallback,
        BaseSliderView.OnSliderClickListener,
        MyScrollView.ISmartScrollChangedListener {
    //private ImageView iv_scanning;//扫一哈子

    private View view;

    /**
     * 自营专区  直营便利店 二手交易 小区信息
     */
    //private LinearLayout lin_self_support, lin_directly_mark, lin_second_hand, lin_village_manage;

    /**
     * 预约服务 美食外卖 收发快递 更多
     */
    //private LinearLayout lin_appointment, lin_food_out, lin_express, lin_more;

    //积分专区 抢购专区 商家入驻 跑腿服务
    //private ImageView iv_integral, iv_scare_buying, iv_business, iv_run;

    /**
     * 地址
     */
    //public EditText et_address;
    //private TextView tv_shopname;

    private TextView tv_gointo_shop;//进入店铺

    /**
     * 商品列表适配器
     */
    private CatefoodslistAdapter cAdapter;
    private List<GoodsIndex.MsgBean.CatefoodslistBean> ceFoodList = new ArrayList<>();
    private List<GoodsIndex.MsgBean.CatefoodslistBean> ceFood = new ArrayList<>();

    /**
     * 页面之间的跳转传值
     */
    private Intent intent;
    private String shopid, startTime, mapphone, address;
    private String latitude, longtitude;//经纬度

    public static String shopName;

    /**
     * 垂直跑马灯
     */
    private MarqueeView marqueeView;
    private List<String> str_marqueeView = new ArrayList<>();

    // 自定义的GridView的上下拉动刷新
    private PullToRefreshView mPullToRefreshView;

    private MyGridView myGridView, mgv_classifly;
    private GridViewAdapter gridViewAdapter;

    /*页面*/
    private int page = 1;

    /*查看本地存储*/
    UserInfo userInfo;
    //private String jingang;//还得召唤我的大名！！！

    /*轮播图*/
    //private SliderLayout sliderLayout;

    private ProgressDialog pd;

    /*搜索店铺、商品*/
    //private EditText et_search;

    //没有网络显示
    private LinearLayout lin_no_network;
    private Button but_load_again;//重新加载

    private LinearLayout lin_yes_network;

    //顶部
    //private RelativeLayout rel_jingang;

    /*界面修改*/
    private RelativeLayout rel_new_header;//背景
    private ImageView iv_new_scaning, iv_new_search;//扫一扫 搜索
    private EditText et_new_address;//定位地址

    /*积分抢购 抢购专区 商家入驻 跑腿服务*/
    private LinearLayout lin_jifen, lin_qianggou, lin_ruzhu, lin_paotui;

    /*签到 充值 联系电话*/
    //private Button but_sign, but_recharge, but_phone;

    /*接口地址*/
    private String PATH = "";
    private String uid, pwd, phone;

    private List<ImageView> views = new ArrayList<ImageView>();
    private List<ADInfo> infos = new ArrayList<ADInfo>();
    private CycleViewPager cycleViewPager;

//    private String[] imageUrls = {"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
//            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
//            "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
//            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
//            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};

    private ImageView iv_adv001, iv_adv002;
    private String linkurl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("首页 加载");
        view = inflater.inflate(R.layout.fragment_home_page, null);
        x.Ext.init(getActivity().getApplication());
        x.Ext.setDebug(BuildConfig.DEBUG);

        initData();

        if (!(userInfo.getUserInfo().equals(""))) {
            Register register = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), Register.class);
            uid = register.getMsg().getUid();
            phone = register.getMsg().getPhone();
            if (!(userInfo.getPwd().equals(""))) {
                pwd = userInfo.getPwd();
            }

            if (userInfo.getCode().equals("0")) {
                //System.out.println("我的验证码"+userInfo.getCode());
                getSignToDay(uid, pwd);
            } else {
                //System.out.println("我的手机号"+phone);
                getSignToDay("phone", phone);
            }
        } else {
            toast("未登录");
        }

        configImageLoader();
        initialize();

        /*广告位*/
        getAdvByType("weixinmid");
        getAdvByTypes("weixinmid2");

        return view;
    }

    /**
     * 组件 初始化
     */
    private void initData() {
        userInfo = new UserInfo(getActivity().getApplication());
        //jingang = userInfo.getLogin();

//        lin_self_support = (LinearLayout) view.findViewById(R.id.lin_self_support);
//        lin_directly_mark = (LinearLayout) view.findViewById(R.id.lin_directly_mark);
//        lin_second_hand = (LinearLayout) view.findViewById(R.id.lin_second_hand);
//        lin_village_manage = (LinearLayout) view.findViewById(R.id.lin_village_manage);
//
//        lin_self_support.setOnClickListener(this);
//        lin_directly_mark.setOnClickListener(this);
//        lin_second_hand.setOnClickListener(this);
//        lin_village_manage.setOnClickListener(this);

        //
//        iv_integral = (ImageView) view.findViewById(R.id.iv_integral);
//        iv_scare_buying = (ImageView) view.findViewById(R.id.iv_scare_buying);
//        iv_business = (ImageView) view.findViewById(R.id.iv_business);
//        iv_run = (ImageView) view.findViewById(R.id.iv_run);
//
//        iv_integral.setOnClickListener(this);
//        iv_scare_buying.setOnClickListener(this);
//        iv_business.setOnClickListener(this);
//        iv_run.setOnClickListener(this);

        //
//        lin_appointment = (LinearLayout) view.findViewById(R.id.lin_appointment);
//        lin_food_out = (LinearLayout) view.findViewById(R.id.lin_food_out);
//        lin_express = (LinearLayout) view.findViewById(R.id.lin_express);
//        lin_more = (LinearLayout) view.findViewById(R.id.lin_more);
//
//        lin_appointment.setOnClickListener(this);
//        lin_food_out.setOnClickListener(this);
//        lin_express.setOnClickListener(this);
//        lin_more.setOnClickListener(this);

        //地址
//        et_address = (EditText) view.findViewById(R.id.et_address);
//        et_address.setOnClickListener(this);
//        tv_shopname = (TextView) view.findViewById(R.id.tv_shopname);
//        tv_shopname.setOnClickListener(this);

        //进入店铺
        tv_gointo_shop = (TextView) view.findViewById(R.id.tv_gointo_shop);
        tv_gointo_shop.setOnClickListener(this);

        //扫一哈子
//        iv_scanning = (ImageView) view.findViewById(R.id.iv_scanning);
//        iv_scanning.setOnClickListener(this);

        //垂直跑马灯
        marqueeView = (MarqueeView) view.findViewById(R.id.marqueeView);

        // Android ScrollView嵌套ListView嵌套GridView的上下拉以及加载更多
        mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.main_pull_refresh_view);

        myGridView = (MyGridView) view.findViewById(R.id.myGridView);

//        et_search = (EditText) view.findViewById(R.id.et_home_page_search);
//        et_search.setOnClickListener(this);

        //没有网络或加载数据失败的情况
        lin_no_network = (LinearLayout) view.findViewById(R.id.lin_no_network);
        but_load_again = (Button) view.findViewById(R.id.but_load_again);
        but_load_again.setOnClickListener(this);

        //有网链接
        lin_yes_network = (LinearLayout) view.findViewById(R.id.lin_yes_network);

        //
        //rel_jingang = (RelativeLayout) view.findViewById(R.id.rel_jingang);

        /*界面修改*/
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

        /*签到 充值 联系电话*/
//        but_sign = (Button) view.findViewById(R.id.but_sign);
//        but_recharge = (Button) view.findViewById(R.id.but_recharge);
//        but_phone = (Button) view.findViewById(R.id.but_phone);
//
//        but_sign.setOnClickListener(this);
//        but_recharge.setOnClickListener(this);
//        but_phone.setOnClickListener(this);

        //新版商品分类
        mgv_classifly = (MyGridView) view.findViewById(R.id.mgv_goods_classify);

        /*轮播图*/
        //sliderLayout = (SliderLayout) view.findViewById(R.id.sliderLayout);

        cycleViewPager = (CycleViewPager) getActivity().getFragmentManager().findFragmentById(R.id.fragment_cycle_viewpager_content);

        /*广告位*/
        iv_adv001 = (ImageView) view.findViewById(R.id.iv_adv001);
        iv_adv002 = (ImageView) view.findViewById(R.id.iv_adv002);
        iv_adv001.setOnClickListener(this);
        iv_adv002.setOnClickListener(this);


        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);
        mPullToRefreshView.setLastUpdated(new Date().toLocaleString());

        str_marqueeView.add("易贝通便利店欢迎您！");

        marqueeView.startWithList(str_marqueeView);

        /*判断网络连接状态*/
        isNetworkUtil();

        /*str_marqueeView.add("贝通开业了！");
        str_marqueeView.add("通开业了！");
        str_marqueeView.add("开业了！");
        str_marqueeView.add("业了！");
        str_marqueeView.add("了！");
        str_marqueeView.add("！");*/

        MyScrollView.setScanScrollChangedListener(this);

        MyScrollView.doSomeThing();

    }

    @Override
    public void onScrolledToBottom(boolean isToButtom) {
        //isToBrttom = ture到达底部
    }

    @Override
    public void onScrolledToTop(boolean isToTop) {
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
    public void onDestroy() {
        super.onDestroy();
    }

    LocalBroadcastManager broadcastManager;

    /**
     * 注册广播接收器
     */
    private void registerReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("jerry");
        broadcastManager.registerReceiver(mAdDownLoadReceiver, intentFilter);
    }

    private BroadcastReceiver mAdDownLoadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String change = intent.getStringExtra("change");
            if ("yes".equals(change)) {
                // 这地方只能在主线程中刷新UI,子线程中无效，因此用Handler来实现
                new Handler().post(new Runnable() {
                    public void run() {
                        //在这里来写你需要刷新的地方
                        //例如：testView.setText("恭喜你成功了");
                        System.out.println("刷新！！！！！！！");

                        ceFoodList.clear();
                        getShopGoods(latitude, longtitude, 1);

                    }
                });
            }
        }
    };

    MainActivity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
        //注册广播
        registerReceiver();
    }

    /**
     * 注销广播
     */
    @Override
    public void onDetach() {
        super.onDetach();
        broadcastManager.unregisterReceiver(mAdDownLoadReceiver);
    }



    private void initialize() {

    }

    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            if (cycleViewPager.isCycle()) {
                position = position - 1;
//                Toast.makeText(getActivity(),
//                        "position-->" + info.getContent(), Toast.LENGTH_SHORT)
//                        .show();
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

                        userInfo.saveADV(new Gson().toJson(wxADV.getMsg()));


                        for(int i = 0; i < wxADV.getMsg().size(); i ++){
                            ADInfo info = new ADInfo();
                            info.setUrl("http://www.ybt9.com/"+wxADV.getMsg().get(i).getImg());
                            info.setContent("图片-->" + i );
                            infos.add(info);
                        }

                        /*for(int i = 0; i < imageUrls.length; i ++){
                            ADInfo info = new ADInfo();
                            info.setUrl(imageUrls[i]);
                            info.setContent("图片-->" + i );
                            infos.add(info);
                        }*/

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
                        cycleViewPager.setTime(4000);
                        //设置圆点指示图标组居中显示，默认靠右
                        cycleViewPager.setIndicatorCenter();





                        /*轮播图*/
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
     * @param type
     */
    public void getAdvByType(String type){
        PATH = HttpPath.PATH+HttpPath.ADV+"type="+type;

        RequestParams params = new RequestParams(PATH);
        System.out.println("广告位一"+PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("广告位一"+result);
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
     * @param type
     */
    public void getAdvByTypes(String type){
        PATH = HttpPath.PATH+HttpPath.ADV+"type="+type;

        RequestParams params = new RequestParams(PATH);
        System.out.println("广告位二"+PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("广告位二"+result);
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
                        Error error = GsonUtil.gsonIntance().gsonToBean(result, Error.class);
                        if (error.isError() == true) {
                        } else {
                            //but_sign.setText("已签到");
                        }
                        toast("" + error.getMsg().toString());

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
                getShopGoods(latitude, longtitude, page);

                //Toast.makeText(getActivity(), "加载更多数据!", Toast.LENGTH_SHORT).show();
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

                netWorkType = 0;
                exit = false;
                ceFoodList.clear();
                //getShopGoods(latitude, longtitude, 1);

                isNetworkUtil();
                //Toast.makeText(getActivity(), "数据刷新完成!", Toast.LENGTH_SHORT).show();
            }

        }, 1000);
    }

    /**
     * 获取首页数据
     *
     * @param latitude
     * @param longtitude
     */
    public void getShopGoods(String latitude, String longtitude, final int page) {
        String Path = HttpPath.PATH + HttpPath.GOODSINDEX +
                "lat=" + latitude + "&lng=" + longtitude + "" + "&page=" + page;

        System.out.println("" + Path);

        RequestParams params = new RequestParams(Path);
        x.http().post(params,
                new Callback.CommonCallback<String>() {
                    public void onSuccess(String result) {
                        pd.dismiss();
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

                        //et_address.setText(shopName);
                        et_new_address.setText("" + shopName);

                        //tv_shopname.setText("(当前店铺：" + shopName + "）");

                        if (goodsIndex.getMsg().getCatefoodslist().toString().equals("[]")) {
                            toast("没有更多数据");

                        } else {
                            ceFood = goodsIndex.getMsg().getCatefoodslist();
                            //获取信息列表
                            ceFoodList.addAll(ceFood);

                            gridViewAdapter = new GridViewAdapter();
                            gridViewAdapter.setCatefoodslistBeanList(getActivity(), ceFoodList);

                            myGridView.setAdapter(gridViewAdapter);

                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        System.out.println("首页onError" + ex.toString());
                        pd.dismiss();
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
        //et_address.setText(address);

        getShopGoods(latitude, longtitude, page);

        System.out.println("经纬度" + longtitude + "   " + latitude);

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        System.out.println("ssssss");
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

    private LocationService locationService;

    /**
     * 获取当前的经纬度
     */
    public void getLocation() {
        pd = ProgressDialog.show(getActivity(), "正在刷新数据", "请稍候");

        super.onStart();
        locationService = new LocationService(getActivity());
        //获取locationservice实例，建议应用中只初始化1个location实例，
        // 然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        locationService.start();

        /*开启线程*/
        new Thread(new ThreadShow()).start();

    }


    private int netWorkType = 0;//定位类型

    private String stringBuffer = "";//定位返回

    /**
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     */
    private BDLocationListener mListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            locationService.stop();//出现结果即停止定位
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                sb.append(location.getTime());
                sb.append("\nlocType : ");// 定位类型
                sb.append(location.getLocType());
                sb.append("\nlocType description : ");// *****对应的定位类型说明*****
                //sb.append(location.getLocTypeDescription());
                sb.append("\nlatitude : ");// 纬度
                sb.append(location.getLatitude());

                //获取纬度
                latitude = Double.toString(location.getLatitude());

                sb.append("\nlontitude : ");// 经度
                sb.append(location.getLongitude());

                //获取经度
                longtitude = Double.toString(location.getLongitude());

                sb.append("\nradius : ");// 半径
                sb.append(location.getRadius());
                sb.append("\nCountryCode : ");// 国家码
                sb.append(location.getCountryCode());
                sb.append("\nCountry : ");// 国家名称
                sb.append(location.getCountry());
                sb.append("\ncitycode : ");// 城市编码
                sb.append(location.getCityCode());
                sb.append("\ncity : ");// 城市
                sb.append(location.getCity());
                sb.append("\nDistrict : ");// 区
                sb.append(location.getDistrict());
                sb.append("\nStreet : ");// 街道
                sb.append(location.getStreet());
                sb.append("\naddr : ");// 地址信息
                sb.append(location.getAddrStr());

                address = location.getStreet();

                sb.append("\nUserIndoorState: ");// *****返回用户室内外判断结果*****
                //sb.append(location.getUserIndoorState());
                sb.append("\nDirection(not all devices have value): ");
                sb.append(location.getDirection());// 方向
                sb.append("\nlocationdescribe: ");
                sb.append(location.getLocationDescribe());// 位置语义化信息
                sb.append("\nPoi: ");// POI信息
                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                    for (int i = 0; i < location.getPoiList().size(); i++) {
                        Poi poi = (Poi) location.getPoiList().get(i);
                        sb.append(poi.getName() + ";");
                    }
                }
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 速度 单位：km/h
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());// 卫星数目
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 海拔高度 单位：米
                    sb.append("\ngps status : ");
                    //sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");

                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    // 运营商信息
                    if (location.hasAltitude()) {// *****如果有海拔高度*****
                        sb.append("\nheight : ");
                        sb.append(location.getAltitude());// 单位：米
                    }
                    sb.append("\noperationers : ");// 运营商信息
                    sb.append(location.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");

                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
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

                netWorkType = location.getLocType();//网络定位返回码

                stringBuffer = sb.toString();//网络定位返回数据

                System.out.println("经纬度1111111111111" + longtitude + "   " + latitude);

                //logMsg(sb.toString());

            }
        }
    };

    // handler类接收数据
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                pd.dismiss();

                lin_yes_network.setVisibility(View.VISIBLE);
                lin_no_network.setVisibility(View.INVISIBLE);

                logMsg(stringBuffer);

                exit = true;

            } else if (msg.what == 2) {
                pd.dismiss();
                lin_yes_network.setVisibility(View.INVISIBLE);
                lin_no_network.setVisibility(View.VISIBLE);

                exit = true;

            }
        }

        ;
    };

    public boolean exit = false;

    // 线程类
    class ThreadShow implements Runnable {

        @Override
        public void run() {
            while (!exit) {
                try {
                    //超时10s
                    Thread.sleep(3000);
                    if (netWorkType == 161) {
                        //pd.dismiss();
                        Message msg = new Message();
                        msg.what = 1;
                        handler.sendMessage(msg);
                        System.out.println("发送...");

                    } else {
                        Message msg = new Message();
                        msg.what = 2;
                        handler.sendMessage(msg);
                        System.out.println("定位失败");

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("线程 错误...");
                }
            }
        }
    }

    private List<Recommed.MsgBean> msgBeanCate = new ArrayList<>();
    private CateInfoAdapter cateInfoAdapter;

    /**
     * 首页推荐 店铺优选
     * 获取店铺推荐区
     *
     * @param shopid
     */
    public void getCateRecommed(String shopid) {
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

                        cateInfoAdapter = new CateInfoAdapter(getActivity(), msgBeanCate);
                        //gv_cateinfo.setAdapter(cateInfoAdapter);
                        mgv_classifly.setAdapter(cateInfoAdapter);

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
     * 首页推荐店铺区适配器(现为 商品分类按钮)
     */
    public class CateInfoAdapter extends BaseAdapter {
        private Context mContext;
        private List<Recommed.MsgBean> msgBeanList;

        public CateInfoAdapter(Context mContext, List<Recommed.MsgBean> msgBeanList) {
            this.mContext = mContext;
            this.msgBeanList = msgBeanList;
        }

        @Override
        public int getCount() {
            return msgBeanList.size();
        }

        @Override
        public Object getItem(int i) {
            return msgBeanList.get(i);
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
                view = LayoutInflater.from(mContext).inflate(R.layout.item_goods_classily, null);

                vh.imageView = (ImageView) view.findViewById(R.id.iv_item_gc_img);
                vh.title = (TextView) view.findViewById(R.id.tv_item_gc_title);

                view.setTag(vh);

            } else {
                vh = (ViewHolder) view.getTag();
            }

            String name = msgBeanList.get(i).getName();
            String imgPath = msgBeanList.get(i).getImg();

            if (imgPath.equals("")) {
                vh.imageView.setImageResource(R.mipmap.yibeitong001);

            } else {
                /*BitmapUtils bitmapUtils = new BitmapUtils(mContext);

                bitmapUtils.configDiskCacheEnabled(true);
                bitmapUtils.configMemoryCacheEnabled(false);

                bitmapUtils.display(vh.imageView, "http://www.ybt9.com/" + imgPath);*/
                x.image().bind(vh.imageView, "http://www.ybt9.com/" + imgPath);

            }

            vh.title.setText("" + name);

            //点击GridView
            mgv_classifly.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String type = msgBeanList.get(i).getType();
                    String cateid = msgBeanList.get(i).getOrderid();
                    String name = msgBeanList.get(i).getName();
                    String param = msgBeanList.get(i).getParam();
                    String url = (String) msgBeanList.get(i).getUrl();
                    if (type.equals("cate")) {
                        //toast("商品分类" + msgBeanList.get(i).getName());

                        intent = new Intent(getActivity(), CateInfoActivity.class);
                        intent.putExtra("shopid", shopid);
                        intent.putExtra("cateid", cateid);
                        intent.putExtra("param", param);
                        intent.putExtra("name", name);
                        startActivity(intent);

                    } else if (type.equals("jiameng")) {
                        //toast("jiameng" + msgBeanList.get(i).getName());
                        intent = new Intent(getActivity(), JoinActivity.class);
                        intent.putExtra("title", "我要加盟");
                        intent.putExtra("url", url);
                        startActivity(intent);
                        //startActivity(new Intent(getActivity(), JoinActivity.class));

                    } else if (type.equals("tesefuwu")) {
                        //toast("tesefuwu" + msgBeanList.get(i).getName());
                        intent = new Intent(getActivity(), SpecialActivity.class);
                        intent.putExtra("shopid", shopid);
                        startActivity(intent);

                        //startActivity(new Intent(getActivity(), SpecialActivity.class));

                    } else if (type.equals("fenlei")) {
                        //toast("fenlei" + msgBeanList.get(i).getName());

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
                        toast("啥啥啥");
                    }

                }
            });

            /*点击事件*/
            /*vh.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (param.equals("sssm")) {
                        System.out.println("送水上门");
                        intent = new Intent(getActivity(), SendWaterActivity.class);
                        intent.putExtra("shopid", shopid);
                        startActivity(intent);

                    } else {
                        intent = new Intent(getActivity(), CateInfoActivity.class);
                        intent.putExtra("shopid", shopid);
                        intent.putExtra("param", param);
                        intent.putExtra("cateid", cateid);
                        intent.putExtra("name", name);
                        startActivity(intent);
                    }

                }
            });*/


            return view;
        }

        public class ViewHolder {
            public ImageView imageView;
            public TextView title;

        }

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.et_address:
//                //定位
//                intent = new Intent(getActivity(), LocationActivity.class);
//                startActivityForResult(intent, 10);
//                break;

//            case R.id.et_home_page_search:
//                //搜索店铺、商品
//                intent = new Intent(getActivity(), SearchActivity.class);
//                intent.putExtra("lat", latitude);
//                intent.putExtra("lng", longtitude);
//                intent.putExtra("shopid", shopid);
//                startActivity(intent);
//
//                break;

//            case R.id.lin_self_support:
//                //自营专区
//                intent = new Intent(getActivity(), SelfSupportActivity.class);
//                intent.putExtra("latitude", latitude);
//                intent.putExtra("longitude", longtitude);
//                intent.putExtra("shopid", shopid);
//                intent.putExtra("shopName", shopName);
//                startActivityForResult(intent, 10);
//
//                break;

//            case R.id.lin_directly_mark:
//                //直营便利店
//                intent = new Intent(getActivity(), DirectlyMarkActivity.class);
//                intent.putExtra("latitude", latitude);
//                intent.putExtra("longitude", longtitude);
//                intent.putExtra("shopid", shopid);
//                intent.putExtra("shopName", shopName);
//                startActivityForResult(intent, 10);
//
//                break;

//            case R.id.lin_second_hand:
//                //二手交易
//                if (jingang.equals("")) {
//                    //未登录
//                    startActivity(new Intent(getActivity(), LoginRegisterActivity.class));
//                } else if (jingang.equals("0")) {
//                    //未登录
//                    startActivity(new Intent(getActivity(), LoginRegisterActivity.class));
//                } else if (jingang.equals("1")) {
//                    //已登录
//                    intent = new Intent(getActivity(), SecondHandActivity.class);
//                    intent.putExtra("id", "1");
//                    startActivity(intent);
//                    //startActivity(new Intent(getActivity(), VillageActivity.class));
//                }
//
//                break;

//            case R.id.lin_village_manage:
//                //小区信息
//                //进入之前判断是否已经登录
//                if (jingang.equals("")) {
//                    //未登录
//                    startActivity(new Intent(getActivity(), LoginRegisterActivity.class));
//                } else if (jingang.equals("0")) {
//                    //未登录
//                    startActivity(new Intent(getActivity(), LoginRegisterActivity.class));
//                } else if (jingang.equals("1")) {
//                    //已登录
//                    intent = new Intent(getActivity(), VillageActivity.class);
//                    intent.putExtra("mkid", "2");
//                    startActivity(intent);
//                }
//                break;
//            case R.id.lin_appointment:
//                //预约服务
//                startActivity(new Intent(getActivity(), WillOpenActivity.class));
//                break;

//            case R.id.lin_food_out:
//                //美食外卖
//                intent = new Intent(getActivity(), FoodOutActivity.class);
//                intent.putExtra("latitude", latitude);
//                intent.putExtra("longitude", longtitude);
//                intent.putExtra("shopid", shopid);
//                intent.putExtra("shopName", shopName);
//                startActivityForResult(intent, 10);
//
//                break;

//            case R.id.lin_express:
//                //收发快递
//                Intent intent = new Intent(getActivity(), ExpressActivity.class);
//                intent.putExtra("shopid", shopid);
//                startActivity(intent);
//                break;

//            case R.id.lin_more:
//                //更多
//                break;

            case R.id.tv_gointo_shop:
                //进入店铺
                intent = new Intent(getActivity(), ShopNewActivity.class);
                intent.putExtra("shopid", shopid);
                intent.putExtra("shopname", shopName);
                intent.putExtra("startTime", startTime);
                intent.putExtra("mapphone", mapphone);
                intent.putExtra("address", address);

                intent.putExtra("lat", latitude);
                intent.putExtra("lng", longtitude);

                startActivity(intent);

                break;

//            case R.id.iv_scanning:
//                //扫一哈子 6.0系统需要手动设置摄像头权限
//
//                if (ContextCompat.checkSelfPermission(getActivity(),
//                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(
//                            getActivity(), new String[]{
//                                    Manifest.permission.CAMERA
//                            }, 9
//                    );
//
//                } else {
//
//                    intent = new Intent(getActivity(), ZXingCaptureActivity.class);
//                    intent.putExtra("shopid", shopid);
//                    intent.putExtra("coder", "2");
//                    startActivity(intent);
//
//                }
//
//                break;

            case R.id.but_load_again:
                //重新加载
                exit = false;
                isNetworkUtil();

                break;

            case R.id.iv_new_scanning:
                //新版扫一扫

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
                    startActivity(intent);

                }
                break;
            case R.id.iv_new_search:
                //新版搜索
                intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("lat", latitude);
                intent.putExtra("lng", longtitude);
                intent.putExtra("shopid", shopid);
                startActivity(intent);
                break;

            case R.id.et_new_address:
                //新版选择地址
                intent = new Intent(getActivity(), LocationActivity.class);
                startActivityForResult(intent, 10);
                break;

            case R.id.lin_jinfen:
                //新版积分专区

                String url_jifen = "https://www.ybt9.com/index.php?ctrl=app&action=specialpage&&id=11&lat="
                        + latitude + "&lng" + longtitude + "=&mapname=" + address;
                intent = new Intent(getActivity(), JoinActivity.class);
                intent.putExtra("title", "积分专区");
                intent.putExtra("url", url_jifen);
                startActivity(intent);
                break;

            case R.id.lin_qianggou:
                //新版 抢购专区

                String qianggou = "https://www.ybt9.com/index.php?ctrl=app&action=specialpage&&id=4&" +
                        "lat=" + latitude + "&lng=" + longtitude + "&mapname=" + address;
                intent = new Intent(getActivity(), JoinActivity.class);
                intent.putExtra("title", "抢购专区");
                intent.putExtra("url", qianggou);
                startActivity(intent);
                break;

            case R.id.lin_ruzhu:
                //改为签到
                if (!(userInfo.getUserInfo().equals(""))) {
                    Register register = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), Register.class);
                    uid = register.getMsg().getUid();
                    if (!(userInfo.getPwd().equals(""))) {
                        pwd = userInfo.getPwd();

                    }

                    if (userInfo.getCode().equals("0")) {
                        System.out.println("0000000");
                        signToDay(uid, pwd);
                    } else {
                        System.out.println("1111111");
                        signToDay("phone", phone);
                    }
                } else {
                    toast("未登录");
                }

                break;

            case R.id.lin_paotui:
                //新版 跑腿服务
                //startActivity(new Intent(getActivity(), WillOpenActivity.class));
                //改为充值
                intent = new Intent(getActivity(), YuEActivity.class);
                intent.putExtra("yue","100.00");
                startActivity(intent);

                break;

//            case R.id.but_sign:
//                //签到
//                if (!(userInfo.getUserInfo().equals(""))) {
//                    Register register = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), Register.class);
//                    uid = register.getMsg().getUid();
//                    if (!(userInfo.getPwd().equals(""))) {
//                        pwd = userInfo.getPwd();
//
//                        if (userInfo.getCode().equals("0")) {
//                            //System.out.println("我的验证码"+userInfo.getCode());
//                            signToDay(uid, pwd);
//                        } else {
//                            //System.out.println("我的手机号"+phone);
//                            signToDay("phone", phone);
//                        }
//
//                    }
//                } else {
//                    toast("未登录");
//                }
//
//                break;

//            case R.id.but_recharge:
//                //充值
//                intent = new Intent(getActivity(), YuEActivity.class);
//                startActivity(intent);
//
//                break;

//            case R.id.but_phone:
//                //联系电话
//                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "17865069350"));
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//
//                break;

            case R.id.iv_adv001:
                //toast("iv_adv001");

                intent = new Intent(getActivity(), JoinActivity.class);
                intent.putExtra("title", "我要加盟");
                intent.putExtra("url", "https://www.ybt9.com"+linkurl);
                startActivity(intent);
                break;

            case R.id.iv_adv002:
                //toast("iv_adv002");
                break;

            default:
                break;
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

            getShopGoods(latitude, longtitude, 1);

        }

    }

    /**
     * 判断网络连接情况 灵需要考虑请求超时的问题
     */
    public void isNetworkUtil() {

        if (NetworkUtils.isNotWorkAvilable(getActivity())) {
            if (NetworkUtils.getCurrentNetType(getActivity()) != null) {
                //有网络连接 判断网络的连接类型是否符合标准
                //可以根据规定进行数据的刷新

                getLocation();

                //轮播图
                getWxAdv();

            }
        } else {
            //无网络连接  显示缓存数 据
            //toast("无网络连接");
            //et_address.setText("定位失败");
            et_new_address.setText("定位失败");

            //6.0读写权限的手动设置
            /*if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        getActivity(), new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        }, 7
                );

            } else {*/

            /*首页商品展示*/
            String cateFoodList = userInfo.getMeetings();
            System.out.println("缓存" + userInfo.getMeetings());

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
                    cateInfoAdapter = new CateInfoAdapter(getActivity(), classifyList);
                    mgv_classifly.setAdapter(cateInfoAdapter);
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

                    for(int i = 0; i < wxAdvList.size(); i ++){
                        ADInfo info = new ADInfo();
                        info.setUrl("http://www.ybt9.com/"+wxAdvList.get(i).getImg());
                        info.setContent("图片-->" + i );
                        infos.add(info);
                    }

                        /*for(int i = 0; i < imageUrls.length; i ++){
                            ADInfo info = new ADInfo();
                            info.setUrl(imageUrls[i]);
                            info.setContent("图片-->" + i );
                            infos.add(info);
                        }*/

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
                    cycleViewPager.setTime(4000);
                    //设置圆点指示图标组居中显示，默认靠右
                    cycleViewPager.setIndicatorCenter();

                    configImageLoader();


//                    HashMap<String, String> url_maps = new HashMap<String, String>();
//                    for (int i = 0; i < wxAdvList.size(); i++) {
//                        //url_maps.put("易贝通开业了" + i, "http://www.ybt9.com/" + wxADV.getMsg().get(i).getImg());
//                        url_maps.put(wxAdvList.get(i).getTitle(), "http://www.ybt9.com/" + wxAdvList.get(i).getImg());
//                    }
//
//                    TextSliderView textSliderView;
//                    for (String name : url_maps.keySet()) {
//                        textSliderView = new TextSliderView(getActivity());
//                        // initialize a SliderLayout
//                        textSliderView
//                                .description("")
//                                .image(url_maps.get(name))
//                                .setScaleType(BaseSliderView.ScaleType.Fit);
//                        // .setOnSliderClickListener(this);
//
//                        //add your extra information
//                        textSliderView.getBundle()
//                                .putString("", name);
//
//                        sliderLayout.addSlider(textSliderView);
//                    }
//
//                    //sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
//                    //sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
//                    //sliderLayout.setCustomAnimation(new DescriptionAnimation());
//                    sliderLayout.setDuration(4000);

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
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}