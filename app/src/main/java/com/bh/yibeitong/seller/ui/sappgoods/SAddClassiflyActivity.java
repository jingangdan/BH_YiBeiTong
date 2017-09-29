package com.bh.yibeitong.seller.ui.sappgoods;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
 * Created by jingang on 2017/9/26.
 * 添加分类（商家端）
 */

public class SAddClassiflyActivity extends BaseTextActivity {

    SAddClassiflyActivity TAG = SAddClassiflyActivity.this;

    /*接收页面传值*/
    private Intent intent;
    private String uid = "", pwd = "", id = "", ftype = "", title = "", name = "", code = "";
    String UTF_contactname = null;

    /*接口地址*/
    private String PATH = "";

    /*UI展示*/
    private EditText et_name, et_id;
    private String orderid = "";
    private Button but_ok, but_no;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_s_addclassifly);
    }

    @Override
    protected void initWidght() {
        super.initWidght();

        initData();

        setTitleName("" + title);
        setTitleBack(true, 0);
    }

    public void initData() {
        intent = getIntent();
        uid = intent.getStringExtra("uid");
        pwd = intent.getStringExtra("pwd");
        id = intent.getStringExtra("id");
        ftype = intent.getStringExtra("ftype");
        title = intent.getStringExtra("title");
        name = intent.getStringExtra("name");
        code = intent.getStringExtra("code");

        et_name = (EditText) findViewById(R.id.et_s_addclassifly_name);
        et_id = (EditText) findViewById(R.id.et_s_addclassifly_id);
        but_ok = (Button) findViewById(R.id.but_s_classifly_ok);
        but_no = (Button) findViewById(R.id.but_s_classifly_no);
        but_ok.setOnClickListener(TAG);
        but_no.setOnClickListener(TAG);

        et_name.setText("" + name);
        et_id.setText("0");


    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.but_s_classifly_ok:
                //提交
                name = et_name.getText().toString();

                if (!("").equals(name)) {
                    try {
                        UTF_contactname = URLEncoder.encode(name, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                if (!name.equals("")) {
                    if (code.equals("1")) {
                        postAddMarketF(uid, pwd, id, ftype, UTF_contactname, "");
                    } else if (code.equals("2")) {
                        postAddMarketT(uid, pwd, id, ftype, UTF_contactname, "");
                    }

                } else {
                    toast("商品名称不可为空");
                }
                break;

            case R.id.but_s_classifly_no:
                //取消
                TAG.finish();
                break;

            default:
                break;
        }
    }

    public void setResult(String name) {
        intent = new Intent();
        setResult(CodeUtils.CODE_SELLER_CLASSIFY_ADD, intent);
    }

    /**
     * 添加（修改） 一级分类
     *
     * @param uid
     * @param pwd
     * @param id
     * @param ftype
     * @param name
     * @param orderid
     */
    public void postAddMarketF(String uid, String pwd, String id, String ftype, String name, String orderid) {
        PATH = HttpPath.PATH + HttpPath.APP_ADDMARKERFGOODSTYPE +
                "uid=" + uid + "&pwd=" + pwd + "&id=" + id +
                "&ftype=" + ftype + "&name=" + name + "&orderid=" + orderid;

        RequestParams params = new RequestParams(PATH);
        System.out.println("添加（修改） 一级分类" + PATH);

        x.http().post(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("添加（修改） 一级分类" + result);
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

    /**
     * 添加（修改） 二级分类
     *
     * @param uid
     * @param pwd
     * @param id
     * @param ftype
     * @param name
     * @param orderid
     */
    public void postAddMarketT(String uid, String pwd, String id, String ftype, String name, String orderid) {
        PATH = HttpPath.PATH + HttpPath.APP_ADDMARKERTGOODSTYPE +
                "uid=" + uid + "&pwd=" + pwd + "&id=" + id +
                "&ftype=" + ftype + "&name=" + name + "&orderid=" + orderid;

        RequestParams params = new RequestParams(PATH);
        System.out.println("添加（修改） 二级分类" + PATH);

        x.http().post(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("添加（修改） 二级分类" + result);
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
