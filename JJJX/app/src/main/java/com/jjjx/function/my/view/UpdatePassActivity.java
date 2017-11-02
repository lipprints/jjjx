package com.jjjx.function.my.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jjjx.R;
import com.jjjx.activity.BaseActivity;
import com.jjjx.utils.ToastUtil;

/**
 * @author xz
 * @date 2017/11/2 0002
 * 我的-修改密码
 */

public class UpdatePassActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 验证码
     */
    private EditText mCodeEt;
    /**
     * 注册手机号
     */
    private EditText mRegPhoneEt;
    private EditText mOldPassEt;
    private EditText mNewPassEt;
    private EditText mNewPassAgain;
    private TextView aup_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pass);
        setTitle("修改密码");
        setBtnBack();
        onInitView();
    }

    private void onInitView() {
        mRegPhoneEt = findViewById(R.id.aup_reg_phone);
        mCodeEt = findViewById(R.id.aup_code);
        mOldPassEt = findViewById(R.id.aup_old_pass);
        mNewPassEt = findViewById(R.id.aup_new_pass);
        mNewPassAgain = findViewById(R.id.aup_new_pass_again);
        //获取验证码按钮
        TextView sendCode = findViewById(R.id.aup_send_code);
        //提交按钮
        TextView submit = findViewById(R.id.aup_submit);
        sendCode.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aup_send_code:
                String phoneStr = mRegPhoneEt.getText().toString().trim();
                if (TextUtils.isEmpty(phoneStr)) {
                    ToastUtil.showToast("请先输入注册手机号");
                    return;
                }
                break;
            case R.id.aup_submit:

                break;
            default:
                break;
        }
    }
}
