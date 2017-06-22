package com.bh.yibeitong.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bh.yibeitong.R;

/**
 * Created by jingang on 2016/11/2.
 * 商店 评价
 */

public class FMShopComment extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_shop_comment, null);

        return view;
    }
    public static FMShopComment newInstance(String name) {
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        FMShopComment fragment = new FMShopComment();
        fragment.setArguments(bundle);
        return fragment;
    }


}
