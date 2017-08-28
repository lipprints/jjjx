package com.jjjx.data.okhttp;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;


import com.jjjx.App;
import com.jjjx.Constants;
import com.jjjx.data.response.RequestRoleResponse;
import com.jjjx.model.MediaModel;
import com.jjjx.utils.CacheTask;
import com.jjjx.utils.NLog;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

import static com.baidu.location.h.j.F;
import static com.baidu.location.h.j.e;
import static com.jjjx.data.json.JsonMananger.jsonToBean;


/**
 * [OkHttpClient使用辅助类]
 *
 * @author devin.hu
 * @version 1.0
 * @date 2016-6-1
 **/
public class OkHttpUtils {

    public static final String TAG = OkHttpClient.class.getSimpleName();
    private static final int DEFAULT_CONNECT_TIMEOUT = 30 * 1000;
    private static final int DEFAULT_WRITE_TIMEOUT = 30 * 1000;
    private static final int DEFAULT_READ_TIMEOUT = 30 * 1000;

    private static OkHttpUtils instance;
    private OkHttpClient client;

    private String CER = "-----BEGIN CERTIFICATE-----\n" +
            "MIICUDCCAbmgAwIBAgIEJs69NTANBgkqhkiG9w0BAQsFADBaMQ0wCwYDVQQGEwQoQ04pMQ0wCwYD\n" +
            "VQQIEwQoQkopMQ0wCwYDVQQHEwQoQkopMQ0wCwYDVQQKEwQoU1MpMQ0wCwYDVQQLEwQoU1MpMQ0w\n" +
            "CwYDVQQDEwQoU1MpMCAXDTE2MTIwNzE0MzMyNloYDzIxMTYxMTEzMTQzMzI2WjBaMQ0wCwYDVQQG\n" +
            "EwQoQ04pMQ0wCwYDVQQIEwQoQkopMQ0wCwYDVQQHEwQoQkopMQ0wCwYDVQQKEwQoU1MpMQ0wCwYD\n" +
            "VQQLEwQoU1MpMQ0wCwYDVQQDEwQoU1MpMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCoVGN0\n" +
            "VNkRyOPgpK5QXHUhfiWWDtyztFCaRq2C8tBkIsDBV3XkC0yzb/5vQBLRnHgV3sEcrYHkBqZ4u0XQ\n" +
            "ZXpAm6Jd0jpn+CMXZbI4WxsjnjVFBZO8Mb+l0kVCCofssWpkOBtF0xT0A8yvzyHYK4ydjjAkq7ug\n" +
            "0MgwqSmh5eS4RQIDAQABoyEwHzAdBgNVHQ4EFgQUW8DIhb9EYLXFuEoHW1Dzyyy+z5gwDQYJKoZI\n" +
            "hvcNAQELBQADgYEAc4LoGujDA5WvQ5LK2kffpt8rIsHEX5Yi4gSukEEA+VXmg3RhxPk9mqNS7U54\n" +
            "En481mHpiO0/AfAmh17a3cbSDZ4BuRn/xkd/uJ05Tk4/C3aXdgDjE6LWNaCuhoSr3p4kzOOI811L\n" +
            "G4IsNLJZi6HzWlLhQV21WlWmbLKSl/aBWzE=\n" +
            "-----END CERTIFICATE-----";

