package com.jjjx.data.response;

/**
 * Created by AMing on 17/5/7.
 * Company RongCloud
 */
public class RegisterResponse {

    /**
     * msg : 两次密码不一致！
     * code : E0001
     */

    private HeadEntity head;

    public void setHead(HeadEntity head) {
        this.head = head;
    }

    public HeadEntity getHead() {
        return head;
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
}
