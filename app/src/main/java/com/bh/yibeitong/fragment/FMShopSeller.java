package com.bh.yibeitong.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseFragment;

/**
 * Created by jingang on 2016/11/2.
 * 商店 商家
 */

public class FMShopSeller extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_shop_seller, null);

        return view;
    }

    public static FMShopSeller newInstance(String name) {
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        FMShopSeller fragment = new FMShopSeller();
        fragment.setArguments(bundle);
        return fragment;
    }

}
