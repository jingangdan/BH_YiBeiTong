package com.bh.yibeitong.bean.percen;

/**
 * Created by jingang on 2017/7/7.
 * 兑换 礼品详情
 */

public class GiftInfo {

    /**
     * error : false
     * msg : {"giftinfo":{"id":"14","market_cost":"1000.00","score":"10000","title":"白酒一瓶","content":"测试数据，请勿兑换","typeid":"6","sell_count":"12","stock":"98","img":"/upload/goods/20170706101952631.jpg"}}
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
         * giftinfo : {"id":"14","market_cost":"1000.00","score":"10000","title":"白酒一瓶","content":"测试数据，请勿兑换","typeid":"6","sell_count":"12","stock":"98","img":"/upload/goods/20170706101952631.jpg"}
         */

        private GiftinfoBean giftinfo;

        public GiftinfoBean getGiftinfo() {
            return giftinfo;
        }

        public void setGiftinfo(GiftinfoBean giftinfo) {
            this.giftinfo = giftinfo;
        }

        public static class GiftinfoBean {
            /**
             * id : 14
             * market_cost : 1000.00
             * score : 10000
             * title : 白酒一瓶
             * content : 测试数据，请勿兑换
             * typeid : 6
             * sell_count : 12
             * stock : 98
             * img : /upload/goods/20170706101952631.jpg
             */

            private String id;
            private String market_cost;
            private String score;
            private String title;
            private String content;
            private String typeid;
            private String sell_count;
            private String stock;
            private String img;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMarket_cost() {
                return market_cost;
            }

            public void setMarket_cost(String market_cost) {
                this.market_cost = market_cost;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getTypeid() {
                return typeid;
            }

            public void setTypeid(String typeid) {
                this.typeid = typeid;
            }

            public String getSell_count() {
                return sell_count;
            }

            public void setSell_count(String sell_count) {
                this.sell_count = sell_count;
            }

            public String getStock() {
                return stock;
            }

            public void setStock(String stock) {
                this.stock = stock;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            @Override
            public String toString() {
                return "GiftinfoBean{" +
                        "id='" + id + '\'' +
                        ", market_cost='" + market_cost + '\'' +
                        ", score='" + score + '\'' +
                        ", title='" + title + '\'' +
                        ", content='" + content + '\'' +
                        ", typeid='" + typeid + '\'' +
                        ", sell_count='" + sell_count + '\'' +
                        ", stock='" + stock + '\'' +
                        ", img='" + img + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "giftinfo=" + giftinfo +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GiftInfo{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
