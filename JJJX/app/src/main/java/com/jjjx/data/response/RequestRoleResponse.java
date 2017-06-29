package com.jjjx.data.response;

/**
 * Created by AMing on 17/6/29.
 * Company RongCloud
 */
public class RequestRoleResponse {


    /**
     * head : {"msg":"申请成功！","code":"S0000"}
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
         * msg : 申请成功！
         * code : S0000
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
