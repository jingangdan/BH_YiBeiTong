package com.bh.yibeitong.bean;

import java.util.List;

/**
 * Created by jingang on 2016/10/27.
 * 商品详情
 */

public class GoodsDetails {

    /**
     * error : false
     * msg : {"comment":[],"goods":{"id":"3568","gno":"6901035605328       ","typeid":"462","parentid":"6881","name":"青岛啤酒","count":"79","cost":"6.00","img":[{"imgurl":"https://www.ybt9.com./upload/goodsimg/6901035605328/ontop/4.jpg"},{"imgurl":"https://www.ybt9.com./upload/goodsimg/6901035605328/ontop/5.jpg"}],"point":"0","sellcount":"21","shopid":"17","uid":"0","signid":"","pointcount":"0","instro":"","descgoods":null,"daycount":"0","marketcost":"0.00","is_com":"0","show_com":"0","is_hot":"0","is_new":"0","bagcost":"0.00","shoptype":"1","good_order":"0","is_waisong":"1","is_dingtai":"1","weeks":"1,2,3,4,5,6,0","goodattr":"听  ","have_det":"0","product_attr":[],"is_cx":0,"wx_url":null,"virtualsellcount":"0","is_live":"1","pfcost":"0.00","guige":"500ml","zhekou":0,"product":[],"cartnum":3},"shopinfo":{"shopid":"17","is_orderbefore":"0","delaytime":"0","limitcost":"0","limitstro":"","befortime":"0","sendtype":"0","is_hot":"0","is_com":"0","is_new":"0","maketime":"0","pradius":"5","pradiusvalue":"a:5:{i:0;i:0;i:1;i:10;i:2;i:20;i:3;i:30;i:4;i:40;}","pscost":"0","arrivetime":"","postdate":"a:29:{i:0;a:4:{s:1:\"s\";i:28800;s:1:\"e\";i:30600;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:1;a:4:{s:1:\"s\";i:30600;s:1:\"e\";i:32400;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:2;a:4:{s:1:\"s\";i:32400;s:1:\"e\";i:34200;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:3;a:4:{s:1:\"s\";i:34200;s:1:\"e\";i:36000;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:4;a:4:{s:1:\"s\";i:36000;s:1:\"e\";i:37800;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:5;a:4:{s:1:\"s\";i:37800;s:1:\"e\";i:39600;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:6;a:4:{s:1:\"s\";i:39600;s:1:\"e\";i:41400;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:7;a:4:{s:1:\"s\";i:41400;s:1:\"e\";i:43200;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:8;a:4:{s:1:\"s\";i:43200;s:1:\"e\";i:45000;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:9;a:4:{s:1:\"s\";i:45000;s:1:\"e\";i:46800;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:10;a:4:{s:1:\"s\";i:46800;s:1:\"e\";i:48600;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:11;a:4:{s:1:\"s\";i:48600;s:1:\"e\";i:50400;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:12;a:4:{s:1:\"s\";i:50400;s:1:\"e\";i:52200;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:13;a:4:{s:1:\"s\";i:52200;s:1:\"e\";i:54000;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:14;a:4:{s:1:\"s\";i:54000;s:1:\"e\";i:55800;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:15;a:4:{s:1:\"s\";i:55800;s:1:\"e\";i:57600;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:16;a:4:{s:1:\"s\";i:57600;s:1:\"e\";i:59400;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:17;a:4:{s:1:\"s\";i:59400;s:1:\"e\";i:61200;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:18;a:4:{s:1:\"s\";i:61200;s:1:\"e\";i:63000;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:19;a:4:{s:1:\"s\";i:63000;s:1:\"e\";i:64800;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:20;a:4:{s:1:\"s\";i:64800;s:1:\"e\";i:66600;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:21;a:4:{s:1:\"s\";i:66600;s:1:\"e\";i:68400;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:22;a:4:{s:1:\"s\";i:68400;s:1:\"e\";i:70200;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:23;a:4:{s:1:\"s\";i:70200;s:1:\"e\";i:72000;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:24;a:4:{s:1:\"s\";i:72000;s:1:\"e\";i:73800;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:25;a:4:{s:1:\"s\";i:73800;s:1:\"e\";i:75600;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:26;a:4:{s:1:\"s\";i:75600;s:1:\"e\";i:77400;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:27;a:4:{s:1:\"s\";i:77400;s:1:\"e\";i:79200;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:28;a:4:{s:1:\"s\";i:79200;s:1:\"e\";i:81000;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}}","is_hui":"0","is_shophui":"0","is_shgift":"0","sendgift":"0","is_timeduan":"0","interval_minit":"30"}}
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
         * comment : []
         * goods : {"id":"3568","gno":"6901035605328       ","typeid":"462","parentid":"6881","name":"青岛啤酒","count":"79","cost":"6.00","img":[{"imgurl":"https://www.ybt9.com./upload/goodsimg/6901035605328/ontop/4.jpg"},{"imgurl":"https://www.ybt9.com./upload/goodsimg/6901035605328/ontop/5.jpg"}],"point":"0","sellcount":"21","shopid":"17","uid":"0","signid":"","pointcount":"0","instro":"","descgoods":null,"daycount":"0","marketcost":"0.00","is_com":"0","show_com":"0","is_hot":"0","is_new":"0","bagcost":"0.00","shoptype":"1","good_order":"0","is_waisong":"1","is_dingtai":"1","weeks":"1,2,3,4,5,6,0","goodattr":"听  ","have_det":"0","product_attr":[],"is_cx":0,"wx_url":null,"virtualsellcount":"0","is_live":"1","pfcost":"0.00","guige":"500ml","zhekou":0,"product":[],"cartnum":3}
         * shopinfo : {"shopid":"17","is_orderbefore":"0","delaytime":"0","limitcost":"0","limitstro":"","befortime":"0","sendtype":"0","is_hot":"0","is_com":"0","is_new":"0","maketime":"0","pradius":"5","pradiusvalue":"a:5:{i:0;i:0;i:1;i:10;i:2;i:20;i:3;i:30;i:4;i:40;}","pscost":"0","arrivetime":"","postdate":"a:29:{i:0;a:4:{s:1:\"s\";i:28800;s:1:\"e\";i:30600;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:1;a:4:{s:1:\"s\";i:30600;s:1:\"e\";i:32400;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:2;a:4:{s:1:\"s\";i:32400;s:1:\"e\";i:34200;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:3;a:4:{s:1:\"s\";i:34200;s:1:\"e\";i:36000;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:4;a:4:{s:1:\"s\";i:36000;s:1:\"e\";i:37800;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:5;a:4:{s:1:\"s\";i:37800;s:1:\"e\";i:39600;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:6;a:4:{s:1:\"s\";i:39600;s:1:\"e\";i:41400;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:7;a:4:{s:1:\"s\";i:41400;s:1:\"e\";i:43200;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:8;a:4:{s:1:\"s\";i:43200;s:1:\"e\";i:45000;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:9;a:4:{s:1:\"s\";i:45000;s:1:\"e\";i:46800;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:10;a:4:{s:1:\"s\";i:46800;s:1:\"e\";i:48600;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:11;a:4:{s:1:\"s\";i:48600;s:1:\"e\";i:50400;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:12;a:4:{s:1:\"s\";i:50400;s:1:\"e\";i:52200;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:13;a:4:{s:1:\"s\";i:52200;s:1:\"e\";i:54000;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:14;a:4:{s:1:\"s\";i:54000;s:1:\"e\";i:55800;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:15;a:4:{s:1:\"s\";i:55800;s:1:\"e\";i:57600;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:16;a:4:{s:1:\"s\";i:57600;s:1:\"e\";i:59400;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:17;a:4:{s:1:\"s\";i:59400;s:1:\"e\";i:61200;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:18;a:4:{s:1:\"s\";i:61200;s:1:\"e\";i:63000;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:19;a:4:{s:1:\"s\";i:63000;s:1:\"e\";i:64800;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:20;a:4:{s:1:\"s\";i:64800;s:1:\"e\";i:66600;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:21;a:4:{s:1:\"s\";i:66600;s:1:\"e\";i:68400;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:22;a:4:{s:1:\"s\";i:68400;s:1:\"e\";i:70200;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:23;a:4:{s:1:\"s\";i:70200;s:1:\"e\";i:72000;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:24;a:4:{s:1:\"s\";i:72000;s:1:\"e\";i:73800;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:25;a:4:{s:1:\"s\";i:73800;s:1:\"e\";i:75600;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:26;a:4:{s:1:\"s\";i:75600;s:1:\"e\";i:77400;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:27;a:4:{s:1:\"s\";i:77400;s:1:\"e\";i:79200;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}i:28;a:4:{s:1:\"s\";i:79200;s:1:\"e\";i:81000;s:1:\"i\";s:0:\"\";s:4:\"cost\";s:0:\"\";}}","is_hui":"0","is_shophui":"0","is_shgift":"0","sendgift":"0","is_timeduan":"0","interval_minit":"30"}
         */

