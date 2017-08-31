package com.jjjx.activity;

import android.os.Bundle;

import com.jjjx.R;

/**
 * Created by AMing on 17/8/31.
 * Company RongCloud
 */

public class UserProfileActivity extends BaseActivity {
    private static final int QUERY_USER_INFO = 106;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        setTitle("用户详情");
        userId = getIntent().getStringExtra("userId");
        request(QUERY_USER_INFO);
    }

    @Override
    public Object doInBackground(int requestCode) throws Exception {
        return action.getUserProfile(userId);
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        super.onSuccess(requestCode, result);
    }
}
