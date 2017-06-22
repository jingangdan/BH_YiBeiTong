package com.bh.yibeitong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bh.yibeitong.R;

import java.util.List;
import java.util.Map;

/**
 * Created by jingang on 2017/6/19.
 */
public class MyGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<Map<String, Object>> listItems;

    public MyGridViewAdapter(Context mContext, List<Map<String, Object>> listItems) {
        this.mContext = mContext;
        this.listItems = listItems;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int i) {
        return listItems.get(i);
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
            vh.tv_title = (TextView) view.findViewById(R.id.tv_item_gc_title);

            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        vh.imageView.setImageResource((Integer) listItems.get(i).get("image"));

        vh.tv_title.setText((String) listItems.get(i).get("title"));

        return view;
    }

    public class ViewHolder {
        ImageView imageView;
        TextView tv_title;
    }
}