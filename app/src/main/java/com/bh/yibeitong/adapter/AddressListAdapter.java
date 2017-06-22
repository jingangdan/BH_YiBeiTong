package com.bh.yibeitong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.bean.Address;
import com.bh.yibeitong.utils.HttpPath;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingang on 2016/11/23.
 * 我的收货地址 适配器
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
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


            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        String name = msgBean.get(position).getContactname();
        String phone = msgBean.get(position).getPhone();
        String bigadr = msgBean.get(position).getBigadr();
        String detailadr = msgBean.get(position).getDetailadr();

        //删除收货地址参数
        int uid;
        String pwd;

        final String id = msgBean.get(position).getId();

        vh.name.setText(name);
        vh.phone.setText(phone);
        vh.bigadr.setText(bigadr);
        vh.detailadr.setText(detailadr);

        vh.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String PATH = HttpPath.PATH + HttpPath.ADDRESS_DEL +
                        "uid=184" + "&pwd=123456" + "&addresid=" + id;

                RequestParams params = new RequestParams(PATH);
                x.http().post(params,
                        new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                System.out.println("删除收货地址" + result);

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

        return convertView;
    }

    public class ViewHolder {
        TextView name, phone, bigadr, detailadr;//选择地址 详细地址
        CheckBox def_address;
        Button update, delete;
    }
}
