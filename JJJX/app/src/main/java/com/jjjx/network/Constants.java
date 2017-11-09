package com.jjjx.network;


/**
 * Created by AMing on 17/5/7.
 * Company RongCloud
 */

public class Constants {

    //TODO http://47.93.217.163:8080/app/authorize?role=1&user_id=100009 web 后台审核通过接口 18618268584

    public static final String DOMAIN = "http://47.93.217.163:8080/";

    public static final String LOGIN_URL = "app/login";

    public static final String REGIST_URL = "app/regist";

    public static final String SEND_CODE = "app/checkCaptcha";

    public static final String GET_RONGCLOUD_TOKEN = "app/getToken";

    public static final String ADD_PIC = "app/addUserPic"; //修改头像

    public static final String AUTH_ROLE = "app/auth"; // role 申请

    public static final String UPDATEINFORMATION = "app/updateInformation"; //用户信息设置

    public static final String ISSUE = "app/issue"; //发布

    public static final String FIND_PUBLISH = "app/publish";

    public static final String INDEX_ALL = "app/queryAllCourseRelease"; //首页数据

    public static final String ADD_ATTENTION_INFO = "app/addAttentionInfo"; //关注

    public static final String DELETE_ATTENTION_INFO = "app/deleteAttentionInfo"; //取消关注

    public static final String QUERY_MY_ATTENTIONINFO = "app/queryMyAttentionInfo";

    public static final String SEARCH = "app/queryCourseReleaseByWhere";

    public static final String FIND_DATA = "app/queryDiscoverAllUnload"; //未登录的发现热门数据
    public static final String FIND_DATA_LOGIN = "app/queryDiscoverAll"; //登录后的

    public static final String ADD_LIKE = "app/thumbUp";//点赞
    public static final String CANCEL_LIKE = "app/thumbUpCancel";//取消点赞

    public static final String COLLECTION = "app/collectCourse"; //收藏课程
    public static final String COLLECTION_CANCEL = "app/collectCourseCancel"; //取消收藏课程

    public static final String MY_COLLECTION = "app/queryMyCollections"; //我收藏的课程列表

    public static final String GET_USER_PROFILE = "app/queryUserAllDiscover"; //查询用户详情 包含(1 用户信息 2 是否关注 3 发布的课程 4 发现)

    public static final String SORT_BY_SENIORITY = "app/sortBySeniority"; //教龄排序

    public static final String SORT_BY_CLASS_FEE_ASC = "app/sortByClassFeeASC"; //课次价格又低到高

    public static final String SORT_BY_CLASS_FEE_DESC = "app/sortByClassFeeDESC"; //课次价格又高到低

    public static final String CHOICE_TO_TEACHER = "app/choiceToTeacher"; //筛选教师

    public static final String CHOICE_TO_SCHOOL = "app/choiceToSchool";//筛选学校

    public static final String MANAGE_FOR_COURSE = "app/manageForCourse" ; // 我的课程列表

    public static final String DELETE_COURSE_BY_ID = "app/deleteCourseById"; //根据课程id删除课程

    public static final String QUERY_FOR_CITY_WIDE = "app/queryForCityWide"; //发现同城

    public static final String ADD_COMMENT = "app/addComment";

    public static final String GET_COMMENT_LIST = "app/getCommentList";

    /**
     * http://47.93.217.163:8080/app/sortByClassFeeASC?page=0
     * http://47.93.217.163:8080/app/sortBySeniority?page=0
     * http://47.93.217.163:8080/app/sortByClassFeeDESC 
     * http://47.93.217.163:8080/app/choiceToTeacher?page=0
     * http://47.93.217.163:8080/app/choiceToSchool 
     * http://47.93.217.163:8080/app/manageForCourse 
     * http://47.93.217.163:8080/app/deleteCourseById?id=100047
     * http://47.93.217.163:8080/app/queryForCityWide?city=北京
     * http://47.93.217.163:8080/app/queryMyAttentionInfo?user_id=100005
     */

}
