package com.bh.yibeitong.ui.percen;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.adapter.GiftAdapter;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.Gift;
import com.bh.yibeitong.refresh.MyGridView;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.lidroid.xutils.BitmapUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingang on 2016/12/7.
 * 兑换礼品
 */
public class ExChangeActivity extends BaseTextActivity {

    private TextView myJifen, notes;

    /*兑换礼品列表UI*/
    private GiftAdapter giftAdapter;
    private MyGridView myGridView;

    /*接收页面传值*/
    private Intent intent;
    private String s_jifen;

    private String gift_jifen = "";
    private int i_jifen;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_exchange);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("积分兑换");
        setTitleBack(true, 0);

        initData();

        getGiftList();
    }

    /**
     * 组件 初始化
     */
    public void initData() {
        intent = getIntent();
        s_jifen = intent.getStringExtra("jifen");

        myJifen = (TextView) findViewById(R.id.tv_my_jifen);
        notes = (TextView) findViewById(R.id.tv_exchange_notes);

        notes.setOnClickListener(this);

        myGridView = (MyGridView) findViewById(R.id.mgv_exchange);

        myJifen.setText(""+s_jifen);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_exchange_notes:
                //兑换记录
                Intent intent = new Intent(ExChangeActivity.this, ExGiftLogActivity.class);

                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * 获取礼品列表  无参数
     */
    public void getGiftList() {
        String PATH = HttpPath.PATH + HttpPath.GIFT_LIST;

        RequestParams params = new RequestParams(PATH);

        System.out.println("礼品列表" + PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("礼品列表" + result);

                        final Gift gift = GsonUtil.gsonIntance().gsonToBean(result, Gift.class);

                        giftAdapter = new GiftAdapter(ExChangeActivity.this, gift.getMsg());

                        myGridView.setAdapter(giftAdapter);
                        /**/
                        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String id = gift.getMsg().get(i).getId();
                                intent = new Intent(ExChangeActivity.this, GiftInfoActivity.class);
                                intent.putExtra("giftid", id);
                                intent.putExtra("jifen", s_jifen);
                                startActivity(intent);
                                //toast(""+i);
                            }
                        });


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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==32) {
            if(resultCode==33) {
                String paytype=data.getStringExtra("jifen");
                String giftscore = data.getStringExtra("giftscore");

                //textView.setText(result);
                if(paytype.equals("1")){
                    //当前积分 - 礼品积分  //此处刷新页面
                    i_jifen =  (int) Double.parseDouble(s_jifen) - (int) Double.parseDouble(giftscore);
                    //System.out.println("111111111111111111111"+result);
                    myJifen.setText(""+i_jifen);
                }

            }
        }
    }

    /*礼品列表适配器*/
    public class GiftAdapter extends BaseAdapter {

        private List<Gift.MsgBean> msgBeen = new ArrayList<>();
        private Context mContext;

        public GiftAdapter(Context mContext, List<Gift.MsgBean> msgBeen){
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
            ViewHolder vh;
            if(convertView == null){
                vh = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gift_list, null);

                vh.img = (ImageView) convertView.findViewById(R.id.iv_item_gift);
                vh.title = (TextView) convertView.findViewById(R.id.tv_item_gift_name);
                vh.jifen = (TextView) convertView.findViewById(R.id.tv_gift_jifen);
                vh.exchange = (TextView) convertView.findViewById(R.id.tv_gift_exchange);

                convertView.setTag(vh);
            }else{
                vh = (ViewHolder) convertView.getTag();
            }

            String imgPath = msgBeen.get(position).getImg();

            if (imgPath.equals("")) {
                vh.img.setImageResource(R.mipmap.yibeitong001);

            } else {
                BitmapUtils bitmapUtils = new BitmapUtils(mContext);

                bitmapUtils.configDiskCacheEnabled(true);
                bitmapUtils.configMemoryCacheEnabled(false);

                bitmapUtils.display(vh.img, imgPath);

            }


            String title = msgBeen.get(position).getTitle();
            String score = msgBeen.get(position).getScore();
            final String id = msgBeen.get(position).getId();

            vh.title.setText(""+title);
            vh.jifen.setText(""+score+"积分");

        /*兑换*/
            vh.exchange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ExChangeAddressActivity.class);
                    intent.putExtra("giftid",id);
                    intent.putExtra("giftscore", msgBeen.get(position).getScore());
                    startActivityForResult(intent, 32);
                    //mContext.startActivity(intent);
                }
            });

            return convertView;
        }

        public class ViewHolder{
            ImageView img;
            TextView title, jifen, exchange;
        }
    }

}
