package com.bh.yibeitong.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.bh.yibeitong.actitvity.MainActivity;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by jingang on 2016/10/23.
 * 获取网络数据方法
 */

public class GetMsgForNet extends Activity {

    //注意：每一段address之间以符号“&”分割
    private static String address1 = "http://www.ybt9.com/index.php?ctrl=app&";
    private static String address2 = "action=goodsindex&";

    private static String address3 = "lat=35.111217&lng=118.351972&";

    private static String address4 = "page=1&datatype=json";

    public static Context mContext;


    public static String path = address1 + address2 + address3 + address4;


    private MainActivity mainActivity = new MainActivity();


    /**
     * 使用XUtils框架
     *
     * @param latitude
     * @param longtitude
     */
    public static void getXUtils(String latitude, String longtitude) {
        HttpUtils http = new HttpUtils();
        http.send(HttpMethod.GET,
                HttpPath.path + "lat=" + latitude + "&lng=" + longtitude + "",
                new RequestCallBack<String>() {
                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        //testTextView.setText(current + "/" + total);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        //textView.setText(responseInfo.result);
                        System.out.println("成功返回数据 = " + responseInfo.result.toString());

                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("失败" + error);
                    }
                });
    }


    /**
     * 添加购物车
     *
     * @param shopid 商店id
     * @param num    数量
     * @param gid    商品id
     */
    public static void putShopCart(String shopid, int num, String gid) {
        HttpUtils http = new HttpUtils();
        http.send(HttpMethod.GET,
                HttpPath.PATH + HttpPath.ADD_SHOPCART
                        + "shopid=" + shopid + "&num=" + num + "&gid=" + gid,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {


                        String result = responseInfo.result;
                        System.out.println("添加购物车 返回值:" + result);
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        System.out.println("添加车 失败" + e.toString() + "   String = " + s);

                    }
                });

    }

    /**
     * 获取购物车信息
     *
     * @param shopid
     */
    public static void getShopCart(String shopid) {

        String PATH = HttpPath.PATH + HttpPath.GETCART + "shopid=" + shopid;

        RequestParams params = new RequestParams(PATH);
        x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        //没有数据
                        System.out.println("获取购物车成功" + result);
                        Toast.makeText(mContext, "添加购物车成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Toast.makeText(mContext, "添加购物车失败", Toast.LENGTH_SHORT).show();
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
     * 添加购物车
     * 还有一个num 默认为1
     *
     * @param shopid
     * @param gid
     */
    public void addShopCart(String shopid, String gid) {
        String PATH = HttpPath.PATH + HttpPath.ADD_SHOPCART +
                "&num=1&shopid=" + shopid + "&gid=" + gid;
        RequestParams params = new RequestParams(PATH);
        x.http().post(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Toast.makeText(mContext, "添加购物车成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Toast.makeText(mContext, "添加购物车失败", Toast.LENGTH_SHORT).show();
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
     * 使用post方法上传数据
     *
     * @param shopid
     * @param num
     * @param gid
     */
    public void postXUtils(String shopid, int num, String gid) {
        RequestParams params = new RequestParams();
        params.addHeader("name", "value");
        params.addQueryStringParameter("name", "value");
        // 只包含字符串参数时默认使用BodyParamsEntity，
        // 类似于UrlEncodedFormEntity（"application/x-www-form-urlencoded"）。
        params.addBodyParameter("name", "value");
        // 加入文件参数后默认使用MultipartEntity（"multipart/form-data"），
        // 如需"multipart/related"，xUtils中提供的MultipartEntity支持设置subType为"related"。
        // 使用params.setBodyEntity(httpEntity)可设置更多类型的HttpEntity（如：
        // MultipartEntity,BodyParamsEntity,FileUploadEntity,InputStreamUploadEntity,StringEntity）。
        // 例如发送json参数：params.setBodyEntity(new StringEntity(jsonStr,charset));
        params.addBodyParameter("file", new File("path"));

        HttpUtils http = new HttpUtils();
        http.send(HttpMethod.POST,
                "uploadUrl....",
                null,
                new RequestCallBack<String>() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                    }
                });

    }

    public void addShopCart(String shopid, int num, String gid) {
        String Path = "";


    }

}
