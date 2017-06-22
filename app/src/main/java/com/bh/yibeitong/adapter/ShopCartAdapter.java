package com.bh.yibeitong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bh.yibeitong.R;
import com.bh.yibeitong.bean.ShopCart;
import com.bh.yibeitong.utils.HttpPath;
import com.lidroid.xutils.BitmapUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingang on 2016/11/20.
 * 购物车 适配器
 */

public class ShopCartAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {

    private Context mContext;
    private List<ShopCart> shopCarts = new ArrayList<>();

    private List<ShopCart.MsgBean.ListBean> listBean = new ArrayList<>();

    public ShopCartAdapter(Context mContext, List<ShopCart.MsgBean.ListBean> listBean) {
        this.mContext = mContext;
        this.listBean = listBean;
    }

    @Override
    public int getCount() {
        return listBean.size();
    }

    @Override
    public Object getItem(int position) {
        return listBean.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_shopcart, null);

            vh.img = (ImageView) convertView.findViewById(R.id.iv_item_sc_img);
            vh.gname = (TextView) convertView.findViewById(R.id.tv_item_sc_gname);
            vh.cost = (TextView) convertView.findViewById(R.id.tv_item_sc_cost);

            vh.count = (TextView) convertView.findViewById(R.id.tv_item_sc_num);

            vh.add = (ImageView) convertView.findViewById(R.id.iv_item_sc_add);
            vh.sub = (ImageView) convertView.findViewById(R.id.iv_item_sc_sub);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        String img = listBean.get(position).getImg();
        String gname = listBean.get(position).getName();
        String cost = listBean.get(position).getCost();

        int count = listBean.get(position).getCount();

        int id = listBean.get(position).getId();
        final String str_id = String.valueOf(id);

        if (img.equals("")) {
            vh.img.setImageResource(R.mipmap.yibeitong001);
        } else {
            BitmapUtils bitmapUtils = new BitmapUtils(mContext);
            bitmapUtils.display(vh.img, "http://www.ybt9.com/" + img);
        }

        vh.gname.setText(gname);
        vh.cost.setText("￥" + cost);
        vh.count.setText("" + count);

        /**
         * 添加购物车
         */
        vh.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addShopCart("8", 1, str_id);
            }
        });

        /**
         * 减少购物车
         */
        vh.sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addShopCart("8", -1, str_id);
                System.out.println("str_id = " + str_id);
            }
        });

//        double i_cost = 0;
//        double i_count = 0;
//        int index = listBean.size();
//
//        i_cost = Double.parseDouble(listBean.get(position).getCost());
//        i_count = listBean.get(position).getCount();
//
//        /*for (int i = 0; i < index; i++) {
//            i_cost = Integer.valueOf(listBean.get(i).getCost());
//            i_count = listBean.get(i).getCount();
//        }*/
//
//        System.out.println("合计 = " + i_cost * i_count);


        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        ViewHolder holder = (ViewHolder) view.getTag();

        /*double d_all = 0;

        double d_cost = Double.parseDouble(listBean.get(position).getCost());
        double d_count = listBean.get(position).getCount();

        d_all += d_cost * d_count;

        System.out.println("d_all = " + d_all);*/


        /*boolean selected = !mSelectState.get(_id, false);
        holder.checkBox.toggle();
        if (selected) {
            mSelectState.put(_id, true);
            totalPrice += bean.getCarNum() * bean.getPrice();
        } else {
            mSelectState.delete(_id);
            totalPrice -= bean.getCarNum() * bean.getPrice();
        }
        mSelectNum.setText("已选" + mSelectState.size() + "件商品");
        mPriceAll.setText("￥" + totalPrice + "");
        if (mSelectState.size() == mListData.size()) {
            mCheckAll.setChecked(true);
        } else {
            mCheckAll.setChecked(false);
        }*/

    }


    public class ViewHolder {
        private TextView gname, cost;
        private ImageView img;
        private TextView count;
        private ImageView add, sub;

    }

    /**
     * 购物车操作
     * num = 1 为添加
     * num = -1 为减少
     *
     * @param shopid
     * @param num
     * @param gid
     */
    public void addShopCart(String shopid, final int num, String gid) {
        String PATH = HttpPath.PATH + HttpPath.ADD_SHOPCART + "&shopid=" + shopid + "&num=" + num + "&gid=" + gid;

        //"http://www.ybt9.com/index.php?ctrl=app&datatype=json&action=addcart&shopid=8&num=-1&gid=1061"
        RequestParams params = new RequestParams(PATH);
        x.http().post(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("购物车操作" + result);
                        /*if (num == 1) {
                            Toast.makeText(mContext, "添加购物车成功", Toast.LENGTH_SHORT).show();
                        } else if (num == -1) {
                            Toast.makeText(mContext, "减少购物车成功", Toast.LENGTH_SHORT).show();
                        }*/
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        /*if (num == 1) {
                            Toast.makeText(mContext, "添加购物车失败", Toast.LENGTH_SHORT).show();
                        } else if (num == -1) {
                            Toast.makeText(mContext, "减少购物车失败", Toast.LENGTH_SHORT).show();
                        }*/
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });

    }
}
