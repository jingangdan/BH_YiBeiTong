package com.bh.yibeitong.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bh.yibeitong.bean.CeShi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by binbin on 2016/11/16.
 */

public class CeShiAdapter extends BaseAdapter {
    private List<CeShi.MsgBean.CatefoodslistBean> catefoodslistBeanList = new ArrayList<>();
    private Context mContext;

    public CeShiAdapter(){

    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
