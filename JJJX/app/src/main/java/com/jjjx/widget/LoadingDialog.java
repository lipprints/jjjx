
package com.jjjx.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jjjx.R;


public class LoadingDialog {
    private ProgressDialog mProgressDialog;
    private boolean mCancellable;
    private String detailLabel;

    private LoadingDialog(Context context) {
        mProgressDialog = new ProgressDialog(context);
    }

    public static LoadingDialog create(Context context) {
        return new LoadingDialog(context);
    }

    /**
     * 设置加载状态描述
     *
     * @param detailLabel
     */
    public LoadingDialog setDetailLabel(String detailLabel) {
        this.detailLabel = detailLabel;
        return this;
    }

    public LoadingDialog setCancellable(boolean isCancellable) {
        mCancellable = isCancellable;
        return this;
    }

    public LoadingDialog show() {
        if (!isShowing()) {
            mProgressDialog.show();
        }
        return this;
    }

    public boolean isShowing() {
        return mProgressDialog != null && mProgressDialog.isShowing();
    }

    public void dismiss() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private class ProgressDialog extends Dialog {
        ProgressDialog(Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.rce_loading_dialog);

            Window window = getWindow();
            window.setBackgroundDrawable(new ColorDrawable(Color.argb(0, 0, 0, 0)));
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            window.setDimAmount(0);

            setCanceledOnTouchOutside(false);
            setCancelable(mCancellable);

            initViews();
        }

        private void initViews() {
            FrameLayout containerFrame = (FrameLayout) findViewById(R.id.rce_container);
            int wrapParam = ViewGroup.LayoutParams.WRAP_CONTENT;
            View view = new SpinView(getContext());
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(wrapParam, wrapParam);
            containerFrame.addView(view, params);

            if (detailLabel != null) {
                TextView textView = (TextView) findViewById(R.id.rce_details_label);
                textView.setVisibility(View.VISIBLE);
                textView.setText(detailLabel);
            }
        }
    }
}
