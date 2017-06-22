package com.bh.yibeitong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bh.yibeitong.Interface.CallbackCart;
import com.bh.yibeitong.R;
import com.bh.yibeitong.bean.AddShopCart;
import com.bh.yibeitong.bean.CeShi;
import com.bh.yibeitong.bean.GoodsIndex;
import com.bh.yibeitong.utils.GetMsgForNet;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingang on 2016/10/25.
 * 商品列表适配器
 */

public class CatefoodslistAdapter extends BaseAdapter {
    private Context mContext;
    //private List<CeShi.MsgBean.CatefoodslistBean> ceShiList;
    private List<GoodsIndex.MsgBean.CatefoodslistBean> goodList = new ArrayList<>();
    private int good_num;

    /*public CatefoodslistAdapter(Context mContext, List<CeShi.MsgBean.CatefoodslistBean> ceShiList) {
        this.mContext = mContext;
        this.ceShiList = ceShiList;

    }*/
    public CatefoodslistAdapter(Context mContext, List<GoodsIndex.MsgBean.CatefoodslistBean> goodList) {
        this.mContext = mContext;
        this.goodList = goodList;
    }

    @Override
    public int getCount() {
        return goodList.size();
    }

    @Override
    public Object getItem(int position) {
        return goodList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_self_support, null);
            //获取item中的组件

            vh.imager = (ImageView) convertView.findViewById(R.id.iv_item_ss);
            vh.title = (TextView) convertView.findViewById(R.id.tv_item_ss_title);
            vh.price = (TextView) convertView.findViewById(R.id.tv_item_ss_price);
            vh.num = (TextView) convertView.findViewById(R.id.tv_item_ss_num);

            //添加购物车
            vh.tv_shop_num = (TextView) convertView.findViewById(R.id.tv_shop_num);
            vh.iv_add_button = (ImageView) convertView.findViewById(R.id.iv_add);
            vh.iv_sub_botton = (ImageView) convertView.findViewById(R.id.iv_sub);

            convertView.setTag(vh);

        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        // 组件赋值
        String imgPath = goodList.get(position).getImg();
        if (goodList.get(position).getImg().equals("")) {
            vh.imager.setImageResource(R.mipmap.yibeitong001);

        } else {
            BitmapUtils bitmapUtils = new BitmapUtils(mContext);

            bitmapUtils.configDiskCacheEnabled(true);
            bitmapUtils.configMemoryCacheEnabled(false);

            bitmapUtils.display(vh.imager, "http://www.ybt9.com/" + imgPath);

        }

        vh.title.setText(goodList.get(position).getName());

        vh.price.setText("￥" + goodList.get(position).getCost());

        vh.num.setText("月售" + goodList.get(position).getSellcount() + "笔");

        //购物车部分
        if (good_num == 0) {
            vh.tv_shop_num.setVisibility(View.INVISIBLE);
            vh.iv_sub_botton.setVisibility(View.INVISIBLE);
        } else {
            vh.tv_shop_num.setVisibility(View.VISIBLE);
            vh.iv_sub_botton.setVisibility(View.VISIBLE);

        }

        return convertView;
    }

    public class ViewHolder {
        private ImageView imager;
        private TextView title, price, num;

        private ImageView iv_add_button, iv_sub_botton;
        private TextView tv_shop_num;
    }

}
