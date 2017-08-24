package com.bh.yibeitong.ui.address;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.bh.yibeitong.LocationService;
import com.bh.yibeitong.R;
import com.bh.yibeitong.adapter.location.PoiSearchAdapter;
import com.bh.yibeitong.application.CatchExcep;

import java.util.List;

/**
 * Created by jingang on 2017/7/25.
 * 定位店铺
 */
public class MoreAddressActivity extends Activity implements
        View.OnClickListener,
        BaiduMap.OnMapStatusChangeListener,
        BDLocationListener,
        OnGetGeoCoderResultListener,
        OnGetPoiSearchResultListener, OnGetSuggestionResultListener {
    private ImageView iv_left;
    protected static final String TAG = "MoreAddressActivity";
    private ListView lv_near_address;
    private BaiduMap mBaiduMap = null;

    private MapView map;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    //private LocationClient mLocClient;
    private LocationService mLocClient;
    private GeoCoder geoCoder;
    private String city;
    private boolean isFirstLoc = true;
    private LatLng locationLatLng;

    /*建议搜索周边*/
    private PoiSearch mPoiSearch = null;
    private SuggestionSearch mSuggestionSearch = null;
    private ListView listView;
    private PoiSearchAdapter adapter;

    /*搜索关键字入口*/
    private AutoCompleteTextView keyWorldsView = null;
    private ArrayAdapter<String> sugAdapter = null;
    private int load_Index = 0;
    private static StringBuilder sb;

    /*建议周边  地图周边*/
    private LinearLayout lin_auto, lin_map;

    /*定位成功 搜索关键字*/
    EditText editCity;
    EditText editSearchKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_moreaddress);

        lin_auto = (LinearLayout) findViewById(R.id.lin_poi_auto);
        lin_map = (LinearLayout) findViewById(R.id.lin_poi_map);

        initDatas();

        initData();
    }

    /*建议搜索*/
    public void initDatas() {
        // 初始化搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);
        keyWorldsView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        sugAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line);
        keyWorldsView.setAdapter(sugAdapter);
