package com.bh.yibeitong.bean;

import java.util.List;

/**
 * Created by jingang on 2017/3/13.
 */

public class SendWater {
    /**
     * error : false
     * msg : [{"id":"3","img":"/upload/goods/20170304144049303.png","gid":"4704","shopid":"23","orderid":"0","name":"蒙山纯净水"},{"id":"4","img":"/upload/goods/20170304144107676.png","gid":"4705","shopid":"23","orderid":"1","name":"长山山泉水"}]
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
         * img : /upload/goods/20170304144049303.png
         * gid : 4704
         * shopid : 23
         * orderid : 0
         * name : 蒙山纯净水
         */

        private String id;
        private String img;
        private String gid;
        private String shopid;
        private String orderid;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "id='" + id + '\'' +
                    ", img='" + img + '\'' +
                    ", gid='" + gid + '\'' +
                    ", shopid='" + shopid + '\'' +
                    ", orderid='" + orderid + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SendWater{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
