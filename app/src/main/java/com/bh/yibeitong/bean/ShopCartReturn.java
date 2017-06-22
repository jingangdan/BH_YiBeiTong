package com.bh.yibeitong.bean;

/**
 * Created by jingang on 2016/12/15.
 * 操作（添加 减少）购物车 返回
 */

public class ShopCartReturn {

    /**
     * error : false
     * msg : {"result":false}
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
         * result : false
         */

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
        return "ShopCartReturn{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
