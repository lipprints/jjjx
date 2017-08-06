package com.jjjx.fragment.find.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jjjx.fragment.find.CityFragment;
import com.jjjx.fragment.find.FollowFragment;
import com.jjjx.fragment.find.HotFragment;

/**
 * Created by xz on 2017/8/2 0002.
 * 发现的fragment适配器
 */

public class FindPagerAdapter extends FragmentStatePagerAdapter {
    private String[] mTitles;
    private HotFragment mHotFragment;
    private FollowFragment mFollowFragment;
    private CityFragment mCityFragment;


    public FindPagerAdapter(FragmentManager fm, String... titles) {
        super(fm);
        this.mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            //热门
            if (mHotFragment == null)
                mHotFragment = new HotFragment();
            return mHotFragment;
        } else if (position == 1) {
            //关注
            if (mFollowFragment == null)
                mFollowFragment = new FollowFragment();
            return mFollowFragment;
        } else if (position == 2) {
            //同城
            if (mCityFragment == null)
                mCityFragment = new CityFragment();
            return mCityFragment;
        } else {
//            if (mHotFragment == null)
//                mHotFragment = new HotFragment();
//            return mHotFragment;
            return null;
        }
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
