package com.bh.yibeitong.ui.address;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.Address;
import com.bh.yibeitong.bean.Register;
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
 * Created by jingang on 2016/11/23.
 * 管理收货地址 增删改查
 */

public class ManageAddressActivity extends BaseTextActivity {
    private LinearLayout lin_add_address;

    //显示我的收货地址
    private ListView mlv_address;
    private AddressListAdapter alAdapter;

    private List<Address.MsgBean> msgBeanList = new ArrayList<>();

    private String uid, pwd, page;
    private String phone;

    /*本地存储*/
    UserInfo userInfo;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_manage_address);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("管理收货地址");
        setTitleBack(true, 0);
        initData();

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
     * 组件 初始化
     */
    public void initData() {
        userInfo = new UserInfo(getApplication());

        lin_add_address = (LinearLayout) findViewById(R.id.lin_add_address);
        lin_add_address.setOnClickListener(this);

        mlv_address = (ListView) findViewById(R.id.mlv_address);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.lin_add_address:
                //添加收货地址
                Intent intent = new Intent(ManageAddressActivity.this, AddAddressActivity.class);
                intent.putExtra("title", "添加收货地址");
                startActivityForResult(intent, 2);
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

                        alAdapter = new AddressListAdapter(ManageAddressActivity.this, msgBeanList);
                        mlv_address.setAdapter(alAdapter);

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
        if (requestCode == 2 && resultCode == 3) {
            Bundle bundle = data.getExtras();
            String strResult = bundle.getString("result");

            String contactname = bundle.getString("contactname");
            String phone = bundle.getString("phone");
            String bigadr = bundle.getString("bigadr");
            String detailadr = bundle.getString("detailadr");

            mlv_address.deferNotifyDataSetChanged();


            if (userInfo.getCode().equals("0")) {
                getAddressList(uid, pwd, "1");
            } else {
                getAddressList("phone", phone, "1");
            }

            System.out.println("不知道走没走");

        }

    }

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

                        //alAdapter.notifyDataSetInvalidated();
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

        public void setItemList(List<Address.MsgBean> msgBean) {
            msgBean = msgBean;
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
                vh = new AddressListAdapter.ViewHolder();
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
                vh = (AddressListAdapter.ViewHolder) convertView.getTag();
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


            //选择
            vh.lin_address_list.setOnClickListener(new View.OnClickListener() {
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
                    ManageAddressActivity.this.finish();


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
                    ManageAddressActivity.this.finish();
                    /*String PATH;
                    if (userInfo.getCode().equals("0")) {
                        PATH = HttpPath.PATH + HttpPath.ADDRESS_SETDEF +
                                "uid=" + uid + "&pwd=" + pwd + "&addresid=" + addresid;
                    } else {
                        PATH = HttpPath.PATH + HttpPath.ADDRESS_DEL +
                                "logintype=" + uid + "&loginphone=" + pwd + "&addresid=" + addresid;
                    }

                    RequestParams params = new RequestParams(PATH);
                    x.http().post(params,
                            new Callback.CommonCallback<String>() {
                                @Override
                                public void onSuccess(String result) {
                                    System.out.println("设置默认"+result);

                                    alAdapter.setItemList(msgBeanList);

                                    //刷新适配信息
                                    //alAdapter.notifyDataSetInvalidated();
                                    alAdapter.notifyDataSetChanged();

                                    ManageAddressActivity.this.finish();

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
                            });*/


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

                    Intent intent = new Intent(ManageAddressActivity.this, AddAddressActivity.class);
                    intent.putExtra("contactname", contactname);
                    intent.putExtra("phone", phone);
                    intent.putExtra("bigadr", bigadr);
                    intent.putExtra("detailadr", detailadr);
                    intent.putExtra("title", "编辑收货地址");

                    intent.putExtra("addresid", addresid);


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


                    CustomDialog.Builder builder = new CustomDialog.Builder(ManageAddressActivity.this);
                    builder.setMessage("确定要删除吗？");
                    //builder.setTitle("还未登录");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //设置你的操作事项
                            //startActivity(new Intent(ManageAddressActivity.this, LoginRegisterActivity.class));

                            String PATH;
                            if (userInfo.getCode().toString().equals("0")) {
                                PATH = HttpPath.PATH + HttpPath.ADDRESS_DEL +
                                        "uid=" + uid + "&pwd=" + pwd + "&addresid=" + id;
                            } else {
                                PATH = HttpPath.PATH + HttpPath.ADDRESS_DEL +
                                        "logintype=" + uid + "&loginphone=" + pwd + "&addresid=" + id;
                            }


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