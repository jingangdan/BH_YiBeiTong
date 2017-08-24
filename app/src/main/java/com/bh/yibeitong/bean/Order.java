package com.bh.yibeitong.bean;

import java.util.List;

/**
 * Created by jingang on 2016/11/25.
 * 订单列表
 */

public class Order {

    /**
     * error : false
     * msg : [{"id":"23061","dno":"15033897757053-易贝通IFC店","shopname":"易贝通IFC店","shopid":"24","allcost":"11.00","addtime":"2017-08-22 16:16:15","status":"4","paystatus":"0","paytype":"1","is_reback":"0","is_goshop":"0","is_make":"0","is_ping":"0","pstype":"1","shoplogo":"https://www.ybt9.com/upload/user/20170711171503940.png","seestatus":"订单取消"},{"id":"23043","dno":"15011402055231","shopname":"易贝通IFC店","shopid":"24","allcost":"12.00","addtime":"2017-07-27 15:23:25","status":"4","paystatus":"0","paytype":"1","is_reback":"0","is_goshop":"0","is_make":"0","is_ping":"0","pstype":"1","shoplogo":"https://www.ybt9.com/upload/user/20170711171503940.png","seestatus":"订单取消"},{"id":"23040","dno":"15000123433467","shopname":"易贝通花半里","shopid":"23","allcost":"10.00","addtime":"2017-07-14 14:05:43","status":"1","paystatus":"1","paytype":"1","is_reback":"0","is_goshop":"0","is_make":"1","is_ping":"0","pstype":"1","shoplogo":"https://www.ybt9.com/upload/user/20170711171519523.png","seestatus":"待发货"},{"id":"23005","dno":"14988685142180","shopname":"易贝通花半里","shopid":"23","allcost":"227.00","addtime":"2017-07-01 08:21:54","status":"4","paystatus":"0","paytype":"1","is_reback":"0","is_goshop":"0","is_make":"0","is_ping":"0","pstype":"1","shoplogo":"https://www.ybt9.com/upload/user/20170711171519523.png","seestatus":"订单取消"},{"id":"22990","dno":"14922232979786","shopname":"易贝通花半里","shopid":"23","allcost":"15.00","addtime":"2017-04-15 10:28:17","status":"1","paystatus":"0","paytype":"0","is_reback":"0","is_goshop":"0","is_make":"1","is_ping":"0","pstype":"1","shoplogo":"https://www.ybt9.com/upload/user/20170711171519523.png","seestatus":"待发货"},{"id":"22966","dno":"14841182175153","shopname":"易贝通快递服务","shopid":"22","allcost":"0.00","addtime":"2017-01-11 15:03:37","status":"3","paystatus":"0","paytype":"0","is_reback":"0","is_goshop":"0","is_make":"1","is_ping":"1","pstype":"0","shoplogo":"https://www.ybt9.com/upload/goods/20170713104733977.png","seestatus":"已完成"},{"id":"22927","dno":"14834975571337","shopname":"易贝通直营测试店2","shopid":"17","allcost":"3.61","addtime":"2017-01-04 10:39:17","status":"1","paystatus":"1","paytype":"1","is_reback":"0","is_goshop":"0","is_make":"0","is_ping":"0","pstype":"1","shoplogo":"https://www.ybt9.com/upload/goods/20170713104733977.png","seestatus":"待发货"},{"id":"22911","dno":"14829787158554","shopname":"易贝通直营测试店2","shopid":"17","allcost":"0.01","addtime":"2016-12-29 10:31:55","status":"1","paystatus":"1","paytype":"1","is_reback":"1","is_goshop":"0","is_make":"0","is_ping":"0","pstype":"1","shoplogo":"https://www.ybt9.com/upload/goods/20170713104733977.png","seestatus":"待发货"},{"id":"22892","dno":"14822033862344","shopname":"易贝通直营测试店2","shopid":"17","allcost":"0.01","addtime":"2016-12-20 11:09:46","status":"1","paystatus":"1","paytype":"1","is_reback":"0","is_goshop":"0","is_make":"0","is_ping":"0","pstype":"1","shoplogo":"https://www.ybt9.com/upload/goods/20170713104733977.png","seestatus":"待发货"},{"id":"22891","dno":"14822003546687","shopname":"易贝通直营测试店2","shopid":"17","allcost":"0.02","addtime":"2016-12-20 10:19:14","status":"3","paystatus":"1","paytype":"1","is_reback":"0","is_goshop":"0","is_make":"1","is_ping":"0","pstype":"1","shoplogo":"https://www.ybt9.com/upload/goods/20170713104733977.png","seestatus":"已完成"}]
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
         * id : 23061
         * dno : 15033897757053-易贝通IFC店
         * shopname : 易贝通IFC店
         * shopid : 24
         * allcost : 11.00
         * addtime : 2017-08-22 16:16:15
         * status : 4
         * paystatus : 0
         * paytype : 1
         * is_reback : 0
         * is_goshop : 0
         * is_make : 0
         * is_ping : 0
         * pstype : 1
         * shoplogo : https://www.ybt9.com/upload/user/20170711171503940.png
         * seestatus : 订单取消
         */

