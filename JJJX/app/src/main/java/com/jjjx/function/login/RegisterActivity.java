package com.jjjx.function.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.jjjx.R;
import com.jjjx.function.base.BaseActivity;
import com.jjjx.data.response.GetVerifyCodeResponse;
import com.jjjx.data.response.RegisterResponse;
import com.jjjx.utils.NToast;
import com.jjjx.utils.ToastUtil;
import com.jjjx.utils.downtime.DownTimer;
import com.jjjx.utils.downtime.DownTimerListener;
import com.jjjx.widget.dialog.AppProgressDialog;

/**
 * @author AMing
 * @date 17/5/7
 * @update xz
 * Company RongCloud
 * 注册页
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener, DownTimerListener {

    /**
     * 获取验证码的其你去
     */
    private static final int GET_VERIFY_CODE = 2;
    /**
     * 注册的请求
     */
    private static final int REGISTER = 3;

    private EditText mAccountET;
    private EditText mCodeET;
    private EditText mPwdET;
    /**
     * 再次输入密码框
     */
    private EditText mPwdAgainEt;
    private TextView mRegisterBtn;
    private TextView mGetCodeBtn;
    /**
     * 确认阅读协议框
     */
    private CheckBox mSelectAgreementCb;
    /**
     * 协议跳转
     */
    private TextView mAgreementTv;

    private String account;
    private String verifyCode;
    private String pwd;
    private DownTimer mDownTimer;

    boolean isBright = true;
    private String mPwdAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register_personal);
        setTitle("注册");
        initView();
    }


    private void initView() {
        mAccountET = findViewById(R.id.frp_account);
        mCodeET = findViewById(R.id.frp_code);
        mPwdET = findViewById(R.id.frp_password);
        mPwdAgainEt = findViewById(R.id.frp_password_again);
        mRegisterBtn = findViewById(R.id.frp_submit);
        mGetCodeBtn = findViewById(R.id.frp_send_code);
        mSelectAgreementCb = findViewById(R.id.frp_select_agreement);
        mAgreementTv = findViewById(R.id.frp_agreement);
        mRegisterBtn.setOnClickListener(this);
        mGetCodeBtn.setOnClickListener(this);
    }


    @Override
    public Object doInBackground(int requestCode) throws Exception {
        switch (requestCode) {
            case GET_VERIFY_CODE:
                return action.getVerifyCode(account);
            case REGISTER:
                //AppProgressDialog.show(RegisterActivity.this,"正在注册");
                return action.register("0", account, pwd, mPwdAgain, verifyCode);
            default:
                break;
        }
        return super.doInBackground(requestCode);
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        switch (requestCode) {
            case GET_VERIFY_CODE:
                GetVerifyCodeResponse response = (GetVerifyCodeResponse) result;
                if (response.getHead().getCode().equals(String.valueOf(10000))) {
                    NToast.shortToast(RegisterActivity.this, "请求成功,请注意查收验证码");
                } else if ("E0008".equals(response.getHead().getCode())) {
                    NToast.shortToast(RegisterActivity.this, "3 分钟内不能重复获取验证码");
                }
                break;
            case REGISTER:
                AppProgressDialog.onDismiss();
                RegisterResponse registerResponse = (RegisterResponse) result;
                if (registerResponse.getHead().getCode().equals(String.valueOf(10000))) {
                    NToast.shortToast(RegisterActivity.this, registerResponse.getHead().getMsg());
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    intent.putExtra("account", account);
                    intent.putExtra("pwd", pwd);
                    setResult(99, intent);
                    finish();
                } else if ("E0001".equals(registerResponse.getHead().getCode())) {
                    //两次密码不一致

                } else if ("E0004".equals(registerResponse.getHead().getCode())) {
                    //请先发送验证码
                }
                NToast.shortToast(RegisterActivity.this, registerResponse.getHead().getMsg());
                break;
            default:
                break;
        }
    }

    @Override
    public void onFailure(int requestCode, int state, Object result) {
        AppProgressDialog.onDismiss();
        super.onFailure(requestCode, state, result);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.frp_send_code:
                account = mAccountET.getText().toString().trim();
                if (TextUtils.isEmpty(account)) {
                    NToast.shortToast(RegisterActivity.this, "账号不能为空");
                    return;
                }
                mDownTimer = new DownTimer();
                mDownTimer.setListener(this);
                mDownTimer.startDown(180 * 1000);
                request(GET_VERIFY_CODE);
                break;
            case R.id.frp_submit:
                verifyCode = mCodeET.getText().toString().trim();
                pwd = mPwdET.getText().toString().trim();
                mPwdAgain = mPwdAgainEt.getText().toString().trim();
                if (TextUtils.isEmpty(account)) {
                    NToast.shortToast(RegisterActivity.this, "账号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(verifyCode)) {
                    NToast.shortToast(RegisterActivity.this, "验证码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(pwd)) {
                    NToast.shortToast(RegisterActivity.this, "密码不能为空");
                    return;
                }
                if (!TextUtils.equals(pwd, mPwdAgain)) {
                    ToastUtil.showToast("两次密码不相同");
                    return;
                }
                if (!mSelectAgreementCb.isChecked()) {
                    ToastUtil.showToast("请阅读并同意注册协议");
                    return;
                }
                request(REGISTER);
                break;
            default:
                break;
        }
    }


    @Override
    public void onTick(long millisUntilFinished) {
        mGetCodeBtn.setText(String.valueOf(millisUntilFinished / 1000) + "s");
        mGetCodeBtn.setClickable(false);
        mGetCodeBtn.setBackground(ContextCompat.getDrawable(RegisterActivity.this, R.drawable.rs_select_btn_gray));
        isBright = false;
    }

    @Override
    public void onFinish() {
        mGetCodeBtn.setText("发送验证码");
        mGetCodeBtn.setClickable(true);
        mGetCodeBtn.setBackground(ContextCompat.getDrawable(RegisterActivity.this, R.drawable.rs_select_btn_yellow));
        isBright = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppProgressDialog.onDismiss();
        if (mDownTimer != null) {
            mDownTimer.stopDown();
        }
    }
}
