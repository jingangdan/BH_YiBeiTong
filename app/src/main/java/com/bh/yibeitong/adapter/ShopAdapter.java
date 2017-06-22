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
import com.bh.yibeitong.bean.Shop;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingang on 2016/11/12.
 * 显示店铺列表 适配器
 */

public class ShopAdapter extends BaseAdapter {
    private Context mContext;
    private List<Shop.MsgBean> shopList = new ArrayList<>();

    public ShopAdapter(Context mContext, List<Shop.MsgBean> shopList) {
        this.mContext = mContext;
        this.shopList = shopList;
    }

    @Override
    public int getCount() {
        return shopList.size();
    }

    @Override
    public Object getItem(int position) {
        return shopList.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_shop, null);
            vh.img = (ImageView) convertView.findViewById(R.id.iv_item_shop_image);
            vh.name = (TextView) convertView.findViewById(R.id.tv_item_shop_name);

            vh.sellcount = (TextView) convertView.findViewById(R.id.tv_item_shop_sellcount);
            vh.juli = (TextView) convertView.findViewById(R.id.tv_item_shop_juli);

            vh.limitcost = (TextView) convertView.findViewById(R.id.tv_item_shop_limitcost);
            vh.pscost = (TextView) convertView.findViewById(R.id.tv_item_shop_pscost);

            //vh.tv_cost = (TextView) convertView.findViewById(R.id.tv_item_shop_pscosts);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        //
        int canps = shopList.get(position).getCanps();


        /*图片*/
        String img = shopList.get(position).getShopimg();

        /*名称*/
        String name = shopList.get(position).getShopname();

        /*已售单数*/
        int sellcount = shopList.get(position).getSellcount();

        /*距离*/
        String juli = shopList.get(position).getJuli();

        /*起送价  配送费*/
        String limitcost = shopList.get(position).getLimitcost();
        int pscost = shopList.get(position).getPscost();


        if (img.equals("")) {
            vh.img.setImageResource(R.mipmap.yibeitong001);

        } else {
            BitmapUtils bitmapUtils = new BitmapUtils(mContext);

            bitmapUtils.configDiskCacheEnabled(true);
            bitmapUtils.configMemoryCacheEnabled(false);

            bitmapUtils.display(vh.img, img);

        }


       /* vh.img.setImageResource(R.mipmap.yibeitong001);
        vh.name.setText("易贝通曹王庄店");*/

        vh.name.setText(name);

        vh.sellcount.setText("已售" + sellcount + "单");

        vh.juli.setText("距离" + juli);
        vh.limitcost.setText("￥" + limitcost+"起送");
        //vh.tv_pscost.setText("￥"+pscost);

        if (canps == 1) {
            vh.pscost.setText("￥" + pscost+"  配送");
            //vh.tv_cost.setVisibility(View.VISIBLE);
            vh.pscost.setTextColor(Color.rgb(255, 150, 150));
        } else if (canps == 0) {
            vh.pscost.setText("不在配送范围");
            //vh.tv_cost.setVisibility(View.INVISIBLE);
            vh.pscost.setTextColor(Color.GRAY);
        }

        return convertView;
    }

    public class ViewHolder {
        private ImageView img;
        //名称 已售单数 距离
        private TextView name, sellcount, juli;

        //起送价 配送费
        private TextView limitcost, pscost;

        //private TextView tv_cost;

    }


}
