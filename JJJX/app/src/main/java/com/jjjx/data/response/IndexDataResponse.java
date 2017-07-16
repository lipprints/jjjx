package com.jjjx.data.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by AMing on 17/7/13.
 * Company RongCloud
 */
public class IndexDataResponse {

    /**
     * head : {"msg":"查询成功！","code":"10000"}
     * para : {"complaints":[{"courseName":"??","role":"2","rightAge":"??","teachingDate":"?","video":"http://47.93.217.163:8080/geren/VID_20170713_140251.mp4","teachingNumber":"???","teachingPlace":"上门","picture":"http://47.93.217.163:8080/geren/48d82afb-2b04-4999-92c8-aaf6a4fcb659.png,http://47.93.217.163:8080/geren/videoImageFile1499925797514.png","head_portrait":"http://47.93.217.163:8080/geren/a78a4795-f1a6-481e-81bc-3c816451f2ae.png"},{"courseName":"udjcj","role":"2","rightAge":"??","teachingDate":"jfjf","video":"http://47.93.217.163:8080/geren/VID_20170712_195625.mp4","teachingNumber":"???","teachingPlace":"上门","picture":"http://47.93.217.163:8080/geren/2a5f27e2-b77a-4048-b4f9-e1d69c558a28.png,http://47.93.217.163:8080/geren/videoImageFile1499860604998.png","head_portrait":"http://47.93.217.163:8080/geren/a78a4795-f1a6-481e-81bc-3c816451f2ae.png"},{"courseName":"ufjfjf","role":"2","rightAge":"??","teachingDate":"jfc","video":"http://47.93.217.163:8080/geren/VID_20170712_194952.mp4","teachingNumber":"???","teachingPlace":"上门","picture":"http://47.93.217.163:8080/geren/50771ced-340e-4033-a47a-ac3db669362c.png,http://47.93.217.163:8080/geren/videoImageFile.png","head_portrait":"http://47.93.217.163:8080/geren/a78a4795-f1a6-481e-81bc-3c816451f2ae.png"},{"courseName":"jfjcjc","role":"2","rightAge":"??","teachingDate":"ufjc","video":"http://47.93.217.163:8080/geren/VID_20170712_194504.mp4","teachingNumber":"???","teachingPlace":"上门","picture":"http://47.93.217.163:8080/geren/542ef352-0191-4275-a4eb-63e25256bd54.png","head_portrait":"http://47.93.217.163:8080/geren/a78a4795-f1a6-481e-81bc-3c816451f2ae.png"},{"courseName":"??","role":"2","rightAge":"??","teachingDate":"??","video":"http://47.93.217.163:8080/geren/VID_20170712_193338.mp4","teachingNumber":"???","teachingPlace":"上门","picture":"http://47.93.217.163:8080/geren/ffd80c09-8e9d-49f1-99e7-b49afc9d6595.png","head_portrait":"http://47.93.217.163:8080/geren/a78a4795-f1a6-481e-81bc-3c816451f2ae.png"},{"courseName":"???","role":"2","rightAge":"3~5","teachingDate":"?OK","video":"http://47.93.217.163:8080/geren/VID_20170712_100001.mp4","teachingNumber":"???","teachingPlace":"上门","picture":"http://47.93.217.163:8080/geren/3624592b-45b1-4ac0-884c-ad71361115da.png","head_portrait":"http://47.93.217.163:8080/geren/a78a4795-f1a6-481e-81bc-3c816451f2ae.png"},{"courseName":"????","role":"2","rightAge":"11","teachingDate":"12","video":"http://47.93.217.163:8080/geren/QQ???20170618184643.mp4","teachingNumber":"11","teachingPlace":"上门","picture":"http://47.93.217.163:8080/geren/Chrysanthemum.jpg,http://47.93.217.163:8080/geren/Desert.jpg,http://47.93.217.163:8080/geren/Hydrangeas.jpg,http://47.93.217.163:8080/geren/Jellyfish.jpg,http://47.93.217.163:8080/geren/Koala.jpg","head_portrait":"http://47.93.217.163:8080/geren/a78a4795-f1a6-481e-81bc-3c816451f2ae.png"},{"courseName":"?????","role":"2","rightAge":"11","teachingDate":"11","video":"http://47.93.217.163:8080/geren/QQ???20170618184643.mp4","teachingNumber":"11","teachingPlace":"上门","picture":"http://47.93.217.163:8080/geren/Desert.jpg,http://47.93.217.163:8080/geren/Hydrangeas.jpg,http://47.93.217.163:8080/geren/Koala.jpg,http://47.93.217.163:8080/geren/Penguins.jpg,http://47.93.217.163:8080/geren/Tulips.jpg","head_portrait":"http://47.93.217.163:8080/geren/a78a4795-f1a6-481e-81bc-3c816451f2ae.png"},{"courseName":"??","role":"5","rightAge":"??","teachingDate":"10","teachingNumber":"10","teachingPlace":"上门","picture":"http://47.93.217.163:8080/geren/u=3073797763,http://47.93.217.163:8080/3674326206&fm=26&gp=0.jpg,http://47.93.217.163:8080/geren/sy_60361445913.jpg,http://47.93.217.163:8080/geren/Capture001.png,http://47.93.217.163:8080/geren/152518bmhub0l7r0r6rb77.jpg","head_portrait":"http://47.93.217.163:8080/geren/myphoto.jpg"},{"courseName":"??","role":"2","rightAge":"3~5","teachingDate":"??","video":"http://47.93.217.163:8080/geren/VID_20170709_193828.mp4","teachingNumber":"???","teachingPlace":"上门","picture":"http://47.93.217.163:8080/geren/c6cd6e47-e69a-46fe-9f24-981bc0c8be74.png","head_portrait":"http://47.93.217.163:8080/geren/a78a4795-f1a6-481e-81bc-3c816451f2ae.png"},{"courseName":"??","role":"2","rightAge":"??","teachingDate":"???","video":"http://47.93.217.163:8080/geren/VID_20170709_192950.mp4","teachingNumber":"???","teachingPlace":"上门","picture":"http://47.93.217.163:8080/geren/3f192bc4-6cd6-42c0-8e32-92ba575e7d03.png,http://47.93.217.163:8080/geren/1f53cdfe-2a28-44c3-8500-f4a27c381f2b.png,http://47.93.217.163:8080/geren/3ba8786e-b4be-42e5-a08a-7581b96cc8c8.png","head_portrait":"http://47.93.217.163:8080/geren/a78a4795-f1a6-481e-81bc-3c816451f2ae.png"},{"courseName":"??","role":"2","rightAge":"3~5","teachingDate":"??","video":"http://47.93.217.163:8080/geren/VID_20170709_192532.mp4","teachingNumber":"???","teachingPlace":"上门","picture":"http://47.93.217.163:8080/geren/a4ba0b6e-b15d-4800-8cd3-d60072db61f0.png,http://47.93.217.163:8080/geren/ef36c17d-5749-4a31-a737-d14e25c4ae2a.png,http://47.93.217.163:8080/geren/fdf9ebc3-477e-43cd-be74-b1963b3b8b5b.png","head_portrait":"http://47.93.217.163:8080/geren/a78a4795-f1a6-481e-81bc-3c816451f2ae.png"},{"courseName":"???","role":"2","rightAge":"5~10","teachingDate":"?????","video":"http://47.93.217.163:8080/geren/VID_20170709_190110.mp4","teachingNumber":"???","teachingPlace":"上门","picture":"http://47.93.217.163:8080/geren/bcb7e53c-9a09-4681-96d9-7df8d008d18b.png,http://47.93.217.163:8080/geren/f16979a9-6a6d-4a4f-bdeb-e0e57a7adcd3.png","head_portrait":"http://47.93.217.163:8080/geren/a78a4795-f1a6-481e-81bc-3c816451f2ae.png"},{"courseName":"???","role":"2","rightAge":"5~10","teachingDate":"?????","video":"http://47.93.217.163:8080/geren/VID_20170709_190110.mp4","teachingNumber":"???","teachingPlace":"上门","picture":"http://47.93.217.163:8080/geren/bcb7e53c-9a09-4681-96d9-7df8d008d18b.png","head_portrait":"http://47.93.217.163:8080/geren/a78a4795-f1a6-481e-81bc-3c816451f2ae.png"},{"role":"2","video":"http://47.93.217.163:8080/geren/VID_20170705_201559.mp4","teachingPlace":"上门","picture":"http://47.93.217.163:8080/geren/d9f93c71-5d94-4d29-b337-2e39c3c128c0.png","head_portrait":"http://47.93.217.163:8080/geren/a78a4795-f1a6-481e-81bc-3c816451f2ae.png"},{"courseName":"","role":"2","rightAge":"","teachingDate":"","teachingNumber":"1","teachingPlace":"上门","picture":"http://47.93.217.163:8080/geren/Chrysanthemum.jpg,http://47.93.217.163:8080/geren/Desert.jpg","head_portrait":"http://47.93.217.163:8080/geren/a78a4795-f1a6-481e-81bc-3c816451f2ae.png"},{"role":"2","teachingPlace":"上门","picture":"http://47.93.217.163:8080/geren/f7b2264a-c2ed-4dc3-9f06-615e95925433.png,http://47.93.217.163:8080/geren/d9f93c71-5d94-4d29-b337-2e39c3c128c0.png","head_portrait":"http://47.93.217.163:8080/geren/a78a4795-f1a6-481e-81bc-3c816451f2ae.png"},{"courseName":"","role":"2","rightAge":"","teachingDate":"","video":"http://47.93.217.163:8080/geren/QQ???20170618184643.mp4","teachingNumber":"10","teachingPlace":"上门","picture":"http://47.93.217.163:8080/geren/Chrysanthemum.jpg","head_portrait":"http://47.93.217.163:8080/geren/a78a4795-f1a6-481e-81bc-3c816451f2ae.png"},{"courseName":"1","role":"2","rightAge":"11","teachingDate":"11","teachingNumber":"1","teachingPlace":"上门","picture":"http://47.93.217.163:8080/geren/152518bmhub0l7r0r6rb77.jpg,http://47.93.217.163:8080/geren/sy_60361445913.jpg,http://47.93.217.163:8080/geren/u=3073797763,http://47.93.217.163:8080/3674326206&fm=26&gp=0.jpg,http://47.93.217.163:8080/geren/152518bmhub0l7r0r6rb77.jpg,http://47.93.217.163:8080/geren/sy_60361445913.jpg,http://47.93.217.163:8080/geren/u=3073797763,http://47.93.217.163:8080/3674326206&fm=26&gp=0.jpg","head_portrait":"http://47.93.217.163:8080/geren/a78a4795-f1a6-481e-81bc-3c816451f2ae.png"},{"courseName":"??","role":"1","rightAge":"20-30","teachingDate":"20170618","video":"http://47.93.217.163:8080/geren/QQ???20170618184643.mp4","teachingNumber":"10","teachingPlace":"上门","picture":"http://47.93.217.163:8080/geren/Chrysanthemum.jpg,http://47.93.217.163:8080/geren/Desert.jpg,http://47.93.217.163:8080/geren/Hydrangeas.jpg,http://47.93.217.163:8080/geren/Jellyfish.jpg,http://47.93.217.163:8080/geren/Koala.jpg","head_portrait":"http://47.93.217.163:8080/geren/7cdb7e38-2d56-4acf-bc89-15bf8938e089.jpg"},{"courseName":"唱歌","role":"1","rightAge":"20-30","teachingDate":"20170618","video":"http://47.93.217.163:8080/geren/QQ短视频20170618184643.mp4","teachingNumber":"10","teachingPlace":"上门","picture":"http://47.93.217.163:8080/geren/Chrysanthemum.jpg,http://47.93.217.163:8080/geren/Lighthouse.jpg","head_portrait":"http://47.93.217.163:8080/geren/7cdb7e38-2d56-4acf-bc89-15bf8938e089.jpg"},{"courseName":"唱歌","role":"2","rightAge":"20-30","teachingDate":"20170618","video":"http://47.93.217.163:8080/geren/QQ短视频20170618184643.mp4","teachingNumber":"10","teachingPlace":"上门","picture":"http://47.93.217.163:8080/geren/Chrysanthemum.jpg","head_portrait":"http://47.93.217.163:8080/geren/9d2bc1ba-6bda-4dbe-a9b6-17d10d8fa13d.jpg"},{"courseName":"唱歌","role":"1","rightAge":"20-30","teachingDate":"20170618","video":"http://47.93.217.163:8080/geren/QQ短视频20170618184643.mp4","teachingNumber":"10","teachingPlace":"上门","picture":"http://47.93.217.163:8080/geren/Chrysanthemum.jpg","head_portrait":"http://47.93.217.163:8080/geren/7cdb7e38-2d56-4acf-bc89-15bf8938e089.jpg"},{"courseName":"唱歌","role":"1","rightAge":"20-30","teachingDate":"20170618","video":"http://47.93.217.163:8080/D:/apache-tomcat-8.0.30/webapps/jjjx/gerenQQ短视频20170618184643.mp4","teachingNumber":"10","teachingPlace":"上门","picture":"http://47.93.217.163:8080/D:/apache-tomcat-8.0.30/webapps/jjjx/gerenChrysanthemum.jpg","head_portrait":"http://47.93.217.163:8080/geren/7cdb7e38-2d56-4acf-bc89-15bf8938e089.jpg"},{"courseName":"唱歌","role":"1","rightAge":"20-30","teachingDate":"20170618","teachingNumber":"10","teachingPlace":"上门","picture":"http://app.21315.com/geren/picture.jpg","head_portrait":"http://47.93.217.163:8080/geren/7cdb7e38-2d56-4acf-bc89-15bf8938e089.jpg"},{"courseName":"唱歌1","role":"1","rightAge":"20-30","teachingDate":"20170618","video":"http://47.93.217.163:8080/geren/QQ短视频20170618184643.mp4","teachingNumber":"10","teachingPlace":"上门","picture":"http://47.93.217.163:8080/geren/Desert.jpg,http://47.93.217.163:8080/geren/Tulips.jpg","head_portrait":"http://47.93.217.163:8080/geren/7cdb7e38-2d56-4acf-bc89-15bf8938e089.jpg"}]}
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
        private List<ComplaintsEntity> complaints;

        public List<ComplaintsEntity> getComplaints() {
            return complaints;
        }

        public void setComplaints(List<ComplaintsEntity> complaints) {
            this.complaints = complaints;
        }

        public static class ComplaintsEntity implements Parcelable {
            /**
             * courseName : ??
             * role : 2
             * rightAge : ??
             * teachingDate : ?
             * video : http://47.93.217.163:8080/geren/VID_20170713_140251.mp4
             * teachingNumber : ???
             * teachingPlace : 上门
             * picture : http://47.93.217.163:8080/geren/48d82afb-2b04-4999-92c8-aaf6a4fcb659.png,http://47.93.217.163:8080/geren/videoImageFile1499925797514.png
             * head_portrait : http://47.93.217.163:8080/geren/a78a4795-f1a6-481e-81bc-3c816451f2ae.png
             */

