package com.creec.crungooyummy;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class FragAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public FragAdapter(FragmentManager fm, List<Fragment> fragments) {  // 申请了一个Fragment的List对象，用于保存用于滑动的Fragment对象，并在创造函数中初始化
        super(fm);
        // TODO Auto-generated constructor stub
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) { // Return the Fragment associated with a specified position.
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
