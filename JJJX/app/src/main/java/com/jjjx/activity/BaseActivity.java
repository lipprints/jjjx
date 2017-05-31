package com.jjjx.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.gyf.barlibrary.ImmersionBar;
import com.jjjx.JxAction;
import com.jjjx.R;
import com.jjjx.data.ActivityPageManager;
import com.jjjx.data.async.AsyncTaskManager;
import com.jjjx.data.async.OnDataListener;
import com.jjjx.utils.NToast;


/**
 * Created by AMing on 17/5/7.
 * Company RongCloud
 */

public class BaseActivity extends FragmentActivity implements OnDataListener {

    protected Context mContext;
    protected JxAction action;
    private AsyncTaskManager mAsyncTaskManager;

    protected ViewFlipper mContentView;
    protected RelativeLayout layout_head;
    protected TextView btn_left;
    protected TextView btn_right;
    protected TextView tv_title;
    protected View base_line;
    protected final String TAG = this.getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.layout_base);
        mContext = this;

//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//			setTranslucentStatus(true);
//		}

        //初始化公共头部
        mContentView = (ViewFlipper) super.findViewById(R.id.layout_container);
        layout_head = (RelativeLayout) super.findViewById(R.id.layout_head);
        btn_left = (TextView) super.findViewById(R.id.btn_left);
        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_right = (TextView) super.findViewById(R.id.btn_right);
        tv_title = (TextView) super.findViewById(R.id.tv_title);
        base_line = (View) super.findViewById(R.id.base_line);

        //初始化异步框架
        action = new JxAction(mContext);
        mAsyncTaskManager = AsyncTaskManager.getInstance(getApplicationContext());
        //Activity管理
        ActivityPageManager.getInstance().addActivity(this);
        ImmersionBar.with(this).init(); //初始化，默认透明状态栏和黑色导航栏
    }


    @Override
    public void setContentView(View view) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        mContentView.addView(view, lp);
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        setContentView(view);
    }


    @SuppressWarnings("unchecked")
    protected <T extends View> T getViewById(int id) {
        return (T) findViewById(id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy(); //不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
        ActivityPageManager.unbindReferences(mContentView);
        ActivityPageManager.getInstance().removeActivity(this);
        mContentView = null;
        mContext = null;
    }


    /**
     * 发送请求（需要检查网络）
     *
     * @param requestCode 请求码
     */
    public void request(int requestCode) {
        mAsyncTaskManager.request(requestCode, this);
    }


    /**
     * 发送请求
     *
     * @param requestCode    请求码
     * @param isCheckNetwork 是否需检查网络，true检查，false不检查
     */
    public void request(int requestCode, boolean isCheckNetwork) {
        mAsyncTaskManager.request(requestCode, isCheckNetwork, this);
    }

    /**
     * 取消所有请求
     */
    public void cancelRequest() {
        mAsyncTaskManager.cancelRequest();
    }


    @Override
    public Object doInBackground(int requestCode) throws Exception {
        return null;
    }

    @Override
    public boolean onIntercept(int requestCode, Object result) {
        return false;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {

    }

    @Override
    public void onFailure(int requestCode, int state, Object result) {
        switch (state) {
            //网络不可用给出提示
            case AsyncTaskManager.HTTP_NULL_CODE:
                NToast.shortToast(mContext, R.string.common_network_unavailable);
                break;

            //网络有问题给出提示
            case AsyncTaskManager.HTTP_ERROR_CODE:
                NToast.shortToast(mContext, R.string.common_network_error);
                break;

            //请求有问题给出提示
            case AsyncTaskManager.REQUEST_ERROR_CODE:
                NToast.shortToast(mContext, R.string.common_request_error);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 设置头部是否可见
     *
     * @param visibility
     */
    public void setHeadVisibility(int visibility) {
        layout_head.setVisibility(visibility);
        base_line.setVisibility(visibility);
    }

    /**
     * 设置标题
     */
    public void setTitle(int titleId) {
        tv_title.setText(getString(titleId));
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        tv_title.setText(title);
    }

    /**
     * 设置左按钮图标
     *
     * @param resid
     */
    public void setBtnLeft(int resid) {
        Drawable img = getResources().getDrawable(resid);
        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
        btn_left.setCompoundDrawables(img, null, null, null);
        btn_left.setText("");
        btn_left.setVisibility(View.VISIBLE);
    }

    /**
     * 设置左按钮文字
     *
     * @param txt
     */
    public void setBtnLeft(String txt) {
        btn_left.setCompoundDrawables(null, null, null, null);
        btn_left.setText(txt);
        btn_left.setVisibility(View.VISIBLE);
    }

    /**
     * 设置左按钮图标
     *
     * @param resid
     */
    public void setBtnRight(int resid) {
        Drawable img = getResources().getDrawable(resid);
        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
        btn_right.setCompoundDrawables(img, null, null, null);
        btn_right.setText("");
        btn_right.setVisibility(View.VISIBLE);
    }

    /**
     * 设置左按钮文字
     *
     * @param txt
     */
    public void setBtnRight(String txt) {
        btn_right.setCompoundDrawables(null, null, null, null);
        btn_right.setText(txt);
        btn_right.setVisibility(View.VISIBLE);
    }


    /**
     * 设置返回
     */
    public void setBtnBack() {
        Drawable img = getResources().getDrawable(R.drawable.nav_back);
        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
        btn_left.setCompoundDrawables(img, null, null, null);
        btn_left.setText("");
        btn_left.setVisibility(View.VISIBLE);
    }
}
