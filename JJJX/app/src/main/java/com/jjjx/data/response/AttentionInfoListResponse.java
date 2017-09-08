package com.jjjx.data.response;

import java.util.List;

/**
 * Created by AMing on 17/9/8.
 * Company RongCloud
 */
public class AttentionInfoListResponse {
    /**
     * head : {"msg":"查询成功！","code":"10000"}
     * para : {"complaints":[{"createtime":1501344000000,"password":"96e79218965eb72c92a549dd5a330112","role":"4","gender":"女","city":"北京","collections":4,"phone":"18618268584","user_id":100012,"loginName":"18618268584","name":"周先生","updatetime":1501344000000,"head_portrait":"geren/fa9fc491-018c-41cb-a314-618522ffa2e4.png"}]}
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

        public static class ComplaintsEntity {
            /**
             * createtime : 1501344000000
             * password : 96e79218965eb72c92a549dd5a330112
             * role : 4
             * gender : 女
             * city : 北京
             * collections : 4
             * phone : 18618268584
             * user_id : 100012
             * loginName : 18618268584
             * name : 周先生
             * updatetime : 1501344000000
             * head_portrait : geren/fa9fc491-018c-41cb-a314-618522ffa2e4.png
             */

            private long createtime;
            private String password;
            private String role;
            private String gender;
            private String city;
            private int collections;
            private String phone;
            private int user_id;
            private String loginName;
            private String name;
            private long updatetime;
            private String head_portrait;

            public long getCreatetime() {
                return createtime;
            }

            public void setCreatetime(long createtime) {
                this.createtime = createtime;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getRole() {
                return role;
            }

            public void setRole(String role) {
                this.role = role;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public int getCollections() {
                return collections;
            }

            public void setCollections(int collections) {
                this.collections = collections;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
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

            public long getUpdatetime() {
                return updatetime;
            }

            public void setUpdatetime(long updatetime) {
                this.updatetime = updatetime;
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
