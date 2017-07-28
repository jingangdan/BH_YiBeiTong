package com.bh.yibeitong.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bh.yibeitong.R;
import com.bh.yibeitong.actitvity.ActivityCollector;
import com.bh.yibeitong.actitvity.MainActivity;
import com.bh.yibeitong.bean.Error;
import com.bh.yibeitong.bean.Register;
import com.bh.yibeitong.ui.QuickLoginActivity;
import com.bh.yibeitong.ui.loginregist.FindCodeActivity;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.view.UserInfo;

import org.xutils.BuildConfig;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by jingang on 2016/11/3.
 * 登录
 */
public class FMLongin extends Fragment implements View.OnClickListener, View.OnLongClickListener {
    private View view;

    // 声明控件对象
    private EditText et_name, et_pass;
    private Button mLoginButton;

    private Button bt_username_clear;
    private Button bt_pwd_clear;
    private Button bt_pwd_eye;
    private TextWatcher username_watcher;
    private TextWatcher password_watcher;

    //使EditText失去焦点
    private LinearLayout lin_login_main;

    private Intent intent;

    //快速登录 找回密码
    private TextView tv_quick_logon, tv_retrieve_pwd;

    UserInfo userInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, null);
        initData();

        x.Ext.init(getActivity().getApplication());
        x.Ext.setDebug(BuildConfig.DEBUG);

        initWatcher();
        et_name.addTextChangedListener(username_watcher);
        et_pass.addTextChangedListener(password_watcher);

        mLoginButton = (Button) view.findViewById(R.id.login);

        mLoginButton.setOnClickListener(this);

        lin_login_main = (LinearLayout) view.findViewById(R.id.lin_login_main);

        lin_login_main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(et_name.getWindowToken(), 0);
                return true;
            }
        });

        return view;
    }

    public static FMLongin newInstance(String name) {
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        FMLongin fragment = new FMLongin();
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * 组件 初始化
     */
    public void initData() {
        userInfo = new UserInfo(getActivity().getApplication());
        tv_quick_logon = (TextView) view.findViewById(R.id.tv_quick_logon);
        tv_retrieve_pwd = (TextView) view.findViewById(R.id.tv_retrieve_pwd);

        tv_quick_logon.setOnClickListener(this);
        tv_retrieve_pwd.setOnClickListener(this);

        et_name = (EditText) view.findViewById(R.id.username);
        et_pass = (EditText) view.findViewById(R.id.password);

        bt_username_clear = (Button) view.findViewById(R.id.bt_username_clear);
        bt_pwd_clear = (Button) view.findViewById(R.id.bt_pwd_clear);
        bt_pwd_eye = (Button) view.findViewById(R.id.bt_pwd_eye);
        bt_username_clear.setOnClickListener(this);
        bt_pwd_clear.setOnClickListener(this);
        bt_pwd_eye.setOnClickListener(this);


    }

    /**
     * 手机号，密码输入控件公用这一个watcher
     */
    private void initWatcher() {
        username_watcher = new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                et_pass.setText("");
                if (s.toString().length() > 0) {
                    bt_username_clear.setVisibility(View.VISIBLE);
                } else {
                    bt_username_clear.setVisibility(View.INVISIBLE);
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
                    bt_pwd_clear.setVisibility(View.VISIBLE);
                } else {
                    bt_pwd_clear.setVisibility(View.INVISIBLE);
                }
            }
        };
    }

    private String name, pass;

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.login:  //登陆

                name = et_name.getText().toString();
                pass = et_pass.getText().toString();

                if (!name.equals("")) {
                    if (!pass.equals("")) {
                        getLogin(name, pass);
                    } else {
                        Toast.makeText(getActivity(), "密码不可为空！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "账号不可为空！", Toast.LENGTH_SHORT).show();

                }


                break;

            case R.id.bt_username_clear:
                //清除用户名输入
                et_name.setText("");
                et_pass.setText("");

                break;
            case R.id.bt_pwd_clear:
                //清除密码输入
                et_pass.setText("");

                break;
            case R.id.bt_pwd_eye:
                //密码是否可见
                if (et_pass.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                    bt_pwd_eye.setBackgroundResource(R.mipmap.ic_look001);
                    et_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                } else {
                    bt_pwd_eye.setBackgroundResource(R.mipmap.ic_look001);
                    et_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                et_pass.setSelection(et_pass.getText().toString().length());

                break;

            case R.id.tv_quick_logon:
                //快速登录
                startActivity(new Intent(getActivity(), QuickLoginActivity.class));

                break;

            case R.id.tv_retrieve_pwd:
                //找回密码
                //Toast.makeText(getActivity(), "找回密码系统暂未开发", Toast.LENGTH_SHORT).show();

                intent = new Intent(getActivity(), FindCodeActivity.class);
                intent.putExtra("title", "找回密码");
                startActivity(intent);

                break;

            default:
                break;
        }
    }


    private String str_result;
    /**
     * 登录
     *
     * @param uname
     * @param pwd
     */
    public void getLogin(String uname, String pwd) {
        String Path = "http://www.ybt9.com//index.php?ctrl=app&source=1&action=appMemlogin&" +
                "datatype=json&" + "uname=" + uname + "&pwd=" + pwd;

        RequestParams params = new RequestParams(Path);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("onSuccess");

                str_result = result;

                System.out.println("登录成功返回 = " + result);

                Register register = GsonUtil.gsonIntance().gsonToBean(result, Register.class);

                if (register.isError() == true) {
                    //登录失败
                    Toast.makeText(getActivity(), register.getMsg().toString(), Toast.LENGTH_SHORT).show();
                } else if (register.isError() == false) {
                    //登录成功 跳转主界面

                    String pwd = et_pass.getText().toString();

                    userInfo.saveLogin("1");//登录成功 保存数字1

                    userInfo.savePwd(pwd);//保存密码 方便后续操作
                    userInfo.saveCoder("0");

                    userInfo.saveScore(register.getMsg().getScore());//积分

                    //MainActivity.locationService.stop();//停止定位

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("login", result);
                    intent.putExtra("islogin", "true");
                    ActivityCollector.finishAll();
                    startActivity(intent);
                    getActivity().finish();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("onError");

                Error error = GsonUtil.gsonIntance().gsonToBean(str_result, Error.class);
                Toast.makeText(getActivity(), error.getMsg().toString(), Toast.LENGTH_SHORT).show();

//                Toast.makeText(getActivity(), "登录失败,请检查账户和密码是否有误", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCancelled(CancelledException cex) {
                System.out.println("onCancelled");

            }

            @Override
            public void onFinished() {
                System.out.println("onFinished");
            }
        });

    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {

        }
        return true;
    }
}