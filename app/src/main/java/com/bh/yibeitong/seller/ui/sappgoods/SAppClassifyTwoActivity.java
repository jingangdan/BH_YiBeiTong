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
import com.bh.yibeitong.bean.seller.MarketTgoodstype;
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
 * 店铺管理（商家端）(二级分类管理)
 */

public class SAppClassifyTwoActivity extends BaseTextActivity {
    private SAppClassifyTwoActivity TAG = SAppClassifyTwoActivity.this;

    /*接收页面传值*/
    private Intent intent;
    private String uid = "", pwd = "", id = "";

    /*接口地址*/
    private String PATH = "";

    /*UI展示*/
    private LinearLayout lin_add_classifly;
    private ListView listView;
    private List<MarketTgoodstype.MsgBean> marketTList = new ArrayList<>();
    private MarketTAdapter marketTAdapter;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_s_appclassify);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleBack(true, 0);
        setTitleName("分类管理（二级）");

        initData();
    }

    public void initData() {
        intent = getIntent();
        uid = intent.getStringExtra("uid");
        pwd = intent.getStringExtra("pwd");
        id = intent.getStringExtra("id");

        lin_add_classifly = (LinearLayout) findViewById(R.id.lin_add_claaifly);
        lin_add_classifly.setOnClickListener(this);

        listView = (ListView) findViewById(R.id.lv_s_goodslist);

        getMarketTgoodstype(uid, pwd, id);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.lin_add_claaifly:
                //添加分类
                setIntent(uid, pwd, "", id, "添加二级分类", "","2");

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
        startActivityForResult(intent, CodeUtils.CODE_SELLER_GOODS_TWO);
    }


    /**
     * 超市商家获取一级商品分类
     *
     * @param uid
     * @param pwd
     */
    public void getMarketTgoodstype(final String uid, final String pwd, String id) {
        PATH = HttpPath.PATH + HttpPath.APP_MARKERTGOODSTYPE +
                "uid=" + uid + "&pwd=" + pwd+"&ftype="+id;
        RequestParams params = new RequestParams(PATH);
        System.out.println("超市商家获取二级商品分类" + PATH);

        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("超市商家获取二级商品分类" + result);
                        MarketTgoodstype marketTgoodstype = GsonUtil.gsonIntance().gsonToBean(result, MarketTgoodstype.class);

                        marketTList = marketTgoodstype.getMsg();

                        marketTAdapter = new MarketTAdapter(marketTList, TAG);
                        listView.setAdapter(marketTAdapter);

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

    public void delMarketT(String uid, String pwd, String id, String ftype, final int position) {
        PATH = HttpPath.PATH + HttpPath.APP_DELMARKERTGOODSTYPE +
                "uid=" + uid + "&pwd=" + pwd + "&id=" + id+"&ftype="+ftype;

        RequestParams params = new RequestParams(PATH);
        System.out.println("超市商家删除二级商品分类" + PATH);

        x.http().post(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("超市商家删除二级商品分类" + result);

                        marketTList.remove(position);
                        marketTAdapter.notifyDataSetChanged();
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
    protected void dialog(final String id, final String ftype, final int position) {
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

                delMarketT(uid, pwd, id,ftype, position);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    /*
    * 超市商家获取二级商品分类适配器*/
    public class MarketTAdapter extends BaseAdapter {

        private List<MarketTgoodstype.MsgBean> marketTList;
        private Context mContent;

        public MarketTAdapter(List<MarketTgoodstype.MsgBean> marketTList, Context mContent) {
            this.marketTList = marketTList;
            this.mContent = mContent;
        }

        @Override
        public int getCount() {
            return marketTList.size();
        }

        @Override
        public Object getItem(int i) {
            return marketTList.get(i);
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
            String name = marketTList.get(i).getName();
            int num = marketTList.get(i).getShuliang();

            vh.tv_name.setText("" + name);
            vh.tv_num.setText("菜品数：" + num);

            /*修改*/
            vh.but_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setIntent(uid, pwd, marketTList.get(i).getId(), id, "修改二级分类", marketTList.get(i).getName() , "2");
                }
            });

            /*管理*/
            vh.but_manage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intent = new Intent(TAG, SAppGoodsActivity.class);
                    intent.putExtra("uid", uid);
                    intent.putExtra("pwd", pwd);
                    intent.putExtra("id", marketTList.get(i).getId());
                    intent.putExtra("classify", marketTList.get(i).getName());
                    startActivityForResult(intent, CodeUtils.CODE_SELLER_GOODS_TWO);
                }
            });

            /*删除*/
            vh.but_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog( marketTList.get(i).getId(),id, i);
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
        if(requestCode == CodeUtils.CODE_SELLER_GOODS_TWO){
            if(resultCode == CodeUtils.CODE_SELLER_CLASSIFY_ADD){
                toast("6666");

                marketTList.clear();

                getMarketTgoodstype(uid, pwd,id);
            }
        }
    }
}
