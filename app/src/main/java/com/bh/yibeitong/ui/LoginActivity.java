package com.bh.yibeitong.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bh.yibeitong.R;
import com.bh.yibeitong.actitvity.MainActivity;
import com.bh.yibeitong.base.BaseActivity;

/**
 * Created by jingang on 2016/10/18.
 * 登录
 */

/**
 * 注      册： ValidatePhoneNumActivity  -->  RegisterTestActivity
 * <p>
 * 忘记密码   ForgetCodeActivity        -->  RepasswordActivity
 *
 * @author jingang
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, View.OnLongClickListener {
    // 声明控件对象
    private EditText et_name, et_pass;
    private Button mLoginButton, mLoginError, mRegister;
    int selectIndex = 1;
    int tempSelect = selectIndex;
    boolean isReLogin = false;
    private int SERVER_FLAG = 0;
    private RelativeLayout countryselect;
    private TextView coutry_phone_sn, coutryName;
    // private String [] coutry_phone_sn_array,coutry_name_array;

    public final static int LOGIN_ENABLE = 0x01;    //注册完毕了
    public final static int LOGIN_UNABLE = 0x02;    //注册完毕了
    public final static int PASS_ERROR = 0x03;      //注册完毕了
    public final static int NAME_ERROR = 0x04;      //注册完毕了

    final Handler UiMangerHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case LOGIN_ENABLE:
                    mLoginButton.setClickable(true);
                    //    mLoginButton.setText(R.string.login);
                    break;
                case LOGIN_UNABLE:
                    mLoginButton.setClickable(false);
                    break;
                case PASS_ERROR:

                    break;
                case NAME_ERROR:
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private Button bt_username_clear;
    private Button bt_pwd_clear;
    private Button bt_pwd_eye;
    private TextWatcher username_watcher;
    private TextWatcher password_watcher;

    //使EditText失去焦点
    private LinearLayout lin_login_main;

    private Intent intent;

    private SharedPreferences sp;
    private String userName, passWord;

    /**
     * 销毁当层 返回上一层
     */
    private TextView tv_login_back;

    /**
     * 快速注册
     */
    private TextView tv_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //初始化SP
        sp = getSharedPreferences("config", MODE_PRIVATE);


        //  //不显示系统的标题栏
        //  getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //    WindowManager.LayoutParams.FLAG_FULLSCREEN );

        setContentView(R.layout.activity_login);
        et_name = (EditText) findViewById(R.id.username);
        et_pass = (EditText) findViewById(R.id.password);

        bt_username_clear = (Button) findViewById(R.id.bt_username_clear);
        bt_pwd_clear = (Button) findViewById(R.id.bt_pwd_clear);
        bt_pwd_eye = (Button) findViewById(R.id.bt_pwd_eye);
        bt_username_clear.setOnClickListener(this);
        bt_pwd_clear.setOnClickListener(this);
        bt_pwd_eye.setOnClickListener(this);
        initWatcher();
        et_name.addTextChangedListener(username_watcher);
        et_pass.addTextChangedListener(password_watcher);

        mLoginButton = (Button) findViewById(R.id.login);
        mLoginError = (Button) findViewById(R.id.login_error);
        mRegister = (Button) findViewById(R.id.register);

        mLoginButton.setOnClickListener(this);
        mLoginError.setOnClickListener(this);
        mRegister.setOnClickListener(this);

        //  countryselect=(RelativeLayout) findViewById(R.id.countryselect_layout);
        //  countryselect.setOnClickListener(this);
        //  coutry_phone_sn=(TextView) findViewById(R.id.contry_sn);
        //  coutryName=(TextView) findViewById(R.id.country_name);

        //  coutryName.setText(coutry_name_array[selectIndex]);
        // 默认为1
        //  coutry_phone_sn.setText("+"+coutry_phone_sn_array[selectIndex]);

        lin_login_main = (LinearLayout) findViewById(R.id.lin_login_main);

        lin_login_main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager inputMethodManager = (InputMethodManager) getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(et_name.getWindowToken(), 0);
                return true;
            }
        });

        tv_login_back = (TextView) findViewById(R.id.tv_login_back);
        tv_login_back.setOnClickListener(this);

        tv_register = (TextView) findViewById(R.id.tv_register);
        tv_register.setOnClickListener(this);


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
                //   login();
                //startActivity(MainActivity.class);

                name = et_name.getText().toString();
                pass = et_pass.getText().toString();

                if (name.equals("888") && pass.equals("888")) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "账号为888， 密码为888", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.login_error:
                //无法登陆(忘记密码了吧)
                //   Intent login_error_intent=new Intent();
                //   login_error_intent.setClass(LoginActivity.this, ForgetCodeActivity.class);
                //   startActivity(login_error_intent);
                break;
            case R.id.register:
                //注册新的用户
                //   Intent intent=new Intent();
                //   intent.setClass(LoginActivity.this, ValidatePhoneNumActivity.class);
                //   startActivity(intent);

                //startActivity(new Intent(LoginActivity.this, RegisterTestActivity.class));

                break;

            case R.id.bt_username_clear:
                et_name.setText("");
                et_pass.setText("");
                break;
            case R.id.bt_pwd_clear:
                et_pass.setText("");
                break;
            case R.id.bt_pwd_eye:
                //密码是否可见
                if (et_pass.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                    bt_pwd_eye.setBackgroundResource(R.mipmap.ic_launcher);
                    et_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                } else {
                    bt_pwd_eye.setBackgroundResource(R.mipmap.ic_launcher);
                    et_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                et_pass.setSelection(et_pass.getText().toString().length());
                break;

            case R.id.tv_login_back:
                finish();

                break;

            case R.id.tv_register:
                //startActivity(new Intent(LoginActivity.this, RegisterTestActivity.class));

                break;

            default:
                break;

        }
    }

    /**
     * 登陆
     */
    private void login() {
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {

        }
        return true;
    }


}
