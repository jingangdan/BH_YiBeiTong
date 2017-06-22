package com.bh.yibeitong.ui.village;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.adapter.VillageAdapter;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.Error;
import com.bh.yibeitong.bean.Register;
import com.bh.yibeitong.bean.village.Village;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.utils.MD5Util;
import com.bh.yibeitong.view.UserInfo;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingang on 2016/12/12.
 * 小区信息
 */
public class VillageActivity extends BaseTextActivity {
    /*本地缓存*/
    private UserInfo userInfo;
    private String jingang;

    /*获取数据需要的参数*/
    private String uid, pwd, phone;

    /*已关注列表*/
    private GridView gv_guanzhu;
    private VillageAdapter vAdapter;
    private List<Village.MsgBean.ListBean> listBeen;

    /*未关注列表*/
    private GridView gv_vList;
    private VillageSlistAdapter villageSlistAdapter;
    private List<Village.MsgBean.SlistBean> slistBeen;

    /*接口获取数据*/
    private String str_resilt;
    String PATH;

    /*小区搜索*/
    private ImageView iv_sreach;
    private EditText et_sreach;
    String str_sreach;

    private Intent intent;
    private String mkid;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_village);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("小区信息");
        setTitleBack(true, 0);

        initData();

        isLogin();

    }

    /**
     * 组件初始化
     */
    public void initData() {
        userInfo = new UserInfo(getApplication());
        jingang = userInfo.getLogin();

        gv_guanzhu = (GridView) findViewById(R.id.gv_village_guanzhu);
        gv_vList = (GridView) findViewById(R.id.gv_village_list);

        et_sreach = (EditText) findViewById(R.id.et_village_sreach);
        iv_sreach = (ImageView) findViewById(R.id.iv_village_sreach);
        iv_sreach.setOnClickListener(this);

        intent = getIntent();
        mkid = intent.getStringExtra("mkid");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_village_sreach:
                //toast("搜索");
                str_sreach = et_sreach.getText().toString();
                System.out.println("et_sreach = " + str_sreach);
                if (!("".equals(str_sreach))) {
                    try {
                        postVillageSreach(URLEncoder.encode(str_sreach, "UTF-8"), "2");

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    toast("搜索关键字为空");
                }

                break;

            default:
                break;

        }
    }

    /**
     * 判断用户收否登录并判断登录方式
     */
    public void isLogin() {
        //验证登录方式
        if (!(userInfo.getUserInfo().equals(""))) {
            Register register = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), Register.class);
            uid = register.getMsg().getUid();
            phone = register.getMsg().getPhone();
            if (!(userInfo.getPwd().equals(""))) {
                pwd = userInfo.getPwd();
            }
        }

        if (userInfo.getCode().equals("0")) {
            getVillage(uid, pwd, mkid);
        } else {
            System.out.println("我的手机号" + phone);
            getVillage("phone", phone, mkid);
        }

    }

    /**
     * 获取已关注和未关注列表
     *
     * @param uid
     * @param pwd
     */
    public void getVillage(String uid, String pwd, String id) {

        if (userInfo.getCode().toString().equals("0")) {
            PATH = HttpPath.PATH + HttpPath.VILLAGE_LUNTAN +
                    "uid=" + uid + "&pwd=" + pwd + "&mkid=" + id;
        } else {
            PATH = HttpPath.PATH + HttpPath.VILLAGE_LUNTAN +
                    "logintype=" + uid + "&loginphone=" + pwd + "&mkid=" + id;
        }

        System.out.println("" + PATH);
        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        str_resilt = result;
                        System.out.println("获取小区列表" + result);
                        String results = MD5Util.getUnicode(result);

                        final Village village = GsonUtil.gsonIntance().gsonToBean(results, Village.class);

                        if (village.getMsg().getList() == null) {
                            System.out.println("list = null");
                        } else {
                            System.out.println("list != null");
                            listBeen = village.getMsg().getList();
                            vAdapter = new VillageAdapter(VillageActivity.this, village.getMsg().getList());
                            gv_guanzhu.setAdapter(vAdapter);
                        }

                        if (village.getMsg().getSlist() == null) {
                            System.out.println("slist = null");
                        } else {
                            slistBeen = village.getMsg().getSlist();

                            villageSlistAdapter = new VillageSlistAdapter(VillageActivity.this, village.getMsg().getSlist());
                            gv_vList.setAdapter(villageSlistAdapter);
                        }

                        gv_guanzhu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent = new Intent(VillageActivity.this, VillagePostBarActivity.class);
                                intent.putExtra("id", listBeen.get(i).getId());
                                intent.putExtra("catename", listBeen.get(i).getCatename());
                                intent.putExtra("parent_id", listBeen.get(i).getParent_id());
                                intent.putExtra("cate_order", listBeen.get(i).getCate_order());
                                intent.putExtra("img", listBeen.get(i).getImg());
                                intent.putExtra("guanzhu", listBeen.get(i).getGuanzhu());

                                intent.putExtra("village", new Gson().toJson(village.getMsg().getList()));

                                startActivity(intent);
                            }
                        });

                        gv_vList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent = new Intent(VillageActivity.this, VillagePostBarActivity.class);
                                intent.putExtra("id", slistBeen.get(i).getId());
                                intent.putExtra("catename", slistBeen.get(i).getCatename());
                                intent.putExtra("parent_id", slistBeen.get(i).getParent_id());
                                intent.putExtra("cate_order", slistBeen.get(i).getCate_order());
                                intent.putExtra("img", slistBeen.get(i).getImg());
                                intent.putExtra("guanzhu", slistBeen.get(i).getGuanzhu());
                                intent.putExtra("count", String.valueOf(slistBeen.get(i).getCount()));

                                intent.putExtra("village", new Gson().toJson(village.getMsg().getSlist()));

                                startActivity(intent);
                            }
                        });

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Error error = GsonUtil.gsonIntance().gsonToBean(str_resilt, Error.class);
                        toast(error.getMsg().toString());

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
     * 小区搜索
     *
     * @param keyword 搜索关键字
     * @param mkid    模块id
     */
    public void postVillageSreach(String keyword, String mkid) {
        PATH = HttpPath.PATH + HttpPath.VILLAGE_SREACHLUNTAN +
                "keyword=" + keyword + "&mkid=" + mkid;

        System.out.println(PATH);
        RequestParams params = new RequestParams(PATH);
        x.http().post(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        str_resilt = result;
                        System.out.println("搜索小区" + result);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Error error = GsonUtil.gsonIntance().gsonToBean(str_resilt, Error.class);
                        toast("cuowu"+error.getMsg().toString());
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
     * 未关注列表 适配器
     */
    public class VillageSlistAdapter extends BaseAdapter {
        private Context mContext;
        private List<Village.MsgBean.SlistBean> slistBeanList = new ArrayList<>();

        public VillageSlistAdapter(Context mContext, List<Village.MsgBean.SlistBean> slistBeanList) {
            this.mContext = mContext;
            this.slistBeanList = slistBeanList;
        }

        @Override
        public int getCount() {
            return slistBeanList.size();
        }

        @Override
        public Object getItem(int i) {
            return slistBeanList.get(i);
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
                view = LayoutInflater.from(mContext).inflate(R.layout.item_village, null);

                vh.v_name = (TextView) view.findViewById(R.id.tv_item_village_name);

                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }

            String name = slistBeanList.get(i).getCatename();

            vh.v_name.setText(name);


            return view;
        }

        public class ViewHolder {
            TextView v_name;
        }
    }
}