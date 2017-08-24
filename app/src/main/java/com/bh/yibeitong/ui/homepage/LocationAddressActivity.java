package com.bh.yibeitong.ui.homepage;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.bh.yibeitong.LocationService;
import com.bh.yibeitong.R;
import com.bh.yibeitong.application.CatchExcep;
import com.bh.yibeitong.bean.Address;
import com.bh.yibeitong.bean.Register;
import com.bh.yibeitong.refresh.MyListView;
import com.bh.yibeitong.ui.LoginRegisterActivity;
import com.bh.yibeitong.ui.address.AddAddressActivity;
import com.bh.yibeitong.utils.CodeUtils;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.CustomDialog;
import com.bh.yibeitong.view.UserInfo;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingang on 2017/7/25.
 * 定位店铺
 */
public class LocationAddressActivity extends Activity implements
        View.OnClickListener,
        BDLocationListener,
        OnGetGeoCoderResultListener {

    //private LocationClient mLocClient = null;

    private LocationService mLocationService = null;
    private GeoCoder geoCoder;
    private String city;
    private boolean isFirstLoc = true;
    private LatLng locationLatLng;
    private String latitude = "", longitude = "", address = "";

    /*本地轻量型缓存*/
    private UserInfo userInfo;
    private String jingang, uid = "", pwd = "", phone = "";

    /*接收页面传值*/
    private Intent intent;

    /*接口地址*/
    private String PATH = "";

    /*UI显示*/
    private ImageView iv_back;
    private TextView tv_add_address, tv_address, tv_poi, tv_more_address;
    private LinearLayout lin_more_address, lin_location;
    private MyListView mlv_address, mlv_poi;

    /*适配器*/
    private AddressAdapter addressAdapter;
    private PoiAdapter poiAdapter;

    private List<Address.MsgBean> addressList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_location_address);

        initData();
        initDatas();
    }

    /*组件初始化*/
    public void initData() {
        userInfo = new UserInfo(getApplication());
        jingang = userInfo.getLogin();

        iv_back = (ImageView) findViewById(R.id.iv_al_back);
        tv_add_address = (TextView) findViewById(R.id.tv_al_add_address);
        lin_more_address = (LinearLayout) findViewById(R.id.lin_al_more_address);
        lin_location = (LinearLayout) findViewById(R.id.lin_al_location);

        iv_back.setOnClickListener(this);
        tv_add_address.setOnClickListener(this);
        lin_more_address.setOnClickListener(this);
        lin_location.setOnClickListener(this);

        tv_address = (TextView) findViewById(R.id.tv_al_address);
        tv_poi = (TextView) findViewById(R.id.tv_al_poi);
        mlv_address = (MyListView) findViewById(R.id.mlv_al_address);
        mlv_poi = (MyListView) findViewById(R.id.mlv_al_poi);
        tv_more_address = (TextView) findViewById(R.id.tv_al_more_address);
        tv_more_address.setOnClickListener(this);

        if (isLogin()) {
            tv_address.setVisibility(View.VISIBLE);
            Register register = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), Register.class);
            phone = register.getMsg().getPhone();
            uid = register.getMsg().getUid();

            pwd = userInfo.getPwd();

            if (userInfo.getCode().equals("0")) {
                getAddressList(uid, pwd, "1");
            } else {
                getAddressList("phone", phone, "1");
            }
        } else {
            tv_address.setVisibility(View.GONE);
            System.out.println("未登录");

        }

    }

    /*地图检索*/
    private void initDatas() {
        mLocationService =  new LocationService(this);

        //mLocationService = ((CatchExcep) getApplication()).locationService;
        mLocationService.registerListener(this);

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

        mLocationService.setLocationOption(mLocationService.getDefaultLocationClientOption());
        //mLocClient.setLocOption(mLocationService.getDefaultLocationClientOption());
        // 开始定位
        //mLocClient.start();
        mLocationService.start();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_al_back:
                //返回上层
                LocationAddressActivity.this.finish();
                break;

            case R.id.tv_al_add_address:
                //新增地址

                if (isLogin()) {
                    intent = new Intent(LocationAddressActivity.this, AddAddressActivity.class);
                    intent.putExtra("title", "添加收货地址");
                    startActivityForResult(intent, CodeUtils.REQUEST_CODE_LOCATION_ADDRESS);
                } else {
                    dialog();
                }


                break;

            case R.id.lin_al_more_address:
                //输入收货地址（地图）
                intent = new Intent(LocationAddressActivity.this, LocationTestActivity.class);
                startActivityForResult(intent, CodeUtils.REQUEST_CODE_LOCATION_ADDRESS);
                break;

            case R.id.lin_al_location:
                //定位当前地址

//                Intent intent = new Intent();
//                intent.putExtra("lat", latitude);
//                intent.putExtra("lng", longitude);
//                setResult(2, intent);// 设置resultCode，onActivityResult()中能获取到

                setResult(latitude, longitude, address);
                LocationAddressActivity.this.finish();

                break;
            case R.id.tv_al_more_address:
                //更多地址
                intent = new Intent(LocationAddressActivity.this, LocationTestActivity.class);
                startActivityForResult(intent, CodeUtils.REQUEST_CODE_LOCATION_ADDRESS);

                break;

            default:
                break;
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
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setMessage("还未登录，确定要登录吗？");
        builder.setPositiveButton("登录/注册", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //设置你的操作事项
                startActivityForResult(new Intent(LocationAddressActivity.this, LoginRegisterActivity.class),
                        CodeUtils.REQUEST_CODE_LOCATION_ADDRESS);
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

    /*页面跳转时传值*/
    public void setResult(String lat, String lng, String address) {
        intent = new Intent();
        intent.putExtra("lat", lat);
        intent.putExtra("lng", lng);
        intent.putExtra("address", address);

        setResult(CodeUtils.REQUEST_CODE_LOCATION_ADDRESS, intent);
    }

    /**
     * 获取收货地址
     *
     * @param uid
     * @param pwd
     * @param page 页码
     */
    public void getAddressList(String uid, String pwd, String page) {
        if (userInfo.getCode().equals("0")) {
            PATH = HttpPath.PATH + HttpPath.ADDRESS_LIST +
                    "uid=" + uid + "&pwd=" + pwd + "&page=" + page;
        } else {
            PATH = HttpPath.PATH + HttpPath.ADDRESS_LIST +
                    "logintype=" + uid + "&loginphone=" + pwd + "&page=" + page;
        }

        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("我的收货地址" + result);

                        Address address = GsonUtil.gsonIntance().gsonToBean(result, Address.class);
                        addressList = address.getMsg();
                        if (addressList.size() > 0) {
                            tv_address.setVisibility(View.VISIBLE);

                            addressAdapter = new AddressAdapter(LocationAddressActivity.this, addressList);
                            mlv_address.setAdapter(addressAdapter);

                            mlv_address.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    setResult(addressList.get(i).getLat(), addressList.get(i).getLng(), addressList.get(i).getBigadr());
                                    LocationAddressActivity.this.finish();
                                }
                            });
                        } else {
                            tv_address.setVisibility(View.GONE);
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

    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        // 定位数据
//        MyLocationData data = new MyLocationData.Builder()
//                // 定位精度bdLocation.getRadius()
//                .accuracy(bdLocation.getRadius())
//                // 此处设置开发者获取到的方向信息，顺时针0-360
//                .direction(bdLocation.getDirection())
//                // 经度
//                .latitude(bdLocation.getLatitude())
//                // 纬度
//                .longitude(bdLocation.getLongitude())
//                // 构建
//                .build();

        // 设置定位数据
        //mBaiduMap.setMyLocationData(data);

        // 是否是第一次定位
        if (isFirstLoc) {
            isFirstLoc = false;
            LatLng ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
            MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(ll, 18);
        }

        locationLatLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());

        latitude = String.valueOf(locationLatLng.latitude);
        longitude = String.valueOf(locationLatLng.longitude);


        // 获取城市，待会用于POISearch
        city = bdLocation.getCity();

        // 创建GeoCoder实例对象
        geoCoder = GeoCoder.newInstance();
        // 发起反地理编码请求(经纬度->地址信息)
        ReverseGeoCodeOption reverseGeoCodeOption = new ReverseGeoCodeOption();
        // 设置反地理编码位置坐标
        reverseGeoCodeOption.location(new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude()));
        geoCoder.reverseGeoCode(reverseGeoCodeOption);

        // 设置查询结果监听者
        geoCoder.setOnGetGeoCodeResultListener(LocationAddressActivity.this);

        //mLocClient.stop();//停止定位
        mLocationService.stop();
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult arg0) {

    }

    // 拿到变化地点后的附近地址
    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        final List<PoiInfo> poiInfos = reverseGeoCodeResult.getPoiList();
        //mLocClient.stop();//停止定位
        mLocationService.stop();

        System.out.println("这里的值啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊:" + poiInfos.get(0).address);

        if (poiInfos != null && !"".equals(poiInfos)) {
            if (poiInfos.size() > 0) {
                tv_poi.setVisibility(View.VISIBLE);

                address = poiInfos.get(0).name.toString();
                poiAdapter = new PoiAdapter(LocationAddressActivity.this, poiInfos);
                mlv_poi.setAdapter(poiAdapter);
                mlv_poi.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String name = poiInfos.get(position).name.toString();
                        double latitude = poiInfos.get(position).location.latitude;
                        double longitude = poiInfos.get(position).location.longitude;

                        setResult(String.valueOf(latitude), String.valueOf(longitude), name);

                        LocationAddressActivity.this.finish();
                    }
                });
            } else {
                tv_poi.setVisibility(View.GONE);
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CodeUtils.REQUEST_CODE_LOCATION_ADDRESS) {
            if (resultCode == CodeUtils.REQUEST_CODE_LOCATION) {
                Bundle bundle = data.getExtras();
                latitude = bundle.getString("lat");
                longitude = bundle.getString("lng");
                address = bundle.getString("address");

                setResult(latitude, longitude, address);
                LocationAddressActivity.this.finish();
            } else if (resultCode == 3) {
                addressList.clear();
                if (userInfo.getCode().equals("0")) {
                    getAddressList(uid, pwd, "1");
                } else {
                    getAddressList("phone", phone, "1");
                }
            } else if (resultCode == CodeUtils.REQUEST_CODE_LOGIN ||
                    resultCode == CodeUtils.REQUEST_CODE_QUICK_LOGIN) {
                tv_address.setVisibility(View.VISIBLE);
                addressList.clear();

                jingang = userInfo.getLogin();

                if (isLogin()) {
                    tv_address.setVisibility(View.VISIBLE);
                    Register register = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), Register.class);
                    phone = register.getMsg().getPhone();
                    uid = register.getMsg().getUid();

                    pwd = userInfo.getPwd();

                    if (userInfo.getCode().equals("0")) {
                        getAddressList(uid, pwd, "1");
                    } else {
                        getAddressList("phone", phone, "1");
                    }
                } else {
                    tv_address.setVisibility(View.GONE);
                    System.out.println("未登录");

                }

            }
        }
    }

    /*我的收货地址 适配器*/
    public class AddressAdapter extends BaseAdapter {
        private Context mContext;
        private List<Address.MsgBean> msgBean = new ArrayList<>();

        public AddressAdapter(Context mContext, List<Address.MsgBean> msgBean) {
            this.mContext = mContext;
            this.msgBean = msgBean;
        }


        @Override
        public int getCount() {
            return msgBean.size();
        }

        @Override
        public Object getItem(int position) {
            return msgBean.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder vh;
            if (convertView == null) {
                vh = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_addresslist, null);

                vh.name = (TextView) convertView.findViewById(R.id.tv_item_address_name);
                vh.phone = (TextView) convertView.findViewById(R.id.tv_item_address_phone);
                vh.bigadr = (TextView) convertView.findViewById(R.id.tv_item_address_bigadr);
                vh.detailadr = (TextView) convertView.findViewById(R.id.tv_item_address_detailadr);

                vh.def_address = (CheckBox) convertView.findViewById(R.id.cb_item_address);

                vh.update = (Button) convertView.findViewById(R.id.but_item_address_update);
                vh.delete = (Button) convertView.findViewById(R.id.but_item_address_delete);

                vh.lin_address_updata = (LinearLayout) convertView.findViewById(R.id.lin_address_updata);
                vh.lin_address_updata.setVisibility(View.GONE);

                vh.lin_address_list = (LinearLayout) convertView.findViewById(R.id.lin_address_list);

                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }

            final String addresid = msgBean.get(position).getId();
            String name = msgBean.get(position).getContactname();
            final String phone = msgBean.get(position).getPhone();
            String bigadr = msgBean.get(position).getBigadr();
            String detailadr = msgBean.get(position).getDetailadr();

            //删除收货地址参数
            final String id = msgBean.get(position).getId();

            vh.name.setText(name);
            vh.phone.setText(phone);
            vh.bigadr.setText(bigadr);
            vh.detailadr.setText(detailadr);


            final String contactname = msgBean.get(position).getContactname();
            final String my_address = msgBean.get(position).getAddress();
            final String lat = msgBean.get(position).getLat();
            final String lng = msgBean.get(position).getLng();

            return convertView;
        }

        public class ViewHolder {
            TextView name, phone, bigadr, detailadr;//选择地址 详细地址
            CheckBox def_address;
            Button update, delete;
            LinearLayout lin_address_updata, lin_address_list;
        }
    }


    /*地图检索适配器*/
    class PoiAdapter extends BaseAdapter {
        private Context context;
        private List<PoiInfo> pois;

        PoiAdapter(Context context, List<PoiInfo> pois) {
            this.context = context;
            this.pois = pois;
        }

        @Override
        public int getCount() {
            //return pois.size();
            if (pois.size() > 5) {
                return 5;
            } else {
                return pois.size();
            }
        }

        @Override
        public Object getItem(int position) {
            return pois.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PoiAdapter.ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.locationpois_item, null);
                holder = new PoiAdapter.ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (PoiAdapter.ViewHolder) convertView.getTag();
            }
