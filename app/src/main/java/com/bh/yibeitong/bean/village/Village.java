package com.bh.yibeitong.bean.village;

import java.util.List;

/**
 * Created by jingang on 2016/12/13.
 * 小区信息
 */

public class Village {

    /**
     * error : false
     * msg : {"list":[{"id":"3","catename":"花半里","parent_id":"2","cate_order":"0","img":"/upload/goods/20161207163356513.jpg","guanzhu":"3"}],"slist":[{"id":"4","catename":"锦绣蓝山","parent_id":"2","cate_order":"1","img":"/upload/goods/20161207163406936.jpg","guanzhu":"4","count":4}]}
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
        private List<ListBean> list;
        private List<SlistBean> slist;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public List<SlistBean> getSlist() {
            return slist;
        }

        public void setSlist(List<SlistBean> slist) {
            this.slist = slist;
        }

        public static class ListBean {
            /**
             * id : 3
             * catename : 花半里
             * parent_id : 2
             * cate_order : 0
             * img : /upload/goods/20161207163356513.jpg
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
                return "ListBean{" +
                        "id='" + id + '\'' +
                        ", catename='" + catename + '\'' +
                        ", parent_id='" + parent_id + '\'' +
                        ", cate_order='" + cate_order + '\'' +
                        ", img='" + img + '\'' +
                        ", guanzhu='" + guanzhu + '\'' +
                        '}';
            }
        }

        public static class SlistBean {
            /**
             * id : 4
             * catename : 锦绣蓝山
             * parent_id : 2
             * cate_order : 1
             * img : /upload/goods/20161207163406936.jpg
             * guanzhu : 4
             * count : 4
             */

            private String id;
            private String catename;
            private String parent_id;
            private String cate_order;
            private String img;
            private String guanzhu;
            private int count;

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

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            @Override
            public String toString() {
                return "SlistBean{" +
                        "id='" + id + '\'' +
                        ", catename='" + catename + '\'' +
                        ", parent_id='" + parent_id + '\'' +
                        ", cate_order='" + cate_order + '\'' +
                        ", img='" + img + '\'' +
                        ", guanzhu='" + guanzhu + '\'' +
                        ", count=" + count +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "list=" + list +
                    ", slist=" + slist +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Village{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
