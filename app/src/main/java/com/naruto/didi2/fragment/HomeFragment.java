package com.naruto.didi2.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.naruto.didi2.MyApp;
import com.naruto.didi2.R;
import com.naruto.didi2.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2020/9/21/0021
 */

public class HomeFragment extends Fragment implements View.OnClickListener {
    private Button bt_start;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            initVp();
        }
    };
    private ViewPager vp_inner;

    private void initVp() {
        final List<Fragment> list = new ArrayList<>();
        list.add(new OneFragment());
        list.add(new TwoFragment());

        if (!isAdded()) {
            LogUtils.e("homeisunAdded");
            return;
        }
        vp_inner.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
    }

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
        initView(view);

//        handler.sendEmptyMessageDelayed(1, 5000);

        handler.sendEmptyMessage(1);

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtils.e("HomeFragment onDetach");
    }

    private void initView(View view) {
        bt_start = (Button) view.findViewById(R.id.bt_start);

        bt_start.setOnClickListener(this);
        vp_inner = (ViewPager) view.findViewById(R.id.vp_inner);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_start:
                Toast.makeText(MyApp.getContext(), "点击了按钮", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.e("HomeFragment setUserVisibleHint:"+isVisibleToUser);
    }

}