            private String courseName;
            private String role;
            private String rightAge;
            private String teachingDate;
            private String video;
            private String teachingNumber;
            private String teachingPlace;
            private String picture;
            private String head_portrait;

            protected ComplaintsEntity(Parcel in) {
                courseName = in.readString();
                role = in.readString();
                rightAge = in.readString();
                teachingDate = in.readString();
                video = in.readString();
                teachingNumber = in.readString();
                teachingPlace = in.readString();
                picture = in.readString();
                head_portrait = in.readString();
            }

            public static final Creator<ComplaintsEntity> CREATOR = new Creator<ComplaintsEntity>() {
                @Override
                public ComplaintsEntity createFromParcel(Parcel in) {
                    return new ComplaintsEntity(in);
                }

                @Override
                public ComplaintsEntity[] newArray(int size) {
                    return new ComplaintsEntity[size];
                }
            };

            public String getCourseName() {
                return courseName;
            }

            public void setCourseName(String courseName) {
                this.courseName = courseName;
            }

            public String getRole() {
                return role;
            }

            public void setRole(String role) {
                this.role = role;
            }

            public String getRightAge() {
                return rightAge;
            }

            public void setRightAge(String rightAge) {
                this.rightAge = rightAge;
            }

            public String getTeachingDate() {
                return teachingDate;
            }

            public void setTeachingDate(String teachingDate) {
                this.teachingDate = teachingDate;
            }

            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
            }

            public String getTeachingNumber() {
                return teachingNumber;
            }

            public void setTeachingNumber(String teachingNumber) {
                this.teachingNumber = teachingNumber;
            }

            public String getTeachingPlace() {
                return teachingPlace;
            }

            public void setTeachingPlace(String teachingPlace) {
                this.teachingPlace = teachingPlace;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public String getHead_portrait() {
                return head_portrait;
            }

            public void setHead_portrait(String head_portrait) {
                this.head_portrait = head_portrait;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(courseName);
                parcel.writeString(role);
                parcel.writeString(rightAge);
                parcel.writeString(teachingDate);
                parcel.writeString(video);
                parcel.writeString(teachingNumber);
                parcel.writeString(teachingPlace);
                parcel.writeString(picture);
                parcel.writeString(head_portrait);

            }
        }
    }
}