//            if (position == 0) {
//                holder.iv_gps.setImageDrawable(getResources().getDrawable(R.mipmap.gps_grey));
//                holder.locationpoi_name.setTextColor(Color.parseColor("#FF9D06"));
//                holder.locationpoi_address.setTextColor(Color.parseColor("#FF9D06"));
//            }
//            if (position == 0 && linearLayout.getChildCount() < 2) {
//                ImageView imageView = new ImageView(context);
//                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(32, 32);
//                imageView.setLayoutParams(params);
//                imageView.setBackgroundColor(Color.TRANSPARENT);
//                imageView.setImageResource(R.mipmap.gps_grey);
//                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                linearLayout.addView(imageView, 0, params);
//                holder.locationpoi_name.setTextColor(Color.parseColor("#FF5722"));
//            }

            PoiInfo poiInfo = pois.get(position);
            holder.locationpoi_name.setText(poiInfo.name);
            //holder.locationpoi_address.setText(poiInfo.address);
            return convertView;
        }

        class ViewHolder {
            ImageView iv_gps;
            TextView locationpoi_name;
            TextView locationpoi_address;

            ViewHolder(View view) {
                locationpoi_name = (TextView) view.findViewById(R.id.locationpois_name);
                locationpoi_address = (TextView) view.findViewById(R.id.locationpois_address);
                iv_gps = (ImageView) view.findViewById(R.id.iv_gps);

                locationpoi_address.setVisibility(View.GONE);
                iv_gps.setVisibility(View.GONE);
            }
        }
    }
}