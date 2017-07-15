package com.bh.yibeitong.seller.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bh.yibeitong.R;
import com.bh.yibeitong.actitvity.ActivityCollector;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.Error;
import com.bh.yibeitong.bean.seller.SellerLogin;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.UserInfo;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

import org.xutils.BuildConfig;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by jingang on 2017/2/13.
 * 商家登录页面
 */
public class SellerLoginActivity extends BaseTextActivity {

    /*使EditText失去焦点*/
    private LinearLayout lin_login_main;

    /*用户名 密码输入*/
    private EditText et_username, et_password;

    /*获取用户输入的用户名和密码*/
    private String user, pass;

    /*清除用户输入*/
    private Button but_clear_username, but_clear_password;

    /*密码可见*/
    private Button but_psd_eye;

    /*登录*/
    private Button but_sl_login;

    /*接收页面传值*/
    Intent intent;
    String loginType;

    /**/
    UserInfo userInfo;

    //信鸽通知获取的token
    private String str_data = "";

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_seller_login);

    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleBack(true, 0);
        setTitleName("商家登录");

        x.Ext.init(getApplication());
        x.Ext.setDebug(BuildConfig.DEBUG);

        XGPushConfig.enableDebug(this, true);

        //绑定设备注册
        XGPushManager.registerPush(this);

        XGPushManager.registerPush(this, new XGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
                Log.d("TPush", "注册成功，设备token为：" + data);
                str_data = String.valueOf(data);
            }

            @Override
            public void onFail(Object data, int errCode, String msg) {
                Log.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
            }
        });


        initData();

        initWatcher();//便于清除按钮的错作

    }

    /*组件 初始化*/
    public void initData() {
        userInfo = new UserInfo(getApplication());

        lin_login_main = (LinearLayout) findViewById(R.id.lin_sligon_main);

        et_username = (EditText) findViewById(R.id.et_sl_username);
        et_password = (EditText) findViewById(R.id.et_sl_password);

        but_clear_username = (Button) findViewById(R.id.but_sl_username_clear);
        but_clear_password = (Button) findViewById(R.id.but_sl_pwd_clear);

        but_clear_username.setOnClickListener(this);
        but_clear_password.setOnClickListener(this);

        but_psd_eye = (Button) findViewById(R.id.but_sl_pwd_eye);
        but_psd_eye.setOnClickListener(this);

        but_sl_login = (Button) findViewById(R.id.but_sl_login);
        but_sl_login.setOnClickListener(this);

        //取消软键盘焦点
        lin_login_main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(et_username.getWindowToken(), 0);
                return true;
            }
        });

        intent = getIntent();
        loginType = intent.getStringExtra("loginType");

    }

    @Override
    protected void onPause() {
        super.onPause();
        XGPushManager.onActivityStoped(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.but_sl_username_clear:
                //清除用户名输入
                et_username.setText("");
                et_password.setText("");
                break;

            case R.id.but_sl_pwd_clear:
                //清除密码输入
                et_password.setText("");
                break;

            case R.id.but_sl_pwd_eye:

                //密码是否可见
                if (et_password.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                    but_psd_eye.setBackgroundResource(R.mipmap.ic_look001);
                    et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                } else {
                    but_psd_eye.setBackgroundResource(R.mipmap.ic_look001);
                    et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                et_password.setSelection(et_password.getText().toString().length());

                break;

            case R.id.but_sl_login:

                //绑定设备注册
                XGPushManager.registerPush(this);

                XGPushManager.registerPush(this, new XGIOperateCallback() {
                    @Override
                    public void onSuccess(Object data, int flag) {
                        Log.d("TPush", "注册成功，设备token为：" + data);
                        str_data = String.valueOf(data);
                    }

                    @Override
                    public void onFail(Object data, int errCode, String msg) {
                        Log.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
                    }
                });

                //登录
                user = et_username.getText().toString();
                pass = et_password.getText().toString();

                if (!user.equals("")) {
                    if (!pass.equals("")) {
                        //login(user, pass, "", str_data, "");//登录
                        if (!str_data.equals("")) {
                            login(user, pass, "", str_data, "");//登录
                        } else {
                            toast("token申请不成功，请重新登录" + str_data);
                            XGPushManager.registerPush(this, new XGIOperateCallback() {
                                @Override
                                public void onSuccess(Object data, int flag) {
                                    Log.d("TPush", "注册成功，设备token为：" + data);
                                    str_data = String.valueOf(data);
                                }

                                @Override
                                public void onFail(Object data, int errCode, String msg) {
                                    Log.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
                                }
                            });
                        }

                    } else {
                        toast("密码不可为空！");
                    }
                } else {
                    toast("账号不可为空！");
                }


                break;


            default:
                break;

        }

    }

    private void getToken() {

    }

    //daf4d24fc6b4da1d4d90be6360542e9085770fe0

//    class MyThread implements Runnable {
//        private boolean flag = true;
//
//        @Override
//        public void run() {
//            while (flag) {
//                if (str_data.equals("")) {
//                    System.out.println("11");
//                    XGPushManager.registerPush(SellerLoginActivity.this, new XGIOperateCallback() {
//                        @Override
//                        public void onSuccess(Object data, int flag) {
//                            Log.d("TPush", "注册成功，设备token为：" + data);
//                            str_data = String.valueOf(data);
//                        }
//
//                        @Override
//                        public void onFail(Object data, int errCode, String msg) {
//                            Log.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
//                        }
//                    });
//                } else {
//                    flag = false;
//                    System.out.println("2222");
//                }
//            }
//        }
//    }


    //注册TextWatcher类型的用户名和密码
    private TextWatcher username_watcher, password_watcher;

    /**
     * 手机号，密码输入控件公用这一个watcher
     * <p>
     * 用户名、密码共用一个watcher 便于对清除按钮的错作
     */
    private void initWatcher() {
        username_watcher = new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                et_password.setText("");
                if (s.toString().length() > 0) {
                    but_clear_username.setVisibility(View.VISIBLE);
                } else {
                    but_clear_username.setVisibility(View.INVISIBLE);
                }
            }
        };

        password_watcher = new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    but_clear_password.setVisibility(View.VISIBLE);
                } else but_clear_password.setVisibility(View.INVISIBLE);
            }
        };
    }

    /**/
    private String str_result = "";

    /**
     * 商家登录
     * <p>
     * uname	是	string	用户名
     * pwd	是	string	密码
     * showtype	否	string	模式普通店铺 shop
     * mDeviceID	是	string
     * userid	是	string
     */
    public void login(String username, final String pwd, String showtype, String mDevice, String userid) {
        String PATH = HttpPath.PATH + HttpPath.APP_LOGIN + "" +
                "uname=" + username + "&pwd=" + pwd +
                "&showtype=shop" + showtype + "&mDeviceID=" + mDevice + "&userid=" + userid;

        RequestParams params = new RequestParams(PATH);

        System.out.println("" + PATH);
        x.http().post(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        str_result = result;
                        System.out.println("商家登录成功" + result);
                        SellerLogin sellerLogin = GsonUtil.gsonIntance().gsonToBean(result, SellerLogin.class);

                        String username = sellerLogin.getMsg().getUsername();
                        String uid = sellerLogin.getMsg().getUid();
                        String shopid = sellerLogin.getMsg().getShopid();

                        if (sellerLogin.isError() == false) {
                            //登录成功
                            userInfo.saveLogin("2");//商家登录成功 保存数字2
                            userInfo.saveCoder("0");//登录类型 0 密码登录 否则 手机号快速登录
                            userInfo.savePwd(pwd);
                            userInfo.saveUserInfo(result);

                            userInfo.saveSellerUserAccoun(""+username+uid);

                            Intent intent = new Intent(SellerLoginActivity.this, SellerActivity.class);
                            intent.putExtra("UserAccount", username + uid);
                            intent.putExtra("shopid", shopid);

                            startActivity(intent);

                            //startActivity(new Intent(SellerLoginActivity.this, SellerActivity.class));

                            ActivityCollector.finishAll();//销毁所有Activity
                            SellerLoginActivity.this.finish();

                        }

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        //System.out.println("商家登录失败");

                        Error error = GsonUtil.gsonIntance().gsonToBean(str_result, Error.class);

                        toast(error.getMsg().toString());
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