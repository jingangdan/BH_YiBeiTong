package com.bh.yibeitong.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
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

import com.bh.yibeitong.R;
import com.bh.yibeitong.actitvity.PerCenActivity;
import com.bh.yibeitong.base.BaseFragment;
import com.bh.yibeitong.bean.Error;
import com.bh.yibeitong.bean.Register;
import com.bh.yibeitong.bean.UserInfos;
import com.bh.yibeitong.bean.VerCode;
import com.bh.yibeitong.bean.homepage.GetSign;
import com.bh.yibeitong.seller.activity.SellerLoginActivity;
import com.bh.yibeitong.ui.LoginRegisterActivity;
import com.bh.yibeitong.ui.SettingActivity;
import com.bh.yibeitong.ui.address.ManageAddressActivity;
import com.bh.yibeitong.ui.homepage.JoinActivity;
import com.bh.yibeitong.ui.percen.AboutUsActivity;
import com.bh.yibeitong.ui.percen.ExChangeActivity;
import com.bh.yibeitong.ui.percen.YouHuiQuanActivity;
import com.bh.yibeitong.updateversion.UpdateStatus;
import com.bh.yibeitong.updateversion.UpdateVersionUtil;
import com.bh.yibeitong.updateversion.VersionInfo;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.utils.UpdataUtils;
import com.bh.yibeitong.utils.ZXingUtils;
import com.bh.yibeitong.view.RoundImageView;
import com.bh.yibeitong.view.UserInfo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingang on 2016/10/18.
 * 个人中心
 */
public class FMPerCen extends BaseFragment implements View.OnClickListener {
    private View view;

    private TextView tv_username;

    private LinearLayout lin_personal_header;

    /**
     * 设置
     */
    private ImageView iv_percen_setting;

    private Intent intent;

    //收藏
    private LinearLayout lin_collect_goods, lin_collect_seller, lin_collect_shopcart;

    private RoundImageView roundImageView;

    private String login;

    private String username, member;

    /*本地缓存*/
    UserInfo userInfo;
    String jingang;
    String uid, pwd, phone;

    /*余额 优惠券 积分*/
    private TextView yue, youhuiquan, jifen;
    String s_yue, s_youhuiquan, s_jifen;
    int i_youhuiquan;

    /*管理收货地址  我的收藏  兑换礼品  邀请好友  关于我们  商家入驻     检查版本更新*/
    private RelativeLayout mAddress, collect, exchange, invite, aboutUs, updata,
            rel_sign;

    //显示签到状态
    private TextView tv_sign;

    Bitmap bitmap;


    //没有登录登录时布局
    private Button but_login;
    private TextView tv_nologin_title;

    /*接口地址*/
    private String PATH = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        System.out.println("个人中心页 加载");
        userInfo = new UserInfo(getActivity().getApplication());

        jingang = userInfo.getLogin();

        /*判断是否已登录*/
        if (jingang.equals("")) {

            System.out.println("未登录 空 = " + jingang);
            view = inflater.inflate(R.layout.activity_nologin, container, false);

            initDatas();

        } else if (jingang.equals("0")) {
            System.out.println("未登录 0 = " + jingang);
            view = inflater.inflate(R.layout.activity_nologin, container, false);

            initDatas();

        } else if (jingang.equals("1")) {
            System.out.println("已登录 1 = " + jingang);
            view = inflater.inflate(R.layout.fragment_personal_center, container, false);

            initData();

            initVariable();

        }

