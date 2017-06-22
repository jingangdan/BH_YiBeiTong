package com.bh.yibeitong.bean;

/**
 * Created by jingang on 2016/12/6.
 * 配送费
 */

public class PSCost {

    /**
     * error : false
     * msg : {"baidupscost":0,"canps":1,"pscost":0}
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
         * baidupscost : 0
         * canps : 1
         * pscost : 0
         */

        private String baidupscost;
        private int canps;
        private int pscost;

        public String getBaidupscost() {
            return baidupscost;
        }

        public void setBaidupscost(String baidupscost)  {
            this.baidupscost = baidupscost;
        }

        public int getCanps() {
            return canps;
        }

        public void setCanps(int canps) {
            this.canps = canps;
        }

        public int getPscost() {
            return pscost;
        }

        public void setPscost(int pscost) {
            this.pscost = pscost;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "baidupscost=" + baidupscost +
                    ", canps=" + canps +
                    ", pscost=" + pscost +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PSCost{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
