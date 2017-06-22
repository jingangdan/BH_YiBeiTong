package com.bh.yibeitong.ui.village;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.Register;
import com.bh.yibeitong.bean.village.VPBDetailed;
import com.bh.yibeitong.refresh.MyListView;
import com.bh.yibeitong.utils.EmojiTextView;
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
 * Created by jingang on 2016/12/30.
 * 小区帖子详情
 */

public class VillagePBDetailedActivity extends BaseTextActivity {
    /*UI*/
    private TextView tv_title, tv_user, tv_data, tv_like, tv_comment;
    private LinearLayout lin_like, lin_comment;
    private ImageView iv_like;
    /*赞 分享 评论*/
    private Button but_zan, but_share, but_comment;

    private EmojiTextView tv_bady;

    /*显示图片*/
    private MyListView myListView_img;
    private UserImgAdapter userImgAdapter;

    /*页面传值*/
    private Intent intent;
    String id, title, usercontent, username, creattime, zongzanshu, pingjiazongshu;

    /*评论列表*/
    //private ListView listView;
    private VPBCommentAdapter vpbCommentAdapter;
    private MyListView myListView_comment;
    private List<VPBDetailed.MsgBean.WxreplylistBean> wxreplylistBeen;
    private TextView comment_null;

    private View parentView;

    /*本地缓存*/
    UserInfo userInfo;
    private String uid, pwd, phone;

    /*接口地址*/
    String PATH;


    @Override
    protected void setRootView() {
        super.setRootView();
        //setContentView(R.layout.activity_village_pb_detailed);
        parentView = getLayoutInflater().inflate(R.layout.activity_village_pb_detailed, null);
        setContentView(parentView);

    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("帖子详情");
        setTitleBack(true, 0);

        initData();

    }

