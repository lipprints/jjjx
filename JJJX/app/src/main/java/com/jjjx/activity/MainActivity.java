package com.jjjx.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jjjx.fragment.FindFragment;
import com.jjjx.fragment.IndexFragment;
import com.jjjx.fragment.MineFragment;
import com.jjjx.R;
import com.jjjx.data.okhttp.OkHttpUtils;
import com.jjjx.utils.CacheTask;
import com.jjjx.widget.DragPointView;
import com.jjjx.widget.JxViewPager;

import net.alhazmy13.mediapicker.Image.ImagePicker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

public class MainActivity extends BaseActivity implements View.OnClickListener, DragPointView.OnDragListener, ViewPager.OnPageChangeListener {

    private JxViewPager mViewPager;
    private List<Fragment> mFragment = new ArrayList<>();
    private ImageView mainTabImg, findTabImg, messageTabImg, meTabImg;
    private TextView mainTabTxt, findTabTxt, messageTabTxt, meTabTxt;
    private DragPointView mUnreadNumView;
    private RelativeLayout publishView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setHeadVisibility(View.GONE);
        initViews();
        changeTextViewColor();
        changeSelectedTabState(0);
        initMainViewPager();
    }

    private void initMainViewPager() {
        ConversationListFragment conversationList = initConversationList();
        mViewPager = (JxViewPager) findViewById(R.id.main_viewpager);

        mUnreadNumView = (DragPointView) findViewById(R.id.seal_num);
        mUnreadNumView.setOnClickListener(this);
        mUnreadNumView.setDragListener(this);

        mFragment.add(new IndexFragment());
        mFragment.add(new FindFragment());
        mFragment.add(conversationList);
        mFragment.add(new MineFragment());

        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }
        };

        mViewPager.setAdapter(fragmentPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setScanScroll(false);
    }

    private ConversationListFragment initConversationList() {
        ConversationListFragment listFragment = new ConversationListFragment();
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//群组
                .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
                .build();
        listFragment.setUri(uri);
        return listFragment;
    }

    private void initViews() {
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.jx_tab_main);
        RelativeLayout findLayout = (RelativeLayout) findViewById(R.id.jx_tab_find);
        RelativeLayout messageLayout = (RelativeLayout) findViewById(R.id.jx_tab_chats);
        RelativeLayout meLayout = (RelativeLayout) findViewById(R.id.jx_tab_me);
        mainTabImg = (ImageView) findViewById(R.id.tab_img_main);
        findTabImg = (ImageView) findViewById(R.id.tab_img_find);
        messageTabImg = (ImageView) findViewById(R.id.tab_img_chats);
        meTabImg = (ImageView) findViewById(R.id.tab_img_me);
        publishView = (RelativeLayout) findViewById(R.id.jx_tab_publish);
        mainTabTxt = (TextView) findViewById(R.id.tab_text_main);
        findTabTxt = (TextView) findViewById(R.id.tab_text_find);
        messageTabTxt = (TextView) findViewById(R.id.tab_text_chats);
        meTabTxt = (TextView) findViewById(R.id.tab_text_me);
        mainLayout.setOnClickListener(this);
        findLayout.setOnClickListener(this);
        messageLayout.setOnClickListener(this);
        meLayout.setOnClickListener(this);
        publishView.setOnClickListener(this);
    }


    private void changeTextViewColor() {
        mainTabImg.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_task_normal_icon));
        findTabImg.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_nearby_normal_icon));
        messageTabImg.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_circle_normal_icon));
        meTabImg.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_personal_center_normal_icon));
        mainTabTxt.setTextColor(Color.parseColor("#abadbb"));
        findTabTxt.setTextColor(Color.parseColor("#abadbb"));
        messageTabTxt.setTextColor(Color.parseColor("#abadbb"));
        meTabTxt.setTextColor(Color.parseColor("#abadbb"));
    }


    private void changeSelectedTabState(int position) {
        switch (position) {
            case 0:
                mainTabTxt.setTextColor(Color.parseColor("#FFE14F"));
                mainTabImg.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_task_highlight_icon));
                break;
            case 1:
                findTabTxt.setTextColor(Color.parseColor("#FFE14F"));
                findTabImg.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_nearby_highlight_icon));
                break;
            case 2:
                messageTabTxt.setTextColor(Color.parseColor("#FFE14F"));
                messageTabImg.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_circle_highlight_icon));
                break;
            case 3:
                meTabTxt.setTextColor(Color.parseColor("#FFE14F"));
                meTabImg.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_personal_center_highlight_icon));
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.jx_tab_main:
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.jx_tab_find:
                mViewPager.setCurrentItem(1, false);
                break;
            case R.id.jx_tab_chats:
                if (CacheTask.getInstance().isLogin()) {
                    mViewPager.setCurrentItem(2, false);
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            case R.id.jx_tab_me:
                if (CacheTask.getInstance().isLogin()) {
                    mViewPager.setCurrentItem(3, false);
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            case R.id.jx_tab_publish:
                if (CacheTask.getInstance().isLogin()) {
                    startActivity(new Intent(this, PublishActivity.class));
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
        }
    }


    @Override
    public void onDragOut() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        changeTextViewColor();
        changeSelectedTabState(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> mPaths = (List<String>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_PATH);
            File imageFile = new File(mPaths.get(0));
            if (imageFile.exists()) {
                OkHttpUtils.getInstance(this).uploadImage(CacheTask.getInstance().getUserId(), imageFile, new OkHttpUtils.UploadImageListener() {
                    @Override
                    public void onSuccess(String result) {
                        Log.e(TAG,result);
                        //{"head":{"msg":"发布失败！","code":"E0005"}}
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                });
            }
        }
    }
}
