package com.jjjx.fragment.find;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jjjx.R;
import com.jjjx.activity.FindImageActivity;
import com.jjjx.activity.FindVideoActivity;
import com.jjjx.activity.LoginActivity;
import com.jjjx.app.adapter.RvPureAdapter;
import com.jjjx.app.base.XBaseLazyFragment;
import com.jjjx.data.GlideManage;
import com.jjjx.data.response.FindDataResponse;
import com.jjjx.fragment.find.adapter.FindPureAdapter;
import com.jjjx.utils.CacheTask;
import com.jjjx.utils.refreshload.SmartRefreshUtil;
import com.jjjx.widget.like.LikeButton;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

/**
 * Created by xz on 2017/8/1 0001.
 * 热门
 */

public class HotFragment extends XBaseLazyFragment implements LoginActivity.LoginDoneListener {

    private static final int GET_FIND = 3;
    private static final int GET_FIND_LOGIN = 4;
    private static final int ADD_LIKE = 5;
    private static final int CANCEL_LIKE = 6;
    private SmartRefreshLayout mSmartRefreshLayout;
    private boolean isRefresh = false;
    private int mPageIndex = 0;//页码
    private RecyclerView mRecyclerView;
    private SmartRefreshUtil mRefreshUtil;
    private GlideManage mGlideManage;
    private FindPureAdapter mAdapter;
    private int tempPosition;

    @Override
    protected int getContentView() {
        return R.layout.fragment_find_hot;
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
        LinearLayout parentlayout = (LinearLayout) view.findViewById(R.id.fih_parentlayout);
        mSmartRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.fih_srl);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fih_rv);
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
                if (TextUtils.isEmpty(CacheTask.getInstance().getUserId())) {
                    request(GET_FIND);
                } else {
                    request(GET_FIND_LOGIN);
                }
            }
        });
        //刷新加载
        mRefreshUtil = new SmartRefreshUtil(mSmartRefreshLayout, mRecyclerView);
        LoginActivity.setOnLoginDoneListener(this);
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
                        FindDataResponse.ParaEntity.DiscoverInfoEntity entity = mAdapter.getItem(position);
                        if (TextUtils.isEmpty(entity.getFirstFrame())) {
                            Intent imageIntent = new Intent(getActivity(), FindImageActivity.class);
                            imageIntent.putExtra("FindImageEntity", entity);
                            startActivity(imageIntent);
                        } else {
                            Intent videoIntent = new Intent(getActivity(), FindVideoActivity.class);
                            videoIntent.putExtra("FindVideoEntity", entity);
                            startActivity(videoIntent);
                        }
                    }
                });
                mAdapter.setOnLikeButtonClickListener(new FindPureAdapter.OnLikeButtonClickListener() {
                    @Override
                    public void select(LikeButton likeButton, boolean isCheck, int position) {
                        tempPosition = position;
                        if (isCheck) {
                            request(ADD_LIKE);
                        } else {
                            request(CANCEL_LIKE);
                        }
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

    @Override
    public String closeFragment() {
        if (mAdapter != null)
            mAdapter.removeDataAll();
        if (mRecyclerView != null) {
            mRecyclerView.removeAllViews();
        }
        mAdapter = null;
        mGlideManage = null;
        mRefreshUtil = null;
        return "HotFragment";
    }

    @Override
    public Object doInBackground(int requestCode) throws Exception {
        if (requestCode == GET_FIND) {
            return action.getFindData(mPageIndex);
        } else if (requestCode == GET_FIND_LOGIN) {
            return action.getFindDataByUserId(mPageIndex, CacheTask.getInstance().getUserId());
        } else if (requestCode == ADD_LIKE) {

            return action.addLike(CacheTask.getInstance().getUserId(), String.valueOf(mAdapter.getItem(tempPosition).getPid()));
        } else if (requestCode == CANCEL_LIKE) {
            return action.cancelLike(CacheTask.getInstance().getUserId(), String.valueOf(mAdapter.getItem(tempPosition).getPid()));
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        if (result != null && (requestCode == GET_FIND_LOGIN || requestCode == GET_FIND)) {
            FindDataResponse response = (FindDataResponse) result;
            if (response.getHead().getCode().equals("10000") && isNoCloseFragment) {
                //TODO fragment没有被销毁
                if (response.getPara().getDiscoverInfo().size() > 0) {
                    if (isRefresh) {
                        mAdapter.setDatas(response.getPara().getDiscoverInfo(), true);
                    } else {
                        mAdapter.addDatas(response.getPara().getDiscoverInfo(), true);
                    }
                    mRefreshUtil.stopRefrshLoad(SmartRefreshUtil.LOAD_SUCCESS);
                } else {
                    mRefreshUtil.stopRefrshLoad(SmartRefreshUtil.LOAD_NO);
                }
                return;
            }
        } else if (requestCode == ADD_LIKE) {
            FindDataResponse.ParaEntity.DiscoverInfoEntity item = mAdapter.getItem(tempPosition);
            item.setThumbNo(String.valueOf(Integer.parseInt(item.getThumbNo()) + 1));
            item.setTab("1");
            mAdapter.notifyItemChanged(tempPosition);
            Toast.makeText(getActivity(), "点赞成功", Toast.LENGTH_SHORT).show();
        } else if (requestCode == CANCEL_LIKE) {
            FindDataResponse.ParaEntity.DiscoverInfoEntity item = mAdapter.getItem(tempPosition);
            item.setThumbNo(String.valueOf(Integer.parseInt(item.getThumbNo()) - 1));
            item.setTab("2");
            mAdapter.notifyItemChanged(tempPosition);
            Toast.makeText(getActivity(), "取消点赞", Toast.LENGTH_SHORT).show();
        }
        mRefreshUtil.stopRefrshLoad();
    }

    @Override
    public void onFailure(int requestCode, int state, Object result) {
        super.onFailure(requestCode, state, result);
        if (isNoCloseFragment)
            mRefreshUtil.stopRefrshLoad(SmartRefreshUtil.LOAD_ERROR);
    }

    @Override
    public void done() {
        request(GET_FIND_LOGIN);
    }
}
