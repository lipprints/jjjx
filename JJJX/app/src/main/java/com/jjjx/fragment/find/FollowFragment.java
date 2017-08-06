package com.jjjx.fragment.find;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjjx.R;
import com.jjjx.app.base.XBaseLazyFragment;

/**
 * Created by xz on 2017/8/1 0001.
 * 关注
 */

public class FollowFragment extends XBaseLazyFragment {
    @Override
    protected int getContentView() {
        return R.layout.fragment_find_follow;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public String closeFragment() {
        return "FollowFragment";
    }

    @Override
    public View onCreateFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }
}
