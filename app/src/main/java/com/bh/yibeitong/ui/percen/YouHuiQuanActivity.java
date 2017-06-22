package com.bh.yibeitong.ui.percen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.Register;
import com.bh.yibeitong.bean.YHQuan;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.UserInfo;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingang on 2016/12/8.
 * 优惠券列表
 */

public class YouHuiQuanActivity extends BaseTextActivity {
    /*接口地址*/
    String PATH;

    /*显示优惠券列表*/
    private TextView yhq_null;
    private ListView listView;
    private YouHuiQuanAdapter yhqAdapter;
    private List<YHQuan.MsgBean> msgBeen = new ArrayList<>();

    private UserInfo userInfo;
    private String uid, pwd, phone;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_youhuiquan);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("优惠券列表");
        setTitleBack(true, 0);

        initData();


    }

    public void initData() {
        userInfo = new UserInfo(getApplication());

        yhq_null = (TextView) findViewById(R.id.tv_yhq_muyou);
        listView = (ListView) findViewById(R.id.lv_youhuiquan);

        //不用担心userInfo.getUserInfo为空
        Register register = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), Register.class);

        uid = register.getMsg().getUid();

        pwd = userInfo.getPwd();

        phone = register.getMsg().getPhone();


        //判断登录方式
        if (userInfo.getCode().toString().equals("0")) {
            getYouHuiQuan(uid, pwd);
        } else {
            getYouHuiQuan("phone", phone);
        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    /**
     * 获取优惠券列表
     * 用户登录验证
     */
    public void getYouHuiQuan(String uid, String pwd) {
        if (userInfo.getCode().toString().equals("0")) {
            PATH = HttpPath.PATH + HttpPath.JUAN_LIST +
                    "uid=" + uid + "&pwd=" + pwd;
        } else {
            PATH = HttpPath.PATH + HttpPath.JUAN_LIST +
                    "logintype=" + uid + "&loginphone" + pwd;
        }

        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("优惠券" + result);
                        YHQuan yhQuan = GsonUtil.gsonIntance().gsonToBean(result, YHQuan.class);

                        msgBeen = yhQuan.getMsg();
                        if (msgBeen.size() == 0) {
                            yhq_null.setVisibility(View.VISIBLE);
                        } else if (msgBeen.size() > 0) {
                            yhqAdapter = new YouHuiQuanAdapter(YouHuiQuanActivity.this, msgBeen);
                            listView.setAdapter(yhqAdapter);
                        } else {

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
     * 优惠券适配器
     */
    public class YouHuiQuanAdapter extends BaseAdapter {
        private Context mContext;
        private List<YHQuan.MsgBean> msgBeanList = new ArrayList<>();

        public YouHuiQuanAdapter(Context mContext, List<YHQuan.MsgBean> msgBeanList) {
            this.mContext = mContext;
            this.msgBeanList = msgBeanList;
        }

        @Override
        public int getCount() {
            return msgBeanList.size();
        }

        @Override
        public Object getItem(int i) {
            return msgBeanList.get(i);
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
                view = LayoutInflater.from(mContext).inflate(R.layout.item_youhuiquan, null);

                vh.cost = (TextView) view.findViewById(R.id.tv_item_yhq_cost);
                vh.limitcost = (TextView) view.findViewById(R.id.tv_item_yhq_limitcost);
                vh.title = (TextView) view.findViewById(R.id.tv_item_yhq_title);
                vh.data = (TextView) view.findViewById(R.id.tv_item_yhq_data);
                vh.status = (Button) view.findViewById(R.id.but_item_yhq_status);

                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }
            //组件赋值

            String cost = msgBeanList.get(i).getCost();
            String limitcost = msgBeanList.get(i).getLimitcost();
            String title = msgBeanList.get(i).getName();
            String creattime = msgBeanList.get(i).getCreattime();
            String endtime = msgBeanList.get(i).getEndtime();
            String status = msgBeanList.get(i).getStatus();

            vh.cost.setText("￥" + cost);
            vh.limitcost.setText("满" + limitcost + "元可用");
            vh.title.setText("" + title);
            vh.data.setText(creattime + " - " + endtime);

            if (status.equals("0")) {
                vh.status.setText("未使用");

            } else if (status.equals("1")) {
                vh.status.setText("已绑定");

            } else if (status.equals("2")) {
                vh.status.setText("已使用");

            } else if (status.equals("3")) {
                vh.status.setText("已过期");

            }

            return view;
        }

        public class ViewHolder {
            TextView cost, limitcost, title, data;
            Button status;

        }
    }
}
