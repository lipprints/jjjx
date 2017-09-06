package com.jjjx.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jjjx.R;
import com.jjjx.data.response.UserProfileResponse;
import com.jjjx.fragment.ProfileClassFragment;
import com.jjjx.fragment.ProfileFindFragment;
import com.jjjx.utils.CacheTask;
import com.jjjx.utils.NToast;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by AMing on 17/8/31.
 * Company RongCloud
 */

public class UserProfileActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private static final int QUERY_USER_INFO = 106;
    private static final int FOLLOW = 107;
    private static final int UN_FOLLOW = 108;
    private String userId;
    SimpleDraweeView simpleDraweeView;
    TextView nameTextView;
    Button followButton;
    int followInt;
    String byuser_id;
    private ViewPager mViewPager;
    private List<Fragment> mFragment = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        setTitle("用户详情");
        userId = getIntent().getStringExtra("userId");
        request(QUERY_USER_INFO);
        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.profile_user_head);
        nameTextView = (TextView) findViewById(R.id.profile_user_name);
        followButton = (Button) findViewById(R.id.user_profile_follow);
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (followInt % 2 == 0) {
                    //去关注
                    NToast.shortToast(mContext, "关注");
                    followButton.setText("取消关注");
                    request(UN_FOLLOW);

                } else {
                    //取消关注
                    NToast.shortToast(mContext, "取消关注");
                    followButton.setText("关注");
                    request(FOLLOW);
                }
                followInt++;
            }
        });
        mViewPager = (ViewPager) findViewById(R.id.user_profile_viewpager);


    }

    @Override
    public Object doInBackground(int requestCode) throws Exception {
        switch (requestCode) {
            case QUERY_USER_INFO:
                return action.getUserProfile(userId);
            case UN_FOLLOW:
                return action.addAttentionInfo(CacheTask.getInstance().getUserId(), byuser_id);
            case FOLLOW:
                return action.deleteAttentionInfo(CacheTask.getInstance().getUserId(), byuser_id);
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        if (requestCode == QUERY_USER_INFO) {
            UserProfileResponse response = (UserProfileResponse) result;
            if (response.getHead().getCode().equals("10000")) {
                simpleDraweeView.setImageURI(response.getPara().getUser().getHead_portrait());
                nameTextView.setText(response.getPara().getUser().getName());
                if (CacheTask.getInstance().getUserId().equals(String.valueOf(response.getPara().getUser().getUser_id()))) {
                    followButton.setVisibility(View.GONE);
                } else {
                    followButton.setVisibility(View.VISIBLE);
                    if (response.getPara().getTab().equals("2")) { //未关注
                        followButton.setText("关注");
                        followInt = 0;
                    } else {
                        followButton.setText("取消关注");
                        followInt = 1;
                    }
                }

                byuser_id = String.valueOf(response.getPara().getUser().getUser_id());

                if (CacheTask.getInstance().getUserRole().equals("2") || CacheTask.getInstance().getUserRole().equals("3")) {
                    mFragment.add(ProfileClassFragment.newInstance(response.getPara().getCourseRelease())); //课程的Fragment
                }
                mFragment.add(ProfileFindFragment.newInstance(response.getPara().getDiscoverInfo()));//发现的 Fragment
                FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
                    @Override
                    public Fragment getItem(int position) {
                        return mFragment.get(position);
                    }

                    @Override
                    public int getCount() {
                        return mFragment.size();
                    }
                };
                mViewPager.setAdapter(fragmentPagerAdapter);
                mViewPager.setOnPageChangeListener(this);


            }
        } else if (requestCode == FOLLOW) {

        } else if (requestCode == UN_FOLLOW) {
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
