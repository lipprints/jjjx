package com.jjjx.data.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by AMing on 17/8/6.
 * Company RongCloud
 */
public class FindDataResponse {

    /**
     * head : {"msg":"查询成功！","code":"10000"}
     * para : {"discoverInfo":[{"firstFrame":"http://47.93.217.163:8080/faxian/videoImageFile1501997222539.png","user_id":100012,"name":"周先生","pid":100011,"video":"http://47.93.217.163:8080/faxian/VID_20170806_132654.mp4","content":"哈哈哈","head_portrait":"http://47.93.217.163:8080/geren/fa9fc491-018c-41cb-a314-618522ffa2e4.png"},{"user_id":100012,"name":"周先生","pid":100012,"content":"哈比比","picture":"http://47.93.217.163:8080/faxian/ae7b684f-3d9f-4820-b08f-fd5de9f74c6a.png","head_portrait":"http://47.93.217.163:8080/geren/fa9fc491-018c-41cb-a314-618522ffa2e4.png"},{"firstFrame":"http://47.93.217.163:8080/faxian/videoImageFile1501997962034.png","user_id":100012,"name":"周先生","pid":100013,"video":"http://47.93.217.163:8080/faxian/VID_20170806_133915.mp4","content":"进去","head_portrait":"http://47.93.217.163:8080/geren/fa9fc491-018c-41cb-a314-618522ffa2e4.png"},{"user_id":100012,"name":"周先生","pid":100014,"content":"积极","picture":"http://47.93.217.163:8080/faxian/03c92042-1637-4a40-9de6-fc73c22ce240.png","head_portrait":"http://47.93.217.163:8080/geren/fa9fc491-018c-41cb-a314-618522ffa2e4.png"},{"user_id":100012,"name":"周先生","pid":100015,"content":"","picture":"http://47.93.217.163:8080/faxian/a66a7b92-548e-43d1-aee1-d2229474129c.png","head_portrait":"http://47.93.217.163:8080/geren/fa9fc491-018c-41cb-a314-618522ffa2e4.png"},{"firstFrame":"http://47.93.217.163:8080/faxian/videoImageFile1501998020363.png","user_id":100012,"name":"周先生","pid":100016,"video":"http://47.93.217.163:8080/faxian/VID_20170806_134014.mp4","content":"数字","head_portrait":"http://47.93.217.163:8080/geren/fa9fc491-018c-41cb-a314-618522ffa2e4.png"},{"user_id":100012,"name":"周先生","pid":100017,"content":"","picture":"http://47.93.217.163:8080/faxian/98a790e7-dc09-4e83-87e2-24518cf22f51.png","head_portrait":"http://47.93.217.163:8080/geren/fa9fc491-018c-41cb-a314-618522ffa2e4.png"},{"firstFrame":"http://47.93.217.163:8080/faxian/videoImageFile1501998051114.png","user_id":100012,"name":"周先生","pid":100018,"video":"http://47.93.217.163:8080/faxian/VID_20170806_134040.mp4","content":"题库","head_portrait":"http://47.93.217.163:8080/geren/fa9fc491-018c-41cb-a314-618522ffa2e4.png"},{"user_id":100012,"name":"周先生","pid":100019,"content":"糊涂","picture":"http://47.93.217.163:8080/faxian/6b79635f-828d-4f86-92e9-6bce3a6e59bf.png","head_portrait":"http://47.93.217.163:8080/geren/fa9fc491-018c-41cb-a314-618522ffa2e4.png"},{"user_id":100012,"name":"周先生","pid":100020,"content":"捅哦开","picture":"http://47.93.217.163:8080/faxian/583a7e63-87ff-4aa0-a11b-9e6eb4699ca9.png","head_portrait":"http://47.93.217.163:8080/geren/fa9fc491-018c-41cb-a314-618522ffa2e4.png"},{"user_id":100012,"name":"周先生","pid":100021,"content":"哦对","picture":"http://47.93.217.163:8080/faxian/ecab0c33-e58e-4252-9446-ce69d7c1fce3.png","head_portrait":"http://47.93.217.163:8080/geren/fa9fc491-018c-41cb-a314-618522ffa2e4.png"},{"firstFrame":"http://47.93.217.163:8080/faxian/videoImageFile1501998100146.png","user_id":100012,"name":"周先生","pid":100022,"video":"http://47.93.217.163:8080/faxian/VID_20170806_134130.mp4","content":"顾虑","head_portrait":"http://47.93.217.163:8080/geren/fa9fc491-018c-41cb-a314-618522ffa2e4.png"},{"firstFrame":"http://47.93.217.163:8080/faxian/videoImageFile1501998122150.png","user_id":100012,"name":"周先生","pid":100023,"video":"http://47.93.217.163:8080/faxian/VID_20170806_134152.mp4","content":"","head_portrait":"http://47.93.217.163:8080/geren/fa9fc491-018c-41cb-a314-618522ffa2e4.png"},{"firstFrame":"http://47.93.217.163:8080/faxian/videoImageFile1501998145055.png","user_id":100012,"name":"周先生","pid":100024,"video":"http://47.93.217.163:8080/faxian/VID_20170806_134210.mp4","content":"","head_portrait":"http://47.93.217.163:8080/geren/fa9fc491-018c-41cb-a314-618522ffa2e4.png"},{"firstFrame":"http://47.93.217.163:8080/faxian/videoImageFile1501998185927.png","user_id":100012,"name":"周先生","pid":100025,"video":"http://47.93.217.163:8080/faxian/VID_20170806_134236.mp4","content":"","head_portrait":"http://47.93.217.163:8080/geren/fa9fc491-018c-41cb-a314-618522ffa2e4.png"},{"firstFrame":"http://47.93.217.163:8080/faxian/videoImageFile1501998201995.png","user_id":100012,"name":"周先生","pid":100026,"video":"http://47.93.217.163:8080/faxian/VID_20170806_134315.mp4","content":"","head_portrait":"http://47.93.217.163:8080/geren/fa9fc491-018c-41cb-a314-618522ffa2e4.png"},{"user_id":100012,"name":"周先生","pid":100027,"content":"你以为","picture":"http://47.93.217.163:8080/faxian/ca0484e1-8fdf-4e0b-8cc2-a00edb1dcab8.png","head_portrait":"http://47.93.217.163:8080/geren/fa9fc491-018c-41cb-a314-618522ffa2e4.png"},{"firstFrame":"http://47.93.217.163:8080/faxian/videoImageFile1501999358196.png","user_id":100012,"name":"周先生","pid":100028,"video":"http://47.93.217.163:8080/faxian/VID_20170806_140229.mp4","content":"噢耶了我了","head_portrait":"http://47.93.217.163:8080/geren/fa9fc491-018c-41cb-a314-618522ffa2e4.png"},{"user_id":100012,"name":"周先生","pid":100029,"content":"民族","picture":"http://47.93.217.163:8080/faxian/5104535a-68ef-496a-a993-a88577c6582f.png","head_portrait":"http://47.93.217.163:8080/geren/fa9fc491-018c-41cb-a314-618522ffa2e4.png"},{"user_id":100012,"name":"周先生","pid":100030,"content":"哦了也用咯","picture":"http://47.93.217.163:8080/faxian/6c3325bc-16c8-4526-8d39-2e48ca009396.png","head_portrait":"http://47.93.217.163:8080/geren/fa9fc491-018c-41cb-a314-618522ffa2e4.png"}]}
     */

