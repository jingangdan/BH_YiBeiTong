package com.bh.yibeitong.bean;

import java.util.List;

/**
 * Created by jingang on 2016/11/21.
 * 轮播图
 */

public class WxADV {


    /**
     * error : false
     * msg : [{"id":"8","title":"banner1","advtype":"weixinlb","img":"/upload/goods/20161026110042410.jpg","linkurl":"/index.php?ctrl=wxsite&action=shoplist&typelx=mk&typeid=272","module":"m7"},{"id":"16","title":"banner2","advtype":"weixinlb","img":"/upload/goods/20161026110058996.jpg","linkurl":"/index.php?ctrl=wxsite&action=shopshow&typelx=mk&id=8","module":"m7"},{"id":"17","title":"banner3","advtype":"weixinlb","img":"/upload/goods/20161026110016390.jpg","linkurl":"/index.php?ctrl=wxsite&action=luntan&ltmk=2","module":"m7"}]
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
         * id : 8
         * title : banner1
         * advtype : weixinlb
         * img : /upload/goods/20161026110042410.jpg
         * linkurl : /index.php?ctrl=wxsite&action=shoplist&typelx=mk&typeid=272
         * module : m7
         */

        private String id;
        private String title;
        private String advtype;
        private String img;
        private String linkurl;
        private String module;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAdvtype() {
            return advtype;
        }

        public void setAdvtype(String advtype) {
            this.advtype = advtype;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getLinkurl() {
            return linkurl;
        }

        public void setLinkurl(String linkurl) {
            this.linkurl = linkurl;
        }

        public String getModule() {
            return module;
        }

        public void setModule(String module) {
            this.module = module;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", advtype='" + advtype + '\'' +
                    ", img='" + img + '\'' +
                    ", linkurl='" + linkurl + '\'' +
                    ", module='" + module + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "WxADV{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
