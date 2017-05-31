package com.jjjx;

import android.support.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.jjjx.utils.CacheTask;
import com.jjjx.utils.SystemUtils;

import io.rong.imkit.RongIM;

/**
 * Created by AMing on 17/5/1.
 * Company RongCloud
 */

public class App extends MultiDexApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        if (getApplicationInfo().packageName.equals(SystemUtils.getCurProcessName(this))) {
            RongIM.init(this);
            CacheTask.getInstance().init(this);
            Fresco.initialize(this);
        }
    }
}
