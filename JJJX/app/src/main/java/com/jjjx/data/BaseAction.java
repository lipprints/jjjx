package com.jjjx.data;

import android.content.Context;
import android.text.TextUtils;

import com.jjjx.network.Constants;
import com.jjjx.data.cache.CacheManager;
import com.jjjx.data.json.JsonMananger;
import com.jjjx.data.okhttp.OkHttpUtils;
import com.jjjx.data.okhttp.RequestParams;
import com.jjjx.utils.CacheTask;

import java.io.IOException;
import java.util.List;

/**
 * Created by AMing on 17/5/7.
 * Company RongCloud
 */

public abstract class BaseAction {

    protected Context mContext;
    protected OkHttpUtils httpManager;

    /**
     * 缓存有效期5分钟
     **/
    protected final long INVALID_TIME = 5 * 60;
    /**
     * 缓存有效期30分钟
     **/
    protected final long INVALID_TIME_30MIN = 30 * 60;
    /**
     * 缓存有效期1小时
     **/
    protected final long INVALID_TIME_1HOURS = 60 * 60;
    /**
     * 缓存有效期1天
     **/
    protected final long INVALID_TIME_1DAY = 24 * 60 * 60;
    /**
     * 缓存有效期1周
     **/
    protected final long INVALID_TIME_1WEEK = 7 * 24 * 60 * 60;
    /**
     * 缓存有效期1个月
     **/
    protected final long INVALID_TIME_1MONTH = 30 * 24 * 60 * 60;


    /**
     * 构造方法
     *
     * @param context
     */
    public BaseAction(Context context) {
        this.mContext = context;
        this.httpManager = OkHttpUtils.getInstance(context);
        CacheManager.setSysCachePath(context.getCacheDir().getPath());
    }


    /**
     * JSON转JAVA对象方法
     *
     * @param json
     * @param cls
     * @return
     */
    public <T> T jsonToBean(String json, Class<T> cls) {
        T obj = JsonMananger.jsonToBean(json, cls);
        return obj;
    }

    /**
     * JSON转JAVA对象数组方法
     *
     * @param json
     * @param cls
     * @return
     * @throws IOException
     */
    public <T> List<T> jsonToList(String json, Class<T> cls) throws IOException {
        return JsonMananger.jsonToList(json, cls);
    }

    /**
     * JAVA对象转JSON方法
     *
     * @param obj
     * @return
     */
    public String beanTojson(Object obj) {
        return JsonMananger.beanToJson(obj);
    }

    /**
     * 获取处理后的RequestParams对象
     *
     * @return
     */
    public RequestParams getRequestParams() {
        RequestParams params = new RequestParams();
        params.put("jsoncallback", "result");
        params.put("devices", "android");
        params.put("version", "1.0");

        String access_token = CacheManager.readObject("access_token");
        if (!TextUtils.isEmpty(access_token)) {
            params.put("access_token", access_token);
        }
        return params;
    }

    public RequestParams getContainsUserIdRequestParams() {
        RequestParams params = new RequestParams();
        params.put("jsoncallback", "result");
        if (!TextUtils.isEmpty(CacheTask.getInstance().getUserId())) {
            params.put("uid", CacheTask.getInstance().getUserId());
        }
        return params;
    }

    /**
     * 获取处理后的RequestParams对象
     *
     * @param params
     * @return
     */
    public RequestParams getFinalParams(RequestParams params) {
        //TODO 这里处理公共参数，签名等操作
        return getFinalParams(params, true);
    }

    /**
     * 获取处理后的RequestParams对象
     *
     * @param params
     * @param isSign 是否签名
     * @return
     */
    public RequestParams getFinalParams(RequestParams params, boolean isSign) {
        //TODO 这里处理公共参数，如签名、加密等操作
        return params;
    }

    /**
     * 获取完整URL方法
     *
     * @param url
     * @return
     */
    protected String getURL(String url) {
        return getURL(url, new String[]{});
    }

    /**
     * 获取完整URL方法
     *
     * @param url
     * @param params
     * @return
     */
    protected String getURL(String url, String... params) {
        StringBuilder builder = new StringBuilder();
        builder.append(Constants.DOMAIN);
        builder.append(url);
        if (params != null) {
            for (String param : params) {
                if (!builder.toString().endsWith("/")) {
                    builder.append("/");
                }
                builder.append(param);
            }
        }
        return builder.toString();
    }

    /**
     * 获取图片参数
     *
     * @return
     */
    protected String getImagesParam(List<String> imglist) {
        StringBuilder imgBuilder = new StringBuilder();
        if (imglist != null && imglist.size() > 0) {
            for (int i = 0; i < imglist.size(); i++) {
                imgBuilder.append(imglist.get(i));
                if (i < imglist.size() - 1) {
                    imgBuilder.append(",");
                }
            }
        }
        return imgBuilder.toString();
    }
}
