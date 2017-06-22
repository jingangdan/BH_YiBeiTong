package com.bh.yibeitong.bean;

import java.util.List;

/**
 * Created by jingang on 2016/11/25.
 * 订单详情
 */
public class OrderDetaile {
    /**
     * error : false
     * msg : {"ordertype":"超市","paystatusintro":"到付(未支付)","shopcost":"82.50","shopps":"0.00","cxcost":0,"allcost":"82.50","shoptype":"1","is_goshop":"0","pstype":"1","posttime":"2016-11-25 16:00-16:30","buyername":"金刚","buyerphone":"17865069350","buyeraddress":"金刚","shopid":"8","id":"22613","shopname":"易贝通曹王庄店","shopaddress":"临西八路","shopphone":"05398606806","content":"","dno":"14800581757663","addtime":"2016-11-25 15:16","sendtime":"1970-01-01 08:00","suretime":"1970-01-01 08:00","passtime":"2016-11-25 15:16","statuslist":[{"name":"订单已提交","miaoshu":"请耐心等待商家确认","addtime":"1480058175","time":"11-25 15:16"}],"is_reback":"0","is_print":"0","status":"1","is_make":"0","paystatus":"0","paytype":"0","is_ping":"0","gdlist":[{"id":"13986","order_id":"22613","goodsid":"1026","goodsname":"回头客辣大叔烤薯片香辣鲜虾味","goodscost":"7.50","goodscount":"0","status":"0","shopid":"8","is_send":"0","product_id":"0","have_det":"0"},{"id":0,"order_id":0,"goodsid":0,"goodsname":"商品总价","goodscost":"82.50","goodscount":0,"status":0,"is_send":0,"shopid":0},{"id":0,"order_id":0,"goodsid":0,"goodsname":"订单实价","goodscost":"82.50","goodscount":0,"status":0,"is_send":0,"shopid":0}]}
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
         * ordertype : 超市
         * paystatusintro : 到付(未支付)
         * shopcost : 82.50
         * shopps : 0.00
         * cxcost : 0
         * allcost : 82.50
         * shoptype : 1
         * is_goshop : 0
         * pstype : 1
         * posttime : 2016-11-25 16:00-16:30
         * buyername : 金刚
         * buyerphone : 17865069350
         * buyeraddress : 金刚
         * shopid : 8
         * id : 22613
         * shopname : 易贝通曹王庄店
         * shopaddress : 临西八路
         * shopphone : 05398606806
         * content :
         * dno : 14800581757663
         * addtime : 2016-11-25 15:16
         * sendtime : 1970-01-01 08:00
         * suretime : 1970-01-01 08:00
         * passtime : 2016-11-25 15:16
         * statuslist : [{"name":"订单已提交","miaoshu":"请耐心等待商家确认","addtime":"1480058175","time":"11-25 15:16"}]
         * is_reback : 0
         * is_print : 0
         * status : 1
         * is_make : 0
         * paystatus : 0
         * paytype : 0
         * is_ping : 0
         * gdlist : [{"id":"13986","order_id":"22613","goodsid":"1026","goodsname":"回头客辣大叔烤薯片香辣鲜虾味","goodscost":"7.50","goodscount":"0","status":"0","shopid":"8","is_send":"0","product_id":"0","have_det":"0"},{"id":0,"order_id":0,"goodsid":0,"goodsname":"商品总价","goodscost":"82.50","goodscount":0,"status":0,"is_send":0,"shopid":0},{"id":0,"order_id":0,"goodsid":0,"goodsname":"订单实价","goodscost":"82.50","goodscount":0,"status":0,"is_send":0,"shopid":0}]
         */

        private String ordertype;
        private String paystatusintro;
        private String shopcost;
        private String shopps;
        private int cxcost;
        private String allcost;
        private String shoptype;
        private String is_goshop;
        private String pstype;
        private String posttime;
        private String buyername;
        private String buyerphone;
        private String buyeraddress;
        private String shopid;
        private String id;
        private String shopname;
        private String shopaddress;
        private String shopphone;
        private String content;
        private String dno;
        private String addtime;
        private String sendtime;
        private String suretime;
        private String passtime;
        private String is_reback;
        private String is_print;
        private String status;
        private String is_make;
        private String paystatus;
        private String paytype;
        private String is_ping;
        private List<StatuslistBean> statuslist;
        private List<GdlistBean> gdlist;

