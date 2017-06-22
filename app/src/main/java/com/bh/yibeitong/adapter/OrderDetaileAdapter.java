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
 * 订单详情 适配器
 */

public class OrderDetaileAdapter extends BaseAdapter {
    private Context mContext;
    private List<OrderDetaile.MsgBean.GdlistBean> gdlistBeen = new ArrayList<>();

    public OrderDetaileAdapter(Context mContext, List<OrderDetaile.MsgBean.GdlistBean> gdlistBeen) {
        this.mContext = mContext;
        this.gdlistBeen = gdlistBeen;
    }

    @Override
    public int getCount() {
        return gdlistBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return gdlistBeen.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_order_shopcart, null);

            vh.name = (TextView) convertView.findViewById(R.id.tv_item_od_name);
            vh.count = (TextView) convertView.findViewById(R.id.tv_item_od_count);
            vh.cost = (TextView) convertView.findViewById(R.id.tv_item_od_cost);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        String name = gdlistBeen.get(position).getGoodsname();
        String count = gdlistBeen.get(position).getGoodscount();
        String cost = gdlistBeen.get(position).getGoodscost();

        vh.name.setText(name);
        vh.count.setText(count);
        vh.cost.setText(cost);

        return convertView;
    }

    public class ViewHolder {
        TextView name, count, cost;
    }
}
