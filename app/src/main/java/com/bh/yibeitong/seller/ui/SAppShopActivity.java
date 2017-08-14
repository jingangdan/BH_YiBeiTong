package com.bh.yibeitong.seller.ui;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.seller.bean.AppShop;
import com.bh.yibeitong.ui.village.PublishThemeActivity;
import com.bh.yibeitong.ui.village.addimg.activity.AlbumActivity;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by jingang on 2017/8/4.
 * 2 店铺管理（商家端）
 */

public class SAppShopActivity extends BaseTextActivity {
    /*接口地址*/
    private String PATH = "";

    /*接收页面传值*/
    private Intent intent;
    private String uid = "", pwd = "";

    /*UI显示*/
    private ImageView iv_shoplogo;
    private TextView tv_shopname, tv_shopphone, tv_opentime, tv_limitcost;

    private String shoplogo = "", shopname = "", shopphone = "", opentime = "", limitcost = "";

    /*选择头像*/
    private Button but_photo;
    private PopupWindow pop = null;

    private LinearLayout ll_popup;
    private View parentView ;

    @Override
    protected void setRootView() {
        super.setRootView();

        parentView = getLayoutInflater().inflate(R.layout.activity_s_appshop, null);
        setContentView(parentView);
        //setContentView(R.layout.activity_s_appshop);
    }

    @Override
    protected void initWidght() {
        super.initWidght();

        setTitleName("店铺管理");
        setTitleBack(true, 0);

        initData();
        Init();
    }

    public void initData() {
        intent = getIntent();
        uid = intent.getStringExtra("uid");
        pwd = intent.getStringExtra("pwd");

        iv_shoplogo = (ImageView) findViewById(R.id.iv_sas_shoplogo);
        tv_shopname = (TextView) findViewById(R.id.tv_sas_shopname);
        tv_shopphone = (TextView) findViewById(R.id.tv_sas_shopphone);
        tv_opentime = (TextView) findViewById(R.id.tv_sas_opentime);
        tv_limitcost = (TextView) findViewById(R.id.tv_sas_limitcost);

        but_photo = (Button) findViewById(R.id.but_sas_photo);
        but_photo.setOnClickListener(this);

        getAppShop(uid, pwd);

    }

    /*选择图片（拍照）*/
    public void Init() {
        pop = new PopupWindow(SAppShopActivity.this);

        View view = getLayoutInflater().inflate(R.layout.personal_header_choice, null);

        view.setAnimation(AnimationUtils.loadAnimation(
                SAppShopActivity.this, R.anim.slide_bottom_to_top));

        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);

        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);

        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        Button bt1 = (Button) view
                .findViewById(R.id.btn_take_photo);
        Button bt2 = (Button) view
                .findViewById(R.id.btn_pick_photo);
        Button bt3 = (Button) view
                .findViewById(R.id.btn_cancel);
        parent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //拍照
                photo();
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //相册选择图片
                Intent intent = new Intent(SAppShopActivity.this, AlbumActivity.class);

                startActivityForResult(intent, 20);
                //startActivity(intent);
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });

    }

    /*拍照*/
    public void photo() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(openCameraIntent, 0x000001);
    }

    /**
     * 店铺信息
     *
     * @param uid
     * @param pwd
     */
    public void getAppShop(String uid, String pwd) {
        PATH = HttpPath.PATH + HttpPath.APP_SHOP +
                "uid=" + uid + "&pwd=" + pwd;
        RequestParams params = new RequestParams(PATH);

        System.out.println("店铺信息" + PATH);

        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("店铺信息" + result);
                        AppShop appShop = GsonUtil.gsonIntance().gsonToBean(result, AppShop.class);

                        shoplogo = appShop.getMsg().getShoplogo();
                        shopname = appShop.getMsg().getShopname();
                        shopphone = appShop.getMsg().getShopphone();
                        opentime = appShop.getMsg().getOpentime();
                        limitcost = appShop.getMsg().getLimitcost();

                        if (shoplogo.equals("")) {
                            iv_shoplogo.setImageResource(R.mipmap.yibeitong001);
                        } else {
                            x.image().bind(iv_shoplogo, shoplogo);
                        }

                        tv_shopname.setText("" + shopname);
                        tv_shopphone.setText("" + shopphone);
                        tv_opentime.setText("" + opentime);
                        tv_limitcost.setText("" + limitcost);

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

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.but_sas_photo:
                ll_popup.startAnimation(AnimationUtils.loadAnimation(SAppShopActivity.this, R.anim.slide_bottom_to_top));
                pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
                break;

            default:
                break;
        }
    }


}