    /**
     * 构造方法
     *
     * @return
     */
    private OkHttpUtils(Context context) {
        try {
            HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(new InputStream[]{new Buffer().writeUtf8(CER).inputStream()}, null, null);
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                    .writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                    .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    })
                    .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                    .cookieJar(new CookieJarImpl(new PersistentCookieStore(context)));
            client = builder.build();
        } catch (Exception ex) {

        }
    }

    /**
     * 获取单例instance
     *
     * @return
     */
    public static OkHttpUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (OkHttpUtils.class) {
                if (instance == null) {
                    instance = new OkHttpUtils(context);
                }
            }
        }
        return instance;
    }

    /**
     * 获取OkHttpClient
     *
     * @return
     */
    public OkHttpClient getOkHttpClient() {
        return client;
    }


    public String get(String url) throws Exception {
        Request request = new Request.Builder().get().url(url).build();
        return sendRequest(url, null, request);
    }


    public String get(String url, RequestParams params) throws Exception {
        url = params.getParamString(url);
        Request request = new Request.Builder().get().url(url).build();
        return sendRequest(url, params, request);
    }


    public String post(String url, RequestParams params) throws Exception {
        String tag = String.valueOf(url.hashCode());
        Request request = new Request.Builder().post(params.getRequestBody()).url(url).tag(tag).build();
        return sendRequest(url, params, request);
    }

    public String sendRequest(String url, RequestParams params, Request request) throws Exception {
        Response response = client.newCall(request).execute();
        String result = response.body().string();

        StringBuilder logbuilder = new StringBuilder();
        logbuilder.append("----------------------------------------------------------").append("\n");
        logbuilder.append("  method  : " + request.method()).append("\n");
        logbuilder.append("  request : " + url);
        if (request.method().equals("POST")) {
            logbuilder.append("?").append(params.toString());
        }
        logbuilder.append("\n");
        logbuilder.append("response :" + result).append("\n");
        NLog.e(TAG, logbuilder.toString());

        response.close();
        return result;
    }

    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static final MediaType MEDIA_TYPE_MP4 = MediaType.parse("video/mpeg4");

    public void uploadImage(String user_id, File file, final UploadImageListener uploadImageListener) {
        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
        multipartBodyBuilder.setType(MultipartBody.FORM);
        multipartBodyBuilder.addFormDataPart("user_id", user_id);
        multipartBodyBuilder.addFormDataPart("filename", file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file));
        final RequestBody requestBody = multipartBodyBuilder.build();
        Request.Builder RequestBuilder = new Request.Builder();
        RequestBuilder.url(Constants.DOMAIN + Constants.ADD_PIC);// 添加URL地址
        RequestBuilder.post(requestBody);
        Request request = RequestBuilder.build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, e.getMessage());
                if (uploadImageListener != null) {
                    uploadImageListener.onFailure(e);
                }

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    if (uploadImageListener != null) {
                        uploadImageListener.onSuccess(response.body().string());
                    }
                }
            }
        });

    }

    /**
     * 业务发布 包含一个视频上传、多图上传和其他发布信息上传
     *
     * @param user_id             当前用户 id
     * @param mediaModels         视频、图片文件
     * @param courseName          课程名
     * @param synopsis            课程简介
     * @param classFee            课时费
     * @param rightAge            适学年龄
     * @param teachingNumber      授课人数
     * @param teachingDate        上课时间
     * @param teachingAddress     上课地址
     * @param contactNumber       联系方式
     *                            lng 经度
     *                            lat 纬度
     * @param uploadImageListener 回调
     */
    public void publish(String user_id, List<MediaModel> mediaModels, String courseName, @Nullable String synopsis, String classFee, String rightAge, String teachingNumber, String teachingDate, String teachingAddress, String contactNumber, String lng, String lat, final UploadImageListener uploadImageListener) {
        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
        multipartBodyBuilder.setType(MultipartBody.FORM);
        multipartBodyBuilder.addFormDataPart("user_id", user_id);
        multipartBodyBuilder.addFormDataPart("courseName", courseName);
        if (!TextUtils.isEmpty(synopsis)) {
            multipartBodyBuilder.addFormDataPart("synopsis", synopsis);
        }
        multipartBodyBuilder.addFormDataPart("classFee", classFee);
        multipartBodyBuilder.addFormDataPart("rightAge", rightAge);
        multipartBodyBuilder.addFormDataPart("teachingNumber", teachingNumber);
        multipartBodyBuilder.addFormDataPart("teachingDate", teachingDate);
        multipartBodyBuilder.addFormDataPart("teachingAddress", teachingAddress);
        multipartBodyBuilder.addFormDataPart("contactNumber", contactNumber);
        multipartBodyBuilder.addFormDataPart("lng", lng);
        multipartBodyBuilder.addFormDataPart("lat", lat);

        for (int i = 0; i < mediaModels.size(); i++) {
            File file = mediaModels.get(i).getMediaFile();
            if (mediaModels.get(i).getType() == MediaModel.MediaType.IMAGE) {
                multipartBodyBuilder.addFormDataPart("files", file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file));
            } else {
                multipartBodyBuilder.addFormDataPart("files", file.getName(), RequestBody.create(MEDIA_TYPE_MP4, file));
                File videoImageFile = new File(mediaModels.get(i).getDisplayPicturePath());
                if (videoImageFile.exists()) {
                    multipartBodyBuilder.addFormDataPart("files", "videoImageFile" + System.currentTimeMillis() + ".png", RequestBody.create(MEDIA_TYPE_PNG, videoImageFile));
                }
            }
        }
        final RequestBody requestBody = multipartBodyBuilder.build();
        Request.Builder requestBuilder = new Request.Builder();

        requestBuilder.url(Constants.DOMAIN + Constants.ISSUE);// 添加URL地址
        requestBuilder.post(requestBody);
        Request request = requestBuilder.build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.e(TAG, e.getMessage());
                if (uploadImageListener != null) {
                    uploadImageListener.onFailure(e);
                }

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    if (uploadImageListener != null) {
                        uploadImageListener.onSuccess(response.body().string());

                    }
                }
            }
        });

    }

    public void findPublish(String user_id, List<MediaModel> mediaModels, String content, final UploadImageListener uploadImageListener) {
        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
        multipartBodyBuilder.setType(MultipartBody.FORM);
        multipartBodyBuilder.addFormDataPart("user_id", user_id);
        multipartBodyBuilder.addFormDataPart("content", content);

        for (int i = 0; i < mediaModels.size(); i++) {
            File file = mediaModels.get(i).getMediaFile();
            if (mediaModels.get(i).getType() == MediaModel.MediaType.IMAGE) {
                multipartBodyBuilder.addFormDataPart("files", file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file));
            } else {
                multipartBodyBuilder.addFormDataPart("files", file.getName(), RequestBody.create(MEDIA_TYPE_MP4, file));
                File videoImageFile = new File(mediaModels.get(i).getDisplayPicturePath());
                if (videoImageFile.exists()) {
                    multipartBodyBuilder.addFormDataPart("files", "videoImageFile" + System.currentTimeMillis() + ".png", RequestBody.create(MEDIA_TYPE_PNG, videoImageFile));
                }
            }
        }
        final RequestBody requestBody = multipartBodyBuilder.build();
        Request.Builder requestBuilder = new Request.Builder();

        requestBuilder.url(Constants.DOMAIN + Constants.FIND_PUBLISH);// 添加URL地址
        requestBuilder.post(requestBody);
        Request request = requestBuilder.build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.e(TAG, e.getMessage());
                if (uploadImageListener != null) {
                    uploadImageListener.onFailure(e);
                }

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    if (uploadImageListener != null) {
                        uploadImageListener.onSuccess(response.body().string());

                    }
                }
            }
        });

    }

    /**
     * 申请教师 或者 机构认证
     *
     * @param role
     * @return
     * @throws Exception
     */
    public void requestRole(String role, List<MediaModel> mediaModels, final UploadImageListener uploadImageListener) {
        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
        multipartBodyBuilder.setType(MultipartBody.FORM);
        multipartBodyBuilder.addFormDataPart("user_id", CacheTask.getInstance().getUserId());
        multipartBodyBuilder.addFormDataPart("role", role);

        for (int i = 0; i < mediaModels.size(); i++) {
            File file = mediaModels.get(i).getMediaFile();
            multipartBodyBuilder.addFormDataPart("files", file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file));

        }
        final RequestBody requestBody = multipartBodyBuilder.build();
        Request.Builder RequestBuilder = new Request.Builder();
        RequestBuilder.url(Constants.DOMAIN + Constants.AUTH_ROLE);// 添加URL地址
        RequestBuilder.post(requestBody);
        Request request = RequestBuilder.build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, e.getMessage());
                if (uploadImageListener != null) {
                    uploadImageListener.onFailure(e);
                }

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    if (uploadImageListener != null) {
                        uploadImageListener.onSuccess(response.body().string());
                    }
                }
            }
        });
    }

    /**
     * 单文件上传进度  RequestBody 自定义
     *
     * @param contentType
     * @param file
     * @param listener
     * @return
     * @link: http://blog.csdn.net/djk_dong/article/details/48179315
     */
    public static RequestBody createCustomRequestBody(final MediaType contentType, final File file, final ProgressListener listener) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return contentType;
            }

            @Override
            public long contentLength() {
                return file.length();
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source;
                try {
                    source = Okio.source(file);
                    //sink.writeAll(source);
                    Buffer buf = new Buffer();
                    Long remaining = contentLength();
                    for (long readCount; (readCount = source.read(buf, 2048)) != -1; ) {
                        sink.write(buf, readCount);
                        listener.onProgress(contentLength(), remaining -= readCount, remaining == 0);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    interface ProgressListener {
        void onProgress(long totalBytes, long remainingBytes, boolean done);
    }


    public interface UploadImageListener {
        void onSuccess(String result);

        void onFailure(IOException e);
    }


    /**
     * 根据Tag取消请求
     */
    public void cancelTag(Object tag) {
        for (Call call : getOkHttpClient().dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : getOkHttpClient().dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }
}

