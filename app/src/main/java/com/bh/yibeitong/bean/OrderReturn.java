package com.bh.yibeitong.bean;

/**
 * Created by jingang on 2016/11/27.
 * 生成订单返回
 */

public class OrderReturn {

    /**
     * error : false
     * msg : {"id":"22686","dno":"14802080604596","shopuid":"28","shopid":"8","shopname":"易贝通曹王庄店","shopphone":"05398606806","shopaddress":"临西八路","buyeruid":"184","buyername":"金刚","buyeraddress":"颐高上海街一期A座2006","buyerphone":"17865069350","status":"0","is_acceptorder":"0","paytype":"1","paystatus":"0","trade_no":"","content":"","ordertype":"4","daycode":"3","area1":null,"area2":null,"area3":null,"cxids":"","cxcost":"0.00","yhjcost":"0","yhjids":"","ipaddress":"112.251.122.43湖南省","scoredown":"0","is_ping":"0","isbefore":"0","marketcost":"0.00","marketps":"0.00","shopcost":"81.20","shopps":"0.00","pstype":"1","bagcost":"0.00","addtime":"1480208060","posttime":"1480212000","passtime":"1480208060","sendtime":"0","suretime":"0","allcost":"81.20","buycode":"6905ff","othertext":"","is_print":"0","wxstatus":"0","shoptype":"1","is_make":"0","is_reback":"0","areaids":"","psuid":null,"psusername":null,"psemail":null,"admin_id":"0","is_goshop":"0","paytype_name":null,"taxcost":"0.00","postdate":"10:00-10:30","pttype":"0","ptkg":"","ptkm":"","allkgcost":"0.00","allkmcost":"0.00","farecost":null}
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
         * id : 22686
         * dno : 14802080604596
         * shopuid : 28
         * shopid : 8
         * shopname : 易贝通曹王庄店
         * shopphone : 05398606806
         * shopaddress : 临西八路
         * buyeruid : 184
         * buyername : 金刚
         * buyeraddress : 颐高上海街一期A座2006
         * buyerphone : 17865069350
         * status : 0
         * is_acceptorder : 0
         * paytype : 1
         * paystatus : 0
         * trade_no :
         * content :
         * ordertype : 4
         * daycode : 3
         * area1 : null
         * area2 : null
         * area3 : null
         * cxids :
         * cxcost : 0.00
         * yhjcost : 0
         * yhjids :
         * ipaddress : 112.251.122.43湖南省
         * scoredown : 0
         * is_ping : 0
         * isbefore : 0
         * marketcost : 0.00
         * marketps : 0.00
         * shopcost : 81.20
         * shopps : 0.00
         * pstype : 1
         * bagcost : 0.00
         * addtime : 1480208060
         * posttime : 1480212000
         * passtime : 1480208060
         * sendtime : 0
         * suretime : 0
         * allcost : 81.20
         * buycode : 6905ff
         * othertext :
         * is_print : 0
         * wxstatus : 0
         * shoptype : 1
         * is_make : 0
         * is_reback : 0
         * areaids :
         * psuid : null
         * psusername : null
         * psemail : null
         * admin_id : 0
         * is_goshop : 0
         * paytype_name : null
         * taxcost : 0.00
         * postdate : 10:00-10:30
         * pttype : 0
         * ptkg :
         * ptkm :
         * allkgcost : 0.00
         * allkmcost : 0.00
         * farecost : null
         */