        private GoodsBean goods;
        private ShopinfoBean shopinfo;
        private List<?> comment;

        public GoodsBean getGoods() {
            return goods;
        }

        public void setGoods(GoodsBean goods) {
            this.goods = goods;
        }

        public ShopinfoBean getShopinfo() {
            return shopinfo;
        }

        public void setShopinfo(ShopinfoBean shopinfo) {
            this.shopinfo = shopinfo;
        }

        public List<?> getComment() {
            return comment;
        }

        public void setComment(List<?> comment) {
            this.comment = comment;
        }

        public static class GoodsBean {
            /**
             * id : 3568
             * gno : 6901035605328
             * typeid : 462
             * parentid : 6881
             * name : 青岛啤酒
             * count : 79
             * cost : 6.00
             * img : [{"imgurl":"https://www.ybt9.com./upload/goodsimg/6901035605328/ontop/4.jpg"},{"imgurl":"https://www.ybt9.com./upload/goodsimg/6901035605328/ontop/5.jpg"}]
             * point : 0
             * sellcount : 21
             * shopid : 17
             * uid : 0
             * signid :
             * pointcount : 0
             * instro :
             * descgoods : null
             * daycount : 0
             * marketcost : 0.00
             * is_com : 0
             * show_com : 0
             * is_hot : 0
             * is_new : 0
             * bagcost : 0.00
             * shoptype : 1
             * good_order : 0
             * is_waisong : 1
             * is_dingtai : 1
             * weeks : 1,2,3,4,5,6,0
             * goodattr : 听
             * have_det : 0
             * product_attr : []
             * is_cx : 0
             * wx_url : null
             * virtualsellcount : 0
             * is_live : 1
             * pfcost : 0.00
             * guige : 500ml
             * zhekou : 0
             * product : []
             * cartnum : 3
             */

