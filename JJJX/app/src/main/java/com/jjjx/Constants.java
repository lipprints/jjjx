package com.jjjx;


/**
 * Created by AMing on 17/5/7.
 * Company RongCloud
 */

public class Constants {

    //TODO http://47.93.217.163:8080/app/authorize?role=1&user_id=100009 web 后台审核通过接口 18618268584

    public static final String DOMAIN = "http://47.93.217.163:8080/";

    /**
     * 参数:
     * <p>
     * name : mobile or email
     * <p>
     * pwd
     */
    public static final String LOGIN_URL = "app/login";
    /**
     * 参数:
     * <p>
     * flag : 0 注册 1 找回密码
     * <p>
     * mobile or email
     * <p>
     * pwd
     */
    public static final String REGIST_URL = "app/regist";
    /**
     * mobile or email
     */
    public static final String SEND_CODE = "app/checkCaptcha";

    public static final String GET_RONGCLOUD_TOKEN = "app/getToken";

    public static final String ADD_PIC = "app/addUserPic"; //修改头像

    public static final String AUTH_ROLE = "app/auth"; // role 申请

    public static final String UPDATEINFORMATION = "app/updateInformation"; //用户信息设置

    public static final String ISSUE = "app/issue"; //发布

    public static final String FIND_PUBLISH = "app/publish";

    public static final String INDEX_ALL = "app/queryAllCourseRelease"; //首页数据

    public static final String ADD_ATTENTION_INFO = "add/addAttentionInfo";

    public static final String DELETE_ATTENTION_INFO = "add/deleteAttentionInfo";

    public static final String SEARCH = "app/queryCourseReleaseByWhere";

}
