package com.bh.yibeitong.bean.homepage;

import java.util.List;

/**
 * Created by jingang on 2017/6/23.
 * 特色服务模块
 */

public class Tese {

    /**
     * error : false
     * msg : [{"id":"1","name":"送水上门","action":"sssm","img":null,"orderid":"0"},{"id":"2","name":"收发快递","action":"mkkd","img":null,"orderid":"1"},{"id":"3","name":"小区信息","action":"luntan","img":null,"orderid":"2"},{"id":"4","name":"二手交易","action":"togethersay","img":null,"orderid":"3"}]
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
         * id : 1
         * name : 送水上门
         * action : sssm
         * img : null
         * orderid : 0
         */

        private String id;
        private String name;
        private String action;
        private String img;
        private String orderid;

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

        @Override
        public String toString() {
            return "MsgBean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", action='" + action + '\'' +
                    ", img=" + img +
                    ", orderid='" + orderid + '\'' +
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
