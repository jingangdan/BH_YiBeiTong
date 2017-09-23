package com.bh.yibeitong.bean.homepage;

import java.util.List;

/**
 * Created by jingang on 2017/9/22.
 * 店铺分类
 */

public class CateList {

    /**
     * error : false
     * msg : [{"id":"2072","name":"百货用品","parent_id":"0","pronum":2},{"id":"2094","name":"进口休闲","parent_id":"0","pronum":2},{"id":"2150","name":"加工食品","parent_id":"0","pronum":1},{"id":"2096","name":"进口奶饮","parent_id":"0","pronum":1},{"id":"2039","name":"泉水饮料","parent_id":"0","pronum":30},{"id":"2029","name":"食杂调味","parent_id":"0","pronum":4},{"id":"2020","name":"日配商品","parent_id":"0","pronum":8},{"id":"2044","name":"冲调保健","parent_id":"0","pronum":2},{"id":"2064","name":"洗化用品","parent_id":"0","pronum":4},{"id":"2062","name":"冷饮零食","parent_id":"0","pronum":4},{"id":"2050","name":"休闲速食","parent_id":"0","pronum":57}]
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
         * id : 2072
         * name : 百货用品
         * parent_id : 0
         * pronum : 2
         */

        private String id;
        private String name;
        private String parent_id;
        private int pronum;

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

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public int getPronum() {
            return pronum;
        }

        public void setPronum(int pronum) {
            this.pronum = pronum;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", parent_id='" + parent_id + '\'' +
                    ", pronum=" + pronum +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CateList{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
