package com.jjjx.function.my.verify;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jjjx.R;
import com.jjjx.function.base.BaseActivity;
import com.jjjx.function.base.CommonlyPagerAdapter;
import com.jjjx.function.entity.TabEntity;
import com.jjjx.utils.AddressUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xz
 * @date 2017/11/8 0008
 * 我要认证
 */

public class IdentityVerifyActivity extends BaseActivity {

    private ViewPager mViewPager;
    private CommonTabLayout mTabLayout;
    private TeacherVerifyFragment mTeacherVerifyFragment;
    private OrganizationVerifyFragment mOrganizationVerifyFragment;
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_verify);
        setTitle("我要认证");
        initView();
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
    }


    private void initView() {
        mTabLayout = findViewById(R.id.aiv_ctl);
        mViewPager = findViewById(R.id.aiv_vp);

        String[] strs = {"教师认证", "机构认证"};
        List<Fragment> fragments = new ArrayList<>();
        mTeacherVerifyFragment = new TeacherVerifyFragment();
        fragments.add(mTeacherVerifyFragment);
        mOrganizationVerifyFragment = new OrganizationVerifyFragment();
        fragments.add(mOrganizationVerifyFragment);

        mViewPager.setAdapter(new CommonlyPagerAdapter(getSupportFragmentManager(), strs, fragments));
        mViewPager.setOffscreenPageLimit(2);
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
        mTabEntities.add(new TabEntity("教师认证", 0, 0));
        mTabEntities.add(new TabEntity("机构认证", 0, 0));
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
    }

    /**
     * 用子线程来 解析省市区
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 写子线程中的操作,解析省市区数据
                                initProvince();
                            }
                        });
                        thread.start();
                    }
                    break;
                default:
                    break;

            }
        }
    };

    /**
     * 在这里获取省市区
     */
    private void initProvince() {
        AddressUtil addressUtil = new AddressUtil();
        addressUtil.setOnProvinceCityAreaLintener(new AddressUtil.OnProvinceCityAreaLintener() {
            @Override
            public void listProvinceCityArea(ArrayList<String> listProvince, ArrayList<ArrayList<String>> listcity, ArrayList<ArrayList<ArrayList<String>>> listarea) {
                if (mTeacherVerifyFragment != null) {
                    mTeacherVerifyFragment.setProvinceCity(listProvince, listcity);
                }
                if (mOrganizationVerifyFragment != null) {
                    mOrganizationVerifyFragment.setProvinceCity(listProvince, listcity);
                }
            }
        });
        addressUtil.getProvinceCity(addressUtil.getProvinceToList(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        thread.interrupt();
        mHandler.removeMessages(MSG_LOAD_DATA);
    }
}
