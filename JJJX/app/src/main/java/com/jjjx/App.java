package com.jjjx;

import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClientOption;
import com.baidu.location.service.LocationService;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.jjjx.utils.CacheTask;
import com.jjjx.utils.SystemUtils;


import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;

/**
 * Created by AMing on 17/5/1.
 * Company RongCloud
 */

public class App extends MultiDexApplication implements BDLocationListener {

    public LocationService locationService;
    private List<OnBDLocationListener> listenerList;
    public static volatile Context applicationContext;
    public static volatile Handler applicationHandler;


    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
        applicationHandler = new Handler(applicationContext.getMainLooper());
        if (getApplicationInfo().packageName.equals(SystemUtils.getCurProcessName(this))) {
            instance = this;
            listenerList = new ArrayList<>();
            RongIM.init(this);
            CacheTask.getInstance().init(this);
            Fresco.initialize(this);
            initBDLocationObserver();
        }
    }


    private void initBDLocationObserver() {
        locationService = new LocationService(getApplicationContext());
        locationService.registerListener(this);
        LocationClientOption locationClientOption = locationService.getDefaultLocationClientOption();
        locationService.setLocationOption(locationClientOption);
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        Log.e("APP", "bdLocation"); //30秒定位一次
        for (OnBDLocationListener listener : listenerList) {
            listener.onLocation(bdLocation);
        }
    }

    @Override
    public void onConnectHotSpotMessage(String s, int i) {

    }

    public void addOnBDLocationObserver(OnBDLocationListener onBDLocationListener) {
        this.listenerList.add(onBDLocationListener);
    }

    public void removeOnBDLocationObserver(OnBDLocationListener onBDLocationListener) {
        this.listenerList.remove(onBDLocationListener);
    }

    public void stopLocationObserver() {
        if (locationService != null) {
            locationService.stop();
        }
    }

    public void startLocationObserver() {
        if (locationService != null) {
            locationService.start();
        }
    }

    private static App instance;

    public static App getInstance() {
        return instance;
    }
}
