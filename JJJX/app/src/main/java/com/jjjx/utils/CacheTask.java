package com.jjjx.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * Created by AMing on 17/5/12.
 * Company RongCloud
 */

public class CacheTask {
    private final static String JX_SP = "JxSp";
    private SharedPreferences preferences;
    private boolean isCacheValid;

    private static class SingleTonHolder {
        private static CacheTask sIns = new CacheTask();
    }

    /**
     * 获取实例
     *
     * @return
     */
    public static CacheTask getInstance() {
        return SingleTonHolder.sIns;
    }

    private CacheTask() {

    }

    public void init(Context context) {
        preferences = context.getApplicationContext().getSharedPreferences(JX_SP, Context.MODE_PRIVATE);
    }


    /**
     * 保存登录基本信息
     *
     * @param userId   用户 id
     * @param account  账号
     * @param password 密码
     * @param token    token
     */
    public void cacheLoginInfo(@NonNull String userId, @NonNull String account, @NonNull String password, @NonNull String token) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userId", userId);
        editor.putString("account", account);
        editor.putString("password", password);
        editor.putString("token", token);
        editor.apply();
    }

    /**
     * 保存 token 到 SharedPreferences
     *
     * @param token
     */
    public void cacheToken(@NonNull String token) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token", token);
        editor.apply();
    }

    public void cacheUserId(@NonNull String userId) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userId", userId);
        editor.apply();
    }

    public void cacheAccount(@NonNull String account) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("account", account);
        editor.apply();
    }

    public void cachePwd(@NonNull String password) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("password", password);
        editor.apply();
    }

    public boolean isLogin() {
        return (!TextUtils.isEmpty(preferences.getString("userId", "")));
    }

    /**
     * 获取用户 id
     *
     * @return
     */
    public String getUserId() {
        return preferences.getString("userId", null);
    }

    /**
     * 获取 token
     *
     * @return
     */
    public String getToken() {
        return preferences.getString("token", null);
    }

    /**
     * 获取账号
     *
     * @return
     */
    public String getAccount() {
        return preferences.getString("account", null);
    }

    /**
     * 获取密码
     *
     * @return
     */
    public String getPassword() {
        return preferences.getString("password", null);
    }

    /**
     * 清除 SharedPreferences
     */
    public void clearAllCache() {
        preferences.edit().remove("userId").apply();
        preferences.edit().remove("token").apply();
        preferences.edit().remove("account").apply();
        preferences.edit().remove("password").apply();
    }
}
