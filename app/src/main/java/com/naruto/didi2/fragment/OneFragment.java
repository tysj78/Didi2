package com.naruto.didi2.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.naruto.didi2.R;
import com.naruto.didi2.util.LogUtils;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2021/3/18/0018
 */

public class OneFragment extends Fragment {
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtils.e("OneFragment onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.e("OneFragment onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.e("OneFragment onCreateView");

        View view = inflater.inflate(R.layout.one_frag, null);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtils.e("OneFragment onDetach");
    }
}
