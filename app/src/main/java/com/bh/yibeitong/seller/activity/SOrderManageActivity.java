package com.bh.yibeitong.seller.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.NotificationCompat;
import android.view.View;

import com.bh.yibeitong.R;
import com.bh.yibeitong.adapter.SimpleFragmentPagerAdapter;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.seller.SellerLogin;
import com.bh.yibeitong.bean.seller.SellerOrder;
import com.bh.yibeitong.seller.fragment.FMSellerCollect;
import com.bh.yibeitong.seller.fragment.FMSellerOK;
import com.bh.yibeitong.seller.fragment.FMSellerSend;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.NoScrollViewPager;
import com.bh.yibeitong.view.UserInfo;

import org.xutils.BuildConfig;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingang on 2017/3/30.
 * 订单管理（商家端）
 */

public class SOrderManageActivity extends BaseTextActivity implements ViewPager.OnPageChangeListener {
    /*请求地址*/
    private String PATH = "";

    private String str_result;

    /*本地轻量型缓存 短时间保存pwd*/
    UserInfo userInfo;
    String uid, pwd;

    /*TabLayout*/
    private TabLayout tabLayout;
    private NoScrollViewPager noScrollViewPager;

    private String[] titles = new String[]{"待确认", "待发货", "待收货"};
    /*private Fragment[] fragments = new Fragment[]{
            new FMSellerOK(),
            new FMSellerSend(),
            new FMSellerCollect()
    };*/
    private List<Fragment> fragments = new ArrayList<>();

    private SimpleFragmentPagerAdapter sfpAdapter;


    private SellerOrder sellerOrder;


    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_s_order_manage);

        x.Ext.init(getApplication());
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.

    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("订单管理");
        setTitleBack(true, 0);

        initData();

    }

    /*组件 初始化*/
    public void initData() {
        userInfo = new UserInfo(getApplication());
        pwd = userInfo.getPwd();

        /*此处需验证 数据是否为空*/
        /*SellerLogin sellerLogin = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), SellerLogin.class);
        uid = sellerLogin.getMsg().getUid();

        tabLayout = (TabLayout) findViewById(R.id.tl_seller_order_manage);
        noScrollViewPager = (NoScrollViewPager) findViewById(R.id.vp_seller_order_manage);


        getSellerOrder(uid, pwd, "", "wait");*/

        noScrollViewPager = (NoScrollViewPager) findViewById(R.id.vp_seller_order_manage);
        tabLayout = (TabLayout) findViewById(R.id.tl_seller_order_manage);

        fragments.add(FMSellerOK.newInstance("FMSellerOK"));

        fragments.add(FMSellerSend.newInstance("FMSellerSend"));
        fragments.add(FMSellerCollect.newInstance("FMSellerCollect"));

        sfpAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), this, fragments, titles);
        noScrollViewPager.setAdapter(sfpAdapter);


        noScrollViewPager.setOffscreenPageLimit(titles.length);

        noScrollViewPager.setOnPageChangeListener(this);
        tabLayout.setupWithViewPager(noScrollViewPager);

        notificationMethod();


    }


    /*清除通知栏*/
    public void notificationMethod() {
        // 在Android进行通知处理，首先需要重系统哪里获得通知管理器NotificationManager，它是一个系统Service。
        NotificationManager manager = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE);

        manager.cancel(1);

        //switch (view.getId()) {
            // 默认通知
//            case R.id.btn1:
//                // 创建一个PendingIntent，和Intent类似，不同的是由于不是马上调用，需要在下拉状态条出发的activity，所以采用的是PendingIntent,即点击Notification跳转启动到哪个Activity
//                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
//                        new Intent(this, MainActivity.class), 0);
//                // 下面需兼容Android 2.x版本是的处理方式
//                // Notification notify1 = new Notification(R.drawable.message,
//                // "TickerText:" + "您有新短消息，请注意查收！", System.currentTimeMillis());
//                Notification notify1 = new Notification();
//                notify1.icon = R.drawable.message;
//                notify1.tickerText = "TickerText:您有新短消息，请注意查收！";
//                notify1.when = System.currentTimeMillis();
//                notify1.setLatestEventInfo(this, "Notification Title",
//                        "This is the notification message", pendingIntent);
//                notify1.number = 1;
//                notify1.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。
//                // 通过通知管理器来发起通知。如果id不同，则每click，在statu那里增加一个提示
//                manager.notify(NOTIFICATION_FLAG, notify1);
//                break;
            // 默认通知 API11及之后可用
