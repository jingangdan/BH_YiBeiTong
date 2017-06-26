package com.bh.yibeitong.bean.shopbean;

import java.util.List;

/**
 * Created by jingang on 2017/2/28.
 * 店铺
 */

public class CateInfo {
    /**
     * error : false
     * msg : {"cateinfo":{"newid":"2","id":"0","name":"休闲零食","keywd":null,"desc":null,"parent_id":"0","shopid":"8","orderid":"0","img":null,"img2":null},"childcate":[{"newid":"3","id":"1607","name":"饼干","keywd":"","desc":"","parent_id":"2","shopid":"8","orderid":"999","img":"","img2":null},{"newid":"4","id":"1608","name":"点心","keywd":"","desc":"","parent_id":"2","shopid":"8","orderid":"999","img":"","img2":null},{"newid":"5","id":"1609","name":"速食品","keywd":"","desc":"","parent_id":"2","shopid":"8","orderid":"999","img":"","img2":null},{"newid":"6","id":"1610","name":"糖果","keywd":"","desc":"","parent_id":"2","shopid":"8","orderid":"999","img":"","img2":null},{"newid":"7","id":"1611","name":"巧克力","keywd":"","desc":"","parent_id":"2","shopid":"8","orderid":"999","img":"","img2":null},{"newid":"8","id":"1614","name":"休闲熟食","keywd":"","desc":"","parent_id":"2","shopid":"8","orderid":"999","img":"","img2":null},{"newid":"9","id":"1615","name":"膨化食品","keywd":"","desc":"","parent_id":"2","shopid":"8","orderid":"999","img":"","img2":null},{"newid":"10","id":"1616","name":"休闲杂项","keywd":"","desc":"","parent_id":"2","shopid":"8","orderid":"999","img":"","img2":null}]}
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
         * cateinfo : {"newid":"2","id":"0","name":"休闲零食","keywd":null,"desc":null,"parent_id":"0","shopid":"8","orderid":"0","img":null,"img2":null}
         * childcate : [{"newid":"3","id":"1607","name":"饼干","keywd":"","desc":"","parent_id":"2","shopid":"8","orderid":"999","img":"","img2":null},{"newid":"4","id":"1608","name":"点心","keywd":"","desc":"","parent_id":"2","shopid":"8","orderid":"999","img":"","img2":null},{"newid":"5","id":"1609","name":"速食品","keywd":"","desc":"","parent_id":"2","shopid":"8","orderid":"999","img":"","img2":null},{"newid":"6","id":"1610","name":"糖果","keywd":"","desc":"","parent_id":"2","shopid":"8","orderid":"999","img":"","img2":null},{"newid":"7","id":"1611","name":"巧克力","keywd":"","desc":"","parent_id":"2","shopid":"8","orderid":"999","img":"","img2":null},{"newid":"8","id":"1614","name":"休闲熟食","keywd":"","desc":"","parent_id":"2","shopid":"8","orderid":"999","img":"","img2":null},{"newid":"9","id":"1615","name":"膨化食品","keywd":"","desc":"","parent_id":"2","shopid":"8","orderid":"999","img":"","img2":null},{"newid":"10","id":"1616","name":"休闲杂项","keywd":"","desc":"","parent_id":"2","shopid":"8","orderid":"999","img":"","img2":null}]
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
             * newid : 2
             * id : 0
             * name : 休闲零食
             * keywd : null
             * desc : null
             * parent_id : 0
             * shopid : 8
             * orderid : 0
             * img : null
             * img2 : null
             */

            private String newid;
            private String id;
            private String name;
            private Object keywd;
            private Object desc;
            private String parent_id;
            private String shopid;
            private String orderid;
            private Object img;
            private Object img2;

            public String getNewid() {
                return newid;
            }

            public void setNewid(String newid) {
                this.newid = newid;
            }

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

            public Object getImg2() {
                return img2;
            }

            public void setImg2(Object img2) {
                this.img2 = img2;
            }
        }

        public static class ChildcateBean {
            /**
             * newid : 3
             * id : 1607
             * name : 饼干
             * keywd :
             * desc :
             * parent_id : 2
             * shopid : 8
             * orderid : 999
             * img :
             * img2 : null
             */

            private String newid;
            private String id;
            private String name;
            private String keywd;
            private String desc;
            private String parent_id;
            private String shopid;
            private String orderid;
            private String img;
            private Object img2;

            public String getNewid() {
                return newid;
            }

            public void setNewid(String newid) {
                this.newid = newid;
            }

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

            public Object getImg2() {
                return img2;
            }

            public void setImg2(Object img2) {
                this.img2 = img2;
            }

            @Override
            public String toString() {
                return "ChildcateBean{" +
                        "newid='" + newid + '\'' +
                        ", id='" + id + '\'' +
                        ", name='" + name + '\'' +
                        ", keywd='" + keywd + '\'' +
                        ", desc='" + desc + '\'' +
                        ", parent_id='" + parent_id + '\'' +
                        ", shopid='" + shopid + '\'' +
                        ", orderid='" + orderid + '\'' +
                        ", img='" + img + '\'' +
                        ", img2=" + img2 +
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
