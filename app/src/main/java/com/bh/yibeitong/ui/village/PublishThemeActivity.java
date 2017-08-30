package com.bh.yibeitong.ui.village;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.Errors;
import com.bh.yibeitong.bean.Register;
import com.bh.yibeitong.bean.village.ImgRet;
import com.bh.yibeitong.ui.village.addimg.activity.AlbumActivity;
import com.bh.yibeitong.ui.village.addimg.activity.GalleryActivity;
import com.bh.yibeitong.ui.village.addimg.util.Bimp;
import com.bh.yibeitong.ui.village.addimg.util.FileUtils;
import com.bh.yibeitong.ui.village.addimg.util.ImageItem;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.view.UserInfo;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 发表主题（帖子）
 *
 * @author jingang
 * @version 2016年12月29日
 */
public class PublishThemeActivity extends BaseTextActivity {
    private View parentView;
    private GridView noScrollgridview;
    private GridAdapter adapter;
    private PopupWindow pop = null;
    private LinearLayout ll_popup;
    public static Bitmap bimap;

    /**/
    private EditText publish_title, publish_bady;
    private Button publish, cancel;

    /**/
    String title, bady;
    String UTF_title, UTF_bady;

    /*本地缓存*/
    private UserInfo userInfo;
    String uid, pwd, phone;

    /*接口地址*/
    String PATH;

    //图片列表
    private String media_ids = "";

    /*接收上个页面传值*/
    private Intent intent;
    private String cateid;

    int size = 0;

    @Override
    protected void setRootView() {
        super.setRootView();
        parentView = getLayoutInflater().inflate(R.layout.activity_publish_theme, null);
        setContentView(parentView);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("发表主题");
        setTitleBack(true, 0);

        initData();

        Init();

    }

    /*初始化UI组件*/
    public void initData() {
        userInfo = new UserInfo(getApplication());

        publish_title = (EditText) findViewById(R.id.et_publish_title);
        publish_bady = (EditText) findViewById(R.id.et_publish_bady);

        publish = (Button) findViewById(R.id.but_publish);
        cancel = (Button) findViewById(R.id.but_publish_cancel);

        publish.setOnClickListener(this);
        cancel.setOnClickListener(this);

        intent = getIntent();
        cateid = intent.getStringExtra("cateid");

        if (!(userInfo.getUserInfo().equals(""))) {
            Register register = GsonUtil.gsonIntance().gsonToBean(userInfo.getUserInfo(), Register.class);
            uid = register.getMsg().getUid();
            phone = register.getMsg().getPhone();
            if (!(userInfo.getPwd().equals(""))) {
                pwd = userInfo.getPwd();

            }
        } else {
            toast("未登录");
        }


    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.but_publish:
                //发送
                System.out.println("本地缓存imgMedia = "+userInfo.getImgMedia());

                title = publish_title.getText().toString();
                bady = publish_bady.getText().toString();

                try {
                    UTF_title = URLEncoder.encode(title, "UTF-8");
                    UTF_bady = URLEncoder.encode(bady, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                if (userInfo.getCode().toString().equals("0")) {
                    postPublishTheme(uid, pwd, userInfo.getImgMedia().toString(), UTF_title, UTF_bady,cateid);
                } else {
                    postPublishTheme("phone", phone, userInfo.getImgMedia().toString(), UTF_title, UTF_bady, cateid);
                }

                media_ids = "";

                break;

            case R.id.but_publish_cancel:
                //取消
                PublishThemeActivity.this.finish();
                break;

            default:
                break;
        }
    }

    /**
     * 上传图片
     * 可放置在
     * @param imgPath
     */
    public void uploadMethod(String imgPath) {
        RequestParams params = new RequestParams(
                "http://www.ybt9.com/index.php?ctrl=app&action=luntanimg&datatype=json");
        params.addBodyParameter("imgFile[]", new File(imgPath));
        params.setMultipart(true);
        Callback.Cancelable cancelable
                = x.http().post(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {

                        System.out.println("上传返回" + result);
                        ImgRet imgRet = GsonUtil.gsonIntance().gsonToBean(result, ImgRet.class);

                        if (media_ids.equals("")) {
                            media_ids = imgRet.getMsg().get(0).toString();
                        } else {
                            media_ids = media_ids + "@" + imgRet.getMsg().get(0).toString();
                        }

                        //上传图片返回图片地址
                        System.out.println("media_ids = "+media_ids);

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFinished() {

                    }

                });

    }

    /**
     * 发表主题
     *
     * @param uid
     * @param pwd
     * @param media_ids 图片
     * @param title     标题
     * @param message   内容
     * @param cateid    论坛模块
     */

    public void postPublishTheme(String uid, String pwd, String media_ids, String title, String message, String cateid) {
        if (userInfo.getCode().toString().equals("0")) {
            PATH = HttpPath.PATH + HttpPath.VILLAGE_SAVEUSERPMES +
                    "uid=" + uid + "&pwd=" + pwd + "&media_ids=" + media_ids +
                    "&title=" + title + "&message=" + message + "&cateid=" + cateid;
        } else {
            PATH = HttpPath.PATH + HttpPath.VILLAGE_SAVEUSERPMES +
                    "logintype=" + uid + "&loginphone=" + pwd + "&media_ids=" + media_ids +
                    "&title=" + title + "&message=" + message + "&cateid=" + cateid;
        }

        System.out.println("" + PATH);

        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("发表主题" + result);
                        Errors error = GsonUtil.gsonIntance().gsonToBean(result, Errors.class);

                        if(error.isError() == false){
                            //发布成功
                            Intent intent = new Intent();
                            setResult(24, intent);
                            PublishThemeActivity.this.finish();

                        }else{
                            toast("发布失败!");
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


    /*选择图片（拍照）*/
    public void Init() {

        pop = new PopupWindow(PublishThemeActivity.this);

        View view = getLayoutInflater().inflate(R.layout.personal_header_choice, null);

        view.setAnimation(AnimationUtils.loadAnimation(
                PublishThemeActivity.this, R.anim.slide_bottom_to_top));

        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);

        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);

        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        Button bt1 = (Button) view
                .findViewById(R.id.btn_take_photo);
        Button bt2 = (Button) view
                .findViewById(R.id.btn_pick_photo);
        Button bt3 = (Button) view
                .findViewById(R.id.btn_cancel);
        parent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //拍照
                photo();
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //相册选择图片
                Intent intent = new Intent(PublishThemeActivity.this, AlbumActivity.class);

                startActivityForResult(intent, 20);
                //startActivity(intent);
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });

