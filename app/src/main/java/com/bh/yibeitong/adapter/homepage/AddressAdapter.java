package com.bh.yibeitong.adapter.homepage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.bean.Address;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingang on 2017/8/18.
 * 收货地址适配器
 */

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
        //选择
//        vh.lin_address_list.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //同时设置默认地址
//
//                if (userInfo.getCode().toString().equals("0")) {
//                    postDefAddress(uid, pwd, addresid);
//                } else {
//                    postDefAddress("phone", phone, addresid);
//                }
//
//                Intent intent = new Intent();
//                intent.putExtra("result", contactname + "  " + phone + "\n" + my_address);
//
//                intent.putExtra("contactname", contactname);
//                intent.putExtra("phone", phone);
//                intent.putExtra("address", my_address);
//                    /*intent.putExtra("bigadr", bigadr);
//                    intent.putExtra("detailadr", detailadr);*/
//
//                intent.putExtra("lat", lat);
//                intent.putExtra("lng", lng);
//
//            }
//        });

        return convertView;
    }

    public class ViewHolder {
        TextView name, phone, bigadr, detailadr;//选择地址 详细地址
        CheckBox def_address;
        Button update, delete;
        LinearLayout lin_address_updata, lin_address_list;
    }
}
