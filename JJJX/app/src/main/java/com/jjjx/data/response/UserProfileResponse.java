package com.jjjx.data.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AMing on 17/8/31.
 * Company RongCloud
 */
public class UserProfileResponse {

    /**
     * head : {"msg":"查询成功！","code":"10000"}
     * para : {"tab":"2","courseRelease":[{"classFee":"369","collects":0,"contactNumber":"987","courseName":"噢耶屎","id":100044,"lat":"40.062332","lng":"116.412902","picture":"geren\\755a331c-80c4-4fd0-9943-4ddea3174e78.png","rightAge":"不限","synopsis":"明","teachingAddress":"在北京北附近","teachingDate":"你以为","teachingNumber":"一对一","teachingTime":1502625997000,"user_id":"100012"},{"classFee":"666","collects":0,"contactNumber":"666","courseName":"墨迹","id":100045,"lat":"40.062332","lng":"116.412902","picture":"geren\\05bc337c-5f50-460c-b341-fe5e4c7a854f.png","rightAge":"不限","synopsis":"噢耶屎","teachingAddress":"在北京北附近","teachingDate":"你在","teachingNumber":"一对一","teachingTime":1502626030000,"user_id":"100012"},{"classFee":"369","collects":1,"contactNumber":"987","courseName":"你一","id":100046,"lat":"40.062298","lng":"116.412974","picture":"geren\\fc11b2a3-965d-448e-88cc-0d90e2d9523a.png","rightAge":"不限","synopsis":"你一","teachingAddress":"在北京北附近","teachingDate":"你屋","teachingNumber":"一对一","teachingTime":1502626090000,"user_id":"100012"},{"classFee":"36","collects":1,"contactNumber":"369","courseName":"你五一","id":100047,"lat":"40.038411","lng":"116.424114","picture":"geren\\5c5cd550-8aa1-4302-a4ae-84985a791555.png","rightAge":"不限","synopsis":"议息","teachingAddress":"在北辰新纪元大厦附近","teachingDate":"嗯","teachingNumber":"一对一","teachingTime":1502677691000,"user_id":"100012"}],"discoverInfo":[{"firstFrame":"http://47.93.217.163:8080/faxian/videoImageFile1501997222539.png","pid":100011,"video":"http://47.93.217.163:8080/faxian/VID_20170806_132654.mp4","content":"哈哈哈"},{"pid":100012,"content":"哈比比","picture":"http://47.93.217.163:8080/faxian/ae7b684f-3d9f-4820-b08f-fd5de9f74c6a.png"},{"firstFrame":"http://47.93.217.163:8080/faxian/videoImageFile1501997962034.png","pid":100013,"video":"http://47.93.217.163:8080/faxian/VID_20170806_133915.mp4","content":"进去"},{"pid":100014,"content":"积极","picture":"http://47.93.217.163:8080/faxian/03c92042-1637-4a40-9de6-fc73c22ce240.png"},{"pid":100015,"content":"","picture":"http://47.93.217.163:8080/faxian/a66a7b92-548e-43d1-aee1-d2229474129c.png"},{"firstFrame":"http://47.93.217.163:8080/faxian/videoImageFile1501998020363.png","pid":100016,"video":"http://47.93.217.163:8080/faxian/VID_20170806_134014.mp4","content":"数字"},{"pid":100017,"content":"","picture":"http://47.93.217.163:8080/faxian/98a790e7-dc09-4e83-87e2-24518cf22f51.png"},{"firstFrame":"http://47.93.217.163:8080/faxian/videoImageFile1501998051114.png","pid":100018,"video":"http://47.93.217.163:8080/faxian/VID_20170806_134040.mp4","content":"题库"},{"pid":100019,"content":"糊涂","picture":"http://47.93.217.163:8080/faxian/6b79635f-828d-4f86-92e9-6bce3a6e59bf.png"},{"pid":100020,"content":"捅哦开","picture":"http://47.93.217.163:8080/faxian/583a7e63-87ff-4aa0-a11b-9e6eb4699ca9.png"},{"pid":100021,"content":"哦对","picture":"http://47.93.217.163:8080/faxian/ecab0c33-e58e-4252-9446-ce69d7c1fce3.png"},{"firstFrame":"http://47.93.217.163:8080/faxian/videoImageFile1501998100146.png","pid":100022,"video":"http://47.93.217.163:8080/faxian/VID_20170806_134130.mp4","content":"顾虑"},{"firstFrame":"http://47.93.217.163:8080/faxian/videoImageFile1501998122150.png","pid":100023,"video":"http://47.93.217.163:8080/faxian/VID_20170806_134152.mp4","content":""},{"firstFrame":"http://47.93.217.163:8080/faxian/videoImageFile1501998145055.png","pid":100024,"video":"http://47.93.217.163:8080/faxian/VID_20170806_134210.mp4","content":""},{"firstFrame":"http://47.93.217.163:8080/faxian/videoImageFile1501998185927.png","pid":100025,"video":"http://47.93.217.163:8080/faxian/VID_20170806_134236.mp4","content":""},{"firstFrame":"http://47.93.217.163:8080/faxian/videoImageFile1501998201995.png","pid":100026,"video":"http://47.93.217.163:8080/faxian/VID_20170806_134315.mp4","content":""},{"pid":100027,"content":"你以为","picture":"http://47.93.217.163:8080/faxian/ca0484e1-8fdf-4e0b-8cc2-a00edb1dcab8.png"},{"firstFrame":"http://47.93.217.163:8080/faxian/videoImageFile1501999358196.png","pid":100028,"video":"http://47.93.217.163:8080/faxian/VID_20170806_140229.mp4","content":"噢耶了我了"},{"pid":100029,"content":"民族","picture":"http://47.93.217.163:8080/faxian/5104535a-68ef-496a-a993-a88577c6582f.png"},{"pid":100030,"content":"哦了也用咯","picture":"http://47.93.217.163:8080/faxian/6c3325bc-16c8-4526-8d39-2e48ca009396.png"},{"firstFrame":"http://47.93.217.163:8080/faxian/videoImageFile1501999442704.png","pid":100031,"video":"http://47.93.217.163:8080/faxian/VID_20170806_140355.mp4","content":"你在"},{"pid":100032,"content":"地主爷","picture":"http://47.93.217.163:8080/faxian/5a4da468-606e-4830-8512-d098b2ae3b13.png"},{"pid":100033,"content":"我是图片","picture":"http://47.93.217.163:8080/faxian/f155ffb9-8474-435a-847b-31f338e2165a.png"},{"firstFrame":"http://47.93.217.163:8080/faxian/videoImageFile1502161598081.png","pid":100034,"video":"http://47.93.217.163:8080/faxian/VID_20170808_110628.mp4","content":""},{"pid":100035,"content":"面的","picture":"http://47.93.217.163:8080/faxian/fbbb281b-ec8d-497a-b168-330839abd18a.png"},{"firstFrame":"http://47.93.217.163:8080/faxian/videoImageFile1502680909780.png","pid":100036,"video":"http://47.93.217.163:8080/faxian/VID_20170814_112131.mp4","content":"不错哦"}],"user":{"city":"北京","createtime":1501344000000,"gender":"女","head_portrait":"http://47.93.217.163:8080/geren/fa9fc491-018c-41cb-a314-618522ffa2e4.png","loginName":"18618268584","name":"周先生","password":"96e79218965eb72c92a549dd5a330112","phone":"18618268584","role":"4","updatetime":1501344000000,"user_id":100012}}
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
        /**
         * tab : 2
         * courseRelease : [{"classFee":"369","collects":0,"contactNumber":"987","courseName":"噢耶屎","id":100044,"lat":"40.062332","lng":"116.412902","picture":"geren\\755a331c-80c4-4fd0-9943-4ddea3174e78.png","rightAge":"不限","synopsis":"明","teachingAddress":"在北京北附近","teachingDate":"你以为","teachingNumber":"一对一","teachingTime":1502625997000,"user_id":"100012"},{"classFee":"666","collects":0,"contactNumber":"666","courseName":"墨迹","id":100045,"lat":"40.062332","lng":"116.412902","picture":"geren\\05bc337c-5f50-460c-b341-fe5e4c7a854f.png","rightAge":"不限","synopsis":"噢耶屎","teachingAddress":"在北京北附近","teachingDate":"你在","teachingNumber":"一对一","teachingTime":1502626030000,"user_id":"100012"},{"classFee":"369","collects":1,"contactNumber":"987","courseName":"你一","id":100046,"lat":"40.062298","lng":"116.412974","picture":"geren\\fc11b2a3-965d-448e-88cc-0d90e2d9523a.png","rightAge":"不限","synopsis":"你一","teachingAddress":"在北京北附近","teachingDate":"你屋","teachingNumber":"一对一","teachingTime":1502626090000,"user_id":"100012"},{"classFee":"36","collects":1,"contactNumber":"369","courseName":"你五一","id":100047,"lat":"40.038411","lng":"116.424114","picture":"geren\\5c5cd550-8aa1-4302-a4ae-84985a791555.png","rightAge":"不限","synopsis":"议息","teachingAddress":"在北辰新纪元大厦附近","teachingDate":"嗯","teachingNumber":"一对一","teachingTime":1502677691000,"user_id":"100012"}]
         * discoverInfo : [{"firstFrame":"http://47.93.217.163:8080/faxian/videoImageFile1501997222539.png","pid":100011,"video":"http://47.93.217.163:8080/faxian/VID_20170806_132654.mp4","content":"哈哈哈"},{"pid":100012,"content":"哈比比","picture":"http://47.93.217.163:8080/faxian/ae7b684f-3d9f-4820-b08f-fd5de9f74c6a.png"},{"firstFrame":"http://47.93.217.163:8080/faxian/videoImageFile1501997962034.png","pid":100013,"video":"http://47.93.217.163:8080/faxian/VID_20170806_133915.mp4","content":"进去"},{"pid":100014,"content":"积极","picture":"http://47.93.217.163:8080/faxian/03c92042-1637-4a40-9de6-fc73c22ce240.png"},{"pid":100015,"content":"","picture":"http://47.93.217.163:8080/faxian/a66a7b92-548e-43d1-aee1-d2229474129c.png"},{"firstFrame":"http://47.93.217.163:8080/faxian/videoImageFile1501998020363.png","pid":100016,"video":"http://47.93.217.163:8080/faxian/VID_20170806_134014.mp4","content":"数字"},{"pid":100017,"content":"","picture":"http://47.93.217.163:8080/faxian/98a790e7-dc09-4e83-87e2-24518cf22f51.png"},{"firstFrame":"http://47.93.217.163:8080/faxian/videoImageFile1501998051114.png","pid":100018,"video":"http://47.93.217.163:8080/faxian/VID_20170806_134040.mp4","content":"题库"},{"pid":100019,"content":"糊涂","picture":"http://47.93.217.163:8080/faxian/6b79635f-828d-4f86-92e9-6bce3a6e59bf.png"},{"pid":100020,"content":"捅哦开","picture":"http://47.93.217.163:8080/faxian/583a7e63-87ff-4aa0-a11b-9e6eb4699ca9.png"},{"pid":100021,"content":"哦对","picture":"http://47.93.217.163:8080/faxian/ecab0c33-e58e-4252-9446-ce69d7c1fce3.png"},{"firstFrame":"http://47.93.217.163:8080/faxian/videoImageFile1501998100146.png","pid":100022,"video":"http://47.93.217.163:8080/faxian/VID_20170806_134130.mp4","content":"顾虑"},{"firstFrame":"http://47.93.217.163:8080/faxian/videoImageFile1501998122150.png","pid":100023,"video":"http://47.93.217.163:8080/faxian/VID_20170806_134152.mp4","content":""},{"firstFrame":"http://47.93.217.163:8080/faxian/videoImageFile1501998145055.png","pid":100024,"video":"http://47.93.217.163:8080/faxian/VID_20170806_134210.mp4","content":""},{"firstFrame":"http://47.93.217.163:8080/faxian/videoImageFile1501998185927.png","pid":100025,"video":"http://47.93.217.163:8080/faxian/VID_20170806_134236.mp4","content":""},{"firstFrame":"http://47.93.217.163:8080/faxian/videoImageFile1501998201995.png","pid":100026,"video":"http://47.93.217.163:8080/faxian/VID_20170806_134315.mp4","content":""},{"pid":100027,"content":"你以为","picture":"http://47.93.217.163:8080/faxian/ca0484e1-8fdf-4e0b-8cc2-a00edb1dcab8.png"},{"firstFrame":"http://47.93.217.163:8080/faxian/videoImageFile1501999358196.png","pid":100028,"video":"http://47.93.217.163:8080/faxian/VID_20170806_140229.mp4","content":"噢耶了我了"},{"pid":100029,"content":"民族","picture":"http://47.93.217.163:8080/faxian/5104535a-68ef-496a-a993-a88577c6582f.png"},{"pid":100030,"content":"哦了也用咯","picture":"http://47.93.217.163:8080/faxian/6c3325bc-16c8-4526-8d39-2e48ca009396.png"},{"firstFrame":"http://47.93.217.163:8080/faxian/videoImageFile1501999442704.png","pid":100031,"video":"http://47.93.217.163:8080/faxian/VID_20170806_140355.mp4","content":"你在"},{"pid":100032,"content":"地主爷","picture":"http://47.93.217.163:8080/faxian/5a4da468-606e-4830-8512-d098b2ae3b13.png"},{"pid":100033,"content":"我是图片","picture":"http://47.93.217.163:8080/faxian/f155ffb9-8474-435a-847b-31f338e2165a.png"},{"firstFrame":"http://47.93.217.163:8080/faxian/videoImageFile1502161598081.png","pid":100034,"video":"http://47.93.217.163:8080/faxian/VID_20170808_110628.mp4","content":""},{"pid":100035,"content":"面的","picture":"http://47.93.217.163:8080/faxian/fbbb281b-ec8d-497a-b168-330839abd18a.png"},{"firstFrame":"http://47.93.217.163:8080/faxian/videoImageFile1502680909780.png","pid":100036,"video":"http://47.93.217.163:8080/faxian/VID_20170814_112131.mp4","content":"不错哦"}]
         * user : {"city":"北京","createtime":1501344000000,"gender":"女","head_portrait":"http://47.93.217.163:8080/geren/fa9fc491-018c-41cb-a314-618522ffa2e4.png","loginName":"18618268584","name":"周先生","password":"96e79218965eb72c92a549dd5a330112","phone":"18618268584","role":"4","updatetime":1501344000000,"user_id":100012}
         */

