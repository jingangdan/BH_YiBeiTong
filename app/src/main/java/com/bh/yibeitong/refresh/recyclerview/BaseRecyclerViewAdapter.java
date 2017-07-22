package com.bh.yibeitong.refresh.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bh.yibeitong.bean.GoodsIndex;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by 12406 on 2016/4/16.
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_ITEM = 0;
    public static final int TYPE_FOOTER = 1;
    public Context mContext;
    public LayoutInflater mInflater;
    public List<GoodsIndex.MsgBean.CatefoodslistBean> mDatas = new LinkedList<>();
    public OnItemClickListener<T> mOnItemClickListener;


    public BaseRecyclerViewAdapter(Context context, List<GoodsIndex.MsgBean.CatefoodslistBean> datas) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        if (datas != null) {
            mDatas = datas;
        }
    }

    public void addItemLast(List<GoodsIndex.MsgBean.CatefoodslistBean> datas) {
        mDatas.addAll(datas);
    }

    public void addItemTop(List<GoodsIndex.MsgBean.CatefoodslistBean> datas) {
        mDatas = datas;
    }

    public void remove(int position) {
        mDatas.remove(position);
    }

    public void removeAll() {
        mDatas.clear();
    }


    @Override
    public int getItemCount() {
        return mDatas.size() == 0 ? 0 : mDatas.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<GoodsIndex.MsgBean.CatefoodslistBean> getDatas() {
        return mDatas;
    }

    public interface OnItemClickListener<T> {

        void onItemClick(View view, int position, T model);

        void onItemLongClick(View view, int position, T model);
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreate(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBind(holder, position);
    }

    public abstract void onBind(RecyclerView.ViewHolder holder, int position);

    public abstract RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType);

}
