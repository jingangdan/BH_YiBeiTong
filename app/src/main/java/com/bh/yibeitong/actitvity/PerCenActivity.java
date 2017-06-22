package com.bh.yibeitong.actitvity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseActivity;
import com.bh.yibeitong.utils.FileUtil;

import java.io.File;

/**
 * Created by jingang on 2016/10/18.
 * 个人信息
 */

public class PerCenActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_percen_back;

    private RelativeLayout rel_percen_header, rel_percen_member_jifen,
            rel_percen_data, rel_percen_address;

    private ImageView iv_percen_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percen);
        initData();

    }

    /**
     * 组件 初始化
     */
    public void initData() {
        iv_percen_back = (ImageView) findViewById(R.id.iv_percen_back);
        iv_percen_back.setOnClickListener(this);

        iv_percen_header = (ImageView) findViewById(R.id.iv_percen_header);

        //
        rel_percen_header = (RelativeLayout) findViewById(R.id.rel_percen_header);
        rel_percen_member_jifen = (RelativeLayout) findViewById(R.id.rel_percen_member_jifen);
        rel_percen_data = (RelativeLayout) findViewById(R.id.rel_percen_data);
        rel_percen_address = (RelativeLayout) findViewById(R.id.rel_percen_address);

        rel_percen_header.setOnClickListener(new OnClickListener());
        rel_percen_member_jifen.setOnClickListener(this);
        rel_percen_data.setOnClickListener(this);
        rel_percen_address.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_percen_back:
                finish();
                break;

            case R.id.rel_percen_header:
                //头像
                break;

            case R.id.rel_percen_member_jifen:
                //会员积分
                //startActivity(new Intent(PerCenActivity.this, MemberActivity.class));
                break;

            case R.id.rel_percen_data:
                //基本资料
                //startActivity(new Intent(PerCenActivity.this, PercenDataActivity.class));
                break;

            case R.id.rel_percen_address:
                //我的收货地址
                break;

            default:
                break;
        }

    }


    private AlertDialog alertDialog;
    private Button btn_take_photo, btn_pick_photo, btn_cancel;
    //private SelectPicPopupWindow menuWindow;//自定义的头像编辑弹出框
    private String imgUrl = "";
    private static final String IMAGE_FILE_NAME = "avatarImage.jpg";// 头像文件名称
    private String urlpath;            // 图片本地路径

    private static final int REQUESTCODE_PICK = 0;        // 相册选图标记
    private static final int REQUESTCODE_TAKE = 1;        // 相机拍照标记
    private static final int REQUESTCODE_CUTTING = 2;    // 图片裁切标记

    private String resultStr = "";    // 服务端返回结果集
    private static ProgressDialog pd;// 等待进度圈

    /**
     *
     */
    public class OnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            /*menuWindow = new SelectPicPopupWindow(mContext, itemsOnClick);01
            menuWindow.showAtLocation(v.findViewById(R.id.mainLayout),
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);*/

            //有待更新 可以使用popupwindow来实现 注意点击出现时候的背景变化
            alertDialog = new AlertDialog.Builder(PerCenActivity.this).create();
            View localView = getLayoutInflater()
                    .inflate(R.layout.personal_header_choice, null);
            localView.setAnimation(AnimationUtils.loadAnimation(
                    PerCenActivity.this, R.anim.slide_bottom_to_top));
            Window localWindow = alertDialog.getWindow();
            localWindow.getAttributes();
            alertDialog.show();
            localWindow.setContentView(localView);
            localWindow.setGravity(Gravity.BOTTOM);
            localWindow.setLayout(-1, 380);

            btn_take_photo = (Button) localView.findViewById(R.id.btn_take_photo);
            btn_take_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //拍照

                    alertDialog.dismiss();//关闭AlertDialog

                    Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //下面这句指定调用相机拍照后的照片存储的路径
                    takeIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
                    startActivityForResult(takeIntent, REQUESTCODE_TAKE);

                }
            });

            btn_pick_photo = (Button) localView.findViewById(R.id.btn_pick_photo);
            btn_pick_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //相册

                    alertDialog.dismiss();

                    Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                    // 如果朋友们要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
                    pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(pickIntent, REQUESTCODE_PICK);

                }
            });

            btn_cancel = (Button) localView.findViewById(R.id.btn_cancel);
            //取消
            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //取消
                    alertDialog.dismiss();
                }
            });

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUESTCODE_PICK:// 直接从相册获取
                try {
                    startPhotoZoom(data.getData());
                } catch (NullPointerException e) {
                    e.printStackTrace();// 用户点击取消操作
                }
                break;
            case REQUESTCODE_TAKE:// 调用相机拍照
                File temp = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_FILE_NAME);
                startPhotoZoom(Uri.fromFile(temp));
                break;
            case REQUESTCODE_CUTTING:// 取得裁剪后的图片
                if (data != null) {
                    setPicToView(data);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUESTCODE_CUTTING);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            // 取得SDCard图片路径做显示
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(null, photo);
            urlpath = FileUtil.saveFile(PerCenActivity.this, "temphead.jpg", photo);
            iv_percen_header.setImageDrawable(drawable);
            //roundImageView.setImageDrawable(drawable);

            // 新线程后台上传服务端
//			pd = ProgressDialog.show(mContext, null, "正在上传图片，请稍候...");
//			new Thread(uploadImageRunnable).start();
        }
    }


}
