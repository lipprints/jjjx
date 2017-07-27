package com.jjjx.enums;


/**
 * Created by AMing on 17/7/27.
 * Compy RongCloud
 */

public enum UserRole {

    NOTHING(0, "0"),//无身份
    TEACHER(1, "1"),//教师
    ORGANIZATION(2, "2"),//机构
    VERIFICATION(3, "3")//正在审核
    ;


    UserRole(int i, String s) {

    }
}
