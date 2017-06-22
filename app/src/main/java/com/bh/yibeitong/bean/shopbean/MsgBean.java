package com.bh.yibeitong.bean.shopbean;

import java.util.List;

/**
 * Created by binbin on 2016/10/24.
 */

public class MsgBean {
    private ShopInfoBean shopInfoBean;
    private String opentype;
    private List<CatefoodslistBean> catefoodslistBeanList;

    public MsgBean() {
    }

    public MsgBean(ShopInfoBean shopInfoBean, String opentype, List<CatefoodslistBean> catefoodslistBeanList) {
        this.shopInfoBean = shopInfoBean;
        this.opentype = opentype;
        this.catefoodslistBeanList = catefoodslistBeanList;
    }

    public List<CatefoodslistBean> getCatefoodslistBeanList() {
        return catefoodslistBeanList;
    }

    public void setCatefoodslistBeanList(List<CatefoodslistBean> catefoodslistBeanList) {
        this.catefoodslistBeanList = catefoodslistBeanList;
    }

    public ShopInfoBean getShopInfoBean() {
        return shopInfoBean;
    }

    public void setShopInfoBean(ShopInfoBean shopInfoBean) {
        this.shopInfoBean = shopInfoBean;
    }

    public String getOpentype() {
        return opentype;
    }

    public void setOpentype(String opentype) {
        this.opentype = opentype;
    }

    @Override
    public String toString() {
        return "MsgBean{" +
                "shopInfoBean=" + shopInfoBean +
                ", opentype='" + opentype + '\'' +
                ", catefoodslistBeanList=" + catefoodslistBeanList +
                '}';
    }

}
