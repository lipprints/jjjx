package com.jjjx.function.entity;

import java.io.File;

/**
 * Created by AMing on 17/5/12.
 * Company RongCloud
 */

public class MediaModel {
    /**
     * 如果是视频文件则展示第一帧
     */
    private String displayPicturePath;

    private MediaType type;

    private File mediaFile;

    public MediaModel(String displayPicturePath, MediaType type, File mediaFile) {
        this.displayPicturePath = displayPicturePath;
        this.type = type;
        this.mediaFile = mediaFile;
    }

    public File getMediaFile() {
        return mediaFile;
    }

    public void setMediaFile(File mediaFile) {
        this.mediaFile = mediaFile;
    }

    public String getDisplayPicturePath() {
        return displayPicturePath;
    }

    public void setDisplayPicturePath(String displayPicturePath) {
        this.displayPicturePath = displayPicturePath;
    }

    public MediaType getType() {
        return type;
    }

    public void setType(MediaType type) {
        this.type = type;
    }

    public enum MediaType {
        IMAGE,
        VIDEO;
    }
}
