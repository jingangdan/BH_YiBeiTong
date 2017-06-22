package com.bh.yibeitong.bean.shopbean;

import java.util.List;

/**
 * Created by jingang on 2017/2/27.
 * 首页推荐 店铺优选
 * 店铺推荐区
 */

public class Recommed {
    /**
     * error : false
     * msg : [{"id":"12","name":"休闲零食","img":"/upload/goods/20170619113410325.png","url":null,"isshow":"1","orderid":"0","shopid":"24","param":"1690","type":"cate"},{"id":"13","name":"酒水饮品","img":"/upload/goods/20170619113442252.png","url":null,"isshow":"1","orderid":"1","shopid":"24","param":"1674","type":"cate"},{"id":"14","name":"粮油副食","img":"/upload/goods/20170619113515112.png","url":null,"isshow":"1","orderid":"2","shopid":"24","param":"1669","type":"cate"},{"id":"15","name":"洗护日用","img":"/upload/goods/20170619113550873.png","url":null,"isshow":"1","orderid":"3","shopid":"24","param":"1704","type":"cate"},{"id":"16","name":"生鲜果蔬","img":"/upload/goods/20170619113618536.png","url":null,"isshow":"1","orderid":"4","shopid":"24","param":"1660","type":"cate"},{"id":0,"img":"/upload/goods/wyjm.png","url":null,"isshow":1,"orderid":5,"shopid":24,"param":"0","type":"jiameng","name":"我要加盟"},{"id":0,"img":"/upload/goods/tsfw.png","url":null,"isshow":1,"orderid":6,"shopid":24,"param":"0","type":"tesefuwu","name":"特色服务"},{"id":0,"img":"/upload/goods/tb8.png","url":null,"isshow":1,"orderid":7,"shopid":24,"param":"0","type":"fenlei","name":"全部分类"}]
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
         * id : 12
         * name : 休闲零食
         * img : /upload/goods/20170619113410325.png
         * url : null
         * isshow : 1
         * orderid : 0
         * shopid : 24
         * param : 1690
         * type : cate
         */

        private String id;
        private String name;
        private String img;
        private Object url;
        private String isshow;
        private String orderid;
        private String shopid;
        private String param;
        private String type;

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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public Object getUrl() {
            return url;
        }

        public void setUrl(Object url) {
            this.url = url;
        }

        public String getIsshow() {
            return isshow;
        }

        public void setIsshow(String isshow) {
            this.isshow = isshow;
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

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
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
                    ", name='" + name + '\'' +
                    ", img='" + img + '\'' +
                    ", url=" + url +
                    ", isshow='" + isshow + '\'' +
                    ", orderid='" + orderid + '\'' +
                    ", shopid='" + shopid + '\'' +
                    ", param='" + param + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Recommed{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
