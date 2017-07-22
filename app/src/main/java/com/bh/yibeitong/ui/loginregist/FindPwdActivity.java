package com.bh.yibeitong.ui.loginregist;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bh.yibeitong.R;
import com.bh.yibeitong.actitvity.ActivityCollector;
import com.bh.yibeitong.actitvity.MainActivity;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.Error;
import com.bh.yibeitong.bean.Register;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.UserInfo;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by jingang on 2017/7/21.
 * 找回密码
 */

public class FindPwdActivity extends BaseTextActivity {
    private EditText et_input_code, et_input_uname, et_input_email, et_input_pwd, et_input_pwd_again;
    private Button but_getcode, but_register;

    //获取客户端输入内容
    private String code, uname, email, pwd, pwd2;

    /*接收页面传值*/
    private Intent intent;
    private String title, phone;


    private boolean isChange;
    //控制按钮样式是否改变
    private boolean tag = true;
    //每次验证请求需要间隔60S
    private int i = 60;

    /*本地轻量型缓存*/
    private UserInfo userInfo;

    /*接口地址*/
    private String PATH = "";

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        initData();
        setTitleName("" + title);
        setTitleBack(true, 0);

    }

    /**
     * 组件 初始化
     */
    public void initData() {
        intent = getIntent();
        phone = intent.getStringExtra("phone");
        title = intent.getStringExtra("title");

        userInfo = new UserInfo(getApplication());

        et_input_code = (EditText) findViewById(R.id.et_input_code);
        et_input_uname = (EditText) findViewById(R.id.et_input_uname);
        et_input_email = (EditText) findViewById(R.id.et_input_email);
        et_input_pwd = (EditText) findViewById(R.id.et_input_pwd);
        et_input_pwd_again = (EditText) findViewById(R.id.et_input_pwd_again);

        but_getcode = (Button) findViewById(R.id.but_getcode);
        but_getcode.setOnClickListener(this);

        but_register = (Button) findViewById(R.id.but_register);
        but_register.setOnClickListener(this);

        but_register.setText("" + title);
    }

    @Override
    public void onClick(View v) {
        code = et_input_code.getText().toString();
        uname = et_input_uname.getText().toString();
        email = et_input_email.getText().toString();
        pwd = et_input_pwd.getText().toString();
        pwd2 = et_input_pwd_again.getText().toString();

        super.onClick(v);
        switch (v.getId()) {
            case R.id.but_getcode:
                //获取验证码
                changeBtnGetCode();
                postCode(phone, "2");

                break;

            case R.id.but_register:
                //注册
                if (code.equals("")) {
                    //验证码为空
                    toast("信息填写不完整");
                    //Toast.makeText(RegisterActivity.this, "信息填写不完整", Toast.LENGTH_SHORT).show();
                } else if (pwd.equals("")) {

                    toast("信息填写不完整");
                    //Toast.makeText(RegisterActivity.this, "信息填写不完整", Toast.LENGTH_SHORT).show();

                } else if (pwd2.equals("") && pwd2 == pwd) {
                    toast("信息填写不完整");
                    //Toast.makeText(cRegisterActivity.this, "信息填写不完整", Toast.LENGTH_SHORT).show();

                } else {

                    //postReg(phone, code, phone, "", pwd, pwd2);

                    findPwd(phone, pwd, pwd2, code);
                }

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
        RequestParams params = new RequestParams(
                HttpPath.PATH + HttpPath.SENDREGPHONE + "phone=" + phone + "&type=" + type);
        x.http().post(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("获取验证码 = " + result);



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

    private String str_result;

    /**
     * 注册
     *
     * @param phone
     * @param code
     * @param uname
     * @param email
     * @param pwd
     * @param pwd2
     */
    public void postReg(String phone, String code, String uname, String email, String pwd, String pwd2) {
        RequestParams params_reg = new RequestParams(HttpPath.PATH + HttpPath.REG +
                "phone=" + phone + "&code=" + code + "&email=" + email + "&tname=" + uname + "&pwd=" + pwd + "&pwd2=" + pwd2);

        x.http().post(params_reg,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("注册返回" + result);

                        str_result = result;

                        Register register = GsonUtil.gsonIntance().gsonToBean(result, Register.class);
                        if (register.isError() == true) {
                            //登录失败
                            System.out.println("注册失败" + register.getMsg().toString());
                            toast("" + register.getMsg().toString());
                            // Toast.makeText(RegisterActivity.this, register.getMsg().toString(), Toast.LENGTH_SHORT).show();
                        } else if (register.isError() == false) {
                            //登录成功 跳转主界面

                            String pwd = et_input_pwd_again.getText().toString();
                            userInfo.saveLogin("1");//登录成功 保存数字1

                            userInfo.savePwd(pwd);//保存密码 方便后续操作
                            userInfo.saveCoder("0");

                            intent = new Intent(FindPwdActivity.this, MainActivity.class);
                            intent.putExtra("login", result);

                            intent.putExtra("islogin", "true");
                            FindPwdActivity.this.finish();
                            ActivityCollector.finishAll();
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
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

    /**
     * 找回密码
     * @param phone
     * @param newpwd
     * @param surepwd
     * @param code
     */
    public void findPwd(String phone, String newpwd, String surepwd, String code) {
        PATH = HttpPath.PATH+HttpPath.LR_FINDPWD+
                "phone="+phone+"&newpwd="+newpwd+"&surepwd="+surepwd+"&code="+code;

        RequestParams params = new RequestParams(PATH);

        System.out.println("找回密码"+PATH);

        x.http().post(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("找回密码"+result);

                        Error error = GsonUtil.gsonIntance().gsonToBean(result, Error.class);

                        if(error.isError() == false){
                            toast(""+error.getMsg().toString());
                            FindPwdActivity.this.finish();
                        }else{
                            toast("操作失败，请重试");
                        }
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

                        FindPwdActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                but_getcode.setText("获取验证码(" + i + ")");
                                but_getcode.setClickable(false);
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
                    FindPwdActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            but_getcode.setText("获取验证码");
                            but_getcode.setClickable(true);
                        }
                    });
                }
            }
        };
        thread.start();
    }
}
