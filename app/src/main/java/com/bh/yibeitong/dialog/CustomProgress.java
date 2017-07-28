package com.bh.yibeitong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bh.yibeitong.R;

/**
 * Created by jingang on 2017/7/19.
 */

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;


/********************************************************************
 * [Summary]
 *       TODO 请在此处简要描述此类所实现的功能。因为这项注释主要是为了在IDE环境中生成tip帮助，务必简明扼要
 * [Remarks]
 *       TODO 请在此处详细描述类的功能、调用方法、注意事项、以及与其它类的关系.
 *******************************************************************/

public class CustomProgress extends Dialog {
    private Context context = null;
    private static CustomProgress customProgressDialog = null;

    public CustomProgress(Context context){
        super(context);
        this.context = context;
    }

    public CustomProgress(Context context, int theme) {
        super(context, theme);
    }

    public static CustomProgress createDialog(Context context){
        customProgressDialog = new CustomProgress(context,R.style.CustomProgressDialog);
        customProgressDialog.setContentView(R.layout.progress_custom);
        customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;

        customProgressDialog.setCanceledOnTouchOutside(false);

        return customProgressDialog;
    }

    public void onWindowFocusChanged(boolean hasFocus){

        if (customProgressDialog == null){
            return;
        }

        ImageView imageView = (ImageView) customProgressDialog.findViewById(R.id.loadingImageView);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
    }

    /**
     *
     * [Summary]
     *       setTitile 标题
     * @param strTitle
     * @return
     *
     */
    public CustomProgress setTitile(String strTitle){
        return customProgressDialog;
    }

    /**
     *
     * [Summary]
     *       setMessage 提示内容
     * @param strMessage
     * @return
     *
     */
    public CustomProgress setMessage(String strMessage){
        TextView tvMsg = (TextView)customProgressDialog.findViewById(R.id.id_tv_loadingmsg);

        if (tvMsg != null){
            tvMsg.setText(strMessage);
        }

        return customProgressDialog;
    }
}




//public class CustomProgress extends Dialog {
//    public CustomProgress(Context context) {
//        super(context);
//    }
//
//    public CustomProgress(Context context, int theme) {
//        super(context, theme);
//    }
//
//    /**
//     * 当窗口焦点改变时调用
//     */
//    public void onWindowFocusChanged(boolean hasFocus) {
//        ImageView imageView = (ImageView) findViewById(R.id.spinnerImageView);
//        // 获取ImageView上的动画背景
//        AnimationDrawable spinner = (AnimationDrawable) imageView.getBackground();
//        // 开始动画
//        spinner.start();
//    }
//
//    /**
//     * 给Dialog设置提示信息
//     *
//     * @param message
//     */
//    public void setMessage(CharSequence message) {
//        if (message != null && message.length() > 0) {
//            findViewById(R.id.message).setVisibility(View.VISIBLE);
//            TextView txt = (TextView) findViewById(R.id.message);
//            txt.setText(message);
//            txt.invalidate();
//        }
//    }
//
//    /**
//     * 弹出自定义ProgressDialog
//     *
//     * @param context
//     *            上下文
//     * @param message
//     *            提示
//     * @param cancelable
//     *            是否按返回键取消
//     * @param cancelListener
//     *            按下返回键监听
//     * @return
//     */
//    public static CustomProgress show(Context context, CharSequence message, boolean cancelable, OnCancelListener cancelListener) {
//        CustomProgress dialog = new CustomProgress(context, R.style.Custom_Progress);
//        dialog.setTitle("");
//        dialog.setContentView(R.layout.progress_custom);
//        if (message == null || message.length() == 0) {
//            dialog.findViewById(R.id.message).setVisibility(View.GONE);
//        } else {
//            TextView txt = (TextView) dialog.findViewById(R.id.custom_message);
//            txt.setText(message);
//        }
//        // 按返回键是否取消
//        dialog.setCancelable(cancelable);
//        // 监听返回键处理
//        dialog.setOnCancelListener(cancelListener);
//        // 设置居中
//        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
//        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
//        // 设置背景层透明度
//        lp.dimAmount = 0.2f;
//        dialog.getWindow().setAttributes(lp);
//        // dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
//        dialog.show();
//        return dialog;
//    }
//}
