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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.seller.GoodsList;
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
 * 商家获取商品（商家端）（商品操作）
 */

public class SAppGoodsActivity extends BaseTextActivity {
    private SAppGoodsActivity TAG = SAppGoodsActivity.this;

    /*接收页面传值*/
    private Intent intent;
    private String uid = "", pwd = "", id = "", classify = "";

    /*接口地址*/
    private String PATH = "";

    /*UI展示*/
    private TextView tv_addclassify;
    private LinearLayout lin_add_classifly;
    private ListView listView;
    private List<GoodsList.MsgBean> goodsList = new ArrayList<>();
    private GoodsListAdapter goodsListAdapter;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_s_appclassify);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleBack(true, 0);
        setTitleName("菜品管理");

        initData();
    }

    public void initData() {
        intent = getIntent();
        uid = intent.getStringExtra("uid");
        pwd = intent.getStringExtra("pwd");
        id = intent.getStringExtra("id");
        classify = intent.getStringExtra("classify");

        lin_add_classifly = (LinearLayout) findViewById(R.id.lin_add_claaifly);
        lin_add_classifly.setOnClickListener(this);

        listView = (ListView) findViewById(R.id.lv_s_goodslist);

        tv_addclassify = (TextView) findViewById(R.id.tv_s_add_classify);
        tv_addclassify.setText("添加菜品");

        getGoodsList(uid, pwd, id, 1, 100);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.lin_add_claaifly:
                //添加菜品
                setIntent(uid, pwd, "", "", classify, "", "", "", "", "添加商品", id);

                break;

            default:
                break;

        }
    }

    /*Activity跳转并传值*/
    public void setIntent(String uid, String pwd, String id, String img, String classify,
                          String name, String bagcost, String count, String cost, String title, String typeid) {
        intent = new Intent(TAG, SAddGoodsActivity.class);
        intent.putExtra("uid", uid);
        intent.putExtra("pwd", pwd);
        intent.putExtra("id", id);
        intent.putExtra("img", img);
        intent.putExtra("classify", classify);
        intent.putExtra("name", name);
        intent.putExtra("bagcost", bagcost);
        intent.putExtra("count", count);
        intent.putExtra("cost", cost);
        intent.putExtra("title", title);
        intent.putExtra("typeid", typeid);
        startActivityForResult(intent, CodeUtils.CODE_SELLER_GOODS);
    }


    /**
     * 商家获取商品
     *
     * @param typeid
     * @param page
     * @param pagesize
     */
    public void getGoodsList(String uid, String pwd, String typeid, int page, int pagesize) {
        PATH = HttpPath.PATH + HttpPath.APP_GOODSLIST +
                "uid=" + uid + "&pwd=" + pwd + "&typeid=" + typeid +
                "&page=" + page + "&pagesize=" + pagesize;
        RequestParams params = new RequestParams(PATH);
        System.out.println("商家获取商品" + PATH);

        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("商家获取商品" + result);
                        GoodsList goodsLists = GsonUtil.gsonIntance().gsonToBean(result, GoodsList.class);

                        goodsList = goodsLists.getMsg();

                        goodsListAdapter = new GoodsListAdapter(goodsList, TAG);
                        listView.setAdapter(goodsListAdapter);

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
     * 商家删除商品
     *
     * @param uid
     * @param pwd
     * @param id
     * @param position
     */
    public void delGoods(String uid, String pwd, String id, final int position) {
        PATH = HttpPath.PATH + HttpPath.APP_DELGOODS +
                "uid=" + uid + "&pwd=" + pwd + "&id=" + id;

        RequestParams params = new RequestParams(PATH);
        System.out.println("超市商家删除一级商品分类" + PATH);

        x.http().post(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("超市商家删除一级商品分类" + PATH);

                        goodsList.remove(position);
                        goodsListAdapter.notifyDataSetChanged();
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

                delGoods(uid, pwd, id, position);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    /*
    * 超市商家获取一级商品分类适配器*/
    public class GoodsListAdapter extends BaseAdapter {

        private List<GoodsList.MsgBean> marketfList;
        private Context mContent;

        private boolean[] isLive;

        public GoodsListAdapter(List<GoodsList.MsgBean> marketfList, Context mContent) {
            this.marketfList = marketfList;
            this.mContent = mContent;
            isLive = new boolean[marketfList.size()];
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
            final ViewHolder vh;
            if (view == null) {
                vh = new ViewHolder();
                view = LayoutInflater.from(mContent).inflate(R.layout.item_s_goodslist, null);

                vh.iv_img = (ImageView) view.findViewById(R.id.item_iv_s_img);
                vh.tv_name = (TextView) view.findViewById(R.id.item_tv_s_name);
                vh.tv_count = (TextView) view.findViewById(R.id.item_tv_s_count);
                vh.tv_cost = (TextView) view.findViewById(R.id.item_tv_s_cost);

                vh.but_is_live = (Button) view.findViewById(R.id.item_but_s_is_live);
                vh.but_update = (Button) view.findViewById(R.id.item_but_s_update);
                vh.but_delete = (Button) view.findViewById(R.id.item_but_s_delete);

                //isLive[i] = false;

                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }
            String img = marketfList.get(i).getImg();
            String name = marketfList.get(i).getName();
            String count = marketfList.get(i).getCount();
            String cost = marketfList.get(i).getCost();
            final String is_live = marketfList.get(i).getIs_live();

            if (marketfList.get(i).getImg().equals("")) {
                vh.iv_img.setImageResource(R.mipmap.yibeitong001);

            } else {
                //加载图片
                x.image().bind(vh.iv_img, "http://www.ybt9.com/" + img);
            }

            vh.tv_name.setText("" + name);
            vh.tv_count.setText("库存：" + count);
            vh.tv_cost.setText("￥" + cost);

            if (is_live.equals("0")) {
                vh.but_is_live.setText("已下架");
                isLive[i] = false;
            } else if (is_live.equals("1")) {
                vh.but_is_live.setText("已上架");
                isLive[i] = true;
            }

            /*修改*/
            vh.but_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setIntent(uid, pwd, marketfList.get(i).getId(),
                            marketfList.get(i).getImg(), classify, marketfList.get(i).getName(),
                            marketfList.get(i).getBagcost(), marketfList.get(i).getCount(), marketfList.get(i).getCost(),
                            "修改商品", id);
                }
            });

            /*删除*/
            vh.but_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog(goodsList.get(i).getId(), i);
                }
            });

            vh.but_is_live.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isPocess = isLive[i];
                    toast("item" + i + isPocess);
                    if (!isPocess) {
                        PATH = HttpPath.PATH + HttpPath.APP_EDITGOODSLIVE +
                                "uid=" + uid + "&pwd=" + pwd + "&goodsid=" + goodsList.get(i).getId() +
                                "&is_live=" + 1;
                        RequestParams params = new RequestParams(PATH);
                        System.out.println("商品上架" + PATH);

                        x.http().post(params,
                                new Callback.CommonCallback<String>() {
                                    @Override
                                    public void onSuccess(String result) {
                                        System.out.println("商品上架" + result);
                                        //vh.but_is_live.setText("上架");
                                        isLive[i] = true;
                                        goodsList.get(i).setIs_live("1");
                                        goodsListAdapter.notifyDataSetChanged();
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
                    } else {

                        PATH = HttpPath.PATH + HttpPath.APP_EDITGOODSLIVE +
                                "uid=" + uid + "&pwd=" + pwd + "&goodsid=" + goodsList.get(i).getId() +
                                "&is_live=" + 0;
                        RequestParams params = new RequestParams(PATH);
                        System.out.println("商品下架" + PATH);

                        x.http().post(params,
                                new Callback.CommonCallback<String>() {
                                    @Override
                                    public void onSuccess(String result) {
                                        System.out.println("商品下架" + result);
                                        //vh.but_is_live.setText("下架");
                                        isLive[i] = false;

                                        goodsList.get(i).setIs_live("0");
                                        goodsListAdapter.notifyDataSetChanged();
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

                }
            });

            return view;
        }

        public class ViewHolder {
            ImageView iv_img;
            TextView tv_name, tv_count, tv_cost;
            Button but_is_live, but_update, but_delete;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CodeUtils.CODE_SELLER_GOODS) {
            if (resultCode == CodeUtils.CODE_SELLER_GOODS_ADD) {
                goodsList.clear();
                getGoodsList(uid, pwd, id, 1, 100);
            }
        }
    }
}
