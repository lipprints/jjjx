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
import com.jjjx.fragment.find.adapter.HotAdapter;
import com.jjjx.model.HotEntity;
import com.jjjx.utils.ToastUtil;
import com.jjjx.utils.refreshload.SmartRefreshUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xz on 2017/8/1 0001.
 * 关注
 */

public class FollowFragment extends XBaseLazyFragment {

    private SmartRefreshLayout mSmartRefreshLayout;
    private boolean isRefresh = false;
    private int mPageIndex = 0;//页码
    private int mTotalPages = 1;//总页数
    private RecyclerView mRecyclerView;
    private SmartRefreshUtil mRefreshUtil;
    private GlideManage mGlideManage;
    private HotAdapter mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.fragment_find_follow;
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
        LinearLayout parentlayout = (LinearLayout) view.findViewById(R.id.fif_parentlayout);
        mSmartRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.fif_srl);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fif_rv);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        //
        mSmartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                isRefresh = false;
                if (mPageIndex < mTotalPages) {
                    mPageIndex++;
                    initData();
                } else {
                    //停止加载，并在加载栏显示，暂无更多数据加载
                    mRefreshUtil.stopRefrshLoad(SmartRefreshUtil.LOAD_NO);
                }
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //刷新后，要设置可以触发加载功能
                refreshlayout.setLoadmoreFinished(false);
                isRefresh = true;
                mPageIndex = 1;
                initData();
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
                mAdapter = new HotAdapter(mGlideManage, getContext());
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
            if (mAdapter.getDatas().size() == 0)
                mSmartRefreshLayout.autoRefresh();
            //每次在这里需要允许glide加载图片
            if (getContext() != null && mGlideManage != null)
                mGlideManage.getRequestManager().resumeRequests();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 这里请求网络等...
     */
    private void initData() {
        List<HotEntity> listH = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            HotEntity hotEntity = new HotEntity();
            hotEntity.setId(i + 1);
            if (i % 3 == 0) {
                hotEntity.setImgUrl("http://img01.e23.cn/2014/0823/20140823070324537.jpg");
                hotEntity.setUserImg("http://img01.e23.cn/2014/0823/20140823070324537.jpg");
            } else if (i % 5 == 0) {
                hotEntity.setImgUrl("http://upload.northnews.cn/2013/0114/1358095079926.jpg");
                hotEntity.setUserImg("http://upload.northnews.cn/2013/0114/1358095079926.jpg");
            } else if (i % 7 == 0) {
                hotEntity.setImgUrl("http://www.bz55.com/uploads/allimg/150414/139-150414093956-50.jpg");
                hotEntity.setUserImg("http://www.bz55.com/uploads/allimg/150414/139-150414093956-50.jpg");
            } else {
                hotEntity.setImgUrl("http://www.sznews.com/photo/images/attachement/jpg/site3/20160205/6c0b840b6799181e94795c.jpg");
                hotEntity.setUserImg("http://www.sznews.com/photo/images/attachement/jpg/site3/20160205/6c0b840b6799181e94795c.jpg");
            }

            hotEntity.setUserName("美女");
            hotEntity.setNumber(i % 2 == 0 ? 100 : 200);
            listH.add(hotEntity);
        }
        mAdapter.setDatas(listH, true);
        mRefreshUtil.stopRefrshLoad(SmartRefreshUtil.LOAD_SUCCESS);
    }


    @Override
    public String closeFragment() {
        if (mAdapter != null)
            mAdapter.removeDataAll();
        if (mRecyclerView != null)
            mRecyclerView.removeAllViews();
        mAdapter = null;
        mGlideManage = null;
        mRefreshUtil = null;
        return "FollowFragment";
    }
}
