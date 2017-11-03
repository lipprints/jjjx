package com.jjjx.function.my;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jjjx.App;
import com.jjjx.Constants;
import com.jjjx.R;
import com.jjjx.activity.ClassManageActivity;
import com.jjjx.activity.MyCollectionsActivity;
import com.jjjx.activity.ProfileSettingActivity;
import com.jjjx.activity.VerifyRoleActivity;
import com.jjjx.activity.WaitingVerifyActivity;
import com.jjjx.app.base.XBaseFragment;
import com.jjjx.data.GlideManage;
import com.jjjx.data.okhttp.OkHttpUtils;
import com.jjjx.function.entity.UploadImageModel;
import com.jjjx.function.entity.eventbus.LoginRefreshBus;
import com.jjjx.function.login.LoginActivity;
import com.jjjx.function.my.view.UpdatePassActivity;
import com.jjjx.utils.CacheTask;
import com.jjjx.utils.DpUtil;
import com.jjjx.utils.NToast;
import com.jjjx.widget.CircleImageView;

import net.alhazmy13.mediapicker.Image.ImagePicker;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * @author AMing
 * @date 17/5/8
 * @update xz
 * Company RongCloud
 * <p>
 * 我的fragment
 */
public class MyFragment extends XBaseFragment implements View.OnClickListener {
    /**
     * 用户头像
     */
    CircleImageView circleImageView;
    /**
     * 我要认证
     */
    LinearLayout verifyLayout;
    /**
     * 我的资料
     */
    LinearLayout profileSettingLayout;
    /**
     * 我的收藏
     */
    LinearLayout myCollectionLayout;
    /**
     * 课程管理
     */
    LinearLayout classManageLayout;
    /**
     * 用户名
     */
    TextView mUserName;
    /**
     * 用户id
     */
    private TextView mUserId;
    /**
     * 头部背景
     */
    private ImageView mUserBackgroud;
    /**
     * 用户头像宽高 px
     */
    private int mHeadImageWidth;
    private GlideManage mGlideManage;


    @Override
    protected int getContentView() {
        EventBus.getDefault().register(this);
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View view) {
        mUserBackgroud = find(R.id.fm_user_bg);
        circleImageView = find(R.id.fm_head);
        verifyLayout = find(R.id.fm_want_verify);
        profileSettingLayout = find(R.id.fm_profile_setting);
        LinearLayout updatePass = find(R.id.fm_update_pass);
        myCollectionLayout = find(R.id.fm_my_collection);
        classManageLayout = find(R.id.fm_class_manage);
        //退出登录
        LinearLayout outLogin = find(R.id.fm_out);
        mUserId = find(R.id.fm_id);
        mUserName = find(R.id.fm_name);

        circleImageView.setOnClickListener(this);
        verifyLayout.setOnClickListener(this);
        profileSettingLayout.setOnClickListener(this);
        myCollectionLayout.setOnClickListener(this);
        classManageLayout.setOnClickListener(this);
        updatePass.setOnClickListener(this);
        outLogin.setOnClickListener(this);
    }

    @Override
    protected void xLoad() {
        mGlideManage = new GlideManage(getContext());
        mHeadImageWidth = DpUtil.dip2px(getContext(), 50);
        //用户块添加背景色
        mGlideManage.getRequestManager().load(R.drawable.ico_user_head_bg).centerCrop().placeholder(R.color.app_sub_color).into(mUserBackgroud);

        ProfileSettingActivity.setProfileChangeListener(new ProfileSettingActivity.ProfileChangeListener() {
            @Override
            public void change() {
                mUserName.setText(CacheTask.getInstance().getName());

            }
        });
        onInitData();
    }

