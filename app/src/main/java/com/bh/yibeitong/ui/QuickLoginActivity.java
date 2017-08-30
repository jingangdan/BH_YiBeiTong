package com.bh.yibeitong.ui;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.Errors;
import com.bh.yibeitong.bean.Register;
import com.bh.yibeitong.utils.CodeUtils;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.utils.MD5Util;
import com.bh.yibeitong.view.UserInfo;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jingang on 2016/11/19.
 * 快速登录
 */
public class QuickLoginActivity extends BaseTextActivity {
    private EditText et_ql_phone, et_ql_code;
    private Button but_ql_code, but_ql_login;

    String phone, code;

    private boolean isChange;
    //控制按钮样式是否改变
    private boolean tag = true;
    //每次验证请求需要间隔60S
    private int i = 60;

    UserInfo userInfo;

    /*接口地址*/
    private String PATH = "";

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_quick_login);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("快速登录");
        setTitleBack(true, 0);

        initData();
    }

    /**
     * 组件 初始化
     */
    public void initData() {

        userInfo = new UserInfo(getApplication());

        et_ql_phone = (EditText) findViewById(R.id.et_ql_phone);
        et_ql_code = (EditText) findViewById(R.id.et_ql_code);

        but_ql_code = (Button) findViewById(R.id.but_ql_code);
        but_ql_login = (Button) findViewById(R.id.but_ql_login);

        but_ql_code.setOnClickListener(this);
        but_ql_login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        phone = et_ql_phone.getText().toString();
        code = et_ql_code.getText().toString();

        switch (v.getId()) {
            case R.id.but_ql_code:
                //获取验证码
                if (isMobile(phone)) {
                    //手机号格式验证通过
                    Toast.makeText(QuickLoginActivity.this, "手机号验证通过", Toast.LENGTH_SHORT).show();
                    postCode(phone, "4");
                    changeBtnGetCode();

                } else {
                    //手机号格式验证不通过
                    Toast.makeText(QuickLoginActivity.this, "手机号验证不通过", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.but_ql_login:
                if (!phone.equals("")) {
                    if (!code.equals("")) {
                        quickLogin(phone, code);

                    } else {
                        toast("验证码为空");
                    }
                } else {
                    toast("手机号为空");
                }

                //登录
                break;

            default:
                break;
        }
    }

    /**
     * 获取验证码
     *
     * @param phone
     * @param type
     */
    public void postCode(String phone, String type) {
        PATH = HttpPath.PATH + HttpPath.SENDREGPHONE + "phone=" + phone + "&type=" + type;

        PATH = HttpPath.PATH_HEAD+HttpPath.PATH_DATA+(System.currentTimeMillis()/1000)+"&"+HttpPath.SENDREGPHONE+
                "phone=" + phone + "&type=" + type+
                "&sign="+ MD5Util.getMD5String(HttpPath.PATH_DATA+(System.currentTimeMillis()/1000)+"&"+HttpPath.SENDREGPHONE+
                "phone=" + phone + "&type="+ type+"&"
                +HttpPath.PATH_BAIHAI);

        System.out.println("加密"+HttpPath.PATH_DATA_MD5+HttpPath.SENDREGPHONE+
                "phone=" + phone + "&type="+ type+"&"
                +HttpPath.PATH_BAIHAI);

        System.out.println("获取验证码"+PATH);
        RequestParams params = new RequestParams(PATH);
        x.http().post(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("获取验证码 = " + result);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        System.out.println("获取验证码onError");
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
    }

    private String str_result;

    /**
     * 快速登录
     *
     * @param phone
     * @param code
     */
    public void quickLogin(String phone, final String code) {
        String Path = HttpPath.PATH + HttpPath.QUICK_LOGIN + "phone=" + phone + "&code=" + code;

        RequestParams params = new RequestParams(Path);
        x.http().post(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        str_result = result;
                        System.out.println("快速登录" + result);

                        Register register = GsonUtil.gsonIntance().gsonToBean(result, Register.class);
                        if (register.isError() == true) {
                            //登录失败
                            System.out.println("登录失败" + register.getMsg().toString());
                            Toast.makeText(QuickLoginActivity.this, register.getMsg().toString(), Toast.LENGTH_SHORT).show();
                        } else if (register.isError() == false) {
                            //登录成功 跳转主界面


                            userInfo.saveLogin("1");//登录成功 保存数字1

                            userInfo.savePwd("");//保存密码 方便后续操作
                            userInfo.saveCoder("1");

                            userInfo.saveScore(register.getMsg().getScore());//积分
                            userInfo.saveUserInfo(result);

                            Intent intent = new Intent();
                            intent.putExtra("jingang", "1");
                            setResult(CodeUtils.REQUEST_CODE_QUICK_LOGIN, intent);
                            QuickLoginActivity.this.finish();

//                            userInfo.saveCoder("1");//保存数字1
//                            userInfo.saveLogin("1");//登录成功 保存数字1
//                            userInfo.saveUserInfo(result);
//                            userInfo.savePwd("");//保存密码 方便后续操作
//                            userInfo.saveScore(register.getMsg().getScore());//积分
//
//                            Intent intent = new Intent();
//                            intent.putExtra("jingang", "1");
//                            setResult(CodeUtils.REQUEST_CODE_QUICK_LOGIN, intent);
//                            QuickLoginActivity.this.finish();

//                            Intent intent = new Intent(QuickLoginActivity.this, MainActivity.class);
//                            intent.putExtra("login", result);
//
//                            QuickLoginActivity.this.finish();//不知道为什么 这家伙没有销毁掉。。。
//                            ActivityCollector.finishAll();
//                            startActivity(intent);
                        }

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Errors error = GsonUtil.gsonIntance().gsonToBean(str_result, Errors.class);
                        Toast.makeText(QuickLoginActivity.this, error.getMsg().toString(), Toast.LENGTH_SHORT).show();
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
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 改变按钮样式
     */
    private void changeBtnGetCode() {

        Thread thread = new Thread() {
            @Override
            public void run() {
                if (tag) {
                    while (i > 0) {
                        i--;
                        //如果活动为空
                        if (this == null) {
                            break;
                        }

                        QuickLoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                but_ql_code.setText("获取验证码(" + i + ")");
                                but_ql_code.setClickable(false);
                            }
                        });

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    tag = false;
                }
                i = 60;
                tag = true;

                if (this != null) {
                    QuickLoginActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            but_ql_code.setText("获取验证码");
                            but_ql_code.setClickable(true);
                        }
                    });
                }
            }
        };
        thread.start();
    }

}
