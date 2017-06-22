package com.bh.yibeitong.bean;

import java.util.List;

/**
 * Created by jingang on 2016/12/14.
 * 优惠券
 */

public class YHQuan {

    /**
     * error : false
     * msg : [{"id":"3933","card":"256","card_password":"f7184","status":"1","creattime":"1480521600","cost":"5","limitcost":"30","endtime":"2017-03-31","uid":"220","username":"15634310460","usetime":"0","name":"注册赠送","shareuid":"0","paytype":3,"type":null,"orderid":null,"bangphone":null}]
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
         * id : 3933
         * card : 256
         * card_password : f7184
         * status : 1
         * creattime : 1480521600
         * cost : 5
         * limitcost : 30
         * endtime : 2017-03-31
         * uid : 220
         * username : 15634310460
         * usetime : 0
         * name : 注册赠送
         * shareuid : 0
         * paytype : 3
         * type : null
         * orderid : null
         * bangphone : null
         */

        private String id;
        private String card;
        private String card_password;
        private String status;
        private String creattime;
        private String cost;
        private String limitcost;
        private String endtime;
        private String uid;
        private String username;
        private String usetime;
        private String name;
        private String shareuid;
        private int paytype;
        private Object type;
        private Object orderid;
        private Object bangphone;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public String getCard_password() {
            return card_password;
        }

        public void setCard_password(String card_password) {
            this.card_password = card_password;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreattime() {
            return creattime;
        }

        public void setCreattime(String creattime) {
            this.creattime = creattime;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public String getLimitcost() {
            return limitcost;
        }

        public void setLimitcost(String limitcost) {
            this.limitcost = limitcost;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUsetime() {
            return usetime;
        }

        public void setUsetime(String usetime) {
            this.usetime = usetime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShareuid() {
            return shareuid;
        }

        public void setShareuid(String shareuid) {
            this.shareuid = shareuid;
        }

        public int getPaytype() {
            return paytype;
        }

        public void setPaytype(int paytype) {
            this.paytype = paytype;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public Object getOrderid() {
            return orderid;
        }

        public void setOrderid(Object orderid) {
            this.orderid = orderid;
        }

        public Object getBangphone() {
            return bangphone;
        }

        public void setBangphone(Object bangphone) {
            this.bangphone = bangphone;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "id='" + id + '\'' +
                    ", card='" + card + '\'' +
                    ", card_password='" + card_password + '\'' +
                    ", status='" + status + '\'' +
                    ", creattime='" + creattime + '\'' +
                    ", cost='" + cost + '\'' +
                    ", limitcost='" + limitcost + '\'' +
                    ", endtime='" + endtime + '\'' +
                    ", uid='" + uid + '\'' +
                    ", username='" + username + '\'' +
                    ", usetime='" + usetime + '\'' +
                    ", name='" + name + '\'' +
                    ", shareuid='" + shareuid + '\'' +
                    ", paytype=" + paytype +
                    ", type=" + type +
                    ", orderid=" + orderid +
                    ", bangphone=" + bangphone +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "YHQuan{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
