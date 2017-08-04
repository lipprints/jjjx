package com.jjjx;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.jjjx.data.BaseAction;
import com.jjjx.data.okhttp.RequestParams;
import com.jjjx.data.response.GetRongCloudTokenResponse;
import com.jjjx.data.response.GetVerifyCodeResponse;
import com.jjjx.data.response.IndexDataResponse;
import com.jjjx.data.response.InformationResponse;
import com.jjjx.data.response.LoginResponse;
import com.jjjx.data.response.RegisterResponse;
import com.jjjx.data.response.RequestRoleResponse;
import com.jjjx.data.response.SearchResponse;
import com.jjjx.data.response.UpdateInformationResponse;
import com.jjjx.utils.AMUtils;
import com.jjjx.utils.CacheTask;
import com.jjjx.utils.NLog;

import java.net.URL;

import static android.R.attr.x;
import static com.baidu.location.h.j.p;
import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;
import static io.rong.imlib.statistics.UserData.gender;

/**
 * Created by AMing on 17/5/7.
 * Company RongCloud
 */

public class JxAction extends BaseAction {
    public static final String TAG = JxAction.class.getSimpleName();

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
        NLog.e(TAG, result);
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
        NLog.e(TAG, result);
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
        NLog.e(TAG, result);
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
        NLog.e(TAG, result);
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
        RequestParams params = getRequestParams();
        params.put("role", role);
        params.put("user_id", CacheTask.getInstance().getUserId());
        String result = httpManager.get(url, params);
        NLog.e(TAG, result);
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
        NLog.e(TAG, result);
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, IndexDataResponse.class);
        }
        return response;
    }
//    user_id,name ,gender 男女,age 年龄,occupation 职业,seniority 教龄,courses 课程,teacher_amount 教师人数,average_age 平均年龄


    /**
     * 设置无身份的用户信息
     *
     * @param user_id
     * @param name
     * @param gender  性别需要默认值
     * @throws Exception
     */
    public InformationResponse setUserInfo(String user_id, String name, String gender) throws Exception {
        InformationResponse response = new InformationResponse();
        String url = getURL(Constants.UPDATEINFORMATION);
        RequestParams params = getRequestParams();
        params.put("user_id", user_id);
        params.put("name", name);
        params.put("gender", gender);
        String result = httpManager.post(url, params);
        NLog.e(TAG, result);
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, InformationResponse.class);
        }
        return response;
    }

    /**
     * 设置教师的用户信息
     *
     * @param user_id
     * @param name
     * @param gender
     * @param occupation 职业
     * @param seniority  教龄
     * @param courses    主要课程
     */
    public InformationResponse setTeacherInfo(String user_id, String name, String gender, String occupation, String seniority, String courses) throws Exception {
        InformationResponse response = new InformationResponse();
        String url = getURL(Constants.UPDATEINFORMATION);
        RequestParams params = getRequestParams();
        params.put("user_id", user_id);
        params.put("name", name);
        params.put("gender", gender);
        params.put("occupation", occupation);
        params.put("seniority", seniority);
        params.put("courses", courses);
        String result = httpManager.post(url, params);
        NLog.e(TAG, result);
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, InformationResponse.class);
        }
        return response;
    }

    /**
     * 设置机构的用户信息
     *
     * @param user_id
     * @param name           机构名
     * @param seniority      机构办学时长
     * @param courses        主要课程
     * @param teacher_amount 教师人数
     * @param average_age    平均教龄
     */
    public InformationResponse setOrganizationInfo(String user_id, String name, String seniority, String courses, String teacher_amount, String average_age) throws Exception {
        InformationResponse response = new InformationResponse();
        String url = getURL(Constants.UPDATEINFORMATION);
        RequestParams params = getRequestParams();
        params.put("user_id", user_id);
        params.put("name", name);
        params.put("seniority", seniority);
        params.put("courses", courses);
        params.put("teacher_amount", teacher_amount);
        params.put("average_age", average_age);
        String result = httpManager.post(url, params);
        NLog.e(TAG, result);
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, InformationResponse.class);
        }
        return response;
    }

    /**
     * 添加收藏
     *
     * @param user_id
     * @param byuser_id
     * @return
     * @throws Exception
     */
    public InformationResponse addAttentionInfo(String user_id, String byuser_id) throws Exception {
        InformationResponse response = new InformationResponse();
        String url = getURL(Constants.ADD_ATTENTION_INFO);
        RequestParams params = getRequestParams();
        params.put("user_id", user_id);
        params.put("byuser_id", byuser_id);
        String result = httpManager.post(url, params);
        NLog.e(TAG, result);
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, InformationResponse.class);
        }
        return response;
    }

    /**
     * 取消收藏
     *
     * @param user_id
     * @param byuser_id
     * @return
     * @throws Exception
     */
    public InformationResponse deleteAttentionInfo(String user_id, String byuser_id) throws Exception {
        InformationResponse response = new InformationResponse();
        String url = getURL(Constants.DELETE_ATTENTION_INFO);
        RequestParams params = getRequestParams();
        params.put("user_id", user_id);
        params.put("byuser_id", byuser_id);
        String result = httpManager.post(url, params);
        NLog.e(TAG, result);
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, InformationResponse.class);
        }
        return response;
    }

    /**
     * 无身份游客有的字段为
     * userid
     * name
     * gender
     * <p>
     * 教师有的字段为
     * userid
     * name
     * gender
     * age
     * occupation
     * seniority 教龄
     * courses
     * <p>
     * 机构
     * userid
     * name
     * seniority 机构办学时长
     * courses
     * teacher_amount
     * average_age
     */

//    URL：http://47.93.217.163:8080/app/queryCourseReleaseByWhere 	输入参数：condition
    public IndexDataResponse search(String condition) throws Exception {
        IndexDataResponse response = new IndexDataResponse();
        String url = getURL(Constants.SEARCH + "?condition=" + condition);
        String result = httpManager.get(url);
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, IndexDataResponse.class);
        }
        return response;
    }
}
