package com.bh.yibeitong.ui.sendwater;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.SendWater;
import com.bh.yibeitong.ui.CateFoodDetailsActivity;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by jingang on 2017/3/13.
 * 送水上门
 */
public class SendWaterActivity extends BaseTextActivity {

    /*接收页面传值*/
    Intent intent;
    String shopid;

    /*接口地址*/
    String PATH;

    /*UI赋值*/
    SendWaterAdapter sendWaterAdapter;
    private List<SendWater.MsgBean> msgBeen;
    private ListView listView;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_sendwater);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleBack(true, 0);
        setTitleName("送水上门");

        initData();

        getSendWater(shopid);
    }

    public void initData() {
        listView = (ListView) findViewById(R.id.lv_sendwater);

        intent = getIntent();
        shopid = intent.getStringExtra("shopid");

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    /**
     * @param shopid
     */
    public void getSendWater(String shopid) {
        PATH = HttpPath.path + HttpPath.CATE_SSSM + "shopid=" + shopid;
        RequestParams params = new RequestParams(PATH);

        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("送水上门");
                        SendWater sendWater = GsonUtil.gsonIntance().gsonToBean(result, SendWater.class);

                        msgBeen = sendWater.getMsg();
                        sendWaterAdapter = new SendWaterAdapter(SendWaterActivity.this, msgBeen);

                        listView.setAdapter(sendWaterAdapter);

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

    /**
     * 水 适配器
     */
    public class SendWaterAdapter extends BaseAdapter {

        private Context mContext;
        private List<SendWater.MsgBean> msgBeanList;

        public SendWaterAdapter(Context mContext, List<SendWater.MsgBean> msgBeanList) {
            this.mContext = mContext;
            this.msgBeanList = msgBeanList;
        }

        @Override
        public int getCount() {
            return msgBeanList.size();
        }

        @Override
        public Object getItem(int i) {
            return msgBeanList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolder vh;
            if (view == null) {
                vh = new ViewHolder();
                view = LayoutInflater.from(mContext).inflate(R.layout.item_sendwater, null);

                vh.imageView = (ImageView) view.findViewById(R.id.iv_item_sendwater);

                view.setTag(vh);

            } else {
                vh = (ViewHolder) view.getTag();
            }

            String imgPath = msgBeanList.get(i).getImg();
            final String id = msgBeanList.get(i).getGid();

            if (imgPath.equals("")) {
                vh.imageView.setImageResource(R.mipmap.yibeitong001);

            } else {
                x.image().bind(vh.imageView, "http://www.ybt9.com/" + imgPath);
            }

            /*点击图片跳*/
            vh.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    intent = new Intent(mContext, CateFoodDetailsActivity.class);
                    intent.putExtra("id", id);//商品id
                    intent.putExtra("instro", "");

                    mContext.startActivity(intent);
                }
            });


            return view;
        }

        public class ViewHolder {
            ImageView imageView;
        }
    }

}
