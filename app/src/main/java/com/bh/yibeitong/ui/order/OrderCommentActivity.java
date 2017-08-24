package com.bh.yibeitong.ui.order;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.Error;
import com.bh.yibeitong.bean.OrderComment;
import com.bh.yibeitong.bean.Register;
import com.bh.yibeitong.ordercomment.CommonAdapter;
import com.bh.yibeitong.ordercomment.ViewHolder;
import com.bh.yibeitong.utils.CodeUtils;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.UserInfo;
import com.lidroid.xutils.BitmapUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingang on 2016/12/19.
 */

public class OrderCommentActivity extends BaseTextActivity {
    /*本地缓存*/
    private UserInfo userInfo;
    String uid, pwd, phone;

    Intent intent;
    String orderid;

    //适配器 显示订单列表
    CommonAdapter<OrderComment.MsgBean> mAdapter;
    List<OrderComment.MsgBean> msgBeen = new ArrayList<>();
    private ListView listView;

    /*评价星级*/
    private String xingxing = "0";
    private String pointcontent = "";
    private Button comment;
    String orderdetid;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_order_comment);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("评价订单");
        setTitleBack(true, 0);
        initData();
    }

    public void initData() {
        userInfo = new UserInfo(getApplication());

        listView = (ListView) findViewById(R.id.lv_order_comment);
        comment = (Button) findViewById(R.id.but_order_comment);
        comment.setOnClickListener(this);

        intent = getIntent();
        orderid = intent.getStringExtra("orderid");

        //验证登录方式
        if (!(userInfo.getUserInfo().equals(""))) {
            Register register = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), Register.class);
            uid = register.getMsg().getUid();
            phone = register.getMsg().getPhone();
            if (!(userInfo.getPwd().equals(""))) {
                pwd = userInfo.getPwd();
                if (userInfo.getCode().equals("0")) {
                    getCommentList(uid, pwd, orderid);
                } else {
                    getCommentList("phone", phone, orderid);
                }
            }
        }

        System.out.println("orderid = " + orderid);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.but_order_comment:
                //提交订单评论
                for (int i = 0; i < msgBeen.size(); i++) {
                    orderdetid = msgBeen.get(i).getId();
                    xingxing = msgBeen.get(i).getXing();
                    pointcontent = msgBeen.get(i).getContent();
                    if (userInfo.getCode().toString().equals("0")) {
                        postCommentList(uid, pwd, orderdetid, xingxing, pointcontent);
                    } else {
                        postCommentList("phone", phone, orderdetid, xingxing, pointcontent);
                    }
                    //System.out.println("&&&& = " + orderdetid + "" + xingxing + "" + pointcontent);

                }


                break;

            default:
                break;
        }
    }

    /**
     * 获取评价订单列表
     *
     * @param uid
     * @param pwd
     * @param orderid
     */
    public void getCommentList(String uid, String pwd, String orderid) {
        String PATH;
        if (userInfo.getCode().toString().equals("0")) {
            PATH = HttpPath.PATH + HttpPath.ORDER_NEW_PINGLIST +
                    "uid=" + uid + "&pwd=" + pwd + "&orderid=" + orderid;
        } else {
            PATH = HttpPath.PATH + HttpPath.ORDER_NEW_PINGLIST +
                    "logintype=" + uid + "&loginphone=" + pwd + "&orderid=" + orderid;
        }

        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("获取评价订单列表" + result);
                        OrderComment orderComment = GsonUtil.gsonIntance().gsonToBean(result, OrderComment.class);
                        msgBeen = orderComment.getMsg();

                        mAdapter = new CommonAdapter<OrderComment.MsgBean>(OrderCommentActivity.this,
                                msgBeen, R.layout.item_order_comment) {
                            @Override
                            public void convert(ViewHolder helper, final OrderComment.MsgBean item, int position) {

                                TextView tv_name = helper.getView(R.id.tv_item_oc_title);
                                tv_name.setText(msgBeen.get(position).getGoodsname());

                                ImageView iv_img = helper.getView(R.id.iv_item_oc_img);

                                if (msgBeen.get(position).getImg().toString().equals("")) {
                                    iv_img.setImageResource(R.mipmap.yibeitong001);

                                } else {
                                    BitmapUtils bitmapUtils = new BitmapUtils(mContext);

                                    bitmapUtils.configDiskCacheEnabled(true);
                                    bitmapUtils.configMemoryCacheEnabled(false);

                                    bitmapUtils.display(iv_img, msgBeen.get(position).getImg());

                                }


                                ((RatingBar) helper.getView(R.id.rb_order_comment)).
                                        setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                                            @Override
                                            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                                                item.setXing(rating + "");
                                            }
                                        });

                                ((EditText) helper.getView(R.id.et_item_order_comment)).addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        item.setContent(s + "");
                                    }
                                });

                            }
                        };

                        listView.setAdapter(mAdapter);

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

    /**
     * 提交订单评论
     *
     * @param uid
     * @param pwd
     * @param orderdetid
     * @param point
     * @param pointcontent
     */
    public void postCommentList(String uid, String pwd, String orderdetid, String point, String pointcontent) {
        String PATH;
        if (userInfo.getCode().toString().equals("0")) {
            PATH = HttpPath.PATH + HttpPath.ORDER_NEW_PINGORDER +
                    "uid=" + uid + "&pwd=" + pwd + "&orderdetid=" + orderdetid +
                    "&point=" + point + "&pointcontent=" + pointcontent;
        } else {
            PATH = HttpPath.PATH + HttpPath.ORDER_NEW_PINGORDER +
                    "logintype=" + uid + "&loginphone=" + pwd + "&orderdetid=" + orderdetid +
                    "&point=" + point + "&pointcontent=" + pointcontent;
        }

        RequestParams params = new RequestParams(PATH);
        x.http().post(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("提交订单评论" + result);

                        Error error = GsonUtil.gsonIntance().gsonToBean(result, Error.class);

                        if (error.isError() == false) {
                            intent = new Intent();
                            setResult(CodeUtils.REQUEST_CODE_ORDER_COMMENT, intent);
                            OrderCommentActivity.this.finish();
                        } else {

                        }

                        toast("" + error.getMsg().toString());
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

}
