package com.jjjx.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.afollestad.materialcamera.MaterialCamera;
import com.baidu.location.BDLocation;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jjjx.App;
import com.jjjx.OnBDLocationListener;
import com.jjjx.R;
import com.jjjx.data.okhttp.OkHttpUtils;
import com.jjjx.model.MediaModel;
import com.jjjx.utils.CacheTask;
import com.jjjx.utils.NToast;
import com.jjjx.widget.ListItemTextView;
import com.jjjx.widget.LoadDialog;
import com.jjjx.widget.MediaGridView;
import com.jjjx.widget.WheelView;
import com.jjjx.widget.dialog.AppProgressDialog;

import net.alhazmy13.mediapicker.Image.ImagePicker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


public class PublishActivity extends BaseActivity implements View.OnClickListener, OnBDLocationListener {

    private static final int VIDEO_REQUEST_CODE = 22;
    private MediaGridView mGridView;

    private MediaGridAdapter mediaGridAdapter;

    private List<MediaModel> publishMediaList = new ArrayList<>();
    private static final int CAMERA_RQ = 8099;
    private static final int CHECK_PERMISSION = 8001;
    private static final String SCHEME = "file://";

    private ListItemTextView ageTextView;
    private ListItemTextView classTypeTextView;
    private ListItemTextView personsTextView;
    private ListItemTextView addressTextView;
    private EditText classNameEditText;
    private EditText introduceEditText;
    private EditText feeEditText;
    private EditText timeEditText;
    private EditText phoneEditText;

    String[] ageArray = new String[]{"不限", "3~5", "5~10", "10~15"};
    String[] classTypeArray = new String[]{"不限", "幼儿班", "中小班", "成人班"};
    String[] personsArray = new String[]{"一对一", "一对二", "一对三", "一对四", "一对五", "一对多"};
    String ageString;
    String personsString;
    String classTypeString;
    String addressString;

