package com.bh.yibeitong.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseFragment;
import com.bh.yibeitong.bean.Error;
import com.bh.yibeitong.bean.GoodsIndex;
import com.bh.yibeitong.bean.Order;
import com.bh.yibeitong.bean.OrderDel;
import com.bh.yibeitong.bean.Register;
import com.bh.yibeitong.seller.activity.SellerLoginActivity;
import com.bh.yibeitong.ui.LoginRegisterActivity;
import com.bh.yibeitong.ui.OrderCommentActivity;
import com.bh.yibeitong.ui.OrderDetaileActivity;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.utils.XUtilsImageUtils;
import com.bh.yibeitong.view.CustomDialog;
import com.bh.yibeitong.view.UserInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.BitmapUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingang on 2016/11/28.
 * 测试fragment懒加载
 */

public class FMOrderTest extends BaseFragment {

    private TextView tv_header_title;
    private ImageView iv_header_left, iv_header_right;

    private String login;

    public static String uid, pwd;

    public static String phone;

    private ListView lv_order;

    /*本地存储*/
    UserInfo userInfo;
    private String jingang;//有请我的大名！

    /*适配器*/
    private OrderAdapter orderAdapter;
    private List<Order.MsgBean> msgBeanList = new ArrayList<>();

    private Gson gson;

    private View view;

    //没有登录登录时布局
    private Button but_login;
    private TextView tv_nologin_title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        System.out.println("订单页 加载");

        userInfo = new UserInfo(getActivity().getApplication());

        jingang = userInfo.getLogin();

        /*判断是否已登录*/
        if (jingang.equals("")) {

            view = inflater.inflate(R.layout.activity_nologin, container, false);

            initDatas();

        } else if (jingang.equals("0")) {
            view = inflater.inflate(R.layout.activity_nologin, container, false);

            initDatas();

        } else if (jingang.equals("1")) {
            view = inflater.inflate(R.layout.fragment_orders, container, false);

            initData();

        }