        private String tab;  // 1 已关注 2 未关注
        private UserEntity user;
        private ArrayList<CourseReleaseEntity> courseRelease;
        private ArrayList<DiscoverInfoEntity> discoverInfo;

        public String getTab() {
            return tab;
        }

        public void setTab(String tab) {
            this.tab = tab;
        }

        public UserEntity getUser() {
            return user;
        }

        public void setUser(UserEntity user) {
            this.user = user;
        }

        public ArrayList<CourseReleaseEntity> getCourseRelease() {
            return courseRelease;
        }

        public void setCourseRelease(ArrayList<CourseReleaseEntity> courseRelease) {
            this.courseRelease = courseRelease;
        }

        public ArrayList<DiscoverInfoEntity> getDiscoverInfo() {
            return discoverInfo;
        }

        public void setDiscoverInfo(ArrayList<DiscoverInfoEntity> discoverInfo) {
            this.discoverInfo = discoverInfo;
        }

        public static class UserEntity {
            /**
             * city : 北京
             * createtime : 1501344000000
             * gender : 女
             * head_portrait : http://47.93.217.163:8080/geren/fa9fc491-018c-41cb-a314-618522ffa2e4.png
             * loginName : 18618268584
             * name : 周先生
             * password : 96e79218965eb72c92a549dd5a330112
             * phone : 18618268584
             * role : 4
             * updatetime : 1501344000000
             * user_id : 100012
             */

