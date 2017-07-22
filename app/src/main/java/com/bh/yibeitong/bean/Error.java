package com.bh.yibeitong.bean;

/**
 * Created by jingang on 2016/12/13.
 */

public class Error {

    /**
     * error : true
     * msg : 密码错误
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
        return "Error{" +
                "error=" + error +
                ", msg='" + msg + '\'' +
                '}';
    }

    /*java.lang.SecurityException:
    No permission to write to /storage/emulated/0/ybt_updateVersion/ybt.apk:
    Neither user 10190 nor current process has android.permission.WRITE_EXTERNAL_STORAGE.*/
}

