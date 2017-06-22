package com.bh.yibeitong.bean;

/**
 * Created by jingang on 2016/11/1.
 * 添加购物车返回码
 */

public class AddShopCart {

    /**
     * error : false
     * msg : {"result":true}
     */

    private boolean error;
    /**
     * result : true
     */

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
        private boolean result;

        public boolean isResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "result=" + result +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AddShopCart{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
