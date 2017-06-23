package com.bh.yibeitong.ui.homepage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.homepage.Tese;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by jingang on 2017/6/20.
 * 主页特色服务
 */
public class SpecialActivity extends BaseTextActivity {
    /*接口地址*/
    private String PATH = "";

    private ListView listView;
    private TeseListAdapter teseListAdapter;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_special);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        initData();

        setTitleBack(true, 0);
        setTitleName("特色服务");

    }

    public void initData() {
        listView = (ListView) findViewById(R.id.lv_tese);

        getLtmkList();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    /**
     * 获取特色服务模块
     */
    public void getLtmkList() {
        PATH = HttpPath.PATH + HttpPath.GETTESE;

        RequestParams params = new RequestParams(PATH);

        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("特色服务" + result);

                        Tese tese = GsonUtil.gsonIntance().gsonToBean(result, Tese.class);
                        teseListAdapter = new TeseListAdapter(SpecialActivity.this, tese.getMsg());
                        listView.setAdapter(teseListAdapter);

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
     *
     */
    public class TeseListAdapter extends BaseAdapter {
        private Context mContext;
        private List<Tese.MsgBean> msgBeenList;

        public TeseListAdapter(Context mContext, List<Tese.MsgBean> msgBeenList) {
            this.mContext = mContext;
            this.msgBeenList = msgBeenList;
        }

        @Override
        public int getCount() {
            return msgBeenList.size();
        }

        @Override
        public Object getItem(int i) {
            return msgBeenList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder vh;
            if (view == null) {
                vh = new ViewHolder();
                view = LayoutInflater.from(mContext).inflate(R.layout.item_tese, null);

                vh.iv_img = (ImageView) view.findViewById(R.id.iv_item_tese_img);
                vh.tv_name = (TextView) view.findViewById(R.id.tv_item_tese_name);

                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }

            String name = msgBeenList.get(i).getName();
            String img = (String) msgBeenList.get(i).getImg();

            System.out.println("img = " + img);

            /*if (img.equals("")) {
                vh.iv_img.setImageResource(R.mipmap.yibeitong001);

            } else {
                x.image().bind(vh.iv_img, "http://www.ybt9.com/" + img);
            }*/
            vh.tv_name.setText("" + name);

            /*点击listView的item*/
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    toast("" + i);
                }
            });

            return view;
        }

        public class ViewHolder {
            TextView tv_name;
            ImageView iv_img;
        }
    }
}
