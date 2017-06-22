package com.bh.yibeitong.bean.shopbean;

/**
 * "uid":"28",
 * "phone":"05398606806",
 * "ruzhutype":"0",
 * "areaid1":"0",
 * "areaid2":"0",
 * "areaid3":"0",
 * "is_open":"1",
 * "goodlistmodule":"0",
 * "addtime":"1476416595",
 * "otherlink":"",
 * "zmimg":"",
 * "wxhui_ewmurl":"",
 * "pointcount":"2",
 * "starttime":"01:00-23:59",
 * "psservicepoint":"10",
 * "noticetype":null,
 * "foodtongimg":null,
 * "shoplogo":null,
 * "email":"cwz@ybt9.com",
 * "fmimg":"",
 * "jkzimg":null,
 * "endtime":"1507952595",
 * "sort":"999",
 * "is_onlinepay":"1",
 * "sqimg":null,
 * "sellcount":"3",
 * "lng":"118.303800",
 * "yjin":"0.00",
 * "shopname":"易贝通曹王庄店",
 * "machine_code":null,
 * "shoplicense":"",
 * "id":"8",
 * "is_pass":"1",
 * "is_recom":"0",
 * "shortname":"",
 * "lat":"35.079189",
 * "goodattrdefault":"个",
 * "mKey":null,
 * "qtshuoming":null,
 * "qiyeimg":"",
 * "shopbacklogo":null,
 * "virtualsellcounts":null,
 * "psservicepointcount":"2",
 * "point":"15",
 * "pradiusa":"10",
 * "maphone":"13695397227",
 * "IMEI":"",
 * "shoptype":"1",
 * "address":"临西八路",
 * "cx_info":null,
 * "admin_id":"0",
 * "is_daopay":"1",
 * "notice_info":null,
 * "intr_info":null
 */

public class ShopInfoBean {
    private String id;
    private String uid;
    private String shortname;
    private String shopname;
    private String shoplogo;
    private String phone;
    private String address;
    private String point;
    private String cx_info;
    private String intr_info;
    private String notice_info;
    private String starttime;
    private String is_open;
    private String is_onlinepay;
    private String is_daopay;
    private String is_pass;
    private String is_recom;
    private String maphone;
    private String addtime;
    private String pointcount;
    private String psservicepoint;
    private String psservicepointcount;
    private String yjin;
    private String lat;
    private String lng;
    private String shopbacklogo;
    private String sort;
    private String email;
    private String areaid1;
    private String areaid2;
    private String areaid3;
    private String otherlink;
    private String endtime;
    private String IMEI;
    private String shoptype;
    private String noticetype;
    private String machine_code;
    private String mKey;
    private String pradiusa;
    private String admin_id;
    private String sellcount;
    private String goodattrdefault;
    private String ruzhutype;
    private String qiyeimg;
    private String zmimg;
    private String fmimg;
    private String foodtongimg;
    private String jkzimg;
    private String sqimg;
    private String qtshuoming;
    private String wxhui_ewmurl;
    private String goodlistmodule;
    private String shoplicense;
    private String virtualsellcounts;

    public ShopInfoBean() {
    }

    public ShopInfoBean(String id, String uid, String shortname, String shopname, String shoplogo, String phone, String address, String point, String cx_info, String intr_info, String notice_info, String starttime, String is_open, String is_onlinepay, String is_daopay, String is_pass, String is_recom, String maphone, String addtime, String pointcount, String psservicepoint, String psservicepointcount, String yjin, String lat, String lng, String shopbacklogo, String sort, String email, String areaid1, String areaid2, String areaid3, String otherlink, String endtime, String IMEI, String shoptype, String noticetype, String machine_code, String mKey, String pradiusa, String admin_id, String sellcount, String goodattrdefault, String ruzhutype, String qiyeimg, String zmimg, String fmimg, String foodtongimg, String jkzimg, String sqimg, String qtshuoming, String wxhui_ewmurl, String goodlistmodule, String shoplicense, String virtualsellcounts) {
        this.id = id;
        this.uid = uid;
        this.shortname = shortname;
        this.shopname = shopname;
        this.shoplogo = shoplogo;
        this.phone = phone;
        this.address = address;
        this.point = point;
        this.cx_info = cx_info;
        this.intr_info = intr_info;
        this.notice_info = notice_info;
        this.starttime = starttime;
        this.is_open = is_open;
        this.is_onlinepay = is_onlinepay;
        this.is_daopay = is_daopay;
        this.is_pass = is_pass;
        this.is_recom = is_recom;
        this.maphone = maphone;
        this.addtime = addtime;
        this.pointcount = pointcount;
        this.psservicepoint = psservicepoint;
        this.psservicepointcount = psservicepointcount;
        this.yjin = yjin;
        this.lat = lat;
        this.lng = lng;
        this.shopbacklogo = shopbacklogo;
        this.sort = sort;
        this.email = email;
        this.areaid1 = areaid1;
        this.areaid2 = areaid2;
        this.areaid3 = areaid3;
        this.otherlink = otherlink;
        this.endtime = endtime;
        this.IMEI = IMEI;
        this.shoptype = shoptype;
        this.noticetype = noticetype;
        this.machine_code = machine_code;
        this.mKey = mKey;
        this.pradiusa = pradiusa;
        this.admin_id = admin_id;
        this.sellcount = sellcount;
        this.goodattrdefault = goodattrdefault;
        this.ruzhutype = ruzhutype;
        this.qiyeimg = qiyeimg;
        this.zmimg = zmimg;
        this.fmimg = fmimg;
        this.foodtongimg = foodtongimg;
        this.jkzimg = jkzimg;
        this.sqimg = sqimg;
        this.qtshuoming = qtshuoming;
        this.wxhui_ewmurl = wxhui_ewmurl;
        this.goodlistmodule = goodlistmodule;
        this.shoplicense = shoplicense;
        this.virtualsellcounts = virtualsellcounts;
    }

