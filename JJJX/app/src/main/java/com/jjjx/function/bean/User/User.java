package com.jjjx.function.bean.User;

/**
 * Created by AMing on 17/7/27.
 * Company RongCloud
 */

public class User {
    protected String userId;
    protected String name;
    protected String portraitUrl;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }
}
