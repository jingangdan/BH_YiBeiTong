package com.bh.yibeitong.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bh.yibeitong.Interface.OnItemClickListener;
import com.bh.yibeitong.Interface.OnItemLongClickListener;
import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseFragment;
import com.bh.yibeitong.bean.Errors;
import com.bh.yibeitong.bean.Order;
import com.bh.yibeitong.bean.OrderDel;
import com.bh.yibeitong.bean.Register;
import com.bh.yibeitong.refresh.PullToRefreshView;
import com.bh.yibeitong.seller.activity.SellerLoginActivity;
import com.bh.yibeitong.ui.LoginRegisterActivity;
import com.bh.yibeitong.ui.PayActivity;
import com.bh.yibeitong.ui.ShopNewActivity;
import com.bh.yibeitong.ui.order.OrderCommentActivity;
import com.bh.yibeitong.ui.order.OrderDetaileActivity;
import com.bh.yibeitong.utils.BaseRecyclerViewHolder;
import com.bh.yibeitong.utils.CodeUtils;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.CustomDialog;
import com.bh.yibeitong.view.UserInfo;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingang on 2016/11/28.
 * 测试fragment懒加载
 */

public class FMOrder extends BaseFragment implements
        PullToRefreshView.OnHeaderRefreshListener,
        PullToRefreshView.OnFooterRefreshListener {

    private TextView tv_header_title;
    private ImageView iv_header_left, iv_header_right;

    //private ListView lv_order;

    /*本地存储*/
    UserInfo userInfo;
    private String jingang;//有请我的大名！
    public static String uid = "", pwd = "", phone = "";
    private Intent intent;

    /*适配器*/
    private OrderAdapter orderAdapter;
    private List<Order.MsgBean> msgBeanList = new ArrayList<>();

    private View view;

    //没有登录登录时布局
    private Button but_login;
    private TextView tv_nologin_title;

    /*判断是否登录*/
    private LinearLayout lin_login, lin_nologin;

    /*接口地址*/
    private String PATH = "";

    /*UI显示*/
//    private PullToRefreshView pullToRefreshView;
//    private MyListView myListView;

    /*是否到达底部*/
    public boolean isToTops = false;
    private int page = 0;


    /**
     * 服务器端一共多少条数据
     */
    private static final int TOTAL_COUNTER = 34;
    //如果服务器没有返回总数据或者总页数，这里设置为最大值比如10000，什么时候没有数据了根据接口返回判断

    /**
     * 每一页展示多少条数据
     */
    private static final int REQUEST_COUNT = 10;

    /**
     * 已经获取到多少条数据了
     */
    private static int mCurrentCounter = 0;

    private LRecyclerView mRecyclerView = null;

    // private DataAdapter mDataAdapter = null;

    // private PreviewHandler mHandler = new PreviewHandler(this);

    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("订单页 加载");

        view = inflater.inflate(R.layout.fragment_orders, container, false);

        initData();
        initDatas();

        return view;
    }

    /**
     * 组件初始化 登录状态下
     */
    public void initData() {

        userInfo = new UserInfo(getActivity().getApplication());
        jingang = userInfo.getLogin();

        tv_header_title = (TextView) view.findViewById(R.id.tv_header_title);
        iv_header_left = (ImageView) view.findViewById(R.id.iv_header_left);
        iv_header_right = (ImageView) view.findViewById(R.id.iv_header_right);

        tv_header_title.setText("我的订单");
        iv_header_left.setVisibility(View.INVISIBLE);

        //lv_order = (ListView) view.findViewById(R.id.lv_order);

        /**/
        lin_login = (LinearLayout) view.findViewById(R.id.lin_order_login);
        lin_nologin = (LinearLayout) view.findViewById(R.id.lin_order_nologin);

//        pullToRefreshView = (PullToRefreshView) view.findViewById(R.id.ptrv_order);
//        myListView = (MyListView) view.findViewById(R.id.mlv_order);
//
//        orderAdapter = new OrderAdapter(getActivity(), msgBeanList);
//        mRecyclerView.setAdapter(orderAdapter);


//        pullToRefreshView.setOnHeaderRefreshListener(this);
//        pullToRefreshView.setOnFooterRefreshListener(this);
//        pullToRefreshView.setLastUpdated(new Date().toLocaleString());


        mRecyclerView = (LRecyclerView) view.findViewById(R.id.list);

        orderAdapter = new OrderAdapter(getActivity(), msgBeanList);

        //mDataAdapter = new DataAdapter(getActivity());
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(orderAdapter);
        mRecyclerView.setAdapter(mLRecyclerViewAdapter);

        DividerDecoration divider = new DividerDecoration.Builder(getActivity())
                .setHeight(R.dimen.dp_4)
                .setPadding(R.dimen.dp_4)
                .setColorResource(R.color.rel_back)
                .build();

        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(divider);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader);
        mRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);

        //add a HeaderView
