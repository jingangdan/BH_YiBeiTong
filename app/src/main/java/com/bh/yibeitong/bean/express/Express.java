package com.bh.yibeitong.bean.express;

import java.util.List;

/**
 * Created by jingang on 2017/1/20.
 * 收发快递主页
 */

public class Express {

    /**
     * error : false
     * msg : {"goods":[{"id":"4703","gno":"","typeid":"564","parentid":"0","name":"百世快递","count":"100","cost":"0.00","img":"/upload/user/20170120094754501.png","point":"0","sellcount":"0","shopid":"23","uid":"0","signid":"","pointcount":"0","instro":"","descgoods":"","daycount":"100","marketcost":"0.00","is_com":"0","show_com":"0","is_hot":"0","is_new":"0","bagcost":"0.00","shoptype":"1","good_order":"0","is_waisong":"1","is_dingtai":"0","weeks":"1,2,3,4,5,6,0","goodattr":"","have_det":"0","product_attr":"","is_cx":"0","wx_url":null,"virtualsellcount":"0","is_live":"1","pfcost":"0.00","guige":null,"newtype":"1"}],"img":"https://www.ybt9.com/templates/m7/public/wxsite/images/kdtop.png"}
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
         * goods : [{"id":"4703","gno":"","typeid":"564","parentid":"0","name":"百世快递","count":"100","cost":"0.00","img":"/upload/user/20170120094754501.png","point":"0","sellcount":"0","shopid":"23","uid":"0","signid":"","pointcount":"0","instro":"","descgoods":"","daycount":"100","marketcost":"0.00","is_com":"0","show_com":"0","is_hot":"0","is_new":"0","bagcost":"0.00","shoptype":"1","good_order":"0","is_waisong":"1","is_dingtai":"0","weeks":"1,2,3,4,5,6,0","goodattr":"","have_det":"0","product_attr":"","is_cx":"0","wx_url":null,"virtualsellcount":"0","is_live":"1","pfcost":"0.00","guige":null,"newtype":"1"}]
         * img : https://www.ybt9.com/templates/m7/public/wxsite/images/kdtop.png
         */

