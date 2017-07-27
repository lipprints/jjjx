package com.jjjx.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.jjjx.R;
import com.jjjx.data.response.RequestRoleResponse;
import com.jjjx.utils.CacheTask;
import com.jjjx.utils.NToast;


/**
 * Created by AMing on 17/6/29.
 * Company RongCloud
 */
public class VerifyRoleActivity extends BaseActivity {
    private static final int ROLE_TEACHER = 201;
    private static final int ROLE_ORGANIZATION = 202;
    LinearLayout teacherBtn;
    LinearLayout organizationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        setTitle("身份验证");
        teacherBtn = (LinearLayout) findViewById(R.id.verify_teacher);
        organizationBtn = (LinearLayout) findViewById(R.id.verify_organization);
        teacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request(ROLE_TEACHER);
            }
        });
        organizationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request(ROLE_ORGANIZATION);
            }
        });
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        if (result != null) {
            RequestRoleResponse response = (RequestRoleResponse) result;
            if (response.getHead().getCode().equals("S0000")) {
                NToast.longToast(mContext, "申请成功,我们工作人员将在 1 个工作日内对您的申请进行审核~");
                CacheTask.getInstance().cacheRole(response.getRole());
                finish();
            } else if (response.getHead().getCode().equals("S0002")) {
                NToast.shortToast(mContext, "申请已提交，正在审核。请勿重复申请~");
            }
        }
    }


    @Override
    public Object doInBackground(int requestCode) throws Exception {
        switch (requestCode) {
            case ROLE_ORGANIZATION:
                return action.requestRole("2");
            case ROLE_TEACHER:
                return action.requestRole("1");
        }
        return null;
    }
}
