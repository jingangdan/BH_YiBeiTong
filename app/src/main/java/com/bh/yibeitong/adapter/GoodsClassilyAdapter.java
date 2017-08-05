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
 * shopnew 商品一级分类
 */
public class GoodsClassilyAdapter extends BaseAdapter{
    private Context mContext;
    private List<ShopNew.MsgBean> msgBeen = new ArrayList<>();
    private int mSelect = 0;

    public GoodsClassilyAdapter(Context mContext, List<ShopNew.MsgBean> msgBeen){
        this.mContext = mContext;
        this.msgBeen = msgBeen;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_good_classily, null);

            vh.goodName = (TextView) convertView.findViewById(R.id.tv_item_good_classily);
            vh.lin_shop_good = (LinearLayout) convertView.findViewById(R.id.lin_shop_good);

            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }

        //点击改变背景
        if (mSelect == position) {
            vh.lin_shop_good.setBackgroundColor(Color.WHITE);
        } else {
            vh.lin_shop_good.setBackgroundColor(Color.rgb(240, 240, 240));
        }

        vh.goodName.setText(msgBeen.get(position).getName());


//        for (int i = 0; i < msgBeen.get(position).getChild().size(); i++){
//            if (msgBeen.get(position).getChild().get(i).getDet().toString().equals("[]")) {
//                vh.lin_shop_good.setVisibility(View.GONE);
//            } else {
//                vh.lin_shop_good.setVisibility(View.VISIBLE);
//
//                if (mSelect == position) {
//                    vh.lin_shop_good.setBackgroundColor(Color.WHITE);
//                } else {
//                    vh.lin_shop_good.setBackgroundColor(Color.rgb(240, 240, 240));
//                }
//
//                vh.goodName.setText(msgBeen.get(position).getName());
//            }
//        }

        return convertView;
    }

    public class ViewHolder{
        TextView goodName;
        LinearLayout lin_shop_good;
    }
}