    private HeadEntity head;
    private ParaEntity para;

    public HeadEntity getHead() {
        return head;
    }

    public void setHead(HeadEntity head) {
        this.head = head;
    }

    public ParaEntity getPara() {
        return para;
    }

    public void setPara(ParaEntity para) {
        this.para = para;
    }

    public static class HeadEntity {
        /**
         * msg : 查询成功！
         * code : 10000
         */

        private String msg;
        private String code;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    public static class ParaEntity {
        private List<DiscoverInfoEntity> discoverInfo;

        public List<DiscoverInfoEntity> getDiscoverInfo() {
            return discoverInfo;
        }

        public void setDiscoverInfo(List<DiscoverInfoEntity> discoverInfo) {
            this.discoverInfo = discoverInfo;
        }

        public static class DiscoverInfoEntity implements Parcelable{
            /**
             * firstFrame : http://47.93.217.163:8080/faxian/videoImageFile1501997222539.png
             * user_id : 100012
             * name : 周先生
             * pid : 100011
             * video : http://47.93.217.163:8080/faxian/VID_20170806_132654.mp4
             * content : 哈哈哈
             * head_portrait : http://47.93.217.163:8080/geren/fa9fc491-018c-41cb-a314-618522ffa2e4.png
             * picture : http://47.93.217.163:8080/faxian/ae7b684f-3d9f-4820-b08f-fd5de9f74c6a.png
             */

            private String firstFrame;
            private int user_id;
            private String name;
            private int pid;
            private String video;
            private String content;
            private String head_portrait;
            private String picture;

            protected DiscoverInfoEntity(Parcel in) {
                firstFrame = in.readString();
                user_id = in.readInt();
                name = in.readString();
                pid = in.readInt();
                video = in.readString();
                content = in.readString();
                head_portrait = in.readString();
                picture = in.readString();
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(firstFrame);
                dest.writeInt(user_id);
                dest.writeString(name);
                dest.writeInt(pid);
                dest.writeString(video);
                dest.writeString(content);
                dest.writeString(head_portrait);
                dest.writeString(picture);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public static final Creator<DiscoverInfoEntity> CREATOR = new Creator<DiscoverInfoEntity>() {
                @Override
                public DiscoverInfoEntity createFromParcel(Parcel in) {
                    return new DiscoverInfoEntity(in);
                }

                @Override
                public DiscoverInfoEntity[] newArray(int size) {
                    return new DiscoverInfoEntity[size];
                }
            };

            public String getFirstFrame() {
                return firstFrame;
            }

            public void setFirstFrame(String firstFrame) {
                this.firstFrame = firstFrame;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getPid() {
                return pid;
            }

            public void setPid(int pid) {
                this.pid = pid;
            }

            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getHead_portrait() {
                return head_portrait;
            }

            public void setHead_portrait(String head_portrait) {
                this.head_portrait = head_portrait;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }
        }
    }
}
