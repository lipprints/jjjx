package com.jjjx.data.response;

import java.util.List;

/**
 * Created by AMing on 17/9/14.
 * Company RongCloud
 */

public class CommentListResponse {


    /**
     * head : {"msg":"查询成功！","code":"10000"}
     * para : {"discoverInfo":[{"createtime":"刚刚","user_id":100012,"name":"周先生","content":"棒棒哒棒棒哒棒棒哒","head_portrait":"http://47.93.217.163:8080/geren/fa9fc491-018c-41cb-a314-618522ffa2e4.png"}]}
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
        private List<DiscoverInfoEntity> discoverInfo;

        public List<DiscoverInfoEntity> getDiscoverInfo() {
            return discoverInfo;
        }

        public void setDiscoverInfo(List<DiscoverInfoEntity> discoverInfo) {
            this.discoverInfo = discoverInfo;
        }

        public static class DiscoverInfoEntity {
            /**
             * createtime : 刚刚
             * user_id : 100012
             * name : 周先生
             * content : 棒棒哒棒棒哒棒棒哒
             * head_portrait : http://47.93.217.163:8080/geren/fa9fc491-018c-41cb-a314-618522ffa2e4.png
             */

            private String createtime;
            private int user_id;
            private String name;
            private String content;
            private String head_portrait;

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
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
