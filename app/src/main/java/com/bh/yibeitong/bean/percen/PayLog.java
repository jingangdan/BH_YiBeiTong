package com.bh.yibeitong.bean.percen;

import java.util.List;

/**
 * Created by jingang on 2017/7/5.
 */

public class PayLog {

    /**
     * error : false
     * msg : [{"id":"92","userid":"13","type":"2","addtype":"2","result":"3.61","addtime":"2017-01-04 10:39","content":"支付订单14834975571337帐号金额减少3.61元","title":"余额支付订单","acount":"752.65","bj":"0"},{"id":"87","userid":"13","type":"2","addtype":"2","result":"0.01","addtime":"2016-12-29 10:32","content":"支付订单14829787158554帐号金额减少0.01元","title":"余额支付订单","acount":"756.26","bj":"0"},{"id":"70","userid":"13","type":"2","addtype":"2","result":"3.60","addtime":"2016-12-17 15:30","content":"支付订单14819598323626帐号金额减少3.60元","title":"余额支付订单","acount":"756.27","bj":"0"},{"id":"69","userid":"13","type":"2","addtype":"1","result":"18.00","addtime":"2016-12-17 11:05","content":"管理员退款给用户","title":"退款处理","acount":"759.87","bj":"0"},{"id":"68","userid":"13","type":"2","addtype":"2","result":"13.61","addtime":"2016-12-17 10:45","content":"支付订单14819427195567帐号金额减少13.61元","title":"余额支付订单","acount":"741.87","bj":"0"},{"id":"67","userid":"13","type":"2","addtype":"2","result":"16.11","addtime":"2016-12-17 10:22","content":"支付订单14819413447434帐号金额减少16.11元","title":"余额支付订单","acount":"755.48","bj":"0"},{"id":"66","userid":"13","type":"2","addtype":"2","result":"18.00","addtime":"2016-12-17 10:14","content":"支付订单14819405994548帐号金额减少18.00元","title":"余额支付订单","acount":"771.59","bj":"0"},{"id":"65","userid":"13","type":"2","addtype":"2","result":"10.80","addtime":"2016-12-17 09:48","content":"支付订单14819392962918帐号金额减少10.80元","title":"余额支付订单","acount":"789.59","bj":"0"},{"id":"64","userid":"13","type":"2","addtype":"2","result":"7.20","addtime":"2016-12-17 09:44","content":"支付订单14819390204765帐号金额减少7.20元","title":"余额支付订单","acount":"800.39","bj":"0"},{"id":"61","userid":"13","type":"2","addtype":"2","result":"12.01","addtime":"2016-12-16 11:35","content":"支付订单14818593156067帐号金额减少12.01元","title":"余额支付订单","acount":"807.59","bj":"0"}]
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
         * id : 92
         * userid : 13
         * type : 2
         * addtype : 2
         * result : 3.61
         * addtime : 2017-01-04 10:39
         * content : 支付订单14834975571337帐号金额减少3.61元
         * title : 余额支付订单
         * acount : 752.65
         * bj : 0
         */

        private String id;
        private String userid;
        private String type;
        private String addtype;
        private String result;
        private String addtime;
        private String content;
        private String title;
        private String acount;
        private String bj;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAddtype() {
            return addtype;
        }

        public void setAddtype(String addtype) {
            this.addtype = addtype;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAcount() {
            return acount;
        }

        public void setAcount(String acount) {
            this.acount = acount;
        }

        public String getBj() {
            return bj;
        }

        public void setBj(String bj) {
            this.bj = bj;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "id='" + id + '\'' +
                    ", userid='" + userid + '\'' +
                    ", type='" + type + '\'' +
                    ", addtype='" + addtype + '\'' +
                    ", result='" + result + '\'' +
                    ", addtime='" + addtime + '\'' +
                    ", content='" + content + '\'' +
                    ", title='" + title + '\'' +
                    ", acount='" + acount + '\'' +
                    ", bj='" + bj + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PayLog{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
