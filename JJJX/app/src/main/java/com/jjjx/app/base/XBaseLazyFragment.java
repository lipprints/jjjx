package com.jjjx.app.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjjx.data.ActivityPageManager;
import com.jjjx.fragment.BaseFragment;

/**
 * Created by xz on 2017/6/30 0030.
 * <p>
 * 用户需要懒加载的fragment，需要于viewpage搭配使用
 *
 * 注：
 * 1.因为导航页过多，fragment只允许保存2个；
 * 2.要注意用户使用快速的滑动，导致fragment的生命周期快速迭代；
 * 3.注意对象和集合的不能重复；
 * 4.要注意各种状态的改变（网络）；
 *
 */

public abstract class XBaseLazyFragment extends BaseFragment {
    protected boolean isVisible = false;//fragment是否是显示状态
    protected boolean isPrepared = false;//fragment的视图是否加载完毕
    private View mContentView;
    protected boolean isNoCloseFragment = true;//是否销毁fragment了 [true-没有]
    /**
     * 在这里实现Fragment数据的懒加载
     *
     * @param isVisibleToUser Fragment UI对用户是否可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            if (isVisible && isPrepared == true) {
                onVisible();
            }
        } else {
            isVisible = false;
            if (!isVisible && isPrepared == true) {
                onInvisible();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mContentView = inflater.inflate(getContentView(), container, false);
        isNoCloseFragment=true;
        isPrepared = true;
        initView(mContentView);
        return mContentView;
    }

    @Override
    public View onCreateFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //被重写了
        return null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //当布局加载完成，并且界面显示的时候才会调用
        if (isPrepared && isVisible) {
            lazyLoad();// 数据处理
        }
    }


    protected void onVisible() {//当fragment显示的时候
        if (isPrepared && isVisible) {
            lazyLoad();// 数据处理
        }
    }

    @LayoutRes
    protected abstract int getContentView();

    protected abstract void initView(View view);

    protected abstract void lazyLoad();

    protected void onInvisible() {//当fragment隐藏的时候
    }

    /**
     * 方法说明:手动释放内存
     * 方法名称:releaseMemory
     * 返回值:void
     */
    public abstract String closeFragment();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isNoCloseFragment = false;
        String closeStr = closeFragment();
        //关闭Okhttp的网络请求
        ActivityPageManager.unbindReferences(mContentView);
        mContentView = null;
    }


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