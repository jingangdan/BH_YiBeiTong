package com.bh.yibeitong.bean.village;

import java.util.List;

/**
 * Created by jingang on 2017/2/21.
 * 上传图片返回
 */

public class ImgRet {

    /**
     * error : false
     * msg : ["/upload/wximages/20170221143656502.jpg"]
     */



    private boolean error;
    private List<String> msg;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<String> getMsg() {
        return msg;
    }

    public void setMsg(List<String> msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ImgRet{" +
                "error=" + error +
                ", msg=" + msg +
                '}';
    }
}

