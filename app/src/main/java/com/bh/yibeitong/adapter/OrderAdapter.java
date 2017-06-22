package com.bh.yibeitong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.bean.Order;
import com.bh.yibeitong.fragment.FMOrders;
import com.lidroid.xutils.BitmapUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingang on 2016/11/19.
 * 订单适配器
 */

public class OrderAdapter extends BaseAdapter {
    private Context mContext;
    private List<Order.MsgBean> msgBeen = new ArrayList<>();

    public OrderAdapter(Context mContext, List<Order.MsgBean> msgBeen) {
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
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_order, null);

            vh.img = (ImageView) convertView.findViewById(R.id.iv_item_order_img);
            vh.shopname = (TextView) convertView.findViewById(R.id.tv_item_order_shopname);
            vh.state = (TextView) convertView.findViewById(R.id.tv_item_order_state);
            vh.price = (TextView) convertView.findViewById(R.id.tv_item_order_price);
            vh.time = (TextView) convertView.findViewById(R.id.tv_item_order_time);
            vh.del = (Button) convertView.findViewById(R.id.but_item_order_del);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        // 组件赋值
        String imgPath = msgBeen.get(position).getShoplogo();
        String shopname = msgBeen.get(position).getShopname();
        String state = msgBeen.get(position).getSeestatus();
        String price = msgBeen.get(position).getAllcost();
        String time = msgBeen.get(position).getAddtime();

        final String orderid = msgBeen.get(position).getId();


        if (imgPath.equals("")) {
            vh.img.setImageResource(R.mipmap.yibeitong001);

        } else {
            BitmapUtils bitmapUtils = new BitmapUtils(mContext);

            bitmapUtils.configDiskCacheEnabled(true);
            bitmapUtils.configMemoryCacheEnabled(false);

            bitmapUtils.display(vh.img, imgPath);

        }

        vh.shopname.setText(shopname);
        vh.state.setText(state);
        vh.price.setText("￥" + price);
        vh.time.setText(time);

        //删除订单
        vh.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FMOrders fmOrders = new FMOrders();

                String PATH = "http://www.ybt9.com//index.php?ctrl=app&source=1&datatype=json&" +
                        "action=newordercontrol&doname=delorder&uid="
                        +fmOrders.uid+"+&pwd"+fmOrders.pwd+"&orderid="+orderid;
                System.out.println(PATH);

                RequestParams params = new RequestParams(PATH);
                x.http().post(params,
                        new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                System.out.println("删除订单"+result);
                            }

                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {

                            }

                            @Override
                            public void onCancelled(CancelledException cex) {

                            }

                            @Override
                            public void onFinished() {

                            }
                        });

            }
        });

        return convertView;
    }

    public class ViewHolder {
        private TextView shopname, price, state, time;
        private ImageView img;
        private Button del;
    }

}
