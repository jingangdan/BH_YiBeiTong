package com.bh.yibeitong.seller.bean;

/**
 * Created by jingang on 2017/8/4.
 * 店铺管理实体类
 */

public class AppShop {

    /**
     * error : false
     * msg : {"shopopentype":"1","shoplogo":"https://www.ybt9.com/upload/user/20170711171503940.png","shoptype":"1","opentime":"00:01-23:59","shopname":"易贝通IFC店","id":"24","shopaddress":"沂蒙路","shopphone":"15376913618","intr_info":"","notice_info":"","is_orderbefore":"1","limitcost":"9"}
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
         * shopopentype : 1
         * shoplogo : https://www.ybt9.com/upload/user/20170711171503940.png
         * shoptype : 1
         * opentime : 00:01-23:59
         * shopname : 易贝通IFC店
         * id : 24
         * shopaddress : 沂蒙路
         * shopphone : 15376913618
         * intr_info :
         * notice_info :
         * is_orderbefore : 1
         * limitcost : 9
         */

        private String shopopentype;
        private String shoplogo;
        private String shoptype;
        private String opentime;
        private String shopname;
        private String id;
        private String shopaddress;
        private String shopphone;
        private String intr_info;
        private String notice_info;
        private String is_orderbefore;
        private String limitcost;

        public String getShopopentype() {
            return shopopentype;
        }

        public void setShopopentype(String shopopentype) {
            this.shopopentype = shopopentype;
        }

        public String getShoplogo() {
            return shoplogo;
        }

        public void setShoplogo(String shoplogo) {
            this.shoplogo = shoplogo;
        }

        public String getShoptype() {
            return shoptype;
        }

        public void setShoptype(String shoptype) {
            this.shoptype = shoptype;
        }

        public String getOpentime() {
            return opentime;
        }

        public void setOpentime(String opentime) {
            this.opentime = opentime;
        }

        public String getShopname() {
            return shopname;
        }

        public void setShopname(String shopname) {
            this.shopname = shopname;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShopaddress() {
            return shopaddress;
        }

        public void setShopaddress(String shopaddress) {
            this.shopaddress = shopaddress;
        }

        public String getShopphone() {
            return shopphone;
        }

        public void setShopphone(String shopphone) {
            this.shopphone = shopphone;
        }

        public String getIntr_info() {
            return intr_info;
        }

        public void setIntr_info(String intr_info) {
            this.intr_info = intr_info;
        }

        public String getNotice_info() {
            return notice_info;
        }

        public void setNotice_info(String notice_info) {
            this.notice_info = notice_info;
        }

        public String getIs_orderbefore() {
            return is_orderbefore;
        }

        public void setIs_orderbefore(String is_orderbefore) {
            this.is_orderbefore = is_orderbefore;
        }

        public String getLimitcost() {
            return limitcost;
        }

        public void setLimitcost(String limitcost) {
            this.limitcost = limitcost;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "shopopentype='" + shopopentype + '\'' +
                    ", shoplogo='" + shoplogo + '\'' +
                    ", shoptype='" + shoptype + '\'' +
                    ", opentime='" + opentime + '\'' +
                    ", shopname='" + shopname + '\'' +
                    ", id='" + id + '\'' +
                    ", shopaddress='" + shopaddress + '\'' +
                    ", shopphone='" + shopphone + '\'' +
                    ", intr_info='" + intr_info + '\'' +
                    ", notice_info='" + notice_info + '\'' +
                    ", is_orderbefore='" + is_orderbefore + '\'' +
                    ", limitcost='" + limitcost + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AppShop{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
