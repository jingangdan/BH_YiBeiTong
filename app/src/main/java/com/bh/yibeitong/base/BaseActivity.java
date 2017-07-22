package com.bh.yibeitong.base;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.bh.yibeitong.actitvity.ActivityCollector;
import com.bh.yibeitong.application.CatchExcep;

/**
 * Created by jingang on 2016/10/18.
 * Activity 基类
 */

public class BaseActivity extends FragmentActivity {
    private Toast mToast;

    private CatchExcep application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);

        /*application = (CatchExcep)getApplication();
        application.init();
        application.addActivity(this);*/

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }


    /**
     * <strong>AlertDialog with one button.</strong><br>
     * The difference between the method "alert" and "toast" is
     * that "alert" would get focus automatically, and won't
     * disappear until you click the button.
     *
     * @param message The message you wanna show.
     * @return AlertDialog
     */
    protected AlertDialog alert(Object message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message.toString());
        builder.setPositiveButton("确定", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return alertDialog;
    }

    /**
     * As "Toast.makeText(context, text, duration).show()"
     *
     * @param text The message you wanna show.
     * @return Toast
     */
    protected Toast toast(Object text) {
        if (mToast == null) {
            mToast = Toast.makeText(this, text.toString(), Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text.toString());
        }
        mToast.show();
        return mToast;
    }


}
