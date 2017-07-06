package com.bh.yibeitong.adapter.homepage;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.bean.homepage.TeseCate;

import java.util.List;

/**
 * Created by jingang on 2017/6/29.
 */

public class TeseClassifyAdapter extends BaseAdapter{
    private Context mContext;
    private List<TeseCate.MsgBean.ChildcateBean> childcateBeen;
    private int mSelect = 0;

    public TeseClassifyAdapter(Context mContext, List<TeseCate.MsgBean.ChildcateBean> childcateBeen){
        this.mContext = mContext;
        this.childcateBeen = childcateBeen;
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
        return childcateBeen.size();
    }

    @Override
    public Object getItem(int i) {
        return childcateBeen.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if(view == null){
            vh = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_good_classily_second, null);

            vh.name = (TextView) view.findViewById(R.id.tv_item_good_classily_second);
            vh.linearLayout = (LinearLayout) view.findViewById(R.id.lin_item_good_classily_second);

            view.setTag(vh);
        }else{
            vh = (ViewHolder) view.getTag();
        }
        String name = childcateBeen.get(i).getName();

        vh.name.setText(""+name);

        if(mSelect == i){
            vh.name.setTextColor(Color.rgb(255, 255, 255));
            vh.linearLayout.setBackgroundResource(R.drawable.linstyle_green);
        }else{
            vh.name.setTextColor(Color.rgb(153, 153, 153));
            vh.linearLayout.setBackgroundResource(R.drawable.linstyle_white);
        }

        return view;
    }
    public class ViewHolder{
        TextView name;
        LinearLayout linearLayout;
    }
}
