package com.jjjx.function.find;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jjjx.R;
import com.jjjx.app.base.BaseFragment;
import com.jjjx.function.entity.TabEntity;
import com.jjjx.function.find.adapter.FindPagerAdapter;
import com.jjjx.function.find.view.FindPublishActivity;
import com.jjjx.utils.CacheTask;
import com.jjjx.utils.NToast;

import java.util.ArrayList;

/**
 *
 * @author AMing
 * @date 17/5/8
 * Company RongCloud
 * 发现首页
 */
public class FindFragment extends BaseFragment {

    private ViewPager mViewPager;
    private CommonTabLayout mTabLayout;
    private TextView findPublish;


    @Override
    public View onCreateFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mTabLayout = view.findViewById(R.id.ff_stl);
        mViewPager =  view.findViewById(R.id.ff_viewpager);
        //
        mViewPager.setAdapter(new FindPagerAdapter(getChildFragmentManager(), "热门", "关注", "同城"));
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
        mTabEntities.add(new TabEntity("热门", 0, 0));
        mTabEntities.add(new TabEntity("关注", 0, 0));
        mTabEntities.add(new TabEntity("同城", 0, 0));
        mTabLayout.setTabData(mTabEntities);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        findPublish = view.findViewById(R.id.jx_find_publish);
        findPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CacheTask.getInstance().isLogin()) {
                    startActivity(new Intent(getActivity(), FindPublishActivity.class));
                }else {
                    NToast.shortToast(getActivity(),"请登录后操作");
                }
            }
        });
    }


}
