package com.jjjx.function.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by xz on 2017/6/30 0030.
 * <p>
 * 一般使用的fragment，配合FragmentTransition使用
 * <p>
 * fragment的显示隐藏，如果触发了onResume，则不会触发onHiddenChanged的显示;
 */

public abstract class XBaseFragment extends BaseFragment {
    /**
     * fragment是否是显示状态
     */
    protected boolean isVisible = false;
    /**
     * fragment的视图是否加载完毕
     */
    protected boolean isPrepared = false;
    private View mContentView;

    protected final String HTTP_TAG = this.getClass().getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mContentView = inflater.inflate(getContentView(), container, false);
        initView(mContentView);
        isPrepared = true;
        return mContentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //当布局加载完成，并且界面显示的时候才会调用
        if (isPrepared) {
            // 数据处理
            xLoad();
        }
    }

    /**
     * 用来判断 fragment是否隐藏[true-隐藏]
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            isVisible = false;
            onInvisible();
        } else {
            isVisible = true;
            onVisible();
        }
    }

    @Override
    public View onCreateFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //被重写了
        return null;
    }

    @LayoutRes
    protected abstract int getContentView();

    protected abstract void initView(View view);//初始话view

    protected abstract void xLoad();//可以加载数据了

    protected void onVisible() {
    }//当fragment显示的时候

    protected void onInvisible() {
    }//当fragment隐藏的时候

    /**
     * [绑定控件]
     *
     * @param resId viewId
     * @return view
     */
    protected <T extends View> T find(@IdRes int resId) {
        return mContentView.findViewById(resId);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        closeFragment();
        mContentView = null;
    }


    /**
     * 方法说明:手动释放内存
     * 方法名称:releaseMemory
     * 返回值:void
     */
    protected abstract void closeFragment();

    /**
     * param pClass
     * 方法说明:启动指定activity
     * 方法名称:openActivity
     * 返回值:void
     */
    public void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    /**
     * param pClass
     * param pBundle
     * 方法说明:启动到指定activity，Bundle传递对象（作用于2个界面之间传递数据）
     * 方法名称:openActivity
     * 返回值:void
     */
    public void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(getContext(), pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

    /**
     * param pClass
     * param pBundle
     * param requestCode
     * 方法说明:A-Activity需要在B-Activtiy中执行一些数据操作， 而B-Activity又要将，执行操作数据的结果返回给A-Activity
     * ， Bundle传递对象（作用于2个界面之间传递数据）
     * 方法名称:openActivityResult
     * 返回值:void
     */
    public void openActivityResult(Class<?> pClass, Bundle pBundle,
                                   int requestCode) {
        Intent intent = new Intent(this.getContext(), pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivityForResult(intent, requestCode);
    }


}
