package com.jjjx.function.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jjjx.App;
import com.jjjx.R;
import com.jjjx.activity.BaseActivity;
import com.jjjx.activity.PublishActivity;
import com.jjjx.activity.VerifyRoleActivity;
import com.jjjx.activity.WaitingVerifyActivity;
import com.jjjx.data.GlideManage;
import com.jjjx.function.find.FindFragment;
import com.jjjx.function.home.HomeFragment;
import com.jjjx.function.login.LoginActivity;
import com.jjjx.function.my.MyFragment;
import com.jjjx.utils.CacheTask;
import com.jjjx.widget.JxViewPager;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

/**
 * @author am
 * @update xz
 */
public class MainActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {


    private JxViewPager mViewPager;
    private List<Fragment> mFragment = new ArrayList<>();
    private RadioButton mHomeRadio;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setHeadVisibility(View.GONE);
        initViews();
        initMainViewPager();
        startBaidu();
        mHomeRadio.setChecked(true);
    }

    private void startBaidu() {
        App.getInstance().startLocationObserver();
    }

    private void initMainViewPager() {
        ConversationListFragment conversationList = initConversationList();
        mViewPager = findViewById(R.id.main_viewpager);

        mFragment.add(new HomeFragment());
        mFragment.add(new FindFragment());
        mFragment.add(conversationList);
        mFragment.add(new MyFragment());

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
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setScanScroll(false);
    }

    private ConversationListFragment initConversationList() {
        ConversationListFragment listFragment = new ConversationListFragment();
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                //设置私聊会话是否聚合显示
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")
                //群组
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")
                //公共服务号
                .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")
                //订阅号
                .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")
                //系统
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")
                .build();
        listFragment.setUri(uri);
        return listFragment;
    }

    private void initViews() {
        RadioGroup radioGroup = findViewById(R.id.ahm_rg);
        LinearLayout addLayout = findViewById(R.id.am_add);
        ImageView addIco = findViewById(R.id.am_add_ico);
        mHomeRadio = findViewById(R.id.tab_home);
        radioGroup.setOnCheckedChangeListener(this);
        addLayout.setOnClickListener(this);
        new GlideManage(this).getRequestManager().load(R.drawable.ico_add).into(addIco);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.am_add:
                //1是个人 2是老师 3是机构 4审核中
                if (CacheTask.getInstance().isLogin()) {
                    switch (CacheTask.getInstance().getUserRole()) {
                        case "1"://无身份
                            startActivity(new Intent(this, VerifyRoleActivity.class));
                            //TODO 选择验证教师 或者 机构
                            break;
                        case "2"://教师
                            startActivity(new Intent(this, PublishActivity.class));
                            break;
                        case "3"://机构
                            startActivity(new Intent(this, PublishActivity.class));
                            break;
                        case "4"://审核中
                            //TODO 告知在审核中 可以询问客服进度
                            startActivity(new Intent(this, WaitingVerifyActivity.class));
                            break;
                        default:
                            break;
                    }

                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * radioGroup 的item点击处理
     * 控制viewpage
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            // 首页
            case R.id.tab_home:
                mViewPager.setCurrentItem(0);
                break;
            // 发现
            case R.id.tab_find:
                mViewPager.setCurrentItem(1);
                break;
            // 消息
            case R.id.tab_message:
                if (CacheTask.getInstance().isLogin()) {
                    mViewPager.setCurrentItem(2);
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }

                break;
            // 我的
            case R.id.tab_my:
                if (CacheTask.getInstance().isLogin()) {
                    mViewPager.setCurrentItem(3);
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }

                break;
            default:
                break;
        }
    }

}
