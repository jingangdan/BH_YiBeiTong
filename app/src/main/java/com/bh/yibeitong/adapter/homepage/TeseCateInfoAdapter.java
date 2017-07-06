package com.bh.yibeitong.adapter.homepage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bh.yibeitong.R;

/**
 * Created by jingang on 2017/6/29.
 */

public class TeseCateInfoAdapter extends BaseAdapter {
    private Context mContext;

    public TeseCateInfoAdapter(Context mContext){
        this.mContext = mContext;
    }


    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if(view == null){
            vh = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_self_support, null);

            vh.img = (ImageView) view.findViewById(R.id.iv_item_ss);
            vh.add = (ImageView) view.findViewById(R.id.iv_add);
            vh.sub = (ImageView) view.findViewById(R.id.iv_sub);
            vh.name = (TextView) view.findViewById(R.id.tv_item_ss_title);
            vh.cost = (TextView) view.findViewById(R.id.tv_item_ss_price);
            vh.num = (TextView) view.findViewById(R.id.tv_shop_num);

            view.setTag(vh);
        }else{
            vh = (ViewHolder) view.getTag();
        }
        vh.img.setImageResource(R.mipmap.yibeitong001);

        vh.name.setText("测试商品");
        vh.cost.setText("100.00");

        return view;
    }
    public class ViewHolder{
        ImageView img, add, sub;
        TextView name, cost, num;

    }
}