//        final View header = LayoutInflater.from(getActivity()).inflate(R.layout.sample_header, (ViewGroup) findViewById(android.R.id.content), false);
//        mLRecyclerViewAdapter.addHeaderView(header);

        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {

                //mDataAdapter.clear();
                mLRecyclerViewAdapter.notifyDataSetChanged();//fix bug:crapped or attached views may not be recycled. isScrap:false isAttached:true
                mCurrentCounter = 0;

                //requestData();

                // mRecyclerView.refreshComplete(REQUEST_COUNT);

                System.out.println("刷新数据");

                //msgBeanList.clear();

                isLogin();

            }
        });

        //禁用自动加载更多功能
        mRecyclerView.setLoadMoreEnabled(true);

        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                if (mCurrentCounter < TOTAL_COUNTER) {
                    // loading more
                    //requestData();
                    System.out.println("加载更多");

                    // mRecyclerView.refreshComplete(REQUEST_COUNT);
                    mRecyclerView.setNoMore(true);
                } else {
                    //the end
                    mRecyclerView.setNoMore(true);
                }
            }
        });

        mRecyclerView.setLScrollListener(new LRecyclerView.LScrollListener() {

            @Override
            public void onScrollUp() {
            }

            @Override
            public void onScrollDown() {
            }


            @Override
            public void onScrolled(int distanceX, int distanceY) {
            }

            @Override
            public void onScrollStateChanged(int state) {

            }

        });

        //设置头部加载颜色
        mRecyclerView.setHeaderViewColor(R.color.colorAccent, R.color.gray, android.R.color.white);
        //设置底部加载颜色
        mRecyclerView.setFooterViewColor(R.color.colorAccent, R.color.gray, android.R.color.white);
        //设置底部加载文字提示
        mRecyclerView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");

        //mRecyclerView.refresh();

        isLogin();


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
                        intent = new Intent(getActivity(), LoginRegisterActivity.class);
                        startActivityForResult(intent, CodeUtils.REQUEST_CODE_HOME_ORDER);
                        //startActivity(new Intent(getActivity(), LoginRegisterActivity.class));
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


    /*判断登录状态*/
    public void isLogin() {
        jingang = userInfo.getLogin();

        if (jingang.equals("1")) {
            //验证登录方式
            if (!(userInfo.getUserInfo().equals(""))) {
                Register register = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), Register.class);
                uid = register.getMsg().getUid();
                phone = register.getMsg().getPhone();

                if (!(userInfo.getPwd().equals(""))) {
                    pwd = userInfo.getPwd();
                }

                if (userInfo.getCode().equals("0")) {
                    System.out.println("我的验证码" + userInfo.getCode());
                    getOrder(uid, pwd, 1, 10);
                } else if (userInfo.getCode().equals("1")) {
                    System.out.println("我的手机号" + phone);
                    getOrder("phone", phone, 1, 10);
                } else {
                }

            }

            lin_login.setVisibility(View.VISIBLE);
            lin_nologin.setVisibility(View.GONE);
        } else {
            lin_login.setVisibility(View.GONE);
            lin_nologin.setVisibility(View.VISIBLE);
        }
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

            //msgBeanList.clear();

            isLogin();


        }
    }

    /**
     * 获取订单
     *
     * @param uid
     * @param pwd
     */
    public void getOrder(String uid, String pwd, int page, int pagesize) {
        if (userInfo.getCode().equals("0")) {
            PATH = HttpPath.PATH + HttpPath.ORDER_NEW +
                    "uid=" + uid + "&pwd=" + pwd;
            //+"&page=" + page + "&pagesize=" + pagesize
        } else if (userInfo.getCode().equals("1")) {
            PATH = HttpPath.PATH + HttpPath.ORDER_NEW +
                    "logintype=" + uid + "&loginphone=" + pwd;
        } else {
        }

        System.out.println("订单列表" + PATH);

        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("订单列表" + result);
                        isToTops = true;

                        mRecyclerView.refreshComplete(REQUEST_COUNT);

                        msgBeanList.clear();

                        Order order = GsonUtil.gsonIntance().gsonToBean(result, Order.class);
                        //msgBeanList = order.getMsg();

                        msgBeanList.addAll(order.getMsg());
                        orderAdapter.notifyDataSetChanged();

                        orderAdapter.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(getActivity(), OrderDetaileActivity.class);
                                intent.putExtra("orderid", msgBeanList.get(position - 1).getId());
                                intent.putExtra("status", msgBeanList.get(position - 1).getStatus());
                                startActivityForResult(intent, CodeUtils.REQUEST_CODE_HOME_ORDER);
//
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
     * 提示框(未登录)
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CodeUtils.REQUEST_CODE_HOME_ORDER) {
            if (resultCode == CodeUtils.REQUEST_CODE_LOGIN) {
                isLogin();
            } else if (resultCode == CodeUtils.REQUEST_CODE_ORDER_COMMENT) {
                isLogin();
            } else if (resultCode == 100) {
                System.out.println("00000000");
                isLogin();
                //orderAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {

    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
//        pullToRefreshView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                pullToRefreshView.onHeaderRefreshComplete("更新于："
//                        + Calendar.getInstance().getTime().toLocaleString());
//                pullToRefreshView.onHeaderRefreshComplete();
//
//                msgBeanList.clear();
//
//                isLogin();
//
//            }
//
//        }, 1000);
    }

    /**
     * 将适配器嵌套在Activity中 更好的操作Activity中的控件变化
     * 不知道有木有更好的办法
     */

//    public class OrderAdapter extends BaseAdapter {
//        private Context mContext;
//        private List<Order.MsgBean> msgBeen = new ArrayList<>();
//
//        public OrderAdapter(Context mContext, List<Order.MsgBean> msgBeen) {
//            this.mContext = mContext;
//            this.msgBeen = msgBeen;
//        }
//
//        @Override
//        public int getCount() {
//            return msgBeen.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return msgBeen.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent) {
//            final ViewHolder vh;
//            if (convertView == null) {
//                vh = new ViewHolder();
//                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_order, null);
//
//                vh.img = (ImageView) convertView.findViewById(R.id.iv_item_order_img);
//                vh.shopname = (TextView) convertView.findViewById(R.id.tv_item_order_shopname);
//                vh.state = (TextView) convertView.findViewById(R.id.tv_item_order_state);
//                vh.price = (TextView) convertView.findViewById(R.id.tv_item_order_price);
//                vh.time = (TextView) convertView.findViewById(R.id.tv_item_order_time);
//
//                /*删除订单 确认收货 评价订单 再来一单*/
//                vh.del = (Button) convertView.findViewById(R.id.but_item_order_del);
//                vh.c_receipt = (Button) convertView.findViewById(R.id.but_item_order_c_receipt);
//                vh.comment = (Button) convertView.findViewById(R.id.but_item_order_comment);
//                vh.again = (Button) convertView.findViewById(R.id.but_item_order_again);
//                vh.repay = (Button) convertView.findViewById(R.id.but_item_order_repay);
//
//
//                convertView.setTag(vh);
//            } else {
//                vh = (ViewHolder) convertView.getTag();
//            }
//            // 组件赋值
//            String imgPath = msgBeen.get(position).getShoplogo();
//            String shopname = msgBeen.get(position).getShopname();
//            String state = msgBeen.get(position).getSeestatus();
//            final String allcost = msgBeen.get(position).getAllcost();
//            String time = msgBeen.get(position).getAddtime();
//
//            //订单状态
//            final String status = msgBeen.get(position).getStatus();
//
//            /*支付状态*/
//            String paystatus = msgBeen.get(position).getPaystatus();
//
//            final String orderid = msgBeen.get(position).getId();
//
//            final String is_ping = msgBeen.get(position).getIs_ping();
//
//
//            if (imgPath.equals("")) {
//                vh.img.setImageResource(R.mipmap.yibeitong001);
//
//            } else {
//                x.image().bind(vh.img, imgPath);
//            }
//
//            vh.shopname.setText(shopname);
//            vh.state.setText(state);
//            vh.price.setText("￥" + allcost);
//            vh.time.setText(time);
//
//            /*订单状态*/
//            if (status.toString().equals("0")) {
//                //新订单
//                vh.again.setVisibility(View.VISIBLE);
//                vh.del.setVisibility(View.INVISIBLE);
//                vh.c_receipt.setVisibility(View.INVISIBLE);
//                vh.comment.setVisibility(View.INVISIBLE);
//
//
//            } else if (status.toString().equals("1")) {
//                //待发货
//                vh.del.setVisibility(View.INVISIBLE);
//                vh.again.setVisibility(View.VISIBLE);
//
//                vh.c_receipt.setVisibility(View.INVISIBLE);
//                vh.comment.setVisibility(View.INVISIBLE);
//            } else if (status.toString().equals("2")) {
//                //待确认
//                vh.del.setVisibility(View.INVISIBLE);
//                vh.c_receipt.setVisibility(View.VISIBLE);
//                vh.again.setVisibility(View.VISIBLE);
//                vh.comment.setVisibility(View.INVISIBLE);
//
//            } else if (status.toString().equals("3")) {
//                //已完成
//                vh.del.setVisibility(View.INVISIBLE);
//
//                vh.again.setVisibility(View.VISIBLE);
//
//                if (is_ping.equals("0")) {
//                    vh.comment.setVisibility(View.VISIBLE);
//                } else if (is_ping.equals("1")) {
//                    vh.comment.setVisibility(View.GONE);
//                } else {
//                    vh.comment.setVisibility(View.GONE);
//                }
//
//            } else if (status.toString().equals("4")) {
//                //订单取消
//                vh.del.setVisibility(View.VISIBLE);
//
//            } else if (status.toString().equals("5")) {
//                //订单取消
//                vh.del.setVisibility(View.VISIBLE);
//            }
//
//            if (paystatus.equals("0")) {
//                //未支付
//                vh.repay.setVisibility(View.VISIBLE);
//            } else if (paystatus.equals("1")) {
//                //已支付
//                vh.repay.setVisibility(View.GONE);
//            } else {
//                //错误
//                vh.repay.setVisibility(View.GONE);
//            }
//
//            //删除订单
//            vh.del.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String PATH;
//                    if (userInfo.getCode().equals("0")) {
//                        PATH = HttpPath.PATH + HttpPath.ORDER_DEL +
//                                "uid=" + uid + "&pwd=" + pwd + "&orderid=" + orderid;
//                    } else {
//
//                        PATH = HttpPath.PATH + HttpPath.ORDER_DEL +
//                                "logintype=phone" + "+&loginphone" + pwd + "&orderid=" + orderid;
//                    }
//
//                    System.out.println(PATH);
//
//                    RequestParams params = new RequestParams(PATH);
//                    x.http().post(params,
//                            new Callback.CommonCallback<String>() {
//                                @Override
//                                public void onSuccess(String result) {
//                                    System.out.println("删除订单" + result);
//
//                                    OrderDel orderDel = GsonUtil.gsonIntance().gsonToBean(result, OrderDel.class);
//                                    if (orderDel.isError() == false) {
//                                        Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
//                                        msgBeen.remove(position);
//                                        orderAdapter.notifyDataSetChanged();
//                                    } else {
//                                        Toast.makeText(mContext, orderDel.getMsg().toString(), Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//
//                                @Override
//                                public void onError(Throwable ex, boolean isOnCallback) {
//
//                                }
//
//                                @Override
//                                public void onCancelled(CancelledException cex) {
//
//                                }
//
//                                @Override
//                                public void onFinished() {
//
//                                }
//                            });
//
//                }
//            });
//
//            /*确认收货*/
//            vh.c_receipt.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    String PATH;
//                    if (userInfo.getCode().toString().equals("0")) {
//                        PATH = HttpPath.PATH + HttpPath.ORDER_NEW_CONTROL +
//                                "uid=" + uid + "&pwd=" + pwd + "&doname=sureorder" + "&orderid=" + orderid;
//                    } else {
//                        PATH = HttpPath.PATH + HttpPath.ORDER_NEW_CONTROL +
//                                "logintype=phone" + "&loginphone=" + phone + "&doname=sureorder" + "&orderid=" + orderid;
//                    }
//
//                    RequestParams params = new RequestParams(PATH);
//                    x.http().post(params,
//                            new Callback.CommonCallback<String>() {
//                                @Override
//                                public void onSuccess(String result) {
//                                    System.out.println("确认收货" + result);
//                                    Errors error = GsonUtil.gsonIntance().gsonToBean(result, Errors.class);
//
//                                    if (error.isError() == false) {
//                                        vh.comment.setVisibility(View.VISIBLE);
//                                        vh.c_receipt.setVisibility(View.INVISIBLE);
//
//                                        orderAdapter.notifyDataSetChanged();
//
//                                    } else {
//
//                                    }
//                                    Toast.makeText(mContext, error.getMsg().toString(), Toast.LENGTH_SHORT).show();
//
//                                }
//
//                                @Override
//                                public void onError(Throwable ex, boolean isOnCallback) {
//
//                                }
//
//                                @Override
//                                public void onCancelled(CancelledException cex) {
//
//                                }
//
//                                @Override
//                                public void onFinished() {
//
//                                }
//                            });
//
//                }
//            });
//
//            /*再来一单*/
//            vh.again.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //跳到商店
//
//                }
//            });
//
//            /*评价订单*/
//            vh.comment.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //
//                    Intent intent = new Intent(mContext, OrderCommentActivity.class);
//                    intent.putExtra("orderid", orderid);
//                    startActivityForResult(intent, CodeUtils.REQUEST_CODE_HOME_ORDER);
//
//                }
//            });
//
//            /*继续支付*/
//            vh.repay.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (status.equals("0")) {
//                        intent = new Intent(getActivity(), PayActivity.class);
//                        intent.putExtra("dno", msgBeen.get(position).getDno());
//                        intent.putExtra("shopcost", allcost);
//                        intent.putExtra("orderid", orderid);
//                        intent.putExtra("type", "order");
//
//                        startActivityForResult(intent, CodeUtils.REQUEST_CODE_HOME_ORDER);
//                    } else {
//                        toast("订单状态不能支付");
//                    }
//
//                }
//            });
//
//
//            return convertView;
//        }
//
//        public class ViewHolder {
//            private TextView shopname, price, state, time;
//            private ImageView img;
//            private Button del, comment, again, c_receipt, repay;
//
//        }
//    }


    /*
    * LRecyclerView适配器
    * */
    class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

        private OnItemClickListener mOnItemClickListener;
        private OnItemLongClickListener mOnItemLongClickListener;

        public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
            this.mOnItemClickListener = mOnItemClickListener;
        }

        public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
            this.mOnItemLongClickListener = mOnItemLongClickListener;
        }

        private Context mContext;
        private List<Order.MsgBean> msgBeen;

        public OrderAdapter(Context mContext, List<Order.MsgBean> msgBeen) {
            this.mContext = mContext;
            this.msgBeen = msgBeen;
        }

        @Override
        public OrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            OrderAdapter.MyViewHolder vh = new OrderAdapter.MyViewHolder(LayoutInflater.from(
                    getActivity()).inflate(R.layout.item_order, parent,
                    false));
            return vh;
        }

        @Override
        public void onBindViewHolder(final OrderAdapter.MyViewHolder vh, final int position) {
            if (mOnItemClickListener != null) {
                //为ItemView设置监听器
                vh.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = vh.getLayoutPosition(); // 1
                        mOnItemClickListener.onItemClick(vh.itemView, position); // 2
                    }
                });
            }
            if (mOnItemLongClickListener != null) {
                vh.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int position = vh.getLayoutPosition();
                        mOnItemLongClickListener.onItemLongClick(vh.itemView, position);
                        //返回true 表示消耗了事件 事件不会继续传递
                        return true;
                    }
                });
            }


