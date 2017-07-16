package com.jjjx;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.jjjx.data.BaseAction;
import com.jjjx.data.okhttp.RequestParams;
import com.jjjx.data.response.GetRongCloudTokenResponse;
import com.jjjx.data.response.GetVerifyCodeResponse;
import com.jjjx.data.response.IndexDataResponse;
import com.jjjx.data.response.LoginResponse;
import com.jjjx.data.response.RegisterResponse;
import com.jjjx.data.response.RequestRoleResponse;
import com.jjjx.data.response.UpdateInformationResponse;
import com.jjjx.utils.AMUtils;

import static android.R.attr.x;
import static com.baidu.location.h.j.p;
import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by AMing on 17/5/7.
 * Company RongCloud
 */

public class JxAction extends BaseAction {
    /**
     * 构造方法
     *
     * @param context
     */
    public JxAction(Context context) {
        super(context);
    }

    /**
     * 登录
     *
     * @param account
     * @param pwd
     * @return
     * @throws Exception
     */
    public LoginResponse login(String account, String pwd) throws Exception {
        LoginResponse response = new LoginResponse();
        String url = getURL(Constants.LOGIN_URL);
        RequestParams params = getRequestParams();
        params.put("name", account);
        params.put("pwd", pwd);
        String result = httpManager.post(url, params);
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, LoginResponse.class);
        }
        return response;
    }

    /**
     * 注册
     *
     * @param flag        0 注册 1 找回密码
     * @param account     邮箱或者手机号
     * @param pwd
     * @param sms_captcha 验证码
     * @return
     * @throws Exception
     */
    public RegisterResponse register(String flag, String account, String pwd, String sms_captcha) throws Exception {
        RegisterResponse response = new RegisterResponse();
        String url = getURL(Constants.REGIST_URL);
        RequestParams params = getRequestParams();
        if (AMUtils.isMobile(account)) {
            params.put("mobile", account);
        } else {
            params.put("email", account);
        }
        params.put("flag", flag);
        params.put("pwd", pwd);
        params.put("repass", pwd);
        params.put("sms_captcha", sms_captcha);
        String result = httpManager.post(url, params);
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, RegisterResponse.class);
        }
        return response;
    }

    /**
     * 获取验证码
     *
     * @param account
     * @return
     * @throws Exception
     */
    public GetVerifyCodeResponse getVerifyCode(String account) throws Exception {
        GetVerifyCodeResponse response = new GetVerifyCodeResponse();
        String url = getURL(Constants.SEND_CODE);
        RequestParams params = getRequestParams();
        if (AMUtils.isMobile(account)) {
            params.put("mobile", account);
        } else {
            params.put("email", account);
        }
        String result = httpManager.post(url, params);
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, GetVerifyCodeResponse.class);
        }
        return response;
    }

    /**
     * 获取融云token
     *
     * @param uname
     * @return
     * @throws Exception
     */
    public GetRongCloudTokenResponse getRongCloudToken(String uname) throws Exception {
        GetRongCloudTokenResponse response = new GetRongCloudTokenResponse();
        String url = getURL(Constants.GET_RONGCLOUD_TOKEN);
        RequestParams params = getContainsUserIdRequestParams();
        params.put("uname", uname);
        String result = httpManager.get(url, params);
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, GetRongCloudTokenResponse.class);
        }
        return response;
    }

    /**
     * 申请教师 或者 机构认证
     *
     * @param role
     * @return
     * @throws Exception
     */
    public RequestRoleResponse requestRole(String role) throws Exception {
        RequestRoleResponse response = new RequestRoleResponse();
        String url = getURL(Constants.AUTH_ROLE);
        RequestParams params = getContainsUserIdRequestParams();
        params.put("role", role);
        String result = httpManager.get(url, params);
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, RequestRoleResponse.class);
        }
        return response;
    }

    /**
     * 请求首页数据
     *
     * @return
     * @throws Exception
     */
    public IndexDataResponse requestIndexData() throws Exception {
        IndexDataResponse response = new IndexDataResponse();
        String url = getURL(Constants.INDEX_ALL);
        String result = httpManager.get(url);
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, IndexDataResponse.class);
        }
        return response;
    }

    public UpdateInformationResponse updateInformation() throws Exception {
        return null;
    }
}
