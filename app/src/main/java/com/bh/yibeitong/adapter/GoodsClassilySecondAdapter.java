package com.bh.yibeitong.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.bean.ShopNew;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingang on 2016/11/5.
 * 商品二级分类
 */

public class GoodsClassilySecondAdapter extends BaseAdapter {
    private Context mContext;
    private List<ShopNew.MsgBean.ChildBean> childBeen = new ArrayList<>();
    private int mSelect = 0;

    public GoodsClassilySecondAdapter(Context mContext, List<ShopNew.MsgBean.ChildBean> childBeen){
        this.mContext = mContext;
        this.childBeen = childBeen;
    }

    /**
     * 刷新方法
     * @param positon
     */
    public void changeSelected(int positon){
        if(positon != mSelect){
            mSelect = positon;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return childBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return childBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView == null){
            vh = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_good_classily_second, null);

            vh.name = (TextView) convertView.findViewById(R.id.tv_item_good_classily_second);

            vh.lin_item_good_classily_second = (LinearLayout) convertView.findViewById(R.id.lin_item_good_classily_second);


            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }

        //ListView点击改变背景 文字
        if(mSelect == position){
            //白字 绿背景
            vh.name.setTextColor(Color.rgb(255,255,255));
            vh.lin_item_good_classily_second.setBackgroundResource(R.drawable.linstyle_green);
        }else{
            //灰字白背景
            vh.name.setTextColor(Color.rgb(153, 153, 153));
            vh.lin_item_good_classily_second.setBackgroundResource(R.drawable.linstyle_white);
        }

        String name = childBeen.get(position).getName();
        vh.name.setText(name);


        return convertView;
    }

    public class ViewHolder{
        TextView name;
        LinearLayout lin_item_good_classily_second;
    }
}
