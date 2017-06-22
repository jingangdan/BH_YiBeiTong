package com.bh.yibeitong.bean;

import java.util.List;

/**
 * Created by jingang on 2016/12/7.
 * 礼品列表
 */

public class Gift {

    /**
     * error : false
     * msg : [{"id":"13","score":"10000","title":"10000积分兑换一斤水果","stock":"100","img":"https://www.ybt9.com/upload/goods/20161012145617881.png"},{"id":"14","score":"10000","title":"白酒一瓶","stock":"100","img":"https://www.ybt9.com/upload/goods/20161012145617881.png"}]
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
         * id : 13
         * score : 10000
         * title : 10000积分兑换一斤水果
         * stock : 100
         * img : https://www.ybt9.com/upload/goods/20161012145617881.png
         */

        private String id;
        private String score;
        private String title;
        private String stock;
        private String img;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
            return "MsgBean{" +
                    "id='" + id + '\'' +
                    ", score='" + score + '\'' +
                    ", title='" + title + '\'' +
                    ", stock='" + stock + '\'' +
                    ", img='" + img + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Gift{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
