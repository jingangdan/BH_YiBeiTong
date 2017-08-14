package com.bh.yibeitong.actitvity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bh.yibeitong.R;
import com.bh.yibeitong.adapter.ViewPagerAdapter;
import com.bh.yibeitong.base.BaseActivity;
import com.bh.yibeitong.seller.activity.SellerActivity;
import com.bh.yibeitong.view.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现首次启动的引导页面
 */
public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private ViewPager vp;
    private int[] imageIdArray;//图片资源的数组
    private List<View> viewList;//图片资源的集合

    //最后一页的按钮
    private Button ib_start;

    private ViewPagerAdapter vpAdapter;

    //判断是否登录（商家端）
    UserInfo userInfo;

    /*String 数组，保存需要检查的权限*/
    private static final String[] PERMISSIONS_CONTACT = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};

    /*定义一个请求码*/
    private static final int REQUEST_CONTACTS = 1000;

    /*立即体验*/
    private Button but_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_test);
        userInfo = new UserInfo(getApplication());

        ib_start = (Button) findViewById(R.id.guide_ib_start);
        but_start = (Button) findViewById(R.id.but_start);

        ib_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                init(v);

//                if (Integer.parseInt(android.os.Build.VERSION.SDK) > 22) {
//
//                    if (ContextCompat.checkSelfPermission(GuideActivity.this,
//                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                        ActivityCompat.requestPermissions(
//                                GuideActivity.this, new String[]{
//                                        Manifest.permission.ACCESS_COARSE_LOCATION
//                                }, 8
//                        );
//
//                    } else {
//
//                        if (userInfo.getLogin().equals("2")) {
//                            //商家端登录
//                            startActivity(new Intent(GuideActivity.this, SellerActivity.class));
//                        } else {
//                            startActivity(new Intent(GuideActivity.this, MainActivity.class));
//                        }
//                        finish();
//                    }
//                }else{
//                    if (userInfo.getLogin().equals("2")) {
//                        //商家端登录
//                        startActivity(new Intent(GuideActivity.this, SellerActivity.class));
//                    } else {
//                        startActivity(new Intent(GuideActivity.this, MainActivity.class));
//                    }
//                    finish();
//                }




            }
        });

        but_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init(view);
            }
        });

        //加载ViewPager
        initViewPager();

    }

    public void init(View view) {
        if (Build.VERSION.SDK_INT > 22) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                System.out.println("没有");
                requestSetPermissions(view);
            } else {
                //mLocClient.start();
                System.out.println("有");

                if (userInfo.getLogin().equals("2")) {
                    //商家端登录
                    startActivity(new Intent(GuideActivity.this, SellerActivity.class));
                } else {
                    startActivity(new Intent(GuideActivity.this, MainActivity.class));
                }
                finish();

            }

        } else {
            //6.0版本以下
            if (userInfo.getLogin().equals("2")) {
                //商家端登录
                startActivity(new Intent(GuideActivity.this, SellerActivity.class));
            } else {
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
            }
            finish();

        }
    }

    private void requestSetPermissions(View view) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
            Snackbar.make(view, "permission_contacts_rationale",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            System.out.println("okokokokok");
                            ActivityCompat.requestPermissions(GuideActivity.this, PERMISSIONS_CONTACT, REQUEST_CONTACTS);

                        }
                    }).setAction("no", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("nonononono");
                }
            })
                    .show();
        } else {
            System.out.println("oknookno");
            ActivityCompat.requestPermissions(this, PERMISSIONS_CONTACT, REQUEST_CONTACTS);
        }
    }


    /**
     * 加载图片ViewPager 此处同样可以加载布局ViewPager
     */
    private void initViewPager() {

        ib_start = (Button) findViewById(R.id.guide_ib_start);
        vp = (ViewPager) findViewById(R.id.guide_vp);
        // 初始化底部小圆点
        //initDots();

        imageIdArray = new int[]{R.mipmap.lunch_1, R.mipmap.lunch_2, R.mipmap.lunch_3
                , R.mipmap.lunch_4};

        viewList = new ArrayList<>();

        // 为引导图片提供布局参数
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT);

        // 初始化引导图片列表
        for (int i = 0; i < imageIdArray.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource(imageIdArray[i]);
            viewList.add(iv);
        }

        // 初始化Adapter
        vpAdapter = new ViewPagerAdapter(viewList);
        vp.setAdapter(vpAdapter);
        // 绑定回调
        vp.setOnPageChangeListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * 滑动后的监听
     *
     * @param position
     */
    @Override
    public void onPageSelected(int position) {

        //判断是否是最后一页，若是则显示按钮
        if (position == imageIdArray.length - 1) {
            ib_start.setVisibility(View.VISIBLE);

            but_start.setVisibility(View.GONE);

        } else {
            ib_start.setVisibility(View.GONE);
            but_start.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CONTACTS) {
            if (PackageManager.PERMISSION_GRANTED != grantResults[0]) {

                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                finish();

            } else {
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                finish();
                //Toast.makeText(getApplicationContext(), "授权不通过", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//
//        if (requestCode == 8) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                //callPhone();
//                startActivity(new Intent(GuideActivity.this, MainActivity.class));
//                finish();
//                //Toast.makeText(GuideActivity.this, "开启定位权限", Toast.LENGTH_SHORT).show();
//
//            } else {
//                // Permission Denied
//                //Toast.makeText(GuideActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
//            }
//            return;
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
}