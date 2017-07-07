package com.bh.yibeitong.bean.percen;

import java.util.List;

/**
 * Created by jingang on 2017/7/7.
 * 礼品兑换记录
 */

public class ExGiftLog {

    /**
     * error : false
     * msg : [{"id":"137","giftid":"14","uid":"13","score":"10000","addtime":"2017-07-05","status":"2","address":"颐高上海街","telphone":"18669974364","contactman":"刘波","count":"1","content":"测试","title":"白酒一瓶","img":"https://www.ybt9.com/upload/goods/20170706101952631.jpg"}]
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
         * id : 137
         * giftid : 14
         * uid : 13
         * score : 10000
         * addtime : 2017-07-05
         * status : 2
         * address : 颐高上海街
         * telphone : 18669974364
         * contactman : 刘波
         * count : 1
         * content : 测试
         * title : 白酒一瓶
         * img : https://www.ybt9.com/upload/goods/20170706101952631.jpg
         */

        private String id;
        private String giftid;
        private String uid;
        private String score;
        private String addtime;
        private String status;
        private String address;
        private String telphone;
        private String contactman;
        private String count;
        private String content;
        private String title;
        private String img;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGiftid() {
            return giftid;
        }

        public void setGiftid(String giftid) {
            this.giftid = giftid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }

        public String getContactman() {
            return contactman;
        }

        public void setContactman(String contactman) {
            this.contactman = contactman;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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
                    ", giftid='" + giftid + '\'' +
                    ", uid='" + uid + '\'' +
                    ", score='" + score + '\'' +
                    ", addtime='" + addtime + '\'' +
                    ", status='" + status + '\'' +
                    ", address='" + address + '\'' +
                    ", telphone='" + telphone + '\'' +
                    ", contactman='" + contactman + '\'' +
                    ", count='" + count + '\'' +
                    ", content='" + content + '\'' +
                    ", title='" + title + '\'' +
                    ", img='" + img + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ExGiftLog{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
