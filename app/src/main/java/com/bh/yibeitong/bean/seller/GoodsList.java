package com.bh.yibeitong.bean.seller;

import java.util.List;

/**
 * Created by jingang on 2017/9/26.
 * 商家获取商品
 */

public class GoodsList {

    /**
     * error : false
     * msg : [{"id":"14727","typeid":"1944","name":"新贵人道","count":"100","cost":"45.00","bagcost":"0.00","img":"","have_det":"0","is_live":"1","shopid":"23"},{"id":"14726","typeid":"1944","name":"红星二锅头56°","count":"100","cost":"16.00","bagcost":"0.00","img":"","have_det":"0","is_live":"1","shopid":"23"},{"id":"14725","typeid":"1944","name":"红星二锅头43°","count":"100","cost":"12.00","bagcost":"0.00","img":"","have_det":"0","is_live":"1","shopid":"23"},{"id":"14724","typeid":"1944","name":"红星二锅头43°","count":"100","cost":"15.00","bagcost":"0.00","img":"","have_det":"0","is_live":"1","shopid":"23"},{"id":"14723","typeid":"1944","name":"43度红星蓝瓶二锅头（八年陈酿）","count":"100","cost":"29.00","bagcost":"0.00","img":"","have_det":"0","is_live":"1","shopid":"23"},{"id":"14722","typeid":"1944","name":"红星二锅头（绵柔8年陈）53°","count":"100","cost":"31.00","bagcost":"0.00","img":"","have_det":"0","is_live":"1","shopid":"23"},{"id":"14721","typeid":"1944","name":"52度红星二锅头","count":"100","cost":"12.00","bagcost":"0.00","img":"","have_det":"0","is_live":"1","shopid":"23"},{"id":"14720","typeid":"1944","name":"红星二锅头52°","count":"100","cost":"36.00","bagcost":"0.00","img":"","have_det":"0","is_live":"1","shopid":"23"},{"id":"14719","typeid":"1944","name":"兰陵王39°（瓶）","count":"100","cost":"90.00","bagcost":"0.00","img":"","have_det":"0","is_live":"1","shopid":"23"},{"id":"14718","typeid":"1944","name":"醉卧兰陵500ml*6","count":"100","cost":"199.00","bagcost":"0.00","img":"","have_det":"0","is_live":"1","shopid":"23"},{"id":"13078","typeid":"1944","name":"兰陵双轮陈香","count":"100","cost":"50.00","bagcost":"0.00","img":"./upload/goodsimg/6902674119221/ontop/1.jpg","have_det":"0","is_live":"1","shopid":"23"}]
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
         * id : 14727
         * typeid : 1944
         * name : 新贵人道
         * count : 100
         * cost : 45.00
         * bagcost : 0.00
         * img :
         * have_det : 0
         * is_live : 1
         * shopid : 23
         */

        private String id;
        private String typeid;
        private String name;
        private String count;
        private String cost;
        private String bagcost;
        private String img;
        private String have_det;
        private String is_live;
        private String shopid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTypeid() {
            return typeid;
        }

        public void setTypeid(String typeid) {
            this.typeid = typeid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public String getBagcost() {
            return bagcost;
        }

        public void setBagcost(String bagcost) {
            this.bagcost = bagcost;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getHave_det() {
            return have_det;
        }

        public void setHave_det(String have_det) {
            this.have_det = have_det;
        }

        public String getIs_live() {
            return is_live;
        }

        public void setIs_live(String is_live) {
            this.is_live = is_live;
        }

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "id='" + id + '\'' +
                    ", typeid='" + typeid + '\'' +
                    ", name='" + name + '\'' +
                    ", count='" + count + '\'' +
                    ", cost='" + cost + '\'' +
                    ", bagcost='" + bagcost + '\'' +
                    ", img='" + img + '\'' +
                    ", have_det='" + have_det + '\'' +
                    ", is_live='" + is_live + '\'' +
                    ", shopid='" + shopid + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GoodsList{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}