//            holder.time.setText(msgBeen.get(position).getAddtime());

            // 组件赋值
            String imgPath = msgBeen.get(position).getShoplogo();
            String shopname = msgBeen.get(position).getShopname();
            String state = msgBeen.get(position).getSeestatus();
            final String allcost = msgBeen.get(position).getAllcost();
            String time = msgBeen.get(position).getAddtime();

            //订单状态
            final String status = msgBeen.get(position).getStatus();

            /*支付状态*/
            String paystatus = msgBeen.get(position).getPaystatus();

            final String orderid = msgBeen.get(position).getId();

            final String is_ping = msgBeen.get(position).getIs_ping();


            if (imgPath.equals("")) {
                vh.img.setImageResource(R.mipmap.yibeitong001);

            } else {
                x.image().bind(vh.img, imgPath);
            }

            vh.shopname.setText(shopname);
            vh.state.setText(state);
            vh.price.setText("￥" + allcost);
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

                vh.again.setVisibility(View.VISIBLE);

                if (is_ping.equals("0")) {
                    vh.comment.setVisibility(View.VISIBLE);
                } else if (is_ping.equals("1")) {
                    vh.comment.setVisibility(View.GONE);
                } else {
                    vh.comment.setVisibility(View.GONE);
                }

            } else if (status.toString().equals("4")) {
                //订单取消
                vh.del.setVisibility(View.VISIBLE);

            } else if (status.toString().equals("5")) {
                //订单取消
                vh.del.setVisibility(View.VISIBLE);
            }

            if (paystatus.equals("0")) {
                //未支付
                vh.repay.setVisibility(View.VISIBLE);
            } else if (paystatus.equals("1")) {
                //已支付
                vh.repay.setVisibility(View.GONE);
            } else {
                //错误
                vh.repay.setVisibility(View.GONE);
            }

            //删除订单
            vh.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String PATH;
                    if (userInfo.getCode().equals("0")) {
                        PATH = HttpPath.PATH + HttpPath.ORDER_DEL +
                                "uid=" + uid + "&pwd=" + pwd + "&orderid=" + orderid;
                    } else {

                        PATH = HttpPath.PATH + HttpPath.ORDER_DEL +
                                "logintype=phone" + "+&loginphone" + pwd + "&orderid=" + orderid;
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
                                    Errors error = GsonUtil.gsonIntance().gsonToBean(result, Errors.class);

                                    if (error.isError() == false) {
                                        vh.comment.setVisibility(View.VISIBLE);
                                        vh.c_receipt.setVisibility(View.INVISIBLE);

                                        orderAdapter.notifyDataSetChanged();

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

                    intent = new Intent(getActivity(), ShopNewActivity.class);
                    intent.putExtra("shopid", FMHomePage.shopid);
                    intent.putExtra("shopname", FMHomePage.shopName);
                    intent.putExtra("startTime", FMHomePage.startTime);
                    intent.putExtra("mapphone", FMHomePage.mapphone);
                    intent.putExtra("address", FMHomePage.address);

                    intent.putExtra("lat", FMHomePage.latitude);
                    intent.putExtra("lng", FMHomePage.longtitude);
                    startActivity(intent);

                }
            });

            /*评价订单*/
            vh.comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //
                    Intent intent = new Intent(mContext, OrderCommentActivity.class);
                    intent.putExtra("orderid", orderid);
                    startActivityForResult(intent, CodeUtils.REQUEST_CODE_HOME_ORDER);

                }
            });

            /*继续支付*/
            vh.repay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (status.equals("0")) {
                        intent = new Intent(getActivity(), PayActivity.class);
                        intent.putExtra("dno", msgBeen.get(position).getDno());
                        intent.putExtra("shopcost", allcost);
                        intent.putExtra("orderid", orderid);
                        intent.putExtra("type", "order");

                        startActivityForResult(intent, CodeUtils.REQUEST_CODE_HOME_ORDER);
                    } else {
                        toast("订单状态不能支付");
                    }

                }
            });

        }

        @Override
        public int getItemCount() {
            return msgBeen.size();
        }

        class MyViewHolder extends BaseRecyclerViewHolder {

            private TextView shopname, price, state, time;
            private ImageView img;
            private Button del, comment, again, c_receipt, repay;


            public MyViewHolder(View convertView) {
                super(convertView);
                time = (TextView) convertView.findViewById(R.id.tv_item_order_time);
                img = (ImageView) convertView.findViewById(R.id.iv_item_order_img);
                shopname = (TextView) convertView.findViewById(R.id.tv_item_order_shopname);
                state = (TextView) convertView.findViewById(R.id.tv_item_order_state);
                price = (TextView) convertView.findViewById(R.id.tv_item_order_price);
                time = (TextView) convertView.findViewById(R.id.tv_item_order_time);

                /*删除订单 确认收货 评价订单 再来一单*/
                del = (Button) convertView.findViewById(R.id.but_item_order_del);
                c_receipt = (Button) convertView.findViewById(R.id.but_item_order_c_receipt);
                comment = (Button) convertView.findViewById(R.id.but_item_order_comment);
                again = (Button) convertView.findViewById(R.id.but_item_order_again);
                repay = (Button) convertView.findViewById(R.id.but_item_order_repay);
            }
        }
    }


}