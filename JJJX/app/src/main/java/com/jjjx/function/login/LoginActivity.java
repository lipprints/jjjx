package com.jjjx.function.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jjjx.network.Constants;
import com.jjjx.R;
import com.jjjx.function.base.BaseActivity;
import com.jjjx.data.response.GetRongCloudTokenResponse;
import com.jjjx.data.response.LoginResponse;
import com.jjjx.function.entity.eventbus.LoginRefreshBus;
import com.jjjx.utils.AMUtils;
import com.jjjx.utils.CacheTask;
import com.jjjx.utils.NToast;
import com.jjjx.widget.LoadDialog;
import com.jjjx.widget.dialog.AppProgressDialog;

import org.greenrobot.eventbus.EventBus;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 *
 * @author AMing
 * @date 17/5/7
 * Company RongCloud
 * 登陆页
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
        setTitle("账号登陆");
        onInitView();
    }

    private void onInitView() {
        ImageView loginIco = findViewById(R.id.al_login);
        accountET = findViewById(R.id.jx_login_account);
        pwdET = findViewById(R.id.jx_login_pwd);
        registerView = findViewById(R.id.tv_register);
        resetPasswordView = findViewById(R.id.tv_reset);
        registerView.setOnClickListener(this);
        resetPasswordView.setOnClickListener(this);

        Glide.with(this).load(R.drawable.ico_login).into(loginIco);
    }

    public void login(View view) {
        accountString = accountET.getText().toString().trim();
        pwdString = pwdET.getText().toString().trim();
        if (TextUtils.isEmpty(accountString) || TextUtils.isEmpty(pwdString)) {
            NToast.shortToast(this, "账号或密码为空");
            return;
        }

        if (AMUtils.isEmail(accountString) || AMUtils.isMobile(accountString)) {
            AppProgressDialog.show(mContext, "登录中...");
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
            default:
                break;
        }
        return super.doInBackground(requestCode);
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        switch (requestCode) {
            case JX_LOGIN:
                LoginResponse response = (LoginResponse) result;
                if ("10000".equals(response.getHead().getCode())) {
                    userId = String.valueOf(response.getPara().getUser_id());
                    CacheTask.getInstance().cacheUserId(userId);
                    CacheTask.getInstance().cacheAccount(accountString);
                    CacheTask.getInstance().cachePwd(pwdString);
                    CacheTask.getInstance().cacheRole(response.getPara().getRole());
                    CacheTask.getInstance().cacheName(response.getPara().getName());
                    CacheTask.getInstance().cacheSex(response.getPara().getGender());
                    CacheTask.getInstance().cachePortrait(Constants.DOMAIN + response.getPara().getHead_portrait());
                    //刷新我的页面
                    request(GET_RONG_CLOUD_TOKEN);
                } else if ("E0003".equals(response.getHead().getCode())) {
                    NToast.shortToast(this, response.getHead().getMsg());
                    LoadDialog.dismiss(LoginActivity.this);
                }
                break;
            case GET_RONG_CLOUD_TOKEN:
                GetRongCloudTokenResponse getTokenResponse = (GetRongCloudTokenResponse) result;
                if ("000000".equals(getTokenResponse.getHead().getCode())) {
                    CacheTask.getInstance().cacheToken(getTokenResponse.getPara().getToken().getToken());
                    RongIM.connect(getTokenResponse.getPara().getToken().getToken(), new RongIMClient.ConnectCallback() {
                        @Override
                        public void onTokenIncorrect() {
                            Log.e(TAG, "----onTokenIncorrect");
                        }

                        @Override
                        public void onSuccess(String s) {
                            AppProgressDialog.onDismiss();
                            EventBus.getDefault().post(new LoginRefreshBus(true));
                            NToast.shortToast(LoginActivity.this, "登录成功");
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
            default:
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
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
                break;
            default:
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

}
