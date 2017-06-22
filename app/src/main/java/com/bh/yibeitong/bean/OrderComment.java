package com.bh.yibeitong.bean;

import java.util.List;

/**
 * Created by jingang on 2016/12/19.
 * 订单评价列表
 */

public class OrderComment {

    /**
     * error : false
     * msg : [{"id":"14486","order_id":"22889","goodsid":"3562","goodsname":"柠檬","goodscost":"0.01","goodscount":"1","status":"0","shopid":"17","is_send":"0","product_id":"0","have_det":"0","pingcontent":"","pingtime":"","point":"0","img":"https://www.ybt9.com./upload/goodsimg/00161/ontop/2.jpg"}]
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
         * id : 14486
         * order_id : 22889
         * goodsid : 3562
         * goodsname : 柠檬
         * goodscost : 0.01
         * goodscount : 1
         * status : 0
         * shopid : 17
         * is_send : 0
         * product_id : 0
         * have_det : 0
         * pingcontent :
         * pingtime :
         * point : 0
         * img : https://www.ybt9.com./upload/goodsimg/00161/ontop/2.jpg
         */

        private String id;
        private String order_id;
        private String goodsid;
        private String goodsname;
        private String goodscost;
        private String goodscount;
        private String status;
        private String shopid;
        private String is_send;
        private String product_id;
        private String have_det;
        private String pingcontent;
        private String pingtime;
        private String point;
        private String img;

        private String xing = "0";//商品星级评定
        private String content ="";//商品评价内容

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getXing() {
            return xing;
        }

        public void setXing(String xing) {
            this.xing = xing;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getGoodsid() {
            return goodsid;
        }

        public void setGoodsid(String goodsid) {
            this.goodsid = goodsid;
        }

        public String getGoodsname() {
            return goodsname;
        }

        public void setGoodsname(String goodsname) {
            this.goodsname = goodsname;
        }

        public String getGoodscost() {
            return goodscost;
        }

        public void setGoodscost(String goodscost) {
            this.goodscost = goodscost;
        }

        public String getGoodscount() {
            return goodscount;
        }

        public void setGoodscount(String goodscount) {
            this.goodscount = goodscount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public String getIs_send() {
            return is_send;
        }

        public void setIs_send(String is_send) {
            this.is_send = is_send;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getHave_det() {
            return have_det;
        }

        public void setHave_det(String have_det) {
            this.have_det = have_det;
        }

        public String getPingcontent() {
            return pingcontent;
        }

        public void setPingcontent(String pingcontent) {
            this.pingcontent = pingcontent;
        }

        public String getPingtime() {
            return pingtime;
        }

        public void setPingtime(String pingtime) {
            this.pingtime = pingtime;
        }

        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "id='" + id + '\'' +
                    ", order_id='" + order_id + '\'' +
                    ", goodsid='" + goodsid + '\'' +
                    ", goodsname='" + goodsname + '\'' +
                    ", goodscost='" + goodscost + '\'' +
                    ", goodscount='" + goodscount + '\'' +
                    ", status='" + status + '\'' +
                    ", shopid='" + shopid + '\'' +
                    ", is_send='" + is_send + '\'' +
                    ", product_id='" + product_id + '\'' +
                    ", have_det='" + have_det + '\'' +
                    ", pingcontent='" + pingcontent + '\'' +
                    ", pingtime='" + pingtime + '\'' +
                    ", point='" + point + '\'' +
                    ", img='" + img + '\'' +
                    ", xing='" + xing + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "OrderComment{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
