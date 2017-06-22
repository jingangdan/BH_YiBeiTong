package com.bh.yibeitong.seller.bean;

/**
 * Created by binbin on 2017/2/6.
 */

public class Goods {

    /**
     * error : false
     * msg : 可口可乐/含糖可乐 瓶装饮料
     */

    private boolean error;
    private String msg;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "error=" + error +
                ", msg='" + msg + '\'' +
                '}';
    }
}
