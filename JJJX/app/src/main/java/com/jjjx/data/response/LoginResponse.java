package com.jjjx.data.response;

/**
 * Created by AMing on 17/5/7.
 * Company RongCloud
 */
public class LoginResponse {
    /**
     * head : {"msg":"恭喜，登录成功！","code":"10000"}
     * para : {"role":"1","user_id":100009,"mobile":"18618268584","md5":"176a9151be5c8b07014d708b2843cf84"}
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
         * msg : 恭喜，登录成功！
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
         * role : 1
         * user_id : 100009
         * mobile : 18618268584
         * md5 : 176a9151be5c8b07014d708b2843cf84
         */

        private String role;
        private int user_id;
        private String mobile;
        private String md5;

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }
    }


    //null({"head":{"msg":"用户名或密码错误！","code":"E0003"}})

}
