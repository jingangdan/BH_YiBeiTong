package com.bh.yibeitong.bean;

/**
 * Created by jingang on 2016/12/16.
 * 用户信息
 */

public class UserInfos {

    /**
     * error : false
     * msg : {"uid":"221","password":"e10adc3949ba59abbe56e057f20f883e","email":"","username":"17865069350","phone":"17865069350","address":"","creattime":"1481785331","logintime":"1481857413","usertype":"0","score":"0.0000","yes_score":null,"tod_score":null,"con_score":null,"cost":"2405.00","loginip":"112.251.122.43","logo":"","status":"0","safepwd":null,"group":"5","is_bang":"0","parent_id":"0","total":"0","admin_id":"0","sex":"0","wxopenid":"","temp_password":null,"shopid":"0","shopcost":"0.00","backacount":null,"md_flag":"0","sitephone":"05398606806","juancount":1}
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
         * uid : 221
         * password : e10adc3949ba59abbe56e057f20f883e
         * email :
         * username : 17865069350
         * phone : 17865069350
         * address :
         * creattime : 1481785331
         * logintime : 1481857413
         * usertype : 0
         * score : 0.0000
         * yes_score : null
         * tod_score : null
         * con_score : null
         * cost : 2405.00
         * loginip : 112.251.122.43
         * logo :
         * status : 0
         * safepwd : null
         * group : 5
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
         * sitephone : 05398606806
         * juancount : 1
         */

        private String uid;
        private String password;
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
        private String sitephone;
        private int juancount;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
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

        public String getSitephone() {
            return sitephone;
        }

        public void setSitephone(String sitephone) {
            this.sitephone = sitephone;
        }

        public int getJuancount() {
            return juancount;
        }

        public void setJuancount(int juancount) {
            this.juancount = juancount;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "uid='" + uid + '\'' +
                    ", password='" + password + '\'' +
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
                    ", sitephone='" + sitephone + '\'' +
                    ", juancount=" + juancount +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UserInfos{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
