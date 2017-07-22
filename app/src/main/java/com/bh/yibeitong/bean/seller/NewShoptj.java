package com.bh.yibeitong.bean.seller;

/**
 * Created by jingang on 2017/7/17.
 * 商家端统计（订单数和营业额等）
 */

public class NewShoptj {

    /**
     * error : false
     * msg : {"daycost":0,"daycount":0,"weekcost":27,"weekcount":2,"monthcost":27,"monthcount":2,"allcost":86.7,"allcount":5}
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
         * daycost : 0
         * daycount : 0
         * weekcost : 27
         * weekcount : 2
         * monthcost : 27
         * monthcount : 2
         * allcost : 86.7
         * allcount : 5
         */

        private int daycost;
        private int daycount;
        private int weekcost;
        private int weekcount;
        private int monthcost;
        private int monthcount;
        private double allcost;
        private int allcount;

        public int getDaycost() {
            return daycost;
        }

        public void setDaycost(int daycost) {
            this.daycost = daycost;
        }

        public int getDaycount() {
            return daycount;
        }

        public void setDaycount(int daycount) {
            this.daycount = daycount;
        }

        public int getWeekcost() {
            return weekcost;
        }

        public void setWeekcost(int weekcost) {
            this.weekcost = weekcost;
        }

        public int getWeekcount() {
            return weekcount;
        }

        public void setWeekcount(int weekcount) {
            this.weekcount = weekcount;
        }

        public int getMonthcost() {
            return monthcost;
        }

        public void setMonthcost(int monthcost) {
            this.monthcost = monthcost;
        }

        public int getMonthcount() {
            return monthcount;
        }

        public void setMonthcount(int monthcount) {
            this.monthcount = monthcount;
        }

        public double getAllcost() {
            return allcost;
        }

        public void setAllcost(double allcost) {
            this.allcost = allcost;
        }

        public int getAllcount() {
            return allcount;
        }

        public void setAllcount(int allcount) {
            this.allcount = allcount;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "daycost=" + daycost +
                    ", daycount=" + daycount +
                    ", weekcost=" + weekcost +
                    ", weekcount=" + weekcount +
                    ", monthcost=" + monthcost +
                    ", monthcount=" + monthcount +
                    ", allcost=" + allcost +
                    ", allcount=" + allcount +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NewShoptj{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
