package com.bh.yibeitong.zxing;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseTextActivity;
import com.bh.yibeitong.bean.Error;
import com.bh.yibeitong.bean.ScanninGoodIndex;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.bh.yibeitong.zxing.camera.CameraManager;
import com.bh.yibeitong.zxing.decoding.CaptureActivityHandler;
import com.bh.yibeitong.zxing.decoding.InactivityTimer;
import com.bh.yibeitong.zxing.view.ViewfinderView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.util.Vector;

/**
 * Created by jingang on 2016/11/02.
 * 扫一哈子
 */
public class ZXingCaptureActivity extends BaseTextActivity implements Callback {

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;

    //private Button but_zxing_capture_back;

    /*接收页面传值*/
    Intent intent;
    String shopid;

    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_zxing_capture);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName("扫一扫");
        setTitleBack(true, 0);

        intent = getIntent();
        shopid = intent.getStringExtra("shopid");

        CameraManager.init(getApplication());
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);

//        but_zxing_capture_back = (Button) findViewById(R.id.but_zxing_capture_back);
//        but_zxing_capture_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ZXingCaptureActivity.this.finish();
//            }
//        });

        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    /**
     * 处理扫描结果
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();

        Intent intent;
        String resultCoder;
        if (resultString.equals("")) {
            Toast.makeText(ZXingCaptureActivity.this, "Scan failed!", Toast.LENGTH_SHORT).show();
        } else {

            intent = getIntent();
            resultCoder = intent.getStringExtra("coder");
            if(resultCoder.equals("1")){

                Intent resultIntent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("name", resultString);
                resultIntent.putExtras(bundle);
                this.setResult(26, resultIntent);

                ZXingCaptureActivity.this.finish();
            }else{
                getGoods(resultString, shopid);
            }


            /*Intent intent = new Intent(ZXingCaptureActivity.this, ZXingResultActivity.class);
            intent.putExtra("result", resultString);
            intent.putExtra("shopid", shopid);
            startActivity(intent);
            ZXingCaptureActivity.this.finish();*/


        }
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    String str_result = "";

    /**
     * @param gno
     * @param shopid
     */
    public void getGoods(String gno, String shopid) {

        String PATH = HttpPath.PATH + HttpPath.GETGOODS + "" +
                "gno=" + gno + "&shopid=" + shopid;

        System.out.println("" + PATH);
        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new org.xutils.common.Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("根据货号获取商品信息 = " + result);

                        ZXingCaptureActivity.this.finish();

                        str_result = result;

                        ScanninGoodIndex goods = GsonUtil.gsonIntance().gsonToBean(result, ScanninGoodIndex.class);

                        if (goods.isError() == true) {
                            Toast.makeText(ZXingCaptureActivity.this,
                                    "" + goods.getMsg().toString(), Toast.LENGTH_SHORT).show();

                        } else if (goods.isError() == false) {
                            Intent intent = new Intent(ZXingCaptureActivity.this, ZXingResultActivity.class);
                            intent.putExtra("result", result);
                            startActivity(intent);
                        }


                        /*Intent intent = new Intent(ZXingCaptureActivity.this, ZXingResultActivity.class);
                        intent.putExtra("result", result);
                        startActivity(intent);*/



                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Error error = GsonUtil.gsonIntance().gsonToBean(str_result, Error.class);
                        Toast.makeText(ZXingCaptureActivity.this,
                                "" + error.getMsg().toString(), Toast.LENGTH_SHORT).show();

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