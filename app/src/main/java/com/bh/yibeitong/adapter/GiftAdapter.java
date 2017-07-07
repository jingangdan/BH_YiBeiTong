package com.bh.yibeitong.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.bean.Gift;
import com.bh.yibeitong.ui.percen.ExChangeActivity;
import com.bh.yibeitong.ui.percen.ExChangeAddressActivity;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingang on 2016/12/7.
 * 礼品列表 适配器
 */

public class GiftAdapter extends BaseAdapter {

    private List<Gift.MsgBean> msgBeen = new ArrayList<>();
    private Context mContext;

    public GiftAdapter(Context mContext, List<Gift.MsgBean> msgBeen){
        this.mContext = mContext;
        this.msgBeen = msgBeen;
    }


    @Override
    public int getCount() {
        return msgBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return msgBeen.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gift_list, null);

            vh.img = (ImageView) convertView.findViewById(R.id.iv_item_gift);
            vh.title = (TextView) convertView.findViewById(R.id.tv_item_gift_name);
            vh.jifen = (TextView) convertView.findViewById(R.id.tv_gift_jifen);
            vh.exchange = (TextView) convertView.findViewById(R.id.tv_gift_exchange);

            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }

        String imgPath = msgBeen.get(position).getImg();

        if (imgPath.equals("")) {
            vh.img.setImageResource(R.mipmap.yibeitong001);

        } else {
            BitmapUtils bitmapUtils = new BitmapUtils(mContext);

            bitmapUtils.configDiskCacheEnabled(true);
            bitmapUtils.configMemoryCacheEnabled(false);

            bitmapUtils.display(vh.img, imgPath);

        }


        String title = msgBeen.get(position).getTitle();
        String score = msgBeen.get(position).getScore();
        final String id = msgBeen.get(position).getId();

        vh.title.setText(""+title);
        vh.jifen.setText(""+score+"积分");

        /*兑换*/
        vh.exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ExChangeAddressActivity.class);
                intent.putExtra("giftid",id);
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    public class ViewHolder{
        ImageView img;
        TextView title, jifen, exchange;
    }
}
