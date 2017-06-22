package com.bh.yibeitong.seller.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bh.yibeitong.R;
import com.bh.yibeitong.actitvity.ActivityCollector;
import com.bh.yibeitong.actitvity.MainActivity;
import com.bh.yibeitong.refresh.MyGridView;
import com.bh.yibeitong.view.SlideMenuView;
import com.bh.yibeitong.view.UserInfo;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jingang on 2017/3/14.
 * 商家界面
 */
public class SellerActivity extends Activity implements View.OnClickListener {

    /*侧边栏*/
    private SlideMenuView slideMenuView;
    private ImageView imageView;

    /*UI*/
    private MyGridView myGridView;
    private MyGridViewAdapter mgvAdapter;
    private ScrollView scrollView;

    /*营业额 订单数*/
    private TextView tv_turnover, tv_order_num;

    /*立即结算*/
    private Button but_pay;

    /*账户余额*/
    private TextView tv_balance;

    private List<Map<String, Object>> listItems;

    /*测试元素添加*/
    private Integer[] imgIds = {R.mipmap.ic_seller_one, R.mipmap.ic_seller_two, R.mipmap.ic_seller_three,
            R.mipmap.ic_seller_four,
            R.mipmap.ic_seller_five, R.mipmap.ic_seller_six, R.mipmap.ic_seller_sevie,
            R.mipmap.ic_seller_eight,
            R.mipmap.ic_seller_nine, R.mipmap.ic_seller_ten, R.mipmap.ic_seller_one,
            R.mipmap.ic_seller_eleven};

    private String[] strNames = {"订单管理", "商店管理", "店铺管理", "促销管理",
            "商家结算", "店铺统计", "留言评价", "货物采购",
            "打印机设置", "营业状态", "闪惠订单", "库存查询"};

    private String[] strDetail = {"没有处理的订单", "支持商品一键发布", "设置店铺信息", "发布促销信息",
            "查询余额提现", "查看店铺收益", "查看最新留言", "官方直供直达",
            "链接打印机", "营业状态设置", "没有处理的订单", "及时查看库存"};

    /*侧边栏UI
    * 首页 订单管理 商品管理 店铺管理 促销管理 店铺统计 评价留言 退出账号*/
    private TextView tv_home_page, tv_order_manage, tv_goods_manage,
            tv_shop_manage, tv_sales_manage, tv_shop_count, tv_comment, tv_without;

    /*本地缓存*/
    private UserInfo userInfo;

