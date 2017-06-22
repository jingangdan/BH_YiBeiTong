package com.bh.yibeitong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.bean.village.Village;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingang on 2016/12/13.
 */

public class VillageAdapter extends BaseAdapter {
    private Context mContext;
    private List<Village.MsgBean.ListBean> listBeen = new ArrayList<>();

    public VillageAdapter(Context mContext, List<Village.MsgBean.ListBean> listBeen){
        this.mContext = mContext;
        this.listBeen = listBeen;
    }
    @Override
    public int getCount() {
        return listBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return listBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if(view == null){
            vh = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_village, null);

            vh.v_name = (TextView) view.findViewById(R.id.tv_item_village_name);

            view.setTag(vh);
        }else{
            vh = (ViewHolder) view.getTag();
        }

        String name = listBeen.get(position).getCatename();

        vh.v_name.setText(name);


        return view;
    }

    public class ViewHolder{
        TextView v_name;

    }
}
