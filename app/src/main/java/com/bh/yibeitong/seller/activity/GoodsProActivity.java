package com.bh.yibeitong.seller.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bh.yibeitong.BuildConfig;
import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.seller.bean.Goods;
import com.bh.yibeitong.seller.bean.Manage;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.zxing.ZXingCaptureActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jingang on 2017/5/22.
 * 货物采购
 */

public class GoodsProActivity extends BaseTextActivity {

    private Button button;//扫一扫 获取条形码

    private EditText et_code;//条形码
    private Button but_name; //获取商品名称
    private Button but_ok; //生成txt

    String str_code, str_num;

    //private TextView tv_sub, tv_add;//数量添加、减少
    /*数量 添加 减少 显示数量*/
    private ImageView iv_add, iv_sub;
    private TextView tv_num;

    private int i = 0;

    String string_code, string_num, string_name;//生成本地txt 保存

    /*打开文件管理*/
    private Button but_file;

    private ListView listView;
    private Adapter adapter;
    private List<Manage> manages = new ArrayList<>();

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_s_goodspro);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleBack(true, 0);
        setTitleName("货物采购");

        x.Ext.init(getApplication());
        x.Ext.setDebug(BuildConfig.DEBUG);

        initData();
    }

    /*组件初始化*/
    public void initData() {
        et_code = (EditText) findViewById(R.id.et_tiao);
        but_ok = (Button) findViewById(R.id.but_goodspro_ok);

        but_name = (Button) findViewById(R.id.but_goodspro_name);
        but_file = (Button) findViewById(R.id.but_goodspro_file);
        button = (Button) findViewById(R.id.but_goodspro_add);

        tv_num = (TextView) findViewById(R.id.tv_goodspro_num);
        iv_add = (ImageView) findViewById(R.id.iv_goodspro_add);
        iv_sub = (ImageView) findViewById(R.id.iv_goodspro_sub);

        iv_add.setOnClickListener(this);
        iv_sub.setOnClickListener(this);


        but_name.setOnClickListener(this);
        but_file.setOnClickListener(this);
        button.setOnClickListener(this);

        listView = (ListView) findViewById(R.id.listView);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {

            case R.id.but_goodspro_add:
                //扫一扫添加
                if (ContextCompat.checkSelfPermission(GoodsProActivity.this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            GoodsProActivity.this, new String[]{
                                    Manifest.permission.CAMERA
                            }, 9
                    );

                } else {

                    Intent intent = new Intent(GoodsProActivity.this, ZXingCaptureActivity.class);

                    i = 0;

                    intent.putExtra("coder", "1");

                    startActivityForResult(intent, 25);


                }
                break;

            case R.id.but_goodspro_file:
                //打开文件夹
                Intent intent = new Intent();
                intent.setAction("android.intent.action.MAIN");//这个地方换了很多都没达到效果
                intent.addCategory("android.intent.category.DEFAULT");
                startActivity(intent);
                break;

            case R.id.iv_goodspro_add:
                //添加数量
                str_num = tv_num.getText().toString();
                int num = Integer.parseInt(str_num);
                num++;
                tv_num.setText("" + num);
                break;

            case R.id.iv_goodspro_sub:
                //减少数量
                str_num = tv_num.getText().toString();
                int num_sub = Integer.parseInt(str_num);

                if (num_sub > 0) {
                    num_sub--;
                    tv_num.setText("" + num_sub);

                } else {
                    Toast.makeText(GoodsProActivity.this, "数量不可小于零", Toast.LENGTH_SHORT).show();
                    but_ok.setVisibility(View.INVISIBLE);
                }
                break;

            case R.id.but_goodspro_name:
                //获取商品名称并添加
                str_code = et_code.getText().toString();
                str_num = tv_num.getText().toString();

                postName(str_code);
                break;

            case R.id.but_goodspro_ok:
                //生成txt文件
                postData();
                break;

            default:
                break;
        }
    }


    /**
     * 获取商品名称
     *
     * @param code
     */
    public void postName(String code) {
        String PATH = "https://www.ybt9.com/index.php?ctrl=app&action=getsxproname&datatype=json&item_no="
                + code;
        RequestParams params = new RequestParams(PATH);
        x.http().post(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("" + result);
                        Goods goods = GsonUtil.gsonIntance().gsonToBean(result, Goods.class);

                        if (goods.isError() == false) {
                            string_name = goods.getMsg().toString();
                            Toast.makeText(GoodsProActivity.this, "商品名称" + goods.getMsg(), Toast.LENGTH_SHORT).show();

                            string_code = et_code.getText().toString();
                            string_num = tv_num.getText().toString();

                            int size = manages.size();
                            boolean isExit = false;


                            if (size == 0) {

                                System.out.println("size = " + size);
                                Manage manage = new Manage();
                                manage.setName(string_name);
                                manage.setCode(string_code);
                                manage.setNum(string_num);

                                manages.add(manage);
                            } else {
                                System.out.println("size = " + size);

                                for (int i = 0; i < size; i++) {
                                    if (string_code.equals(manages.get(i).getCode())) {
                                        System.out.println("有重复");

                                        int i_num = Integer.parseInt(manages.get(i).getNum()) + Integer.parseInt(tv_num.getText().toString());

                                        manages.get(i).setNum("" + i_num);

                                        adapter.notifyDataSetChanged();

                                        isExit = true;
                                        break;


                                    } else {
                                        System.out.println("无重复");

                                        continue;

                                    }
                                }
                                if (!isExit) {
                                    Manage manage = new Manage();
                                    manage.setName(string_name);
                                    manage.setCode(string_code);
                                    manage.setNum(string_num);

                                    manages.add(manage);
                                }

                            }

                            adapter = new Adapter(GoodsProActivity.this, manages);
                            listView.setAdapter(adapter);

                            but_ok.setVisibility(View.VISIBLE);

                            et_code.setText("");
                            tv_num.setText("");

                            Intent intent = new Intent(GoodsProActivity.this, ZXingCaptureActivity.class);

                            i = 0;

                            startActivityForResult(intent, 1);


                        } else {
                            Toast.makeText(GoodsProActivity.this, "商品不存在", Toast.LENGTH_SHORT).show();
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
     * 建立文件夹 生成txt文件
     */
    private void postData() {
        System.out.println("走你");
        String filePath = "/sdcard/Test/";


        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String str = sdf.format(date);

        String fileName = "盘点" + str + ".txt";

        int size = manages.size();
        for (int i = 0; i < size; i++) {
            string_name = manages.get(i).getName();
            string_code = manages.get(i).getCode();
            string_num = manages.get(i).getNum();

            writeTxtToFile(string_name + "," + string_code + "," + string_num, filePath, fileName);
        }


    }

    // 将字符串写入到文本文件中
    public void writeTxtToFile(String strcontent, String filePath, String fileName) {
        //生成文件夹之后，再生成文件，不然会出错
        makeFilePath(filePath, fileName);

        String strFilePath = filePath + fileName;
        // 每次写入时，都换行写
        String strContent = strcontent + "\r\n";
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                Log.d("TestFile", "Create the file:" + strFilePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            Log.e("TestFile", "Errors on write File:" + e);
        }
    }

    // 生成文件
    public File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(GoodsProActivity.this, "生成文件成功！", Toast.LENGTH_SHORT).show();
        return file;
    }

    // 生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("error:", e + "");
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 25 && resultCode == 26) {

            String str = data.getStringExtra("name");

            et_code.setText(str);
            i++;
            tv_num.setText("" + i);

        }
    }

    /**
     * 适配器
     */
    public class Adapter extends BaseAdapter {
        private Context mContext;
        private List<Manage> manageList = new ArrayList<>();

        public Adapter(Context mContext, List<Manage> manageList) {
            this.mContext = mContext;
            this.manageList = manageList;
        }

        @Override
        public int getCount() {
            return manageList.size();
        }

        @Override
        public Object getItem(int position) {
            return manageList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh;
            if (convertView == null) {
                vh = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_manage, null);

                vh.name = (TextView) convertView.findViewById(R.id.item_name);
                vh.code = (TextView) convertView.findViewById(R.id.item_code);
                vh.num = (TextView) convertView.findViewById(R.id.item_num);
                vh.add = (ImageView) convertView.findViewById(R.id.item_add);
                vh.sub = (ImageView) convertView.findViewById(R.id.item_sub);

                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }

            //名称 条形码 数量
            vh.name.setText(manageList.get(position).getName());
            vh.code.setText(manageList.get(position).getCode());
            vh.num.setText(manageList.get(position).getNum());


            return convertView;
        }

        public class ViewHolder {
            TextView name, code, num;
            ImageView add, sub;
        }

    }


}
