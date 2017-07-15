package com.bh.yibeitong.bean.seller;

import java.util.List;

/**
 * Created by jingang on 2017/7/14.
 * 订单详情
 */

public class AppOne {

    /**
     * error : false
     * msg : {"id":"23040","dno":"15000123433467","addtime":"14:05:43","daycode":"1","shopname":"易贝通花半里","shopuid":"260","paytype":"账号余额支付","paystatus":"1","ipaddress":"27.218.127.140美国","allcost":"10.00","buyername":"刘波","buyeraddress":"开元花半里3栋2楼","buyerphone":"18669974364","posttime":"2017-07-14 14:01:00","status":"1","is_make":"0","pstype":"1","shopps":"0.00","shoptype":"超市","cxcost":"0.00","yhjcost":"0","scoredown":"0","bagcost":"0.00","content":"","othertext":"","is_goshop":"0","showstatus":"新订单","othercontent":"","posttimename":"配送时间:","ordershow":"超市","allcx":0,"paystatustype":"已支付","det":[{"id":"14724","order_id":"23040","goodsname":"优冠牛奶香浓饼干","goodscost":"5.00(份)","goodscount":"2"},{"id":"0","order_id":"23040","goodsname":"总价","goodscount":2,"goodscost":"10.00"}]}
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
         * id : 23040
         * dno : 15000123433467
         * addtime : 14:05:43
         * daycode : 1
         * shopname : 易贝通花半里
         * shopuid : 260
         * paytype : 账号余额支付
         * paystatus : 1
         * ipaddress : 27.218.127.140美国
         * allcost : 10.00
         * buyername : 刘波
         * buyeraddress : 开元花半里3栋2楼
         * buyerphone : 18669974364
         * posttime : 2017-07-14 14:01:00
         * status : 1
         * is_make : 0
         * pstype : 1
         * shopps : 0.00
         * shoptype : 超市
         * cxcost : 0.00
         * yhjcost : 0
         * scoredown : 0
         * bagcost : 0.00
         * content :
         * othertext :
         * is_goshop : 0
         * showstatus : 新订单
         * othercontent :
         * posttimename : 配送时间:
         * ordershow : 超市
         * allcx : 0
         * paystatustype : 已支付
         * det : [{"id":"14724","order_id":"23040","goodsname":"优冠牛奶香浓饼干","goodscost":"5.00(份)","goodscount":"2"},{"id":"0","order_id":"23040","goodsname":"总价","goodscount":2,"goodscost":"10.00"}]
         */

        private String id;
        private String dno;
        private String addtime;
        private String daycode;
        private String shopname;
        private String shopuid;
        private String paytype;
        private String paystatus;
        private String ipaddress;
        private String allcost;
        private String buyername;
        private String buyeraddress;
        private String buyerphone;
        private String posttime;
        private String status;
        private String is_make;
        private String pstype;
        private String shopps;
        private String shoptype;
        private String cxcost;
        private String yhjcost;
        private String scoredown;
        private String bagcost;
        private String content;
        private String othertext;
        private String is_goshop;
        private String showstatus;
        private String othercontent;
        private String posttimename;
        private String ordershow;
        private int allcx;
        private String paystatustype;
        private List<DetBean> det;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDno() {
            return dno;
        }

