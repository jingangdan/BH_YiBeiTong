package com.bh.yibeitong.bean.homepage;

import java.util.List;

/**
 * Created by jingang on 2017/7/6.
 */

public class AdvByType {

    /**
     * error : false
     * msg : [{"id":"19","title":"微信通栏2","advtype":"weixinmid2","img":"/upload/goods/20170704161503670.png","linkurl":"#","module":"m7"}]
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
         * id : 19
         * title : 微信通栏2
         * advtype : weixinmid2
         * img : /upload/goods/20170704161503670.png
         * linkurl : #
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
        return "AdvByType{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
