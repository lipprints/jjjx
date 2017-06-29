package com.jjjx.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jjjx.App;
import com.jjjx.Constants;
import com.jjjx.R;
import com.jjjx.data.okhttp.OkHttpUtils;
import com.jjjx.model.UploadImageModel;
import com.jjjx.utils.CacheTask;
import com.jjjx.utils.NToast;
import com.jjjx.widget.CircleImageView;
import com.jjjx.widget.ListItemTextView;

import net.alhazmy13.mediapicker.Image.ImagePicker;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by AMing on 17/5/8.
 * Company RongCloud
 */
public class MineFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    CircleImageView circleImageView;
    ListItemTextView quitTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mine, container, false);
        circleImageView = (CircleImageView) v.findViewById(R.id.jx_user_head);
        quitTextView = (ListItemTextView) v.findViewById(R.id.mine_quit);
        circleImageView.setOnClickListener(this);
        quitTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CacheTask.getInstance().clearAllCache();
                NToast.shortToast(getActivity(), "退出成功");
                getActivity().finish();
            }
        });
        return v;
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
        new ImagePicker.Builder(getActivity())
                .mode(ImagePicker.Mode.GALLERY)
                .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                .directory(ImagePicker.Directory.DEFAULT)
                .extension(ImagePicker.Extension.PNG)
                .scale(600, 600)
                .allowMultipleImages(false)
                .enableDebuggingMode(true)
                .build();
    }
}