        public void setDno(String dno) {
            this.dno = dno;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getDaycode() {
            return daycode;
        }

        public void setDaycode(String daycode) {
            this.daycode = daycode;
        }

        public String getShopname() {
            return shopname;
        }

        public void setShopname(String shopname) {
            this.shopname = shopname;
        }

        public String getShopuid() {
            return shopuid;
        }

        public void setShopuid(String shopuid) {
            this.shopuid = shopuid;
        }

        public String getPaytype() {
            return paytype;
        }

        public void setPaytype(String paytype) {
            this.paytype = paytype;
        }

        public String getPaystatus() {
            return paystatus;
        }

        public void setPaystatus(String paystatus) {
            this.paystatus = paystatus;
        }

        public String getIpaddress() {
            return ipaddress;
        }

        public void setIpaddress(String ipaddress) {
            this.ipaddress = ipaddress;
        }

        public String getAllcost() {
            return allcost;
        }

        public void setAllcost(String allcost) {
            this.allcost = allcost;
        }

        public String getBuyername() {
            return buyername;
        }

        public void setBuyername(String buyername) {
            this.buyername = buyername;
        }

        public String getBuyeraddress() {
            return buyeraddress;
        }

        public void setBuyeraddress(String buyeraddress) {
            this.buyeraddress = buyeraddress;
        }

        public String getBuyerphone() {
            return buyerphone;
        }

        public void setBuyerphone(String buyerphone) {
            this.buyerphone = buyerphone;
        }

        public String getPosttime() {
            return posttime;
        }

        public void setPosttime(String posttime) {
            this.posttime = posttime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getIs_make() {
            return is_make;
        }

        public void setIs_make(String is_make) {
            this.is_make = is_make;
        }

        public String getPstype() {
            return pstype;
        }

        public void setPstype(String pstype) {
            this.pstype = pstype;
        }

        public String getShopps() {
            return shopps;
        }

        public void setShopps(String shopps) {
            this.shopps = shopps;
        }

        public String getShoptype() {
            return shoptype;
        }

        public void setShoptype(String shoptype) {
            this.shoptype = shoptype;
        }

        public String getCxcost() {
            return cxcost;
        }

        public void setCxcost(String cxcost) {
            this.cxcost = cxcost;
        }

        public String getYhjcost() {
            return yhjcost;
        }

        public void setYhjcost(String yhjcost) {
            this.yhjcost = yhjcost;
        }

        public String getScoredown() {
            return scoredown;
        }

        public void setScoredown(String scoredown) {
            this.scoredown = scoredown;
        }

        public String getBagcost() {
            return bagcost;
        }

        public void setBagcost(String bagcost) {
            this.bagcost = bagcost;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getOthertext() {
            return othertext;
        }

        public void setOthertext(String othertext) {
            this.othertext = othertext;
        }

        public String getIs_goshop() {
            return is_goshop;
        }

        public void setIs_goshop(String is_goshop) {
            this.is_goshop = is_goshop;
        }

        public String getShowstatus() {
            return showstatus;
        }

        public void setShowstatus(String showstatus) {
            this.showstatus = showstatus;
        }

        public String getOthercontent() {
            return othercontent;
        }

        public void setOthercontent(String othercontent) {
            this.othercontent = othercontent;
        }

        public String getPosttimename() {
            return posttimename;
        }

        public void setPosttimename(String posttimename) {
            this.posttimename = posttimename;
        }

        public String getOrdershow() {
            return ordershow;
        }

        public void setOrdershow(String ordershow) {
            this.ordershow = ordershow;
        }

        public int getAllcx() {
            return allcx;
        }

        public void setAllcx(int allcx) {
            this.allcx = allcx;
        }

        public String getPaystatustype() {
            return paystatustype;
        }

        public void setPaystatustype(String paystatustype) {
            this.paystatustype = paystatustype;
        }

        public List<DetBean> getDet() {
            return det;
        }

        public void setDet(List<DetBean> det) {
            this.det = det;
        }

        public static class DetBean {
            /**
             * id : 14724
             * order_id : 23040
             * goodsname : 优冠牛奶香浓饼干
             * goodscost : 5.00(份)
             * goodscount : 2
             */

            private String id;
            private String order_id;
            private String goodsname;
            private String goodscost;
            private String goodscount;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getOrder_id() {
                return order_id;
            }

            public void setOrder_id(String order_id) {
                this.order_id = order_id;
            }

            public String getGoodsname() {
                return goodsname;
            }

            public void setGoodsname(String goodsname) {
                this.goodsname = goodsname;
            }

            public String getGoodscost() {
                return goodscost;
            }

            public void setGoodscost(String goodscost) {
                this.goodscost = goodscost;
            }

            public String getGoodscount() {
                return goodscount;
            }

            public void setGoodscount(String goodscount) {
                this.goodscount = goodscount;
            }

            @Override
            public String toString() {
                return "DetBean{" +
                        "id='" + id + '\'' +
                        ", order_id='" + order_id + '\'' +
                        ", goodsname='" + goodsname + '\'' +
                        ", goodscost='" + goodscost + '\'' +
                        ", goodscount='" + goodscount + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "id='" + id + '\'' +
                    ", dno='" + dno + '\'' +
                    ", addtime='" + addtime + '\'' +
                    ", daycode='" + daycode + '\'' +
                    ", shopname='" + shopname + '\'' +
                    ", shopuid='" + shopuid + '\'' +
                    ", paytype='" + paytype + '\'' +
                    ", paystatus='" + paystatus + '\'' +
                    ", ipaddress='" + ipaddress + '\'' +
                    ", allcost='" + allcost + '\'' +
                    ", buyername='" + buyername + '\'' +
                    ", buyeraddress='" + buyeraddress + '\'' +
                    ", buyerphone='" + buyerphone + '\'' +
                    ", posttime='" + posttime + '\'' +
                    ", status='" + status + '\'' +
                    ", is_make='" + is_make + '\'' +
                    ", pstype='" + pstype + '\'' +
                    ", shopps='" + shopps + '\'' +
                    ", shoptype='" + shoptype + '\'' +
                    ", cxcost='" + cxcost + '\'' +
                    ", yhjcost='" + yhjcost + '\'' +
                    ", scoredown='" + scoredown + '\'' +
                    ", bagcost='" + bagcost + '\'' +
                    ", content='" + content + '\'' +
                    ", othertext='" + othertext + '\'' +
                    ", is_goshop='" + is_goshop + '\'' +
                    ", showstatus='" + showstatus + '\'' +
                    ", othercontent='" + othercontent + '\'' +
                    ", posttimename='" + posttimename + '\'' +
                    ", ordershow='" + ordershow + '\'' +
                    ", allcx=" + allcx +
                    ", paystatustype='" + paystatustype + '\'' +
                    ", det=" + det +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AppOne{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
