package com.bh.yibeitong.bean.percen;

import java.util.List;

/**
 * Created by jingang on 2017/7/6.
 * 积分明细
 */

public class ScoreLog {

    /**
     * error : false
     * msg : [{"id":"103","userid":"13","type":"1","addtype":"2","result":"10000.00","addtime":"2017-07-05 16:27","content":"兑换白酒一瓶减少10000积分","title":"兑换礼品","acount":"9999.99","bj":"0"}]
     */

    private boolean error;
    private List<MsgBean> msg;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<MsgBean> getMsg() {
        return msg;
    }

    public void setMsg(List<MsgBean> msg) {
        this.msg = msg;
    }

    public static class MsgBean {
        /**
         * id : 103
         * userid : 13
         * type : 1
         * addtype : 2
         * result : 10000.00
         * addtime : 2017-07-05 16:27
         * content : 兑换白酒一瓶减少10000积分
         * title : 兑换礼品
         * acount : 9999.99
         * bj : 0
         */

        private String id;
        private String userid;
        private String type;
        private String addtype;
        private String result;
        private String addtime;
        private String content;
        private String title;
        private String acount;
        private String bj;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAddtype() {
            return addtype;
        }

        public void setAddtype(String addtype) {
            this.addtype = addtype;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAcount() {
            return acount;
        }

        public void setAcount(String acount) {
            this.acount = acount;
        }

        public String getBj() {
            return bj;
        }

        public void setBj(String bj) {
            this.bj = bj;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "id='" + id + '\'' +
                    ", userid='" + userid + '\'' +
                    ", type='" + type + '\'' +
                    ", addtype='" + addtype + '\'' +
                    ", result='" + result + '\'' +
                    ", addtime='" + addtime + '\'' +
                    ", content='" + content + '\'' +
                    ", title='" + title + '\'' +
                    ", acount='" + acount + '\'' +
                    ", bj='" + bj + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ScoreLog{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
