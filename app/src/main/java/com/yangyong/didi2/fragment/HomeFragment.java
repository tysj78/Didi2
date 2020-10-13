package com.yangyong.didi2.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yangyong.didi2.R;
import com.yangyong.didi2.util.LogUtils;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2020/9/21/0021
 */

public class HomeFragment extends Fragment {
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtils.e("HomeFragment onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.e("HomeFragment onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.e("HomeFragment onCreateView");

        View view = inflater.inflate(R.layout.homefrag, null);
        return view;
    }
}