//		mBaiduMap = ((SupportMapFragment) (getSupportFragmentManager()
//				.findFragmentById(R.id.map))).getBaiduMap();

        listView = (ListView) findViewById(R.id.lv_poi_address);

        editCity = (EditText) findViewById(R.id.edit_address_city);
        editSearchKey = (EditText) findViewById(R.id.autoCompleteTextView);

        /**
         * 当输入关键字变化时，动态更新建议列表
         */
        keyWorldsView.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {

            }

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2,
                                      int arg3) {
//                if (cs.length() <= 0) {
//                    return;
//                }
//                String city = ((EditText) findViewById(R.id.edit_address_city)).getText()
//                        .toString();
//                /**
//                 * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
//                 */
//                mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
//                        .keyword(cs.toString()).city(city));

                System.out.println("length = " + cs.length());

                if (cs.length() <= 0) {
                    //地图显示
                    lin_map.setVisibility(View.VISIBLE);
                    lin_auto.setVisibility(View.GONE);
                } else {
                    //地图被覆盖
                    lin_map.setVisibility(View.GONE);
                    lin_auto.setVisibility(View.VISIBLE);
                }


                searchButtonProcess(null);
            }
        });
    }

    /**
     * 影响搜索按钮点击事件
     * 点击搜索
     *
     * @param v
     */
    public void searchButtonProcess(View v) {

        mPoiSearch.searchInCity((new PoiCitySearchOption())
                .city(editCity.getText().toString())
                .keyword(editSearchKey.getText().toString())
                .pageNum(load_Index));
        sb = new StringBuilder();
    }

    @Override
    public void onGetPoiResult(PoiResult result) {
        if (result == null
                || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            Toast.makeText(MoreAddressActivity.this, "未找到结果", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
//			mBaiduMap.clear();
//			PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
//			mBaiduMap.setOnMarkerClickListener(overlay);
//			overlay.setData(result);
//			overlay.addToMap();
//			overlay.zoomToSpan();
            final List<PoiInfo> ppp = result.getAllPoi();
            if (ppp != null && ppp.size() != 0) {
                for (PoiInfo poiInfo : ppp) {
                    Log.i("yxx", "==1=poi===城市：" + poiInfo.city + "名字：" + poiInfo.name + "地址：" + poiInfo.address + "" + poiInfo.location.longitude);
                }
                adapter = new PoiSearchAdapter(this, ppp);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        Log.i("点击", ""+ppp.get(i).location.longitude+"\n"+ppp.get(i).location.latitude);
//                        Intent intentAddress = new Intent();
//                        intentAddress.putExtra("lat", ""+ppp.get(i).location.latitude);
//                        intentAddress.putExtra("lng", ""+ppp.get(i).location.longitude);
//                        setResult(2, intentAddress);
//
                        Intent intentAddress = new Intent();
                        intentAddress.putExtra("selectAddress", ppp.get(i).name);
                        intentAddress.putExtra("lat", ""+ppp.get(i).location.latitude);
                        intentAddress.putExtra("lng", ""+ppp.get(i).location.longitude);
                        setResult(4, intentAddress);

                        MoreAddressActivity.this.finish();
                    }
                });
            }

            return;
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

            // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
            String strInfo = "在";
            for (CityInfo cityInfo : result.getSuggestCityList()) {
                strInfo += cityInfo.city;
                strInfo += ",";
            }
            strInfo += "找到结果";
            Toast.makeText(MoreAddressActivity.this, strInfo, Toast.LENGTH_LONG)
                    .show();
        }
    }

    public void onGetPoiDetailResult(PoiDetailResult result) {
        if (result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(MoreAddressActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Log.i("yxx", "==2=poi===" + result.getName() + ": " + result.getAddress());
        }
    }

    @Override
    public void onGetSuggestionResult(SuggestionResult res) {
        if (res == null || res.getAllSuggestions() == null) {
            return;
        }
        sugAdapter.clear();
        for (SuggestionResult.SuggestionInfo info : res.getAllSuggestions()) {
            if (info.key != null)
                sugAdapter.add(info.key);
        }
        sugAdapter.notifyDataSetChanged();
    }

    private class MyPoiOverlay extends PoiOverlay {

        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            PoiInfo poi = getPoiResult().getAllPoi().get(index);
            // if (poi.hasCaterDetails) {
            mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
                    .poiUid(poi.uid));
            // }
            return true;
        }
    }


    /*地图检索*/
    private void initData() {
        map = (MapView) findViewById(R.id.map);
        mBaiduMap = map.getMap();
        MapStatus mapStatus = new MapStatus.Builder().zoom(15).build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);
        // 地图状态改变相关监听
        mBaiduMap.setOnMapStatusChangeListener(this);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位图层显示方式
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, null));
        mLocClient = new LocationService(this);
        //mLocClient = ((CatchExcep) getApplication()).locationService;
        // 注册定位监听
        //mLocClient.registerLocationListener(this);
        mLocClient.registerListener(this);

        // 定位选项
