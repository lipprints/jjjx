package com.jjjx.data.response;

/**
 * Created by AMing on 17/9/14.
 * Company RongCloud
 */
public class AddCommentResponse {


    /**
     * head : {"msg":"评论成功","code":"S0009"}
     * para : {"content":"棒棒哒棒棒哒棒棒哒","pid":100021,"user_id":100012}
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
         * msg : 评论成功
         * code : S0009
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
         * content : 棒棒哒棒棒哒棒棒哒
         * pid : 100021
         * user_id : 100012
         */

        private String content;
        private int pid;
        private int user_id;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }
    }
}
