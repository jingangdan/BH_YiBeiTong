package com.bh.yibeitong.fragment.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseFragment;
import com.bh.yibeitong.seller.fragment.FMSellerOK;

/**
 * Created by jingang on 2016/11/11.
 * 体验问题
 */

public class FMProblem extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_problem, null);
        return view;
    }
    public static FMProblem newInstance(String name) {
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        FMProblem fragment = new FMProblem();
        fragment.setArguments(bundle);
        return fragment;
    }


}