    private void onInitData() {
        if (CacheTask.getInstance().isLogin()) {
            mGlideManage.getRequestManager().load(CacheTask.getInstance().getPortrait()).override(mHeadImageWidth, mHeadImageWidth).into(new SimpleTarget<GlideDrawable>() {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    circleImageView.setImageDrawable(resource);
                }
            });
            if (!TextUtils.isEmpty(CacheTask.getInstance().getName())) {
                mUserName.setText(CacheTask.getInstance().getName());
            }
            mUserId.setText("ID:" + CacheTask.getInstance().getUserId());
        } else {
            mGlideManage.getRequestManager().load(R.drawable.ico_user_head).override(mHeadImageWidth, mHeadImageWidth).into(new SimpleTarget<GlideDrawable>() {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    circleImageView.setImageDrawable(resource);
                }
            });
            mUserName.setText("欢迎观临，马上登录");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshPay(LoginRefreshBus refreshBus) {
       if(refreshBus.isRefresh()){
           onInitData();
       }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> mPaths = (List<String>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_PATH);
            File imageFile = new File(mPaths.get(0));
            if (imageFile.exists()) {
                OkHttpUtils.getInstance(getActivity()).uploadImage(CacheTask.getInstance().getUserId(), imageFile, new OkHttpUtils.UploadImageListener() {
                    @Override
                    public void onSuccess(String result) {
                        final UploadImageModel model = JSON.parseObject(result, UploadImageModel.class);
                        App.applicationHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                NToast.shortToast(getActivity(), "上传头像成功");
                                Log.e("MineFragment", Constants.DOMAIN + model.getUrl());
                                Glide.with(getActivity()).load(Constants.DOMAIN + model.getUrl()).into(new SimpleTarget<GlideDrawable>() {
                                    @Override
                                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                        circleImageView.setImageDrawable(resource);
                                    }
                                });
                                CacheTask.getInstance().cachePortrait(Constants.DOMAIN + model.getUrl());
                            }
                        });
                    }

                    @Override
                    public void onFailure(IOException e) {
                        App.applicationHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                NToast.shortToast(getActivity(), "上传头像失败");
                            }
                        });
                    }
                });
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fm_out:
                new AlertDialog.Builder(getActivity())
                        .setTitle("提示")
                        .setMessage("是否退出应用?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                CacheTask.getInstance().clearAllCache();
                                NToast.shortToast(getActivity(), "退出成功");
                                getActivity().finish();
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.fm_update_pass:
                if (!CacheTask.getInstance().isLogin()) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    return;
                }
                //修改密码
                startActivity(new Intent(getActivity(), UpdatePassActivity.class));
                break;
            case R.id.fm_want_verify:
                switch (Integer.parseInt(CacheTask.getInstance().getUserRole())) {
                    case 1:
                        startActivity(new Intent(getActivity(), VerifyRoleActivity.class));
                        break;
                    case 2:
                        NToast.shortToast(getActivity(), "当前已经成功审核为教师身份");
                        break;
                    case 3:
                        NToast.shortToast(getActivity(), "当前已经成功审核为机构身份");
                        break;
                    case 4:
                        startActivity(new Intent(getActivity(), WaitingVerifyActivity.class));
                        break;
                    default:
                        break;
                }
                break;
            case R.id.fm_profile_setting:
                startActivity(new Intent(getActivity(), ProfileSettingActivity.class));
                break;
            case R.id.fm_my_collection://我的收藏
                startActivity(new Intent(getActivity(), MyCollectionsActivity.class));
                break;
            case R.id.fm_class_manage:
                if ("2".equals(CacheTask.getInstance().getUserRole()) || "3".equals(CacheTask.getInstance().getUserRole())) {
                    startActivity(new Intent(getActivity(), ClassManageActivity.class));
                } else {
                    NToast.shortToast(getActivity(), "只有老师和学校才能进行课程发布和管理哦,快来加入吧~^_^");
                }
                break;

            default:
                new ImagePicker.Builder(getActivity())
                        .mode(ImagePicker.Mode.GALLERY)
                        .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                        .directory(ImagePicker.Directory.DEFAULT)
                        .extension(ImagePicker.Extension.PNG)
                        .scale(600, 600)
                        .allowMultipleImages(false)
                        .enableDebuggingMode(true)
                        .build();
                break;
        }

    }


    @Override
    protected void closeFragment() {
        EventBus.getDefault().unregister(this);
    }
}