        private String img;
        private List<GoodsBean> goods;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean {
            /**
             * id : 4703
             * gno :
             * typeid : 564
             * parentid : 0
             * name : 百世快递
             * count : 100
             * cost : 0.00
             * img : /upload/user/20170120094754501.png
             * point : 0
             * sellcount : 0
             * shopid : 23
             * uid : 0
             * signid :
             * pointcount : 0
             * instro :
             * descgoods :
             * daycount : 100
             * marketcost : 0.00
             * is_com : 0
             * show_com : 0
             * is_hot : 0
             * is_new : 0
             * bagcost : 0.00
             * shoptype : 1
             * good_order : 0
             * is_waisong : 1
             * is_dingtai : 0
             * weeks : 1,2,3,4,5,6,0
             * goodattr :
             * have_det : 0
             * product_attr :
             * is_cx : 0
             * wx_url : null
             * virtualsellcount : 0
             * is_live : 1
             * pfcost : 0.00
             * guige : null
             * newtype : 1
             */

            private String id;
            private String gno;
            private String typeid;
            private String parentid;
            private String name;
            private String count;
            private String cost;
            private String img;
            private String point;
            private String sellcount;
            private String shopid;
            private String uid;
            private String signid;
            private String pointcount;
            private String instro;
            private String descgoods;
            private String daycount;
            private String marketcost;
            private String is_com;
            private String show_com;
            private String is_hot;
            private String is_new;
            private String bagcost;
            private String shoptype;
            private String good_order;
            private String is_waisong;
            private String is_dingtai;
            private String weeks;
            private String goodattr;
            private String have_det;
            private String product_attr;
            private String is_cx;
            private Object wx_url;
            private String virtualsellcount;
            private String is_live;
            private String pfcost;
            private Object guige;
            private String newtype;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getGno() {
                return gno;
            }

            public void setGno(String gno) {
                this.gno = gno;
            }

            public String getTypeid() {
                return typeid;
            }

            public void setTypeid(String typeid) {
                this.typeid = typeid;
            }

            public String getParentid() {
                return parentid;
            }

            public void setParentid(String parentid) {
                this.parentid = parentid;
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

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getPoint() {
                return point;
            }

            public void setPoint(String point) {
                this.point = point;
            }

            public String getSellcount() {
                return sellcount;
            }

            public void setSellcount(String sellcount) {
                this.sellcount = sellcount;
            }

            public String getShopid() {
                return shopid;
            }

            public void setShopid(String shopid) {
                this.shopid = shopid;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getSignid() {
                return signid;
            }

            public void setSignid(String signid) {
                this.signid = signid;
            }

            public String getPointcount() {
                return pointcount;
            }

            public void setPointcount(String pointcount) {
                this.pointcount = pointcount;
            }

            public String getInstro() {
                return instro;
            }

            public void setInstro(String instro) {
                this.instro = instro;
            }

            public String getDescgoods() {
                return descgoods;
            }

            public void setDescgoods(String descgoods) {
                this.descgoods = descgoods;
            }

            public String getDaycount() {
                return daycount;
            }

            public void setDaycount(String daycount) {
                this.daycount = daycount;
            }

            public String getMarketcost() {
                return marketcost;
            }

            public void setMarketcost(String marketcost) {
                this.marketcost = marketcost;
            }

            public String getIs_com() {
                return is_com;
            }

            public void setIs_com(String is_com) {
                this.is_com = is_com;
            }

            public String getShow_com() {
                return show_com;
            }

            public void setShow_com(String show_com) {
                this.show_com = show_com;
            }

            public String getIs_hot() {
                return is_hot;
            }

            public void setIs_hot(String is_hot) {
                this.is_hot = is_hot;
            }

            public String getIs_new() {
                return is_new;
            }

            public void setIs_new(String is_new) {
                this.is_new = is_new;
            }

            public String getBagcost() {
                return bagcost;
            }

            public void setBagcost(String bagcost) {
                this.bagcost = bagcost;
            }

            public String getShoptype() {
                return shoptype;
            }

            public void setShoptype(String shoptype) {
                this.shoptype = shoptype;
            }

            public String getGood_order() {
                return good_order;
            }

            public void setGood_order(String good_order) {
                this.good_order = good_order;
            }

            public String getIs_waisong() {
                return is_waisong;
            }

            public void setIs_waisong(String is_waisong) {
                this.is_waisong = is_waisong;
            }

            public String getIs_dingtai() {
                return is_dingtai;
            }

            public void setIs_dingtai(String is_dingtai) {
                this.is_dingtai = is_dingtai;
            }

            public String getWeeks() {
                return weeks;
            }

            public void setWeeks(String weeks) {
                this.weeks = weeks;
            }

            public String getGoodattr() {
                return goodattr;
            }

            public void setGoodattr(String goodattr) {
                this.goodattr = goodattr;
            }

            public String getHave_det() {
                return have_det;
            }

            public void setHave_det(String have_det) {
                this.have_det = have_det;
            }

            public String getProduct_attr() {
                return product_attr;
            }

            public void setProduct_attr(String product_attr) {
                this.product_attr = product_attr;
            }

            public String getIs_cx() {
                return is_cx;
            }

            public void setIs_cx(String is_cx) {
                this.is_cx = is_cx;
            }

            public Object getWx_url() {
                return wx_url;
            }

            public void setWx_url(Object wx_url) {
                this.wx_url = wx_url;
            }

            public String getVirtualsellcount() {
                return virtualsellcount;
            }

            public void setVirtualsellcount(String virtualsellcount) {
                this.virtualsellcount = virtualsellcount;
            }

            public String getIs_live() {
                return is_live;
            }

            public void setIs_live(String is_live) {
                this.is_live = is_live;
            }

            public String getPfcost() {
                return pfcost;
            }

            public void setPfcost(String pfcost) {
                this.pfcost = pfcost;
            }

            public Object getGuige() {
                return guige;
            }

            public void setGuige(Object guige) {
                this.guige = guige;
            }

            public String getNewtype() {
                return newtype;
            }

            public void setNewtype(String newtype) {
                this.newtype = newtype;
            }

            @Override
            public String toString() {
                return "GoodsBean{" +
                        "id='" + id + '\'' +
                        ", gno='" + gno + '\'' +
                        ", typeid='" + typeid + '\'' +
                        ", parentid='" + parentid + '\'' +
                        ", name='" + name + '\'' +
                        ", count='" + count + '\'' +
                        ", cost='" + cost + '\'' +
                        ", img='" + img + '\'' +
                        ", point='" + point + '\'' +
                        ", sellcount='" + sellcount + '\'' +
                        ", shopid='" + shopid + '\'' +
                        ", uid='" + uid + '\'' +
                        ", signid='" + signid + '\'' +
                        ", pointcount='" + pointcount + '\'' +
                        ", instro='" + instro + '\'' +
                        ", descgoods='" + descgoods + '\'' +
                        ", daycount='" + daycount + '\'' +
                        ", marketcost='" + marketcost + '\'' +
                        ", is_com='" + is_com + '\'' +
                        ", show_com='" + show_com + '\'' +
                        ", is_hot='" + is_hot + '\'' +
                        ", is_new='" + is_new + '\'' +
                        ", bagcost='" + bagcost + '\'' +
                        ", shoptype='" + shoptype + '\'' +
                        ", good_order='" + good_order + '\'' +
                        ", is_waisong='" + is_waisong + '\'' +
                        ", is_dingtai='" + is_dingtai + '\'' +
                        ", weeks='" + weeks + '\'' +
                        ", goodattr='" + goodattr + '\'' +
                        ", have_det='" + have_det + '\'' +
                        ", product_attr='" + product_attr + '\'' +
                        ", is_cx='" + is_cx + '\'' +
                        ", wx_url=" + wx_url +
                        ", virtualsellcount='" + virtualsellcount + '\'' +
                        ", is_live='" + is_live + '\'' +
                        ", pfcost='" + pfcost + '\'' +
                        ", guige=" + guige +
                        ", newtype='" + newtype + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "img='" + img + '\'' +
                    ", goods=" + goods +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Express{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}