        private String id;
        private String dno;
        private String shopname;
        private String shopid;
        private String allcost;
        private String addtime;
        private String status;
        private String paystatus;
        private String paytype;
        private String is_reback;
        private String is_goshop;
        private String is_make;
        private String is_ping;
        private String pstype;
        private String shoplogo;
        private String seestatus;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDno() {
            return dno;
        }

        public void setDno(String dno) {
            this.dno = dno;
        }

        public String getShopname() {
            return shopname;
        }

        public void setShopname(String shopname) {
            this.shopname = shopname;
        }

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public String getAllcost() {
            return allcost;
        }

        public void setAllcost(String allcost) {
            this.allcost = allcost;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPaystatus() {
            return paystatus;
        }

        public void setPaystatus(String paystatus) {
            this.paystatus = paystatus;
        }

        public String getPaytype() {
            return paytype;
        }

        public void setPaytype(String paytype) {
            this.paytype = paytype;
        }

        public String getIs_reback() {
            return is_reback;
        }

        public void setIs_reback(String is_reback) {
            this.is_reback = is_reback;
        }

        public String getIs_goshop() {
            return is_goshop;
        }

        public void setIs_goshop(String is_goshop) {
            this.is_goshop = is_goshop;
        }

        public String getIs_make() {
            return is_make;
        }

        public void setIs_make(String is_make) {
            this.is_make = is_make;
        }

        public String getIs_ping() {
            return is_ping;
        }

        public void setIs_ping(String is_ping) {
            this.is_ping = is_ping;
        }

        public String getPstype() {
            return pstype;
        }

        public void setPstype(String pstype) {
            this.pstype = pstype;
        }

        public String getShoplogo() {
            return shoplogo;
        }

        public void setShoplogo(String shoplogo) {
            this.shoplogo = shoplogo;
        }

        public String getSeestatus() {
            return seestatus;
        }

        public void setSeestatus(String seestatus) {
            this.seestatus = seestatus;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "id='" + id + '\'' +
                    ", dno='" + dno + '\'' +
                    ", shopname='" + shopname + '\'' +
                    ", shopid='" + shopid + '\'' +
                    ", allcost='" + allcost + '\'' +
                    ", addtime='" + addtime + '\'' +
                    ", status='" + status + '\'' +
                    ", paystatus='" + paystatus + '\'' +
                    ", paytype='" + paytype + '\'' +
                    ", is_reback='" + is_reback + '\'' +
                    ", is_goshop='" + is_goshop + '\'' +
                    ", is_make='" + is_make + '\'' +
                    ", is_ping='" + is_ping + '\'' +
                    ", pstype='" + pstype + '\'' +
                    ", shoplogo='" + shoplogo + '\'' +
                    ", seestatus='" + seestatus + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
