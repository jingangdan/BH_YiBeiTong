package com.bh.yibeitong.ui.search;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseActivity;
import com.bh.yibeitong.bean.search.HotKey;
import com.bh.yibeitong.sqlite.RecordSQLiteOpenHelper;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.utils.MD5Util;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingang on 2016/12/21.
 * 首页搜索界面 热门搜索 历史记录
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener {
    /*销毁当层*/
    private ImageView iv_back;

    private ImageView iv_search;
    private EditText et_search;

    private Intent intent;
    String searchvalue, latitude, longtitude, shopid;

    /*接口地址*/
    private String PATH;

    /*显示热搜词*/
    private GridView gridView;
    private ListView listView;
    HotKeyAdapter hotKeyAdapter;

    /*清空历史记录*/
    private Button but_clear_history;
    private RecordSQLiteOpenHelper helper = new RecordSQLiteOpenHelper(this);
    ;
    private SQLiteDatabase db;
    private BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initData();

        /*获取热搜词*/
        getHotKey();

    }


    /**
     *
     */
    public void initData() {
        gridView = (GridView) findViewById(R.id.gv_search);
        listView = (ListView) findViewById(R.id.lv_search);

        iv_back = (ImageView) findViewById(R.id.iv_search_back);
        iv_back.setOnClickListener(this);

        iv_search = (ImageView) findViewById(R.id.iv_search_more);
        et_search = (EditText) findViewById(R.id.et_search_more);

        iv_search.setOnClickListener(this);

        but_clear_history = (Button) findViewById(R.id.but_clear_history);
        but_clear_history.setOnClickListener(this);

        /*获取页面传值*/
        intent = getIntent();
        searchvalue = intent.getStringExtra("searchvalue");
        latitude = intent.getStringExtra("lat");
        longtitude = intent.getStringExtra("lng");
        shopid = intent.getStringExtra("shopid");

        // 搜索框的文本变化实时监听
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 0) {
                    //tv_tip.setText("搜索历史");
                } else {
                    //tv_tip.setText("搜索结果");
                }
                String tempName = et_search.getText().toString();
                // 根据tempName去模糊查询数据库中有没有数据
                queryData(tempName);

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                String name = textView.getText().toString();
                et_search.setText(name);
                //Toast.makeText(SearchActivity.this, name, Toast.LENGTH_SHORT).show();
                // TODO 获取到item上面的文字，根据该关键字跳转到另一个页面查询，由你自己去实现

                intent = new Intent(SearchActivity.this, SearchListActivity.class);
                intent.putExtra("searchvalue", name);
                intent.putExtra("lat", latitude);
                intent.putExtra("lng", longtitude);
                intent.putExtra("shopid", shopid);

                startActivity(intent);

                SearchActivity.this.finish();
            }
        });

        queryData("");


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search_back:
                this.finish();
                break;

            case R.id.iv_search_more:
                searchvalue = et_search.getText().toString();
                if (!(searchvalue.equals(""))) {

                    boolean hasData = hasData(et_search.getText().toString().trim());
                    if (!hasData) {
                        insertData(et_search.getText().toString().trim());
                        queryData("");
                    }

                    intent = new Intent(SearchActivity.this, SearchListActivity.class);
                    intent.putExtra("searchvalue", searchvalue);
                    intent.putExtra("lat", latitude);
                    intent.putExtra("lng", longtitude);
                    intent.putExtra("shopid", shopid);
                    startActivity(intent);
                    this.finish();

                } else {
                    toast("请输入搜索内容");
                }

                break;

            case R.id.but_clear_history:
                //清空历史记录

                deleteData();
                queryData("");

                break;

            default:
                break;
        }
    }

    /**
     * 获取热搜词
     * 无参
     */
    public void getHotKey() {
        //PATH = HttpPath.PATH + HttpPath.SEARCH_HOT_KEY;

//        PATH = HttpPath.PATH_HEAD+HttpPath.PATH_DATA_MD5+HttpPath.SEARCH_HOT_KEY+
//        "sign="+MD5Util.getMD5String(HttpPath.PATH_DATA_MD5+HttpPath.SEARCH_HOT_KEY+HttpPath.PATH_BAIHAI);
//

        PATH = HttpPath.PATH_HEAD+HttpPath.PATH_DATA+(System.currentTimeMillis()/1000)+"&"+HttpPath.SEARCH_HOT_KEY
                +"sign="+MD5Util.getMD5String(HttpPath.PATH_DATA+(System.currentTimeMillis()/1000)+"&"+HttpPath.SEARCH_HOT_KEY+HttpPath.PATH_BAIHAI);

        System.out.println("获取热搜词"+PATH);

        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("获取热搜词" + result);
                        final HotKey hotKey = GsonUtil.gsonIntance().gsonToBean(result, HotKey.class);


                        hotKeyAdapter = new HotKeyAdapter(SearchActivity.this, hotKey.getMsg());
                        gridView.setAdapter(hotKeyAdapter);

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
     * 插入数据
     */
    private void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();
    }

    /**
     * 模糊查询数据
     */
    private void queryData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);
        // 创建adapter适配器对象
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[]{"name"},
                new int[]{android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // 设置适配器
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**
     * 检查数据库中是否已经有该条记录
     */
    private boolean hasData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        //判断是否有下一个
        return cursor.moveToNext();
    }

    /**
     * 清空数据
     */
    private void deleteData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
    }


    /**
     * 热门搜索词 适配器
     */
    public class HotKeyAdapter extends BaseAdapter {
        private Context mContext;
        private List<HotKey.MsgBean> msgBeanList = new ArrayList<>();

        public HotKeyAdapter(Context mContext, List<HotKey.MsgBean> msgBeanList) {
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
                view = LayoutInflater.from(mContext).inflate(R.layout.item_hotkey, null);

                vh.title = (Button) view.findViewById(R.id.but_item_hotkey);

                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }
            final String name = msgBeanList.get(i).getName();

            vh.title.setText("" + name);

            vh.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    intent = new Intent(SearchActivity.this, SearchListActivity.class);
                    intent.putExtra("searchvalue", name);
                    intent.putExtra("lat", latitude);
                    intent.putExtra("lng", longtitude);
                    intent.putExtra("shopid", shopid);


                    boolean hasData = hasData(name.toString().trim());

                    if (!hasData) {
                        insertData(name.toString().trim());
                        queryData("");
                    }

                    startActivity(intent);

                    SearchActivity.this.finish();

                }
            });

            return view;
        }

        public class ViewHolder {
            Button title;
        }
    }
}