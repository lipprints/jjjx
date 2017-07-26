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
     * para : {"complaints":[{"role":"2","rightAge":"不限","synopsis":"明年你明明你跟你妈民工嗯呐呐喊，明年你门口你无语民工明年你五一呜呜呜呜哦口语呜呜呜T恤五XP一明明哦mins阴公公民numlock明明红米","video":"http://47.93.217.163:8080/geren/VID_20170724_113036.mp4","teachingNumber":"一对一","teachingPlace":"上门","picture":"http://47.93.217.163:8080/geren/dccee4b1-0bd2-48ab-9a6f-1f68d575b982.png,http://47.93.217.163:8080/geren/3ddb8c66-8854-46ed-ba07-32dcb3d6f170.png,http://47.93.217.163:8080/geren/4ed2f57f-6eb1-46cf-bcd4-1694e222a927.png","courseName":"牛逼的钢琴课","firstFrame":"http://47.93.217.163:8080/geren/videoImageFile1500867252802.png","user_id":100009,"teachingDate":"周六下午两点","contactNumber":"18618268584","teachingAddress":"哈哈biz","id":100036,"head_portrait":"http://47.93.217.163:8080/geren/myphoto.jpg"},{"role":"2","rightAge":"5~10","synopsis":"牛逼的钢琴","teachingNumber":"一对一","teachingPlace":"上门","picture":"http://47.93.217.163:8080/geren/42fd7362-bef7-4ec5-9661-387f73eedda6.png,http://47.93.217.163:8080/geren/53918a10-74da-456f-b5d3-dd2ec5f3fde9.png","courseName":"钢琴课","user_id":100009,"teachingDate":"周末下午","contactNumber":"18618268584","teachingAddress":"天通苑","id":100033,"head_portrait":"http://47.93.217.163:8080/geren/myphoto.jpg"}]}
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

        public static class ComplaintsEntity implements Parcelable{
            /**
             * role : 2
             * rightAge : 不限
             * synopsis : 明年你明明你跟你妈民工嗯呐呐喊，明年你门口你无语民工明年你五一呜呜呜呜哦口语呜呜呜T恤五XP一明明哦mins阴公公民numlock明明红米
             * video : http://47.93.217.163:8080/geren/VID_20170724_113036.mp4
             * teachingNumber : 一对一
             * teachingPlace : 上门
             * picture : http://47.93.217.163:8080/geren/dccee4b1-0bd2-48ab-9a6f-1f68d575b982.png,http://47.93.217.163:8080/geren/3ddb8c66-8854-46ed-ba07-32dcb3d6f170.png,http://47.93.217.163:8080/geren/4ed2f57f-6eb1-46cf-bcd4-1694e222a927.png
             * courseName : 牛逼的钢琴课
             * firstFrame : http://47.93.217.163:8080/geren/videoImageFile1500867252802.png
             * user_id : 100009
             * teachingDate : 周六下午两点
             * contactNumber : 18618268584
             * teachingAddress : 哈哈biz
             * id : 100036
             * head_portrait : http://47.93.217.163:8080/geren/myphoto.jpg
             */

            private String role;
            private String rightAge;
            private String synopsis;
            private String video;
            private String teachingNumber;
            private String teachingPlace;
            private String picture;
            private String courseName;
            private String firstFrame;
            private int user_id;
            private String teachingDate;
            private String contactNumber;
            private String teachingAddress;
            /**
             * 该详情 item id
             */
            private int id;
            private String head_portrait;
            private String name;
            private String seniority;

            protected ComplaintsEntity(Parcel in) {
                role = in.readString();
                rightAge = in.readString();
                synopsis = in.readString();
                video = in.readString();
                teachingNumber = in.readString();
                teachingPlace = in.readString();
                picture = in.readString();
                courseName = in.readString();
                firstFrame = in.readString();
                user_id = in.readInt();
                teachingDate = in.readString();
                contactNumber = in.readString();
                teachingAddress = in.readString();
                id = in.readInt();
                head_portrait = in.readString();
                name = in.readString();
                seniority = in.readString();
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(role);
                dest.writeString(rightAge);
                dest.writeString(synopsis);
                dest.writeString(video);
                dest.writeString(teachingNumber);
                dest.writeString(teachingPlace);
                dest.writeString(picture);
                dest.writeString(courseName);
                dest.writeString(firstFrame);
                dest.writeInt(user_id);
                dest.writeString(teachingDate);
                dest.writeString(contactNumber);
                dest.writeString(teachingAddress);
                dest.writeInt(id);
                dest.writeString(head_portrait);
                dest.writeString(name);
                dest.writeString(seniority);
            }

            @Override
            public int describeContents() {
                return 0;
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

            public ComplaintsEntity(){}

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

            public String getSynopsis() {
                return synopsis;
            }

            public void setSynopsis(String synopsis) {
                this.synopsis = synopsis;
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

            public String getCourseName() {
                return courseName;
            }

            public void setCourseName(String courseName) {
                this.courseName = courseName;
            }

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

            public String getTeachingDate() {
                return teachingDate;
            }

            public void setTeachingDate(String teachingDate) {
                this.teachingDate = teachingDate;
            }

            public String getContactNumber() {
                return contactNumber;
            }

            public void setContactNumber(String contactNumber) {
                this.contactNumber = contactNumber;
            }

            public String getTeachingAddress() {
                return teachingAddress;
            }

            public void setTeachingAddress(String teachingAddress) {
                this.teachingAddress = teachingAddress;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getHead_portrait() {
                return head_portrait;
            }

            public void setHead_portrait(String head_portrait) {
                this.head_portrait = head_portrait;
            }
        }
    }

}
