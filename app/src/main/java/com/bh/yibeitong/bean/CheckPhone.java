package com.bh.yibeitong.bean;

/**
 * Created by jingang on 2016/11/18.
 * 注册前验证手机号存在
 */

public class CheckPhone {

    /**
     * error : false
     * msg : {"exit":false}
     */

    private boolean error;
    /**
     * exit : false
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
        private boolean exit;

        public boolean isExit() {
            return exit;
        }

        public void setExit(boolean exit) {
            this.exit = exit;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "exit=" + exit +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CheckPhone{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
