package com.jjjx.model;

/**
 * Created by AMing on 17/6/28.
 * Company RongCloud
 */

public class UploadImageModel {

    /**
     * head : {"msg":"修改成功！","code":"10000"}
     * url : geren\8d68ca5d-49e4-4361-8389-937cc0f9153c.png
     */

    private HeadEntity head;
    private String url;

    public HeadEntity getHead() {
        return head;
    }

    public void setHead(HeadEntity head) {
        this.head = head;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