            private String city;
            private long createtime;
            private String gender;
            private String head_portrait;
            private String loginName;
            private String name;
            private String password;
            private String phone;
            private String role;
            private long updatetime;
            private int user_id;

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public long getCreatetime() {
                return createtime;
            }

            public void setCreatetime(long createtime) {
                this.createtime = createtime;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getHead_portrait() {
                return head_portrait;
            }

            public void setHead_portrait(String head_portrait) {
                this.head_portrait = head_portrait;
            }

            public String getLoginName() {
                return loginName;
            }

            public void setLoginName(String loginName) {
                this.loginName = loginName;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getRole() {
                return role;
            }

            public void setRole(String role) {
                this.role = role;
            }

            public long getUpdatetime() {
                return updatetime;
            }

            public void setUpdatetime(long updatetime) {
                this.updatetime = updatetime;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }
        }

        public static class CourseReleaseEntity implements Parcelable{
            /**
             * classFee : 369
             * collects : 0
             * contactNumber : 987
             * courseName : 噢耶屎
             * id : 100044
             * lat : 40.062332
             * lng : 116.412902
             * picture : geren\755a331c-80c4-4fd0-9943-4ddea3174e78.png
             * rightAge : 不限
             * synopsis : 明
             * teachingAddress : 在北京北附近
             * teachingDate : 你以为
             * teachingNumber : 一对一
             * teachingTime : 1502625997000
             * user_id : 100012
             */

            private String classFee;
            private int collects;
            private String contactNumber;
            private String courseName;
            private int id;
            private String lat;
            private String lng;
            private String picture;
            private String rightAge;
            private String synopsis;
            private String teachingAddress;
            private String teachingDate;
            private String teachingNumber;
            private long teachingTime;
            private String user_id;

            protected CourseReleaseEntity(Parcel in) {
                classFee = in.readString();
                collects = in.readInt();
                contactNumber = in.readString();
                courseName = in.readString();
                id = in.readInt();
                lat = in.readString();
                lng = in.readString();
                picture = in.readString();
                rightAge = in.readString();
                synopsis = in.readString();
                teachingAddress = in.readString();
                teachingDate = in.readString();
                teachingNumber = in.readString();
                teachingTime = in.readLong();
                user_id = in.readString();
            }

            public CourseReleaseEntity(){

            }

            public static final Creator<CourseReleaseEntity> CREATOR = new Creator<CourseReleaseEntity>() {
                @Override
                public CourseReleaseEntity createFromParcel(Parcel in) {
                    return new CourseReleaseEntity(in);
                }

                @Override
                public CourseReleaseEntity[] newArray(int size) {
                    return new CourseReleaseEntity[size];
                }
            };

            public String getClassFee() {
                return classFee;
            }

            public void setClassFee(String classFee) {
                this.classFee = classFee;
            }

            public int getCollects() {
                return collects;
            }

            public void setCollects(int collects) {
                this.collects = collects;
            }

            public String getContactNumber() {
                return contactNumber;
            }

            public void setContactNumber(String contactNumber) {
                this.contactNumber = contactNumber;
            }

            public String getCourseName() {
                return courseName;
            }

            public void setCourseName(String courseName) {
                this.courseName = courseName;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public String getRightAge() {
                return rightAge;
            }

            public void setRightAge(String rightAge) {
                this.rightAge = rightAge;
            }

            public String getSynopsis() {
                return synopsis;
            }

            public void setSynopsis(String synopsis) {
                this.synopsis = synopsis;
            }

            public String getTeachingAddress() {
                return teachingAddress;
            }

            public void setTeachingAddress(String teachingAddress) {
                this.teachingAddress = teachingAddress;
            }

            public String getTeachingDate() {
                return teachingDate;
            }

            public void setTeachingDate(String teachingDate) {
                this.teachingDate = teachingDate;
            }

            public String getTeachingNumber() {
                return teachingNumber;
            }

            public void setTeachingNumber(String teachingNumber) {
                this.teachingNumber = teachingNumber;
            }

            public long getTeachingTime() {
                return teachingTime;
            }

            public void setTeachingTime(long teachingTime) {
                this.teachingTime = teachingTime;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(classFee);
                parcel.writeInt(collects);
                parcel.writeString(contactNumber);
                parcel.writeString(courseName);
                parcel.writeInt(id);
                parcel.writeString(lat);
                parcel.writeString(lng);
                parcel.writeString(picture);
                parcel.writeString(rightAge);
                parcel.writeString(synopsis);
                parcel.writeString(teachingAddress);
                parcel.writeString(teachingDate);
                parcel.writeString(teachingNumber);
                parcel.writeLong(teachingTime);
                parcel.writeString(user_id);
            }
        }

        public static class DiscoverInfoEntity implements Parcelable{
            /**
             * firstFrame : http://47.93.217.163:8080/faxian/videoImageFile1501997222539.png
             * pid : 100011
             * video : http://47.93.217.163:8080/faxian/VID_20170806_132654.mp4
             * content : 哈哈哈
             * picture : http://47.93.217.163:8080/faxian/ae7b684f-3d9f-4820-b08f-fd5de9f74c6a.png
             */

            private String firstFrame;
            private int pid;
            private String video;
            private String content;
            private String picture;

            protected DiscoverInfoEntity(Parcel in) {
                firstFrame = in.readString();
                pid = in.readInt();
                video = in.readString();
                content = in.readString();
                picture = in.readString();
            }

            public DiscoverInfoEntity(){}

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(firstFrame);
                dest.writeInt(pid);
                dest.writeString(video);
                dest.writeString(content);
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

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }
        }
    }
}