    /*组件初始化*/
    public void initData() {
        userInfo = new UserInfo(getApplication());

        tv_title = (TextView) findViewById(R.id.tv_vpb_detailed_title);
        tv_bady = (EmojiTextView) findViewById(R.id.tv_vpb_detailed_bady);
        tv_user = (TextView) findViewById(R.id.tv_vpb_detailed_user);
        tv_data = (TextView) findViewById(R.id.tv_vpb_detailed_data);
        tv_like = (TextView) findViewById(R.id.tv_vpb_detailed_like);
        tv_comment = (TextView) findViewById(R.id.tv_vpb_detailed_comment);

        lin_like = (LinearLayout) findViewById(R.id.lin_pb_detailed_zan);
        lin_comment = (LinearLayout) findViewById(R.id.lin_pb_detailed_comment);

        lin_like.setOnClickListener(this);
        lin_comment.setOnClickListener(this);


        myListView_img = (MyListView) findViewById(R.id.mlv_vpb_detailed);

        myListView_comment = (MyListView) findViewById(R.id.mlv_vpb_comment);
        comment_null = (TextView) findViewById(R.id.tv_vpb_c_null);

        /*赞 分享 评论*/
        but_zan = (Button) findViewById(R.id.but_vpb_zan);
        but_share = (Button) findViewById(R.id.but_vpb_share);
        but_comment = (Button) findViewById(R.id.but_vpb_comment);
        but_zan.setOnClickListener(this);
        but_share.setOnClickListener(this);
        but_comment.setOnClickListener(this);

        /*接收页面传值*/
        intent = getIntent();
        id = intent.getStringExtra("id");
        title = intent.getStringExtra("title");
        usercontent = intent.getStringExtra("usercontent");
        username = intent.getStringExtra("username");
        creattime = intent.getStringExtra("creattime");
        zongzanshu = intent.getStringExtra("zongzanshu");
        pingjiazongshu = intent.getStringExtra("pingjiazongshu");

        /*更新UI*/
        tv_title.setText("" + title);
        tv_bady.setText("" + usercontent);
        tv_user.setText("" + username);
        tv_data.setText("" + creattime);
        tv_like.setText("" + zongzanshu);
        tv_comment.setText("" + pingjiazongshu);

        //验证登录方式
        if (!(userInfo.getUserInfo().equals(""))) {
            Register register = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), Register.class);
            uid = register.getMsg().getUid();
            phone = register.getMsg().getPhone();
            if (!(userInfo.getPwd().equals(""))) {
                pwd = userInfo.getPwd();

                if (userInfo.getCode().equals("0")) {
                    getPostBarDetailed(uid, pwd, id);
                } else {
                    System.out.println("我的手机号" + phone);
                    getPostBarDetailed("phone", phone, id);
                }

            }
        } else {
            toast("未登录");
        }


        //getPostBarDetailed(id);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.lin_pb_detailed_zan:
                //点赞
                break;

            case R.id.lin_pb_detailed_comment:
                //评论
                Init();
                break;

            case R.id.but_vpb_comment:
                //底部按钮 评论
                Init();
                break;

            default:
                break;
        }
    }

    private PopupWindow pop = null;
    private LinearLayout ll_comment_test;

    public void Init() {
        pop = new PopupWindow(VillagePBDetailedActivity.this);

        View view = getLayoutInflater().inflate(R.layout.dialog_comment, null);

        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.rel_parent);

        ll_comment_test = (LinearLayout) view.findViewById(R.id.ll_comment_test);

        view.setAnimation(AnimationUtils.loadAnimation(
                VillagePBDetailedActivity.this, R.anim.slide_bottom_to_top));

        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);

        //防止pop被软键盘挡住
        pop.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);

        parent.setOnClickListener(new View.OnClickListener() {
            @Override
             public void onClick(View view) {
                pop.dismiss();
                ll_comment_test.clearAnimation();
            }
        });


    }

    /**
     * 小区帖子详情
     *
     * @param id
     */
    public void getPostBarDetailed(String uid, String pwd, String id) {
        if (userInfo.getCode().toString().equals("0")) {
            PATH = HttpPath.PATH + HttpPath.VILLAGE_COMMENT_TWUSER +
                    "uid=" + uid + "&pwd=" + pwd + "&id=" + id;
        } else {
            PATH = HttpPath.PATH + HttpPath.VILLAGE_COMMENT_TWUSER +
                    "logintype=" + uid + "&loginphone=" + pwd + "&id=" + id;
        }

        System.out.println("" + PATH);
        RequestParams params = new RequestParams(PATH);

        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("帖子详情" + result);
                        VPBDetailed vpbDetailed = GsonUtil.gsonIntance().gsonToBean(result, VPBDetailed.class);

                        //图片适配器
                        userImgAdapter = new UserImgAdapter(VillagePBDetailedActivity.this, vpbDetailed.getMsg().getUserimages());
                        myListView_img.setAdapter(userImgAdapter);

                        //评论列表适配器
                        if (vpbDetailed.getMsg().getWxreplylist().size() == 0) {
                            comment_null.setVisibility(View.VISIBLE);
                        } else {
                            vpbCommentAdapter = new VPBCommentAdapter(VillagePBDetailedActivity.this, vpbDetailed.getMsg().getWxreplylist());
                            myListView_comment.setAdapter(vpbCommentAdapter);
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


    /**
     * 评论列表适配器
     */
    public class VPBCommentAdapter extends BaseAdapter {
        private Context mContext;
        private List<VPBDetailed.MsgBean.WxreplylistBean> wxreplylistBeanList = new ArrayList<>();

        public VPBCommentAdapter(Context mContext, List<VPBDetailed.MsgBean.WxreplylistBean> wxreplylistBeanList) {
            this.mContext = mContext;
            this.wxreplylistBeanList = wxreplylistBeanList;
        }

        @Override
        public int getCount() {
            return wxreplylistBeanList.size();
        }

        @Override
        public Object getItem(int i) {
            return wxreplylistBeanList.get(i);
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
                view = LayoutInflater.from(mContext).inflate(R.layout.item_vpb_comment, null);

                vh.iv_header = (ImageView) view.findViewById(R.id.iv_item_vpb_c_header);
                vh.tv_user = (TextView) view.findViewById(R.id.tv_item_vpb_c_user);
                vh.tv_time = (TextView) view.findViewById(R.id.tv_item_vpb_c_time);
                vh.tv_content = (TextView) view.findViewById(R.id.tv_item_vpb_c_content);

                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }

            String user = wxreplylistBeanList.get(i).getUsername();
            String time = wxreplylistBeanList.get(i).getCreattime();
            String content = wxreplylistBeanList.get(i).getContent();

            if (wxreplylistBeanList.get(i).getLogo().toString().equals("")) {
                vh.iv_header.setImageResource(R.mipmap.yibeitong001);
            } else {
                BitmapUtils bitmapUtils = new BitmapUtils(mContext);
                bitmapUtils.configDiskCacheEnabled(true);
                bitmapUtils.configMemoryCacheEnabled(false);

                bitmapUtils.display(vh.iv_header, wxreplylistBeanList.get(i).getLogo());
            }

            vh.tv_user.setText("" + user);
            vh.tv_time.setText("" + time);
            vh.tv_content.setText("" + content);

            return view;
        }

        public class ViewHolder {
            ImageView iv_header;
            TextView tv_user, tv_time, tv_content;
        }
    }


    /**
     * 图片展示适配器
     */
    public class UserImgAdapter extends BaseAdapter {
        private Context mContext;
        private List<String> userImgs;

        public UserImgAdapter(Context mContext, List<String> userImgs) {
            this.mContext = mContext;
            this.userImgs = userImgs;
        }

        @Override
        public int getCount() {
            return userImgs.size();
        }

        @Override
        public Object getItem(int i) {
            return userImgs.get(i);
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
                view = LayoutInflater.from(mContext).inflate(R.layout.item_vpb_imgs, null);
                vh.img = (ImageView) view.findViewById(R.id.iv_item_vpb_imgs);

                view.setTag(vh);

            } else {
                vh = (ViewHolder) view.getTag();
            }

            if (userImgs.get(i).toString().equals("")) {
                //vh.img.setImageResource(R.mipmap.yibeitong001);
                //vh.img.setVisibility(View.INVISIBLE);
            } else {
                vh.img.setVisibility(View.VISIBLE);
                BitmapUtils bitmapUtils = new BitmapUtils(mContext);
                bitmapUtils.configDiskCacheEnabled(true);
                bitmapUtils.configMemoryCacheEnabled(false);

                bitmapUtils.display(vh.img, userImgs.get(i).toString());
            }

            return view;
        }

        public class ViewHolder {
            ImageView img;
        }

    }
}