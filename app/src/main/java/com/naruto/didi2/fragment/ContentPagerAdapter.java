package com.naruto.didi2.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2021/3/18/0018
 */

public class ContentPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList;

    public ContentPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        fragmentList = list;
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList == null ? 0 : fragmentList.size();
    }
}
