package com.bh.yibeitong.bean.homepage;

import java.util.List;

/**
 * Created by jingang on 2017/7/5.
 * 充值
 */

public class Recharge {

    /**
     * error : false
     * msg : {"rechargelist":[{"id":"2","cost":"100.00","is_sendcost":"1","sendcost":"2.00","is_sendjuan":"0","sendjuancost":"0.00","orderid":"2","totalsendcost":2,"juanname":"充值100.00元赠送2.00元"},{"id":"3","cost":"200.00","is_sendcost":"1","sendcost":"10.00","is_sendjuan":"0","sendjuancost":"100.00","orderid":"3","totalsendcost":10,"juanname":"充值200.00元赠送10.00元"},{"id":"4","cost":"500.00","is_sendcost":"1","sendcost":"25.00","is_sendjuan":"0","sendjuancost":"100.00","orderid":"4","totalsendcost":25,"juanname":"充值500.00元赠送25.00元"}]}
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
        private List<RechargelistBean> rechargelist;

        public List<RechargelistBean> getRechargelist() {
            return rechargelist;
        }

        public void setRechargelist(List<RechargelistBean> rechargelist) {
            this.rechargelist = rechargelist;
        }

        public static class RechargelistBean {
            /**
             * id : 2
             * cost : 100.00
             * is_sendcost : 1
             * sendcost : 2.00
             * is_sendjuan : 0
             * sendjuancost : 0.00
             * orderid : 2
             * totalsendcost : 2
             * juanname : 充值100.00元赠送2.00元
             */

            private String id;
            private String cost;
            private String is_sendcost;
            private String sendcost;
            private String is_sendjuan;
            private String sendjuancost;
            private String orderid;
            private int totalsendcost;
            private String juanname;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCost() {
                return cost;
            }

            public void setCost(String cost) {
                this.cost = cost;
            }

            public String getIs_sendcost() {
                return is_sendcost;
            }

            public void setIs_sendcost(String is_sendcost) {
                this.is_sendcost = is_sendcost;
            }

            public String getSendcost() {
                return sendcost;
            }

            public void setSendcost(String sendcost) {
                this.sendcost = sendcost;
            }

            public String getIs_sendjuan() {
                return is_sendjuan;
            }

            public void setIs_sendjuan(String is_sendjuan) {
                this.is_sendjuan = is_sendjuan;
            }

            public String getSendjuancost() {
                return sendjuancost;
            }

            public void setSendjuancost(String sendjuancost) {
                this.sendjuancost = sendjuancost;
            }

            public String getOrderid() {
                return orderid;
            }

            public void setOrderid(String orderid) {
                this.orderid = orderid;
            }

            public int getTotalsendcost() {
                return totalsendcost;
            }

            public void setTotalsendcost(int totalsendcost) {
                this.totalsendcost = totalsendcost;
            }

            public String getJuanname() {
                return juanname;
            }

            public void setJuanname(String juanname) {
                this.juanname = juanname;
            }

            @Override
            public String toString() {
                return "RechargelistBean{" +
                        "id='" + id + '\'' +
                        ", cost='" + cost + '\'' +
                        ", is_sendcost='" + is_sendcost + '\'' +
                        ", sendcost='" + sendcost + '\'' +
                        ", is_sendjuan='" + is_sendjuan + '\'' +
                        ", sendjuancost='" + sendjuancost + '\'' +
                        ", orderid='" + orderid + '\'' +
                        ", totalsendcost=" + totalsendcost +
                        ", juanname='" + juanname + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "rechargelist=" + rechargelist +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Recharge{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
