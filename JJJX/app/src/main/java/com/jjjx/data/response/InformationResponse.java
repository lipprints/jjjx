package com.jjjx.data.response;

/**
 * Created by AMing on 17/7/27.
 * Company RongCloud
 */

public class InformationResponse {


    /**
     * head : {"msg":"修改成功！","code":"10000"}
     * role : 1
     */

    private HeadEntity head;
    private String role;

    public HeadEntity getHead() {
        return head;
    }

    public void setHead(HeadEntity head) {
        this.head = head;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static class HeadEntity {
        /**
         * msg : 修改成功！
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
}
