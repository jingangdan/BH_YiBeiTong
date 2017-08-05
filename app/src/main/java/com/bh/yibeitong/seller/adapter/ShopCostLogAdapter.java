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
import com.bh.yibeitong.seller.bean.AppShopCostLog;
import com.bh.yibeitong.utils.BaseRecyclerViewHolder;

import java.util.List;

/**
 * Created by jingang on 2017/8/5.
 * 商家结算列表适配器
 */
public class ShopCostLogAdapter extends RecyclerView.Adapter<ShopCostLogAdapter.MyViewHolder> {
    private Context mContext;
    private List<AppShopCostLog.MsgBean> msgBeanList;

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public ShopCostLogAdapter(Context mContext, List<AppShopCostLog.MsgBean> msgBeanList) {
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

        String name = msgBeanList.get(position).getName();

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
