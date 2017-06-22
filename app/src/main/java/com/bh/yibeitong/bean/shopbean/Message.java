package com.bh.yibeitong.bean.shopbean;

/**
 * Created by binbin on 2016/10/24.
 */

public class Message {
    private String error;
    private MsgBean msgBean;

    public Message() {
    }

    public Message(String error, MsgBean msgBean) {
        this.error = error;
        this.msgBean = msgBean;
    }

    public MsgBean getMsgBean() {
        return msgBean;
    }

    public void setMsgBean(MsgBean msgBean) {
        this.msgBean = msgBean;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "Message{" +
                "error='" + error + '\'' +
                ", msgBean=" + msgBean +
                '}';
    }

}
