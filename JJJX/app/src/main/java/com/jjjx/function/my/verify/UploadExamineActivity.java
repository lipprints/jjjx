package com.jjjx.function.my.verify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jjjx.app.App;
import com.jjjx.R;
import com.jjjx.function.base.BaseActivity;
import com.jjjx.data.okhttp.OkHttpUtils;
import com.jjjx.function.entity.MediaModel;
import com.jjjx.utils.NToast;
import com.jjjx.widget.MediaGridView;

import net.alhazmy13.mediapicker.Image.ImagePicker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.service.notification.Condition.SCHEME;

/**
 *
 * @author AMing
 * @date 17/8/28
 * @update xz
 * Company RongCloud
 * 机构，教师认证
 */
public class UploadExamineActivity extends BaseActivity {

    private List<MediaModel> publishMediaList = new ArrayList<>();

    private MediaGridView mGridView;
    private MediaGridAdapter mediaGridAdapter;
    private int role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examine);
        role = getIntent().getIntExtra("Role", 0);
        setTitle(role == 2 ? "上传教师资质" : "上传机构资质");
        mGridView = (MediaGridView) findViewById(R.id.examine_media_view);
        mediaGridAdapter = new MediaGridAdapter(publishMediaList, this);
        mGridView.setAdapter(mediaGridAdapter);
    }


    private class MediaGridAdapter extends BaseAdapter {

        private List<MediaModel> list;
        Context context;

        public List<MediaModel> getList() {
            return list;
        }

        MediaGridAdapter(List<MediaModel> list, Context context) {
            this.list = list;
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size() + 1;
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            MediaGridAdapter.ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_media, parent, false);
                holder = new MediaGridAdapter.ViewHolder();
                holder.imageView =  convertView.findViewById(R.id.media_image);
                holder.deleteImage = convertView.findViewById(R.id.badge_delete);
                convertView.setTag(holder);
            } else {
                holder = (MediaGridAdapter.ViewHolder) convertView.getTag();
            }
            if (position == getCount() - 1) {
                holder.imageView.setImageResource(R.drawable.rc_plugin_default);
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (list.size() == 9) {
                            NToast.shortToast(context, "最多只能上传 9 张资质申请");
                            return;
                        }
                        image();
                    }
                });
                holder.deleteImage.setVisibility(View.GONE);
            } else {
                //普通媒体文件
                holder.deleteImage.setVisibility(View.VISIBLE);
                final MediaModel mediaModel = list.get(position);
                //TODO 视频第一帧图片需要 + File 图片待定
                holder.imageView.setImageURI("file://" + mediaModel.getDisplayPicturePath());
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        list.remove(position);
                        notifyDataSetChanged();
                    }
                });
            }
            return convertView;
        }

        /**
         * 传入新的数据 刷新UI的方法
         */
        void updateGridView(List<MediaModel> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        void incrementRefresh(MediaModel mediaModel) {
            if (mediaModel != null) {
                this.list.add(mediaModel);
                notifyDataSetChanged();
            }

        }

        private class ViewHolder {
            SimpleDraweeView imageView;
            ImageView deleteImage;
        }
    }

    private void image() {
        new ImagePicker.Builder(this)
                .mode(ImagePicker.Mode.GALLERY)
                .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                .directory(ImagePicker.Directory.DEFAULT)
                .extension(ImagePicker.Extension.PNG)
                .scale(600, 600)
                .allowMultipleImages(false)
                .enableDebuggingMode(true)
                .build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> mPaths = (List<String>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_PATH);
            File imageFile = new File(mPaths.get(0));
            if (imageFile.exists()) {
                mediaGridAdapter.incrementRefresh(new MediaModel(SCHEME + mPaths.get(0), MediaModel.MediaType.IMAGE, imageFile));
            }
        }
    }


    public void uploadExamine(View view) {
        OkHttpUtils.getInstance(mContext).requestRole(String.valueOf(role), publishMediaList, new OkHttpUtils.UploadImageListener() {
            @Override
            public void onSuccess(String result) {
                App.applicationHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        NToast.shortToast(mContext, "上传资质成功,我们工作人员将在 1 个工作日内对您的申请进行审核~");
                        finish();
                    }
                });
            }

            @Override
            public void onFailure(IOException e) {
                App.applicationHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        NToast.shortToast(mContext, "上传失败");
                    }
                });
                NToast.shortToast(mContext, "上传失败");
            }
        });
    }
}
