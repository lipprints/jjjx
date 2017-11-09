package com.jjjx.function.login;

import android.os.Bundle;

import com.jjjx.R;
import com.jjjx.function.base.BaseActivity;

/**
 * Created by AMing on 17/8/21.
 * Company RongCloud
 * 找回密码
 */

public class ResetPasswordActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        setTitle("找回密码");
    }
}
