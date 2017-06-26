package com.bh.yibeitong.bean.homepage;

/**
 * Created by jingang on 2017/6/26.
 * 外链接
 */

public class Link {


    /**
     * error : false
     * msg : http://m.yiche.com/
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
        return "Link{" +
                "error=" + error +
                ", msg='" + msg + '\'' +
                '}';
    }
}
