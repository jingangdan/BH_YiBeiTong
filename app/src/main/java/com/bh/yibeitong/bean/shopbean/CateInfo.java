package com.bh.yibeitong.bean.shopbean;

import java.util.List;

/**
 * Created by jingang on 2017/2/28.
 * 店铺
 */

public class CateInfo {

    /**
     * error : false
     * msg : {"cateinfo":{"id":"489","name":"奶乳饮品","keywd":"","desc":"","parent_id":"0","shopid":"23","orderid":"3","img":""},"childcate":[{"id":"492","name":"乳饮料","keywd":"","desc":"","parent_id":"489","shopid":"23","orderid":"3","img":""},{"id":"491","name":"酸奶","keywd":"","desc":"","parent_id":"489","shopid":"23","orderid":"2","img":""},{"id":"490","name":"纯奶","keywd":"","desc":"","parent_id":"489","shopid":"23","orderid":"1","img":""}]}
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
         * cateinfo : {"id":"489","name":"奶乳饮品","keywd":"","desc":"","parent_id":"0","shopid":"23","orderid":"3","img":""}
         * childcate : [{"id":"492","name":"乳饮料","keywd":"","desc":"","parent_id":"489","shopid":"23","orderid":"3","img":""},{"id":"491","name":"酸奶","keywd":"","desc":"","parent_id":"489","shopid":"23","orderid":"2","img":""},{"id":"490","name":"纯奶","keywd":"","desc":"","parent_id":"489","shopid":"23","orderid":"1","img":""}]
         */

        private CateinfoBean cateinfo;
        private List<ChildcateBean> childcate;

        public CateinfoBean getCateinfo() {
            return cateinfo;
        }

        public void setCateinfo(CateinfoBean cateinfo) {
            this.cateinfo = cateinfo;
        }

        public List<ChildcateBean> getChildcate() {
            return childcate;
        }

        public void setChildcate(List<ChildcateBean> childcate) {
            this.childcate = childcate;
        }

        public static class CateinfoBean {
            /**
             * id : 489
             * name : 奶乳饮品
             * keywd :
             * desc :
             * parent_id : 0
             * shopid : 23
             * orderid : 3
             * img :
             */

            private String id;
            private String name;
            private String keywd;
            private String desc;
            private String parent_id;
            private String shopid;
            private String orderid;
            private String img;

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

            public String getKeywd() {
                return keywd;
            }

            public void setKeywd(String keywd) {
                this.keywd = keywd;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getParent_id() {
                return parent_id;
            }

            public void setParent_id(String parent_id) {
                this.parent_id = parent_id;
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

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }

        public static class ChildcateBean {
            /**
             * id : 492
             * name : 乳饮料
             * keywd :
             * desc :
             * parent_id : 489
             * shopid : 23
             * orderid : 3
             * img :
             */

            private String id;
            private String name;
            private String keywd;
            private String desc;
            private String parent_id;
            private String shopid;
            private String orderid;
            private String img;

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

            public String getKeywd() {
                return keywd;
            }

            public void setKeywd(String keywd) {
                this.keywd = keywd;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getParent_id() {
                return parent_id;
            }

            public void setParent_id(String parent_id) {
                this.parent_id = parent_id;
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

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            @Override
            public String toString() {
                return "ChildcateBean{" +
                        "id='" + id + '\'' +
                        ", name='" + name + '\'' +
                        ", keywd='" + keywd + '\'' +
                        ", desc='" + desc + '\'' +
                        ", parent_id='" + parent_id + '\'' +
                        ", shopid='" + shopid + '\'' +
                        ", orderid='" + orderid + '\'' +
                        ", img='" + img + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "cateinfo=" + cateinfo +
                    ", childcate=" + childcate +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CateInfo{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
