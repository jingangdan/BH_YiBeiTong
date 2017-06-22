package com.bh.yibeitong.bean.village;

/**
 * Created by jingang on 2016/12/24.
 * 添加/取消关注返回
 */

public class GuanZhuReturn {

    /**
     * error : false
     * msg : {"id":"4","catename":"锦绣蓝山","parent_id":"2","cate_order":"1","img":"/upload/goods/20161207163406936.jpg","guanzhu":"3"}
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
         * id : 4
         * catename : 锦绣蓝山
         * parent_id : 2
         * cate_order : 1
         * img : /upload/goods/20161207163406936.jpg
         * guanzhu : 3
         */

        private String id;
        private String catename;
        private String parent_id;
        private String cate_order;
        private String img;
        private String guanzhu;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCatename() {
            return catename;
        }

        public void setCatename(String catename) {
            this.catename = catename;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getCate_order() {
            return cate_order;
        }

        public void setCate_order(String cate_order) {
            this.cate_order = cate_order;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getGuanzhu() {
            return guanzhu;
        }

        public void setGuanzhu(String guanzhu) {
            this.guanzhu = guanzhu;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "id='" + id + '\'' +
                    ", catename='" + catename + '\'' +
                    ", parent_id='" + parent_id + '\'' +
                    ", cate_order='" + cate_order + '\'' +
                    ", img='" + img + '\'' +
                    ", guanzhu='" + guanzhu + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GuanZhuReturn{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
