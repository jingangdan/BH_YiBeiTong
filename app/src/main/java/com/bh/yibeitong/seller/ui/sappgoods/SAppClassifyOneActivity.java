package com.bh.yibeitong.seller.ui.sappgoods;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.seller.MarketFgoodstype;
import com.bh.yibeitong.utils.CodeUtils;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingang on 2017/9/26.
 * 店铺管理（商家端）(一级分类管理)
 */

public class SAppClassifyOneActivity extends BaseTextActivity {
    private SAppClassifyOneActivity TAG = SAppClassifyOneActivity.this;

    /*接收页面传值*/
    private Intent intent;
    private String uid = "", pwd = "";

    /*接口地址*/
    private String PATH = "";

    /*UI展示*/
    private LinearLayout lin_add_classifly;
    private ListView listView;
    private List<MarketFgoodstype.MsgBean> marketFList = new ArrayList<>();
    private MarketFAdapter marketFAdapter;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_s_appclassify);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleBack(true, 0);
        setTitleName("分类管理（一级）");

        initData();
    }

    public void initData() {
        intent = getIntent();
        uid = intent.getStringExtra("uid");
        pwd = intent.getStringExtra("pwd");

        lin_add_classifly = (LinearLayout) findViewById(R.id.lin_add_claaifly);
        lin_add_classifly.setOnClickListener(this);

        listView = (ListView) findViewById(R.id.lv_s_goodslist);

        getMarketFgoodstype(uid, pwd);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.lin_add_claaifly:
                //添加分类
                setIntent(uid, pwd, "", "", "添加一级分类", "", "1");

                break;

            default:
                break;

        }
    }

    /*Activity跳转并传值*/
    public void setIntent(String uid, String pwd, String id, String ftype, String title, String name, String code) {
        intent = new Intent(TAG, SAddClassiflyActivity.class);
        intent.putExtra("uid", uid);
        intent.putExtra("pwd", pwd);
        intent.putExtra("id", id);
        intent.putExtra("ftype", ftype);
        intent.putExtra("title", title);
        intent.putExtra("name", name);
        intent.putExtra("code", code);
        startActivityForResult(intent, CodeUtils.CODE_SELLER_GOODS_ONE);
    }


    /**
     * 超市商家获取一级商品分类
     *
     * @param uid
     * @param pwd
     */
    public void getMarketFgoodstype(final String uid, final String pwd) {
        PATH = HttpPath.PATH + HttpPath.APP_MARKERFGOODSTYPE +
                "uid=" + uid + "&pwd=" + pwd;
        RequestParams params = new RequestParams(PATH);
        System.out.println("超市商家获取一级商品分类" + PATH);

        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("超市商家获取一级商品分类" + result);
                        MarketFgoodstype marketFgoodstype = GsonUtil.gsonIntance().gsonToBean(result, MarketFgoodstype.class);

                        marketFList = marketFgoodstype.getMsg();

                        marketFAdapter = new MarketFAdapter(marketFList, TAG);
                        listView.setAdapter(marketFAdapter);

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
     * 超市商家删除一级商品分类
     *
     * @param uid
     * @param pwd
     * @param id
     */
    public void delMarketF(String uid, String pwd, String id, final int position) {
        PATH = HttpPath.PATH + HttpPath.APP_DELMARKERFGOODSTYPE +
                "uid=" + uid + "&pwd=" + pwd + "&id=" + id;

        RequestParams params = new RequestParams(PATH);
        System.out.println("超市商家删除一级商品分类" + PATH);

        x.http().post(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("超市商家删除一级商品分类" + result);

                        marketFList.remove(position);
                        marketFAdapter.notifyDataSetChanged();
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

    /*提示框*/
    protected void dialog(final String id, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(TAG);
        builder.setTitle("确定删除此类？");
        //builder.setMessage("这是一个普通的对话框的内容");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                delMarketF(uid, pwd, id, position);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /*
    * 超市商家获取一级商品分类适配器*/
    public class MarketFAdapter extends BaseAdapter {

        private List<MarketFgoodstype.MsgBean> marketfList;
        private Context mContent;

        public MarketFAdapter(List<MarketFgoodstype.MsgBean> marketfList, Context mContent) {
            this.marketfList = marketfList;
            this.mContent = mContent;
        }

        @Override
        public int getCount() {
            return marketfList.size();
        }

        @Override
        public Object getItem(int i) {
            return marketfList.get(i);
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
                view = LayoutInflater.from(mContent).inflate(R.layout.item_s_classifly, null);

                vh.tv_name = (TextView) view.findViewById(R.id.tv_s_classifly_name);
                vh.tv_num = (TextView) view.findViewById(R.id.tv_s_classifly_num);
                vh.but_update = (Button) view.findViewById(R.id.but_s_classifly_update);
                vh.but_manage = (Button) view.findViewById(R.id.but_s_classifly_manage);
                vh.but_delete = (Button) view.findViewById(R.id.but_s_classifly_delete);

                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }
            String name = marketfList.get(i).getName();
            int num = marketfList.get(i).getShuliang();

            vh.tv_name.setText("" + name);
            vh.tv_num.setText("菜品数：" + num);

            /*修改*/
            vh.but_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setIntent(uid, pwd, marketfList.get(i).getId(), "", "修改一级分类", marketfList.get(i).getName(), "1");
                }
            });

            /*管理*/
            vh.but_manage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intent = new Intent(TAG, SAppClassifyTwoActivity.class);
                    intent.putExtra("uid", uid);
                    intent.putExtra("pwd", pwd);
                    intent.putExtra("id", marketfList.get(i).getId());
                    startActivityForResult(intent, CodeUtils.CODE_SELLER_GOODS_ONE);
                }
            });

            /*删除*/
            vh.but_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog(marketfList.get(i).getId(), i);
                }
            });

            return view;
        }

        public class ViewHolder {
            TextView tv_name, tv_num;
            Button but_update, but_manage, but_delete;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CodeUtils.CODE_SELLER_GOODS_ONE){
            if(resultCode == CodeUtils.CODE_SELLER_CLASSIFY_ADD){
                toast("555");

                marketFList.clear();

                getMarketFgoodstype(uid, pwd);

            }
        }
//        if (requestCode == CodeUtils.CODE_SELLER_GOODS_ONE) {
//            if (resultCode == CodeUtils.CODE_SELLER_CLASSIFY_ADD) {
//                toast("5555");
//
//
//            }
//        }
    }
}
