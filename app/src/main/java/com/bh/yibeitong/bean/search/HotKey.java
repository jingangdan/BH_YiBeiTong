package com.bh.yibeitong.bean.search;

import java.util.List;

/**
 * Created by jingang on 2016/12/22.
 * 热搜词
 */

public class HotKey {

    /**
     * error : false
     * msg : [{"id":0,"name":"大米花"},{"id":1,"name":"芒果"},{"id":2,"name":"鲜花"},{"id":3,"name":"鸡排"},{"id":4,"name":"光合"},{"id":5,"name":"同城外卖"},{"id":6,"name":"跑腿"},{"id":7,"name":"美食"},{"id":8,"name":"商城"}]
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
         * id : 0
         * name : 大米花
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "HotKey{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
