package com.bh.yibeitong.actitvity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseActivity;
import com.bh.yibeitong.seller.activity.SellerActivity;
import com.bh.yibeitong.view.UserInfo;

/**
 * Created by jingang on 2016/10/17.
 *
 */
public class StartActivity extends BaseActivity {
    private SharedPreferences sharedPreferences;
    private String user;//判断用户是否为首次进入

    //判断是否登录（商家端）
    UserInfo userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_start);

        userInfo = new UserInfo(getApplication());

        sharedPreferences = getSharedPreferences("config", 0);
        user = sharedPreferences.getString("first", "1");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    //跳转到主界面
                    //startActivity(new Intent(TestActivity.this, MainActivity.class));

                    //跳转到登录界面
                    /*startActivity(new Intent(TestActivity.this, LoginActivity.class));
                    finish();*/

                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
                if (user.equals("1")) {
                    //首次进入
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("first", "0");
                    editor.commit();

                    startActivity(new Intent(StartActivity.this, GuideActivity.class));

                } else {
                    if(userInfo.getLogin().equals("2")){
                        //商家登录
                        startActivity(new Intent(StartActivity.this, SellerActivity.class));
                    }else{
                        startActivity(new Intent(StartActivity.this, MainActivity.class));
                    }

                    /*startActivity(new Intent(StartActivity.this, MainActivity.class));*/
                }

                finish();
            }
        }).start();
    }
}

