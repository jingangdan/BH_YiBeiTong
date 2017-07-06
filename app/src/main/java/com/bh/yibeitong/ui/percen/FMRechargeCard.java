package com.bh.yibeitong.ui.percen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.bh.yibeitong.R;
import com.bh.yibeitong.view.UserInfo;

/**
 * Created by jingang on 2017/7/5.
 */

public class FMRechargeCard extends Fragment {
    private View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_rechargecard, null);
        initData();
        return view;
    }

    public static FMRechargeCard newInstance(String name) {
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        FMRechargeCard fragment = new FMRechargeCard();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void initData() {
    }
}
