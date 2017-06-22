package com.bh.yibeitong.ui.village;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.Error;
import com.bh.yibeitong.bean.Register;
import com.bh.yibeitong.bean.village.GuanZhuReturn;
import com.bh.yibeitong.bean.village.VillagePostBar;
import com.bh.yibeitong.refresh.MyListView;
import com.bh.yibeitong.ui.village.jiugongge.CustomImageView;
import com.bh.yibeitong.ui.village.jiugongge.Image;
import com.bh.yibeitong.ui.village.jiugongge.NineGridlayout;
import com.bh.yibeitong.ui.village.jiugongge.ScreenTools;
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
 * Created by binbin on 2016/12/13.
 * 小区信息 发布 显示
 */

public class VillagePostBarActivity extends BaseTextActivity {
    /*发布帖子按钮*/
    private ImageView write;

    /*接收页面传值*/
    private Intent intent;
    private String str_village;
    private String id, catename, parent_id, cate_order, img, guanzhu, count;

    /*UI组件*/
    private ImageView iv_img;
    private TextView v_name, v_guanzhu;
    private Button b_guanzhu;

    /*本地缓存*/
    private UserInfo userInfo;
    private String uid, pwd, phone;

    /*数据显示UI*/
    private MyListView myListView;
    private List<VillagePostBar.MsgBean.TogethersaylistBean>
            togethersaylistBeen = new ArrayList<>();
    private VillagePostBarAdapter vpbAdapter;

    /*接口地址*/
    private String PATH;
    private String str_result;