        private String id;
        private String dno;
        private String shopuid;
        private String shopid;
        private String shopname;
        private String shopphone;
        private String shopaddress;
        private String buyeruid;
        private String buyername;
        private String buyeraddress;
        private String buyerphone;
        private String status;
        private String is_acceptorder;
        private String paytype;
        private String paystatus;
        private String trade_no;
        private String content;
        private String ordertype;
        private String daycode;
        private Object area1;
        private Object area2;
        private Object area3;
        private String cxids;
        private String cxcost;
        private String yhjcost;
        private String yhjids;
        private String ipaddress;
        private String scoredown;
        private String is_ping;
        private String isbefore;
        private String marketcost;
        private String marketps;
        private String shopcost;
        private String shopps;
        private String pstype;
        private String bagcost;
        private String addtime;
        private String posttime;
        private String passtime;
        private String sendtime;
        private String suretime;
        private String allcost;
        private String buycode;
        private String othertext;
        private String is_print;
        private String wxstatus;
        private String shoptype;
        private String is_make;
        private String is_reback;
        private String areaids;
        private Object psuid;
        private Object psusername;
        private Object psemail;
        private String admin_id;
        private String is_goshop;
        private Object paytype_name;
        private String taxcost;
        private String postdate;
        private String pttype;
        private String ptkg;
        private String ptkm;
        private String allkgcost;
        private String allkmcost;
        private Object farecost;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDno() {
            return dno;
        }

        public void setDno(String dno) {
            this.dno = dno;
        }

        public String getShopuid() {
            return shopuid;
        }

        public void setShopuid(String shopuid) {
            this.shopuid = shopuid;
        }

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public String getShopname() {
            return shopname;
        }

        public void setShopname(String shopname) {
            this.shopname = shopname;
        }

        public String getShopphone() {
            return shopphone;
        }

        public void setShopphone(String shopphone) {
            this.shopphone = shopphone;
        }

        public String getShopaddress() {
            return shopaddress;
        }

        public void setShopaddress(String shopaddress) {
            this.shopaddress = shopaddress;
        }

        public String getBuyeruid() {
            return buyeruid;
        }

        public void setBuyeruid(String buyeruid) {
            this.buyeruid = buyeruid;
        }

        public String getBuyername() {
            return buyername;
        }

        public void setBuyername(String buyername) {
            this.buyername = buyername;
        }

        public String getBuyeraddress() {
            return buyeraddress;
        }

        public void setBuyeraddress(String buyeraddress) {
            this.buyeraddress = buyeraddress;
        }

        public String getBuyerphone() {
            return buyerphone;
        }

