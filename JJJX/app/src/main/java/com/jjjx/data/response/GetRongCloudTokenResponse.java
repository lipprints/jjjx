package com.jjjx.data.response;

/**
 * Created by AMing on 17/5/22.
 * Company RongCloud
 */
public class GetRongCloudTokenResponse {

    /**
     * msg : 获取token成功！
     * code : 000000
     */

    private HeadEntity head;
    /**
     * uid : 100003
     * uname : 18501331209
     * headPotrait : /geren/myphoto.jpg
     * token : {"code":200,"token":"xnsI3gE3j2tKEPLPs9s0gmfOEmtSEncZvQC9ak9JTfs30EEi3fx4oS5NmPihJS60IIDkLWUgsDwJrZPvH4anuQ==","userId":"100003"}
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
        private String uid;
        private String uname;
        private String headPotrait;
        /**
         * code : 200
         * token : xnsI3gE3j2tKEPLPs9s0gmfOEmtSEncZvQC9ak9JTfs30EEi3fx4oS5NmPihJS60IIDkLWUgsDwJrZPvH4anuQ==
         * userId : 100003
         */

        private TokenEntity token;

        public void setUid(String uid) {
            this.uid = uid;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public void setHeadPotrait(String headPotrait) {
            this.headPotrait = headPotrait;
        }

        public void setToken(TokenEntity token) {
            this.token = token;
        }

        public String getUid() {
            return uid;
        }

        public String getUname() {
            return uname;
        }

        public String getHeadPotrait() {
            return headPotrait;
        }

        public TokenEntity getToken() {
            return token;
        }

        public static class TokenEntity {
            private int code;
            private String token;
            private String userId;

            public void setCode(int code) {
                this.code = code;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public int getCode() {
                return code;
            }

            public String getToken() {
                return token;
            }

            public String getUserId() {
                return userId;
            }
        }
    }
}

