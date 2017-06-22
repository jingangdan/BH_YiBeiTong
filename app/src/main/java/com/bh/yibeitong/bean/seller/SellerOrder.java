package com.bh.yibeitong.bean.seller;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jingang on 2017/4/12.
 * 订单（商家端）
 */

public class SellerOrder implements Serializable{
    /**
     * error : false
     * msg : [{"id":"22977","addtime":"16:06:09","posttime":"04-12 18:00-18:30","paytype":"0","paystatus":"0","dno":"14919843696144","is_reback":"0","allcost":"19.90","status":"1","is_make":"0","daycode":"2","buyeruid":"245","is_goshop":"0","buyername":"设计设计","buyerphone":"15634310460","buyeraddress":"开元花半里开元花半里","postdate":"18:00-18:30","showstatus":"新订单","payresult":"货到支付","orderNum":1},{"id":"22976","addtime":"16:04:49","posttime":"04-12 16:30-17:00","paytype":"1","paystatus":"1","dno":"14919842893175","is_reback":"0","allcost":"22.50","status":"1","is_make":"0","daycode":"1","buyeruid":"245","is_goshop":"0","buyername":"设计设计","buyerphone":"15634310460","buyeraddress":"开元花半里开元花半里","postdate":"16:30-17:00","showstatus":"新订单","payresult":"在线支付已付","orderNum":1},{"id":"22978","addtime":"16:07:04","posttime":"04-12 16:30-17:00","paytype":"0","paystatus":"0","dno":"14919844247298","is_reback":"0","allcost":"37.40","status":"1","is_make":"0","daycode":"3","buyeruid":"245","is_goshop":"0","buyername":"设计设计","buyerphone":"15634310460","buyeraddress":"开元花半里开元花半里","postdate":"16:30-17:00","showstatus":"新订单","payresult":"货到支付","orderNum":1}]
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
         * id : 22977
         * addtime : 16:06:09
         * posttime : 04-12 18:00-18:30
         * paytype : 0
         * paystatus : 0
         * dno : 14919843696144
         * is_reback : 0
         * allcost : 19.90
         * status : 1
         * is_make : 0
         * daycode : 2
         * buyeruid : 245
         * is_goshop : 0
         * buyername : 设计设计
         * buyerphone : 15634310460
         * buyeraddress : 开元花半里开元花半里
         * postdate : 18:00-18:30
         * showstatus : 新订单
         * payresult : 货到支付
         * orderNum : 1
         */

        private String id;
        private String addtime;
        private String posttime;
        private String paytype;
        private String paystatus;
        private String dno;
        private String is_reback;
        private String allcost;
        private String status;
        private String is_make;
        private String daycode;
        private String buyeruid;
        private String is_goshop;
        private String buyername;
        private String buyerphone;
        private String buyeraddress;
        private String postdate;
        private String showstatus;
        private String payresult;
        private int orderNum;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getPosttime() {
            return posttime;
        }

        public void setPosttime(String posttime) {
            this.posttime = posttime;
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

        public String getDno() {
            return dno;
        }

        public void setDno(String dno) {
            this.dno = dno;
        }

        public String getIs_reback() {
            return is_reback;
        }

        public void setIs_reback(String is_reback) {
            this.is_reback = is_reback;
        }

        public String getAllcost() {
            return allcost;
        }

        public void setAllcost(String allcost) {
            this.allcost = allcost;
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

        public String getDaycode() {
            return daycode;
        }

        public void setDaycode(String daycode) {
            this.daycode = daycode;
        }

        public String getBuyeruid() {
            return buyeruid;
        }

        public void setBuyeruid(String buyeruid) {
            this.buyeruid = buyeruid;
        }

        public String getIs_goshop() {
            return is_goshop;
        }

        public void setIs_goshop(String is_goshop) {
            this.is_goshop = is_goshop;
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

        public String getPostdate() {
            return postdate;
        }

        public void setPostdate(String postdate) {
            this.postdate = postdate;
        }

        public String getShowstatus() {
            return showstatus;
        }

        public void setShowstatus(String showstatus) {
            this.showstatus = showstatus;
        }

        public String getPayresult() {
            return payresult;
        }

        public void setPayresult(String payresult) {
            this.payresult = payresult;
        }

        public int getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(int orderNum) {
            this.orderNum = orderNum;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "id='" + id + '\'' +
                    ", addtime='" + addtime + '\'' +
                    ", posttime='" + posttime + '\'' +
                    ", paytype='" + paytype + '\'' +
                    ", paystatus='" + paystatus + '\'' +
                    ", dno='" + dno + '\'' +
                    ", is_reback='" + is_reback + '\'' +
                    ", allcost='" + allcost + '\'' +
                    ", status='" + status + '\'' +
                    ", is_make='" + is_make + '\'' +
                    ", daycode='" + daycode + '\'' +
                    ", buyeruid='" + buyeruid + '\'' +
                    ", is_goshop='" + is_goshop + '\'' +
                    ", buyername='" + buyername + '\'' +
                    ", buyerphone='" + buyerphone + '\'' +
                    ", buyeraddress='" + buyeraddress + '\'' +
                    ", postdate='" + postdate + '\'' +
                    ", showstatus='" + showstatus + '\'' +
                    ", payresult='" + payresult + '\'' +
                    ", orderNum=" + orderNum +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SellerOrder{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