        return view;
    }

    PopupWindow pop;
    View view_pop;
    LinearLayout ll_popup;
    Button but_user, but_seller, but_cancel;

    /**
     * 没有登录的情况下下载的页面
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

                //
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
                        startActivity(new Intent(getActivity(), SellerLoginActivity.class));

                        pop.dismiss();
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

        tv_nologin_title.setText("个人中心");
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            System.out.println("离开FMPerCen");
        } else {
            System.out.println("刷新FMPerCen");
            //initData();


        }
    }

    /**
     * 组件 初始化
     */
    public void initData() {
        tv_username = (TextView) view.findViewById(R.id.tv_username);

        lin_personal_header = (LinearLayout) view.findViewById(R.id.lin_personal_header);
        lin_personal_header.setOnClickListener(this);

        //设置
        iv_percen_setting = (ImageView) view.findViewById(R.id.iv_percen_setting);
        iv_percen_setting.setOnClickListener(this);

        //收藏
        lin_collect_goods = (LinearLayout) view.findViewById(R.id.lin_collect_goods);
        lin_collect_seller = (LinearLayout) view.findViewById(R.id.lin_collect_seller);
        lin_collect_shopcart = (LinearLayout) view.findViewById(R.id.lin_collect_shopcart);

        lin_collect_goods.setOnClickListener(this);
        lin_collect_seller.setOnClickListener(this);
        lin_collect_shopcart.setOnClickListener(this);

        //圆形头像
        roundImageView = (RoundImageView) view.findViewById(R.id.roundImageView);

        yue = (TextView) view.findViewById(R.id.tv_percen_yue);
        youhuiquan = (TextView) view.findViewById(R.id.tv_percen_yohuiquan);
        jifen = (TextView) view.findViewById(R.id.tv_percen_jifen);

        /**/
        mAddress = (RelativeLayout) view.findViewById(R.id.rel_percen_maddress);
        collect = (RelativeLayout) view.findViewById(R.id.rel_percen_collect);
        exchange = (RelativeLayout) view.findViewById(R.id.rel_percen_exchange);
        invite = (RelativeLayout) view.findViewById(R.id.rel_percen_invite);
        aboutUs = (RelativeLayout) view.findViewById(R.id.rel_percen_about);
        //enter = (RelativeLayout) view.findViewById(R.id.rel_percen_enter);

        mAddress.setOnClickListener(this);
        collect.setOnClickListener(this);
        exchange.setOnClickListener(this);
        invite.setOnClickListener(this);
        aboutUs.setOnClickListener(this);
        //enter.setOnClickListener(this);

        updata = (RelativeLayout) view.findViewById(R.id.rel_percen_updata);
        updata.setOnClickListener(this);

        //签到
        rel_sign = (RelativeLayout) view.findViewById(R.id.rel_percen_sign);
        rel_sign.setOnClickListener(this);

        tv_sign = (TextView) view.findViewById(R.id.tv_percen_sign);

        if (!(userInfo.getUserInfo().equals(""))) {
            Register register = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), Register.class);
            uid = register.getMsg().getUid();
            phone = register.getMsg().getPhone();

        }

        pwd = userInfo.getPwd();

        if (userInfo.getCode().toString().equals("0")) {
            getAppMem(uid, pwd);
            getSignToDay(uid, pwd);
        } else {
            getAppMem("phone", phone);
            getSignToDay("phone", phone);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_personal_header:

                if (jingang.equals("")) {
                    tv_username.setText("点击登录");

                    startActivity(new Intent(getActivity(), LoginRegisterActivity.class));
                } else if (jingang.equals("0")) {
                    tv_username.setText("点击登录");

                    startActivity(new Intent(getActivity(), LoginRegisterActivity.class));
                } else if (jingang.equals("1")) {
                    tv_username.setText(phone);
                    startActivity(new Intent(getActivity(), PerCenActivity.class));

                }

                break;

            case R.id.iv_percen_setting:
                //设置

                intent = new Intent(getActivity(), SettingActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("phone", phone);
                intent.putExtra("member", member);
                startActivity(intent);
                //startActivity(new Intent(getActivity(), SettingActivity.class));

                break;

            case R.id.lin_collect_goods:
                //账户余额
//                intent = new Intent(getActivity(), YuEActivity.class);
//                intent.putExtra("yue", s_yue);
//                startActivity(intent);

                //startActivity(new Intent(getActivity(), YuEActivity.class));
                break;

            case R.id.lin_collect_seller:
                //优惠券
                startActivity(new Intent(getActivity(), YouHuiQuanActivity.class));
                break;

            case R.id.lin_collect_shopcart:
                //积分
                break;

            case R.id.rel_percen_maddress:
                //管理收货地址
                startActivity(new Intent(getActivity(), ManageAddressActivity.class));
                break;

            case R.id.rel_percen_collect:
                //我的收藏
                break;

            case R.id.rel_percen_exchange:
                //兑换礼品
                intent = new Intent(getActivity(), ExChangeActivity.class);
                intent.putExtra("", "");//传值 积分
                startActivity(intent);
                break;

            case R.id.rel_percen_invite:
                //邀请好友
                toast("充值");

                break;

            case R.id.rel_percen_about:
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
                //关于我们
                break;

            /*case R.id.rel_percen_enter:
                //商家入驻
                toast("签到");
                break;*/

            case R.id.rel_percen_sign:
                //签到
                if (!(userInfo.getUserInfo().equals(""))) {
                    Register register = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), Register.class);
                    uid = register.getMsg().getUid();
                    if (!(userInfo.getPwd().equals(""))) {
                        pwd = userInfo.getPwd();

                        if (userInfo.getCode().equals("0")) {
                            //System.out.println("我的验证码"+userInfo.getCode());
                            signToDay(uid, pwd);
                        } else {
                            //System.out.println("我的手机号"+phone);
                            signToDay("phone", phone);
                        }

                    }
                } else {
                    toast("未登录");
                }

                break;

            case R.id.rel_percen_updata:
                //检查版本更新
                //toast("检查版本更新");
                //new checkNewestVersionAsyncTask().execute();

                String verName = UpdataUtils.getVerName(getActivity());
                getLoadVersion(verName);

                break;

            default:
                break;
        }
    }

    /**
     * 获取用户信息
     *
     * @param uid
     * @param pwd
     */
    public void getAppMem(String uid, String pwd) {
        String PATH;
        if (userInfo.getCode().toString().equals("0")) {
            PATH = HttpPath.PATH + HttpPath.USER_GETAPPMEM +
                    "uid=" + uid + "&pwd=" + pwd;
        } else {
            PATH = HttpPath.PATH + HttpPath.USER_GETAPPMEM +
                    "logintype=" + uid + "&loginphone=" + pwd;
        }
        System.out.println("获取用户信息" + PATH);

        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("获取用户信息" + result);

                        UserInfos userInfos = GsonUtil.gsonIntance().gsonToBean(result, UserInfos.class);
                        username = userInfos.getMsg().getUsername();
                        phone = userInfos.getMsg().getPhone();
                        s_yue = userInfos.getMsg().getCost();
                        s_youhuiquan = String.valueOf(userInfos.getMsg().getJuancount());
                        s_jifen = userInfos.getMsg().getScore();
                        //member = userInfos.getMsg().get  //会员账号

                        yue.setText("" + s_yue);
                        youhuiquan.setText("" + s_youhuiquan);
                        jifen.setText("" + s_jifen);

                        bitmap = ZXingUtils.createQRImage(phone.trim(),
                                roundImageView.getWidth() + 100, roundImageView.getHeight() + 100);
                        roundImageView.setImageBitmap(bitmap);

                        tv_username.setText("" + phone);

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
     * 查询今天的签到状态
     *
     * @param uid
     * @param pwd
     */
    public void getSignToDay(String uid, String pwd) {

        if (userInfo.getCode().equals("0")) {
            PATH = HttpPath.PATH + HttpPath.GETSIGN +
                    "uid=" + uid + "&pwd=" + pwd;
        } else {
            PATH = HttpPath.PATH + HttpPath.GETSIGN +
                    "logintype=" + uid + "&loginphone=" + pwd;
        }

        System.out.println("今日签到状态" + PATH);
        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("今日签到状态" + result);
                        GetSign getSign = GsonUtil.gsonIntance().gsonToBean(result, GetSign.class);
                        System.out.println("" + getSign.isError());
                        if (getSign.isError() == true) {
                            tv_sign.setText("已签到");
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
     * 签到
     *
     * @param uid
     * @param pwd
     */
    public void signToDay(String uid, String pwd) {
        String PATH = null;
        if (userInfo.getCode().equals("0")) {
            PATH = HttpPath.PATH + HttpPath.SIGN +
                    "uid=" + uid + "&pwd=" + pwd;
        } else {
            PATH = HttpPath.PATH + HttpPath.SIGN +
                    "logintype=" + uid + "&loginphone=" + pwd;
        }

        System.out.println("签到" + PATH);
        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("签到" + result);
                        Error error = GsonUtil.gsonIntance().gsonToBean(result, Error.class);
                        if (error.isError() == true) {
                        } else {
                            tv_sign.setText("已签到");
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

    @Override
    protected void lazyLoad() {

    }


    /*检查版本更新*/
    long m_newVerCode; //最新版的版本号
    String m_newVerName; //最新版的版本名
    String m_appNameStr; //下载到本地要给这个APP命的名字

    Handler m_mainHandler;
    ProgressDialog m_progressDlg;

    private void initVariable() {
        m_mainHandler = new Handler();
        m_progressDlg = new ProgressDialog(getActivity());
        m_progressDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // 设置ProgressDialog 的进度条是否不明确 false 就是不设置为不明确
        m_progressDlg.setIndeterminate(false);
        m_appNameStr = "ybt.apk";
    }

    class checkNewestVersionAsyncTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            if (postCheckNewestVersionCommand2Server()) {
                int vercode = UpdataUtils.getVerCode(getActivity().getApplicationContext()); // 用到前面第一节写的方法
                if (m_newVerCode > vercode) {
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {//如果有最新版本
                doNewVersionUpdate(); // 更新新版本
            } else {
                notNewVersionDlgShow(); // 提示当前为最新版本
            }
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    /**
     * 从服务器获取当前最新版本号，如果成功返回TURE，如果失败，返回FALSE
     *
     * @return
     */
    private Boolean postCheckNewestVersionCommand2Server() {
        StringBuilder builder = new StringBuilder();
        JSONArray jsonArray = null;
        try {
            // 构造POST方法的{name:value} 参数对
            List<NameValuePair> vps = new ArrayList<NameValuePair>();
            // 将参数传入post方法中
            vps.add(new BasicNameValuePair("action", "checkNewestVersion"));
            builder = UpdataUtils.post_to_server(vps);
            Log.e("msg", builder.toString());
            jsonArray = new JSONArray(builder.toString());
            if (jsonArray.length() > 0) {
                if (jsonArray.getJSONObject(0).getInt("id") == 1) {
                    m_newVerName = jsonArray.getJSONObject(0).getString("verName");
                    m_newVerCode = jsonArray.getJSONObject(0).getLong("verCode");

                    return true;
                }
            }

            return false;
        } catch (Exception e) {
            //Log.e("msg",e.getMessage());
            m_newVerName = "";
            m_newVerCode = -1;
            return false;
        }
    }

    /**
     * 提示更新新版本
     */
    private void doNewVersionUpdate() {
        int verCode = UpdataUtils.getVerCode(getActivity().getApplicationContext());
        String verName = UpdataUtils.getVerName(getActivity().getApplicationContext());

        String str = "当前版本：" + verName + " Code:" + verCode + " ,发现新版本：" + m_newVerName +
                " Code:" + m_newVerCode + " ,是否更新？";
        Dialog dialog = new AlertDialog.Builder(getActivity()).setTitle("软件更新").setMessage(str)
                // 设置内容
                .setPositiveButton("更新",// 设置确定按钮
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                m_progressDlg.setTitle("正在下载");
                                m_progressDlg.setMessage("请稍候...");
                                downFile("http://www.ybt9.com/app/ybt.apk");  //开始下载
                            }
                        })
                .setNegativeButton("暂不更新",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                // 点击"取消"按钮之后退出程序
                                //finish();
                                dialog.dismiss();
                            }
                        }).create();// 创建
        // 显示对话框
        dialog.show();
    }

    /**
     * 提示当前为最新版本
     */
    private void notNewVersionDlgShow() {
        int verCode = UpdataUtils.getVerCode(getActivity());
        String verName = UpdataUtils.getVerName(getActivity());
        String str = "当前版本:" + verName + " Code:" + verCode + ",\n已是最新版,无需更新!";
        Dialog dialog = new AlertDialog.Builder(getActivity()).setTitle("软件更新")
                .setMessage(str)// 设置内容
                .setPositiveButton("确定",// 设置确定按钮
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                                //finish();
                            }
                        }).create();// 创建
        // 显示对话框
        dialog.show();
    }

    /**
     * 下载更新
     *
     * @param url
     */
    private void downFile(final String url) {
        m_progressDlg.show();
        new Thread() {
            public void run() {
                HttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(url);
                HttpResponse response;
                try {
                    response = client.execute(get);
                    HttpEntity entity = response.getEntity();
                    long length = entity.getContentLength();

                    m_progressDlg.setMax((int) length);//设置进度条的最大值

                    InputStream is = entity.getContent();
                    FileOutputStream fileOutputStream = null;
                    if (is != null) {
                        File file = new File(
                                Environment.getExternalStorageDirectory(),
                                m_appNameStr);
                        fileOutputStream = new FileOutputStream(file);
                        byte[] buf = new byte[1024];
                        int ch = -1;
                        int count = 0;
                        while ((ch = is.read(buf)) != -1) {
                            fileOutputStream.write(buf, 0, ch);
                            count += ch;
                            if (length > 0) {
                                m_progressDlg.setProgress(count);
                            }
                        }
                    }
                    fileOutputStream.flush();
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    down();  //告诉HANDER已经下载完成了，可以安装了
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 告诉HANDER已经下载完成了，可以安装了
     */
    private void down() {
        m_mainHandler.post(new Runnable() {
            public void run() {
                m_progressDlg.cancel();
                update();
            }
        });
    }

    /**
     * 安装程序
     */
    void update() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(Environment
                        .getExternalStorageDirectory(), m_appNameStr)),
                "application/vnd.android.package-archive");
        startActivity(intent);
    }


    /**
     * 检查版本更新
     *
     * @param v 当前版本号
     */
    public void getLoadVersion(final String v) {
        String PATH = HttpPath.path + HttpPath.ANDROID_CHECKV + "" +
                "v=" + v;

        RequestParams params = new RequestParams(PATH);

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("检查版本号" + result);

                VerCode verCode = GsonUtil.gsonIntance().gsonToBean(result, VerCode.class);

                boolean error = verCode.isError();

                if (error == false) {
                    System.out.println("最新" + verCode.getMsg());
                    toast("已是最新版本！");
                    //notNewVersionDlgShow(verCode.getMsg().getUrl(),verCode.getMsg().getMsg());
                } else if (error == true) {
                    System.out.println("旧的" + verCode.getMsg());
                    //doNewVersionUpdates(verCode.getMsg().getUrl(), verCode.getMsg().getMsg());
                    //downFile("http://www.ybt9.com/app/ybt.apk");
                    //doNewVersionUpdate();

                    if (!canDownloadState()) {
                        Toast.makeText(getActivity(), "下载服务不用,请您启用", Toast.LENGTH_SHORT).show();
                        showDownloadSetting();
                        return;
                    }
                    /*String url;
                    if (TextUtils.isEmpty(editText.getText().toString())) {
                        url = "http://www.ybt9.com/app/ybt.apk";
                    } else {
                        url = editText.getText().toString();
                    }*/
                    /*ApkUpdateUtils.download(getActivity(), "http://www.ybt9.com/app/ybt.apk",
                            getResources().getString(R.string.app_name));*/


                    UpdateVersionUtil.localCheckedVersion(getActivity(),
                            new UpdateVersionUtil.UpdateListener() {
                                @Override
                                public void onUpdateReturned(int updateStatus, VersionInfo versionInfo) {
                                    //判断回调过来的版本检测状态
                                    switch (updateStatus) {
                                        case UpdateStatus.YES:
                                            //弹出更新提示
                                            UpdateVersionUtil.showDialog(getActivity(), versionInfo, v);
                                            break;
                                        case UpdateStatus.NO:
                                            //没有新版本
                                            toast("已经是最新版本了!");
                                            break;
                                        case UpdateStatus.NOWIFI:
                                            //当前是非wifi网络
                                            toast("只有在wifi下更新！");
                                            break;
                                        case UpdateStatus.ERROR:
                                            //检测失败
                                            toast("检测失败，请稍后重试！");
                                            break;
                                        case UpdateStatus.TIMEOUT:
                                            //链接超时
                                            toast("链接超时，请检查网络设置!");
                                            break;
                                    }
                                }
                            }, v);

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

    private void showDownloadSetting() {
        String packageName = "";
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + packageName));
        if (intentAvailable(intent)) {
            startActivity(intent);
        }
    }

    private boolean intentAvailable(Intent intent) {
        PackageManager packageManager = getActivity().getPackageManager();
        List list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }


    private boolean canDownloadState() {
        try {
            int state = getActivity().getPackageManager().getApplicationEnabledSetting("com.android.providers.downloads");

            if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 解决安卓6.0以上版本不能读取外部存储权限的问题
     *
     * @param activity
     * @return
     */
    public static boolean isGrantExternalRW(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);

            return false;
        }
        return true;
    }


    /**
     * 提示更新新版本
     */
    private void doNewVersionUpdates(final String str_url, String str_msg) {

        Dialog dialog = new AlertDialog.
                Builder(getActivity()).
                setTitle("软件更新").
                setMessage(str_msg)
                // 设置内容
                .setPositiveButton("更新",
                        // 设置确定按钮
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                downFiles(str_url);
                            }
                        })
                .setNegativeButton("暂不更新",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                // 点击"取消"按钮之后退出程序
                                //finish();
                                dialog.dismiss();
                            }
                        }).create();// 创建
        // 显示对话框
        dialog.show();
    }

    /**
     * 下载更新
     *
     * @param url
     */
    private void downFiles(final String url) {
        System.out.println("url=" + url);
        m_progressDlg.setMessage("正在下载，请稍候。。。");
        m_progressDlg.show();

        new Thread() {
            public void run() {
                HttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(url);
                HttpResponse response;
                try {

                    response = client.execute(get);
                    HttpEntity entity = response.getEntity();
                    long length = entity.getContentLength();

                    m_progressDlg.setMax((int) length);//设置进度条的最大值

                    InputStream is = entity.getContent();
                    FileOutputStream fileOutputStream = null;
                    if (is != null) {
                        File file = new File(
                                Environment.getExternalStorageDirectory(),
                                "ybt");
                        fileOutputStream = new FileOutputStream(file);
                        byte[] buf = new byte[1024];
                        int ch = -1;
                        int count = 0;
                        while ((ch = is.read(buf)) != -1) {
                            fileOutputStream.write(buf, 0, ch);
                            count += ch;
                            if (length > 0) {
                                m_progressDlg.setProgress(count);
                            }
                        }
                    }
                    fileOutputStream.flush();
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    downs();  //告诉HANDER已经下载完成了，可以安装了
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println(e.toString());
                }
            }
        }.start();
    }

    /**
     * 告诉HANDER已经下载完成了，可以安装了
     */
    private void downs() {
        m_mainHandler.post(new Runnable() {
            public void run() {
                m_progressDlg.cancel();
                updates();
            }
        });
    }

    /**
     * 安装程序
     */
    void updates() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(Environment
                        .getExternalStorageDirectory(), "ybt")),
                "application/vnd.android.package-archive");
        startActivity(intent);
    }


}