package com.jjjx.data.response;

/**
 * Created by AMing on 17/5/7.
 * Company RongCloud
 */
public class LoginResponse {
    /**
     * msg : 恭喜，登录成功！
     * code : 10000
     */

    private HeadEntity head;
    /**
     * user_id : 100006
     * mobile : 13062822313
     * md5 : 20a399a9895c3bf040b0b038a1fb88d7
     */

    private ParaEntity para;

    public void setHead(HeadEntity head) {
        this.head = head;
    }

    public void setPara(ParaEntity para) {
        this.para = para;
    }

    public HeadEntity getHead() {
        return head;
    }

    public ParaEntity getPara() {
        return para;
    }

    public static class HeadEntity {
        private String msg;
        private String code;

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public String getCode() {
            return code;
        }
    }

    public static class ParaEntity {
        private int user_id;
        private String mobile;
        private String md5;

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public int getUser_id() {
            return user_id;
        }

        public String getMobile() {
            return mobile;
        }

        public String getMd5() {
            return md5;
        }
    }

    //null({"head":{"msg":"用户名或密码错误！","code":"E0003"}})

}