    private String lng;
    private String lat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        setTitle("发布");
        initView();
    }

    private void initView() {
        mGridView = (MediaGridView) findViewById(R.id.media_view);
        mediaGridAdapter = new MediaGridAdapter(publishMediaList, this);
        mGridView.setAdapter(mediaGridAdapter);

        classNameEditText = (EditText) findViewById(R.id.publish_class_name);
        introduceEditText = (EditText) findViewById(R.id.publish_class_introduce);
        feeEditText = (EditText) findViewById(R.id.publish_fee);
        timeEditText = (EditText) findViewById(R.id.publish_time);
        phoneEditText = (EditText) findViewById(R.id.publish_phone);

        ageTextView = (ListItemTextView) findViewById(R.id.publish_age);
        classTypeTextView = (ListItemTextView) findViewById(R.id.publish_class_type);
        personsTextView = (ListItemTextView) findViewById(R.id.publish_persons);
        addressTextView = (ListItemTextView) findViewById(R.id.publish_address);

        ageTextView.setDetail(ageArray[0]);
        ageString = ageArray[0];

        classTypeTextView.setDetail(classTypeArray[0]);
        classTypeString = classTypeArray[0];

        personsTextView.setDetail(personsArray[0]);
        personsString = personsArray[0];

        ageTextView.setOnClickListener(this);
        classTypeTextView.setOnClickListener(this);
        personsTextView.setOnClickListener(this);
        addressTextView.setOnClickListener(this);
        App.getInstance().addOnBDLocationObserver(this);
        App.getInstance().startLocationObserver();
        Log.e("App", "BDLocation2");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> mPaths = (List<String>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_PATH);
            File imageFile = new File(mPaths.get(0));
            if (imageFile.exists()) {
                mediaGridAdapter.incrementRefresh(new MediaModel(SCHEME + mPaths.get(0), MediaModel.MediaType.IMAGE, imageFile));
            }
        } else if (requestCode == VIDEO_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri selectedVideo = data.getData();
            File videoFile = new File(selectedVideo.getPath());
            if (videoFile.exists()) {
                mediaGridAdapter.incrementRefresh(new MediaModel(getVideoFileFristImage(selectedVideo.getPath()), MediaModel.MediaType.VIDEO, videoFile));
            }
        } else if (requestCode == CAMERA_RQ && resultCode == RESULT_OK) {
            try {
                String filePath = data.getStringExtra("videoUrl");
                Log.e("lzf_video", filePath);
                if (filePath != null && !filePath.equals("")) {
                    if (filePath.startsWith("file://")) {
                        filePath = data.getStringExtra("videoUrl").substring(7, filePath.length());
                        Log.e("video", "视频保存在：" + filePath);
                        File videoFile = new File(filePath);
                        if (videoFile.exists()) {
                            mediaGridAdapter.incrementRefresh(new MediaModel(getVideoFileFristImage(filePath), MediaModel.MediaType.VIDEO, videoFile));
                        }
                    }
                }

            } catch (Exception ex) {

            }
        } else if (requestCode == 98 && resultCode == 99) {
            addressString = data.getStringExtra("classAddress");
            addressTextView.setDetail(addressString);
        }
    }


    public void upload(View view) {
        String className = classNameEditText.getText().toString().trim();
        if (TextUtils.isEmpty(className)) {
            NToast.shortToast(this, "课程名不能为空");
            return;
        }
        String time = timeEditText.getText().toString().trim();
        if (TextUtils.isEmpty(time)) {
            NToast.shortToast(this, "授课时间不能为空");
            return;
        }
        String phone = phoneEditText.getText().toString().trim();

        if (TextUtils.isEmpty(phone)) {
            NToast.shortToast(this, "联系方式不能为空");
            return;
        }

        String fee = feeEditText.getText().toString().trim();
        if (TextUtils.isEmpty(fee)) {
            NToast.shortToast(this, "课时费不能为空");
            return;
        }

        if (publishMediaList.size() == 0) {
            NToast.shortToast(this, "至少上传一张课程介绍图片或一段课程介绍视频");
            return;
        }

        AppProgressDialog.show(mContext, "正在上传请稍后...");
        OkHttpUtils.getInstance(this).publish(CacheTask.getInstance().getUserId(), publishMediaList, className, introduceEditText.getText().toString(), fee, ageString, personsString, time, addressString, phone, lng, lat, new OkHttpUtils.UploadImageListener() {
            @Override
            public void onSuccess(String result) {
                App.applicationHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        NToast.shortToast(PublishActivity.this, "发布成功");
                        AppProgressDialog.onDismiss();
                        if (mRefershDataListener != null) {
                            mRefershDataListener.refresh();
                        }
                        finish();
                    }
                });

            }

            @Override
            public void onFailure(IOException e) {
                App.applicationHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        LoadDialog.dismiss(PublishActivity.this);
                        NToast.shortToast(PublishActivity.this, "发布失败");
                    }
                });
            }
        });

    }

    private void record() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            startRecordVideo();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO}, CHECK_PERMISSION);
        }
    }

    public void startRecordVideo() {
        File saveDir;
        saveDir = new File(Environment.getExternalStorageDirectory(), "MaterialCamera");
        saveDir.mkdirs();
        MaterialCamera materialCamera = new MaterialCamera(this)
                .saveDir(saveDir)
                .showPortraitWarning(true)
                .allowRetry(true)
                .defaultToFrontFacing(true)
                .allowRetry(true)
                .autoSubmit(false)
                .labelConfirm(R.string.mcam_use_video);
        /*
        * code  返回code
        * showGuide 是否显示引导
        * */
        materialCamera.start(CAMERA_RQ, false);

//        new MaterialCamera(this)                               // Constructor takes an Activity
//                .allowRetry(true)                                  // Whether or not 'Retry' is visible during playback 在回放期间是否可以看到“重试”
//                .autoSubmit(false)                                 // Whether or not user is allowed to playback videos after recording. 是否允许用户在录制后播放视频？This can affect other things, discussed in the next section.
//                .saveDir(saveFolder)                               // The folder recorded videos are saved to 文件夹录制的视频被保存到
//                .primaryColorAttr(R.attr.colorPrimary)             // The theme color used for the camera, defaults to colorPrimary of Activity in the constructor
//                .showPortraitWarning(true)                         // Whether or not a warning is displayed if the user presses record in portrait orientation 如果用户按纵向定位记录，是否显示警告
//                .defaultToFrontFacing(false)                       // Whether or not the camera will initially show the front facing camera  相机是否会首先显示前置摄像头
//                .allowChangeCamera(true)                           // Allows the user to change cameras.  允许用户更改相机。
//                .retryExits(false)                                 // If true, the 'Retry' button in the playback screen will exit the camera instead of going back to the recorder 如果为真，则回放屏幕中的“重试”按钮将退出摄像机，而不是回到记录器。
//                .restartTimerOnRetry(false)                        // If true, the countdown timer is reset to 0 when the user taps 'Retry' in playback 如果为真，则当用户在回放中点击“重试”时，倒计时计时器将重置为0。
//                .continueTimerInPlayback(false)                    // If true, the countdown timer will continue to go down during playback, rather than pausing. 如果是真的，倒计时计时器将在回放期间继续下去，而不是暂停。
//                .videoEncodingBitRate(1024000)                     // Sets a custom bit rate for video recording. 设置视频录制的自定义比特率。
//                .audioEncodingBitRate(50000)                       // Sets a custom bit rate for audio recording.
//                .videoFrameRate(24)                                // Sets a custom frame rate (FPS) for video recording.设置用于视频记录的自定义帧速率（fps）。
//                .qualityProfile(MaterialCamera.QUALITY_HIGH)       // 设置质量配置文件，手动设置比特率或帧速率与其他设置将覆盖个人质量配置文件设置。
//                .videoPreferredHeight(720)                         // Sets a preferred height for the recorded video output. 为录制的视频输出设置一个首选高度。
//                .videoPreferredAspect(4f / 3f)                     // Sets a preferred aspect ratio for the recorded video output.为录制的视频输出设置一个优先的长宽比。
//                .maxAllowedFileSize(1024 * 1024 * 5)               // 设置最大文件大小为5，记录文件是否达到这个值停止。记住，FAT文件系统有4GB文件大小限制。
//                .iconRecord(R.drawable.mcam_action_capture)        // Sets a custom icon for the button used to start recording
//                .iconStop(R.drawable.mcam_action_stop)             // Sets a custom icon for the button used to stop recording
//                .iconFrontCamera(R.drawable.mcam_camera_front)     // Sets a custom icon for the button used to switch to the front camera
//                .iconRearCamera(R.drawable.mcam_camera_rear)       // Sets a custom icon for the button used to switch to the rear camera
//                .iconPlay(R.drawable.evp_action_play)              // Sets a custom icon used to start playback
//                .iconPause(R.drawable.evp_action_pause)            // Sets a custom icon used to pause playback
//                .iconRestart(R.drawable.evp_action_restart)        // Sets a custom icon used to restart playback
//                .labelRetry(R.string.mcam_retry)                   // Sets a custom button label for the button used to retry recording, when available
//                .labelConfirm(R.string.mcam_use_video)             // Sets a custom button label for the button used to confirm/submit a recording
//                .autoRecordWithDelaySec(5)                         // 摄像机将在5秒倒数后自动开始录制。这将禁用前端和背面摄像机之间的切换。
//                .autoRecordWithDelayMs(5000)                       // 与上面一样，用毫秒表示，而不是秒。
//                .audioDisabled(false)                              // 设置为真实录制视频没有任何音频。
//                .start(CAMERA_RQ);                                 // Starts the camera activity, the result will be sent back to the current Activity
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.publish_age:
                List<String> ageList = Arrays.asList(ageArray);
                final View mainClassView = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
                WheelView mainWheelView = (WheelView) mainClassView.findViewById(R.id.wheel_view_wv);
                mainWheelView.setOffset(2);
                mainWheelView.setItems(ageList);
                mainWheelView.setSeletion(0);
                mainWheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        ageString = item;
                    }
                });

                new AlertDialog.Builder(this)
                        .setTitle("适学年龄")
                        .setView(mainClassView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ageTextView.setDetail(TextUtils.isEmpty(ageString) ? ageArray[0] : ageString);
                            }
                        })
                        .show();
                break;
            case R.id.publish_class_type:
                List<String> classTypeList = Arrays.asList(classTypeArray);
                final View classTypeView = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
                WheelView classTypeWheelView = (WheelView) classTypeView.findViewById(R.id.wheel_view_wv);
                classTypeWheelView.setOffset(2);
                classTypeWheelView.setItems(classTypeList);
                classTypeWheelView.setSeletion(0);
                classTypeWheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        classTypeString = item;
                    }
                });

                new AlertDialog.Builder(this)
                        .setTitle("班级选择")
                        .setView(classTypeView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                classTypeTextView.setDetail(TextUtils.isEmpty(classTypeString) ? classTypeArray[0] : classTypeString);
                            }
                        })
                        .show();
                break;
            case R.id.publish_persons:
                List<String> personsList = Arrays.asList(personsArray);
                final View personsView = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
                WheelView personsWheelView = (WheelView) personsView.findViewById(R.id.wheel_view_wv);
                personsWheelView.setOffset(2);
                personsWheelView.setItems(personsList);
                personsWheelView.setSeletion(0);
                personsWheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        personsString = item;
                    }
                });

                new AlertDialog.Builder(this)
                        .setTitle("授课人数")
                        .setView(personsView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                personsTextView.setDetail(TextUtils.isEmpty(personsString) ? personsArray[0] : personsString);
                            }
                        })
                        .show();
                break;
            case R.id.publish_address:
                Intent intent = new Intent(this, ClassModeActivity.class);
                startActivityForResult(intent, 98);
                break;
        }
    }

    @Override
    public void onLocation(BDLocation bdLocation) {
        Log.e("App", "BDLocation2");
        if (bdLocation != null) {
            lng = String.valueOf(bdLocation.getLongitude());
            lat = String.valueOf(bdLocation.getLatitude());
        }
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
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_media, parent, false);
                holder = new ViewHolder();
                holder.imageView = (SimpleDraweeView) convertView.findViewById(R.id.media_image);
                holder.deleteImage = (ImageView) convertView.findViewById(R.id.badge_delete);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            //添加视频按钮
            if (position == getCount() - 1) {
                holder.imageView.setImageResource(R.drawable.rc_voip_icon_input_video);
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (list.size() > 0) {
                            for (MediaModel mediaModel : list) {
                                if (mediaModel.getType() == MediaModel.MediaType.VIDEO) {
                                    NToast.shortToast(context, "最多只能上传一个视频");
                                    return;
                                }
                            }

                        }
                        record();
                    }
                });
                holder.deleteImage.setVisibility(View.GONE);
            } else if (position == getCount() - 2) {
                holder.imageView.setImageResource(R.drawable.rc_plugin_default);
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

    public String getVideoFileFristImage(String videoString) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(videoString);
        Bitmap bitmap = media.getFrameAtTime();
        return saveBitmap(this, bitmap);
    }

    private static final String SD_PATH = "/sdcard/dskqxt/pic/";
    private static final String IN_PATH = "/dskqxt/pic/";

    public String saveBitmap(Context context, Bitmap mBitmap) {
        String savePath;
        File filePic;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            savePath = SD_PATH;
        } else {
            savePath = context.getApplicationContext().getFilesDir()
                    .getAbsolutePath()
                    + IN_PATH;
        }
        try {
            filePic = new File(savePath + generateFileName() + ".jpg");
            //TODO 每次文件重新生成 此处待改造
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return filePic.getAbsolutePath();
    }

    /**
     * 随机生产文件名
     *
     * @return
     */
    private static String generateFileName() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CHECK_PERMISSION
                && grantResults.length == 4
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED
                && grantResults[2] == PackageManager.PERMISSION_GRANTED
                && grantResults[3] == PackageManager.PERMISSION_GRANTED) {
            startRecordVideo();
        }
    }

    private static RefreshDataListener mRefershDataListener;

    public interface RefreshDataListener {
        void refresh();
    }

    public static void setRefreshDataListener(RefreshDataListener refershDataListener) {
        mRefershDataListener = refershDataListener;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getInstance().removeOnBDLocationObserver(this);
    }
}