    public String getVirtualsellcounts() {
        return virtualsellcounts;
    }

    public void setVirtualsellcounts(String virtualsellcounts) {
        this.virtualsellcounts = virtualsellcounts;
    }

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

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getShoplogo() {
        return shoplogo;
    }

    public void setShoplogo(String shoplogo) {
        this.shoplogo = shoplogo;
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

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getCx_info() {
        return cx_info;
    }

    public void setCx_info(String cx_info) {
        this.cx_info = cx_info;
    }

    public String getIntr_info() {
        return intr_info;
    }

    public void setIntr_info(String intr_info) {
        this.intr_info = intr_info;
    }

    public String getNotice_info() {
        return notice_info;
    }

    public void setNotice_info(String notice_info) {
        this.notice_info = notice_info;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getIs_open() {
        return is_open;
    }

    public void setIs_open(String is_open) {
        this.is_open = is_open;
    }

    public String getIs_onlinepay() {
        return is_onlinepay;
    }

    public void setIs_onlinepay(String is_onlinepay) {
        this.is_onlinepay = is_onlinepay;
    }

    public String getIs_daopay() {
        return is_daopay;
    }

    public void setIs_daopay(String is_daopay) {
        this.is_daopay = is_daopay;
    }

    public String getIs_pass() {
        return is_pass;
    }

    public void setIs_pass(String is_pass) {
        this.is_pass = is_pass;
    }

    public String getIs_recom() {
        return is_recom;
    }

    public void setIs_recom(String is_recom) {
        this.is_recom = is_recom;
    }

    public String getMaphone() {
        return maphone;
    }

    public void setMaphone(String maphone) {
        this.maphone = maphone;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getPointcount() {
        return pointcount;
    }

    public void setPointcount(String pointcount) {
        this.pointcount = pointcount;
    }

    public String getPsservicepoint() {
        return psservicepoint;
    }

    public void setPsservicepoint(String psservicepoint) {
        this.psservicepoint = psservicepoint;
    }

    public String getPsservicepointcount() {
        return psservicepointcount;
    }

    public void setPsservicepointcount(String psservicepointcount) {
        this.psservicepointcount = psservicepointcount;
    }

    public String getYjin() {
        return yjin;
    }

    public void setYjin(String yjin) {
        this.yjin = yjin;
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

    public String getShopbacklogo() {
        return shopbacklogo;
    }

    public void setShopbacklogo(String shopbacklogo) {
        this.shopbacklogo = shopbacklogo;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAreaid1() {
        return areaid1;
    }

    public void setAreaid1(String areaid1) {
        this.areaid1 = areaid1;
    }

    public String getAreaid2() {
        return areaid2;
    }

    public void setAreaid2(String areaid2) {
        this.areaid2 = areaid2;
    }

    public String getAreaid3() {
        return areaid3;
    }

    public void setAreaid3(String areaid3) {
        this.areaid3 = areaid3;
    }

    public String getOtherlink() {
        return otherlink;
    }

    public void setOtherlink(String otherlink) {
        this.otherlink = otherlink;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getShoptype() {
        return shoptype;
    }

    public void setShoptype(String shoptype) {
        this.shoptype = shoptype;
    }

    public String getNoticetype() {
        return noticetype;
    }

    public void setNoticetype(String noticetype) {
        this.noticetype = noticetype;
    }

    public String getMachine_code() {
        return machine_code;
    }

    public void setMachine_code(String machine_code) {
        this.machine_code = machine_code;
    }

    public String getmKey() {
        return mKey;
    }

    public void setmKey(String mKey) {
        this.mKey = mKey;
    }

    public String getPradiusa() {
        return pradiusa;
    }

    public void setPradiusa(String pradiusa) {
        this.pradiusa = pradiusa;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getSellcount() {
        return sellcount;
    }

    public void setSellcount(String sellcount) {
        this.sellcount = sellcount;
    }

    public String getGoodattrdefault() {
        return goodattrdefault;
    }

    public void setGoodattrdefault(String goodattrdefault) {
        this.goodattrdefault = goodattrdefault;
    }

    public String getRuzhutype() {
        return ruzhutype;
    }

    public void setRuzhutype(String ruzhutype) {
        this.ruzhutype = ruzhutype;
    }

    public String getQiyeimg() {
        return qiyeimg;
    }

    public void setQiyeimg(String qiyeimg) {
        this.qiyeimg = qiyeimg;
    }

    public String getZmimg() {
        return zmimg;
    }

    public void setZmimg(String zmimg) {
        this.zmimg = zmimg;
    }

    public String getFmimg() {
        return fmimg;
    }

    public void setFmimg(String fmimg) {
        this.fmimg = fmimg;
    }

    public String getFoodtongimg() {
        return foodtongimg;
    }

    public void setFoodtongimg(String foodtongimg) {
        this.foodtongimg = foodtongimg;
    }

    public String getJkzimg() {
        return jkzimg;
    }

    public void setJkzimg(String jkzimg) {
        this.jkzimg = jkzimg;
    }

    public String getSqimg() {
        return sqimg;
    }

    public void setSqimg(String sqimg) {
        this.sqimg = sqimg;
    }

    public String getQtshuoming() {
        return qtshuoming;
    }

    public void setQtshuoming(String qtshuoming) {
        this.qtshuoming = qtshuoming;
    }

    public String getWxhui_ewmurl() {
        return wxhui_ewmurl;
    }

    public void setWxhui_ewmurl(String wxhui_ewmurl) {
        this.wxhui_ewmurl = wxhui_ewmurl;
    }

    public String getGoodlistmodule() {
        return goodlistmodule;
    }

    public void setGoodlistmodule(String goodlistmodule) {
        this.goodlistmodule = goodlistmodule;
    }

    public String getShoplicense() {
        return shoplicense;
    }

    public void setShoplicense(String shoplicense) {
        this.shoplicense = shoplicense;
    }

    @Override
    public String toString() {
        return "ShopInfoBean{" +
                "id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", shortname='" + shortname + '\'' +
                ", shopname='" + shopname + '\'' +
                ", shoplogo='" + shoplogo + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", point='" + point + '\'' +
                ", cx_info='" + cx_info + '\'' +
                ", intr_info='" + intr_info + '\'' +
                ", notice_info='" + notice_info + '\'' +
                ", starttime='" + starttime + '\'' +
                ", is_open='" + is_open + '\'' +
                ", is_onlinepay='" + is_onlinepay + '\'' +
                ", is_daopay='" + is_daopay + '\'' +
                ", is_pass='" + is_pass + '\'' +
                ", is_recom='" + is_recom + '\'' +
                ", maphone='" + maphone + '\'' +
                ", addtime='" + addtime + '\'' +
                ", pointcount='" + pointcount + '\'' +
                ", psservicepoint='" + psservicepoint + '\'' +
                ", psservicepointcount='" + psservicepointcount + '\'' +
                ", yjin='" + yjin + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", shopbacklogo='" + shopbacklogo + '\'' +
                ", sort='" + sort + '\'' +
                ", email='" + email + '\'' +
                ", areaid1='" + areaid1 + '\'' +
                ", areaid2='" + areaid2 + '\'' +
                ", areaid3='" + areaid3 + '\'' +
                ", otherlink='" + otherlink + '\'' +
                ", endtime='" + endtime + '\'' +
                ", IMEI='" + IMEI + '\'' +
                ", shoptype='" + shoptype + '\'' +
                ", noticetype='" + noticetype + '\'' +
                ", machine_code='" + machine_code + '\'' +
                ", mKey='" + mKey + '\'' +
                ", pradiusa='" + pradiusa + '\'' +
                ", admin_id='" + admin_id + '\'' +
                ", sellcount='" + sellcount + '\'' +
                ", goodattrdefault='" + goodattrdefault + '\'' +
                ", ruzhutype='" + ruzhutype + '\'' +
                ", qiyeimg='" + qiyeimg + '\'' +
                ", zmimg='" + zmimg + '\'' +
                ", fmimg='" + fmimg + '\'' +
                ", foodtongimg='" + foodtongimg + '\'' +
                ", jkzimg='" + jkzimg + '\'' +
                ", sqimg='" + sqimg + '\'' +
                ", qtshuoming='" + qtshuoming + '\'' +
                ", wxhui_ewmurl='" + wxhui_ewmurl + '\'' +
                ", goodlistmodule='" + goodlistmodule + '\'' +
                ", shoplicense='" + shoplicense + '\'' +
                ", virtualsellcounts='" + virtualsellcounts + '\'' +
                '}';
    }

}
