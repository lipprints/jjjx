package com.jjjx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.jjjx.R;
import com.jjjx.data.response.GetVerifyCodeResponse;
import com.jjjx.data.response.RegisterResponse;
import com.jjjx.utils.NToast;
import com.jjjx.utils.downtime.DownTimer;
import com.jjjx.utils.downtime.DownTimerListener;
import com.jjjx.widget.ClearWriteEditText;

/**
 * Created by AMing on 17/5/7.
 * Company RongCloud
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener, DownTimerListener {

    private static final int GET_VERIFY_CODE = 2;
    private static final int REGISTER = 3;
    private ClearWriteEditText accountET;
    private ClearWriteEditText codeET;
    private ClearWriteEditText pwdET;
    private Button registerBtn;
    private Button getCodeBtn;
    private String account;
    private String verifyCode;
    private String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setHeadVisibility(View.GONE);
        initView();
    }

    private void initView() {
        accountET = (ClearWriteEditText) findViewById(R.id.jx_reg_account);
        codeET = (ClearWriteEditText) findViewById(R.id.jx_reg_code);
        pwdET = (ClearWriteEditText) findViewById(R.id.jx_reg_password);
        registerBtn = (Button) findViewById(R.id.jx_reg_btn);
        getCodeBtn = (Button) findViewById(R.id.jx_reg_get_code);
        registerBtn.setOnClickListener(this);
        getCodeBtn.setOnClickListener(this);
    }


    @Override
    public Object doInBackground(int requestCode) throws Exception {
        switch (requestCode) {
            case GET_VERIFY_CODE:
                return action.getVerifyCode(account);
            case REGISTER:
                return action.register("0", account, pwd, verifyCode);
        }
        return super.doInBackground(requestCode);
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        switch (requestCode) {
            case GET_VERIFY_CODE:
                GetVerifyCodeResponse response = (GetVerifyCodeResponse) result;
                if (response.getHead().getCode().equals(String.valueOf(10000))) {
                    NToast.shortToast(this, "请求成功,请注意查收验证码");
                } else if ("E0008".equals(response.getHead().getCode())) {
                    NToast.shortToast(this, "3 分钟内不能重复获取验证码");
                }
                break;
            case REGISTER:
                RegisterResponse registerResponse = (RegisterResponse) result;
                if (registerResponse.getHead().getCode().equals(String.valueOf(10000))) {
                    NToast.shortToast(this, registerResponse.getHead().getMsg());
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    intent.putExtra("account",account);
                    intent.putExtra("pwd",pwd);
                    setResult(99,intent);
                    finish();

                } else if ("E0001".equals(registerResponse.getHead().getCode())) {
                    //两次密码不一致
                } else if ("E0004".equals(registerResponse.getHead().getCode())) {
                    //请先发送验证码
                }
                NToast.shortToast(this, registerResponse.getHead().getMsg());
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
            case R.id.jx_reg_get_code:
                account = accountET.getText().toString().trim();
                if (TextUtils.isEmpty(account)) {
                    NToast.shortToast(this, "账号不能为空");
                    return;
                }
                DownTimer downTimer = new DownTimer();
                downTimer.setListener(this);
                downTimer.startDown(180 * 1000);
                request(GET_VERIFY_CODE);
                break;
            case R.id.jx_reg_btn:
                verifyCode = codeET.getText().toString().trim();
                pwd = pwdET.getText().toString().trim();
                if (TextUtils.isEmpty(account)) {
                    NToast.shortToast(this, "账号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(verifyCode)) {
                    NToast.shortToast(this, "验证码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(pwd)) {
                    NToast.shortToast(this, "密码不能为空");
                    return;
                }
                request(REGISTER);
                break;
        }
    }

    boolean isBright = true;

    @Override
    public void onTick(long millisUntilFinished) {
        getCodeBtn.setText(String.valueOf(millisUntilFinished / 1000) + "s");
        getCodeBtn.setClickable(false);
        getCodeBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.rs_select_btn_gray));
        isBright = false;
    }

    @Override
    public void onFinish() {
        getCodeBtn.setText("发送验证码");
        getCodeBtn.setClickable(true);
        getCodeBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.rs_select_btn_yellow));
        isBright = true;
    }
}
