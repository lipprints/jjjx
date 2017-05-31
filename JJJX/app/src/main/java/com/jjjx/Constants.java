package com.jjjx;

/**
 * Created by AMing on 17/5/7.
 * Company RongCloud
 */

public class Constants {

    public static final String DOMAIN = "http://47.93.217.163:8080/";

    /**
     *
     * 参数:
     *
     * name : mobile or email
     *
     * pwd
     */
    public static final String LOGIN_URL = "app/login";
    /**
     *
     * 参数:
     *
     * flag : 0 注册 1 找回密码
     *
     * mobile or email
     *
     * pwd
     *
     */
    public static final String REGIST_URL = "app/regist";
    /**
     *
     * mobile or email
     */
    public static final String SEND_CODE = "app/checkCaptcha";

    public static final String GET_RONGCLOUD_TOKEN = "app/getToken";

    public static final String ADD_PIC = "app/addUserPic";

//    授权接口：http://localhost:8080/app/authorize?role=2&user_id=100008
}
