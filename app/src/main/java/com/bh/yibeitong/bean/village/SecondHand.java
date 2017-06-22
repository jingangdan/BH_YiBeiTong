package com.bh.yibeitong.bean.village;

import java.util.List;

/**
 * Created by jingang on 2017/2/22.
 * 二手交易
 */

public class SecondHand {

    /**
     * error : false
     * msg : {"cate":{"id":"1","catename":"二手交易","parent_id":"0","cate_order":"0","img":"","guanzhu":"6","count":1,"usergz":1},"togethersaylist":[{"id":"37","uid":"245","usercontent":"二手iphone7 128g","userimg":"https://www.ybt9.com/upload/wximages/20170118160031143.png","addtime":"1484726432","cityname":"","areaname":"","streetname":"","is_top":"0","is_show":"1","cate_id":"1","title":"二手手机","password":"4a96a00d3fb8c67574c7a8c85df75e5d","email":"","username":"f09460e8","phone":"15634310460","address":"","creattime":"1482462003","logintime":"1482462003","usertype":"0","score":"10.8400","yes_score":null,"tod_score":null,"con_score":null,"cost":"0.00","loginip":"112.233.1.122","logo":"/upload/goods/20161012145612741.png","status":"0","safepwd":null,"group":"5","is_bang":"0","parent_id":"0","total":"0","admin_id":"0","sex":"0","wxopenid":"","temp_password":"762089","shopid":"0","shopcost":"0.00","backacount":null,"md_flag":"0","pingjiazongshu":0,"zongzanshu":1,"wxuserimgarr":["https://www.ybt9.com/upload/wximages/20170118160031143.png"],"is_zan":0}],"togethersaycomlist":[]}
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
         * cate : {"id":"1","catename":"二手交易","parent_id":"0","cate_order":"0","img":"","guanzhu":"6","count":1,"usergz":1}
         * togethersaylist : [{"id":"37","uid":"245","usercontent":"二手iphone7 128g","userimg":"https://www.ybt9.com/upload/wximages/20170118160031143.png","addtime":"1484726432","cityname":"","areaname":"","streetname":"","is_top":"0","is_show":"1","cate_id":"1","title":"二手手机","password":"4a96a00d3fb8c67574c7a8c85df75e5d","email":"","username":"f09460e8","phone":"15634310460","address":"","creattime":"1482462003","logintime":"1482462003","usertype":"0","score":"10.8400","yes_score":null,"tod_score":null,"con_score":null,"cost":"0.00","loginip":"112.233.1.122","logo":"/upload/goods/20161012145612741.png","status":"0","safepwd":null,"group":"5","is_bang":"0","parent_id":"0","total":"0","admin_id":"0","sex":"0","wxopenid":"","temp_password":"762089","shopid":"0","shopcost":"0.00","backacount":null,"md_flag":"0","pingjiazongshu":0,"zongzanshu":1,"wxuserimgarr":["https://www.ybt9.com/upload/wximages/20170118160031143.png"],"is_zan":0}]
         * togethersaycomlist : []
         */

        private CateBean cate;
        private List<TogethersaylistBean> togethersaylist;
        private List<?> togethersaycomlist;

        public CateBean getCate() {
            return cate;
        }

        public void setCate(CateBean cate) {
            this.cate = cate;
        }

        public List<TogethersaylistBean> getTogethersaylist() {
            return togethersaylist;
        }

        public void setTogethersaylist(List<TogethersaylistBean> togethersaylist) {
            this.togethersaylist = togethersaylist;
        }

        public List<?> getTogethersaycomlist() {
            return togethersaycomlist;
        }

        public void setTogethersaycomlist(List<?> togethersaycomlist) {
            this.togethersaycomlist = togethersaycomlist;
        }

        public static class CateBean {
            /**
             * id : 1
             * catename : 二手交易
             * parent_id : 0
             * cate_order : 0
             * img :
             * guanzhu : 6
             * count : 1
             * usergz : 1
             */

            private String id;
            private String catename;
            private String parent_id;
            private String cate_order;
            private String img;
            private String guanzhu;
            private int count;
            private int usergz;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCatename() {
                return catename;
            }

            public void setCatename(String catename) {
                this.catename = catename;
            }

            public String getParent_id() {
                return parent_id;
            }

            public void setParent_id(String parent_id) {
                this.parent_id = parent_id;
            }

            public String getCate_order() {
                return cate_order;
            }

