package com.jjjx.function.base;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * @author xz
 * @date 2017/11/8 0008
 * 通用的 viewPager和tablayout的 适配器
 */

public class CommonlyPagerAdapter extends FragmentStatePagerAdapter {
    private String[] mTitles;
    private List<Fragment> mFragments;


    public CommonlyPagerAdapter(FragmentManager fm, @NonNull String[] titles, @NonNull List<Fragment> fragments) {
        super(fm);
        this.mTitles = titles;
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        if (position < mFragments.size()) {
            if (mFragments.get(position) != null) {
                return mFragments.get(position);
            } else {
                throw new IllegalArgumentException("fragment is null");
            }
        } else {
            throw new IllegalArgumentException("fragment to position is no");
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
