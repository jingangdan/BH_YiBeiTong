package com.bh.yibeitong.bean;

/**
 * Created by jingang on 2016/12/7.
 * 删除订单 返回
 */

public class OrderDel {

    /**
     * error : false
     * msg : success
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
        return "OrderDel{" +
                "error=" + error +
                ", msg='" + msg + '\'' +
                '}';
    }
}
