package com.bh.yibeitong.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.bean.CeShi;
import com.bh.yibeitong.bean.SelfSupport;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingang on 2016/10/20.
 * 自营专区 适配器
 */

public class SelfSupportAdapter extends BaseAdapter{
    /*private Context mContext;
    private List<CeShi> ceShiList = new ArrayList<>();

    public SelfSupportAdapter(Context mContext, List<CeShi> ceShiList){
        this.mContext = mContext;
        this.ceShiList = ceShiList;
    }
    @Override
    public int getCount() {
        return ceShiList.size();
    }

    @Override
    public Object getItem(int position) {
        return ceShiList.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_self_support, null);
            vh.image = (ImageView) convertView.findViewById(R.id.iv_item_ss);
            vh.title = (TextView) convertView.findViewById(R.id.tv_item_ss_title);
            vh.price = (TextView) convertView.findViewById(R.id.tv_item_ss_price);
            vh.num = (TextView) convertView.findViewById(R.id.tv_item_ss_num);



            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }

        BitmapUtils bitmapUtils = new BitmapUtils(mContext);
        bitmapUtils.display(vh.image, ceShiList.get(position).getMsg().getCatefoodslist().get(position).getImg());

        vh.title.setText(ceShiList.get(position).getMsg().getCatefoodslist().get(position).getName());

        vh.price.setText(ceShiList.get(position).getMsg().getCatefoodslist().get(position).getCost());

        vh.num.setText(ceShiList.get(position).getMsg().getCatefoodslist().get(position).getSellcount());


        return convertView;
    }

    public class ViewHolder{
        private ImageView image;
        private TextView title, price, num;
    }
*/


    private Context mContext;

    private List<SelfSupport> selfSupports = new ArrayList<>();

    public SelfSupportAdapter(Context mContext, List<SelfSupport> selfSupports){
        this.mContext = mContext;
        this.selfSupports = selfSupports;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return selfSupports.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh ;
        if(convertView == null){
            vh = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_self_support, null);

            vh.image = (ImageView) convertView.findViewById(R.id.iv_item_ss);
            vh.title = (TextView) convertView.findViewById(R.id.tv_item_ss_title);
            vh.price = (TextView) convertView.findViewById(R.id.tv_item_ss_price);
            vh.num = (TextView) convertView.findViewById(R.id.tv_item_ss_num);

            convertView.setTag(vh);

        }else{
            vh = (ViewHolder) convertView.getTag();
        }

        vh.image.setImageResource(R.mipmap.yibeitong001);
        vh.title.setText("易贝通啊易贝通");
        vh.price.setTextColor(Color.RED);
        vh.price.setText("￥10000.00");
        vh.num.setText("100");


        return convertView;
    }

    public class ViewHolder{
        private ImageView image;
        private TextView title, price, num;
    }
}
