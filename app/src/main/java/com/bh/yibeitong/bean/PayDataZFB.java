package com.bh.yibeitong.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jingang on 2016/12/3.
 * 获取支付宝一些参数
 * 商户PID
 * 商户收款账号
 * 商户私钥，pkcs8格式
 */

public class PayDataZFB {

    /**
     * error : false
     * msg : {"alipay":{"paytype":"acount","orderid":"22716","allcost":"126.00","wmrid":101528,"support":1,"RSA_PRIVATE":"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANyVEa4rwRE3UKFYY3kr0eBGdRwwmxZUOeUqw7V+nR4k9BI1OpTstq/pIAThDSYTNNL7Enl+/Z70YNN4Lr8ghw6VzCKVNfw70Da0+44VT7i1plFcWSLTQeBgMVBy3I5wAqa93Lx/usA6GOYXKo1WXv2ZC7OSoqzleDyD7s6nhv+fAgMBAAECgYBRBiy1ZnsHoOLsMTRwyOjR0e+oiC4feJa/mALFgxmhff9tRMIc/Apq+V36jNQwnTN1ICZ8HGp63ddHNkktiXwx77eIKiOKdlCjbLM+fnsHSFsTDEk2pNFb0I1bC8zg9TNYGB+pfWOc/Asyjyqq1NQv6gvXJfruzqwEmL3Ahfm4sQJBAPHu1PAKzMZCAwmSc2uEAtbIb0K7R7tYo3eOVV1UoVtBRj/cpRMHXiRsl53smPOnNGH1rB68gAm7yyGDydYrDmcCQQDpaG7x2xz6cN1QtsZxP1x2uJWXykE/EQ6F1NQAVmpPW5/mUKPUOYf3vwQ7Rf6DzRQhqRbMymEengEvLZ0KVdIJAkEAs8dCPM4JtRnAoBBP7IlAgtKWuwPoHfOUJXeDP8Nv1BwBU9tmX2mOL4ryb8wtLIIHjvO8X2q7yn/eRp1kEt4T6QJADRPk43g7b2zTeapEBCRgseY1u4OBlF8ISUdbeYld+4ROuUDbbRCsJZ12RzvsvsXG0vv+YwsT1ftUZahQnDuiwQJBAJULLw03/6kl3Egax14mJNE31v5HH5BbUUJzsUoYqF60by1aHH+9fnn0IUk6tMWd8YccEkGBpLS5MT0ONVDLVus=","RSA_PUBLIC":"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB","SELLER":"ybtsc@qq.com","PARTNER":"2088521039616005","Notify_Url":"https://www.ybt9.com/plug/pay/alipayapp/notify_url.php"},"plate":{"acountcost":"1000.00","support":0},"weixin":{"appid":"","noncestr":"","package":"Sign=WXPay","partnerid":"","prepayid":"","timestamp":"","sign":"","support":0},"allcost":"126.00","titlename":"在线充值"}
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
         * alipay : {"paytype":"acount","orderid":"22716","allcost":"126.00","wmrid":101528,"support":1,"RSA_PRIVATE":"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANyVEa4rwRE3UKFYY3kr0eBGdRwwmxZUOeUqw7V+nR4k9BI1OpTstq/pIAThDSYTNNL7Enl+/Z70YNN4Lr8ghw6VzCKVNfw70Da0+44VT7i1plFcWSLTQeBgMVBy3I5wAqa93Lx/usA6GOYXKo1WXv2ZC7OSoqzleDyD7s6nhv+fAgMBAAECgYBRBiy1ZnsHoOLsMTRwyOjR0e+oiC4feJa/mALFgxmhff9tRMIc/Apq+V36jNQwnTN1ICZ8HGp63ddHNkktiXwx77eIKiOKdlCjbLM+fnsHSFsTDEk2pNFb0I1bC8zg9TNYGB+pfWOc/Asyjyqq1NQv6gvXJfruzqwEmL3Ahfm4sQJBAPHu1PAKzMZCAwmSc2uEAtbIb0K7R7tYo3eOVV1UoVtBRj/cpRMHXiRsl53smPOnNGH1rB68gAm7yyGDydYrDmcCQQDpaG7x2xz6cN1QtsZxP1x2uJWXykE/EQ6F1NQAVmpPW5/mUKPUOYf3vwQ7Rf6DzRQhqRbMymEengEvLZ0KVdIJAkEAs8dCPM4JtRnAoBBP7IlAgtKWuwPoHfOUJXeDP8Nv1BwBU9tmX2mOL4ryb8wtLIIHjvO8X2q7yn/eRp1kEt4T6QJADRPk43g7b2zTeapEBCRgseY1u4OBlF8ISUdbeYld+4ROuUDbbRCsJZ12RzvsvsXG0vv+YwsT1ftUZahQnDuiwQJBAJULLw03/6kl3Egax14mJNE31v5HH5BbUUJzsUoYqF60by1aHH+9fnn0IUk6tMWd8YccEkGBpLS5MT0ONVDLVus=","RSA_PUBLIC":"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB","SELLER":"ybtsc@qq.com","PARTNER":"2088521039616005","Notify_Url":"https://www.ybt9.com/plug/pay/alipayapp/notify_url.php"}
         * plate : {"acountcost":"1000.00","support":0}
         * weixin : {"appid":"","noncestr":"","package":"Sign=WXPay","partnerid":"","prepayid":"","timestamp":"","sign":"","support":0}
         * allcost : 126.00
         * titlename : 在线充值
         */

