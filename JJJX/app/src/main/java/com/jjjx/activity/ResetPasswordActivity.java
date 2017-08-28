package com.jjjx.activity;

import android.os.Bundle;

import com.jjjx.R;

/**
 * Created by AMing on 17/8/21.
 * Company RongCloud
 */

public class ResetPasswordActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        setTitle("找回密码");
    }
}
