package com.bh.yibeitong.seller.bean;

import java.util.List;

/**
 * Created by jingang on 2017/5/31.
 * 库存查询
 */

public class CheckStock {

    /**
     * error : false
     * msg : [{"item_no":"6907992500010 ","stock_qty":"-48.0000","item_name":"伊利原味优酸乳250ml","item_clsno":"100510 "},{"item_no":"6923644268503 ","stock_qty":"-24.0000","item_name":"蒙牛真果粒草莓味","item_clsno":"100510 "},{"item_no":"6901028157827 ","stock_qty":"-23.0000","item_name":"泰山沂蒙山烤烟型香烟","item_clsno":"70 "},{"item_no":"6901028111263 ","stock_qty":"-9.0000","item_name":"南京（炫赫门）","item_clsno":"70 "},{"item_no":"6901028118170 ","stock_qty":"-6.0000","item_name":"利群烟","item_clsno":"70 "},{"item_no":"6901028300056 ","stock_qty":"-5.0000","item_name":"南京烟（特醇）","item_clsno":"70 "},{"item_no":"6919208585643 ","stock_qty":"-3.0000","item_name":"礼拜天红豆糯米方糕","item_clsno":"3011 "},{"item_no":"6911308110794 ","stock_qty":"-2.0000","item_name":"一个鸡蛋果.燕麦冰淇淋","item_clsno":"3011 "},{"item_no":"6944437022300 ","stock_qty":"-2.0000","item_name":"小样酸Q糖桶装蓝莓味","item_clsno":"300704 "},{"item_no":"6948856314107 ","stock_qty":"-2.0000","item_name":"东北老哈大板","item_clsno":"3011 "},{"item_no":"6957690901084 ","stock_qty":"-1.0000","item_name":"绿豆奶糕","item_clsno":"3011 "},{"item_no":"6938888887519 ","stock_qty":"-1.0000","item_name":"香飘飘芒果布丁奶茶","item_clsno":"300602 "},{"item_no":"6927462205658 ","stock_qty":"-1.0000","item_name":"金锣肉粒多","item_clsno":"100502 "},{"item_no":"00183 ","stock_qty":"-0.2480","item_name":"红石头巧克力","item_clsno":"300730 "},{"item_no":"00346 ","stock_qty":"0.0000","item_name":"薯愿焙烤风情蜂蜜牛奶味","item_clsno":"300709 "},{"item_no":"00347 ","stock_qty":"0.0000","item_name":"薯愿焙烤风情香烤原味","item_clsno":"300709 "},{"item_no":"00348 ","stock_qty":"0.0000","item_name":"薯愿焙烤风情红酒牛排","item_clsno":"300709 "},{"item_no":"00349 ","stock_qty":"0.0000","item_name":"鸡蛋","item_clsno":"3003 "},{"item_no":"00350 ","stock_qty":"0.0000","item_name":"草鸡蛋","item_clsno":"3003 "},{"item_no":"4891338002974 ","stock_qty":"0.0000","item_name":"黑人弹力洁齿软毛牙刷","item_clsno":"500104 "}]
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
         * item_no : 6907992500010
         * stock_qty : -48.0000
         * item_name : 伊利原味优酸乳250ml
         * item_clsno : 100510
         */

        private String item_no;
        private String stock_qty;
        private String item_name;
        private String item_clsno;

        public String getItem_no() {
            return item_no;
        }

        public void setItem_no(String item_no) {
            this.item_no = item_no;
        }

        public String getStock_qty() {
            return stock_qty;
        }

        public void setStock_qty(String stock_qty) {
            this.stock_qty = stock_qty;
        }

        public String getItem_name() {
            return item_name;
        }

        public void setItem_name(String item_name) {
            this.item_name = item_name;
        }

        public String getItem_clsno() {
            return item_clsno;
        }

        public void setItem_clsno(String item_clsno) {
            this.item_clsno = item_clsno;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "item_no='" + item_no + '\'' +
                    ", stock_qty='" + stock_qty + '\'' +
                    ", item_name='" + item_name + '\'' +
                    ", item_clsno='" + item_clsno + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CheckStock{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}
