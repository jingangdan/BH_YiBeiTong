package com.bh.yibeitong.seller.bean;

import java.util.List;

/**
 * Created by jingang on 2017/8/9.
 * 评价留言实体类
 */

public class ManagesCommt {

    /**
     * error : false
     * msg : [{"id":"22","orderid":"22968","orderdetid":"14587","shopid":"23","goodsid":"3823","uid":"309","content":"","addtime":"2017-02-09","replycontent":null,"replytime":"","point":"5","is_show":"0","virtualname":null,"username":"18263975553","logo":"","name":null,"repstatus":0},{"id":"21","orderid":"22968","orderdetid":"14586","shopid":"23","goodsid":"3625","uid":"309","content":"","addtime":"2017-02-09","replycontent":null,"replytime":"","point":"5","is_show":"0","virtualname":null,"username":"18263975553","logo":"","name":null,"repstatus":0},{"id":"20","orderid":"22968","orderdetid":"14588","shopid":"23","goodsid":"4025","uid":"309","content":"","addtime":"2017-02-09","replycontent":null,"replytime":"","point":"5","is_show":"0","virtualname":null,"username":"18263975553","logo":"","name":null,"repstatus":0}]
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
         * id : 22
         * orderid : 22968
         * orderdetid : 14587
         * shopid : 23
         * goodsid : 3823
         * uid : 309
         * content :
         * addtime : 2017-02-09
         * replycontent : null
         * replytime :
         * point : 5
         * is_show : 0
         * virtualname : null
         * username : 18263975553
         * logo :
         * name : null
         * repstatus : 0
         */

        private String id;
        private String orderid;
        private String orderdetid;
        private String shopid;
        private String goodsid;
        private String uid;
        private String content;
        private String addtime;
        private Object replycontent;
        private String replytime;
        private String point;
        private String is_show;
        private Object virtualname;
        private String username;
        private String logo;
        private Object name;
        private int repstatus;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getOrderdetid() {
            return orderdetid;
        }

        public void setOrderdetid(String orderdetid) {
            this.orderdetid = orderdetid;
        }

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public String getGoodsid() {
            return goodsid;
        }

        public void setGoodsid(String goodsid) {
            this.goodsid = goodsid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public Object getReplycontent() {
            return replycontent;
        }

        public void setReplycontent(Object replycontent) {
            this.replycontent = replycontent;
        }

        public String getReplytime() {
            return replytime;
        }

        public void setReplytime(String replytime) {
            this.replytime = replytime;
        }

        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }

        public String getIs_show() {
            return is_show;
        }

        public void setIs_show(String is_show) {
            this.is_show = is_show;
        }

        public Object getVirtualname() {
            return virtualname;
        }

        public void setVirtualname(Object virtualname) {
            this.virtualname = virtualname;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }

        public int getRepstatus() {
            return repstatus;
        }

        public void setRepstatus(int repstatus) {
            this.repstatus = repstatus;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "id='" + id + '\'' +
                    ", orderid='" + orderid + '\'' +
                    ", orderdetid='" + orderdetid + '\'' +
                    ", shopid='" + shopid + '\'' +
                    ", goodsid='" + goodsid + '\'' +
                    ", uid='" + uid + '\'' +
                    ", content='" + content + '\'' +
                    ", addtime='" + addtime + '\'' +
                    ", replycontent=" + replycontent +
                    ", replytime='" + replytime + '\'' +
                    ", point='" + point + '\'' +
                    ", is_show='" + is_show + '\'' +
                    ", virtualname=" + virtualname +
                    ", username='" + username + '\'' +
                    ", logo='" + logo + '\'' +
                    ", name=" + name +
                    ", repstatus=" + repstatus +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ManagesCommt{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
