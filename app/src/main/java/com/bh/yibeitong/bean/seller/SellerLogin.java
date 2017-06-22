package com.bh.yibeitong.bean.seller;

/**
 * Created by jingang on 2017/2/17.
 * 商家端登陆返回
 */

public class SellerLogin {

    /**
     * error : false
     * msg : {"uid":"260","email":"ybthbl@ybt9.com","username":"ybthbl","phone":"15376913615","address":"","creattime":"1484445133","logintime":"1489475570","usertype":"0","score":"0.0000","yes_score":null,"tod_score":null,"con_score":null,"cost":"0.00","loginip":"39.76.90.2","logo":"","status":"0","safepwd":null,"group":"3","is_bang":"0","parent_id":"0","total":"0","admin_id":"0","sex":"0","wxopenid":"","temp_password":null,"shopid":"0","shopcost":"0.00","backacount":null,"md_flag":"0","shoptype":0}
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
         * uid : 260
         * email : ybthbl@ybt9.com
         * username : ybthbl
         * phone : 15376913615
         * address :
         * creattime : 1484445133
         * logintime : 1489475570
         * usertype : 0
         * score : 0.0000
         * yes_score : null
         * tod_score : null
         * con_score : null
         * cost : 0.00
         * loginip : 39.76.90.2
         * logo :
         * status : 0
         * safepwd : null
         * group : 3
         * is_bang : 0
         * parent_id : 0
         * total : 0
         * admin_id : 0
         * sex : 0
         * wxopenid :
         * temp_password : null
         * shopid : 0
         * shopcost : 0.00
         * backacount : null
         * md_flag : 0
         * shoptype : 0
         */

        private String uid;
        private String email;
        private String username;
        private String phone;
        private String address;
        private String creattime;
        private String logintime;
        private String usertype;
        private String score;
        private Object yes_score;
        private Object tod_score;
        private Object con_score;
        private String cost;
        private String loginip;
        private String logo;
        private String status;
        private Object safepwd;
        private String group;
        private String is_bang;
        private String parent_id;
        private String total;
        private String admin_id;
        private String sex;
        private String wxopenid;
        private Object temp_password;
        private String shopid;
        private String shopcost;
        private Object backacount;
        private String md_flag;
        private int shoptype;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCreattime() {
            return creattime;
        }

        public void setCreattime(String creattime) {
            this.creattime = creattime;
        }

        public String getLogintime() {
            return logintime;
        }

        public void setLogintime(String logintime) {
            this.logintime = logintime;
        }

        public String getUsertype() {
            return usertype;
        }

        public void setUsertype(String usertype) {
            this.usertype = usertype;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public Object getYes_score() {
            return yes_score;
        }

        public void setYes_score(Object yes_score) {
            this.yes_score = yes_score;
        }

        public Object getTod_score() {
            return tod_score;
        }

        public void setTod_score(Object tod_score) {
            this.tod_score = tod_score;
        }

        public Object getCon_score() {
            return con_score;
        }

        public void setCon_score(Object con_score) {
            this.con_score = con_score;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public String getLoginip() {
            return loginip;
        }

        public void setLoginip(String loginip) {
            this.loginip = loginip;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getSafepwd() {
            return safepwd;
        }

        public void setSafepwd(Object safepwd) {
            this.safepwd = safepwd;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getIs_bang() {
            return is_bang;
        }

        public void setIs_bang(String is_bang) {
            this.is_bang = is_bang;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getAdmin_id() {
            return admin_id;
        }

        public void setAdmin_id(String admin_id) {
            this.admin_id = admin_id;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getWxopenid() {
            return wxopenid;
        }

        public void setWxopenid(String wxopenid) {
            this.wxopenid = wxopenid;
        }

        public Object getTemp_password() {
            return temp_password;
        }

        public void setTemp_password(Object temp_password) {
            this.temp_password = temp_password;
        }

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public String getShopcost() {
            return shopcost;
        }

        public void setShopcost(String shopcost) {
            this.shopcost = shopcost;
        }

        public Object getBackacount() {
            return backacount;
        }

        public void setBackacount(Object backacount) {
            this.backacount = backacount;
        }

        public String getMd_flag() {
            return md_flag;
        }

        public void setMd_flag(String md_flag) {
            this.md_flag = md_flag;
        }

        public int getShoptype() {
            return shoptype;
        }

        public void setShoptype(int shoptype) {
            this.shoptype = shoptype;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "uid='" + uid + '\'' +
                    ", email='" + email + '\'' +
                    ", username='" + username + '\'' +
                    ", phone='" + phone + '\'' +
                    ", address='" + address + '\'' +
                    ", creattime='" + creattime + '\'' +
                    ", logintime='" + logintime + '\'' +
                    ", usertype='" + usertype + '\'' +
                    ", score='" + score + '\'' +
                    ", yes_score=" + yes_score +
                    ", tod_score=" + tod_score +
                    ", con_score=" + con_score +
                    ", cost='" + cost + '\'' +
                    ", loginip='" + loginip + '\'' +
                    ", logo='" + logo + '\'' +
                    ", status='" + status + '\'' +
                    ", safepwd=" + safepwd +
                    ", group='" + group + '\'' +
                    ", is_bang='" + is_bang + '\'' +
                    ", parent_id='" + parent_id + '\'' +
                    ", total='" + total + '\'' +
                    ", admin_id='" + admin_id + '\'' +
                    ", sex='" + sex + '\'' +
                    ", wxopenid='" + wxopenid + '\'' +
                    ", temp_password=" + temp_password +
                    ", shopid='" + shopid + '\'' +
                    ", shopcost='" + shopcost + '\'' +
                    ", backacount=" + backacount +
                    ", md_flag='" + md_flag + '\'' +
                    ", shoptype=" + shoptype +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SellerLogin{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }

}
