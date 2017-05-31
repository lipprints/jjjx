package com.jjjx.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

public class SystemUtils {
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context
                                           .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfos = mActivityManager.getRunningAppProcesses();
        if(runningAppProcessInfos == null){
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo appProcess : runningAppProcessInfos) {
            if (appProcess.pid == pid) {

                return appProcess.processName;
            }
        }
        return null;
    }
}
