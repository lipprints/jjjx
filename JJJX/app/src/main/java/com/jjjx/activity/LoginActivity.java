package com.jjjx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jjjx.Constants;
import com.jjjx.R;
import com.jjjx.data.response.GetRongCloudTokenResponse;
import com.jjjx.data.response.LoginResponse;
import com.jjjx.utils.AMUtils;
import com.jjjx.utils.CacheTask;
import com.jjjx.utils.NLog;
import com.jjjx.utils.NToast;
import com.jjjx.widget.LoadDialog;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * Created by AMing on 17/5/7.
 * Company RongCloud
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final int JX_LOGIN = 1;
    private static final int GET_RONG_CLOUD_TOKEN = 4;
    private EditText accountET;
    private EditText pwdET;
    private String accountString;
    private String pwdString;
    private String userId;
    private TextView registerView;
    private TextView resetPasswordView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setHeadVisibility(View.GONE);
        accountET = (EditText) findViewById(R.id.jx_login_account);
        pwdET = (EditText) findViewById(R.id.jx_login_pwd);
        registerView = (TextView) findViewById(R.id.tv_register);
        resetPasswordView = (TextView) findViewById(R.id.tv_reset);
        registerView.setOnClickListener(this);
        resetPasswordView.setOnClickListener(this);
    }

    public void login(View view) {
        accountString = accountET.getText().toString().trim();
        pwdString = pwdET.getText().toString().trim();
        if (TextUtils.isEmpty(accountString) || TextUtils.isEmpty(pwdString)) {
            NToast.shortToast(this, "账号或密码为空");
            return;
        }

        if (AMUtils.isEmail(accountString) || AMUtils.isMobile(accountString)) {
            LoadDialog.show(this);
            request(JX_LOGIN);
        } else {
            NToast.shortToast(this, "非法手机或邮箱");
        }
    }

    @Override
    public Object doInBackground(int requestCode) throws Exception {
        switch (requestCode) {
            case JX_LOGIN:
                return action.login(accountString, pwdString);
            case GET_RONG_CLOUD_TOKEN:
                return action.getRongCloudToken(accountString);
        }
        return super.doInBackground(requestCode);
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        switch (requestCode) {
            case JX_LOGIN:
                LoginResponse response = (LoginResponse) result;
                if (response.getHead().getCode().equals("10000")) {
                    userId = String.valueOf(response.getPara().getUser_id());
                    CacheTask.getInstance().cacheUserId(userId);
                    CacheTask.getInstance().cacheAccount(accountString);
                    CacheTask.getInstance().cachePwd(pwdString);
                    CacheTask.getInstance().cacheRole(response.getPara().getRole());
                    CacheTask.getInstance().cacheName(response.getPara().getName());
                    CacheTask.getInstance().cacheSex(response.getPara().getGender());
                    CacheTask.getInstance().cachePortrait(Constants.DOMAIN + response.getPara().getHead_portrait());

                    request(GET_RONG_CLOUD_TOKEN);
                } else if (response.getHead().getCode().equals("E0003")) {
                    NToast.shortToast(this, response.getHead().getMsg());
                    LoadDialog.dismiss(LoginActivity.this);
                }
                break;
            case GET_RONG_CLOUD_TOKEN:
                GetRongCloudTokenResponse getTokenResponse = (GetRongCloudTokenResponse) result;
                if (getTokenResponse.getHead().getCode().equals("000000")) {
                    CacheTask.getInstance().cacheToken(getTokenResponse.getPara().getToken().getToken());
                    RongIM.connect(getTokenResponse.getPara().getToken().getToken(), new RongIMClient.ConnectCallback() {
                        @Override
                        public void onTokenIncorrect() {
                            Log.e(TAG, "----onTokenIncorrect");
                        }

                        @Override
                        public void onSuccess(String s) {
                            LoadDialog.dismiss(LoginActivity.this);
                            NToast.shortToast(LoginActivity.this, "登录成功");
                            if (mLoginDoneListener != null) {
                                mLoginDoneListener.done();
                            }
                            finish();
                            Log.e(TAG, s + "----onSuccess");
                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {
                            Log.e(TAG, errorCode.getValue() + "----onError");
                        }
                    });
                }
                break;
        }
    }

    @Override
    public void onFailure(int requestCode, int state, Object result) {
        super.onFailure(requestCode, state, result);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                //TODO 个人注册 机构注册 dialog 邮箱注册 和 手机注册
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.tv_reset:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 99 && data != null) {
            accountET.setText(data.getStringExtra("account"));
            pwdET.setText(data.getStringExtra("pwd"));
        }
    }

    private static LoginDoneListener mLoginDoneListener;

    public interface LoginDoneListener {
        void done();
    }

    public static void setOnLoginDoneListener(LoginDoneListener loginDoneListener) {
        mLoginDoneListener = loginDoneListener;
    }
}