        return view;
    }

    /**
     * 组件初始化 登录状态下
     */
    public void initData() {

        tv_header_title = (TextView) view.findViewById(R.id.tv_header_title);
        iv_header_left = (ImageView) view.findViewById(R.id.iv_header_left);
        iv_header_right = (ImageView) view.findViewById(R.id.iv_header_right);

        tv_header_title.setText("我的订单");
        iv_header_left.setVisibility(View.INVISIBLE);

        lv_order = (ListView) view.findViewById(R.id.lv_order);

        gson = new Gson();
        Type listType = new TypeToken<List<GoodsIndex.MsgBean.ShopdetBean.PostdateBean>>() {
        }.getType();

        final List<GoodsIndex.MsgBean.ShopdetBean.PostdateBean> catefoodslist =
                gson.fromJson(userInfo.getPostData(), listType);

        //验证登录方式
        if (!(userInfo.getUserInfo().equals(""))) {
            Register register = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), Register.class);
            uid = register.getMsg().getUid();
            phone = register.getMsg().getPhone();
            if (!(userInfo.getPwd().equals(""))) {
                pwd = userInfo.getPwd();

                if (userInfo.getCode().equals("0")) {
                    System.out.println("我的验证码" + userInfo.getCode());
                    getOrder(uid, pwd);
                } else {
                    System.out.println("我的手机号" + phone);
                    getOrder("phone", phone);
                }

            }

        }
    }

    PopupWindow pop;
    View view_pop;
    LinearLayout ll_popup;
    Button but_user, but_seller, but_cancel;

    /**
     * 组件初始化 未登录状态下
     */
    public void initDatas() {
        but_login = (Button) view.findViewById(R.id.but_login);

        but_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getActivity(), LoginRegisterActivity.class));

                pop = new PopupWindow(getActivity());
                view_pop = getActivity().getLayoutInflater().inflate(R.layout.pop_login_type, null);
                view_pop.setAnimation(AnimationUtils.loadAnimation(
                        getActivity(), R.anim.slide_bottom_to_top));
                ll_popup = (LinearLayout) view_pop.findViewById(R.id.ll_login_type);

                pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                pop.setBackgroundDrawable(new BitmapDrawable());
                pop.setFocusable(true);
                pop.setOutsideTouchable(true);
                pop.setContentView(view_pop);
                pop.showAsDropDown(view_pop);

                final RelativeLayout parent = (RelativeLayout) view_pop.findViewById(R.id.rel_login_type);

                but_user = (Button) view_pop.findViewById(R.id.but_user);
                but_seller = (Button) view_pop.findViewById(R.id.but_seller);
                but_cancel = (Button) view_pop.findViewById(R.id.but_cancel);

                parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pop.dismiss();
                        ll_popup.clearAnimation();
                    }
                });

                //用户登录
                but_user.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getActivity(), LoginRegisterActivity.class));
                        pop.dismiss();
                        parent.clearAnimation();
                    }
                });

                //商家登录
                but_seller.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), SellerLoginActivity.class);
                        intent.putExtra("loginType", "2");
                        startActivity(intent);

                        //startActivity(new Intent(getActivity(), SellerLoginActivity.class));
                        pop.dismiss();
                        parent.clearAnimation();

                    }
                });

                //取消
                but_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pop.dismiss();
                        parent.clearAnimation();
                    }
                });


            }
        });

        tv_nologin_title = (TextView) view.findViewById(R.id.tv_nologin_title);

        tv_nologin_title.setText("我的订单");

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            System.out.println("离开FMOrder");
            //离开Fragment

        } else {
            //相当于Fragment的onPause
            System.out.println("加载FMOrder");
            //重新刷新

            if (!(userInfo.getUserInfo().equals(""))) {
                Register register = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), Register.class);
                uid = register.getMsg().getUid();
                phone = register.getMsg().getPhone();
                if (!(userInfo.getPwd().equals(""))) {
                    pwd = userInfo.getPwd();

                    if (userInfo.getCode().equals("0")) {
                        System.out.println("我的验证码" + userInfo.getCode());
                        getOrder(uid, pwd);
                    } else {
                        System.out.println("我的手机号" + phone);
                        getOrder("phone", phone);
                    }
                }
            } else {

            }


        }
    }

    /**
     * 获取订单
     *
     * @param uid
     * @param pwd
     */
    public void getOrder(String uid, String pwd) {
        String PATH = null;
        if (userInfo.getCode().equals("0")) {
            PATH = HttpPath.PATH + HttpPath.ORDER_NEW +
                    "uid=" + uid + "&pwd=" + pwd;
        } else {
            PATH = HttpPath.PATH + HttpPath.ORDER_NEW +
                    "logintype=" + uid + "&loginphone=" + pwd;
        }

        System.out.println("订单列表"+PATH);


        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("订单列表" + result);

                        Order order = GsonUtil.gsonIntance().gsonToBean(result, Order.class);
                        msgBeanList = order.getMsg();

                        orderAdapter = new OrderAdapter(getActivity(), msgBeanList);
                        lv_order.setAdapter(orderAdapter);

                        //订单详情
                        lv_order.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //
                                Intent intent = new Intent(getActivity(), OrderDetaileActivity.class);
                                intent.putExtra("orderid", msgBeanList.get(position).getId());
                                intent.putExtra("status", msgBeanList.get(position).getStatus());
                                startActivity(intent);
                            }
                        });

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        System.out.println("订单列表onError");
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                    }

                    @Override
                    public void onFinished() {

                    }
                });
    }

    /**
     * 提示框
     */
    protected void dialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
        builder.setMessage("还未登录，确定要登录吗？");
        builder.setTitle("还未登录");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //设置你的操作事项
                startActivity(new Intent(getActivity(), LoginRegisterActivity.class));
            }
        });

        builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.create().show();

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onClick(View view) {

    }


    /**
     * 将适配器嵌套在Activity中 更好的操作Activity中的控件变化
     * 不知道有木有更好的办法
     */
    public class OrderAdapter extends BaseAdapter {
        private Context mContext;
        private List<Order.MsgBean> msgBeen = new ArrayList<>();

        public OrderAdapter(Context mContext, List<Order.MsgBean> msgBeen) {
            this.mContext = mContext;
            this.msgBeen = msgBeen;
        }

        @Override
        public int getCount() {
            return msgBeen.size();
        }

        @Override
        public Object getItem(int position) {
            return msgBeen.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder vh;
            if (convertView == null) {
                vh = new OrderAdapter.ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_order, null);

                vh.img = (ImageView) convertView.findViewById(R.id.iv_item_order_img);
                vh.shopname = (TextView) convertView.findViewById(R.id.tv_item_order_shopname);
                vh.state = (TextView) convertView.findViewById(R.id.tv_item_order_state);
                vh.price = (TextView) convertView.findViewById(R.id.tv_item_order_price);
                vh.time = (TextView) convertView.findViewById(R.id.tv_item_order_time);

                /*删除订单 确认收货 评价订单 再来一单*/
                vh.del = (Button) convertView.findViewById(R.id.but_item_order_del);
                vh.c_receipt = (Button) convertView.findViewById(R.id.but_item_order_c_receipt);
                vh.comment = (Button) convertView.findViewById(R.id.but_item_order_comment);
                vh.again = (Button) convertView.findViewById(R.id.but_item_order_again);


                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            // 组件赋值
            String imgPath = msgBeen.get(position).getShoplogo();
            String shopname = msgBeen.get(position).getShopname();
            String state = msgBeen.get(position).getSeestatus();
            String price = msgBeen.get(position).getAllcost();
            String time = msgBeen.get(position).getAddtime();

            //订单状态
            String status = msgBeen.get(position).getStatus();

            final String orderid = msgBeen.get(position).getId();


            if (imgPath.equals("")) {
                vh.img.setImageResource(R.mipmap.yibeitong001);

            } else {
//                BitmapUtils bitmapUtils = new BitmapUtils(mContext);
//
//                bitmapUtils.configDiskCacheEnabled(true);
//                bitmapUtils.configMemoryCacheEnabled(false);
//
//                bitmapUtils.display(vh.img, imgPath);

                x.image().bind(vh.img, imgPath);

                //XUtilsImageUtils.display(vh.img, imgPath, 0);
            }

            vh.shopname.setText(shopname);
            vh.state.setText(state);
            vh.price.setText("￥" + price);
            vh.time.setText(time);

            /*订单状态*/
            if (status.toString().equals("0")) {
                //新订单
                vh.again.setVisibility(View.VISIBLE);
                vh.del.setVisibility(View.INVISIBLE);
                vh.c_receipt.setVisibility(View.INVISIBLE);
                vh.comment.setVisibility(View.INVISIBLE);


            } else if (status.toString().equals("1")) {
                //待发货
                vh.del.setVisibility(View.INVISIBLE);
                vh.again.setVisibility(View.VISIBLE);

                vh.c_receipt.setVisibility(View.INVISIBLE);
                vh.comment.setVisibility(View.INVISIBLE);
            } else if (status.toString().equals("2")) {
                //待确认
                vh.del.setVisibility(View.INVISIBLE);
                vh.c_receipt.setVisibility(View.VISIBLE);
                vh.again.setVisibility(View.VISIBLE);
                vh.comment.setVisibility(View.INVISIBLE);

            } else if (status.toString().equals("3")) {
                //已完成
                vh.del.setVisibility(View.INVISIBLE);
                vh.comment.setVisibility(View.VISIBLE);
                vh.again.setVisibility(View.VISIBLE);

            } else if (status.toString().equals("4")) {
                //订单取消
                vh.del.setVisibility(View.VISIBLE);

            } else if (status.toString().equals("5")) {
                //订单取消
                vh.del.setVisibility(View.VISIBLE);
            }

            //删除订单
            vh.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String PATH;
                    if (userInfo.getCode().equals("0")) {
                        PATH = "http://www.ybt9.com//index.php?ctrl=app&source=1&datatype=json&action=newordercontrol&doname=delorder&uid="
                                + uid + "+&pwd" + pwd + "&orderid=" + orderid;
                    } else {

                        PATH = "http://www.ybt9.com//index.php?ctrl=app&source=1&datatype=json&action=newordercontrol&doname=delorder&" +
                                "logintype=phone" + "+&longinphone=" + phone + "&orderid=" + orderid;
                    }

                    System.out.println(PATH);

                    RequestParams params = new RequestParams(PATH);
                    x.http().post(params,
                            new Callback.CommonCallback<String>() {
                                @Override
                                public void onSuccess(String result) {
                                    System.out.println("删除订单" + result);

                                    OrderDel orderDel = GsonUtil.gsonIntance().gsonToBean(result, OrderDel.class);
                                    if (orderDel.isError() == false) {
                                        Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
                                        msgBeen.remove(position);
                                        orderAdapter.notifyDataSetChanged();
                                    } else {
                                        Toast.makeText(mContext, orderDel.getMsg().toString(), Toast.LENGTH_SHORT).show();
                                    }
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
            });

            /*确认收货*/
            vh.c_receipt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String PATH;
                    if (userInfo.getCode().toString().equals("0")) {
                        PATH = HttpPath.PATH + HttpPath.ORDER_NEW_CONTROL +
                                "uid=" + uid + "&pwd=" + pwd + "&doname=sureorder" + "&orderid=" + orderid;
                    } else {
                        PATH = HttpPath.PATH + HttpPath.ORDER_NEW_CONTROL +
                                "logintype=phone" + "&loginphone=" + phone + "&doname=sureorder" + "&orderid=" + orderid;
                    }

                    RequestParams params = new RequestParams(PATH);
                    x.http().post(params,
                            new Callback.CommonCallback<String>() {
                                @Override
                                public void onSuccess(String result) {
                                    System.out.println("确认收货" + result);
                                    Error error = GsonUtil.gsonIntance().gsonToBean(result, Error.class);

                                    if (error.isError() == false) {
                                        vh.comment.setVisibility(View.VISIBLE);
                                        vh.c_receipt.setVisibility(View.INVISIBLE);

                                    } else {

                                    }
                                    Toast.makeText(mContext, error.getMsg().toString(), Toast.LENGTH_SHORT).show();

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
            });

            /*再来一单*/
            vh.again.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //跳到商店
                }
            });

            /*评价订单*/
            vh.comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //
                    Intent intent = new Intent(mContext, OrderCommentActivity.class);
                    intent.putExtra("orderid", orderid);
                    startActivity(intent);

                }
            });


            return convertView;
        }

        public class ViewHolder {
            private TextView shopname, price, state, time;
            private ImageView img;
            private Button del, comment, again, c_receipt;

        }

    }

}


