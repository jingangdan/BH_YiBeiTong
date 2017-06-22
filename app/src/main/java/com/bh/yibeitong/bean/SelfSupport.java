package com.bh.yibeitong.bean;

/**
 * Created by jingang on 2016/10/20.
 * 自营专区
 */

public class SelfSupport {
    private String id;
    private String mark;
    private String image;
    private String title;
    private String price;
    private String num;

    public SelfSupport() {
    }

    public SelfSupport(String id, String mark, String image, String title, String price, String num) {
        this.id = id;
        this.mark = mark;
        this.image = image;
        this.title = title;
        this.price = price;
        this.num = num;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "SelfSupport{" +
                "id='" + id + '\'' +
                ", mark='" + mark + '\'' +
                ", image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", num='" + num + '\'' +
                '}';
    }


}
