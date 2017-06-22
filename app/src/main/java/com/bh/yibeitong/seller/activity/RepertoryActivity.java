package com.bh.yibeitong.seller.activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.seller.bean.CheckStock;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by jingang on 2017/5/31.
 * 库存查询
 */
public class RepertoryActivity extends BaseTextActivity {
    /*接收页面传值*/
    public Intent intent;
    private String shopid;

    /*数据接口地址 页码*/
    private String PATH = "";
    private int page = 1;

    /*ListView显示*/
    private ListView listView;
    private CheckStockAdapter checkStockAdapter;
    private List<CheckStock.MsgBean> msgBeen;

    /*上一页 下一页*/
    private Button but_last, but_next, but_sure;
    private EditText et_page;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_repertory);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleBack(true, 0);
        setTitleName("库存查询");

        initData();
    }

    /*组件初始化*/
    public void initData() {
        intent = getIntent();
        shopid = intent.getStringExtra("shopid");

        listView = (ListView) findViewById(R.id.lv_sr);

        et_page = (EditText) findViewById(R.id.et_sr_page);

        but_last = (Button) findViewById(R.id.but_sr_last);
        but_next = (Button) findViewById(R.id.but_sr_next);
        but_sure = (Button) findViewById(R.id.but_sr_sure);
        but_last.setOnClickListener(this);
        but_next.setOnClickListener(this);
        but_sure.setOnClickListener(this);

        getCheckStock(shopid, "1.23", 1, 20);
        //getCheckStock("23", "1.23", 1, 20);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {

            case R.id.but_sr_last:
                //上一页
                but_last.setClickable(false);
                if (page <= 1) {
                    toast("已经是第一页");
                } else {
                    page--;
                    getCheckStock(shopid, "1.23",
                            page, 20);
                }

                break;

            case R.id.but_sr_next:
                //下一页
                but_next.setClickable(false);
                page++;
                getCheckStock(shopid, "1.23", page, 20);

                break;

            case R.id.but_sr_sure:
                //确定
                but_sure.setClickable(false);
                page = Integer.parseInt(et_page.getText().toString());

                getCheckStock(shopid, "1.23", page, 20);

                break;

            default:
                break;
        }
    }

    /**
     * shopid 店铺id
     * &count=1.23 筛选条件 1.23为默认
     * &page=1 页码
     * &pagesize=20 每页个数
     *
     * @param shopid
     */
    public void getCheckStock(String shopid, String count, final int page, int pagesize) {
        PATH = HttpPath.PATH + HttpPath.APP_CHECKSTOCK +
                "shopid=" + shopid + "&count=1.23" + "&page=" + page +
                "&pagesize=" + pagesize;

        RequestParams params = new RequestParams(PATH);

        System.out.println("" + PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("库存查询（默认）" + result);

                        //恢复点击
                        but_last.setClickable(true);
                        but_next.setClickable(true);
                        but_sure.setClickable(true);

                        CheckStock checkStock = GsonUtil.gsonIntance().gsonToBean(result, CheckStock.class);

                        checkStockAdapter = new CheckStockAdapter(RepertoryActivity.this, checkStock.getMsg());
                        listView.setAdapter(checkStockAdapter);

                        et_page.setText("" + page);

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        toast("网络请求错误，错误原因" + ex);

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });

    }

    /*listView适配器*/
    public class CheckStockAdapter extends BaseAdapter {
        private Context mContext;
        private List<CheckStock.MsgBean> msgBeanList;

        public CheckStockAdapter(Context mContext, List<CheckStock.MsgBean> msgBeanList) {
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
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder vh;
            if (view == null) {
                vh = new ViewHolder();
                view = LayoutInflater.from(mContext).inflate(R.layout.item_seller_repertory, null);
                vh.no = (TextView) view.findViewById(R.id.tv_item_sr_no);
                vh.name = (TextView) view.findViewById(R.id.tv_item_sr_name);
                vh.qty = (TextView) view.findViewById(R.id.tv_item_sr_qty);

                view.setTag(vh);

            } else {
                vh = (ViewHolder) view.getTag();
            }

            String no = msgBeanList.get(i).getItem_no();
            String name = msgBeanList.get(i).getItem_name();
            String qty = msgBeanList.get(i).getStock_qty();

            vh.no.setText("" + no);
            vh.name.setText("" + name);
            vh.qty.setText("" + qty);


            return view;
        }

        public class ViewHolder {
            private TextView no, name, qty;
        }
    }
}