//        LocationClientOption option = new LocationClientOption();
//
//        /*
//        coorType - 取值有3个：
//        返回国测局经纬度坐标系：gcj02
//         返回百度墨卡托坐标系 ：bd09
//         返回百度经纬度坐标系：bd09ll
//        */
//        option.setCoorType("bd09ll");
//        // 设置是否需要地址信息，默认为无地址
//        option.setIsNeedAddress(true);
//        // 设置是否需要返回位置语义化信息，可以在BDLocation.getLocationDescribe()中得到数据，ex:"在天安门附近"，
//        // 可以用作地址信息的补充
//        option.setIsNeedLocationDescribe(true);
//        // 设置是否需要返回位置POI信息，可以在BDLocation.getPoiList()中得到数据
//        option.setIsNeedLocationPoiList(true);
//
//       /* 设置定位模式 Battery_Saving 低功耗模式 Device_Sensors 仅设备(Gps) 模式 Hight_Accuracy
//        高精度模式*/
//
//        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//        // 设置是否打开gps进行定位
//        option.setOpenGps(true);
//        // 设置扫描间隔，单位是毫秒 当<1000(1s)时，定时定位无效
//        option.setScanSpan(1000);
//        // 设置 LocationClientOption
//        mLocClient.setLocOption(option);

        mLocClient.setLocationOption(mLocClient.getDefaultLocationClientOption());
        // 开始定位
        mLocClient.start();
        lv_near_address = (ListView) findViewById(R.id.lv_near_address);

        iv_left = (ImageView) findViewById(R.id.iv_address_back);
        iv_left.setOnClickListener(this);
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
            case R.id.iv_address_back:
                MoreAddressActivity.this.finish();
                break;

            default:
                break;
        }
    }

    public void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mPoiSearch.destroy();
        mSuggestionSearch.destroy();
        super.onDestroy();
    }


    /**/
    @Override
    public void onMapStatusChange(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus) {
        // 地图操作的中心点
        LatLng cenpt = mapStatus.target;
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(cenpt));
    }

    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {

    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        // 如果bdLocation为空或mapView销毁后不再处理新数据接收的位置
        if (bdLocation == null || mBaiduMap == null) {
            return;
        }

        // 定位数据
        MyLocationData data = new MyLocationData.Builder()
                // 定位精度bdLocation.getRadius()
                .accuracy(bdLocation.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(bdLocation.getDirection())
                // 经度
                .latitude(bdLocation.getLatitude())
                // 纬度
                .longitude(bdLocation.getLongitude())
                // 构建
                .build();

        // 设置定位数据
        mBaiduMap.setMyLocationData(data);

        // 是否是第一次定位
        if (isFirstLoc) {
            isFirstLoc = false;
            LatLng ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
            MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(ll, 18);
            mBaiduMap.animateMapStatus(msu);
        }

        locationLatLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
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
        geoCoder.setOnGetGeoCodeResultListener(this);

        editCity.setText("" + city);

        mLocClient.stop();//停止定位
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult arg0) {

    }

    // 拿到变化地点后的附近地址
    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        final List<PoiInfo> poiInfos = reverseGeoCodeResult.getPoiList();
        mLocClient.stop();//停止定位
        Log.i(TAG, "这里的值:" + poiInfos);

        if (poiInfos != null && !"".equals(poiInfos)) {
            final PoiAdapter poiAdapter = new PoiAdapter(MoreAddressActivity.this, poiInfos);
            lv_near_address.setAdapter(poiAdapter);
            lv_near_address.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String name = poiInfos.get(position).name.toString();
                    double latitude = poiInfos.get(position).location.latitude;
                    double longitude = poiInfos.get(position).location.longitude;

                    Intent intent = new Intent();
                    intent.putExtra("selectAddress", name);
                    intent.putExtra("lat", String.valueOf(latitude));
                    intent.putExtra("lng", String.valueOf(longitude));
                    setResult(4, intent);
                    MoreAddressActivity.this.finish();

//                    Intent intentAddress = new Intent();
//                    intentAddress.putExtra("lat", String.valueOf(latitude));
//                    intentAddress.putExtra("lng", String.valueOf(longitude));
//
//                    setResult(2, intentAddress);
//                    MoreAddressActivity.this.finish();
                }
            });
        }
    }


    /*地图检索适配器*/
    class PoiAdapter extends BaseAdapter {
        private Context context;
        private List<PoiInfo> pois;
        private LinearLayout linearLayout;

        PoiAdapter(Context context, List<PoiInfo> pois) {
            this.context = context;
            this.pois = pois;
        }

        @Override
        public int getCount() {
            return pois.size();
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
                linearLayout = (LinearLayout) convertView.findViewById(R.id.locationpois_linearlayout);
                holder = new PoiAdapter.ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (PoiAdapter.ViewHolder) convertView.getTag();
            }
            if (position == 0) {
                holder.iv_gps.setImageDrawable(getResources().getDrawable(R.mipmap.gps_grey));
                holder.locationpoi_name.setTextColor(Color.parseColor("#FF9D06"));
                holder.locationpoi_address.setTextColor(Color.parseColor("#FF9D06"));
            }
            if (position == 0 && linearLayout.getChildCount() < 2) {
                ImageView imageView = new ImageView(context);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(32, 32);
                imageView.setLayoutParams(params);
                imageView.setBackgroundColor(Color.TRANSPARENT);
                imageView.setImageResource(R.mipmap.gps_grey);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                linearLayout.addView(imageView, 0, params);
                holder.locationpoi_name.setTextColor(Color.parseColor("#FF5722"));
            }
            PoiInfo poiInfo = pois.get(position);
            holder.locationpoi_name.setText(poiInfo.name);
            holder.locationpoi_address.setText(poiInfo.address);
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
            }
        }
    }
}