        public String getOrdertype() {
            return ordertype;
        }

        public void setOrdertype(String ordertype) {
            this.ordertype = ordertype;
        }

        public String getPaystatusintro() {
            return paystatusintro;
        }

        public void setPaystatusintro(String paystatusintro) {
            this.paystatusintro = paystatusintro;
        }

        public String getShopcost() {
            return shopcost;
        }

        public void setShopcost(String shopcost) {
            this.shopcost = shopcost;
        }

        public String getShopps() {
            return shopps;
        }

        public void setShopps(String shopps) {
            this.shopps = shopps;
        }

        public int getCxcost() {
            return cxcost;
        }

        public void setCxcost(int cxcost) {
            this.cxcost = cxcost;
        }

        public String getAllcost() {
            return allcost;
        }

        public void setAllcost(String allcost) {
            this.allcost = allcost;
        }

        public String getShoptype() {
            return shoptype;
        }

        public void setShoptype(String shoptype) {
            this.shoptype = shoptype;
        }

        public String getIs_goshop() {
            return is_goshop;
        }

        public void setIs_goshop(String is_goshop) {
            this.is_goshop = is_goshop;
        }

        public String getPstype() {
            return pstype;
        }

        public void setPstype(String pstype) {
            this.pstype = pstype;
        }

        public String getPosttime() {
            return posttime;
        }

        public void setPosttime(String posttime) {
            this.posttime = posttime;
        }

        public String getBuyername() {
            return buyername;
        }

        public void setBuyername(String buyername) {
            this.buyername = buyername;
        }

        public String getBuyerphone() {
            return buyerphone;
        }

        public void setBuyerphone(String buyerphone) {
            this.buyerphone = buyerphone;
        }

        public String getBuyeraddress() {
            return buyeraddress;
        }

        public void setBuyeraddress(String buyeraddress) {
            this.buyeraddress = buyeraddress;
        }

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShopname() {
            return shopname;
        }

        public void setShopname(String shopname) {
            this.shopname = shopname;
        }

        public String getShopaddress() {
            return shopaddress;
        }

        public void setShopaddress(String shopaddress) {
            this.shopaddress = shopaddress;
        }

        public String getShopphone() {
            return shopphone;
        }

