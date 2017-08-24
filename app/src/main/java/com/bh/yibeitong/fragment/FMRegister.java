package com.bh.yibeitong.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.bh.yibeitong.R;
import com.bh.yibeitong.bean.CheckPhone;
import com.bh.yibeitong.ui.RegisterActivity;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.timebutton.TimeButton;

import org.xutils.BuildConfig;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jingang on 2016/11/3.
 * 注册
 * 1.验证手机号 阅读并同意协议
 * 2.获取验证码
 * 3.输入密码 再次输入密码
 */
public class FMRegister extends Fragment implements View.OnClickListener {
    private View view;

    /**
     * 获取用户输入的信息 手机号 验证码 密码
     */
    private EditText et_input_number;//手机号
    private CheckBox cb_register;//阅读并同意协议
    private EditText et_input_icode;//验证码
    private EditText et_input_pass, et_input_pass_again;//输入密码

    private Button but_input_number;

    /**
     * 清除输入num
     */
    private Button but_input_clearnum, but_input_clearpass, but_input_eye;

    /**
     * 自定义的倒计时button控件
     */
    private TimeButton timeButton;

    private Button bt_getCode;

    private boolean isChange;
    //控制按钮样式是否改变
    private boolean tag = true;
    //每次验证请求需要间隔60S
    private int i = 60;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register, null);

        x.Ext.init(getActivity().getApplication());
        x.Ext.setDebug(BuildConfig.DEBUG);

        initData();

        timeButton = (TimeButton) view.findViewById(R.id.timebutton);
        timeButton.onCreate(savedInstanceState);
        timeButton.setTextAfter("秒后重新获取").setTextBefore("点击获取验证码").setLenght(5 * 1000);
        timeButton.setOnClickListener(this);

        bt_getCode = (Button) view.findViewById(R.id.bt_getCode);
        bt_getCode.setOnClickListener(this);

        return view;
    }
    public static FMRegister newInstance(String name) {
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        FMRegister fragment = new FMRegister();
        fragment.setArguments(bundle);
        return fragment;
    }


    /**
     * 组件 初始化
     */
    public void initData() {
        //输入手机号（用户名） 密码等操作
        et_input_number = (EditText) view.findViewById(R.id.et_input_number);
        but_input_clearnum = (Button) view.findViewById(R.id.but_input_clearnum);

        but_input_clearnum.setOnClickListener(this);

        et_input_pass = (EditText) view.findViewById(R.id.et_input_pass);
        but_input_clearpass = (Button) view.findViewById(R.id.but_input_clearpass);
        but_input_eye = (Button) view.findViewById(R.id.but_input_eye);

        but_input_clearpass.setOnClickListener(this);
        but_input_eye.setOnClickListener(this);

        //
        cb_register = (CheckBox) view.findViewById(R.id.cb_register);
        et_input_icode = (EditText) view.findViewById(R.id.et_input_icode);
        et_input_pass_again = (EditText) view.findViewById(R.id.et_input_pass_again);

        but_input_number = (Button) view.findViewById(R.id.but_input_number);
        but_input_number.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        String str_num = et_input_number.getText().toString();

        String str_code = et_input_icode.getText().toString();

        String str_pass = et_input_pass.getText().toString();

        String str_pass_again = et_input_pass_again.getText().toString();

        switch (v.getId()) {
            case R.id.but_input_number:
                //输入手机号
                if (cb_register.isChecked()){
                    if (isMobile(str_num)) {
                        Toast.makeText(getActivity(), "通过验证", Toast.LENGTH_SHORT).show();

                        checkphone(str_num);

                    } else {
                        Toast.makeText(getActivity(), "未通过验证", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(),"需同意协议", Toast.LENGTH_SHORT).show();
                }

                break;

            /*case R.id.but_input_icode:
                //输入验证码
                if (str_code.equals("")) {
                    Toast.makeText(getActivity(), "验证码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    //填写了验证码，进行验证
                    submitVerificationCode("86", str_num, str_code);
                    //setLinVisibility(1);
                }


                break;*/

            /*case R.id.but_input_pass:
                //输入密码
                if (str_pass.length() < 6 && str_pass.length() > 16) {
                    Toast.makeText(getActivity(), "密码长度为6~16位", Toast.LENGTH_SHORT).show();
                } else {
                    if (str_pass_again == str_pass) {
                        //setLinVisibility(2);
                    }

                }

                break;*/

            case R.id.but_input_clearnum:
                //清除num输入
                et_input_number.setText("");

                break;

            case R.id.but_input_clearpass:
                //清除pass输入
                et_input_pass.setText("");
                break;

            case R.id.but_input_eye:
                //密码是否可见
                if (et_input_pass.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                    but_input_eye.setBackgroundResource(R.mipmap.ic_look001);
                    et_input_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                } else {
                    but_input_eye.setBackgroundResource(R.mipmap.ic_look001);
                    et_input_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                et_input_pass.setSelection(et_input_pass.getText().toString().length());

                break;

            case R.id.timebutton:
                Toast.makeText(getActivity(), "点击获取验证码", Toast.LENGTH_SHORT).show();
                //toast("点击获取验证码");
                break;

            default:
                break;
        }


    }

    /**
     * 注册前验证手机号是否已经存在
     *
     * @param phone
     */
    public void checkphone(final String phone) {
        RequestParams params = new RequestParams(
                HttpPath.PATH + HttpPath.CHECKPHONE + "phone=" + phone);

        x.http().post(params, new Callback.CommonCallback<String>() {
            public void onSuccess(String result) {
                System.out.println("成功返回" + result);

                CheckPhone checkPhone = GsonUtil.gsonIntance().gsonToBean(result, CheckPhone.class);

                boolean isExit = checkPhone.getMsg().isExit();

                if (isExit == false) {
                    //手机号可以注册
                    Intent intent = new Intent(getActivity(), RegisterActivity.class);
                    intent.putExtra("phone", phone);
                    startActivity(intent);

                } else {
                    //手机号已经注册
                    Toast.makeText(getActivity(), "该手机号已经注册", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("onError");
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                System.out.println("onCancelled");
            }

            @Override
            public void onFinished() {
                System.out.println("onFinished");
            }
        });

    }

    public void register() {
        RequestParams params = new RequestParams("path");
        x.http().post(params, new Callback.CommonCallback<String>() {

            public void onSuccess(String result) {
                System.out.println("成功返回" + result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("onError");
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                System.out.println("onCancelled");
            }

            @Override
            public void onFinished() {
                System.out.println("onFinished");
            }
        });
    }

    /*
  * 改变按钮样式
  * */
    private void changeBtnGetCode() {

        Thread thread = new Thread() {
            @Override
            public void run() {
                if (tag) {
                    while (i > 0) {
                        i--;
                        //如果活动为空
                        if (getActivity() == null) {
                            break;
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                bt_getCode.setText("获取验证码(" + i + ")");
                                bt_getCode.setClickable(false);
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

                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bt_getCode.setText("获取验证码");
                            bt_getCode.setClickable(true);
                        }
                    });
                }
            }
        };
        thread.start();
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
     * 电话号码验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isPhone(String str) {
        Pattern p1 = null, p2 = null;
        Matcher m = null;
        boolean b = false;
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
        if (str.length() > 9) {
            m = p1.matcher(str);
            b = m.matches();
        } else {
            m = p2.matcher(str);
            b = m.matches();
        }
        return b;
    }

}