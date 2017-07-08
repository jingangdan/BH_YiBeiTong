package com.bh.yibeitong.bean;

/**
 * Created by jingang on 2017/4/6.
 * 版本更新
 */

public class VerCode {
    /**
     * error : true
     * msg : {"url":"https://www.ybt9.com/app/ybt.apk","version":"1.1.7","msg":"发现新版本，点击确定更新"}
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
         * url : https://www.ybt9.com/app/ybt.apk
         * version : 1.1.7
         * msg : 发现新版本，点击确定更新
         */

        private String url;
        private String version;
        private String msg;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "url='" + url + '\'' +
                    ", version='" + version + '\'' +
                    ", msg='" + msg + '\'' +
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
    /**
     * error : true
     * msg : {"msg":"发现新版本，点击确定更新","url":"https://www.ybt9.com/app/ybt.apk"}
     */

//    private boolean error;
//    private MsgBean msg;
//
//    public boolean isError() {
//        return error;
//    }
//
//    public void setError(boolean error) {
//        this.error = error;
//    }
//
//    public MsgBean getMsg() {
//        return msg;
//    }
//
//    public void setMsg(MsgBean msg) {
//        this.msg = msg;
//    }
//
//    public static class MsgBean {
//        /**
//         * msg : 发现新版本，点击确定更新
//         * url : https://www.ybt9.com/app/ybt.apk
//         */
//
//        private String msg;
//        private String url;
//
//        public String getMsg() {
//            return msg;
//        }
//
//        public void setMsg(String msg) {
//            this.msg = msg;
//        }
//
//        public String getUrl() {
//            return url;
//        }
//
//        public void setUrl(String url) {
//            this.url = url;
//        }
//
//        @Override
//        public String toString() {
//            return "MsgBean{" +
//                    "msg='" + msg + '\'' +
//                    ", url='" + url + '\'' +
//                    '}';
//        }
//    }
//
//    @Override
//    public String toString() {
//        return "VerCode{" +
//                "error=" + error +
//                ", msg=" + msg +
//                '}';
//    }
}