        private AlipayBean alipay;
        private PlateBean plate;
        private WeixinBean weixin;
        private String allcost;
        private String titlename;

        public AlipayBean getAlipay() {
            return alipay;
        }

        public void setAlipay(AlipayBean alipay) {
            this.alipay = alipay;
        }

        public PlateBean getPlate() {
            return plate;
        }

        public void setPlate(PlateBean plate) {
            this.plate = plate;
        }

        public WeixinBean getWeixin() {
            return weixin;
        }

        public void setWeixin(WeixinBean weixin) {
            this.weixin = weixin;
        }

        public String getAllcost() {
            return allcost;
        }

        public void setAllcost(String allcost) {
            this.allcost = allcost;
        }

        public String getTitlename() {
            return titlename;
        }

        public void setTitlename(String titlename) {
            this.titlename = titlename;
        }

        public static class AlipayBean {
            /**
             * paytype : acount
             * orderid : 22716
             * allcost : 126.00
             * wmrid : 101528
             * support : 1
             * RSA_PRIVATE : MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANyVEa4rwRE3UKFYY3kr0eBGdRwwmxZUOeUqw7V+nR4k9BI1OpTstq/pIAThDSYTNNL7Enl+/Z70YNN4Lr8ghw6VzCKVNfw70Da0+44VT7i1plFcWSLTQeBgMVBy3I5wAqa93Lx/usA6GOYXKo1WXv2ZC7OSoqzleDyD7s6nhv+fAgMBAAECgYBRBiy1ZnsHoOLsMTRwyOjR0e+oiC4feJa/mALFgxmhff9tRMIc/Apq+V36jNQwnTN1ICZ8HGp63ddHNkktiXwx77eIKiOKdlCjbLM+fnsHSFsTDEk2pNFb0I1bC8zg9TNYGB+pfWOc/Asyjyqq1NQv6gvXJfruzqwEmL3Ahfm4sQJBAPHu1PAKzMZCAwmSc2uEAtbIb0K7R7tYo3eOVV1UoVtBRj/cpRMHXiRsl53smPOnNGH1rB68gAm7yyGDydYrDmcCQQDpaG7x2xz6cN1QtsZxP1x2uJWXykE/EQ6F1NQAVmpPW5/mUKPUOYf3vwQ7Rf6DzRQhqRbMymEengEvLZ0KVdIJAkEAs8dCPM4JtRnAoBBP7IlAgtKWuwPoHfOUJXeDP8Nv1BwBU9tmX2mOL4ryb8wtLIIHjvO8X2q7yn/eRp1kEt4T6QJADRPk43g7b2zTeapEBCRgseY1u4OBlF8ISUdbeYld+4ROuUDbbRCsJZ12RzvsvsXG0vv+YwsT1ftUZahQnDuiwQJBAJULLw03/6kl3Egax14mJNE31v5HH5BbUUJzsUoYqF60by1aHH+9fnn0IUk6tMWd8YccEkGBpLS5MT0ONVDLVus=
             * RSA_PUBLIC : MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB
             * SELLER : ybtsc@qq.com
             * PARTNER : 2088521039616005
             * Notify_Url : https://www.ybt9.com/plug/pay/alipayapp/notify_url.php
             */

