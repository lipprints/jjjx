package com.jjjx.fragment.find;

import android.view.View;

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
}
