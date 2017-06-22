package com.bh.yibeitong.bean;

/**
 * Created by jingang on 2017/4/6.
 * 版本更新
 */

public class VerCode {

    /**
     * error : true
     * msg : {"msg":"发现新版本，点击确定更新","url":"https://www.ybt9.com/app/ybt.apk"}
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
         * msg : 发现新版本，点击确定更新
         * url : https://www.ybt9.com/app/ybt.apk
         */

        private String msg;
        private String url;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "msg='" + msg + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "VerCode{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