    private boolean isGuanzhu = false;
    private String villageId = "";

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_village_post_bar);
    }

    @Override
    protected void initWidght() {
        super.initWidght();

        initData();

        setTitleName(catename);
        setTitleBack(true, 0);
    }

    public void initData() {
        userInfo = new UserInfo(getApplication());

        write = (ImageView) findViewById(R.id.iv_write_postbar);
        write.setOnClickListener(this);

        iv_img = (ImageView) findViewById(R.id.iv_village_img);
        v_name = (TextView) findViewById(R.id.tv_village_name);
        v_guanzhu = (TextView) findViewById(R.id.tv_village_gaunzhu);
        b_guanzhu = (Button) findViewById(R.id.but_village_guanzhu);
        b_guanzhu.setOnClickListener(this);

        myListView = (MyListView) findViewById(R.id.myListView_village);

        //更新UI
        intent = getIntent();

        id = intent.getStringExtra("id");
        catename = intent.getStringExtra("catename");
        guanzhu = intent.getStringExtra("guanzhu");
        count = intent.getStringExtra("count");
        img = intent.getStringExtra("img");

        v_name.setText(catename);
        v_guanzhu.setText("关注  " + guanzhu + "  帖子  " + count);

        if (img.equals("")) {
            iv_img.setImageResource(R.mipmap.yibeitong001);

        } else {
            BitmapUtils bitmapUtils = new BitmapUtils(this);

            bitmapUtils.configDiskCacheEnabled(true);
            bitmapUtils.configMemoryCacheEnabled(false);

            bitmapUtils.display(iv_img, "http://www.ybt9.com/" + img);

        }

        //验证登录方式
        if (!(userInfo.getUserInfo().equals(""))) {
            Register register = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), Register.class);
            uid = register.getMsg().getUid();
            phone = register.getMsg().getPhone();
            if (!(userInfo.getPwd().equals(""))) {
                pwd = userInfo.getPwd();

                if (userInfo.getCode().equals("0")) {
                    getTogethersay(uid, pwd, id);
                } else {
                    System.out.println("我的手机号" + phone);
                    getTogethersay("phone", phone, id);
                }

            }
        } else {
            toast("未登录");
        }


    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.but_village_guanzhu:
                //toast("暂未开通");
                if (isGuanzhu) {
                    if (userInfo.getCode().toString().equals("0")) {
                        postDelGuanzhu(uid, pwd, villageId);
                    } else {
                        postDelGuanzhu("phone", phone, villageId);
                    }

                } else {
                    if (userInfo.getCode().toString().equals("0")) {
                        postAddGuanzhu(uid, pwd, villageId);
                    } else {
                        postAddGuanzhu("phone", phone, villageId);
                    }
                }
                break;

            case R.id.iv_write_postbar:
                //发布帖子

                Intent intent = new Intent(VillagePostBarActivity.this, PublishThemeActivity.class);
                intent.putExtra("cateid", villageId);
                startActivityForResult(intent, 23);
                //startActivity(intent);

                //startActivity(new Intent(VillagePostBarActivity.this, PublishThemeActivity.class));
                break;

            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 23 && resultCode == 24){
            System.out.println("发布成功即使刷新帖子列表");
            vpbAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 添加关注
     *
     * @param uid
     * @param pwd
     * @param villageId
     */
    public void postAddGuanzhu(String uid, String pwd, String villageId) {
        if (userInfo.getCode().toString().equals("0")) {
            PATH = HttpPath.PATH + HttpPath.VILLAGE_ADDCOMMENT_CATE_USER +
                    "uid=" + uid + "&pwd=" + pwd + "&id=" + villageId;
        } else {
            PATH = HttpPath.PATH + HttpPath.VILLAGE_ADDCOMMENT_CATE_USER +
                    "logintype=" + uid + "&loginphone=" + pwd + "&id=" + villageId;

        }

        RequestParams params = new RequestParams(PATH);
        params.setConnectTimeout(1000 * 10);
        x.http().post(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        str_result = result;
                        System.out.println("添加关注" + result);
                        GuanZhuReturn guanZhuReturn = GsonUtil.gsonIntance().gsonToBean(result, GuanZhuReturn.class);
                        if (guanZhuReturn.isError() == false) {
                            toast("添加成功");
                            isGuanzhu = true;
                            b_guanzhu.setText("已关注");
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Error error = GsonUtil.gsonIntance().gsonToBean(str_result, Error.class);
                        toast("" + error.getMsg().toString());

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
     * 取消关注
     *
     * @param uid
     * @param pwd
     * @param villageId
     */
    public void postDelGuanzhu(String uid, String pwd, String villageId) {
        if (userInfo.getCode().toString().equals("0")) {
            PATH = HttpPath.PATH + HttpPath.VILLAGE_DELCOMMENT_CATE_USER +
                    "uid=" + uid + "&pwd=" + pwd + "&id=" + villageId;
        } else {
            PATH = HttpPath.PATH + HttpPath.VILLAGE_DELCOMMENT_CATE_USER +
                    "logintype=" + uid + "&loginphone=" + pwd + "&id=" + villageId;
        }
        RequestParams params = new RequestParams(PATH);
        params.setConnectTimeout(1000 * 10);

        x.http().post(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        str_result = result;
                        System.out.println("取消关注" + result);
                        GuanZhuReturn guanZhuReturn = GsonUtil.gsonIntance().gsonToBean(result, GuanZhuReturn.class);
                        if (guanZhuReturn.isError() == false) {
                            toast("取消成功");
                            isGuanzhu = false;

                            b_guanzhu.setText("未关注");
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Error error = GsonUtil.gsonIntance().gsonToBean(str_result, Error.class);
                        toast("" + error.getMsg().toString());
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
     * 小区信息帖子列表
     */

    public void getTogethersay(String uid, String pwd, String id) {
        String PATH;
        if (userInfo.getCode().toString().equals("0")) {
            PATH = HttpPath.PATH + HttpPath.VILLAGE_TOGETTHERSAY +
                    "uid=" + uid + "&pwd=" + pwd + "&id=" + id;
        } else {
            PATH = HttpPath.PATH + HttpPath.VILLAGE_TOGETTHERSAY +
                    "logintype=" + uid + "&loginphone=" + phone + "&id=" + id;
        }

        System.out.println("" + PATH);

        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("小区信息帖子列表" + result);

                        VillagePostBar villagePostBar = GsonUtil.gsonIntance().gsonToBean(result, VillagePostBar.class);

                        int usergz = villagePostBar.getMsg().getCate().getUsergz();
                        villageId = villagePostBar.getMsg().getCate().getId();
                        if (usergz == 0) {
                            //未关注
                            isGuanzhu = false;
                            b_guanzhu.setText("未关注");
                        } else if (usergz == 1) {
                            //已关注
                            isGuanzhu = true;
                            b_guanzhu.setText("已关注");
                        }

                        if (villagePostBar.getMsg().getTogethersaylist().size() > 0) {
                            togethersaylistBeen = villagePostBar.getMsg().getTogethersaylist();

                            vpbAdapter = new VillagePostBarAdapter(VillagePostBarActivity.this, togethersaylistBeen);
                            myListView.setAdapter(vpbAdapter);
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


    private List<List<Image>> imagesList;

    /**
     * 小区帖子适配器
     */
    public class VillagePostBarAdapter extends BaseAdapter {
        private Context mContext;
        private List<VillagePostBar.MsgBean.TogethersaylistBean> togethersaylistBeenList = new ArrayList<>();

        public VillagePostBarAdapter(Context mContext, List<VillagePostBar.MsgBean.TogethersaylistBean> togethersaylistBeenList) {
            this.mContext = mContext;
            this.togethersaylistBeenList = togethersaylistBeenList;
        }

        @Override
        public int getCount() {
            return togethersaylistBeenList.size();
        }

        @Override
        public Object getItem(int i) {
            return togethersaylistBeenList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolder vh;
            if (view == null) {
                vh = new ViewHolder();
                view = LayoutInflater.from(mContext).inflate(R.layout.item_viilage_post_bar, null);

                vh.title = (TextView) view.findViewById(R.id.tv_item_vpb_title);
                vh.bady = (TextView) view.findViewById(R.id.tv_item_vpb_bady);
                vh.user = (TextView) view.findViewById(R.id.tv_item_vpb_user);
                vh.data = (TextView) view.findViewById(R.id.tv_item_vpb_data);
                vh.tv_like = (TextView) view.findViewById(R.id.tv_item_vpb_like);
                vh.tv_comment = (TextView) view.findViewById(R.id.tv_item_vpb_comment);

                vh.img = (ImageView) view.findViewById(R.id.iv_item_vpb_img);
                vh.sex = (ImageView) view.findViewById(R.id.iv_item_vpb_sex);
                vh.iv_like = (ImageView) view.findViewById(R.id.iv_item_vpb_like);

                /*九宫格显示图片*/
                vh.ivMore = (NineGridlayout) view.findViewById(R.id.nineGridlayout);
                vh.ivOne = (CustomImageView) view.findViewById(R.id.customImageView);

                //点赞 评论
                vh.lin_zan = (LinearLayout) view.findViewById(R.id.lin_post_bar_zan);
                vh.lin_comment = (LinearLayout) view.findViewById(R.id.lin_post_bar_comment);

                vh.rel_title = (RelativeLayout) view.findViewById(R.id.rel_item_vpb_title);
                vh.rel_bady = (RelativeLayout) view.findViewById(R.id.rel_item_vpb_bady);

                view.setTag(vh);

            } else {
                vh = (ViewHolder) view.getTag();
            }

            final String id = togethersaylistBeenList.get(i).getId();
            final String title = togethersaylistBeenList.get(i).getTitle();
            final String usercontent = togethersaylistBeenList.get(i).getUsercontent();
            final String username = togethersaylistBeenList.get(i).getUsername();
            final String creattime = togethersaylistBeenList.get(i).getCreattime();
            final int zongzanshu = togethersaylistBeenList.get(i).getZongzanshu();

            //final String is_bang = togethersaylistBeenList.get(i).getIs_bang();
            final  int is_zan = togethersaylistBeenList.get(i).getIs_zan();

            final int pingjiazongshu = togethersaylistBeenList.get(i).getPingjiazongshu();

            final List<String> wxuserimgarr = togethersaylistBeenList.get(i).getWxuserimgarr();

            vh.title.setText(title);
            vh.bady.setText(usercontent);
            vh.user.setText("" + username);
            vh.data.setText("" + creattime);
            vh.tv_like.setText("" + zongzanshu);
            vh.tv_comment.setText("" + pingjiazongshu);

            /*if(is_bang.equals("0")){
                //没点过赞
                vh.iv_like.setImageResource(R.mipmap.ic_like_gay);

            }else if(is_bang.equals("1")){
                //点过赞
                vh.iv_like.setImageResource(R.mipmap.ic_like_red);
            }else{
                //不知道
            }*/

            if(is_zan == 0){
                //没点过赞
                vh.iv_like.setImageResource(R.mipmap.ic_like_gay);
            }else if(is_zan == 1){
                //点过赞
                vh.iv_like.setImageResource(R.mipmap.ic_like_red);
            }else{
                //不知道
            }


            /*九宫格显示图片*/
            imagesList = new ArrayList<>();

            int size = togethersaylistBeenList.get(i).getWxuserimgarr().size();

            ArrayList<Image> itemList = new ArrayList<>();
            if (!(wxuserimgarr.toString().equals("[]"))) {
                for (int k = 0; k < size; k++) {
                    itemList.add(new Image(togethersaylistBeenList.get(i).getWxuserimgarr().get(k).toString(), 250, 250));
                    imagesList.add(itemList);

                }
                //vh.ivMore.setImagesData(itemList);

            }

            if (itemList.isEmpty() || itemList.isEmpty()) {
                vh.ivMore.setVisibility(View.GONE);
                vh.ivOne.setVisibility(View.GONE);
            } else if (itemList.size() == 0) {
                System.out.println("我是零啊");
            } else if (itemList.size() == 1) {
                vh.ivMore.setVisibility(View.GONE);
                vh.ivOne.setVisibility(View.VISIBLE);

                handlerOneImage(vh, itemList.get(0));
            } else {
                vh.ivMore.setVisibility(View.VISIBLE);
                vh.ivOne.setVisibility(View.GONE);

                vh.ivMore.setImagesData(itemList);
            }

            /*点赞*/
            vh.lin_zan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toast("点赞" + i);


                }
            });

            /*评论（帖子详情）*/
            vh.lin_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("usercontent = "+usercontent+"  zongzanshu = "+zongzanshu+"  pingjiazongshu = "+pingjiazongshu);
                    Intent intent = new Intent(VillagePostBarActivity.this, VillagePBDetailedActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("title", title);
                    intent.putExtra("usercontent", usercontent);
                    //intent.putExtra("img",);
                    intent.putExtra("username", username);
                    intent.putExtra("creattime", creattime);
                    intent.putExtra("zongzanshu", String.valueOf(zongzanshu));
                    intent.putExtra("pingjiazongshu", String.valueOf(pingjiazongshu));
                    startActivity(intent);

                }
            });
            /*点击内容*/
            vh.rel_bady.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("usercontent = "+usercontent+"  zongzanshu = "+zongzanshu+"  pingjiazongshu = "+pingjiazongshu);
                    Intent intent = new Intent(VillagePostBarActivity.this, VillagePBDetailedActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("title", title);
                    intent.putExtra("usercontent", usercontent);
                    //intent.putExtra("img",);
                    intent.putExtra("username", username);
                    intent.putExtra("creattime", creattime);
                    intent.putExtra("zongzanshu", String.valueOf(zongzanshu));
                    intent.putExtra("pingjiazongshu", String.valueOf(pingjiazongshu));
                    startActivity(intent);
                }
            });

            /*点击标题*/
            vh.rel_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("usercontent = "+usercontent+"  zongzanshu = "+zongzanshu+"  pingjiazongshu = "+pingjiazongshu);
                    Intent intent = new Intent(VillagePostBarActivity.this, VillagePBDetailedActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("title", title);
                    intent.putExtra("usercontent", usercontent);
                    //intent.putExtra("img",);
                    intent.putExtra("username", username);
                    intent.putExtra("creattime", creattime);
                    intent.putExtra("zongzanshu", String.valueOf(zongzanshu));
                    intent.putExtra("pingjiazongshu", String.valueOf(pingjiazongshu));
                    startActivity(intent);
                }
            });



            return view;
        }

        private void handlerOneImage(ViewHolder viewHolder, Image image) {
            int totalWidth;
            int imageWidth;
            int imageHeight;
            ScreenTools screentools = ScreenTools.instance(mContext);
            totalWidth = screentools.getScreenWidth() - screentools.dip2px(80);
            imageWidth = screentools.dip2px(image.getWidth());
            imageHeight = screentools.dip2px(image.getHeight());
            if (image.getWidth() <= image.getHeight()) {
                if (imageHeight > totalWidth) {
                    imageHeight = totalWidth;
                    imageWidth = (imageHeight * image.getWidth()) / image.getHeight();
                }
            } else {
                if (imageWidth > totalWidth) {
                    imageWidth = totalWidth;
                    imageHeight = (imageWidth * image.getHeight()) / image.getWidth();
                }
            }
            ViewGroup.LayoutParams layoutparams = viewHolder.ivOne.getLayoutParams();
            layoutparams.height = imageHeight;
            layoutparams.width = imageWidth;
            viewHolder.ivOne.setLayoutParams(layoutparams);
            viewHolder.ivOne.setClickable(true);
            viewHolder.ivOne.setScaleType(android.widget.ImageView.ScaleType.FIT_XY);
            viewHolder.ivOne.setImageUrl(image.getUrl());

        }

        public class ViewHolder {
            /*标题 内容 作者 日期 喜欢数量 评论数量*/
            TextView title, bady, user, data, tv_like, tv_comment;

            /*图片 性别标志 喜欢标志（是否喜欢）*/
            ImageView img, sex, iv_like;

            public NineGridlayout ivMore;
            public CustomImageView ivOne;

            LinearLayout lin_zan, lin_comment;

            RelativeLayout rel_title, rel_bady;

        }
    }

    /**
     * 用户点赞（帖子）
     *
     * @param uid
     * @param pwd
     * @param commentid
     */
    public void postZanJia(String uid, String pwd, String commentid) {
        if (userInfo.getCode().toString().equals("0")) {
            PATH = HttpPath.PATH + HttpPath.VILLAGE_SAVEUSERZANJIA +
                    "uid=" + uid + "&pwd=" + pwd + "&commentid=" + commentid;
        } else {
            PATH = HttpPath.PATH + HttpPath.VILLAGE_SAVEUSERZANJIA +
                    "logintype=" + uid + "&loginphone=" + pwd + "&commentid=" + commentid;
        }

        RequestParams params = new RequestParams(PATH);
        x.http().post(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("点赞" + result);
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
     * 用户取消点赞
     *
     * @param uid
     * @param pwd
     * @param commentid
     */
    public void postZanJian(String uid, String pwd, String commentid) {
        if (userInfo.getCode().toString().equals("0")) {
            PATH = HttpPath.PATH + HttpPath.VILLAGE_SAVEUSERZANJIAN +
                    "uid=" + uid + "&pwd=" + pwd + "&commentid=" + commentid;
        } else {
            PATH = HttpPath.PATH + HttpPath.VILLAGE_SAVEUSERZANJIAN +
                    "logintype=" + uid + "&loginphone=" + pwd + "&commentid=" + commentid;
        }
        RequestParams params = new RequestParams(PATH);
        x.http().post(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("取消点赞" + result);

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