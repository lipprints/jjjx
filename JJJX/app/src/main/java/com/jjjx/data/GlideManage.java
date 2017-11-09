package com.jjjx.data;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.jjjx.app.App;

/**
 * Created by xcoder_xz on 2017/5/3 0003.
 * 对glide的简单封转
 */

public class GlideManage {

    private RequestManager requestManager;

    public RequestManager getRequestManager() {
        if (requestManager == null) {
            requestManager = Glide.with(App.applicationContext);
        }
        return requestManager;
    }

    public GlideManage(Context context) {
        requestManager = Glide.with(context);
    }

    public GlideManage(Activity activity) {
        requestManager = Glide.with(activity);
    }

    /**
     * 传入v4包的fragment
     *
     * @param fragment
     */

    public GlideManage(android.support.v4.app.Fragment fragment) {
        requestManager = Glide.with(fragment);
    }


    /**
     * 传入普通包的framgent
     *
     * @param fragment
     */
    public GlideManage(android.app.Fragment fragment) {
        requestManager = Glide.with(fragment);
    }

    public GlideManage(FragmentActivity fragmentActivity) {
        requestManager = Glide.with(fragmentActivity);
    }


}
