package com.jjjx.function.find.view;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jjjx.JxAction;
import com.jjjx.R;
import com.jjjx.adapter.CommentAdapter;
import com.jjjx.data.async.AsyncTaskManager;
import com.jjjx.data.async.OnDataListener;
import com.jjjx.data.response.AddCommentResponse;
import com.jjjx.data.response.CommentListResponse;
import com.jjjx.data.response.FindDataResponse;
import com.jjjx.utils.CacheTask;
import com.jjjx.utils.NToast;
import com.jjjx.utils.refreshload.SmartRefreshUtil;
import com.jjjx.widget.commentdialog.BottomDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCUserAction;
import fm.jiecao.jcvideoplayer_lib.JCUserActionStandard;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 *
 * @author AMing
 * @date 17/8/8
 * Company RongCloud
 *
 */
public class FindVideoActivity extends AppCompatActivity implements OnDataListener {
    JCVideoPlayerStandard mJcVideoPlayerStandard;
    private static final int ADD_COMMENT = 211;
    private static final int GET_COMMENT_LIST = 212;

    TextView commentButton;

    private EditText mEditText;
    private TextView mButton;
    private BottomDialog dialog;
    private FindDataResponse.ParaEntity.DiscoverInfoEntity entity;
    private SmartRefreshLayout mSmartRefreshLayout;
    private SmartRefreshUtil mSmartRefreshUtil;
    private int mPageIndex = 0;//页码
    private boolean isRefresh = false;
    private List<CommentListResponse.ParaEntity.DiscoverInfoEntity> data = new ArrayList<>();
    private ListView commentList;
    private CommentAdapter adapter;
    private View headView;
    private AsyncTaskManager mAsyncTaskManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_video);
        setTitle("发现详情");
        mAsyncTaskManager = AsyncTaskManager.getInstance(getApplicationContext());
        entity = getIntent().getParcelableExtra("FindVideoEntity");


        commentButton = (TextView) findViewById(R.id.comment_button);
        commentButton.setVisibility(CacheTask.getInstance().isLogin() ? View.VISIBLE : View.GONE);
        commentList = (ListView) findViewById(R.id.comment_list);
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCommentDialog();
            }
        });
        mSmartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.comment_sfl);
        mSmartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                isRefresh = false;
                mPageIndex++;
                mAsyncTaskManager.request(GET_COMMENT_LIST, FindVideoActivity.this);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //刷新后，要设置可以触发加载功能
                refreshlayout.setLoadmoreFinished(false);
                isRefresh = true;
                mPageIndex = 0;
                mAsyncTaskManager.request(GET_COMMENT_LIST, FindVideoActivity.this);

            }
        });
        mSmartRefreshUtil = new SmartRefreshUtil(mSmartRefreshLayout);
        headView = LayoutInflater.from(this).inflate(R.layout.find_video_head_view, commentList, false);
        mJcVideoPlayerStandard = (JCVideoPlayerStandard) headView.findViewById(R.id.jc_video);
        startVideo(entity.getVideo(), entity.getFirstFrame(), "", true);
        adapter = new CommentAdapter(data, this);
        commentList.addHeaderView(headView);
        commentList.setAdapter(adapter);
        mAsyncTaskManager.request(GET_COMMENT_LIST, FindVideoActivity.this);
    }

    /**
     * 弹出评论dialog
     */
    private void showCommentDialog() {
        dialog = BottomDialog.create(getSupportFragmentManager())
                .setLayoutRes(R.layout.dialog_comment_view)
                .setViewListener(new BottomDialog.ViewListener() {
                    @Override
                    public void bindView(View v) {
                        initView(v);
                    }
                })
                .setDimAmount(0.6f)
                .setCancelOutside(false)
                .setTag("comment")
                .show();
    }


    private void initView(View v) {
        mEditText = (EditText) v.findViewById(R.id.edit_text);
        mButton = (TextView) v.findViewById(R.id.comment_btn);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    mButton.setBackgroundResource(R.drawable.dialog_send_btn);
                    mButton.setEnabled(false);
                } else {
                    mButton.setBackgroundResource(R.drawable.dialog_send_btn_pressed);
                    mButton.setEnabled(true);
                }
            }
        });
        mEditText.post(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mEditText, 0);
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAsyncTaskManager.request(ADD_COMMENT, FindVideoActivity.this);
                dialog.dismiss();
            }
        });

    }


    @Override
    public Object doInBackground(int requestCode) throws Exception {
        if (requestCode == ADD_COMMENT) {
            return new JxAction(this).addComment(String.valueOf(entity.getPid()), mEditText.getText().toString());
        } else if (requestCode == GET_COMMENT_LIST) {
            return new JxAction(this).getCommentList(String.valueOf(entity.getPid()), String.valueOf(mPageIndex));
        }
        return null;
    }

    boolean isMoveList = false;

    @Override
    public void onSuccess(int requestCode, Object result) {
        if (requestCode == ADD_COMMENT) {
            AddCommentResponse response = (AddCommentResponse) result;
            if ("S0009".equals(response.getHead().getCode())) {
                NToast.shortToast(this, "评论成功");
                isRefresh = true;
                mPageIndex = 0;
                mAsyncTaskManager.request(GET_COMMENT_LIST, FindVideoActivity.this);
                isMoveList = true;
            }
        } else if (requestCode == GET_COMMENT_LIST) {
            CommentListResponse response = (CommentListResponse) result;
            if ("10000".equals(response.getHead().getCode())) {
                if (response.getPara().getDiscoverInfo().size() > 0) {
                    if (isRefresh) {//是下拉刷新
                        adapter.refreshAdapter(response.getPara().getDiscoverInfo());
                        if (isMoveList) {
                            new android.os.Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    commentList.smoothScrollToPosition(1);
                                }
                            }, 300);
                        }
                    } else {//是上拉加载
                        adapter.addDataAdapter(response.getPara().getDiscoverInfo());
                    }
                    mSmartRefreshUtil.stopRefrshLoad(SmartRefreshUtil.LOAD_SUCCESS);
                } else {
                    mSmartRefreshUtil.stopRefrshLoad(SmartRefreshUtil.LOAD_NO);
                }
            }
        }
    }

    @Override
    public boolean onIntercept(int requestCode, Object result) {
        return false;
    }

    @Override
    public void onFailure(int requestCode, int state, Object result) {

    }

    private void startVideo(String videoUrl, String imageUrl, String title, boolean isAutoPlayer) {
        mJcVideoPlayerStandard.setUp(videoUrl, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, title);
        Glide.with(this).load(imageUrl).into(mJcVideoPlayerStandard.thumbImageView);//加载缩略图
        JCVideoPlayer.setJcUserAction(new MyUserActionStandard());//各种事件状态
        if (isAutoPlayer) {
            mJcVideoPlayerStandard.startButton.performClick();//自动播放
        }
    }

    class MyUserActionStandard implements JCUserActionStandard {

        @Override
        public void onEvent(int type, String url, int screen, Object... objects) {
            switch (type) {
                case JCUserAction.ON_CLICK_START_ICON:
                    Log.i("USER_EVENT", "ON_CLICK_START_ICON" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_START_ERROR:
                    Log.i("USER_EVENT", "ON_CLICK_START_ERROR" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_START_AUTO_COMPLETE:
                    Log.i("USER_EVENT", "ON_CLICK_START_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_PAUSE:
                    Log.i("USER_EVENT", "ON_CLICK_PAUSE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_RESUME:
                    Log.i("USER_EVENT", "ON_CLICK_RESUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_SEEK_POSITION:
                    Log.i("USER_EVENT", "ON_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_AUTO_COMPLETE:
                    Log.i("USER_EVENT", "ON_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_ENTER_FULLSCREEN:
                    Log.i("USER_EVENT", "ON_ENTER_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_QUIT_FULLSCREEN:
                    Log.i("USER_EVENT", "ON_QUIT_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_ENTER_TINYSCREEN:
                    Log.i("USER_EVENT", "ON_ENTER_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_QUIT_TINYSCREEN:
                    Log.i("USER_EVENT", "ON_QUIT_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_TOUCH_SCREEN_SEEK_VOLUME:
                    Log.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_VOLUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_TOUCH_SCREEN_SEEK_POSITION:
                    Log.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;

                case JCUserActionStandard.ON_CLICK_START_THUMB:
                    Log.i("USER_EVENT", "ON_CLICK_START_THUMB" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserActionStandard.ON_CLICK_BLANK:
                    Log.i("USER_EVENT", "ON_CLICK_BLANK" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                default:
                    Log.i("USER_EVENT", "unknow");
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
