package com.jjjx.function.main;

import android.content.Intent;
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

import com.jjjx.app.App;
import com.jjjx.R;
import com.jjjx.function.base.BaseActivity;
import com.jjjx.function.add.view.PublishActivity;
import com.jjjx.function.my.verify.VerifyRoleActivity;
import com.jjjx.function.my.verify.WaitingVerifyActivity;
import com.jjjx.data.GlideManage;
import com.jjjx.function.find.FindFragment;
import com.jjjx.function.home.HomeFragment;
import com.jjjx.function.login.LoginActivity;
import com.jjjx.function.message.view.ChatListFragment;
import com.jjjx.function.my.MyFragment;
import com.jjjx.utils.AppCompatNotificationBar;
import com.jjjx.utils.CacheTask;
import com.jjjx.widget.JxViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author am
 * @update xz
 */
public class MainActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {


    private JxViewPager mViewPager;
    private List<Fragment> mFragment = new ArrayList<>();
    private RadioButton mHomeRadio;
    /**
     * 记录上一个fragment
     */
    private int pageTag = -1;


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
        mViewPager = findViewById(R.id.main_viewpager);

        mFragment.add(new HomeFragment());
        mFragment.add(new FindFragment());
        mFragment.add(new ChatListFragment());
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
        if (checkedId == R.id.tab_home || checkedId == R.id.tab_my) {
            AppCompatNotificationBar.setNotificationBarColor(R.color.app_main_color, this);
        } else {
            AppCompatNotificationBar.setNotificationBarColor(R.color.app_sub_color, this);
        }
        switch (checkedId) {
            // 首页
            case R.id.tab_home:
                pageTag = checkedId;
                mViewPager.setCurrentItem(0);
                break;
            // 发现
            case R.id.tab_find:
                pageTag = checkedId;
                mViewPager.setCurrentItem(1);
                break;
            // 消息
            case R.id.tab_message:
                if (CacheTask.getInstance().isLogin()) {
                    mViewPager.setCurrentItem(2);
                } else {
                    //如果没有登陆，是需要把radioButton的还原到上个位置
                    ((RadioButton) findViewById(pageTag)).setChecked(true);
                    startActivity(new Intent(this, LoginActivity.class));
                }

                break;
            // 我的
            case R.id.tab_my:
                //if (CacheTask.getInstance().isLogin()) {
                pageTag = checkedId;
                mViewPager.setCurrentItem(3);
//                } else {
//                    //如果没有登陆，是需要把radioButton的还原到上个位置
//                    ((RadioButton) findViewById(pageTag)).setChecked(true);
//                    startActivity(new Intent(this, LoginActivity.class));
//                }

                break;
            default:
                break;
        }

    }

}