        public void setBuyerphone(String buyerphone) {
            this.buyerphone = buyerphone;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getIs_acceptorder() {
            return is_acceptorder;
        }

        public void setIs_acceptorder(String is_acceptorder) {
            this.is_acceptorder = is_acceptorder;
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

        public String getTrade_no() {
            return trade_no;
        }

        public void setTrade_no(String trade_no) {
            this.trade_no = trade_no;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getOrdertype() {
            return ordertype;
        }

        public void setOrdertype(String ordertype) {
            this.ordertype = ordertype;
        }

        public String getDaycode() {
            return daycode;
        }

        public void setDaycode(String daycode) {
            this.daycode = daycode;
        }

        public Object getArea1() {
            return area1;
        }

        public void setArea1(Object area1) {
            this.area1 = area1;
        }

        public Object getArea2() {
            return area2;
        }

        public void setArea2(Object area2) {
            this.area2 = area2;
        }

        public Object getArea3() {
            return area3;
        }

        public void setArea3(Object area3) {
            this.area3 = area3;
        }

        public String getCxids() {
            return cxids;
        }

        public void setCxids(String cxids) {
            this.cxids = cxids;
        }

        public String getCxcost() {
            return cxcost;
        }

        public void setCxcost(String cxcost) {
            this.cxcost = cxcost;
        }

        public String getYhjcost() {
            return yhjcost;
        }

        public void setYhjcost(String yhjcost) {
            this.yhjcost = yhjcost;
        }

        public String getYhjids() {
            return yhjids;
        }

        public void setYhjids(String yhjids) {
            this.yhjids = yhjids;
        }

        public String getIpaddress() {
            return ipaddress;
        }

        public void setIpaddress(String ipaddress) {
            this.ipaddress = ipaddress;
        }

        public String getScoredown() {
            return scoredown;
        }

        public void setScoredown(String scoredown) {
            this.scoredown = scoredown;
        }

        public String getIs_ping() {
            return is_ping;
        }

        public void setIs_ping(String is_ping) {
            this.is_ping = is_ping;
        }

        public String getIsbefore() {
            return isbefore;
        }

        public void setIsbefore(String isbefore) {
            this.isbefore = isbefore;
        }

        public String getMarketcost() {
            return marketcost;
        }

        public void setMarketcost(String marketcost) {
            this.marketcost = marketcost;
        }

        public String getMarketps() {
            return marketps;
        }

        public void setMarketps(String marketps) {
            this.marketps = marketps;
        }

        public String getShopcost() {
            return shopcost;
        }

        public void setShopcost(String shopcost) {
            this.shopcost = shopcost;
        }

        public String getShopps() {
            return shopps;
        }

        public void setShopps(String shopps) {
            this.shopps = shopps;
        }

        public String getPstype() {
            return pstype;
        }

        public void setPstype(String pstype) {
            this.pstype = pstype;
        }

        public String getBagcost() {
            return bagcost;
        }

        public void setBagcost(String bagcost) {
            this.bagcost = bagcost;
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

        public String getPasstime() {
            return passtime;
        }

        public void setPasstime(String passtime) {
            this.passtime = passtime;
        }

        public String getSendtime() {
            return sendtime;
        }

        public void setSendtime(String sendtime) {
            this.sendtime = sendtime;
        }

        public String getSuretime() {
            return suretime;
        }

        public void setSuretime(String suretime) {
            this.suretime = suretime;
        }

        public String getAllcost() {
            return allcost;
        }

        public void setAllcost(String allcost) {
            this.allcost = allcost;
        }

        public String getBuycode() {
            return buycode;
        }

        public void setBuycode(String buycode) {
            this.buycode = buycode;
        }

        public String getOthertext() {
            return othertext;
        }

        public void setOthertext(String othertext) {
            this.othertext = othertext;
        }

        public String getIs_print() {
            return is_print;
        }

        public void setIs_print(String is_print) {
            this.is_print = is_print;
        }

        public String getWxstatus() {
            return wxstatus;
        }

        public void setWxstatus(String wxstatus) {
            this.wxstatus = wxstatus;
        }

        public String getShoptype() {
            return shoptype;
        }

        public void setShoptype(String shoptype) {
            this.shoptype = shoptype;
        }

        public String getIs_make() {
            return is_make;
        }

        public void setIs_make(String is_make) {
            this.is_make = is_make;
        }

        public String getIs_reback() {
            return is_reback;
        }

        public void setIs_reback(String is_reback) {
            this.is_reback = is_reback;
        }

        public String getAreaids() {
            return areaids;
        }

        public void setAreaids(String areaids) {
            this.areaids = areaids;
        }

        public Object getPsuid() {
            return psuid;
        }

        public void setPsuid(Object psuid) {
            this.psuid = psuid;
        }

        public Object getPsusername() {
            return psusername;
        }

        public void setPsusername(Object psusername) {
            this.psusername = psusername;
        }

        public Object getPsemail() {
            return psemail;
        }

        public void setPsemail(Object psemail) {
            this.psemail = psemail;
        }

        public String getAdmin_id() {
            return admin_id;
        }

        public void setAdmin_id(String admin_id) {
            this.admin_id = admin_id;
        }

        public String getIs_goshop() {
            return is_goshop;
        }

        public void setIs_goshop(String is_goshop) {
            this.is_goshop = is_goshop;
        }

        public Object getPaytype_name() {
            return paytype_name;
        }

        public void setPaytype_name(Object paytype_name) {
            this.paytype_name = paytype_name;
        }

        public String getTaxcost() {
            return taxcost;
        }

        public void setTaxcost(String taxcost) {
            this.taxcost = taxcost;
        }

        public String getPostdate() {
            return postdate;
        }

        public void setPostdate(String postdate) {
            this.postdate = postdate;
        }

        public String getPttype() {
            return pttype;
        }

        public void setPttype(String pttype) {
            this.pttype = pttype;
        }

        public String getPtkg() {
            return ptkg;
        }

        public void setPtkg(String ptkg) {
            this.ptkg = ptkg;
        }

        public String getPtkm() {
            return ptkm;
        }

        public void setPtkm(String ptkm) {
            this.ptkm = ptkm;
        }

        public String getAllkgcost() {
            return allkgcost;
        }

        public void setAllkgcost(String allkgcost) {
            this.allkgcost = allkgcost;
        }

        public String getAllkmcost() {
            return allkmcost;
        }

        public void setAllkmcost(String allkmcost) {
            this.allkmcost = allkmcost;
        }

        public Object getFarecost() {
            return farecost;
        }

        public void setFarecost(Object farecost) {
            this.farecost = farecost;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "id='" + id + '\'' +
                    ", dno='" + dno + '\'' +
                    ", shopuid='" + shopuid + '\'' +
                    ", shopid='" + shopid + '\'' +
                    ", shopname='" + shopname + '\'' +
                    ", shopphone='" + shopphone + '\'' +
                    ", shopaddress='" + shopaddress + '\'' +
                    ", buyeruid='" + buyeruid + '\'' +
                    ", buyername='" + buyername + '\'' +
                    ", buyeraddress='" + buyeraddress + '\'' +
                    ", buyerphone='" + buyerphone + '\'' +
                    ", status='" + status + '\'' +
                    ", is_acceptorder='" + is_acceptorder + '\'' +
                    ", paytype='" + paytype + '\'' +
                    ", paystatus='" + paystatus + '\'' +
                    ", trade_no='" + trade_no + '\'' +
                    ", content='" + content + '\'' +
                    ", ordertype='" + ordertype + '\'' +
                    ", daycode='" + daycode + '\'' +
                    ", area1=" + area1 +
                    ", area2=" + area2 +
                    ", area3=" + area3 +
                    ", cxids='" + cxids + '\'' +
                    ", cxcost='" + cxcost + '\'' +
                    ", yhjcost='" + yhjcost + '\'' +
                    ", yhjids='" + yhjids + '\'' +
                    ", ipaddress='" + ipaddress + '\'' +
                    ", scoredown='" + scoredown + '\'' +
                    ", is_ping='" + is_ping + '\'' +
                    ", isbefore='" + isbefore + '\'' +
                    ", marketcost='" + marketcost + '\'' +
                    ", marketps='" + marketps + '\'' +
                    ", shopcost='" + shopcost + '\'' +
                    ", shopps='" + shopps + '\'' +
                    ", pstype='" + pstype + '\'' +
                    ", bagcost='" + bagcost + '\'' +
                    ", addtime='" + addtime + '\'' +
                    ", posttime='" + posttime + '\'' +
                    ", passtime='" + passtime + '\'' +
                    ", sendtime='" + sendtime + '\'' +
                    ", suretime='" + suretime + '\'' +
                    ", allcost='" + allcost + '\'' +
                    ", buycode='" + buycode + '\'' +
                    ", othertext='" + othertext + '\'' +
                    ", is_print='" + is_print + '\'' +
                    ", wxstatus='" + wxstatus + '\'' +
                    ", shoptype='" + shoptype + '\'' +
                    ", is_make='" + is_make + '\'' +
                    ", is_reback='" + is_reback + '\'' +
                    ", areaids='" + areaids + '\'' +
                    ", psuid=" + psuid +
                    ", psusername=" + psusername +
                    ", psemail=" + psemail +
                    ", admin_id='" + admin_id + '\'' +
                    ", is_goshop='" + is_goshop + '\'' +
                    ", paytype_name=" + paytype_name +
                    ", taxcost='" + taxcost + '\'' +
                    ", postdate='" + postdate + '\'' +
                    ", pttype='" + pttype + '\'' +
                    ", ptkg='" + ptkg + '\'' +
                    ", ptkm='" + ptkm + '\'' +
                    ", allkgcost='" + allkgcost + '\'' +
                    ", allkmcost='" + allkmcost + '\'' +
                    ", farecost=" + farecost +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "OrderReturn{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
