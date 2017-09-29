package com.bh.yibeitong.seller.ui.sappgoods;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.utils.CodeUtils;
import com.bh.yibeitong.utils.HttpPath;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by jingang on 2017/9/27.
 * 添加菜品（商家端）
 */

public class SAddGoodsActivity extends BaseTextActivity {
    SAddGoodsActivity TAG = SAddGoodsActivity.this;

    /*接收页面传值*/
    private Intent intent;
    private String uid = "", pwd = "", id = "",
            img = "", classify = "", name = "",
            bagcost = "", count = "", cost = "",
            title = "", typeid = "";

    /*接口地址*/
    private String PATH = "";

    /*UI展示*/
    private ImageView imageView;
    private EditText et_classify, et_name, et_bagcost, et_count, et_cost;
    private Button but_ok, but_no;

    String UTF_classify = null, UTF_name = null;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_s_addgoods);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        initData();

        setTitleBack(true, 0);
        setTitleName("" + title);
    }

    public void initData() {
        intent = getIntent();
        uid = intent.getStringExtra("uid");
        pwd = intent.getStringExtra("pwd");
        id = intent.getStringExtra("id");
        img = intent.getStringExtra("img");
        classify = intent.getStringExtra("classify");
        name = intent.getStringExtra("name");
        bagcost = intent.getStringExtra("bagcost");
        count = intent.getStringExtra("count");
        cost = intent.getStringExtra("cost");
        title = intent.getStringExtra("title");
        typeid = intent.getStringExtra("typeid");

        imageView = (ImageView) findViewById(R.id.iv_s_addgoods_img);
        et_classify = (EditText) findViewById(R.id.et_s_addgoods_classify);
        et_name = (EditText) findViewById(R.id.et_s_addgoods_name);
        et_bagcost = (EditText) findViewById(R.id.et_s_addgoods_bagcost);
        et_count = (EditText) findViewById(R.id.et_s_addgoods_count);
        et_cost = (EditText) findViewById(R.id.et_s_addgoods_cost);

        but_ok = (Button) findViewById(R.id.but_s_goods_ok);
        but_no = (Button) findViewById(R.id.but_s_goods_no);
        but_ok.setOnClickListener(TAG);
        but_no.setOnClickListener(TAG);

        et_classify.setText("" + classify);
        et_name.setText("" + name);
        et_bagcost.setText("" + bagcost);
        et_count.setText("" + count);
        et_cost.setText("" + cost);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.but_s_goods_ok:
                //提交
                classify = et_classify.getText().toString();
                name = et_name.getText().toString();
                bagcost = et_bagcost.getText().toString();
                count = et_count.getText().toString();
                cost = et_cost.getText().toString();

                if (!("").equals(classify)) {
                    try {
                        UTF_classify = URLEncoder.encode(classify, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                if (!("").equals(name)) {
                    try {
                        UTF_name = URLEncoder.encode(name, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                if(!classify.equals("")){
                    if(!name.equals("")){
                        if(!bagcost.equals("")){
                            if(!count.equals("")){
                                if(!cost.equals("")){
                                    postAddGoods(uid, pwd, id, UTF_name, cost, bagcost, count, typeid);

                                }else{
                                    toast("商品单价不可为空");
                                }

                            }else{
                                toast("商品数量不可为空");
                            }
                        }else{
                            toast("打包费不可为空");
                        }
                    }else{
                        toast("商品名称不可为空");
                    }
                }else{
                    toast("分类名称不可为空");
                }



                break;

            case R.id.but_s_goods_no:
                //取消
                TAG.finish();
                break;

            default:
                break;
        }
    }

    public void setResult(String name){
        intent = new Intent();
        setResult(CodeUtils.CODE_SELLER_GOODS_ADD, intent);
    }


    /**
     * 超市商家添加商品
     *
     * @param uid
     * @param pwd
     * @param id      商品id  修改时传
     * @param name
     * @param cost
     * @param bagcost
     * @param count
     * @param typeid  上级分类id
     */
    public void postAddGoods(String uid, String pwd, String id,
                             String name, String cost, String bagcost,
                             String count, String typeid) {
        PATH = HttpPath.PATH + HttpPath.APP_ADDGOODS +
                "uid=" + uid + "&pwd=" + pwd + "&id=" + id +
                "&name=" + name + "&cost=" + cost + "&bagcost=" + bagcost +
                "&count=" + count + "&typeid=" + typeid;

        RequestParams params = new RequestParams(PATH);

        System.out.println("超市商家添加商品"+PATH);

        x.http().post(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("超市商家添加商品"+result);
                        setResult("");
                        TAG.finish();
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
