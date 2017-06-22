package com.bh.yibeitong.bean;

import java.util.List;

/**
 * Created by jingang on 2016/11/20.
 * 购物车 实体类  修改2017/02/20 添加onlynewtype属性（是否只有快递）
 */

public class ShopCart {

    /**
     * error : false
     * msg : {"list":[{"count":1,"id":4064,"img":"./upload/goodsimg/4891599338393/ontop/2.jpg","name":"加多宝 凉茶310ml","cost":"3.50","shopid":"23","store_num":"97","sellcount":"11","bagcost":"0.00","typeid":"496","newtype":"0"}],"sumcount":1,"sum":3.5,"bagcost":0,"surecost":3.5,"downcost":0,"gzdata":[],"pstype":"1","pscost":0,"canps":1,"onlynewtype":false}
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
         * list : [{"count":1,"id":4064,"img":"./upload/goodsimg/4891599338393/ontop/2.jpg","name":"加多宝 凉茶310ml","cost":"3.50","shopid":"23","store_num":"97","sellcount":"11","bagcost":"0.00","typeid":"496","newtype":"0"}]
         * sumcount : 1
         * sum : 3.5
         * bagcost : 0
         * surecost : 3.5
         * downcost : 0
         * gzdata : []
         * pstype : 1
         * pscost : 0
         * canps : 1
         * onlynewtype : false
         */

        private int sumcount;
        private double sum;
        private int bagcost;
        private double surecost;
        private int downcost;
        private String pstype;
        private int pscost;
        private int canps;
        private boolean onlynewtype;
        private List<ListBean> list;
        private List<?> gzdata;

        public int getSumcount() {
            return sumcount;
        }

        public void setSumcount(int sumcount) {
            this.sumcount = sumcount;
        }

        public double getSum() {
            return sum;
        }

        public void setSum(double sum) {
            this.sum = sum;
        }

        public int getBagcost() {
            return bagcost;
        }

        public void setBagcost(int bagcost) {
            this.bagcost = bagcost;
        }

        public double getSurecost() {
            return surecost;
        }

        public void setSurecost(double surecost) {
            this.surecost = surecost;
        }

        public int getDowncost() {
            return downcost;
        }

        public void setDowncost(int downcost) {
            this.downcost = downcost;
        }

        public String getPstype() {
            return pstype;
        }

        public void setPstype(String pstype) {
            this.pstype = pstype;
        }

        public int getPscost() {
            return pscost;
        }

        public void setPscost(int pscost) {
            this.pscost = pscost;
        }

        public int getCanps() {
            return canps;
        }

        public void setCanps(int canps) {
            this.canps = canps;
        }

        public boolean isOnlynewtype() {
            return onlynewtype;
        }

        public void setOnlynewtype(boolean onlynewtype) {
            this.onlynewtype = onlynewtype;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public List<?> getGzdata() {
            return gzdata;
        }

        public void setGzdata(List<?> gzdata) {
            this.gzdata = gzdata;
        }

        public static class ListBean {
            /**
             * count : 1
             * id : 4064
             * img : ./upload/goodsimg/4891599338393/ontop/2.jpg
             * name : 加多宝 凉茶310ml
             * cost : 3.50
             * shopid : 23
             * store_num : 97
             * sellcount : 11
             * bagcost : 0.00
             * typeid : 496
             * newtype : 0
             */

            private int count;
            private int id;
            private String img;
            private String name;
            private String cost;
            private String shopid;
            private String store_num;
            private String sellcount;
            private String bagcost;
            private String typeid;
            private String newtype;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCost() {
                return cost;
            }

            public void setCost(String cost) {
                this.cost = cost;
            }

            public String getShopid() {
                return shopid;
            }

            public void setShopid(String shopid) {
                this.shopid = shopid;
            }

            public String getStore_num() {
                return store_num;
            }

            public void setStore_num(String store_num) {
                this.store_num = store_num;
            }

            public String getSellcount() {
                return sellcount;
            }

            public void setSellcount(String sellcount) {
                this.sellcount = sellcount;
            }

            public String getBagcost() {
                return bagcost;
            }

            public void setBagcost(String bagcost) {
                this.bagcost = bagcost;
            }

            public String getTypeid() {
                return typeid;
            }

            public void setTypeid(String typeid) {
                this.typeid = typeid;
            }

            public String getNewtype() {
                return newtype;
            }

            public void setNewtype(String newtype) {
                this.newtype = newtype;
            }

            @Override
            public String toString() {
                return "ListBean{" +
                        "count=" + count +
                        ", id=" + id +
                        ", img='" + img + '\'' +
                        ", name='" + name + '\'' +
                        ", cost='" + cost + '\'' +
                        ", shopid='" + shopid + '\'' +
                        ", store_num='" + store_num + '\'' +
                        ", sellcount='" + sellcount + '\'' +
                        ", bagcost='" + bagcost + '\'' +
                        ", typeid='" + typeid + '\'' +
                        ", newtype='" + newtype + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "sumcount=" + sumcount +
                    ", sum=" + sum +
                    ", bagcost=" + bagcost +
                    ", surecost=" + surecost +
                    ", downcost=" + downcost +
                    ", pstype='" + pstype + '\'' +
                    ", pscost=" + pscost +
                    ", canps=" + canps +
                    ", onlynewtype=" + onlynewtype +
                    ", list=" + list +
                    ", gzdata=" + gzdata +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ShopCart{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
