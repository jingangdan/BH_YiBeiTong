package com.bh.yibeitong.bean;

import java.util.List;

/**
 * Created by jingang on 2016/12/9.
 * 店铺类型
 */

public class ShopType {
    /**
     * error : false
     * msg : [{"id":"116","name":"果蔬市场","type":"0","parent_id":"114","cattype":"1","is_search":"0","is_main":"0","is_admin":"0","instro":"","orderid":"0"},{"id":"117","name":"粮油市场","type":"0","parent_id":"114","cattype":"1","is_search":"0","is_main":"0","is_admin":"0","instro":"","orderid":"0"},{"id":"159","name":"调料市场","type":"0","parent_id":"114","cattype":"1","is_search":"0","is_main":"0","is_admin":"0","instro":"","orderid":"0"},{"id":"262","name":"肉类市场","type":"0","parent_id":"114","cattype":"1","is_search":"0","is_main":"0","is_admin":"0","instro":"","orderid":"0"},{"id":"261","name":"综合超市","type":"0","parent_id":"114","cattype":"1","is_search":"0","is_main":"0","is_admin":"0","instro":"","orderid":"0"},{"id":"264","name":"禽蛋市场","type":"0","parent_id":"114","cattype":"1","is_search":"0","is_main":"0","is_admin":"0","instro":"","orderid":"0"},{"id":"265","name":"清真食品","type":"0","parent_id":"114","cattype":"1","is_search":"0","is_main":"0","is_admin":"0","instro":"","orderid":"0"},{"id":"266","name":"便民服务","type":"0","parent_id":"114","cattype":"1","is_search":"0","is_main":"0","is_admin":"0","instro":"","orderid":"0"},{"id":"270","name":"直营","type":"0","parent_id":"114","cattype":"1","is_search":"0","is_main":"0","is_admin":"0","instro":"","orderid":"0"},{"id":"272","name":"自营专区","type":"0","parent_id":"114","cattype":"1","is_search":"0","is_main":"0","is_admin":"0","instro":"","orderid":"0"}]
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
         * id : 116
         * name : 果蔬市场
         * type : 0
         * parent_id : 114
         * cattype : 1
         * is_search : 0
         * is_main : 0
         * is_admin : 0
         * instro :
         * orderid : 0
         */

        private String id;
        private String name;
        private String type;
        private String parent_id;
        private String cattype;
        private String is_search;
        private String is_main;
        private String is_admin;
        private String instro;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getCattype() {
            return cattype;
        }

        public void setCattype(String cattype) {
            this.cattype = cattype;
        }

        public String getIs_search() {
            return is_search;
        }

        public void setIs_search(String is_search) {
            this.is_search = is_search;
        }

        public String getIs_main() {
            return is_main;
        }

        public void setIs_main(String is_main) {
            this.is_main = is_main;
        }

        public String getIs_admin() {
            return is_admin;
        }

        public void setIs_admin(String is_admin) {
            this.is_admin = is_admin;
        }

        public String getInstro() {
            return instro;
        }

        public void setInstro(String instro) {
            this.instro = instro;
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
                    ", type='" + type + '\'' +
                    ", parent_id='" + parent_id + '\'' +
                    ", cattype='" + cattype + '\'' +
                    ", is_search='" + is_search + '\'' +
                    ", is_main='" + is_main + '\'' +
                    ", is_admin='" + is_admin + '\'' +
                    ", instro='" + instro + '\'' +
                    ", orderid='" + orderid + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ShopType{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
