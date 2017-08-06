package com.jjjx.fragment.find;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjjx.R;
import com.jjjx.app.base.XBaseLazyFragment;

/**
 * Created by xz on 2017/8/1 0001.
 * 同城
 */

public class CityFragment extends XBaseLazyFragment{
    @Override
    protected int getContentView() {
        return R.layout.fragment_find_city;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public String closeFragment() {
        return "CityFragment";
    }

    @Override
    public View onCreateFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }
}
