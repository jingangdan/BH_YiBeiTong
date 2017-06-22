/*
package com.bh.yibeitong.ui.address;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.bh.yibeitong.R;

*/
/**
 * Created by jingang on 2016/11/22.
 * 选择地址 测试
 *//*


public class MoreAddressTestActivity extends Activity implements View.OnClickListener,
        OnGetPoiSearchResultListener {
    private ImageView iv_address_back;

    private MapView mapView;
    private PoiSearch poiSearch;//检索对象
    private PoiCitySearchOption poiCitySearchOption;//检索参数
    //private OnGetPoiSearchResultListener onGetPoiSearchResultListene;//检索结果


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moreaddress);
        initData();

        poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(onGetPoiSearchResultListener);

        //检索执行
        poiCitySearchOption = new PoiCitySearchOption()
                .city("临沂")
                .keyword("颐高上海街");
        poiSearch.searchInCity(poiCitySearchOption);

    }


    OnGetPoiSearchResultListener onGetPoiSearchResultListener = new OnGetPoiSearchResultListener() {

        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            // TODO Auto-generated method stub
            String poiname = poiResult.getAllPoi().get(0).name;
            String poiadd = poiResult.getAllPoi().get(0).address;
            String idString = poiResult.getAllPoi().get(0).uid;

            System.out.println("第一条结果是：\n名称＝［" +
                    poiname +
                    "］\nID = [" +
                    idString
                    + "] \n地址＝［" +
                    poiadd +
                    "］");


           */
/* textView.setText(
                    "第一条结果是：\n名称＝［"+
                            poiname+
                            "］\nID = ["+
                            idString
                            + "] \n地址＝［"+
                            poiadd+
                            "］");*//*

        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
    };

    */
/**
     *
     *//*

    public void initData() {
        iv_address_back = (ImageView) findViewById(R.id.iv_address_back);
        iv_address_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_address_back:
                finish();
                break;

            default:
                break;
        }
    }

    @Override
    public void onGetPoiResult(PoiResult poiResult) {

    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }
}
*/
