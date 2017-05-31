package com.jjjx.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jjjx.R;
import com.jjjx.model.MediaModel;
import com.jjjx.utils.NToast;
import com.jjjx.widget.MediaGridView;

import net.alhazmy13.mediapicker.Image.ImagePicker;
import net.alhazmy13.mediapicker.Video.VideoPicker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class PublishActivity extends BaseActivity {

    private MediaGridView mGridView;

    private MediaGridAdapter mediaGridAdapter;

    private List<MediaModel> publishMediaList = new ArrayList<>();

    private Boolean isContainsVideo;

    private static final String SCHEME = "file://";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        mGridView = (MediaGridView) findViewById(R.id.media_view);
        mediaGridAdapter = new MediaGridAdapter(publishMediaList, this);
        mGridView.setAdapter(mediaGridAdapter);
    }

    private void image() {
        new ImagePicker.Builder(PublishActivity.this)
                .mode(ImagePicker.Mode.GALLERY)
                .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                .directory(ImagePicker.Directory.DEFAULT)
                .extension(ImagePicker.Extension.PNG)
                .scale(600, 600)
                .allowMultipleImages(false)
                .enableDebuggingMode(true)
                .build();
    }

    private void video() {
        new VideoPicker.Builder(PublishActivity.this)
                .mode(VideoPicker.Mode.GALLERY)
                .directory(VideoPicker.Directory.DEFAULT)
                .extension(VideoPicker.Extension.MP4)
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
        } else if (requestCode == VideoPicker.VIDEO_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> mPaths = (List<String>) data.getSerializableExtra(VideoPicker.EXTRA_VIDEO_PATH);
            File videoFile = new File(mPaths.get(0));
            if (videoFile.exists()) {
                publishMediaList.add(new MediaModel(SCHEME + mPaths.get(0), MediaModel.MediaType.VIDEO, videoFile));
            }
        }
    }


    private class MediaGridAdapter extends BaseAdapter {

        private List<MediaModel> list;
        Context context;


        MediaGridAdapter(List<MediaModel> list, Context context) {
            this.list = list;
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size() + 2;
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
            Log.d("rym_dg", "pos = " + position + ", count = " + list.size() + ", convertView = " + convertView);
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_media, parent, false);
                holder = new ViewHolder();
                holder.imageView = (SimpleDraweeView) convertView.findViewById(R.id.media_image);
                holder.deleteImage = (ImageView) convertView.findViewById(R.id.badge_delete);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Log.d("rym_dg", "holder.imageView = " + holder.imageView);
            //添加视频按钮
            if (position == getCount() - 1) {
                holder.imageView.setImageResource(R.drawable.add_video);
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NToast.shortToast(PublishActivity.this, "add 视频");
                    }
                });
                holder.deleteImage.setVisibility(View.GONE);
            } else if (position == getCount() - 2) {
                holder.imageView.setImageResource(R.drawable.add_image);
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        image();
                    }
                });
                holder.deleteImage.setVisibility(View.GONE);
            } else {
                //普通媒体文件
                holder.deleteImage.setVisibility(View.VISIBLE);
                final MediaModel mediaModel = list.get(position);
                if (mediaModel.getType() == MediaModel.MediaType.IMAGE) {
                    holder.imageView.setImageURI(mediaModel.getDisplayPicturePath());
                    holder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            list.remove(position);
                            notifyDataSetChanged();
                        }
                    });
                }
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
}