        public void setShopphone(String shopphone) {
            this.shopphone = shopphone;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public String getSendtime() {
            return sendtime;
        }

        public void setSendtime(String sendtime) {
            this.sendtime = sendtime;
        }

        public String getSuretime() {
            return suretime;
        }

        public void setSuretime(String suretime) {
            this.suretime = suretime;
        }

        public String getPasstime() {
            return passtime;
        }

        public void setPasstime(String passtime) {
            this.passtime = passtime;
        }

        public String getIs_reback() {
            return is_reback;
        }

        public void setIs_reback(String is_reback) {
            this.is_reback = is_reback;
        }

        public String getIs_print() {
            return is_print;
        }

        public void setIs_print(String is_print) {
            this.is_print = is_print;
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

        public String getPaystatus() {
            return paystatus;
        }

        public void setPaystatus(String paystatus) {
            this.paystatus = paystatus;
        }

        public String getPaytype() {
            return paytype;
        }

        public void setPaytype(String paytype) {
            this.paytype = paytype;
        }

        public String getIs_ping() {
            return is_ping;
        }

        public void setIs_ping(String is_ping) {
            this.is_ping = is_ping;
        }

        public List<StatuslistBean> getStatuslist() {
            return statuslist;
        }

        public void setStatuslist(List<StatuslistBean> statuslist) {
            this.statuslist = statuslist;
        }

        public List<GdlistBean> getGdlist() {
            return gdlist;
        }

        public void setGdlist(List<GdlistBean> gdlist) {
            this.gdlist = gdlist;
        }

        public static class StatuslistBean {
            /**
             * name : 订单已提交
             * miaoshu : 请耐心等待商家确认
             * addtime : 1480058175
             * time : 11-25 15:16
             */

            private String name;
            private String miaoshu;
            private String addtime;
            private String time;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getMiaoshu() {
                return miaoshu;
            }

            public void setMiaoshu(String miaoshu) {
                this.miaoshu = miaoshu;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class GdlistBean {
            /**
             * id : 13986
             * order_id : 22613
             * goodsid : 1026
             * goodsname : 回头客辣大叔烤薯片香辣鲜虾味
             * goodscost : 7.50
             * goodscount : 0
             * status : 0
             * shopid : 8
             * is_send : 0
             * product_id : 0
             * have_det : 0
             */

            private String id;
            private String order_id;
            private String goodsid;
            private String goodsname;
            private String goodscost;
            private String goodscount;
            private String status;
            private String shopid;
            private String is_send;
            private String product_id;
            private String have_det;

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

            public String getGoodsid() {
                return goodsid;
            }

            public void setGoodsid(String goodsid) {
                this.goodsid = goodsid;
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

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getShopid() {
                return shopid;
            }

            public void setShopid(String shopid) {
                this.shopid = shopid;
            }

            public String getIs_send() {
                return is_send;
            }

            public void setIs_send(String is_send) {
                this.is_send = is_send;
            }

            public String getProduct_id() {
                return product_id;
            }

            public void setProduct_id(String product_id) {
                this.product_id = product_id;
            }

            public String getHave_det() {
                return have_det;
            }

            public void setHave_det(String have_det) {
                this.have_det = have_det;
            }

            @Override
            public String toString() {
                return "GdlistBean{" +
                        "id='" + id + '\'' +
                        ", order_id='" + order_id + '\'' +
                        ", goodsid='" + goodsid + '\'' +
                        ", goodsname='" + goodsname + '\'' +
                        ", goodscost='" + goodscost + '\'' +
                        ", goodscount='" + goodscount + '\'' +
                        ", status='" + status + '\'' +
                        ", shopid='" + shopid + '\'' +
                        ", is_send='" + is_send + '\'' +
                        ", product_id='" + product_id + '\'' +
                        ", have_det='" + have_det + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "ordertype='" + ordertype + '\'' +
                    ", paystatusintro='" + paystatusintro + '\'' +
                    ", shopcost='" + shopcost + '\'' +
                    ", shopps='" + shopps + '\'' +
                    ", cxcost=" + cxcost +
                    ", allcost='" + allcost + '\'' +
                    ", shoptype='" + shoptype + '\'' +
                    ", is_goshop='" + is_goshop + '\'' +
                    ", pstype='" + pstype + '\'' +
                    ", posttime='" + posttime + '\'' +
                    ", buyername='" + buyername + '\'' +
                    ", buyerphone='" + buyerphone + '\'' +
                    ", buyeraddress='" + buyeraddress + '\'' +
                    ", shopid='" + shopid + '\'' +
                    ", id='" + id + '\'' +
                    ", shopname='" + shopname + '\'' +
                    ", shopaddress='" + shopaddress + '\'' +
                    ", shopphone='" + shopphone + '\'' +
                    ", content='" + content + '\'' +
                    ", dno='" + dno + '\'' +
                    ", addtime='" + addtime + '\'' +
                    ", sendtime='" + sendtime + '\'' +
                    ", suretime='" + suretime + '\'' +
                    ", passtime='" + passtime + '\'' +
                    ", is_reback='" + is_reback + '\'' +
                    ", is_print='" + is_print + '\'' +
                    ", status='" + status + '\'' +
                    ", is_make='" + is_make + '\'' +
                    ", paystatus='" + paystatus + '\'' +
                    ", paytype='" + paytype + '\'' +
                    ", is_ping='" + is_ping + '\'' +
                    ", statuslist=" + statuslist +
                    ", gdlist=" + gdlist +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "OrderDetaile{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }


}