    /*接收页面传值*/
    private Intent intent;
    private String userAccount;
    private String shopid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);

        intent = getIntent();
        userAccount = intent.getStringExtra("UserAccount");
        shopid = intent.getStringExtra("shopid");

        if (!(userAccount == null)) {
            XGPushManager.registerPush(this, userAccount);

            XGPushManager.registerPush(this, userAccount,
                    new XGIOperateCallback() {
                        @Override
                        public void onSuccess(Object data, int flag) {
                            //Log.d("TPush", "注册成功，设备token为：" + data);
                            System.out.println("绑定账号 成功 = " + userAccount);
                            System.out.println("绑定账号注册成功，设备token为：" + data);
                        }

                        @Override
                        public void onFail(Object data, int errCode, String msg) {
                            System.out.println("绑定账号 失败 = " + userAccount);
                            //Log.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
                            System.out.println("绑定账号注册失败，错误码：" + errCode + ",错误信息：" + msg);
                        }
                    });
        } else {

        }

        initData();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        XGPushClickedResult click = XGPushManager.onActivityStarted(this);
        if (click != null) {
            // 判断是否来自信鸽的打开方式
            Toast.makeText(this, "通知被点击了 :" + click.toString(),
                    Toast.LENGTH_SHORT).show();

            //通知被点击 跳转到订单页
            /*startActivity(new Intent(SellerActivity.this, SOrderManageActivity.class));

            slideMenuView.closeMenu();*/

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (SellerActivity.class == null) {
            System.out.println("跳转");

            startActivity(new Intent(SellerActivity.this, SOrderManageActivity.class));

        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        XGPushManager.onActivityStoped(this);
        System.out.println("通知被点击了");

        XGPushClickedResult click = XGPushManager.onActivityStarted(this);
        if (click != null) {
            // 判断是否来自信鸽的打开方式
            Toast.makeText(this, "通知被点击了 :" + click.toString(),
                    Toast.LENGTH_SHORT).show();

            //通知被点击 跳转到订单页
            /*startActivity(new Intent(SellerActivity.this, SOrderManageActivity.class));

            slideMenuView.closeMenu();*/


        }
    }

    /**
     * 初始化商品信息
     */
    private List<Map<String, Object>> getListItems() {
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < strNames.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", imgIds[i]);               //图片资源
            map.put("title", strNames[i]);     //物品名称
            map.put("info", strDetail[i]); //物品详情
            listItems.add(map);
        }
        return listItems;
    }

    /*组件 初始化*/
    public void initData() {

        userInfo = new UserInfo(getApplication());

        slideMenuView = (SlideMenuView) findViewById(R.id.slideMenuView);
        imageView = (ImageView) findViewById(R.id.title_bar_menu_btn);
        imageView.setOnClickListener(this);

        myGridView = (MyGridView) findViewById(R.id.mgv_seller);

        scrollView = (ScrollView) findViewById(R.id.sv_seller);

        listItems = getListItems();
        mgvAdapter = new MyGridViewAdapter(SellerActivity.this, listItems);
        myGridView.setAdapter(mgvAdapter);

        scrollView.setFocusable(true);
        scrollView.setFocusableInTouchMode(true);
        scrollView.requestFocus();

        /*侧边栏*/
        tv_home_page = (TextView) findViewById(R.id.tv_sm_home_page);
        tv_order_manage = (TextView) findViewById(R.id.tv_sm_order_manage);
        tv_goods_manage = (TextView) findViewById(R.id.tv_sm_goods_manage);
        tv_shop_manage = (TextView) findViewById(R.id.tv_sm_shop_manage);
        tv_sales_manage = (TextView) findViewById(R.id.tv_sm_sales_manage);
        tv_shop_count = (TextView) findViewById(R.id.tv_sm_shop_count);
        tv_comment = (TextView) findViewById(R.id.tv_sm_comment);
        tv_without = (TextView) findViewById(R.id.tv_sm_without);

        tv_home_page.setOnClickListener(this);
        tv_order_manage.setOnClickListener(this);
        tv_goods_manage.setOnClickListener(this);
        tv_shop_manage.setOnClickListener(this);
        tv_sales_manage.setOnClickListener(this);
        tv_shop_count.setOnClickListener(this);
        tv_comment.setOnClickListener(this);
        tv_without.setOnClickListener(this);

        /*点击选项*/
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    //订单管理
                    startActivity(new Intent(SellerActivity.this, SOrderManageActivity.class));

                } else if (i == 7) {
                    //货物采购
                    startActivity(new Intent(SellerActivity.this, GoodsProActivity.class));

                }else if(i == 11){
                    //库存查询
                    intent = new Intent(SellerActivity.this, RepertoryActivity.class);
                    intent.putExtra("shopid", shopid);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.title_bar_menu_btn:
                if (slideMenuView.isMainScreenShowing()) {
                    slideMenuView.openMenu();
                } else {
                    slideMenuView.closeMenu();
                }
                break;
            case R.id.tv_sm_home_page:
                //首页
                slideMenuView.closeMenu();

                break;

            case R.id.tv_sm_order_manage:
                //订单管理

                startActivity(new Intent(SellerActivity.this, SOrderManageActivity.class));

                slideMenuView.closeMenu();

                break;

            case R.id.tv_sm_goods_manage:
                //商品管理

                break;

            case R.id.tv_sm_shop_manage:
                //店铺管理

                break;
            case R.id.tv_sm_sales_manage:
                //促销管理

                break;

            case R.id.tv_sm_shop_count:
                //店铺统计

                break;

            case R.id.tv_sm_comment:
                //评价留言

                break;

            case R.id.tv_sm_without:
                userInfo.saveLogin("0");//退出登录保存数字0

                XGPushManager.unregisterPush(this);

                Intent intent = new Intent(SellerActivity.this, MainActivity.class);
                intent.putExtra("islogin", false);
                ActivityCollector.finishAll();
                startActivity(intent);
                SellerActivity.this.finish();


                //推出账号

                break;

            default:
                break;
        }
    }

    /**
     *
     */
    public class MyGridViewAdapter extends BaseAdapter {
        private Context mContext;
        private List<Map<String, Object>> listItems;

        public MyGridViewAdapter(Context mContext, List<Map<String, Object>> listItems) {
            this.mContext = mContext;
            this.listItems = listItems;
        }

        @Override
        public int getCount() {
            return listItems.size();
        }

        @Override
        public Object getItem(int i) {
            return listItems.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder vh;
            if (view == null) {
                vh = new ViewHolder();
                view = LayoutInflater.from(mContext).inflate(R.layout.item_seller_bady, null);

                vh.imageView = (ImageView) view.findViewById(R.id.iv_item_seller_img);
                vh.tv_title = (TextView) view.findViewById(R.id.tv_item_seller_title);
                vh.tv_bady = (TextView) view.findViewById(R.id.tv_item_seller_bady);

                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }

            vh.imageView.setImageResource((Integer) listItems.get(i).get("image"));

            vh.tv_title.setText((String) listItems.get(i).get("title"));
            vh.tv_bady.setText((String) listItems.get(i).get("info"));

            return view;
        }

        public class ViewHolder {
            ImageView imageView;
            TextView tv_title, tv_bady;
        }
    }

    //双击的时间间隔
    private long millis = 0;

    /**
     * 监听返回键 双击退出程序
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //
            boolean is;
            //双击退出
            if (System.currentTimeMillis() - millis < 1000) {
                return super.onKeyDown(keyCode, event);
            } else {
                Toast.makeText(this, "再次点击退出程序", Toast.LENGTH_SHORT).show();
                millis = System.currentTimeMillis();
                return false;
            }

        }
        return false;
    }

}