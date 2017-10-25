package com.jjjx.data.okhttp;

import android.text.TextUtils;
import android.util.Log;


import com.jjjx.utils.NLog;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * [日志拦截类]
 *
 * @author devin.hu
 * @version 1.0
 * @date 2016-6-1
 *
 **/
public class LogInterceptor implements Interceptor {

    public static final String TAG = OkHttpClient.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        beforeRequest(request);
        Response response = chain.proceed(request);
        return beforeResponse(response);
    }

    private void beforeRequest(Request request) {
        try {
            String url = request.url().toString();
            Headers headers = request.headers();

            NLog.e(TAG, "---------------------request start---------------------");
            NLog.e(TAG, "method : " + request.method() + " url : " + url);

            if (headers != null && headers.size() > 0) {
                NLog.e(TAG, "headers : \n");
                NLog.e(TAG, headers.toString());
            }

            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    NLog.e(TAG, "contentType : " + mediaType.toString());
                    if (isText(mediaType)) {
                        NLog.e(TAG, "content : " + bodyToString(request));
                    } else {
                        NLog.e(TAG, "content : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            NLog.e(TAG, "---------------------request end-----------------------");
        }
    }

    private Response beforeResponse(Response response) {
        try {
            NLog.e(TAG, "---------------------response start---------------------");
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            NLog.e(TAG, "url : " + clone.request().url());
            NLog.e(TAG, "code : " + clone.code());
            NLog.e(TAG, "protocol : " + clone.protocol());
            if (!TextUtils.isEmpty(clone.message())) {
                Log.e(TAG, "message : " + clone.message());
            }

            ResponseBody body = clone.body();
            if (body != null) {
                MediaType mediaType = body.contentType();
                if (mediaType != null) {
                    NLog.e(TAG, "contentType : " + mediaType.toString());
                    if (isText(mediaType)) {
                        String resp = body.string();
                        NLog.e(TAG, "content : " + resp);
                        body = ResponseBody.create(mediaType, resp);
                        return response.newBuilder().body(body).build();
                    } else {
                        NLog.e(TAG, "content : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            NLog.e(TAG, "---------------------response end-----------------------");
        }

        return response;
    }

    private boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && "text".equals(mediaType.type())) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if ("json".equals(mediaType.subtype()) ||
                    "xml".equals(mediaType.subtype()) ||
                    "html".equals(mediaType.subtype()) ||
                    "webviewhtml".equals(mediaType.subtype())) //
            {
                return true;
            }
        }
        return false;
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }
}
