package com.bh.yibeitong.ui.loginregist;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.CheckPhone;
import com.bh.yibeitong.ui.RegisterActivity;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.timebutton.TimeButton;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jingang on 2017/7/21.
 * 验证手机号 获取验证码
 */

public class FindCodeActivity extends BaseTextActivity {
    /*接收页面传值*/
    private Intent intent;
    private String title = "";

    /**
     * 获取用户输入的信息 手机号 验证码 密码
     */
    private EditText et_input_number;//手机号
    private CheckBox cb_register;//阅读并同意协议

    private Button but_input_number;

    /**
     * 清除输入num
     */
    private Button but_input_clearnum;

    /**
     * 自定义的倒计时button控件
     */
    private TimeButton timeButton;

    private Button bt_getCode;

    private boolean isChange;
    //控制按钮样式是否改变
    private boolean tag = true;

    private LinearLayout lin_inc_register_header;//顶部

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.fragment_register);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        initData();

        setTitleName(""+title);
        setTitleBack(true, 0);
    }

    public void initData() {
        intent = getIntent();
        title = intent.getStringExtra("title");

        //输入手机号（用户名） 密码等操作
        et_input_number = (EditText) findViewById(R.id.et_input_number);
        but_input_clearnum = (Button) findViewById(R.id.but_input_clearnum);

        but_input_clearnum.setOnClickListener(this);

        //
        cb_register = (CheckBox) findViewById(R.id.cb_register);

        but_input_number = (Button) findViewById(R.id.but_input_number);
        but_input_number.setOnClickListener(this);

        lin_inc_register_header  = (LinearLayout) findViewById(R.id.lin_inc_register_header);
        lin_inc_register_header.setVisibility(View.VISIBLE);

        but_input_number.setText(""+title);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        String str_num = et_input_number.getText().toString();

        switch (v.getId()) {
            case R.id.but_input_number:
                //输入手机号
                if (cb_register.isChecked()){
                    if (isMobile(str_num)) {
                        toast("通过验证");
                        //Toast.makeText(FindCodeActivity.this, "通过验证", Toast.LENGTH_SHORT).show();

                        checkphone(str_num);

                    } else {
                        toast("未通过验证");
                        //Toast.makeText(getActivity(), "未通过验证", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    toast("需同意协议书");
                    //Toast.makeText(getActivity(),"需同意协议", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.but_input_clearnum:
                //清除num输入
                et_input_number.setText("");

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
                    toast("该手机号还未注册");
                } else {
                    intent = new Intent(FindCodeActivity.this, FindPwdActivity.class);
                    intent.putExtra("phone", phone);
                    intent.putExtra("title", title);
                    startActivity(intent);
                    FindCodeActivity.this.finish();
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

}
