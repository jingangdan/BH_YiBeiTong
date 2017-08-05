package com.bh.yibeitong.seller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bh.yibeitong.Interface.OnItemClickListener;
import com.bh.yibeitong.Interface.OnItemLongClickListener;
import com.bh.yibeitong.R;
import com.bh.yibeitong.seller.bean.AppShop;
import com.bh.yibeitong.utils.BaseRecyclerViewHolder;

import org.xutils.x;

import java.util.List;

/**
 * Created by jingang on 2017/8/4.
 * 店铺管理适配器
 */
public class AppShopAdapter extends RecyclerView.Adapter<AppShopAdapter.MyViewHolder> {
    private Context mContext;
    private List<AppShop.MsgBean> msgBeanList;

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public AppShopAdapter(Context mContext, List<AppShop.MsgBean> msgBeanList) {
        this.mContext = mContext;
        this.msgBeanList = msgBeanList;
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder vh = new MyViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_shop, parent,
                false));
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        if (mOnItemClickListener != null) {
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(holder.itemView, position); // 2
                }
            });
        }
        if (mOnItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemLongClickListener.onItemLongClick(holder.itemView, position);
                    //返回true 表示消耗了事件 事件不会继续传递
                    return true;
                }
            });
        }

        String shoplogo = msgBeanList.get(position).getShoplogo();
        String shopname = msgBeanList.get(position).getShopname();

        if (shoplogo.equals("")) {
            holder.shoplogo.setImageResource(R.mipmap.yibeitong001);
        } else {
            x.image().bind(holder.shoplogo, shoplogo);
        }

        holder.shopname.setText("" + shopname);


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyViewHolder extends BaseRecyclerViewHolder {
        ImageView shoplogo;
        TextView shopname;

        public MyViewHolder(View view) {
            super(view);
            shoplogo = (ImageView) view.findViewById(R.id.iv_item_shop_image);
            shopname = (TextView) view.findViewById(R.id.tv_item_shop_name);

        }
    }
}
