package com.naruto.didi2.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


import com.naruto.didi2.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPageActivity extends AppCompatActivity {

    private List<Fragment> fragments;
    private ViewPager vp_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_page);
        initView();
        initData();

        vp_content.setAdapter(new ContentPagerAdapter(getSupportFragmentManager(), fragments));
    }

    private void initData() {
        HomeFragment homeFragment = new HomeFragment();
        MyFragment myFragment = new MyFragment();
        ThreeFragment threeFragment = new ThreeFragment();
        fragments = new ArrayList<>();
        fragments.add(homeFragment);
        fragments.add(myFragment);
        fragments.add(threeFragment);
    }

    private void initView() {
        vp_content = (ViewPager) findViewById(R.id.vp_content);
    }

}