//package com.bh.yibeitong.ui.address;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ArrayAdapter;
//import android.widget.AutoCompleteTextView;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.baidu.location.BDLocation;
//import com.baidu.location.BDLocationListener;
//import com.baidu.location.LocationClient;
//import com.baidu.location.LocationClientOption;
//import com.baidu.mapapi.SDKInitializer;
//import com.baidu.mapapi.map.BaiduMap;
//import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
//import com.baidu.mapapi.map.MapStatus;
//import com.baidu.mapapi.map.MapStatusUpdate;
//import com.baidu.mapapi.map.MapStatusUpdateFactory;
//import com.baidu.mapapi.map.MapView;
//import com.baidu.mapapi.map.MyLocationConfiguration;
//import com.baidu.mapapi.map.MyLocationData;
//import com.baidu.mapapi.model.LatLng;
//import com.baidu.mapapi.search.core.PoiInfo;
//import com.baidu.mapapi.search.core.SearchResult;
//import com.baidu.mapapi.search.geocode.GeoCodeResult;
//import com.baidu.mapapi.search.geocode.GeoCoder;
//import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
//import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
//import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
//import com.baidu.mapapi.search.poi.PoiDetailResult;
//import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
//import com.baidu.mapapi.search.sug.SuggestionResult;
//import com.baidu.mapapi.search.sug.SuggestionSearch;
//import com.baidu.mapapi.search.sug.SuggestionSearchOption;
//import com.bh.yibeitong.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MoreAddressActivity extends Activity implements OnClickListener, OnGetSuggestionResultListener,
//        OnMapStatusChangeListener, BDLocationListener, OnGetGeoCoderResultListener {
//    private ImageView iv_left;
//    protected static final String TAG = "MoreAddressActivity";
//    protected static final int REQUEST_OK = 0;
//    private ListView lv_near_address;
//    private SuggestionSearch mSuggestionSearch = null;
//    private BaiduMap mBaiduMap = null;
//
//    private String latitude, longitude;
//
//    private List<String> ls_lat;
//    private List<String> ls_lng;
//
//    /*定位的城市名称*/
//    private String city_name = "";
//
//    /*搜索关键字输入窗口*/
//    private AutoCompleteTextView keyWorldsView = null;
//    private ArrayAdapter<String> sugAdapter = null;
//    private int load_Index = 0;
//    private PoiSearchAdapter adapter;
//    private MapView map;
//    private MyLocationConfiguration.LocationMode mCurrentMode;
//    private LocationClient mLocClient;
//    private GeoCoder geoCoder;
//    private String city;
//    private boolean isFirstLoc = true;
//    private LatLng locationLatLng;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        SDKInitializer.initialize(getApplicationContext());
//        setContentView(R.layout.activity_moreaddress);
//        initData();
//        // setStatusBarColor();
//    }
//
//    private void initData() {
//        map = (MapView) findViewById(R.id.map);
//        mBaiduMap = map.getMap();
//        MapStatus mapStatus = new MapStatus.Builder().zoom(15).build();
//        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
//        mBaiduMap.setMapStatus(mMapStatusUpdate);
//        // 地图状态改变相关监听
//        mBaiduMap.setOnMapStatusChangeListener(this);
//        // 开启定位图层
//        mBaiduMap.setMyLocationEnabled(true);
//        // 定位图层显示方式
//        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
//        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, null));
//        mLocClient = new LocationClient(this);
//        // 注册定位监听
//        mLocClient.registerLocationListener(this);
//        // 定位选项
//        LocationClientOption option = new LocationClientOption();
//
//        /*
//        coorType - 取值有3个：
//        返回国测局经纬度坐标系：gcj02
//         返回百度墨卡托坐标系 ：bd09
//         返回百度经纬度坐标系：bd09ll
//        */
//        option.setCoorType("bd09ll");
//        // 设置是否需要地址信息，默认为无地址
//        option.setIsNeedAddress(true);
//        // 设置是否需要返回位置语义化信息，可以在BDLocation.getLocationDescribe()中得到数据，ex:"在天安门附近"，
//        // 可以用作地址信息的补充
//        option.setIsNeedLocationDescribe(true);
//        // 设置是否需要返回位置POI信息，可以在BDLocation.getPoiList()中得到数据
//        option.setIsNeedLocationPoiList(true);
//
//       /* 设置定位模式 Battery_Saving 低功耗模式 Device_Sensors 仅设备(Gps) 模式 Hight_Accuracy
//        高精度模式*/
//
//
//        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//        // 设置是否打开gps进行定位
//        option.setOpenGps(true);
//        // 设置扫描间隔，单位是毫秒 当<1000(1s)时，定时定位无效
//        option.setScanSpan(1000);
//        // 设置 LocationClientOption
//        mLocClient.setLocOption(option);
//        // 开始定位
//        mLocClient.start();
//        lv_near_address = (ListView) findViewById(R.id.lv_near_address);
//        // 初始化搜索模块，注册搜索事件监听
//        mSuggestionSearch = SuggestionSearch.newInstance();
//        mSuggestionSearch.setOnGetSuggestionResultListener(this);
//        keyWorldsView = (AutoCompleteTextView) findViewById(R.id.et_search);
//        sugAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);
//        keyWorldsView.setAdapter(sugAdapter);
//
//        /*当输入关键字变化时，动态更新建议列表*/
//        keyWorldsView.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void afterTextChanged(Editable arg0) {
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
//                if (cs.length() <= 0) {
//                    return;
//                }
//               // String city = "临沂";
//
//                /*使用建议搜索服务获取建议列表，结果在onSuggestionResult() 中更新*/
//                mSuggestionSearch.requestSuggestion((new SuggestionSearchOption()).keyword(cs.toString()).city(city));
//            }
//        });
//
//        keyWorldsView.setOnItemClickListener(new OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                String address = arg0.getItemAtPosition(arg2).toString();
//
//                Intent intentAddress = new Intent();
//                intentAddress.putExtra("selectAddress", keyWorldsView.getText().toString());
//                intentAddress.putExtra("lat", ls_lat.get(arg2).toString());
//                intentAddress.putExtra("lng", ls_lng.get(arg2).toString());
//                setResult(4, intentAddress);
//
//                finish();
//            }
//        });
//        iv_left = (ImageView) findViewById(R.id.iv_address_back);
//        iv_left.setOnClickListener(this);
//    }
//
//   /* 接受周边地理位置结果
//
//    @param
//    poiResult*/
//
//
//    @Override
//
//    protected void onDestroy() {
//        super.onDestroy();
//        mLocClient.stop();
//        mBaiduMap.setMyLocationEnabled(false);
//        map.onDestroy();
//        if (geoCoder != null) {
//            geoCoder.destroy();
//        }
//        map = null;
//        mSuggestionSearch.destroy();
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.iv_address_back:
//                finish();
//                break;
//
//            default:
//                break;
//        }
//    }
//
//    // public void onResume() {
//    // super.onResume();
//    // map.onResume();
//    // if (getIntent().getStringExtra("latitude") != null &&
//    // (!"".equals(getIntent().getStringExtra("latitude")))) {
//    // latitude = Double.parseDouble(getIntent().getStringExtra("latitude"));
//    // longitude = Double.parseDouble(getIntent().getStringExtra("longitude"));
//    // TtelifeLog.i(TAG, "latitude" + latitude);
//    // }
//    // MobclickAgent.onResume(this);
//    // }
//
//    public void onPause() {
//        super.onPause();
//        map.onPause();
//    }
//
//    // private void setStatusBarColor() {
//    // Window window = getWindow();
//    // WindowManager.LayoutParams params = getWindow().getAttributes();
//    // params.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
//    // window.setAttributes(params);
//    // SystemBarTintManager tintManager = new SystemBarTintManager(this);
//    // tintManager.setStatusBarTintEnabled(true);
//    // tintManager.setTintColor(getResources().getColor(R.color.tab));
//    // }
//
//    public void onGetPoiDetailResult(PoiDetailResult result) {
//        if (result.error != SearchResult.ERRORNO.NO_ERROR) {
//            Toast.makeText(MoreAddressActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(MoreAddressActivity.this, result.getName() + ": " + result.getAddress(), Toast.LENGTH_SHORT)
//                    .show();
//        }
//    }
//
//    @Override
//    public void onGetSuggestionResult(SuggestionResult res) {
//        ls_lat = new ArrayList<>();
//        ls_lng = new ArrayList<>();
//        if (res == null || res.getAllSuggestions() == null) {
//            return;
//        }
//        sugAdapter.clear();
//        ls_lat.clear();
//        ls_lng.clear();
//        for (int i = 0; i < res.getAllSuggestions().size(); i++) {
//            if (!(null == res.getAllSuggestions().get(i).pt)) {
//                System.out.println("pt = " + res.getAllSuggestions().get(i).pt);
//                System.out.println("key = " + res.getAllSuggestions().get(i).key);
//                sugAdapter.add(res.getAllSuggestions().get(i).key);
//
//                ls_lat.add("" + res.getAllSuggestions().get(i).pt.latitude);
//                ls_lng.add("" + res.getAllSuggestions().get(i).pt.longitude);
//            }
//
//
//        }
//
//        /*if (res == null || res.getAllSuggestions() == null) {
//            return;
//        }
//        sugAdapter.clear();
//        for (SuggestionResult.SuggestionInfo info : res.getAllSuggestions()) {
//            if (info.key != null)
//                sugAdapter.add(info.key + " " + info.city + info.district);
//        }
//        sugAdapter.notifyDataSetChanged();*/
//    }
//
//    class PoiSearchAdapter extends BaseAdapter {
//
//        private Context context;
//        private List<PoiInfo> list;
//        private ViewHolder holder;
//
//        public PoiSearchAdapter(Context context, List<PoiInfo> appGroup) {
//            this.context = context;
//            this.list = appGroup;
//        }
//
//        @Override
//        public int getCount() {
//            return list.size();
//        }
//
//        @Override
//        public Object getItem(int location) {
//            return list.get(location);
//        }
//
//        @Override
//        public long getItemId(int arg0) {
//            return arg0;
//        }
//
//        public void addObject(List<PoiInfo> mAppGroup) {
//            this.list = mAppGroup;
//            notifyDataSetChanged();
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//
//            if (convertView == null) {
//                holder = new ViewHolder();
//                convertView = LayoutInflater.from(context).inflate(R.layout.activity_poi_search_item, null);
//                holder.mpoi_name = (TextView) convertView.findViewById(R.id.mpoiNameT);
//                holder.mpoi_address = (TextView) convertView.findViewById(R.id.mpoiAddressT);
//                convertView.setTag(holder);
//            } else {
//                holder = (ViewHolder) convertView.getTag();
//            }
//            holder.mpoi_name.setText(list.get(position).name);
//            holder.mpoi_address.setText(list.get(position).address);
//            // Log.i("yxx", "==1=poi===城市：" + poiInfo.city + "名字：" +
//            // poiInfo.name + "地址：" + poiInfo.address);
//            return convertView;
//        }
//
//        public class ViewHolder {
//            public TextView mpoi_name;// 名称
//            public TextView mpoi_address;// 地址
//
//        }
//
//    }
//
//    @Override
//    public void onMapStatusChange(MapStatus mapStatus) {
//
//    }
//
//    @Override
//    public void onMapStatusChangeFinish(MapStatus mapStatus) {
//        // 地图操作的中心点
//        LatLng cenpt = mapStatus.target;
//        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(cenpt));
//    }
//
//    @Override
//    public void onMapStatusChangeStart(MapStatus mapStatus) {
//
//    }
//
//    @Override
//    public void onReceiveLocation(BDLocation bdLocation) {
//        // 如果bdLocation为空或mapView销毁后不再处理新数据接收的位置
//        if (bdLocation == null || mBaiduMap == null) {
//            return;
//        }
//
//        // 定位数据
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
//
//        // 设置定位数据
//        mBaiduMap.setMyLocationData(data);
//
//        // 是否是第一次定位
//        if (isFirstLoc) {
//            isFirstLoc = false;
//            LatLng ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
//            MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(ll, 18);
//            mBaiduMap.animateMapStatus(msu);
//        }
//
//        locationLatLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
//        // 获取城市，待会用于POISearch
//        city = bdLocation.getCity();
//
//        // 创建GeoCoder实例对象
//        geoCoder = GeoCoder.newInstance();
//        // 发起反地理编码请求(经纬度->地址信息)
//        ReverseGeoCodeOption reverseGeoCodeOption = new ReverseGeoCodeOption();
//        // 设置反地理编码位置坐标
//        reverseGeoCodeOption.location(new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude()));
//        geoCoder.reverseGeoCode(reverseGeoCodeOption);
//
//        // 设置查询结果监听者
//        geoCoder.setOnGetGeoCodeResultListener(this);
//
//        mLocClient.stop();//停止定位
//    }
//
//    @Override
//    public void onGetGeoCodeResult(GeoCodeResult arg0) {
//
//    }
//
//    // 拿到变化地点后的附近地址
//    @Override
//    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
//        final List<PoiInfo> poiInfos = reverseGeoCodeResult.getPoiList();
//        mLocClient.stop();//停止定位
//        Log.i(TAG, "这里的值:" + poiInfos);
//
//        if (poiInfos != null && !"".equals(poiInfos)) {
//            final PoiAdapter poiAdapter = new PoiAdapter(MoreAddressActivity.this, poiInfos);
//            lv_near_address.setAdapter(poiAdapter);
//            lv_near_address.setOnItemClickListener(new OnItemClickListener() {
//
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    String name = poiInfos.get(position).name.toString();
//                    double latitude = poiInfos.get(position).location.latitude;
//                    double longitude = poiInfos.get(position).location.longitude;
//
//                    Intent intent = new Intent();
//                    intent.putExtra("selectAddress", name);
//                    intent.putExtra("lat", String.valueOf(latitude));
//                    intent.putExtra("lng", String.valueOf(longitude));
//                    setResult(4, intent);
//                    finish();
//                }
//            });
//        }
//    }
//
//    class PoiAdapter extends BaseAdapter {
//        private Context context;
//        private List<PoiInfo> pois;
//        private LinearLayout linearLayout;
//
//        PoiAdapter(Context context, List<PoiInfo> pois) {
//            this.context = context;
//            this.pois = pois;
//        }
//
//        @Override
//        public int getCount() {
//            return pois.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return pois.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder holder = null;
//            if (convertView == null) {
//                convertView = LayoutInflater.from(context).inflate(R.layout.locationpois_item, null);
//                linearLayout = (LinearLayout) convertView.findViewById(R.id.locationpois_linearlayout);
//                holder = new ViewHolder(convertView);
//                convertView.setTag(holder);
//            } else {
//                holder = (ViewHolder) convertView.getTag();
//            }
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
//            PoiInfo poiInfo = pois.get(position);
//            holder.locationpoi_name.setText(poiInfo.name);
//            holder.locationpoi_address.setText(poiInfo.address);
//            return convertView;
//        }
//
//        class ViewHolder {
//            ImageView iv_gps;
//            TextView locationpoi_name;
//            TextView locationpoi_address;
//
//            ViewHolder(View view) {
//                locationpoi_name = (TextView) view.findViewById(R.id.locationpois_name);
//                locationpoi_address = (TextView) view.findViewById(R.id.locationpois_address);
//                iv_gps = (ImageView) view.findViewById(R.id.iv_gps);
//            }
//        }
//    }
//}
