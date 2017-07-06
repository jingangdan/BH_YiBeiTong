package com.bh.yibeitong.bean.homepage;

import java.util.List;

/**
 * Created by jingang on 2017/6/29.
 */

public class TeseCate {

    /**
     * error : false
     * msg : {"timg":"/upload/goods/20170629103705195.png","cateinfo":{"id":"2103","name":"汽车","keywd":null,"desc":null,"parent_id":"0","shopid":"8","orderid":"15","img":null,"asyncid":"0"},"childcate":[{"id":"2104","name":"奥迪","keywd":null,"desc":null,"parent_id":"2103","shopid":"8","orderid":"0","img":null,"asyncid":"0"},{"id":"2107","name":"大众","keywd":null,"desc":null,"parent_id":"2103","shopid":"8","orderid":"2","img":null,"asyncid":"0"}]}
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
         * timg : /upload/goods/20170629103705195.png
         * cateinfo : {"id":"2103","name":"汽车","keywd":null,"desc":null,"parent_id":"0","shopid":"8","orderid":"15","img":null,"asyncid":"0"}
         * childcate : [{"id":"2104","name":"奥迪","keywd":null,"desc":null,"parent_id":"2103","shopid":"8","orderid":"0","img":null,"asyncid":"0"},{"id":"2107","name":"大众","keywd":null,"desc":null,"parent_id":"2103","shopid":"8","orderid":"2","img":null,"asyncid":"0"}]
         */

        private String timg;
        private CateinfoBean cateinfo;
        private List<ChildcateBean> childcate;

        public String getTimg() {
            return timg;
        }

        public void setTimg(String timg) {
            this.timg = timg;
        }

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
             * id : 2103
             * name : 汽车
             * keywd : null
             * desc : null
             * parent_id : 0
             * shopid : 8
             * orderid : 15
             * img : null
             * asyncid : 0
             */

            private String id;
            private String name;
            private Object keywd;
            private Object desc;
            private String parent_id;
            private String shopid;
            private String orderid;
            private Object img;
            private String asyncid;

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

            public Object getKeywd() {
                return keywd;
            }

            public void setKeywd(Object keywd) {
                this.keywd = keywd;
            }

            public Object getDesc() {
                return desc;
            }

            public void setDesc(Object desc) {
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

            public Object getImg() {
                return img;
            }

            public void setImg(Object img) {
                this.img = img;
            }

            public String getAsyncid() {
                return asyncid;
            }

            public void setAsyncid(String asyncid) {
                this.asyncid = asyncid;
            }
        }

        public static class ChildcateBean {
            /**
             * id : 2104
             * name : 奥迪
             * keywd : null
             * desc : null
             * parent_id : 2103
             * shopid : 8
             * orderid : 0
             * img : null
             * asyncid : 0
             */

            private String id;
            private String name;
            private Object keywd;
            private Object desc;
            private String parent_id;
            private String shopid;
            private String orderid;
            private Object img;
            private String asyncid;

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

            public Object getKeywd() {
                return keywd;
            }

            public void setKeywd(Object keywd) {
                this.keywd = keywd;
            }

            public Object getDesc() {
                return desc;
            }

            public void setDesc(Object desc) {
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

            public Object getImg() {
                return img;
            }

            public void setImg(Object img) {
                this.img = img;
            }

            public String getAsyncid() {
                return asyncid;
            }

            public void setAsyncid(String asyncid) {
                this.asyncid = asyncid;
            }

            @Override
            public String toString() {
                return "ChildcateBean{" +
                        "id='" + id + '\'' +
                        ", name='" + name + '\'' +
                        ", keywd=" + keywd +
                        ", desc=" + desc +
                        ", parent_id='" + parent_id + '\'' +
                        ", shopid='" + shopid + '\'' +
                        ", orderid='" + orderid + '\'' +
                        ", img=" + img +
                        ", asyncid='" + asyncid + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "timg='" + timg + '\'' +
                    ", cateinfo=" + cateinfo +
                    ", childcate=" + childcate +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TeseCate{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
