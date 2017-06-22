package com.bh.yibeitong.seller.bean;

/**
 * Created by binbin on 2017/1/19.
 */

public class Manage {
    private String name;
    private String code;
    private String num;

    public Manage() {
    }

    public Manage(String name, String code, String num) {
        this.name = name;
        this.code = code;
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Manage{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", num='" + num + '\'' +
                '}';
    }
}
