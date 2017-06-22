package com.bh.yibeitong.bean.homepage;

/**
 * Created by jingang on 2017/6/20.
 * 当天签到状态
 */

public class GetSign {
    /**
     * error : true
     * msg : {"id":"8","uid":"449","time":"1497922722","coday":"1","score":"2","today":true}
     */

    private boolean error;
    private MsgBean msg;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public MsgBean getMsg() {
        return msg;
    }

    public void setMsg(MsgBean msg) {
        this.msg = msg;
    }

    public static class MsgBean {
        /**
         * id : 8
         * uid : 449
         * time : 1497922722
         * coday : 1
         * score : 2
         * today : true
         */

        private String id;
        private String uid;
        private String time;
        private String coday;
        private String score;
        private boolean today;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getCoday() {
            return coday;
        }

        public void setCoday(String coday) {
            this.coday = coday;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public boolean isToday() {
            return today;
        }

        public void setToday(boolean today) {
            this.today = today;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "id='" + id + '\'' +
                    ", uid='" + uid + '\'' +
                    ", time='" + time + '\'' +
                    ", coday='" + coday + '\'' +
                    ", score='" + score + '\'' +
                    ", today=" + today +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GetSign{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
