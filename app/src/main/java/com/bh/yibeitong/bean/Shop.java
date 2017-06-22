package com.bh.yibeitong.bean;

import java.util.List;

/**
 * Created by jingang on 2016/12/7.
 * 商店列表
 */
public class Shop {

    /**
     * error : false
     * msg : [{"shopid":"17","id":"17","shopname":"易贝通直营测试店2","sellcount":0,"point":0,"pointcount":"0","virtualsellcounts":null,"is_open":"1","starttime":"08:00","pointsellcount":"0","lat":"35.109700","lng":"118.351315","is_orderbefore":"0","limitcost":"12","shoplogo":null,"is_hot":"0","is_com":"0","is_new":"0","maketime":"0","pradius":"5","sendtype":"0","pscost":0,"pradiusvalue":"a:5:{i:0;i:0;i:1;i:10;i:2;i:20;i:3;i:30;i:4;i:40;}","arrivetime":"","address":"颐高上海街测试店","shoptype":"1","cxinfo":[],"juli":"156米","canps":1,"opentype":2,"shopimg":"https://www.ybt9.com/upload/goods/20161012145617881.png","pstime":"00:00"},{"shopid":"8","id":"8","shopname":"易贝通曹王庄店","sellcount":0,"point":8,"pointcount":"2","virtualsellcounts":null,"is_open":"1","starttime":"08:00","pointsellcount":"2","lat":"35.079189","lng":"118.303800","is_orderbefore":"1","limitcost":"9","shoplogo":null,"is_hot":"0","is_com":"0","is_new":"0","maketime":"0","pradius":"20","sendtype":"1","pscost":40,"pradiusvalue":"a:20:{i:0;i:0;i:1;i:5;i:2;i:15;i:3;i:20;i:4;i:30;i:5;i:40;i:6;i:50;i:7;i:60;i:8;i:70;i:9;i:0;i:10;i:0;i:11;i:0;i:12;i:0;i:13;i:0;i:14;i:0;i:15;i:0;i:16;i:0;i:17;i:0;i:18;i:0;i:19;i:0;}","arrivetime":"","address":"临西八路","shoptype":"1","cxinfo":[],"juli":"5.64千米","canps":1,"opentype":2,"shopimg":"https://www.ybt9.com/upload/goods/20161012145617881.png","pstime":"00:00"}]
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
         * shopid : 17
         * id : 17
         * shopname : 易贝通直营测试店2
         * sellcount : 0
         * point : 0
         * pointcount : 0
         * virtualsellcounts : null
         * is_open : 1
         * starttime : 08:00
         * pointsellcount : 0
         * lat : 35.109700
         * lng : 118.351315
         * is_orderbefore : 0
         * limitcost : 12
         * shoplogo : null
         * is_hot : 0
         * is_com : 0
         * is_new : 0
         * maketime : 0
         * pradius : 5
         * sendtype : 0
         * pscost : 0
         * pradiusvalue : a:5:{i:0;i:0;i:1;i:10;i:2;i:20;i:3;i:30;i:4;i:40;}
         * arrivetime :
         * address : 颐高上海街测试店
         * shoptype : 1
         * cxinfo : []
         * juli : 156米
         * canps : 1
         * opentype : 2
         * shopimg : https://www.ybt9.com/upload/goods/20161012145617881.png
         * pstime : 00:00
         */

        private String shopid;
        private String id;
        private String shopname;
        private int sellcount;
        private int point;
        private String pointcount;
        private Object virtualsellcounts;
        private String is_open;
        private String starttime;
        private String pointsellcount;
        private String lat;
        private String lng;
        private String is_orderbefore;
        private String limitcost;
        private Object shoplogo;
        private String is_hot;
        private String is_com;
        private String is_new;
        private String maketime;
        private String pradius;
        private String sendtype;
        private int pscost;
        private String pradiusvalue;
        private String arrivetime;
        private String address;
        private String shoptype;
        private String juli;
        private int canps;
        private int opentype;
        private String shopimg;
        private String pstime;
        private List<?> cxinfo;

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShopname() {
            return shopname;
        }

        public void setShopname(String shopname) {
            this.shopname = shopname;
        }

        public int getSellcount() {
            return sellcount;
        }

        public void setSellcount(int sellcount) {
            this.sellcount = sellcount;
        }

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public String getPointcount() {
            return pointcount;
        }

        public void setPointcount(String pointcount) {
            this.pointcount = pointcount;
        }

        public Object getVirtualsellcounts() {
            return virtualsellcounts;
        }

        public void setVirtualsellcounts(Object virtualsellcounts) {
            this.virtualsellcounts = virtualsellcounts;
        }

