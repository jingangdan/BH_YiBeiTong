package com.bh.yibeitong.bean.seller;

import java.util.List;

/**
 * Created by jingang on 2017/9/26.
 * 超市商家获取一级商品分类
 */

public class MarketFgoodstype {

    /**
     * error : false
     * msg : [{"id":"2206","name":"test","keywd":null,"desc":null,"parent_id":"0","shopid":"23","orderid":"1011","img":null,"asyncid":"0","display":"0","shuliang":0},{"id":"1921","name":"水果","keywd":null,"desc":null,"parent_id":"0","shopid":"23","orderid":"999","img":null,"asyncid":"692","display":"0","shuliang":41},{"id":"1923","name":"蔬菜","keywd":null,"desc":null,"parent_id":"0","shopid":"23","orderid":"999","img":null,"asyncid":"694","display":"0","shuliang":69},{"id":"1929","name":"日配商品","keywd":null,"desc":null,"parent_id":"0","shopid":"23","orderid":"999","img":null,"asyncid":"700","display":"0","shuliang":116},{"id":"1935","name":"粮油副食","keywd":null,"desc":null,"parent_id":"0","shopid":"23","orderid":"999","img":null,"asyncid":"706","display":"0","shuliang":52},{"id":"1938","name":"食杂调味","keywd":null,"desc":null,"parent_id":"0","shopid":"23","orderid":"999","img":null,"asyncid":"709","display":"0","shuliang":102},{"id":"1943","name":"酒水畅饮","keywd":null,"desc":null,"parent_id":"0","shopid":"23","orderid":"999","img":null,"asyncid":"716","display":"0","shuliang":33},{"id":"1948","name":"泉水饮料","keywd":null,"desc":null,"parent_id":"0","shopid":"23","orderid":"999","img":null,"asyncid":"721","display":"0","shuliang":119},{"id":"1953","name":"冲调保健","keywd":null,"desc":null,"parent_id":"0","shopid":"23","orderid":"999","img":null,"asyncid":"726","display":"0","shuliang":36},{"id":"1959","name":"休闲速食","keywd":null,"desc":null,"parent_id":"0","shopid":"23","orderid":"999","img":null,"asyncid":"732","display":"0","shuliang":457},{"id":"1971","name":"冷饮零食","keywd":null,"desc":null,"parent_id":"0","shopid":"23","orderid":"999","img":null,"asyncid":"744","display":"0","shuliang":39},{"id":"1973","name":"洗化用品","keywd":null,"desc":null,"parent_id":"0","shopid":"23","orderid":"999","img":null,"asyncid":"746","display":"0","shuliang":155},{"id":"1981","name":"百货用品","keywd":null,"desc":null,"parent_id":"0","shopid":"23","orderid":"999","img":null,"asyncid":"754","display":"0","shuliang":138},{"id":"1989","name":"文体玩具","keywd":null,"desc":null,"parent_id":"0","shopid":"23","orderid":"999","img":null,"asyncid":"762","display":"0","shuliang":64},{"id":"1994","name":"针织用品","keywd":null,"desc":null,"parent_id":"0","shopid":"23","orderid":"999","img":null,"asyncid":"767","display":"0","shuliang":17},{"id":"1998","name":"进口酒饮","keywd":null,"desc":null,"parent_id":"0","shopid":"23","orderid":"999","img":null,"asyncid":"771","display":"0","shuliang":8},{"id":"2001","name":"进口速食","keywd":null,"desc":null,"parent_id":"0","shopid":"23","orderid":"999","img":null,"asyncid":"774","display":"0","shuliang":4},{"id":"2003","name":"进口休闲","keywd":null,"desc":null,"parent_id":"0","shopid":"23","orderid":"999","img":null,"asyncid":"776","display":"0","shuliang":10},{"id":"2005","name":"进口奶饮","keywd":null,"desc":null,"parent_id":"0","shopid":"23","orderid":"999","img":null,"asyncid":"778","display":"0","shuliang":2},{"id":"2171","name":"水产畜产品","keywd":null,"desc":null,"parent_id":"0","shopid":"23","orderid":"999","img":null,"asyncid":"696","display":"0","shuliang":3},{"id":"2173","name":"加工食品","keywd":null,"desc":null,"parent_id":"0","shopid":"23","orderid":"999","img":null,"asyncid":"698","display":"0","shuliang":19},{"id":"2112","name":"定汽车","keywd":null,"desc":null,"parent_id":"0","shopid":"23","orderid":"998","img":null,"asyncid":"0","display":"1","shuliang":4},{"id":"2126","name":"订蛋糕","keywd":null,"desc":null,"parent_id":"0","shopid":"23","orderid":"26","img":null,"asyncid":"0","display":"1","shuliang":15},{"id":"2124","name":"选房产","keywd":null,"desc":null,"parent_id":"0","shopid":"23","orderid":"25","img":null,"asyncid":"0","display":"1","shuliang":4},{"id":"2121","name":"洗衣服务","keywd":null,"desc":null,"parent_id":"0","shopid":"23","orderid":"24","img":null,"asyncid":"0","display":"1","shuliang":4},{"id":"2120","name":"快递","keywd":null,"desc":null,"parent_id":"0","shopid":"23","orderid":"23","img":null,"asyncid":"0","display":"1","shuliang":2},{"id":"2118","name":"家政服务","keywd":null,"desc":null,"parent_id":"0","shopid":"23","orderid":"22","img":null,"asyncid":"0","display":"1","shuliang":4},{"id":"2116","name":"公益活动","keywd":null,"desc":null,"parent_id":"0","shopid":"23","orderid":"21","img":null,"asyncid":"0","display":"1","shuliang":2},{"id":"2114","name":"定鲜花","keywd":null,"desc":null,"parent_id":"0","shopid":"23","orderid":"20","img":null,"asyncid":"0","display":"1","shuliang":26}]
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
         * id : 2206
         * name : test
         * keywd : null
         * desc : null
         * parent_id : 0
         * shopid : 23
         * orderid : 1011
         * img : null
         * asyncid : 0
         * display : 0
         * shuliang : 0
         */

        private String id;
        private String name;
        private Object keywd;
        private Object desc;
        private String parent_id;
        private String shopid;
        private String orderid;
        private Object img;
        private String asyncid;
        private String display;
        private int shuliang;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getKeywd() {
            return keywd;
        }

        public void setKeywd(Object keywd) {
            this.keywd = keywd;
        }

        public Object getDesc() {
            return desc;
        }

        public void setDesc(Object desc) {
            this.desc = desc;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public Object getImg() {
            return img;
        }

        public void setImg(Object img) {
            this.img = img;
        }

        public String getAsyncid() {
            return asyncid;
        }

        public void setAsyncid(String asyncid) {
            this.asyncid = asyncid;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }

        public int getShuliang() {
            return shuliang;
        }

        public void setShuliang(int shuliang) {
            this.shuliang = shuliang;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", keywd=" + keywd +
                    ", desc=" + desc +
                    ", parent_id='" + parent_id + '\'' +
                    ", shopid='" + shopid + '\'' +
                    ", orderid='" + orderid + '\'' +
                    ", img=" + img +
                    ", asyncid='" + asyncid + '\'' +
                    ", display='" + display + '\'' +
                    ", shuliang=" + shuliang +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MarketFgoodstype{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