            private String paytype;
            private String orderid;
            private String allcost;
            private int wmrid;
            private int support;
            private String RSA_PRIVATE;
            private String RSA_PUBLIC;
            private String SELLER;
            private String PARTNER;
            private String Notify_Url;

            public String getPaytype() {
                return paytype;
            }

            public void setPaytype(String paytype) {
                this.paytype = paytype;
            }

            public String getOrderid() {
                return orderid;
            }

            public void setOrderid(String orderid) {
                this.orderid = orderid;
            }

            public String getAllcost() {
                return allcost;
            }

            public void setAllcost(String allcost) {
                this.allcost = allcost;
            }

            public int getWmrid() {
                return wmrid;
            }

            public void setWmrid(int wmrid) {
                this.wmrid = wmrid;
            }

            public int getSupport() {
                return support;
            }

            public void setSupport(int support) {
                this.support = support;
            }

            public String getRSA_PRIVATE() {
                return RSA_PRIVATE;
            }

            public void setRSA_PRIVATE(String RSA_PRIVATE) {
                this.RSA_PRIVATE = RSA_PRIVATE;
            }

            public String getRSA_PUBLIC() {
                return RSA_PUBLIC;
            }

            public void setRSA_PUBLIC(String RSA_PUBLIC) {
                this.RSA_PUBLIC = RSA_PUBLIC;
            }

            public String getSELLER() {
                return SELLER;
            }

            public void setSELLER(String SELLER) {
                this.SELLER = SELLER;
            }

            public String getPARTNER() {
                return PARTNER;
            }

            public void setPARTNER(String PARTNER) {
                this.PARTNER = PARTNER;
            }

            public String getNotify_Url() {
                return Notify_Url;
            }

            public void setNotify_Url(String Notify_Url) {
                this.Notify_Url = Notify_Url;
            }
        }

        public static class PlateBean {
            /**
             * acountcost : 1000.00
             * support : 0
             */

            private String acountcost;
            private int support;

            public String getAcountcost() {
                return acountcost;
            }

            public void setAcountcost(String acountcost) {
                this.acountcost = acountcost;
            }

            public int getSupport() {
                return support;
            }

            public void setSupport(int support) {
                this.support = support;
            }
        }

        public static class WeixinBean {
            /**
             * appid :
             * noncestr :
             * package : Sign=WXPay
             * partnerid :
             * prepayid :
             * timestamp :
             * sign :
             * support : 0
             */

            private String appid;
            private String noncestr;
            @SerializedName("package")
            private String packageX;
            private String partnerid;
            private String prepayid;
            private String timestamp;
            private String sign;
            private int support;

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getNoncestr() {
                return noncestr;
            }

            public void setNoncestr(String noncestr) {
                this.noncestr = noncestr;
            }

            public String getPackageX() {
                return packageX;
            }

            public void setPackageX(String packageX) {
                this.packageX = packageX;
            }

            public String getPartnerid() {
                return partnerid;
            }

            public void setPartnerid(String partnerid) {
                this.partnerid = partnerid;
            }

            public String getPrepayid() {
                return prepayid;
            }

            public void setPrepayid(String prepayid) {
                this.prepayid = prepayid;
            }

            public String getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public int getSupport() {
                return support;
            }

            public void setSupport(int support) {
                this.support = support;
            }

            @Override
            public String toString() {
                return "WeixinBean{" +
                        "appid='" + appid + '\'' +
                        ", noncestr='" + noncestr + '\'' +
                        ", packageX='" + packageX + '\'' +
                        ", partnerid='" + partnerid + '\'' +
                        ", prepayid='" + prepayid + '\'' +
                        ", timestamp='" + timestamp + '\'' +
                        ", sign='" + sign + '\'' +
                        ", support=" + support +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "alipay=" + alipay +
                    ", plate=" + plate +
                    ", weixin=" + weixin +
                    ", allcost='" + allcost + '\'' +
                    ", titlename='" + titlename + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PayDataZFB{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