            private String id;
            private String gno;
            private String typeid;
            private String parentid;
            private String name;
            private String count;
            private String cost;
            private String point;
            private String sellcount;
            private String shopid;
            private String uid;
            private String signid;
            private String pointcount;
            private String instro;
            private Object descgoods;
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
            private int is_cx;
            private Object wx_url;
            private String virtualsellcount;
            private String is_live;
            private String pfcost;
            private String guige;
            private int zhekou;
            private int cartnum;
            private List<ImgBean> img;
            private List<?> product_attr;
            private List<?> product;

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

            public Object getDescgoods() {
                return descgoods;
            }

            public void setDescgoods(Object descgoods) {
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

            public int getIs_cx() {
                return is_cx;
            }

            public void setIs_cx(int is_cx) {
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

            public String getGuige() {
                return guige;
            }

            public void setGuige(String guige) {
                this.guige = guige;
            }

            public int getZhekou() {
                return zhekou;
            }

            public void setZhekou(int zhekou) {
                this.zhekou = zhekou;
            }

            public int getCartnum() {
                return cartnum;
            }

            public void setCartnum(int cartnum) {
                this.cartnum = cartnum;
            }

            public List<ImgBean> getImg() {
                return img;
            }

            public void setImg(List<ImgBean> img) {
                this.img = img;
            }

            public List<?> getProduct_attr() {
                return product_attr;
            }

            public void setProduct_attr(List<?> product_attr) {
                this.product_attr = product_attr;
            }

            public List<?> getProduct() {
                return product;
            }

            public void setProduct(List<?> product) {
                this.product = product;
            }

            public static class ImgBean {
                /**
                 * imgurl : https://www.ybt9.com./upload/goodsimg/6901035605328/ontop/4.jpg
                 */

                private String imgurl;

                public String getImgurl() {
                    return imgurl;
                }

                public void setImgurl(String imgurl) {
                    this.imgurl = imgurl;
                }
            }
        }

        public static class ShopinfoBean {
            /**
             * shopid : 17
             * is_orderbefore : 0
             * delaytime : 0
             * limitcost : 0
             * limitstro :
             * befortime : 0
             * sendtype : 0
             * is_hot : 0
             * is_com : 0
             * is_new : 0
             * maketime : 0
             * pradius : 5
             * pradiusvalue : a:5:{i:0;i:0;i:1;i:10;i:2;i:20;i:3;i:30;i:4;i:40;}
             * pscost : 0
             * arrivetime :
             * postdate : a:29:{i:0;a:4:{s:1:"s";i:28800;s:1:"e";i:30600;s:1:"i";s:0:"";s:4:"cost";s:0:"";}i:1;a:4:{s:1:"s";i:30600;s:1:"e";i:32400;s:1:"i";s:0:"";s:4:"cost";s:0:"";}i:2;a:4:{s:1:"s";i:32400;s:1:"e";i:34200;s:1:"i";s:0:"";s:4:"cost";s:0:"";}i:3;a:4:{s:1:"s";i:34200;s:1:"e";i:36000;s:1:"i";s:0:"";s:4:"cost";s:0:"";}i:4;a:4:{s:1:"s";i:36000;s:1:"e";i:37800;s:1:"i";s:0:"";s:4:"cost";s:0:"";}i:5;a:4:{s:1:"s";i:37800;s:1:"e";i:39600;s:1:"i";s:0:"";s:4:"cost";s:0:"";}i:6;a:4:{s:1:"s";i:39600;s:1:"e";i:41400;s:1:"i";s:0:"";s:4:"cost";s:0:"";}i:7;a:4:{s:1:"s";i:41400;s:1:"e";i:43200;s:1:"i";s:0:"";s:4:"cost";s:0:"";}i:8;a:4:{s:1:"s";i:43200;s:1:"e";i:45000;s:1:"i";s:0:"";s:4:"cost";s:0:"";}i:9;a:4:{s:1:"s";i:45000;s:1:"e";i:46800;s:1:"i";s:0:"";s:4:"cost";s:0:"";}i:10;a:4:{s:1:"s";i:46800;s:1:"e";i:48600;s:1:"i";s:0:"";s:4:"cost";s:0:"";}i:11;a:4:{s:1:"s";i:48600;s:1:"e";i:50400;s:1:"i";s:0:"";s:4:"cost";s:0:"";}i:12;a:4:{s:1:"s";i:50400;s:1:"e";i:52200;s:1:"i";s:0:"";s:4:"cost";s:0:"";}i:13;a:4:{s:1:"s";i:52200;s:1:"e";i:54000;s:1:"i";s:0:"";s:4:"cost";s:0:"";}i:14;a:4:{s:1:"s";i:54000;s:1:"e";i:55800;s:1:"i";s:0:"";s:4:"cost";s:0:"";}i:15;a:4:{s:1:"s";i:55800;s:1:"e";i:57600;s:1:"i";s:0:"";s:4:"cost";s:0:"";}i:16;a:4:{s:1:"s";i:57600;s:1:"e";i:59400;s:1:"i";s:0:"";s:4:"cost";s:0:"";}i:17;a:4:{s:1:"s";i:59400;s:1:"e";i:61200;s:1:"i";s:0:"";s:4:"cost";s:0:"";}i:18;a:4:{s:1:"s";i:61200;s:1:"e";i:63000;s:1:"i";s:0:"";s:4:"cost";s:0:"";}i:19;a:4:{s:1:"s";i:63000;s:1:"e";i:64800;s:1:"i";s:0:"";s:4:"cost";s:0:"";}i:20;a:4:{s:1:"s";i:64800;s:1:"e";i:66600;s:1:"i";s:0:"";s:4:"cost";s:0:"";}i:21;a:4:{s:1:"s";i:66600;s:1:"e";i:68400;s:1:"i";s:0:"";s:4:"cost";s:0:"";}i:22;a:4:{s:1:"s";i:68400;s:1:"e";i:70200;s:1:"i";s:0:"";s:4:"cost";s:0:"";}i:23;a:4:{s:1:"s";i:70200;s:1:"e";i:72000;s:1:"i";s:0:"";s:4:"cost";s:0:"";}i:24;a:4:{s:1:"s";i:72000;s:1:"e";i:73800;s:1:"i";s:0:"";s:4:"cost";s:0:"";}i:25;a:4:{s:1:"s";i:73800;s:1:"e";i:75600;s:1:"i";s:0:"";s:4:"cost";s:0:"";}i:26;a:4:{s:1:"s";i:75600;s:1:"e";i:77400;s:1:"i";s:0:"";s:4:"cost";s:0:"";}i:27;a:4:{s:1:"s";i:77400;s:1:"e";i:79200;s:1:"i";s:0:"";s:4:"cost";s:0:"";}i:28;a:4:{s:1:"s";i:79200;s:1:"e";i:81000;s:1:"i";s:0:"";s:4:"cost";s:0:"";}}
             * is_hui : 0
             * is_shophui : 0
             * is_shgift : 0
             * sendgift : 0
             * is_timeduan : 0
             * interval_minit : 30
             */

            private String shopid;
            private String is_orderbefore;
            private String delaytime;
            private String limitcost;
            private String limitstro;
            private String befortime;
            private String sendtype;
            private String is_hot;
            private String is_com;
            private String is_new;
            private String maketime;
            private String pradius;
            private String pradiusvalue;
            private String pscost;
            private String arrivetime;
            private String postdate;
            private String is_hui;
            private String is_shophui;
            private String is_shgift;
            private String sendgift;
            private String is_timeduan;
            private String interval_minit;

            public String getShopid() {
                return shopid;
            }

            public void setShopid(String shopid) {
                this.shopid = shopid;
            }

            public String getIs_orderbefore() {
                return is_orderbefore;
            }

            public void setIs_orderbefore(String is_orderbefore) {
                this.is_orderbefore = is_orderbefore;
            }

            public String getDelaytime() {
                return delaytime;
            }

            public void setDelaytime(String delaytime) {
                this.delaytime = delaytime;
            }

            public String getLimitcost() {
                return limitcost;
            }

            public void setLimitcost(String limitcost) {
                this.limitcost = limitcost;
            }

            public String getLimitstro() {
                return limitstro;
            }

            public void setLimitstro(String limitstro) {
                this.limitstro = limitstro;
            }

            public String getBefortime() {
                return befortime;
            }

            public void setBefortime(String befortime) {
                this.befortime = befortime;
            }

            public String getSendtype() {
                return sendtype;
            }

            public void setSendtype(String sendtype) {
                this.sendtype = sendtype;
            }

            public String getIs_hot() {
                return is_hot;
            }

            public void setIs_hot(String is_hot) {
                this.is_hot = is_hot;
            }

            public String getIs_com() {
                return is_com;
            }

            public void setIs_com(String is_com) {
                this.is_com = is_com;
            }

            public String getIs_new() {
                return is_new;
            }

            public void setIs_new(String is_new) {
                this.is_new = is_new;
            }

            public String getMaketime() {
                return maketime;
            }

            public void setMaketime(String maketime) {
                this.maketime = maketime;
            }

            public String getPradius() {
                return pradius;
            }

            public void setPradius(String pradius) {
                this.pradius = pradius;
            }

            public String getPradiusvalue() {
                return pradiusvalue;
            }

            public void setPradiusvalue(String pradiusvalue) {
                this.pradiusvalue = pradiusvalue;
            }

            public String getPscost() {
                return pscost;
            }

            public void setPscost(String pscost) {
                this.pscost = pscost;
            }

            public String getArrivetime() {
                return arrivetime;
            }

            public void setArrivetime(String arrivetime) {
                this.arrivetime = arrivetime;
            }

            public String getPostdate() {
                return postdate;
            }

            public void setPostdate(String postdate) {
                this.postdate = postdate;
            }

            public String getIs_hui() {
                return is_hui;
            }

            public void setIs_hui(String is_hui) {
                this.is_hui = is_hui;
            }

            public String getIs_shophui() {
                return is_shophui;
            }

            public void setIs_shophui(String is_shophui) {
                this.is_shophui = is_shophui;
            }

            public String getIs_shgift() {
                return is_shgift;
            }

            public void setIs_shgift(String is_shgift) {
                this.is_shgift = is_shgift;
            }

            public String getSendgift() {
                return sendgift;
            }

            public void setSendgift(String sendgift) {
                this.sendgift = sendgift;
            }

            public String getIs_timeduan() {
                return is_timeduan;
            }

            public void setIs_timeduan(String is_timeduan) {
                this.is_timeduan = is_timeduan;
            }

            public String getInterval_minit() {
                return interval_minit;
            }

            public void setInterval_minit(String interval_minit) {
                this.interval_minit = interval_minit;
            }

            @Override
            public String toString() {
                return "ShopinfoBean{" +
                        "shopid='" + shopid + '\'' +
                        ", is_orderbefore='" + is_orderbefore + '\'' +
                        ", delaytime='" + delaytime + '\'' +
                        ", limitcost='" + limitcost + '\'' +
                        ", limitstro='" + limitstro + '\'' +
                        ", befortime='" + befortime + '\'' +
                        ", sendtype='" + sendtype + '\'' +
                        ", is_hot='" + is_hot + '\'' +
                        ", is_com='" + is_com + '\'' +
                        ", is_new='" + is_new + '\'' +
                        ", maketime='" + maketime + '\'' +
                        ", pradius='" + pradius + '\'' +
                        ", pradiusvalue='" + pradiusvalue + '\'' +
                        ", pscost='" + pscost + '\'' +
                        ", arrivetime='" + arrivetime + '\'' +
                        ", postdate='" + postdate + '\'' +
                        ", is_hui='" + is_hui + '\'' +
                        ", is_shophui='" + is_shophui + '\'' +
                        ", is_shgift='" + is_shgift + '\'' +
                        ", sendgift='" + sendgift + '\'' +
                        ", is_timeduan='" + is_timeduan + '\'' +
                        ", interval_minit='" + interval_minit + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "goods=" + goods +
                    ", shopinfo=" + shopinfo +
                    ", comment=" + comment +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GoodsDetails{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}