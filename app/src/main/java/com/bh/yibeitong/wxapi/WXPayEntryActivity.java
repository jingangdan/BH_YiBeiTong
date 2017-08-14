package com.bh.yibeitong.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by jingang on 2017/1/7.
 * 微信支付
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.pay_result);
        api = WXAPIFactory.createWXAPI(this, "wxb8a68c0129244502");
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {

        int code = resp.errCode;

        switch (code) {
            case 0:
                //支付成功后的界面
                Toast.makeText(this, "支付成功！", Toast.LENGTH_LONG).show();
                finish();
                break;
            case -1:
                Toast.makeText(this,
                        "签名错误、未注册APPID、" +
                                "项目设置APPID不正确、" +
                                 "注册的APPID与设置的不匹配、" +
                                "您的微信账号异常等。",
                        Toast.LENGTH_SHORT).show();
                finish();
                break;
            case -2:
                //用户取消支付后的界面
                Toast.makeText(this, "用户取消支付", Toast.LENGTH_SHORT).show();
                finish();
                break;


        }
        //finish();
        //微信支付后续操作，失败，成功，取消
    }
}