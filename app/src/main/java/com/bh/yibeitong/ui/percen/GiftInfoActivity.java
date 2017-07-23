package com.bh.yibeitong.ui.percen;

import android.content.Intent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.percen.GiftInfo;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by jingang on 2017/7/7.
 * 礼品详情
 */

public class GiftInfoActivity extends BaseTextActivity {
    /*接收页面传值*/
    private Intent intent;
    private String giftid, s_jifen;

    /*接口地址*/
    private String PATH = "";

    /*UI显示*/
    private ImageView iv_img;
    private TextView tv_title, tv_score, tv_ex;
    private String img, title, score, content;

    /*礼品详情（简介）*/
    private WebView contentWeb;
    private WebSettings webSettings;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_giftinfo);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("礼品详情");
        setTitleBack(true, 0);
        initData();
    }

    public void initData() {
        intent = getIntent();
        giftid = intent.getStringExtra("giftid");
        s_jifen = intent.getStringExtra("jifen");

        iv_img = (ImageView) findViewById(R.id.iv_giftinfo_img);
        tv_title = (TextView) findViewById(R.id.tv_giftinfo_title);
        tv_score = (TextView) findViewById(R.id.tv_giftinfo_score);
        //tv_content = (TextView) findViewById(R.id.tv_giftinfo_content);

        tv_ex = (TextView) findViewById(R.id.tv_giftinfo_ex);
        tv_ex.setOnClickListener(this);

        getGiftInfo(giftid);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_giftinfo_ex:
                if ((int) Double.parseDouble(s_jifen) >= Integer.parseInt(score)) {
                    //当前积分大于或等于兑换积分时  进行兑换
                    //toast("兑换");

                    Intent intent = new Intent(GiftInfoActivity.this, ExChangeAddressActivity.class);
                    intent.putExtra("giftid",giftid);
                    startActivity(intent);

                    GiftInfoActivity.this.finish();
                }
                break;

            default:
                break;
        }
    }

    public void getGiftInfo(final String giftid) {
        PATH = HttpPath.PATH + HttpPath.GIFT_INFO + "id=" + giftid;

        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("礼品详情" + result);
                        GiftInfo giftInfo = GsonUtil.gsonIntance().gsonToBean(result, GiftInfo.class);
                        img = giftInfo.getMsg().getGiftinfo().getImg();
                        title = giftInfo.getMsg().getGiftinfo().getTitle();
                        score = giftInfo.getMsg().getGiftinfo().getScore();
                        content = giftInfo.getMsg().getGiftinfo().getContent();

                        if (img.equals("")) {
                            iv_img.setImageResource(R.mipmap.yibeitong001);

                        } else {
                            x.image().bind(iv_img, "http://www.ybt9.com/" + img);

                        }
                        tv_title.setText("" + title);
                        tv_score.setText("" + score);
//                        tv_content.setText("" + content);

                        if ((int) Double.parseDouble(s_jifen) >= Integer.parseInt(score)) {
                            tv_ex.setBackgroundResource(R.drawable.button_red_shape);
                            tv_ex.setText("立即兑换");
                        } else {
                            tv_ex.setBackgroundResource(R.drawable.button_gray_shape);
                            tv_ex.setText("积分不足");
                        }

                        getWebHTML(content.toString());

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
     * @param html_bady
     */
    public void getWebHTML(String html_bady) {
        contentWeb = (WebView) findViewById(R.id.wv_giftinfo);
        contentWeb.getSettings().setJavaScriptEnabled(true);
        webSettings = contentWeb.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(false);
        String baseUrl = "http://www.ybt9.com/";
        //拼接HTML
        String css = "<style type=\"text/css\"> img {" +
                "width:100%;" +
                "height:auto;" +
                "}" +
                "body {" +
                "margin-right:15px;" +
                "margin-left:15px;" +
                "margin-top:15px;" +
                "font-size:45px;" +
                "}" +
                "</style>";
        String html = "<html><header>" + css + "</header><body>" + html_bady + "</body></html>";
        contentWeb.loadDataWithBaseURL(baseUrl, html, "text/html", "utf-8", null);

        System.out.println(""+html);

    }

}
