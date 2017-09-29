package com.bh.yibeitong.bean.seller;

import java.util.List;

/**
 * Created by jingang on 2017/9/26.
 * 超市商家获取二级商品分类
 */

public class MarketTgoodstype {

    /**
     * error : false
     * msg : [{"id":"1949","name":"水类","keywd":null,"desc":null,"parent_id":"1948","shopid":"23","orderid":"999","img":null,"asyncid":"722","display":"0","shuliang":13},{"id":"1950","name":"水饮料","keywd":null,"desc":null,"parent_id":"1948","shopid":"23","orderid":"999","img":null,"asyncid":"723","display":"0","shuliang":97},{"id":"1951","name":"奶饮料","keywd":null,"desc":null,"parent_id":"1948","shopid":"23","orderid":"999","img":null,"asyncid":"724","display":"0","shuliang":9}]
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
         * id : 1949
         * name : 水类
         * keywd : null
         * desc : null
         * parent_id : 1948
         * shopid : 23
         * orderid : 999
         * img : null
         * asyncid : 722
         * display : 0
         * shuliang : 13
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
        private String display;
        private int shuliang;

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

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }

        public int getShuliang() {
            return shuliang;
        }

        public void setShuliang(int shuliang) {
            this.shuliang = shuliang;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", keywd=" + keywd +
                    ", desc=" + desc +
                    ", parent_id='" + parent_id + '\'' +
                    ", shopid='" + shopid + '\'' +
                    ", orderid='" + orderid + '\'' +
                    ", img=" + img +
                    ", asyncid='" + asyncid + '\'' +
                    ", display='" + display + '\'' +
                    ", shuliang=" + shuliang +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MarketTgoodstype{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
