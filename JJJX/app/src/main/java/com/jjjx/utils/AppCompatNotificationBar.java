package com.jjjx.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by xcoder_xz on 2017/5/3 0003.
 * 对状态栏，做一些不可描述的事情
 */

public class AppCompatNotificationBar {


    /**
     * 设置界面全屏，透明通知栏
     * 在华为和一些手机上面会导致界面的底部跟按钮重合
     *
     * @param context
     */
    public void transparentNotification(Context context) {
        if (Build.VERSION.SDK_INT > 16) {
//            ((Activity) context).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                    | View.SYSTEM_UI_LAYOUT_FLAGS | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
//            ((Activity) context).getWindow().getDecorView().setFitsSystemWindows(true);
//
//
//            ((Activity) context).getWindow().requestFeature(Window.FEATURE_NO_TITLE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = ((Activity) context).getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                window.setNavigationBarColor(Color.TRANSPARENT);
            }
        }
    }


    /**
     * 设置界面全屏，透明通知栏
     * 在华为和一些手机上面会导致界面的底部跟按钮重合
     *
     * @param activity
     */
    public void transparentNotification(Activity activity) {
        if (Build.VERSION.SDK_INT > 16) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_LAYOUT_FLAGS | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
            activity.getWindow().getDecorView().setFitsSystemWindows(true);
        }
    }

    /**
     * 设置状态栏颜色
     *
     * @param barColor
     */
    public static void setNotificationBarColor(@ColorRes int barColor,Activity activity) {
        //当手机是6.0+的手机时
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = activity.getWindow();
            //   window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(ContextCompat.getColor(activity.getApplicationContext(), barColor));
        }
    }

    /**
     * @param barColor     状态栏颜色
     * @param barTextColor 是否设置成暗色
     * @param activity
     */
    public void setNotificationBarColor(@ColorRes int barColor, Boolean barTextColor, Activity activity) {
        //当手机是6.0+的手机时
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            Window window = activity.getWindow();
            //   window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(ContextCompat.getColor(activity.getApplicationContext(), barColor));
            //
            //设置导航栏颜色android:
            //   window.setNavigationBarColor(((Activity) context).getResources().getColor(R.color.green));

            //设置字体颜色
            if (barTextColor) {
                //设置状态栏的文字颜色为暗色
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                // ((Activity) context).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                //设置状态栏的文字颜色为浅色windowLightStatusBar
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                // ((Activity) context).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }


    /**
     * @param barColor     状态栏颜色
     * @param barTextColor 是否设置成暗色
     * @param context
     */
    public void setNotificationBarColor(@ColorRes int barColor, Boolean barTextColor, Context context) {
        //当手机是6.0+的手机时
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            Window window = ((Activity) context).getWindow();
            //   window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(ContextCompat.getColor(context.getApplicationContext(), barColor));
            //
            //设置导航栏颜色android:
            //   window.setNavigationBarColor(((Activity) context).getResources().getColor(R.color.green));

            //设置字体颜色
            if (barTextColor) {
                //设置状态栏的文字颜色为暗色
                ((Activity) context).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                // ((Activity) context).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                //设置状态栏的文字颜色为浅色windowLightStatusBar
                ((Activity) context).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                // ((Activity) context).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }
}
