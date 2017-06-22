package com.bh.yibeitong.bean;

import java.util.List;

/**
 * Created by jingang on 2016/11/25.
 * 订单列表
 */

public class Order {
    /**
     * error : false
     * msg : [{"id":"22613","shopname":"易贝通曹王庄店","shopid":"8","allcost":"82.50","addtime":"2016-11-25 15:16:15","status":"1","paystatus":"0","paytype":"0","is_reback":"0","is_goshop":"0","is_make":"0","is_ping":"0","pstype":"1","shoplogo":"https://www.ybt9.com/upload/goods/20161012145617881.png","seestatus":"待发货"},{"id":"22612","shopname":"易贝通曹王庄店","shopid":"8","allcost":"66.00","addtime":"2016-11-25 15:13:28","status":"1","paystatus":"0","paytype":"0","is_reback":"0","is_goshop":"0","is_make":"0","is_ping":"0","pstype":"1","shoplogo":"https://www.ybt9.com/upload/goods/20161012145617881.png","seestatus":"待发货"},{"id":"22611","shopname":"易贝通曹王庄店","shopid":"8","allcost":"66.00","addtime":"2016-11-25 15:10:45","status":"1","paystatus":"0","paytype":"0","is_reback":"0","is_goshop":"0","is_make":"0","is_ping":"0","pstype":"1","shoplogo":"https://www.ybt9.com/upload/goods/20161012145617881.png","seestatus":"待发货"},{"id":"22610","shopname":"易贝通曹王庄店","shopid":"8","allcost":"66.00","addtime":"2016-11-25 15:03:25","status":"1","paystatus":"0","paytype":"0","is_reback":"0","is_goshop":"0","is_make":"0","is_ping":"0","pstype":"1","shoplogo":"https://www.ybt9.com/upload/goods/20161012145617881.png","seestatus":"待发货"},{"id":"22609","shopname":"易贝通曹王庄店","shopid":"8","allcost":"66.00","addtime":"2016-11-25 15:02:58","status":"1","paystatus":"0","paytype":"0","is_reback":"0","is_goshop":"0","is_make":"0","is_ping":"0","pstype":"1","shoplogo":"https://www.ybt9.com/upload/goods/20161012145617881.png","seestatus":"待发货"}]
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
         * id : 22613
         * shopname : 易贝通曹王庄店
         * shopid : 8
         * allcost : 82.50
         * addtime : 2016-11-25 15:16:15
         * status : 1
         * paystatus : 0
         * paytype : 0
         * is_reback : 0
         * is_goshop : 0
         * is_make : 0
         * is_ping : 0
         * pstype : 1
         * shoplogo : https://www.ybt9.com/upload/goods/20161012145617881.png
         * seestatus : 待发货
         */

        private String id;
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
