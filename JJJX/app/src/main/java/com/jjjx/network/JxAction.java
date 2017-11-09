package com.jjjx.network;

import android.content.Context;
import android.text.TextUtils;

import com.jjjx.data.BaseAction;
import com.jjjx.data.okhttp.RequestParams;
import com.jjjx.data.response.AddCommentResponse;
import com.jjjx.data.response.AttentionInfoListResponse;
import com.jjjx.data.response.CommentListResponse;
import com.jjjx.data.response.FindDataResponse;
import com.jjjx.data.response.GetRongCloudTokenResponse;
import com.jjjx.data.response.GetVerifyCodeResponse;
import com.jjjx.data.response.IndexDataResponse;
import com.jjjx.data.response.InformationResponse;
import com.jjjx.data.response.LoginResponse;
import com.jjjx.data.response.RegisterResponse;
import com.jjjx.data.response.RequestRoleResponse;
import com.jjjx.data.response.UserProfileResponse;
import com.jjjx.utils.AMUtils;
import com.jjjx.utils.CacheTask;
import com.orhanobut.logger.Logger;

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
        params.put("city", CacheTask.getInstance().getCity());
        String result = httpManager.post(url, params);
        if (!TextUtils.isEmpty(result)) {
            Logger.json(result);
            response = jsonToBean(result, LoginResponse.class);
        }
        return response;
    }

    /**
     * 注册
     *
     * @param flag        0 注册 1 找回密码
     * @param account     邮箱或者手机号
     * @param pwd 密码
     * @param pwdAgain 确认密码
     * @param sms_captcha 验证码
     * @return
     * @throws Exception
     */
    public RegisterResponse register(String flag, String account, String pwd,String pwdAgain, String sms_captcha) throws Exception {
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
        params.put("repass", pwdAgain);
        params.put("sms_captcha", sms_captcha);
        String result = httpManager.post(url, params);
        if (!TextUtils.isEmpty(result)) {
            Logger.json(result);
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
            Logger.json(result);
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
            Logger.json(result);
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
        if (!TextUtils.isEmpty(result)) {
            Logger.json(result);
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
    public IndexDataResponse requestIndexData(int page) throws Exception {
        IndexDataResponse response = new IndexDataResponse();
        String url;
        if (TextUtils.isEmpty(CacheTask.getInstance().getUserId())) {
            url = getURL(Constants.INDEX_ALL + "?page=" + page + "&lng2=" + CacheTask.getInstance().getLoginLng() + "&lat2=" + CacheTask.getInstance().getLoginLat());
        } else {
            url = getURL(Constants.INDEX_ALL + "?page=" + page + "&lng2=" + CacheTask.getInstance().getLoginLng() + "&lat2=" + CacheTask.getInstance().getLoginLat() + "&user_id=" + CacheTask.getInstance().getUserId());
        }
        String result = httpManager.get(url);
        if (!TextUtils.isEmpty(result)) {
            Logger.json(result);
            response = jsonToBean(result, IndexDataResponse.class);
        }
        return response;
    }
//    user_id,name ,gender 男女,age 年龄,occupation 职业,seniority 教龄,courses 课程,teacher_amount 教师人数,average_age 平均年龄

    /**
     * 首页教龄
     *
     * @param page
     * @return
     * @throws Exception
     */
    public IndexDataResponse requestSortBySeniority(int page) throws Exception {
        IndexDataResponse response = new IndexDataResponse();
        String url = getURL(Constants.SORT_BY_SENIORITY + "?page=" + page + "&lng2=" + CacheTask.getInstance().getLoginLng() + "&lat2=" + CacheTask.getInstance().getLoginLat());
        String result = httpManager.get(url);
        if (!TextUtils.isEmpty(result)) {
            Logger.json(result);
            response = jsonToBean(result, IndexDataResponse.class);
        }
        return response;
    }


    /**
     * 首页课次低到高
     *
     * @param page
     * @return
     * @throws Exception
     */
    public IndexDataResponse requestSortByClassFeeASC(int page) throws Exception {
        IndexDataResponse response = new IndexDataResponse();
        String url = getURL(Constants.SORT_BY_CLASS_FEE_ASC + "?page=" + page + "&lng2=" + CacheTask.getInstance().getLoginLng() + "&lat2=" + CacheTask.getInstance().getLoginLat());
        String result = httpManager.get(url);
        if (!TextUtils.isEmpty(result)) {
            Logger.json(result);
            response = jsonToBean(result, IndexDataResponse.class);
        }
        return response;
    }

    /**
     * 首页课次高到低
     *
     * @param page
     * @return
     * @throws Exception
     */
    public IndexDataResponse requestSortByClassFeeDESC(int page) throws Exception {
        IndexDataResponse response = new IndexDataResponse();
        String url = getURL(Constants.SORT_BY_CLASS_FEE_DESC + "?page=" + page + "&lng2=" + CacheTask.getInstance().getLoginLng() + "&lat2=" + CacheTask.getInstance().getLoginLat());
        String result = httpManager.get(url);
        if (!TextUtils.isEmpty(result)) {
            Logger.json(result);
            response = jsonToBean(result, IndexDataResponse.class);
        }
        return response;
    }

    /**
     * 筛选老师
     *
     * @param page
     * @return
     * @throws Exception
     */
    public IndexDataResponse requestChoiceToTeacher(int page) throws Exception {
        IndexDataResponse response = new IndexDataResponse();
        String url = getURL(Constants.CHOICE_TO_TEACHER + "?page=" + page + "&lng2=" + CacheTask.getInstance().getLoginLng() + "&lat2=" + CacheTask.getInstance().getLoginLat());
        String result = httpManager.get(url);
        if (!TextUtils.isEmpty(result)) {
            Logger.json(result);
            response = jsonToBean(result, IndexDataResponse.class);
        }
        return response;
    }

    /**
     * 筛选学校
     *
     * @param page
     * @return
     * @throws Exception
     */
    public IndexDataResponse requestChoiceToSchool(int page) throws Exception {
        IndexDataResponse response = new IndexDataResponse();
        String url = getURL(Constants.CHOICE_TO_SCHOOL + "?page=" + page + "&lng2=" + CacheTask.getInstance().getLoginLng() + "&lat2=" + CacheTask.getInstance().getLoginLat());
        String result = httpManager.get(url);
        if (!TextUtils.isEmpty(result)) {
            Logger.json(result);
            response = jsonToBean(result, IndexDataResponse.class);
        }
        return response;
    }

    /**
     * 获取我的课程数据列表
     *
     * @param user_id
     * @return
     * @throws Exception
     */
    public IndexDataResponse getClassManageList(String user_id) throws Exception {
        IndexDataResponse response = new IndexDataResponse();
        String url = getURL(Constants.MANAGE_FOR_COURSE + "?user_id=" + user_id);
        String result = httpManager.get(url);
        if (!TextUtils.isEmpty(result)) {
            Logger.json(result);
            response = jsonToBean(result, IndexDataResponse.class);
        }
        return response;
    }

    /**
     * 根据课程 id 删除课程
     *
     * @param id
     * @return
     * @throws Exception
     */
    public InformationResponse deleteCourseById(String id) throws Exception {
        InformationResponse response = new InformationResponse();
        String url = getURL(Constants.DELETE_COURSE_BY_ID + "?id=" + id);
        String result = httpManager.get(url);
        if (!TextUtils.isEmpty(result)) {
            Logger.json(result);
            response = jsonToBean(result, InformationResponse.class);
        }
        return response;
    }

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
        if (!TextUtils.isEmpty(result)) {
            Logger.json(result);
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
        if (!TextUtils.isEmpty(result)) {
            Logger.json(result);
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
        if (!TextUtils.isEmpty(result)) {
            Logger.json(result);
            response = jsonToBean(result, InformationResponse.class);
        }
        return response;
    }

    /**
     * 关注用户
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
        if (!TextUtils.isEmpty(result)) {
            Logger.json(result);
            response = jsonToBean(result, InformationResponse.class);
        }
        return response;
    }

    /**
     * 取消关注用户
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
        if (!TextUtils.isEmpty(result)) {
            Logger.json(result);
            response = jsonToBean(result, InformationResponse.class);
        }
        return response;
    }

    /**
     * 我关注的列表
     *
     * @return
     * @throws Exception
     */
    public AttentionInfoListResponse getMyAttentionInfoList() throws Exception {
        AttentionInfoListResponse response = new AttentionInfoListResponse();
        String url = getURL(Constants.QUERY_MY_ATTENTIONINFO + "?user_id=" + CacheTask.getInstance().getUserId());
        String result = httpManager.get(url);
        if (!TextUtils.isEmpty(result)) {
            Logger.json(result);
            response = jsonToBean(result, AttentionInfoListResponse.class);
        }
        return response;
    }

    /**
     * 点赞
     *
     * @param user_id
     * @param pid
     * @return
     * @throws Exception
     */
    public InformationResponse addLike(String user_id, String pid) throws Exception {
        InformationResponse response = new InformationResponse();
        String url = getURL(Constants.ADD_LIKE);
        RequestParams params = getRequestParams();
        params.put("user_id", user_id);
        params.put("pid", pid);
        String result = httpManager.post(url, params);
        if (!TextUtils.isEmpty(result)) {
            Logger.json(result);
            response = jsonToBean(result, InformationResponse.class);
        }
        return response;
    }

    /**
     * 添加收藏
     *
     * @param user_id
     * @param course_id
     * @return
     * @throws Exception
     */
    public InformationResponse addCollection(String user_id, String course_id) throws Exception {
        InformationResponse response = new InformationResponse();
        String url = getURL(Constants.COLLECTION);
        RequestParams params = getRequestParams();
        params.put("user_id", user_id);
        params.put("course_id", course_id);
        String result = httpManager.post(url, params);
        if (!TextUtils.isEmpty(result)) {
            Logger.json(result);
            response = jsonToBean(result, InformationResponse.class);
        }
        return response;
    }


    /**
     * 取消收藏
     *
     * @param user_id
     * @param course_id
     * @return
     * @throws Exception
     */
    public InformationResponse deleteCollection(String user_id, String course_id) throws Exception {
        InformationResponse response = new InformationResponse();
        String url = getURL(Constants.COLLECTION_CANCEL);
        RequestParams params = getRequestParams();
        params.put("user_id", user_id);
        params.put("course_id", course_id);
        String result = httpManager.post(url, params);
        if (!TextUtils.isEmpty(result)) {
            Logger.json(result);
            response = jsonToBean(result, InformationResponse.class);
        }
        return response;
    }


    /**
     * 取消点赞
     *
     * @param user_id
     * @param pid
     * @return
     * @throws Exception
     */
    public InformationResponse cancelLike(String user_id, String pid) throws Exception {
        InformationResponse response = new InformationResponse();
        String url = getURL(Constants.CANCEL_LIKE);
        RequestParams params = getRequestParams();
        params.put("user_id", user_id);
        params.put("pid", pid);
        String result = httpManager.post(url, params);
        if (!TextUtils.isEmpty(result)) {
            Logger.json(result);
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
            Logger.json(result);
            response = jsonToBean(result, IndexDataResponse.class);
        }
        return response;
    }

    /**
     * 无需依赖登录的发现数据
     *
     * @param page
     * @return
     * @throws Exception
     */
    public FindDataResponse getFindData(int page) throws Exception {
        FindDataResponse response = new FindDataResponse();
        String url = getURL(Constants.FIND_DATA + "?page=" + page);
        String result = httpManager.get(url);
        if (!TextUtils.isEmpty(result)) {
            Logger.json(result);
            response = jsonToBean(result, FindDataResponse.class);
        }
        return response;
    }

    /**
     * 发现同城数据
     *
     * @param city
     * @param page
     * @return
     * @throws Exception
     */
    public FindDataResponse getFindByCity(String city, int page) throws Exception {
        FindDataResponse response = new FindDataResponse();
        String url = getURL(Constants.QUERY_FOR_CITY_WIDE + "?page=" + page + "&city=" + city);
        String result = httpManager.get(url);
        if (!TextUtils.isEmpty(result)) {
            Logger.json(result);
            response = jsonToBean(result, FindDataResponse.class);
        }
        return response;
    }

    /**
     * 需要依赖登录的发现数据
     *
     * @param page
     * @param user_id
     * @return
     * @throws Exception
     */
    public FindDataResponse getFindDataByUserId(int page, String user_id) throws Exception {
        FindDataResponse response = new FindDataResponse();
        String url = getURL(Constants.FIND_DATA_LOGIN + "?page=" + page + "&user_id=" + user_id);
        String result = httpManager.get(url);
        if (!TextUtils.isEmpty(result)) {
            Logger.json(result);
            response = jsonToBean(result, FindDataResponse.class);
        }
        return response;
    }

    /**
     * 获取我收藏的列表
     *
     * @return
     * @throws Exception
     */
    public IndexDataResponse getMyCollections() throws Exception {
        IndexDataResponse response = new IndexDataResponse();
        String url = getURL(Constants.MY_COLLECTION + "?user_id=" + CacheTask.getInstance().getUserId());
        String result = httpManager.get(url);
        if (!TextUtils.isEmpty(result)) {
            Logger.json(result);
            response = jsonToBean(result, IndexDataResponse.class);
        }
        return response;
    }


    /**
     * 查询用户详情接口 包含(1 用户信息 2 是否关注 3 发布的课程 4 发现)
     */
    public UserProfileResponse getUserProfile(String user_id) throws Exception {
        UserProfileResponse response = new UserProfileResponse();
        String url = getURL(Constants.GET_USER_PROFILE + "?user_id=" + CacheTask.getInstance().getUserId() + "&byuser_id=" + user_id);
        String result = httpManager.get(url);
        if (!TextUtils.isEmpty(result)) {
            Logger.json(result);
            response = jsonToBean(result, UserProfileResponse.class);
        }
        return response;
    }

    /**
     * 添加评论
     *
     * @param pid 发现数据的 id
     * @return
     * @throws Exception
     */
    public AddCommentResponse addComment(String pid, String content) throws Exception {
        AddCommentResponse response = new AddCommentResponse();
        String url = getURL(Constants.ADD_COMMENT);
        RequestParams params = getRequestParams();
        params.put("user_id", CacheTask.getInstance().getUserId());
        params.put("pid", pid);
        params.put("content", content);
        String result = httpManager.post(url, params);
        if (!TextUtils.isEmpty(result)) {
            Logger.json(result);
            response = jsonToBean(result, AddCommentResponse.class);
        }
        return response;
    }

    /**
     * 获取评论列表
     *
     * @param pid  发现数据的 id
     * @param page 分页
     * @return
     */
    public CommentListResponse getCommentList(String pid, String page) throws Exception {
        CommentListResponse response = new CommentListResponse();
        String url = getURL(Constants.GET_COMMENT_LIST);
        RequestParams params = getRequestParams();
        params.put("page", page);
        params.put("pid", pid);
        String result = httpManager.post(url, params);
        if (!TextUtils.isEmpty(result)) {
            Logger.json(result);
            response = jsonToBean(result, CommentListResponse.class);
        }
        return response;
    }

}
