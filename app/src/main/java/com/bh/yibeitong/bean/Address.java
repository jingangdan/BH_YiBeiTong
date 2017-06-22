package com.bh.yibeitong.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jingang on 2016/11/22.
 * 我的收货地址
 */

public class Address implements Serializable {


    /**
     * error : false
     * msg : [{"id":"58","username":"17865069350","address":"颐高上海街1期A座2006","phone":"17865069350","contactname":"金刚","default":"0","sex":"0","bigadr":"颐高上海街","detailadr":"1期A座2006","lat":"35.111218","lng":"35.111218"}]
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
         * id : 58
         * username : 17865069350
         * address : 颐高上海街1期A座2006
         * phone : 17865069350
         * contactname : 金刚
         * default : 0
         * sex : 0
         * bigadr : 颐高上海街
         * detailadr : 1期A座2006
         * lat : 35.111218
         * lng : 35.111218
         */

        private String id;
        private String username;
        private String address;
        private String phone;
        private String contactname;
        @SerializedName("default")
        private String defaultX;
        private String sex;
        private String bigadr;
        private String detailadr;
        private String lat;
        private String lng;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

        public String getDefaultX() {
            return defaultX;
        }

        public void setDefaultX(String defaultX) {
            this.defaultX = defaultX;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBigadr() {
            return bigadr;
        }

        public void setBigadr(String bigadr) {
            this.bigadr = bigadr;
        }

        public String getDetailadr() {
            return detailadr;
        }

        public void setDetailadr(String detailadr) {
            this.detailadr = detailadr;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "id='" + id + '\'' +
                    ", username='" + username + '\'' +
                    ", address='" + address + '\'' +
                    ", phone='" + phone + '\'' +
                    ", contactname='" + contactname + '\'' +
                    ", defaultX='" + defaultX + '\'' +
                    ", sex='" + sex + '\'' +
                    ", bigadr='" + bigadr + '\'' +
                    ", detailadr='" + detailadr + '\'' +
                    ", lat='" + lat + '\'' +
                    ", lng='" + lng + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Address{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
