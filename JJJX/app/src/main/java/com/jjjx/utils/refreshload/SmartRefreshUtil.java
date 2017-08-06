package com.jjjx.utils.refreshload;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

/**
 * Created by xz on 2017/7/31 0031.
 * 把 SmartRefresh和Recyclerview整合一下，
 */

public class SmartRefreshUtil {

    private SmartRefreshLayout mSmartRefreshLayout;
    private RecyclerView mRecyclerView;
    public static final int LOAD_NO=121;
    public static final int LOAD_ERROR=122;
    public static final int LOAD_SUCCESS=123;

    public SmartRefreshUtil(@NonNull SmartRefreshLayout mSmartRefreshLayout) {
        this.mSmartRefreshLayout = mSmartRefreshLayout;
    }

    public SmartRefreshUtil(@NonNull SmartRefreshLayout mSmartRefreshLayout, @NonNull RecyclerView mRecyclerView) {
        this.mSmartRefreshLayout = mSmartRefreshLayout;
        this.mRecyclerView = mRecyclerView;
        init();
    }


    private void init() {

    }

    /**
     * 添加阻尼效果的footer
     */
    public SmartRefreshUtil addReboundFooter(@NonNull Context context) {
        mSmartRefreshLayout.setRefreshFooter(new FalsifyFooter(context), ViewGroup.LayoutParams.MATCH_PARENT, 200);
        mSmartRefreshLayout.setFooterMaxDragRate(1);
        mSmartRefreshLayout.setDragRate(1);
        mSmartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mSmartRefreshLayout.finishLoadmore(0);
            }
        });
        return this;
    }


    /**
     * 立即停止刷新加载
     */
    public void stopRefrshLoad() {
        if (mSmartRefreshLayout != null && mSmartRefreshLayout.isRefreshing())
            mSmartRefreshLayout.finishRefresh(200);
        if (mSmartRefreshLayout != null && mSmartRefreshLayout.isLoading())
            mSmartRefreshLayout.finishLoadmore(200);
    }

    /**
     * 立即停止刷新加载
     */
    public void stopRefrshLoad(int loadStatus) {
        if (mSmartRefreshLayout != null && mSmartRefreshLayout.isRefreshing())
            mSmartRefreshLayout.finishRefresh();
        if (mSmartRefreshLayout != null && mSmartRefreshLayout.isLoading()) {
            switch (loadStatus){
                case LOAD_NO:
                    mSmartRefreshLayout.setLoadmoreFinished(true);
                    mSmartRefreshLayout.finishLoadmore(200);
                    break;
                case LOAD_ERROR:
                    mSmartRefreshLayout.finishLoadmore(200,false);
                    break;
                case LOAD_SUCCESS:
                    mSmartRefreshLayout.finishLoadmore(200,true);
                    break;
            }

        }
    }


    /**
     * 立即停止刷新加载
     */
    public void stopRefrshLoad(int refrshDelayed, int loadDelayed) {
        if (mSmartRefreshLayout != null && mSmartRefreshLayout.isRefreshing())
            mSmartRefreshLayout.finishRefresh(refrshDelayed);
        if (mSmartRefreshLayout != null && mSmartRefreshLayout.isLoading())
            mSmartRefreshLayout.finishLoadmore(loadDelayed);

    }

}
