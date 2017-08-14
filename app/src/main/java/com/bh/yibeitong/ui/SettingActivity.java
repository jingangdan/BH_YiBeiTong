package com.bh.yibeitong.ui;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.actitvity.ActivityCollector;
import com.bh.yibeitong.actitvity.MainActivity;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.utils.CodeUtils;
import com.bh.yibeitong.view.UserInfo;

public class SettingActivity extends BaseTextActivity{
    private TextView tv_num, tv_phone, tv_member;
    private Button but_log_off;

    private UserInfo userInfo;

    private Intent intent;
    String username, phone, member;
    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_setting);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("设置");
        setTitleBack(true, 0);

        initData();

        intent = getIntent();
        username = intent.getStringExtra("username");
        phone = intent.getStringExtra("phone");
        member = intent.getStringExtra("member");

        tv_num.setText(username);
        tv_phone.setText(phone);
        tv_member.setText(member);

    }

    /**
     * 组件 初始化
     */
    public void initData(){
        userInfo = new UserInfo(getApplication());
        but_log_off = (Button) findViewById(R.id.but_log_off);
        but_log_off.setOnClickListener(this);

        tv_num = (TextView) findViewById(R.id.tv_setting_num);
        tv_phone = (TextView) findViewById(R.id.tv_setting_phone);
        tv_member = (TextView) findViewById(R.id.tv_setting_member);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()){
            case R.id.but_log_off:
                userInfo.saveUserInfo("");//清空本地保存的个人信息
                userInfo.saveLogin("0");//退出登录保存数字0
                //userInfo.saveCoder("");//保存 空
                userInfo.savePwd("");//密码

                userInfo.saveScore("");//积分

                Intent intent = new Intent();
                setResult(CodeUtils.REQUEST_CODE_SETTING, intent);
                this.finish();


//                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
//                intent.putExtra("islogin", false);
//                ActivityCollector.finishAll();
//                startActivity(intent);
//                SettingActivity.this.finish();

                break;

            default:
                break;
        }
    }
}




/*package com.bh.yibeitong.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bh.yibeitong.R;
import com.bh.yibeitong.actitvity.ActivityCollector;
import com.bh.yibeitong.actitvity.MainActivity;
import com.bh.yibeitong.base.BaseActivity;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.ui.setting.AboutActivity;
import com.bh.yibeitong.ui.setting.CommonActivity;
import com.bh.yibeitong.ui.setting.DownloadActivity;
import com.bh.yibeitong.ui.setting.FeedbackActivity;
import com.bh.yibeitong.ui.setting.ImageQualityActivity;
import com.bh.yibeitong.ui.setting.NotifyActivity;
import com.bh.yibeitong.view.UserInfos;

*//**
 * Created by jingang on 2016/10/19.
 * 设置
 *//*
public class SettingActivity extends BaseTextActivity {
    private RelativeLayout rel_setting_information, rel_setting_notify,
            rel_setting_common, rel_setting_image, rel_setting_download,
            rel_setting_dabout, rel_setting_Feedback;

    private Button but_log_off;

    UserInfos userInfo;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_setting);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("设置");
        setTitleBack(true, 0);
        initData();

    }


    *//**
     * 组件 初始化
     *//*
    public void initData() {
        userInfo = new UserInfos(getApplication());

        rel_setting_information = (RelativeLayout) findViewById(R.id.rel_setting_information);
        rel_setting_notify = (RelativeLayout) findViewById(R.id.rel_setting_notify);
        rel_setting_common = (RelativeLayout) findViewById(R.id.rel_setting_common);
        rel_setting_image = (RelativeLayout) findViewById(R.id.rel_setting_image);
        rel_setting_download = (RelativeLayout) findViewById(R.id.rel_setting_download);
        rel_setting_dabout = (RelativeLayout) findViewById(R.id.rel_setting_dabout);
        rel_setting_Feedback = (RelativeLayout) findViewById(R.id.rel_setting_Feedback);

        rel_setting_information.setOnClickListener(this);
        rel_setting_notify.setOnClickListener(this);
        rel_setting_common.setOnClickListener(this);
        rel_setting_image.setOnClickListener(this);
        rel_setting_download.setOnClickListener(this);
        rel_setting_dabout.setOnClickListener(this);
        rel_setting_Feedback.setOnClickListener(this);

        //退出登录
        but_log_off = (Button) findViewById(R.id.but_log_off);
        but_log_off.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.rel_setting_information:
                //个人信息
                break;

            case R.id.rel_setting_notify:
                //通知消息
                startActivity(new Intent(SettingActivity.this, NotifyActivity.class));

                break;

            case R.id.rel_setting_common:
                //通用设置
                startActivity(new Intent(SettingActivity.this, CommonActivity.class));
                break;

            case R.id.rel_setting_image:
                //图片质量
                startActivity(new Intent(SettingActivity.this, ImageQualityActivity.class));
                break;

            case R.id.rel_setting_download:
                //自动下载最新安装包
                startActivity(new Intent(SettingActivity.this, DownloadActivity.class));
                break;
            case R.id.rel_setting_dabout:
                //关于我们
                startActivity(new Intent(SettingActivity.this, AboutActivity.class));
                break;

            case R.id.rel_setting_Feedback:
                //意见反馈
                startActivity(new Intent(SettingActivity.this, FeedbackActivity.class));
                break;

            case R.id.but_log_off:
                //退出登录
                userInfo.saveUserInfo("");//清空本地保存的个人信息

                userInfo.saveLogin("0");//退出登录保存数字0

                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                intent.putExtra("islogin", false);
                ActivityCollector.finishAll();
                startActivity(intent);
                SettingActivity.this.finish();


                break;


            default:
                break;
        }

    }
}*/
