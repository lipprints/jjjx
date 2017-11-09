package com.jjjx.widget.popup;

import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.widget.PopupWindow;

/**
 * @author xz
 * @date 2017/11/7 0007
 * 解决7.0的popup 位置错误问题
 */

public class NsdkPopupWindow extends PopupWindow {

    public NsdkPopupWindow() {
        super();
    }

    public NsdkPopupWindow(View contentView) {
        super(contentView);
    }

    public NsdkPopupWindow(int width, int height) {
        super(width,height);
    }


    public NsdkPopupWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView,width,height,focusable);
    }

    public NsdkPopupWindow(View contentView, int width, int height) {
        super(contentView,width,height);
    }


        @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }
}
