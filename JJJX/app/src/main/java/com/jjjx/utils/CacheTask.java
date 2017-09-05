package com.jjjx.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.regex.Pattern;

import static com.baidu.location.h.j.p;
import static io.rong.imlib.statistics.UserData.name;

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


    public void cacheName(String name) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("name", name);
        editor.apply();
    }

    public void cacheLoginLng(String lng) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("lng", lng);
        editor.apply();
    }

    public void cacheLoginLat(String lat) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("lat", lat);
        editor.apply();
    }

    public String getLoginLng() {
        return preferences.getString("lng", null);
    }

    public String getLoginLat() {
        return preferences.getString("lat", null);
    }

    public String getName() {
        return preferences.getString("name", null);
    }

    public void cacheSex(String sex) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("sex", sex);
        editor.apply();
    }

    public void cacheCity(String city) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("city", city);
        editor.apply();
    }

    public String getCity() {
        return preferences.getString("city", null);
    }

    public String getSex() {
        return preferences.getString("sex", null);
    }

    public void cachePortrait(String portrait) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("portrait", portrait);
        editor.apply();
    }

    public String getPortrait() {
        return preferences.getString("portrait", null);
    }


    public void cacheLearnYear(String learnYear) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("learnYear", learnYear);
        editor.apply();
    }

    public String getLearnYear() {
        return preferences.getString("learnYear", null);
    }

    public void cacheMainClass(String mainClass) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("mainClass", mainClass);
        editor.apply();
    }

    public String getMainClass() {
        return preferences.getString("mainClass", null);
    }

    public void cacheTeacherAmount(String teacherAmount) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("teacherAmount", teacherAmount);
        editor.apply();
    }

    public String getAeacherAmount() {
        return preferences.getString("teacherAmount", null);
    }

    public void cacheAverageAge(String averageAge) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("averageAge", averageAge);
        editor.apply();
    }

    public String getAverageAge() {
        return preferences.getString("averageAge", null);
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

    public void cacheRole(String role) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userRole", role);
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

    public String getUserRole() {
        return preferences.getString("userRole", "1");
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
        preferences.edit().remove("userRole").apply();
        preferences.edit().remove("name").apply();
        preferences.edit().remove("sex").apply();
        preferences.edit().remove("portrait").apply();
        preferences.edit().remove("averageAge").apply();
        preferences.edit().remove("teacherAmount").apply();
    }
}
