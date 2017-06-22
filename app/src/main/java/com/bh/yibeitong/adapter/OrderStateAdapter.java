package com.bh.yibeitong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.bean.OrderDetaile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingang on 2016/11/25.
 * 订单状态 适配器
 */

public class OrderStateAdapter extends BaseAdapter {
    private Context mContext;
    private List<OrderDetaile.MsgBean.StatuslistBean> statuslist = new ArrayList<>();

    public OrderStateAdapter(Context mContext, List<OrderDetaile.MsgBean.StatuslistBean> statuslist) {
        this.mContext = mContext;
        this.statuslist = statuslist;
    }

    @Override
    public int getCount() {
        return statuslist.size();
    }

    @Override
    public Object getItem(int position) {
        return statuslist.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_order_state, null);

            vh.name = (TextView) convertView.findViewById(R.id.tv_item_os_name);
            vh.time = (TextView) convertView.findViewById(R.id.tv_item_os_time);
            vh.miaoshu = (TextView) convertView.findViewById(R.id.tv_item_os_miaoshu);

            convertView.setTag(vh);

        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        String name = statuslist.get(position).getName();
        String time = statuslist.get(position).getTime();
        String miaoshu = statuslist.get(position).getMiaoshu();

        vh.name.setText(name);
        vh.time.setText(time);
        vh.miaoshu.setText(miaoshu);

        return convertView;
    }

    public class ViewHolder {
        TextView name, time, miaoshu;

    }
}
