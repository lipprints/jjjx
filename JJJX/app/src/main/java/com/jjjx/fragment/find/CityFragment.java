package com.jjjx.fragment.find;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.jjjx.R;
import com.jjjx.app.adapter.RvPureAdapter;
import com.jjjx.app.base.XBaseLazyFragment;
import com.jjjx.data.GlideManage;
import com.jjjx.fragment.find.adapter.FindPureAdapter;
import com.jjjx.utils.ToastUtil;
import com.jjjx.utils.refreshload.SmartRefreshUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

/**
 * Created by xz on 2017/8/1 0001.
 * 同城
 */

public class CityFragment extends XBaseLazyFragment {

    private SmartRefreshLayout mSmartRefreshLayout;
    private boolean isRefresh = false;
    private int mPageIndex = 0;//页码
    private int mTotalPages = 1;//总页数
    private RecyclerView mRecyclerView;
    private SmartRefreshUtil mRefreshUtil;
    private GlideManage mGlideManage;
    private FindPureAdapter mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.fragment_find_city;
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        if (getContext() != null && mGlideManage != null) {
            mGlideManage.getRequestManager().resumeRequests();
        }
    }

    @Override
    protected void onInvisible() {
        super.onInvisible();
        if (getContext() != null && mGlideManage != null) {
            Glide.get(getContext()).clearMemory();
            //当界面被隐藏时，需要暂停下载图片
            mGlideManage.getRequestManager().pauseRequests();
        }
    }

    @Override
    protected void initView(View view) {
        LinearLayout parentlayout = (LinearLayout) view.findViewById(R.id.fic_parentlayout);
        mSmartRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.fic_srl);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fic_rv);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        //
        mSmartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                isRefresh = false;
                mPageIndex++;
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //刷新后，要设置可以触发加载功能
                refreshlayout.setLoadmoreFinished(false);
                isRefresh = true;
                mPageIndex = 0;
            }
        });
        //刷新加载
        mRefreshUtil = new SmartRefreshUtil(mSmartRefreshLayout, mRecyclerView);
    }

    @Override
    protected void lazyLoad() {
        //懒加载会在fragment显示的时候被触发，导致数据多次加载，要判断是否有数据，如果有，不触发数据加载
        try {
            if (mAdapter == null) {
                mGlideManage = new GlideManage(getContext());
                mAdapter = new FindPureAdapter(mGlideManage, getContext());
                //这里处理点击事件
                mAdapter.setOnItemClickListener(new RvPureAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                        ToastUtil.showToast("你点击了咯");
                    }
                });
                mRecyclerView.setAdapter(mAdapter);
            }
            //如果数据为0，开启自动刷新
            if (mAdapter.getDatas().size() == 0) {
                mSmartRefreshLayout.autoRefresh();
            }
            //每次在这里需要允许glide加载图片
            if (getContext() != null && mGlideManage != null) {
                mGlideManage.getRequestManager().resumeRequests();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public String closeFragment() {
        if (mAdapter != null) {
            mAdapter.removeDataAll();
        }
        if (mRecyclerView != null) {
            mRecyclerView.removeAllViews();
        }
        mAdapter = null;
        mGlideManage = null;
        mRefreshUtil = null;
        return "CityFragment";
    }


}
