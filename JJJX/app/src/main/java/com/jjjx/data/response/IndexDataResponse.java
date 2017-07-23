package com.jjjx.data.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;



/**
 * Created by AMing on 17/7/13.
 * Company RongCloud
 */
public class IndexDataResponse {


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
             * courseName : 哈哈哈
             * role : 1
             * firstFrame : http://47.93.217.163:8080/geren/videoImageFile1500770992635.png
             * rightAge : 不限
             * teachingDate : 周末早上八点半
             * video : http://47.93.217.163:8080/geren/VID_20170723_084909.mp4
             * teachingNumber : 一对一
             * teachingPlace : 上门
             * picture : http://47.93.217.163:8080/geren/e5832735-fbee-4d33-9347-4160738d6315.png
             * head_portrait : http://47.93.217.163:8080/geren/myphoto.jpg
             * name : 张三
             * seniority : 1年
             */

            private String courseName;
            private String role;
            private String firstFrame;
            private String rightAge;
            private String teachingDate;
            private String video;
            private String teachingNumber;
            private String teachingPlace;
            private String picture;
            private String head_portrait;
            private String name;
            private String seniority;


            public ComplaintsEntity() {

            }


            protected ComplaintsEntity(Parcel in) {
                courseName = in.readString();
                role = in.readString();
                firstFrame = in.readString();
                rightAge = in.readString();
                teachingDate = in.readString();
                video = in.readString();
                teachingNumber = in.readString();
                teachingPlace = in.readString();
                picture = in.readString();
                head_portrait = in.readString();
                name = in.readString();
                seniority = in.readString();
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

            public String getFirstFrame() {
                return firstFrame;
            }

            public void setFirstFrame(String firstFrame) {
                this.firstFrame = firstFrame;
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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSeniority() {
                return seniority;
            }

            public void setSeniority(String seniority) {
                this.seniority = seniority;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(courseName);
                parcel.writeString(role);
                parcel.writeString(firstFrame);
                parcel.writeString(rightAge);
                parcel.writeString(teachingDate);
                parcel.writeString(video);
                parcel.writeString(teachingNumber);
                parcel.writeString(teachingPlace);
                parcel.writeString(picture);
                parcel.writeString(head_portrait);
                parcel.writeString(name);
                parcel.writeString(seniority);
            }
        }
    }
}
