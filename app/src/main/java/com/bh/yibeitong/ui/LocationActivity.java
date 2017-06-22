package com.bh.yibeitong.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.bh.yibeitong.LocationService;
import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.Address;
import com.bh.yibeitong.bean.Register;
import com.bh.yibeitong.ui.address.AddAddressActivity;
import com.bh.yibeitong.ui.address.ManageAddressActivity;
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
 * Created by jingang on 2016/12/12.
 * 定位选择地址
 */
public class LocationActivity extends BaseTextActivity implements
        OnGetSuggestionResultListener {
    private ListView listView;
    private AddressListAdapter alAdapter;
    private List<Address.MsgBean> msgBeanList = new ArrayList<>();

    private LinearLayout lin_location;

    /*缓存本地*/
    private UserInfo userInfo;
    private String uid, pwd, phone;
    private int page = 1;

    /*经纬度*/
    private String latitude, longitude;

    private List<String> ls_lat;
    private List<String> ls_lng;

    /*定位的城市*/
    private String city_name = "";

    /*输入关键字搜索周边*/
    private AutoCompleteTextView keyWorldsView = null;
    private SuggestionSearch mSuggestionSearch = null;
    private ArrayAdapter<String> sugAdapter = null;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_location);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("选择收货地址");
        setTitleBack(true, 0);

        SDKInitializer.initialize(getApplicationContext());
        initData();

        getLocation();

        if (userInfo.getUserInfo().equals("")) {
            toast("请先登录");
        } else {
            Register register = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), Register.class);
            phone = register.getMsg().getPhone();

            uid = register.getMsg().getUid();

            pwd = userInfo.getPwd();
            if (userInfo.getCode().equals("0")) {
                getAddressList(uid, pwd, "1");
            } else {
                getAddressList("phone", phone, "1");
            }
        }

    }

    /**
     * 组件初始化
     */
    public void initData() {
        userInfo = new UserInfo(getApplication());

        listView = (ListView) findViewById(R.id.lv_location_address);
        lin_location = (LinearLayout) findViewById(R.id.lin_location);
        lin_location.setOnClickListener(this);

        keyWorldsView = (AutoCompleteTextView) findViewById(R.id.actv_search);

        mSuggestionSearch = SuggestionSearch.newInstance();

        mSuggestionSearch.setOnGetSuggestionResultListener(this);

        sugAdapter = new ArrayAdapter<String>(LocationActivity.this, android.R.layout.simple_dropdown_item_1line);
        keyWorldsView.setAdapter(sugAdapter);
        /**
         * 当输入关键字变化时，动态更新建议列表
         */

        keyWorldsView.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {

               // String city = "临沂";
                /**
                 * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
                 */
                mSuggestionSearch.requestSuggestion((new SuggestionSearchOption()).
                        keyword(cs.toString()).city(city_name));

                System.out.println("city = "+city_name);

                /*if(cs.length() > 0){
                    String city = "临沂";
                    *//**
                     * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
                     *//*
                    mSuggestionSearch.requestSuggestion((new SuggestionSearchOption()).
                            keyword(cs.toString()).city(city));
                }else{
                    return;
                }*/
                /*if (cs.length() <= 0) {
                    return;
                }
                String city = "临沂";
                *//**
                 * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
                 *//*
                mSuggestionSearch.requestSuggestion((new SuggestionSearchOption()).
                        keyword(cs.toString()).city(city));

//                mSuggestionSearch.requestSuggestion((new SuggestionSearchOption()).
//                        keyword(cs.toString()));*/

            }
        });
        keyWorldsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String address = arg0.getItemAtPosition(arg2).toString();

                Intent intentAddress = new Intent();
                intentAddress.putExtra("selectAddress", keyWorldsView.getText().toString());
                intentAddress.putExtra("lat", ls_lat.get(arg2).toString());
                intentAddress.putExtra("lng", ls_lng.get(arg2).toString());

                setResult(2, intentAddress);
                finish();
            }
        });

    }

    @Override
    public void onGetSuggestionResult(SuggestionResult res) {
        ls_lat = new ArrayList<>();
        ls_lng = new ArrayList<>();
        if (res == null || res.getAllSuggestions() == null) {
            return;
        }
        sugAdapter.clear();
        ls_lat.clear();
        ls_lng.clear();
        for (int i = 0; i < res.getAllSuggestions().size(); i++){
            if(!(null == res.getAllSuggestions().get(i).pt)){
                System.out.println("pt = "+res.getAllSuggestions().get(i).pt);
                System.out.println("key = "+res.getAllSuggestions().get(i).key);
                sugAdapter.add(res.getAllSuggestions().get(i).key);

                ls_lat.add(""+res.getAllSuggestions().get(i).pt.latitude);
                ls_lng.add(""+res.getAllSuggestions().get(i).pt.longitude);
            }


        }
        /*for (SuggestionResult.SuggestionInfo info : res.getAllSuggestions()) {
            if (info.key != null)
                //sugAdapter.add(info.key + " " + info.city + info.district);
                sugAdapter.add(info.key + info.pt);

            *//*System.out.println("lat = "+info.pt.latitude);
            System.out.println("lng = "+info.pt.longitude);*//*
        }*/

        //System.out.println("lat = "+res.getAllSuggestions().get(0).pt.latitude);

        sugAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.lin_location:
                //定位

                getLocation();

                Intent intent = new Intent();


                intent.putExtra("lat", latitude);
                intent.putExtra("lng", longitude);

                setResult(2, intent);// 设置resultCode，onActivityResult()中能获取到
                LocationActivity.this.finish();

                break;


            default:
                break;
        }
    }

    /**
     * 获取收货地址
     *
     * @param uid
     * @param pwd
     * @param page 页码
     */
    public void getAddressList(String uid, String pwd, String page) {
        String PATH = "";
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

                        final Address address = GsonUtil.gsonIntance().gsonToBean(result, Address.class);

                        msgBeanList = address.getMsg();

                        System.out.println("我的收货地址" + address);

                        alAdapter = new AddressListAdapter(LocationActivity.this, msgBeanList);
                        listView.setAdapter(alAdapter);

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
     * 显示请求字符串
     */
    public void logMsg(String string) {

    }

    private LocationService locationService;

    /**
     * 开启定位
     */
    public void getLocation() {
        super.onStart();

        locationService = new LocationService(getApplicationContext());
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        locationService.start();

    }

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
                longitude = Double.toString(location.getLongitude());

                sb.append("\nradius : ");// 半径
                sb.append(location.getRadius());
                sb.append("\nCountryCode : ");// 国家码
                sb.append(location.getCountryCode());
                sb.append("\nCountry : ");// 国家名称
                sb.append(location.getCountry());
                sb.append("\ncitycode : ");// 城市编码
                sb.append(location.getCityCode());
                sb.append("\ncity : ");// 城市
                city_name = location.getCity();

                sb.append(location.getCity());
                sb.append("\nDistrict : ");// 区
                sb.append(location.getDistrict());
                sb.append("\nStreet : ");// 街道
                sb.append(location.getStreet());
                sb.append("\naddr : ");// 地址信息
                sb.append(location.getAddrStr());
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

                //logMsg(sb.toString());

                /*Intent intent = new Intent();


                intent.putExtra("lat", latitude);
                intent.putExtra("lng", longitude);

                setResult(2, intent);// 设置resultCode，onActivityResult()中能获取到
                LocationActivity.this.finish();*/

            }
        }

    };

    /**
     * 设置默认地址
     *
     * @param uid
     * @param pwd
     * @param addresid
     */
    public void postDefAddress(String uid, String pwd, String addresid) {
        String PATH;
        if (userInfo.getCode().equals("0")) {
            PATH = HttpPath.PATH + HttpPath.ADDRESS_SETDEF +
                    "uid=" + uid + "&pwd=" + pwd + "&addresid=" + addresid;
        } else {
            PATH = HttpPath.PATH + HttpPath.ADDRESS_SETDEF +
                    "logintype=" + uid + "&loginphone=" + pwd + "&addresid=" + addresid;
        }

        RequestParams params = new RequestParams(PATH);
        x.http().post(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("设置默认" + result);

                        //msgBeanList.addAll(msgBean);

                        alAdapter.notifyDataSetInvalidated();

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
     * Activity中定义adapter
     */
    public class AddressListAdapter extends BaseAdapter {
        private Context mContext;
        private List<Address.MsgBean> msgBean = new ArrayList<>();

        public AddressListAdapter(Context mContext, List<Address.MsgBean> msgBean) {
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

                vh.lin_address_list = (LinearLayout) convertView.findViewById(R.id.lin_address_list);

                vh.cb_item_address = (CheckBox) convertView.findViewById(R.id.cb_item_address);


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
            //String phone = msgBean.get(position).getPhone();
            final String my_address = msgBean.get(position).getAddress();

            final String lat = msgBean.get(position).getLat();

            final String lng = msgBean.get(position).getLng();
            //选择
            vh.lin_address_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //同时设置默认地址

                    if (userInfo.getCode().toString().equals("0")) {
                        postDefAddress(uid, pwd, addresid);
                    } else {
                        postDefAddress("phone", phone, addresid);
                    }

                    Intent intent = new Intent();
                    intent.putExtra("result", contactname + "  " + phone + "\n" + my_address);

                    intent.putExtra("contactname", contactname);
                    intent.putExtra("phone", phone);
                    intent.putExtra("address", my_address);
                    /*intent.putExtra("bigadr", bigadr);
                    intent.putExtra("detailadr", detailadr);*/

                    intent.putExtra("lat", lat);
                    intent.putExtra("lng", lng);

                    setResult(2, intent);// 设置resultCode，onActivityResult()中能获取到
                    LocationActivity.this.finish();


                }
            });

            if (msgBeanList.get(position).getDefaultX().toString().equals("1")) {
                vh.cb_item_address.setChecked(true);
            } else {
                vh.cb_item_address.setChecked(false);
            }


            //默认地址
            vh.cb_item_address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (userInfo.getCode().equals("0")) {
                        postDefAddress(uid, pwd, addresid);

                    } else {
                        postDefAddress("phone", phone, addresid);
                    }

                    Intent intent = new Intent();
                    intent.putExtra("result", contactname + "  " + phone + "\n" + my_address);

                    intent.putExtra("contactname", contactname);
                    intent.putExtra("phone", phone);
                    intent.putExtra("address", my_address);
                    /*intent.putExtra("bigadr", bigadr);
                    intent.putExtra("detailadr", detailadr);*/

                    intent.putExtra("lat", lat);
                    intent.putExtra("lng", lng);

                    setResult(2, intent);// 设置resultCode，onActivityResult()中能获取到
                    LocationActivity.this.finish();
                }
            });

            //编辑
            vh.update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String contactname = msgBean.get(position).getContactname();
                    String phone = msgBean.get(position).getPhone();
                    String bigadr = msgBean.get(position).getBigadr();
                    String detailadr = msgBean.get(position).getDetailadr();

                    String lat = msgBean.get(position).getLat();
                    String lng = msgBean.get(position).getLng();

                    String addresid = msgBean.get(position).getId();

                    Intent intent = new Intent(LocationActivity.this, AddAddressActivity.class);
                    intent.putExtra("contactname", contactname);
                    intent.putExtra("phone", phone);
                    intent.putExtra("bigadr", bigadr);
                    intent.putExtra("detailadr", detailadr);
                    intent.putExtra("title", "编辑收货地址");


                    intent.putExtra("lat", lat);
                    intent.putExtra("lng", lng);

                    intent.putExtra("addresid", addresid);

                    startActivityForResult(intent, 2);
                }
            });


            //删除
            vh.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CustomDialog.Builder builder = new CustomDialog.Builder(LocationActivity.this);
                    builder.setMessage("确定要删除吗？");
                    //builder.setTitle("还未登录");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //设置你的操作事项
                            //startActivity(new Intent(LocationActivity.this, LoginRegisterActivity.class));

                            String PATH = HttpPath.PATH + HttpPath.ADDRESS_DEL +
                                    "uid=" + uid + "&pwd=" + pwd + "&addresid=" + id;

                            RequestParams params = new RequestParams(PATH);
                            x.http().post(params,
                                    new Callback.CommonCallback<String>() {
                                        @Override
                                        public void onSuccess(String result) {
                                            System.out.println("删除收货地址" + result);

                                            msgBeanList.remove(position);

                                            alAdapter.notifyDataSetChanged();

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

                    builder.setNegativeButton("取消",
                            new android.content.DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    builder.create().show();

                }
            });


            return convertView;
        }

        public class ViewHolder {
            TextView name, phone, bigadr, detailadr;//选择地址 详细地址
            CheckBox def_address;
            Button update, delete;

            CheckBox cb_item_address;

            LinearLayout lin_address_list;
        }
    }

}