//            case R.id.btn2:
//                PendingIntent pendingIntent2 = PendingIntent.getActivity(this, 0,
//                        new Intent(this, MainActivity.class), 0);
//                // 通过Notification.Builder来创建通知，注意API Level
//                // API11之后才支持
//                Notification notify2 = new Notification.Builder(this)
//                        .setSmallIcon(R.drawable.message) // 设置状态栏中的小图片，尺寸一般建议在24×24，这个图片同样也是在下拉状态栏中所显示，如果在那里需要更换更大的图片，可以使用setLargeIcon(Bitmap
//                        // icon)
//                        .setTicker("TickerText:" + "您有新短消息，请注意查收！")// 设置在status
//                        // bar上显示的提示文字
//                        .setContentTitle("Notification Title")// 设置在下拉status
//                        // bar后Activity，本例子中的NotififyMessage的TextView中显示的标题
//                        .setContentText("This is the notification message")// TextView中显示的详细内容
//                        .setContentIntent(pendingIntent2) // 关联PendingIntent
//                        .setNumber(1) // 在TextView的右方显示的数字，可放大图片看，在最右侧。这个number同时也起到一个序列号的左右，如果多个触发多个通知（同一ID），可以指定显示哪一个。
//                        .getNotification(); // 需要注意build()是在API level
//                // 16及之后增加的，在API11中可以使用getNotificatin()来代替
//                notify2.flags |= Notification.FLAG_AUTO_CANCEL;
//                manager.notify(NOTIFICATION_FLAG, notify2);
//                break;
            // 默认通知 API16及之后可用
//            case R.id.btn3:
//                PendingIntent pendingIntent3 = PendingIntent.getActivity(this, 0,
//                        new Intent(this, MainActivity.class), 0);
//                // 通过Notification.Builder来创建通知，注意API Level
//                // API16之后才支持
//                Notification notify3 = new Notification.Builder(this)
//                        .setSmallIcon(R.drawable.message)
//                        .setTicker("TickerText:" + "您有新短消息，请注意查收！")
//                        .setContentTitle("Notification Title")
//                        .setContentText("This is the notification message")
//                        .setContentIntent(pendingIntent3).setNumber(1).build(); // 需要注意build()是在API
//                // level16及之后增加的，API11可以使用getNotificatin()来替代
//                notify3.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。
//                manager.notify(NOTIFICATION_FLAG, notify3);// 步骤4：通过通知管理器来发起通知。如果id不同，则每click，在status哪里增加一个提示
//                break;
            // 自定义通知
//            case R.id.btn4:
//                // Notification myNotify = new Notification(R.drawable.message,
//                // "自定义通知：您有新短信息了，请注意查收！", System.currentTimeMillis());
//                Notification myNotify = new Notification();
//                myNotify.icon = R.drawable.message;
//                myNotify.tickerText = "TickerText:您有新短消息，请注意查收！";
//                myNotify.when = System.currentTimeMillis();
//                myNotify.flags = Notification.FLAG_NO_CLEAR;// 不能够自动清除
//                RemoteViews rv = new RemoteViews(getPackageName(),
//                        R.layout.my_notification);
//                rv.setTextViewText(R.id.text_content, "hello wrold!");
//                myNotify.contentView = rv;
//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                PendingIntent contentIntent = PendingIntent.getActivity(this, 1,
//                        intent, 1);
//                myNotify.contentIntent = contentIntent;
//                manager.notify(NOTIFICATION_FLAG, myNotify);
//                break;


            /*case R.id.btn5:
                // 清除id为NOTIFICATION_FLAG的通知
                manager.cancel(NOTIFICATION_FLAG);
                // 清除所有的通知
                // manager.cancelAll();
                break;*/
//            default:
//                break;
//        }
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case 1:
                break;

            default:
                break;
        }
    }

    /**
     * @param uid
     * @param pwd
     * @param searchday 查询日期
     * @param gettype   订单类型 wait waitsend is_send
     */
    public void getSellerOrder(String uid, String pwd, String searchday, String gettype) {
        PATH = HttpPath.path + HttpPath.APP_ORDER +
                "uid=" + uid + "&pwd=" + pwd + "&searchday=" + searchday + "&gettype=" + gettype;

        System.out.println("" + PATH);
        RequestParams params = new RequestParams(PATH);

        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("商家订单 = " + result);
                        //SellerOrder sellerOrder = GsonUtil.gsonIntance().gsonToBean(result, SellerOrder.class);

                        fragments.add(FMSellerOK.newInstance(result));

                        fragments.add(FMSellerSend.newInstance("FMSellerSend"));
                        fragments.add(FMSellerCollect.newInstance("FMSellerCollect"));

                        sfpAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), SOrderManageActivity.this, fragments, titles);
                        noScrollViewPager.setAdapter(sfpAdapter);


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

    /*ViewPager 滑动控制*/
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}