package com.lee.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by li on 2016/4/16.
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        // TODO Auto-generated constructor stub

        this.fragmentList = fragmentList;
    }
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
