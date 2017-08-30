package com.bh.yibeitong.bean;

/**
 * Created by jingang on 2017/8/30.
 * 退款详情（实体类）
 */

public class BackLog {

    /**
     * error : false
     * msg : {"id":"192","uid":"13","username":"aaaaa","reason":"???????","orderid":"23040","shopid":"23","content":"typeid=1","phone":"","contactname":"","status":"0","addtime":"1504077092","cost":"10.00","bkcontent":null,"admin_id":"0","type":"0"}
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
         * id : 192
         * uid : 13
         * username : aaaaa
         * reason : ???????
         * orderid : 23040
         * shopid : 23
         * content : typeid=1
         * phone :
         * contactname :
         * status : 0
         * addtime : 1504077092
         * cost : 10.00
         * bkcontent : null
         * admin_id : 0
         * type : 0
         */

        private String id;
        private String uid;
        private String username;
        private String reason;
        private String orderid;
        private String shopid;
        private String content;
        private String phone;
        private String contactname;
        private String status;
        private String addtime;
        private String cost;
        private Object bkcontent;
        private String admin_id;
        private String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getContactname() {
            return contactname;
        }

        public void setContactname(String contactname) {
            this.contactname = contactname;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public Object getBkcontent() {
            return bkcontent;
        }

        public void setBkcontent(Object bkcontent) {
            this.bkcontent = bkcontent;
        }

        public String getAdmin_id() {
            return admin_id;
        }

        public void setAdmin_id(String admin_id) {
            this.admin_id = admin_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "id='" + id + '\'' +
                    ", uid='" + uid + '\'' +
                    ", username='" + username + '\'' +
                    ", reason='" + reason + '\'' +
                    ", orderid='" + orderid + '\'' +
                    ", shopid='" + shopid + '\'' +
                    ", content='" + content + '\'' +
                    ", phone='" + phone + '\'' +
                    ", contactname='" + contactname + '\'' +
                    ", status='" + status + '\'' +
                    ", addtime='" + addtime + '\'' +
                    ", cost='" + cost + '\'' +
                    ", bkcontent=" + bkcontent +
                    ", admin_id='" + admin_id + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "BackLog{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
