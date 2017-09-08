package com.jjjx.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jjjx.JxAction;
import com.jjjx.R;
import com.jjjx.adapter.IndexItemAdapter;
import com.jjjx.data.async.AsyncTaskManager;
import com.jjjx.data.async.OnDataListener;
import com.jjjx.data.response.IndexDataResponse.ParaEntity.ComplaintsEntity;
import com.jjjx.fragment.IndexFragment;
import com.jjjx.utils.CacheTask;
import com.jjjx.utils.NToast;
import com.jjjx.widget.like.LikeButton;
import com.jjjx.widget.like.OnLikeListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCUserAction;
import fm.jiecao.jcvideoplayer_lib.JCUserActionStandard;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import io.rong.imkit.RongIM;

/**
 * Created by AMing on 17/7/13.
 * Company RongCloud
 */

public class IndexItemDetailsActivity extends AppCompatActivity {

    public static final int ADD_CLASS = 801;
    public static final int CANCEL_CLASS = 802;

    JCVideoPlayerStandard mJcVideoPlayerStandard;
    private ComplaintsEntity entity;
    private List<String> pictureList = new ArrayList<>();
    private ListView itemListView;
    private View headView;
    private View footView;
    private IndexItemAdapter adapter;
    private SimpleDraweeView simpleDraweeView;
    private TextView name;
    private LikeButton likeButton;
    private TextView chatButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_index);
        itemListView = (ListView) findViewById(R.id.index_item_list);
        likeButton = (LikeButton) findViewById(R.id.like_button);
        chatButton = (TextView) findViewById(R.id.start_chat);
        headView = LayoutInflater.from(this).inflate(R.layout.index_item_head_view, itemListView, false);
        footView = LayoutInflater.from(this).inflate(R.layout.index_item_foot_view, itemListView, false);
        itemListView.addHeaderView(headView);
        itemListView.addFooterView(footView);
        adapter = new IndexItemAdapter(this, pictureList);
        itemListView.setAdapter(adapter);
        entity = getIntent().getParcelableExtra("indexItemData");
        if (entity != null) {
            simpleDraweeView = (SimpleDraweeView) findViewById(R.id.index_item_user_head);
            name = (TextView) findViewById(R.id.index_item_user_name);
            simpleDraweeView.setImageURI(entity.getHead_portrait());
            simpleDraweeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!CacheTask.getInstance().isLogin()) {
                        NToast.shortToast(IndexItemDetailsActivity.this, "请登录才能做后续操作");
                        return;
                    }
                    Intent intent = new Intent(IndexItemDetailsActivity.this, UserProfileActivity.class);
                    intent.putExtra("userId", String.valueOf(entity.getUser_id()));
                    startActivity(intent);
                }
            });
            name.setText(entity.getName());
            if (entity.getVideo() != null && !TextUtils.isEmpty(entity.getVideo())) {
                mJcVideoPlayerStandard = (JCVideoPlayerStandard) headView.findViewById(R.id.jc_video);
                mJcVideoPlayerStandard.setVisibility(View.VISIBLE);
                startVideo(entity.getVideo(), entity.getFirstFrame(), entity.getCourseName(), true);
            }
            if (entity.getPicture().contains(",")) {
                String[] arrays = entity.getPicture().split(",");
                pictureList = Arrays.asList(arrays);
            } else {
                pictureList.add(entity.getPicture());
            }
            adapter.refreshAdapter(pictureList);
            likeButton.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    //TODO 收藏
                    AsyncTaskManager.getInstance(IndexItemDetailsActivity.this).request(ADD_CLASS, new OnDataListener() {
                        @Override
                        public Object doInBackground(int requestCode) throws Exception {
                            return new JxAction(IndexItemDetailsActivity.this).addCollection(CacheTask.getInstance().getUserId(), String.valueOf(entity.getId()));

                        }

                        @Override
                        public boolean onIntercept(int requestCode, Object result) {
                            return false;
                        }

                        @Override
                        public void onSuccess(int requestCode, Object result) {
                            NToast.shortToast(IndexItemDetailsActivity.this, "收藏成功");
                            if (mRefreshListener != null) {
                                mRefreshListener.onRefresh();
                            }
                        }

                        @Override
                        public void onFailure(int requestCode, int state, Object result) {

                        }
                    });

                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    AsyncTaskManager.getInstance(IndexItemDetailsActivity.this).request(CANCEL_CLASS, new OnDataListener() {
                        @Override
                        public Object doInBackground(int requestCode) throws Exception {
                            return new JxAction(IndexItemDetailsActivity.this).deleteCollection(CacheTask.getInstance().getUserId(), String.valueOf(entity.getId()));
                        }

                        @Override
                        public boolean onIntercept(int requestCode, Object result) {
                            return false;
                        }

                        @Override
                        public void onSuccess(int requestCode, Object result) {
                            NToast.shortToast(IndexItemDetailsActivity.this, "已取消收藏");
                            if (mRefreshListener != null) {
                                mRefreshListener.onRefresh();
                            }
                        }

                        @Override
                        public void onFailure(int requestCode, int state, Object result) {

                        }
                    });
                }
            });
            if (!CacheTask.getInstance().isLogin()) {
                likeButton.setVisibility(View.GONE);
            } else {
                likeButton.setVisibility(View.VISIBLE);
                if (entity.getTab().equals("1")) {
                    likeButton.setLiked(true);
                }
            }


            chatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!CacheTask.getInstance().isLogin()) {
                        NToast.shortToast(IndexItemDetailsActivity.this, "请登录才能做后续操作");
                        return;
                    }
                    RongIM.getInstance().startPrivateChat(IndexItemDetailsActivity.this, String.valueOf(entity.getUser_id()), entity.getName());
                }
            });
        }
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

    private static RefreshListener mRefreshListener;

    public interface RefreshListener {
        void onRefresh();
    }

    public static void setRefreshListener(RefreshListener refreshListener) {
        mRefreshListener = refreshListener;
    }

}
