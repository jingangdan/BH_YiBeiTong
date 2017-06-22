package com.bh.yibeitong.fragment.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bh.yibeitong.R;

/**
 * Created by jingang on 2016/11/11.
 * 商品/商家投诉
 */

public class FMComplain extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_complain, null);
        return view;
    }

    public static FMComplain newInstance(String name) {
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        FMComplain fragment = new FMComplain();
        fragment.setArguments(bundle);
        return fragment;
    }
}