        noScrollgridview = (GridView) findViewById(R.id.gv_addimg);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(this);
        adapter.update();
        noScrollgridview.setAdapter(adapter);

        noScrollgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (arg2 == Bimp.tempSelectBitmap.size()) {
                    ll_popup.startAnimation(AnimationUtils.loadAnimation(PublishThemeActivity.this, R.anim.slide_bottom_to_top));
                    pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
                } else {
                    Intent intent = new Intent(PublishThemeActivity.this,
                            GalleryActivity.class);
                    intent.putExtra("position", "1");
                    intent.putExtra("ID", arg2);
                    startActivity(intent);
                }
            }
        });

    }

    /**
     * 最多显示9张图片（包括拍照和相册选择）
     */
    @SuppressLint("HandlerLeak")
    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private int selectedPosition = -1;
        private boolean shape;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void update() {
            loading();
        }

        public int getCount() {
            if (Bimp.tempSelectBitmap.size() == 9) {
                return 9;
            }
            return (Bimp.tempSelectBitmap.size() + 1);
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_published_grida,
                        parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView
                        .findViewById(R.id.item_grida_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == Bimp.tempSelectBitmap.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(), R.mipmap.ic_addimg));
                if (position == 9) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
            }

            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
        }

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        adapter.notifyDataSetChanged();
                        break;
                }
                super.handleMessage(msg);
            }
        };

        public void loading() {
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        if (Bimp.max == Bimp.tempSelectBitmap.size()) {
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                            break;
                        } else {
                            Bimp.max += 1;
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        }
                    }
                }
            }).start();
        }
    }

    public String getString(String s) {
        String path = null;
        if (s == null)
            return "";
        for (int i = s.length() - 1; i > 0; i++) {
            s.charAt(i);
        }
        return path;
    }

    protected void onRestart() {
        adapter.update();
        super.onRestart();
    }

    private static final int TAKE_PICTURE = 0x000001;

    public void photo() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (Bimp.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK) {

                    String fileName = String.valueOf(System.currentTimeMillis());
                    Bitmap bm = (Bitmap) data.getExtras().get("data");
                    FileUtils.saveBitmap(bm, fileName);

                    ImageItem takePhoto = new ImageItem();
                    takePhoto.setBitmap(bm);
                    Bimp.tempSelectBitmap.add(takePhoto);
                }
                break;
        }

        if (requestCode == 20 && resultCode == 21) {
            Bundle bundle = data.getExtras();
            /*String imgPath = bundle.getString("imgPath");
            System.out.println("21 Album = "+imgPath);*/
        }else if(requestCode == 20 && resultCode == 22){
            Bundle bundle = data.getExtras();
            /*String imgPath = bundle.getString("imgPath");
            System.out.println("22 Gallery = "+imgPath);*/
        }
    }

}