            public void setCate_order(String cate_order) {
                this.cate_order = cate_order;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getGuanzhu() {
                return guanzhu;
            }

            public void setGuanzhu(String guanzhu) {
                this.guanzhu = guanzhu;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getUsergz() {
                return usergz;
            }

            public void setUsergz(int usergz) {
                this.usergz = usergz;
            }
        }

        public static class TogethersaylistBean {
            /**
             * id : 37
             * uid : 245
             * usercontent : 二手iphone7 128g
             * userimg : https://www.ybt9.com/upload/wximages/20170118160031143.png
             * addtime : 1484726432
             * cityname :
             * areaname :
             * streetname :
             * is_top : 0
             * is_show : 1
             * cate_id : 1
             * title : 二手手机
             * password : 4a96a00d3fb8c67574c7a8c85df75e5d
             * email :
             * username : f09460e8
             * phone : 15634310460
             * address :
             * creattime : 1482462003
             * logintime : 1482462003
             * usertype : 0
             * score : 10.8400
             * yes_score : null
             * tod_score : null
             * con_score : null
             * cost : 0.00
             * loginip : 112.233.1.122
             * logo : /upload/goods/20161012145612741.png
             * status : 0
             * safepwd : null
             * group : 5
             * is_bang : 0
             * parent_id : 0
             * total : 0
             * admin_id : 0
             * sex : 0
             * wxopenid :
             * temp_password : 762089
             * shopid : 0
             * shopcost : 0.00
             * backacount : null
             * md_flag : 0
             * pingjiazongshu : 0
             * zongzanshu : 1
             * wxuserimgarr : ["https://www.ybt9.com/upload/wximages/20170118160031143.png"]
             * is_zan : 0
             */

            private String id;
            private String uid;
            private String usercontent;
            private String userimg;
            private String addtime;
            private String cityname;
            private String areaname;
            private String streetname;
            private String is_top;
            private String is_show;
            private String cate_id;
            private String title;
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
            private String temp_password;
            private String shopid;
            private String shopcost;
            private Object backacount;
            private String md_flag;
            private int pingjiazongshu;
            private int zongzanshu;
            private int is_zan;
            private List<String> wxuserimgarr;

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

            public String getUsercontent() {
                return usercontent;
            }

            public void setUsercontent(String usercontent) {
                this.usercontent = usercontent;
            }

            public String getUserimg() {
                return userimg;
            }

            public void setUserimg(String userimg) {
                this.userimg = userimg;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getCityname() {
                return cityname;
            }

            public void setCityname(String cityname) {
                this.cityname = cityname;
            }

            public String getAreaname() {
                return areaname;
            }

            public void setAreaname(String areaname) {
                this.areaname = areaname;
            }

            public String getStreetname() {
                return streetname;
            }

            public void setStreetname(String streetname) {
                this.streetname = streetname;
            }

            public String getIs_top() {
                return is_top;
            }

            public void setIs_top(String is_top) {
                this.is_top = is_top;
            }

            public String getIs_show() {
                return is_show;
            }

            public void setIs_show(String is_show) {
                this.is_show = is_show;
            }

            public String getCate_id() {
                return cate_id;
            }

            public void setCate_id(String cate_id) {
                this.cate_id = cate_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
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

            public String getTemp_password() {
                return temp_password;
            }

            public void setTemp_password(String temp_password) {
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

            public int getPingjiazongshu() {
                return pingjiazongshu;
            }

            public void setPingjiazongshu(int pingjiazongshu) {
                this.pingjiazongshu = pingjiazongshu;
            }

            public int getZongzanshu() {
                return zongzanshu;
            }

            public void setZongzanshu(int zongzanshu) {
                this.zongzanshu = zongzanshu;
            }

            public int getIs_zan() {
                return is_zan;
            }

            public void setIs_zan(int is_zan) {
                this.is_zan = is_zan;
            }

            public List<String> getWxuserimgarr() {
                return wxuserimgarr;
            }

            public void setWxuserimgarr(List<String> wxuserimgarr) {
                this.wxuserimgarr = wxuserimgarr;
            }

            @Override
            public String toString() {
                return "TogethersaylistBean{" +
                        "id='" + id + '\'' +
                        ", uid='" + uid + '\'' +
                        ", usercontent='" + usercontent + '\'' +
                        ", userimg='" + userimg + '\'' +
                        ", addtime='" + addtime + '\'' +
                        ", cityname='" + cityname + '\'' +
                        ", areaname='" + areaname + '\'' +
                        ", streetname='" + streetname + '\'' +
                        ", is_top='" + is_top + '\'' +
                        ", is_show='" + is_show + '\'' +
                        ", cate_id='" + cate_id + '\'' +
                        ", title='" + title + '\'' +
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
                        ", temp_password='" + temp_password + '\'' +
                        ", shopid='" + shopid + '\'' +
                        ", shopcost='" + shopcost + '\'' +
                        ", backacount=" + backacount +
                        ", md_flag='" + md_flag + '\'' +
                        ", pingjiazongshu=" + pingjiazongshu +
                        ", zongzanshu=" + zongzanshu +
                        ", is_zan=" + is_zan +
                        ", wxuserimgarr=" + wxuserimgarr +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "cate=" + cate +
                    ", togethersaylist=" + togethersaylist +
                    ", togethersaycomlist=" + togethersaycomlist +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SecondHand{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