        public String getIs_open() {
            return is_open;
        }

        public void setIs_open(String is_open) {
            this.is_open = is_open;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public String getPointsellcount() {
            return pointsellcount;
        }

        public void setPointsellcount(String pointsellcount) {
            this.pointsellcount = pointsellcount;
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

        public String getIs_orderbefore() {
            return is_orderbefore;
        }

        public void setIs_orderbefore(String is_orderbefore) {
            this.is_orderbefore = is_orderbefore;
        }

        public String getLimitcost() {
            return limitcost;
        }

        public void setLimitcost(String limitcost) {
            this.limitcost = limitcost;
        }

        public Object getShoplogo() {
            return shoplogo;
        }

        public void setShoplogo(Object shoplogo) {
            this.shoplogo = shoplogo;
        }

        public String getIs_hot() {
            return is_hot;
        }

        public void setIs_hot(String is_hot) {
            this.is_hot = is_hot;
        }

        public String getIs_com() {
            return is_com;
        }

        public void setIs_com(String is_com) {
            this.is_com = is_com;
        }

        public String getIs_new() {
            return is_new;
        }

        public void setIs_new(String is_new) {
            this.is_new = is_new;
        }

        public String getMaketime() {
            return maketime;
        }

        public void setMaketime(String maketime) {
            this.maketime = maketime;
        }

        public String getPradius() {
            return pradius;
        }

        public void setPradius(String pradius) {
            this.pradius = pradius;
        }

        public String getSendtype() {
            return sendtype;
        }

        public void setSendtype(String sendtype) {
            this.sendtype = sendtype;
        }

        public int getPscost() {
            return pscost;
        }

        public void setPscost(int pscost) {
            this.pscost = pscost;
        }

        public String getPradiusvalue() {
            return pradiusvalue;
        }

        public void setPradiusvalue(String pradiusvalue) {
            this.pradiusvalue = pradiusvalue;
        }

        public String getArrivetime() {
            return arrivetime;
        }

        public void setArrivetime(String arrivetime) {
            this.arrivetime = arrivetime;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getShoptype() {
            return shoptype;
        }

        public void setShoptype(String shoptype) {
            this.shoptype = shoptype;
        }

        public String getJuli() {
            return juli;
        }

        public void setJuli(String juli) {
            this.juli = juli;
        }

        public int getCanps() {
            return canps;
        }

        public void setCanps(int canps) {
            this.canps = canps;
        }

        public int getOpentype() {
            return opentype;
        }

        public void setOpentype(int opentype) {
            this.opentype = opentype;
        }

        public String getShopimg() {
            return shopimg;
        }

        public void setShopimg(String shopimg) {
            this.shopimg = shopimg;
        }

        public String getPstime() {
            return pstime;
        }

        public void setPstime(String pstime) {
            this.pstime = pstime;
        }

        public List<?> getCxinfo() {
            return cxinfo;
        }

        public void setCxinfo(List<?> cxinfo) {
            this.cxinfo = cxinfo;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "shopid='" + shopid + '\'' +
                    ", id='" + id + '\'' +
                    ", shopname='" + shopname + '\'' +
                    ", sellcount=" + sellcount +
                    ", point=" + point +
                    ", pointcount='" + pointcount + '\'' +
                    ", virtualsellcounts=" + virtualsellcounts +
                    ", is_open='" + is_open + '\'' +
                    ", starttime='" + starttime + '\'' +
                    ", pointsellcount='" + pointsellcount + '\'' +
                    ", lat='" + lat + '\'' +
                    ", lng='" + lng + '\'' +
                    ", is_orderbefore='" + is_orderbefore + '\'' +
                    ", limitcost='" + limitcost + '\'' +
                    ", shoplogo=" + shoplogo +
                    ", is_hot='" + is_hot + '\'' +
                    ", is_com='" + is_com + '\'' +
                    ", is_new='" + is_new + '\'' +
                    ", maketime='" + maketime + '\'' +
                    ", pradius='" + pradius + '\'' +
                    ", sendtype='" + sendtype + '\'' +
                    ", pscost=" + pscost +
                    ", pradiusvalue='" + pradiusvalue + '\'' +
                    ", arrivetime='" + arrivetime + '\'' +
                    ", address='" + address + '\'' +
                    ", shoptype='" + shoptype + '\'' +
                    ", juli='" + juli + '\'' +
                    ", canps=" + canps +
                    ", opentype=" + opentype +
                    ", shopimg='" + shopimg + '\'' +
                    ", pstime='" + pstime + '\'' +
                    ", cxinfo=" + cxinfo +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Shop{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
