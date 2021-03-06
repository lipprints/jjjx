package com.jjjx.widget.popwinpicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jjjx.R;
import com.jjjx.widget.popup.NsdkPopupWindow;


/**
 * 自定义的带弹出框的按钮,类似于美团和大众点评的筛选框
 * Created by Chris on 2014/12/8.
 */
@SuppressLint("AppCompatCustomView")
public class PopupButton extends TextView implements PopupWindow.OnDismissListener {
    /**
     * 正常状态下的背景
     */
    private int normalBg;
    /**
     * 按下状态下的背景
     */
    private int pressBg;
    /**
     * 正常状态下的图标
     */
    private int normalIcon;

    /**
     * 按下状态下的图标
     */
    private int pressIcon;
    private NsdkPopupWindow popupWindow;
    private Context context;
    private int screenWidth;
    private int screenHeight;
    private int paddingTop;
    private int paddingLeft;
    private int paddingRight;
    private int paddingBottom;
    /**
     * 图标宽
     */
    private int iconWith;

    /**
     * 图标高
     */
    private int iconHeight;
    private PopupButtonListener listener;

    public PopupButton(Context context) {
        super(context);
        this.context = context;
    }

    public PopupButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAttrs(context, attrs);
        initBtn(context);
    }

    public PopupButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    //初始化各种自定义参数
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.popupbtn);

        normalBg = typedArray.getResourceId(R.styleable.popupbtn_normalBg, -1);
        pressBg = typedArray.getResourceId(R.styleable.popupbtn_pressBg, -1);
        normalIcon = typedArray.getResourceId(R.styleable.popupbtn_normalIcon, -1);
        pressIcon = typedArray.getResourceId(R.styleable.popupbtn_pressIcon, -1);

        iconWith = typedArray.getDimensionPixelOffset(R.styleable.popupbtn_iconWith, -1);
        iconHeight = typedArray.getDimensionPixelOffset(R.styleable.popupbtn_iconHeight, -1);
        typedArray.recycle();
    }


    /**
     * 初始话各种按钮样式
     */
    private void initBtn(final Context context) {
        paddingTop = this.getPaddingTop();
        paddingLeft = this.getPaddingLeft();
        paddingRight = this.getPaddingRight();
        paddingBottom = this.getPaddingBottom();
        setNormal();

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
        screenHeight = wm.getDefaultDisplay().getHeight();

    }

    /**
     * 隐藏弹出框
     */
    public void hidePopup() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    /**
     * 设置自定义接口
     *
     * @param listener
     */
    public void setListener(PopupButtonListener listener) {
        this.listener = listener;
    }

    /**
     * 设置popupwindow的view
     *
     * @param view
     */
    public void setPopupView(final View view) {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow == null) {
                    LinearLayout layout = new LinearLayout(context);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (screenHeight * 0.6));
                    view.setLayoutParams(params);
                    layout.addView(view);
                    layout.setBackgroundColor(Color.argb(60, 0, 0, 0));
                    popupWindow = new NsdkPopupWindow(layout, screenWidth, screenHeight);
                    popupWindow.setFocusable(true);
                    popupWindow.setBackgroundDrawable(new BitmapDrawable());
                    popupWindow.setOutsideTouchable(true);
                    popupWindow.setOnDismissListener(PopupButton.this);
                    layout.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });
                }
                if (listener != null) {
                    listener.onShow();
                }
                setPress();
                popupWindow.showAsDropDown(PopupButton.this);
            }
        });
    }




    /**
     * 设置选中时候的按钮状态
     */
    private void setPress() {
        if (pressBg != -1) {
            this.setBackgroundResource(pressBg);
            this.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        }
        if (pressIcon != -1) {
            Drawable drawable = ContextCompat.getDrawable(getContext(), pressIcon);
            /// 这一步必须要做,否则不会显示.
            if (iconHeight != -1 && iconWith != -1) {
                drawable.setBounds(0, 0, iconWith, iconHeight);
            } else {
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            }
            this.setCompoundDrawables(null, null, drawable, null);
        }
    }

    /**
     * 设置正常模式下的按钮状态
     */
    private void setNormal() {
        if (normalBg != -1) {
            this.setBackgroundResource(normalBg);
            this.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        }
        if (normalIcon != -1) {
            Drawable drawable = ContextCompat.getDrawable(getContext(), normalIcon);
            /// 这一步必须要做,否则不会显示.
            if (iconHeight != -1 && iconWith != -1) {
                drawable.setBounds(0, 0, iconWith, iconHeight);
            } else {
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            }
            this.setCompoundDrawables(null, null, drawable, null);
        }
    }

    @Override
    public void onDismiss() {
        setNormal();
        if (listener != null) {
            listener.onHide();
        }
    }
}
