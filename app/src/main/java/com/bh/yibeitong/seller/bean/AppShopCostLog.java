package com.bh.yibeitong.seller.bean;

import java.util.List;

/**
 * Created by jingang on 2017/8/5.
 * 商家结算列表实体类
 */

public class AppShopCostLog {

    /**
     * error : false
     * msg : [{"id":"3","cost":"1200.00","type":"1","status":"2","addtime":"1501895779","shopid":"0","shopuid":"378","name":"测试","yue":"1200.00","jsid":"0","statusname":"处理成功","adddate":"2017-08-05 09:16:19"}]
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
         * id : 3
         * cost : 1200.00
         * type : 1
         * status : 2
         * addtime : 1501895779
         * shopid : 0
         * shopuid : 378
         * name : 测试
         * yue : 1200.00
         * jsid : 0
         * statusname : 处理成功
         * adddate : 2017-08-05 09:16:19
         */

        private String id;
        private String cost;
        private String type;
        private String status;
        private String addtime;
        private String shopid;
        private String shopuid;
        private String name;
        private String yue;
        private String jsid;
        private String statusname;
        private String adddate;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public String getShopuid() {
            return shopuid;
        }

        public void setShopuid(String shopuid) {
            this.shopuid = shopuid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getYue() {
            return yue;
        }

        public void setYue(String yue) {
            this.yue = yue;
        }

        public String getJsid() {
            return jsid;
        }

        public void setJsid(String jsid) {
            this.jsid = jsid;
        }

        public String getStatusname() {
            return statusname;
        }

        public void setStatusname(String statusname) {
            this.statusname = statusname;
        }

        public String getAdddate() {
            return adddate;
        }

        public void setAdddate(String adddate) {
            this.adddate = adddate;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "id='" + id + '\'' +
                    ", cost='" + cost + '\'' +
                    ", type='" + type + '\'' +
                    ", status='" + status + '\'' +
                    ", addtime='" + addtime + '\'' +
                    ", shopid='" + shopid + '\'' +
                    ", shopuid='" + shopuid + '\'' +
                    ", name='" + name + '\'' +
                    ", yue='" + yue + '\'' +
                    ", jsid='" + jsid + '\'' +
                    ", statusname='" + statusname + '\'' +
                    ", adddate='" + adddate + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AppShopCostLog{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
