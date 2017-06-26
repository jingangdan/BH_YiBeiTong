package com.bh.yibeitong.bean.homepage;

import java.util.List;

/**
 * Created by jingang on 2017/6/23.
 * 特色服务模块
 */

public class Tese {
    /**
     * error : false
     * msg : [{"id":"6","name":"定蛋糕","action":"#","img":"/upload/goods/20170626140448115.png","orderid":"1","tstype":"url","imgwidth":"50","param":"#"},{"id":"7","name":"定鲜花","action":"#","img":"/upload/goods/20170626140517833.png","orderid":"2","tstype":"url","imgwidth":"50","param":"#"},{"id":"2","name":"收发快递","action":"mkkd","img":"/upload/goods/20170626143905892.png","orderid":"3","tstype":"action","imgwidth":"100","param":"shopid=#"},{"id":"8","name":"选房产","action":"#","img":"/upload/goods/20170626140626567.png","orderid":"4","tstype":"url","imgwidth":"50","param":"#"},{"id":"5","name":"汽车","action":"tscar","img":"/upload/goods/20170626095526795.png","orderid":"5","tstype":"url","imgwidth":"50","param":"#"},{"id":"9","name":"理财","action":"#","img":"/upload/goods/20170626140706553.png","orderid":"6","tstype":"url","imgwidth":"100","param":"#"},{"id":"10","name":"公益活动","action":"#","img":"/upload/goods/20170626140738517.png","orderid":"7","tstype":"url","imgwidth":"50","param":"#"},{"id":"11","name":"洗衣服务","action":"#","img":"/upload/goods/20170626140804254.png","orderid":"8","tstype":"url","imgwidth":"50","param":"#"},{"id":"12","name":"家政服务","action":"#","img":"/upload/goods/20170626142810799.png","orderid":"9","tstype":"url","imgwidth":"50","param":"#"},{"id":"4","name":"二手交易","action":"togethersay","img":"/upload/goods/20170626095512122.png","orderid":"10","tstype":"action","imgwidth":"50","param":"id=2"}]
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
         * id : 6
         * name : 定蛋糕
         * action : #
         * img : /upload/goods/20170626140448115.png
         * orderid : 1
         * tstype : url
         * imgwidth : 50
         * param : #
         */

        private String id;
        private String name;
        private String action;
        private String img;
        private String orderid;
        private String tstype;
        private String imgwidth;
        private String param;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getTstype() {
            return tstype;
        }

        public void setTstype(String tstype) {
            this.tstype = tstype;
        }

        public String getImgwidth() {
            return imgwidth;
        }

        public void setImgwidth(String imgwidth) {
            this.imgwidth = imgwidth;
        }

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", action='" + action + '\'' +
                    ", img='" + img + '\'' +
                    ", orderid='" + orderid + '\'' +
                    ", tstype='" + tstype + '\'' +
                    ", imgwidth='" + imgwidth + '\'' +
                    ", param='" + param + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Tese{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
