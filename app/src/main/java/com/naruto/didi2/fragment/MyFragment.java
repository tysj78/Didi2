package com.naruto.didi2.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.naruto.didi2.R;
import com.naruto.didi2.util.LogUtils;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2020/9/21/0021
 */

public class MyFragment extends Fragment implements View.OnClickListener {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            bt_myfrag_content.setText("已加载");
        }
    };
    private Button bt_myfrag_content;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtils.e("MyFragment onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.e("MyFragment onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.e("MyFragment onCreateView");

        View view = inflater.inflate(R.layout.myfrag, null);

        initView(view);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtils.e("MyFragment onDetach");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.e("MyFragment setUserVisibleHint:" + isVisibleToUser);

        if (isVisibleToUser) {
            handler.sendEmptyMessageDelayed(1,3000);
        }
    }

    private void initView(View view) {
        bt_myfrag_content = (Button) view.findViewById(R.id.bt_myfrag_content);

        bt_myfrag_content.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_myfrag_content:

                break;
        }
    }
}
